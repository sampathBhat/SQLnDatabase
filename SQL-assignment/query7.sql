

SELECT DISTINCT s.releaseyear, movie2.name, s.Rating FROM (SELECT movie1.releaseyear, max(R1.Rating) as Rating FROM MOVIE movie1, REVIEWS R1 WHERE movie1.MID = R1.Movie AND movie1.releaseyear >= 2005 GROUP BY movie1.releaseyear) s, MOVIE movie2, REVIEWS R2 WHERE movie2.MID = R2.Movie AND movie2.releaseyear = s.releaseyear AND R2.Rating = s.Rating ORDER BY s.releaseyear ASC, movie2.name ASC;

