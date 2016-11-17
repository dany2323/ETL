SPOOL 197_TRIGGER_UO.log
SET DEFINE OFF;
SET ECHO ON;
SET TIMING ON;
SET TIME ON;
SET SQLBLANKLINES ON;
CREATE OR REPLACE TRIGGER TRIGGER_UO 
    BEFORE INSERT ON DMALM_UNITA_ORGANIZZATIVA REFERENCING 
    NEW AS N 
    FOR EACH ROW 
DECLARE 
  err_msg VARCHAR2(200);
  err_code VARCHAR2(20);
  l_today_timestamp   TIMESTAMP := SYSTIMESTAMP;
	V_UO DMALM_UNITA_ORGANIZZATIVA.DMALM_UNITA_ORG_PK%TYPE;
	CURSOR C1 IS SELECT DMALM_UNITA_ORG_PK FROM DMALM_UNITA_ORGANIZZATIVA UO WHERE UO.CD_AREA = :N.CD_AREA AND UO.DT_FINE_VALIDITA=(SELECT MAX(ORG.DT_FINE_VALIDITA) FROM DMALM_UNITA_ORGANIZZATIVA ORG WHERE UO.CD_AREA = ORG.CD_AREA) AND UO.DMALM_UNITA_ORG_PK ^= :N.DMALM_UNITA_ORG_PK; 
	V_PROJECT DMALM_PROJECT%ROWTYPE;
	CURSOR C2 IS SELECT * FROM DMALM_PROJECT PR 
          WHERE PR.DMALM_STRUTTURA_ORG_FK_02 = 
        (select uo.DMALM_UNITA_ORG_PK from dmalm_unita_organizzativa uo where cd_area = :N.CD_AREA and uo.dt_fine_validita =TIMESTAMP '9999-12-31 00:00:00') 
        and PR.DT_FINE_VALIDITA=TIMESTAMP '9999-12-31 00:00:00' 
        and PR.TEMPLATE^='SVILUPPO' and not exists 
        (
        select null from (
        select c_id, 'SIRE' as REPO from dmalm_sire_history_project p where p.DATA_CARICAMENTO = :N.DT_CARICAMENTO
          union all
        select c_id, 'SISS' as REPO from dmalm_siss_history_project p where p.DATA_CARICAMENTO = :N.DT_CARICAMENTO
        ) all_projects
        where pr.ID_PROJECT = all_projects.C_ID 
        and pr.ID_REPOSITORY = all_projects.REPO
        );
	CURSOR C3 IS SELECT * FROM DMALM_PROJECT PR WHERE PR.DMALM_STRUTTURA_ORG_FK_02 = 0 and PR.DT_FINE_VALIDITA=TIMESTAMP '9999-12-31 00:00:00' and PR.TEMPLATE^='SVILUPPO' and not exists 
  (
		select null from (
			select c_id, 'SIRE' as REPO from dmalm_sire_history_project p where p.DATA_CARICAMENTO = :N.DT_CARICAMENTO
				union all
			select c_id, 'SISS' as REPO from dmalm_siss_history_project p where p.DATA_CARICAMENTO = :N.DT_CARICAMENTO
			) all_projects
			where pr.ID_PROJECT = all_projects.C_ID 
			and pr.ID_REPOSITORY = all_projects.REPO
  );
BEGIN
  insert into DMALM_LOG_DEBUG (OBJECT_TYPE,LOG_DEBUG,ACTION,ID,RELATED_OBJECT_NAME,ID_RELATED_OBJECT,DATA_CARICAMENTO)values('TRIGGER_UO', 'INSERTING UO RECORD WITH CD_AREA: "'|| :N.CD_AREA|| '" AND PK: "'||:N.DMALM_UNITA_ORG_PK|| '"','INSERTING',:N.DMALM_UNITA_ORG_PK, 'DMALM_UNITA_ORGANIZZATIVA',0,l_today_timestamp);
 	OPEN C1;
	LOOP
		FETCH C1 INTO V_UO;
		if C1%NOTFOUND and :N.CD_AREA ^= 'Non presente' THEN
			OPEN C3;
      LOOP
				FETCH C3 INTO V_PROJECT;
				EXIT WHEN C3%NOTFOUND;
				if C3%FOUND  THEN
					case V_PROJECT.TEMPLATE
					when 'DEMAND' then  
						if concat('LI',substr(V_PROJECT.NOME_COMPLETO_PROJECT, 1, instr(V_PROJECT.NOME_COMPLETO_PROJECT, '.')-1)) = :N.CD_AREA then
              storicizza_proj_uo(V_PROJECT, :N.DMALM_UNITA_ORG_PK);
            end if;
					when  'IT' then 
						if 'LIA352' = :N.CD_AREA then							
              storicizza_proj_uo(V_PROJECT, :N.DMALM_UNITA_ORG_PK);       
            end if;
					when 'SERDEP' then 
						if :N.CD_AREA=concat ('LI', V_PROJECT.ID_PROJECT) then							
              storicizza_proj_uo(V_PROJECT, :N.DMALM_UNITA_ORG_PK);         
            end if;
					when  'ASSISTENZA' then 
						if instr(V_PROJECT.NOME_COMPLETO_PROJECT, 'Assistenza.') ^= 0 and :N.CD_AREA=concat('LI',substr(V_PROJECT.NOME_COMPLETO_PROJECT,instr(V_PROJECT.NOME_COMPLETO_PROJECT, 'Assistenza.')+11)) then           
              storicizza_proj_uo(V_PROJECT, :N.DMALM_UNITA_ORG_PK);
            elsif instr(V_PROJECT.NOME_COMPLETO_PROJECT, 'AF') ^= 0 and :N.CD_AREA= concat('LI',substr(V_PROJECT.NOME_COMPLETO_PROJECT,instr(V_PROJECT.NOME_COMPLETO_PROJECT, 'AF')+2)) then
              storicizza_proj_uo(V_PROJECT, :N.DMALM_UNITA_ORG_PK);             
            end if;
          else null;  
					end case;
				end if;
      END LOOP;
		elsif :N.CD_AREA='Non presente' THEN
			null;
		else
      OPEN C2;
			LOOP
				FETCH C2 INTO V_PROJECT;
				EXIT
				WHEN C2%NOTFOUND;
					storicizza_proj_uo(V_PROJECT, :N.DMALM_UNITA_ORG_PK);
      end loop;   
		end if;
    FETCH C1 INTO V_UO;
		EXIT WHEN C1%NOTFOUND;
	END LOOP;
EXCEPTION
WHEN OTHERS THEN
    err_code := SQLCODE;
    err_msg := SUBSTR(SQLERRM, 1, 200);
  insert into DMALM_LOG_DEBUG (OBJECT_TYPE,
               LOG_DEBUG,
               ACTION,
               ID,
               RELATED_OBJECT_NAME,
               ID_RELATED_OBJECT,
               DATA_CARICAMENTO)
               values('TRIGGER_UO',
               'EXCEPTION WITH SQLCODE: '||err_code||' AND MESSAGE ' ||err_msg,
               'EXCEPTION',
                V_UO, 
               null,
               0,
               l_today_timestamp);
RAISE;
END; 
/
SHOW ERRORS;
SPOOL OFF;