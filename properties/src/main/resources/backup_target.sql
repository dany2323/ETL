insert into T_DMALM_ASM                     ( select * from DMALM_ASM ); 
insert into T_DMALM_PROGETTO_SFERA          ( select * from DMALM_PROGETTO_SFERA ); 
insert into T_DMALM_MISURA                  ( select * from DMALM_MISURA );
insert into T_DMALM_AMBIENTE_TECNOLOGICO    ( select * from DMALM_AMBIENTE_TECNOLOGICO   );
insert into T_DMALM_AMBTECN_CLASF           ( select * from DMALM_AMBTECN_CLASF          );
insert into T_DMALM_ANOMALIA_ASSISTENZA     ( select * from DMALM_ANOMALIA_ASSISTENZA    );
insert into T_DMALM_ANOMALIA_PRODOTTO       ( select * from DMALM_ANOMALIA_PRODOTTO      );
insert into T_DMALM_ANOMALIA_PROD_DUMMY     ( select * from DMALM_ANOMALIA_PRODOTTO_DUMMY);
insert into T_DMALM_AREA_TEMATICA           ( select * from DMALM_AREA_TEMATICA          );
insert into T_DMALM_ATTACHMENT              ( select * from DMALM_ATTACHMENT             );
insert into T_DMALM_BUILD                   ( select * from DMALM_BUILD                  );
insert into T_DMALM_CLASSIFICATORI          ( select * from DMALM_CLASSIFICATORI         );
insert into T_DMALM_DIFETTO_PRODOTTO        ( select * from DMALM_DIFETTO_PRODOTTO       );
insert into T_DMALM_DIFETTO_PROD_DUMMY      ( select * from DMALM_DIFETTO_PRODOTTO_DUMMY );
insert into T_DMALM_DOCUMENTO               ( select * from DMALM_DOCUMENTO              );
insert into T_DMALM_FASE                    ( select * from DMALM_FASE                   );
insert into T_DMALM_FUNZIONALITA            ( select * from DMALM_FUNZIONALITA           );
insert into T_DMALM_FUNZIONALITA_CLASF      ( select * from DMALM_FUNZIONALITA_CLASF     );
insert into T_DMALM_HYPERLINK               ( select * from DMALM_HYPERLINK              );
insert into T_DMALM_LINKED_WORKITEMS        ( select * from DMALM_LINKED_WORKITEMS       );
insert into T_DMALM_MANUTENZIONE            ( select * from DMALM_MANUTENZIONE           );
insert into T_DMALM_MODULO                  ( select * from DMALM_MODULO                 );
insert into T_DMALM_MODULO_CLASF            ( select * from DMALM_MODULO_CLASF           );
insert into T_DMALM_PEI                     ( select * from DMALM_PEI                    );
insert into T_DMALM_CLASSIFICATORE			( select * from DMALM_CLASSIFICATORE         );
insert into T_DMALM_PERSONALE               ( select * from DMALM_PERSONALE              );
insert into T_DMALM_PRODOTTO                ( select * from DMALM_PRODOTTO               );
insert into T_DMALM_PRODOTTO_ARCH_CLASF     ( select * from DMALM_PRODOTTO_ARCH_CLASF    );
insert into T_DMALM_PROGETTO_DEMAND         ( select * from DMALM_PROGETTO_DEMAND        );
insert into T_DMALM_PROGETTO_ESE            ( select * from DMALM_PROGETTO_ESE           );
insert into T_DMALM_PROGETTO_SVILUPPO_DEM   ( select * from DMALM_PROGETTO_SVILUPPO_DEM  );
insert into T_DMALM_PROGETTO_SVILUPPO_SVIL  ( select * from DMALM_PROGETTO_SVILUPPO_SVIL );
insert into T_DMALM_PROGRAMMA               ( select * from DMALM_PROGRAMMA              );
insert into T_DMALM_PROJECT                 ( select * from DMALM_PROJECT                );
insert into T_DMALM_PROJECT_ROLES_SGR       ( select * from DMALM_PROJECT_ROLES_SGR      );
insert into T_DMALM_RELEASE_DI_PROGETTO     ( select * from DMALM_RELEASE_DI_PROGETTO    );
insert into T_DMALM_RELEASE_IT              ( select * from DMALM_RELEASE_IT             );
insert into T_DMALM_RELEASE_SERVIZI         ( select * from DMALM_RELEASE_SERVIZI        );
insert into T_DMALM_RICHIESTA_GESTIONE      ( select * from DMALM_RICHIESTA_GESTIONE     );
insert into T_DMALM_RICHIESTA_MANUTENZIONE  ( select * from DMALM_RICHIESTA_MANUTENZIONE );
insert into T_DMALM_SOTTOPROGRAMMA          ( select * from DMALM_SOTTOPROGRAMMA         );
insert into T_DMALM_SOTTOSISTEMA            ( select * from DMALM_SOTTOSISTEMA           );
insert into T_DMALM_STATO_WORKITEM          ( select * from DMALM_STATO_WORKITEM         );
insert into T_DMALM_TASK                    ( select * from DMALM_TASK                   );
insert into T_DMALM_TASK_IT                 ( select * from DMALM_TASK_IT                );
insert into T_DMALM_TESTCASE                ( select * from DMALM_TESTCASE               );
insert into T_DMALM_UNITA_ORGANIZZATIVA     ( select * from DMALM_UNITA_ORGANIZZATIVA    );
insert into T_DMALM_USER                    ( select * from DMALM_USER                   );
insert into T_DMALM_USER_ROLES_SGR          ( select * from DMALM_USER_ROLES_SGR         );
insert into T_DMALM_SCHEDE_SERVIZIO         ( select * from DMALM_SCHEDE_SERVIZIO        );
insert into T_DMALM_WORKITEM_ASSIGNEES		( select * from DMALM_WORKITEM_ASSIGNEES	 );
insert into T_DMALM_EL_AMBIENTE_TECNO       ( select * from DMALM_EL_AMBIENTE_TECNOLOGICO);
insert into T_DMALM_EL_AMBTECN_CLASSIF      ( select * from DMALM_EL_AMBTECN_CLASSIF);
insert into T_DMALM_EL_CLASSIFICATORI       ( select * from DMALM_EL_CLASSIFICATORI);
insert into T_DMALM_EL_FUNZIONALITA	        ( select * from DMALM_EL_FUNZIONALITA);
insert into T_DMALM_EL_MODULI	            ( select * from DMALM_EL_MODULI);
insert into T_DMALM_EL_PERSONALE	        ( select * from DMALM_EL_PERSONALE);
insert into T_DMALM_EL_PRODOTTI_ARCH        ( select * from DMALM_EL_PRODOTTI_ARCH);
insert into T_DMALM_EL_UNITA_ORGANIZZ       ( select * from DMALM_EL_UNITA_ORGANIZZATIVE);
insert into T_DMALM_ASM_PRODOTTI_ARCH       ( select * from DMALM_ASM_PRODOTTI_ARCH);
insert into T_DMALM_PROJECT_PRODOTTI_ARCH   ( select * from DMALM_PROJECT_PRODOTTI_ARCH);
insert into T_DMALM_PROJECT_PRODOTTO        ( select * from DMALM_PROJECT_PRODOTTO);
insert into T_DMALM_ASM_PRODOTTO            ( select * from DMALM_ASM_PRODOTTO);
insert into T_DMALM_ASM_PROJECT_PRODOTTO    ( select * from DMALM_ASM_PROJECT_PRODOTTO);
insert into T_DMALM_ASM_PROJECT_PROD_ARC    ( select * from DMALM_ASM_PROJECT_PRODOTTI_ARC);
insert into T_DMALM_TEMPLATE_ASS_FUNZIONAL  ( select * from DMALM_TEMPLATE_ASS_FUNZIONALE);
insert into T_DMALM_TEMPLATE_DEMAND         ( select * from DMALM_TEMPLATE_DEMAND);
insert into T_DMALM_TEMPLATE_INT_TECNICA    ( select * from DMALM_TEMPLATE_INT_TECNICA);
insert into T_DMALM_TEMPLATE_SERVIZI        ( select * from DMALM_TEMPLATE_SERVIZI);
insert into T_DMALM_TEMPLATE_SVILUPPO       ( select * from DMALM_TEMPLATE_SVILUPPO);
insert into T_DMALM_TEMPLATE_DOCUMENTI      ( select * from DMALM_TEMPLATE_DOCUMENTI);
insert into T_DMALM_FILIERA_ANOMALIE        ( select * from DMALM_FILIERA_ANOMALIE);
insert into T_DMALM_FILIERA_PRODUTTIVA      ( select * from DMALM_FILIERA_PRODUTTIVA);
insert into T_DMALM_EL_UNITA_ORGANIZ_FLAT   ( select * from DMALM_EL_UNITA_ORGANIZZAT_FLAT);
insert into T_DMALM_RICHIESTA_SUPPORTO ( select * from DMALM_RICHIESTA_SUPPORTO);

