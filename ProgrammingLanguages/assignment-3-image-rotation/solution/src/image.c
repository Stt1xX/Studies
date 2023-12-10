#include "image.h"

#include <inttypes.h>
#include <malloc.h>
#include <stdio.h>

struct image {
  uint64_t width, height;
  struct pixel* data;
};

struct image* create_empty_image(void) {
  struct image* link = malloc(sizeof(struct image));
  *link = (struct image){0};
  return link;
}

void fill(uint64_t width, uint64_t height, struct pixel* data,
          struct image* img) {
  img->data = data;
  img->height = height;
  img->width = width;
}

struct image* create_full_image(uint64_t width, uint64_t height,
                                struct pixel* data) {
  struct image* ret = create_empty_image();
  fill(width, height, data, ret);
  return ret;
}

uint64_t get_width(struct image const* image) { return image->width; }

uint64_t get_height(struct image const* image) { return image->height; }

struct pixel* get_pixel_body(struct image const* image) { return image->data; }

void destroy_image(struct image* image) {
  free(image->data);
  free(image);
}
