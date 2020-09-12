a.out:		test_pntvec.o pntvec.h pntvec.o
		g++ pntvec.o test_pntvec.o -std=c++11

pntvec.o:	pntvec.h pntvec.cpp
		g++ -c pntvec.cpp -std=c++11

test_pntvec.o:	test_pntvec.cpp pntvec.h
		g++ -c test_pntvec.cpp -std=c++11

clean:		makefile
		rm -f *.o *.out *~ *#

