select distinct CODICE, 'SIRE' as ID_REPOSITORY from total where ID_REPOSITORY = 'SIRE' group by CODICE having count(distinct (TYPE)) >1