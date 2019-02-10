CREATE TABLE IMDB_user(IMBD_id VARCHAR(10) NOT NULL,firstName VARCHAR(20),lastName VARCHAR(20),BirthDate DATE,email VARCHAR(20),birthplace VARCHAR(20),gender VARCHAR(10),PRIMARY KEY (IMBD_id));


INSERT ALL
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID2','juan@gmail.com','Juan','Carlos',to_date('4/12/1994','MM/DD/YYYY'),'AK','M')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID1','john@yahoo.com','John','Smith',to_date('10/5/1995','MM/DD/YYYY'),'FL','M')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID3','Jane@gmail.com','Jane','Chapel',to_date('11/2/1993','MM/DD/YYYY'),'IL','F')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID4','adi@yahoo.com','Aditya','Awasthi',to_date('12/12/1992','MM/DD/YYYY'),'CA','M')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID5','james@hotmail.com','James','Williams',to_date('5/5/1991','MM/DD/YYYY'),'NY','M')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID6','mike@yahoo.com','Mike','Brown',to_date('3/1/1988','MM/DD/YYYY'),'NC','M')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID7','bob@yahoo.com','Bob','Jones',to_date('2/7/1988','MM/DD/YYYY'),'NY','M')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID8','wei@gmail.com','Wei','Zhang',to_date('8/12/1985','MM/DD/YYYY'),'NV','F')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID9','mark@gmail.com','Mark','Davis',to_date('5/10/1984','MM/DD/YYYY'),'CA','M')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID10','daniel@yahoo.com','Daniel','Garcia',to_date('6/1/1980','MM/DD/YYYY'),'NJ','M')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID11','maria@hotmail.com','Maria','Rodriguez',to_date('3/18/1975','MM/DD/YYYY'),'CA','F')
  INTO IMDB_user (IMBD_id, email, firstName, lastName, BirthDate,  birthplace, gender) VALUES ('ID12','freya@yahoo.com','Freya','Wilson',to_date('2/19/1970','MM/DD/YYYY'),'NJ','F')
SELECT * FROM dual;


CREATE TABLE person(pid varchar(10) not null,name varchar(20),birthdate DATE,gender varchar(10),birthplace varchar(20),attribute varchar(20),primary key (pid));

INSERT ALL
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P1','Brian de forma',to_date('9/11/1940 ', 'MM/DD/YYYY'),'M','New York','Director')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P2','Martin Brest',to_date('8/8/1951 ', 'MM/DD/YYYY'),'M','San Jose','Director')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P3','Scarlett Johanson',to_date('11/22/1984 ', 'MM/DD/YYYY'),'F','Austin','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P4','Luc Besson',to_date('5/30/1975 ', 'MM/DD/YYYY'),'F','Paris','Director')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P5','Morgan Freeman',to_date('6/5/1953 ', 'MM/DD/YYYY'),'M','Canberra','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P6','Al Pacino',to_date('11/12/1956 ', 'MM/DD/YYYY'),'M','Portland','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P7','Angelina Jolie',to_date('3/3/1970 ', 'MM/DD/YYYY'),'F','Seattle','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P8','Brad Pitt',to_date('4/4/1975 ', 'MM/DD/YYYY'),'M','London','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P9','Tom Hanks',to_date('5/19/1964 ', 'MM/DD/YYYY'),'M','Perth','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P10','Jessica Alba',to_date('8/7/1983 ', 'MM/DD/YYYY'),'F','Seoul','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P12','Alex Parish',to_date('7/9/1977 ', 'MM/DD/YYYY'),'F','San Jose','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P13','Jack Nicholson',to_date('11/13/1958 ', 'MM/DD/YYYY'),'M','Austin','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P15','Harrison Ford',to_date('9/11/1957 ', 'MM/DD/YYYY'),'M','Canberra','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P16','Julia Roberts',to_date('1/1/1967 ', 'MM/DD/YYYY'),'F','Portland','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P17','Matt Damon',to_date('1/7/1971 ', 'MM/DD/YYYY'),'M','Seattle','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P18','Jennifer Lawrence',to_date('2/2/1962 ', 'MM/DD/YYYY'),'F','London','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P19','George clooney',to_date('3/3/1965 ', 'MM/DD/YYYY'),'M','Perth','Actor')
INTO person ( pid,name,birthdate,gender,birthplace,attribute) values ('P20','Jennifer Aniston',to_date('4/4/1968 ', 'MM/DD/YYYY'),'F','Seoul','Actor')
SELECT * FROM dual;


CREATE TABLE movie(mid VARCHAR (10) NOT NULL,name VARCHAR(100) NOT NULL,releaseyear NUMBER,director VARCHAR(10),primary key (mid));

INSERT ALL
INTO movie (mid,name,releaseyear,director) values('M1','Scarface','1988','P1')
INTO movie (mid,name,releaseyear,director) values('M2','Scent of a women','1995','P2')
INTO movie (mid,name,releaseyear,director) values('M3','My big fat greek wedding','2000','P4')
INTO movie (mid,name,releaseyear,director) values('M4','The Devil''s Advocate','1997','P1')
INTO movie (mid,name,releaseyear,director) values('M5','Mr. and Mrs Smith','1965','P1')
INTO movie (mid,name,releaseyear,director) values('M6','Now You see me','2013','P2')
INTO movie (mid,name,releaseyear,director) values('M7','Barely Lethal','2014','P4')
INTO movie (mid,name,releaseyear,director) values('M8','The Man with one red shoe','1984','P1')
INTO movie (mid,name,releaseyear,director) values('M9','The Polar Express','2010','P2')
INTO movie (mid,name,releaseyear,director) values('M10','Her','2013','P2')
INTO movie (mid,name,releaseyear,director) values('M11','Lucy','2015','P4')
INTO movie (mid,name,releaseyear,director) values('M12','The Da Vinci Code','2005','P4')
INTO movie (mid,name,releaseyear,director) values('M13','The God Father part II','1975','P1')
INTO movie (mid,name,releaseyear,director) values('M15','Angels and Daemons','2009','P2')
INTO movie (mid,name,releaseyear,director) values('M16','The Island','2010','P4')
SELECT * from DUAL;


CREATE TABLE actorList (mid VARCHAR (10) NOT NULL,actors VARCHAR(10),primary key (mid, actors),foreign key (mid) references movie ON DELETE CASCADE);

INSERT ALL
INTO actorList (mid, actors) values('M1','P5')
INTO actorList (mid, actors) values('M1','P6')
INTO actorList (mid, actors) values('M2','P5')
INTO actorList (mid, actors) values('M2','P6')
INTO actorList (mid, actors) values('M3','P7')
INTO actorList (mid, actors) values('M3','P9')
INTO actorList (mid, actors) values('M4','P5')
INTO actorList (mid, actors) values('M4','P6')
INTO actorList (mid, actors) values('M4','P8')
INTO actorList (mid, actors) values('M5','P5')
INTO actorList (mid, actors) values('M5','P7')
INTO actorList (mid, actors) values('M5','P8')
INTO actorList (mid, actors) values('M6','P5')
INTO actorList (mid, actors) values('M6','P7')
INTO actorList (mid, actors) values('M6','P6')
INTO actorList (mid, actors) values('M7','P5')
INTO actorList (mid, actors) values('M7','P10')
INTO actorList (mid, actors) values('M8','P9')
INTO actorList (mid, actors) values('M8','P10')
INTO actorList (mid, actors) values('M9','P9')
INTO actorList (mid, actors) values('M9','P17')
INTO actorList (mid, actors) values('M9','P19')
INTO actorList (mid, actors) values('M10','P5')
INTO actorList (mid, actors) values('M10','P3')
INTO actorList (mid, actors) values('M10','P6')
INTO actorList (mid, actors) values('M11','P9')
INTO actorList (mid, actors) values('M11','P8')
INTO actorList (mid, actors) values('M11','P3')
INTO actorList (mid, actors) values('M11','P5')
INTO actorList (mid, actors) values('M12','P3')
INTO actorList (mid, actors) values('M12','P10')
INTO actorList (mid, actors) values('M12','P9')
INTO actorList (mid, actors) values('M13','P5')
INTO actorList (mid, actors) values('M13','P6')
INTO actorList (mid, actors) values('M13','P16')
INTO actorList (mid, actors) values('M15','P9')
INTO actorList (mid, actors) values('M15','P18')
INTO actorList (mid, actors) values('M15','P12')
INTO actorList (mid, actors) values('M16','P16')
INTO actorList (mid, actors) values('M16','P15')
INTO actorList (mid, actors) values('M16','P10')
Select * from dual;


CREATE TABLE Reviews(
        Movie VARCHAR(10) NOT NULL,
        Author VARCHAR(10) NOT NULL,
        Rating SMALLINT CHECK(Rating >= 0 AND Rating <= 10),
        Votes SMALLINT CHECK(Votes >= 0),
        Publish_Date VARCHAR(24),
        FOREIGN KEY(Movie) REFERENCES movie(mid) ON DELETE CASCADE,
        FOREIGN KEY(Author) REFERENCES IMDB_user(IMBD_id) ON DELETE CASCADE
);



INSERT ALL
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M1', 'ID1', 7, 25, '2007-10-02 09:10:54')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M2', 'ID2', 8, 35, '2007-09-29 13:45:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M2', 'ID3', 9, 24, '2007-09-29 10:38:25')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M3', 'ID4', 10, 8, '2013-10-02 13:05:56')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M3', 'ID5', 9, 11, '2007-10-25 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M4', 'ID6', 8, 6, '2007-09-26 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M4', 'ID7', 7, 23, '2007-09-26 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M5', 'ID9', 9, 22, '2007-09-28 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M6', 'ID10', 8, 26, '2007-10-29 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M7', 'ID11', 8, 27, '2007-09-30 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M7', 'ID12', 8, 18, '2007-10-25 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M8', 'ID1', 7, 19, '2007-09-25 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M9', 'ID2', 7, 16, '2007-09-25 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M10', 'ID3', 8, 18, '2007-09-29 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M11', 'ID4', 9, 22, '2015-06-07 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M11', 'ID5', 10, 13, '2015-05-05 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M12', 'ID6', 9, 50, '2015-05-05 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M13', 'ID7', 5, 34, '2007-10-25 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M13', 'ID1', 4, 34, '2007-10-25 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M15', 'ID10', 8, 25, '2015-05-05 17:15:00')
INTO Reviews(Movie, Author, Rating, Votes, Publish_Date) VALUES('M16', 'ID11', 7, 12, '2015-05-05 17:15:00')
SELECT * from DUAL;


CREATE TABLE genres (
mid VARCHAR (10) NOT NULL,
genre VARCHAR(10),
primary key (mid, genre),
foreign key (mid) references movie
ON DELETE CASCADE
);

INSERT ALL
INTO genres (MID, Genre) VALUES('M1', 'Action')
INTO genres (MID, Genre) VALUES('M2', 'Action')
INTO genres (MID, Genre) VALUES('M2', 'Comedy')
INTO genres (MID, Genre) VALUES('M3', 'Comedy')
INTO genres (MID, Genre) VALUES('M4', 'Thriller')
INTO genres (MID, Genre) VALUES('M5', 'Comedy')
INTO genres (MID, Genre) VALUES('M5', 'Action')
INTO genres (MID, Genre) VALUES('M6', 'Thriller')
INTO genres (MID, Genre) VALUES('M7', 'Action')
INTO genres (MID, Genre) VALUES('M8', 'comedy')
INTO genres (MID, Genre) VALUES('M9', 'comedy')
INTO genres (MID, Genre) VALUES('M10', 'Thriller')
INTO genres (MID, Genre) VALUES('M11', 'Thriller')
INTO genres (MID, Genre) VALUES('M12', 'Action')
INTO genres (MID, Genre) VALUES('M12', 'Thriller')
INTO genres (MID, Genre) VALUES('M13', 'Action')
INTO genres (MID, Genre) VALUES('M13', 'Thriller')
INTO genres (MID, Genre) VALUES('M15', 'Action')
INTO genres (MID, Genre) VALUES('M15', 'Thriller')
INTO genres (MID, Genre) VALUES('M16', 'Action')
INTO genres (MID, Genre) VALUES('M16', 'Comedy')
SELECT * from DUAL;


CREATE TABLE Roles(
        Movie VARCHAR(5) NOT NULL,
        Person VARCHAR(5) NOT NULL,
        Role VARCHAR(40) NOT NULL,
        FOREIGN KEY(Movie) REFERENCES movie(mid) ON DELETE CASCADE,
        FOREIGN KEY(Person) REFERENCES person(pid) ON DELETE CASCADE
);

INSERT ALL
 INTO Roles(Movie, Person, Role) VALUES('M1', 'P5', 'Jessica')
 INTO Roles(Movie, Person, Role) VALUES('M1', 'P6', 'robert')
 INTO Roles(Movie, Person, Role) VALUES('M2', 'P5', 'John')
 INTO Roles(Movie, Person, Role) VALUES('M2', 'P6', 'Mark')
 INTO Roles(Movie, Person, Role) VALUES('M3', 'P9', 'Alex')
 INTO Roles(Movie, Person, Role) VALUES('M3', 'P7', 'Julie')
 INTO Roles(Movie, Person, Role) VALUES('M4', 'P5', 'Jessica')
 INTO Roles(Movie, Person, Role) VALUES('M4', 'P6', 'Matt')
 INTO Roles(Movie, Person, Role) VALUES('M4', 'P8', 'Jennifer')
 INTO Roles(Movie, Person, Role) VALUES('M5', 'P7', 'Jennifer')
 INTO Roles(Movie, Person, Role) VALUES('M5', 'P8', 'Tom')
 INTO Roles(Movie, Person, Role) VALUES('M5', 'P5', 'Milo')
 INTO Roles(Movie, Person, Role) VALUES('M6', 'P6', 'Chris')
 INTO Roles(Movie, Person, Role) VALUES('M6', 'P7', 'Rose')
 INTO Roles(Movie, Person, Role) VALUES('M6', 'P5', 'Bill')
 INTO Roles(Movie, Person, Role) VALUES('M7', 'P10', 'Jane')
 INTO Roles(Movie, Person, Role) VALUES('M7', 'P5', 'Brad')
 INTO Roles(Movie, Person, Role) VALUES('M8', 'P9', 'Lucas')
 INTO Roles(Movie, Person, Role) VALUES('M8', 'P10', 'Juanita')
 INTO Roles(Movie, Person, Role) VALUES('M9', 'P9', 'Clayne')
 INTO Roles(Movie, Person, Role) VALUES('M9', 'P9', 'Jane')
 INTO Roles(Movie, Person, Role) VALUES('M9', 'P9', 'Brad')
 INTO Roles(Movie, Person, Role) VALUES('M9', 'P9', 'Lucas')
 INTO Roles(Movie, Person, Role) VALUES('M9', 'P9', 'Bradley')
 INTO Roles(Movie, Person, Role) VALUES('M9', 'P9', 'Justin')
 INTO Roles(Movie, Person, Role) VALUES('M9', 'P17', 'Martin')
 INTO Roles(Movie, Person, Role) VALUES('M9', 'P19', 'Mike')
 INTO Roles(Movie, Person, Role) VALUES('M10', 'P3', 'Travis')
 INTO Roles(Movie, Person, Role) VALUES('M10', 'P5', 'Alexander')
 INTO Roles(Movie, Person, Role) VALUES('M10', 'P6', 'Justin')
 INTO Roles(Movie, Person, Role) VALUES('M11', 'P3', 'Jessica')
 INTO Roles(Movie, Person, Role) VALUES('M11', 'P5', 'Johnny')
 INTO Roles(Movie, Person, Role) VALUES('M11', 'P8', 'Rami')
 INTO Roles(Movie, Person, Role) VALUES('M11', 'P9', 'Sam')
 INTO Roles(Movie, Person, Role) VALUES('M12', 'P9', 'Gatek')
 INTO Roles(Movie, Person, Role) VALUES('M12', 'P10', 'Rose')
 INTO Roles(Movie, Person, Role) VALUES('M12', 'P3', 'maria')
 INTO Roles(Movie, Person, Role) VALUES('M13', 'P5', 'Travis')
 INTO Roles(Movie, Person, Role) VALUES('M13', 'P6', 'Alexander')
 INTO Roles(Movie, Person, Role) VALUES('M13', 'P16', 'Pearl')
 INTO Roles(Movie, Person, Role) VALUES('M15', 'P12', 'Sofia')
 INTO Roles(Movie, Person, Role) VALUES('M15', 'P18', 'chrissy')
 INTO Roles(Movie, Person, Role) VALUES('M15', 'P9', 'Mike')
 INTO Roles(Movie, Person, Role) VALUES('M16', 'P10', 'Martin')
 INTO Roles(Movie, Person, Role) VALUES('M16', 'P15', 'Bill')
 INTO Roles(Movie, Person, Role) VALUES('M16', 'P16', 'Emilia')
 select * from dual;


