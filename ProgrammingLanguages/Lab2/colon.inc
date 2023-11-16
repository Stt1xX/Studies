%define adr 0x0
%macro colon 2
        %%item:
        %2: dq adr
        db %1, 0
        %define adr %%item
%endmacro

