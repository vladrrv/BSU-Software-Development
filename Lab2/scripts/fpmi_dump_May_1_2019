-- -------------------------------------------------------------
-- TablePlus 2.3(222)
--
-- https://tableplus.com/
--
-- Database: course_catalogue_db
-- Generation Time: 2019-05-01 13:52:43.7960
-- -------------------------------------------------------------


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


CREATE TABLE `course_offerings` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `professor_id` bigint(20) unsigned DEFAULT NULL,
  `course_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `professorId` (`professor_id`),
  KEY `courseId` (`course_id`),
  CONSTRAINT `course_offerings_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `professors` (`id`),
  CONSTRAINT `course_offerings_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `courses` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `price` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `grades` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `course_offering_id` bigint(20) unsigned DEFAULT NULL,
  `student_id` bigint(20) unsigned DEFAULT NULL,
  `grade` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `courseOfferingId` (`course_offering_id`),
  KEY `studentId` (`student_id`),
  CONSTRAINT `grades_ibfk_1` FOREIGN KEY (`course_offering_id`) REFERENCES `course_offerings` (`id`),
  CONSTRAINT `grades_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `logins` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `type` enum('professor','student') NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `professors` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `login_id` bigint(20) unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `loginId` (`login_id`),
  CONSTRAINT `professors_ibfk_1` FOREIGN KEY (`login_id`) REFERENCES `logins` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `students` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `login_id` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `loginId` (`login_id`),
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`login_id`) REFERENCES `logins` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `course_offerings` (`id`, `professor_id`, `course_id`) VALUES ('10', '1', '103');

INSERT INTO `courses` (`id`, `description`, `price`) VALUES ('100', 'HTML course for beginners', '20'),
('101', 'CSS course for beginners', '20'),
('102', 'Advanced calculus', '40'),
('103', 'Programming Technologies', '0');

INSERT INTO `grades` (`id`, `course_offering_id`, `student_id`, `grade`) VALUES ('1000', '10', '1', '10'),
('1001', '10', '2', '10'),
('1002', '10', '3', '10');

INSERT INTO `logins` (`id`, `email`, `type`, `password`) VALUES ('1', 'drobushevich@bsu.by', X'70726f666573736f72', '123123'),
('2', 'kalugin@bsu.by', X'73747564656e74', '123123'),
('3', 'reentovich@bsu.by', X'73747564656e74', '123123'),
('4', 'polochanina', X'73747564656e74', '123123');

INSERT INTO `professors` (`id`, `login_id`, `name`) VALUES ('1', '1', 'Drobushevich Lubov Fedorovna');

INSERT INTO `students` (`id`, `login_id`, `name`) VALUES ('1', '2', 'Pavel Kalugin'),
('2', '3', 'Vladislav Reentovich'),
('3', '4', 'Eugenia Polochanina');




/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;