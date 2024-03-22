INSERT INTO chantier (nom, adresse)
VALUES ("appartement", "rue ronsard"),
       ("maison", "impasse des hurlevents");

INSERT INTO tache (nom, temps)
VALUES ("tache 1", 4),
       ("tache 2", 2);

INSERT INTO role (designation)
VALUES ("Admin"),
       ("Chef de chantier"),
       ("Ouvrier"),
       ("Client");

INSERT INTO user (pseudo, password, role_id)
VALUES ("fadime", "$2y$10$.gxs1ctKPMPVTZErKZwfO.3latkzvB1bgJ78.hPqAjWog99/etEVG", 2),
       ("toto", "$2y$10$nA6nJ/UKjmt1e9ONpwuC0.gMEF07gdljUQOhOaNs8xi3R4GLOFcza", 3),
       ("titi", "$2y$10$5xVqNSNYmjDxerWXzp5FUOEDrY4nsZiyaWdZ4vtOHJ2mtzQnzSTUq", 4);

INSERT INTO operation (nom, date, chantier_id, tache_id, user_id)
VALUES ("operation 1", "2024-04-12", 1, 1, 1),
       ("operation 2", "2024-06-23", 2, 1, 2);


INSERT INTO consommable (nom)
VALUES ("bois"),
       ("ciment");



