#pragma once

#include "header.h"
#include "image.h"

enum write_status {
  WRITE_OK = 0,
  WRITE_INVALID_HEADER,
  WRITE_INVALID_BODY,
  WRITE_UNKNOWN_TYPE
};

enum write_status write_image(struct header* header, struct image const* image,
                              FILE* file);
