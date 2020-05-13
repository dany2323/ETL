SELECT  max(a.value) as highest_open_cur 
FROM v$sesstat a, v$statname b, v$parameter p 
WHERE  a.statistic# = b.statistic#  
and b.name = 'opened cursors current' 
and p.name= 'open_cursors' group by p.value 
