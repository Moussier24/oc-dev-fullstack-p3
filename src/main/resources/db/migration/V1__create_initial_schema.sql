-- Nous n'exécutons pas réellement ces commandes, mais nous les gardons pour référence
-- CREATE TABLE `USERS` (...);
-- CREATE TABLE `RENTALS` (...);
-- CREATE TABLE `MESSAGES` (...);
-- CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);
-- ALTER TABLE `RENTALS` ADD FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`);
-- ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
-- ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`);
-- Au lieu de cela, nous allons simplement insérer un commentaire
SELECT 'Initial schema creation' AS 'Migration Info';