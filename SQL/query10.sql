

SELECT P2.Name FROM( SELECT s.PID, RANK() OVER (ORDER BY s.longevity DESC) rank	FROM( SELECT P1.PID, max(M1.releaseyear) - min(M1.releaseyear) as longevity FROM MOVIE M1, PERSON P1, ROLES R1 WHERE M1.MID = R1.Movie AND P1.PID = R1.Person GROUP BY P1.PID)s)s1,PERSON P2 WHERE rank = 1 AND P2.PID = s1.PID;
