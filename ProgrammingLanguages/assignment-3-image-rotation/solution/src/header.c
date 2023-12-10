#include "header.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static enum header_name type_to_enum(char* string) {
  if (string)
    if (!strcmp(string, "bmp")) {
      return BMP;
    } /*else if (!strcmp(string, "jmp")) {
      return JPG;
    } else if (!strcmp(string, "gif")) {
      return GIF;
    } else if (!strcmp(string, "png")) {
      return PNG;
    }*/
  return INVALID;
}

enum header_name set_header_type(char* file_name, struct header* header) {
  char* str = strtok(file_name, ".");
  char* previous = NULL;
  while (str != NULL) {
    previous = str;
    str = strtok(NULL, ".");
  }
  enum header_name type = type_to_enum(previous);
  header->name = type;
  return type;
}
