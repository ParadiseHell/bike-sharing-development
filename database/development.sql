DROP TABLE IF EXISTS `development`;
CREATE TABLE `development` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	
  `bike_id` int(10) unsigned NOT NULL,
  `city` varchar(10) NOT NULL,
  `delivery_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),

	UNIQUE KEY (`bike_id`,`city`,`delivery_at`),

	FOREIGN KEY (`bike_id`) REFERENCES `bike`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
