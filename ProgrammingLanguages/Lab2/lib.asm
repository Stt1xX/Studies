%define UINT_SIZE 21
%define INT_SIZE 20
%define SYS_READ 0
%define SYS_WRITE 1
%define STDOUT 1
%define STDERR 2
%define STDIN 0
%define SYS_EXIT 60
%define LINE_FEED 0xA
%define ASCII_OFSET 0x30
%define TAB 0x9
%define WHITESPACE 0x20


section .text

global print_err
global exit
global string_length
global print_string
global print_char
global print_newline
global print_int
global print_uint
global string_equals
global read_char
global read_word
global parse_uint
global parse_int
global string_copy
global read_string
; Принимает код возврата и завершает текущий процесс
exit:
    mov rax, SYS_EXIT
    syscall

; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    xor rax, rax
    .loop:
        cmp byte[rdi + rax], 0
        je .end
        inc rax
        jmp .loop
    .end:
        ret    
; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    push rdi
    call string_length
    pop rsi
    mov rdi, STDOUT
    mov rdx, rax
    
    mov rax, SYS_WRITE
    syscall
    ret
    
print_err:
    push rdi
    call string_length
    pop rsi
    mov rdi, STDERR
    mov rdx, rax
    
    mov rax, SYS_WRITE
    syscall
    ret

; Принимает код символа и выводит его в stdout
print_char:
    dec rsp
    mov [rsp], dil
    mov rsi, rsp
    mov rdx, 1
    mov rdi, STDOUT
    mov rax, SYS_WRITE
    syscall
    inc rsp
    ret

; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    mov rdi, LINE_FEED
    jmp print_char

; Выводит беззнаковое 8-байтовое число в десятичном формате
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
print_uint:
    mov rax, rdi
    mov rcx, 10
    mov r11, UINT_SIZE
    sub rsp, r11
    dec r11
    mov byte[rsp + r11], 0
    .iterate:
        xor rdx, rdx
        div rcx
        add rdx, ASCII_OFSET
        dec r11
        mov [rsp + r11], dl
        test rax, rax
        jne .iterate
    lea rdi, [rsp + r11]
    call print_string
    add rsp, UINT_SIZE
    ret


; Выводит знаковое 8-байтовое число в десятичном формате
print_int:
    test rdi, rdi
    jns .plus
    neg rdi
    push rax
    push rdi
    push rsi
    push rdx
    mov dil, '-'
    call print_char
    pop rdx
    pop rsi
    pop rdi
    pop rax
    .plus:
        call print_uint
    ret

; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
string_equals:
        mov cl, [rdi]
        mov dl, [rsi]
        cmp cl, dl
        jne .end
        test cl, cl
        je .ok
        inc rdi
        inc rsi
        jmp string_equals
    .ok:
        mov rax, 1
        ret
    .end:
        xor rax, rax
        ret

; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока

read_char:
        push 0
        mov rax, SYS_READ       ;xor rax, rax
        mov rdi, STDIN          ;xor rdi, rdi
        mov rdx, 1
        mov rsi, rsp
        syscall
        pop rax
        ret

; Принимает: адрес начала буфера, размер буфера
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор

read_word:
    push r12
    push r13
    push r14
    xor r14, r14
    mov r12, rsi
    mov r13, rdi
    .loop:
        call read_char
        cmp al, WHITESPACE
        jz .delete
        cmp al, TAB
        jz .delete
        cmp al, LINE_FEED
        jz .delete
        test al, al
        jz .ok
        cmp r14, r12
        jge .error
        mov [r13 + r14], al
        inc r14
        jmp .loop
    .delete:
        test r14, r14
        jz .loop
    .ok:
        mov byte[r13 + r14], 0
        mov rax, r13
        mov rdx, r14
    .end:
        pop r14
        pop r13
        pop r12
        ret
    .error:
        xor rax, rax
        jmp .end

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
    xor rdx, rdx
    xor rax, rax
    xor rcx, rcx
    mov r10, 10
    .loop:
        mov cl, [rdi]
        cmp cl, '9'
        jg .end
        cmp cl, '0'
        jl .end
        sub cl, ASCII_OFSET
        push rdx
        mul r10
        pop rdx
        add rax, rcx
        inc rdi
        inc rdx
        jmp .loop
    .end:
        ret


; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был)
; rdx = 0 если число прочитать не удалось
parse_int:
    xor rdx, rdx
    xor rax, rax
    xor rcx, rcx
    xor r9, r9 ;flag
    mov r10, 10
    mov cl, [rdi]
    cmp cl, '-'
    jne .loop
    inc rdi
    inc r9
    .loop:
        push r9
        call parse_uint
        pop r9
    .negCheck:
        cmp r9, 1
        jne .end
        neg rax
        inc rdx
    .end:
        ret

; Принимает указатель на строку, указатель на буфер и длину буфера
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
string_copy:
    xor r10, r10
    push rdi
    push rsi
    push rdx
    call string_length
    pop rdx
    pop rsi
    pop rdi
    cmp rax, rdx
    jg .greater
    test rax, rax
    je .greater
    .iterate:
        mov cl, byte[rdi]
        mov [rsi], cl
        dec rax
        inc rsi
        inc rdi
        inc r10
        test rax, rax
        jne .iterate
        mov rax, r10
        mov byte[rsi], 0
        ret
    .greater:
        xor rax, rax
        mov byte[rsi], 0
        ret

; rdi - адрес буфера
; rsi - размер буфера (256 включая 0end)
; ret:
;   -1 - The string's length is too high
;   0 - All good 
read_string:
    mov r10, rdi
    mov r9, rsi
    add r9, rdi
    .loop:
        call read_char
        cmp al, LINE_FEED
        jz .ok
        mov [r10], al
        inc r10
        cmp r10, r9
        jne .loop
    .owerflow:
        mov rax, -1
        ret
    .ok:
        mov byte[r10], 0
        xor rax, rax
        ret