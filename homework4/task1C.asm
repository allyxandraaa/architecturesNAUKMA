.model small

.stack 100h

.data
array dw 10 DUP(0) ; масив довжиною 10

.code
main PROC

    mov ax, @data ; завантажуємо адресу сегменту даних
    mov ds, ax

    mov bx, 0 ; позиція масиву
    mov cx, 0 ; лічильник
    mov dx, 0 ; поточний елемент масива
    mov ax, 0 ; множник для mul

    again:
    
    mov ax, cx
    mov dx, cx
    mul dx 

    mov [array + bx], ax
    add bx, 2
    inc cx

    cmp cx, 10
    jne again

     mov cx, 10

    againClear:
    mov [array - bx], 0
    sub bx, 2
    dec cx
    cmp cx, 0
    jne againClear

    mov [array + 2], 1
    
    mov cx, 0 ; і-2 елемент
    mov bx, 0 ; лічильник, також доступ до елементів масиву
    mov dx, 0 ; i-1 елемент
    mov ax, 0 ; 

    fibonacci:
    
    mov cx, [array + bx]
    add bx, 2
    mov dx, [array + bx]
    add cx, dx
    add bx, 2
    mov [array + bx], cx
    sub bx, 2

    cmp bx, 16
    jne fibonacci

    mov ax, 4c00h
    int 21h

main ENDP
end main