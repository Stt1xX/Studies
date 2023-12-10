#pragma once

#include <inttypes.h>
#include <stdio.h>

#include "header.h"
#include "image.h"

enum read_status {
  READ_OK = 0,
  READ_INVALID_BODY,
  READ_INVALID_HEADER,
  READ_UNKNOWN_TYPE
};

enum read_status read_image(struct header* header, struct image* image,
                            FILE* file);
