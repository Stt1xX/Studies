#include "mem.h"
#include "mem_debug.c"

#define HEAP_SIZE (8 * 1024)
#define HEAP_CAPACITY 8175 // 8 * 1024 - 17 (17 - size of header)
#define START_TEST(number) printf("--------------------\n" #number ": Test is running...\n");
#define END_TEST(number) printf(#number ": Test is completed!\n--------------------\n");

enum test_status {
    HEAP_INIT_ERROR = 0,
    MEMORY_ALLOC_ERROR
};

char* responses[] = {"Error in heap initialization\n", "Error in heap termination\n",
                     "Error in memory allocation\n", "Error in memory free\n"};

static void fill_memory(char* to , const size_t count){
    for (size_t i = 0; i < count; i++){
        to[i] = 'a';
    }
}


static void simple_test(){
    START_TEST(1)
    const size_t test_size = 1024;
    if (!heap_init(HEAP_CAPACITY)){
        printf("%s", responses[HEAP_INIT_ERROR]);
        return;
    }
    void* ptr = _malloc(test_size);
    if (!ptr){
        printf("%s", responses[MEMORY_ALLOC_ERROR]);
        return;
    }
    fill_memory(ptr, test_size);  // if _malloc allocated less memory then specified fill_memory throw SEG_ERR
    _free(ptr);
    heap_term();
    END_TEST(1)
}

static void free_one_of_several(){
    START_TEST(2)
    const size_t test_size = 1024;
    if (!heap_init(HEAP_CAPACITY)){
        printf("%s", responses[HEAP_INIT_ERROR]);
        return;
    }
    void* ptr1 = _malloc(test_size);
    void* ptr2 = _malloc(test_size * 2);
    void* ptr3 = _malloc(test_size);
    fill_memory(ptr1, test_size);
    if (!ptr1 || !ptr2 || !ptr3){
        printf("%s", responses[MEMORY_ALLOC_ERROR]);
        return;
    }
    _free(ptr1);
    fill_memory(ptr2, test_size * 2);
    fill_memory(ptr3, test_size);
    heap_term();
    END_TEST(2)
}

static void free_some_of_several(){
    START_TEST(3)
    const size_t test_size = 999;
    if (!heap_init(HEAP_CAPACITY * 2)){
        printf("%s", responses[HEAP_INIT_ERROR]);
        return;
    }
    void* ptr1 = _malloc(test_size);
    void* ptr2 = _malloc(test_size * 2);
    void* ptr3 = _malloc(test_size);
    void* ptr4 = _malloc(test_size * 5);
    fill_memory(ptr1, test_size);
    fill_memory(ptr4, test_size* 5);
    if (!ptr1 || !ptr2 || !ptr3 || !ptr4){
        printf("%s", responses[MEMORY_ALLOC_ERROR]);
        return;
    }
    _free(ptr1);
    _free(ptr4);
    fill_memory(ptr2, test_size * 2);
    fill_memory(ptr3, test_size);
    heap_term();
    END_TEST(3)
}

static void end_of_memory_with_continuation(){
    START_TEST(4)
    const size_t test_size = 1024;
    if (!heap_init(HEAP_CAPACITY)){
        printf("%s", responses[HEAP_INIT_ERROR]);
        return;
    }
    void* ptr1 = _malloc(HEAP_CAPACITY);
    fill_memory(ptr1, HEAP_CAPACITY);
    if (!ptr1){
        printf("%s", responses[MEMORY_ALLOC_ERROR]);
        return;
    }
    void* ptr2 = _malloc(test_size);
    if (!ptr2){
        printf("%s", responses[MEMORY_ALLOC_ERROR]);
        return;
    }
    heap_term();
    END_TEST(4)
}

static void end_of_memory_without_continuation(){
    START_TEST(5)
    const size_t test_size = 1024;
    if (!heap_init(HEAP_CAPACITY)){
        printf("%s", responses[HEAP_INIT_ERROR]);
        return;
    }
    void* ptr1 = _malloc(HEAP_CAPACITY);
    fill_memory(ptr1, HEAP_CAPACITY);
    if (!ptr1) {
        printf("%s", responses[MEMORY_ALLOC_ERROR]);
        return;
    }
    mmap((void*)HEAP_START + HEAP_SIZE, test_size, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
    void* ptr2 = _malloc(test_size);
    if (!ptr2){
        printf("%s", responses[MEMORY_ALLOC_ERROR]);
        return;
    }
    heap_term();
    END_TEST(5)
}


void run_tests(){
    simple_test();
    free_one_of_several();
    free_some_of_several();
    end_of_memory_with_continuation();
    end_of_memory_without_continuation();
}