a.out:		tester.o node.o string_set.o
		g++ -std=c++11 tester.o node.o string_set.o

tester.o:	node.h string_set.h tester.cpp
		g++ -std=c++11 -c tester.cpp

node.o:		node.cpp node.h
		g++ -std=c++11 -c node.cpp

string_set.o:	string_set.h node.h string_set.cpp
		g++ -std=c++11 -c string_set.cpp

clean:		makefile
		rm -f a.out *.o *# *~
