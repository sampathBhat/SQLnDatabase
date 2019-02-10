

SELECT s1.name, s1.count as "cast size" FROM(SELECT M.name, s.count, RANK() OVER (ORDER BY s.Count DESC) rank FROM(SELECT R.Movie, count(DISTINCT R.Person) AS Count FROM Roles R GROUP BY R.Movie) s, Movie M WHERE M.mid = s.Movie) s1 WHERE rank = 1;
