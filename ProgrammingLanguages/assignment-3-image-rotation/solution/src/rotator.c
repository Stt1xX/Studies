#include "rotator.h"

#include <inttypes.h>

#include "bmp_rotate.h"
#include "header.h"
#include "image.h"

struct image* rotate_image(struct header* header, struct image* image,
                           int16_t angle) {  // clockwise rotation by 90 degrees
  switch (header->name) {
    case BMP:
      return rotate_bmp(&(header->format.as_bmp), image, angle);
    case INVALID:
      return NULL;
  }
  return NULL;
}
