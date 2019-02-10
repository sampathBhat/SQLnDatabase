
SELECT tmp.dec FROM(SELECT s.dec, RANK() OVER (ORDER BY s.Count DESC) rank FROM(SELECT (FLOOR(M.releaseyear/10) * 10) AS dec, COUNT(M.mid) AS Count FROM Movie M GROUP BY FLOOR(M.releaseyear/10)) s)
tmp WHERE rank = 1;
