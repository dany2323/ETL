update dmalm_asm 
set DMALM_UNITAORG_FLAT_FK_04 = null;
update dmalm_asm p 
set p.DMALM_UNITAORG_FLAT_FK_04 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_08 = p.DMALM_UNITAORGANIZZATIVA_FK_03
        ) 
where p.DMALM_UNITAORG_FLAT_FK_04 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_03 is not null; 
update dmalm_asm p  
set p.DMALM_UNITAORG_FLAT_FK_04 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_07 = p.DMALM_UNITAORGANIZZATIVA_FK_03
        ) 
where p.DMALM_UNITAORG_FLAT_FK_04 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_03 is not null; 
update dmalm_asm p  
set p.DMALM_UNITAORG_FLAT_FK_04 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_06 = p.DMALM_UNITAORGANIZZATIVA_FK_03
        ) 
where p.DMALM_UNITAORG_FLAT_FK_04 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_03 is not null; 
update dmalm_asm p  
set p.DMALM_UNITAORG_FLAT_FK_04 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_05 = p.DMALM_UNITAORGANIZZATIVA_FK_03
        ) 
where p.DMALM_UNITAORG_FLAT_FK_04 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_03 is not null; 
update dmalm_asm p  
set p.DMALM_UNITAORG_FLAT_FK_04 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_04 = p.DMALM_UNITAORGANIZZATIVA_FK_03
        ) 
where p.DMALM_UNITAORG_FLAT_FK_04 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_03 is not null; 
update dmalm_asm p  
set p.DMALM_UNITAORG_FLAT_FK_04 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_03 = p.DMALM_UNITAORGANIZZATIVA_FK_03
        ) 
where p.DMALM_UNITAORG_FLAT_FK_04 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_03 is not null; 
update dmalm_asm p  
set p.DMALM_UNITAORG_FLAT_FK_04 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_02 = p.DMALM_UNITAORGANIZZATIVA_FK_03
        ) 
where p.DMALM_UNITAORG_FLAT_FK_04 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_03 is not null; 
update dmalm_asm p  
set p.DMALM_UNITAORG_FLAT_FK_04 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_01 = p.DMALM_UNITAORGANIZZATIVA_FK_03
        ) 
where p.DMALM_UNITAORG_FLAT_FK_04 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_03 is not null; 
update dmalm_asm p  
set p.DMALM_UNITAORG_FLAT_FK_04 = '0'
where p.DMALM_UNITAORGANIZZATIVA_FK_03 ='0'; 