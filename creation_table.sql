DROP TABLE IF EXISTS emprunt;
DROP TABLE IF EXISTS reserv;
DROP TABLE IF EXISTS exemplaire;
DROP TABLE IF EXISTS livre;
DROP TABLE IF EXISTS etu;

create table etu(
	id_et integer primary key autoincrement not null , -- TODO: A CHANGER AVANT DE RENDRE
	prenom varchar(255) not null,
	nom varchar(255) not null,
	mdp varchar(255) not null,
	email varchar(255) not null unique CHECK(email like '%@%.%')
);

create table livre (
	id_liv int primary key not null,
	auteur varchar(255) not null,
	titre varchar(255) not null
);

create table exemplaire (
	id_ex int primary key not null,
	id_liv int references livre(id_liv) not null
);

create table reserv (
	id_et int references etu(id_et) not null,
	date_res date not null,
	id_liv int references livre(id_liv) not null,
	date_fin_res date not null,
	primary key (id_et,id_liv,date_res)
);

create table emprunt (
	id_et int references etu(id_et) not null,
	id_ex int references exemplaire(id_ex) not null,
	date_emp date default sysdate,
	date_retour date default sysdate, -- + 5,
	primary key (id_et,id_ex,date_emp)
);

INSERT INTO livre VALUES (1,'Charlotte','Foenkinos, David');
INSERT INTO livre VALUES (2,'Le Royaume','Carrère, Emmanuel');
INSERT INTO livre VALUES (3,'Chanson douce','Slimani, Lela');
INSERT INTO livre VALUES (4,'L''amie prodigieuse (Tome 2); - Le nouveau nom','Ferrante, Elena');
INSERT INTO livre VALUES (5,'Article 353 du code pénal','Viel, Tanguy');
INSERT INTO livre VALUES (6,'Les jours de ton absence','Walsh, Rosie');
INSERT INTO livre VALUES (7,'Les Bourgeois','Ferney, Alice');
INSERT INTO livre VALUES (8,'Temps glaciaires','Vargas, Fred');
INSERT INTO livre VALUES (9,'Réparer les vivants','Kerangal, Maylis de');
INSERT INTO livre VALUES (10,'Ce qui reste de nos vies','Shalev, Zeruya');
INSERT INTO livre VALUES (11,'La Veille de presque tout','Del Arbol, Victor');
INSERT INTO livre VALUES (12,'L''ordre du jour','Vuillard, Eric');
INSERT INTO livre VALUES (13,'La sorcière','Läckberg Camilla');
INSERT INTO livre VALUES (14,'Les mille et une gaffes de l''ange gardien Ariel Auvinen','Paasilinna, Arto');
INSERT INTO livre VALUES (15,'Pour que tu ne te perdes pas dans le quartier','Modiano, Patrick');
INSERT INTO livre VALUES (16,'Culottées. Des femmes qui ne font que ce qu''elles veulent (Tome 2);','Bagieu, Pénélope');
INSERT INTO livre VALUES (17,'Millénium 4 - Ce qui ne me tue pas','Lagercrantz, David');
INSERT INTO livre VALUES (18,'Le collier rouge','Rufin, Jean-Christophe');
INSERT INTO livre VALUES (19,'Barbarie 2.0','Japp, Andrea H.');
INSERT INTO livre VALUES (20,'Du sang sur la glace','Nesbo, Jo');
INSERT INTO livre VALUES (21,'Dans la forêt','Hegland, Jean');
INSERT INTO livre VALUES (22,'L''Art de perdre','Zeniter, Alice');
INSERT INTO livre VALUES (23,'Le beau mystère','Penny, Louise');
INSERT INTO livre VALUES (24,'Désorientale','Négar Djavadi');
INSERT INTO livre VALUES (25,'Une famille délicieuse','Marsh, Willa');
INSERT INTO livre VALUES (26,'Ils vont tuer Robert Kennedy','Dugain, Marc');
INSERT INTO livre VALUES (27,'Le Garçon qui ne pleurait plus','Schulman, Ninni');
INSERT INTO livre VALUES (28,'Attends-moi au ciel','Salem, Carlos');
INSERT INTO livre VALUES (29,'La Beauté des jours','Gallay, Claudie');
INSERT INTO livre VALUES (30,'Les disparus du phare','May, Peter');
INSERT INTO livre VALUES (31,'Ne pars pas avant moi','Rouart, Jean-Marie');
INSERT INTO livre VALUES (32,'L''Heure indigo','Harmel, Kristin');
INSERT INTO livre VALUES (33,'Les soeurs ennemies','Kellerman, Jonathan');
INSERT INTO livre VALUES (34,'Fermé pour l''hiver','Horst, Jorn Lier');
INSERT INTO livre VALUES (35,'La terre qui les spare','Matar, Hisham');
INSERT INTO livre VALUES (36,'Dieu, Allah, moi et les autres','Bachi, Salim');
INSERT INTO livre VALUES (37,'Ma mère, cette inconnue','Labro, Philippe');
INSERT INTO livre VALUES (38,'Incident voyageurs','Frioux, Dalibor');
INSERT INTO livre VALUES (39,'Hérétiques','Padura Fuentes, Leonardo');
INSERT INTO livre VALUES (40,'Karpathia','Menegoz, Mathias');
INSERT INTO livre VALUES (41,'Hötel du Grand Cerf','Bartelt, Franz');
INSERT INTO livre VALUES (42,'Des garçons bien élevés','Parsons, Tony');
INSERT INTO livre VALUES (43,'Émilie voit quelqu''un - Après la psy, le beau temps ?','Rouquette, Anne');
INSERT INTO livre VALUES (44,'Le Labyrinthe des esprits','Zafon, Carlos ruiz');
INSERT INTO livre VALUES (45,'L''anniversaire du roi','Trillard, Marc');
INSERT INTO livre VALUES (46,'L''écrivain national','Joncour, Serge');
INSERT INTO livre VALUES (47,'Châtiments','McDermid, Val');
INSERT INTO livre VALUES (48,'Rouen 1203','Aillon, Jean d');
INSERT INTO livre VALUES (49,'Un maniaque dans la ville','Kellerman, Jonathan');
INSERT INTO livre VALUES (50,'Blind Lake','Wilson, Robert Charles');
INSERT INTO livre VALUES (51,'La Maison des Turner','Flournoy, Angela');
INSERT INTO livre VALUES (52,'Sécurité','Wohlsdorf, Gina');
INSERT INTO livre VALUES (53,'À son image','Ferrari, Jérôme');
INSERT INTO livre VALUES (54,'La délicatesse','Bonin, Cyril');
INSERT INTO livre VALUES (55,'Les fantômes du vieux pays','Hill, Nathan');
INSERT INTO livre VALUES (56,'Le Temps des regrets','Higgins Clark, Mary');
INSERT INTO livre VALUES (57,'Dans le jardin de l''ogre','Slimani, Leïla');
INSERT INTO livre VALUES (58,'L''amour et les forêts','Reinhardt Eric');
INSERT INTO livre VALUES (59,'Un secret du docteur Freud','Abécassis, Eliette');
INSERT INTO livre VALUES (60,'La route sombre','Ma, Jian');
INSERT INTO livre VALUES (61,'Sous l''eau','Levy, Deborah');
INSERT INTO livre VALUES (62,'Soleil de nuit','Nesbo, Jo');
INSERT INTO livre VALUES (63,'Un coeur sombre','Ellory, R. J.');
INSERT INTO livre VALUES (64,'Danser dans la poussire','Cook, Thomas H.');
INSERT INTO livre VALUES (65,'Feu follet','Melo, Patricia');
INSERT INTO livre VALUES (66,'Une Jeunesse','Modiano, Patrick');
INSERT INTO livre VALUES (67,'Ça aussi, ça passera','Busquets, Milena');
INSERT INTO livre VALUES (68,'Laëtitia ou la fin des hommes','Jablonka, Ivan');
INSERT INTO livre VALUES (69,'Les infâmes','Miller, Jax');
INSERT INTO livre VALUES (70,'L''ïle Louvre','Chavouet, Florent');
INSERT INTO livre VALUES (71,'Eugenia','Duroy, Lionel');
INSERT INTO livre VALUES (72,'Un moindre mal','Flanagan, Joe');
INSERT INTO livre VALUES (73,'Idaho','Ruskovich, Emily');
INSERT INTO livre VALUES (74,'Boussole','Enard, Mathias');
INSERT INTO livre VALUES (75,'Le prix Nobel','Alexieva, Elena');
INSERT INTO livre VALUES (76,'La poupée de Kafka','Colin, Fabrice');
INSERT INTO livre VALUES (77,'Mr Gwyn','Baricco, Alessandro');
INSERT INTO livre VALUES (78,'Les mécanos de Vénus','Lansdale, Joe R.');
INSERT INTO livre VALUES (79,'Tombée du ciel','Ahern, Cecelia');
INSERT INTO livre VALUES (80,'Trois fois dès l''aube','Baricco, Alessandro');
INSERT INTO livre VALUES (81,'La Vengeance des mères','Ferus, Jim');
INSERT INTO livre VALUES (82,'Indu Boy','Clément, Catherine');
INSERT INTO livre VALUES (83,'Le commis','Malamud, Bernard');
INSERT INTO livre VALUES (84,'Les Chants de la mort','Gonthier, Nicole');
INSERT INTO livre VALUES (85,'Landfall','Urbani, Ellen');
INSERT INTO livre VALUES (86,'La Jeune pouse','Baricco, Alessandro');
INSERT INTO livre VALUES (87,'Les Furies','Groff, Lauren');
INSERT INTO livre VALUES (88,'Un déluge de feu','Ghosh, Amitav');
INSERT INTO livre VALUES (89,'Une illusion d''optique','Penny, Louise');
INSERT INTO livre VALUES (90,'Sidney Chambers et les périls de la nuit','Runcie, James');
INSERT INTO livre VALUES (91,'Farallon Islands','Geni, Abby');
INSERT INTO livre VALUES (92,'Mélodie de Vienne','Lothar, Ernst');
INSERT INTO livre VALUES (93,'Les filles au lion','Burton, Jessie');
INSERT INTO livre VALUES (94,'Cabaret Biarritz','Vales, José C.');
INSERT INTO livre VALUES (95,'La salle de bal','Hope, Anna');
INSERT INTO livre VALUES (96,'L''affaire Collini','Schirach, Ferdinand von');
INSERT INTO livre VALUES (97,'Tarpeia, les venins de Rome','Bouchard, Nicolas');
INSERT INTO livre VALUES (98,'Les Vies multiples d''Amory Clay','Boyd, William');
INSERT INTO livre VALUES (99,'A mains nues','Barbato, Paola');
INSERT INTO livre VALUES (100,'Il était une ville','Reverdy, Thomas B.');

INSERT INTO etu VALUES (0, "Tom", "Dupont", "mdp", "mail1@mail.com");
INSERT INTO etu VALUES (1, "Fabrice", "Dupond", "mdp", "mail2@mail.com");
INSERT INTO etu VALUES (2, "Kel", "Sunny", "mdp", "mail3@mail.com");
INSERT INTO etu VALUES (3, "Aubrey", "Mari", "mdp", "mail4@mail.com");

INSERT INTO exemplaire VALUES (1, 1);
INSERT INTO exemplaire VALUES (2, 2);
INSERT INTO exemplaire VALUES (3, 2);

INSERT INTO emprunt (id_et, id_ex) VALUES (0, 1);
INSERT INTO emprunt (id_et, id_ex) VALUES (0, 2);
INSERT INTO emprunt (id_et, id_ex) VALUES (1, 3);
