#define _DEFAULT_SOURCE

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "mem_internals.h"
#include "mem.h"
#include "util.h"


void debug_block(struct block_header* b, const char* fmt, ... );
void debug(const char* fmt, ... );

extern inline block_size size_from_capacity( block_capacity cap );
extern inline block_capacity capacity_from_size( block_size sz );

static bool            block_is_big_enough( size_t query, struct block_header* block ) { return block->capacity.bytes >= query; }
static size_t          pages_count   ( size_t mem )                      { return mem / getpagesize() + ((mem % getpagesize()) > 0); }
static size_t          round_pages   ( size_t mem )                      { return getpagesize() * pages_count( mem ) ; }

static void block_init( void* restrict addr, block_size block_sz, void* restrict next ) {
  *((struct block_header*)addr) = (struct block_header) {
    .next = next,
    .capacity = capacity_from_size(block_sz),
    .is_free = true
  };
}

static size_t region_actual_size( size_t query ) { return size_max( round_pages( query ), REGION_MIN_SIZE ); }

extern inline bool region_is_invalid( const struct region* r );

static void* map_pages(void const* addr, size_t length, int additional_flags) {
  return mmap( (void*) addr, length, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS | additional_flags , -1, 0 );
}

/*  аллоцировать регион памяти и инициализировать его блоком */
static struct region alloc_region  ( void const * addr, size_t query ) {
    size_t size = region_actual_size(query + offsetof( struct block_header, contents ) );
    void* ptr = map_pages(addr, size, MAP_FIXED_NOREPLACE);
    if (ptr == MAP_FAILED) {
        ptr = map_pages(addr, size, 0);
    }
    if (ptr == MAP_FAILED){
        return REGION_INVALID;
    }
    block_init(ptr, (block_size){size}, NULL);
    return (struct region){ptr, size, ptr == addr};
}

static void* block_after( struct block_header const* block );

void* heap_init( size_t initial ) {
    const struct region region = alloc_region( HEAP_START, initial );
    if ( region_is_invalid(&region) ) return NULL;

    return region.addr;
}

#define BLOCK_MIN_CAPACITY 24

/*  --- Разделение блоков (если найденный свободный блок слишком большой )--- */

static bool block_splittable( struct block_header* restrict block, size_t query) {
  return block-> is_free && query + offsetof( struct block_header, contents ) + BLOCK_MIN_CAPACITY <= block->capacity.bytes;
}

static bool split_if_too_big( struct block_header* block, size_t query ) {

    size_t first_size = size_max(query, BLOCK_MIN_CAPACITY +
    offsetof(struct block_header, contents));
    if (block != NULL)
        if (block_splittable(block, query)) {
            struct block_header *second_part = (struct block_header*)((void*)block + first_size);
            block_init(second_part,
                       (block_size){size_from_capacity(block->capacity).bytes - first_size},
                       block->next);
            block_init(block, (block_size ){first_size}, second_part);
            return true;
        }
    return false;
}


/*  --- Слияние соседних свободных блоков --- */

static void* block_after( struct block_header const* block )              {
  return  (void*) (block->contents + block->capacity.bytes);
}

static bool blocks_continuous (
                               struct block_header const* fst,
                               struct block_header const* snd ) {
  return (void*)snd == block_after(fst);
}

static bool mergeable(struct block_header const* restrict fst, struct block_header const* restrict snd) {
  return fst->is_free && snd->is_free && blocks_continuous( fst, snd ) ;
}

static bool try_merge_with_next( struct block_header* block ) {
    if (block != NULL && block->next != NULL)
        if(mergeable(block, block->next)) {
            block->capacity.bytes += size_from_capacity(block->next->capacity).bytes;
            block->next = block->next->next;
            return true;
        }
    return false;
}


/*  --- ... ecли размера кучи хватает --- */

struct block_search_result {
  enum {BSR_FOUND_GOOD_BLOCK, BSR_REACHED_END_NOT_FOUND, BSR_CORRUPTED} type;
  struct block_header* block;
};


static struct block_search_result find_good_or_last  ( struct block_header* restrict block, size_t sz )    {
    struct block_header* curr_block = block;
    struct block_header* previous_block = block;
    while(curr_block != NULL){
        if (!curr_block->is_free){
            curr_block = curr_block->next;
            continue;
        }
        if (block_is_big_enough(sz, curr_block)){
            return (struct block_search_result){BSR_FOUND_GOOD_BLOCK, curr_block};
        }
        previous_block = curr_block;
        if (!try_merge_with_next(curr_block)){
            curr_block = curr_block->next;
        }
    }
    return (struct block_search_result){BSR_REACHED_END_NOT_FOUND, previous_block};
}

/*  Попробовать выделить память в куче начиная с блока `block` не пытаясь расширить кучу
 Можно переиспользовать как только кучу расширили. */
static struct block_search_result try_memalloc_existing ( size_t query, struct block_header* block ) {
    struct block_search_result result = find_good_or_last(block, query);
    if (result.type == BSR_FOUND_GOOD_BLOCK){
        split_if_too_big(result.block, query + offsetof(struct block_header, contents));
        while(try_merge_with_next(result.block->next)){}
        result.block->is_free = false;
    }
    return result;
}



static struct block_header* grow_heap( struct block_header* restrict last, size_t query ) {
    void* ptr = block_after(last);
    struct region region = alloc_region(ptr, query);
    struct block_header* big_header = (struct block_header*)(region.addr);
    last->next = big_header;
    if (try_merge_with_next(last))
        return last;
    else
        return big_header;
}

/*  Реализует основную логику malloc и возвращает заголовок выделенного блока */
static struct block_header* memalloc( size_t query, struct block_header* heap_start) {
    struct block_search_result result = try_memalloc_existing(query, heap_start);
    if (result.type == BSR_FOUND_GOOD_BLOCK){
        return result.block;
    }
    struct block_header* new_block = grow_heap(result.block, query);
    if (new_block == NULL) return NULL;
    split_if_too_big(new_block, query + offsetof(struct block_header, contents));
    new_block->is_free = false;
    return new_block;
}

void* _malloc( size_t query ) {
  struct block_header* const addr = memalloc( query, (struct block_header*) HEAP_START );
  if (addr) return addr->contents;
  else return NULL;
}

static struct block_header* block_get_header(void* contents) {
  return (struct block_header*) (((uint8_t*)contents)-offsetof(struct block_header, contents));
}

void _free( void* mem ) {
    if (!mem) return ;
    struct block_header* start_block = block_get_header( mem );
    start_block->is_free = true;
    while(try_merge_with_next(start_block)){}
}

/*  освободить всю память, выделенную под кучу */
void heap_term( ) {
    struct block_header *curr_header = (struct block_header*)(HEAP_START);
    while(curr_header != NULL){
        curr_header->is_free = true;
        curr_header = curr_header->next;
    }
    curr_header = (struct block_header*)(HEAP_START);
    struct block_header* temp;
    while(curr_header != NULL) {
        while(try_merge_with_next(curr_header));
        temp = curr_header;
        curr_header = curr_header->next;
        munmap(temp, round_pages(size_from_capacity(temp->capacity).bytes));
    }
}
