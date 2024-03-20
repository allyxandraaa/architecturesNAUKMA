.model small

.stack 100h

.data
array dw 12*12 DUP(0) ; двомирній масив, 12 рядків 12 стовпців

.code
main PROC

    mov ax, @data ; завантажуємо адресу сегменту даних
    mov ds, ax

    mov bx, 0 ; лічильник для зовнішнього циклу, номер ряда      - X
    mov dx, 0 ; лічильник для внутрнішнього циклу, номер стовпця - Y

    outerloop:
    
    mov dx, 0
    innerLoop:
    
    call calculate
    call write

    inc dx
    cmp dx, 12
    jne innerLoop

    inc bx
    cmp bx, 12
    jne outerloop
    
    mov ax, 4c00h
    int 21h

main ENDP

; рахує ax = Y - Y * X =  dx - dx * bx
calculate PROC 
    push dx
    mov ax, dx
    mul bx
    pop dx
    neg ax
    add ax, dx
    ret    
calculate ENDP

write PROC
    mov cx, 0
    push ax ; зберігаємо значення для запису
    ; рахуємо офсет cx = bx * 12 * 2 + dx * 2
    mov ax, 24
    push dx
    mul bx
    add cx, ax
    ; cx += 24 * bx
    mov ax, 2
    pop dx
    push dx
    mul dx
    pop dx
    add cx, ax
    ; cx += 2 * dx
    pop ax
    push bx
    ; зберегти значення рядка
    mov bx, cx
    mov [array + bx], ax ; записуємо значення в масив
    pop bx
    ret
write ENDP

end main