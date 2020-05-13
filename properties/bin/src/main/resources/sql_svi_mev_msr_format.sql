 select b.DMALM_STG_MISURA_PK,b.ID_PROGETTO,b.ID_ASM,b.ID_MSR,b.TIPO_PROGETTO,b.APPLICAZIONE,b.NOME_PROGETTO, b.NOME_MISURA  from DMALM_STG_MISURA b 
 where REGEXP_LIKE (b.NOME_MISURA,'*ST[:blank:]*3[:blank:]*(-|_)', 'i') and b.nome_PROGETTO not like 'a0%'
 and not EXISTS(SELECT NOME_PROGETTO,NOME_MISURA from DMALM_STG_MISURA m where REGEXP_LIKE (m.NOME_MISURA,'*ST(1|2)(-|_)','i') and m.nome_PROGETTO not like 'a0%' and m.nome_progetto=b.nome_progetto)
 


