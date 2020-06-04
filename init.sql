/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for Task Management Application
CREATE DATABASE IF NOT EXISTS `task-management` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `task-management`;

-- Dumping structure for table task-management.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`id`			bigint(20) 		NOT NULL AUTO_INCREMENT,
	`username` 		varchar(100) 	NOT NULL UNIQUE,
    `email`			varchar(100)	NOT NULL UNIQUE,
	`password` 		varchar(100) 	NOT NULL,
	`full_name`		varchar(100) 	NOT NULL,
	`avatar`		varchar(200) 	DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Dumping data for table lunglinh.category: ~1 row (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`username`, `email`, `password`, `full_name`, `avatar`) VALUES
	('admin', 'admin@gmail.com', '$2a$12$W46dM0Hi73mgICg3L1kzXO4Vu5i8AYAfb1xEJ7qh5ifsvYkb6.AaG', 'Minh Duc', null);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table task-management.project
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
    `project_name`			varchar(50)		NOT NULL,
    `project_identifier`	varchar(5)		NOT NULL UNIQUE,
    `description`			varchar(500)	NOT NULL,
    `start_date`			datetime		DEFAULT NULL,
    `end_date`				datetime		DEFAULT NULL,
    `created_at`			datetime		NOT NULL,
    `updated_at`			datetime		NOT NULL,
    `user_id`				bigint(20)		NOT NULL,
	PRIMARY KEY (`project_identifier`),
    CONSTRAINT `FK_USER` FOREIGN KEY (`user_id`) 
	REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- Dumping structure for table task-management.backlog
DROP TABLE IF EXISTS `backlog`;
CREATE TABLE `backlog` (
	`id`					bigint(20) 		NOT NULL AUTO_INCREMENT,
    `project_identifier`	varchar(5)		NOT NULL UNIQUE,
	CONSTRAINT `FK_PROJECT` FOREIGN KEY (`project_identifier`)
    REFERENCES `project` (`project_identifier`),
	PRIMARY KEY (`id`, `project_identifier`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Dumping structure for table task-management.task_count
DROP TABLE IF EXISTS `task_count`;
CREATE TABLE `task_count` (
	`id`					bigint(20)		NOT NULL AUTO_INCREMENT,
	`backlog_id`			bigint(20)		NOT NULL UNIQUE,
    `total`					bigint(10)		NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_BACKLOG2` FOREIGN KEY (`backlog_id`)
    REFERENCES `backlog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Dumping structure for table task-management.task
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
	`id`					bigint(20) 		NOT NULL AUTO_INCREMENT,
    `backlog_id`			bigint(20)		NOT NULL,
    `sequence`				varchar(50)		NOT NULL UNIQUE,
    `summary`				varchar(200)	NOT NULL,
    `acceptance_criteria`	varchar(200)	DEFAULT NULL,
    `status`				enum('TODO', 'DOING', 'DONE') NOT NULL,
    `priority`				enum('LOW', 'MEDIUM', 'HIGH') NOT NULL,
    `due_date`				date			DEFAULT NULL,
    `created_at`			datetime		NOT NULL,
    `updated_at`			datetime		NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_BACKLOG` FOREIGN KEY (`backlog_id`)
    REFERENCES `backlog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

