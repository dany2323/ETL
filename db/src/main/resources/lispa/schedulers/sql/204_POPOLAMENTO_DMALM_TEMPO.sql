SPOOL 204_POPOLAMENTO_DMALM_TEMPO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;SET SQLBLANKLINES ON;
INSERT INTO DMALM_TEMPO(DMALM_TEMPO_PK,DT_OSSERVAZIONE,GIORNO,DS_GIORNO,MESE,DS_MESE,TRIMESTRE,SEMESTRE,ANNO,FL_FESTIVO) VALUES (1,TIMESTAMP '2003-01-01 00:00:00',1,'Mercoledi','1','Gennaio 2003','20031','20031','2003',1);
insert into DMALM_TEMPO(
					SELECT  (SELECT MAX(DMALM_TEMPO_PK) from DMALM_TEMPO) + rownum AS DMALM_TEMPO_PK,
	       					(select max(DT_OSSERVAZIONE) from DMALM_TEMPO) + rownum AS DT_OSSERVAZIONE,
	       					TO_NUMBER (TO_CHAR (mydate, 'DD')) AS GIORNO,
	      					TO_CHAR (mydate, 'Day', 'NLS_DATE_LANGUAGE=ITALIAN') AS DS_GIORNO,
	      					TO_CHAR (mydate, 'MM') AS MESE,
	     					TO_CHAR (mydate, 'Month yyyy','NLS_DATE_LANGUAGE=ITALIAN') AS DS_MESE,
	     					TO_CHAR (mydate, 'yyyyQ','NLS_DATE_LANGUAGE=ITALIAN') AS TRIMESTRE,
	        					TO_CHAR (mydate, 'yyyy')||CEIL((TO_CHAR (mydate, 'Q'))/2) as SEMESTRE,
	       					TRUNC(TO_CHAR (mydate, 'YYYY')) AS ANNO,
	       	 				CASE WHEN TO_NUMBER (TO_CHAR (mydate, 'D')) IN (1, 7) OR 
	        		   					TO_CHAR(TRUNC(mydate), 'mm-dd' )in('01-01','01-06','11-01','12-08','12-25','12-26','05-01','06-02','08-15') OR
	        		   					TO_CHAR(TRUNC(mydate), 'yyyy-mm-dd')in('2016-03-28','2017-04-17','2018-04-02','2019-04-22','2020-04-13','2021-04-05','2022-04-18','2023-04-10','2024-04-01','2025-04-21','2026-04-06',
												   				'2027-03-29','2028-04-17','2029-04-02','2030-04-22') THEN 1
           					ELSE 0
	      					END AS FL_FESTIVO     
	  	 					FROM ( 	SELECT TRUNC ((select max(DT_OSSERVAZIONE)+1 from DMALM_TEMPO))-1 + LEVEL AS mydate
	           					  	FROM dual
	         							CONNECT BY LEVEL <= 365 * 20 + 5
	       						)
					);		
COMMIT;					
SPOOL OFF;