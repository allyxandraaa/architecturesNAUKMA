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

    mov ax, 4c00h
    int 21h

main ENDP
end main