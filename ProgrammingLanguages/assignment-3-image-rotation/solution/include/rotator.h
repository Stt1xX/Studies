#pragma once
#include "header.h"
#include "image.h"

struct image* rotate_image(struct header* header, struct image* image,
                           int16_t angle);
