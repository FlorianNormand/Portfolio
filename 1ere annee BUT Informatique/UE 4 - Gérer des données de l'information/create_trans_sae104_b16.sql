drop schema if exists transmusicales cascade;

create schema transmusicales;

set schema 'transmusicales';

--
-- Table _groupe_artiste
--

CREATE TABLE _groupe_artiste(
    id_groupe_artiste   char(10) NOT NULL,
    nom_groupe_artiste  varchar NOT NULL,
    site_web            varchar NOT NULL,
    sortie_discographie int NOT NULL,
    debut               int NOT NULL,
    a_pour_origine      varchar NOT NULL,
    CONSTRAINT _groupe_artiste_pk PRIMARY KEY(id_groupe_artiste)
);


--
-- Table _annee
--

CREATE TABLE _annee (
    an      INTEGER NOT NULL,
    CONSTRAINT _annee_pk PRIMARY KEY(an)
);


--
-- Table _pays
--

CREATE TABLE _pays (
    nom_p   varchar NOT NULL, /* Certains pays ont un nom officiel très long */
    CONSTRAINT _pays_pk PRIMARY KEY(nom_p)
);


--
-- Table _ville
--

CREATE TABLE _ville (
    nom_v       varchar NOT NULL, /* Certaines villes ont un nom très long  */
    se_situe    varchar NOT NULL,
    CONSTRAINT _ville_pk PRIMARY KEY(nom_v)
);


--
-- Table _edition
--

CREATE TABLE _edition (
    nom_edition     varchar NOT NULL, /* Le nom des éditions peuvent varier et être long */
    annee_edition   int NOT NULL,
    CONSTRAINT _edition_pk PRIMARY KEY(nom_edition)
);


--
-- Table _formation
--

CREATE TABLE _formation (
    libelle_formation   varchar NOT NULL,
    CONSTRAINT _formation_pk PRIMARY KEY(libelle_formation)
);


--
-- Table _concert
--

CREATE TABLE _concert (
    no_concert      char(10) NOT NULL,
    titre           varchar NOT NULL, 
    resume          varchar NOT NULL,
    duree           time NOT NULL,
    tarif           float NOT NULL,
    est_de          varchar(20) NOT NULL,   
    CONSTRAINT _concert_pk PRIMARY KEY(no_concert)

);

---
-- Table _representation
--

CREATE TABLE _representation (
    numero_representation   char(10) NOT NULL,
    heure                   time NOT NULL, /* fomat heure 00:00 */
    date_representation     date NOT NULL,
    jouee_par               char(10) NOT NULL,
    a_lieu_dans             char(10) NOT NULL,    
    correspond_a            char(10) NOT NULL,        
    CONSTRAINT _representation_pk PRIMARY KEY (numero_representation)

);


--
-- Table _lieu
--- 

CREATE TABLE _lieu (
    id_lieu         char(10) NOT NULL,
    nom_lieu        varchar NOT NULL,
    accesPMR        boolean NOT NULL,
    capacite_max    int NOT NULL,
    type_lieu       varchar(20) NOT NULL,
    dans            varchar NOT NULL,
    CONSTRAINT _lieu_pk PRIMARY KEY (id_lieu)

);


--
-- Table _type_musique
--

CREATE TABLE _type_musique (
    type_m      varchar(20) NOT NULL,
    CONSTRAINT _type_musique_pk PRIMARY KEY (type_m)
);

--------------------------------------------
--    On initialise les mutliplicités    ---
--------------------------------------------

--
-- a pour >
--
create table _a_pour (
    id_groupe_artiste   char(10) NOT NULL,
    libelle_formation   varchar NOT NULL,
    CONSTRAINT _a_pour_pk PRIMARY KEY (id_groupe_artiste, libelle_formation),
    CONSTRAINT _a_pour_fk_1 FOREIGN KEY (id_groupe_artiste)
    REFERENCES _groupe_artiste(id_groupe_artiste),
    CONSTRAINT _a_pour_fk_2 FOREIGN KEY (libelle_formation)
    REFERENCES _formation(libelle_formation)
) ;


--
-- se deroule
--

create table _se_deroule (
    nom_edition     varchar NOT NULL,
    no_concert      char(10) NOT NULL,
    CONSTRAINT _se_deroule_pk PRIMARY KEY (nom_edition, no_concert),
    CONSTRAINT _se_deroule_fk_1 FOREIGN KEY (nom_edition)
    REFERENCES _edition(nom_edition),
    CONSTRAINT _se_deroule_fk_2 FOREIGN KEY (no_concert)
    REFERENCES _concert(no_concert)
);


--
-- type principal
--

create table _type_principal (
    id_groupe_artiste   char(10) NOT NULL,
    type_m              varchar(20) NOT NULL,
    CONSTRAINT _type_principal_pk PRIMARY KEY (id_groupe_artiste, type_m),
    CONSTRAINT _type_principal_fk_1 FOREIGN KEY (id_groupe_artiste)
        REFERENCES _groupe_artiste(id_groupe_artiste),
    CONSTRAINT _type_principal_fk_2 FOREIGN KEY (type_m)
        REFERENCES _type_musique(type_m)
);

--
-- type principal
--

create table _type_ponctuel (
    id_groupe_artiste   char(10) NOT NULL,
    type_m              varchar(20) NOT NULL,
    CONSTRAINT _type_ponctuel_pk PRIMARY KEY (id_groupe_artiste, type_m),
    CONSTRAINT _type_ponctuel_fk_1 FOREIGN KEY (id_groupe_artiste)
        REFERENCES _groupe_artiste(id_groupe_artiste),
    CONSTRAINT _type_ponctuel_fk_2 FOREIGN KEY (type_m)
        REFERENCES _type_musique(type_m)
);


--
-- Clé étrangère
--
ALTER TABLE _groupe_artiste
    ADD CONSTRAINT _groupe_artiste_fk_annee1 FOREIGN KEY (sortie_discographie)
    REFERENCES _annee(an);


ALTER TABLE _groupe_artiste
    ADD CONSTRAINT _groupe_artiste_fk_annee2 FOREIGN KEY (debut)
    REFERENCES _annee(an);

ALTER TABLE _groupe_artiste
    ADD CONSTRAINT _groupe_artiste_fk_pays FOREIGN KEY (a_pour_origine)
    REFERENCES _pays(nom_p);

ALTER TABLE _ville
    ADD CONSTRAINT _ville_fk_pays FOREIGN KEY (se_situe)
    REFERENCES _pays(nom_p);

ALTER TABLE _edition
    ADD CONSTRAINT _edition_fk_annee FOREIGN KEY (annee_edition)
    REFERENCES _annee(an);

ALTER TABLE _concert
    ADD CONSTRAINT _concert_fk_type_musique FOREIGN KEY (est_de)
    REFERENCES _type_musique(type_m);

ALTER TABLE _representation
    ADD CONSTRAINT _representation_fk_groupe_artiste FOREIGN KEY (jouee_par)
    REFERENCES _groupe_artiste(id_groupe_artiste);

ALTER TABLE _representation
    ADD CONSTRAINT _representation_fk_lieu FOREIGN KEY (a_lieu_dans)
    REFERENCES _lieu(id_lieu);

ALTER TABLE _representation
    ADD CONSTRAINT _representation_fk_concert FOREIGN KEY (correspond_a)
    REFERENCES _concert(no_concert);

ALTER TABLE _lieu
    ADD CONSTRAINT _lieu_fk_ville FOREIGN KEY (dans)
    REFERENCES _ville(nom_v);



