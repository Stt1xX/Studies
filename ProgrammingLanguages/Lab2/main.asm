%include "dict.inc"
%include "word.inc"
%include "lib.inc"

%define BUF_SIZE 256

section .rodata
    notFoundResp: db "Our dictinary doesn't contain this key!", 0xA, 0
    owerflowResp: db "Your key is too large!", 0xA, 0

section .bss
    buffer: resb BUF_SIZE
    
section .text
global _start

_start:
    mov rdi, buffer
    mov rsi, BUF_SIZE
    call read_string
    cmp rax, -1
    je .owerflow
    mov rdi, buffer
    mov rsi, first_word
    call find_word
    test rax, rax
    jnz .yes
    mov rdi, notFoundResp
    jmp .error
.yes:
    push rax
    mov rdi, rax
    call string_length
    inc rax
    pop rdi
    add rdi, rax
    call print_string
    call print_newline
    xor rdi, rdi
    call exit
.owerflow:
    mov rdi, owerflowResp    
.error:
    call print_err
    mov rdi, 1
    call exit