#include "bmp_format.h"

#include <inttypes.h>
#include <malloc.h>
#include <stdint.h>
#include <stdio.h>

#include "image.h"
#include "reader.h"
#include "writer.h"

static const int MULTIPLISITY_OF_WIDTH = 4;

uint32_t get_padding(uint32_t width,
                     uint32_t size_of_pixel) {  // size_of_pixel in bytes
  return MULTIPLISITY_OF_WIDTH - width * size_of_pixel % MULTIPLISITY_OF_WIDTH;
}

static enum read_status read_header(struct bmp_header* header, FILE* file) {
  size_t total_sum_of_reads = fread(header, sizeof(struct bmp_header), 1, file);
  rewind(file);
  if (total_sum_of_reads != 1) {
    return READ_INVALID_HEADER;
  } else {
    return READ_OK;
  }
}

static enum write_status write_header(struct bmp_header* header, FILE* file) {
  size_t total_sum_of_writes =
      fwrite(header, sizeof(struct bmp_header), 1, file);
  rewind(file);
  if (total_sum_of_writes != 1) {
    return WRITE_INVALID_HEADER;
  } else {
    return WRITE_OK;
  }
}

static enum read_status read_body(struct bmp_header* header,
                                  struct image* image, FILE* file) {
  fseek(file, header->bOffBits, SEEK_SET);
  uint16_t pixel_size =
      header->biBitCount / 8;  // biBitCount in bits, we need bytes
  uint32_t padding = get_padding(header->biWidth, pixel_size);
  struct pixel* body = malloc(header->biWidth * header->biHeight * pixel_size);
  if (body) {
    uint8_t* pointer = (uint8_t*)(body);

    for (uint32_t height = 0; height < header->biHeight; height++) {
      if (fread(pointer, header->biWidth * pixel_size, 1, file) != 1) {
        return READ_INVALID_BODY;
      }
      pointer += header->biWidth * pixel_size;
      fseek(file, padding, SEEK_CUR);
    }
    fill(header->biWidth, header->biHeight, body, image);
    return READ_OK;
  }
  return READ_INVALID_BODY;
}

static enum write_status write_body(struct bmp_header* header,
                                    struct image const* image, FILE* file) {
  fseek(file, 0L, SEEK_END);
  uint16_t pixel_size =
      header->biBitCount / 8;  // biBitCount in bits, we need bytes
  uint32_t padding = get_padding(header->biWidth, pixel_size);
  uint8_t* pointer = (uint8_t*)(get_pixel_body(image));
  for (uint32_t height = 0; height < header->biHeight - 1;
       height++) {  // if we read all strings, including last, we catch
                    // exception heap buffer owerflow
    if (fwrite(pointer, header->biWidth * pixel_size + padding, 1, file) !=
        1) {  // read with padding
      return WRITE_INVALID_BODY;
    }
    pointer += header->biWidth * pixel_size;
  }
  if (fwrite(pointer, header->biWidth * pixel_size, 1, file) !=
      1) {  // read without padding
    return WRITE_INVALID_BODY;
  }
  fwrite(pointer, 1, padding, file);

  return WRITE_OK;
}

enum read_status from_bmp(struct bmp_header* header, struct image* image,
                          FILE* file) {
  enum read_status header_status = read_header(header, file);
  if (header_status != READ_OK) {
    return header_status;
  }
  enum read_status body_status = read_body(header, image, file);
  return body_status;
}

enum write_status to_bmp(struct bmp_header* header, struct image const* image,
                         FILE* file) {
  enum write_status header_status = write_header(header, file);
  if (header_status != WRITE_OK) {
    return header_status;
  }
  enum write_status body_status = write_body(header, image, file);

  return body_status;
}
