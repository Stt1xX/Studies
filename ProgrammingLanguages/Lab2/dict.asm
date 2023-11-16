%include "lib.inc"
%define DICT_OFFSET 8
section .text

global find_word

; rdi - указатель на нуль-терменированную строку
; rsi - указатель на начало словоря
; ret - адрес вхождения или zero
find_word:
    mov r10, rdi ; - указатель на нуль-терменированную строку
    mov r9, rsi ; - указатель на начало словоря
.loop:
    add r9, DICT_OFFSET
    mov rdi, r10
    mov rsi, r9
    call string_equals
    test rax, rax
    jnz .found
    sub r9, DICT_OFFSET
    mov r9, qword[r9]
    test r9, r9
    jnz .loop 
.end:
    xor rax, rax ;d
    ret
    
.found:
    mov rax, r9
    ret