#pragma once

#include "bmp_format.h"
#include "image.h"

struct image* rotate_bmp(struct bmp_header* header, struct image const* source,
                         int16_t angle);
