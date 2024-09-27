from enum import IntEnum

class Method(IntEnum):
    EULER = 0
    EULER_IMPROVED = 1
    MILN = 2

class StatusMethod(IntEnum):
    ERROR = 0
    OK = 1

class FindAccYValueRetCode(IntEnum):
    TOO_LOW_H_VALUE = 0
    TOO_MUCH_ITERATIONS = 1
    OK = 2