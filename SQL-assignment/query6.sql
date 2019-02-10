

SELECT P.name as actor, M.name as movie, count(DISTINCT R.Role) as "distinct roles" FROM ROLES R, PERSON P, MOVIE M WHERE M.MID = R.Movie AND P.PID = R.Person AND M.releaseyear = 2010 GROUP BY P.name, M.name HAVING count(distinct R.Role) >= 5;
