WITH actor_pid as (SELECT pid FROM person p
                                   WHERE p.name='Al Pacino'),
     movie_list as (SELECT DISTINCT ar.movie as mid1
                                        FROM actor_pid ap, Roles ar
                                        WHERE ar.person=ap.pid),
        actor_list as (SELECT distinct person as pid1, count(ar1.Movie) as cnt
                                   FROM Roles ar1, movie_list ml
                                   WHERE ar1.Movie in ml.mid1
                                   GROUP BY ar1.person)
SELECT p.name as "co actor", 'Al Pacino' as Actor
FROM person p
WHERE p.pid in (SELECT al2.pid1
                                 FROM actor_list al2, actor_pid ap2
                                 WHERE al2.pid1 != ap2.pid and al2.cnt =(SELECT cnt  FROM actor_list al1, actor_pid ap1  WHERE al1.pid1 = ap1.pid));