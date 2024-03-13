.model small

.stack 100h

.data
array dw 20*5 DUP(0) ; двомирній масив, 20 рядків 5 стовпців

.code
main PROC

    mov ax, @data ; завантажуємо адресу сегменту даних
    mov ds, ax

    mov dx, 0 ; лічильник для внутрнішнього циклу, номер колонки
    mov cx, 0 ; лічильник для зовнішнього циклу, номер ряда

    outerloop:
    

    innerLoop:

    mov bx, 0
    push dx

    ; рахуємо офсет bx = cx * 5 * 2 + dx * 2
    mov ax, 2
    mul dx
    add bx, ax
    mov dx, cx
    mov ax, 10
    mul dx
    add bx, ax
    pop dx

    push dx

    ; (x+2)*(y+3) = (cx + 2) * (dx + 3)
    mov ax, cx
    add ax, 2
    add dx, 3
    mul dx

    mov [array + bx], ax
    pop dx
    inc dx
    cmp dx, 5
    jne innerLoop
    mov dx, 0

    inc cx
    cmp cx, 20
    jne outerloop
    
    mov ax, 4c00h
    int 21h


main ENDP
end main