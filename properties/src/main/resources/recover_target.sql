insert into DMALM_EL_UNITA_ORGANIZZATIVE  ( select * from T_DMALM_EL_UNITA_ORGANIZZ);
insert into DMALM_EL_PERSONALE            ( select * from T_DMALM_EL_PERSONALE);
insert into DMALM_EL_CLASSIFICATORI       ( select * from T_DMALM_EL_CLASSIFICATORI);
insert into DMALM_EL_PRODOTTI_ARCH        ( select * from T_DMALM_EL_PRODOTTI_ARCH);
insert into DMALM_EL_MODULI               ( select * from T_DMALM_EL_MODULI);
insert into DMALM_EL_FUNZIONALITA         ( select * from T_DMALM_EL_FUNZIONALITA);
insert into DMALM_EL_AMBIENTE_TECNOLOGICO ( select * from T_DMALM_EL_AMBIENTE_TECNO);
insert into DMALM_EL_AMBTECN_CLASSIF      ( select * from T_DMALM_EL_AMBTECN_CLASSIF);
insert into DMALM_UNITA_ORGANIZZATIVA     ( select * from T_DMALM_UNITA_ORGANIZZATIVA      );
insert into DMALM_PERSONALE               ( select * from T_DMALM_PERSONALE                );
insert into DMALM_ASM                     ( select * from T_DMALM_ASM ); 
insert into DMALM_PROGETTO_SFERA          ( select * from T_DMALM_PROGETTO_SFERA ); 
insert into DMALM_MISURA                  ( select * from T_DMALM_MISURA ); 
insert into DMALM_AREA_TEMATICA           ( select * from T_DMALM_AREA_TEMATICA            );
insert into DMALM_PROJECT                 ( select * from T_DMALM_PROJECT                  );
insert into DMALM_USER                    ( select * from T_DMALM_USER                     );
insert into DMALM_STATO_WORKITEM          ( select * from T_DMALM_STATO_WORKITEM           );
insert into DMALM_PROJECT_ROLES_SGR       ( select * from T_DMALM_PROJECT_ROLES_SGR        );
insert into DMALM_USER_ROLES_SGR          ( select * from T_DMALM_USER_ROLES_SGR           );
insert into DMALM_SCHEDE_SERVIZIO         ( select * from T_DMALM_SCHEDE_SERVIZIO          );
insert into DMALM_ANOMALIA_ASSISTENZA     ( select * from T_DMALM_ANOMALIA_ASSISTENZA      );
insert into DMALM_ANOMALIA_PRODOTTO       ( select * from T_DMALM_ANOMALIA_PRODOTTO        );
insert into DMALM_ANOMALIA_PRODOTTO_DUMMY ( select * from T_DMALM_ANOMALIA_PROD_DUMMY      );
insert into DMALM_BUILD                   ( select * from T_DMALM_BUILD                    );
insert into DMALM_DIFETTO_PRODOTTO        ( select * from T_DMALM_DIFETTO_PRODOTTO         );
insert into DMALM_DIFETTO_PRODOTTO_DUMMY  ( select * from T_DMALM_DIFETTO_PROD_DUMMY       );
insert into DMALM_DOCUMENTO               ( select * from T_DMALM_DOCUMENTO                );
insert into DMALM_FASE                    ( select * from T_DMALM_FASE                     );
insert into DMALM_MANUTENZIONE            ( select * from T_DMALM_MANUTENZIONE             );
insert into DMALM_PEI                     ( select * from T_DMALM_PEI                      );
insert into DMALM_CLASSIFICATORE          ( select * from T_DMALM_CLASSIFICATORE           );
insert into DMALM_PROGETTO_DEMAND         ( select * from T_DMALM_PROGETTO_DEMAND          );
insert into DMALM_PROGETTO_ESE            ( select * from T_DMALM_PROGETTO_ESE             );
insert into DMALM_PROGETTO_SVILUPPO_DEM   ( select * from T_DMALM_PROGETTO_SVILUPPO_DEM    );
insert into DMALM_PROGETTO_SVILUPPO_SVIL  ( select * from T_DMALM_PROGETTO_SVILUPPO_SVIL   );
insert into DMALM_PROGRAMMA               ( select * from T_DMALM_PROGRAMMA                );
insert into DMALM_RELEASE_DI_PROGETTO     ( select * from T_DMALM_RELEASE_DI_PROGETTO      );
insert into DMALM_RELEASE_IT              ( select * from T_DMALM_RELEASE_IT               );
insert into DMALM_RELEASE_SERVIZI         ( select * from T_DMALM_RELEASE_SERVIZI          );
insert into DMALM_RICHIESTA_GESTIONE      ( select * from T_DMALM_RICHIESTA_GESTIONE       );
insert into DMALM_RICHIESTA_MANUTENZIONE  ( select * from T_DMALM_RICHIESTA_MANUTENZIONE   );
insert into DMALM_SOTTOPROGRAMMA          ( select * from T_DMALM_SOTTOPROGRAMMA           );
insert into DMALM_TASK                    ( select * from T_DMALM_TASK                     );
insert into DMALM_TASK_IT                 ( select * from T_DMALM_TASK_IT                  );
insert into DMALM_TESTCASE                ( select * from T_DMALM_TESTCASE                 );
insert into DMALM_HYPERLINK               ( select * from T_DMALM_HYPERLINK                );
insert into DMALM_ATTACHMENT              ( select * from T_DMALM_ATTACHMENT               );
insert into DMALM_LINKED_WORKITEMS        ( select * from T_DMALM_LINKED_WORKITEMS         );
insert into DMALM_CLASSIFICATORI          ( select * from T_DMALM_CLASSIFICATORI           );
insert into DMALM_FUNZIONALITA            ( select * from T_DMALM_FUNZIONALITA             );
insert into DMALM_PRODOTTO                ( select * from T_DMALM_PRODOTTO                 );
insert into DMALM_SOTTOSISTEMA            ( select * from T_DMALM_SOTTOSISTEMA             );
insert into DMALM_MODULO                  ( select * from T_DMALM_MODULO                   );
insert into DMALM_AMBIENTE_TECNOLOGICO    ( select * from T_DMALM_AMBIENTE_TECNOLOGICO     );
insert into DMALM_FUNZIONALITA_CLASF      ( select * from T_DMALM_FUNZIONALITA_CLASF       );
insert into DMALM_PRODOTTO_ARCH_CLASF     ( select * from T_DMALM_PRODOTTO_ARCH_CLASF      );
insert into DMALM_MODULO_CLASF            ( select * from T_DMALM_MODULO_CLASF             );
insert into DMALM_AMBTECN_CLASF           ( select * from T_DMALM_AMBTECN_CLASF            );
insert into DMALM_WORKITEM_ASSIGNEES      ( select * from T_DMALM_WORKITEM_ASSIGNEES	   );
insert into DMALM_PROJECT_PRODOTTO		  ( select * from T_DMALM_PROJECT_PRODOTTO);
insert into DMALM_ASM_PRODOTTO            ( select * from T_DMALM_ASM_PRODOTTO); 
insert into DMALM_ASM_PRODOTTI_ARCH       ( select * from T_DMALM_ASM_PRODOTTI_ARCH); 
insert into DMALM_PROJECT_PRODOTTI_ARCH   ( select * from T_DMALM_PROJECT_PRODOTTI_ARCH); 
insert into DMALM_ASM_PROJECT_PRODOTTO    ( select * from T_DMALM_ASM_PROJECT_PRODOTTO); 
insert into DMALM_ASM_PROJECT_PRODOTTI_ARC ( select * from T_DMALM_ASM_PROJECT_PROD_ARC); 
insert into DMALM_TEMPLATE_ASS_FUNZIONALE  ( select * from T_DMALM_TEMPLATE_ASS_FUNZIONAL);
insert into DMALM_TEMPLATE_DEMAND          ( select * from T_DMALM_TEMPLATE_DEMAND);
insert into DMALM_TEMPLATE_INT_TECNICA     ( select * from T_DMALM_TEMPLATE_INT_TECNICA);
insert into DMALM_TEMPLATE_SERVIZI         ( select * from T_DMALM_TEMPLATE_SERVIZI);
insert into DMALM_TEMPLATE_SVILUPPO        ( select * from T_DMALM_TEMPLATE_SVILUPPO);
insert into DMALM_TEMPLATE_DOCUMENTI       ( select * from T_DMALM_TEMPLATE_DOCUMENTI);
insert into DMALM_FILIERA_ANOMALIE         ( select * from T_DMALM_FILIERA_ANOMALIE);
insert into DMALM_FILIERA_PRODUTTIVA       ( select * from T_DMALM_FILIERA_PRODUTTIVA);
insert into DMALM_EL_UNITA_ORGANIZZAT_FLAT ( select * from T_DMALM_EL_UNITA_ORGANIZ_FLAT);
insert into DMALM_RICHIESTA_SUPPORTO ( select * from T_DMALM_RICHIESTA_SUPPORTO);