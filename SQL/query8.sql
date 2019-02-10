create view highrating as select distinct ( person.name )from person where person.pid in (select A.actors from actorList A where mid in (select Movie from Reviews where Reviews.Rating >=8));

select count(*) from highrating;

create view lowrating as select distinct ( person.name )from person where person.pid in (select A.actors from actorList A where mid in (select Movie from Reviews where Reviews.Rating < 8));

select count(*) from lowrating;

select count ( * ) from ((select * from highrating) minus (select * from lowrating));

create view noFlop as select * from ((select * from highrating) minus (select * from lowrating));

select * from (select noFlop.name , count ( actorList.mid ) as M from noFlop , actorList, person where person.pid = actorList.actors and person.name = noFlop.name group by noFlop.name order by M desc , noFlop.name asc) WHERE ROWNUM <= 10;
