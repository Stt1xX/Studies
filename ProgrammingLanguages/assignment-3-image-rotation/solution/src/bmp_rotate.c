#include "bmp_rotate.h"

#include <malloc.h>

#include "image.h"

static struct image* rotate_bmp_90(struct bmp_header* header,
                                   struct image const* source) {
  struct image* prepared_image = create_empty_image();
  uint32_t prepared_width = header->biHeight;
  uint32_t prepared_height = header->biWidth;
  uint16_t pixel_size =
      header->biBitCount / 8;  // biBitCount in bits, we need bytes
  header->bFileSize -=
      header->biHeight * get_padding(header->biWidth, pixel_size);
  header->biSizeImage -=
      header->biHeight * get_padding(header->biWidth, pixel_size);
  header->biHeight = prepared_height;
  header->biWidth = prepared_width;
  header->biSizeImage +=
      header->biHeight * get_padding(header->biWidth, pixel_size);
  header->bFileSize +=
      header->biHeight * get_padding(header->biWidth, pixel_size);
  struct pixel* source_pointer = get_pixel_body(source);
  struct pixel* prepared_pointer =
      malloc(pixel_size * prepared_height * prepared_width);
  struct pixel* current_source_pointer;
  struct pixel* current_prepared_pointer = prepared_pointer;
  if (prepared_pointer) {
    for (uint64_t width = get_width(source); width > 0;
         width--) {  
      current_source_pointer = source_pointer + width - 1;
      for (uint64_t height = 1; height <= get_height(source); height++) {
        *current_prepared_pointer = *current_source_pointer;
        current_source_pointer += get_width(source);
        current_prepared_pointer++;
      }
    }
    fill(prepared_width, prepared_height, prepared_pointer, prepared_image);
    return prepared_image;
  }
  return NULL;
}

static struct image* rotate_bmp_180(struct bmp_header* header,
                                    struct image const* source) {
  struct image* prepared_image = create_empty_image();
  uint32_t prepared_width = header->biWidth;
  uint32_t prepared_height = header->biHeight;
  uint16_t pixel_size =
      header->biBitCount / 8;  // biBitCount in bits, we need bytes
  struct pixel* source_pointer = get_pixel_body(source);
  struct pixel* prepared_pointer =
      malloc(pixel_size * prepared_height * prepared_width);
  struct pixel* current_source_pointer =
      source_pointer + get_width(source) * get_height(source) - 1;
  struct pixel* current_prepared_pointer = prepared_pointer;
  if (prepared_pointer) {
    for (uint64_t width = get_width(source); width > 0;
         width--) {  
      for (uint64_t height = 1; height <= get_height(source); height++) {
        *current_prepared_pointer = *current_source_pointer;
        current_source_pointer--;
        current_prepared_pointer++;
      }
    }
    fill(prepared_width, prepared_height, prepared_pointer, prepared_image);
    return prepared_image;
  }
  return NULL;
}

static struct image* rotate_bmp_270(struct bmp_header* header,
                                    struct image const* source) {
  struct image* prepared_image = create_empty_image();
  uint32_t prepared_width = header->biHeight;
  uint32_t prepared_height = header->biWidth;
  uint16_t pixel_size =
      header->biBitCount / 8;  // biBitCount in bits, we need bytes
  header->biSizeImage -=
      header->biHeight * get_padding(header->biWidth, pixel_size);
  header->bFileSize -=
      header->biHeight * get_padding(header->biWidth, pixel_size);
  header->biHeight = prepared_height;
  header->biWidth = prepared_width;
  header->biSizeImage +=
      header->biHeight * get_padding(header->biWidth, pixel_size);
  header->bFileSize +=
      header->biHeight * get_padding(header->biWidth, pixel_size);
  struct pixel* source_pointer =
      get_pixel_body(source) + get_width(source) * (get_height(source) - 1);
  struct pixel* prepared_pointer =
      malloc(pixel_size * prepared_height * prepared_width);
  struct pixel* current_source_pointer;
  struct pixel* current_prepared_pointer = prepared_pointer;
  if (prepared_pointer) {
    for (uint64_t width = 0; width < get_width(source); width++) {
      current_source_pointer = source_pointer + width;
      for (uint64_t height = 1; height <= get_height(source); height++) {
        *current_prepared_pointer = *current_source_pointer;
        current_source_pointer -= get_width(source);
        current_prepared_pointer++;
      }
    }
    fill(prepared_width, prepared_height, prepared_pointer, prepared_image);
    return prepared_image;
  }
  return NULL;
}

struct image* rotate_bmp(struct bmp_header* header, struct image const* source,
                         int16_t angle) {
  struct image* image = (struct image*)(source);
  struct image* new_image;
  switch (angle) {
    case 0:
    case 360:
      return image;
    case 90:
    case -270:
      new_image = rotate_bmp_90(header, source);
      destroy_image(image);
      return new_image;
    case 180:
    case -180:
      new_image = rotate_bmp_180(header, source);
      destroy_image(image);
      return new_image;
    case -90:
    case 270:
      new_image = rotate_bmp_270(header, source);
      destroy_image(image);
      return new_image;
    default:
      destroy_image(image);
      return NULL;
  }
}
