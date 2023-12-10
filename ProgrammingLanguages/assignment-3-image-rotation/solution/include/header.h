#pragma once

#include "bmp_header.h"

enum header_name { BMP = 0 /*, JPG, PNG, GIF*/ , INVALID };

struct header {
  enum header_name name;
  union header_format {
    struct bmp_header as_bmp;
  } format;
};

enum header_name set_header_type(char* file_name, struct header* header);
