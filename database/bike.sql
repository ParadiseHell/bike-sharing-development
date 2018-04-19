DROP TABLE IF EXISTS `bike`;
CREATE TABLE `bike` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  `name` varchar(20) NOT NULL,
  `founded_at` TIMESTAMP NULL DEFAULT NULL,
	
  PRIMARY KEY (`id`),
	
	UNIQUE KEY(`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;