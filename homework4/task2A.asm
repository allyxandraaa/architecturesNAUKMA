.model small
stack 30h

.data

.code
main PROC
push 1
push 3
call sumFunction

mov ax, 4c00h
int 21h

main ENDP

sumFunction PROC
pop dx ; збережемо адресу повернення
pop ax ; завантажимо параметри
pop bx
push dx
add ax, bx
ret
sumFunction ENDP

end main