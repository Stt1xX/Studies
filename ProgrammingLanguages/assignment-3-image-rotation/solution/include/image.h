#pragma once

#include <inttypes.h>

struct image;

struct pixel {
  uint8_t b, g, r;
};

struct image* create_empty_image(void);
void fill(uint64_t width, uint64_t height, struct pixel* data,
          struct image* img);
struct pixel* get_pixel_body(struct image const* image);
uint64_t get_height(struct image const* image);
uint64_t get_width(struct image const* image);
void destroy_image(struct image* image);
