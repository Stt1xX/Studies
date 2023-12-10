#include <inttypes.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

#include "bmp_format.h"
#include "header.h"
#include "image.h"
#include "reader.h"
#include "rotator.h"
#include "writer.h"

int main(int argc, char** argv) {
  if (argc != 4) {
    return 1;
  }
  char* file_in = argv[1];
  char* file_out = argv[2];
  int16_t angle = (int16_t)atoi(argv[3]);

  //(void)argv, (void)argc;
  // char* file_in = "input.bmp";
  // char* file_out = "output.bmp";
  // int16_t angle = 90;

  char* copy_of_name_file =
      file_in;  // because set_header_type( char* name_of file, *header)
                // function changes pointer of str;

  struct header header = {0};
  struct image* source_image = create_empty_image();

  FILE* in = fopen(file_in, "rb");
  set_header_type(copy_of_name_file, &header);
  read_image(&header, source_image, in);
  fclose(in);

  struct image* new_image = rotate_image(&header, source_image, angle);

  FILE* out = fopen(file_out, "wb");
  write_image(&header, new_image, out);
  fclose(out);

  destroy_image(new_image);

  return 0;
}
