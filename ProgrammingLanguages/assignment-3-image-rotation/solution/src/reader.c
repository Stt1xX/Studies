
#include "reader.h"

#include "bmp_format.h"
#include "header.h"
#include "image.h"

enum read_status read_image(struct header* header, struct image* image,
                            FILE* file) {
  switch (header->name) {
    case BMP:
      return from_bmp(&(header->format.as_bmp), image, file);
    case INVALID:
      return READ_UNKNOWN_TYPE;
  }
  return READ_UNKNOWN_TYPE;
}
