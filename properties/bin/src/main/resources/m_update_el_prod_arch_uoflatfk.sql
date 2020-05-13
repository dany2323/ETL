update DMALM_EL_PRODOTTI_ARCH 
set DMALM_UNITAORG_FLAT_FK_03 = null;
update DMALM_EL_PRODOTTI_ARCH p 
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_15 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p 
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_14 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p 
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_13 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p 
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_12 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p 
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_11 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p 
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_10 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p 
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_09 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p 
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_08 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p  
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_07 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p  
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_06 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p  
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_05 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p  
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_04 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p  
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_03 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p  
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_02 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p  
set p.DMALM_UNITAORG_FLAT_FK_03 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_01 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORG_FLAT_FK_03 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update DMALM_EL_PRODOTTI_ARCH p  
set p.DMALM_UNITAORG_FLAT_FK_03 = '0'
where p.DMALM_UNITAORGANIZZATIVA_FK_01 ='0';