
SELECT M.MID, M.name, M.releaseyear, P.Name as Director FROM( SELECT s.Movie, RANK() OVER (ORDER BY s.number_of_ratings DESC) rating_rank, RANK() OVER (ORDER BY s.average ASC) average_rank 
FROM(
		SELECT R.Movie, count(R.Rating) as number_of_ratings, avg(R.Rating) as average
		FROM REVIEWS R
		GROUP BY R.Movie
	) s
) s1, MOVIE M, PERSON P
WHERE rating_rank = 1 AND average_rank = 1 AND M.MID = s1.Movie AND M.Director = P.PID;
