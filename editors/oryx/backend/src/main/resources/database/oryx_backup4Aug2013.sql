CREATE DATABASE  IF NOT EXISTS `oryx` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `oryx`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: oryx
-- ------------------------------------------------------
-- Server version	5.5.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary table structure for view `access_`
--

DROP TABLE IF EXISTS `access_`;
/*!50001 DROP VIEW IF EXISTS `access_`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `access_` (
  `context_id` mediumint(9),
  `context_name` text,
  `subject_id` mediumint(9),
  `subject_name` text,
  `object_id` mediumint(9),
  `object_name` text,
  `access_id` mediumint(9),
  `access_scheme` text,
  `access_term` text
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `comment_`
--

DROP TABLE IF EXISTS `comment_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) NOT NULL,
  `title` text,
  `content` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `content_`
--

DROP TABLE IF EXISTS `content_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content_` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `erdf` text NOT NULL,
  `svg` text,
  `png_large` blob,
  `png_small` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `friend_`
--

DROP TABLE IF EXISTS `friend_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend_` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  `model_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `subject_id` (`subject_id`,`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `identity_`
--

DROP TABLE IF EXISTS `identity_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `identity_` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `uri` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interaction_`
--

DROP TABLE IF EXISTS `interaction_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interaction_` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `subject` text NOT NULL,
  `subject_descend` tinyint(1) NOT NULL DEFAULT '0',
  `object` text NOT NULL,
  `object_self` tinyint(1) NOT NULL DEFAULT '1',
  `object_descend` tinyint(1) NOT NULL DEFAULT '0',
  `object_restrict_to_parent` tinyint(1) NOT NULL DEFAULT '0',
  `scheme` text NOT NULL,
  `term` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `model_rating_`
--

DROP TABLE IF EXISTS `model_rating_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model_rating_` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) NOT NULL,
  `object_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plugin_`
--

DROP TABLE IF EXISTS `plugin_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plugin_` (
  `rel` text NOT NULL,
  `title` text NOT NULL,
  `description` text NOT NULL,
  `java_class` text NOT NULL,
  `is_export` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `representation_`
--

DROP TABLE IF EXISTS `representation_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `representation_` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `ident_id` int(11) NOT NULL,
  `mime_type` text NOT NULL,
  `language` varchar(255) DEFAULT 'en_US',
  `title` varchar(255) DEFAULT '',
  `summary` varchar(255) DEFAULT '',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `type` varchar(255) DEFAULT 'undefined',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schema_info_`
--

DROP TABLE IF EXISTS `schema_info_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_info_` (
  `version` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `setting_`
--

DROP TABLE IF EXISTS `setting_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting_` (
  `subject_id` int(11) DEFAULT NULL,
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `key_` text NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `structure_`
--

DROP TABLE IF EXISTS `structure_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `structure_` (
  `hierarchy` text NOT NULL,
  `ident_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subject_`
--

DROP TABLE IF EXISTS `subject_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject_` (
  `ident_id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `nickname` text,
  `email` text,
  `fullname` text,
  `dob` date DEFAULT NULL,
  `gender` text,
  `postcode` text,
  `first_login` date NOT NULL,
  `last_login` date NOT NULL,
  `login_count` int(11) NOT NULL DEFAULT '0',
  `language_code` text,
  `country_code` text,
  `password` text,
  `visibility` text,
  PRIMARY KEY (`ident_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_definition_`
--

DROP TABLE IF EXISTS `tag_definition_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_definition_` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) NOT NULL,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_relation_`
--

DROP TABLE IF EXISTS `tag_relation_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_relation_` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `tag_id` int(11) NOT NULL,
  `object_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'oryx'
--
/*!50003 DROP FUNCTION IF EXISTS `identity_` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 FUNCTION `identity_`(openid text) RETURNS int(11)
BEGIN
		declare result INT;
		select identity_.id into result from identity_ where uri = openid;
		IF result = 0
		THEN
			BEGIN
				insert into identity_(uri) values(openid);
				SELECT LAST_INSERT_ID() into result;
			END;
		END IF;
		return result;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ensure_descendant` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `ensure_descendant`(IN root_hierarchy text, IN root_hierarchy_next_child_position text, IN target INT)
BEGIN		DECLARE lastId INT;
		DECLARE resultCount INT;
        select count(*) into resultCount from structure_ where hierarchy like CONCAT(root_hierarchy,'%') and ident_id = target;
				IF resultCount=0		THEN 			BEGIN				insert into structure_(hierarchy, ident_id) values(root_hierarchy_next_child_position, target);
				select structure_.*  from structure_ where hierarchy = root_hierarchy_next_child_position and ident_id = target;
			END;
		ELSE			BEGIN				select structure_.*  from structure_ where hierarchy like CONCAT(root_hierarchy,'%') and ident_id = target;
			END;
					END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `identity_` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `identity_`(IN openid text)
BEGIN		DECLARE lastId INT;
		DECLARE resultCount INT;
		select count(*) into resultCount from identity_ where uri = openid;
		IF resultCount=0		THEN 			BEGIN				insert into identity_(uri) values(openid);
				SELECT LAST_INSERT_ID() into lastId;
				select identity_.*  from identity_ where id = lastId;
			END;
		ELSE			BEGIN				select identity_.*  from identity_ where uri = openid;
			END;
					END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `next_child_position` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `next_child_position`(IN hierarchy text, IN encode_position INT, IN parent text)
BEGIN        select CONCAT(hierarchy,encode_position) from structure_ where hierarchy = parent;
		END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `access_`
--

/*!50001 DROP TABLE IF EXISTS `access_`*/;
/*!50001 DROP VIEW IF EXISTS `access_`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `access_` AS select `context_name`.`id` AS `context_id`,`context_name`.`uri` AS `context_name`,`subject_name`.`id` AS `subject_id`,`subject_name`.`uri` AS `subject_name`,`object_name`.`id` AS `object_id`,`object_name`.`uri` AS `object_name`,`access`.`id` AS `access_id`,`access`.`scheme` AS `access_scheme`,`access`.`term` AS `access_term` from ((((((`interaction_` `access` join `structure_` `context`) join `identity_` `context_name`) join `structure_` `subject_axis`) join `identity_` `subject_name`) join `structure_` `object_axis`) join `identity_` `object_name`) where ((`access`.`subject` = `context`.`hierarchy`) and (`context`.`ident_id` = `context_name`.`id`) and ((`access`.`subject` = `subject_axis`.`hierarchy`) or `access`.`subject_descend`) and (((not(`access`.`object_restrict_to_parent`)) and `access`.`object_self` and (`access`.`object` = `object_axis`.`hierarchy`)) or ((not(`access`.`object_restrict_to_parent`)) and `access`.`object_descend`) or (`access`.`object_restrict_to_parent` and `access`.`object_self` and (`object_axis`.`hierarchy` = `subject_axis`.`hierarchy`)) or (`access`.`object_restrict_to_parent` and `access`.`object_descend`)) and (`subject_axis`.`ident_id` = `subject_name`.`id`) and (`object_axis`.`ident_id` = `object_name`.`id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-08-04 16:16:54
