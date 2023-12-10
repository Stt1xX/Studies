#pragma once

#include "bmp_format.h"
#include "image.h"
#include "reader.h"
#include "writer.h"

enum read_status from_bmp(struct bmp_header* header, struct image* image,
                          FILE* file);

enum write_status to_bmp(struct bmp_header* header, struct image const* image,
                         FILE* file);

uint32_t get_padding(uint32_t width, uint32_t size_of_pixel);
