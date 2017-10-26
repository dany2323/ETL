update dmalm_el_personale 
set DMALM_UNITAORGANIZZ_FLAT_FK_02 = null;
update dmalm_el_personale p 
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_08 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_07 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_06 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_05 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_04 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_03 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_02 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_01 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.DT_INIZIO_VALIDITA and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null;
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = '0'
where p.DMALM_UNITAORGANIZZATIVA_FK_01 ='0';
