CC = bcc32
INCLUDE = ../include
LIB = ../lib

all : Capture.o
	@echo "-------------------------------TERMINER------------------\n"

Capture.o : Capture.c $(INCLUDE)/Capture.h
	$(CC) -c -I$(INCLUDE) $*.c -o$*.obj

Capture : Capture.obj
	@echo "--------------------COMPILATION CAPTURE.C--------------------\n"
	$(CC) -I$(INCLUDE) $*.obj -o$*
	

clean :
	-del *.o *.obj
	-del *.tds
	-del *.swp
	-del *~
