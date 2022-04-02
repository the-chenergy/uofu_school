-- create table Patrons (CardNum int primary key, Name varchar(255)); create table Phones (CardNum int not null, Phone varchar(255), foreign key (CardNum) references Patrons(CardNum)); create table Titles (ISBN varchar(255) primary key, Title varchar(255) not null, Author varchar(255) not null); create table Inventory (Serial int primary key, ISBN varchar(255) not null, foreign key (ISBN) references Titles(ISBN)); create table CheckedOut (CardNum int not null, Serial int not null, primary key (CardNum, Serial), foreign key (CardNum) references Patrons(CardNum), foreign key (Serial) references Inventory(Serial)); insert into Patrons values (1, 'Joe'), (2, 'Ann'), (3, 'Ben'), (4, 'Dan'); insert into Phones values (1, '555-5555'), (2, '666-6666'), (3, '777-7777'), (4, '888-8888'), (4, '999-9999'); insert into Titles values ('978-0547928227', 'The Hobbit', 'Tolkien'), ('978-0679732242', 'The Sound and the Fury', 'Faulkner'), ('978-0394823379', 'The Lorax', 'Seuss'), ('978-0062278791', 'Profiles in Courage', 'Kennedy'), ('978-0441172719', 'Dune', 'Herbert'); insert into Inventory values (1001, '978-0547928227'), (1002, '978-0547928227'), (1003, '978-0679732242'), (1004, '978-0394823379'), (1005, '978-0394823379'), (1006, '978-0062278791'); insert into CheckedOut values (1, 1001), (1, 1004), (4, 1005), (4, 1006);
-- drop table CheckedOut; drop table Inventory; drop table Titles; drop table Phones; drop table Patrons;








