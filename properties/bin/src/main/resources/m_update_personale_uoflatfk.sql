update dmalm_el_personale 
set DMALM_UNITAORGANIZZ_FLAT_FK_02 = null;
update dmalm_el_personale p 
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_15 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA) 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p 
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_14 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_15='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p 
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_13 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_14='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p 
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_12 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_13='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p 
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_11 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_12='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p 
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_10 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_11='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p 
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_09 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_10='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p 
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_08 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_09='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_07 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_08='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_06 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_07='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_05 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_06='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_04 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_05='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_03 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_04='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_02 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_03='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null; 
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = 
        (select MAX(uo.ID_FLAT)
        from DMALM_EL_UNITA_ORGANIZZAT_FLAT uo
        where uo.UNITA_ORG_FK_01 = p.DMALM_UNITAORGANIZZATIVA_FK_01
        and p.dt_inizio_validita between uo.dt_inizio_validita and uo.DT_FINE_VALIDITA
        and uo.UNITA_ORG_FK_02='0') 
where p.DMALM_UNITAORGANIZZ_FLAT_FK_02 is null 
and p.DMALM_UNITAORGANIZZATIVA_FK_01 is not null;
update dmalm_el_personale p  
set p.DMALM_UNITAORGANIZZ_FLAT_FK_02 = '0'
where p.DMALM_UNITAORGANIZZATIVA_FK_01 ='0';