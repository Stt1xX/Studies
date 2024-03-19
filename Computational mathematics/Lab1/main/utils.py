class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKCYAN = '\033[96m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

class data_sources:
    CONSOLE = 0
    FILE = 1
    RAND = 2
    
class read_file_return_codes:
    OK = 0
    INVALID_PATH = 1
    INVALID_FILE = 2

class read_equation_return_codes:
    OK = 0
    INVALID_NUMBER_OF_VARIABLES = 1
    INVALID_COEFFICIENTS = 2

class simple_iterations_codes:
    OK = 0
    INVALID_EQUATION = 1
