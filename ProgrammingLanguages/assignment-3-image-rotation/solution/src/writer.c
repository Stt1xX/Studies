#include "writer.h"

#include "bmp_format.h"
#include "header.h"
#include "image.h"

enum write_status write_image(struct header* header, struct image const* image,
                              FILE* file) {
  switch (header->name) {
    case BMP:
      return to_bmp(&(header->format.as_bmp), image, file);
    case INVALID:
      return WRITE_UNKNOWN_TYPE;
  }
  return WRITE_UNKNOWN_TYPE;
}
