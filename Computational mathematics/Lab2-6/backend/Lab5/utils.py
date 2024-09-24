from enum import IntEnum

class StatusMethod(IntEnum):
    ERROR = 0
    OK = 1

class StatusInterpol(IntEnum):
    ERROR = 0
    FIRST_TYPE_OK = 1
    SECOND_TYPE_OK = 2