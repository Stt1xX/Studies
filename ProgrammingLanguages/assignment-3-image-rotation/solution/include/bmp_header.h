#pragma once

#include <inttypes.h>
#include <stdio.h>

#pragma pack(push, 1)
struct bmp_header {
  uint16_t bfType;
  uint32_t bFileSize;
  uint32_t bfReserved;
  uint32_t bOffBits;
  uint32_t biSize;
  uint32_t biWidth;
  uint32_t biHeight;
  uint16_t biPlanes;
  uint16_t biBitCount;
  uint32_t biCompression;
  uint32_t biSizeImage;
  uint32_t biXPElsPerMeter;
  uint32_t biYPElsPerMeter;
  uint32_t biClrUsed;
  uint32_t biClrImportant;
};
#pragma pack(pop)
