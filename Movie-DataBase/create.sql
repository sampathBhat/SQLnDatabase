CREATE TABLE MOVIE(
id number primary key,
title varchar(256),
year number,
rating float,
numOfRating number);

CREATE TABLE ACTORS(
movie_id number not null,
actor_id varchar(256),
actor_name varchar(256),
ranking number,
foreign key (movie_id) references MOVIE(id) ON DELETE CASCADE);

CREATE TABLE COUNTRY(
movie_id number not null,
country varchar(256),
foreign key (movie_id) REFERENCES MOVIE(id) ON DELETE CASCADE);

CREATE TABLE DIRECTORS(
movie_id number,
directorID varchar(256),
directorName varchar(256),
foreign key (movie_id) REFERENCES MOVIE(id) ON DELETE CASCADE);

CREATE TABLE GENRE(
movie_id number not null,
genre varchar(256),
foreign key (movie_id) REFERENCES MOVIE(id) ON DELETE CASCADE);

CREATE TABLE TAG(
ID number primary key,
tagValue varchar(256));

CREATE TABLE MOVIE_TAG(
movie_id number not null,
tagID number,
tagWieght number,
foreign key (movie_id) REFERENCES MOVIE(id) ON DELETE CASCADE,
foreign key (tagID) REFERENCES TAG(ID) ON DELETE SET NULL);

CREATE TABLE USER_TAG(
user_id number not null,
movie_id number,
tag_id number);

CREATE INDEX i_genre ON GENRE(genre);
CREATE INDEX i_movie_tag ON MOVIE_TAG(tagID);
CREATE INDEX i_actorName ON ACTORS(actor_name);