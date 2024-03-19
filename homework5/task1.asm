.model small
stack 30h

.data

.code
main PROC
push 49
push 14
call NSD

mov ax, 4c00h
int 21h


main ENDP

nsd PROC
pop dx ; адреса
pop bx
pop ax

again:

cmp ax, bx
jg AxBigger

; BxBigger
sub bx, ax
jmp ignore


AxBigger:
sub ax, bx

ignore:
cmp ax, bx
jnz again

mov bx,0
push dx
ret

nsd ENDP

end main