ASMS = $(wildcard ./*.asm)
INTERMEDIATEDIR = intermediate
OBJECTS = $(patsubst ./%.asm, $(INTERMEDIATEDIR)/%.o, $(ASMS))
DEPENDENCES = $(patsubst ./%.asm, $(INTERMEDIATEDIR)/%.d, $(ASMS))
TARGET = main
NASM_FLAGS = -g -felf64
LD_FLAGS = -o
MAKE_FLAGS = -M -MF

.PHONY: clean test all
.INTERMEDIATE: $(INTERMEDIATEDIR)/%.d

all: $(TARGET)

include $(DEPENDENCES)

$(INTERMEDIATEDIR)/%.d: %.asm
	mkdir -p $(INTERMEDIATEDIR)
	nasm $(NASM_FLAGS) $(MAKE_FLAGS) $@ $<

$(INTERMEDIATEDIR)/%.o: %.asm $(INTERMEDIATEDIR)/%.d
	mkdir -p $(INTERMEDIATEDIR)
	nasm $(NASM_FLAGS) -o $@ $<

$(TARGET): $(OBJECTS)
	ld $(LD_FLAGS) $(TARGET) $(OBJECTS)

clean:
	rm -rf $(TARGET) $(INTERMEDIATEDIR) report.xml
	
test:
	python3 test.py