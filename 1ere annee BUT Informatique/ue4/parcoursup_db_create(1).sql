DROP SCHEMA IF EXISTS parcoursup CASCADE;

CREATE SCHEMA parcoursup;
SET SCHEMA 'parcoursup';


CREATE TABLE _academie(
    academie_nom                                                        VARCHAR(50),

    CONSTRAINT academie_pk                                              PRIMARY KEY (academie_nom)
);

CREATE TABLE _session(
    session_annee                                                       Integer,

    CONSTRAINT session_pk                                               PRIMARY KEY (session_annee)
);

CREATE TABLE _filiere(
    filiere_id                                                          Integer,
    filiere_libelle                                                     VARCHAR(50),
    filiere_libelle_tres_abrege                                         VARCHAR(50),
    filiere_libelle_abrege                                              VARCHAR(50),
    filiere_libelle_detaille_bis                                        VARCHAR(50),

    CONSTRAINT filiere_pk                                               PRIMARY KEY (filiere_id)
);

CREATE TABLE _regroupement(
    libelle_regroupement                                                VARCHAR(50),

    CONSTRAINT regroupement_pk                                          PRIMARY KEY (libelle_regroupement)
);

CREATE TABLE _etablissement(
    etablissement_code_uai                                              VARCHAR(50),
    etablissement_nom                                                   VARCHAR(50),
    etablissement_statut                                                VARCHAR(50),
    CONSTRAINT etablissement_pk                                         PRIMARY KEY (etablissement_code_uai)
);

CREATE TABLE _region(
    region_nom                                                          VARCHAR(50),

    CONSTRAINT region_pk                                                PRIMARY KEY (region_nom)
);

CREATE TABLE _departement(
    departement_code                                                    VARCHAR(50),
    departement_nom                                                     VARCHAR(50),
    region_nom                                                          VARCHAR(50) NOT NULL,
 
    CONSTRAINT departement_pk                                           PRIMARY KEY (departement_code),
    CONSTRAINT departement_fk_region                                    FOREIGN KEY (region_nom)                                        REFERENCES _region(region_nom)
);


CREATE TABLE _commune(
commune_nom VARCHAR(50),
departement_code VARCHAR(50),
constraint commune_pk                                                   PRIMARY KEY (commune_nom,departement_code),
constraint commune_fk_departement                                       FOREIGN KEY (departement_code)                                  REFERENCES _departement(departement_code)
);

CREATE TABLE _formation(
    cod_aff_form                                                        VARCHAR(50),
    filiere_libelle_detaille                                            VARCHAR(50),
    coordonnees_gps                                                     VARCHAR(50),
    list_com                                                            VARCHAR(50),
    concours_communs_banque_epreuve                                     VARCHAR(50),
    url_formation                                                       VARCHAR(50),
    tri                                                                 VARCHAR(50),
    academie_nom                                                        VARCHAR(50),
    etablissement_code_uai                                              VARCHAR(50),
    commune_nom                                                         VARCHAR(50) NOT NULL,
    departement_code                                                    VARCHAR(50) NOT NULL,
    filiere_id                                                          Integer     NOT NULL,

    CONSTRAINT formation_pk                                             PRIMARY KEY (cod_aff_form),
    CONSTRAINT formation_fk_academie                                    FOREIGN KEY (academie_nom)                                      REFERENCES _academie(academie_nom),
    CONSTRAINT formation_fk_etablissement                               FOREIGN KEY (academie_nom)                                      REFERENCES _etablissement(etablissement_code_uai),
    constraint formation_fk_commune                                     FOREIGN KEY (commune_nom,departement_code)                      REFERENCES _commune(commune_nom,departement_code),
    constraint formation_fk_filiere                                     FOREIGN KEY (filiere_id)                                        REFERENCES _filiere(filiere_id)
);



CREATE TABLE _rang_dernier_appele_selon_regroupement(
    rang_dernier_appele                                                 Integer,
    libelle_regroupement VARCHAR(50),
    session_annee int,
    cod_aff_form VARCHAR(50),

    CONSTRAINT rang_dernier_appele_selon_regroupement_pk                PRIMARY KEY (libelle_regroupement,cod_aff_form,session_annee),
    constraint rang_dernier_appele_selon_regroupement_fk_regroupement   FOREIGN KEY (libelle_regroupement)                              REFERENCES _regroupement(libelle_regroupement),
    constraint rang_dernier_appele_selon_regroupement_fk_session        FOREIGN KEY (session_annee)                                     REFERENCES _session(session_annee),
    constraint rang_dernier_appele_selon_regroupement_fk_formation      FOREIGN KEY (cod_aff_form)                                      REFERENCES _formation(cod_aff_form)
);



CREATE TABLE _admissions_generalites(
    selectivite                                                         VARCHAR(50),
    capacite                                                            Integer,
    effectif_total_candidats                                            Integer,
    effectif_total_candidates                                           Integer,
    session_annee                                                       Integer,
    cod_aff_form                                                        VARCHAR(50),
    constraint admissions_generalites_pk                                primary key (session_annee,cod_aff_form),
    constraint admissions_generalites_fk_session                        FOREIGN KEY (session_annee)                                     REFERENCES _session(session_annee),
    constraint admissions_generalites_fk_formation                      FOREIGN KEY (cod_aff_form)                                      REFERENCES _formation(cod_aff_form)    
);

CREATE TABLE _type_bac(
    type_bac                                                            VARCHAR(50),

    CONSTRAINT type_bac_pk                                              PRIMARY KEY (type_bac)
);

CREATE TABLE _admissions_selon_type_neo_bac(
    effectif_candidat_neo_bac_classes                                   Integer,
    cod_aff_form                                                        VARCHAR(50),
    type_bac                                                            VARCHAR(50),
    session_annee                                                       Integer,

    CONSTRAINT admissions_selon_type_neo_bac_pk                         PRIMARY KEY (type_bac,cod_aff_form,session_annee),
    constraint admissions_selon_type_neo_bac_fk_type_bac                FOREIGN KEY (type_bac)                                          REFERENCES _type_bac(type_bac),
    constraint admissions_selon_type_neo_bac_fk_formation               FOREIGN KEY (cod_aff_form)                                      REFERENCES _formation(cod_aff_form),
    constraint admissions_selon_type_neo_bac_fk_session                 FOREIGN KEY (session_annee)                                     REFERENCES _session(session_annee)
);

CREATE TABLE _mention_bac(
    libelle_mention                                                     VARCHAR(50),

    CONSTRAINT mention_bac_pk                                           PRIMARY KEY (libelle_mention)
);

CREATE TABLE _effectis_selon_mention(
    affectif_admis_neo_bac_selon_mention                                Integer,
    libelle_mention                                                     VARCHAR(50),
    session_annee                                                       Integer,
    cod_aff_form                                                        VARCHAR(50),
    CONSTRAINT effectis_selon_mention_pk                                PRIMARY KEY (libelle_mention,session_annee,cod_aff_form),
    constraint effectis_selon_mention_fk_mention_bac                    FOREIGN KEY (libelle_mention)                                   REFERENCES _mention_bac(libelle_mention),
    constraint effectis_selon_mention_fk_session                        FOREIGN KEY (session_annee)                                     REFERENCES _session(session_annee),
    constraint effectis_selon_mention_fk_formation                      FOREIGN KEY (cod_aff_form)                                      REFERENCES _formation(cod_aff_form)
);