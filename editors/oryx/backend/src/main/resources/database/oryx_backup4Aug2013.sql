CREATE DATABASE  IF NOT EXISTS `conxportal610` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `conxportal610`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: conxportal610
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
-- Table structure for table `account_`
--

DROP TABLE IF EXISTS `account_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_` (
  `accountId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `parentAccountId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `legalName` varchar(75) DEFAULT NULL,
  `legalId` varchar(75) DEFAULT NULL,
  `legalType` varchar(75) DEFAULT NULL,
  `sicCode` varchar(75) DEFAULT NULL,
  `tickerSymbol` varchar(75) DEFAULT NULL,
  `industry` varchar(75) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `size_` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_`
--

LOCK TABLES `account_` WRITE;
/*!40000 ALTER TABLE `account_` DISABLE KEYS */;
INSERT INTO `account_` VALUES (10156,10154,0,'','2012-08-14 02:47:54','2012-08-14 02:47:54',0,'liferay.com','','','','','','','','');
/*!40000 ALTER TABLE `account_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `addressId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `street1` varchar(75) DEFAULT NULL,
  `street2` varchar(75) DEFAULT NULL,
  `street3` varchar(75) DEFAULT NULL,
  `city` varchar(75) DEFAULT NULL,
  `zip` varchar(75) DEFAULT NULL,
  `regionId` bigint(20) DEFAULT NULL,
  `countryId` bigint(20) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `mailing` tinyint(4) DEFAULT NULL,
  `primary_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`addressId`),
  KEY `IX_93D5AD4E` (`companyId`),
  KEY `IX_ABD7DAC0` (`companyId`,`classNameId`),
  KEY `IX_71CB1123` (`companyId`,`classNameId`,`classPK`),
  KEY `IX_923BD178` (`companyId`,`classNameId`,`classPK`,`mailing`),
  KEY `IX_9226DBB4` (`companyId`,`classNameId`,`classPK`,`primary_`),
  KEY `IX_5BC8B0D4` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcementsdelivery`
--

DROP TABLE IF EXISTS `announcementsdelivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcementsdelivery` (
  `deliveryId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `email` tinyint(4) DEFAULT NULL,
  `sms` tinyint(4) DEFAULT NULL,
  `website` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`deliveryId`),
  UNIQUE KEY `IX_BA4413D5` (`userId`,`type_`),
  KEY `IX_6EDB9600` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcementsdelivery`
--

LOCK TABLES `announcementsdelivery` WRITE;
/*!40000 ALTER TABLE `announcementsdelivery` DISABLE KEYS */;
INSERT INTO `announcementsdelivery` VALUES (16568,10154,16562,'general',0,0,0),(16569,10154,16562,'news',0,0,0),(16570,10154,16562,'test',0,0,0);
/*!40000 ALTER TABLE `announcementsdelivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcementsentry`
--

DROP TABLE IF EXISTS `announcementsentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcementsentry` (
  `uuid_` varchar(75) DEFAULT NULL,
  `entryId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `title` varchar(75) DEFAULT NULL,
  `content` longtext,
  `url` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `displayDate` datetime DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `alert` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`entryId`),
  KEY `IX_A6EF0B81` (`classNameId`,`classPK`),
  KEY `IX_14F06A6B` (`classNameId`,`classPK`,`alert`),
  KEY `IX_D49C2E66` (`userId`),
  KEY `IX_1AFBDE08` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcementsentry`
--

LOCK TABLES `announcementsentry` WRITE;
/*!40000 ALTER TABLE `announcementsentry` DISABLE KEYS */;
/*!40000 ALTER TABLE `announcementsentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcementsflag`
--

DROP TABLE IF EXISTS `announcementsflag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcementsflag` (
  `flagId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `entryId` bigint(20) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  PRIMARY KEY (`flagId`),
  UNIQUE KEY `IX_4539A99C` (`userId`,`entryId`,`value`),
  KEY `IX_9C7EB9F` (`entryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcementsflag`
--

LOCK TABLES `announcementsflag` WRITE;
/*!40000 ALTER TABLE `announcementsflag` DISABLE KEYS */;
/*!40000 ALTER TABLE `announcementsflag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetcategory`
--

DROP TABLE IF EXISTS `assetcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assetcategory` (
  `uuid_` varchar(75) DEFAULT NULL,
  `categoryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `parentCategoryId` bigint(20) DEFAULT NULL,
  `leftCategoryId` bigint(20) DEFAULT NULL,
  `rightCategoryId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `title` longtext,
  `description` longtext,
  `vocabularyId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `IX_BE4DF2BF` (`parentCategoryId`,`name`,`vocabularyId`),
  UNIQUE KEY `IX_E8D019AA` (`uuid_`,`groupId`),
  KEY `IX_E639E2F6` (`groupId`),
  KEY `IX_510B46AC` (`groupId`,`parentCategoryId`,`name`),
  KEY `IX_2008FACB` (`groupId`,`vocabularyId`),
  KEY `IX_D61ABE08` (`name`,`vocabularyId`),
  KEY `IX_7BB1826B` (`parentCategoryId`),
  KEY `IX_9DDD15EA` (`parentCategoryId`,`name`),
  KEY `IX_B185E980` (`parentCategoryId`,`vocabularyId`),
  KEY `IX_4D37BB00` (`uuid_`),
  KEY `IX_287B1F89` (`vocabularyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assetcategory`
--

LOCK TABLES `assetcategory` WRITE;
/*!40000 ALTER TABLE `assetcategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetcategoryproperty`
--

DROP TABLE IF EXISTS `assetcategoryproperty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assetcategoryproperty` (
  `categoryPropertyId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `key_` varchar(75) DEFAULT NULL,
  `value` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`categoryPropertyId`),
  UNIQUE KEY `IX_DBD111AA` (`categoryId`,`key_`),
  KEY `IX_99DA856` (`categoryId`),
  KEY `IX_8654719F` (`companyId`),
  KEY `IX_52340033` (`companyId`,`key_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assetcategoryproperty`
--

LOCK TABLES `assetcategoryproperty` WRITE;
/*!40000 ALTER TABLE `assetcategoryproperty` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetcategoryproperty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetentries_assetcategories`
--

DROP TABLE IF EXISTS `assetentries_assetcategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assetentries_assetcategories` (
  `entryId` bigint(20) NOT NULL,
  `categoryId` bigint(20) NOT NULL,
  PRIMARY KEY (`entryId`,`categoryId`),
  KEY `IX_A188F560` (`categoryId`),
  KEY `IX_E119938A` (`entryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assetentries_assetcategories`
--

LOCK TABLES `assetentries_assetcategories` WRITE;
/*!40000 ALTER TABLE `assetentries_assetcategories` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetentries_assetcategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetentries_assettags`
--

DROP TABLE IF EXISTS `assetentries_assettags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assetentries_assettags` (
  `entryId` bigint(20) NOT NULL,
  `tagId` bigint(20) NOT NULL,
  PRIMARY KEY (`entryId`,`tagId`),
  KEY `IX_2ED82CAD` (`entryId`),
  KEY `IX_B2A61B55` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assetentries_assettags`
--

LOCK TABLES `assetentries_assettags` WRITE;
/*!40000 ALTER TABLE `assetentries_assettags` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetentries_assettags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetentry`
--

DROP TABLE IF EXISTS `assetentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assetentry` (
  `entryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `classUuid` varchar(75) DEFAULT NULL,
  `classTypeId` bigint(20) DEFAULT NULL,
  `visible` tinyint(4) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `publishDate` datetime DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `mimeType` varchar(75) DEFAULT NULL,
  `title` longtext,
  `description` longtext,
  `summary` longtext,
  `url` longtext,
  `layoutUuid` varchar(75) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `priority` double DEFAULT NULL,
  `viewCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`entryId`),
  UNIQUE KEY `IX_1E9D371D` (`classNameId`,`classPK`),
  KEY `IX_FC1F9C7B` (`classUuid`),
  KEY `IX_7306C60` (`companyId`),
  KEY `IX_75D42FF9` (`expirationDate`),
  KEY `IX_1EBA6821` (`groupId`,`classUuid`),
  KEY `IX_2E4E3885` (`publishDate`),
  KEY `IX_9029E15A` (`visible`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assetentry`
--

LOCK TABLES `assetentry` WRITE;
/*!40000 ALTER TABLE `assetentry` DISABLE KEYS */;
INSERT INTO `assetentry` VALUES (10178,10172,10154,10158,'','2012-08-14 02:47:55','2012-08-14 02:47:55',10117,10176,'db48b56b-09ec-49fc-9b4b-c130cbe6a234',0,0,NULL,NULL,NULL,NULL,'text/html','10175','','','','',0,0,0,0),(10201,10192,10154,10196,'Test Test','2012-08-14 02:47:55','2012-08-14 02:47:55',10005,10196,'cc2dc218-ec17-4109-8c08-ebedf3cdc0f7',0,0,NULL,NULL,NULL,NULL,'','Test Test','','','','',0,0,0,0),(10317,10310,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10117,10314,'f78527a8-ec82-4916-89bf-0a9d9f636a49',0,0,NULL,NULL,NULL,NULL,'text/html','10313','','','','',0,0,0,0),(10326,10320,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10117,10324,'6aa6a7fe-b92f-44fc-8c28-e69ee3e15c6d',0,0,NULL,NULL,NULL,NULL,'text/html','10323','','','','',0,0,0,0),(10335,10329,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10117,10333,'7ab697de-2ad9-433a-b205-b80f31067cb1',0,0,NULL,NULL,NULL,NULL,'text/html','10332','','','','',0,0,0,0),(10349,10338,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10117,10347,'3a6f959d-4cfc-4fee-88e8-96aea94dd2a7',0,0,NULL,NULL,NULL,NULL,'text/html','10346','','','','',0,0,0,0),(10355,10338,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10117,10353,'1430ffc9-b18e-4de6-af84-41e753eeafc3',0,0,NULL,NULL,NULL,NULL,'text/html','10352','','','','',0,0,0,0),(10361,10338,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10117,10359,'3c142b2d-4aad-423c-ab69-ac2911c77d45',0,0,NULL,NULL,NULL,NULL,'text/html','10358','','','','',0,0,0,0),(10375,10364,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10117,10373,'d8f911e1-849f-47db-8328-84320e0a5cc8',0,0,NULL,NULL,NULL,NULL,'text/html','10372','','','','',0,0,0,0),(10383,10364,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10117,10381,'e028a04d-db81-4322-936b-2c5633a507c5',0,0,NULL,NULL,NULL,NULL,'text/html','10380','','','','',0,0,0,0),(10389,10364,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10117,10387,'db9f7369-4036-4cd3-8e83-1be5b1ca2120',0,0,NULL,NULL,NULL,NULL,'text/html','10386','','','','',0,0,0,0),(10395,10364,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10117,10393,'b0f86b58-09a9-4ab8-9141-7c868d2d9c1d',0,0,NULL,NULL,NULL,NULL,'text/html','10392','','','','',0,0,0,0),(10413,10180,10154,10158,'','2012-08-14 02:48:00','2012-08-14 02:48:00',10010,10411,'3ed17b37-3380-4d30-a5fc-22a20625a76f',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_3.jpg','','','','',0,0,0,0),(10416,10180,10154,10158,'','2012-08-14 02:48:00','2012-08-14 02:48:00',10117,10414,'5da12679-de02-4591-a54e-44399eea4c9e',0,0,NULL,NULL,NULL,NULL,'text/html','10411','','','','',0,0,0,0),(10421,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10419,'ebd9c0bf-a7c3-4638-997f-062cdb9575b0',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_11.jpg','','','','',0,0,0,0),(10425,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10117,10422,'83ff0b6f-dbbe-4702-a972-69961ce1aff5',0,0,NULL,NULL,NULL,NULL,'text/html','10419','','','','',0,0,0,0),(10430,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10428,'8b76a308-57ed-442e-999e-df1a78f1045f',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_12.jpg','','','','',0,0,0,0),(10433,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10117,10431,'6fb446c2-1132-40de-9316-6d35f228e10b',0,0,NULL,NULL,NULL,NULL,'text/html','10428','','','','',0,0,0,0),(10438,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10436,'47a2c229-e287-476a-8f5e-618ac56e857f',0,1,NULL,NULL,NULL,NULL,'image/png','welcome_bg_10.png','','','','',0,0,0,0),(10441,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10117,10439,'3fe85993-e185-4c20-8abd-3a7a6677ae7e',0,0,NULL,NULL,NULL,NULL,'text/html','10436','','','','',0,0,0,0),(10449,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10447,'85baf84c-5151-4d30-9cda-a1346fbdbf4d',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_2.jpg','','','','',0,0,0,0),(10452,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10117,10450,'0a0b3176-e37a-473a-abcb-821521414e5d',0,0,NULL,NULL,NULL,NULL,'text/html','10447','','','','',0,0,0,0),(10457,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10455,'ce45d760-a26a-452c-976f-c2b0022b7fc4',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_9.jpg','','','','',0,0,0,3),(10461,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10117,10459,'d3064ed3-3865-431f-800b-4ac1dd506c74',0,0,NULL,NULL,NULL,NULL,'text/html','10455','','','','',0,0,0,0),(10469,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10467,'c1cd182d-ba30-4961-ae6b-3a2daae746e3',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_4.jpg','','','','',0,0,0,0),(10472,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10117,10470,'d375f1c2-5a81-4b9b-afe0-aacaa3e8cffd',0,0,NULL,NULL,NULL,NULL,'text/html','10467','','','','',0,0,0,0),(10481,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10479,'039e457b-ce55-406a-901f-7c1eb59d38ca',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_6.jpg','','','','',0,0,0,0),(10484,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10117,10482,'364c3905-95f6-4f03-92ed-ffcc52ff3771',0,0,NULL,NULL,NULL,NULL,'text/html','10479','','','','',0,0,0,0),(10493,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10489,'0cc635cd-6b1a-4c4a-9c85-52f56c93a0fb',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_7.jpg','','','','',0,0,0,0),(10496,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10117,10494,'2475b941-8b98-4515-a8b6-0aca1969e82a',0,0,NULL,NULL,NULL,NULL,'text/html','10489','','','','',0,0,0,0),(10502,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10010,10499,'33e84fce-e4be-4e50-87e6-b209f01a77a3',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_5.jpg','','','','',0,0,0,0),(10505,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10117,10503,'63d3f7f2-5eb5-4cef-9def-e3a304127e3e',0,0,NULL,NULL,NULL,NULL,'text/html','10499','','','','',0,0,0,0),(10513,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10010,10511,'c3edd8cf-f02c-48c2-b0ee-2a0d5988a1b1',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_13.jpg','','','','',0,0,0,0),(10516,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10117,10514,'1263e3a3-f8b3-4f72-9a7c-5b59e0dd4b56',0,0,NULL,NULL,NULL,NULL,'text/html','10511','','','','',0,0,0,0),(10525,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10010,10523,'60087992-6d2a-4f75-985d-c5e859173b21',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_1.jpg','','','','',0,0,0,1),(10528,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10117,10526,'7ba59ca5-722b-4969-95c8-381e3d2673f2',0,0,NULL,NULL,NULL,NULL,'text/html','10523','','','','',0,0,0,0),(10537,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10010,10535,'9da09bba-5fd1-4f85-bb5e-3665b5317aa2',0,1,NULL,NULL,NULL,NULL,'image/jpeg','welcome_bg_8.jpg','','','','',0,0,0,4),(10540,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10117,10538,'9ad03b7f-5caf-463e-a9a3-406bcdeb644d',0,0,NULL,NULL,NULL,NULL,'text/html','10535','','','','',0,0,0,0),(10554,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10108,10548,'10aae8f4-1fe7-450e-afce-ed1fd0c52b49',0,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Who Is Using Liferay</Title></root>','','','','',0,0,0,4),(10557,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10117,10555,'f72048c5-754f-49e4-8a02-33a7d2f0ae4a',0,0,NULL,NULL,NULL,NULL,'text/html','10548','','','','',0,0,0,0),(10576,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10108,10574,'52665fc1-edd4-434a-aa86-d18f49883bb3',10566,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Liferay Benefits</Title></root>','','','','',0,0,0,2),(10579,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10117,10577,'3a119992-7292-4ee5-a881-cbb7811b4ca7',0,0,NULL,NULL,NULL,NULL,'text/html','10574','','','','',0,0,0,0),(10588,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10108,10586,'aea644dd-7ead-4eae-9f66-ce5751f54f6b',10566,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">What We Do</Title></root>','','','','',0,0,0,4),(10591,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10117,10589,'563baed0-ef55-4867-ad84-6c37219398ff',0,0,NULL,NULL,NULL,NULL,'text/html','10586','','','','',0,0,0,0),(10600,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10117,10598,'6a6a32f4-84f5-43f9-aa67-facf77699ba5',0,0,NULL,NULL,NULL,NULL,'text/html','10597','','','','',0,0,0,0),(10606,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10117,10604,'d6035795-ec9e-48f8-971f-8fdb344e2859',0,0,NULL,NULL,NULL,NULL,'text/html','10603','','','','',0,0,0,0),(10612,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10117,10610,'0e4ddbc0-38d0-4e22-841d-3b8b9274e15b',0,0,NULL,NULL,NULL,NULL,'text/html','10609','','','','',0,0,0,0),(10622,10198,10154,10196,'Test Test','2012-08-14 02:48:51','2012-08-14 02:48:51',10117,10620,'bb330ec6-7091-4888-a3df-8e32edbc1008',0,0,NULL,NULL,NULL,NULL,'text/html','10619','','','','',0,0,0,0),(10627,10198,10154,10196,'Test Test','2012-08-14 02:48:51','2012-08-14 02:48:51',10117,10625,'29724955-220f-4cca-8ef8-df80da877925',0,0,NULL,NULL,NULL,NULL,'text/html','10624','','','','',0,0,0,0),(10638,10180,10154,10196,'Test Test','2012-08-14 02:50:26','2012-08-14 02:50:26',10117,10636,'bdb5239c-413c-4611-ba7b-e539f18e7f72',0,0,NULL,NULL,NULL,NULL,'text/html','10635','','','','',0,0,0,0),(11103,10180,10154,10196,'Test Test','2012-08-17 16:18:07','2012-08-17 16:18:07',10010,11101,'c69a9473-6d94-456b-9721-d1aa6d9e4822',0,1,NULL,NULL,NULL,NULL,'application/pdf','Bill Of Laden','Bill Of Laden','','','',0,0,0,0),(11106,10180,10154,10196,'Test Test','2012-08-17 16:18:07','2012-08-17 16:18:07',10117,11104,'bee2f5df-0d13-49d2-960d-d8e0babbff0d',0,0,NULL,NULL,NULL,NULL,'text/html','11101','','','','',0,0,0,0),(11111,10180,10154,10196,'Test Test','2012-08-17 16:18:08','2012-08-17 16:18:08',10010,11109,'9efd576f-7f79-4394-841e-2f863e8a27d4',0,1,NULL,NULL,NULL,NULL,'image/jpeg','PO','PO','','','',0,0,0,0),(11114,10180,10154,10196,'Test Test','2012-08-17 16:18:08','2012-08-17 16:18:08',10117,11112,'9ca680b8-2562-4f44-a751-79abd041806a',0,0,NULL,NULL,NULL,NULL,'text/html','11109','','','','',0,0,0,0),(16350,10180,10154,10196,'Test Test','2012-08-30 17:34:28','2012-08-30 17:34:28',10117,16348,'9354f51c-3aa4-4df2-a3b5-c4a3e8bdef87',0,0,NULL,NULL,NULL,NULL,'text/html','16347','','','','',0,0,0,0),(16567,10192,10154,10196,'Test Test','2012-08-30 19:24:10','2012-08-30 19:24:33',10005,16562,'286af049-fcd1-432b-89ad-23891a136400',0,0,NULL,NULL,NULL,NULL,'','ConX User','','','','',0,0,0,0),(16574,16564,10154,16562,'ConX User','2012-08-30 19:24:59','2012-08-30 19:24:59',10117,16572,'d0d07113-9d56-4a7b-8638-2a7af1ed2dd3',0,0,NULL,NULL,NULL,NULL,'text/html','16571','','','','',0,0,0,0),(16579,16564,10154,16562,'ConX User','2012-08-30 19:24:59','2012-08-30 19:24:59',10117,16577,'0cddc2d9-69f6-42d2-9fd5-09ee8f48299f',0,0,NULL,NULL,NULL,NULL,'text/html','16576','','','','',0,0,0,0),(32072,32062,10154,10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30',10010,32070,'1b9dbc42-d2c9-4f72-ae8e-84329ebcf4cb',0,1,NULL,NULL,NULL,NULL,'image/png','pen.png','','','','',0,0,0,0),(32075,32062,10154,10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30',10117,32073,'90e7400f-3237-4bbb-ac50-00e99a9c0e5b',0,0,NULL,NULL,NULL,NULL,'text/html','32070','','','','',0,0,0,0),(32084,32062,10154,10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30',10010,32078,'74353928-596b-4746-8582-db08f631e235',0,1,NULL,NULL,NULL,NULL,'image/jpeg','carousel_item3.jpg','','','','',0,0,0,0),(32087,32062,10154,10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30',10117,32085,'be382441-506b-4761-8252-a2bccfce4653',0,0,NULL,NULL,NULL,NULL,'text/html','32078','','','','',0,0,0,0),(32093,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32090,'0800d3a2-be09-43ac-8a57-5fc3b2a7ad64',0,1,NULL,NULL,NULL,NULL,'image/png','people.png','','','','',0,0,0,0),(32096,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10117,32094,'7965a8fb-9565-464e-ab78-7de850dc328a',0,0,NULL,NULL,NULL,NULL,'text/html','32090','','','','',0,0,0,0),(32105,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32102,'263e2d7c-e799-4e62-a115-96e77a29181a',0,1,NULL,NULL,NULL,NULL,'image/jpeg','carousel_item1.jpg','','','','',0,0,0,0),(32108,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10117,32106,'4e123f9a-5193-44f8-b49b-dc14ba0c5954',0,0,NULL,NULL,NULL,NULL,'text/html','32102','','','','',0,0,0,0),(32116,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32114,'7015eb92-9b12-4dae-9f8a-a46fd7930afc',0,1,NULL,NULL,NULL,NULL,'image/png','facebook.png','','','','',0,0,0,0),(32119,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10117,32117,'c488f683-7800-433d-b95b-2d76de0a616e',0,0,NULL,NULL,NULL,NULL,'text/html','32114','','','','',0,0,0,0),(32128,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32126,'4535eaaf-0509-414c-a62c-a0252cd232d3',0,1,NULL,NULL,NULL,NULL,'image/png','icon_gears.png','','','','',0,0,0,0),(32131,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10117,32129,'083e31e6-d2f0-49bf-8f6e-02a61c02d69c',0,0,NULL,NULL,NULL,NULL,'text/html','32126','','','','',0,0,0,0),(32140,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32138,'0b857adc-5e8e-4054-aaf4-d68c7db99693',0,1,NULL,NULL,NULL,NULL,'image/png','mouse.png','','','','',0,0,0,0),(32143,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10117,32141,'a7587a3b-999d-44b1-9fdb-bbde50469307',0,0,NULL,NULL,NULL,NULL,'text/html','32138','','','','',0,0,0,0),(32152,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32150,'ee00f58b-ca06-415e-9ae5-bc9d17cfc2bf',0,1,NULL,NULL,NULL,NULL,'image/png','icon_phone.png','','','','',0,0,0,0),(32155,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10117,32153,'e17ff9f9-03cb-4a7b-acd4-e12f22a889d1',0,0,NULL,NULL,NULL,NULL,'text/html','32150','','','','',0,0,0,0),(32164,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32162,'56c6fa37-63bb-49d0-bf10-34ad3c82dd90',0,1,NULL,NULL,NULL,NULL,'image/jpeg','carousel_item2.jpg','','','','',0,0,0,0),(32167,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10117,32165,'cd02cc6d-808e-4832-971f-91b654b9b155',0,0,NULL,NULL,NULL,NULL,'text/html','32162','','','','',0,0,0,0),(32176,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32174,'570a24b1-1d06-4863-b794-a87d17a7a98b',0,1,NULL,NULL,NULL,NULL,'image/png','twitter.png','','','','',0,0,0,0),(32179,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10117,32177,'9cfb6e6d-098b-4edc-929e-82b48100b8d3',0,0,NULL,NULL,NULL,NULL,'text/html','32174','','','','',0,0,0,0),(32188,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32186,'9d97e944-6800-41e9-920a-3e0690b54287',0,1,NULL,NULL,NULL,NULL,'image/png','paperpen.png','','','','',0,0,0,0),(32191,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10117,32189,'252a952e-a3c6-453d-bdaa-24938ecb0f30',0,0,NULL,NULL,NULL,NULL,'text/html','32186','','','','',0,0,0,0),(32200,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32198,'a77d22f5-d564-4998-ad29-b8d7b9434405',0,1,NULL,NULL,NULL,NULL,'image/png','network.png','','','','',0,0,0,0),(32203,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10117,32201,'22d37f7d-3210-4a52-806b-f7e618b86eba',0,0,NULL,NULL,NULL,NULL,'text/html','32198','','','','',0,0,0,0),(32212,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32210,'74bf915f-b101-459a-8cf5-aea8806567dc',0,1,NULL,NULL,NULL,NULL,'image/png','rss.png','','','','',0,0,0,0),(32215,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10117,32213,'e2bf363b-4ece-40ac-93d4-0c37a3b24db2',0,0,NULL,NULL,NULL,NULL,'text/html','32210','','','','',0,0,0,0),(32224,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32222,'a37e2884-e689-4bb6-a3ac-d1f8137df566',0,1,NULL,NULL,NULL,NULL,'image/png','icon_beaker.png','','','','',0,0,0,0),(32227,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10117,32225,'18f352f0-3c0f-4bf5-9714-d267a7a1277f',0,0,NULL,NULL,NULL,NULL,'text/html','32222','','','','',0,0,0,0),(32236,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32234,'291de86e-8d38-4db7-b2b8-8493820b684c',0,1,NULL,NULL,NULL,NULL,'image/png','linkedin.png','','','','',0,0,0,0),(32239,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10117,32237,'90900c01-f708-4913-9a9d-a563f5e4f0ae',0,0,NULL,NULL,NULL,NULL,'text/html','32234','','','','',0,0,0,0),(32248,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32246,'f7ea45e0-fc11-4ca2-9529-bb90f1486b71',0,1,NULL,NULL,NULL,NULL,'image/png','icon_network.png','','','','',0,0,0,0),(32251,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10117,32249,'1f60473a-48a1-485d-a5f8-13289d6f6dda',0,0,NULL,NULL,NULL,NULL,'text/html','32246','','','','',0,0,0,0),(32265,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10108,32263,'4e6dd3a2-938d-4374-b829-37d0bcd6359c',0,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Footer Blurb</Title></root>','','','','',0,0,0,0),(32268,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10117,32266,'2a43fba2-247d-41f8-bfa4-dd3caccf7e2a',0,0,NULL,NULL,NULL,NULL,'text/html','32263','','','','',0,0,0,0),(32274,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32272,'90ca3c7e-0eb4-4fa7-836a-5f9faedea365',0,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Main Content</Title></root>','','','','',0,0,0,0),(32277,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10117,32275,'1065f7ec-9f7c-4606-9b34-0859630dfc39',0,0,NULL,NULL,NULL,NULL,'text/html','32272','','','','',0,0,0,0),(32282,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32280,'26443cd4-7d41-43d8-8cf7-14b7b966e4ea',0,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Why Seven Cogs</Title></root>','','','','',0,0,0,0),(32285,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10117,32283,'38ffe805-76d0-4807-af0a-db82cf9adf6b',0,0,NULL,NULL,NULL,NULL,'text/html','32280','','','','',0,0,0,0),(32295,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32293,'8bea3cc7-16bb-413e-a6ef-7833eb91754e',32288,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">What Our Clients Are Saying</Title></root>','','','','',0,0,0,0),(32298,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10117,32296,'584d15fe-ca86-44a1-84ba-76862c7a9c6c',0,0,NULL,NULL,NULL,NULL,'text/html','32293','','','','',0,0,0,0),(32308,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32306,'294ef961-bc4a-4977-9142-ce60ed59bd42',32301,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Footer Links</Title></root>','','','','',0,0,0,0),(32311,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10117,32309,'e20a9ef8-fd87-49be-8712-c53cc6863bb6',0,0,NULL,NULL,NULL,NULL,'text/html','32306','','','','',0,0,0,0),(32321,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32319,'1f2214a7-6460-4a74-9073-a004dafa7014',32314,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Our Goal</Title></root>','','','','',0,0,0,0),(32324,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10117,32322,'f13faf82-1183-406c-9265-7211a75ae4e0',0,0,NULL,NULL,NULL,NULL,'text/html','32319','','','','',0,0,0,0),(32329,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32327,'84fcfce9-7e6f-4a73-ac0d-df304c47d6f5',32314,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">The Experience</Title></root>','','','','',0,0,0,0),(32332,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10117,32330,'4b8c47aa-765f-4b52-9612-3d7bc635eea6',0,0,NULL,NULL,NULL,NULL,'text/html','32327','','','','',0,0,0,0),(32337,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32335,'f799172b-6d29-4a70-a6d3-f2d178f994ac',32314,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Exceptional Support</Title></root>','','','','',0,0,0,0),(32340,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10117,32338,'5d32dad8-b618-4455-afad-0913c335fe26',0,0,NULL,NULL,NULL,NULL,'text/html','32335','','','','',0,0,0,0),(32350,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32348,'126e5c28-441a-4757-98ae-79870c0a217a',32343,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Main Carousel</Title></root>','','','','',0,0,0,0),(32353,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10117,32351,'327fd929-c74a-4a62-904f-5266fffc861d',0,0,NULL,NULL,NULL,NULL,'text/html','32348','','','','',0,0,0,0),(32363,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10108,32361,'62e1d461-585d-483d-b1a1-d50f6ec4d2ca',32356,1,NULL,NULL,'2010-02-01 00:00:00',NULL,'text/html','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Footer Social</Title></root>','','','','',0,0,0,0),(32366,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10117,32364,'91ca03bb-16a2-46a0-b058-abb9ed1a977f',0,0,NULL,NULL,NULL,NULL,'text/html','32361','','','','',0,0,0,0),(32371,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10117,32369,'4393dec5-fa28-427c-aa1d-e5396f2f788f',0,0,NULL,NULL,NULL,NULL,'text/html','32368','','','','',0,0,0,0),(32381,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10117,32379,'c9f93382-d3eb-4ed6-98ef-6f0c56775027',0,0,NULL,NULL,NULL,NULL,'text/html','32378','','','','',0,0,0,0),(32386,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10117,32384,'5bc77444-839e-4153-82bf-700507e7a4bb',0,0,NULL,NULL,NULL,NULL,'text/html','32383','','','','',0,0,0,0),(32391,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10117,32389,'3aa4e0af-570f-4cca-9af1-45b971909137',0,0,NULL,NULL,NULL,NULL,'text/html','32388','','','','',0,0,0,0),(32396,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10117,32394,'1109453b-1356-40eb-8806-deca44253e71',0,0,NULL,NULL,NULL,NULL,'text/html','32393','','','','',0,0,0,0),(32403,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10117,32401,'0ddf65ac-1c28-42c9-bed1-8296384aa82f',0,0,NULL,NULL,NULL,NULL,'text/html','32400','','','','',0,0,0,0),(35204,10180,10154,10196,'Test Test','2012-11-26 07:16:37','2012-11-26 07:16:37',10117,35202,'84b0a794-854d-4962-a1d4-fd4bdfd654f8',0,0,NULL,NULL,NULL,NULL,'text/html','35201','','','','',0,0,0,0),(35813,10180,10154,10196,'Test Test','2012-11-30 12:54:03','2012-12-01 02:46:37',10010,35811,'944f09e3-feb4-4d4f-9813-e046e8654e58',0,1,NULL,NULL,NULL,NULL,'application/pdf','Bill Of Laden','Bill Of Laden','','','',0,0,0,0),(35816,10180,10154,10196,'Test Test','2012-11-30 12:54:03','2012-11-30 12:54:03',10117,35814,'16f00f8d-f6e6-4f4b-98a2-b6b6725228fd',0,0,NULL,NULL,NULL,NULL,'text/html','35811','','','','',0,0,0,0),(35822,10180,10154,10196,'Test Test','2012-11-30 12:54:04','2012-12-01 02:46:37',10010,35819,'29d27da3-eef1-4d57-9c51-559a67e5bdd5',0,1,NULL,NULL,NULL,NULL,'image/jpeg','PO','PO','','','',0,0,0,0),(35825,10180,10154,10196,'Test Test','2012-11-30 12:54:04','2012-11-30 12:54:04',10117,35823,'7fe17d30-f32a-42ad-8c7c-6da33a0cdbcd',0,0,NULL,NULL,NULL,NULL,'text/html','35819','','','','',0,0,0,0),(35835,10180,10154,10196,'Test Test','2012-11-30 12:54:04','2012-12-01 02:46:38',10010,35833,'a94d6c7d-ca5d-445b-90eb-20141f27a6d1',0,1,NULL,NULL,NULL,NULL,'application/pdf','Bill Of Laden','Bill Of Laden','','','',0,0,0,0),(35839,10180,10154,10196,'Test Test','2012-11-30 12:54:04','2012-11-30 12:54:04',10117,35836,'3c7464d1-4a0c-499a-95cb-f67c87b65c06',0,0,NULL,NULL,NULL,NULL,'text/html','35833','','','','',0,0,0,0),(35848,10180,10154,10196,'Test Test','2012-11-30 12:54:05','2012-12-01 02:46:38',10010,35845,'e23171c1-8553-46bd-b2e0-78acf185c43d',0,1,NULL,NULL,NULL,NULL,'image/jpeg','PO','PO','','','',0,0,0,0),(35851,10180,10154,10196,'Test Test','2012-11-30 12:54:05','2012-11-30 12:54:05',10117,35849,'e2fd7f9f-982f-47f2-a382-6dbc00db681b',0,0,NULL,NULL,NULL,NULL,'text/html','35845','','','','',0,0,0,0),(36385,10180,10154,10196,'Test Test','2012-12-01 05:26:51','2012-12-01 05:26:51',10010,36383,'8f864110-fa5f-4f1d-b9a0-f027119f9139',0,1,NULL,NULL,NULL,NULL,'application/pdf','Label RDFLT000001-01L1_1','Label RDFLT000001-01L1_1','','','',0,0,0,9),(36388,10180,10154,10196,'Test Test','2012-12-01 05:26:51','2012-12-01 05:26:51',10117,36386,'bf12e44e-fc43-411d-b7db-898fe2f05562',0,0,NULL,NULL,NULL,NULL,'text/html','36383','','','','',0,0,0,0);
/*!40000 ALTER TABLE `assetentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetlink`
--

DROP TABLE IF EXISTS `assetlink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assetlink` (
  `linkId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `entryId1` bigint(20) DEFAULT NULL,
  `entryId2` bigint(20) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`linkId`),
  UNIQUE KEY `IX_8F542794` (`entryId1`,`entryId2`,`type_`),
  KEY `IX_128516C8` (`entryId1`),
  KEY `IX_56E0AB21` (`entryId1`,`entryId2`),
  KEY `IX_14D5A20D` (`entryId1`,`type_`),
  KEY `IX_12851A89` (`entryId2`),
  KEY `IX_91F132C` (`entryId2`,`type_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assetlink`
--

LOCK TABLES `assetlink` WRITE;
/*!40000 ALTER TABLE `assetlink` DISABLE KEYS */;
/*!40000 ALTER TABLE `assetlink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assettag`
--

DROP TABLE IF EXISTS `assettag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assettag` (
  `tagId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `assetCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`tagId`),
  KEY `IX_7C9E46BA` (`groupId`),
  KEY `IX_D63322F9` (`groupId`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assettag`
--

LOCK TABLES `assettag` WRITE;
/*!40000 ALTER TABLE `assettag` DISABLE KEYS */;
/*!40000 ALTER TABLE `assettag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assettagproperty`
--

DROP TABLE IF EXISTS `assettagproperty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assettagproperty` (
  `tagPropertyId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `tagId` bigint(20) DEFAULT NULL,
  `key_` varchar(75) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tagPropertyId`),
  UNIQUE KEY `IX_2C944354` (`tagId`,`key_`),
  KEY `IX_DFF1F063` (`companyId`),
  KEY `IX_13805BF7` (`companyId`,`key_`),
  KEY `IX_3269E180` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assettagproperty`
--

LOCK TABLES `assettagproperty` WRITE;
/*!40000 ALTER TABLE `assettagproperty` DISABLE KEYS */;
/*!40000 ALTER TABLE `assettagproperty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assettagstats`
--

DROP TABLE IF EXISTS `assettagstats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assettagstats` (
  `tagStatsId` bigint(20) NOT NULL,
  `tagId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `assetCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`tagStatsId`),
  UNIQUE KEY `IX_56682CC4` (`tagId`,`classNameId`),
  KEY `IX_50702693` (`classNameId`),
  KEY `IX_9464CA` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assettagstats`
--

LOCK TABLES `assettagstats` WRITE;
/*!40000 ALTER TABLE `assettagstats` DISABLE KEYS */;
/*!40000 ALTER TABLE `assettagstats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assetvocabulary`
--

DROP TABLE IF EXISTS `assetvocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assetvocabulary` (
  `uuid_` varchar(75) DEFAULT NULL,
  `vocabularyId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `title` longtext,
  `description` longtext,
  `settings_` longtext,
  PRIMARY KEY (`vocabularyId`),
  UNIQUE KEY `IX_C0AAD74D` (`groupId`,`name`),
  UNIQUE KEY `IX_1B2B8792` (`uuid_`,`groupId`),
  KEY `IX_B22D908C` (`companyId`),
  KEY `IX_B6B8CA0E` (`groupId`),
  KEY `IX_55F58818` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assetvocabulary`
--

LOCK TABLES `assetvocabulary` WRITE;
/*!40000 ALTER TABLE `assetvocabulary` DISABLE KEYS */;
INSERT INTO `assetvocabulary` VALUES ('5e3d561c-f050-41fa-a792-42cf3370e5a1',10316,10192,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58','Topic','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Topic</Title></root>','',''),('bcb4b481-82a6-40b2-ba44-e19e66907445',10733,10180,10154,10158,'','2012-08-15 11:33:58','2012-08-15 11:33:58','Topic','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Topic</Title></root>','','');
/*!40000 ALTER TABLE `assetvocabulary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blogsentry`
--

DROP TABLE IF EXISTS `blogsentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blogsentry` (
  `uuid_` varchar(75) DEFAULT NULL,
  `entryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `title` varchar(150) DEFAULT NULL,
  `urlTitle` varchar(150) DEFAULT NULL,
  `description` varchar(75) DEFAULT NULL,
  `content` longtext,
  `displayDate` datetime DEFAULT NULL,
  `allowPingbacks` tinyint(4) DEFAULT NULL,
  `allowTrackbacks` tinyint(4) DEFAULT NULL,
  `trackbacks` longtext,
  `smallImage` tinyint(4) DEFAULT NULL,
  `smallImageId` bigint(20) DEFAULT NULL,
  `smallImageURL` longtext,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`entryId`),
  UNIQUE KEY `IX_DB780A20` (`groupId`,`urlTitle`),
  UNIQUE KEY `IX_1B1040FD` (`uuid_`,`groupId`),
  KEY `IX_72EF6041` (`companyId`),
  KEY `IX_430D791F` (`companyId`,`displayDate`),
  KEY `IX_BB0C2905` (`companyId`,`displayDate`,`status`),
  KEY `IX_EB2DCE27` (`companyId`,`status`),
  KEY `IX_8CACE77B` (`companyId`,`userId`),
  KEY `IX_A5F57B61` (`companyId`,`userId`,`status`),
  KEY `IX_2672F77F` (`displayDate`,`status`),
  KEY `IX_81A50303` (`groupId`),
  KEY `IX_621E19D` (`groupId`,`displayDate`),
  KEY `IX_F0E73383` (`groupId`,`displayDate`,`status`),
  KEY `IX_1EFD8EE9` (`groupId`,`status`),
  KEY `IX_FBDE0AA3` (`groupId`,`userId`,`displayDate`),
  KEY `IX_DA04F689` (`groupId`,`userId`,`displayDate`,`status`),
  KEY `IX_49E15A23` (`groupId`,`userId`,`status`),
  KEY `IX_69157A4D` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blogsentry`
--

LOCK TABLES `blogsentry` WRITE;
/*!40000 ALTER TABLE `blogsentry` DISABLE KEYS */;
/*!40000 ALTER TABLE `blogsentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blogsstatsuser`
--

DROP TABLE IF EXISTS `blogsstatsuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blogsstatsuser` (
  `statsUserId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `entryCount` int(11) DEFAULT NULL,
  `lastPostDate` datetime DEFAULT NULL,
  `ratingsTotalEntries` int(11) DEFAULT NULL,
  `ratingsTotalScore` double DEFAULT NULL,
  `ratingsAverageScore` double DEFAULT NULL,
  PRIMARY KEY (`statsUserId`),
  UNIQUE KEY `IX_82254C25` (`groupId`,`userId`),
  KEY `IX_90CDA39A` (`companyId`,`entryCount`),
  KEY `IX_43840EEB` (`groupId`),
  KEY `IX_28C78D5C` (`groupId`,`entryCount`),
  KEY `IX_BB51F1D9` (`userId`),
  KEY `IX_507BA031` (`userId`,`lastPostDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blogsstatsuser`
--

LOCK TABLES `blogsstatsuser` WRITE;
/*!40000 ALTER TABLE `blogsstatsuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `blogsstatsuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookmarksentry`
--

DROP TABLE IF EXISTS `bookmarksentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookmarksentry` (
  `uuid_` varchar(75) DEFAULT NULL,
  `entryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `resourceBlockId` bigint(20) DEFAULT NULL,
  `folderId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` longtext,
  `description` longtext,
  `visits` int(11) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`entryId`),
  UNIQUE KEY `IX_EAA02A91` (`uuid_`,`groupId`),
  KEY `IX_E52FF7EF` (`groupId`),
  KEY `IX_5200100C` (`groupId`,`folderId`),
  KEY `IX_E2E9F129` (`groupId`,`userId`),
  KEY `IX_E848278F` (`resourceBlockId`),
  KEY `IX_B670BA39` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmarksentry`
--

LOCK TABLES `bookmarksentry` WRITE;
/*!40000 ALTER TABLE `bookmarksentry` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookmarksentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookmarksfolder`
--

DROP TABLE IF EXISTS `bookmarksfolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookmarksfolder` (
  `uuid_` varchar(75) DEFAULT NULL,
  `folderId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `resourceBlockId` bigint(20) DEFAULT NULL,
  `parentFolderId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`folderId`),
  UNIQUE KEY `IX_DC2F8927` (`uuid_`,`groupId`),
  KEY `IX_2ABA25D7` (`companyId`),
  KEY `IX_7F703619` (`groupId`),
  KEY `IX_967799C0` (`groupId`,`parentFolderId`),
  KEY `IX_28A49BB9` (`resourceBlockId`),
  KEY `IX_451E7AE3` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmarksfolder`
--

LOCK TABLES `bookmarksfolder` WRITE;
/*!40000 ALTER TABLE `bookmarksfolder` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookmarksfolder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `browsertracker`
--

DROP TABLE IF EXISTS `browsertracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `browsertracker` (
  `browserTrackerId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `browserKey` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`browserTrackerId`),
  UNIQUE KEY `IX_E7B95510` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `browsertracker`
--

LOCK TABLES `browsertracker` WRITE;
/*!40000 ALTER TABLE `browsertracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `browsertracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calevent`
--

DROP TABLE IF EXISTS `calevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calevent` (
  `uuid_` varchar(75) DEFAULT NULL,
  `eventId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `title` varchar(75) DEFAULT NULL,
  `description` longtext,
  `location` longtext,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `durationHour` int(11) DEFAULT NULL,
  `durationMinute` int(11) DEFAULT NULL,
  `allDay` tinyint(4) DEFAULT NULL,
  `timeZoneSensitive` tinyint(4) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `repeating` tinyint(4) DEFAULT NULL,
  `recurrence` longtext,
  `remindBy` int(11) DEFAULT NULL,
  `firstReminder` int(11) DEFAULT NULL,
  `secondReminder` int(11) DEFAULT NULL,
  PRIMARY KEY (`eventId`),
  UNIQUE KEY `IX_5CCE79C8` (`uuid_`,`groupId`),
  KEY `IX_D6FD9496` (`companyId`),
  KEY `IX_12EE4898` (`groupId`),
  KEY `IX_4FDDD2BF` (`groupId`,`repeating`),
  KEY `IX_FCD7C63D` (`groupId`,`type_`),
  KEY `IX_FD93CBFA` (`groupId`,`type_`,`repeating`),
  KEY `IX_F6006202` (`remindBy`),
  KEY `IX_C1AD2122` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calevent`
--

LOCK TABLES `calevent` WRITE;
/*!40000 ALTER TABLE `calevent` DISABLE KEYS */;
/*!40000 ALTER TABLE `calevent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classname_`
--

DROP TABLE IF EXISTS `classname_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `classname_` (
  `classNameId` bigint(20) NOT NULL,
  `value` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`classNameId`),
  UNIQUE KEY `IX_B27A301F` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classname_`
--

LOCK TABLES `classname_` WRITE;
/*!40000 ALTER TABLE `classname_` DISABLE KEYS */;
INSERT INTO `classname_` VALUES (10014,'com.liferay.counter.model.Counter'),(10405,'com.liferay.marketplace.model.App'),(10406,'com.liferay.marketplace.model.Module'),(10015,'com.liferay.portal.kernel.workflow.WorkflowTask'),(10016,'com.liferay.portal.model.Account'),(10017,'com.liferay.portal.model.Address'),(10018,'com.liferay.portal.model.BrowserTracker'),(10019,'com.liferay.portal.model.ClassName'),(10020,'com.liferay.portal.model.ClusterGroup'),(10021,'com.liferay.portal.model.Company'),(10022,'com.liferay.portal.model.Contact'),(10023,'com.liferay.portal.model.Country'),(10024,'com.liferay.portal.model.EmailAddress'),(10001,'com.liferay.portal.model.Group'),(10025,'com.liferay.portal.model.Image'),(10002,'com.liferay.portal.model.Layout'),(10026,'com.liferay.portal.model.LayoutBranch'),(10027,'com.liferay.portal.model.LayoutPrototype'),(10028,'com.liferay.portal.model.LayoutRevision'),(10029,'com.liferay.portal.model.LayoutSet'),(10030,'com.liferay.portal.model.LayoutSetBranch'),(10031,'com.liferay.portal.model.LayoutSetPrototype'),(10032,'com.liferay.portal.model.ListType'),(10033,'com.liferay.portal.model.Lock'),(10034,'com.liferay.portal.model.MembershipRequest'),(10003,'com.liferay.portal.model.Organization'),(10035,'com.liferay.portal.model.OrgGroupPermission'),(10036,'com.liferay.portal.model.OrgGroupRole'),(10037,'com.liferay.portal.model.OrgLabor'),(10038,'com.liferay.portal.model.PasswordPolicy'),(10039,'com.liferay.portal.model.PasswordPolicyRel'),(10040,'com.liferay.portal.model.PasswordTracker'),(10041,'com.liferay.portal.model.Permission'),(10042,'com.liferay.portal.model.Phone'),(10043,'com.liferay.portal.model.PluginSetting'),(10044,'com.liferay.portal.model.PortalPreferences'),(10045,'com.liferay.portal.model.Portlet'),(10046,'com.liferay.portal.model.PortletItem'),(10047,'com.liferay.portal.model.PortletPreferences'),(10048,'com.liferay.portal.model.Region'),(10049,'com.liferay.portal.model.Release'),(10050,'com.liferay.portal.model.Repository'),(10051,'com.liferay.portal.model.RepositoryEntry'),(10052,'com.liferay.portal.model.Resource'),(10053,'com.liferay.portal.model.ResourceAction'),(10054,'com.liferay.portal.model.ResourceBlock'),(10055,'com.liferay.portal.model.ResourceBlockPermission'),(10056,'com.liferay.portal.model.ResourceCode'),(10057,'com.liferay.portal.model.ResourcePermission'),(10058,'com.liferay.portal.model.ResourceTypePermission'),(10004,'com.liferay.portal.model.Role'),(10059,'com.liferay.portal.model.ServiceComponent'),(10060,'com.liferay.portal.model.Shard'),(10061,'com.liferay.portal.model.Subscription'),(10062,'com.liferay.portal.model.Team'),(10063,'com.liferay.portal.model.Ticket'),(10005,'com.liferay.portal.model.User'),(10006,'com.liferay.portal.model.UserGroup'),(10064,'com.liferay.portal.model.UserGroupGroupRole'),(10065,'com.liferay.portal.model.UserGroupRole'),(10066,'com.liferay.portal.model.UserIdMapper'),(10067,'com.liferay.portal.model.UserNotificationEvent'),(10188,'com.liferay.portal.model.UserPersonalSite'),(10068,'com.liferay.portal.model.UserTracker'),(10069,'com.liferay.portal.model.UserTrackerPath'),(10070,'com.liferay.portal.model.VirtualHost'),(10071,'com.liferay.portal.model.WebDAVProps'),(10072,'com.liferay.portal.model.Website'),(10073,'com.liferay.portal.model.WorkflowDefinitionLink'),(10074,'com.liferay.portal.model.WorkflowInstanceLink'),(10410,'com.liferay.portal.repository.liferayrepository.LiferayRepository'),(10075,'com.liferay.portlet.announcements.model.AnnouncementsDelivery'),(10076,'com.liferay.portlet.announcements.model.AnnouncementsEntry'),(10077,'com.liferay.portlet.announcements.model.AnnouncementsFlag'),(10078,'com.liferay.portlet.asset.model.AssetCategory'),(10079,'com.liferay.portlet.asset.model.AssetCategoryProperty'),(10080,'com.liferay.portlet.asset.model.AssetEntry'),(10081,'com.liferay.portlet.asset.model.AssetLink'),(10082,'com.liferay.portlet.asset.model.AssetTag'),(10083,'com.liferay.portlet.asset.model.AssetTagProperty'),(10084,'com.liferay.portlet.asset.model.AssetTagStats'),(10085,'com.liferay.portlet.asset.model.AssetVocabulary'),(10007,'com.liferay.portlet.blogs.model.BlogsEntry'),(10086,'com.liferay.portlet.blogs.model.BlogsStatsUser'),(10008,'com.liferay.portlet.bookmarks.model.BookmarksEntry'),(10087,'com.liferay.portlet.bookmarks.model.BookmarksFolder'),(10009,'com.liferay.portlet.calendar.model.CalEvent'),(10088,'com.liferay.portlet.documentlibrary.model.DLContent'),(10010,'com.liferay.portlet.documentlibrary.model.DLFileEntry'),(10089,'com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata'),(10090,'com.liferay.portlet.documentlibrary.model.DLFileEntryType'),(10091,'com.liferay.portlet.documentlibrary.model.DLFileRank'),(10092,'com.liferay.portlet.documentlibrary.model.DLFileShortcut'),(10093,'com.liferay.portlet.documentlibrary.model.DLFileVersion'),(10094,'com.liferay.portlet.documentlibrary.model.DLFolder'),(10095,'com.liferay.portlet.documentlibrary.model.DLSync'),(10096,'com.liferay.portlet.dynamicdatalists.model.DDLRecord'),(10097,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet'),(10098,'com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion'),(10099,'com.liferay.portlet.dynamicdatamapping.model.DDMContent'),(10100,'com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink'),(10101,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure'),(10102,'com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink'),(10103,'com.liferay.portlet.dynamicdatamapping.model.DDMTemplate'),(10104,'com.liferay.portlet.expando.model.ExpandoColumn'),(10105,'com.liferay.portlet.expando.model.ExpandoRow'),(10106,'com.liferay.portlet.expando.model.ExpandoTable'),(10107,'com.liferay.portlet.expando.model.ExpandoValue'),(10108,'com.liferay.portlet.journal.model.JournalArticle'),(10109,'com.liferay.portlet.journal.model.JournalArticleImage'),(10110,'com.liferay.portlet.journal.model.JournalArticleResource'),(10111,'com.liferay.portlet.journal.model.JournalContentSearch'),(10112,'com.liferay.portlet.journal.model.JournalFeed'),(10113,'com.liferay.portlet.journal.model.JournalStructure'),(10114,'com.liferay.portlet.journal.model.JournalTemplate'),(10115,'com.liferay.portlet.messageboards.model.MBBan'),(10116,'com.liferay.portlet.messageboards.model.MBCategory'),(10117,'com.liferay.portlet.messageboards.model.MBDiscussion'),(10118,'com.liferay.portlet.messageboards.model.MBMailingList'),(10011,'com.liferay.portlet.messageboards.model.MBMessage'),(10119,'com.liferay.portlet.messageboards.model.MBStatsUser'),(10012,'com.liferay.portlet.messageboards.model.MBThread'),(10120,'com.liferay.portlet.messageboards.model.MBThreadFlag'),(10121,'com.liferay.portlet.mobiledevicerules.model.MDRAction'),(10122,'com.liferay.portlet.mobiledevicerules.model.MDRRule'),(10123,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup'),(10124,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance'),(10125,'com.liferay.portlet.polls.model.PollsChoice'),(10126,'com.liferay.portlet.polls.model.PollsQuestion'),(10127,'com.liferay.portlet.polls.model.PollsVote'),(10128,'com.liferay.portlet.ratings.model.RatingsEntry'),(10129,'com.liferay.portlet.ratings.model.RatingsStats'),(10130,'com.liferay.portlet.shopping.model.ShoppingCart'),(10131,'com.liferay.portlet.shopping.model.ShoppingCategory'),(10132,'com.liferay.portlet.shopping.model.ShoppingCoupon'),(10133,'com.liferay.portlet.shopping.model.ShoppingItem'),(10134,'com.liferay.portlet.shopping.model.ShoppingItemField'),(10135,'com.liferay.portlet.shopping.model.ShoppingItemPrice'),(10136,'com.liferay.portlet.shopping.model.ShoppingOrder'),(10137,'com.liferay.portlet.shopping.model.ShoppingOrderItem'),(10138,'com.liferay.portlet.social.model.SocialActivity'),(10139,'com.liferay.portlet.social.model.SocialActivityAchievement'),(10140,'com.liferay.portlet.social.model.SocialActivityCounter'),(10141,'com.liferay.portlet.social.model.SocialActivityLimit'),(10142,'com.liferay.portlet.social.model.SocialActivitySetting'),(10143,'com.liferay.portlet.social.model.SocialRelation'),(10144,'com.liferay.portlet.social.model.SocialRequest'),(10145,'com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion'),(10146,'com.liferay.portlet.softwarecatalog.model.SCLicense'),(10147,'com.liferay.portlet.softwarecatalog.model.SCProductEntry'),(10148,'com.liferay.portlet.softwarecatalog.model.SCProductScreenshot'),(10149,'com.liferay.portlet.softwarecatalog.model.SCProductVersion'),(10150,'com.liferay.portlet.usernotifications.model.UserNotificationEvent'),(10151,'com.liferay.portlet.wiki.model.WikiNode'),(10013,'com.liferay.portlet.wiki.model.WikiPage'),(10152,'com.liferay.portlet.wiki.model.WikiPageResource');
/*!40000 ALTER TABLE `classname_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clustergroup`
--

DROP TABLE IF EXISTS `clustergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clustergroup` (
  `clusterGroupId` bigint(20) NOT NULL,
  `name` varchar(75) DEFAULT NULL,
  `clusterNodeIds` varchar(75) DEFAULT NULL,
  `wholeCluster` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`clusterGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clustergroup`
--

LOCK TABLES `clustergroup` WRITE;
/*!40000 ALTER TABLE `clustergroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `clustergroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `companyId` bigint(20) NOT NULL,
  `accountId` bigint(20) DEFAULT NULL,
  `webId` varchar(75) DEFAULT NULL,
  `key_` longtext,
  `mx` varchar(75) DEFAULT NULL,
  `homeURL` longtext,
  `logoId` bigint(20) DEFAULT NULL,
  `system` tinyint(4) DEFAULT NULL,
  `maxUsers` int(11) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`companyId`),
  UNIQUE KEY `IX_EC00543C` (`webId`),
  KEY `IX_38EFE3FD` (`logoId`),
  KEY `IX_12566EC2` (`mx`),
  KEY `IX_35E3E7C6` (`system`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (10154,10156,'liferay.com','rO0ABXNyABRqYXZhLnNlY3VyaXR5LktleVJlcL35T7OImqVDAgAETAAJYWxnb3JpdGhtdAASTGphdmEvbGFuZy9TdHJpbmc7WwAHZW5jb2RlZHQAAltCTAAGZm9ybWF0cQB+AAFMAAR0eXBldAAbTGphdmEvc2VjdXJpdHkvS2V5UmVwJFR5cGU7eHB0AANERVN1cgACW0Ks8xf4BghU4AIAAHhwAAAACGhYDcGJC21tdAADUkFXfnIAGWphdmEuc2VjdXJpdHkuS2V5UmVwJFR5cGUAAAAAAAAAABIAAHhyAA5qYXZhLmxhbmcuRW51bQAAAAAAAAAAEgAAeHB0AAZTRUNSRVQ=','liferay.com','',0,0,0,1);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_`
--

DROP TABLE IF EXISTS `contact_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_` (
  `contactId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `accountId` bigint(20) DEFAULT NULL,
  `parentContactId` bigint(20) DEFAULT NULL,
  `firstName` varchar(75) DEFAULT NULL,
  `middleName` varchar(75) DEFAULT NULL,
  `lastName` varchar(75) DEFAULT NULL,
  `prefixId` int(11) DEFAULT NULL,
  `suffixId` int(11) DEFAULT NULL,
  `male` tinyint(4) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `smsSn` varchar(75) DEFAULT NULL,
  `aimSn` varchar(75) DEFAULT NULL,
  `facebookSn` varchar(75) DEFAULT NULL,
  `icqSn` varchar(75) DEFAULT NULL,
  `jabberSn` varchar(75) DEFAULT NULL,
  `msnSn` varchar(75) DEFAULT NULL,
  `mySpaceSn` varchar(75) DEFAULT NULL,
  `skypeSn` varchar(75) DEFAULT NULL,
  `twitterSn` varchar(75) DEFAULT NULL,
  `ymSn` varchar(75) DEFAULT NULL,
  `employeeStatusId` varchar(75) DEFAULT NULL,
  `employeeNumber` varchar(75) DEFAULT NULL,
  `jobTitle` varchar(100) DEFAULT NULL,
  `jobClass` varchar(75) DEFAULT NULL,
  `hoursOfOperation` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`contactId`),
  KEY `IX_66D496A3` (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_`
--

LOCK TABLES `contact_` WRITE;
/*!40000 ALTER TABLE `contact_` DISABLE KEYS */;
INSERT INTO `contact_` VALUES (10159,10154,10158,'','2012-08-14 02:47:54','2012-08-14 02:47:54',10156,0,'','','',0,0,1,'2012-08-14 02:47:54','','','','','','','','','','','','','','',''),(10197,10154,10196,'','2012-08-14 02:47:55','2012-08-14 02:47:55',10156,0,'Test','','Test',0,0,1,'1970-01-01 00:00:00','','','','','','','','','','','','','','',''),(16563,10154,10196,'Test Test','2012-08-30 19:24:10','2012-08-30 19:24:33',10156,0,'ConX','','User',0,0,1,'1970-01-01 00:00:00','','','','','','','','','','','','','','','');
/*!40000 ALTER TABLE `contact_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `counter`
--

DROP TABLE IF EXISTS `counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `counter` (
  `name` varchar(75) NOT NULL,
  `currentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `counter`
--

LOCK TABLES `counter` WRITE;
/*!40000 ALTER TABLE `counter` DISABLE KEYS */;
INSERT INTO `counter` VALUES ('com.liferay.counter.model.Counter',36400),('com.liferay.portal.model.Layout#10172#true',1),('com.liferay.portal.model.Layout#10180#false',7),('com.liferay.portal.model.Layout#10198#false',1),('com.liferay.portal.model.Layout#10198#true',1),('com.liferay.portal.model.Layout#10310#true',1),('com.liferay.portal.model.Layout#10320#true',1),('com.liferay.portal.model.Layout#10329#true',1),('com.liferay.portal.model.Layout#10338#true',3),('com.liferay.portal.model.Layout#10364#true',4),('com.liferay.portal.model.Layout#16564#false',1),('com.liferay.portal.model.Layout#16564#true',1),('com.liferay.portal.model.Layout#32062#true',6),('com.liferay.portal.model.Permission',100),('com.liferay.portal.model.Resource',100),('com.liferay.portal.model.ResourceAction',900),('com.liferay.portal.model.ResourcePermission',3100),('com.liferay.portlet.documentlibrary.model.DLFileEntry',2000),('com.liferay.portlet.social.model.SocialActivity',6700);
/*!40000 ALTER TABLE `counter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `countryId` bigint(20) NOT NULL,
  `name` varchar(75) DEFAULT NULL,
  `a2` varchar(75) DEFAULT NULL,
  `a3` varchar(75) DEFAULT NULL,
  `number_` varchar(75) DEFAULT NULL,
  `idd_` varchar(75) DEFAULT NULL,
  `zipRequired` tinyint(4) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`countryId`),
  UNIQUE KEY `IX_717B97E1` (`a2`),
  UNIQUE KEY `IX_717B9BA2` (`a3`),
  UNIQUE KEY `IX_19DA007B` (`name`),
  KEY `IX_25D734CD` (`active_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'Canada','CA','CAN','124','001',1,1),(2,'China','CN','CHN','156','086',1,1),(3,'France','FR','FRA','250','033',1,1),(4,'Germany','DE','DEU','276','049',1,1),(5,'Hong Kong','HK','HKG','344','852',1,1),(6,'Hungary','HU','HUN','348','036',1,1),(7,'Israel','IL','ISR','376','972',1,1),(8,'Italy','IT','ITA','380','039',1,1),(9,'Japan','JP','JPN','392','081',1,1),(10,'South Korea','KR','KOR','410','082',1,1),(11,'Netherlands','NL','NLD','528','031',1,1),(12,'Portugal','PT','PRT','620','351',1,1),(13,'Russia','RU','RUS','643','007',1,1),(14,'Singapore','SG','SGP','702','065',1,1),(15,'Spain','ES','ESP','724','034',1,1),(16,'Turkey','TR','TUR','792','090',1,1),(17,'Vietnam','VN','VNM','704','084',1,1),(18,'United Kingdom','GB','GBR','826','044',1,1),(19,'United States','US','USA','840','001',1,1),(20,'Afghanistan','AF','AFG','4','093',1,1),(21,'Albania','AL','ALB','8','355',1,1),(22,'Algeria','DZ','DZA','12','213',1,1),(23,'American Samoa','AS','ASM','16','684',1,1),(24,'Andorra','AD','AND','20','376',1,1),(25,'Angola','AO','AGO','24','244',0,1),(26,'Anguilla','AI','AIA','660','264',1,1),(27,'Antarctica','AQ','ATA','10','672',1,1),(28,'Antigua','AG','ATG','28','268',0,1),(29,'Argentina','AR','ARG','32','054',1,1),(30,'Armenia','AM','ARM','51','374',1,1),(31,'Aruba','AW','ABW','533','297',0,1),(32,'Australia','AU','AUS','36','061',1,1),(33,'Austria','AT','AUT','40','043',1,1),(34,'Azerbaijan','AZ','AZE','31','994',1,1),(35,'Bahamas','BS','BHS','44','242',0,1),(36,'Bahrain','BH','BHR','48','973',1,1),(37,'Bangladesh','BD','BGD','50','880',1,1),(38,'Barbados','BB','BRB','52','246',1,1),(39,'Belarus','BY','BLR','112','375',1,1),(40,'Belgium','BE','BEL','56','032',1,1),(41,'Belize','BZ','BLZ','84','501',0,1),(42,'Benin','BJ','BEN','204','229',0,1),(43,'Bermuda','BM','BMU','60','441',1,1),(44,'Bhutan','BT','BTN','64','975',1,1),(45,'Bolivia','BO','BOL','68','591',1,1),(46,'Bosnia-Herzegovina','BA','BIH','70','387',1,1),(47,'Botswana','BW','BWA','72','267',0,1),(48,'Brazil','BR','BRA','76','055',1,1),(49,'British Virgin Islands','VG','VGB','92','284',1,1),(50,'Brunei','BN','BRN','96','673',1,1),(51,'Bulgaria','BG','BGR','100','359',1,1),(52,'Burkina Faso','BF','BFA','854','226',0,1),(53,'Burma (Myanmar)','MM','MMR','104','095',1,1),(54,'Burundi','BI','BDI','108','257',0,1),(55,'Cambodia','KH','KHM','116','855',1,1),(56,'Cameroon','CM','CMR','120','237',1,1),(57,'Cape Verde Island','CV','CPV','132','238',1,1),(58,'Cayman Islands','KY','CYM','136','345',1,1),(59,'Central African Republic','CF','CAF','140','236',0,1),(60,'Chad','TD','TCD','148','235',1,1),(61,'Chile','CL','CHL','152','056',1,1),(62,'Christmas Island','CX','CXR','162','061',1,1),(63,'Cocos Islands','CC','CCK','166','061',1,1),(64,'Colombia','CO','COL','170','057',1,1),(65,'Comoros','KM','COM','174','269',0,1),(66,'Republic of Congo','CD','COD','180','242',0,1),(67,'Democratic Republic of Congo','CG','COG','178','243',0,1),(68,'Cook Islands','CK','COK','184','682',0,1),(69,'Costa Rica','CR','CRI','188','506',1,1),(70,'Croatia','HR','HRV','191','385',1,1),(71,'Cuba','CU','CUB','192','053',1,1),(72,'Cyprus','CY','CYP','196','357',1,1),(73,'Czech Republic','CZ','CZE','203','420',1,1),(74,'Denmark','DK','DNK','208','045',1,1),(75,'Djibouti','DJ','DJI','262','253',0,1),(76,'Dominica','DM','DMA','212','767',0,1),(77,'Dominican Republic','DO','DOM','214','809',1,1),(78,'Ecuador','EC','ECU','218','593',1,1),(79,'Egypt','EG','EGY','818','020',1,1),(80,'El Salvador','SV','SLV','222','503',1,1),(81,'Equatorial Guinea','GQ','GNQ','226','240',0,1),(82,'Eritrea','ER','ERI','232','291',0,1),(83,'Estonia','EE','EST','233','372',1,1),(84,'Ethiopia','ET','ETH','231','251',1,1),(85,'Faeroe Islands','FO','FRO','234','298',1,1),(86,'Falkland Islands','FK','FLK','238','500',1,1),(87,'Fiji Islands','FJ','FJI','242','679',0,1),(88,'Finland','FI','FIN','246','358',1,1),(89,'French Guiana','GF','GUF','254','594',1,1),(90,'French Polynesia','PF','PYF','258','689',1,1),(91,'Gabon','GA','GAB','266','241',1,1),(92,'Gambia','GM','GMB','270','220',0,1),(93,'Georgia','GE','GEO','268','995',1,1),(94,'Ghana','GH','GHA','288','233',0,1),(95,'Gibraltar','GI','GIB','292','350',1,1),(96,'Greece','GR','GRC','300','030',1,1),(97,'Greenland','GL','GRL','304','299',1,1),(98,'Grenada','GD','GRD','308','473',0,1),(99,'Guadeloupe','GP','GLP','312','590',1,1),(100,'Guam','GU','GUM','316','671',1,1),(101,'Guatemala','GT','GTM','320','502',1,1),(102,'Guinea','GN','GIN','324','224',0,1),(103,'Guinea-Bissau','GW','GNB','624','245',1,1),(104,'Guyana','GY','GUY','328','592',0,1),(105,'Haiti','HT','HTI','332','509',1,1),(106,'Honduras','HN','HND','340','504',1,1),(107,'Iceland','IS','ISL','352','354',1,1),(108,'India','IN','IND','356','091',1,1),(109,'Indonesia','ID','IDN','360','062',1,1),(110,'Iran','IR','IRN','364','098',1,1),(111,'Iraq','IQ','IRQ','368','964',1,1),(112,'Ireland','IE','IRL','372','353',0,1),(113,'Ivory Coast','CI','CIV','384','225',1,1),(114,'Jamaica','JM','JAM','388','876',1,1),(115,'Jordan','JO','JOR','400','962',1,1),(116,'Kazakhstan','KZ','KAZ','398','007',1,1),(117,'Kenya','KE','KEN','404','254',1,1),(118,'Kiribati','KI','KIR','408','686',0,1),(119,'Kuwait','KW','KWT','414','965',1,1),(120,'North Korea','KP','PRK','408','850',0,1),(121,'Kyrgyzstan','KG','KGZ','471','996',1,1),(122,'Laos','LA','LAO','418','856',1,1),(123,'Latvia','LV','LVA','428','371',1,1),(124,'Lebanon','LB','LBN','422','961',1,1),(125,'Lesotho','LS','LSO','426','266',1,1),(126,'Liberia','LR','LBR','430','231',1,1),(127,'Libya','LY','LBY','434','218',1,1),(128,'Liechtenstein','LI','LIE','438','423',1,1),(129,'Lithuania','LT','LTU','440','370',1,1),(130,'Luxembourg','LU','LUX','442','352',1,1),(131,'Macau','MO','MAC','446','853',0,1),(132,'Macedonia','MK','MKD','807','389',1,1),(133,'Madagascar','MG','MDG','450','261',1,1),(134,'Malawi','MW','MWI','454','265',0,1),(135,'Malaysia','MY','MYS','458','060',1,1),(136,'Maldives','MV','MDV','462','960',1,1),(137,'Mali','ML','MLI','466','223',0,1),(138,'Malta','MT','MLT','470','356',1,1),(139,'Marshall Islands','MH','MHL','584','692',1,1),(140,'Martinique','MQ','MTQ','474','596',1,1),(141,'Mauritania','MR','MRT','478','222',0,1),(142,'Mauritius','MU','MUS','480','230',0,1),(143,'Mayotte Island','YT','MYT','175','269',1,1),(144,'Mexico','MX','MEX','484','052',1,1),(145,'Micronesia','FM','FSM','583','691',1,1),(146,'Moldova','MD','MDA','498','373',1,1),(147,'Monaco','MC','MCO','492','377',1,1),(148,'Mongolia','MN','MNG','496','976',1,1),(149,'Montenegro','ME','MNE','499','382',1,1),(150,'Montserrat','MS','MSR','500','664',0,1),(151,'Morocco','MA','MAR','504','212',1,1),(152,'Mozambique','MZ','MOZ','508','258',1,1),(153,'Namibia','NA','NAM','516','264',1,1),(154,'Nauru','NR','NRU','520','674',0,1),(155,'Nepal','NP','NPL','524','977',1,1),(156,'Netherlands Antilles','AN','ANT','530','599',1,1),(157,'New Caledonia','NC','NCL','540','687',1,1),(158,'New Zealand','NZ','NZL','554','064',1,1),(159,'Nicaragua','NI','NIC','558','505',1,1),(160,'Niger','NE','NER','562','227',1,1),(161,'Nigeria','NG','NGA','566','234',1,1),(162,'Niue','NU','NIU','570','683',0,1),(163,'Norfolk Island','NF','NFK','574','672',1,1),(164,'Norway','NO','NOR','578','047',1,1),(165,'Oman','OM','OMN','512','968',1,1),(166,'Pakistan','PK','PAK','586','092',1,1),(167,'Palau','PW','PLW','585','680',1,1),(168,'Palestine','PS','PSE','275','970',1,1),(169,'Panama','PA','PAN','591','507',1,1),(170,'Papua New Guinea','PG','PNG','598','675',1,1),(171,'Paraguay','PY','PRY','600','595',1,1),(172,'Peru','PE','PER','604','051',1,1),(173,'Philippines','PH','PHL','608','063',1,1),(174,'Poland','PL','POL','616','048',1,1),(175,'Puerto Rico','PR','PRI','630','787',1,1),(176,'Qatar','QA','QAT','634','974',0,1),(177,'Reunion Island','RE','REU','638','262',1,1),(178,'Romania','RO','ROU','642','040',1,1),(179,'Rwanda','RW','RWA','646','250',0,1),(180,'St. Helena','SH','SHN','654','290',1,1),(181,'St. Kitts','KN','KNA','659','869',0,1),(182,'St. Lucia','LC','LCA','662','758',0,1),(183,'St. Pierre & Miquelon','PM','SPM','666','508',1,1),(184,'St. Vincent','VC','VCT','670','784',1,1),(185,'San Marino','SM','SMR','674','378',1,1),(186,'Sao Tome & Principe','ST','STP','678','239',0,1),(187,'Saudi Arabia','SA','SAU','682','966',1,1),(188,'Senegal','SN','SEN','686','221',1,1),(189,'Serbia','RS','SRB','688','381',1,1),(190,'Seychelles','SC','SYC','690','248',0,1),(191,'Sierra Leone','SL','SLE','694','249',0,1),(192,'Slovakia','SK','SVK','703','421',1,1),(193,'Slovenia','SI','SVN','705','386',1,1),(194,'Solomon Islands','SB','SLB','90','677',0,1),(195,'Somalia','SO','SOM','706','252',0,1),(196,'South Africa','ZA','ZAF','710','027',1,1),(197,'Sri Lanka','LK','LKA','144','094',1,1),(198,'Sudan','SD','SDN','736','095',1,1),(199,'Suriname','SR','SUR','740','597',0,1),(200,'Swaziland','SZ','SWZ','748','268',1,1),(201,'Sweden','SE','SWE','752','046',1,1),(202,'Switzerland','CH','CHE','756','041',1,1),(203,'Syria','SY','SYR','760','963',0,1),(204,'Taiwan','TW','TWN','158','886',1,1),(205,'Tajikistan','TJ','TJK','762','992',1,1),(206,'Tanzania','TZ','TZA','834','255',0,1),(207,'Thailand','TH','THA','764','066',1,1),(208,'Togo','TG','TGO','768','228',1,1),(209,'Tonga','TO','TON','776','676',0,1),(210,'Trinidad & Tobago','TT','TTO','780','868',0,1),(211,'Tunisia','TN','TUN','788','216',1,1),(212,'Turkmenistan','TM','TKM','795','993',1,1),(213,'Turks & Caicos','TC','TCA','796','649',1,1),(214,'Tuvalu','TV','TUV','798','688',0,1),(215,'Uganda','UG','UGA','800','256',0,1),(216,'Ukraine','UA','UKR','804','380',1,1),(217,'United Arab Emirates','AE','ARE','784','971',0,1),(218,'Uruguay','UY','URY','858','598',1,1),(219,'Uzbekistan','UZ','UZB','860','998',1,1),(220,'Vanuatu','VU','VUT','548','678',0,1),(221,'Vatican City','VA','VAT','336','039',1,1),(222,'Venezuela','VE','VEN','862','058',1,1),(223,'Wallis & Futuna','WF','WLF','876','681',1,1),(224,'Western Samoa','EH','ESH','732','685',1,1),(225,'Yemen','YE','YEM','887','967',0,1),(226,'Zambia','ZM','ZMB','894','260',1,1),(227,'Zimbabwe','ZW','ZWE','716','263',0,1);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cyrususer`
--

DROP TABLE IF EXISTS `cyrususer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cyrususer` (
  `userId` varchar(75) NOT NULL,
  `password_` varchar(75) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cyrususer`
--

LOCK TABLES `cyrususer` WRITE;
/*!40000 ALTER TABLE `cyrususer` DISABLE KEYS */;
/*!40000 ALTER TABLE `cyrususer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cyrusvirtual`
--

DROP TABLE IF EXISTS `cyrusvirtual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cyrusvirtual` (
  `emailAddress` varchar(75) NOT NULL,
  `userId` varchar(75) NOT NULL,
  PRIMARY KEY (`emailAddress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cyrusvirtual`
--

LOCK TABLES `cyrusvirtual` WRITE;
/*!40000 ALTER TABLE `cyrusvirtual` DISABLE KEYS */;
/*!40000 ALTER TABLE `cyrusvirtual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ddlrecord`
--

DROP TABLE IF EXISTS `ddlrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ddlrecord` (
  `uuid_` varchar(75) DEFAULT NULL,
  `recordId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `versionUserId` bigint(20) DEFAULT NULL,
  `versionUserName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `DDMStorageId` bigint(20) DEFAULT NULL,
  `recordSetId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `displayIndex` int(11) DEFAULT NULL,
  PRIMARY KEY (`recordId`),
  UNIQUE KEY `IX_B4328F39` (`uuid_`,`groupId`),
  KEY `IX_87A6B599` (`recordSetId`),
  KEY `IX_AAC564D3` (`recordSetId`,`userId`),
  KEY `IX_8BC2F891` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ddlrecord`
--

LOCK TABLES `ddlrecord` WRITE;
/*!40000 ALTER TABLE `ddlrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `ddlrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ddlrecordset`
--

DROP TABLE IF EXISTS `ddlrecordset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ddlrecordset` (
  `uuid_` varchar(75) DEFAULT NULL,
  `recordSetId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `DDMStructureId` bigint(20) DEFAULT NULL,
  `recordSetKey` varchar(75) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `minDisplayRows` int(11) DEFAULT NULL,
  `scope` int(11) DEFAULT NULL,
  PRIMARY KEY (`recordSetId`),
  UNIQUE KEY `IX_56DAB121` (`groupId`,`recordSetKey`),
  UNIQUE KEY `IX_270BA5E1` (`uuid_`,`groupId`),
  KEY `IX_4FA5969F` (`groupId`),
  KEY `IX_561E44E9` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ddlrecordset`
--

LOCK TABLES `ddlrecordset` WRITE;
/*!40000 ALTER TABLE `ddlrecordset` DISABLE KEYS */;
/*!40000 ALTER TABLE `ddlrecordset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ddlrecordversion`
--

DROP TABLE IF EXISTS `ddlrecordversion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ddlrecordversion` (
  `recordVersionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `DDMStorageId` bigint(20) DEFAULT NULL,
  `recordSetId` bigint(20) DEFAULT NULL,
  `recordId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `displayIndex` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`recordVersionId`),
  UNIQUE KEY `IX_C79E347` (`recordId`,`version`),
  KEY `IX_2F4DDFE1` (`recordId`),
  KEY `IX_762ADC7` (`recordId`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ddlrecordversion`
--

LOCK TABLES `ddlrecordversion` WRITE;
/*!40000 ALTER TABLE `ddlrecordversion` DISABLE KEYS */;
/*!40000 ALTER TABLE `ddlrecordversion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ddmcontent`
--

DROP TABLE IF EXISTS `ddmcontent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ddmcontent` (
  `uuid_` varchar(75) DEFAULT NULL,
  `contentId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `xml` longtext,
  PRIMARY KEY (`contentId`),
  UNIQUE KEY `IX_EB9BDE28` (`uuid_`,`groupId`),
  KEY `IX_E3BAF436` (`companyId`),
  KEY `IX_50BF1038` (`groupId`),
  KEY `IX_AE4B50C2` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ddmcontent`
--

LOCK TABLES `ddmcontent` WRITE;
/*!40000 ALTER TABLE `ddmcontent` DISABLE KEYS */;
INSERT INTO `ddmcontent` VALUES ('37ed7dcb-3862-4331-89cb-5e2f074c9e8a',10444,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('b47b29dd-40d4-47d7-8732-675ebe5fd8f1',10464,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('da7d93b6-081c-4efb-abd7-9b2694a5994e',10476,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('00a9b418-f48a-46fe-9bd1-c905fdba40d7',10488,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[700]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[304]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('aead6c7a-79f5-4a0e-a563-2c2165e88167',10506,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('eb5e0126-9356-4321-9242-944314606677',10520,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('d85ca376-d0f0-4cf3-8650-4c61a405796b',10532,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('afc44bea-f8d5-4cf9-a246-ef545440ae99',10544,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('6a7143ce-d194-4a6b-834a-1b07a53cc541',10551,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('ebde24f1-f669-48be-805b-214f14666394',10560,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('518a784c-aa2e-4bff-8809-95aa8fbd5cc4',10567,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('fafe6754-cea7-4aea-adf7-95d0b878ad94',10582,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('ad40a9b7-ec96-48f4-bc63-a86efff853e9',10594,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[460]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[303]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('223574a0-0dca-4189-b9db-e7b85b84a4a7',11118,10180,10154,10196,'Test Test','2012-08-17 16:18:08','2012-08-17 16:18:08','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('84794a2e-b8bf-4ff8-a92f-bd2b09e017de',11122,10180,10154,10196,'Test Test','2012-08-17 16:18:08','2012-08-17 16:18:08','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('ac651859-0d41-444a-98f0-7326e99c36b3',32081,32062,10154,10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[305]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[167]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('31ed1219-45ee-4eb0-ac9d-cfcd0923960e',32097,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[673]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[320]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('7826f284-9270-4f46-b7e9-dec0e3757c99',32110,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[701]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[357]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('6bb9d4ef-377b-4746-afb1-8c220c992663',32123,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[673]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[320]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('aa49a792-c4cb-4e86-a6d4-686eb80152f2',32135,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[32]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[31]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('c00f6c45-f4e3-4c72-93d0-c05b694cb9e4',32147,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[30]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[30]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('ce72e4e6-0537-402d-af4e-e98601c18200',32159,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[305]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[167]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('f38714a2-bde6-4dbc-91f7-892dd5e23721',32171,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[30]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[30]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('e54d55a3-a490-4f36-8338-afb85926923d',32183,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[673]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[320]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('f9fe39b6-9571-4f51-9126-f1856bd02dd2',32195,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[32]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[31]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('8c33b900-6cea-4bf1-bf5b-dad3b16997e7',32207,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[305]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[167]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('8a60266e-4e41-4259-9ab9-946656cd8c43',32219,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[305]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[167]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('b11ef4a3-eb0b-41c1-bed8-864dd06bd518',32231,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[32]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[31]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('9731176d-a69b-4777-8afa-0ad622417e69',32243,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[30]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[30]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('15eeeb51-203e-4a49-9752-98eeeaaded64',32255,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[32]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[31]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('dd0851e4-a5be-4d76-950d-22f12c3b5e56',32260,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[30]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/png]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[30]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8 8 8 8]]></dynamic-content>\n	</dynamic-element>\n</root>'),('19bc451c-4c7d-4dea-bc9b-4161849eba0e',35828,10180,10154,10196,'Test Test','2012-11-30 12:54:04','2012-11-30 12:54:04','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('b2793309-45ee-472e-9644-69bb5cdcfca1',35842,10180,10154,10196,'Test Test','2012-11-30 12:54:04','2012-11-30 12:54:04','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('869777d1-f3e6-4e72-b85f-25b7221edb28',35852,10180,10154,10196,'Test Test','2012-11-30 12:54:05','2012-11-30 12:54:05','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('082ad8a0-eb5a-4637-a05f-03d597802b30',35858,10180,10154,10196,'Test Test','2012-11-30 12:54:05','2012-11-30 12:54:05','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('49936e24-0261-4c31-828c-456ccc48054b',35904,0,10154,10196,'Test Test','2012-11-30 17:01:40','2012-11-30 17:01:41','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('bf2e20fc-7be1-4106-8431-b149df350257',35911,0,10154,10196,'Test Test','2012-11-30 17:01:41','2012-11-30 17:01:41','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('dac12cf9-c956-4658-8177-fa5d33fba7e2',35918,0,10154,10196,'Test Test','2012-11-30 17:01:42','2012-11-30 17:01:42','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('2314da5c-e490-4555-bd10-1cf0d275b9f5',35925,0,10154,10196,'Test Test','2012-11-30 17:01:42','2012-11-30 17:01:42','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('497be584-5de7-4a52-bb95-06425e1d9e86',35932,0,10154,10196,'Test Test','2012-11-30 17:15:58','2012-11-30 17:15:58','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('6d80c290-2980-47ee-9ca3-a7eb1d30d6a2',35939,0,10154,10196,'Test Test','2012-11-30 17:15:58','2012-11-30 17:15:58','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('6328d4b0-46ff-4d9a-90f6-04789fc0ff81',35946,0,10154,10196,'Test Test','2012-11-30 17:15:58','2012-11-30 17:15:58','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('4d45cc63-ab18-4b60-88e8-c1cc4156139d',35953,0,10154,10196,'Test Test','2012-11-30 17:15:58','2012-11-30 17:15:59','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('4e0bf852-067e-4c77-b6a0-076dfbe01373',35960,0,10154,10196,'Test Test','2012-11-30 17:20:38','2012-11-30 17:20:38','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('a5283dc5-a2d4-48df-8d25-18f4e4eb0e79',35967,0,10154,10196,'Test Test','2012-11-30 17:20:38','2012-11-30 17:20:38','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('b9f5909a-f2dd-4d14-aae3-f7a211f1d7c3',35974,0,10154,10196,'Test Test','2012-11-30 17:20:39','2012-11-30 17:20:39','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('11d9d56b-8f82-458a-87fe-03d90cd87c55',35981,0,10154,10196,'Test Test','2012-11-30 17:20:39','2012-11-30 17:20:39','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('3c95dedb-c17e-4551-a135-475dc675bf56',35988,0,10154,10196,'Test Test','2012-11-30 17:40:01','2012-11-30 17:40:01','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('985a4072-e082-44b1-a09f-19c2cbe33530',35995,0,10154,10196,'Test Test','2012-11-30 17:40:01','2012-11-30 17:40:01','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('6476029d-8b8c-4f03-b60a-0ebca2c85cff',36002,0,10154,10196,'Test Test','2012-11-30 17:40:01','2012-11-30 17:40:01','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('71009e41-70f3-47d9-ae69-14374c942e70',36009,0,10154,10196,'Test Test','2012-11-30 17:40:01','2012-11-30 17:40:01','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('c52ec5c4-b21b-484e-84e2-3b3b48e6aef6',36016,0,10154,10196,'Test Test','2012-11-30 18:22:14','2012-11-30 18:22:14','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('df6849d0-a813-4cdf-96e8-86b299e739aa',36023,0,10154,10196,'Test Test','2012-11-30 18:22:14','2012-11-30 18:22:14','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('56e0aff0-f1c0-4512-9131-71b53e59f947',36030,0,10154,10196,'Test Test','2012-11-30 18:22:15','2012-11-30 18:22:15','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('b0b8ee86-9794-4697-a00c-4ea2527bcc57',36037,0,10154,10196,'Test Test','2012-11-30 18:22:15','2012-11-30 18:22:15','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('c9cc51af-5bdc-4e06-b906-f2e3ab003835',36044,0,10154,10196,'Test Test','2012-11-30 18:28:08','2012-11-30 18:28:09','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('32378c47-219f-46f1-8665-916b3e6f95a1',36051,0,10154,10196,'Test Test','2012-11-30 18:28:09','2012-11-30 18:28:09','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('178ac6bc-d45b-4c1a-81ce-4e754dd7793c',36058,0,10154,10196,'Test Test','2012-11-30 18:28:09','2012-11-30 18:28:09','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('706e9f11-2710-4277-826b-16b856c1452d',36065,0,10154,10196,'Test Test','2012-11-30 18:28:09','2012-11-30 18:28:09','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('ceb906cf-5aab-4f0e-bb6e-3b9275f302c5',36072,0,10154,10196,'Test Test','2012-11-30 18:32:43','2012-11-30 18:32:43','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('d7724cb5-bceb-4c79-b403-c4d244abdfab',36079,0,10154,10196,'Test Test','2012-11-30 18:32:43','2012-11-30 18:32:43','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('ab3a8f6c-66aa-4ea2-b768-fe9d09121351',36086,0,10154,10196,'Test Test','2012-11-30 18:32:43','2012-11-30 18:32:44','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('4d3e88ab-87a3-4a26-afa7-cdb53f1a0b53',36093,0,10154,10196,'Test Test','2012-11-30 18:32:44','2012-11-30 18:32:44','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('3a539e47-842b-4874-a995-1f0a6212947e',36100,0,10154,10196,'Test Test','2012-11-30 18:46:17','2012-11-30 18:46:17','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('858cb60e-0cce-4a8c-a7ed-45d3cf8ea786',36107,0,10154,10196,'Test Test','2012-11-30 18:46:17','2012-11-30 18:46:17','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('0607db85-ce97-43d0-984d-f72ac1b2a6e0',36114,0,10154,10196,'Test Test','2012-11-30 18:46:17','2012-11-30 18:46:17','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('c398a9e7-cf2f-46bd-b518-d45d38e86d51',36121,0,10154,10196,'Test Test','2012-11-30 18:46:17','2012-11-30 18:46:18','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('c62e26b4-b21d-4f2d-9af9-f578b32cbb1a',36138,0,10154,10196,'Test Test','2012-11-30 19:59:05','2012-11-30 19:59:05','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('b6bc9f95-6944-4afb-aa86-59e09ac43dcf',36145,0,10154,10196,'Test Test','2012-11-30 19:59:05','2012-11-30 19:59:06','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('108c3c14-d856-4427-a523-b7e510945a90',36152,0,10154,10196,'Test Test','2012-11-30 19:59:06','2012-11-30 19:59:06','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('c39387b6-f366-42ca-96f8-f6649a756500',36159,0,10154,10196,'Test Test','2012-11-30 19:59:06','2012-11-30 19:59:06','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('c007fbe3-9c98-418c-8c54-d285e8c721f1',36166,0,10154,10196,'Test Test','2012-11-30 21:29:47','2012-11-30 21:29:47','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('caaef133-ccc5-4425-b884-0ffd84583a0b',36173,0,10154,10196,'Test Test','2012-11-30 21:29:47','2012-11-30 21:29:47','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('befe16dc-fb30-4e63-9642-8091d49d7892',36180,0,10154,10196,'Test Test','2012-11-30 21:29:47','2012-11-30 21:29:47','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('085beab4-0ee3-4c29-a97b-babf2560dcae',36187,0,10154,10196,'Test Test','2012-11-30 21:29:47','2012-11-30 21:29:47','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('ff2dbbe0-853c-45ad-aabb-6a046e16edca',36304,0,10154,10196,'Test Test','2012-11-30 23:03:21','2012-11-30 23:03:23','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('fd16f4ad-5738-4d53-a55c-467f534f7a9d',36311,0,10154,10196,'Test Test','2012-11-30 23:03:23','2012-11-30 23:03:23','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('08754600-730d-47f0-becc-d57304150f5b',36318,0,10154,10196,'Test Test','2012-11-30 23:03:24','2012-11-30 23:03:24','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('9e7e8639-cfaf-4fde-b27e-82923fc40e66',36325,0,10154,10196,'Test Test','2012-11-30 23:03:24','2012-11-30 23:03:24','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('1bdfdc6d-b6bc-4796-ae53-1b04e7cd9611',36347,0,10154,10196,'Test Test','2012-12-01 02:46:37','2012-12-01 02:46:37','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('068c5cd5-9de1-48f7-b998-de5e3b914446',36354,0,10154,10196,'Test Test','2012-12-01 02:46:37','2012-12-01 02:46:37','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('fef72044-aab5-4d09-a8e7-009adf066fff',36361,0,10154,10196,'Test Test','2012-12-01 02:46:38','2012-12-01 02:46:38','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_AUTHOR\">\n		<dynamic-content><![CDATA[NISA012]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2000-06-06T09:24:24Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[http://www.vics.org/bol.doc - Microsoft Internet Explorer]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_TITLE\">\n		<dynamic-content><![CDATA[mso33E.PDF]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2000-12-21T21:16:24Z]]></dynamic-content>\n	</dynamic-element>\n</root>'),('0a671695-33b2-494b-b72b-7955ebcb101f',36368,0,10154,10196,'Test Test','2012-12-01 02:46:38','2012-12-01 02:46:38','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"TIFF_RESOLUTION_UNIT\">\n		<dynamic-content><![CDATA[Inch]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_WIDTH\">\n		<dynamic-content><![CDATA[450]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[image/jpeg]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_VERTICAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_IMAGE_LENGTH\">\n		<dynamic-content><![CDATA[582]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_BITS_PER_SAMPLE\">\n		<dynamic-content><![CDATA[8]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_DATE\">\n		<dynamic-content><![CDATA[2008-02-21T08:17:22]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_ORIENTATION\">\n		<dynamic-content><![CDATA[1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_RESOLUTION_HORIZONTAL\">\n		<dynamic-content><![CDATA[72.0]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"TIFF_SOFTWARE\">\n		<dynamic-content><![CDATA[Adobe Photoshop CS3 Windows]]></dynamic-content>\n	</dynamic-element>\n</root>'),('69270089-0429-431d-b466-37c9cb53e626',36392,10180,10154,10196,'Test Test','2012-12-01 05:26:51','2012-12-01 05:26:51','com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"MSOffice_CREATION_DATE\">\n		<dynamic-content><![CDATA[2012-12-01T05:26:51Z]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_CONTENT_TYPE\">\n		<dynamic-content><![CDATA[application/pdf]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"DublinCore_CREATOR\">\n		<dynamic-content><![CDATA[JasperReports (stockItemSingleLabelByPK)]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element name=\"HttpHeaders_LAST_MODIFIED\">\n		<dynamic-content><![CDATA[2012-12-01T05:26:51Z]]></dynamic-content>\n	</dynamic-element>\n</root>');
/*!40000 ALTER TABLE `ddmcontent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ddmstoragelink`
--

DROP TABLE IF EXISTS `ddmstoragelink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ddmstoragelink` (
  `uuid_` varchar(75) DEFAULT NULL,
  `storageLinkId` bigint(20) NOT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `structureId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`storageLinkId`),
  UNIQUE KEY `IX_702D1AD5` (`classPK`),
  KEY `IX_81776090` (`structureId`),
  KEY `IX_32A18526` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ddmstoragelink`
--

LOCK TABLES `ddmstoragelink` WRITE;
/*!40000 ALTER TABLE `ddmstoragelink` DISABLE KEYS */;
INSERT INTO `ddmstoragelink` VALUES ('6f3ee06a-cc31-4303-9863-4b1af07c149e',10445,10099,10444,10308),('55ffb5e7-e2bd-4d7e-a986-ad6cfeaf1d1f',10465,10099,10464,10308),('02923325-c0b3-4eb9-a6af-fd8840ab3973',10477,10099,10476,10308),('cf54dd01-1a13-4a78-8bb4-8b9902558106',10490,10099,10488,10308),('45348f97-27dd-4812-a164-8673771108a8',10507,10099,10506,10308),('15c99100-f57c-475c-8502-228ab9dc5b6c',10521,10099,10520,10308),('cdb3f170-24f5-4659-a1fa-96f8d6a6c26b',10533,10099,10532,10308),('3ba9197e-5df8-40d3-96c4-0b7997114700',10545,10099,10544,10308),('afb99a61-b7a0-401b-9e72-5f850acdf3f4',10552,10099,10551,10308),('84a675dc-0eb1-413d-8fa6-7fa36d2cb7ba',10561,10099,10560,10308),('53f7a8a3-ae7f-472e-a0ef-c93e10aea475',10568,10099,10567,10308),('a9cf71da-bc74-423d-958a-f107aa299c92',10583,10099,10582,10308),('4041df67-591f-4774-85b0-2d74030da540',10595,10099,10594,10308),('6d17678c-f09e-4874-9405-49bb92ec8c0a',11119,10099,11118,10308),('f4625376-341e-440d-802d-7d5d8f8a1dad',11123,10099,11122,10308),('db1944d0-8200-4693-ba55-40db17fd3102',32082,10099,32081,10308),('05f9dc1b-3509-4814-800f-99ee30910c0f',32098,10099,32097,10308),('e96d98b6-4dc9-4501-bc42-8e14955613b9',32111,10099,32110,10308),('3e19ed56-32f4-42fe-b077-d5ef0efd1598',32124,10099,32123,10308),('3faa32a4-325a-4b16-9bb2-779150fd3478',32136,10099,32135,10308),('33e33f85-f962-4c21-8d5f-a8f11c75d5a3',32148,10099,32147,10308),('dde99029-fd00-4b27-b813-e124a7d2f5bc',32160,10099,32159,10308),('646078f5-dc53-429d-868c-bf5a7aaa7568',32172,10099,32171,10308),('0748e863-d83c-4685-820c-87991800079a',32184,10099,32183,10308),('341e5be5-8e36-4543-ade6-0f795f39e985',32196,10099,32195,10308),('406750c7-26a2-4afa-98ad-513935afeefb',32208,10099,32207,10308),('978c9567-c038-4629-874c-4e20c4f73c3e',32220,10099,32219,10308),('f80d3fb3-0fcc-4ace-a03b-0d222080685d',32232,10099,32231,10308),('5f99d3c5-e820-4352-9fe7-3414f35f6255',32244,10099,32243,10308),('1506ab04-883d-45c0-84e7-b42b3dc9a878',32256,10099,32255,10308),('1035de31-5b12-4002-9f4d-d7ccc9a04f9a',32261,10099,32260,10308),('7de88a7e-fb6d-4835-8b93-cad87bae0100',35829,10099,35828,10308),('a918ef3e-53f4-46ec-81e2-9d3cd8fbc2c1',35843,10099,35842,10308),('e0ff1e85-02d3-4b24-8a90-db89e9ff70bb',35853,10099,35852,10308),('9a227d0f-282f-44a8-9d89-d7b080bea0ee',35859,10099,35858,10308),('c78e4acc-07ff-4fb5-9c30-f56010972d85',35905,10099,35904,10308),('dc71930c-ad56-44b2-b1d8-1c0cb77b2e54',35912,10099,35911,10308),('1098c88e-3cb7-4c3e-ab56-dfea8b733e0f',35919,10099,35918,10308),('662f335e-b9ed-427a-8520-ea9f091f663d',35926,10099,35925,10308),('54baf721-7dd2-43c2-a643-2bc83e7e4c88',35933,10099,35932,10308),('b6504bef-3972-4ef2-b357-629bcc41ff5a',35940,10099,35939,10308),('50956f92-247c-4457-aa59-1a94bdd613fc',35947,10099,35946,10308),('709c294e-6b05-4118-a031-76310dbcac52',35954,10099,35953,10308),('e012e74e-8091-4054-b670-2fb6724eabe8',35961,10099,35960,10308),('089a87bc-cfe5-432a-b751-237edfce6df3',35968,10099,35967,10308),('74968871-342f-42db-a3e2-55884d28b978',35975,10099,35974,10308),('d845756f-34f2-4333-ae78-5200493e633c',35982,10099,35981,10308),('fd7b27e3-8557-4341-8249-905dea9e6009',35989,10099,35988,10308),('bc7d5668-72bc-4291-85cc-90ff79e18f76',35996,10099,35995,10308),('a695a39e-97e7-4dfe-bafd-32be5ea842a9',36003,10099,36002,10308),('eead3e71-44eb-4ea8-a579-8eca710433d2',36010,10099,36009,10308),('0e7290cd-256d-401e-89f7-17c36db8dd5f',36017,10099,36016,10308),('d97a315c-6ecd-444a-8cfd-de46059b644d',36024,10099,36023,10308),('496820d6-5900-40b7-9a36-809d42e18638',36031,10099,36030,10308),('41d33ac7-595d-48a8-9b04-14e30debae49',36038,10099,36037,10308),('765a5fc0-211d-4ce9-91ac-45928f9a07fb',36045,10099,36044,10308),('cde6a6c6-42e3-4436-8f12-725e28b71bc8',36052,10099,36051,10308),('e1cf2d23-c479-462f-b9c5-c2a7647e8d63',36059,10099,36058,10308),('f3e5a554-a1a2-4673-aae7-a79c134f52dc',36066,10099,36065,10308),('327d9780-1100-499a-94fc-664d1df9dd15',36073,10099,36072,10308),('0730a3c4-e337-470b-a4cc-12e32f70440d',36080,10099,36079,10308),('3381eecb-091a-416d-b380-4bcafc1e0b19',36087,10099,36086,10308),('7a238645-ff5c-466e-8e4d-b0462548c2fa',36094,10099,36093,10308),('6f48fae2-f40d-4997-855e-4db06acd8ee0',36101,10099,36100,10308),('685166db-fd58-4bfc-9e31-e91cf40f77ea',36108,10099,36107,10308),('91e8da2f-c812-4351-8622-db0117c4076e',36115,10099,36114,10308),('cdd46288-2638-4932-956b-3b5eeb09ff8d',36122,10099,36121,10308),('3bbd1a8c-f208-4876-a120-9ff8e7bdc6c0',36139,10099,36138,10308),('977bca81-3a98-4045-bb68-1d2c2bf260e9',36146,10099,36145,10308),('fdd91f2a-36b0-4925-837f-6be98b2c2020',36153,10099,36152,10308),('d9cddb24-7129-433a-a7be-e113c50e1197',36160,10099,36159,10308),('ce20f2b2-dbb0-4cb1-8d1e-199eefa93193',36167,10099,36166,10308),('7116e0e3-3830-4f47-ae14-fe6ea5e6fd9a',36174,10099,36173,10308),('84311f97-2d4d-4433-a586-2ff58a56dbb0',36181,10099,36180,10308),('a14a07a1-62eb-4bf4-89f1-a0dc8fa78f8d',36188,10099,36187,10308),('62c9f40c-8f79-446b-aee9-ba8f0c8dce76',36305,10099,36304,10308),('185f0aba-50c9-45b2-be7b-7e0521d16fc8',36312,10099,36311,10308),('1c23ac95-aee7-4aba-bd08-052b6b43ace5',36319,10099,36318,10308),('7235a008-d60b-43c9-bc0c-5e93940c7547',36326,10099,36325,10308),('f0f6784e-7ce9-4bf4-94a0-7a9deeb9c886',36348,10099,36347,10308),('2836525c-4e0b-4d61-b32a-6f6139bb9ff9',36355,10099,36354,10308),('006e6ebb-0b00-4515-b89a-8d457421b8f2',36362,10099,36361,10308),('ad852fd7-6f34-4df1-b619-f3a9afa5bc27',36369,10099,36368,10308),('90f3243a-d856-4eb5-81d8-46be3a8b8662',36393,10099,36392,10308);
/*!40000 ALTER TABLE `ddmstoragelink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ddmstructure`
--

DROP TABLE IF EXISTS `ddmstructure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ddmstructure` (
  `uuid_` varchar(75) DEFAULT NULL,
  `structureId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `structureKey` varchar(75) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `xsd` longtext,
  `storageType` varchar(75) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  PRIMARY KEY (`structureId`),
  UNIQUE KEY `IX_490E7A1E` (`groupId`,`structureKey`),
  UNIQUE KEY `IX_85C7EBE2` (`uuid_`,`groupId`),
  KEY `IX_31817A62` (`classNameId`),
  KEY `IX_C8419FBE` (`groupId`),
  KEY `IX_E61809C8` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ddmstructure`
--

LOCK TABLES `ddmstructure` WRITE;
/*!40000 ALTER TABLE `ddmstructure` DISABLE KEYS */;
INSERT INTO `ddmstructure` VALUES ('17d3d886-ca73-468a-831f-52a4f1d6afed',10297,10192,10154,10158,'','2012-08-14 02:47:57','2012-08-14 02:47:57',10089,'Learning Module Metadata','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Learning Module Metadata</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Learning Module Metadata</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"select2235\" type=\"select\">\n		<dynamic-element name=\"home_edition\" type=\"option\" value=\"HE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Home Edition]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"business_edition\" type=\"option\" value=\"BE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Business Edition]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"enterprise_edition\" type=\"option\" value=\"EE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Enterprise Edition]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Product]]></entry>\n			<entry name=\"multiple\"><![CDATA[true]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select3212\" type=\"select\">\n		<dynamic-element name=\"1_0\" type=\"option\" value=\"1\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[1.0]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"2_0\" type=\"option\" value=\"2\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[2.0]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"3_0\" type=\"option\" value=\"3\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[3.0]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Version]]></entry>\n			<entry name=\"multiple\"><![CDATA[true]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select4115\" type=\"select\">\n		<dynamic-element name=\"administration\" type=\"option\" value=\"admin\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Administration]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"installation\" type=\"option\" value=\"install\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Installation]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"configuration\" type=\"option\" value=\"config\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Configuration]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Topics]]></entry>\n			<entry name=\"multiple\"><![CDATA[true]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select5069\" type=\"select\">\n		<dynamic-element name=\"beginner\" type=\"option\" value=\"beginner\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Beginner]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"intermediate\" type=\"option\" value=\"intermediate\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Intermediate]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"advanced\" type=\"option\" value=\"advanced\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Advanced]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Level]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('3edc25fc-adba-487a-a966-e42bba84a569',10298,10192,10154,10158,'','2012-08-14 02:47:57','2012-08-14 02:47:57',10089,'Marketing Campaign Theme Metadata','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Marketing Campaign Theme Metadata</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Marketing Campaign Theme Metadata</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"select2305\" type=\"select\">\n		<dynamic-element name=\"strong_company\" type=\"option\" value=\"strong\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Strong Company]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"new_product_launch\" type=\"option\" value=\"product\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[New Product Launch]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"company_philosophy\" type=\"option\" value=\"philosophy\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Company Philosophy]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Select]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select3229\" type=\"select\">\n		<dynamic-element name=\"your_trusted_advisor\" type=\"option\" value=\"advisor\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Your Trusted Advisor]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"10_years_of_customer_solutions\" type=\"option\" value=\"solutions\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[10 Years of Customer Solutions]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"making_a_difference\" type=\"option\" value=\"difference\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Making a Difference]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Campaign Theme]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select4282\" type=\"select\">\n		<dynamic-element name=\"awareness\" type=\"option\" value=\"awareness\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Awareness]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"lead_generation\" type=\"option\" value=\"leads\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Lead Generation]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"customer_service\" type=\"option\" value=\"service\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Customer Service]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Business Goal]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('56703416-768f-4906-9095-7088b570b2e1',10299,10192,10154,10158,'','2012-08-14 02:47:57','2012-08-14 02:47:57',10089,'Meeting Metadata','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Meeting Metadata</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Metadata for meeting</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"ddm-date3054\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text2217\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Meeting Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text4569\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Time]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text5638\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Location]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"textarea6584\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"textarea7502\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Participants]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('3f959dd2-37aa-4bae-b107-e8501a456eca',10301,10192,10154,10158,'','2012-08-14 02:47:57','2012-08-14 02:47:57',10089,'auto_faa90c6f-2abd-462f-b971-a3a0b2ac3076','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Contract</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Legal Contracts</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"ddm-date18949\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Effective Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"ddm-date20127\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Expiration Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select10264\" type=\"select\">\n		<dynamic-element name=\"nda\" type=\"option\" value=\"NDA\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[NDA]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"msa\" type=\"option\" value=\"MSA\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[MSA]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"license_agreement\" type=\"option\" value=\"License\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[License Agreement]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Contract Type]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select4893\" type=\"select\">\n		<dynamic-element name=\"draft\" type=\"option\" value=\"Draft\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Draft]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"in_review\" type=\"option\" value=\"Review\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[In Review]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"suspended\" type=\"option\" value=\"Suspended\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Suspended]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"signed\" type=\"option\" value=\"Signed\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Signed]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Status]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text14822\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Legal Reviewer]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text17700\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Signing Authority]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text2087\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Deal Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',1),('5b3e2f7b-4c6d-4b55-82f7-99fd30ad5046',10303,10192,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10089,'auto_916dc699-fc51-4891-b754-4e948b8a1afc','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Marketing Banner</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Marketing Banner</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"radio5547\" type=\"radio\">\n		<dynamic-element name=\"yes\" type=\"option\" value=\"yes\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Yes]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"no\" type=\"option\" value=\"no\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[No]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Needs Legal Review]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text2033\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Banner Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"textarea2873\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',1),('911ef7d9-c9a0-445f-8fc2-242271e2e690',10305,10192,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10089,'auto_3c7e53d1-d185-446f-887a-5358c9194861','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Online Training</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Online Training</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"text2082\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Lesson Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text2979\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Author]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',1),('9712b6f5-1a48-4942-a088-8d34dea5948f',10307,10192,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10089,'auto_006fab2e-435e-4e33-9e8b-c9d5b47971f3','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Sales Presentation</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Sales Presentation</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"select2890\" type=\"select\">\n		<dynamic-element name=\"home_edition\" type=\"option\" value=\"HE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Home Edition]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"business_edition\" type=\"option\" value=\"BE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Business Edition]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"enterprise_edition\" type=\"option\" value=\"EE\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Enterprise Edition]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Product]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select3864\" type=\"select\">\n		<dynamic-element name=\"1_0\" type=\"option\" value=\"1\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[1.0]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"2_0\" type=\"option\" value=\"2\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[2.0]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"3_0\" type=\"option\" value=\"3\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[3.0]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Version]]></entry>\n			<entry name=\"multiple\"><![CDATA[false]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select4831\" type=\"select\">\n		<dynamic-element name=\"website\" type=\"option\" value=\"website\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Website]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"collaboration\" type=\"option\" value=\"collaboration\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Collaboration]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"intranet\" type=\"option\" value=\"intranet\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Intranet]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Areas of Interest]]></entry>\n			<entry name=\"multiple\"><![CDATA[true]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"select5929\" type=\"select\">\n		<dynamic-element name=\"acme\" type=\"option\" value=\"acme\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[ACME]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"sevencogs\" type=\"option\" value=\"sevencogs\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[SevenCogs]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"freeplus\" type=\"option\" value=\"freeplus\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[FreePlus]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"displayChildLabelAsValue\"><![CDATA[true]]></entry>\n			<entry name=\"label\"><![CDATA[Competitors]]></entry>\n			<entry name=\"multiple\"><![CDATA[true]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"text1993\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Prospect Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"readOnly\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',1),('a69fa7e0-c61c-4b29-a6b9-f56bacb474ff',10308,10192,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10010,'TikaRawMetadata','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">TikaRawMetadata</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">TikaRawMetadata</Description></root>','<root available-locales=\"en_US\" default-locale=\"en_US\"><dynamic-element dataType=\"string\" name=\"ClimateForcast_PROGRAM_ID\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.PROGRAM_ID]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_COMMAND_LINE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.COMMAND_LINE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_HISTORY\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.HISTORY]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_TABLE_ID\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.TABLE_ID]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_INSTITUTION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.INSTITUTION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_SOURCE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.SOURCE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_CONTACT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.CONTACT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_PROJECT_ID\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.PROJECT_ID]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_CONVENTIONS\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.CONVENTIONS]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_REFERENCES\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.REFERENCES]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_ACKNOWLEDGEMENT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.ACKNOWLEDGEMENT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_REALIZATION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.REALIZATION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_EXPERIMENT_ID\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.EXPERIMENT_ID]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_COMMENT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.COMMENT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"ClimateForcast_MODEL_NAME_ENGLISH\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.ClimateForcast.MODEL_NAME_ENGLISH]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"CreativeCommons_LICENSE_URL\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.CreativeCommons.LICENSE_URL]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"CreativeCommons_LICENSE_LOCATION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.CreativeCommons.LICENSE_LOCATION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"CreativeCommons_WORK_TYPE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.CreativeCommons.WORK_TYPE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_FORMAT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.FORMAT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_IDENTIFIER\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.IDENTIFIER]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_MODIFIED\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.MODIFIED]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_CONTRIBUTOR\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.CONTRIBUTOR]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_COVERAGE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.COVERAGE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_CREATOR\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.CREATOR]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_DATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.DATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_DESCRIPTION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.DESCRIPTION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_LANGUAGE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.LANGUAGE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_PUBLISHER\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.PUBLISHER]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_RELATION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.RELATION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_RIGHTS\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.RIGHTS]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_SOURCE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.SOURCE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_SUBJECT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.SUBJECT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_TITLE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.TITLE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"DublinCore_TYPE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.DublinCore.TYPE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"Geographic_LATITUDE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.Geographic.LATITUDE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"Geographic_LONGITUDE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.Geographic.LONGITUDE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"Geographic_ALTITUDE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.Geographic.ALTITUDE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_ENCODING\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_ENCODING]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_LANGUAGE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_LANGUAGE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_LENGTH\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_LENGTH]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_LOCATION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_LOCATION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_DISPOSITION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_DISPOSITION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_MD5\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_MD5]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"HttpHeaders_CONTENT_TYPE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.HttpHeaders.CONTENT_TYPE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"HttpHeaders_LAST_MODIFIED\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.HttpHeaders.LAST_MODIFIED]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"HttpHeaders_LOCATION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.HttpHeaders.LOCATION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"Message_MESSAGE_RECIPIENT_ADDRESS\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.Message.MESSAGE_RECIPIENT_ADDRESS]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"Message_MESSAGE_FROM\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.Message.MESSAGE_FROM]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"Message_MESSAGE_TO\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.Message.MESSAGE_TO]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"Message_MESSAGE_CC\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.Message.MESSAGE_CC]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"Message_MESSAGE_BCC\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.Message.MESSAGE_BCC]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_KEYWORDS\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.KEYWORDS]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_COMMENTS\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.COMMENTS]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_LAST_AUTHOR\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.LAST_AUTHOR]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_APPLICATION_NAME\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.APPLICATION_NAME]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_CHARACTER_COUNT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.CHARACTER_COUNT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_LAST_PRINTED\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.LAST_PRINTED]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_LAST_SAVED\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.LAST_SAVED]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_PAGE_COUNT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.PAGE_COUNT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_REVISION_NUMBER\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.REVISION_NUMBER]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_WORD_COUNT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.WORD_COUNT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_TEMPLATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.TEMPLATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_AUTHOR\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.AUTHOR]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_TOTAL_TIME\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.TOTAL_TIME]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_SLIDE_COUNT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.SLIDE_COUNT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_PRESENTATION_FORMAT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.PRESENTATION_FORMAT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_PARAGRAPH_COUNT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.PARAGRAPH_COUNT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_NOTES\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.NOTES]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_MANAGER\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.MANAGER]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_LINE_COUNT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.LINE_COUNT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_CHARACTER_COUNT_WITH_SPACES\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.CHARACTER_COUNT_WITH_SPACES]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_APPLICATION_VERSION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.APPLICATION_VERSION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_VERSION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.VERSION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_CONTENT_STATUS\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.CONTENT_STATUS]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_CATEGORY\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.CATEGORY]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_COMPANY\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.COMPANY]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_SECURITY\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.SECURITY]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_EDIT_TIME\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.EDIT_TIME]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"MSOffice_CREATION_DATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.MSOffice.CREATION_DATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_BITS_PER_SAMPLE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.BITS_PER_SAMPLE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_IMAGE_LENGTH\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.IMAGE_LENGTH]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_IMAGE_WIDTH\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.IMAGE_WIDTH]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_SAMPLES_PER_PIXEL\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.SAMPLES_PER_PIXEL]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_FLASH_FIRED\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.FLASH_FIRED]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_EXPOSURE_TIME\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.EXPOSURE_TIME]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_F_NUMBER\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.F_NUMBER]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_FOCAL_LENGTH\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.FOCAL_LENGTH]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_ISO_SPEED_RATINGS\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.ISO_SPEED_RATINGS]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_EQUIPMENT_MAKE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.EQUIPMENT_MAKE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_EQUIPMENT_MODEL\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.EQUIPMENT_MODEL]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_SOFTWARE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.SOFTWARE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_ORIENTATION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.ORIENTATION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_RESOLUTION_HORIZONTAL\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.RESOLUTION_HORIZONTAL]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_RESOLUTION_VERTICAL\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.RESOLUTION_VERTICAL]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_RESOLUTION_UNIT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.RESOLUTION_UNIT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TIFF_ORIGINAL_DATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TIFF.ORIGINAL_DATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TikaMetadataKeys_RESOURCE_NAME_KEY\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TikaMetadataKeys.RESOURCE_NAME_KEY]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TikaMetadataKeys_PROTECTED\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TikaMetadataKeys.PROTECTED]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TikaMimeKeys_TIKA_MIME_FILE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TikaMimeKeys.TIKA_MIME_FILE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"TikaMimeKeys_MIME_TYPE_MAGIC\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.TikaMimeKeys.MIME_TYPE_MAGIC]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_DURATION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.DURATION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_ABS_PEAK_AUDIO_FILE_PATH\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.ABS_PEAK_AUDIO_FILE_PATH]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_ALBUM\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.ALBUM]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_ALT_TAPE_NAME\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.ALT_TAPE_NAME]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_ARTIST\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.ARTIST]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_AUDIO_MOD_DATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.AUDIO_MOD_DATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_AUDIO_SAMPLE_RATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.AUDIO_SAMPLE_RATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_AUDIO_SAMPLE_TYPE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.AUDIO_SAMPLE_TYPE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_AUDIO_CHANNEL_TYPE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.AUDIO_CHANNEL_TYPE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_AUDIO_COMPRESSOR\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.AUDIO_COMPRESSOR]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_COMPOSER\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.COMPOSER]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_COPYRIGHT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.COPYRIGHT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_ENGINEER\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.ENGINEER]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_FILE_DATA_RATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.FILE_DATA_RATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_GENRE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.GENRE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_INSTRUMENT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.INSTRUMENT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_KEY\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.KEY]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_LOG_COMMENT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.LOG_COMMENT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_LOOP\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.LOOP]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_NUMBER_OF_BEATS\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.NUMBER_OF_BEATS]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_METADATA_MOD_DATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.METADATA_MOD_DATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_PULL_DOWN\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.PULL_DOWN]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_RELATIVE_PEAK_AUDIO_FILE_PATH\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.RELATIVE_PEAK_AUDIO_FILE_PATH]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_RELEASE_DATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.RELEASE_DATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_SCALE_TYPE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.SCALE_TYPE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_SCENE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.SCENE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_SHOT_DATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.SHOT_DATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_SHOT_LOCATION\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.SHOT_LOCATION]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_SHOT_NAME\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.SHOT_NAME]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_SPEAKER_PLACEMENT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.SPEAKER_PLACEMENT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_STRETCH_MODE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.STRETCH_MODE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_TAPE_NAME\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.TAPE_NAME]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_TEMPO\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.TEMPO]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_TIME_SIGNATURE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.TIME_SIGNATURE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_TRACK_NUMBER\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.TRACK_NUMBER]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_ALPHA_MODE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_ALPHA_MODE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_ALPHA_UNITY_IS_TRANSPARENT\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_ALPHA_UNITY_IS_TRANSPARENT]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_COLOR_SPACE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_COLOR_SPACE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_COMPRESSOR\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_COMPRESSOR]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_FIELD_ORDER\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_FIELD_ORDER]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_FRAME_RATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_FRAME_RATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_MOD_DATE\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_MOD_DATE]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_PIXEL_DEPTH\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_PIXEL_DEPTH]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element><dynamic-element dataType=\"string\" name=\"XMPDM_VIDEO_PIXEL_ASPECT_RATIO\" type=\"text\"><meta-data locale=\"en_US\"><entry name=\"label\"><![CDATA[metadata.XMPDM.VIDEO_PIXEL_ASPECT_RATIO]]></entry><entry name=\"predefinedValue\"><![CDATA[]]></entry><entry name=\"required\"><![CDATA[false]]></entry><entry name=\"showLabel\"><![CDATA[true]]></entry></meta-data></dynamic-element></root>','xml',0),('d3613cb7-5046-4c21-820d-41e348be0162',10399,10180,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10097,'Contacts','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Contacts</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Contacts</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"company\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Company]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"email\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Email]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"firstName\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[First Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"imService\" type=\"select\">\n		<dynamic-element name=\"aol\" type=\"option\" value=\"aol\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[AOL]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"yahoo\" type=\"option\" value=\"yahoo\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Yahoo]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"gtalk\" type=\"option\" value=\"gtalk\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[GTalk]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Instant Messenger Service]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[[\"gtalk\"]]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"imUserName\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Instant Messenger]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"jobTitle\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Job Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"lastName\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Last Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"notes\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Notes]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"phoneMobile\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Phone (Mobile)]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"phoneOffice\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Phone (Office)]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('8a5df86e-96e3-434d-9d3d-5de5743a3a38',10400,10180,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10097,'Events','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Events</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Events</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"file-upload\" fieldNamespace=\"ddm\" name=\"attachment\" type=\"ddm-fileupload\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"acceptFiles\"><![CDATA[*]]></entry>\n			<entry name=\"folder\"><![CDATA[{\"folderId\":0,\"folderName\":\"Documents Home\"}]]></entry>\n			<entry name=\"label\"><![CDATA[Attachment]]></entry>\n			<entry name=\"name\"><![CDATA[attachment]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[Upload documents no larger than 3,000k.]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-fileupload]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"double\" fieldNamespace=\"ddm\" name=\"cost\" type=\"ddm-number\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Cost]]></entry>\n			<entry name=\"name\"><![CDATA[cost]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-number]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"description\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"name\"><![CDATA[description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[textarea]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"eventDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Date]]></entry>\n			<entry name=\"name\"><![CDATA[eventDate]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-date]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"eventName\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Event Name]]></entry>\n			<entry name=\"name\"><![CDATA[eventName]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[text]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"eventTime\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Time]]></entry>\n			<entry name=\"name\"><![CDATA[eventTime]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[text]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"location\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Location]]></entry>\n			<entry name=\"name\"><![CDATA[location]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[text]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('9eaaff9f-a16b-444f-93ef-6b2c9a7633d5',10401,10180,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10097,'Inventory','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Inventory</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Inventory</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"description\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"style\"><![CDATA[]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"item\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Item]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"style\"><![CDATA[]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"location\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Location]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"style\"><![CDATA[]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"purchaseDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Purchase Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"style\"><![CDATA[]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"double\" fieldNamespace=\"ddm\" name=\"purchasePrice\" type=\"ddm-number\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Purchase Price]]></entry>\n			<entry name=\"name\"><![CDATA[purchasePrice]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-number]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"double\" fieldNamespace=\"ddm\" name=\"quantity\" type=\"ddm-number\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Quantity]]></entry>\n			<entry name=\"name\"><![CDATA[quantity]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-number]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('7b91f2fe-4382-4343-ae8e-6381c2881da8',10402,10180,10154,10158,'','2012-08-14 02:47:59','2012-08-14 02:47:59',10097,'Issues Tracking','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Issues Tracking</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Issue Tracking</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"assignedTo\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Assigned To]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"file-upload\" fieldNamespace=\"ddm\" name=\"attachment\" type=\"ddm-fileupload\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"acceptFiles\"><![CDATA[*]]></entry>\n			<entry name=\"folder\"><![CDATA[{\"folderId\":0,\"folderName\":\"Documents Home\"}]]></entry>\n			<entry name=\"label\"><![CDATA[Attachment]]></entry>\n			<entry name=\"name\"><![CDATA[attachment]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[Upload documents no larger than 3,000k.]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-fileupload]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"comments\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Comments]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"description\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"dueDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Due Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"issueId\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[Issue ID]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"severity\" type=\"select\">\n		<dynamic-element name=\"critical\" type=\"option\" value=\"critical\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Critical]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"major\" type=\"option\" value=\"major\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Major]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"minor\" type=\"option\" value=\"minor\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Minor]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"trivial\" type=\"option\" value=\"trivial\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Trivial]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Severity]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[[\"minor\"]]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"status\" type=\"select\">\n		<dynamic-element name=\"open\" type=\"option\" value=\"open\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Open]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"pending\" type=\"option\" value=\"pending\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Pending]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"completed\" type=\"option\" value=\"completed\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Completed]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Status]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[[\"open\"]]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"title\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('fe9a4e26-cee0-4755-9b97-fef43b9c27ea',10403,10180,10154,10158,'','2012-08-14 02:47:59','2012-08-14 02:47:59',10097,'Meeting Minutes','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Meeting Minutes</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Meeting Minutes</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"file-upload\" fieldNamespace=\"ddm\" name=\"attachment\" type=\"ddm-fileupload\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"acceptFiles\"><![CDATA[*]]></entry>\n			<entry name=\"folder\"><![CDATA[{\"folderId\":0,\"folderName\":\"Documents Home\"}]]></entry>\n			<entry name=\"label\"><![CDATA[Attachment]]></entry>\n			<entry name=\"name\"><![CDATA[attachment]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[Upload documents no larger than 3,000k.]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-fileupload]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"author\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Author]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"description\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"duration\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Meeting Duration]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"meetingDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Meeting Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"minutes\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Minutes]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"title\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0),('a5244e29-2a25-4243-a147-f4ce7901fbd5',10404,10180,10154,10158,'','2012-08-14 02:47:59','2012-08-14 02:47:59',10097,'To Do','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">To Do</Name></root>','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">To Do</Description></root>','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element dataType=\"string\" name=\"assignedTo\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Assigned To]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"file-upload\" fieldNamespace=\"ddm\" name=\"attachment\" type=\"ddm-fileupload\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"acceptFiles\"><![CDATA[*]]></entry>\n			<entry name=\"folder\"><![CDATA[{\"folderId\":0,\"folderName\":\"Documents Home\"}]]></entry>\n			<entry name=\"label\"><![CDATA[Attachment]]></entry>\n			<entry name=\"name\"><![CDATA[attachment]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[Upload documents no larger than 3,000k.]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-fileupload]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"comments\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Comments]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"description\" type=\"textarea\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w100]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[100]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"endDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[End Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"integer\" fieldNamespace=\"ddm\" name=\"percentComplete\" type=\"ddm-integer\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w25]]></entry>\n			<entry name=\"label\"><![CDATA[% Complete]]></entry>\n			<entry name=\"name\"><![CDATA[percentComplete]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[0]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"tip\"><![CDATA[]]></entry>\n			<entry name=\"type\"><![CDATA[ddm-integer]]></entry>\n			<entry name=\"width\"><![CDATA[25]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"severity\" type=\"select\">\n		<dynamic-element name=\"critical\" type=\"option\" value=\"critical\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Critical]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"major\" type=\"option\" value=\"major\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Major]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"minor\" type=\"option\" value=\"minor\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Minor]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"trivial\" type=\"option\" value=\"trivial\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Trivial]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Severity]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[[\"minor\"]]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"date\" fieldNamespace=\"ddm\" name=\"startDate\" type=\"ddm-date\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Start Date]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"status\" type=\"select\">\n		<dynamic-element name=\"open\" type=\"option\" value=\"open\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Open]]></entry>\n				<entry name=\"multiple\"><![CDATA[false]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"pending\" type=\"option\" value=\"pending\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Pending]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"completed\" type=\"option\" value=\"completed\">\n			<meta-data locale=\"en_US\">\n				<entry name=\"label\"><![CDATA[Completed]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data locale=\"en_US\">\n			<entry name=\"label\"><![CDATA[Status]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[[\"open\"]]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element dataType=\"string\" name=\"title\" type=\"text\">\n		<meta-data locale=\"en_US\">\n			<entry name=\"fieldCssClass\"><![CDATA[aui-w50]]></entry>\n			<entry name=\"label\"><![CDATA[Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"showLabel\"><![CDATA[true]]></entry>\n			<entry name=\"width\"><![CDATA[50]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>','xml',0);
/*!40000 ALTER TABLE `ddmstructure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ddmstructurelink`
--

DROP TABLE IF EXISTS `ddmstructurelink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ddmstructurelink` (
  `structureLinkId` bigint(20) NOT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `structureId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`structureLinkId`),
  UNIQUE KEY `IX_C803899D` (`classPK`),
  KEY `IX_D43E4208` (`classNameId`),
  KEY `IX_17692B58` (`structureId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ddmstructurelink`
--

LOCK TABLES `ddmstructurelink` WRITE;
/*!40000 ALTER TABLE `ddmstructurelink` DISABLE KEYS */;
INSERT INTO `ddmstructurelink` VALUES (10446,10089,10424,10308),(10466,10089,10458,10308),(10478,10089,10475,10308),(10492,10089,10487,10308),(10509,10089,10501,10308),(10522,10089,10519,10308),(10534,10089,10531,10308),(10546,10089,10543,10308),(10553,10089,10550,10308),(10562,10089,10559,10308),(10569,10089,10564,10308),(10584,10089,10581,10308),(10596,10089,10593,10308),(11120,10089,11117,10308),(11124,10089,11121,10308),(32083,10089,32080,10308),(32099,10089,32092,10308),(32113,10089,32104,10308),(32125,10089,32122,10308),(32137,10089,32134,10308),(32149,10089,32146,10308),(32161,10089,32158,10308),(32173,10089,32170,10308),(32185,10089,32182,10308),(32197,10089,32194,10308),(32209,10089,32206,10308),(32221,10089,32218,10308),(32233,10089,32230,10308),(32245,10089,32242,10308),(32257,10089,32252,10308),(32262,10089,32258,10308),(35830,10089,35821,10308),(35844,10089,35838,10308),(35855,10089,35847,10308),(35860,10089,35857,10308),(35906,10089,35903,10308),(35913,10089,35910,10308),(35920,10089,35917,10308),(35927,10089,35924,10308),(35934,10089,35931,10308),(35941,10089,35938,10308),(35948,10089,35945,10308),(35955,10089,35952,10308),(35962,10089,35959,10308),(35969,10089,35966,10308),(35976,10089,35973,10308),(35983,10089,35980,10308),(35990,10089,35987,10308),(35997,10089,35994,10308),(36004,10089,36001,10308),(36011,10089,36008,10308),(36018,10089,36015,10308),(36025,10089,36022,10308),(36032,10089,36029,10308),(36039,10089,36036,10308),(36046,10089,36043,10308),(36053,10089,36050,10308),(36060,10089,36057,10308),(36067,10089,36064,10308),(36074,10089,36071,10308),(36081,10089,36078,10308),(36088,10089,36085,10308),(36095,10089,36092,10308),(36102,10089,36099,10308),(36109,10089,36106,10308),(36116,10089,36113,10308),(36123,10089,36120,10308),(36140,10089,36137,10308),(36147,10089,36144,10308),(36154,10089,36151,10308),(36161,10089,36158,10308),(36168,10089,36165,10308),(36175,10089,36172,10308),(36182,10089,36179,10308),(36189,10089,36186,10308),(36306,10089,36303,10308),(36313,10089,36310,10308),(36320,10089,36317,10308),(36327,10089,36324,10308),(36349,10089,36346,10308),(36356,10089,36353,10308),(36363,10089,36360,10308),(36370,10089,36367,10308),(36394,10089,36391,10308);
/*!40000 ALTER TABLE `ddmstructurelink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ddmtemplate`
--

DROP TABLE IF EXISTS `ddmtemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ddmtemplate` (
  `uuid_` varchar(75) DEFAULT NULL,
  `templateId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `structureId` bigint(20) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `mode_` varchar(75) DEFAULT NULL,
  `language` varchar(75) DEFAULT NULL,
  `script` longtext,
  PRIMARY KEY (`templateId`),
  UNIQUE KEY `IX_1AA75CE3` (`uuid_`,`groupId`),
  KEY `IX_DB24DDDD` (`groupId`),
  KEY `IX_33BEF579` (`language`),
  KEY `IX_C9757A51` (`structureId`),
  KEY `IX_5BC0E264` (`structureId`,`type_`),
  KEY `IX_5B019FE8` (`structureId`,`type_`,`mode_`),
  KEY `IX_C4F283C8` (`type_`),
  KEY `IX_F2A243A7` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ddmtemplate`
--

LOCK TABLES `ddmtemplate` WRITE;
/*!40000 ALTER TABLE `ddmtemplate` DISABLE KEYS */;
/*!40000 ALTER TABLE `ddmtemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlcontent`
--

DROP TABLE IF EXISTS `dlcontent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlcontent` (
  `contentId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `path_` varchar(255) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `data_` longblob,
  `size_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`contentId`),
  UNIQUE KEY `IX_FDD1AAA8` (`companyId`,`repositoryId`,`path_`,`version`),
  KEY `IX_6A83A66A` (`companyId`,`repositoryId`),
  KEY `IX_EB531760` (`companyId`,`repositoryId`,`path_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlcontent`
--

LOCK TABLES `dlcontent` WRITE;
/*!40000 ALTER TABLE `dlcontent` DISABLE KEYS */;
/*!40000 ALTER TABLE `dlcontent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlfileentry`
--

DROP TABLE IF EXISTS `dlfileentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlfileentry` (
  `uuid_` varchar(75) DEFAULT NULL,
  `fileEntryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `versionUserId` bigint(20) DEFAULT NULL,
  `versionUserName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `folderId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `extension` varchar(75) DEFAULT NULL,
  `mimeType` varchar(75) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` longtext,
  `extraSettings` longtext,
  `fileEntryTypeId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `size_` bigint(20) DEFAULT NULL,
  `readCount` int(11) DEFAULT NULL,
  `smallImageId` bigint(20) DEFAULT NULL,
  `largeImageId` bigint(20) DEFAULT NULL,
  `custom1ImageId` bigint(20) DEFAULT NULL,
  `custom2ImageId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`fileEntryId`),
  UNIQUE KEY `IX_5391712` (`groupId`,`folderId`,`name`),
  UNIQUE KEY `IX_ED5CA615` (`groupId`,`folderId`,`title`),
  UNIQUE KEY `IX_BC2E7E6A` (`uuid_`,`groupId`),
  KEY `IX_4CB1B2B4` (`companyId`),
  KEY `IX_F4AF5636` (`groupId`),
  KEY `IX_93CF8193` (`groupId`,`folderId`),
  KEY `IX_29D0AF28` (`groupId`,`folderId`,`fileEntryTypeId`),
  KEY `IX_43261870` (`groupId`,`userId`),
  KEY `IX_D20C434D` (`groupId`,`userId`,`folderId`),
  KEY `IX_64F0FE40` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlfileentry`
--

LOCK TABLES `dlfileentry` WRITE;
/*!40000 ALTER TABLE `dlfileentry` DISABLE KEYS */;
INSERT INTO `dlfileentry` VALUES ('3ed17b37-3380-4d30-a5fc-22a20625a76f',10411,10180,10154,10158,'',10158,'','2012-08-14 02:48:00','2012-08-14 02:48:00',10180,0,'1','jpg','image/jpeg','welcome_bg_3.jpg','','',0,'1.0',65684,0,0,0,0,0),('ebd9c0bf-a7c3-4638-997f-062cdb9575b0',10419,10180,10154,10158,'',10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,'2','jpg','image/jpeg','welcome_bg_11.jpg','','',0,'1.0',43583,0,0,0,0,0),('8b76a308-57ed-442e-999e-df1a78f1045f',10428,10180,10154,10158,'',10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,'3','jpg','image/jpeg','welcome_bg_12.jpg','','',0,'1.0',46446,0,0,0,0,0),('47a2c229-e287-476a-8f5e-618ac56e857f',10436,10180,10154,10158,'',10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,'4','png','image/png','welcome_bg_10.png','','',0,'1.0',27139,0,0,0,0,0),('85baf84c-5151-4d30-9cda-a1346fbdbf4d',10447,10180,10154,10158,'',10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,'5','jpg','image/jpeg','welcome_bg_2.jpg','','',0,'1.0',72911,0,0,0,0,0),('ce45d760-a26a-452c-976f-c2b0022b7fc4',10455,10180,10154,10158,'',10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,'6','jpg','image/jpeg','welcome_bg_9.jpg','','',0,'1.0',50215,0,0,0,0,0),('c1cd182d-ba30-4961-ae6b-3a2daae746e3',10467,10180,10154,10158,'',10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,'7','jpg','image/jpeg','welcome_bg_4.jpg','','',0,'1.0',62483,0,0,0,0,0),('039e457b-ce55-406a-901f-7c1eb59d38ca',10479,10180,10154,10158,'',10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,'8','jpg','image/jpeg','welcome_bg_6.jpg','','',0,'1.0',45456,0,0,0,0,0),('0cc635cd-6b1a-4c4a-9c85-52f56c93a0fb',10489,10180,10154,10158,'',10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,'9','jpg','image/jpeg','welcome_bg_7.jpg','','',0,'1.0',68705,0,0,0,0,0),('33e84fce-e4be-4e50-87e6-b209f01a77a3',10499,10180,10154,10158,'',10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10180,0,'10','jpg','image/jpeg','welcome_bg_5.jpg','','',0,'1.0',40022,0,0,0,0,0),('c3edd8cf-f02c-48c2-b0ee-2a0d5988a1b1',10511,10180,10154,10158,'',10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10180,0,'11','jpg','image/jpeg','welcome_bg_13.jpg','','',0,'1.0',33632,0,0,0,0,0),('60087992-6d2a-4f75-985d-c5e859173b21',10523,10180,10154,10158,'',10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10180,0,'12','jpg','image/jpeg','welcome_bg_1.jpg','','',0,'1.0',54277,1,0,0,0,0),('9da09bba-5fd1-4f85-bb5e-3665b5317aa2',10535,10180,10154,10158,'',10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10180,0,'13','jpg','image/jpeg','welcome_bg_8.jpg','','',0,'1.0',43664,0,0,0,0,0),('c69a9473-6d94-456b-9721-d1aa6d9e4822',11101,10180,10154,10196,'Test Test',10196,'Test Test','2012-08-17 16:18:07','2012-08-17 16:18:07',10180,0,'439','pdf','application/pdf','Bill Of Laden','Bill Of Laden','',0,'1.0',97209,0,0,0,0,0),('9efd576f-7f79-4394-841e-2f863e8a27d4',11109,10180,10154,10196,'Test Test',10196,'Test Test','2012-08-17 16:18:08','2012-08-17 16:18:08',10180,0,'440','jpg','image/jpeg','PO','PO','',0,'1.0',51114,0,0,0,0,0),('1b9dbc42-d2c9-4f72-ae8e-84329ebcf4cb',32070,32062,10154,10158,'',10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30',32062,0,'1301','png','image/png','pen.png','','',0,'1.0',50675,0,0,0,0,0),('74353928-596b-4746-8582-db08f631e235',32078,32062,10154,10158,'',10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30',32062,0,'1302','jpg','image/jpeg','carousel_item3.jpg','','',0,'1.0',41464,0,0,0,0,0),('0800d3a2-be09-43ac-8a57-5fc3b2a7ad64',32090,32062,10154,10158,'',10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,'1303','png','image/png','people.png','','',0,'1.0',379934,0,0,0,0,0),('263e2d7c-e799-4e62-a115-96e77a29181a',32102,32062,10154,10158,'',10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,'1304','jpg','image/jpeg','carousel_item1.jpg','','',0,'1.0',59456,0,0,0,0,0),('7015eb92-9b12-4dae-9f8a-a46fd7930afc',32114,32062,10154,10158,'',10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,'1305','png','image/png','facebook.png','','',0,'1.0',1589,0,0,0,0,0),('4535eaaf-0509-414c-a62c-a0252cd232d3',32126,32062,10154,10158,'',10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,'1306','png','image/png','icon_gears.png','','',0,'1.0',2464,0,0,0,0,0),('0b857adc-5e8e-4054-aaf4-d68c7db99693',32138,32062,10154,10158,'',10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,'1307','png','image/png','mouse.png','','',0,'1.0',55712,0,0,0,0,0),('ee00f58b-ca06-415e-9ae5-bc9d17cfc2bf',32150,32062,10154,10158,'',10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,'1308','png','image/png','icon_phone.png','','',0,'1.0',1581,0,0,0,0,0),('56c6fa37-63bb-49d0-bf10-34ad3c82dd90',32162,32062,10154,10158,'',10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,'1309','jpg','image/jpeg','carousel_item2.jpg','','',0,'1.0',43321,0,0,0,0,0),('570a24b1-1d06-4863-b794-a87d17a7a98b',32174,32062,10154,10158,'',10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,'1310','png','image/png','twitter.png','','',0,'1.0',1788,0,0,0,0,0),('9d97e944-6800-41e9-920a-3e0690b54287',32186,32062,10154,10158,'',10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,'1311','png','image/png','paperpen.png','','',0,'1.0',78785,0,0,0,0,0),('a77d22f5-d564-4998-ad29-b8d7b9434405',32198,32062,10154,10158,'',10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,'1312','png','image/png','network.png','','',0,'1.0',87442,0,0,0,0,0),('74bf915f-b101-459a-8cf5-aea8806567dc',32210,32062,10154,10158,'',10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,'1313','png','image/png','rss.png','','',0,'1.0',2164,0,0,0,0,0),('a37e2884-e689-4bb6-a3ac-d1f8137df566',32222,32062,10154,10158,'',10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,'1314','png','image/png','icon_beaker.png','','',0,'1.0',1427,0,0,0,0,0),('291de86e-8d38-4db7-b2b8-8493820b684c',32234,32062,10154,10158,'',10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,'1315','png','image/png','linkedin.png','','',0,'1.0',1774,0,0,0,0,0),('f7ea45e0-fc11-4ca2-9529-bb90f1486b71',32246,32062,10154,10158,'',10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,'1316','png','image/png','icon_network.png','','',0,'1.0',2348,0,0,0,0,0),('944f09e3-feb4-4d4f-9813-e046e8654e58',35811,10180,10154,10196,'Test Test',10196,'Test Test','2012-11-30 12:54:03','2012-12-01 02:46:36',10180,35809,'1701','pdf','application/pdf','Bill Of Laden','Bill Of Laden','',0,'1.12',97286,0,0,0,0,0),('29d27da3-eef1-4d57-9c51-559a67e5bdd5',35819,10180,10154,10196,'Test Test',10196,'Test Test','2012-11-30 12:54:04','2012-12-01 02:46:37',10180,35809,'1702','jpg','image/jpeg','PO','PO','',0,'1.12',51114,0,0,0,0,0),('a94d6c7d-ca5d-445b-90eb-20141f27a6d1',35833,10180,10154,10196,'Test Test',10196,'Test Test','2012-11-30 12:54:04','2012-12-01 02:46:38',10180,35831,'1703','pdf','application/pdf','Bill Of Laden','Bill Of Laden','',0,'1.12',97286,0,0,0,0,0),('e23171c1-8553-46bd-b2e0-78acf185c43d',35845,10180,10154,10196,'Test Test',10196,'Test Test','2012-11-30 12:54:05','2012-12-01 02:46:38',10180,35831,'1704','jpg','image/jpeg','PO','PO','',0,'1.12',51114,0,0,0,0,0),('8f864110-fa5f-4f1d-b9a0-f027119f9139',36383,10180,10154,10196,'Test Test',10196,'Test Test','2012-12-01 05:26:51','2012-12-01 05:26:51',10180,36381,'1902','','application/pdf','Label RDFLT000001-01L1_1','Label RDFLT000001-01L1_1','',0,'1.0',4383,7,0,0,0,0);
/*!40000 ALTER TABLE `dlfileentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlfileentrymetadata`
--

DROP TABLE IF EXISTS `dlfileentrymetadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlfileentrymetadata` (
  `uuid_` varchar(75) DEFAULT NULL,
  `fileEntryMetadataId` bigint(20) NOT NULL,
  `DDMStorageId` bigint(20) DEFAULT NULL,
  `DDMStructureId` bigint(20) DEFAULT NULL,
  `fileEntryTypeId` bigint(20) DEFAULT NULL,
  `fileEntryId` bigint(20) DEFAULT NULL,
  `fileVersionId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`fileEntryMetadataId`),
  UNIQUE KEY `IX_7332B44F` (`DDMStructureId`,`fileVersionId`),
  KEY `IX_4F40FE5E` (`fileEntryId`),
  KEY `IX_A44636C9` (`fileEntryId`,`fileVersionId`),
  KEY `IX_F8E90438` (`fileEntryTypeId`),
  KEY `IX_1FE9C04` (`fileVersionId`),
  KEY `IX_D49AB5D1` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlfileentrymetadata`
--

LOCK TABLES `dlfileentrymetadata` WRITE;
/*!40000 ALTER TABLE `dlfileentrymetadata` DISABLE KEYS */;
INSERT INTO `dlfileentrymetadata` VALUES ('6f656ac9-4d13-4eb4-be9e-fcaf29c9e178',10424,10444,10308,0,10411,10412),('3c4fe3ca-5c7f-470c-9538-bd419a66dfa2',10458,10464,10308,0,10419,10420),('7637e357-7168-4071-907c-23e6cc3cca69',10475,10476,10308,0,10428,10429),('bcbb6ac3-3466-496d-a212-90c42c0a0b58',10487,10488,10308,0,10436,10437),('b36d9022-771f-45e9-9379-72aaf2f94ce4',10501,10506,10308,0,10447,10448),('58b843d0-0b9e-4b61-be86-6a85b71f7bad',10519,10520,10308,0,10455,10456),('080143f6-56bf-4143-9b29-61f76ad40cb9',10531,10532,10308,0,10467,10468),('1540c69f-f7e2-41b5-9caa-5fabb421dce2',10543,10544,10308,0,10479,10480),('b2b10aec-2b66-4ef9-b183-b3d115da6288',10550,10551,10308,0,10489,10491),('951b6bd9-2153-4162-be24-82bb32d6c299',10559,10560,10308,0,10499,10500),('ae5894b2-cc95-49c9-b091-2e7c0ea9631a',10564,10567,10308,0,10511,10512),('16574ffc-4f9f-49db-b07a-7844715b7a73',10581,10582,10308,0,10523,10524),('20b608a7-444b-4267-bb99-4978196f7b1f',10593,10594,10308,0,10535,10536),('8f129005-c221-4e41-b149-87491421f57b',11117,11118,10308,0,11101,11102),('b77fd88f-6364-4070-b549-7f3631c63773',11121,11122,10308,0,11109,11110),('604d0c1d-8cdd-4c7f-9a79-3255dc2bdfd2',32080,32081,10308,0,32070,32071),('bd43c549-29f7-4ade-aa6b-0933dc9e9b79',32092,32097,10308,0,32078,32079),('0f635d39-c2f8-439e-b60e-e77721337ff5',32104,32110,10308,0,32090,32091),('df3d8e9d-670b-43c6-88a2-20c884e1a5bb',32122,32123,10308,0,32102,32103),('4b02c0ff-04fa-494a-82e5-2d71e5121fe1',32134,32135,10308,0,32114,32115),('03297c63-06a9-4050-87b4-dc5ce8e2b29b',32146,32147,10308,0,32126,32127),('98055d0c-1bcf-46ed-bc71-374e6d3001d5',32158,32159,10308,0,32138,32139),('52bfcd10-a8e3-4586-b643-b60cc1b8ba49',32170,32171,10308,0,32150,32151),('58e70bf0-dff8-4a30-96ef-02776064d300',32182,32183,10308,0,32162,32163),('a0906636-8d1d-4ec3-85f9-b2f97c7a8ad4',32194,32195,10308,0,32174,32175),('ee1d9a87-6d3b-457f-902c-b8db0fb51c44',32206,32207,10308,0,32186,32187),('2f178bce-afcb-44c4-842d-9fde290d1a69',32218,32219,10308,0,32198,32199),('75554a1a-4a79-4fb2-a0f9-7c26eaaab961',32230,32231,10308,0,32210,32211),('6143acd8-fd1a-4ba8-8949-703c11468f24',32242,32243,10308,0,32222,32223),('29c51873-e3b1-4eab-ac3d-c6f970f586e4',32252,32255,10308,0,32234,32235),('c31c2fb0-60dd-4e43-a0d5-0b3d67606427',32258,32260,10308,0,32246,32247),('d9417e37-b52a-4891-aaf8-598743c06b9e',35821,35828,10308,0,35811,35812),('af131fd0-306d-4307-bcfe-9bf4efb9883c',35838,35842,10308,0,35819,35820),('8b7052b3-63d2-4ab5-80fa-f5ed428f9d4b',35847,35852,10308,0,35833,35834),('e94ff6bb-f928-42f9-91fd-c749e2e3ae84',35857,35858,10308,0,35845,35846),('acad9504-5f8e-4bcc-92b2-6278ee3113f3',35903,35904,10308,0,35811,35902),('3384dd9f-7fbe-47a2-ac50-6c77b9cf1898',35910,35911,10308,0,35819,35909),('7b2ac51b-20e8-4556-85d0-24ed851caf66',35917,35918,10308,0,35833,35916),('e8026a9e-3441-456d-9a3c-c784170c3baa',35924,35925,10308,0,35845,35923),('aff87a1e-44a1-476b-9bef-49af951aac09',35931,35932,10308,0,35811,35930),('322d41c8-8fc4-4121-a323-683d447bf8b7',35938,35939,10308,0,35819,35937),('fff6f9f7-024d-4d1a-8e52-fc68e6ebd59b',35945,35946,10308,0,35833,35944),('c22af193-e7b6-4841-be61-022c6038ff29',35952,35953,10308,0,35845,35951),('ee09c645-7c7d-4922-bacf-79a904c5b8cf',35959,35960,10308,0,35811,35958),('584ba25f-1c20-4cf7-851f-ff64f2f046d4',35966,35967,10308,0,35819,35965),('3bac7d34-1eaa-444f-b54a-cc2f2c95bf08',35973,35974,10308,0,35833,35972),('f07ed952-fd04-4ea4-a279-febbda528c6e',35980,35981,10308,0,35845,35979),('addc557e-72fa-40d0-881e-0f4647d59a7f',35987,35988,10308,0,35811,35986),('45d5f08b-cbc0-452e-b17e-928f6455d84f',35994,35995,10308,0,35819,35993),('da79c4cc-b9f7-4767-8b77-070c1bc20c2a',36001,36002,10308,0,35833,36000),('229172ff-1313-4655-ad8c-51db22912054',36008,36009,10308,0,35845,36007),('629a1127-e29c-4b34-89a8-b4f1e68ee476',36015,36016,10308,0,35811,36014),('ad29f732-116e-43a9-8e30-17e15b45ed65',36022,36023,10308,0,35819,36021),('52fdfed9-3a85-487c-be3c-5130422a9632',36029,36030,10308,0,35833,36028),('959443f9-cc44-4b8e-a87d-2203d4b560e2',36036,36037,10308,0,35845,36035),('226e9e4d-c4be-4c51-a73d-9948ef6a807b',36043,36044,10308,0,35811,36042),('6a7731ac-0f30-43a0-a288-8e9c1a27991d',36050,36051,10308,0,35819,36049),('1ebc7076-81cd-4244-b6fe-2bd8f3265e38',36057,36058,10308,0,35833,36056),('4a7f98ea-8616-4c25-859b-77a31a5daaf3',36064,36065,10308,0,35845,36063),('460dee58-362e-4467-ac1d-afa8167950eb',36071,36072,10308,0,35811,36070),('b03eea43-d385-4847-96c2-4287ddae666c',36078,36079,10308,0,35819,36077),('658f13e9-a1f8-4524-9370-f8dce238691d',36085,36086,10308,0,35833,36084),('427bd8e5-52d3-4692-a573-82464fcfd399',36092,36093,10308,0,35845,36091),('f9919dfd-a31a-4d17-a007-baacfad390b2',36099,36100,10308,0,35811,36098),('de21d254-8902-467d-8c7c-2be7ab8ac5ca',36106,36107,10308,0,35819,36105),('c674916a-e4d3-4bf0-a1b2-6e82ba3bc4bd',36113,36114,10308,0,35833,36112),('025b0778-0db9-43e3-89cc-cb6792c698a0',36120,36121,10308,0,35845,36119),('ccf1cffd-5321-42ef-9af0-f7b99496e0f0',36137,36138,10308,0,35811,36136),('4e260a77-2806-41ef-8bff-9a223a668edb',36144,36145,10308,0,35819,36143),('267dc281-1458-42ba-b459-77ec3eb1c302',36151,36152,10308,0,35833,36150),('eee469d1-9144-4a82-927d-7cf11a7b7de5',36158,36159,10308,0,35845,36157),('f2811438-ffff-49d8-ab49-c7c20ef671d7',36165,36166,10308,0,35811,36164),('ad62b68f-c44b-4df8-970b-18a0458d35f8',36172,36173,10308,0,35819,36171),('ff0ae1a4-3ec2-4dd5-b4b4-6411d5e9d064',36179,36180,10308,0,35833,36178),('c776ca37-9407-4175-815f-80956f4634ca',36186,36187,10308,0,35845,36185),('6ccf1449-dd29-499d-9157-a5bea4bade91',36303,36304,10308,0,35811,36302),('9328bd95-2d49-4301-8b8f-92359f09d1d1',36310,36311,10308,0,35819,36309),('0d4323bb-05c2-44b4-b924-a454a5f17f77',36317,36318,10308,0,35833,36316),('5c5d32ce-de40-4f78-a696-c4417fa11f80',36324,36325,10308,0,35845,36323),('687c9137-9123-4d59-99d1-ddc7334ed12b',36346,36347,10308,0,35811,36345),('97e7645e-ef99-450e-956c-2864452022a8',36353,36354,10308,0,35819,36352),('7b8b6556-cb21-432d-8cb1-f4f9cd54708f',36360,36361,10308,0,35833,36359),('6ff21e32-ce5f-4f99-9843-bb857c28b1fc',36367,36368,10308,0,35845,36366),('ab6bdb09-fa0d-4299-8f88-5598ee6f0dab',36391,36392,10308,0,36383,36384);
/*!40000 ALTER TABLE `dlfileentrymetadata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlfileentrytype`
--

DROP TABLE IF EXISTS `dlfileentrytype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlfileentrytype` (
  `uuid_` varchar(75) DEFAULT NULL,
  `fileEntryTypeId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`fileEntryTypeId`),
  UNIQUE KEY `IX_E9B6A85B` (`groupId`,`name`),
  UNIQUE KEY `IX_1399D844` (`uuid_`,`groupId`),
  KEY `IX_4501FD9C` (`groupId`),
  KEY `IX_90724726` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlfileentrytype`
--

LOCK TABLES `dlfileentrytype` WRITE;
/*!40000 ALTER TABLE `dlfileentrytype` DISABLE KEYS */;
INSERT INTO `dlfileentrytype` VALUES ('613ebf47-6266-4411-8c84-de035aa5c8d9',0,0,0,0,'','2012-08-14 02:47:47','2012-08-14 02:47:47','Basic Document',''),('faa90c6f-2abd-462f-b971-a3a0b2ac3076',10300,10192,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58','Contract','Legal Contracts'),('916dc699-fc51-4891-b754-4e948b8a1afc',10302,10192,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58','Marketing Banner','Marketing Banner'),('3c7e53d1-d185-446f-887a-5358c9194861',10304,10192,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58','Online Training','Online Training'),('006fab2e-435e-4e33-9e8b-c9d5b47971f3',10306,10192,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58','Sales Presentation','Sales Presentation');
/*!40000 ALTER TABLE `dlfileentrytype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlfileentrytypes_ddmstructures`
--

DROP TABLE IF EXISTS `dlfileentrytypes_ddmstructures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlfileentrytypes_ddmstructures` (
  `fileEntryTypeId` bigint(20) NOT NULL,
  `structureId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileEntryTypeId`,`structureId`),
  KEY `IX_8373EC7C` (`fileEntryTypeId`),
  KEY `IX_F147CF3F` (`structureId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlfileentrytypes_ddmstructures`
--

LOCK TABLES `dlfileentrytypes_ddmstructures` WRITE;
/*!40000 ALTER TABLE `dlfileentrytypes_ddmstructures` DISABLE KEYS */;
INSERT INTO `dlfileentrytypes_ddmstructures` VALUES (10300,10301),(10302,10298),(10302,10303),(10304,10297),(10304,10305),(10306,10299),(10306,10307);
/*!40000 ALTER TABLE `dlfileentrytypes_ddmstructures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlfileentrytypes_dlfolders`
--

DROP TABLE IF EXISTS `dlfileentrytypes_dlfolders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlfileentrytypes_dlfolders` (
  `fileEntryTypeId` bigint(20) NOT NULL,
  `folderId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileEntryTypeId`,`folderId`),
  KEY `IX_5BB6AD6C` (`fileEntryTypeId`),
  KEY `IX_6E00A2EC` (`folderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlfileentrytypes_dlfolders`
--

LOCK TABLES `dlfileentrytypes_dlfolders` WRITE;
/*!40000 ALTER TABLE `dlfileentrytypes_dlfolders` DISABLE KEYS */;
/*!40000 ALTER TABLE `dlfileentrytypes_dlfolders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlfilerank`
--

DROP TABLE IF EXISTS `dlfilerank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlfilerank` (
  `fileRankId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `fileEntryId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`fileRankId`),
  UNIQUE KEY `IX_38F0315` (`companyId`,`userId`,`fileEntryId`),
  KEY `IX_A65A1F8B` (`fileEntryId`),
  KEY `IX_BAFB116E` (`groupId`,`userId`),
  KEY `IX_EED06670` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlfilerank`
--

LOCK TABLES `dlfilerank` WRITE;
/*!40000 ALTER TABLE `dlfilerank` DISABLE KEYS */;
INSERT INTO `dlfilerank` VALUES (10617,10180,10154,10158,'2012-08-14 02:48:15',10523),(36396,10180,10154,10196,'2012-12-01 05:45:00',36383);
/*!40000 ALTER TABLE `dlfilerank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlfileshortcut`
--

DROP TABLE IF EXISTS `dlfileshortcut`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlfileshortcut` (
  `uuid_` varchar(75) DEFAULT NULL,
  `fileShortcutId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `folderId` bigint(20) DEFAULT NULL,
  `toFileEntryId` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`fileShortcutId`),
  UNIQUE KEY `IX_FDB4A946` (`uuid_`,`groupId`),
  KEY `IX_B0051937` (`groupId`,`folderId`),
  KEY `IX_ECCE311D` (`groupId`,`folderId`,`status`),
  KEY `IX_4B7247F6` (`toFileEntryId`),
  KEY `IX_4831EBE4` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlfileshortcut`
--

LOCK TABLES `dlfileshortcut` WRITE;
/*!40000 ALTER TABLE `dlfileshortcut` DISABLE KEYS */;
/*!40000 ALTER TABLE `dlfileshortcut` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlfileversion`
--

DROP TABLE IF EXISTS `dlfileversion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlfileversion` (
  `uuid_` varchar(75) DEFAULT NULL,
  `fileVersionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `folderId` bigint(20) DEFAULT NULL,
  `fileEntryId` bigint(20) DEFAULT NULL,
  `extension` varchar(75) DEFAULT NULL,
  `mimeType` varchar(75) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` longtext,
  `changeLog` varchar(75) DEFAULT NULL,
  `extraSettings` longtext,
  `fileEntryTypeId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `size_` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`fileVersionId`),
  UNIQUE KEY `IX_E2815081` (`fileEntryId`,`version`),
  UNIQUE KEY `IX_C99B2650` (`uuid_`,`groupId`),
  KEY `IX_C68DC967` (`fileEntryId`),
  KEY `IX_D47BB14D` (`fileEntryId`,`status`),
  KEY `IX_DFD809D3` (`groupId`,`folderId`,`status`),
  KEY `IX_9BE769ED` (`groupId`,`folderId`,`title`,`version`),
  KEY `IX_4BFABB9A` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlfileversion`
--

LOCK TABLES `dlfileversion` WRITE;
/*!40000 ALTER TABLE `dlfileversion` DISABLE KEYS */;
INSERT INTO `dlfileversion` VALUES ('71161591-6dd8-4af2-b1c8-25c69dffc3ec',10412,10180,10154,10158,'','2012-08-14 02:48:00','2012-08-14 02:48:00',10180,0,10411,'jpg','image/jpeg','welcome_bg_3.jpg','','','',0,'1.0',65684,0,10158,'','2012-08-14 02:48:00'),('e72c22ed-4ec2-4c5d-b333-35b531dee92d',10420,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,10419,'jpg','image/jpeg','welcome_bg_11.jpg','','','',0,'1.0',43583,0,10158,'','2012-08-14 02:48:01'),('57ae17d3-6fa2-4f8f-adf4-80f56af88f03',10429,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,10428,'jpg','image/jpeg','welcome_bg_12.jpg','','','',0,'1.0',46446,0,10158,'','2012-08-14 02:48:01'),('2f4c6c89-0178-4fd5-8b0d-fd0510c6481a',10437,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,10436,'png','image/png','welcome_bg_10.png','','','',0,'1.0',27139,0,10158,'','2012-08-14 02:48:01'),('de6a4f96-73fb-450e-8cb3-490766137b4c',10448,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,10447,'jpg','image/jpeg','welcome_bg_2.jpg','','','',0,'1.0',72911,0,10158,'','2012-08-14 02:48:01'),('e66fe0e4-effd-488b-88dc-f6dbe136a5c0',10456,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,10455,'jpg','image/jpeg','welcome_bg_9.jpg','','','',0,'1.0',50215,0,10158,'','2012-08-14 02:48:01'),('70bdd27a-177b-4b73-a8e3-bb252d53d7aa',10468,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,10467,'jpg','image/jpeg','welcome_bg_4.jpg','','','',0,'1.0',62483,0,10158,'','2012-08-14 02:48:01'),('4eae457c-738a-455a-9b8c-ab4fc264a7a2',10480,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,10479,'jpg','image/jpeg','welcome_bg_6.jpg','','','',0,'1.0',45456,0,10158,'','2012-08-14 02:48:01'),('e25a6497-7808-42ec-b6b0-0907bd60b0a9',10491,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10180,0,10489,'jpg','image/jpeg','welcome_bg_7.jpg','','','',0,'1.0',68705,0,10158,'','2012-08-14 02:48:01'),('dcbf6ee4-3e4d-4f13-ba32-086400a7d21e',10500,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10180,0,10499,'jpg','image/jpeg','welcome_bg_5.jpg','','','',0,'1.0',40022,0,10158,'','2012-08-14 02:48:02'),('1f70ba46-0afe-41f0-b3c4-d42d0161922f',10512,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10180,0,10511,'jpg','image/jpeg','welcome_bg_13.jpg','','','',0,'1.0',33632,0,10158,'','2012-08-14 02:48:02'),('33692878-7842-48b1-b9f1-533501e72ce5',10524,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10180,0,10523,'jpg','image/jpeg','welcome_bg_1.jpg','','','',0,'1.0',54277,0,10158,'','2012-08-14 02:48:02'),('590416f2-4ed0-4b09-8773-991cfc019e3c',10536,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10180,0,10535,'jpg','image/jpeg','welcome_bg_8.jpg','','','',0,'1.0',43664,0,10158,'','2012-08-14 02:48:02'),('a7c62452-52db-47fc-a9e6-115e31dfaca2',11102,10180,10154,10196,'Test Test','2012-08-17 16:18:07','2012-08-17 16:18:07',10180,0,11101,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.0',97209,0,10196,'Test Test','2012-08-17 16:18:07'),('71d4dee0-e604-426c-b89a-7ab6815eb6cc',11110,10180,10154,10196,'Test Test','2012-08-17 16:18:08','2012-08-17 16:18:08',10180,0,11109,'jpg','image/jpeg','PO','PO','','',0,'1.0',51114,0,10196,'Test Test','2012-08-17 16:18:08'),('3d01481e-4365-4752-9252-d3b7e97732e3',32071,32062,10154,10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30',32062,0,32070,'png','image/png','pen.png','','','',0,'1.0',50675,0,10158,'','2012-11-14 20:25:30'),('d1723700-dcd5-4c49-a817-6152a71cb4b3',32079,32062,10154,10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30',32062,0,32078,'jpg','image/jpeg','carousel_item3.jpg','','','',0,'1.0',41464,0,10158,'','2012-11-14 20:25:30'),('fa50ce4b-9329-4b95-a2ad-0a3b126d151e',32091,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,32090,'png','image/png','people.png','','','',0,'1.0',379934,0,10158,'','2012-11-14 20:25:31'),('1e304b48-ea5e-4222-b50e-5843303b4d16',32103,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,32102,'jpg','image/jpeg','carousel_item1.jpg','','','',0,'1.0',59456,0,10158,'','2012-11-14 20:25:31'),('9d0e334c-511c-40c8-afde-dc3473d53dd9',32115,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,32114,'png','image/png','facebook.png','','','',0,'1.0',1589,0,10158,'','2012-11-14 20:25:31'),('b9ce46bb-52be-45f7-90cf-8e36aba6a1ca',32127,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,32126,'png','image/png','icon_gears.png','','','',0,'1.0',2464,0,10158,'','2012-11-14 20:25:31'),('199c118b-fbee-44e4-a9f4-14e4ef1cf800',32139,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,32138,'png','image/png','mouse.png','','','',0,'1.0',55712,0,10158,'','2012-11-14 20:25:31'),('251f7649-484b-4224-b70c-ebe6fbe969ca',32151,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,32150,'png','image/png','icon_phone.png','','','',0,'1.0',1581,0,10158,'','2012-11-14 20:25:31'),('077f30b9-a90b-4491-b401-1e48d9d9e8a3',32163,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,32162,'jpg','image/jpeg','carousel_item2.jpg','','','',0,'1.0',43321,0,10158,'','2012-11-14 20:25:31'),('ce23c8cc-bfee-4d13-8747-d7215821c9c3',32175,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',32062,0,32174,'png','image/png','twitter.png','','','',0,'1.0',1788,0,10158,'','2012-11-14 20:25:31'),('3e34281a-a581-4712-90bd-65c2515c6a9a',32187,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,32186,'png','image/png','paperpen.png','','','',0,'1.0',78785,0,10158,'','2012-11-14 20:25:32'),('40196372-c52c-4208-8cb8-d7b0c752845f',32199,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,32198,'png','image/png','network.png','','','',0,'1.0',87442,0,10158,'','2012-11-14 20:25:32'),('b4bfc3f1-0d5e-475b-ad3b-0beb539c8b66',32211,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,32210,'png','image/png','rss.png','','','',0,'1.0',2164,0,10158,'','2012-11-14 20:25:32'),('6188b7b9-d0f5-4fb6-ad9a-e79990120793',32223,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,32222,'png','image/png','icon_beaker.png','','','',0,'1.0',1427,0,10158,'','2012-11-14 20:25:32'),('91bde8e5-7c4e-45a2-ba29-7281b1ee35b9',32235,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,32234,'png','image/png','linkedin.png','','','',0,'1.0',1774,0,10158,'','2012-11-14 20:25:32'),('bae3a753-aa80-4ccc-acad-c735bd863e07',32247,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',32062,0,32246,'png','image/png','icon_network.png','','','',0,'1.0',2348,0,10158,'','2012-11-14 20:25:32'),('134aca69-4563-4524-9e6c-cf916709917f',35812,10180,10154,10196,'Test Test','2012-11-30 12:54:03','2012-11-30 12:54:03',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.0',97209,0,10196,'Test Test','2012-11-30 12:54:03'),('bbef7222-4cd7-45a2-93f8-81827de702f7',35820,10180,10154,10196,'Test Test','2012-11-30 12:54:04','2012-11-30 12:54:04',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.0',51114,0,10196,'Test Test','2012-11-30 12:54:04'),('fa19348e-7cdc-4e18-ba20-462b5f6a5c6c',35834,10180,10154,10196,'Test Test','2012-11-30 12:54:04','2012-11-30 12:54:04',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.0',97209,0,10196,'Test Test','2012-11-30 12:54:04'),('5e7ace37-39d9-4e71-92bc-08ad22b17a90',35846,10180,10154,10196,'Test Test','2012-11-30 12:54:05','2012-11-30 12:54:05',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.0',51114,0,10196,'Test Test','2012-11-30 12:54:05'),('7ca2c2b2-55e3-47c3-9f8f-e72c92d40194',35902,10180,10154,10196,'Test Test','2012-11-30 17:01:39','2012-11-30 17:01:40',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.1',97286,0,10196,'Test Test','2012-11-30 17:01:40'),('25b71be0-39af-4e51-9a7c-e4c80d777d64',35909,10180,10154,10196,'Test Test','2012-11-30 17:01:41','2012-11-30 17:01:41',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.1',51114,0,10196,'Test Test','2012-11-30 17:01:41'),('7cd54e4a-cdd1-432c-ab6f-87dd95b65699',35916,10180,10154,10196,'Test Test','2012-11-30 17:01:42','2012-11-30 17:01:42',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.1',97286,0,10196,'Test Test','2012-11-30 17:01:42'),('22f575f7-8eee-4b68-a0d1-072112a1fc2a',35923,10180,10154,10196,'Test Test','2012-11-30 17:01:42','2012-11-30 17:01:42',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.1',51114,0,10196,'Test Test','2012-11-30 17:01:42'),('69735de4-d967-413c-a5ea-d84bfcd4caec',35930,10180,10154,10196,'Test Test','2012-11-30 17:15:58','2012-11-30 17:15:58',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.2',97286,0,10196,'Test Test','2012-11-30 17:15:58'),('62d146b4-9b91-4383-b032-3960f3a7c907',35937,10180,10154,10196,'Test Test','2012-11-30 17:15:58','2012-11-30 17:15:58',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.2',51114,0,10196,'Test Test','2012-11-30 17:15:58'),('0af0e7f3-33f6-4574-86ac-5612cfb2c799',35944,10180,10154,10196,'Test Test','2012-11-30 17:15:58','2012-11-30 17:15:58',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.2',97286,0,10196,'Test Test','2012-11-30 17:15:58'),('5bcd07b3-41ef-4dac-91c9-d0fef03379dd',35951,10180,10154,10196,'Test Test','2012-11-30 17:15:58','2012-11-30 17:15:58',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.2',51114,0,10196,'Test Test','2012-11-30 17:15:58'),('88d03815-0250-402e-b17a-b568cb44257d',35958,10180,10154,10196,'Test Test','2012-11-30 17:20:38','2012-11-30 17:20:38',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.3',97286,0,10196,'Test Test','2012-11-30 17:20:38'),('19459fff-95c2-4d1f-bb57-b2b8afa3ccff',35965,10180,10154,10196,'Test Test','2012-11-30 17:20:38','2012-11-30 17:20:38',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.3',51114,0,10196,'Test Test','2012-11-30 17:20:38'),('078f4684-d87f-41fb-930d-e84272ce527c',35972,10180,10154,10196,'Test Test','2012-11-30 17:20:39','2012-11-30 17:20:39',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.3',97286,0,10196,'Test Test','2012-11-30 17:20:39'),('65994ef4-e924-4e2f-98bc-eb3d70d12918',35979,10180,10154,10196,'Test Test','2012-11-30 17:20:39','2012-11-30 17:20:39',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.3',51114,0,10196,'Test Test','2012-11-30 17:20:39'),('c39cb35f-4e8b-4212-8ab7-c26ab6bd25a7',35986,10180,10154,10196,'Test Test','2012-11-30 17:40:01','2012-11-30 17:40:01',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.4',97286,0,10196,'Test Test','2012-11-30 17:40:01'),('752013b4-193e-490a-a1b9-2421bd8b95db',35993,10180,10154,10196,'Test Test','2012-11-30 17:40:01','2012-11-30 17:40:01',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.4',51114,0,10196,'Test Test','2012-11-30 17:40:01'),('25b4fb4b-6255-452e-a4a3-3000239a3591',36000,10180,10154,10196,'Test Test','2012-11-30 17:40:01','2012-11-30 17:40:01',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.4',97286,0,10196,'Test Test','2012-11-30 17:40:01'),('af8bf96c-aa0e-4d6c-a50f-9f5d8baeac94',36007,10180,10154,10196,'Test Test','2012-11-30 17:40:01','2012-11-30 17:40:01',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.4',51114,0,10196,'Test Test','2012-11-30 17:40:01'),('d562ccad-4b75-41c3-b1b7-f9ff70e75862',36014,10180,10154,10196,'Test Test','2012-11-30 18:22:14','2012-11-30 18:22:14',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.5',97286,0,10196,'Test Test','2012-11-30 18:22:14'),('e593df70-9cff-4dc8-94bb-6707af664a4e',36021,10180,10154,10196,'Test Test','2012-11-30 18:22:14','2012-11-30 18:22:14',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.5',51114,0,10196,'Test Test','2012-11-30 18:22:14'),('e7a8021a-9ea9-4c03-9c8c-358a533bba30',36028,10180,10154,10196,'Test Test','2012-11-30 18:22:14','2012-11-30 18:22:15',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.5',97286,0,10196,'Test Test','2012-11-30 18:22:15'),('1c22c663-966c-48c3-b95c-5f737af909ae',36035,10180,10154,10196,'Test Test','2012-11-30 18:22:15','2012-11-30 18:22:15',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.5',51114,0,10196,'Test Test','2012-11-30 18:22:15'),('8f1c6dae-3de3-4ad4-9813-4897dec83264',36042,10180,10154,10196,'Test Test','2012-11-30 18:28:08','2012-11-30 18:28:08',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.6',97286,0,10196,'Test Test','2012-11-30 18:28:08'),('0d613744-dbdf-4199-8752-87b3c37e7dc8',36049,10180,10154,10196,'Test Test','2012-11-30 18:28:09','2012-11-30 18:28:09',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.6',51114,0,10196,'Test Test','2012-11-30 18:28:09'),('6cda5e99-1fa5-417a-a919-feac16ca8a86',36056,10180,10154,10196,'Test Test','2012-11-30 18:28:09','2012-11-30 18:28:09',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.6',97286,0,10196,'Test Test','2012-11-30 18:28:09'),('0d485104-846e-44d0-8c80-47b01537198d',36063,10180,10154,10196,'Test Test','2012-11-30 18:28:09','2012-11-30 18:28:09',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.6',51114,0,10196,'Test Test','2012-11-30 18:28:09'),('2c101739-3757-4ddd-bc4a-c701f201e0fa',36070,10180,10154,10196,'Test Test','2012-11-30 18:32:43','2012-11-30 18:32:43',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.7',97286,0,10196,'Test Test','2012-11-30 18:32:43'),('63b3c8bf-84f6-487c-9905-9981bbaa88c2',36077,10180,10154,10196,'Test Test','2012-11-30 18:32:43','2012-11-30 18:32:43',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.7',51114,0,10196,'Test Test','2012-11-30 18:32:43'),('2c7b317d-28a3-4eea-a079-fdd70dde252f',36084,10180,10154,10196,'Test Test','2012-11-30 18:32:43','2012-11-30 18:32:43',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.7',97286,0,10196,'Test Test','2012-11-30 18:32:43'),('14f38620-6f74-42a5-89c0-9680fdd7768f',36091,10180,10154,10196,'Test Test','2012-11-30 18:32:44','2012-11-30 18:32:44',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.7',51114,0,10196,'Test Test','2012-11-30 18:32:44'),('129f5e49-af88-47f6-8757-1adb8a7f4b17',36098,10180,10154,10196,'Test Test','2012-11-30 18:46:17','2012-11-30 18:46:17',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.8',97286,0,10196,'Test Test','2012-11-30 18:46:17'),('0e45eecf-eae8-4c48-94a4-9c82cc63f300',36105,10180,10154,10196,'Test Test','2012-11-30 18:46:17','2012-11-30 18:46:17',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.8',51114,0,10196,'Test Test','2012-11-30 18:46:17'),('ea3c32c5-b4c5-476d-91f7-ccb22014e6c7',36112,10180,10154,10196,'Test Test','2012-11-30 18:46:17','2012-11-30 18:46:17',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.8',97286,0,10196,'Test Test','2012-11-30 18:46:17'),('b3bd7ee1-10be-4b4b-abbc-5c8f20dad42b',36119,10180,10154,10196,'Test Test','2012-11-30 18:46:17','2012-11-30 18:46:17',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.8',51114,0,10196,'Test Test','2012-11-30 18:46:17'),('1614c04d-f3d1-450a-9469-2851b6ff6bcb',36136,10180,10154,10196,'Test Test','2012-11-30 19:59:05','2012-11-30 19:59:05',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.9',97286,0,10196,'Test Test','2012-11-30 19:59:05'),('1a30f4fd-4600-45d4-a3f6-2f0e40644984',36143,10180,10154,10196,'Test Test','2012-11-30 19:59:05','2012-11-30 19:59:05',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.9',51114,0,10196,'Test Test','2012-11-30 19:59:06'),('09d6da45-1949-4b97-90c4-5896c82335dd',36150,10180,10154,10196,'Test Test','2012-11-30 19:59:06','2012-11-30 19:59:06',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.9',97286,0,10196,'Test Test','2012-11-30 19:59:06'),('65938b27-2817-497a-8c1e-aba382a2c218',36157,10180,10154,10196,'Test Test','2012-11-30 19:59:06','2012-11-30 19:59:06',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.9',51114,0,10196,'Test Test','2012-11-30 19:59:06'),('cb4422a1-52ac-4937-87fd-1a14d309c439',36164,10180,10154,10196,'Test Test','2012-11-30 21:29:47','2012-11-30 21:29:47',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.10',97286,0,10196,'Test Test','2012-11-30 21:29:47'),('effb6c04-5dd4-45d4-b18d-7c21b66fe877',36171,10180,10154,10196,'Test Test','2012-11-30 21:29:47','2012-11-30 21:29:47',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.10',51114,0,10196,'Test Test','2012-11-30 21:29:47'),('5190de06-46bd-4ca5-94e6-f9576a08040e',36178,10180,10154,10196,'Test Test','2012-11-30 21:29:47','2012-11-30 21:29:47',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.10',97286,0,10196,'Test Test','2012-11-30 21:29:47'),('30016afc-989d-4340-8335-91d58ad411ad',36185,10180,10154,10196,'Test Test','2012-11-30 21:29:47','2012-11-30 21:29:47',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.10',51114,0,10196,'Test Test','2012-11-30 21:29:47'),('f0cd0fdd-8e6c-471a-9469-be80f79eedf6',36302,10180,10154,10196,'Test Test','2012-11-30 23:03:21','2012-11-30 23:03:21',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.11',97286,0,10196,'Test Test','2012-11-30 23:03:21'),('f69fda65-0e15-4196-ab28-08bd345cb2d2',36309,10180,10154,10196,'Test Test','2012-11-30 23:03:23','2012-11-30 23:03:23',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.11',51114,0,10196,'Test Test','2012-11-30 23:03:23'),('d2158c97-ba2b-46a2-8c81-c1a6154ce333',36316,10180,10154,10196,'Test Test','2012-11-30 23:03:24','2012-11-30 23:03:24',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.11',97286,0,10196,'Test Test','2012-11-30 23:03:24'),('75e87961-c7ad-4a6e-afff-c36ad3399580',36323,10180,10154,10196,'Test Test','2012-11-30 23:03:24','2012-11-30 23:03:24',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.11',51114,0,10196,'Test Test','2012-11-30 23:03:24'),('60a2f970-0e3f-4286-95da-e7cb6d47f7b2',36345,10180,10154,10196,'Test Test','2012-12-01 02:46:36','2012-12-01 02:46:37',10180,35809,35811,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.12',97286,0,10196,'Test Test','2012-12-01 02:46:37'),('abb0c9c2-573a-4312-89fa-976e338cde47',36352,10180,10154,10196,'Test Test','2012-12-01 02:46:37','2012-12-01 02:46:37',10180,35809,35819,'jpg','image/jpeg','PO','PO','','',0,'1.12',51114,0,10196,'Test Test','2012-12-01 02:46:37'),('c1abe518-bafd-4816-a997-c4519337abb0',36359,10180,10154,10196,'Test Test','2012-12-01 02:46:38','2012-12-01 02:46:38',10180,35831,35833,'pdf','application/pdf','Bill Of Laden','Bill Of Laden','','',0,'1.12',97286,0,10196,'Test Test','2012-12-01 02:46:38'),('84b95c7d-c196-4ea4-8796-692c15bd9534',36366,10180,10154,10196,'Test Test','2012-12-01 02:46:38','2012-12-01 02:46:38',10180,35831,35845,'jpg','image/jpeg','PO','PO','','',0,'1.12',51114,0,10196,'Test Test','2012-12-01 02:46:38'),('edbb8046-d320-4c60-b7e4-df6d6f5f1fe5',36384,10180,10154,10196,'Test Test','2012-12-01 05:26:51','2012-12-01 05:26:51',10180,36381,36383,'','application/pdf','Label RDFLT000001-01L1_1','Label RDFLT000001-01L1_1','','',0,'1.0',4383,0,10196,'Test Test','2012-12-01 05:26:51');
/*!40000 ALTER TABLE `dlfileversion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlfolder`
--

DROP TABLE IF EXISTS `dlfolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlfolder` (
  `uuid_` varchar(75) DEFAULT NULL,
  `folderId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `mountPoint` tinyint(4) DEFAULT NULL,
  `parentFolderId` bigint(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` longtext,
  `lastPostDate` datetime DEFAULT NULL,
  `defaultFileEntryTypeId` bigint(20) DEFAULT NULL,
  `overrideFileEntryTypes` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`folderId`),
  UNIQUE KEY `IX_902FD874` (`groupId`,`parentFolderId`,`name`),
  UNIQUE KEY `IX_3CC1DED2` (`uuid_`,`groupId`),
  KEY `IX_A74DB14C` (`companyId`),
  KEY `IX_F2EA1ACE` (`groupId`),
  KEY `IX_49C37475` (`groupId`,`parentFolderId`),
  KEY `IX_2A048EA0` (`groupId`,`parentFolderId`,`mountPoint`),
  KEY `IX_51556082` (`parentFolderId`,`name`),
  KEY `IX_EE29C715` (`repositoryId`),
  KEY `IX_CBC408D8` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlfolder`
--

LOCK TABLES `dlfolder` WRITE;
/*!40000 ALTER TABLE `dlfolder` DISABLE KEYS */;
INSERT INTO `dlfolder` VALUES ('4b0f9d95-05e6-4d4d-be85-adc7cccee2a9',10644,10180,10154,10196,'','2012-08-14 02:53:51','2012-08-14 02:53:51',10180,0,0,'ConXLogistics','','2012-11-30 18:54:47',0,0),('3555f820-d581-45ab-b5d8-3aa8acfd8b62',35807,10180,10154,10196,'','2012-11-30 12:54:03','2012-11-30 12:54:03',10180,0,10644,'Receives','Receives','2012-11-30 12:54:04',0,0),('74cd79d4-6db3-4c60-8a3e-5d97f5148f0f',35809,10180,10154,10196,'','2012-11-30 12:54:03','2012-11-30 12:54:03',10180,0,35807,'Receive-1','Receive-1','2012-11-30 23:03:23',0,0),('ecb482ce-7bc1-4ab7-aaee-0745119ac3f9',35831,10180,10154,10196,'','2012-11-30 12:54:04','2012-11-30 12:54:04',10180,0,35807,'Receive-2','Receive-2','2012-11-30 23:03:24',0,0),('134c0de5-2112-4513-a13f-c60d9cb079e9',36125,10180,10154,10196,'','2012-11-30 18:51:59','2012-11-30 18:51:59',10180,0,10644,'Arrivals','Arrivals','2012-11-30 18:54:12',0,0),('4cfe02bf-ceec-4432-b720-fbed061f8731',36127,10180,10154,10196,'','2012-11-30 18:51:59','2012-11-30 18:51:59',10180,0,36125,'Arrival-1','Arrival-1',NULL,0,0),('33f14646-2988-4ba5-898f-28e8866160da',36129,10180,10154,10196,'','2012-11-30 18:54:12','2012-11-30 18:54:12',10180,0,36125,'Arrival-2','Arrival-2',NULL,0,0),('98addfbf-7287-4281-9a62-d99fee123a91',36131,10180,10154,10196,'','2012-11-30 18:54:47','2012-11-30 18:54:47',10180,0,10644,'StockItems','StockItems','2012-12-01 05:26:51',0,0),('dad8093f-e5d8-4fab-94f5-b1acaafd2dd0',36381,10180,10154,10196,'','2012-12-01 05:26:51','2012-12-01 05:26:51',10180,0,36131,'StockItem-2','StockItem-2','2012-12-01 05:26:51',0,0);
/*!40000 ALTER TABLE `dlfolder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dlsync`
--

DROP TABLE IF EXISTS `dlsync`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dlsync` (
  `syncId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `fileId` bigint(20) DEFAULT NULL,
  `fileUuid` varchar(75) DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `parentFolderId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` longtext,
  `event` varchar(75) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`syncId`),
  UNIQUE KEY `IX_F9821AB4` (`fileId`),
  KEY `IX_B53EC783` (`companyId`,`modifiedDate`,`repositoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dlsync`
--

LOCK TABLES `dlsync` WRITE;
/*!40000 ALTER TABLE `dlsync` DISABLE KEYS */;
INSERT INTO `dlsync` VALUES (10418,10154,'2012-08-14 02:48:00','2012-08-14 02:48:00',10411,'3ed17b37-3380-4d30-a5fc-22a20625a76f',10180,0,'welcome_bg_3.jpg','','add','file','1.0'),(10427,10154,'2012-08-14 02:48:01','2012-08-14 02:48:01',10419,'ebd9c0bf-a7c3-4638-997f-062cdb9575b0',10180,0,'welcome_bg_11.jpg','','add','file','1.0'),(10435,10154,'2012-08-14 02:48:01','2012-08-14 02:48:01',10428,'8b76a308-57ed-442e-999e-df1a78f1045f',10180,0,'welcome_bg_12.jpg','','add','file','1.0'),(10443,10154,'2012-08-14 02:48:01','2012-08-14 02:48:01',10436,'47a2c229-e287-476a-8f5e-618ac56e857f',10180,0,'welcome_bg_10.png','','add','file','1.0'),(10454,10154,'2012-08-14 02:48:01','2012-08-14 02:48:01',10447,'85baf84c-5151-4d30-9cda-a1346fbdbf4d',10180,0,'welcome_bg_2.jpg','','add','file','1.0'),(10463,10154,'2012-08-14 02:48:01','2012-08-14 02:48:01',10455,'ce45d760-a26a-452c-976f-c2b0022b7fc4',10180,0,'welcome_bg_9.jpg','','add','file','1.0'),(10474,10154,'2012-08-14 02:48:01','2012-08-14 02:48:01',10467,'c1cd182d-ba30-4961-ae6b-3a2daae746e3',10180,0,'welcome_bg_4.jpg','','add','file','1.0'),(10486,10154,'2012-08-14 02:48:01','2012-08-14 02:48:01',10479,'039e457b-ce55-406a-901f-7c1eb59d38ca',10180,0,'welcome_bg_6.jpg','','add','file','1.0'),(10498,10154,'2012-08-14 02:48:01','2012-08-14 02:48:01',10489,'0cc635cd-6b1a-4c4a-9c85-52f56c93a0fb',10180,0,'welcome_bg_7.jpg','','add','file','1.0'),(10510,10154,'2012-08-14 02:48:02','2012-08-14 02:48:02',10499,'33e84fce-e4be-4e50-87e6-b209f01a77a3',10180,0,'welcome_bg_5.jpg','','add','file','1.0'),(10518,10154,'2012-08-14 02:48:02','2012-08-14 02:48:02',10511,'c3edd8cf-f02c-48c2-b0ee-2a0d5988a1b1',10180,0,'welcome_bg_13.jpg','','add','file','1.0'),(10530,10154,'2012-08-14 02:48:02','2012-08-14 02:48:02',10523,'60087992-6d2a-4f75-985d-c5e859173b21',10180,0,'welcome_bg_1.jpg','','add','file','1.0'),(10542,10154,'2012-08-14 02:48:02','2012-08-14 02:48:02',10535,'9da09bba-5fd1-4f85-bb5e-3665b5317aa2',10180,0,'welcome_bg_8.jpg','','add','file','1.0'),(10645,10154,'2012-08-14 02:53:51','2012-08-14 02:53:51',10644,'4b0f9d95-05e6-4d4d-be85-adc7cccee2a9',10180,0,'ConXLogistics','','add','folder','-1'),(10647,10154,'2012-08-14 03:55:16','2012-08-14 19:19:44',10646,'07532925-0dae-4592-8327-6a5969bec6d6',10180,10644,'Receive1','Receive1','delete','folder','-1'),(10702,10154,'2012-08-14 19:01:55','2012-08-14 19:19:30',10701,'ff2b0fdb-1b78-4cd7-bbee-d654ce7f842f',10180,10644,'Receive123','Receive123','delete','folder','-1'),(10707,10154,'2012-08-14 19:20:15','2012-08-14 19:35:37',10706,'98b2d967-2c16-412d-b241-5fbb38ba844d',10180,10644,'Receive123','Receive123','delete','folder','-1'),(10710,10154,'2012-08-14 20:09:30','2012-08-14 20:09:30',10709,'a5f5b939-3d24-430f-ab06-ddd06e1e850c',10180,10644,'Receive123','Receive123','delete','folder','-1'),(10713,10154,'2012-08-14 20:40:11','2012-08-14 20:41:41',10712,'feeefc4c-49ba-4dc1-90a9-cce26cb9f7f9',10180,10644,'Receive123','Receive123','delete','folder','-1'),(10716,10154,'2012-08-14 21:04:18','2012-08-14 21:04:18',10715,'d0dd16b6-8229-4ec4-b3b6-2e1c8b75337a',10180,10644,'Receive123','Receive123','delete','folder','-1'),(10719,10154,'2012-08-14 21:06:14','2012-08-16 15:51:53',10718,'91059e0b-5fde-413e-8d76-fe63e4bfabbb',10180,10644,'Receive123','Receive123','delete','folder','-1'),(10727,10154,'2012-08-15 11:31:34','2012-08-15 15:23:22',10720,'fcb6c577-8654-467d-b2c0-158e2e16e786',10180,10718,'BoL','BoL','delete','file','1.0'),(10809,10154,'2012-08-15 15:23:44','2012-08-15 15:27:52',10802,'5f1363db-60dc-453c-9303-0129bac7b52e',10180,10718,'BoL','BoL','delete','file','1.0'),(10822,10154,'2012-08-15 15:28:15','2012-08-16 15:51:53',10815,'097f8d24-732d-4fdb-88de-043e1801774c',10180,10718,'BoL','BoL','delete','file','1.0'),(10902,10154,'2012-08-16 00:09:59','2012-08-16 15:51:53',10901,'45b0b558-1921-4deb-a5bf-62192b77c56e',10180,10644,'Receive-1','Attachments for Record[Receive-1]','delete','folder','-1'),(11002,10154,'2012-08-16 15:01:16','2012-08-16 15:51:53',11001,'e68ff628-afd3-45d0-9700-84d2aff9f687',10180,10644,'Receive','Receive','delete','folder','-1'),(11004,10154,'2012-08-16 15:46:25','2012-08-16 15:51:53',11003,'f5646b70-1ebc-4b55-8c2c-0c5c1111a561',10180,10644,'Receives','Receives','delete','folder','-1'),(11006,10154,'2012-08-16 15:46:25','2012-08-16 15:51:53',11005,'dd2f21ea-1a81-46b3-a444-043f4beef1ad',10180,10644,'Receives-1','Receives-1','delete','folder','-1'),(11013,10154,'2012-08-16 15:52:28','2012-08-16 15:53:24',11012,'4fde4940-9b47-4de3-a939-24c60a36983f',10180,10644,'Receives','Receives','delete','folder','-1'),(11015,10154,'2012-08-16 15:52:28','2012-08-16 15:53:24',11014,'b8ab7a08-15b5-4183-98cc-cb5fbba064ae',10180,10644,'Receive-1','Receive-1','delete','folder','-1'),(11019,10154,'2012-08-16 15:53:51','2012-08-30 12:50:53',11018,'e8a591ad-22fd-45b1-a3fe-7b327b1f565d',10180,10644,'Receive123','Receive123','delete','folder','-1'),(11027,10154,'2012-08-16 15:53:51','2012-08-30 12:50:53',11020,'2fdfd3e8-5fd9-42f2-99a0-6298c0086517',10180,11018,'BoL','BoL','delete','file','1.0'),(11033,10154,'2012-08-16 15:54:26','2012-08-30 12:50:59',11032,'0ac75ed1-9f0a-4243-8e64-30dc0bb52ed6',10180,10644,'Receives','Receives','delete','folder','-1'),(11035,10154,'2012-08-16 15:54:26','2012-08-30 12:50:59',11034,'2201727c-0ca7-4d7c-b2eb-8edbd9be1aa4',10180,11032,'Receive-1','Receive-1','delete','folder','-1'),(11043,10154,'2012-08-16 16:38:48','2012-08-30 12:50:53',11036,'00cbf026-ff6f-483c-a4de-f0bd247f8691',10180,11034,'Bill Of Laden','Bill Of Laden','delete','file','1.327'),(11057,10154,'2012-08-16 16:44:36','2012-08-30 12:50:56',11050,'6ffda955-0b65-447e-944b-34b4c32cf6b7',10180,11034,'PO','PO','delete','file','1.321'),(11108,10154,'2012-08-17 16:18:07','2012-08-17 16:18:07',11101,'c69a9473-6d94-456b-9721-d1aa6d9e4822',10180,0,'Bill Of Laden','Bill Of Laden','add','file','1.0'),(11116,10154,'2012-08-17 16:18:08','2012-08-17 16:18:08',11109,'9efd576f-7f79-4394-841e-2f863e8a27d4',10180,0,'PO','PO','add','file','1.0'),(11126,10154,'2012-08-17 16:25:11','2012-08-30 12:50:59',11125,'d3b8826e-c833-4999-ae38-d4c28fb5d542',10180,11032,'Receive-2','Receive-2','delete','folder','-1'),(11134,10154,'2012-08-17 16:25:11','2012-08-30 12:50:59',11127,'0f02ed61-88f0-40dc-a1b5-6f3b2989791c',10180,11125,'Bill Of Laden','Bill Of Laden','delete','file','1.3'),(11143,10154,'2012-08-17 16:25:11','2012-08-30 12:50:59',11135,'82214bf5-c26d-47a0-a4e3-7dcd7f6dc69a',10180,11125,'PO','PO','delete','file','1.3'),(14542,10154,'2012-08-21 21:50:17','2012-08-22 15:08:48',14535,'013b8a5a-5485-4481-ab0e-5a4cbd86a391',10180,11034,'LICENSES.txt','PO','delete','file','1.0'),(14681,10154,'2012-08-22 15:09:12','2012-08-22 16:18:45',14674,'7eb389c2-804f-470a-b2ba-639b9c0d1b88',10180,11034,'LICENSES.txt','PO','delete','file','1.0'),(14792,10154,'2012-08-22 16:56:26','2012-08-22 17:13:51',14785,'226496c6-70c1-4b36-bf2f-e09c61915c22',10180,11034,'LICENSES.txt','PO','delete','file','1.0'),(14819,10154,'2012-08-22 17:19:13','2012-08-30 12:50:59',14812,'5357ffbc-1c97-47f2-bbe8-e56b1926b5a9',10180,11034,'LICENSES.txt','Bill Of Laden','delete','file','1.3'),(14866,10154,'2012-08-22 17:54:46','2012-08-30 12:50:59',14859,'1e2e367a-d8c5-40cf-8b7f-4853144d2108',10180,11034,'en_windows_7_ultimate.txt','tretertetretre','delete','file','1.5'),(14913,10154,'2012-08-22 18:14:30','2012-08-30 12:50:59',14906,'ac60cc06-b86f-4b1e-b618-c54ede8d710d',10180,11034,'en_windows_7_professional_x64_dvd_x15-65805.txt','Bill Of Laden','delete','file','1.0'),(16278,10154,'2012-08-30 12:52:30','2012-09-30 20:30:20',16277,'5eaa3891-cf61-48ab-8080-f490ad68f3f6',10180,10644,'Receives','Receives','delete','folder','-1'),(16280,10154,'2012-08-30 12:52:30','2012-09-30 20:30:17',16279,'df66bf31-a1a8-4c30-8659-b1e5a0b3a253',10180,16277,'Receive-1','Receive-1','delete','folder','-1'),(16288,10154,'2012-08-30 12:52:30','2012-09-30 20:30:12',16281,'7b30b6bd-4174-40e3-aadb-63f2cbadd8fe',10180,16279,'Bill Of Laden','Bill Of Laden','delete','file','1.226'),(16297,10154,'2012-08-30 12:52:31','2012-09-30 20:30:14',16289,'1462c6ea-6fb7-4005-94d0-5a56c5404db9',10180,16279,'PO','PO','delete','file','1.227'),(16972,10154,'2012-09-03 02:22:38','2012-09-30 20:30:12',16971,'f1793984-e43c-4e10-8f2f-1942cbb05c5a',10180,10644,'Receive123','Receive123','delete','folder','-1'),(16980,10154,'2012-09-03 02:29:19','2012-09-30 20:30:11',16973,'6da18d56-18de-4e74-b964-4996467c7128',10180,16971,'BoL','BoL','delete','file','1.0'),(18130,10154,'2012-09-10 20:09:38','2012-09-30 20:30:20',18129,'89c679ea-8613-4c23-8bd4-476b73bd7d25',10180,16277,'Receive-2','Receive-2','delete','folder','-1'),(18138,10154,'2012-09-10 20:09:38','2012-09-30 20:30:17',18131,'7ddc0968-5216-4c69-adc2-0b66009ae575',10180,18129,'Bill Of Laden','Bill Of Laden','delete','file','1.138'),(18150,10154,'2012-09-10 20:09:39','2012-09-30 20:30:18',18139,'a088abcf-2c8f-4592-ae5c-21d41601e9eb',10180,18129,'PO','PO','delete','file','1.137'),(19564,10154,'2012-09-12 18:47:21','2012-09-30 20:30:20',19557,'4bbfc5f3-cd42-45a1-9b4b-8e1b02619c36',10180,18129,'mANDISA.doc','Bill Of Laden','delete','file','1.0'),(22504,10154,'2012-09-30 20:31:36','2012-11-30 12:53:12',22503,'cd696d01-2353-4ffe-a69f-273061afdb60',10180,10644,'Receives','Receives','delete','folder','-1'),(22506,10154,'2012-09-30 20:31:36','2012-11-30 12:53:05',22505,'5005939c-3c2a-46b5-8847-5c7705d14d74',10180,22503,'Receive-1','Receive-1','delete','folder','-1'),(22514,10154,'2012-09-30 20:31:37','2012-11-30 12:52:58',22507,'7673f922-e6ea-4b5b-86a8-a25b2096701c',10180,22505,'Bill Of Laden','Bill Of Laden','delete','file','1.405'),(22522,10154,'2012-09-30 20:31:38','2012-11-30 12:53:02',22515,'3f0bbf49-ec5d-4c51-957a-77a05c10c1b5',10180,22505,'PO','PO','delete','file','1.392'),(22532,10154,'2012-09-30 20:31:40','2012-11-30 12:53:12',22531,'31f71a08-0e06-442b-91b3-167fac5dacc2',10180,22503,'Receive-2','Receive-2','delete','folder','-1'),(22540,10154,'2012-09-30 20:31:40','2012-11-30 12:53:05',22533,'27862728-8a79-46a3-a270-95fffb2aa5f3',10180,22531,'Bill Of Laden','Bill Of Laden','delete','file','1.392'),(22552,10154,'2012-09-30 20:31:41','2012-11-30 12:53:08',22545,'15799ebc-80e5-4204-b25e-cc9b1898a265',10180,22531,'PO','PO','delete','file','1.392'),(22970,10154,'2012-10-01 01:27:30','2012-11-30 12:53:12',22969,'162005b9-96d1-498a-90cd-4131b64b0c2e',10180,22503,'Receive-3','Receive-3','delete','folder','-1'),(22978,10154,'2012-10-01 01:27:30','2012-11-30 12:53:12',22971,'cc05bf83-e379-4599-8a61-b642c8176a09',10180,22969,'Bill Of Laden','Bill Of Laden','delete','file','1.1'),(22990,10154,'2012-10-01 01:27:31','2012-11-30 12:53:12',22979,'1ec95178-f698-4606-9e2a-a76a210837b7',10180,22969,'PO','PO','delete','file','1.1'),(22996,10154,'2012-10-01 01:27:31','2012-11-30 12:53:12',22995,'ba6e51f2-be49-4484-a35f-1e035bd993ac',10180,22503,'Receive-4','Receive-4','delete','folder','-1'),(23004,10154,'2012-10-01 01:27:31','2012-11-30 12:53:12',22997,'2c95497d-9dad-45c4-8eb6-81a6720e07c1',10180,22995,'Bill Of Laden','Bill Of Laden','delete','file','1.1'),(23016,10154,'2012-10-01 01:27:31','2012-11-30 12:53:12',23006,'cd432d9a-9083-47cf-a843-0b074e618b2b',10180,22995,'PO','PO','delete','file','1.1'),(23330,10154,'2012-10-01 22:00:21','2012-11-30 12:52:58',23329,'e739ea51-950b-4275-ab3f-2862476240b8',10180,10644,'Receive123','Receive123','delete','folder','-1'),(23338,10154,'2012-10-01 22:03:21','2012-11-30 12:52:58',23331,'bc6e8b0d-ee01-45b2-a0f8-84b1c95250d8',10180,23329,'BoL','BoL','delete','file','1.0'),(29922,10154,'2012-11-05 17:22:59','2012-11-30 12:52:58',29921,'7b59c706-ffdd-48cc-b235-eb36f80edfe1',10180,10644,'Arrivals','Arrivals','delete','folder','-1'),(29924,10154,'2012-11-05 17:22:59','2012-11-30 12:52:58',29923,'c3667c1d-1f42-4e07-a077-a419a765a6f5',10180,29921,'Arrival-1','Arrival-1','delete','folder','-1'),(30234,10154,'2012-11-08 18:57:21','2012-11-30 12:52:58',30233,'c886828b-9faf-487a-9bdd-e1ec632aed2c',10180,29921,'Arrival-2','Arrival-2','delete','folder','-1'),(30236,10154,'2012-11-08 19:01:32','2012-11-30 12:52:58',30235,'25bea3cb-e7de-4545-a7c4-f8e75001fc25',10180,29921,'Arrival-3','Arrival-3','delete','folder','-1'),(30238,10154,'2012-11-08 19:02:33','2012-11-30 12:52:58',30237,'32eda4d6-58be-40e7-a832-609b59549fdd',10180,29921,'Arrival-4','Arrival-4','delete','folder','-1'),(30268,10154,'2012-11-08 19:32:10','2012-11-30 12:52:58',30267,'c107ff63-830f-4e18-a1e0-6decc9238a7b',10180,29921,'Arrival-5','Arrival-5','delete','folder','-1'),(30270,10154,'2012-11-08 19:35:44','2012-11-30 12:52:58',30269,'5a5f1c70-6841-4410-bfef-67e2c5070633',10180,29921,'Arrival-6','Arrival-6','delete','folder','-1'),(30272,10154,'2012-11-08 19:49:10','2012-11-30 12:52:58',30271,'a20656f4-03d1-43fe-860c-ae6258495ce1',10180,29921,'Arrival-7','Arrival-7','delete','folder','-1'),(30274,10154,'2012-11-08 19:50:13','2012-11-30 12:52:58',30273,'1c3f2adc-f88d-4beb-bde4-3af1ea66e59f',10180,29921,'Arrival-8','Arrival-8','delete','folder','-1'),(30276,10154,'2012-11-08 19:52:03','2012-11-30 12:52:58',30275,'d11b15a7-0aa3-4e8c-ae62-a84335e48fd4',10180,29921,'Arrival-9','Arrival-9','delete','folder','-1'),(30278,10154,'2012-11-08 19:52:18','2012-11-30 12:52:58',30277,'b60cafef-00bc-41ca-86e2-a48cee452053',10180,29921,'Arrival-10','Arrival-10','delete','folder','-1'),(31092,10154,'2012-11-10 05:37:51','2012-11-30 12:53:12',31091,'da9eddb0-4028-4932-ba84-3a9a9c51b791',10180,10644,'StockItems','StockItems','delete','folder','-1'),(31094,10154,'2012-11-10 05:37:51','2012-11-30 12:53:12',31093,'43f389a4-92ac-4d51-9a3a-1773b1e450ef',10180,31091,'StockItem-1','StockItem-1','delete','folder','-1'),(31096,10154,'2012-11-10 05:40:11','2012-11-30 12:53:12',31095,'6ea3286e-da1f-4c9c-9d45-13f9ec55a27f',10180,31091,'StockItem-2','StockItem-2','delete','folder','-1'),(31098,10154,'2012-11-10 05:43:14','2012-11-30 12:53:12',31097,'e53be0f1-c297-40c0-be9f-b4cec71b6725',10180,31091,'StockItem-null','StockItem-null','delete','folder','-1'),(32077,10154,'2012-11-14 20:25:30','2012-11-14 20:25:30',32070,'1b9dbc42-d2c9-4f72-ae8e-84329ebcf4cb',32062,0,'pen.png','','add','file','1.0'),(32089,10154,'2012-11-14 20:25:30','2012-11-14 20:25:30',32078,'74353928-596b-4746-8582-db08f631e235',32062,0,'carousel_item3.jpg','','add','file','1.0'),(32101,10154,'2012-11-14 20:25:31','2012-11-14 20:25:31',32090,'0800d3a2-be09-43ac-8a57-5fc3b2a7ad64',32062,0,'people.png','','add','file','1.0'),(32112,10154,'2012-11-14 20:25:31','2012-11-14 20:25:31',32102,'263e2d7c-e799-4e62-a115-96e77a29181a',32062,0,'carousel_item1.jpg','','add','file','1.0'),(32121,10154,'2012-11-14 20:25:31','2012-11-14 20:25:31',32114,'7015eb92-9b12-4dae-9f8a-a46fd7930afc',32062,0,'facebook.png','','add','file','1.0'),(32133,10154,'2012-11-14 20:25:31','2012-11-14 20:25:31',32126,'4535eaaf-0509-414c-a62c-a0252cd232d3',32062,0,'icon_gears.png','','add','file','1.0'),(32145,10154,'2012-11-14 20:25:31','2012-11-14 20:25:31',32138,'0b857adc-5e8e-4054-aaf4-d68c7db99693',32062,0,'mouse.png','','add','file','1.0'),(32157,10154,'2012-11-14 20:25:31','2012-11-14 20:25:31',32150,'ee00f58b-ca06-415e-9ae5-bc9d17cfc2bf',32062,0,'icon_phone.png','','add','file','1.0'),(32169,10154,'2012-11-14 20:25:31','2012-11-14 20:25:31',32162,'56c6fa37-63bb-49d0-bf10-34ad3c82dd90',32062,0,'carousel_item2.jpg','','add','file','1.0'),(32181,10154,'2012-11-14 20:25:31','2012-11-14 20:25:31',32174,'570a24b1-1d06-4863-b794-a87d17a7a98b',32062,0,'twitter.png','','add','file','1.0'),(32193,10154,'2012-11-14 20:25:32','2012-11-14 20:25:32',32186,'9d97e944-6800-41e9-920a-3e0690b54287',32062,0,'paperpen.png','','add','file','1.0'),(32205,10154,'2012-11-14 20:25:32','2012-11-14 20:25:32',32198,'a77d22f5-d564-4998-ad29-b8d7b9434405',32062,0,'network.png','','add','file','1.0'),(32217,10154,'2012-11-14 20:25:32','2012-11-14 20:25:32',32210,'74bf915f-b101-459a-8cf5-aea8806567dc',32062,0,'rss.png','','add','file','1.0'),(32229,10154,'2012-11-14 20:25:32','2012-11-14 20:25:32',32222,'a37e2884-e689-4bb6-a3ac-d1f8137df566',32062,0,'icon_beaker.png','','add','file','1.0'),(32241,10154,'2012-11-14 20:25:32','2012-11-14 20:25:32',32234,'291de86e-8d38-4db7-b2b8-8493820b684c',32062,0,'linkedin.png','','add','file','1.0'),(32254,10154,'2012-11-14 20:25:32','2012-11-14 20:25:32',32246,'f7ea45e0-fc11-4ca2-9529-bb90f1486b71',32062,0,'icon_network.png','','add','file','1.0'),(32848,10154,'2012-11-15 16:40:26','2012-11-30 12:52:58',32841,'9b33a199-ad83-4ffe-8d4c-d2934c55ac4e',10180,30233,'Sandile_WifiLog_2012_ 7_22.csv','','delete','file','1.0'),(32860,10154,'2012-11-15 16:44:11','2012-11-30 12:52:58',32853,'2ef6976c-c85b-452d-aa57-f8aaed666226',10180,30235,'Sandile_WifiLog_2012_11_ 1.csv','','delete','file','1.0'),(32872,10154,'2012-11-15 16:45:24','2012-11-30 12:52:58',32865,'3fe7530c-b135-480a-a436-fa4c25a1dfbc',10180,30235,'Sandile_WifiLog_2012_10_19.csv','','delete','file','1.0'),(32884,10154,'2012-11-15 17:03:07','2012-11-30 12:52:58',32877,'2f2f3146-1afb-487d-9e88-d12ae53b7635',10180,30237,'mandiiiii.txt','','delete','file','1.0'),(32896,10154,'2012-11-15 17:10:32','2012-11-30 12:52:58',32889,'02bfd7d0-2335-4cb8-9c99-cdc73d6c6d96',10180,30267,'Sandile_WifiLog_2012_ 5_11.csv','234567890-=','delete','file','1.0'),(32908,10154,'2012-11-15 17:15:20','2012-11-30 12:52:58',32901,'beebef63-afe9-4ab0-bf41-6bf66579aa1a',10180,30269,'Sandile_WifiLog_2012_11_ 1.csv','','delete','file','1.1'),(32948,10154,'2012-11-15 17:45:51','2012-11-30 12:52:57',32941,'03408098-64ed-4e08-9267-1f697ad8da8c',10180,29923,'Sandile_WifiLog_2012_ 7_22.csv','','delete','file','1.0'),(33376,10154,'2012-11-17 23:59:01','2012-11-30 12:52:57',33369,'5624f63b-add9-4a80-b40a-733d4f0adcba',10180,29923,'Application Summary.pdf','','delete','file','1.0'),(33416,10154,'2012-11-18 00:21:09','2012-11-30 12:52:58',33409,'c7eb7c4d-e07c-4a9e-a238-7b0077911878',10180,29923,'ShowTicket.pdf','','delete','file','1.0'),(33760,10154,'2012-11-18 18:32:57','2012-11-30 12:52:58',33753,'69a13283-88a9-4d4b-9082-fa0faf23bcf3',10180,29923,'Sandile Resume 24Feb2011.pdf','','delete','file','1.0'),(35808,10154,'2012-11-30 12:54:03','2012-11-30 12:54:03',35807,'3555f820-d581-45ab-b5d8-3aa8acfd8b62',10180,10644,'Receives','Receives','add','folder','-1'),(35810,10154,'2012-11-30 12:54:03','2012-11-30 12:54:03',35809,'74cd79d4-6db3-4c60-8a3e-5d97f5148f0f',10180,35807,'Receive-1','Receive-1','add','folder','-1'),(35818,10154,'2012-11-30 12:54:03','2012-12-01 02:46:37',35811,'944f09e3-feb4-4d4f-9813-e046e8654e58',10180,35809,'Bill Of Laden','Bill Of Laden','update','file','1.12'),(35827,10154,'2012-11-30 12:54:04','2012-12-01 02:46:37',35819,'29d27da3-eef1-4d57-9c51-559a67e5bdd5',10180,35809,'PO','PO','update','file','1.12'),(35832,10154,'2012-11-30 12:54:04','2012-11-30 12:54:04',35831,'ecb482ce-7bc1-4ab7-aaee-0745119ac3f9',10180,35807,'Receive-2','Receive-2','add','folder','-1'),(35841,10154,'2012-11-30 12:54:04','2012-12-01 02:46:38',35833,'a94d6c7d-ca5d-445b-90eb-20141f27a6d1',10180,35831,'Bill Of Laden','Bill Of Laden','update','file','1.12'),(35856,10154,'2012-11-30 12:54:05','2012-12-01 02:46:38',35845,'e23171c1-8553-46bd-b2e0-78acf185c43d',10180,35831,'PO','PO','update','file','1.12'),(36126,10154,'2012-11-30 18:51:59','2012-11-30 18:51:59',36125,'134c0de5-2112-4513-a13f-c60d9cb079e9',10180,10644,'Arrivals','Arrivals','add','folder','-1'),(36128,10154,'2012-11-30 18:51:59','2012-11-30 18:51:59',36127,'4cfe02bf-ceec-4432-b720-fbed061f8731',10180,36125,'Arrival-1','Arrival-1','add','folder','-1'),(36130,10154,'2012-11-30 18:54:12','2012-11-30 18:54:12',36129,'33f14646-2988-4ba5-898f-28e8866160da',10180,36125,'Arrival-2','Arrival-2','add','folder','-1'),(36132,10154,'2012-11-30 18:54:47','2012-11-30 18:54:47',36131,'98addfbf-7287-4281-9a62-d99fee123a91',10180,10644,'StockItems','StockItems','add','folder','-1'),(36134,10154,'2012-11-30 18:54:47','2012-12-01 05:26:30',36133,'f54437dd-899c-4d45-8ae7-53701b3526ff',10180,36131,'StockItem-null','StockItem-null','delete','folder','-1'),(36198,10154,'2012-11-30 21:34:13','2012-12-01 05:26:30',36191,'68cc8822-f399-4d0d-bed2-9d4ec9a9b8c2',10180,36133,'Label RDFLT000001-01L1_0','Label RDFLT000001-01L1_0','delete','file','1.0'),(36330,10154,'2012-11-30 23:08:53','2012-12-01 05:26:30',36329,'5aa787d9-d726-456b-9a76-30172055b5af',10180,36131,'StockItem-1','StockItem-1','delete','folder','-1'),(36338,10154,'2012-11-30 23:10:23','2012-12-01 05:26:30',36331,'975638bc-3105-4583-97ed-9b99bda8b7b1',10180,36329,'Label RDFLT000001-01L1_0','Label RDFLT000001-01L1_0','delete','file','1.1'),(36382,10154,'2012-12-01 05:26:51','2012-12-01 05:26:51',36381,'dad8093f-e5d8-4fab-94f5-b1acaafd2dd0',10180,36131,'StockItem-2','StockItem-2','add','folder','-1'),(36390,10154,'2012-12-01 05:26:51','2012-12-01 05:26:51',36383,'8f864110-fa5f-4f1d-b9a0-f027119f9139',10180,36381,'Label RDFLT000001-01L1_1','Label RDFLT000001-01L1_1','add','file','1.0');
/*!40000 ALTER TABLE `dlsync` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emailaddress`
--

DROP TABLE IF EXISTS `emailaddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emailaddress` (
  `emailAddressId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `address` varchar(75) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `primary_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`emailAddressId`),
  KEY `IX_1BB072CA` (`companyId`),
  KEY `IX_49D2DEC4` (`companyId`,`classNameId`),
  KEY `IX_551A519F` (`companyId`,`classNameId`,`classPK`),
  KEY `IX_2A2CB130` (`companyId`,`classNameId`,`classPK`,`primary_`),
  KEY `IX_7B43CD8` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emailaddress`
--

LOCK TABLES `emailaddress` WRITE;
/*!40000 ALTER TABLE `emailaddress` DISABLE KEYS */;
/*!40000 ALTER TABLE `emailaddress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expandocolumn`
--

DROP TABLE IF EXISTS `expandocolumn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expandocolumn` (
  `columnId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `tableId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  `defaultData` longtext,
  `typeSettings` longtext,
  PRIMARY KEY (`columnId`),
  UNIQUE KEY `IX_FEFC8DA7` (`tableId`,`name`),
  KEY `IX_A8C0CBE8` (`tableId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expandocolumn`
--

LOCK TABLES `expandocolumn` WRITE;
/*!40000 ALTER TABLE `expandocolumn` DISABLE KEYS */;
INSERT INTO `expandocolumn` VALUES (10409,10154,10408,'client-id',15,'','');
/*!40000 ALTER TABLE `expandocolumn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expandorow`
--

DROP TABLE IF EXISTS `expandorow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expandorow` (
  `rowId_` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `tableId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`rowId_`),
  UNIQUE KEY `IX_81EFBFF5` (`tableId`,`classPK`),
  KEY `IX_D3F5D7AE` (`tableId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expandorow`
--

LOCK TABLES `expandorow` WRITE;
/*!40000 ALTER TABLE `expandorow` DISABLE KEYS */;
INSERT INTO `expandorow` VALUES (32054,10154,10408,10196);
/*!40000 ALTER TABLE `expandorow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expandotable`
--

DROP TABLE IF EXISTS `expandotable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expandotable` (
  `tableId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`tableId`),
  UNIQUE KEY `IX_37562284` (`companyId`,`classNameId`,`name`),
  KEY `IX_B5AE8A85` (`companyId`,`classNameId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expandotable`
--

LOCK TABLES `expandotable` WRITE;
/*!40000 ALTER TABLE `expandotable` DISABLE KEYS */;
INSERT INTO `expandotable` VALUES (10408,10154,10005,'MP');
/*!40000 ALTER TABLE `expandotable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expandovalue`
--

DROP TABLE IF EXISTS `expandovalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expandovalue` (
  `valueId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `tableId` bigint(20) DEFAULT NULL,
  `columnId` bigint(20) DEFAULT NULL,
  `rowId_` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `data_` longtext,
  PRIMARY KEY (`valueId`),
  UNIQUE KEY `IX_9DDD21E5` (`columnId`,`rowId_`),
  UNIQUE KEY `IX_D27B03E7` (`tableId`,`columnId`,`classPK`),
  KEY `IX_B29FEF17` (`classNameId`,`classPK`),
  KEY `IX_F7DD0987` (`columnId`),
  KEY `IX_9112A7A0` (`rowId_`),
  KEY `IX_F0566A77` (`tableId`),
  KEY `IX_1BD3F4C` (`tableId`,`classPK`),
  KEY `IX_CA9AFB7C` (`tableId`,`columnId`),
  KEY `IX_B71E92D5` (`tableId`,`rowId_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expandovalue`
--

LOCK TABLES `expandovalue` WRITE;
/*!40000 ALTER TABLE `expandovalue` DISABLE KEYS */;
INSERT INTO `expandovalue` VALUES (32055,10154,10408,10409,32054,10005,10196,'O+VN9UnfRMfdarz/tneKdJ9AgWTmGnseT+Fv4OoluzVE3tLxiPUYcEmt+ZcQUvD7');
/*!40000 ALTER TABLE `expandovalue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_`
--

DROP TABLE IF EXISTS `group_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_` (
  `groupId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `creatorUserId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `parentGroupId` bigint(20) DEFAULT NULL,
  `liveGroupId` bigint(20) DEFAULT NULL,
  `name` varchar(150) DEFAULT NULL,
  `description` longtext,
  `type_` int(11) DEFAULT NULL,
  `typeSettings` longtext,
  `friendlyURL` varchar(100) DEFAULT NULL,
  `site` tinyint(4) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`groupId`),
  UNIQUE KEY `IX_D0D5E397` (`companyId`,`classNameId`,`classPK`),
  UNIQUE KEY `IX_5DE0BE11` (`companyId`,`classNameId`,`liveGroupId`,`name`),
  UNIQUE KEY `IX_5BDDB872` (`companyId`,`friendlyURL`),
  UNIQUE KEY `IX_BBCA55B` (`companyId`,`liveGroupId`,`name`),
  UNIQUE KEY `IX_5AA68501` (`companyId`,`name`),
  KEY `IX_ABA5CEC2` (`companyId`),
  KEY `IX_16218A38` (`liveGroupId`),
  KEY `IX_7B590A7A` (`type_`,`active_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_`
--

LOCK TABLES `group_` WRITE;
/*!40000 ALTER TABLE `group_` DISABLE KEYS */;
INSERT INTO `group_` VALUES (10172,10154,10158,10001,10172,0,0,'Control Panel','',3,'','/control_panel',1,1),(10180,10154,10158,10001,10180,0,0,'Guest','',1,'mergeGuestPublicPages=false\n','/guest',1,1),(10189,10154,10158,10188,10158,0,0,'User Personal Site','',3,'','/personal_site',0,1),(10192,10154,10158,10021,10154,0,0,'10154','',0,'','/null',0,1),(10198,10154,10196,10005,10196,0,0,'10196','',0,'','/test',0,1),(10310,10154,10158,10027,10309,0,0,'10309','',0,'','/template-10309',0,1),(10320,10154,10158,10027,10319,0,0,'10319','',0,'','/template-10319',0,1),(10329,10154,10158,10027,10328,0,0,'10328','',0,'','/template-10328',0,1),(10338,10154,10158,10031,10337,0,0,'10337','',0,'','/template-10337',0,1),(10364,10154,10158,10031,10363,0,0,'10363','',0,'','/template-10363',0,1),(16564,10154,16562,10005,16562,0,0,'16562','',0,'','/conxuser',0,1),(32062,10154,10158,10031,32061,0,0,'32061','',0,'','/template-32061',0,1);
/*!40000 ALTER TABLE `group_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups_orgs`
--

DROP TABLE IF EXISTS `groups_orgs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups_orgs` (
  `groupId` bigint(20) NOT NULL,
  `organizationId` bigint(20) NOT NULL,
  PRIMARY KEY (`groupId`,`organizationId`),
  KEY `IX_75267DCA` (`groupId`),
  KEY `IX_6BBB7682` (`organizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups_orgs`
--

LOCK TABLES `groups_orgs` WRITE;
/*!40000 ALTER TABLE `groups_orgs` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups_orgs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups_permissions`
--

DROP TABLE IF EXISTS `groups_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups_permissions` (
  `groupId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  PRIMARY KEY (`groupId`,`permissionId`),
  KEY `IX_C48736B` (`groupId`),
  KEY `IX_EC97689D` (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups_permissions`
--

LOCK TABLES `groups_permissions` WRITE;
/*!40000 ALTER TABLE `groups_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups_roles`
--

DROP TABLE IF EXISTS `groups_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups_roles` (
  `groupId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`groupId`,`roleId`),
  KEY `IX_84471FD2` (`groupId`),
  KEY `IX_3103EF3D` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups_roles`
--

LOCK TABLES `groups_roles` WRITE;
/*!40000 ALTER TABLE `groups_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups_usergroups`
--

DROP TABLE IF EXISTS `groups_usergroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups_usergroups` (
  `groupId` bigint(20) NOT NULL,
  `userGroupId` bigint(20) NOT NULL,
  PRIMARY KEY (`groupId`,`userGroupId`),
  KEY `IX_31FB749A` (`groupId`),
  KEY `IX_3B69160F` (`userGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups_usergroups`
--

LOCK TABLES `groups_usergroups` WRITE;
/*!40000 ALTER TABLE `groups_usergroups` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups_usergroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image` (
  `imageId` bigint(20) NOT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `text_` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `size_` int(11) DEFAULT NULL,
  PRIMARY KEY (`imageId`),
  KEY `IX_6A925A4D` (`size_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journalarticle`
--

DROP TABLE IF EXISTS `journalarticle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journalarticle` (
  `uuid_` varchar(75) DEFAULT NULL,
  `id_` bigint(20) NOT NULL,
  `resourcePrimKey` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `articleId` varchar(75) DEFAULT NULL,
  `version` double DEFAULT NULL,
  `title` longtext,
  `urlTitle` varchar(150) DEFAULT NULL,
  `description` longtext,
  `content` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `structureId` varchar(75) DEFAULT NULL,
  `templateId` varchar(75) DEFAULT NULL,
  `layoutUuid` varchar(75) DEFAULT NULL,
  `displayDate` datetime DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `reviewDate` datetime DEFAULT NULL,
  `indexable` tinyint(4) DEFAULT NULL,
  `smallImage` tinyint(4) DEFAULT NULL,
  `smallImageId` bigint(20) DEFAULT NULL,
  `smallImageURL` longtext,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `IX_85C52EEC` (`groupId`,`articleId`,`version`),
  UNIQUE KEY `IX_3463D95B` (`uuid_`,`groupId`),
  KEY `IX_DFF98523` (`companyId`),
  KEY `IX_323DF109` (`companyId`,`status`),
  KEY `IX_3D070845` (`companyId`,`version`),
  KEY `IX_E82F322B` (`companyId`,`version`,`status`),
  KEY `IX_9356F865` (`groupId`),
  KEY `IX_68C0F69C` (`groupId`,`articleId`),
  KEY `IX_4D5CD982` (`groupId`,`articleId`,`status`),
  KEY `IX_9CE6E0FA` (`groupId`,`classNameId`,`classPK`),
  KEY `IX_A2534AC2` (`groupId`,`classNameId`,`layoutUuid`),
  KEY `IX_91E78C35` (`groupId`,`classNameId`,`structureId`),
  KEY `IX_F43B9FF2` (`groupId`,`classNameId`,`templateId`),
  KEY `IX_3C028C1E` (`groupId`,`layoutUuid`),
  KEY `IX_301D024B` (`groupId`,`status`),
  KEY `IX_2E207659` (`groupId`,`structureId`),
  KEY `IX_8DEAE14E` (`groupId`,`templateId`),
  KEY `IX_22882D02` (`groupId`,`urlTitle`),
  KEY `IX_D2D249E8` (`groupId`,`urlTitle`,`status`),
  KEY `IX_F0A26B29` (`groupId`,`version`,`status`),
  KEY `IX_33F49D16` (`resourcePrimKey`),
  KEY `IX_3E2765FC` (`resourcePrimKey`,`status`),
  KEY `IX_EF9B7028` (`smallImageId`),
  KEY `IX_8E8710D9` (`structureId`),
  KEY `IX_9106F6CE` (`templateId`),
  KEY `IX_F029602F` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journalarticle`
--

LOCK TABLES `journalarticle` WRITE;
/*!40000 ALTER TABLE `journalarticle` DISABLE KEYS */;
INSERT INTO `journalarticle` VALUES ('27806e1b-7cfc-4c3b-ad4c-4b9333649724',10547,10548,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:03',0,0,'WHO-IS-USING-LIFERAY',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Who Is Using Liferay</Title></root>','who-is-using-liferay','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<static-content language-id=\"en_US\"><![CDATA[<style type=\"text/css\">\n	.content-area.selected {\n		background: url(/documents/10180/0/welcome_bg_8.jpg/9da09bba-5fd1-4f85-bb5e-3665b5317aa2?version=1.0&t=1344912482761) 100% 0 no-repeat;\n	}\n</style>\n\n<div class=\"navigation-wrapper\">\n	<header class=\"content-head content-head-liferay-portal\">\n		<hgroup>\n			<h1>\n				Liferay helps you build feature-rich, easy-to-use web applications quickly.\n			</h1>\n\n			<hr />\n		</hgroup>\n\n		<p>\n			Here are some of our customers from around the globe:\n		</p>\n\n		<ul class=\"left\">\n			<li><span>Rolex</span></li>\n			<li><span>Bugaboo</span></li>\n			<li><span>Deluxe Corporation</span></li>\n			<li><span>Domino\'s Pizza</span></li>\n			<li><span>BASF</span></li>\n		</ul>\n\n		<ul class=\"right\">\n			<li><span>Honda</span></li>\n			<li><span>GE Capital</span></li>\n			<li><span>Sesame Street</span></li>\n			<li><span>China Mobile</span></li>\n			<li><span>York University</span></li>\n		</ul>\n	</header>\n\n	<div class=\"content-area selected\">\n		<a href=\"//www.liferay.com/users?wh=8\" id=\"marketplace\">&nbsp;</a>\n	</div>\n</div>]]></static-content>\n</root>','general','','','','2010-02-01 00:00:00',NULL,NULL,1,0,10549,'',0,10158,'','2012-08-14 02:48:03'),('5fea7aee-25b2-42d7-b7f4-69b5c0c2d6ed',10573,10574,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',0,0,'LIFERAY-BENEFITS',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Liferay Benefits</Title></root>','liferay-benefits','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element instance-id=\"XbU4Tt8d\" name=\"page-title\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[See how Liferay can change the way you do business.]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"zLvpsWs9\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"XbnjZ8Kf\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_9.jpg/ce45d760-a26a-452c-976f-c2b0022b7fc4?version=1.0&t=1344912481698]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"Jg6grt09\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/open?wh=9]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Open Source: A Better Way]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"mVzYNdMh\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"LU7ujkcC\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_10.png/47a2c229-e287-476a-8f5e-618ac56e857f?version=1.0&t=1344912481496]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"1LCJ560s\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/ready?wh=10]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Ready to Go]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"KC74M8j1\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"dhL2PHcA\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_11.jpg/ebd9c0bf-a7c3-4638-997f-062cdb9575b0?version=1.0&t=1344912481331]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"padOwYYA\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/grow?wh=11]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Ready to Grow]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"A702mdKW\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"f1M1eNh9\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_12.jpg/8b76a308-57ed-442e-999e-df1a78f1045f?version=1.0&t=1344912481419]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"0stopvdI\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/approved?wh=12]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Approved by IT]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"ICAJilfL\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"Q20qsDlX\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_13.jpg/c3edd8cf-f02c-48c2-b0ee-2a0d5988a1b1?version=1.0&t=1344912482086]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"2vjBgvkJ\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/subscription?wh=13]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Open for Business]]></dynamic-content>\n	</dynamic-element>\n</root>','general','10565','10570','','2010-02-01 00:00:00',NULL,NULL,1,0,10575,'',0,10158,'','2012-08-14 02:48:03'),('69421666-feb6-4070-8c78-e9c5ae9b6960',10585,10586,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',0,0,'WHAT-WE-DO',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">What We Do</Title></root>','what-we-do','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element instance-id=\"opu708Sy\" name=\"page-title\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[Liferay helps you build feature-rich, easy-to-use web applications quickly.]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"Dyig4q0t\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"iLGzkJA2\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_1.jpg/60087992-6d2a-4f75-985d-c5e859173b21?version=1.0&t=1344912482678]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"D6RyRV8B\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/platform?wh=1]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[A Foundation for Apps]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"hPiZkCOd\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"MhNFM5l9\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_2.jpg/85baf84c-5151-4d30-9cda-a1346fbdbf4d?version=1.0&t=1344912481594]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"cRxjgI5n\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/wem?wh=2]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Dynamic Websites]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"vpGFKJCQ\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"gh9EXOmx\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_3.jpg/3ed17b37-3380-4d30-a5fc-22a20625a76f?version=1.0&t=1344912480580]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"y9LxlToP\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/dm?wh=3]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Centralized Document Management]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"ouksVhdr\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"uBCTuyxG\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_4.jpg/c1cd182d-ba30-4961-ae6b-3a2daae746e3?version=1.0&t=1344912481770]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"pd27Fmww\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/team?wh=4]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Intuitive Team Collaboration]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"x0WdWruO\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"qDdU3SDP\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_5.jpg/33e84fce-e4be-4e50-87e6-b209f01a77a3?version=1.0&t=1344912482018]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"sl1Vtl1C\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/productivity?wh=5]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Tools for Business]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"8hmTz6rx\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"a3KYFfTC\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_6.jpg/039e457b-ce55-406a-901f-7c1eb59d38ca?version=1.0&t=1344912481853]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"RVoEeB9K\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/sync?wh=6]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Desktop & Mobile Access]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"mB58buvT\" name=\"links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"zXTxzQGO\" name=\"bg-image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/10180/0/welcome_bg_7.jpg/0cc635cd-6b1a-4c4a-9c85-52f56c93a0fb?version=1.0&t=1344912481946]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"7LupLUIR\" name=\"action-link-url\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.liferay.com/marketplace?wh=7]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Liferay Marketplace]]></dynamic-content>\n	</dynamic-element>\n</root>','general','10565','10570','','2010-02-01 00:00:00',NULL,NULL,1,0,10587,'',0,10158,'','2012-08-14 02:48:03'),('bd450b7a-5c0d-414d-8268-6fede370a214',32259,32263,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',0,0,'FOOTER-BLURB',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Footer Blurb</Title></root>','footer-blurb','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<static-content language-id=\"en_US\"><![CDATA[<h2>\n	Zoe\n</h2>\n\n<p>\n	Vivamus rutrum nunc non neque conse ctetur quis placerat neque lobortis. Nam vestibulum, arcu sodales feugiat consectetur, nisl orci bibendum elit, eu euismod magna sapien ut nibh. Donec semper quam scelerisque tortor dictum gravida. In hac habitasse\n</p>]]></static-content>\n</root>','general','','','','2010-02-01 00:00:00',NULL,NULL,1,0,32264,'',0,10158,'','2012-11-14 20:25:32'),('60ce24f4-e821-4576-9a45-9907e7c416d1',32271,32272,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:33',0,0,'MAIN-CONTENT',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Main Content</Title></root>','main-content','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<static-content language-id=\"en_US\"><![CDATA[<img alt=\"\" class=\"main-content-image\" src=\"/documents/32062/0/carousel_item2.jpg/56c6fa37-63bb-49d0-bf10-34ad3c82dd90?version=1.0&t=1352924731786\" />\n\n<p>\n	In condimentum orci id nisl volutpat bibendum. Quisque commodo hendrerit lorem quis egestas. Maecenas quis tortor arcu. Vivamus rutrum nunc non neque consectetur quis placerat neque lobortis. Nam vestibulum, arcu sodales feugiat consectetur, nisl orci bibendum elit, eu euismod magna sapien ut nibh. Donec semper quam scelerisque tortor dictum gravida. In hac habitasse platea dictumst. Nam pulvinar, odio sed rhoncus suscipit, sem diam ultrices mauris, eu consequat purus metus eu velit. Proin metus odio, aliquam eget molestie nec, gravida ut sapien. Phasellus quis est sed turpis sollicitudin venenatis sed eu odio. Praesent eget neque eu eros interdum malesuada non vel leo. Sed fringilla porta ligula egestas tincidunt. Nullam risus magna, ornare vitae varius eget, scelerisque a libero. Morbi eu porttitor ipsum. Nullam lorem nisi, posuere quis volutpat eget, luctus nec massa. Pellentesque aliquam lacinia tellus sit amet bibendum. Ut posuere justo in enim pretium scelerisque. Etiam ornare vehicula euismod. Vestibulum at risus augue. Sed non semper dolor. Sed fringilla consequat velit a porta. Pellentesque.\n</p>]]></static-content>\n</root>','general','','','','2010-02-01 00:00:00',NULL,NULL,1,0,32273,'',0,10158,'','2012-11-14 20:25:33'),('4063b424-a5e6-4e98-b67b-6ae9c7132344',32279,32280,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',0,0,'WHY-SEVEN-COGS',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Why Seven Cogs</Title></root>','why-seven-cogs','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<static-content language-id=\"en_US\"><![CDATA[<div class=\"list-block\">\n	<h3 class=\"title\">\n		Why Seven Cogs?\n	</h3>\n\n	<h5>\n		You deserve us\n	</h5>\n\n	<ul class=\"arrow\">\n		<li>Lorem ipsum dolor sit amet, consect</li>\n		<li>Lorem ipsum dolor sit amet, consect</li>\n		<li>Lorem ipsum dolor sit amet, consect</li>\n		<li>Lorem ipsum dolor sit amet, consect</li>\n		<li>Lorem ipsum dolor sit amet, consect</li>\n		<li>Lorem ipsum dolor sit amet, consect</li>\n	</ul>\n</div>]]></static-content>\n</root>','general','','','','2010-02-01 00:00:00',NULL,NULL,1,0,32281,'',0,10158,'','2012-11-14 20:25:33'),('82f9fc46-c50a-466f-8f12-c0882a5dc1c9',32292,32293,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',0,0,'WHAT-OUR-CLIENTS-ARE-SAYING',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">What Our Clients Are Saying</Title></root>','what-our-clients-are-saying','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element instance-id=\"5VaVOybB\" name=\"title\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[What Our Clients Are Saying]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"TbPa9hO6\" name=\"quote\" type=\"text_box\" index-type=\"\">\n		<dynamic-element instance-id=\"akO85Ivk\" name=\"author\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[Matthew Designer]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Aliquam erat volutpat. Mauris vel neque sit amet nunc gravida congue sed sit amet purus. Quisque lacus.]]></dynamic-content>\n	</dynamic-element>\n</root>','general','32287','32289','','2010-02-01 00:00:00',NULL,NULL,1,0,32294,'',0,10158,'','2012-11-14 20:25:33'),('bb3d08f4-547f-4bea-9494-641964812f3c',32305,32306,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',0,0,'FOOTER-LINKS',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Footer Links</Title></root>','footer-links','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element instance-id=\"umMah2Cu\" name=\"title\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[Footer Links]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"RRsHqYC8\" name=\"footer-links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"0taWbQYJ\" name=\"url-location\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[#]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Link 1]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"zDZxljs0\" name=\"footer-links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"xYz2736x\" name=\"url-location\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[#]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Link 2]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"XW4ePqv5\" name=\"footer-links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"IDDC7b0Y\" name=\"url-location\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[#]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Link 3]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"ehen0mXb\" name=\"footer-links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"APqM5EaX\" name=\"url-location\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[#]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Link 4]]></dynamic-content>\n	</dynamic-element>\n</root>','general','32300','32302','','2010-02-01 00:00:00',NULL,NULL,1,0,32307,'',0,10158,'','2012-11-14 20:25:33'),('b14c7468-907e-4dcf-8e96-0402b8df68a6',32318,32319,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',0,0,'OUR-GOAL',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Our Goal</Title></root>','our-goal','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element instance-id=\"0ROUn9x2\" name=\"title\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[Our Goal]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"jBoTcy7E\" name=\"link\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"O8nD3wSD\" name=\"image\" type=\"document_library\" index-type=\"\">\n		<dynamic-content><![CDATA[]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"6XsJs47K\" name=\"description\" type=\"text_box\" index-type=\"\">\n		<dynamic-content><![CDATA[The services you want and deserve]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"3GhW6xQ2\" name=\"content\" type=\"text_area\" index-type=\"\">\n		<dynamic-content><![CDATA[<img src=\"/documents/32062/0/pen.png/1b9dbc42-d2c9-4f72-ae8e-84329ebcf4cb?version=1.0&t=1352924730778\" /><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce malesuada enim ut lorem accumsan lacinia. Cras in orci purus, vitae ultricies dui. Morbi magna neque, laoreet vitae semper non, faucibus vitae lacus. Quisque pretium molestie sapien.</p><p><a class=\"more left\" href=\"#\"><span>More Information</span></a></p>]]></dynamic-content>\n	</dynamic-element>\n</root>','general','32313','32315','','2010-02-01 00:00:00',NULL,NULL,1,0,32320,'',0,10158,'','2012-11-14 20:25:33'),('57bc51b0-b0f5-4992-9344-0a877598718a',32326,32327,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',0,0,'THE-EXPERIENCE',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">The Experience</Title></root>','the-experience','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element instance-id=\"LS0CetC2\" name=\"title\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[The Experience]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"Z2MEq7BR\" name=\"link\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"Nr5mHc3G\" name=\"image\" type=\"document_library\" index-type=\"\">\n		<dynamic-content><![CDATA[]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"A6jmlX90\" name=\"description\" type=\"text_box\" index-type=\"\">\n		<dynamic-content><![CDATA[Experience you can count on]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"SnOZrhD5\" name=\"content\" type=\"text_area\" index-type=\"\">\n		<dynamic-content><![CDATA[<img src=\"/documents/32062/0/network.png/a77d22f5-d564-4998-ad29-b8d7b9434405?version=1.0&t=1352924732115\" /><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce malesuada enim ut lorem accumsan lacinia. Cras in orci purus, vitae ultricies dui. Morbi magna neque, laoreet vitae semper non, faucibus vitae lacus. Quisque pretium molestie sapien.</p><p><a class=\"more left\" href=\"#\"><span>More Information</span></a></p>]]></dynamic-content>\n	</dynamic-element>\n</root>','general','32313','32315','','2010-02-01 00:00:00',NULL,NULL,1,0,32328,'',0,10158,'','2012-11-14 20:25:33'),('0d1ca5b0-2133-4f9c-9ecd-de62a6b3dd5b',32334,32335,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',0,0,'EXCEPTIONAL-SUPPORT',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Exceptional Support</Title></root>','exceptional-support','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element instance-id=\"7niT3c9Q\" name=\"title\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[Exceptional Support]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"93Ar6Euy\" name=\"link\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"kewBv1i9\" name=\"image\" type=\"document_library\" index-type=\"\">\n		<dynamic-content><![CDATA[]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"Znaw8Uj1\" name=\"description\" type=\"text_box\" index-type=\"\">\n		<dynamic-content><![CDATA[Great Support and Service]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"HlvOk8qY\" name=\"content\" type=\"text_area\" index-type=\"\">\n		<dynamic-content><![CDATA[<img src=\"/documents/32062/0/mouse.png/0b857adc-5e8e-4054-aaf4-d68c7db99693?version=1.0&t=1352924731564\" /><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce malesuada enim ut lorem accumsan lacinia. Cras in orci purus, vitae ultricies dui. Morbi magna neque, laoreet vitae semper non, faucibus vitae lacus. Quisque pretium molestie sapien.</p><p><a class=\"more left\" href=\"#\"><span>More Information</span></a></p>]]></dynamic-content>\n	</dynamic-element>\n</root>','general','32313','32315','','2010-02-01 00:00:00',NULL,NULL,1,0,32336,'',0,10158,'','2012-11-14 20:25:33'),('dd9b37cc-3ac7-494c-9948-4ef538448a56',32347,32348,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',0,0,'MAIN-CAROUSEL',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Main Carousel</Title></root>','main-carousel','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element instance-id=\"Dq2bIXiJ\" name=\"height\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[320]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"O4aO9a8O\" name=\"duration\" type=\"list\" index-type=\"\">\n		<dynamic-content><![CDATA[3]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"qYyn3lcL\" name=\"carousel-item\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"SJwKZiO2\" name=\"tagline\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[Lorem ipsum dolor sit amet consect]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"1eszlZHw\" name=\"icon\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/icon_beaker.png/a37e2884-e689-4bb6-a3ac-d1f8137df566?version=1.0&t=1352924732327]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"NKshI0fa\" name=\"image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/carousel_item1.jpg/263e2d7c-e799-4e62-a115-96e77a29181a?version=1.0&t=1352924731208]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"CaKRiz5o\" name=\"url-location\" type=\"link_to_layout\" index-type=\"\">\n			<dynamic-content><![CDATA[5@public]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Services]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"grOgO2rI\" name=\"carousel-item\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"UDRDxXc6\" name=\"tagline\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[Lorem ipsum dolor sit amet consect]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"l9YsagNO\" name=\"icon\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/icon_gears.png/4535eaaf-0509-414c-a62c-a0252cd232d3?version=1.0&t=1352924731421]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"AB6XGWIW\" name=\"image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/carousel_item2.jpg/56c6fa37-63bb-49d0-bf10-34ad3c82dd90?version=1.0&t=1352924731786]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"0BlzqsMw\" name=\"url-location\" type=\"link_to_layout\" index-type=\"\">\n			<dynamic-content><![CDATA[2@public]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Programming]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"qOSRCIBp\" name=\"carousel-item\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"GaBpYLlY\" name=\"tagline\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[Lorem ipsum dolor sit amet consect]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"FbpLAFwr\" name=\"icon\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/icon_phone.png/ee00f58b-ca06-415e-9ae5-bc9d17cfc2bf?version=1.0&t=1352924731682]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"cJiqbcTO\" name=\"image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/carousel_item3.jpg/74353928-596b-4746-8582-db08f631e235?version=1.0&t=1352924730914]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"6A0R3Dey\" name=\"url-location\" type=\"link_to_layout\" index-type=\"\">\n			<dynamic-content><![CDATA[5@public]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Applications]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"PS84bJen\" name=\"carousel-item\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"GfO1ljb2\" name=\"tagline\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[Lorem ipsum dolor sit amet consect]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"MQboRS7t\" name=\"icon\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/icon_network.png/f7ea45e0-fc11-4ca2-9529-bb90f1486b71?version=1.0&t=1352924732606]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"old8s3oU\" name=\"image\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/carousel_item2.jpg/56c6fa37-63bb-49d0-bf10-34ad3c82dd90?version=1.0&t=1352924731786]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"MyFk6Nmv\" name=\"url-location\" type=\"link_to_layout\" index-type=\"\">\n			<dynamic-content><![CDATA[4@public]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Resources]]></dynamic-content>\n	</dynamic-element>\n</root>','general','32342','32344','','2010-02-01 00:00:00',NULL,NULL,1,0,32349,'',0,10158,'','2012-11-14 20:25:33'),('951b4f83-4dda-40da-ad2e-3ca2e9ee0a96',32360,32361,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',0,0,'FOOTER-SOCIAL',1,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Title language-id=\"en_US\">Footer Social</Title></root>','footer-social','','<?xml version=\"1.0\"?>\n\n<root available-locales=\"en_US\" default-locale=\"en_US\">\n	<dynamic-element instance-id=\"5ByhMfQG\" name=\"title\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[Stay Updated]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"ClMr3Ooh\" name=\"tagline\" type=\"text\" index-type=\"\">\n		<dynamic-content><![CDATA[Subscribe and follow us]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"tsRcWebO\" name=\"social-links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"pR2CBoAV\" name=\"icon\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/linkedin.png/291de86e-8d38-4db7-b2b8-8493820b684c?version=1.0&t=1352924732463]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"IW9cyHuX\" name=\"url-location\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//www.linkedin.com/company/liferay-inc.]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[LinkedIn]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"15PiwExp\" name=\"social-links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"mPf8qYG8\" name=\"icon\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/facebook.png/7015eb92-9b12-4dae-9f8a-a46fd7930afc?version=1.0&t=1352924731313]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"gBaaWyD7\" name=\"url-location\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//facebook.com/liferay]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Facebook]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"1N9ja4Ro\" name=\"social-links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"ZMyL8K8V\" name=\"icon\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/twitter.png/570a24b1-1d06-4863-b794-a87d17a7a98b?version=1.0&t=1352924731886]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"XknqciB1\" name=\"url-location\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[//twitter.com/liferay]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[Twitter]]></dynamic-content>\n	</dynamic-element>\n	<dynamic-element instance-id=\"yjRtC4YQ\" name=\"social-links\" type=\"text\" index-type=\"\">\n		<dynamic-element instance-id=\"6qbJy5AA\" name=\"icon\" type=\"document_library\" index-type=\"\">\n			<dynamic-content><![CDATA[/documents/32062/0/rss.png/74bf915f-b101-459a-8cf5-aea8806567dc?version=1.0&t=1352924732228]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-element instance-id=\"Jaw7trL5\" name=\"url-location\" type=\"text\" index-type=\"\">\n			<dynamic-content><![CDATA[#]]></dynamic-content>\n		</dynamic-element>\n		<dynamic-content><![CDATA[RSS]]></dynamic-content>\n	</dynamic-element>\n</root>','general','32355','32357','','2010-02-01 00:00:00',NULL,NULL,1,0,32362,'',0,10158,'','2012-11-14 20:25:34');
/*!40000 ALTER TABLE `journalarticle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journalarticleimage`
--

DROP TABLE IF EXISTS `journalarticleimage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journalarticleimage` (
  `articleImageId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `articleId` varchar(75) DEFAULT NULL,
  `version` double DEFAULT NULL,
  `elInstanceId` varchar(75) DEFAULT NULL,
  `elName` varchar(75) DEFAULT NULL,
  `languageId` varchar(75) DEFAULT NULL,
  `tempImage` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`articleImageId`),
  UNIQUE KEY `IX_103D6207` (`groupId`,`articleId`,`version`,`elInstanceId`,`elName`,`languageId`),
  KEY `IX_3B51BB68` (`groupId`),
  KEY `IX_158B526F` (`groupId`,`articleId`,`version`),
  KEY `IX_D4121315` (`tempImage`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journalarticleimage`
--

LOCK TABLES `journalarticleimage` WRITE;
/*!40000 ALTER TABLE `journalarticleimage` DISABLE KEYS */;
/*!40000 ALTER TABLE `journalarticleimage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journalarticleresource`
--

DROP TABLE IF EXISTS `journalarticleresource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journalarticleresource` (
  `uuid_` varchar(75) DEFAULT NULL,
  `resourcePrimKey` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `articleId` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`resourcePrimKey`),
  UNIQUE KEY `IX_88DF994A` (`groupId`,`articleId`),
  UNIQUE KEY `IX_84AB0309` (`uuid_`,`groupId`),
  KEY `IX_F8433677` (`groupId`),
  KEY `IX_DCD1FAC1` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journalarticleresource`
--

LOCK TABLES `journalarticleresource` WRITE;
/*!40000 ALTER TABLE `journalarticleresource` DISABLE KEYS */;
INSERT INTO `journalarticleresource` VALUES ('10aae8f4-1fe7-450e-afce-ed1fd0c52b49',10548,10180,'WHO-IS-USING-LIFERAY'),('52665fc1-edd4-434a-aa86-d18f49883bb3',10574,10180,'LIFERAY-BENEFITS'),('aea644dd-7ead-4eae-9f66-ce5751f54f6b',10586,10180,'WHAT-WE-DO'),('4e6dd3a2-938d-4374-b829-37d0bcd6359c',32263,32062,'FOOTER-BLURB'),('90ca3c7e-0eb4-4fa7-836a-5f9faedea365',32272,32062,'MAIN-CONTENT'),('26443cd4-7d41-43d8-8cf7-14b7b966e4ea',32280,32062,'WHY-SEVEN-COGS'),('8bea3cc7-16bb-413e-a6ef-7833eb91754e',32293,32062,'WHAT-OUR-CLIENTS-ARE-SAYING'),('294ef961-bc4a-4977-9142-ce60ed59bd42',32306,32062,'FOOTER-LINKS'),('1f2214a7-6460-4a74-9073-a004dafa7014',32319,32062,'OUR-GOAL'),('84fcfce9-7e6f-4a73-ac0d-df304c47d6f5',32327,32062,'THE-EXPERIENCE'),('f799172b-6d29-4a70-a6d3-f2d178f994ac',32335,32062,'EXCEPTIONAL-SUPPORT'),('126e5c28-441a-4757-98ae-79870c0a217a',32348,32062,'MAIN-CAROUSEL'),('62e1d461-585d-483d-b1a1-d50f6ec4d2ca',32361,32062,'FOOTER-SOCIAL');
/*!40000 ALTER TABLE `journalarticleresource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journalcontentsearch`
--

DROP TABLE IF EXISTS `journalcontentsearch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journalcontentsearch` (
  `contentSearchId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `layoutId` bigint(20) DEFAULT NULL,
  `portletId` varchar(200) DEFAULT NULL,
  `articleId` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`contentSearchId`),
  UNIQUE KEY `IX_C3AA93B8` (`groupId`,`privateLayout`,`layoutId`,`portletId`,`articleId`),
  KEY `IX_9207CB31` (`articleId`),
  KEY `IX_6838E427` (`groupId`,`articleId`),
  KEY `IX_20962903` (`groupId`,`privateLayout`),
  KEY `IX_7CC7D73E` (`groupId`,`privateLayout`,`articleId`),
  KEY `IX_B3B318DC` (`groupId`,`privateLayout`,`layoutId`),
  KEY `IX_7ACC74C9` (`groupId`,`privateLayout`,`layoutId`,`portletId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journalcontentsearch`
--

LOCK TABLES `journalcontentsearch` WRITE;
/*!40000 ALTER TABLE `journalcontentsearch` DISABLE KEYS */;
/*!40000 ALTER TABLE `journalcontentsearch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journalfeed`
--

DROP TABLE IF EXISTS `journalfeed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journalfeed` (
  `uuid_` varchar(75) DEFAULT NULL,
  `id_` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `feedId` varchar(75) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `structureId` varchar(75) DEFAULT NULL,
  `templateId` varchar(75) DEFAULT NULL,
  `rendererTemplateId` varchar(75) DEFAULT NULL,
  `delta` int(11) DEFAULT NULL,
  `orderByCol` varchar(75) DEFAULT NULL,
  `orderByType` varchar(75) DEFAULT NULL,
  `targetLayoutFriendlyUrl` varchar(255) DEFAULT NULL,
  `targetPortletId` varchar(75) DEFAULT NULL,
  `contentField` varchar(75) DEFAULT NULL,
  `feedType` varchar(75) DEFAULT NULL,
  `feedVersion` double DEFAULT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `IX_65576CBC` (`groupId`,`feedId`),
  UNIQUE KEY `IX_39031F51` (`uuid_`,`groupId`),
  KEY `IX_35A2DB2F` (`groupId`),
  KEY `IX_50C36D79` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journalfeed`
--

LOCK TABLES `journalfeed` WRITE;
/*!40000 ALTER TABLE `journalfeed` DISABLE KEYS */;
/*!40000 ALTER TABLE `journalfeed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journalstructure`
--

DROP TABLE IF EXISTS `journalstructure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journalstructure` (
  `uuid_` varchar(75) DEFAULT NULL,
  `id_` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `structureId` varchar(75) DEFAULT NULL,
  `parentStructureId` varchar(75) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `xsd` longtext,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `IX_AB6E9996` (`groupId`,`structureId`),
  UNIQUE KEY `IX_42E86E58` (`uuid_`,`groupId`),
  KEY `IX_B97F5608` (`groupId`),
  KEY `IX_CA0BD48C` (`groupId`,`parentStructureId`),
  KEY `IX_4FA67B72` (`parentStructureId`),
  KEY `IX_8831E4FC` (`structureId`),
  KEY `IX_6702CA92` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journalstructure`
--

LOCK TABLES `journalstructure` WRITE;
/*!40000 ALTER TABLE `journalstructure` DISABLE KEYS */;
INSERT INTO `journalstructure` VALUES ('c704554f-d528-4eb2-bb63-0ed9c1561188',10566,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03','10565','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Welcome Content Structure</Name></root>','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"page-title\" type=\"text\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Sub Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"links\" type=\"text\" index-type=\"\" repeatable=\"true\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Link Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n		<dynamic-element name=\"bg-image\" type=\"document_library\" index-type=\"\" repeatable=\"false\">\n			<meta-data>\n				<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n				<entry name=\"required\"><![CDATA[false]]></entry>\n				<entry name=\"instructions\"><![CDATA[]]></entry>\n				<entry name=\"label\"><![CDATA[Background Image]]></entry>\n				<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"action-link-url\" type=\"text\" index-type=\"\" repeatable=\"false\">\n			<meta-data>\n				<entry name=\"displayAsTooltip\"><![CDATA[false]]></entry>\n				<entry name=\"required\"><![CDATA[false]]></entry>\n				<entry name=\"instructions\"><![CDATA[]]></entry>\n				<entry name=\"label\"><![CDATA[Call to Action URL]]></entry>\n				<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			</meta-data>\n		</dynamic-element>\n	</dynamic-element>\n</root>'),('35502826-e0e6-4a12-8da8-48e96a515fef',32288,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33','32287','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Quote Box</Name></root>','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"title\" type=\"text\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"quote\" type=\"text_box\" index-type=\"\" repeatable=\"true\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Quote]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n		<dynamic-element name=\"author\" type=\"text\" index-type=\"\" repeatable=\"false\">\n			<meta-data>\n				<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n				<entry name=\"required\"><![CDATA[false]]></entry>\n				<entry name=\"instructions\"><![CDATA[]]></entry>\n				<entry name=\"label\"><![CDATA[Author]]></entry>\n				<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			</meta-data>\n		</dynamic-element>\n	</dynamic-element>\n</root>'),('b0ca414c-9289-4fc9-b546-b62d9fb84068',32301,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33','32300','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Footer Links</Name></root>','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"title\" type=\"text\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Footer Link Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"footer-links\" type=\"text\" index-type=\"\" repeatable=\"true\">\n		<dynamic-element name=\"url-location\" type=\"text\" index-type=\"\" repeatable=\"false\">\n			<meta-data>\n				<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n				<entry name=\"required\"><![CDATA[false]]></entry>\n				<entry name=\"instructions\"><![CDATA[]]></entry>\n				<entry name=\"label\"><![CDATA[Url]]></entry>\n				<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[true]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Link Name]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>'),('c94fcb10-014e-423b-ada9-3148eb55cae9',32314,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33','32313','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Featured Content</Name></root>','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"title\" type=\"text\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"link\" type=\"text\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[URL Link]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"image\" type=\"document_library\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Select Image]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"description\" type=\"text_box\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Description]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"content\" type=\"text_area\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Content]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>'),('068eeea4-f674-4e77-8e7e-cb31fe5642c6',32343,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33','32342','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Carousel</Name></root>','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"height\" type=\"text\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[true]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Carousel Height]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[320]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"duration\" type=\"list\" index-type=\"\" repeatable=\"false\">\n		<dynamic-element name=\"1\" type=\"1\" repeatable=\"false\"/>\n		<dynamic-element name=\"2\" type=\"2\" repeatable=\"false\"/>\n		<dynamic-element name=\"3\" type=\"3\" repeatable=\"false\"/>\n		<dynamic-element name=\"4\" type=\"4\" repeatable=\"false\"/>\n		<dynamic-element name=\"5\" type=\"5\" repeatable=\"false\"/>\n		<dynamic-element name=\"6\" type=\"6\" repeatable=\"false\"/>\n		<dynamic-element name=\"7\" type=\"7\" repeatable=\"false\"/>\n		<dynamic-element name=\"8\" type=\"8\" repeatable=\"false\"/>\n		<dynamic-element name=\"9\" type=\"9\" repeatable=\"false\"/>\n		<dynamic-element name=\"10\" type=\"10\" repeatable=\"false\"/>\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Transition Duration]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[5]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"carousel-item\" type=\"text\" index-type=\"\" repeatable=\"true\">\n		<dynamic-element name=\"tagline\" type=\"text\" index-type=\"\" repeatable=\"false\">\n			<meta-data>\n				<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n				<entry name=\"required\"><![CDATA[false]]></entry>\n				<entry name=\"instructions\"><![CDATA[]]></entry>\n				<entry name=\"label\"><![CDATA[Item Tagline]]></entry>\n				<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"icon\" type=\"document_library\" index-type=\"\" repeatable=\"false\">\n			<meta-data>\n				<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n				<entry name=\"required\"><![CDATA[false]]></entry>\n				<entry name=\"instructions\"><![CDATA[]]></entry>\n				<entry name=\"label\"><![CDATA[Item Icon]]></entry>\n				<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"image\" type=\"document_library\" index-type=\"\" repeatable=\"false\">\n			<meta-data>\n				<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n				<entry name=\"required\"><![CDATA[true]]></entry>\n				<entry name=\"instructions\"><![CDATA[]]></entry>\n				<entry name=\"label\"><![CDATA[Item Image]]></entry>\n				<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"url-location\" type=\"link_to_layout\" index-type=\"\" repeatable=\"false\">\n			<meta-data>\n				<entry name=\"displayAsTooltip\"><![CDATA[false]]></entry>\n				<entry name=\"required\"><![CDATA[false]]></entry>\n				<entry name=\"instructions\"><![CDATA[]]></entry>\n				<entry name=\"label\"><![CDATA[Item Url]]></entry>\n				<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Carousel Item]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>'),('892b3b45-4ac8-4bc0-9148-a70c7967849f',32356,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33','32355','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Footer Social</Name></root>','','<?xml version=\"1.0\"?>\n\n<root>\n	<dynamic-element name=\"title\" type=\"text\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[true]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Title]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"tagline\" type=\"text\" index-type=\"\" repeatable=\"false\">\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Tagline]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n	<dynamic-element name=\"social-links\" type=\"text\" index-type=\"\" repeatable=\"true\">\n		<dynamic-element name=\"icon\" type=\"document_library\" index-type=\"\" repeatable=\"false\">\n			<meta-data>\n				<entry name=\"displayAsTooltip\"><![CDATA[true]]></entry>\n				<entry name=\"required\"><![CDATA[false]]></entry>\n				<entry name=\"instructions\"><![CDATA[]]></entry>\n				<entry name=\"label\"><![CDATA[icon]]></entry>\n				<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<dynamic-element name=\"url-location\" type=\"text\" index-type=\"\" repeatable=\"false\">\n			<meta-data>\n				<entry name=\"displayAsTooltip\"><![CDATA[false]]></entry>\n				<entry name=\"required\"><![CDATA[true]]></entry>\n				<entry name=\"instructions\"><![CDATA[]]></entry>\n				<entry name=\"label\"><![CDATA[Url]]></entry>\n				<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n			</meta-data>\n		</dynamic-element>\n		<meta-data>\n			<entry name=\"displayAsTooltip\"><![CDATA[false]]></entry>\n			<entry name=\"required\"><![CDATA[false]]></entry>\n			<entry name=\"instructions\"><![CDATA[]]></entry>\n			<entry name=\"label\"><![CDATA[Social Link]]></entry>\n			<entry name=\"predefinedValue\"><![CDATA[]]></entry>\n		</meta-data>\n	</dynamic-element>\n</root>');
/*!40000 ALTER TABLE `journalstructure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journaltemplate`
--

DROP TABLE IF EXISTS `journaltemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journaltemplate` (
  `uuid_` varchar(75) DEFAULT NULL,
  `id_` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `templateId` varchar(75) DEFAULT NULL,
  `structureId` varchar(75) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `xsl` longtext,
  `langType` varchar(75) DEFAULT NULL,
  `cacheable` tinyint(4) DEFAULT NULL,
  `smallImage` tinyint(4) DEFAULT NULL,
  `smallImageId` bigint(20) DEFAULT NULL,
  `smallImageURL` longtext,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `IX_E802AA3C` (`groupId`,`templateId`),
  UNIQUE KEY `IX_62D1B3AD` (`uuid_`,`groupId`),
  KEY `IX_77923653` (`groupId`),
  KEY `IX_1701CB2B` (`groupId`,`structureId`),
  KEY `IX_25FFB6FA` (`smallImageId`),
  KEY `IX_45F5A7C7` (`structureId`),
  KEY `IX_1B12CA20` (`templateId`),
  KEY `IX_2857419D` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journaltemplate`
--

LOCK TABLES `journaltemplate` WRITE;
/*!40000 ALTER TABLE `journaltemplate` DISABLE KEYS */;
INSERT INTO `journaltemplate` VALUES ('f085934a-a0f7-4a03-a5c9-b632b1f6242d',10571,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03','10570','10565','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Welcome Content Template</Name></root>','','<div class=\"navigation-wrapper\">\n	<header class=\"content-head content-head-liferay-portal\">\n		<hgroup>\n			<h1>$page-title.data</h1>\n\n			<hr />\n		</hgroup>\n\n		<nav>\n			<ul id=\"contentNav\">\n				#foreach($link in $links.siblings)\n					#if($velocityCount == 1)\n						<li class=\"selected\"><a href=\"#pageId$velocityCount\">$link.data</a></li>\n					#else\n						<li><a href=\"#pageId$velocityCount\">$link.data</a></li>\n					#end\n				#end\n			</ul>\n		</nav>\n	</header>\n\n	<div class=\"content-area-wrapper\">\n		#foreach($link in $links.siblings)\n			#if($velocityCount == 1)\n				<style type=\"text/css\">\n					.content-area {\n						background: url($link.bg-image.data) 100% 0 no-repeat;\n					}\n				</style>\n\n				<div class=\"content-area selected\" data-bannerImage=\"$link.bg-image.data\" id=\"pageId$velocityCount\">\n					$link.data\n\n					<a href=\"$link.action-link-url.data\" id=\"marketplace\">&nbsp;</a>\n				</div>\n			#else\n				<div class=\"content-area\" data-bannerImage=\"$link.bg-image.data\" id=\"pageId$velocityCount\">\n					$link.data\n\n					<a href=\"$link.action-link-url.data\" id=\"marketplace\">&nbsp;</a>\n				</div>\n			#end\n		#end\n	</div>\n</div>\n\n<script charset=\"utf-8\" type=\"text/javascript\">\n	AUI().ready(\n		\'aui-base\',\n		function(A) {\n			var hash = null;\n\n			var selectContent = function(href) {\n				var div = A.one(href);\n\n				if (div) {\n					div.radioClass(\'selected\');\n\n					div.setStyle(\'background\', \'url(\' + div.attr(\'data-bannerImage\')+\') 100% 0 no-repeat\');\n\n					hash = href;\n				}\n			};\n\n			var selectNavItem = function(hash) {\n				if (hash) {\n					A.one(\'#contentNav a[href$=\"\'+ hash +\'\"]\').ancestor().radioClass(\'selected\');\n				}\n			};\n\n			var select = function(hash) {\n				selectContent(hash);\n				selectNavItem(hash);\n			};\n\n			var currentHash = location.hash;\n\n			if (currentHash) {\n				select(currentHash);\n			}\n\n			setInterval(\n				function() {\n					var currentHash = location.hash;\n\n					if (currentHash != hash) {\n						select(currentHash);\n					}\n				},\n				200\n			);\n\n			A.one(\'#contentNav\').delegate(\n				\'click\',\n				function(event) {\n					var a = event.currentTarget;\n\n					a.ancestor().radioClass(\'selected\');\n\n					selectContent(a.attr(\'hash\'));\n				},\n				\'a\'\n			);\n		}\n	);\n</script>','vm',0,0,10572,''),('95280b08-4e8f-494e-a984-364ca08fbaec',32290,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33','32289','32287','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Quote Box</Name></root>','','<div id=\"quoteBox\">\n	#if ($title.data != \"\")\n		<h3>\n			$title.data\n		</h3>\n	#end\n\n	<div id=\"quoteBoxContainer\">\n		#set ($count = 0)\n\n		#foreach ($quote-item in $quote.siblings)\n			<div class=\"quote-item quote-item-${count}\">\n				#if ($quote-item.data != \"\")\n					<q class=\"quote\">\n						<span class=\"first\">&quot;</span>\n							$quote-item.data\n						<span class=\"last\">&quot;</span>\n					</q>\n				#end\n\n				#if ($quote-item.author.data != \"\")\n					<div class=\"author\">\n						- $quote-item.author.data\n					</div>\n				#end\n			</div>\n\n			#set ($count = $count + 1)\n		#end\n	</div>\n</div>','vm',0,0,32291,''),('20ece768-466a-41ee-87da-7cf62e1c5576',32303,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33','32302','32300','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Footer Links</Name></root>','','#if ($title.data != \"\")\n	<h2>\n		$title.data\n	</h2>\n#end\n\n<ul class=\"hide-bullets\">\n	#foreach ($footer-link in $footer-links.siblings)\n		<li>\n			#if ($footer-link.url-location.data != \"\")\n				<a href=\"$footer-link.url-location.data\">\n			#end\n\n			$footer-link.data\n\n			#if ($footer-link.url-location.data != \"\")\n				</a>\n			#end\n		</li>\n	#end\n</ul>','vm',0,0,32304,''),('2db3b07c-c1ef-46ff-814a-f66e4b995f5c',32316,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33','32315','32313','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Featured Content</Name></root>','','<div class=\"pod\">\n	#if ($image.data != \"\")\n		<img class=\"pod-image\" src=\"$image.data\" />\n	#end\n\n	<div class=\"pod-text\">\n		#if ($title.data != \"\")\n			<h5>\n				$title.data\n			</h5>\n		#end\n\n		$description.data\n	</div>\n\n	<div class=\"divider\"></div>\n</div>\n\n#if ($content.data != \"\")\n	<div class=\"column-text\">\n		$content.data\n	</div>\n#end','vm',0,0,32317,''),('8550b7e7-c977-451d-8f90-1eb4e06f9500',32345,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33','32344','32342','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Carousel</Name></root>','','#set ($article_id = $reserved-article-id.getData())\n#set ($content_id = \"carousel-\" + $article_id)\n\n<style>\n	#${content_id} {\n		height: ${height.data}px;\n	}\n</style>\n\n<div class=\"carousel-container\" id=\"$content_id\">\n	<ul id=\"tabs\">\n		#foreach ($item in $carousel-item.siblings)\n			#set ($carousel_menu_item_class = \"tab-item item-\" + $velocityCount + \" aui-carousel-menu-index\")\n\n			#if ($velocityCount == 1)\n				#set ($carousel_menu_item_class = $carousel_menu_item_class + \" aui-carousel-menu-active first\")\n			#end\n\n			#if (!$velocityHasNext)\n				#set ($carousel_menu_item_class = $carousel_menu_item_class + \" last\")\n			#end\n\n			<li class=\"$carousel_menu_item_class\">\n				#if ($item.icon.data != \"\")\n					<img class=\"icon\" src=\"$item.icon.data\" />\n				#end\n\n				#if ($item.data != \"\")\n					<h4 class=\"title\">\n						$item.data\n					</h4>\n				#end\n\n				#if ($item.tagline.data != \"\")\n					<span class=\"tagline\">\n						$item.tagline.data\n					</span>\n				#end\n			</li>\n\n			#if ($velocityHasNext)\n				<div class=\"divider\"></div>\n			#end\n		#end\n	</ul>\n\n	<div id=\"carousel\">\n		#foreach ($item in $carousel-item.siblings)\n			#set ($carousel_item_class = \"aui-carousel-item\")\n\n			#if ($velocityCount == 1)\n				#set ($carousel_item_class = $carousel_item_class + \" aui-carousel-item-active\")\n			#end\n\n			#if ($item.url-location.data != \"\")\n				<a class=\"$carousel_item_class\" href=\"$item.url-location.friendlyUrl\" style=\"background: url(${item.image.data}) no-repeat; height: ${height.data}px; width: 674px;\" title=\"$item.data\">$item.data</a>\n			#else\n\n			<div class=\"$carousel_item_class\" style=\"background: url(${item.image.data}) no-repeat; height: ${height.data}px; width: 674px;\">\n				$item.data\n			</div>\n			#end\n		#end\n	</div>\n</div>\n\n<script type=\"text/javascript\">\n	AUI().ready(\n		\'aui-carousel\',\n		function(A) {\n			new A.Carousel(\n				{\n					contentBox: \'#${content_id} #carousel\',\n					height: ${height.data},\n					intervalTime: ${duration.data},\n					nodeMenu: \'#${content_id} #tabs\',\n					nodeMenuItemSelector: \'.tab-item\',\n					width: 287\n				}\n			).render();\n		}\n	);\n</script>','vm',0,0,32346,''),('1b1931cd-6895-4acc-87f2-c579c64d436c',32358,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33','32357','32355','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Footer Social</Name></root>','','#if ($title.data != \"\")\n	<h2>\n		$title.data\n	</h2>\n#end\n\n#if ($tagline.data != \"\")\n	<h4>\n		$tagline.data\n	</h4>\n#end\n\n<ul class=\"social\">\n	#foreach ($social-link in $social-links.siblings)\n		<li>\n			<a href=\"$social-link.url-location.data\" target=\"new_window\" title=\"$social-link.data\">\n				<img alt=\"$social-link.data\" src=\"$social-link.icon.data\" />\n\n				<span>$social-link.data</span>\n			</a>\n		</li>\n	#end\n</ul>','vm',0,0,32359,'');
/*!40000 ALTER TABLE `journaltemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layout`
--

DROP TABLE IF EXISTS `layout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `layout` (
  `uuid_` varchar(75) DEFAULT NULL,
  `plid` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `layoutId` bigint(20) DEFAULT NULL,
  `parentLayoutId` bigint(20) DEFAULT NULL,
  `name` longtext,
  `title` longtext,
  `description` longtext,
  `keywords` longtext,
  `robots` longtext,
  `type_` varchar(75) DEFAULT NULL,
  `typeSettings` longtext,
  `hidden_` tinyint(4) DEFAULT NULL,
  `friendlyURL` varchar(255) DEFAULT NULL,
  `iconImage` tinyint(4) DEFAULT NULL,
  `iconImageId` bigint(20) DEFAULT NULL,
  `themeId` varchar(75) DEFAULT NULL,
  `colorSchemeId` varchar(75) DEFAULT NULL,
  `wapThemeId` varchar(75) DEFAULT NULL,
  `wapColorSchemeId` varchar(75) DEFAULT NULL,
  `css` longtext,
  `priority` int(11) DEFAULT NULL,
  `layoutPrototypeUuid` varchar(75) DEFAULT NULL,
  `layoutPrototypeLinkEnabled` tinyint(4) DEFAULT NULL,
  `sourcePrototypeLayoutUuid` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`plid`),
  UNIQUE KEY `IX_BC2C4231` (`groupId`,`privateLayout`,`friendlyURL`),
  UNIQUE KEY `IX_7162C27C` (`groupId`,`privateLayout`,`layoutId`),
  UNIQUE KEY `IX_CED31606` (`uuid_`,`groupId`),
  KEY `IX_C7FBC998` (`companyId`),
  KEY `IX_C099D61A` (`groupId`),
  KEY `IX_705F5AA3` (`groupId`,`privateLayout`),
  KEY `IX_6DE88B06` (`groupId`,`privateLayout`,`parentLayoutId`),
  KEY `IX_8CE8C0D9` (`groupId`,`privateLayout`,`sourcePrototypeLayoutUuid`),
  KEY `IX_1A1B61D2` (`groupId`,`privateLayout`,`type_`),
  KEY `IX_23922F7D` (`iconImageId`),
  KEY `IX_B529BFD3` (`layoutPrototypeUuid`),
  KEY `IX_D0822724` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layout`
--

LOCK TABLES `layout` WRITE;
/*!40000 ALTER TABLE `layout` DISABLE KEYS */;
INSERT INTO `layout` VALUES ('d13cc38c-0813-4e98-a108-e51f9d72c14e',10175,10172,10154,'2012-08-14 02:47:55','2012-08-14 02:47:55',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Control Panel</Name></root>','','','','','control_panel','',0,'/manage',0,0,'','','','','',0,'',0,''),('6cafadcd-c829-4e80-83bf-a17c97bbf075',10313,10310,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Blog</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=148_INSTANCE_r3brs7aSUO5i,114,\ncolumn-1=33,\n',0,'/layout',0,0,'','','','','',0,'',0,''),('8eff0652-5755-4ccd-ae0a-d5d815603aa0',10323,10320,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Content Display Page</Name></root>','','','','','portlet','default-asset-publisher-portlet-id=101_INSTANCE_0v9ySz6IOVYg\nlayout-template-id=2_columns_ii\ncolumn-2=3,101_INSTANCE_0v9ySz6IOVYg,\ncolumn-1=141_INSTANCE_sdK2lcsmGwhZ,122_INSTANCE_9xxTt1olN52m,\n',0,'/layout',0,0,'','','','','',0,'',0,''),('59dd6439-845f-4bfc-865c-0e8a4bcce549',10332,10329,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Wiki</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=122_INSTANCE_XZXjvFPL0CNe,141_INSTANCE_k06f1IndZnHz,\ncolumn-1=36,\n',0,'/layout',0,0,'','','','','',0,'',0,''),('4a47f344-b9b2-43fc-abaa-6f4d0fb209d3',10346,10338,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Home</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=3,59_INSTANCE_8eh6WKTnWQku,180,\ncolumn-1=19,\n',0,'/home',0,0,'','','','','',0,'',0,''),('7c7aba53-02a9-4f32-8e18-83d6835caf19',10352,10338,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,2,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Calendar</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=101_INSTANCE_XGmIipFP7be1,\ncolumn-1=8,\n',0,'/calendar',0,0,'','','','','',1,'',0,''),('aa97bba0-f610-45c3-b064-f727385fbe0c',10358,10338,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,3,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Wiki</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=122_INSTANCE_R2dDvkG7s7UF,148_INSTANCE_9BlW5AP2uHvA,\ncolumn-1=36,\n',0,'/wiki',0,0,'','','','','',2,'',0,''),('f61842f6-fcb7-4794-bd7d-821a5abf2cdf',10372,10364,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Home</Name></root>','','','','','portlet','layout-template-id=2_columns_i\ncolumn-2=3,82,101_INSTANCE_D2SSkvmZJPDZ,\ncolumn-1=116,\n',0,'/home',0,0,'','','','','',0,'',0,''),('602ff7ec-218b-4898-b73b-a2ea0d81df5b',10380,10364,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,2,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Documents and Media</Name></root>','','','','','portlet','layout-template-id=1_column\ncolumn-1=20,\n',0,'/documents',0,0,'','','','','',1,'',0,''),('29b2c813-7e11-4558-8fc3-b8395c27fdc8',10386,10364,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,3,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Calendar</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=101_INSTANCE_9jLPIIg9a3aK,\ncolumn-1=8,\n',0,'/calendar',0,0,'','','','','',2,'',0,''),('229be93e-13bd-4ee2-8801-ac2bec4a0e94',10392,10364,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,4,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">News</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=39_INSTANCE_Dd0JhJKWcWOw,\ncolumn-1=39_INSTANCE_z51mVQ48aMJ7,\n',0,'/news',0,0,'','','','','',3,'',0,''),('cdc8509a-630e-4ee1-a0d9-723db56f07c2',10597,10180,10154,'2012-08-14 02:48:03','2012-08-14 02:50:04',0,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">What We Do</Name></root>','','','','','portlet','layout-template-id=1_column\n',0,'/what-we-do',0,0,'','','','','',0,'',0,''),('e52a3dec-e6e4-4b29-bbaa-07ae8c0beb9d',10603,10180,10154,'2012-08-14 02:48:03','2012-11-14 20:26:28',0,2,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Who Is Using Liferay</Name></root>','','','','','portlet','sitemap-changefreq=daily\nlayout-template-id=1_column\nshow-alternate-links=true\nsitemap-include=1\nlayoutUpdateable=true\ncolumn-1-customizable=false\ncolumn-1=56_INSTANCE_aTzDuB3geL4w,\n',0,'/who-is-using-liferay',0,0,'classic','','classic','','',1,'',0,''),('0fadce76-fe47-404d-a037-c7f1b6c4c134',10609,10180,10154,'2012-08-14 02:48:03','2012-08-14 02:48:03',0,3,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Liferay Benefits</Name></root>','','','','','portlet','layout-template-id=1_column\ncolumn-1=56_INSTANCE_KbtAD0VeRh9k,\n',0,'/liferay-benefits',0,0,'','','','','',2,'',0,''),('c44fba0f-f9b6-4737-aadd-0a0f782bd51a',10619,10198,10154,'2012-08-14 02:48:50','2012-08-14 02:48:51',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Welcome</Name></root>','','','','','portlet','layout-template-id=2_columns_ii\ncolumn-2=29,8,\ncolumn-1=82,23,11,\n',0,'/home',0,0,'','','','','',0,'',0,''),('d7095707-0139-4771-98fc-fbd8f242a148',10624,10198,10154,'2012-08-14 02:48:51','2012-08-14 02:48:51',0,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Welcome</Name></root>','','','','','portlet','layout-template-id=2_columns_ii\ncolumn-2=33,\ncolumn-1=82,3,\n',0,'/home',0,0,'','','','','',0,'',0,''),('ba8a5263-8475-4bf3-91c9-0df7fa44f213',10635,10180,10154,'2012-08-14 02:50:26','2012-08-14 02:53:24',0,4,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">DocLib</Name></root>','','','','','portlet','layout-template-id=1_column\nshow-alternate-links=true\nsitemap-changefreq=daily\nsitemap-include=1\nlayoutUpdateable=true\ncolumn-2-customizable=false\ncolumn-1-customizable=false\ncolumn-1=20\n',0,'/doclib',0,0,'classic','','classic','','',3,'',0,''),('3c2f5d41-3521-452f-82f2-1d7494d4df84',16347,10180,10154,'2012-08-30 17:34:28','2012-11-26 07:43:22',0,5,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">ConX</Name></root>','','','','','portlet','javascript=function layout() {_SAFE_NEWLINE_CHARACTER_	$(\'.portlet-body div iframe\').height($(\'body\').height());_SAFE_NEWLINE_CHARACTER_}_SAFE_NEWLINE_CHARACTER_$(\'.portlet-body div iframe\').load(function() {_SAFE_NEWLINE_CHARACTER_	layout();_SAFE_NEWLINE_CHARACTER_});_SAFE_NEWLINE_CHARACTER_$(window).resize(function() {_SAFE_NEWLINE_CHARACTER_	layout();_SAFE_NEWLINE_CHARACTER_});\nsitemap-changefreq=daily\nlayout-template-id=1_column\nshow-alternate-links=true\nsitemap-include=1\nlayoutUpdateable=true\ncolumn-2-customizable=false\ncolumn-1-customizable=false\ncolumn-1=48_INSTANCE_QchntqUvZ1JY\n',0,'/conx',0,0,'comconxportalthemesportal_WAR_comconxportalthemesportaltheme','01','classic','','.portlet-column-content {\npadding: 0px;\n}',4,'',0,''),('eabf1a0c-10ed-4100-b561-ee79b7700d4a',16571,16564,10154,'2012-08-30 19:24:59','2012-08-30 19:24:59',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Welcome</Name></root>','','','','','portlet','layout-template-id=2_columns_ii\ncolumn-2=29,8,\ncolumn-1=82,23,11,\n',0,'/home',0,0,'','','','','',0,'',0,''),('92717f54-d9b9-4b7c-8c6f-593638f72da2',16576,16564,10154,'2012-08-30 19:24:59','2012-08-30 19:24:59',0,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Welcome</Name></root>','','','','','portlet','layout-template-id=2_columns_ii\ncolumn-2=33,\ncolumn-1=82,3,\n',0,'/home',0,0,'','','','','',0,'',0,''),('a34a0de2-878e-44ec-8e97-55c9f7a71a47',32368,32062,10154,'2012-11-14 20:25:34','2012-11-14 20:25:34',1,1,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Home</Name></root>','','','','','portlet','layout-template-id=1_3_1_columns\ncolumn-4=56_INSTANCE_dX3cF6QJLhKO,\ncolumn-3=56_INSTANCE_lWf05Qg36GvU,\ncolumn-5=56_INSTANCE_4qOp14zAbq7m,\ncolumn-2=56_INSTANCE_KniNRZT9hDuP,\ncolumn-1=56_INSTANCE_tZuaTAo2hOG6,\n',0,'/home',0,0,'','','','','',0,'',0,''),('ff61e07a-1233-45af-9ed9-8a98d0ddaa1d',32378,32062,10154,'2012-11-14 20:25:34','2012-11-14 20:25:34',1,2,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Clients</Name></root>','','','','','portlet','layout-template-id=1_3_1_columns\n',0,'/clients',0,0,'','','','','',1,'',0,''),('27d47561-5efa-4aef-bc97-85041bfbb265',32383,32062,10154,'2012-11-14 20:25:34','2012-11-14 20:25:34',1,3,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Partners</Name></root>','','','','','portlet','layout-template-id=1_3_1_columns\n',0,'/partners',0,0,'','','','','',2,'',0,''),('7b1e3d33-5cc9-4634-88a1-ae40dfb4c81d',32388,32062,10154,'2012-11-14 20:25:34','2012-11-14 20:25:34',1,4,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Careers</Name></root>','','','','','portlet','layout-template-id=1_3_1_columns\n',0,'/careers',0,0,'','','','','',3,'',0,''),('58469e76-2132-48a2-b362-17a83e05bc08',32393,32062,10154,'2012-11-14 20:25:34','2012-11-14 20:25:34',1,5,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">About Us</Name></root>','','','','','portlet','layout-template-id=2_columns_iii\ncolumn-2=56_INSTANCE_pBXjW6iNevv7,\ncolumn-1=56_INSTANCE_BTgTRPG71sYr,\n',0,'/about-us',0,0,'','','','','',4,'',0,''),('694bbd51-e8cc-46f0-8ba5-d261ea3d71c8',32400,32062,10154,'2012-11-14 20:25:34','2012-11-14 20:25:34',1,6,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Contact Us</Name></root>','','','','','portlet','layout-template-id=1_3_1_columns\n',0,'/contact-us',0,0,'','','','','',5,'',0,''),('f4348739-9933-4f4b-9f71-286590da18d4',35201,10180,10154,'2012-11-26 07:16:37','2012-11-26 07:29:37',0,7,0,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Test</Name></root>','','','','','portlet','javascript=$(iframe).height(\"500px\");\nsitemap-changefreq=daily\nlayout-template-id=1_column\nshow-alternate-links=true\nsitemap-include=1\nlayoutUpdateable=true\ncolumn-2-customizable=false\ncolumn-1-customizable=false\ncolumn-1=48_INSTANCE_z5aG4NvnwC4O,\n',0,'/test',0,0,'comconxportalthemesportal_WAR_comconxportalthemesportaltheme','01','classic','','',5,'',0,'');
/*!40000 ALTER TABLE `layout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layoutbranch`
--

DROP TABLE IF EXISTS `layoutbranch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `layoutbranch` (
  `LayoutBranchId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `layoutSetBranchId` bigint(20) DEFAULT NULL,
  `plid` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `master` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`LayoutBranchId`),
  UNIQUE KEY `IX_FD57097D` (`layoutSetBranchId`,`plid`,`name`),
  KEY `IX_6C226433` (`layoutSetBranchId`),
  KEY `IX_2C42603E` (`layoutSetBranchId`,`plid`),
  KEY `IX_A705FF94` (`layoutSetBranchId`,`plid`,`master`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layoutbranch`
--

LOCK TABLES `layoutbranch` WRITE;
/*!40000 ALTER TABLE `layoutbranch` DISABLE KEYS */;
/*!40000 ALTER TABLE `layoutbranch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layoutprototype`
--

DROP TABLE IF EXISTS `layoutprototype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `layoutprototype` (
  `uuid_` varchar(75) DEFAULT NULL,
  `layoutPrototypeId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `settings_` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`layoutPrototypeId`),
  KEY `IX_30616AAA` (`companyId`),
  KEY `IX_557A639F` (`companyId`,`active_`),
  KEY `IX_CEF72136` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layoutprototype`
--

LOCK TABLES `layoutprototype` WRITE;
/*!40000 ALTER TABLE `layoutprototype` DISABLE KEYS */;
INSERT INTO `layoutprototype` VALUES ('ce619381-bbb5-427f-8d1a-f8680f9cc99c',10309,10154,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Blog</Name></root>','Create, edit, and view blogs from this page. Explore topics using tags, and connect with other members that blog.','',1),('1d2fa2f0-6398-4fea-ba5f-29ab7df6f00e',10319,10154,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Content Display Page</Name></root>','Create, edit, and explore web content with this page. Search available content, explore related content with tags, and browse content categories.','',1),('b1e0d86b-08e5-4af9-b999-029b2c47818e',10328,10154,'<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Wiki</Name></root>','Collaborate with members through the wiki on this page. Discover related content through tags, and navigate quickly and easily with categories.','',1);
/*!40000 ALTER TABLE `layoutprototype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layoutrevision`
--

DROP TABLE IF EXISTS `layoutrevision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `layoutrevision` (
  `layoutRevisionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `layoutSetBranchId` bigint(20) DEFAULT NULL,
  `layoutBranchId` bigint(20) DEFAULT NULL,
  `parentLayoutRevisionId` bigint(20) DEFAULT NULL,
  `head` tinyint(4) DEFAULT NULL,
  `major` tinyint(4) DEFAULT NULL,
  `plid` bigint(20) DEFAULT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `name` longtext,
  `title` longtext,
  `description` longtext,
  `keywords` longtext,
  `robots` longtext,
  `typeSettings` longtext,
  `iconImage` tinyint(4) DEFAULT NULL,
  `iconImageId` bigint(20) DEFAULT NULL,
  `themeId` varchar(75) DEFAULT NULL,
  `colorSchemeId` varchar(75) DEFAULT NULL,
  `wapThemeId` varchar(75) DEFAULT NULL,
  `wapColorSchemeId` varchar(75) DEFAULT NULL,
  `css` longtext,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`layoutRevisionId`),
  KEY `IX_43E8286A` (`head`,`plid`),
  KEY `IX_314B621A` (`layoutSetBranchId`),
  KEY `IX_A9AC086E` (`layoutSetBranchId`,`head`),
  KEY `IX_E10AC39` (`layoutSetBranchId`,`head`,`plid`),
  KEY `IX_13984800` (`layoutSetBranchId`,`layoutBranchId`,`plid`),
  KEY `IX_4A84AF43` (`layoutSetBranchId`,`parentLayoutRevisionId`,`plid`),
  KEY `IX_B7B914E5` (`layoutSetBranchId`,`plid`),
  KEY `IX_70DA9ECB` (`layoutSetBranchId`,`plid`,`status`),
  KEY `IX_7FFAE700` (`layoutSetBranchId`,`status`),
  KEY `IX_9329C9D6` (`plid`),
  KEY `IX_8EC3D2BC` (`plid`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layoutrevision`
--

LOCK TABLES `layoutrevision` WRITE;
/*!40000 ALTER TABLE `layoutrevision` DISABLE KEYS */;
/*!40000 ALTER TABLE `layoutrevision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layoutset`
--

DROP TABLE IF EXISTS `layoutset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `layoutset` (
  `layoutSetId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `logo` tinyint(4) DEFAULT NULL,
  `logoId` bigint(20) DEFAULT NULL,
  `themeId` varchar(75) DEFAULT NULL,
  `colorSchemeId` varchar(75) DEFAULT NULL,
  `wapThemeId` varchar(75) DEFAULT NULL,
  `wapColorSchemeId` varchar(75) DEFAULT NULL,
  `css` longtext,
  `pageCount` int(11) DEFAULT NULL,
  `settings_` longtext,
  `layoutSetPrototypeUuid` varchar(75) DEFAULT NULL,
  `layoutSetPrototypeLinkEnabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`layoutSetId`),
  UNIQUE KEY `IX_48550691` (`groupId`,`privateLayout`),
  KEY `IX_A40B8BEC` (`groupId`),
  KEY `IX_72BBA8B7` (`layoutSetPrototypeUuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layoutset`
--

LOCK TABLES `layoutset` WRITE;
/*!40000 ALTER TABLE `layoutset` DISABLE KEYS */;
INSERT INTO `layoutset` VALUES (10173,10172,10154,'2012-08-14 02:47:55','2012-08-14 02:47:55',1,0,0,'classic','01','mobile','01','',1,'','',0),(10174,10172,10154,'2012-08-14 02:47:55','2012-08-14 02:47:55',0,0,0,'classic','01','mobile','01','',0,'','',0),(10181,10180,10154,'2012-08-14 02:47:55','2012-08-14 02:47:55',1,0,0,'classic','01','mobile','01','',0,'','',0),(10182,10180,10154,'2012-08-14 02:47:55','2012-11-26 07:16:37',0,0,0,'zoetech_WAR_zoetechtheme','01','mobile','01','',6,'lfr-theme:regular:display-footer=false\n','',0),(10190,10189,10154,'2012-08-14 02:47:55','2012-08-14 02:47:55',1,0,0,'classic','01','mobile','01','',0,'','',0),(10191,10189,10154,'2012-08-14 02:47:55','2012-08-14 02:47:55',0,0,0,'classic','01','mobile','01','',0,'','',0),(10193,10192,10154,'2012-08-14 02:47:55','2012-08-14 02:47:55',1,0,0,'classic','01','mobile','01','',0,'','',0),(10194,10192,10154,'2012-08-14 02:47:55','2012-08-14 02:47:55',0,0,0,'classic','01','mobile','01','',0,'','',0),(10199,10198,10154,'2012-08-14 02:47:55','2012-08-14 02:48:51',1,0,0,'classic','01','mobile','01','',1,'','',0),(10200,10198,10154,'2012-08-14 02:47:55','2012-08-14 02:48:51',0,0,0,'classic','01','mobile','01','',1,'','',0),(10311,10310,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,0,0,'classic','01','mobile','01','',1,'','',0),(10312,10310,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',0,0,0,'classic','01','mobile','01','',0,'','',0),(10321,10320,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,0,0,'classic','01','mobile','01','',1,'','',0),(10322,10320,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',0,0,0,'classic','01','mobile','01','',0,'','',0),(10330,10329,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,0,0,'classic','01','mobile','01','',1,'','',0),(10331,10329,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',0,0,0,'classic','01','mobile','01','',0,'','',0),(10339,10338,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,0,0,'classic','01','mobile','01','',3,'','',0),(10340,10338,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',0,0,0,'classic','01','mobile','01','',0,'','',0),(10365,10364,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',1,0,0,'classic','01','mobile','01','',4,'','',0),(10366,10364,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58',0,0,0,'classic','01','mobile','01','',0,'','',0),(16565,16564,10154,'2012-08-30 19:24:10','2012-08-30 19:24:59',1,0,0,'classic','01','mobile','01','',1,'','',0),(16566,16564,10154,'2012-08-30 19:24:10','2012-08-30 19:24:59',0,0,0,'classic','01','mobile','01','',1,'','',0),(32063,32062,10154,'2012-11-14 20:25:30','2012-11-14 20:25:34',1,0,0,'zoetech_WAR_zoetechtheme','01','mobile','01','',6,'','',0),(32064,32062,10154,'2012-11-14 20:25:30','2012-11-14 20:25:30',0,0,0,'classic','01','mobile','01','',0,'','',0);
/*!40000 ALTER TABLE `layoutset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layoutsetbranch`
--

DROP TABLE IF EXISTS `layoutsetbranch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `layoutsetbranch` (
  `layoutSetBranchId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `privateLayout` tinyint(4) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `master` tinyint(4) DEFAULT NULL,
  `logo` tinyint(4) DEFAULT NULL,
  `logoId` bigint(20) DEFAULT NULL,
  `themeId` varchar(75) DEFAULT NULL,
  `colorSchemeId` varchar(75) DEFAULT NULL,
  `wapThemeId` varchar(75) DEFAULT NULL,
  `wapColorSchemeId` varchar(75) DEFAULT NULL,
  `css` longtext,
  `settings_` longtext,
  `layoutSetPrototypeUuid` varchar(75) DEFAULT NULL,
  `layoutSetPrototypeLinkEnabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`layoutSetBranchId`),
  UNIQUE KEY `IX_5FF18552` (`groupId`,`privateLayout`,`name`),
  KEY `IX_8FF5D6EA` (`groupId`),
  KEY `IX_C4079FD3` (`groupId`,`privateLayout`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layoutsetbranch`
--

LOCK TABLES `layoutsetbranch` WRITE;
/*!40000 ALTER TABLE `layoutsetbranch` DISABLE KEYS */;
/*!40000 ALTER TABLE `layoutsetbranch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layoutsetprototype`
--

DROP TABLE IF EXISTS `layoutsetprototype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `layoutsetprototype` (
  `uuid_` varchar(75) DEFAULT NULL,
  `layoutSetPrototypeId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `settings_` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`layoutSetPrototypeId`),
  KEY `IX_55F63D98` (`companyId`),
  KEY `IX_9178FC71` (`companyId`,`active_`),
  KEY `IX_C5D69B24` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layoutsetprototype`
--

LOCK TABLES `layoutsetprototype` WRITE;
/*!40000 ALTER TABLE `layoutsetprototype` DISABLE KEYS */;
INSERT INTO `layoutsetprototype` VALUES ('cbf196ab-6ee2-429d-8a34-5337a9e1b874',10337,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Community Site</Name></root>','Site with Forums, Calendar and Wiki','layoutsUpdateable=true\n',1),('37e9e8d2-bd6d-4053-94d0-7a3d95e421b3',10363,10154,'2012-08-14 02:47:58','2012-08-14 02:47:58','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Intranet Site</Name></root>','Site with Documents, Calendar and News','layoutsUpdateable=true\n',1),('a9dba3fe-ee04-43af-ac15-f28e7054e98e',32061,10154,'2012-11-14 20:25:30','2012-11-14 20:25:34','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Name language-id=\"en_US\">Zoe Tech Theme</Name></root>','','layoutsUpdateable=true\n',1);
/*!40000 ALTER TABLE `layoutsetprototype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listtype`
--

DROP TABLE IF EXISTS `listtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listtype` (
  `listTypeId` int(11) NOT NULL,
  `name` varchar(75) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`listTypeId`),
  KEY `IX_2932DD37` (`type_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listtype`
--

LOCK TABLES `listtype` WRITE;
/*!40000 ALTER TABLE `listtype` DISABLE KEYS */;
INSERT INTO `listtype` VALUES (10000,'billing','com.liferay.portal.model.Account.address'),(10001,'other','com.liferay.portal.model.Account.address'),(10002,'p-o-box','com.liferay.portal.model.Account.address'),(10003,'shipping','com.liferay.portal.model.Account.address'),(10004,'email-address','com.liferay.portal.model.Account.emailAddress'),(10005,'email-address-2','com.liferay.portal.model.Account.emailAddress'),(10006,'email-address-3','com.liferay.portal.model.Account.emailAddress'),(10007,'fax','com.liferay.portal.model.Account.phone'),(10008,'local','com.liferay.portal.model.Account.phone'),(10009,'other','com.liferay.portal.model.Account.phone'),(10010,'toll-free','com.liferay.portal.model.Account.phone'),(10011,'tty','com.liferay.portal.model.Account.phone'),(10012,'intranet','com.liferay.portal.model.Account.website'),(10013,'public','com.liferay.portal.model.Account.website'),(11000,'business','com.liferay.portal.model.Contact.address'),(11001,'other','com.liferay.portal.model.Contact.address'),(11002,'personal','com.liferay.portal.model.Contact.address'),(11003,'email-address','com.liferay.portal.model.Contact.emailAddress'),(11004,'email-address-2','com.liferay.portal.model.Contact.emailAddress'),(11005,'email-address-3','com.liferay.portal.model.Contact.emailAddress'),(11006,'business','com.liferay.portal.model.Contact.phone'),(11007,'business-fax','com.liferay.portal.model.Contact.phone'),(11008,'mobile-phone','com.liferay.portal.model.Contact.phone'),(11009,'other','com.liferay.portal.model.Contact.phone'),(11010,'pager','com.liferay.portal.model.Contact.phone'),(11011,'personal','com.liferay.portal.model.Contact.phone'),(11012,'personal-fax','com.liferay.portal.model.Contact.phone'),(11013,'tty','com.liferay.portal.model.Contact.phone'),(11014,'dr','com.liferay.portal.model.Contact.prefix'),(11015,'mr','com.liferay.portal.model.Contact.prefix'),(11016,'mrs','com.liferay.portal.model.Contact.prefix'),(11017,'ms','com.liferay.portal.model.Contact.prefix'),(11020,'ii','com.liferay.portal.model.Contact.suffix'),(11021,'iii','com.liferay.portal.model.Contact.suffix'),(11022,'iv','com.liferay.portal.model.Contact.suffix'),(11023,'jr','com.liferay.portal.model.Contact.suffix'),(11024,'phd','com.liferay.portal.model.Contact.suffix'),(11025,'sr','com.liferay.portal.model.Contact.suffix'),(11026,'blog','com.liferay.portal.model.Contact.website'),(11027,'business','com.liferay.portal.model.Contact.website'),(11028,'other','com.liferay.portal.model.Contact.website'),(11029,'personal','com.liferay.portal.model.Contact.website'),(12000,'billing','com.liferay.portal.model.Organization.address'),(12001,'other','com.liferay.portal.model.Organization.address'),(12002,'p-o-box','com.liferay.portal.model.Organization.address'),(12003,'shipping','com.liferay.portal.model.Organization.address'),(12004,'email-address','com.liferay.portal.model.Organization.emailAddress'),(12005,'email-address-2','com.liferay.portal.model.Organization.emailAddress'),(12006,'email-address-3','com.liferay.portal.model.Organization.emailAddress'),(12007,'fax','com.liferay.portal.model.Organization.phone'),(12008,'local','com.liferay.portal.model.Organization.phone'),(12009,'other','com.liferay.portal.model.Organization.phone'),(12010,'toll-free','com.liferay.portal.model.Organization.phone'),(12011,'tty','com.liferay.portal.model.Organization.phone'),(12012,'administrative','com.liferay.portal.model.Organization.service'),(12013,'contracts','com.liferay.portal.model.Organization.service'),(12014,'donation','com.liferay.portal.model.Organization.service'),(12015,'retail','com.liferay.portal.model.Organization.service'),(12016,'training','com.liferay.portal.model.Organization.service'),(12017,'full-member','com.liferay.portal.model.Organization.status'),(12018,'provisional-member','com.liferay.portal.model.Organization.status'),(12019,'intranet','com.liferay.portal.model.Organization.website'),(12020,'public','com.liferay.portal.model.Organization.website');
/*!40000 ALTER TABLE `listtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lock_`
--

DROP TABLE IF EXISTS `lock_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lock_` (
  `uuid_` varchar(75) DEFAULT NULL,
  `lockId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `className` varchar(75) DEFAULT NULL,
  `key_` varchar(200) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `inheritable` tinyint(4) DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`lockId`),
  UNIQUE KEY `IX_DD635956` (`className`,`key_`,`owner`),
  KEY `IX_228562AD` (`className`,`key_`),
  KEY `IX_E3F1286B` (`expirationDate`),
  KEY `IX_13C5CD3A` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lock_`
--

LOCK TABLES `lock_` WRITE;
/*!40000 ALTER TABLE `lock_` DISABLE KEYS */;
/*!40000 ALTER TABLE `lock_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marketplace_app`
--

DROP TABLE IF EXISTS `marketplace_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marketplace_app` (
  `uuid_` varchar(75) DEFAULT NULL,
  `appId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `remoteAppId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`appId`),
  KEY `IX_865B7BD5` (`companyId`),
  KEY `IX_20F14D93` (`remoteAppId`),
  KEY `IX_3E667FE1` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marketplace_app`
--

LOCK TABLES `marketplace_app` WRITE;
/*!40000 ALTER TABLE `marketplace_app` DISABLE KEYS */;
INSERT INTO `marketplace_app` VALUES ('115bc12c-2277-4e8c-97cb-3986849658bb',32057,10154,10196,'Test Test','2012-11-14 20:25:00','2012-11-14 20:25:00',15849919,'1.0.1');
/*!40000 ALTER TABLE `marketplace_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marketplace_module`
--

DROP TABLE IF EXISTS `marketplace_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marketplace_module` (
  `uuid_` varchar(75) DEFAULT NULL,
  `moduleId` bigint(20) NOT NULL,
  `appId` bigint(20) DEFAULT NULL,
  `contextName` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`moduleId`),
  KEY `IX_7DC16D26` (`appId`),
  KEY `IX_C6938724` (`appId`,`contextName`),
  KEY `IX_F2F1E964` (`contextName`),
  KEY `IX_A7EFD80E` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marketplace_module`
--

LOCK TABLES `marketplace_module` WRITE;
/*!40000 ALTER TABLE `marketplace_module` DISABLE KEYS */;
INSERT INTO `marketplace_module` VALUES ('8ab70f9b-5eae-4b0a-9baf-79a460a48332',32058,32057,'1-3-1-columns-layouttpl'),('43db1a61-c168-471f-93c2-748890cb41fd',32059,32057,'resources-importer-web'),('c9aec5d5-55c9-427a-a76d-4f393ab87b64',32060,32057,'zoe-tech-theme');
/*!40000 ALTER TABLE `marketplace_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbban`
--

DROP TABLE IF EXISTS `mbban`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbban` (
  `banId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `banUserId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`banId`),
  UNIQUE KEY `IX_8ABC4E3B` (`groupId`,`banUserId`),
  KEY `IX_69951A25` (`banUserId`),
  KEY `IX_5C3FF12A` (`groupId`),
  KEY `IX_48814BBA` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbban`
--

LOCK TABLES `mbban` WRITE;
/*!40000 ALTER TABLE `mbban` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbban` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbcategory`
--

DROP TABLE IF EXISTS `mbcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbcategory` (
  `uuid_` varchar(75) DEFAULT NULL,
  `categoryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `parentCategoryId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `displayStyle` varchar(75) DEFAULT NULL,
  `threadCount` int(11) DEFAULT NULL,
  `messageCount` int(11) DEFAULT NULL,
  `lastPostDate` datetime DEFAULT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `IX_F7D28C2F` (`uuid_`,`groupId`),
  KEY `IX_BC735DCF` (`companyId`),
  KEY `IX_BB870C11` (`groupId`),
  KEY `IX_ED292508` (`groupId`,`parentCategoryId`),
  KEY `IX_C2626EDB` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbcategory`
--

LOCK TABLES `mbcategory` WRITE;
/*!40000 ALTER TABLE `mbcategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbdiscussion`
--

DROP TABLE IF EXISTS `mbdiscussion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbdiscussion` (
  `discussionId` bigint(20) NOT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `threadId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`discussionId`),
  UNIQUE KEY `IX_33A4DE38` (`classNameId`,`classPK`),
  UNIQUE KEY `IX_B5CA2DC` (`threadId`),
  KEY `IX_79D0120B` (`classNameId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbdiscussion`
--

LOCK TABLES `mbdiscussion` WRITE;
/*!40000 ALTER TABLE `mbdiscussion` DISABLE KEYS */;
INSERT INTO `mbdiscussion` VALUES (10179,10002,10175,10177),(10318,10002,10313,10315),(10327,10002,10323,10325),(10336,10002,10332,10334),(10350,10002,10346,10348),(10356,10002,10352,10354),(10362,10002,10358,10360),(10376,10002,10372,10374),(10384,10002,10380,10382),(10390,10002,10386,10388),(10396,10002,10392,10394),(10417,10010,10411,10415),(10426,10010,10419,10423),(10434,10010,10428,10432),(10442,10010,10436,10440),(10453,10010,10447,10451),(10462,10010,10455,10460),(10473,10010,10467,10471),(10485,10010,10479,10483),(10497,10010,10489,10495),(10508,10010,10499,10504),(10517,10010,10511,10515),(10529,10010,10523,10527),(10541,10010,10535,10539),(10558,10108,10548,10556),(10580,10108,10574,10578),(10592,10108,10586,10590),(10601,10002,10597,10599),(10607,10002,10603,10605),(10613,10002,10609,10611),(10623,10002,10619,10621),(10628,10002,10624,10626),(10639,10002,10635,10637),(11107,10010,11101,11105),(11115,10010,11109,11113),(16351,10002,16347,16349),(16575,10002,16571,16573),(16580,10002,16576,16578),(32076,10010,32070,32074),(32088,10010,32078,32086),(32100,10010,32090,32095),(32109,10010,32102,32107),(32120,10010,32114,32118),(32132,10010,32126,32130),(32144,10010,32138,32142),(32156,10010,32150,32154),(32168,10010,32162,32166),(32180,10010,32174,32178),(32192,10010,32186,32190),(32204,10010,32198,32202),(32216,10010,32210,32214),(32228,10010,32222,32226),(32240,10010,32234,32238),(32253,10010,32246,32250),(32269,10108,32263,32267),(32278,10108,32272,32276),(32286,10108,32280,32284),(32299,10108,32293,32297),(32312,10108,32306,32310),(32325,10108,32319,32323),(32333,10108,32327,32331),(32341,10108,32335,32339),(32354,10108,32348,32352),(32367,10108,32361,32365),(32372,10002,32368,32370),(32382,10002,32378,32380),(32387,10002,32383,32385),(32392,10002,32388,32390),(32397,10002,32393,32395),(32404,10002,32400,32402),(35205,10002,35201,35203),(35817,10010,35811,35815),(35826,10010,35819,35824),(35840,10010,35833,35837),(35854,10010,35845,35850),(36389,10010,36383,36387);
/*!40000 ALTER TABLE `mbdiscussion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbmailinglist`
--

DROP TABLE IF EXISTS `mbmailinglist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbmailinglist` (
  `uuid_` varchar(75) DEFAULT NULL,
  `mailingListId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `emailAddress` varchar(75) DEFAULT NULL,
  `inProtocol` varchar(75) DEFAULT NULL,
  `inServerName` varchar(75) DEFAULT NULL,
  `inServerPort` int(11) DEFAULT NULL,
  `inUseSSL` tinyint(4) DEFAULT NULL,
  `inUserName` varchar(75) DEFAULT NULL,
  `inPassword` varchar(75) DEFAULT NULL,
  `inReadInterval` int(11) DEFAULT NULL,
  `outEmailAddress` varchar(75) DEFAULT NULL,
  `outCustom` tinyint(4) DEFAULT NULL,
  `outServerName` varchar(75) DEFAULT NULL,
  `outServerPort` int(11) DEFAULT NULL,
  `outUseSSL` tinyint(4) DEFAULT NULL,
  `outUserName` varchar(75) DEFAULT NULL,
  `outPassword` varchar(75) DEFAULT NULL,
  `allowAnonymous` tinyint(4) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`mailingListId`),
  UNIQUE KEY `IX_76CE9CDD` (`groupId`,`categoryId`),
  UNIQUE KEY `IX_E858F170` (`uuid_`,`groupId`),
  KEY `IX_BFEB984F` (`active_`),
  KEY `IX_4115EC7A` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbmailinglist`
--

LOCK TABLES `mbmailinglist` WRITE;
/*!40000 ALTER TABLE `mbmailinglist` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbmailinglist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbmessage`
--

DROP TABLE IF EXISTS `mbmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbmessage` (
  `uuid_` varchar(75) DEFAULT NULL,
  `messageId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `threadId` bigint(20) DEFAULT NULL,
  `rootMessageId` bigint(20) DEFAULT NULL,
  `parentMessageId` bigint(20) DEFAULT NULL,
  `subject` varchar(75) DEFAULT NULL,
  `body` longtext,
  `format` varchar(75) DEFAULT NULL,
  `attachments` tinyint(4) DEFAULT NULL,
  `anonymous` tinyint(4) DEFAULT NULL,
  `priority` double DEFAULT NULL,
  `allowPingbacks` tinyint(4) DEFAULT NULL,
  `answer` tinyint(4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`messageId`),
  UNIQUE KEY `IX_8D12316E` (`uuid_`,`groupId`),
  KEY `IX_51A8D44D` (`classNameId`,`classPK`),
  KEY `IX_F6687633` (`classNameId`,`classPK`,`status`),
  KEY `IX_B1432D30` (`companyId`),
  KEY `IX_1AD93C16` (`companyId`,`status`),
  KEY `IX_5B153FB2` (`groupId`),
  KEY `IX_1073AB9F` (`groupId`,`categoryId`),
  KEY `IX_4257DB85` (`groupId`,`categoryId`,`status`),
  KEY `IX_B674AB58` (`groupId`,`categoryId`,`threadId`),
  KEY `IX_CBFDBF0A` (`groupId`,`categoryId`,`threadId`,`answer`),
  KEY `IX_385E123E` (`groupId`,`categoryId`,`threadId`,`status`),
  KEY `IX_ED39AC98` (`groupId`,`status`),
  KEY `IX_8EB8C5EC` (`groupId`,`userId`),
  KEY `IX_377858D2` (`groupId`,`userId`,`status`),
  KEY `IX_75B95071` (`threadId`),
  KEY `IX_9D7C3B23` (`threadId`,`answer`),
  KEY `IX_A7038CD7` (`threadId`,`parentMessageId`),
  KEY `IX_9DC8E57` (`threadId`,`status`),
  KEY `IX_7A040C32` (`userId`),
  KEY `IX_59F9CE5C` (`userId`,`classNameId`),
  KEY `IX_ABEB6D07` (`userId`,`classNameId`,`classPK`),
  KEY `IX_4A4BB4ED` (`userId`,`classNameId`,`classPK`,`status`),
  KEY `IX_3321F142` (`userId`,`classNameId`,`status`),
  KEY `IX_C57B16BC` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbmessage`
--

LOCK TABLES `mbmessage` WRITE;
/*!40000 ALTER TABLE `mbmessage` DISABLE KEYS */;
INSERT INTO `mbmessage` VALUES ('db48b56b-09ec-49fc-9b4b-c130cbe6a234',10176,10172,10154,10158,'','2012-08-14 02:47:55','2012-08-14 02:47:55',10002,10175,-1,10177,10176,0,'10175','10175','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:55'),('f78527a8-ec82-4916-89bf-0a9d9f636a49',10314,10310,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10002,10313,-1,10315,10314,0,'10313','10313','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:58'),('6aa6a7fe-b92f-44fc-8c28-e69ee3e15c6d',10324,10320,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10002,10323,-1,10325,10324,0,'10323','10323','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:58'),('7ab697de-2ad9-433a-b205-b80f31067cb1',10333,10329,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10002,10332,-1,10334,10333,0,'10332','10332','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:58'),('3a6f959d-4cfc-4fee-88e8-96aea94dd2a7',10347,10338,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10002,10346,-1,10348,10347,0,'10346','10346','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:58'),('1430ffc9-b18e-4de6-af84-41e753eeafc3',10353,10338,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10002,10352,-1,10354,10353,0,'10352','10352','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:58'),('3c142b2d-4aad-423c-ab69-ac2911c77d45',10359,10338,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10002,10358,-1,10360,10359,0,'10358','10358','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:58'),('d8f911e1-849f-47db-8328-84320e0a5cc8',10373,10364,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10002,10372,-1,10374,10373,0,'10372','10372','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:58'),('e028a04d-db81-4322-936b-2c5633a507c5',10381,10364,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10002,10380,-1,10382,10381,0,'10380','10380','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:58'),('db9f7369-4036-4cd3-8e83-1be5b1ca2120',10387,10364,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10002,10386,-1,10388,10387,0,'10386','10386','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:58'),('b0f86b58-09a9-4ab8-9141-7c868d2d9c1d',10393,10364,10154,10158,'','2012-08-14 02:47:58','2012-08-14 02:47:58',10002,10392,-1,10394,10393,0,'10392','10392','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:47:58'),('5da12679-de02-4591-a54e-44399eea4c9e',10414,10180,10154,10158,'','2012-08-14 02:48:00','2012-08-14 02:48:00',10010,10411,-1,10415,10414,0,'10411','10411','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:00'),('83ff0b6f-dbbe-4702-a972-69961ce1aff5',10422,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10419,-1,10423,10422,0,'10419','10419','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:01'),('6fb446c2-1132-40de-9316-6d35f228e10b',10431,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10428,-1,10432,10431,0,'10428','10428','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:01'),('3fe85993-e185-4c20-8abd-3a7a6677ae7e',10439,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10436,-1,10440,10439,0,'10436','10436','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:01'),('0a0b3176-e37a-473a-abcb-821521414e5d',10450,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10447,-1,10451,10450,0,'10447','10447','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:01'),('d3064ed3-3865-431f-800b-4ac1dd506c74',10459,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10455,-1,10460,10459,0,'10455','10455','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:01'),('d375f1c2-5a81-4b9b-afe0-aacaa3e8cffd',10470,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10467,-1,10471,10470,0,'10467','10467','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:01'),('364c3905-95f6-4f03-92ed-ffcc52ff3771',10482,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10479,-1,10483,10482,0,'10479','10479','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:01'),('2475b941-8b98-4515-a8b6-0aca1969e82a',10494,10180,10154,10158,'','2012-08-14 02:48:01','2012-08-14 02:48:01',10010,10489,-1,10495,10494,0,'10489','10489','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:01'),('63d3f7f2-5eb5-4cef-9def-e3a304127e3e',10503,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10010,10499,-1,10504,10503,0,'10499','10499','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:02'),('1263e3a3-f8b3-4f72-9a7c-5b59e0dd4b56',10514,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10010,10511,-1,10515,10514,0,'10511','10511','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:02'),('7ba59ca5-722b-4969-95c8-381e3d2673f2',10526,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10010,10523,-1,10527,10526,0,'10523','10523','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:02'),('9ad03b7f-5caf-463e-a9a3-406bcdeb644d',10538,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10010,10535,-1,10539,10538,0,'10535','10535','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:02'),('f72048c5-754f-49e4-8a02-33a7d2f0ae4a',10555,10180,10154,10158,'','2012-08-14 02:48:02','2012-08-14 02:48:02',10108,10548,-1,10556,10555,0,'10548','10548','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:02'),('3a119992-7292-4ee5-a881-cbb7811b4ca7',10577,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10108,10574,-1,10578,10577,0,'10574','10574','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:03'),('563baed0-ef55-4867-ad84-6c37219398ff',10589,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10108,10586,-1,10590,10589,0,'10586','10586','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:03'),('6a6a32f4-84f5-43f9-aa67-facf77699ba5',10598,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10002,10597,-1,10599,10598,0,'10597','10597','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:03'),('d6035795-ec9e-48f8-971f-8fdb344e2859',10604,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10002,10603,-1,10605,10604,0,'10603','10603','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:03'),('0e4ddbc0-38d0-4e22-841d-3b8b9274e15b',10610,10180,10154,10158,'','2012-08-14 02:48:03','2012-08-14 02:48:03',10002,10609,-1,10611,10610,0,'10609','10609','bbcode',0,1,0,0,0,0,10158,'','2012-08-14 02:48:03'),('bb330ec6-7091-4888-a3df-8e32edbc1008',10620,10198,10154,10196,'Test Test','2012-08-14 02:48:51','2012-08-14 02:48:51',10002,10619,-1,10621,10620,0,'10619','10619','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-08-14 02:48:51'),('29724955-220f-4cca-8ef8-df80da877925',10625,10198,10154,10196,'Test Test','2012-08-14 02:48:51','2012-08-14 02:48:51',10002,10624,-1,10626,10625,0,'10624','10624','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-08-14 02:48:51'),('bdb5239c-413c-4611-ba7b-e539f18e7f72',10636,10180,10154,10196,'Test Test','2012-08-14 02:50:26','2012-08-14 02:50:26',10002,10635,-1,10637,10636,0,'10635','10635','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-08-14 02:50:26'),('bee2f5df-0d13-49d2-960d-d8e0babbff0d',11104,10180,10154,10196,'Test Test','2012-08-17 16:18:07','2012-08-17 16:18:07',10010,11101,-1,11105,11104,0,'11101','11101','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-08-17 16:18:07'),('9ca680b8-2562-4f44-a751-79abd041806a',11112,10180,10154,10196,'Test Test','2012-08-17 16:18:08','2012-08-17 16:18:08',10010,11109,-1,11113,11112,0,'11109','11109','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-08-17 16:18:08'),('9354f51c-3aa4-4df2-a3b5-c4a3e8bdef87',16348,10180,10154,10196,'Test Test','2012-08-30 17:34:28','2012-08-30 17:34:28',10002,16347,-1,16349,16348,0,'16347','16347','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-08-30 17:34:28'),('d0d07113-9d56-4a7b-8638-2a7af1ed2dd3',16572,16564,10154,16562,'ConX User','2012-08-30 19:24:59','2012-08-30 19:24:59',10002,16571,-1,16573,16572,0,'16571','16571','bbcode',0,0,0,0,0,0,16562,'ConX User','2012-08-30 19:24:59'),('0cddc2d9-69f6-42d2-9fd5-09ee8f48299f',16577,16564,10154,16562,'ConX User','2012-08-30 19:24:59','2012-08-30 19:24:59',10002,16576,-1,16578,16577,0,'16576','16576','bbcode',0,0,0,0,0,0,16562,'ConX User','2012-08-30 19:24:59'),('90e7400f-3237-4bbb-ac50-00e99a9c0e5b',32073,32062,10154,10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30',10010,32070,-1,32074,32073,0,'32070','32070','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:30'),('be382441-506b-4761-8252-a2bccfce4653',32085,32062,10154,10158,'','2012-11-14 20:25:30','2012-11-14 20:25:30',10010,32078,-1,32086,32085,0,'32078','32078','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:30'),('7965a8fb-9565-464e-ab78-7de850dc328a',32094,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32090,-1,32095,32094,0,'32090','32090','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:31'),('4e123f9a-5193-44f8-b49b-dc14ba0c5954',32106,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32102,-1,32107,32106,0,'32102','32102','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:31'),('c488f683-7800-433d-b95b-2d76de0a616e',32117,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32114,-1,32118,32117,0,'32114','32114','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:31'),('083e31e6-d2f0-49bf-8f6e-02a61c02d69c',32129,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32126,-1,32130,32129,0,'32126','32126','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:31'),('a7587a3b-999d-44b1-9fdb-bbde50469307',32141,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32138,-1,32142,32141,0,'32138','32138','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:31'),('e17ff9f9-03cb-4a7b-acd4-e12f22a889d1',32153,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32150,-1,32154,32153,0,'32150','32150','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:31'),('cd02cc6d-808e-4832-971f-91b654b9b155',32165,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32162,-1,32166,32165,0,'32162','32162','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:31'),('9cfb6e6d-098b-4edc-929e-82b48100b8d3',32177,32062,10154,10158,'','2012-11-14 20:25:31','2012-11-14 20:25:31',10010,32174,-1,32178,32177,0,'32174','32174','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:31'),('252a952e-a3c6-453d-bdaa-24938ecb0f30',32189,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32186,-1,32190,32189,0,'32186','32186','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:32'),('22d37f7d-3210-4a52-806b-f7e618b86eba',32201,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32198,-1,32202,32201,0,'32198','32198','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:32'),('e2bf363b-4ece-40ac-93d4-0c37a3b24db2',32213,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32210,-1,32214,32213,0,'32210','32210','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:32'),('18f352f0-3c0f-4bf5-9714-d267a7a1277f',32225,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32222,-1,32226,32225,0,'32222','32222','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:32'),('90900c01-f708-4913-9a9d-a563f5e4f0ae',32237,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32234,-1,32238,32237,0,'32234','32234','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:32'),('1f60473a-48a1-485d-a5f8-13289d6f6dda',32249,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10010,32246,-1,32250,32249,0,'32246','32246','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:32'),('2a43fba2-247d-41f8-bfa4-dd3caccf7e2a',32266,32062,10154,10158,'','2012-11-14 20:25:32','2012-11-14 20:25:32',10108,32263,-1,32267,32266,0,'32263','32263','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:32'),('1065f7ec-9f7c-4606-9b34-0859630dfc39',32275,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32272,-1,32276,32275,0,'32272','32272','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:33'),('38ffe805-76d0-4807-af0a-db82cf9adf6b',32283,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32280,-1,32284,32283,0,'32280','32280','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:33'),('584d15fe-ca86-44a1-84ba-76862c7a9c6c',32296,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32293,-1,32297,32296,0,'32293','32293','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:33'),('e20a9ef8-fd87-49be-8712-c53cc6863bb6',32309,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32306,-1,32310,32309,0,'32306','32306','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:33'),('f13faf82-1183-406c-9265-7211a75ae4e0',32322,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32319,-1,32323,32322,0,'32319','32319','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:33'),('4b8c47aa-765f-4b52-9612-3d7bc635eea6',32330,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32327,-1,32331,32330,0,'32327','32327','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:33'),('5d32dad8-b618-4455-afad-0913c335fe26',32338,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32335,-1,32339,32338,0,'32335','32335','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:33'),('327fd929-c74a-4a62-904f-5266fffc861d',32351,32062,10154,10158,'','2012-11-14 20:25:33','2012-11-14 20:25:33',10108,32348,-1,32352,32351,0,'32348','32348','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:33'),('91ca03bb-16a2-46a0-b058-abb9ed1a977f',32364,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10108,32361,-1,32365,32364,0,'32361','32361','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:34'),('4393dec5-fa28-427c-aa1d-e5396f2f788f',32369,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10002,32368,-1,32370,32369,0,'32368','32368','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:34'),('c9f93382-d3eb-4ed6-98ef-6f0c56775027',32379,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10002,32378,-1,32380,32379,0,'32378','32378','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:34'),('5bc77444-839e-4153-82bf-700507e7a4bb',32384,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10002,32383,-1,32385,32384,0,'32383','32383','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:34'),('3aa4e0af-570f-4cca-9af1-45b971909137',32389,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10002,32388,-1,32390,32389,0,'32388','32388','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:34'),('1109453b-1356-40eb-8806-deca44253e71',32394,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10002,32393,-1,32395,32394,0,'32393','32393','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:34'),('0ddf65ac-1c28-42c9-bed1-8296384aa82f',32401,32062,10154,10158,'','2012-11-14 20:25:34','2012-11-14 20:25:34',10002,32400,-1,32402,32401,0,'32400','32400','bbcode',0,1,0,0,0,0,10158,'','2012-11-14 20:25:34'),('84b0a794-854d-4962-a1d4-fd4bdfd654f8',35202,10180,10154,10196,'Test Test','2012-11-26 07:16:37','2012-11-26 07:16:37',10002,35201,-1,35203,35202,0,'35201','35201','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-11-26 07:16:37'),('16f00f8d-f6e6-4f4b-98a2-b6b6725228fd',35814,10180,10154,10196,'Test Test','2012-11-30 12:54:03','2012-11-30 12:54:03',10010,35811,-1,35815,35814,0,'35811','35811','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-11-30 12:54:03'),('7fe17d30-f32a-42ad-8c7c-6da33a0cdbcd',35823,10180,10154,10196,'Test Test','2012-11-30 12:54:04','2012-11-30 12:54:04',10010,35819,-1,35824,35823,0,'35819','35819','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-11-30 12:54:04'),('3c7464d1-4a0c-499a-95cb-f67c87b65c06',35836,10180,10154,10196,'Test Test','2012-11-30 12:54:04','2012-11-30 12:54:04',10010,35833,-1,35837,35836,0,'35833','35833','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-11-30 12:54:04'),('e2fd7f9f-982f-47f2-a382-6dbc00db681b',35849,10180,10154,10196,'Test Test','2012-11-30 12:54:05','2012-11-30 12:54:05',10010,35845,-1,35850,35849,0,'35845','35845','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-11-30 12:54:05'),('bf12e44e-fc43-411d-b7db-898fe2f05562',36386,10180,10154,10196,'Test Test','2012-12-01 05:26:51','2012-12-01 05:26:51',10010,36383,-1,36387,36386,0,'36383','36383','bbcode',0,0,0,0,0,0,10196,'Test Test','2012-12-01 05:26:51');
/*!40000 ALTER TABLE `mbmessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbstatsuser`
--

DROP TABLE IF EXISTS `mbstatsuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbstatsuser` (
  `statsUserId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `messageCount` int(11) DEFAULT NULL,
  `lastPostDate` datetime DEFAULT NULL,
  PRIMARY KEY (`statsUserId`),
  UNIQUE KEY `IX_9168E2C9` (`groupId`,`userId`),
  KEY `IX_A00A898F` (`groupId`),
  KEY `IX_D33A5445` (`groupId`,`userId`,`messageCount`),
  KEY `IX_847F92B5` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbstatsuser`
--

LOCK TABLES `mbstatsuser` WRITE;
/*!40000 ALTER TABLE `mbstatsuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbstatsuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbthread`
--

DROP TABLE IF EXISTS `mbthread`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbthread` (
  `threadId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `rootMessageId` bigint(20) DEFAULT NULL,
  `rootMessageUserId` bigint(20) DEFAULT NULL,
  `messageCount` int(11) DEFAULT NULL,
  `viewCount` int(11) DEFAULT NULL,
  `lastPostByUserId` bigint(20) DEFAULT NULL,
  `lastPostDate` datetime DEFAULT NULL,
  `priority` double DEFAULT NULL,
  `question` tinyint(4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`threadId`),
  KEY `IX_41F6DC8A` (`categoryId`,`priority`),
  KEY `IX_95C0EA45` (`groupId`),
  KEY `IX_9A2D11B2` (`groupId`,`categoryId`),
  KEY `IX_50F1904A` (`groupId`,`categoryId`,`lastPostDate`),
  KEY `IX_485F7E98` (`groupId`,`categoryId`,`status`),
  KEY `IX_E1E7142B` (`groupId`,`status`),
  KEY `IX_AEDD9CB5` (`lastPostDate`,`priority`),
  KEY `IX_CC993ECB` (`rootMessageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbthread`
--

LOCK TABLES `mbthread` WRITE;
/*!40000 ALTER TABLE `mbthread` DISABLE KEYS */;
INSERT INTO `mbthread` VALUES (10177,10172,10154,-1,10176,10158,1,0,0,'2012-08-14 02:47:55',0,0,0,10158,'','2012-08-14 02:47:55'),(10315,10310,10154,-1,10314,10158,1,0,0,'2012-08-14 02:47:58',0,0,0,10158,'','2012-08-14 02:47:58'),(10325,10320,10154,-1,10324,10158,1,0,0,'2012-08-14 02:47:58',0,0,0,10158,'','2012-08-14 02:47:58'),(10334,10329,10154,-1,10333,10158,1,0,0,'2012-08-14 02:47:58',0,0,0,10158,'','2012-08-14 02:47:58'),(10348,10338,10154,-1,10347,10158,1,0,0,'2012-08-14 02:47:58',0,0,0,10158,'','2012-08-14 02:47:58'),(10354,10338,10154,-1,10353,10158,1,0,0,'2012-08-14 02:47:58',0,0,0,10158,'','2012-08-14 02:47:58'),(10360,10338,10154,-1,10359,10158,1,0,0,'2012-08-14 02:47:58',0,0,0,10158,'','2012-08-14 02:47:58'),(10374,10364,10154,-1,10373,10158,1,0,0,'2012-08-14 02:47:58',0,0,0,10158,'','2012-08-14 02:47:58'),(10382,10364,10154,-1,10381,10158,1,0,0,'2012-08-14 02:47:58',0,0,0,10158,'','2012-08-14 02:47:58'),(10388,10364,10154,-1,10387,10158,1,0,0,'2012-08-14 02:47:58',0,0,0,10158,'','2012-08-14 02:47:58'),(10394,10364,10154,-1,10393,10158,1,0,0,'2012-08-14 02:47:58',0,0,0,10158,'','2012-08-14 02:47:58'),(10415,10180,10154,-1,10414,10158,1,0,0,'2012-08-14 02:48:00',0,0,0,10158,'','2012-08-14 02:48:00'),(10423,10180,10154,-1,10422,10158,1,0,0,'2012-08-14 02:48:01',0,0,0,10158,'','2012-08-14 02:48:01'),(10432,10180,10154,-1,10431,10158,1,0,0,'2012-08-14 02:48:01',0,0,0,10158,'','2012-08-14 02:48:01'),(10440,10180,10154,-1,10439,10158,1,0,0,'2012-08-14 02:48:01',0,0,0,10158,'','2012-08-14 02:48:01'),(10451,10180,10154,-1,10450,10158,1,0,0,'2012-08-14 02:48:01',0,0,0,10158,'','2012-08-14 02:48:01'),(10460,10180,10154,-1,10459,10158,1,0,0,'2012-08-14 02:48:01',0,0,0,10158,'','2012-08-14 02:48:01'),(10471,10180,10154,-1,10470,10158,1,0,0,'2012-08-14 02:48:01',0,0,0,10158,'','2012-08-14 02:48:01'),(10483,10180,10154,-1,10482,10158,1,0,0,'2012-08-14 02:48:01',0,0,0,10158,'','2012-08-14 02:48:01'),(10495,10180,10154,-1,10494,10158,1,0,0,'2012-08-14 02:48:01',0,0,0,10158,'','2012-08-14 02:48:01'),(10504,10180,10154,-1,10503,10158,1,0,0,'2012-08-14 02:48:02',0,0,0,10158,'','2012-08-14 02:48:02'),(10515,10180,10154,-1,10514,10158,1,0,0,'2012-08-14 02:48:02',0,0,0,10158,'','2012-08-14 02:48:02'),(10527,10180,10154,-1,10526,10158,1,0,0,'2012-08-14 02:48:02',0,0,0,10158,'','2012-08-14 02:48:02'),(10539,10180,10154,-1,10538,10158,1,0,0,'2012-08-14 02:48:02',0,0,0,10158,'','2012-08-14 02:48:02'),(10556,10180,10154,-1,10555,10158,1,0,0,'2012-08-14 02:48:02',0,0,0,10158,'','2012-08-14 02:48:02'),(10578,10180,10154,-1,10577,10158,1,0,0,'2012-08-14 02:48:03',0,0,0,10158,'','2012-08-14 02:48:03'),(10590,10180,10154,-1,10589,10158,1,0,0,'2012-08-14 02:48:03',0,0,0,10158,'','2012-08-14 02:48:03'),(10599,10180,10154,-1,10598,10158,1,0,0,'2012-08-14 02:48:03',0,0,0,10158,'','2012-08-14 02:48:03'),(10605,10180,10154,-1,10604,10158,1,0,0,'2012-08-14 02:48:03',0,0,0,10158,'','2012-08-14 02:48:03'),(10611,10180,10154,-1,10610,10158,1,0,0,'2012-08-14 02:48:03',0,0,0,10158,'','2012-08-14 02:48:03'),(10621,10198,10154,-1,10620,10196,1,0,10196,'2012-08-14 02:48:51',0,0,0,10196,'Test Test','2012-08-14 02:48:51'),(10626,10198,10154,-1,10625,10196,1,0,10196,'2012-08-14 02:48:51',0,0,0,10196,'Test Test','2012-08-14 02:48:51'),(10637,10180,10154,-1,10636,10196,1,0,10196,'2012-08-14 02:50:26',0,0,0,10196,'Test Test','2012-08-14 02:50:26'),(11105,10180,10154,-1,11104,10196,1,0,10196,'2012-08-17 16:18:07',0,0,0,10196,'Test Test','2012-08-17 16:18:07'),(11113,10180,10154,-1,11112,10196,1,0,10196,'2012-08-17 16:18:08',0,0,0,10196,'Test Test','2012-08-17 16:18:08'),(16349,10180,10154,-1,16348,10196,1,0,10196,'2012-08-30 17:34:28',0,0,0,10196,'Test Test','2012-08-30 17:34:28'),(16573,16564,10154,-1,16572,16562,1,0,16562,'2012-08-30 19:24:59',0,0,0,16562,'ConX User','2012-08-30 19:24:59'),(16578,16564,10154,-1,16577,16562,1,0,16562,'2012-08-30 19:24:59',0,0,0,16562,'ConX User','2012-08-30 19:24:59'),(32074,32062,10154,-1,32073,10158,1,0,0,'2012-11-14 20:25:30',0,0,0,10158,'','2012-11-14 20:25:30'),(32086,32062,10154,-1,32085,10158,1,0,0,'2012-11-14 20:25:30',0,0,0,10158,'','2012-11-14 20:25:30'),(32095,32062,10154,-1,32094,10158,1,0,0,'2012-11-14 20:25:31',0,0,0,10158,'','2012-11-14 20:25:31'),(32107,32062,10154,-1,32106,10158,1,0,0,'2012-11-14 20:25:31',0,0,0,10158,'','2012-11-14 20:25:31'),(32118,32062,10154,-1,32117,10158,1,0,0,'2012-11-14 20:25:31',0,0,0,10158,'','2012-11-14 20:25:31'),(32130,32062,10154,-1,32129,10158,1,0,0,'2012-11-14 20:25:31',0,0,0,10158,'','2012-11-14 20:25:31'),(32142,32062,10154,-1,32141,10158,1,0,0,'2012-11-14 20:25:31',0,0,0,10158,'','2012-11-14 20:25:31'),(32154,32062,10154,-1,32153,10158,1,0,0,'2012-11-14 20:25:31',0,0,0,10158,'','2012-11-14 20:25:31'),(32166,32062,10154,-1,32165,10158,1,0,0,'2012-11-14 20:25:31',0,0,0,10158,'','2012-11-14 20:25:31'),(32178,32062,10154,-1,32177,10158,1,0,0,'2012-11-14 20:25:31',0,0,0,10158,'','2012-11-14 20:25:31'),(32190,32062,10154,-1,32189,10158,1,0,0,'2012-11-14 20:25:32',0,0,0,10158,'','2012-11-14 20:25:32'),(32202,32062,10154,-1,32201,10158,1,0,0,'2012-11-14 20:25:32',0,0,0,10158,'','2012-11-14 20:25:32'),(32214,32062,10154,-1,32213,10158,1,0,0,'2012-11-14 20:25:32',0,0,0,10158,'','2012-11-14 20:25:32'),(32226,32062,10154,-1,32225,10158,1,0,0,'2012-11-14 20:25:32',0,0,0,10158,'','2012-11-14 20:25:32'),(32238,32062,10154,-1,32237,10158,1,0,0,'2012-11-14 20:25:32',0,0,0,10158,'','2012-11-14 20:25:32'),(32250,32062,10154,-1,32249,10158,1,0,0,'2012-11-14 20:25:32',0,0,0,10158,'','2012-11-14 20:25:32'),(32267,32062,10154,-1,32266,10158,1,0,0,'2012-11-14 20:25:32',0,0,0,10158,'','2012-11-14 20:25:32'),(32276,32062,10154,-1,32275,10158,1,0,0,'2012-11-14 20:25:33',0,0,0,10158,'','2012-11-14 20:25:33'),(32284,32062,10154,-1,32283,10158,1,0,0,'2012-11-14 20:25:33',0,0,0,10158,'','2012-11-14 20:25:33'),(32297,32062,10154,-1,32296,10158,1,0,0,'2012-11-14 20:25:33',0,0,0,10158,'','2012-11-14 20:25:33'),(32310,32062,10154,-1,32309,10158,1,0,0,'2012-11-14 20:25:33',0,0,0,10158,'','2012-11-14 20:25:33'),(32323,32062,10154,-1,32322,10158,1,0,0,'2012-11-14 20:25:33',0,0,0,10158,'','2012-11-14 20:25:33'),(32331,32062,10154,-1,32330,10158,1,0,0,'2012-11-14 20:25:33',0,0,0,10158,'','2012-11-14 20:25:33'),(32339,32062,10154,-1,32338,10158,1,0,0,'2012-11-14 20:25:33',0,0,0,10158,'','2012-11-14 20:25:33'),(32352,32062,10154,-1,32351,10158,1,0,0,'2012-11-14 20:25:33',0,0,0,10158,'','2012-11-14 20:25:33'),(32365,32062,10154,-1,32364,10158,1,0,0,'2012-11-14 20:25:34',0,0,0,10158,'','2012-11-14 20:25:34'),(32370,32062,10154,-1,32369,10158,1,0,0,'2012-11-14 20:25:34',0,0,0,10158,'','2012-11-14 20:25:34'),(32380,32062,10154,-1,32379,10158,1,0,0,'2012-11-14 20:25:34',0,0,0,10158,'','2012-11-14 20:25:34'),(32385,32062,10154,-1,32384,10158,1,0,0,'2012-11-14 20:25:34',0,0,0,10158,'','2012-11-14 20:25:34'),(32390,32062,10154,-1,32389,10158,1,0,0,'2012-11-14 20:25:34',0,0,0,10158,'','2012-11-14 20:25:34'),(32395,32062,10154,-1,32394,10158,1,0,0,'2012-11-14 20:25:34',0,0,0,10158,'','2012-11-14 20:25:34'),(32402,32062,10154,-1,32401,10158,1,0,0,'2012-11-14 20:25:34',0,0,0,10158,'','2012-11-14 20:25:34'),(35203,10180,10154,-1,35202,10196,1,0,10196,'2012-11-26 07:16:37',0,0,0,10196,'Test Test','2012-11-26 07:16:37'),(35815,10180,10154,-1,35814,10196,1,0,10196,'2012-11-30 12:54:03',0,0,0,10196,'Test Test','2012-11-30 12:54:03'),(35824,10180,10154,-1,35823,10196,1,0,10196,'2012-11-30 12:54:04',0,0,0,10196,'Test Test','2012-11-30 12:54:04'),(35837,10180,10154,-1,35836,10196,1,0,10196,'2012-11-30 12:54:04',0,0,0,10196,'Test Test','2012-11-30 12:54:04'),(35850,10180,10154,-1,35849,10196,1,0,10196,'2012-11-30 12:54:05',0,0,0,10196,'Test Test','2012-11-30 12:54:05'),(36387,10180,10154,-1,36386,10196,1,0,10196,'2012-12-01 05:26:51',0,0,0,10196,'Test Test','2012-12-01 05:26:51');
/*!40000 ALTER TABLE `mbthread` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbthreadflag`
--

DROP TABLE IF EXISTS `mbthreadflag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbthreadflag` (
  `threadFlagId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `threadId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`threadFlagId`),
  KEY `IX_8CB0A24A` (`threadId`),
  KEY `IX_A28004B` (`userId`),
  KEY `IX_33781904` (`userId`,`threadId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbthreadflag`
--

LOCK TABLES `mbthreadflag` WRITE;
/*!40000 ALTER TABLE `mbthreadflag` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbthreadflag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mdraction`
--

DROP TABLE IF EXISTS `mdraction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mdraction` (
  `uuid_` varchar(75) DEFAULT NULL,
  `actionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `ruleGroupInstanceId` bigint(20) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `type_` varchar(255) DEFAULT NULL,
  `typeSettings` longtext,
  PRIMARY KEY (`actionId`),
  UNIQUE KEY `IX_75BE36AD` (`uuid_`,`groupId`),
  KEY `IX_FD90786C` (`ruleGroupInstanceId`),
  KEY `IX_77BB5E9D` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mdraction`
--

LOCK TABLES `mdraction` WRITE;
/*!40000 ALTER TABLE `mdraction` DISABLE KEYS */;
/*!40000 ALTER TABLE `mdraction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mdrrule`
--

DROP TABLE IF EXISTS `mdrrule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mdrrule` (
  `uuid_` varchar(75) DEFAULT NULL,
  `ruleId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `ruleGroupId` bigint(20) DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  `type_` varchar(255) DEFAULT NULL,
  `typeSettings` longtext,
  PRIMARY KEY (`ruleId`),
  UNIQUE KEY `IX_F3EFDCB3` (`uuid_`,`groupId`),
  KEY `IX_4F4293F1` (`ruleGroupId`),
  KEY `IX_EA63B9D7` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mdrrule`
--

LOCK TABLES `mdrrule` WRITE;
/*!40000 ALTER TABLE `mdrrule` DISABLE KEYS */;
/*!40000 ALTER TABLE `mdrrule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mdrrulegroup`
--

DROP TABLE IF EXISTS `mdrrulegroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mdrrulegroup` (
  `uuid_` varchar(75) DEFAULT NULL,
  `ruleGroupId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` longtext,
  `description` longtext,
  PRIMARY KEY (`ruleGroupId`),
  UNIQUE KEY `IX_46665CC4` (`uuid_`,`groupId`),
  KEY `IX_5849891C` (`groupId`),
  KEY `IX_7F26B2A6` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mdrrulegroup`
--

LOCK TABLES `mdrrulegroup` WRITE;
/*!40000 ALTER TABLE `mdrrulegroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `mdrrulegroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mdrrulegroupinstance`
--

DROP TABLE IF EXISTS `mdrrulegroupinstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mdrrulegroupinstance` (
  `uuid_` varchar(75) DEFAULT NULL,
  `ruleGroupInstanceId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `ruleGroupId` bigint(20) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`ruleGroupInstanceId`),
  UNIQUE KEY `IX_808A0036` (`classNameId`,`classPK`,`ruleGroupId`),
  UNIQUE KEY `IX_9CBC6A39` (`uuid_`,`groupId`),
  KEY `IX_C95A08D8` (`classNameId`,`classPK`),
  KEY `IX_AFF28547` (`groupId`),
  KEY `IX_22DAB85C` (`groupId`,`classNameId`,`classPK`),
  KEY `IX_BF3E642B` (`ruleGroupId`),
  KEY `IX_B6A6BD91` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mdrrulegroupinstance`
--

LOCK TABLES `mdrrulegroupinstance` WRITE;
/*!40000 ALTER TABLE `mdrrulegroupinstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `mdrrulegroupinstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `membershiprequest`
--

DROP TABLE IF EXISTS `membershiprequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `membershiprequest` (
  `membershipRequestId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `comments` longtext,
  `replyComments` longtext,
  `replyDate` datetime DEFAULT NULL,
  `replierUserId` bigint(20) DEFAULT NULL,
  `statusId` int(11) DEFAULT NULL,
  PRIMARY KEY (`membershipRequestId`),
  KEY `IX_8A1CC4B` (`groupId`),
  KEY `IX_C28C72EC` (`groupId`,`statusId`),
  KEY `IX_35AA8FA6` (`groupId`,`userId`,`statusId`),
  KEY `IX_66D70879` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membershiprequest`
--

LOCK TABLES `membershiprequest` WRITE;
/*!40000 ALTER TABLE `membershiprequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `membershiprequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_`
--

DROP TABLE IF EXISTS `organization_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization_` (
  `organizationId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `parentOrganizationId` bigint(20) DEFAULT NULL,
  `treePath` longtext,
  `name` varchar(100) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `recursable` tinyint(4) DEFAULT NULL,
  `regionId` bigint(20) DEFAULT NULL,
  `countryId` bigint(20) DEFAULT NULL,
  `statusId` int(11) DEFAULT NULL,
  `comments` longtext,
  PRIMARY KEY (`organizationId`),
  UNIQUE KEY `IX_E301BDF5` (`companyId`,`name`),
  KEY `IX_834BCEB6` (`companyId`),
  KEY `IX_418E4522` (`companyId`,`parentOrganizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_`
--

LOCK TABLES `organization_` WRITE;
/*!40000 ALTER TABLE `organization_` DISABLE KEYS */;
/*!40000 ALTER TABLE `organization_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orggrouppermission`
--

DROP TABLE IF EXISTS `orggrouppermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orggrouppermission` (
  `organizationId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  PRIMARY KEY (`organizationId`,`groupId`,`permissionId`),
  KEY `IX_A425F71A` (`groupId`),
  KEY `IX_6C53DA4E` (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orggrouppermission`
--

LOCK TABLES `orggrouppermission` WRITE;
/*!40000 ALTER TABLE `orggrouppermission` DISABLE KEYS */;
/*!40000 ALTER TABLE `orggrouppermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orggrouprole`
--

DROP TABLE IF EXISTS `orggrouprole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orggrouprole` (
  `organizationId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`organizationId`,`groupId`,`roleId`),
  KEY `IX_4A527DD3` (`groupId`),
  KEY `IX_AB044D1C` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orggrouprole`
--

LOCK TABLES `orggrouprole` WRITE;
/*!40000 ALTER TABLE `orggrouprole` DISABLE KEYS */;
/*!40000 ALTER TABLE `orggrouprole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orglabor`
--

DROP TABLE IF EXISTS `orglabor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orglabor` (
  `orgLaborId` bigint(20) NOT NULL,
  `organizationId` bigint(20) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `sunOpen` int(11) DEFAULT NULL,
  `sunClose` int(11) DEFAULT NULL,
  `monOpen` int(11) DEFAULT NULL,
  `monClose` int(11) DEFAULT NULL,
  `tueOpen` int(11) DEFAULT NULL,
  `tueClose` int(11) DEFAULT NULL,
  `wedOpen` int(11) DEFAULT NULL,
  `wedClose` int(11) DEFAULT NULL,
  `thuOpen` int(11) DEFAULT NULL,
  `thuClose` int(11) DEFAULT NULL,
  `friOpen` int(11) DEFAULT NULL,
  `friClose` int(11) DEFAULT NULL,
  `satOpen` int(11) DEFAULT NULL,
  `satClose` int(11) DEFAULT NULL,
  PRIMARY KEY (`orgLaborId`),
  KEY `IX_6AF0D434` (`organizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orglabor`
--

LOCK TABLES `orglabor` WRITE;
/*!40000 ALTER TABLE `orglabor` DISABLE KEYS */;
/*!40000 ALTER TABLE `orglabor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passwordpolicy`
--

DROP TABLE IF EXISTS `passwordpolicy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passwordpolicy` (
  `passwordPolicyId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `defaultPolicy` tinyint(4) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `changeable` tinyint(4) DEFAULT NULL,
  `changeRequired` tinyint(4) DEFAULT NULL,
  `minAge` bigint(20) DEFAULT NULL,
  `checkSyntax` tinyint(4) DEFAULT NULL,
  `allowDictionaryWords` tinyint(4) DEFAULT NULL,
  `minAlphanumeric` int(11) DEFAULT NULL,
  `minLength` int(11) DEFAULT NULL,
  `minLowerCase` int(11) DEFAULT NULL,
  `minNumbers` int(11) DEFAULT NULL,
  `minSymbols` int(11) DEFAULT NULL,
  `minUpperCase` int(11) DEFAULT NULL,
  `history` tinyint(4) DEFAULT NULL,
  `historyCount` int(11) DEFAULT NULL,
  `expireable` tinyint(4) DEFAULT NULL,
  `maxAge` bigint(20) DEFAULT NULL,
  `warningTime` bigint(20) DEFAULT NULL,
  `graceLimit` int(11) DEFAULT NULL,
  `lockout` tinyint(4) DEFAULT NULL,
  `maxFailure` int(11) DEFAULT NULL,
  `lockoutDuration` bigint(20) DEFAULT NULL,
  `requireUnlock` tinyint(4) DEFAULT NULL,
  `resetFailureCount` bigint(20) DEFAULT NULL,
  `resetTicketMaxAge` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`passwordPolicyId`),
  UNIQUE KEY `IX_3FBFA9F4` (`companyId`,`name`),
  KEY `IX_2C1142E` (`companyId`,`defaultPolicy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passwordpolicy`
--

LOCK TABLES `passwordpolicy` WRITE;
/*!40000 ALTER TABLE `passwordpolicy` DISABLE KEYS */;
INSERT INTO `passwordpolicy` VALUES (10195,10154,10158,'','2012-08-14 02:47:55','2012-08-14 02:47:55',1,'Default Password Policy','Default Password Policy',1,1,0,0,1,0,6,0,1,0,1,0,6,0,8640000,86400,0,0,3,0,1,600,86400);
/*!40000 ALTER TABLE `passwordpolicy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passwordpolicyrel`
--

DROP TABLE IF EXISTS `passwordpolicyrel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passwordpolicyrel` (
  `passwordPolicyRelId` bigint(20) NOT NULL,
  `passwordPolicyId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`passwordPolicyRelId`),
  KEY `IX_C3A17327` (`classNameId`,`classPK`),
  KEY `IX_CD25266E` (`passwordPolicyId`),
  KEY `IX_ED7CF243` (`passwordPolicyId`,`classNameId`,`classPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passwordpolicyrel`
--

LOCK TABLES `passwordpolicyrel` WRITE;
/*!40000 ALTER TABLE `passwordpolicyrel` DISABLE KEYS */;
/*!40000 ALTER TABLE `passwordpolicyrel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passwordtracker`
--

DROP TABLE IF EXISTS `passwordtracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passwordtracker` (
  `passwordTrackerId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `password_` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`passwordTrackerId`),
  KEY `IX_326F75BD` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passwordtracker`
--

LOCK TABLES `passwordtracker` WRITE;
/*!40000 ALTER TABLE `passwordtracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `passwordtracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_`
--

DROP TABLE IF EXISTS `permission_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_` (
  `permissionId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `actionId` varchar(75) DEFAULT NULL,
  `resourceId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`permissionId`),
  UNIQUE KEY `IX_4D19C2B8` (`actionId`,`resourceId`),
  KEY `IX_F090C113` (`resourceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_`
--

LOCK TABLES `permission_` WRITE;
/*!40000 ALTER TABLE `permission_` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone`
--

DROP TABLE IF EXISTS `phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone` (
  `phoneId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `number_` varchar(75) DEFAULT NULL,
  `extension` varchar(75) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `primary_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`phoneId`),
  KEY `IX_9F704A14` (`companyId`),
  KEY `IX_A2E4AFBA` (`companyId`,`classNameId`),
  KEY `IX_9A53569` (`companyId`,`classNameId`,`classPK`),
  KEY `IX_812CE07A` (`companyId`,`classNameId`,`classPK`,`primary_`),
  KEY `IX_F202B9CE` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone`
--

LOCK TABLES `phone` WRITE;
/*!40000 ALTER TABLE `phone` DISABLE KEYS */;
/*!40000 ALTER TABLE `phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pluginsetting`
--

DROP TABLE IF EXISTS `pluginsetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pluginsetting` (
  `pluginSettingId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `pluginId` varchar(75) DEFAULT NULL,
  `pluginType` varchar(75) DEFAULT NULL,
  `roles` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`pluginSettingId`),
  UNIQUE KEY `IX_7171B2E8` (`companyId`,`pluginId`,`pluginType`),
  KEY `IX_B9746445` (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pluginsetting`
--

LOCK TABLES `pluginsetting` WRITE;
/*!40000 ALTER TABLE `pluginsetting` DISABLE KEYS */;
/*!40000 ALTER TABLE `pluginsetting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pollschoice`
--

DROP TABLE IF EXISTS `pollschoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pollschoice` (
  `uuid_` varchar(75) DEFAULT NULL,
  `choiceId` bigint(20) NOT NULL,
  `questionId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`choiceId`),
  UNIQUE KEY `IX_D76DD2CF` (`questionId`,`name`),
  KEY `IX_EC370F10` (`questionId`),
  KEY `IX_6660B399` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pollschoice`
--

LOCK TABLES `pollschoice` WRITE;
/*!40000 ALTER TABLE `pollschoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `pollschoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pollsquestion`
--

DROP TABLE IF EXISTS `pollsquestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pollsquestion` (
  `uuid_` varchar(75) DEFAULT NULL,
  `questionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `title` longtext,
  `description` longtext,
  `expirationDate` datetime DEFAULT NULL,
  `lastVoteDate` datetime DEFAULT NULL,
  PRIMARY KEY (`questionId`),
  UNIQUE KEY `IX_F3C9F36` (`uuid_`,`groupId`),
  KEY `IX_9FF342EA` (`groupId`),
  KEY `IX_51F087F4` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pollsquestion`
--

LOCK TABLES `pollsquestion` WRITE;
/*!40000 ALTER TABLE `pollsquestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `pollsquestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pollsvote`
--

DROP TABLE IF EXISTS `pollsvote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pollsvote` (
  `voteId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `questionId` bigint(20) DEFAULT NULL,
  `choiceId` bigint(20) DEFAULT NULL,
  `voteDate` datetime DEFAULT NULL,
  PRIMARY KEY (`voteId`),
  UNIQUE KEY `IX_1BBFD4D3` (`questionId`,`userId`),
  KEY `IX_D5DF7B54` (`choiceId`),
  KEY `IX_12112599` (`questionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pollsvote`
--

LOCK TABLES `pollsvote` WRITE;
/*!40000 ALTER TABLE `pollsvote` DISABLE KEYS */;
/*!40000 ALTER TABLE `pollsvote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portalpreferences`
--

DROP TABLE IF EXISTS `portalpreferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portalpreferences` (
  `portalPreferencesId` bigint(20) NOT NULL,
  `ownerId` bigint(20) DEFAULT NULL,
  `ownerType` int(11) DEFAULT NULL,
  `preferences` longtext,
  PRIMARY KEY (`portalPreferencesId`),
  KEY `IX_D1F795F1` (`ownerId`,`ownerType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portalpreferences`
--

LOCK TABLES `portalpreferences` WRITE;
/*!40000 ALTER TABLE `portalpreferences` DISABLE KEYS */;
INSERT INTO `portalpreferences` VALUES (10153,0,1,'<portlet-preferences />'),(10160,10154,1,'<portlet-preferences />'),(10616,10158,4,'<portlet-preferences />'),(10629,10196,4,'<portlet-preferences><preference><name>com.liferay.portal.util.SessionClicks#iframeGeneralPanel</name><value>open</value></preference><preference><name>com.liferay.portal.util.SessionClicks#uzji</name><value>open</value></preference><preference><name>20#display-style</name><value>list</value></preference><preference><name>com.liferay.portal.util.SessionClicks#iframeDisplaySettingsPanel</name><value>open</value></preference><preference><name>com.liferay.portal.util.SessionClicks#iframeAuthenticationPanel</name><value>closed</value></preference></portlet-preferences>'),(16581,16562,4,'<portlet-preferences />');
/*!40000 ALTER TABLE `portalpreferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portlet`
--

DROP TABLE IF EXISTS `portlet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portlet` (
  `id_` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `portletId` varchar(200) DEFAULT NULL,
  `roles` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `IX_12B5E51D` (`companyId`,`portletId`),
  KEY `IX_80CC9508` (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portlet`
--

LOCK TABLES `portlet` WRITE;
/*!40000 ALTER TABLE `portlet` DISABLE KEYS */;
INSERT INTO `portlet` VALUES (10202,10154,'98','',1),(10203,10154,'66','',1),(10204,10154,'180','',1),(10205,10154,'27','',1),(10206,10154,'152','',1),(10207,10154,'134','',1),(10208,10154,'130','',1),(10209,10154,'122','',1),(10210,10154,'36','',1),(10211,10154,'26','',1),(10212,10154,'104','',1),(10213,10154,'175','',1),(10214,10154,'153','',1),(10215,10154,'64','',1),(10216,10154,'129','',1),(10217,10154,'179','',1),(10218,10154,'173','',1),(10219,10154,'100','',1),(10220,10154,'19','',1),(10221,10154,'157','',1),(10222,10154,'128','',1),(10223,10154,'181','',1),(10224,10154,'154','',1),(10225,10154,'148','',1),(10226,10154,'11','',1),(10227,10154,'29','',1),(10228,10154,'158','',1),(10229,10154,'178','',1),(10230,10154,'8','',1),(10231,10154,'58','',1),(10232,10154,'71','',1),(10233,10154,'97','',1),(10234,10154,'39','',1),(10235,10154,'177','',1),(10236,10154,'85','',1),(10237,10154,'118','',1),(10238,10154,'107','',1),(10239,10154,'30','',1),(10240,10154,'147','',1),(10241,10154,'48','',1),(10242,10154,'125','',1),(10243,10154,'161','',1),(10244,10154,'146','',1),(10245,10154,'62','',1),(10246,10154,'162','',1),(10247,10154,'176','',1),(10248,10154,'108','',1),(10249,10154,'84','',1),(10250,10154,'101','',1),(10251,10154,'121','',1),(10252,10154,'143','',1),(10253,10154,'77','',1),(10254,10154,'167','',1),(10255,10154,'115','',1),(10256,10154,'56','',1),(10257,10154,'16','',1),(10258,10154,'111','',1),(10259,10154,'3','',1),(10260,10154,'23','',1),(10261,10154,'20','',1),(10262,10154,'83','',1),(10263,10154,'164','',1),(10264,10154,'99','',1),(10265,10154,'70','',1),(10266,10154,'141','',1),(10267,10154,'9','',1),(10268,10154,'28','',1),(10269,10154,'137','',1),(10270,10154,'47','',1),(10271,10154,'15','',1),(10272,10154,'116','',1),(10273,10154,'82','',1),(10274,10154,'151','',1),(10275,10154,'54','',1),(10276,10154,'34','',1),(10277,10154,'169','',1),(10278,10154,'132','',1),(10279,10154,'61','',1),(10280,10154,'73','',1),(10281,10154,'31','',1),(10282,10154,'136','',1),(10283,10154,'50','',1),(10284,10154,'127','',1),(10285,10154,'25','',1),(10286,10154,'166','',1),(10287,10154,'33','',1),(10288,10154,'150','',1),(10289,10154,'114','',1),(10290,10154,'149','',1),(10291,10154,'67','',1),(10292,10154,'110','',1),(10293,10154,'59','',1),(10294,10154,'135','',1),(10295,10154,'131','',1),(10296,10154,'102','',1);
/*!40000 ALTER TABLE `portlet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portletitem`
--

DROP TABLE IF EXISTS `portletitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portletitem` (
  `portletItemId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `portletId` varchar(75) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`portletItemId`),
  KEY `IX_96BDD537` (`groupId`,`classNameId`),
  KEY `IX_D699243F` (`groupId`,`name`,`portletId`,`classNameId`),
  KEY `IX_2C61314E` (`groupId`,`portletId`),
  KEY `IX_E922D6C0` (`groupId`,`portletId`,`classNameId`),
  KEY `IX_8E71167F` (`groupId`,`portletId`,`classNameId`,`name`),
  KEY `IX_33B8CE8D` (`groupId`,`portletId`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portletitem`
--

LOCK TABLES `portletitem` WRITE;
/*!40000 ALTER TABLE `portletitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `portletitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portletpreferences`
--

DROP TABLE IF EXISTS `portletpreferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portletpreferences` (
  `portletPreferencesId` bigint(20) NOT NULL,
  `ownerId` bigint(20) DEFAULT NULL,
  `ownerType` int(11) DEFAULT NULL,
  `plid` bigint(20) DEFAULT NULL,
  `portletId` varchar(200) DEFAULT NULL,
  `preferences` longtext,
  PRIMARY KEY (`portletPreferencesId`),
  UNIQUE KEY `IX_C7057FF7` (`ownerId`,`ownerType`,`plid`,`portletId`),
  KEY `IX_E4F13E6E` (`ownerId`,`ownerType`,`plid`),
  KEY `IX_D5EDA3A1` (`ownerType`,`plid`,`portletId`),
  KEY `IX_F15C1C4F` (`plid`),
  KEY `IX_D340DB76` (`plid`,`portletId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portletpreferences`
--

LOCK TABLES `portletpreferences` WRITE;
/*!40000 ALTER TABLE `portletpreferences` DISABLE KEYS */;
INSERT INTO `portletpreferences` VALUES (10351,0,3,10346,'3','<portlet-preferences><preference><name>portletSetupShowBorders</name><value>false</value></preference></portlet-preferences>'),(10357,0,3,10352,'101_INSTANCE_XGmIipFP7be1','<portlet-preferences><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>anyAssetType</name><value>false</value></preference><preference><name>classNameIds</name><value>10009</value></preference><preference><name>portletSetupTitle_en_US</name><value>Upcoming Events</value></preference></portlet-preferences>'),(10377,0,3,10372,'3','<portlet-preferences><preference><name>portletSetupShowBorders</name><value>false</value></preference></portlet-preferences>'),(10378,0,3,10372,'82','<portlet-preferences><preference><name>displayStyle</name><value>3</value></preference></portlet-preferences>'),(10379,0,3,10372,'101_INSTANCE_D2SSkvmZJPDZ','<portlet-preferences><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>portletSetupTitle_en_US</name><value>Recent Content</value></preference></portlet-preferences>'),(10385,0,3,10380,'20','<portlet-preferences><preference><name>portletSetupShowBorders</name><value>false</value></preference></portlet-preferences>'),(10391,0,3,10386,'101_INSTANCE_9jLPIIg9a3aK','<portlet-preferences><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>anyAssetType</name><value>false</value></preference><preference><name>classNameIds</name><value>10009</value></preference><preference><name>portletSetupTitle_en_US</name><value>Upcoming Events</value></preference></portlet-preferences>'),(10397,0,3,10392,'39_INSTANCE_z51mVQ48aMJ7','<portlet-preferences><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>expandedEntriesPerFeed</name><value>3</value></preference><preference><name>urls</name><value>http://partners.userland.com/nytRss/technology.xml</value></preference><preference><name>items-per-channel</name><value>2</value></preference><preference><name>portletSetupTitle_en_US</name><value>Technology news</value></preference></portlet-preferences>'),(10398,0,3,10392,'39_INSTANCE_Dd0JhJKWcWOw','<portlet-preferences><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>expandedEntriesPerFeed</name><value>0</value></preference><preference><name>urls</name><value>http://www.liferay.com/en/about-us/news/-/blogs/rss</value></preference><preference><name>titles</name><value>Liferay Press Releases</value></preference><preference><name>items-per-channel</name><value>2</value></preference><preference><name>portletSetupTitle_en_US</name><value>Liferay news</value></preference></portlet-preferences>'),(10563,10180,2,0,'15','<portlet-preferences />'),(10608,0,3,10603,'56_INSTANCE_aTzDuB3geL4w','<portlet-preferences><preference><name>articleId</name><value>WHO-IS-USING-LIFERAY</value></preference><preference><name>portletSetupShowBorders</name><value>false</value></preference><preference><name>groupId</name><value>10180</value></preference></portlet-preferences>'),(10614,0,3,10609,'56_INSTANCE_KbtAD0VeRh9k','<portlet-preferences><preference><name>articleId</name><value>LIFERAY-BENEFITS</value></preference><preference><name>portletSetupShowBorders</name><value>false</value></preference><preference><name>groupId</name><value>10180</value></preference></portlet-preferences>'),(10615,0,3,10597,'103','<portlet-preferences />'),(10618,0,3,10597,'58','<portlet-preferences />'),(10630,0,3,10597,'145','<portlet-preferences />'),(10631,0,3,10597,'terms-of-use','<portlet-preferences />'),(10632,0,3,10597,'password-reminder','<portlet-preferences />'),(10633,0,3,10175,'88','<portlet-preferences />'),(10634,0,3,10597,'88','<portlet-preferences />'),(10640,0,3,10635,'103','<portlet-preferences />'),(10641,0,3,10635,'145','<portlet-preferences />'),(10642,0,3,10635,'87','<portlet-preferences />'),(10643,0,3,10635,'20','<portlet-preferences />'),(10703,0,3,10635,'58','<portlet-preferences />'),(16352,0,3,16347,'103','<portlet-preferences />'),(16353,0,3,16347,'145','<portlet-preferences />'),(16354,0,3,10175,'1_WAR_marketplaceportlet','<portlet-preferences />'),(16355,0,3,10175,'160','<portlet-preferences />'),(16401,0,3,10175,'145','<portlet-preferences />'),(16402,0,3,10175,'98','<portlet-preferences />'),(16403,0,3,16347,'49','<portlet-preferences />'),(16404,0,3,16347,'58','<portlet-preferences />'),(16557,0,3,16347,'87','<portlet-preferences />'),(16559,0,3,16347,'86','<portlet-preferences />'),(16560,0,3,16347,'113','<portlet-preferences />'),(16561,0,3,10175,'125','<portlet-preferences />'),(16582,0,3,10597,'new-password','<portlet-preferences />'),(16625,0,3,16347,'48','<portlet-preferences xmlns=\"http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd\">\n			<preference>\n				<name>auth</name>\n				<value>false</value>\n			</preference>\n			<preference>\n				<name>auth-type</name>\n				<value>basic</value>\n			</preference>\n			<preference>\n				<name>form-method</name>\n				<value>post</value>\n			</preference>\n			<preference>\n				<name>hidden-variables</name>\n				<value>var1=hello;var2=world</value>\n			</preference>\n			<preference>\n				<name>password</name>\n				<value/>\n			</preference>\n			<preference>\n				<name>src</name>\n				<value/>\n			</preference>\n			<preference>\n				<name>user-name</name>\n				<value/>\n			</preference>\n		</portlet-preferences>'),(16626,0,3,10597,'48','<portlet-preferences xmlns=\"http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd\">\n			<preference>\n				<name>auth</name>\n				<value>false</value>\n			</preference>\n			<preference>\n				<name>auth-type</name>\n				<value>basic</value>\n			</preference>\n			<preference>\n				<name>form-method</name>\n				<value>post</value>\n			</preference>\n			<preference>\n				<name>hidden-variables</name>\n				<value>var1=hello;var2=world</value>\n			</preference>\n			<preference>\n				<name>password</name>\n				<value/>\n			</preference>\n			<preference>\n				<name>src</name>\n				<value/>\n			</preference>\n			<preference>\n				<name>user-name</name>\n				<value/>\n			</preference>\n		</portlet-preferences>'),(17701,0,3,10597,'status','<portlet-preferences />'),(19601,0,3,16347,'48_INSTANCE_QchntqUvZ1JY','<portlet-preferences><preference><name>passwordField</name><value>pwd</value></preference><preference><name>authType</name><value>form</value></preference><preference><name>form-method</name><value>post</value></preference><preference><name>height</name><value>100%</value></preference><preference><name>formMethod</name><value>get</value></preference><preference><name>portletSetupUseCustomTitle</name><value>true</value></preference><preference><name>lfrWapInitialWindowState</name><value>NORMAL</value></preference><preference><name>formPassword</name><value>@password@</value></preference><preference><name>width</name><value>100%</value></preference><preference><name>userNameField</name><value>email</value></preference><preference><name>alt</name><value></value></preference><preference><name>basicPassword</name><value>@password@</value></preference><preference><name>hspace</name><value>0</value></preference><preference><name>formUserName</name><value>@email_address@</value></preference><preference><name>resizeAutomatically</name><value>true</value></preference><preference><name>user-name</name><value></value></preference><preference><name>hiddenVariables</name><value></value></preference><preference><name>heightMaximized</name><value>600</value></preference><preference><name>border</name><value>0</value></preference><preference><name>lfrWapTitle</name><value></value></preference><preference><name>scrolling</name><value>auto</value></preference><preference><name>src</name><value>http://localhost:9080/logistics2.0.2.RC1</value></preference><preference><name>hidden-variables</name><value>var1=hello;var2=world</value></preference><preference><name>vspace</name><value>0</value></preference><preference><name>frameborder</name><value>0</value></preference><preference><name>bordercolor</name><value>#000000</value></preference><preference><name>auth-type</name><value>basic</value></preference><preference><name>portletSetupCss</name><value>{&#034;wapData&#034;:{&#034;title&#034;:&#034;&#034;,&#034;initialWindowState&#034;:&#034;NORMAL&#034;},&#034;hasCssValue&#034;:true,&#034;spacingData&#034;:{&#034;margin&#034;:{&#034;sameForAll&#034;:false,&#034;bottom&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0},&#034;left&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0},&#034;right&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0},&#034;top&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0}},&#034;padding&#034;:{&#034;sameForAll&#034;:false,&#034;bottom&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0},&#034;left&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0},&#034;right&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0},&#034;top&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0}}},&#034;borderData&#034;:{&#034;borderStyle&#034;:{&#034;sameForAll&#034;:false,&#034;bottom&#034;:&#034;&#034;,&#034;left&#034;:&#034;&#034;,&#034;right&#034;:&#034;&#034;,&#034;top&#034;:&#034;&#034;},&#034;borderColor&#034;:{&#034;sameForAll&#034;:false,&#034;bottom&#034;:&#034;&#034;,&#034;left&#034;:&#034;&#034;,&#034;right&#034;:&#034;&#034;,&#034;top&#034;:&#034;&#034;},&#034;borderWidth&#034;:{&#034;sameForAll&#034;:false,&#034;bottom&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0},&#034;left&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0},&#034;right&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0},&#034;top&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:0}}},&#034;textData&#034;:{&#034;fontWeight&#034;:&#034;&#034;,&#034;lineHeight&#034;:&#034;&#034;,&#034;textDecoration&#034;:&#034;&#034;,&#034;letterSpacing&#034;:&#034;&#034;,&#034;color&#034;:&#034;&#034;,&#034;textAlign&#034;:&#034;&#034;,&#034;fontStyle&#034;:&#034;&#034;,&#034;fontFamily&#034;:&#034;&#034;,&#034;wordSpacing&#034;:&#034;&#034;,&#034;fontSize&#034;:&#034;&#034;},&#034;bgData&#034;:{&#034;backgroundPosition&#034;:{&#034;left&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:&#034;&#034;},&#034;top&#034;:{&#034;unit&#034;:&#034;px&#034;,&#034;value&#034;:&#034;&#034;}},&#034;backgroundColor&#034;:&#034;&#034;,&#034;backgroundRepeat&#034;:&#034;&#034;,&#034;backgroundImage&#034;:&#034;&#034;,&#034;useBgImage&#034;:false},&#034;advancedData&#034;:{&#034;customCSS&#034;:&#034;\\n#portlet_48_INSTANCE_QchntqUvZ1JY{\\n\\tpadding: 0 0 0 0px;\\n}\\n&#034;,&#034;customCSSClassName&#034;:&#034;&#034;}}</value></preference><preference><name>title</name><value></value></preference><preference><name>auth</name><value>true</value></preference><preference><name>relative</name><value>false</value></preference><preference><name>password</name><value></value></preference><preference><name>portletSetupShowBorders</name><value>false</value></preference><preference><name>basicUserName</name><value>@email_address@</value></preference><preference><name>heightNormal</name><value>100%</value></preference><preference><name>longdesc</name><value></value></preference><preference><name>portletSetupTitle_en_US</name><value>IFrame</value></preference><preference><name>htmlAttributes</name><value>alt=[$NEW_LINE$]border=0[$NEW_LINE$]bordercolor=#000000[$NEW_LINE$]frameborder=0[$NEW_LINE$]hspace=0[$NEW_LINE$]longdesc=[$NEW_LINE$]scrolling=auto[$NEW_LINE$]title=[$NEW_LINE$]vspace=0[$NEW_LINE$]heightNormal=100%</value></preference></portlet-preferences>'),(31570,0,3,10175,'156','<portlet-preferences />'),(31571,0,3,10175,'174','<portlet-preferences />'),(31572,0,3,10175,'128','<portlet-preferences />'),(32053,0,3,10597,'87','<portlet-preferences />'),(32056,10196,4,10175,'2_WAR_marketplaceportlet','<portlet-preferences />'),(32270,32062,2,0,'15','<portlet-preferences />'),(32373,0,3,32368,'56_INSTANCE_tZuaTAo2hOG6','<portlet-preferences><preference><name>articleId</name><value>MAIN-CAROUSEL</value></preference><preference><name>portletSetupShowBorders</name><value>false</value></preference><preference><name>groupId</name><value>32062</value></preference></portlet-preferences>'),(32374,0,3,32368,'56_INSTANCE_KniNRZT9hDuP','<portlet-preferences><preference><name>articleId</name><value>OUR-GOAL</value></preference><preference><name>portletSetupShowBorders</name><value>false</value></preference><preference><name>groupId</name><value>32062</value></preference></portlet-preferences>'),(32375,0,3,32368,'56_INSTANCE_lWf05Qg36GvU','<portlet-preferences><preference><name>articleId</name><value>EXCEPTIONAL-SUPPORT</value></preference><preference><name>portletSetupShowBorders</name><value>false</value></preference><preference><name>groupId</name><value>32062</value></preference></portlet-preferences>'),(32376,0,3,32368,'56_INSTANCE_dX3cF6QJLhKO','<portlet-preferences><preference><name>articleId</name><value>THE-EXPERIENCE</value></preference><preference><name>portletSetupShowBorders</name><value>false</value></preference><preference><name>groupId</name><value>32062</value></preference></portlet-preferences>'),(32377,0,3,32368,'56_INSTANCE_4qOp14zAbq7m','<portlet-preferences><preference><name>articleId</name><value>WHAT-OUR-CLIENTS-ARE-SAYING</value></preference><preference><name>portletSetupShowBorders</name><value>false</value></preference><preference><name>groupId</name><value>32062</value></preference></portlet-preferences>'),(32398,0,3,32393,'56_INSTANCE_BTgTRPG71sYr','<portlet-preferences><preference><name>articleId</name><value>MAIN-CONTENT</value></preference><preference><name>portletSetupShowBorders</name><value>false</value></preference><preference><name>groupId</name><value>32062</value></preference></portlet-preferences>'),(32399,0,3,32393,'56_INSTANCE_pBXjW6iNevv7','<portlet-preferences><preference><name>articleId</name><value>WHY-SEVEN-COGS</value></preference><preference><name>portletSetupShowBorders</name><value>false</value></preference><preference><name>groupId</name><value>32062</value></preference></portlet-preferences>'),(32405,0,3,10603,'103','<portlet-preferences />'),(32406,0,3,10603,'145','<portlet-preferences />'),(32410,0,3,10609,'103','<portlet-preferences />'),(32411,0,3,10609,'145','<portlet-preferences />'),(35206,0,3,35201,'103','<portlet-preferences />'),(35207,0,3,35201,'145','<portlet-preferences />'),(35208,0,3,10175,'165','<portlet-preferences />'),(35209,0,3,35201,'87','<portlet-preferences />'),(35210,0,3,35201,'48_INSTANCE_z5aG4NvnwC4O','<portlet-preferences><preference><name>passwordField</name><value></value></preference><preference><name>authType</name><value>basic</value></preference><preference><name>form-method</name><value>post</value></preference><preference><name>formMethod</name><value>get</value></preference><preference><name>formPassword</name><value></value></preference><preference><name>width</name><value>100%</value></preference><preference><name>userNameField</name><value></value></preference><preference><name>alt</name><value></value></preference><preference><name>hspace</name><value>0</value></preference><preference><name>basicPassword</name><value></value></preference><preference><name>formUserName</name><value></value></preference><preference><name>resizeAutomatically</name><value>false</value></preference><preference><name>user-name</name><value></value></preference><preference><name>hiddenVariables</name><value></value></preference><preference><name>heightMaximized</name><value>600</value></preference><preference><name>border</name><value>0</value></preference><preference><name>scrolling</name><value>auto</value></preference><preference><name>src</name><value>http://localhost:9080/logistics2.0.2.RC1</value></preference><preference><name>hidden-variables</name><value>var1=hello;var2=world</value></preference><preference><name>vspace</name><value>0</value></preference><preference><name>frameborder</name><value>0</value></preference><preference><name>bordercolor</name><value>#000000</value></preference><preference><name>auth-type</name><value>basic</value></preference><preference><name>title</name><value></value></preference><preference><name>auth</name><value>false</value></preference><preference><name>relative</name><value>false</value></preference><preference><name>password</name><value></value></preference><preference><name>basicUserName</name><value></value></preference><preference><name>longdesc</name><value></value></preference><preference><name>heightNormal</name><value>600</value></preference><preference><name>htmlAttributes</name><value>alt=[$NEW_LINE$]border=0[$NEW_LINE$]bordercolor=#000000[$NEW_LINE$]frameborder=0[$NEW_LINE$]hspace=0[$NEW_LINE$]longdesc=[$NEW_LINE$]scrolling=auto[$NEW_LINE$]title=[$NEW_LINE$]vspace=0</value></preference></portlet-preferences>'),(35211,0,3,35201,'86','<portlet-preferences />');
/*!40000 ALTER TABLE `portletpreferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_blob_triggers`
--

DROP TABLE IF EXISTS `quartz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` longblob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_blob_triggers`
--

LOCK TABLES `quartz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `quartz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_calendars`
--

DROP TABLE IF EXISTS `quartz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` longblob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_calendars`
--

LOCK TABLES `quartz_calendars` WRITE;
/*!40000 ALTER TABLE `quartz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_cron_triggers`
--

DROP TABLE IF EXISTS `quartz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_cron_triggers`
--

LOCK TABLES `quartz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `quartz_cron_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_fired_triggers`
--

DROP TABLE IF EXISTS `quartz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(20) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` tinyint(4) DEFAULT NULL,
  `REQUESTS_RECOVERY` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IX_BE3835E5` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IX_4BD722BM` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IX_204D31E8` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IX_339E078M` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IX_5005E3AF` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IX_BC2F03B0` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_fired_triggers`
--

LOCK TABLES `quartz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `quartz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_job_details`
--

DROP TABLE IF EXISTS `quartz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` tinyint(4) NOT NULL,
  `IS_NONCONCURRENT` tinyint(4) NOT NULL,
  `IS_UPDATE_DATA` tinyint(4) NOT NULL,
  `REQUESTS_RECOVERY` tinyint(4) NOT NULL,
  `JOB_DATA` longblob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IX_88328984` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IX_779BCA37` (`SCHED_NAME`,`REQUESTS_RECOVERY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_job_details`
--

LOCK TABLES `quartz_job_details` WRITE;
/*!40000 ALTER TABLE `quartz_job_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_locks`
--

DROP TABLE IF EXISTS `quartz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_locks`
--

LOCK TABLES `quartz_locks` WRITE;
/*!40000 ALTER TABLE `quartz_locks` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `quartz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_paused_trigger_grps`
--

LOCK TABLES `quartz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `quartz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_scheduler_state`
--

DROP TABLE IF EXISTS `quartz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(20) NOT NULL,
  `CHECKIN_INTERVAL` bigint(20) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_scheduler_state`
--

LOCK TABLES `quartz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `quartz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_simple_triggers`
--

DROP TABLE IF EXISTS `quartz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(20) NOT NULL,
  `REPEAT_INTERVAL` bigint(20) NOT NULL,
  `TIMES_TRIGGERED` bigint(20) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_simple_triggers`
--

LOCK TABLES `quartz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `quartz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_simprop_triggers`
--

DROP TABLE IF EXISTS `quartz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` tinyint(4) DEFAULT NULL,
  `BOOL_PROP_2` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_simprop_triggers`
--

LOCK TABLES `quartz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `quartz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_triggers`
--

DROP TABLE IF EXISTS `quartz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quartz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(20) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(20) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(20) NOT NULL,
  `END_TIME` bigint(20) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` int(11) DEFAULT NULL,
  `JOB_DATA` longblob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IX_186442A4` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IX_1BA1F9DC` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IX_91CA7CCE` (`SCHED_NAME`,`TRIGGER_GROUP`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`,`MISFIRE_INSTR`),
  KEY `IX_D219AFDE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IX_A85822A0` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IX_8AA50BE1` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IX_EEFE382A` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IX_F026CF4C` (`SCHED_NAME`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IX_F2DD7C7E` (`SCHED_NAME`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`,`MISFIRE_INSTR`),
  KEY `IX_1F92813C` (`SCHED_NAME`,`NEXT_FIRE_TIME`,`MISFIRE_INSTR`),
  KEY `IX_99108B6E` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IX_CD7132D0` (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_triggers`
--

LOCK TABLES `quartz_triggers` WRITE;
/*!40000 ALTER TABLE `quartz_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratingsentry`
--

DROP TABLE IF EXISTS `ratingsentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ratingsentry` (
  `entryId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `score` double DEFAULT NULL,
  PRIMARY KEY (`entryId`),
  UNIQUE KEY `IX_B47E3C11` (`userId`,`classNameId`,`classPK`),
  KEY `IX_16184D57` (`classNameId`,`classPK`),
  KEY `IX_A1A8CB8B` (`classNameId`,`classPK`,`score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratingsentry`
--

LOCK TABLES `ratingsentry` WRITE;
/*!40000 ALTER TABLE `ratingsentry` DISABLE KEYS */;
/*!40000 ALTER TABLE `ratingsentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratingsstats`
--

DROP TABLE IF EXISTS `ratingsstats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ratingsstats` (
  `statsId` bigint(20) NOT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `totalEntries` int(11) DEFAULT NULL,
  `totalScore` double DEFAULT NULL,
  `averageScore` double DEFAULT NULL,
  PRIMARY KEY (`statsId`),
  UNIQUE KEY `IX_A6E99284` (`classNameId`,`classPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratingsstats`
--

LOCK TABLES `ratingsstats` WRITE;
/*!40000 ALTER TABLE `ratingsstats` DISABLE KEYS */;
INSERT INTO `ratingsstats` VALUES (36395,10010,36383,0,0,0);
/*!40000 ALTER TABLE `ratingsstats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `region` (
  `regionId` bigint(20) NOT NULL,
  `countryId` bigint(20) DEFAULT NULL,
  `regionCode` varchar(75) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`regionId`),
  UNIQUE KEY `IX_A2635F5C` (`countryId`,`regionCode`),
  KEY `IX_2D9A426F` (`active_`),
  KEY `IX_16D87CA7` (`countryId`),
  KEY `IX_11FB3E42` (`countryId`,`active_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES (1001,1,'AB','Alberta',1),(1002,1,'BC','British Columbia',1),(1003,1,'MB','Manitoba',1),(1004,1,'NB','New Brunswick',1),(1005,1,'NL','Newfoundland and Labrador',1),(1006,1,'NT','Northwest Territories',1),(1007,1,'NS','Nova Scotia',1),(1008,1,'NU','Nunavut',1),(1009,1,'ON','Ontario',1),(1010,1,'PE','Prince Edward Island',1),(1011,1,'QC','Quebec',1),(1012,1,'SK','Saskatchewan',1),(1013,1,'YT','Yukon',1),(2001,2,'CN-34','Anhui',1),(2002,2,'CN-92','Aomen',1),(2003,2,'CN-11','Beijing',1),(2004,2,'CN-50','Chongqing',1),(2005,2,'CN-35','Fujian',1),(2006,2,'CN-62','Gansu',1),(2007,2,'CN-44','Guangdong',1),(2008,2,'CN-45','Guangxi',1),(2009,2,'CN-52','Guizhou',1),(2010,2,'CN-46','Hainan',1),(2011,2,'CN-13','Hebei',1),(2012,2,'CN-23','Heilongjiang',1),(2013,2,'CN-41','Henan',1),(2014,2,'CN-42','Hubei',1),(2015,2,'CN-43','Hunan',1),(2016,2,'CN-32','Jiangsu',1),(2017,2,'CN-36','Jiangxi',1),(2018,2,'CN-22','Jilin',1),(2019,2,'CN-21','Liaoning',1),(2020,2,'CN-15','Nei Mongol',1),(2021,2,'CN-64','Ningxia',1),(2022,2,'CN-63','Qinghai',1),(2023,2,'CN-61','Shaanxi',1),(2024,2,'CN-37','Shandong',1),(2025,2,'CN-31','Shanghai',1),(2026,2,'CN-14','Shanxi',1),(2027,2,'CN-51','Sichuan',1),(2028,2,'CN-71','Taiwan',1),(2029,2,'CN-12','Tianjin',1),(2030,2,'CN-91','Xianggang',1),(2031,2,'CN-65','Xinjiang',1),(2032,2,'CN-54','Xizang',1),(2033,2,'CN-53','Yunnan',1),(2034,2,'CN-33','Zhejiang',1),(3001,3,'A','Alsace',1),(3002,3,'B','Aquitaine',1),(3003,3,'C','Auvergne',1),(3004,3,'P','Basse-Normandie',1),(3005,3,'D','Bourgogne',1),(3006,3,'E','Bretagne',1),(3007,3,'F','Centre',1),(3008,3,'G','Champagne-Ardenne',1),(3009,3,'H','Corse',1),(3010,3,'GF','Guyane',1),(3011,3,'I','Franche ComtÃ©',1),(3012,3,'GP','Guadeloupe',1),(3013,3,'Q','Haute-Normandie',1),(3014,3,'J','ÃŽle-de-France',1),(3015,3,'K','Languedoc-Roussillon',1),(3016,3,'L','Limousin',1),(3017,3,'M','Lorraine',1),(3018,3,'MQ','Martinique',1),(3019,3,'N','Midi-PyrÃ©nÃ©es',1),(3020,3,'O','Nord Pas de Calais',1),(3021,3,'R','Pays de la Loire',1),(3022,3,'S','Picardie',1),(3023,3,'T','Poitou-Charentes',1),(3024,3,'U','Provence-Alpes-CÃ´te-d\'Azur',1),(3025,3,'RE','RÃ©union',1),(3026,3,'V','RhÃ´ne-Alpes',1),(4001,4,'BW','Baden-WÃ¼rttemberg',1),(4002,4,'BY','Bayern',1),(4003,4,'BE','Berlin',1),(4004,4,'BR','Brandenburg',1),(4005,4,'HB','Bremen',1),(4006,4,'HH','Hamburg',1),(4007,4,'HE','Hessen',1),(4008,4,'MV','Mecklenburg-Vorpommern',1),(4009,4,'NI','Niedersachsen',1),(4010,4,'NW','Nordrhein-Westfalen',1),(4011,4,'RP','Rheinland-Pfalz',1),(4012,4,'SL','Saarland',1),(4013,4,'SN','Sachsen',1),(4014,4,'ST','Sachsen-Anhalt',1),(4015,4,'SH','Schleswig-Holstein',1),(4016,4,'TH','ThÃ¼ringen',1),(8001,8,'AG','Agrigento',1),(8002,8,'AL','Alessandria',1),(8003,8,'AN','Ancona',1),(8004,8,'AO','Aosta',1),(8005,8,'AR','Arezzo',1),(8006,8,'AP','Ascoli Piceno',1),(8007,8,'AT','Asti',1),(8008,8,'AV','Avellino',1),(8009,8,'BA','Bari',1),(8010,8,'BT','Barletta-Andria-Trani',1),(8011,8,'BL','Belluno',1),(8012,8,'BN','Benevento',1),(8013,8,'BG','Bergamo',1),(8014,8,'BI','Biella',1),(8015,8,'BO','Bologna',1),(8016,8,'BZ','Bolzano',1),(8017,8,'BS','Brescia',1),(8018,8,'BR','Brindisi',1),(8019,8,'CA','Cagliari',1),(8020,8,'CL','Caltanissetta',1),(8021,8,'CB','Campobasso',1),(8022,8,'CI','Carbonia-Iglesias',1),(8023,8,'CE','Caserta',1),(8024,8,'CT','Catania',1),(8025,8,'CZ','Catanzaro',1),(8026,8,'CH','Chieti',1),(8027,8,'CO','Como',1),(8028,8,'CS','Cosenza',1),(8029,8,'CR','Cremona',1),(8030,8,'KR','Crotone',1),(8031,8,'CN','Cuneo',1),(8032,8,'EN','Enna',1),(8033,8,'FM','Fermo',1),(8034,8,'FE','Ferrara',1),(8035,8,'FI','Firenze',1),(8036,8,'FG','Foggia',1),(8037,8,'FC','Forli-Cesena',1),(8038,8,'FR','Frosinone',1),(8039,8,'GE','Genova',1),(8040,8,'GO','Gorizia',1),(8041,8,'GR','Grosseto',1),(8042,8,'IM','Imperia',1),(8043,8,'IS','Isernia',1),(8044,8,'AQ','L\'Aquila',1),(8045,8,'SP','La Spezia',1),(8046,8,'LT','Latina',1),(8047,8,'LE','Lecce',1),(8048,8,'LC','Lecco',1),(8049,8,'LI','Livorno',1),(8050,8,'LO','Lodi',1),(8051,8,'LU','Lucca',1),(8052,8,'MC','Macerata',1),(8053,8,'MN','Mantova',1),(8054,8,'MS','Massa-Carrara',1),(8055,8,'MT','Matera',1),(8056,8,'MA','Medio Campidano',1),(8057,8,'ME','Messina',1),(8058,8,'MI','Milano',1),(8059,8,'MO','Modena',1),(8060,8,'MZ','Monza',1),(8061,8,'NA','Napoli',1),(8062,8,'NO','Novara',1),(8063,8,'NU','Nuoro',1),(8064,8,'OG','Ogliastra',1),(8065,8,'OT','Olbia-Tempio',1),(8066,8,'OR','Oristano',1),(8067,8,'PD','Padova',1),(8068,8,'PA','Palermo',1),(8069,8,'PR','Parma',1),(8070,8,'PV','Pavia',1),(8071,8,'PG','Perugia',1),(8072,8,'PU','Pesaro e Urbino',1),(8073,8,'PE','Pescara',1),(8074,8,'PC','Piacenza',1),(8075,8,'PI','Pisa',1),(8076,8,'PT','Pistoia',1),(8077,8,'PN','Pordenone',1),(8078,8,'PZ','Potenza',1),(8079,8,'PO','Prato',1),(8080,8,'RG','Ragusa',1),(8081,8,'RA','Ravenna',1),(8082,8,'RC','Reggio Calabria',1),(8083,8,'RE','Reggio Emilia',1),(8084,8,'RI','Rieti',1),(8085,8,'RN','Rimini',1),(8086,8,'RM','Roma',1),(8087,8,'RO','Rovigo',1),(8088,8,'SA','Salerno',1),(8089,8,'SS','Sassari',1),(8090,8,'SV','Savona',1),(8091,8,'SI','Siena',1),(8092,8,'SR','Siracusa',1),(8093,8,'SO','Sondrio',1),(8094,8,'TA','Taranto',1),(8095,8,'TE','Teramo',1),(8096,8,'TR','Terni',1),(8097,8,'TO','Torino',1),(8098,8,'TP','Trapani',1),(8099,8,'TN','Trento',1),(8100,8,'TV','Treviso',1),(8101,8,'TS','Trieste',1),(8102,8,'UD','Udine',1),(8103,8,'VA','Varese',1),(8104,8,'VE','Venezia',1),(8105,8,'VB','Verbano-Cusio-Ossola',1),(8106,8,'VC','Vercelli',1),(8107,8,'VR','Verona',1),(8108,8,'VV','Vibo Valentia',1),(8109,8,'VI','Vicenza',1),(8110,8,'VT','Viterbo',1),(11001,11,'DR','Drenthe',1),(11002,11,'FL','Flevoland',1),(11003,11,'FR','Friesland',1),(11004,11,'GE','Gelderland',1),(11005,11,'GR','Groningen',1),(11006,11,'LI','Limburg',1),(11007,11,'NB','Noord-Brabant',1),(11008,11,'NH','Noord-Holland',1),(11009,11,'OV','Overijssel',1),(11010,11,'UT','Utrecht',1),(11011,11,'ZE','Zeeland',1),(11012,11,'ZH','Zuid-Holland',1),(15001,15,'AN','Andalusia',1),(15002,15,'AR','Aragon',1),(15003,15,'AS','Asturias',1),(15004,15,'IB','Balearic Islands',1),(15005,15,'PV','Basque Country',1),(15006,15,'CN','Canary Islands',1),(15007,15,'CB','Cantabria',1),(15008,15,'CL','Castile and Leon',1),(15009,15,'CM','Castile-La Mancha',1),(15010,15,'CT','Catalonia',1),(15011,15,'CE','Ceuta',1),(15012,15,'EX','Extremadura',1),(15013,15,'GA','Galicia',1),(15014,15,'LO','La Rioja',1),(15015,15,'M','Madrid',1),(15016,15,'ML','Melilla',1),(15017,15,'MU','Murcia',1),(15018,15,'NA','Navarra',1),(15019,15,'VC','Valencia',1),(19001,19,'AL','Alabama',1),(19002,19,'AK','Alaska',1),(19003,19,'AZ','Arizona',1),(19004,19,'AR','Arkansas',1),(19005,19,'CA','California',1),(19006,19,'CO','Colorado',1),(19007,19,'CT','Connecticut',1),(19008,19,'DC','District of Columbia',1),(19009,19,'DE','Delaware',1),(19010,19,'FL','Florida',1),(19011,19,'GA','Georgia',1),(19012,19,'HI','Hawaii',1),(19013,19,'ID','Idaho',1),(19014,19,'IL','Illinois',1),(19015,19,'IN','Indiana',1),(19016,19,'IA','Iowa',1),(19017,19,'KS','Kansas',1),(19018,19,'KY','Kentucky ',1),(19019,19,'LA','Louisiana ',1),(19020,19,'ME','Maine',1),(19021,19,'MD','Maryland',1),(19022,19,'MA','Massachusetts',1),(19023,19,'MI','Michigan',1),(19024,19,'MN','Minnesota',1),(19025,19,'MS','Mississippi',1),(19026,19,'MO','Missouri',1),(19027,19,'MT','Montana',1),(19028,19,'NE','Nebraska',1),(19029,19,'NV','Nevada',1),(19030,19,'NH','New Hampshire',1),(19031,19,'NJ','New Jersey',1),(19032,19,'NM','New Mexico',1),(19033,19,'NY','New York',1),(19034,19,'NC','North Carolina',1),(19035,19,'ND','North Dakota',1),(19036,19,'OH','Ohio',1),(19037,19,'OK','Oklahoma ',1),(19038,19,'OR','Oregon',1),(19039,19,'PA','Pennsylvania',1),(19040,19,'PR','Puerto Rico',1),(19041,19,'RI','Rhode Island',1),(19042,19,'SC','South Carolina',1),(19043,19,'SD','South Dakota',1),(19044,19,'TN','Tennessee',1),(19045,19,'TX','Texas',1),(19046,19,'UT','Utah',1),(19047,19,'VT','Vermont',1),(19048,19,'VA','Virginia',1),(19049,19,'WA','Washington',1),(19050,19,'WV','West Virginia',1),(19051,19,'WI','Wisconsin',1),(19052,19,'WY','Wyoming',1),(32001,32,'ACT','Australian Capital Territory',1),(32002,32,'NSW','New South Wales',1),(32003,32,'NT','Northern Territory',1),(32004,32,'QLD','Queensland',1),(32005,32,'SA','South Australia',1),(32006,32,'TAS','Tasmania',1),(32007,32,'VIC','Victoria',1),(32008,32,'WA','Western Australia',1),(144001,144,'MX-AGS','Aguascalientes',1),(144002,144,'MX-BCN','Baja California',1),(144003,144,'MX-BCS','Baja California Sur',1),(144004,144,'MX-CAM','Campeche',1),(144005,144,'MX-CHP','Chiapas',1),(144006,144,'MX-CHI','Chihuahua',1),(144007,144,'MX-COA','Coahuila',1),(144008,144,'MX-COL','Colima',1),(144009,144,'MX-DUR','Durango',1),(144010,144,'MX-GTO','Guanajuato',1),(144011,144,'MX-GRO','Guerrero',1),(144012,144,'MX-HGO','Hidalgo',1),(144013,144,'MX-JAL','Jalisco',1),(144014,144,'MX-MEX','Mexico',1),(144015,144,'MX-MIC','Michoacan',1),(144016,144,'MX-MOR','Morelos',1),(144017,144,'MX-NAY','Nayarit',1),(144018,144,'MX-NLE','Nuevo Leon',1),(144019,144,'MX-OAX','Oaxaca',1),(144020,144,'MX-PUE','Puebla',1),(144021,144,'MX-QRO','Queretaro',1),(144023,144,'MX-ROO','Quintana Roo',1),(144024,144,'MX-SLP','San Luis PotosÃ­',1),(144025,144,'MX-SIN','Sinaloa',1),(144026,144,'MX-SON','Sonora',1),(144027,144,'MX-TAB','Tabasco',1),(144028,144,'MX-TAM','Tamaulipas',1),(144029,144,'MX-TLX','Tlaxcala',1),(144030,144,'MX-VER','Veracruz',1),(144031,144,'MX-YUC','Yucatan',1),(144032,144,'MX-ZAC','Zacatecas',1),(164001,164,'01','Ã˜stfold',1),(164002,164,'02','Akershus',1),(164003,164,'03','Oslo',1),(164004,164,'04','Hedmark',1),(164005,164,'05','Oppland',1),(164006,164,'06','Buskerud',1),(164007,164,'07','Vestfold',1),(164008,164,'08','Telemark',1),(164009,164,'09','Aust-Agder',1),(164010,164,'10','Vest-Agder',1),(164011,164,'11','Rogaland',1),(164012,164,'12','Hordaland',1),(164013,164,'14','Sogn og Fjordane',1),(164014,164,'15','MÃ¸re of Romsdal',1),(164015,164,'16','SÃ¸r-TrÃ¸ndelag',1),(164016,164,'17','Nord-TrÃ¸ndelag',1),(164017,164,'18','Nordland',1),(164018,164,'19','Troms',1),(164019,164,'20','Finnmark',1),(202001,202,'AG','Aargau',1),(202002,202,'AR','Appenzell Ausserrhoden',1),(202003,202,'AI','Appenzell Innerrhoden',1),(202004,202,'BL','Basel-Landschaft',1),(202005,202,'BS','Basel-Stadt',1),(202006,202,'BE','Bern',1),(202007,202,'FR','Fribourg',1),(202008,202,'GE','Geneva',1),(202009,202,'GL','Glarus',1),(202010,202,'GR','GraubÃ¼nden',1),(202011,202,'JU','Jura',1),(202012,202,'LU','Lucerne',1),(202013,202,'NE','NeuchÃ¢tel',1),(202014,202,'NW','Nidwalden',1),(202015,202,'OW','Obwalden',1),(202016,202,'SH','Schaffhausen',1),(202017,202,'SZ','Schwyz',1),(202018,202,'SO','Solothurn',1),(202019,202,'SG','St. Gallen',1),(202020,202,'TG','Thurgau',1),(202021,202,'TI','Ticino',1),(202022,202,'UR','Uri',1),(202023,202,'VS','Valais',1),(202024,202,'VD','Vaud',1),(202025,202,'ZG','Zug',1),(202026,202,'ZH','ZÃ¼rich',1);
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `release_`
--

DROP TABLE IF EXISTS `release_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `release_` (
  `releaseId` bigint(20) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `servletContextName` varchar(75) DEFAULT NULL,
  `buildNumber` int(11) DEFAULT NULL,
  `buildDate` datetime DEFAULT NULL,
  `verified` tinyint(4) DEFAULT NULL,
  `state_` int(11) DEFAULT NULL,
  `testString` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`releaseId`),
  KEY `IX_8BD6BCA7` (`servletContextName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `release_`
--

LOCK TABLES `release_` WRITE;
/*!40000 ALTER TABLE `release_` DISABLE KEYS */;
INSERT INTO `release_` VALUES (1,'2012-08-13 22:47:40','2012-11-30 23:01:05','portal',6101,'2012-07-31 00:00:00',1,0,'You take the blue pill, the story ends, you wake up in your bed and believe whatever you want to believe. You take the red pill, you stay in Wonderland, and I show you how deep the rabbit hole goes.');
/*!40000 ALTER TABLE `release_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repository`
--

DROP TABLE IF EXISTS `repository`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repository` (
  `uuid_` varchar(75) DEFAULT NULL,
  `repositoryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `portletId` varchar(75) DEFAULT NULL,
  `typeSettings` longtext,
  `dlFolderId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`repositoryId`),
  UNIQUE KEY `IX_11641E26` (`uuid_`,`groupId`),
  KEY `IX_5253B1FA` (`groupId`),
  KEY `IX_74C17B04` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repository`
--

LOCK TABLES `repository` WRITE;
/*!40000 ALTER TABLE `repository` DISABLE KEYS */;
/*!40000 ALTER TABLE `repository` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repositoryentry`
--

DROP TABLE IF EXISTS `repositoryentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repositoryentry` (
  `uuid_` varchar(75) DEFAULT NULL,
  `repositoryEntryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `repositoryId` bigint(20) DEFAULT NULL,
  `mappedId` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`repositoryEntryId`),
  UNIQUE KEY `IX_9BDCF489` (`repositoryId`,`mappedId`),
  UNIQUE KEY `IX_354AA664` (`uuid_`,`groupId`),
  KEY `IX_B7034B27` (`repositoryId`),
  KEY `IX_B9B1506` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repositoryentry`
--

LOCK TABLES `repositoryentry` WRITE;
/*!40000 ALTER TABLE `repositoryentry` DISABLE KEYS */;
/*!40000 ALTER TABLE `repositoryentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_`
--

DROP TABLE IF EXISTS `resource_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource_` (
  `resourceId` bigint(20) NOT NULL,
  `codeId` bigint(20) DEFAULT NULL,
  `primKey` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resourceId`),
  UNIQUE KEY `IX_67DE7856` (`codeId`,`primKey`),
  KEY `IX_2578FBD3` (`codeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_`
--

LOCK TABLES `resource_` WRITE;
/*!40000 ALTER TABLE `resource_` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourceaction`
--

DROP TABLE IF EXISTS `resourceaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourceaction` (
  `resourceActionId` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `actionId` varchar(75) DEFAULT NULL,
  `bitwiseValue` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resourceActionId`),
  UNIQUE KEY `IX_EDB9986E` (`name`,`actionId`),
  KEY `IX_81F2DB09` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourceaction`
--

LOCK TABLES `resourceaction` WRITE;
/*!40000 ALTER TABLE `resourceaction` DISABLE KEYS */;
INSERT INTO `resourceaction` VALUES (1,'com.liferay.portlet.softwarecatalog','ADD_FRAMEWORK_VERSION',2),(2,'com.liferay.portlet.softwarecatalog','ADD_PRODUCT_ENTRY',4),(3,'com.liferay.portlet.softwarecatalog','PERMISSIONS',8),(4,'com.liferay.portal.model.Team','ASSIGN_MEMBERS',2),(5,'com.liferay.portal.model.Team','DELETE',4),(6,'com.liferay.portal.model.Team','PERMISSIONS',8),(7,'com.liferay.portal.model.Team','UPDATE',16),(8,'com.liferay.portal.model.Team','VIEW',1),(9,'com.liferay.portal.model.PasswordPolicy','ASSIGN_MEMBERS',2),(10,'com.liferay.portal.model.PasswordPolicy','DELETE',4),(11,'com.liferay.portal.model.PasswordPolicy','PERMISSIONS',8),(12,'com.liferay.portal.model.PasswordPolicy','UPDATE',16),(13,'com.liferay.portal.model.PasswordPolicy','VIEW',1),(14,'com.liferay.portlet.blogs.model.BlogsEntry','ADD_DISCUSSION',2),(15,'com.liferay.portlet.blogs.model.BlogsEntry','DELETE',4),(16,'com.liferay.portlet.blogs.model.BlogsEntry','DELETE_DISCUSSION',8),(17,'com.liferay.portlet.blogs.model.BlogsEntry','PERMISSIONS',16),(18,'com.liferay.portlet.blogs.model.BlogsEntry','UPDATE',32),(19,'com.liferay.portlet.blogs.model.BlogsEntry','UPDATE_DISCUSSION',64),(20,'com.liferay.portlet.blogs.model.BlogsEntry','VIEW',1),(21,'com.liferay.portlet.dynamicdatamapping.model.DDMTemplate','DELETE',2),(22,'com.liferay.portlet.dynamicdatamapping.model.DDMTemplate','PERMISSIONS',4),(23,'com.liferay.portlet.dynamicdatamapping.model.DDMTemplate','UPDATE',8),(24,'com.liferay.portlet.dynamicdatamapping.model.DDMTemplate','VIEW',1),(25,'com.liferay.portlet.journal.model.JournalFeed','DELETE',2),(26,'com.liferay.portlet.journal.model.JournalFeed','PERMISSIONS',4),(27,'com.liferay.portlet.journal.model.JournalFeed','UPDATE',8),(28,'com.liferay.portlet.journal.model.JournalFeed','VIEW',1),(29,'com.liferay.portlet.wiki.model.WikiNode','ADD_ATTACHMENT',2),(30,'com.liferay.portlet.wiki.model.WikiNode','ADD_PAGE',4),(31,'com.liferay.portlet.wiki.model.WikiNode','DELETE',8),(32,'com.liferay.portlet.wiki.model.WikiNode','IMPORT',16),(33,'com.liferay.portlet.wiki.model.WikiNode','PERMISSIONS',32),(34,'com.liferay.portlet.wiki.model.WikiNode','SUBSCRIBE',64),(35,'com.liferay.portlet.wiki.model.WikiNode','UPDATE',128),(36,'com.liferay.portlet.wiki.model.WikiNode','VIEW',1),(37,'com.liferay.portlet.announcements.model.AnnouncementsEntry','DELETE',2),(38,'com.liferay.portlet.announcements.model.AnnouncementsEntry','UPDATE',4),(39,'com.liferay.portlet.announcements.model.AnnouncementsEntry','VIEW',1),(40,'com.liferay.portlet.announcements.model.AnnouncementsEntry','PERMISSIONS',8),(41,'com.liferay.portlet.bookmarks','ADD_ENTRY',2),(42,'com.liferay.portlet.bookmarks','ADD_FOLDER',4),(43,'com.liferay.portlet.bookmarks','PERMISSIONS',8),(44,'com.liferay.portlet.bookmarks','VIEW',1),(45,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance','DELETE',2),(46,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance','PERMISSIONS',4),(47,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance','UPDATE',8),(48,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance','VIEW',1),(49,'com.liferay.portlet.asset.model.AssetVocabulary','DELETE',2),(50,'com.liferay.portlet.asset.model.AssetVocabulary','PERMISSIONS',4),(51,'com.liferay.portlet.asset.model.AssetVocabulary','UPDATE',8),(52,'com.liferay.portlet.asset.model.AssetVocabulary','VIEW',1),(53,'com.liferay.portlet.documentlibrary.model.DLFolder','ACCESS',2),(54,'com.liferay.portlet.documentlibrary.model.DLFolder','ADD_DOCUMENT',4),(55,'com.liferay.portlet.documentlibrary.model.DLFolder','ADD_SHORTCUT',8),(56,'com.liferay.portlet.documentlibrary.model.DLFolder','ADD_SUBFOLDER',16),(57,'com.liferay.portlet.documentlibrary.model.DLFolder','DELETE',32),(58,'com.liferay.portlet.documentlibrary.model.DLFolder','PERMISSIONS',64),(59,'com.liferay.portlet.documentlibrary.model.DLFolder','UPDATE',128),(60,'com.liferay.portlet.documentlibrary.model.DLFolder','VIEW',1),(61,'com.liferay.portlet.expando.model.ExpandoColumn','DELETE',2),(62,'com.liferay.portlet.expando.model.ExpandoColumn','PERMISSIONS',4),(63,'com.liferay.portlet.expando.model.ExpandoColumn','UPDATE',8),(64,'com.liferay.portlet.expando.model.ExpandoColumn','VIEW',1),(65,'com.liferay.portlet.documentlibrary','ADD_DOCUMENT',2),(66,'com.liferay.portlet.documentlibrary','ADD_DOCUMENT_TYPE',4),(67,'com.liferay.portlet.documentlibrary','ADD_FOLDER',8),(68,'com.liferay.portlet.documentlibrary','ADD_REPOSITORY',16),(69,'com.liferay.portlet.documentlibrary','ADD_STRUCTURE',32),(70,'com.liferay.portlet.documentlibrary','ADD_SHORTCUT',64),(71,'com.liferay.portlet.documentlibrary','PERMISSIONS',128),(72,'com.liferay.portlet.documentlibrary','UPDATE',256),(73,'com.liferay.portlet.documentlibrary','VIEW',1),(74,'com.liferay.portlet.calendar.model.CalEvent','ADD_DISCUSSION',2),(75,'com.liferay.portlet.calendar.model.CalEvent','DELETE',4),(76,'com.liferay.portlet.calendar.model.CalEvent','DELETE_DISCUSSION',8),(77,'com.liferay.portlet.calendar.model.CalEvent','PERMISSIONS',16),(78,'com.liferay.portlet.calendar.model.CalEvent','UPDATE',32),(79,'com.liferay.portlet.calendar.model.CalEvent','UPDATE_DISCUSSION',64),(80,'com.liferay.portlet.calendar.model.CalEvent','VIEW',1),(81,'com.liferay.portlet.shopping.model.ShoppingCategory','ADD_ITEM',2),(82,'com.liferay.portlet.shopping.model.ShoppingCategory','ADD_SUBCATEGORY',4),(83,'com.liferay.portlet.shopping.model.ShoppingCategory','DELETE',8),(84,'com.liferay.portlet.shopping.model.ShoppingCategory','PERMISSIONS',16),(85,'com.liferay.portlet.shopping.model.ShoppingCategory','UPDATE',32),(86,'com.liferay.portlet.shopping.model.ShoppingCategory','VIEW',1),(87,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','ADD_DISCUSSION',2),(88,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','DELETE',4),(89,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','DELETE_DISCUSSION',8),(90,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','PERMISSIONS',16),(91,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','UPDATE',32),(92,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','UPDATE_DISCUSSION',64),(93,'com.liferay.portlet.documentlibrary.model.DLFileShortcut','VIEW',1),(94,'com.liferay.portlet.journal','ADD_ARTICLE',2),(95,'com.liferay.portlet.journal','ADD_FEED',4),(96,'com.liferay.portlet.journal','ADD_STRUCTURE',8),(97,'com.liferay.portlet.journal','ADD_TEMPLATE',16),(98,'com.liferay.portlet.journal','SUBSCRIBE',32),(99,'com.liferay.portlet.journal','PERMISSIONS',64),(100,'com.liferay.portlet.calendar','ADD_EVENT',2),(101,'com.liferay.portlet.calendar','EXPORT_ALL_EVENTS',4),(102,'com.liferay.portlet.calendar','PERMISSIONS',8),(103,'com.liferay.portal.model.LayoutPrototype','DELETE',2),(104,'com.liferay.portal.model.LayoutPrototype','PERMISSIONS',4),(105,'com.liferay.portal.model.LayoutPrototype','UPDATE',8),(106,'com.liferay.portal.model.LayoutPrototype','VIEW',1),(107,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet','ADD_RECORD',2),(108,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet','DELETE',4),(109,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet','PERMISSIONS',8),(110,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet','UPDATE',16),(111,'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet','VIEW',1),(112,'com.liferay.portal.model.Organization','ASSIGN_MEMBERS',2),(113,'com.liferay.portal.model.Organization','ASSIGN_USER_ROLES',4),(114,'com.liferay.portal.model.Organization','DELETE',8),(115,'com.liferay.portal.model.Organization','MANAGE_ANNOUNCEMENTS',16),(116,'com.liferay.portal.model.Organization','MANAGE_SUBORGANIZATIONS',32),(117,'com.liferay.portal.model.Organization','MANAGE_USERS',64),(118,'com.liferay.portal.model.Organization','PERMISSIONS',128),(119,'com.liferay.portal.model.Organization','UPDATE',256),(120,'com.liferay.portal.model.Organization','VIEW',1),(121,'com.liferay.portlet.softwarecatalog.model.SCLicense','DELETE',2),(122,'com.liferay.portlet.softwarecatalog.model.SCLicense','PERMISSIONS',4),(123,'com.liferay.portlet.softwarecatalog.model.SCLicense','UPDATE',8),(124,'com.liferay.portlet.softwarecatalog.model.SCLicense','VIEW',1),(125,'com.liferay.portlet.documentlibrary.model.DLFileEntryType','DELETE',2),(126,'com.liferay.portlet.documentlibrary.model.DLFileEntryType','PERMISSIONS',4),(127,'com.liferay.portlet.documentlibrary.model.DLFileEntryType','UPDATE',8),(128,'com.liferay.portlet.documentlibrary.model.DLFileEntryType','VIEW',1),(129,'com.liferay.portlet.journal.model.JournalTemplate','DELETE',2),(130,'com.liferay.portlet.journal.model.JournalTemplate','PERMISSIONS',4),(131,'com.liferay.portlet.journal.model.JournalTemplate','UPDATE',8),(132,'com.liferay.portlet.journal.model.JournalTemplate','VIEW',1),(133,'com.liferay.portlet.journal.model.JournalArticle','ADD_DISCUSSION',2),(134,'com.liferay.portlet.journal.model.JournalArticle','DELETE',4),(135,'com.liferay.portlet.journal.model.JournalArticle','DELETE_DISCUSSION',8),(136,'com.liferay.portlet.journal.model.JournalArticle','EXPIRE',16),(137,'com.liferay.portlet.journal.model.JournalArticle','PERMISSIONS',32),(138,'com.liferay.portlet.journal.model.JournalArticle','UPDATE',64),(139,'com.liferay.portlet.journal.model.JournalArticle','UPDATE_DISCUSSION',128),(140,'com.liferay.portlet.journal.model.JournalArticle','VIEW',1),(141,'com.liferay.portlet.dynamicdatalists','ADD_RECORD_SET',2),(142,'com.liferay.portlet.dynamicdatalists','ADD_STRUCTURE',4),(143,'com.liferay.portlet.dynamicdatalists','ADD_TEMPLATE',8),(144,'com.liferay.portlet.dynamicdatalists','PERMISSIONS',16),(145,'com.liferay.portlet.bookmarks.model.BookmarksFolder','ACCESS',2),(146,'com.liferay.portlet.bookmarks.model.BookmarksFolder','ADD_ENTRY',4),(147,'com.liferay.portlet.bookmarks.model.BookmarksFolder','ADD_SUBFOLDER',8),(148,'com.liferay.portlet.bookmarks.model.BookmarksFolder','DELETE',16),(149,'com.liferay.portlet.bookmarks.model.BookmarksFolder','PERMISSIONS',32),(150,'com.liferay.portlet.bookmarks.model.BookmarksFolder','UPDATE',64),(151,'com.liferay.portlet.bookmarks.model.BookmarksFolder','VIEW',1),(152,'com.liferay.portal.model.Group','ADD_LAYOUT',2),(153,'com.liferay.portal.model.Group','ADD_LAYOUT_BRANCH',4),(154,'com.liferay.portal.model.Group','ADD_LAYOUT_SET_BRANCH',8),(155,'com.liferay.portal.model.Group','ASSIGN_MEMBERS',16),(156,'com.liferay.portal.model.Group','ASSIGN_USER_ROLES',32),(157,'com.liferay.portal.model.Group','CONFIGURE_PORTLETS',64),(158,'com.liferay.portal.model.Group','DELETE',128),(159,'com.liferay.portal.model.Group','EXPORT_IMPORT_LAYOUTS',256),(160,'com.liferay.portal.model.Group','EXPORT_IMPORT_PORTLET_INFO',512),(161,'com.liferay.portal.model.Group','MANAGE_ANNOUNCEMENTS',1024),(162,'com.liferay.portal.model.Group','MANAGE_ARCHIVED_SETUPS',2048),(163,'com.liferay.portal.model.Group','MANAGE_LAYOUTS',4096),(164,'com.liferay.portal.model.Group','MANAGE_STAGING',8192),(165,'com.liferay.portal.model.Group','MANAGE_TEAMS',16384),(166,'com.liferay.portal.model.Group','PERMISSIONS',32768),(167,'com.liferay.portal.model.Group','PUBLISH_STAGING',65536),(168,'com.liferay.portal.model.Group','PUBLISH_TO_REMOTE',131072),(169,'com.liferay.portal.model.Group','UPDATE',262144),(170,'com.liferay.portal.model.Group','VIEW',1),(171,'com.liferay.portal.model.Group','VIEW_MEMBERS',524288),(172,'com.liferay.portal.model.Group','VIEW_STAGING',1048576),(173,'com.liferay.portlet.journal.model.JournalStructure','DELETE',2),(174,'com.liferay.portlet.journal.model.JournalStructure','PERMISSIONS',4),(175,'com.liferay.portlet.journal.model.JournalStructure','UPDATE',8),(176,'com.liferay.portlet.journal.model.JournalStructure','VIEW',1),(177,'com.liferay.portlet.asset.model.AssetTag','DELETE',2),(178,'com.liferay.portlet.asset.model.AssetTag','PERMISSIONS',4),(179,'com.liferay.portlet.asset.model.AssetTag','UPDATE',8),(180,'com.liferay.portlet.asset.model.AssetTag','VIEW',1),(181,'com.liferay.portal.model.Layout','ADD_DISCUSSION',2),(182,'com.liferay.portal.model.Layout','ADD_LAYOUT',4),(183,'com.liferay.portal.model.Layout','CONFIGURE_PORTLETS',8),(184,'com.liferay.portal.model.Layout','CUSTOMIZE',16),(185,'com.liferay.portal.model.Layout','DELETE',32),(186,'com.liferay.portal.model.Layout','DELETE_DISCUSSION',64),(187,'com.liferay.portal.model.Layout','PERMISSIONS',128),(188,'com.liferay.portal.model.Layout','UPDATE',256),(189,'com.liferay.portal.model.Layout','UPDATE_DISCUSSION',512),(190,'com.liferay.portal.model.Layout','VIEW',1),(191,'com.liferay.portlet.asset','ADD_TAG',2),(192,'com.liferay.portlet.asset','PERMISSIONS',4),(193,'com.liferay.portlet.asset','ADD_CATEGORY',8),(194,'com.liferay.portlet.asset','ADD_VOCABULARY',16),(195,'com.liferay.portal.model.LayoutBranch','DELETE',2),(196,'com.liferay.portal.model.LayoutBranch','PERMISSIONS',4),(197,'com.liferay.portal.model.LayoutBranch','UPDATE',8),(198,'com.liferay.portal.model.LayoutSetBranch','DELETE',2),(199,'com.liferay.portal.model.LayoutSetBranch','MERGE',4),(200,'com.liferay.portal.model.LayoutSetBranch','PERMISSIONS',8),(201,'com.liferay.portal.model.LayoutSetBranch','UPDATE',16),(202,'com.liferay.portlet.messageboards','ADD_CATEGORY',2),(203,'com.liferay.portlet.messageboards','ADD_FILE',4),(204,'com.liferay.portlet.messageboards','ADD_MESSAGE',8),(205,'com.liferay.portlet.messageboards','BAN_USER',16),(206,'com.liferay.portlet.messageboards','MOVE_THREAD',32),(207,'com.liferay.portlet.messageboards','LOCK_THREAD',64),(208,'com.liferay.portlet.messageboards','PERMISSIONS',128),(209,'com.liferay.portlet.messageboards','REPLY_TO_MESSAGE',256),(210,'com.liferay.portlet.messageboards','SUBSCRIBE',512),(211,'com.liferay.portlet.messageboards','UPDATE_THREAD_PRIORITY',1024),(212,'com.liferay.portlet.messageboards','VIEW',1),(213,'com.liferay.portlet.polls','ADD_QUESTION',2),(214,'com.liferay.portlet.polls','PERMISSIONS',4),(215,'com.liferay.portlet.shopping.model.ShoppingItem','DELETE',2),(216,'com.liferay.portlet.shopping.model.ShoppingItem','PERMISSIONS',4),(217,'com.liferay.portlet.shopping.model.ShoppingItem','UPDATE',8),(218,'com.liferay.portlet.shopping.model.ShoppingItem','VIEW',1),(219,'com.liferay.portlet.bookmarks.model.BookmarksEntry','DELETE',2),(220,'com.liferay.portlet.bookmarks.model.BookmarksEntry','PERMISSIONS',4),(221,'com.liferay.portlet.bookmarks.model.BookmarksEntry','UPDATE',8),(222,'com.liferay.portlet.bookmarks.model.BookmarksEntry','VIEW',1),(223,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','ADD_DISCUSSION',2),(224,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','DELETE',4),(225,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','DELETE_DISCUSSION',8),(226,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','PERMISSIONS',16),(227,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','UPDATE',32),(228,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','UPDATE_DISCUSSION',64),(229,'com.liferay.portlet.softwarecatalog.model.SCProductEntry','VIEW',1),(230,'com.liferay.portal.model.User','DELETE',2),(231,'com.liferay.portal.model.User','IMPERSONATE',4),(232,'com.liferay.portal.model.User','PERMISSIONS',8),(233,'com.liferay.portal.model.User','UPDATE',16),(234,'com.liferay.portal.model.User','VIEW',1),(235,'com.liferay.portal.model.LayoutSetPrototype','DELETE',2),(236,'com.liferay.portal.model.LayoutSetPrototype','PERMISSIONS',4),(237,'com.liferay.portal.model.LayoutSetPrototype','UPDATE',8),(238,'com.liferay.portal.model.LayoutSetPrototype','VIEW',1),(239,'com.liferay.portlet.shopping','ADD_CATEGORY',2),(240,'com.liferay.portlet.shopping','ADD_ITEM',4),(241,'com.liferay.portlet.shopping','MANAGE_COUPONS',8),(242,'com.liferay.portlet.shopping','MANAGE_ORDERS',16),(243,'com.liferay.portlet.shopping','PERMISSIONS',32),(244,'com.liferay.portlet.shopping','VIEW',1),(245,'com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion','DELETE',2),(246,'com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion','PERMISSIONS',4),(247,'com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion','UPDATE',8),(248,'com.liferay.portlet.wiki','ADD_NODE',2),(249,'com.liferay.portlet.wiki','PERMISSIONS',4),(250,'com.liferay.portlet.polls.model.PollsQuestion','ADD_VOTE',2),(251,'com.liferay.portlet.polls.model.PollsQuestion','DELETE',4),(252,'com.liferay.portlet.polls.model.PollsQuestion','PERMISSIONS',8),(253,'com.liferay.portlet.polls.model.PollsQuestion','UPDATE',16),(254,'com.liferay.portlet.polls.model.PollsQuestion','VIEW',1),(255,'com.liferay.portlet.shopping.model.ShoppingOrder','DELETE',2),(256,'com.liferay.portlet.shopping.model.ShoppingOrder','PERMISSIONS',4),(257,'com.liferay.portlet.shopping.model.ShoppingOrder','UPDATE',8),(258,'com.liferay.portlet.shopping.model.ShoppingOrder','VIEW',1),(259,'com.liferay.portal.model.UserGroup','ASSIGN_MEMBERS',2),(260,'com.liferay.portal.model.UserGroup','DELETE',4),(261,'com.liferay.portal.model.UserGroup','MANAGE_ANNOUNCEMENTS',8),(262,'com.liferay.portal.model.UserGroup','PERMISSIONS',16),(263,'com.liferay.portal.model.UserGroup','UPDATE',32),(264,'com.liferay.portal.model.UserGroup','VIEW',1),(265,'com.liferay.portal.model.Role','ASSIGN_MEMBERS',2),(266,'com.liferay.portal.model.Role','DEFINE_PERMISSIONS',4),(267,'com.liferay.portal.model.Role','DELETE',8),(268,'com.liferay.portal.model.Role','MANAGE_ANNOUNCEMENTS',16),(269,'com.liferay.portal.model.Role','PERMISSIONS',32),(270,'com.liferay.portal.model.Role','UPDATE',64),(271,'com.liferay.portal.model.Role','VIEW',1),(272,'com.liferay.portlet.messageboards.model.MBCategory','ADD_FILE',2),(273,'com.liferay.portlet.messageboards.model.MBCategory','ADD_MESSAGE',4),(274,'com.liferay.portlet.messageboards.model.MBCategory','ADD_SUBCATEGORY',8),(275,'com.liferay.portlet.messageboards.model.MBCategory','DELETE',16),(276,'com.liferay.portlet.messageboards.model.MBCategory','LOCK_THREAD',32),(277,'com.liferay.portlet.messageboards.model.MBCategory','MOVE_THREAD',64),(278,'com.liferay.portlet.messageboards.model.MBCategory','PERMISSIONS',128),(279,'com.liferay.portlet.messageboards.model.MBCategory','REPLY_TO_MESSAGE',256),(280,'com.liferay.portlet.messageboards.model.MBCategory','SUBSCRIBE',512),(281,'com.liferay.portlet.messageboards.model.MBCategory','UPDATE',1024),(282,'com.liferay.portlet.messageboards.model.MBCategory','UPDATE_THREAD_PRIORITY',2048),(283,'com.liferay.portlet.messageboards.model.MBCategory','VIEW',1),(284,'com.liferay.portlet.mobiledevicerules','ADD_RULE_GROUP',2),(285,'com.liferay.portlet.mobiledevicerules','ADD_RULE_GROUP_INSTANCE',4),(286,'com.liferay.portlet.mobiledevicerules','CONFIGURATION',8),(287,'com.liferay.portlet.mobiledevicerules','VIEW',1),(288,'com.liferay.portlet.mobiledevicerules','PERMISSIONS',16),(289,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure','DELETE',2),(290,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure','PERMISSIONS',4),(291,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure','UPDATE',8),(292,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure','VIEW',1),(293,'com.liferay.portlet.wiki.model.WikiPage','ADD_DISCUSSION',2),(294,'com.liferay.portlet.wiki.model.WikiPage','DELETE',4),(295,'com.liferay.portlet.wiki.model.WikiPage','DELETE_DISCUSSION',8),(296,'com.liferay.portlet.wiki.model.WikiPage','PERMISSIONS',16),(297,'com.liferay.portlet.wiki.model.WikiPage','SUBSCRIBE',32),(298,'com.liferay.portlet.wiki.model.WikiPage','UPDATE',64),(299,'com.liferay.portlet.wiki.model.WikiPage','UPDATE_DISCUSSION',128),(300,'com.liferay.portlet.wiki.model.WikiPage','VIEW',1),(301,'com.liferay.portlet.asset.model.AssetCategory','ADD_CATEGORY',2),(302,'com.liferay.portlet.asset.model.AssetCategory','DELETE',4),(303,'com.liferay.portlet.asset.model.AssetCategory','PERMISSIONS',8),(304,'com.liferay.portlet.asset.model.AssetCategory','UPDATE',16),(305,'com.liferay.portlet.asset.model.AssetCategory','VIEW',1),(306,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup','DELETE',2),(307,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup','PERMISSIONS',4),(308,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup','UPDATE',8),(309,'com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup','VIEW',1),(310,'com.liferay.portlet.messageboards.model.MBMessage','DELETE',2),(311,'com.liferay.portlet.messageboards.model.MBMessage','PERMISSIONS',4),(312,'com.liferay.portlet.messageboards.model.MBMessage','SUBSCRIBE',8),(313,'com.liferay.portlet.messageboards.model.MBMessage','UPDATE',16),(314,'com.liferay.portlet.messageboards.model.MBMessage','VIEW',1),(315,'com.liferay.portlet.messageboards.model.MBThread','SUBSCRIBE',2),(316,'com.liferay.portlet.messageboards.model.MBThread','PERMISSIONS',4),(317,'com.liferay.portlet.blogs','ADD_ENTRY',2),(318,'com.liferay.portlet.blogs','PERMISSIONS',4),(319,'com.liferay.portlet.blogs','SUBSCRIBE',8),(320,'com.liferay.portlet.documentlibrary.model.DLFileEntry','ADD_DISCUSSION',2),(321,'com.liferay.portlet.documentlibrary.model.DLFileEntry','DELETE',4),(322,'com.liferay.portlet.documentlibrary.model.DLFileEntry','DELETE_DISCUSSION',8),(323,'com.liferay.portlet.documentlibrary.model.DLFileEntry','PERMISSIONS',16),(324,'com.liferay.portlet.documentlibrary.model.DLFileEntry','UPDATE',32),(325,'com.liferay.portlet.documentlibrary.model.DLFileEntry','UPDATE_DISCUSSION',64),(326,'com.liferay.portlet.documentlibrary.model.DLFileEntry','VIEW',1),(327,'98','ACCESS_IN_CONTROL_PANEL',2),(328,'98','ADD_TO_PAGE',4),(329,'98','CONFIGURATION',8),(330,'98','VIEW',1),(331,'98','PERMISSIONS',16),(332,'66','VIEW',1),(333,'66','ADD_TO_PAGE',2),(334,'66','CONFIGURATION',4),(335,'66','PERMISSIONS',8),(336,'156','VIEW',1),(337,'156','ADD_TO_PAGE',2),(338,'156','ACCESS_IN_CONTROL_PANEL',4),(339,'156','CONFIGURATION',8),(340,'156','PERMISSIONS',16),(341,'180','VIEW',1),(342,'180','ADD_TO_PAGE',2),(343,'180','CONFIGURATION',4),(344,'180','PERMISSIONS',8),(345,'152','ACCESS_IN_CONTROL_PANEL',2),(346,'152','CONFIGURATION',4),(347,'152','VIEW',1),(348,'152','PERMISSIONS',8),(349,'27','VIEW',1),(350,'27','ADD_TO_PAGE',2),(351,'27','CONFIGURATION',4),(352,'27','PERMISSIONS',8),(353,'88','VIEW',1),(354,'88','ADD_TO_PAGE',2),(355,'88','CONFIGURATION',4),(356,'88','PERMISSIONS',8),(357,'87','VIEW',1),(358,'87','ADD_TO_PAGE',2),(359,'87','CONFIGURATION',4),(360,'87','PERMISSIONS',8),(361,'134','ACCESS_IN_CONTROL_PANEL',2),(362,'134','CONFIGURATION',4),(363,'134','VIEW',1),(364,'134','PERMISSIONS',8),(365,'130','ACCESS_IN_CONTROL_PANEL',2),(366,'130','CONFIGURATION',4),(367,'130','VIEW',1),(368,'130','PERMISSIONS',8),(369,'122','VIEW',1),(370,'122','ADD_TO_PAGE',2),(371,'122','CONFIGURATION',4),(372,'122','PERMISSIONS',8),(373,'36','ADD_TO_PAGE',2),(374,'36','CONFIGURATION',4),(375,'36','VIEW',1),(376,'36','PERMISSIONS',8),(377,'26','VIEW',1),(378,'26','ADD_TO_PAGE',2),(379,'26','CONFIGURATION',4),(380,'26','PERMISSIONS',8),(381,'104','VIEW',1),(382,'104','ADD_TO_PAGE',2),(383,'104','ACCESS_IN_CONTROL_PANEL',4),(384,'104','CONFIGURATION',8),(385,'104','PERMISSIONS',16),(386,'175','VIEW',1),(387,'175','ADD_TO_PAGE',2),(388,'175','CONFIGURATION',4),(389,'175','PERMISSIONS',8),(390,'64','VIEW',1),(391,'64','ADD_TO_PAGE',2),(392,'64','CONFIGURATION',4),(393,'64','PERMISSIONS',8),(394,'153','ACCESS_IN_CONTROL_PANEL',2),(395,'153','ADD_TO_PAGE',4),(396,'153','CONFIGURATION',8),(397,'153','VIEW',1),(398,'153','PERMISSIONS',16),(399,'129','ACCESS_IN_CONTROL_PANEL',2),(400,'129','CONFIGURATION',4),(401,'129','VIEW',1),(402,'129','PERMISSIONS',8),(403,'179','VIEW',1),(404,'179','ADD_TO_PAGE',2),(405,'179','ACCESS_IN_CONTROL_PANEL',4),(406,'179','CONFIGURATION',8),(407,'179','PERMISSIONS',16),(408,'173','VIEW',1),(409,'173','ADD_TO_PAGE',2),(410,'173','ACCESS_IN_CONTROL_PANEL',4),(411,'173','CONFIGURATION',8),(412,'173','PERMISSIONS',16),(413,'100','VIEW',1),(414,'100','ADD_TO_PAGE',2),(415,'100','CONFIGURATION',4),(416,'100','PERMISSIONS',8),(417,'157','ACCESS_IN_CONTROL_PANEL',2),(418,'157','CONFIGURATION',4),(419,'157','VIEW',1),(420,'157','PERMISSIONS',8),(421,'19','ADD_TO_PAGE',2),(422,'19','CONFIGURATION',4),(423,'19','VIEW',1),(424,'19','PERMISSIONS',8),(425,'160','VIEW',1),(426,'160','ADD_TO_PAGE',2),(427,'160','CONFIGURATION',4),(428,'160','PERMISSIONS',8),(429,'128','ACCESS_IN_CONTROL_PANEL',2),(430,'128','CONFIGURATION',4),(431,'128','VIEW',1),(432,'128','PERMISSIONS',8),(433,'181','VIEW',1),(434,'181','ADD_TO_PAGE',2),(435,'181','CONFIGURATION',4),(436,'181','PERMISSIONS',8),(437,'86','VIEW',1),(438,'86','ADD_TO_PAGE',2),(439,'86','CONFIGURATION',4),(440,'86','PERMISSIONS',8),(441,'154','ACCESS_IN_CONTROL_PANEL',2),(442,'154','CONFIGURATION',4),(443,'154','VIEW',1),(444,'154','PERMISSIONS',8),(445,'148','VIEW',1),(446,'148','ADD_TO_PAGE',2),(447,'148','CONFIGURATION',4),(448,'148','PERMISSIONS',8),(449,'11','ADD_TO_PAGE',2),(450,'11','CONFIGURATION',4),(451,'11','VIEW',1),(452,'11','PERMISSIONS',8),(453,'29','ADD_TO_PAGE',2),(454,'29','CONFIGURATION',4),(455,'29','VIEW',1),(456,'29','PERMISSIONS',8),(457,'174','VIEW',1),(458,'174','ADD_TO_PAGE',2),(459,'174','ACCESS_IN_CONTROL_PANEL',4),(460,'174','CONFIGURATION',8),(461,'174','PERMISSIONS',16),(462,'158','ACCESS_IN_CONTROL_PANEL',2),(463,'158','ADD_TO_PAGE',4),(464,'158','CONFIGURATION',8),(465,'158','VIEW',1),(466,'158','PERMISSIONS',16),(467,'178','VIEW',1),(468,'178','ADD_TO_PAGE',2),(469,'178','ACCESS_IN_CONTROL_PANEL',4),(470,'178','CONFIGURATION',8),(471,'178','PERMISSIONS',16),(472,'124','VIEW',1),(473,'124','ADD_TO_PAGE',2),(474,'124','CONFIGURATION',4),(475,'124','PERMISSIONS',8),(476,'8','ACCESS_IN_CONTROL_PANEL',2),(477,'8','ADD_TO_PAGE',4),(478,'8','CONFIGURATION',8),(479,'8','VIEW',1),(480,'8','PERMISSIONS',16),(481,'58','ADD_TO_PAGE',2),(482,'58','CONFIGURATION',4),(483,'58','VIEW',1),(484,'58','PERMISSIONS',8),(485,'97','VIEW',1),(486,'97','ADD_TO_PAGE',2),(487,'97','CONFIGURATION',4),(488,'97','PERMISSIONS',8),(489,'71','ADD_TO_PAGE',2),(490,'71','CONFIGURATION',4),(491,'71','VIEW',1),(492,'71','PERMISSIONS',8),(493,'39','VIEW',1),(494,'39','ADD_TO_PAGE',2),(495,'39','CONFIGURATION',4),(496,'39','PERMISSIONS',8),(497,'177','CONFIGURATION',2),(498,'177','VIEW',1),(499,'177','ADD_TO_PAGE',4),(500,'177','PERMISSIONS',8),(501,'177','ACCESS_IN_CONTROL_PANEL',16),(502,'170','VIEW',1),(503,'170','ADD_TO_PAGE',2),(504,'170','CONFIGURATION',4),(505,'170','PERMISSIONS',8),(506,'85','ADD_TO_PAGE',2),(507,'85','CONFIGURATION',4),(508,'85','VIEW',1),(509,'85','PERMISSIONS',8),(510,'118','VIEW',1),(511,'118','ADD_TO_PAGE',2),(512,'118','CONFIGURATION',4),(513,'118','PERMISSIONS',8),(514,'107','VIEW',1),(515,'107','ADD_TO_PAGE',2),(516,'107','CONFIGURATION',4),(517,'107','PERMISSIONS',8),(518,'30','VIEW',1),(519,'30','ADD_TO_PAGE',2),(520,'30','CONFIGURATION',4),(521,'30','PERMISSIONS',8),(522,'147','ACCESS_IN_CONTROL_PANEL',2),(523,'147','CONFIGURATION',4),(524,'147','VIEW',1),(525,'147','PERMISSIONS',8),(526,'48','VIEW',1),(527,'48','ADD_TO_PAGE',2),(528,'48','CONFIGURATION',4),(529,'48','PERMISSIONS',8),(530,'125','ACCESS_IN_CONTROL_PANEL',2),(531,'125','CONFIGURATION',4),(532,'125','EXPORT_USER',8),(533,'125','VIEW',1),(534,'125','PERMISSIONS',16),(535,'161','ACCESS_IN_CONTROL_PANEL',2),(536,'161','CONFIGURATION',4),(537,'161','VIEW',1),(538,'161','PERMISSIONS',8),(539,'144','VIEW',1),(540,'144','ADD_TO_PAGE',2),(541,'144','CONFIGURATION',4),(542,'144','PERMISSIONS',8),(543,'146','ACCESS_IN_CONTROL_PANEL',2),(544,'146','CONFIGURATION',4),(545,'146','VIEW',1),(546,'146','PERMISSIONS',8),(547,'62','VIEW',1),(548,'62','ADD_TO_PAGE',2),(549,'62','CONFIGURATION',4),(550,'62','PERMISSIONS',8),(551,'162','ACCESS_IN_CONTROL_PANEL',2),(552,'162','CONFIGURATION',4),(553,'162','VIEW',1),(554,'162','PERMISSIONS',8),(555,'176','VIEW',1),(556,'176','ADD_TO_PAGE',2),(557,'176','ACCESS_IN_CONTROL_PANEL',4),(558,'176','CONFIGURATION',8),(559,'176','PERMISSIONS',16),(560,'172','VIEW',1),(561,'172','ADD_TO_PAGE',2),(562,'172','CONFIGURATION',4),(563,'172','PERMISSIONS',8),(564,'108','VIEW',1),(565,'108','ADD_TO_PAGE',2),(566,'108','CONFIGURATION',4),(567,'108','PERMISSIONS',8),(568,'139','ACCESS_IN_CONTROL_PANEL',2),(569,'139','ADD_EXPANDO',4),(570,'139','CONFIGURATION',8),(571,'139','VIEW',1),(572,'139','PERMISSIONS',16),(573,'84','ADD_ENTRY',2),(574,'84','ADD_TO_PAGE',4),(575,'84','CONFIGURATION',8),(576,'84','VIEW',1),(577,'84','PERMISSIONS',16),(578,'101','VIEW',1),(579,'101','ADD_TO_PAGE',2),(580,'101','CONFIGURATION',4),(581,'101','PERMISSIONS',8),(582,'121','VIEW',1),(583,'121','ADD_TO_PAGE',2),(584,'121','CONFIGURATION',4),(585,'121','PERMISSIONS',8),(586,'49','VIEW',1),(587,'49','ADD_TO_PAGE',2),(588,'49','CONFIGURATION',4),(589,'49','PERMISSIONS',8),(590,'143','VIEW',1),(591,'143','ADD_TO_PAGE',2),(592,'143','CONFIGURATION',4),(593,'143','PERMISSIONS',8),(594,'167','ACCESS_IN_CONTROL_PANEL',2),(595,'167','ADD_TO_PAGE',4),(596,'167','CONFIGURATION',8),(597,'167','VIEW',1),(598,'167','PERMISSIONS',16),(599,'77','VIEW',1),(600,'77','ADD_TO_PAGE',2),(601,'77','CONFIGURATION',4),(602,'77','PERMISSIONS',8),(603,'115','VIEW',1),(604,'115','ADD_TO_PAGE',2),(605,'115','CONFIGURATION',4),(606,'115','PERMISSIONS',8),(607,'56','ADD_TO_PAGE',2),(608,'56','CONFIGURATION',4),(609,'56','VIEW',1),(610,'56','PERMISSIONS',8),(611,'142','VIEW',1),(612,'142','ADD_TO_PAGE',2),(613,'142','CONFIGURATION',4),(614,'142','PERMISSIONS',8),(615,'111','VIEW',1),(616,'111','ADD_TO_PAGE',2),(617,'111','CONFIGURATION',4),(618,'111','PERMISSIONS',8),(619,'16','PREFERENCES',2),(620,'16','GUEST_PREFERENCES',4),(621,'16','VIEW',1),(622,'16','ADD_TO_PAGE',8),(623,'16','CONFIGURATION',16),(624,'16','PERMISSIONS',32),(625,'3','VIEW',1),(626,'3','ADD_TO_PAGE',2),(627,'3','CONFIGURATION',4),(628,'3','PERMISSIONS',8),(629,'20','ACCESS_IN_CONTROL_PANEL',2),(630,'20','ADD_TO_PAGE',4),(631,'20','CONFIGURATION',8),(632,'20','VIEW',1),(633,'20','PERMISSIONS',16),(634,'23','VIEW',1),(635,'23','ADD_TO_PAGE',2),(636,'23','CONFIGURATION',4),(637,'23','PERMISSIONS',8),(638,'145','VIEW',1),(639,'145','ADD_TO_PAGE',2),(640,'145','CONFIGURATION',4),(641,'145','PERMISSIONS',8),(642,'83','ADD_ENTRY',2),(643,'83','ADD_TO_PAGE',4),(644,'83','CONFIGURATION',8),(645,'83','VIEW',1),(646,'83','PERMISSIONS',16),(647,'99','ACCESS_IN_CONTROL_PANEL',2),(648,'99','CONFIGURATION',4),(649,'99','VIEW',1),(650,'99','PERMISSIONS',8),(651,'70','VIEW',1),(652,'70','ADD_TO_PAGE',2),(653,'70','CONFIGURATION',4),(654,'70','PERMISSIONS',8),(655,'164','VIEW',1),(656,'164','ADD_TO_PAGE',2),(657,'164','CONFIGURATION',4),(658,'164','PERMISSIONS',8),(659,'141','VIEW',1),(660,'141','ADD_TO_PAGE',2),(661,'141','CONFIGURATION',4),(662,'141','PERMISSIONS',8),(663,'9','VIEW',1),(664,'9','ADD_TO_PAGE',2),(665,'9','CONFIGURATION',4),(666,'9','PERMISSIONS',8),(667,'137','ACCESS_IN_CONTROL_PANEL',2),(668,'137','CONFIGURATION',4),(669,'137','VIEW',1),(670,'137','PERMISSIONS',8),(671,'28','ACCESS_IN_CONTROL_PANEL',2),(672,'28','ADD_TO_PAGE',4),(673,'28','CONFIGURATION',8),(674,'28','VIEW',1),(675,'28','PERMISSIONS',16),(676,'133','VIEW',1),(677,'133','ADD_TO_PAGE',2),(678,'133','CONFIGURATION',4),(679,'133','PERMISSIONS',8),(680,'116','VIEW',1),(681,'116','ADD_TO_PAGE',2),(682,'116','CONFIGURATION',4),(683,'116','PERMISSIONS',8),(684,'15','ACCESS_IN_CONTROL_PANEL',2),(685,'15','ADD_TO_PAGE',4),(686,'15','CONFIGURATION',8),(687,'15','VIEW',1),(688,'15','PERMISSIONS',16),(689,'47','VIEW',1),(690,'47','ADD_TO_PAGE',2),(691,'47','CONFIGURATION',4),(692,'47','PERMISSIONS',8),(693,'82','VIEW',1),(694,'82','ADD_TO_PAGE',2),(695,'82','CONFIGURATION',4),(696,'82','PERMISSIONS',8),(697,'103','VIEW',1),(698,'103','ADD_TO_PAGE',2),(699,'103','CONFIGURATION',4),(700,'103','PERMISSIONS',8),(701,'151','ACCESS_IN_CONTROL_PANEL',2),(702,'151','CONFIGURATION',4),(703,'151','VIEW',1),(704,'151','PERMISSIONS',8),(705,'140','ACCESS_IN_CONTROL_PANEL',2),(706,'140','CONFIGURATION',4),(707,'140','VIEW',1),(708,'140','PERMISSIONS',8),(709,'54','VIEW',1),(710,'54','ADD_TO_PAGE',2),(711,'54','CONFIGURATION',4),(712,'54','PERMISSIONS',8),(713,'169','VIEW',1),(714,'169','ADD_TO_PAGE',2),(715,'169','CONFIGURATION',4),(716,'169','PERMISSIONS',8),(717,'132','ACCESS_IN_CONTROL_PANEL',2),(718,'132','CONFIGURATION',4),(719,'132','VIEW',1),(720,'132','PERMISSIONS',8),(721,'34','ADD_TO_PAGE',2),(722,'34','CONFIGURATION',4),(723,'34','VIEW',1),(724,'34','PERMISSIONS',8),(725,'61','VIEW',1),(726,'61','ADD_TO_PAGE',2),(727,'61','CONFIGURATION',4),(728,'61','PERMISSIONS',8),(729,'73','ADD_TO_PAGE',2),(730,'73','CONFIGURATION',4),(731,'73','VIEW',1),(732,'73','PERMISSIONS',8),(733,'31','VIEW',1),(734,'31','ADD_TO_PAGE',2),(735,'31','CONFIGURATION',4),(736,'31','PERMISSIONS',8),(737,'165','VIEW',1),(738,'165','ADD_TO_PAGE',2),(739,'165','ACCESS_IN_CONTROL_PANEL',4),(740,'165','CONFIGURATION',8),(741,'165','PERMISSIONS',16),(742,'136','ACCESS_IN_CONTROL_PANEL',2),(743,'136','CONFIGURATION',4),(744,'136','VIEW',1),(745,'136','PERMISSIONS',8),(746,'127','VIEW',1),(747,'127','ADD_TO_PAGE',2),(748,'127','ACCESS_IN_CONTROL_PANEL',4),(749,'127','CONFIGURATION',8),(750,'127','PERMISSIONS',16),(751,'50','VIEW',1),(752,'50','ADD_TO_PAGE',2),(753,'50','CONFIGURATION',4),(754,'50','PERMISSIONS',8),(755,'25','ACCESS_IN_CONTROL_PANEL',2),(756,'25','CONFIGURATION',4),(757,'25','VIEW',1),(758,'25','PERMISSIONS',8),(759,'166','ACCESS_IN_CONTROL_PANEL',2),(760,'166','ADD_TO_PAGE',4),(761,'166','CONFIGURATION',8),(762,'166','VIEW',1),(763,'166','PERMISSIONS',16),(764,'90','ADD_COMMUNITY',2),(765,'90','ADD_LAYOUT_PROTOTYPE',4),(766,'90','ADD_LAYOUT_SET_PROTOTYPE',8),(767,'90','ADD_LICENSE',16),(768,'90','ADD_ORGANIZATION',32),(769,'90','ADD_PASSWORD_POLICY',64),(770,'90','ADD_ROLE',128),(771,'90','ADD_USER',256),(772,'90','ADD_USER_GROUP',512),(773,'90','CONFIGURATION',1024),(774,'90','EXPORT_USER',2048),(775,'90','IMPERSONATE',4096),(776,'90','UNLINK_LAYOUT_SET_PROTOTYPE',8192),(777,'90','VIEW_CONTROL_PANEL',16384),(778,'90','ADD_TO_PAGE',32768),(779,'90','PERMISSIONS',65536),(780,'90','VIEW',1),(781,'150','ACCESS_IN_CONTROL_PANEL',2),(782,'150','CONFIGURATION',4),(783,'150','VIEW',1),(784,'150','PERMISSIONS',8),(785,'113','VIEW',1),(786,'113','ADD_TO_PAGE',2),(787,'113','CONFIGURATION',4),(788,'113','PERMISSIONS',8),(789,'33','ADD_TO_PAGE',2),(790,'33','CONFIGURATION',4),(791,'33','VIEW',1),(792,'33','PERMISSIONS',8),(793,'2','ACCESS_IN_CONTROL_PANEL',2),(794,'2','CONFIGURATION',4),(795,'2','VIEW',1),(796,'2','PERMISSIONS',8),(797,'119','VIEW',1),(798,'119','ADD_TO_PAGE',2),(799,'119','CONFIGURATION',4),(800,'119','PERMISSIONS',8),(801,'114','VIEW',1),(802,'114','ADD_TO_PAGE',2),(803,'114','CONFIGURATION',4),(804,'114','PERMISSIONS',8),(805,'149','ACCESS_IN_CONTROL_PANEL',2),(806,'149','CONFIGURATION',4),(807,'149','VIEW',1),(808,'149','PERMISSIONS',8),(809,'67','VIEW',1),(810,'67','ADD_TO_PAGE',2),(811,'67','CONFIGURATION',4),(812,'67','PERMISSIONS',8),(813,'110','VIEW',1),(814,'110','ADD_TO_PAGE',2),(815,'110','CONFIGURATION',4),(816,'110','PERMISSIONS',8),(817,'135','ACCESS_IN_CONTROL_PANEL',2),(818,'135','CONFIGURATION',4),(819,'135','VIEW',1),(820,'135','PERMISSIONS',8),(821,'59','VIEW',1),(822,'59','ADD_TO_PAGE',2),(823,'59','CONFIGURATION',4),(824,'59','PERMISSIONS',8),(825,'131','ACCESS_IN_CONTROL_PANEL',2),(826,'131','CONFIGURATION',4),(827,'131','VIEW',1),(828,'131','PERMISSIONS',8),(829,'102','VIEW',1),(830,'102','ADD_TO_PAGE',2),(831,'102','CONFIGURATION',4),(832,'102','PERMISSIONS',8),(833,'1_WAR_marketplaceportlet','VIEW',1),(834,'1_WAR_marketplaceportlet','ADD_TO_PAGE',2),(835,'1_WAR_marketplaceportlet','ACCESS_IN_CONTROL_PANEL',4),(836,'1_WAR_marketplaceportlet','CONFIGURATION',8),(837,'1_WAR_marketplaceportlet','PERMISSIONS',16),(838,'2_WAR_marketplaceportlet','VIEW',1),(839,'2_WAR_marketplaceportlet','ADD_TO_PAGE',2),(840,'2_WAR_marketplaceportlet','ACCESS_IN_CONTROL_PANEL',4),(841,'2_WAR_marketplaceportlet','CONFIGURATION',8),(842,'2_WAR_marketplaceportlet','PERMISSIONS',16);
/*!40000 ALTER TABLE `resourceaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourceblock`
--

DROP TABLE IF EXISTS `resourceblock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourceblock` (
  `resourceBlockId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `permissionsHash` varchar(75) DEFAULT NULL,
  `referenceCount` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resourceBlockId`),
  UNIQUE KEY `IX_AEEA209C` (`companyId`,`groupId`,`name`,`permissionsHash`),
  KEY `IX_DA30B086` (`companyId`,`groupId`,`name`),
  KEY `IX_2D4CC782` (`companyId`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourceblock`
--

LOCK TABLES `resourceblock` WRITE;
/*!40000 ALTER TABLE `resourceblock` DISABLE KEYS */;
/*!40000 ALTER TABLE `resourceblock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourceblockpermission`
--

DROP TABLE IF EXISTS `resourceblockpermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourceblockpermission` (
  `resourceBlockPermissionId` bigint(20) NOT NULL,
  `resourceBlockId` bigint(20) DEFAULT NULL,
  `roleId` bigint(20) DEFAULT NULL,
  `actionIds` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resourceBlockPermissionId`),
  UNIQUE KEY `IX_D63D20BB` (`resourceBlockId`,`roleId`),
  KEY `IX_4AB3756` (`resourceBlockId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourceblockpermission`
--

LOCK TABLES `resourceblockpermission` WRITE;
/*!40000 ALTER TABLE `resourceblockpermission` DISABLE KEYS */;
/*!40000 ALTER TABLE `resourceblockpermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourcecode`
--

DROP TABLE IF EXISTS `resourcecode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourcecode` (
  `codeId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `scope` int(11) DEFAULT NULL,
  PRIMARY KEY (`codeId`),
  UNIQUE KEY `IX_A32C097E` (`companyId`,`name`,`scope`),
  KEY `IX_717FDD47` (`companyId`),
  KEY `IX_AACAFF40` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourcecode`
--

LOCK TABLES `resourcecode` WRITE;
/*!40000 ALTER TABLE `resourcecode` DISABLE KEYS */;
/*!40000 ALTER TABLE `resourcecode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourcepermission`
--

DROP TABLE IF EXISTS `resourcepermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourcepermission` (
  `resourcePermissionId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `scope` int(11) DEFAULT NULL,
  `primKey` varchar(255) DEFAULT NULL,
  `roleId` bigint(20) DEFAULT NULL,
  `ownerId` bigint(20) DEFAULT NULL,
  `actionIds` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resourcePermissionId`),
  KEY `IX_88705859` (`companyId`,`name`,`primKey`,`ownerId`),
  KEY `IX_C94C7708` (`companyId`,`name`,`primKey`,`roleId`,`actionIds`),
  KEY `IX_60B99860` (`companyId`,`name`,`scope`),
  KEY `IX_2200AA69` (`companyId`,`name`,`scope`,`primKey`),
  KEY `IX_8D83D0CE` (`companyId`,`name`,`scope`,`primKey`,`roleId`),
  KEY `IX_D2E2B644` (`companyId`,`name`,`scope`,`primKey`,`roleId`,`actionIds`),
  KEY `IX_4A1F4402` (`companyId`,`name`,`scope`,`primKey`,`roleId`,`ownerId`,`actionIds`),
  KEY `IX_26284944` (`companyId`,`primKey`),
  KEY `IX_A37A0588` (`roleId`),
  KEY `IX_F4555981` (`scope`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourcepermission`
--

LOCK TABLES `resourcepermission` WRITE;
/*!40000 ALTER TABLE `resourcepermission` DISABLE KEYS */;
INSERT INTO `resourcepermission` VALUES (164,10154,'100',1,'10154',10164,0,2),(165,10154,'100',1,'10154',10165,0,2),(29,10154,'100',2,'10189',10165,0,1),(214,10154,'101',1,'10154',10162,0,2),(215,10154,'101',1,'10154',10164,0,2),(216,10154,'101',1,'10154',10165,0,2),(60,10154,'101',2,'10189',10165,0,1),(344,10154,'101',4,'10323_LAYOUT_101_INSTANCE_0v9ySz6IOVYg',10162,0,1),(342,10154,'101',4,'10323_LAYOUT_101_INSTANCE_0v9ySz6IOVYg',10163,0,15),(343,10154,'101',4,'10323_LAYOUT_101_INSTANCE_0v9ySz6IOVYg',10170,0,1),(301,10154,'102',1,'10154',10162,0,2),(302,10154,'102',1,'10154',10164,0,2),(303,10154,'102',1,'10154',10165,0,2),(106,10154,'102',2,'10189',10165,0,1),(441,10154,'103',4,'10597_LAYOUT_103',10162,0,1),(439,10154,'103',4,'10597_LAYOUT_103',10163,0,15),(440,10154,'103',4,'10597_LAYOUT_103',10170,0,1),(2367,10154,'103',4,'10603_LAYOUT_103',10162,0,1),(2365,10154,'103',4,'10603_LAYOUT_103',10163,0,15),(2366,10154,'103',4,'10603_LAYOUT_103',10170,0,1),(2376,10154,'103',4,'10609_LAYOUT_103',10162,0,1),(2374,10154,'103',4,'10609_LAYOUT_103',10163,0,15),(2375,10154,'103',4,'10609_LAYOUT_103',10170,0,1),(463,10154,'103',4,'10635_LAYOUT_103',10162,0,1),(461,10154,'103',4,'10635_LAYOUT_103',10163,0,15),(462,10154,'103',4,'10635_LAYOUT_103',10170,0,1),(1110,10154,'103',4,'16347_LAYOUT_103',10162,0,1),(1108,10154,'103',4,'16347_LAYOUT_103',10163,0,15),(1109,10154,'103',4,'16347_LAYOUT_103',10170,0,1),(2727,10154,'103',4,'35201_LAYOUT_103',10162,0,1),(2725,10154,'103',4,'35201_LAYOUT_103',10163,0,15),(2726,10154,'103',4,'35201_LAYOUT_103',10170,0,1),(152,10154,'104',1,'10154',10161,0,2),(22,10154,'104',2,'10189',10165,0,1),(201,10154,'107',1,'10154',10164,0,2),(202,10154,'107',1,'10154',10165,0,2),(48,10154,'107',2,'10189',10165,0,1),(210,10154,'108',1,'10154',10164,0,2),(211,10154,'108',1,'10154',10165,0,2),(58,10154,'108',2,'10189',10165,0,1),(174,10154,'11',1,'10154',10164,0,2),(175,10154,'11',1,'10154',10165,0,2),(36,10154,'11',2,'10189',10165,0,1),(297,10154,'110',1,'10154',10164,0,2),(298,10154,'110',1,'10154',10165,0,2),(102,10154,'110',2,'10189',10165,0,1),(236,10154,'111',1,'10154',10161,0,2),(68,10154,'111',2,'10189',10165,0,1),(1312,10154,'113',4,'16347_LAYOUT_113',10162,0,1),(1310,10154,'113',4,'16347_LAYOUT_113',10163,0,15),(1311,10154,'113',4,'16347_LAYOUT_113',10170,0,1),(292,10154,'114',1,'10154',10162,0,2),(293,10154,'114',1,'10154',10164,0,2),(294,10154,'114',1,'10154',10165,0,2),(99,10154,'114',2,'10189',10165,0,1),(329,10154,'114',4,'10313_LAYOUT_114',10162,0,1),(327,10154,'114',4,'10313_LAYOUT_114',10163,0,15),(328,10154,'114',4,'10313_LAYOUT_114',10170,0,1),(228,10154,'115',1,'10154',10162,0,2),(229,10154,'115',1,'10154',10164,0,2),(230,10154,'115',1,'10154',10165,0,2),(65,10154,'115',2,'10189',10165,0,1),(263,10154,'116',1,'10154',10162,0,2),(264,10154,'116',1,'10154',10164,0,2),(265,10154,'116',1,'10154',10165,0,2),(82,10154,'116',2,'10189',10165,0,1),(198,10154,'118',1,'10154',10162,0,2),(199,10154,'118',1,'10154',10164,0,2),(200,10154,'118',1,'10154',10165,0,2),(47,10154,'118',2,'10189',10165,0,1),(217,10154,'121',1,'10154',10162,0,2),(218,10154,'121',1,'10154',10164,0,2),(219,10154,'121',1,'10154',10165,0,2),(61,10154,'121',2,'10189',10165,0,1),(145,10154,'122',1,'10154',10162,0,2),(146,10154,'122',1,'10154',10164,0,2),(147,10154,'122',1,'10154',10165,0,2),(19,10154,'122',2,'10189',10165,0,1),(338,10154,'122',4,'10323_LAYOUT_122_INSTANCE_9xxTt1olN52m',10162,0,1),(336,10154,'122',4,'10323_LAYOUT_122_INSTANCE_9xxTt1olN52m',10163,0,15),(337,10154,'122',4,'10323_LAYOUT_122_INSTANCE_9xxTt1olN52m',10170,0,1),(354,10154,'122',4,'10332_LAYOUT_122_INSTANCE_XZXjvFPL0CNe',10162,0,1),(352,10154,'122',4,'10332_LAYOUT_122_INSTANCE_XZXjvFPL0CNe',10163,0,15),(353,10154,'122',4,'10332_LAYOUT_122_INSTANCE_XZXjvFPL0CNe',10170,0,1),(52,10154,'125',2,'10189',10165,0,1),(1315,10154,'125',4,'10175_LAYOUT_125',10162,0,1),(1313,10154,'125',4,'10175_LAYOUT_125',10163,0,31),(1314,10154,'125',4,'10175_LAYOUT_125',10170,0,1),(286,10154,'127',1,'10154',10161,0,2),(94,10154,'127',2,'10189',10165,0,1),(32,10154,'128',2,'10189',10165,0,1),(2209,10154,'128',4,'10175_LAYOUT_128',10162,0,1),(2207,10154,'128',4,'10175_LAYOUT_128',10163,0,15),(2208,10154,'128',4,'10175_LAYOUT_128',10170,0,1),(26,10154,'129',2,'10189',10165,0,1),(18,10154,'130',2,'10189',10165,0,1),(105,10154,'131',2,'10189',10165,0,1),(88,10154,'132',2,'10189',10165,0,1),(17,10154,'134',2,'10189',10165,0,1),(104,10154,'135',2,'10189',10165,0,1),(92,10154,'136',2,'10189',10165,0,1),(79,10154,'137',2,'10189',10165,0,1),(2,10154,'140',1,'10154',10165,0,2),(252,10154,'141',1,'10154',10162,0,2),(253,10154,'141',1,'10154',10164,0,2),(254,10154,'141',1,'10154',10165,0,2),(76,10154,'141',2,'10189',10165,0,1),(335,10154,'141',4,'10323_LAYOUT_141_INSTANCE_sdK2lcsmGwhZ',10162,0,1),(333,10154,'141',4,'10323_LAYOUT_141_INSTANCE_sdK2lcsmGwhZ',10163,0,15),(334,10154,'141',4,'10323_LAYOUT_141_INSTANCE_sdK2lcsmGwhZ',10170,0,1),(357,10154,'141',4,'10332_LAYOUT_141_INSTANCE_k06f1IndZnHz',10162,0,1),(355,10154,'141',4,'10332_LAYOUT_141_INSTANCE_k06f1IndZnHz',10163,0,15),(356,10154,'141',4,'10332_LAYOUT_141_INSTANCE_k06f1IndZnHz',10170,0,1),(220,10154,'143',1,'10154',10162,0,2),(221,10154,'143',1,'10154',10164,0,2),(222,10154,'143',1,'10154',10165,0,2),(62,10154,'143',2,'10189',10165,0,1),(1203,10154,'145',4,'10175_LAYOUT_145',10162,0,1),(1201,10154,'145',4,'10175_LAYOUT_145',10163,0,15),(1202,10154,'145',4,'10175_LAYOUT_145',10170,0,1),(454,10154,'145',4,'10597_LAYOUT_145',10162,0,1),(452,10154,'145',4,'10597_LAYOUT_145',10163,0,15),(453,10154,'145',4,'10597_LAYOUT_145',10170,0,1),(2373,10154,'145',4,'10603_LAYOUT_145',10162,0,1),(2371,10154,'145',4,'10603_LAYOUT_145',10163,0,15),(2372,10154,'145',4,'10603_LAYOUT_145',10170,0,1),(2382,10154,'145',4,'10609_LAYOUT_145',10162,0,1),(2380,10154,'145',4,'10609_LAYOUT_145',10163,0,15),(2381,10154,'145',4,'10609_LAYOUT_145',10170,0,1),(466,10154,'145',4,'10635_LAYOUT_145',10162,0,1),(464,10154,'145',4,'10635_LAYOUT_145',10163,0,15),(465,10154,'145',4,'10635_LAYOUT_145',10170,0,1),(1113,10154,'145',4,'16347_LAYOUT_145',10162,0,1),(1111,10154,'145',4,'16347_LAYOUT_145',10163,0,15),(1112,10154,'145',4,'16347_LAYOUT_145',10170,0,1),(2730,10154,'145',4,'35201_LAYOUT_145',10162,0,1),(2728,10154,'145',4,'35201_LAYOUT_145',10163,0,15),(2729,10154,'145',4,'35201_LAYOUT_145',10170,0,1),(54,10154,'146',2,'10189',10165,0,1),(117,10154,'147',2,'10189',10164,0,2),(50,10154,'147',2,'10189',10165,0,1),(171,10154,'148',1,'10154',10162,0,2),(172,10154,'148',1,'10154',10164,0,2),(173,10154,'148',1,'10154',10165,0,2),(35,10154,'148',2,'10189',10165,0,1),(326,10154,'148',4,'10313_LAYOUT_148_INSTANCE_r3brs7aSUO5i',10162,0,1),(324,10154,'148',4,'10313_LAYOUT_148_INSTANCE_r3brs7aSUO5i',10163,0,15),(325,10154,'148',4,'10313_LAYOUT_148_INSTANCE_r3brs7aSUO5i',10170,0,1),(100,10154,'149',2,'10189',10165,0,1),(261,10154,'15',1,'10154',10164,0,4),(262,10154,'15',1,'10154',10165,0,4),(124,10154,'15',2,'10189',10164,0,2),(81,10154,'15',2,'10189',10165,0,1),(98,10154,'150',2,'10189',10165,0,1),(84,10154,'151',2,'10189',10165,0,1),(111,10154,'152',2,'10189',10164,0,2),(16,10154,'152',2,'10189',10165,0,1),(156,10154,'153',1,'10154',10164,0,4),(4,10154,'153',1,'10154',10165,0,2),(24,10154,'153',2,'10189',10165,0,1),(114,10154,'154',2,'10189',10164,0,2),(34,10154,'154',2,'10189',10165,0,1),(2203,10154,'156',4,'10175_LAYOUT_156',10162,0,1),(2201,10154,'156',4,'10175_LAYOUT_156',10163,0,31),(2202,10154,'156',4,'10175_LAYOUT_156',10170,0,1),(31,10154,'157',2,'10189',10165,0,1),(178,10154,'158',1,'10154',10164,0,4),(3,10154,'158',1,'10154',10165,0,2),(38,10154,'158',2,'10189',10165,0,1),(234,10154,'16',1,'10154',10164,0,8),(235,10154,'16',1,'10154',10165,0,8),(67,10154,'16',2,'10189',10165,0,1),(1116,10154,'160',4,'10175_LAYOUT_160',10162,0,1),(1114,10154,'160',4,'10175_LAYOUT_160',10163,0,15),(1115,10154,'160',4,'10175_LAYOUT_160',10170,0,1),(118,10154,'161',2,'10189',10164,0,2),(53,10154,'161',2,'10189',10165,0,1),(119,10154,'162',2,'10189',10164,0,2),(56,10154,'162',2,'10189',10165,0,1),(247,10154,'164',1,'10154',10162,0,2),(248,10154,'164',1,'10154',10164,0,2),(249,10154,'164',1,'10154',10165,0,2),(73,10154,'164',2,'10189',10165,0,1),(2733,10154,'165',4,'10175_LAYOUT_165',10162,0,1),(2731,10154,'165',4,'10175_LAYOUT_165',10163,0,31),(2732,10154,'165',4,'10175_LAYOUT_165',10170,0,1),(287,10154,'166',1,'10154',10164,0,4),(288,10154,'166',1,'10154',10165,0,4),(96,10154,'166',2,'10189',10165,0,1),(226,10154,'167',1,'10154',10164,0,4),(227,10154,'167',1,'10154',10165,0,4),(120,10154,'167',2,'10189',10164,0,2),(64,10154,'167',2,'10189',10165,0,1),(273,10154,'169',1,'10154',10164,0,2),(274,10154,'169',1,'10154',10165,0,2),(87,10154,'169',2,'10189',10165,0,1),(161,10154,'173',1,'10154',10162,0,2),(162,10154,'173',1,'10154',10164,0,2),(163,10154,'173',1,'10154',10165,0,2),(113,10154,'173',2,'10189',10164,0,4),(28,10154,'173',2,'10189',10165,0,1),(2206,10154,'174',4,'10175_LAYOUT_174',10162,0,1),(2204,10154,'174',4,'10175_LAYOUT_174',10163,0,31),(2205,10154,'174',4,'10175_LAYOUT_174',10170,0,1),(153,10154,'175',1,'10154',10162,0,2),(154,10154,'175',1,'10154',10164,0,2),(155,10154,'175',1,'10154',10165,0,2),(23,10154,'175',2,'10189',10165,0,1),(209,10154,'176',1,'10154',10161,0,2),(57,10154,'176',2,'10189',10165,0,1),(193,10154,'177',1,'10154',10164,0,4),(194,10154,'177',1,'10154',10165,0,4),(45,10154,'177',2,'10189',10165,0,1),(179,10154,'178',1,'10154',10164,0,2),(180,10154,'178',1,'10154',10165,0,2),(115,10154,'178',2,'10189',10164,0,4),(39,10154,'178',2,'10189',10165,0,1),(160,10154,'179',1,'10154',10161,0,2),(112,10154,'179',2,'10189',10164,0,4),(27,10154,'179',2,'10189',10165,0,1),(140,10154,'180',1,'10154',10162,0,2),(141,10154,'180',1,'10154',10164,0,2),(142,10154,'180',1,'10154',10165,0,2),(14,10154,'180',2,'10189',10165,0,1),(168,10154,'181',1,'10154',10162,0,2),(169,10154,'181',1,'10154',10164,0,2),(170,10154,'181',1,'10154',10165,0,2),(33,10154,'181',2,'10189',10165,0,1),(166,10154,'19',1,'10154',10164,0,2),(167,10154,'19',1,'10154',10165,0,2),(30,10154,'19',2,'10189',10165,0,1),(1119,10154,'1_WAR_marketplaceportlet',4,'10175_LAYOUT_1_WAR_marketplaceportlet',10162,0,1),(1117,10154,'1_WAR_marketplaceportlet',4,'10175_LAYOUT_1_WAR_marketplaceportlet',10163,0,31),(1118,10154,'1_WAR_marketplaceportlet',4,'10175_LAYOUT_1_WAR_marketplaceportlet',10170,0,1),(1,10154,'2',1,'10154',10165,0,2),(242,10154,'20',1,'10154',10162,0,4),(243,10154,'20',1,'10154',10164,0,4),(244,10154,'20',1,'10154',10165,0,4),(121,10154,'20',2,'10189',10164,0,2),(71,10154,'20',2,'10189',10165,0,1),(472,10154,'20',4,'10635_LAYOUT_20',10162,0,1),(470,10154,'20',4,'10635_LAYOUT_20',10163,0,31),(471,10154,'20',4,'10635_LAYOUT_20',10170,0,1),(240,10154,'23',1,'10154',10164,0,2),(241,10154,'23',1,'10154',10165,0,2),(70,10154,'23',2,'10189',10165,0,1),(125,10154,'25',2,'10189',10164,0,2),(95,10154,'25',2,'10189',10165,0,1),(150,10154,'26',1,'10154',10164,0,2),(151,10154,'26',1,'10154',10165,0,2),(21,10154,'26',2,'10189',10165,0,1),(143,10154,'27',1,'10154',10164,0,2),(144,10154,'27',1,'10154',10165,0,2),(15,10154,'27',2,'10189',10165,0,1),(256,10154,'28',1,'10154',10164,0,4),(257,10154,'28',1,'10154',10165,0,4),(123,10154,'28',2,'10189',10164,0,2),(78,10154,'28',2,'10189',10165,0,1),(176,10154,'29',1,'10154',10164,0,2),(177,10154,'29',1,'10154',10165,0,2),(37,10154,'29',2,'10189',10165,0,1),(2306,10154,'2_WAR_marketplaceportlet',4,'10175_LAYOUT_2_WAR_marketplaceportlet',10162,0,1),(2304,10154,'2_WAR_marketplaceportlet',4,'10175_LAYOUT_2_WAR_marketplaceportlet',10163,0,31),(2305,10154,'2_WAR_marketplaceportlet',4,'10175_LAYOUT_2_WAR_marketplaceportlet',10170,0,1),(237,10154,'3',1,'10154',10162,0,2),(238,10154,'3',1,'10154',10164,0,2),(239,10154,'3',1,'10154',10165,0,2),(69,10154,'3',2,'10189',10165,0,1),(341,10154,'3',4,'10323_LAYOUT_3',10162,0,1),(339,10154,'3',4,'10323_LAYOUT_3',10163,0,15),(340,10154,'3',4,'10323_LAYOUT_3',10170,0,1),(203,10154,'30',1,'10154',10164,0,2),(204,10154,'30',1,'10154',10165,0,2),(49,10154,'30',2,'10189',10165,0,1),(280,10154,'31',1,'10154',10162,0,2),(281,10154,'31',1,'10154',10164,0,2),(282,10154,'31',1,'10154',10165,0,2),(91,10154,'31',2,'10189',10165,0,1),(289,10154,'33',1,'10154',10162,0,2),(290,10154,'33',1,'10154',10164,0,2),(291,10154,'33',1,'10154',10165,0,2),(97,10154,'33',2,'10189',10165,0,1),(322,10154,'33',4,'10313_LAYOUT_33',10162,0,1),(320,10154,'33',4,'10313_LAYOUT_33',10163,0,15),(321,10154,'33',4,'10313_LAYOUT_33',10170,0,1),(271,10154,'34',1,'10154',10164,0,2),(272,10154,'34',1,'10154',10165,0,2),(86,10154,'34',2,'10189',10165,0,1),(148,10154,'36',1,'10154',10164,0,2),(149,10154,'36',1,'10154',10165,0,2),(20,10154,'36',2,'10189',10165,0,1),(350,10154,'36',4,'10332_LAYOUT_36',10162,0,1),(348,10154,'36',4,'10332_LAYOUT_36',10163,0,15),(349,10154,'36',4,'10332_LAYOUT_36',10170,0,1),(191,10154,'39',1,'10154',10164,0,2),(192,10154,'39',1,'10154',10165,0,2),(44,10154,'39',2,'10189',10165,0,1),(258,10154,'47',1,'10154',10162,0,2),(259,10154,'47',1,'10154',10164,0,2),(260,10154,'47',1,'10154',10165,0,2),(80,10154,'47',2,'10189',10165,0,1),(205,10154,'48',1,'10154',10164,0,2),(206,10154,'48',1,'10154',10165,0,2),(51,10154,'48',2,'10189',10165,0,1),(1703,10154,'48',4,'16347_LAYOUT_48_INSTANCE_QchntqUvZ1JY',10162,0,1),(1701,10154,'48',4,'16347_LAYOUT_48_INSTANCE_QchntqUvZ1JY',10163,0,15),(1702,10154,'48',4,'16347_LAYOUT_48_INSTANCE_QchntqUvZ1JY',10170,0,1),(2739,10154,'48',4,'35201_LAYOUT_48_INSTANCE_z5aG4NvnwC4O',10162,0,1),(2737,10154,'48',4,'35201_LAYOUT_48_INSTANCE_z5aG4NvnwC4O',10163,0,15),(2738,10154,'48',4,'35201_LAYOUT_48_INSTANCE_z5aG4NvnwC4O',10170,0,1),(1210,10154,'49',4,'16347_LAYOUT_49',10162,0,1),(1208,10154,'49',4,'16347_LAYOUT_49',10163,0,15),(1209,10154,'49',4,'16347_LAYOUT_49',10170,0,1),(283,10154,'50',1,'10154',10162,0,2),(284,10154,'50',1,'10154',10164,0,2),(285,10154,'50',1,'10154',10165,0,2),(93,10154,'50',2,'10189',10165,0,1),(269,10154,'54',1,'10154',10164,0,2),(270,10154,'54',1,'10154',10165,0,2),(85,10154,'54',2,'10189',10165,0,1),(231,10154,'56',1,'10154',10162,0,2),(232,10154,'56',1,'10154',10164,0,2),(233,10154,'56',1,'10154',10165,0,2),(66,10154,'56',2,'10189',10165,0,1),(2370,10154,'56',4,'10603_LAYOUT_56_INSTANCE_aTzDuB3geL4w',10162,0,1),(2368,10154,'56',4,'10603_LAYOUT_56_INSTANCE_aTzDuB3geL4w',10163,0,15),(2369,10154,'56',4,'10603_LAYOUT_56_INSTANCE_aTzDuB3geL4w',10170,0,1),(2379,10154,'56',4,'10609_LAYOUT_56_INSTANCE_KbtAD0VeRh9k',10162,0,1),(2377,10154,'56',4,'10609_LAYOUT_56_INSTANCE_KbtAD0VeRh9k',10163,0,15),(2378,10154,'56',4,'10609_LAYOUT_56_INSTANCE_KbtAD0VeRh9k',10170,0,1),(183,10154,'58',1,'10154',10162,0,2),(184,10154,'58',1,'10154',10164,0,2),(185,10154,'58',1,'10154',10165,0,2),(41,10154,'58',2,'10189',10165,0,1),(447,10154,'58',4,'10597_LAYOUT_58',10162,0,1),(445,10154,'58',4,'10597_LAYOUT_58',10163,0,15),(446,10154,'58',4,'10597_LAYOUT_58',10170,0,1),(504,10154,'58',4,'10635_LAYOUT_58',10162,0,1),(502,10154,'58',4,'10635_LAYOUT_58',10163,0,15),(503,10154,'58',4,'10635_LAYOUT_58',10170,0,1),(1213,10154,'58',4,'16347_LAYOUT_58',10162,0,1),(1211,10154,'58',4,'16347_LAYOUT_58',10163,0,15),(1212,10154,'58',4,'16347_LAYOUT_58',10170,0,1),(299,10154,'59',1,'10154',10164,0,2),(300,10154,'59',1,'10154',10165,0,2),(103,10154,'59',2,'10189',10165,0,1),(275,10154,'61',1,'10154',10164,0,2),(276,10154,'61',1,'10154',10165,0,2),(89,10154,'61',2,'10189',10165,0,1),(207,10154,'62',1,'10154',10164,0,2),(208,10154,'62',1,'10154',10165,0,2),(55,10154,'62',2,'10189',10165,0,1),(157,10154,'64',1,'10154',10162,0,2),(158,10154,'64',1,'10154',10164,0,2),(159,10154,'64',1,'10154',10165,0,2),(25,10154,'64',2,'10189',10165,0,1),(138,10154,'66',1,'10154',10164,0,2),(139,10154,'66',1,'10154',10165,0,2),(13,10154,'66',2,'10189',10165,0,1),(295,10154,'67',1,'10154',10164,0,2),(296,10154,'67',1,'10154',10165,0,2),(101,10154,'67',2,'10189',10165,0,1),(250,10154,'70',1,'10154',10164,0,2),(251,10154,'70',1,'10154',10165,0,2),(75,10154,'70',2,'10189',10165,0,1),(186,10154,'71',1,'10154',10162,0,2),(187,10154,'71',1,'10154',10164,0,2),(188,10154,'71',1,'10154',10165,0,2),(42,10154,'71',2,'10189',10165,0,1),(277,10154,'73',1,'10154',10162,0,2),(278,10154,'73',1,'10154',10164,0,2),(279,10154,'73',1,'10154',10165,0,2),(90,10154,'73',2,'10189',10165,0,1),(223,10154,'77',1,'10154',10162,0,2),(224,10154,'77',1,'10154',10164,0,2),(225,10154,'77',1,'10154',10165,0,2),(63,10154,'77',2,'10189',10165,0,1),(181,10154,'8',1,'10154',10164,0,4),(182,10154,'8',1,'10154',10165,0,4),(116,10154,'8',2,'10189',10164,0,2),(40,10154,'8',2,'10189',10165,0,1),(266,10154,'82',1,'10154',10162,0,2),(267,10154,'82',1,'10154',10164,0,2),(268,10154,'82',1,'10154',10165,0,2),(83,10154,'82',2,'10189',10165,0,1),(245,10154,'83',1,'10154',10164,0,4),(246,10154,'83',1,'10154',10165,0,4),(72,10154,'83',2,'10189',10165,0,1),(212,10154,'84',1,'10154',10164,0,4),(213,10154,'84',1,'10154',10165,0,4),(59,10154,'84',2,'10189',10165,0,1),(195,10154,'85',1,'10154',10162,0,2),(196,10154,'85',1,'10154',10164,0,2),(197,10154,'85',1,'10154',10165,0,2),(46,10154,'85',2,'10189',10165,0,1),(1309,10154,'86',4,'16347_LAYOUT_86',10162,0,1),(1307,10154,'86',4,'16347_LAYOUT_86',10163,0,15),(1308,10154,'86',4,'16347_LAYOUT_86',10170,0,1),(2742,10154,'86',4,'35201_LAYOUT_86',10162,0,1),(2740,10154,'86',4,'35201_LAYOUT_86',10163,0,15),(2741,10154,'86',4,'35201_LAYOUT_86',10170,0,1),(2303,10154,'87',4,'10597_LAYOUT_87',10162,0,1),(2301,10154,'87',4,'10597_LAYOUT_87',10163,0,15),(2302,10154,'87',4,'10597_LAYOUT_87',10170,0,1),(469,10154,'87',4,'10635_LAYOUT_87',10162,0,1),(467,10154,'87',4,'10635_LAYOUT_87',10163,0,15),(468,10154,'87',4,'10635_LAYOUT_87',10170,0,1),(1303,10154,'87',4,'16347_LAYOUT_87',10162,0,1),(1301,10154,'87',4,'16347_LAYOUT_87',10163,0,15),(1302,10154,'87',4,'16347_LAYOUT_87',10170,0,1),(2736,10154,'87',4,'35201_LAYOUT_87',10162,0,1),(2734,10154,'87',4,'35201_LAYOUT_87',10163,0,15),(2735,10154,'87',4,'35201_LAYOUT_87',10170,0,1),(457,10154,'88',4,'10175_LAYOUT_88',10162,0,1),(455,10154,'88',4,'10175_LAYOUT_88',10163,0,15),(456,10154,'88',4,'10175_LAYOUT_88',10170,0,1),(255,10154,'9',1,'10154',10161,0,2),(77,10154,'9',2,'10189',10165,0,1),(11,10154,'90',1,'10154',10165,0,16384),(189,10154,'97',1,'10154',10164,0,2),(190,10154,'97',1,'10154',10165,0,2),(43,10154,'97',2,'10189',10165,0,1),(136,10154,'98',1,'10154',10164,0,4),(137,10154,'98',1,'10154',10165,0,4),(110,10154,'98',2,'10189',10164,0,2),(12,10154,'98',2,'10189',10165,0,1),(1206,10154,'98',4,'10175_LAYOUT_98',10162,0,1),(1204,10154,'98',4,'10175_LAYOUT_98',10163,0,31),(1205,10154,'98',4,'10175_LAYOUT_98',10170,0,1),(122,10154,'99',2,'10189',10164,0,2),(74,10154,'99',2,'10189',10165,0,1),(126,10154,'com.liferay.portal.model.Group',2,'10189',10164,0,4096),(107,10154,'com.liferay.portal.model.Layout',2,'10189',10165,0,1),(7,10154,'com.liferay.portal.model.Layout',4,'10175',10162,0,1),(5,10154,'com.liferay.portal.model.Layout',4,'10175',10163,10158,1023),(6,10154,'com.liferay.portal.model.Layout',4,'10175',10170,0,19),(317,10154,'com.liferay.portal.model.Layout',4,'10313',10163,10158,1023),(318,10154,'com.liferay.portal.model.Layout',4,'10313',10170,0,19),(331,10154,'com.liferay.portal.model.Layout',4,'10323',10163,10158,1023),(332,10154,'com.liferay.portal.model.Layout',4,'10323',10170,0,19),(346,10154,'com.liferay.portal.model.Layout',4,'10332',10163,10158,1023),(347,10154,'com.liferay.portal.model.Layout',4,'10332',10170,0,19),(364,10154,'com.liferay.portal.model.Layout',4,'10346',10162,0,1),(362,10154,'com.liferay.portal.model.Layout',4,'10346',10163,10158,1023),(363,10154,'com.liferay.portal.model.Layout',4,'10346',10170,0,19),(367,10154,'com.liferay.portal.model.Layout',4,'10352',10162,0,1),(365,10154,'com.liferay.portal.model.Layout',4,'10352',10163,10158,1023),(366,10154,'com.liferay.portal.model.Layout',4,'10352',10170,0,19),(370,10154,'com.liferay.portal.model.Layout',4,'10358',10162,0,1),(368,10154,'com.liferay.portal.model.Layout',4,'10358',10163,10158,1023),(369,10154,'com.liferay.portal.model.Layout',4,'10358',10170,0,19),(377,10154,'com.liferay.portal.model.Layout',4,'10372',10162,0,1),(375,10154,'com.liferay.portal.model.Layout',4,'10372',10163,10158,1023),(376,10154,'com.liferay.portal.model.Layout',4,'10372',10170,0,19),(380,10154,'com.liferay.portal.model.Layout',4,'10380',10162,0,1),(378,10154,'com.liferay.portal.model.Layout',4,'10380',10163,10158,1023),(379,10154,'com.liferay.portal.model.Layout',4,'10380',10170,0,19),(383,10154,'com.liferay.portal.model.Layout',4,'10386',10162,0,1),(381,10154,'com.liferay.portal.model.Layout',4,'10386',10163,10158,1023),(382,10154,'com.liferay.portal.model.Layout',4,'10386',10170,0,19),(386,10154,'com.liferay.portal.model.Layout',4,'10392',10162,0,1),(384,10154,'com.liferay.portal.model.Layout',4,'10392',10163,10158,1023),(385,10154,'com.liferay.portal.model.Layout',4,'10392',10170,0,19),(432,10154,'com.liferay.portal.model.Layout',4,'10597',10162,0,1),(430,10154,'com.liferay.portal.model.Layout',4,'10597',10163,10158,1023),(431,10154,'com.liferay.portal.model.Layout',4,'10597',10170,0,19),(435,10154,'com.liferay.portal.model.Layout',4,'10603',10162,0,1),(433,10154,'com.liferay.portal.model.Layout',4,'10603',10163,10158,1023),(434,10154,'com.liferay.portal.model.Layout',4,'10603',10170,0,19),(438,10154,'com.liferay.portal.model.Layout',4,'10609',10162,0,1),(436,10154,'com.liferay.portal.model.Layout',4,'10609',10163,10158,1023),(437,10154,'com.liferay.portal.model.Layout',4,'10609',10170,0,19),(448,10154,'com.liferay.portal.model.Layout',4,'10619',10163,10196,1023),(451,10154,'com.liferay.portal.model.Layout',4,'10624',10162,0,1),(449,10154,'com.liferay.portal.model.Layout',4,'10624',10163,10196,1023),(450,10154,'com.liferay.portal.model.Layout',4,'10624',10164,0,19),(460,10154,'com.liferay.portal.model.Layout',4,'10635',10162,0,1),(458,10154,'com.liferay.portal.model.Layout',4,'10635',10163,10196,1023),(459,10154,'com.liferay.portal.model.Layout',4,'10635',10170,0,19),(1107,10154,'com.liferay.portal.model.Layout',4,'16347',10162,0,1),(1105,10154,'com.liferay.portal.model.Layout',4,'16347',10163,10196,1023),(1106,10154,'com.liferay.portal.model.Layout',4,'16347',10170,0,19),(1317,10154,'com.liferay.portal.model.Layout',4,'16571',10163,16562,1023),(1320,10154,'com.liferay.portal.model.Layout',4,'16576',10162,0,1),(1318,10154,'com.liferay.portal.model.Layout',4,'16576',10163,16562,1023),(1319,10154,'com.liferay.portal.model.Layout',4,'16576',10164,0,19),(2349,10154,'com.liferay.portal.model.Layout',4,'32368',10162,0,1),(2347,10154,'com.liferay.portal.model.Layout',4,'32368',10163,10158,1023),(2348,10154,'com.liferay.portal.model.Layout',4,'32368',10170,0,19),(2352,10154,'com.liferay.portal.model.Layout',4,'32378',10162,0,1),(2350,10154,'com.liferay.portal.model.Layout',4,'32378',10163,10158,1023),(2351,10154,'com.liferay.portal.model.Layout',4,'32378',10170,0,19),(2355,10154,'com.liferay.portal.model.Layout',4,'32383',10162,0,1),(2353,10154,'com.liferay.portal.model.Layout',4,'32383',10163,10158,1023),(2354,10154,'com.liferay.portal.model.Layout',4,'32383',10170,0,19),(2358,10154,'com.liferay.portal.model.Layout',4,'32388',10162,0,1),(2356,10154,'com.liferay.portal.model.Layout',4,'32388',10163,10158,1023),(2357,10154,'com.liferay.portal.model.Layout',4,'32388',10170,0,19),(2361,10154,'com.liferay.portal.model.Layout',4,'32393',10162,0,1),(2359,10154,'com.liferay.portal.model.Layout',4,'32393',10163,10158,1023),(2360,10154,'com.liferay.portal.model.Layout',4,'32393',10170,0,19),(2364,10154,'com.liferay.portal.model.Layout',4,'32400',10162,0,1),(2362,10154,'com.liferay.portal.model.Layout',4,'32400',10163,10158,1023),(2363,10154,'com.liferay.portal.model.Layout',4,'32400',10170,0,19),(2724,10154,'com.liferay.portal.model.Layout',4,'35201',10162,0,1),(2722,10154,'com.liferay.portal.model.Layout',4,'35201',10163,10196,1023),(2723,10154,'com.liferay.portal.model.Layout',4,'35201',10170,0,19),(316,10154,'com.liferay.portal.model.LayoutPrototype',4,'10309',10163,10158,15),(330,10154,'com.liferay.portal.model.LayoutPrototype',4,'10319',10163,10158,15),(345,10154,'com.liferay.portal.model.LayoutPrototype',4,'10328',10163,10158,15),(358,10154,'com.liferay.portal.model.LayoutSetPrototype',4,'10337',10163,10158,15),(371,10154,'com.liferay.portal.model.LayoutSetPrototype',4,'10363',10163,10158,15),(2307,10154,'com.liferay.portal.model.LayoutSetPrototype',4,'32061',10163,10158,15),(135,10154,'com.liferay.portal.model.User',4,'10196',10163,10196,31),(1316,10154,'com.liferay.portal.model.User',4,'16562',10163,10196,31),(127,10154,'com.liferay.portlet.asset',2,'10189',10164,0,30),(319,10154,'com.liferay.portlet.asset.model.AssetVocabulary',4,'10316',10163,10158,15),(511,10154,'com.liferay.portlet.asset.model.AssetVocabulary',4,'10733',10163,10158,15),(128,10154,'com.liferay.portlet.blogs',2,'10189',10164,0,14),(108,10154,'com.liferay.portlet.blogs',2,'10189',10165,0,14),(323,10154,'com.liferay.portlet.blogs',4,'10310',10163,0,14),(129,10154,'com.liferay.portlet.bookmarks',2,'10189',10164,0,15),(130,10154,'com.liferay.portlet.calendar',2,'10189',10164,0,14),(109,10154,'com.liferay.portlet.calendar',2,'10189',10165,0,14),(131,10154,'com.liferay.portlet.documentlibrary',2,'10189',10164,0,511),(475,10154,'com.liferay.portlet.documentlibrary',4,'10180',10162,0,1),(473,10154,'com.liferay.portlet.documentlibrary',4,'10180',10163,0,511),(474,10154,'com.liferay.portlet.documentlibrary',4,'10180',10170,0,75),(395,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10411',10162,0,3),(394,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10411',10163,10158,127),(397,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10419',10162,0,3),(396,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10419',10163,10158,127),(399,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10428',10162,0,3),(398,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10428',10163,10158,127),(401,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10436',10162,0,3),(400,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10436',10163,10158,127),(403,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10447',10162,0,3),(402,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10447',10163,10158,127),(405,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10455',10162,0,3),(404,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10455',10163,10158,127),(407,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10467',10162,0,3),(406,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10467',10163,10158,127),(409,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10479',10162,0,3),(408,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10479',10163,10158,127),(411,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10489',10162,0,3),(410,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10489',10163,10158,127),(413,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10499',10162,0,3),(412,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10499',10163,10158,127),(415,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10511',10162,0,3),(414,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10511',10163,10158,127),(417,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10523',10162,0,3),(416,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10523',10163,10158,127),(419,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10535',10162,0,3),(418,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'10535',10163,10158,127),(901,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'11101',10163,10196,127),(902,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'11109',10163,10196,127),(2311,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32070',10163,10158,127),(2312,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32078',10163,10158,127),(2313,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32090',10163,10158,127),(2314,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32102',10163,10158,127),(2315,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32114',10163,10158,127),(2316,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32126',10163,10158,127),(2317,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32138',10163,10158,127),(2318,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32150',10163,10158,127),(2319,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32162',10163,10158,127),(2320,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32174',10163,10158,127),(2321,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32186',10163,10158,127),(2322,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32198',10163,10158,127),(2323,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32210',10163,10158,127),(2324,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32222',10163,10158,127),(2325,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32234',10163,10158,127),(2326,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'32246',10163,10158,127),(2803,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'35811',10163,10196,127),(2804,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'35819',10163,10196,127),(2806,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'35833',10163,10196,127),(2807,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'35845',10163,10196,127),(3004,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntry',4,'36383',10163,10196,127),(308,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntryType',4,'10300',10163,10158,15),(310,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntryType',4,'10302',10163,10158,15),(312,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntryType',4,'10304',10163,10158,15),(314,10154,'com.liferay.portlet.documentlibrary.model.DLFileEntryType',4,'10306',10163,10158,15),(478,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'10644',10162,0,1),(476,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'10644',10163,10196,255),(477,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'10644',10170,0,29),(2801,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'35807',10163,10196,255),(2802,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'35809',10163,10196,255),(2805,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'35831',10163,10196,255),(2901,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'36125',10163,10196,255),(2902,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'36127',10163,10196,255),(2903,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'36129',10163,10196,255),(2904,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'36131',10163,10196,255),(3003,10154,'com.liferay.portlet.documentlibrary.model.DLFolder',4,'36381',10163,10196,255),(304,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10297',10163,10158,15),(305,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10298',10163,10158,15),(306,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10299',10163,10158,15),(307,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10301',10163,10158,15),(309,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10303',10163,10158,15),(311,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10305',10163,10158,15),(313,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10307',10163,10158,15),(315,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10308',10163,10158,15),(387,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10399',10163,10158,15),(388,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10400',10163,10158,15),(389,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10401',10163,10158,15),(390,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10402',10163,10158,15),(391,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10403',10163,10158,15),(392,10154,'com.liferay.portlet.dynamicdatamapping.model.DDMStructure',4,'10404',10163,10158,15),(393,10154,'com.liferay.portlet.expando.model.ExpandoColumn',4,'10409',10163,0,15),(421,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'10548',10162,0,3),(420,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'10548',10163,10158,255),(427,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'10574',10162,0,3),(426,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'10574',10163,10158,255),(429,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'10586',10162,0,3),(428,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'10586',10163,10158,255),(2327,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'32263',10163,10158,255),(2328,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'32272',10163,10158,255),(2329,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'32280',10163,10158,255),(2332,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'32293',10163,10158,255),(2335,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'32306',10163,10158,255),(2338,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'32319',10163,10158,255),(2339,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'32327',10163,10158,255),(2340,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'32335',10163,10158,255),(2343,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'32348',10163,10158,255),(2346,10154,'com.liferay.portlet.journal.model.JournalArticle',4,'32361',10163,10158,255),(423,10154,'com.liferay.portlet.journal.model.JournalStructure',4,'10566',10162,0,1),(422,10154,'com.liferay.portlet.journal.model.JournalStructure',4,'10566',10163,10158,15),(2330,10154,'com.liferay.portlet.journal.model.JournalStructure',4,'32288',10163,10158,15),(2333,10154,'com.liferay.portlet.journal.model.JournalStructure',4,'32301',10163,10158,15),(2336,10154,'com.liferay.portlet.journal.model.JournalStructure',4,'32314',10163,10158,15),(2341,10154,'com.liferay.portlet.journal.model.JournalStructure',4,'32343',10163,10158,15),(2344,10154,'com.liferay.portlet.journal.model.JournalStructure',4,'32356',10163,10158,15),(425,10154,'com.liferay.portlet.journal.model.JournalTemplate',4,'10571',10162,0,1),(424,10154,'com.liferay.portlet.journal.model.JournalTemplate',4,'10571',10163,10158,15),(2331,10154,'com.liferay.portlet.journal.model.JournalTemplate',4,'32290',10163,10158,15),(2334,10154,'com.liferay.portlet.journal.model.JournalTemplate',4,'32303',10163,10158,15),(2337,10154,'com.liferay.portlet.journal.model.JournalTemplate',4,'32316',10163,10158,15),(2342,10154,'com.liferay.portlet.journal.model.JournalTemplate',4,'32345',10163,10158,15),(2345,10154,'com.liferay.portlet.journal.model.JournalTemplate',4,'32358',10163,10158,15),(132,10154,'com.liferay.portlet.messageboards',2,'10189',10164,0,2047),(133,10154,'com.liferay.portlet.polls',2,'10189',10164,0,6),(1207,10154,'com.liferay.portlet.softwarecatalog',4,'10180',10163,0,14),(134,10154,'com.liferay.portlet.wiki',2,'10189',10164,0,6),(351,10154,'com.liferay.portlet.wiki',4,'10329',10163,0,6);
/*!40000 ALTER TABLE `resourcepermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourcetypepermission`
--

DROP TABLE IF EXISTS `resourcetypepermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourcetypepermission` (
  `resourceTypePermissionId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `roleId` bigint(20) DEFAULT NULL,
  `actionIds` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`resourceTypePermissionId`),
  UNIQUE KEY `IX_BA497163` (`companyId`,`groupId`,`name`,`roleId`),
  KEY `IX_7D81F66F` (`companyId`,`name`,`roleId`),
  KEY `IX_A82690E2` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourcetypepermission`
--

LOCK TABLES `resourcetypepermission` WRITE;
/*!40000 ALTER TABLE `resourcetypepermission` DISABLE KEYS */;
/*!40000 ALTER TABLE `resourcetypepermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_`
--

DROP TABLE IF EXISTS `role_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_` (
  `roleId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `title` longtext,
  `description` longtext,
  `type_` int(11) DEFAULT NULL,
  `subtype` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `IX_A88E424E` (`companyId`,`classNameId`,`classPK`),
  UNIQUE KEY `IX_EBC931B8` (`companyId`,`name`),
  KEY `IX_449A10B9` (`companyId`),
  KEY `IX_F436EC8E` (`name`),
  KEY `IX_5EB4E2FB` (`subtype`),
  KEY `IX_CBE204` (`type_`,`subtype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_`
--

LOCK TABLES `role_` WRITE;
/*!40000 ALTER TABLE `role_` DISABLE KEYS */;
INSERT INTO `role_` VALUES (10161,10154,10004,10161,'Administrator','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Administrators are super users who can do anything.</Description></root>',1,''),(10162,10154,10004,10162,'Guest','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Unauthenticated users always have this role.</Description></root>',1,''),(10163,10154,10004,10163,'Owner','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">This is an implied role with respect to the objects users create.</Description></root>',1,''),(10164,10154,10004,10164,'Power User','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Power Users have their own personal site.</Description></root>',1,''),(10165,10154,10004,10165,'User','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Authenticated users should be assigned this role.</Description></root>',1,''),(10166,10154,10004,10166,'Organization Administrator','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Organization Administrators are super users of their organization but cannot make other users into Organization Administrators.</Description></root>',3,''),(10167,10154,10004,10167,'Organization Owner','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Organization Owners are super users of their organization and can assign organization roles to users.</Description></root>',3,''),(10168,10154,10004,10168,'Organization User','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">All users who belong to an organization have this role within that organization.</Description></root>',3,''),(10169,10154,10004,10169,'Site Administrator','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Site Administrators are super users of their site but cannot make other users into Site Administrators.</Description></root>',2,''),(10170,10154,10004,10170,'Site Member','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">All users who belong to a site have this role within that site.</Description></root>',2,''),(10171,10154,10004,10171,'Site Owner','','<?xml version=\'1.0\' encoding=\'UTF-8\'?><root available-locales=\"en_US\" default-locale=\"en_US\"><Description language-id=\"en_US\">Site Owners are super users of their site and can assign site roles to users.</Description></root>',2,'');
/*!40000 ALTER TABLE `role_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_permissions`
--

DROP TABLE IF EXISTS `roles_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles_permissions` (
  `roleId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  PRIMARY KEY (`roleId`,`permissionId`),
  KEY `IX_7A3619C6` (`permissionId`),
  KEY `IX_E04E486D` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_permissions`
--

LOCK TABLES `roles_permissions` WRITE;
/*!40000 ALTER TABLE `roles_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scframeworkversi_scproductvers`
--

DROP TABLE IF EXISTS `scframeworkversi_scproductvers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scframeworkversi_scproductvers` (
  `frameworkVersionId` bigint(20) NOT NULL,
  `productVersionId` bigint(20) NOT NULL,
  PRIMARY KEY (`frameworkVersionId`,`productVersionId`),
  KEY `IX_3BB93ECA` (`frameworkVersionId`),
  KEY `IX_E8D33FF9` (`productVersionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scframeworkversi_scproductvers`
--

LOCK TABLES `scframeworkversi_scproductvers` WRITE;
/*!40000 ALTER TABLE `scframeworkversi_scproductvers` DISABLE KEYS */;
/*!40000 ALTER TABLE `scframeworkversi_scproductvers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scframeworkversion`
--

DROP TABLE IF EXISTS `scframeworkversion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scframeworkversion` (
  `frameworkVersionId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `url` longtext,
  `active_` tinyint(4) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`frameworkVersionId`),
  KEY `IX_C98C0D78` (`companyId`),
  KEY `IX_272991FA` (`groupId`),
  KEY `IX_6E1764F` (`groupId`,`active_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scframeworkversion`
--

LOCK TABLES `scframeworkversion` WRITE;
/*!40000 ALTER TABLE `scframeworkversion` DISABLE KEYS */;
/*!40000 ALTER TABLE `scframeworkversion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sclicense`
--

DROP TABLE IF EXISTS `sclicense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sclicense` (
  `licenseId` bigint(20) NOT NULL,
  `name` varchar(75) DEFAULT NULL,
  `url` longtext,
  `openSource` tinyint(4) DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  `recommended` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`licenseId`),
  KEY `IX_1C841592` (`active_`),
  KEY `IX_5327BB79` (`active_`,`recommended`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sclicense`
--

LOCK TABLES `sclicense` WRITE;
/*!40000 ALTER TABLE `sclicense` DISABLE KEYS */;
/*!40000 ALTER TABLE `sclicense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sclicenses_scproductentries`
--

DROP TABLE IF EXISTS `sclicenses_scproductentries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sclicenses_scproductentries` (
  `licenseId` bigint(20) NOT NULL,
  `productEntryId` bigint(20) NOT NULL,
  PRIMARY KEY (`licenseId`,`productEntryId`),
  KEY `IX_27006638` (`licenseId`),
  KEY `IX_D7710A66` (`productEntryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sclicenses_scproductentries`
--

LOCK TABLES `sclicenses_scproductentries` WRITE;
/*!40000 ALTER TABLE `sclicenses_scproductentries` DISABLE KEYS */;
/*!40000 ALTER TABLE `sclicenses_scproductentries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scproductentry`
--

DROP TABLE IF EXISTS `scproductentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scproductentry` (
  `productEntryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `shortDescription` longtext,
  `longDescription` longtext,
  `pageURL` longtext,
  `author` varchar(75) DEFAULT NULL,
  `repoGroupId` varchar(75) DEFAULT NULL,
  `repoArtifactId` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`productEntryId`),
  KEY `IX_5D25244F` (`companyId`),
  KEY `IX_72F87291` (`groupId`),
  KEY `IX_98E6A9CB` (`groupId`,`userId`),
  KEY `IX_7311E812` (`repoGroupId`,`repoArtifactId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scproductentry`
--

LOCK TABLES `scproductentry` WRITE;
/*!40000 ALTER TABLE `scproductentry` DISABLE KEYS */;
/*!40000 ALTER TABLE `scproductentry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scproductscreenshot`
--

DROP TABLE IF EXISTS `scproductscreenshot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scproductscreenshot` (
  `productScreenshotId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `productEntryId` bigint(20) DEFAULT NULL,
  `thumbnailId` bigint(20) DEFAULT NULL,
  `fullImageId` bigint(20) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`productScreenshotId`),
  KEY `IX_AE8224CC` (`fullImageId`),
  KEY `IX_467956FD` (`productEntryId`),
  KEY `IX_DA913A55` (`productEntryId`,`priority`),
  KEY `IX_6C572DAC` (`thumbnailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scproductscreenshot`
--

LOCK TABLES `scproductscreenshot` WRITE;
/*!40000 ALTER TABLE `scproductscreenshot` DISABLE KEYS */;
/*!40000 ALTER TABLE `scproductscreenshot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scproductversion`
--

DROP TABLE IF EXISTS `scproductversion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scproductversion` (
  `productVersionId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `productEntryId` bigint(20) DEFAULT NULL,
  `version` varchar(75) DEFAULT NULL,
  `changeLog` longtext,
  `downloadPageURL` longtext,
  `directDownloadURL` varchar(2000) DEFAULT NULL,
  `repoStoreArtifact` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`productVersionId`),
  KEY `IX_7020130F` (`directDownloadURL`(255)),
  KEY `IX_8377A211` (`productEntryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scproductversion`
--

LOCK TABLES `scproductversion` WRITE;
/*!40000 ALTER TABLE `scproductversion` DISABLE KEYS */;
/*!40000 ALTER TABLE `scproductversion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicecomponent`
--

DROP TABLE IF EXISTS `servicecomponent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servicecomponent` (
  `serviceComponentId` bigint(20) NOT NULL,
  `buildNamespace` varchar(75) DEFAULT NULL,
  `buildNumber` bigint(20) DEFAULT NULL,
  `buildDate` bigint(20) DEFAULT NULL,
  `data_` longtext,
  PRIMARY KEY (`serviceComponentId`),
  UNIQUE KEY `IX_4F0315B8` (`buildNamespace`,`buildNumber`),
  KEY `IX_7338606F` (`buildNamespace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicecomponent`
--

LOCK TABLES `servicecomponent` WRITE;
/*!40000 ALTER TABLE `servicecomponent` DISABLE KEYS */;
INSERT INTO `servicecomponent` VALUES (10407,'Marketplace',1,1312562779947,'<?xml version=\"1.0\"?>\n\n<data>\n	<tables-sql><![CDATA[create table Marketplace_App (\n	uuid_ VARCHAR(75) null,\n	appId LONG not null primary key,\n	companyId LONG,\n	userId LONG,\n	userName VARCHAR(75) null,\n	createDate DATE null,\n	modifiedDate DATE null,\n	remoteAppId LONG,\n	version VARCHAR(75) null\n);\n\ncreate table Marketplace_Module (\n	uuid_ VARCHAR(75) null,\n	moduleId LONG not null primary key,\n	appId LONG,\n	contextName VARCHAR(75) null\n);]]></tables-sql>\n	<sequences-sql><![CDATA[]]></sequences-sql>\n	<indexes-sql><![CDATA[create index IX_865B7BD5 on Marketplace_App (companyId);\ncreate index IX_20F14D93 on Marketplace_App (remoteAppId);\ncreate index IX_3E667FE1 on Marketplace_App (uuid_);\n\ncreate index IX_7DC16D26 on Marketplace_Module (appId);\ncreate index IX_C6938724 on Marketplace_Module (appId, contextName);\ncreate index IX_F2F1E964 on Marketplace_Module (contextName);\ncreate index IX_A7EFD80E on Marketplace_Module (uuid_);]]></indexes-sql>\n</data>');
/*!40000 ALTER TABLE `servicecomponent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shard`
--

DROP TABLE IF EXISTS `shard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shard` (
  `shardId` bigint(20) NOT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`shardId`),
  KEY `IX_DA5F4359` (`classNameId`,`classPK`),
  KEY `IX_941BA8C3` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shard`
--

LOCK TABLES `shard` WRITE;
/*!40000 ALTER TABLE `shard` DISABLE KEYS */;
INSERT INTO `shard` VALUES (10155,10021,10154,'default');
/*!40000 ALTER TABLE `shard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppingcart`
--

DROP TABLE IF EXISTS `shoppingcart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shoppingcart` (
  `cartId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `itemIds` longtext,
  `couponCodes` varchar(75) DEFAULT NULL,
  `altShipping` int(11) DEFAULT NULL,
  `insure` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`cartId`),
  UNIQUE KEY `IX_FC46FE16` (`groupId`,`userId`),
  KEY `IX_C28B41DC` (`groupId`),
  KEY `IX_54101CC8` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingcart`
--

LOCK TABLES `shoppingcart` WRITE;
/*!40000 ALTER TABLE `shoppingcart` DISABLE KEYS */;
/*!40000 ALTER TABLE `shoppingcart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppingcategory`
--

DROP TABLE IF EXISTS `shoppingcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shoppingcategory` (
  `categoryId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `parentCategoryId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`categoryId`),
  KEY `IX_5F615D3E` (`groupId`),
  KEY `IX_1E6464F5` (`groupId`,`parentCategoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingcategory`
--

LOCK TABLES `shoppingcategory` WRITE;
/*!40000 ALTER TABLE `shoppingcategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `shoppingcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppingcoupon`
--

DROP TABLE IF EXISTS `shoppingcoupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shoppingcoupon` (
  `couponId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `code_` varchar(75) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `active_` tinyint(4) DEFAULT NULL,
  `limitCategories` longtext,
  `limitSkus` longtext,
  `minOrder` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `discountType` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`couponId`),
  UNIQUE KEY `IX_DC60CFAE` (`code_`),
  KEY `IX_3251AF16` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingcoupon`
--

LOCK TABLES `shoppingcoupon` WRITE;
/*!40000 ALTER TABLE `shoppingcoupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `shoppingcoupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppingitem`
--

DROP TABLE IF EXISTS `shoppingitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shoppingitem` (
  `itemId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `sku` varchar(75) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `description` longtext,
  `properties` longtext,
  `fields_` tinyint(4) DEFAULT NULL,
  `fieldsQuantities` longtext,
  `minQuantity` int(11) DEFAULT NULL,
  `maxQuantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `taxable` tinyint(4) DEFAULT NULL,
  `shipping` double DEFAULT NULL,
  `useShippingFormula` tinyint(4) DEFAULT NULL,
  `requiresShipping` tinyint(4) DEFAULT NULL,
  `stockQuantity` int(11) DEFAULT NULL,
  `featured_` tinyint(4) DEFAULT NULL,
  `sale_` tinyint(4) DEFAULT NULL,
  `smallImage` tinyint(4) DEFAULT NULL,
  `smallImageId` bigint(20) DEFAULT NULL,
  `smallImageURL` longtext,
  `mediumImage` tinyint(4) DEFAULT NULL,
  `mediumImageId` bigint(20) DEFAULT NULL,
  `mediumImageURL` longtext,
  `largeImage` tinyint(4) DEFAULT NULL,
  `largeImageId` bigint(20) DEFAULT NULL,
  `largeImageURL` longtext,
  PRIMARY KEY (`itemId`),
  UNIQUE KEY `IX_1C717CA6` (`companyId`,`sku`),
  KEY `IX_FEFE7D76` (`groupId`,`categoryId`),
  KEY `IX_903DC750` (`largeImageId`),
  KEY `IX_D217AB30` (`mediumImageId`),
  KEY `IX_FF203304` (`smallImageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingitem`
--

LOCK TABLES `shoppingitem` WRITE;
/*!40000 ALTER TABLE `shoppingitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `shoppingitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppingitemfield`
--

DROP TABLE IF EXISTS `shoppingitemfield`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shoppingitemfield` (
  `itemFieldId` bigint(20) NOT NULL,
  `itemId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `values_` longtext,
  `description` longtext,
  PRIMARY KEY (`itemFieldId`),
  KEY `IX_6D5F9B87` (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingitemfield`
--

LOCK TABLES `shoppingitemfield` WRITE;
/*!40000 ALTER TABLE `shoppingitemfield` DISABLE KEYS */;
/*!40000 ALTER TABLE `shoppingitemfield` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppingitemprice`
--

DROP TABLE IF EXISTS `shoppingitemprice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shoppingitemprice` (
  `itemPriceId` bigint(20) NOT NULL,
  `itemId` bigint(20) DEFAULT NULL,
  `minQuantity` int(11) DEFAULT NULL,
  `maxQuantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `taxable` tinyint(4) DEFAULT NULL,
  `shipping` double DEFAULT NULL,
  `useShippingFormula` tinyint(4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`itemPriceId`),
  KEY `IX_EA6FD516` (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingitemprice`
--

LOCK TABLES `shoppingitemprice` WRITE;
/*!40000 ALTER TABLE `shoppingitemprice` DISABLE KEYS */;
/*!40000 ALTER TABLE `shoppingitemprice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppingorder`
--

DROP TABLE IF EXISTS `shoppingorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shoppingorder` (
  `orderId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `number_` varchar(75) DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `shipping` double DEFAULT NULL,
  `altShipping` varchar(75) DEFAULT NULL,
  `requiresShipping` tinyint(4) DEFAULT NULL,
  `insure` tinyint(4) DEFAULT NULL,
  `insurance` double DEFAULT NULL,
  `couponCodes` varchar(75) DEFAULT NULL,
  `couponDiscount` double DEFAULT NULL,
  `billingFirstName` varchar(75) DEFAULT NULL,
  `billingLastName` varchar(75) DEFAULT NULL,
  `billingEmailAddress` varchar(75) DEFAULT NULL,
  `billingCompany` varchar(75) DEFAULT NULL,
  `billingStreet` varchar(75) DEFAULT NULL,
  `billingCity` varchar(75) DEFAULT NULL,
  `billingState` varchar(75) DEFAULT NULL,
  `billingZip` varchar(75) DEFAULT NULL,
  `billingCountry` varchar(75) DEFAULT NULL,
  `billingPhone` varchar(75) DEFAULT NULL,
  `shipToBilling` tinyint(4) DEFAULT NULL,
  `shippingFirstName` varchar(75) DEFAULT NULL,
  `shippingLastName` varchar(75) DEFAULT NULL,
  `shippingEmailAddress` varchar(75) DEFAULT NULL,
  `shippingCompany` varchar(75) DEFAULT NULL,
  `shippingStreet` varchar(75) DEFAULT NULL,
  `shippingCity` varchar(75) DEFAULT NULL,
  `shippingState` varchar(75) DEFAULT NULL,
  `shippingZip` varchar(75) DEFAULT NULL,
  `shippingCountry` varchar(75) DEFAULT NULL,
  `shippingPhone` varchar(75) DEFAULT NULL,
  `ccName` varchar(75) DEFAULT NULL,
  `ccType` varchar(75) DEFAULT NULL,
  `ccNumber` varchar(75) DEFAULT NULL,
  `ccExpMonth` int(11) DEFAULT NULL,
  `ccExpYear` int(11) DEFAULT NULL,
  `ccVerNumber` varchar(75) DEFAULT NULL,
  `comments` longtext,
  `ppTxnId` varchar(75) DEFAULT NULL,
  `ppPaymentStatus` varchar(75) DEFAULT NULL,
  `ppPaymentGross` double DEFAULT NULL,
  `ppReceiverEmail` varchar(75) DEFAULT NULL,
  `ppPayerEmail` varchar(75) DEFAULT NULL,
  `sendOrderEmail` tinyint(4) DEFAULT NULL,
  `sendShippingEmail` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  UNIQUE KEY `IX_D7D6E87A` (`number_`),
  KEY `IX_1D15553E` (`groupId`),
  KEY `IX_119B5630` (`groupId`,`userId`,`ppPaymentStatus`),
  KEY `IX_F474FD89` (`ppTxnId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingorder`
--

LOCK TABLES `shoppingorder` WRITE;
/*!40000 ALTER TABLE `shoppingorder` DISABLE KEYS */;
/*!40000 ALTER TABLE `shoppingorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoppingorderitem`
--

DROP TABLE IF EXISTS `shoppingorderitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shoppingorderitem` (
  `orderItemId` bigint(20) NOT NULL,
  `orderId` bigint(20) DEFAULT NULL,
  `itemId` varchar(75) DEFAULT NULL,
  `sku` varchar(75) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `description` longtext,
  `properties` longtext,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `shippedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`orderItemId`),
  KEY `IX_B5F82C7A` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingorderitem`
--

LOCK TABLES `shoppingorderitem` WRITE;
/*!40000 ALTER TABLE `shoppingorderitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `shoppingorderitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialactivity`
--

DROP TABLE IF EXISTS `socialactivity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialactivity` (
  `activityId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` bigint(20) DEFAULT NULL,
  `mirrorActivityId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  `extraData` longtext,
  `receiverUserId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`activityId`),
  UNIQUE KEY `IX_8F32DEC9` (`groupId`,`userId`,`createDate`,`classNameId`,`classPK`,`type_`,`receiverUserId`),
  KEY `IX_82E39A0C` (`classNameId`),
  KEY `IX_A853C757` (`classNameId`,`classPK`),
  KEY `IX_64B1BC66` (`companyId`),
  KEY `IX_2A2468` (`groupId`),
  KEY `IX_FB604DC7` (`groupId`,`userId`,`classNameId`,`classPK`,`type_`,`receiverUserId`),
  KEY `IX_1271F25F` (`mirrorActivityId`),
  KEY `IX_1F00C374` (`mirrorActivityId`,`classNameId`,`classPK`),
  KEY `IX_121CA3CB` (`receiverUserId`),
  KEY `IX_3504B8BC` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialactivity`
--

LOCK TABLES `socialactivity` WRITE;
/*!40000 ALTER TABLE `socialactivity` DISABLE KEYS */;
INSERT INTO `socialactivity` VALUES (1,10180,10154,10158,1344912480580,0,10010,10411,1,'{\"title\":\"welcome_bg_3.jpg\"}',0),(2,10180,10154,10158,1344912481331,0,10010,10419,1,'{\"title\":\"welcome_bg_11.jpg\"}',0),(3,10180,10154,10158,1344912481419,0,10010,10428,1,'{\"title\":\"welcome_bg_12.jpg\"}',0),(4,10180,10154,10158,1344912481496,0,10010,10436,1,'{\"title\":\"welcome_bg_10.png\"}',0),(5,10180,10154,10158,1344912481594,0,10010,10447,1,'{\"title\":\"welcome_bg_2.jpg\"}',0),(6,10180,10154,10158,1344912481698,0,10010,10455,1,'{\"title\":\"welcome_bg_9.jpg\"}',0),(7,10180,10154,10158,1344912481770,0,10010,10467,1,'{\"title\":\"welcome_bg_4.jpg\"}',0),(8,10180,10154,10158,1344912481853,0,10010,10479,1,'{\"title\":\"welcome_bg_6.jpg\"}',0),(9,10180,10154,10158,1344912481946,0,10010,10489,1,'{\"title\":\"welcome_bg_7.jpg\"}',0),(10,10180,10154,10158,1344912482018,0,10010,10499,1,'{\"title\":\"welcome_bg_5.jpg\"}',0),(11,10180,10154,10158,1344912482086,0,10010,10511,1,'{\"title\":\"welcome_bg_13.jpg\"}',0),(12,10180,10154,10158,1344912482678,0,10010,10523,1,'{\"title\":\"welcome_bg_1.jpg\"}',0),(13,10180,10154,10158,1344912482761,0,10010,10535,1,'{\"title\":\"welcome_bg_8.jpg\"}',0),(401,10180,10154,10196,1345220287790,0,10010,11101,1,'{\"title\":\"Bill Of Laden\"}',0),(402,10180,10154,10196,1345220288466,0,10010,11109,1,'{\"title\":\"PO\"}',0),(5637,32062,10154,10158,1352924730778,0,10010,32070,1,'{\"title\":\"pen.png\"}',0),(5638,32062,10154,10158,1352924730914,0,10010,32078,1,'{\"title\":\"carousel_item3.jpg\"}',0),(5639,32062,10154,10158,1352924731095,0,10010,32090,1,'{\"title\":\"people.png\"}',0),(5640,32062,10154,10158,1352924731208,0,10010,32102,1,'{\"title\":\"carousel_item1.jpg\"}',0),(5641,32062,10154,10158,1352924731313,0,10010,32114,1,'{\"title\":\"facebook.png\"}',0),(5642,32062,10154,10158,1352924731421,0,10010,32126,1,'{\"title\":\"icon_gears.png\"}',0),(5643,32062,10154,10158,1352924731564,0,10010,32138,1,'{\"title\":\"mouse.png\"}',0),(5644,32062,10154,10158,1352924731682,0,10010,32150,1,'{\"title\":\"icon_phone.png\"}',0),(5645,32062,10154,10158,1352924731786,0,10010,32162,1,'{\"title\":\"carousel_item2.jpg\"}',0),(5646,32062,10154,10158,1352924731886,0,10010,32174,1,'{\"title\":\"twitter.png\"}',0),(5647,32062,10154,10158,1352924732014,0,10010,32186,1,'{\"title\":\"paperpen.png\"}',0),(5648,32062,10154,10158,1352924732115,0,10010,32198,1,'{\"title\":\"network.png\"}',0),(5649,32062,10154,10158,1352924732228,0,10010,32210,1,'{\"title\":\"rss.png\"}',0),(5650,32062,10154,10158,1352924732327,0,10010,32222,1,'{\"title\":\"icon_beaker.png\"}',0),(5651,32062,10154,10158,1352924732463,0,10010,32234,1,'{\"title\":\"linkedin.png\"}',0),(5652,32062,10154,10158,1352924732606,0,10010,32246,1,'{\"title\":\"icon_network.png\"}',0),(6401,10180,10154,10196,1354280043507,0,10010,35811,1,'{\"title\":\"Bill Of Laden\"}',0),(6402,10180,10154,10196,1354280044606,0,10010,35819,1,'{\"title\":\"PO\"}',0),(6403,10180,10154,10196,1354280044923,0,10010,35833,1,'{\"title\":\"Bill Of Laden\"}',0),(6404,10180,10154,10196,1354280045092,0,10010,35845,1,'{\"title\":\"PO\"}',0),(6501,10180,10154,10196,1354294899964,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6502,10180,10154,10196,1354294901484,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6503,10180,10154,10196,1354294902232,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6504,10180,10154,10196,1354294902396,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6505,10180,10154,10196,1354295758374,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6506,10180,10154,10196,1354295758592,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6507,10180,10154,10196,1354295758768,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6508,10180,10154,10196,1354295758917,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6509,10180,10154,10196,1354296038656,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6510,10180,10154,10196,1354296038846,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6511,10180,10154,10196,1354296039016,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6512,10180,10154,10196,1354296039157,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6513,10180,10154,10196,1354297201019,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6514,10180,10154,10196,1354297201239,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6515,10180,10154,10196,1354297201419,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6516,10180,10154,10196,1354297201605,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6517,10180,10154,10196,1354299734612,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6518,10180,10154,10196,1354299734832,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6519,10180,10154,10196,1354299734997,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6520,10180,10154,10196,1354299735139,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6521,10180,10154,10196,1354300088879,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6522,10180,10154,10196,1354300089064,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6523,10180,10154,10196,1354300089230,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6524,10180,10154,10196,1354300089366,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6525,10180,10154,10196,1354300363592,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6526,10180,10154,10196,1354300363781,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6527,10180,10154,10196,1354300363957,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6528,10180,10154,10196,1354300364098,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6529,10180,10154,10196,1354301177364,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6530,10180,10154,10196,1354301177589,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6531,10180,10154,10196,1354301177803,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6532,10180,10154,10196,1354301177962,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6533,10180,10154,10196,1354305545768,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6534,10180,10154,10196,1354305545973,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6535,10180,10154,10196,1354305546141,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6536,10180,10154,10196,1354305546286,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6537,10180,10154,10196,1354310987277,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6538,10180,10154,10196,1354310987482,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6539,10180,10154,10196,1354310987650,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6540,10180,10154,10196,1354310987792,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6601,10180,10154,10196,1354316601431,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6602,10180,10154,10196,1354316603595,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6603,10180,10154,10196,1354316604115,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6604,10180,10154,10196,1354316604403,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6606,10180,10154,10196,1354329996729,0,10010,35811,2,'{\"title\":\"Bill Of Laden\"}',0),(6607,10180,10154,10196,1354329997827,0,10010,35819,2,'{\"title\":\"PO\"}',0),(6608,10180,10154,10196,1354329998076,0,10010,35833,2,'{\"title\":\"Bill Of Laden\"}',0),(6609,10180,10154,10196,1354329998305,0,10010,35845,2,'{\"title\":\"PO\"}',0),(6611,10180,10154,10196,1354339611533,0,10010,36383,1,'{\"title\":\"Label RDFLT000001-01L1_1\"}',0);
/*!40000 ALTER TABLE `socialactivity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialactivityachievement`
--

DROP TABLE IF EXISTS `socialactivityachievement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialactivityachievement` (
  `activityAchievementId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `firstInGroup` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`activityAchievementId`),
  UNIQUE KEY `IX_D4390CAA` (`groupId`,`userId`,`name`),
  KEY `IX_E14B1F1` (`groupId`),
  KEY `IX_83E16F2F` (`groupId`,`firstInGroup`),
  KEY `IX_8F6408F0` (`groupId`,`name`),
  KEY `IX_C8FD892B` (`groupId`,`userId`),
  KEY `IX_AABC18E9` (`groupId`,`userId`,`firstInGroup`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialactivityachievement`
--

LOCK TABLES `socialactivityachievement` WRITE;
/*!40000 ALTER TABLE `socialactivityachievement` DISABLE KEYS */;
/*!40000 ALTER TABLE `socialactivityachievement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialactivitycounter`
--

DROP TABLE IF EXISTS `socialactivitycounter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialactivitycounter` (
  `activityCounterId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `ownerType` int(11) DEFAULT NULL,
  `currentValue` int(11) DEFAULT NULL,
  `totalValue` int(11) DEFAULT NULL,
  `graceValue` int(11) DEFAULT NULL,
  `startPeriod` int(11) DEFAULT NULL,
  `endPeriod` int(11) DEFAULT NULL,
  PRIMARY KEY (`activityCounterId`),
  UNIQUE KEY `IX_1B7E3B67` (`groupId`,`classNameId`,`classPK`,`name`,`ownerType`,`endPeriod`),
  UNIQUE KEY `IX_374B35AE` (`groupId`,`classNameId`,`classPK`,`name`,`ownerType`,`startPeriod`),
  KEY `IX_A4B9A23B` (`classNameId`,`classPK`),
  KEY `IX_926CDD04` (`groupId`,`classNameId`,`classPK`,`ownerType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialactivitycounter`
--

LOCK TABLES `socialactivitycounter` WRITE;
/*!40000 ALTER TABLE `socialactivitycounter` DISABLE KEYS */;
/*!40000 ALTER TABLE `socialactivitycounter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialactivitylimit`
--

DROP TABLE IF EXISTS `socialactivitylimit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialactivitylimit` (
  `activityLimitId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `activityType` int(11) DEFAULT NULL,
  `activityCounterName` varchar(75) DEFAULT NULL,
  `value` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`activityLimitId`),
  UNIQUE KEY `IX_F1C1A617` (`groupId`,`userId`,`classNameId`,`classPK`,`activityType`,`activityCounterName`),
  KEY `IX_B15863FA` (`classNameId`,`classPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialactivitylimit`
--

LOCK TABLES `socialactivitylimit` WRITE;
/*!40000 ALTER TABLE `socialactivitylimit` DISABLE KEYS */;
/*!40000 ALTER TABLE `socialactivitylimit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialactivitysetting`
--

DROP TABLE IF EXISTS `socialactivitysetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialactivitysetting` (
  `activitySettingId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `activityType` int(11) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `value` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`activitySettingId`),
  KEY `IX_384788CD` (`groupId`,`activityType`),
  KEY `IX_1E9CF33B` (`groupId`,`classNameId`,`activityType`),
  KEY `IX_D984AABA` (`groupId`,`classNameId`,`activityType`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialactivitysetting`
--

LOCK TABLES `socialactivitysetting` WRITE;
/*!40000 ALTER TABLE `socialactivitysetting` DISABLE KEYS */;
/*!40000 ALTER TABLE `socialactivitysetting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialrelation`
--

DROP TABLE IF EXISTS `socialrelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialrelation` (
  `uuid_` varchar(75) DEFAULT NULL,
  `relationId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` bigint(20) DEFAULT NULL,
  `userId1` bigint(20) DEFAULT NULL,
  `userId2` bigint(20) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  PRIMARY KEY (`relationId`),
  UNIQUE KEY `IX_12A92145` (`userId1`,`userId2`,`type_`),
  KEY `IX_61171E99` (`companyId`),
  KEY `IX_95135D1C` (`companyId`,`type_`),
  KEY `IX_C31A64C6` (`type_`),
  KEY `IX_5A40CDCC` (`userId1`),
  KEY `IX_4B52BE89` (`userId1`,`type_`),
  KEY `IX_B5C9C690` (`userId1`,`userId2`),
  KEY `IX_5A40D18D` (`userId2`),
  KEY `IX_3F9C2FA8` (`userId2`,`type_`),
  KEY `IX_F0CA24A5` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialrelation`
--

LOCK TABLES `socialrelation` WRITE;
/*!40000 ALTER TABLE `socialrelation` DISABLE KEYS */;
/*!40000 ALTER TABLE `socialrelation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialrequest`
--

DROP TABLE IF EXISTS `socialrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialrequest` (
  `uuid_` varchar(75) DEFAULT NULL,
  `requestId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `createDate` bigint(20) DEFAULT NULL,
  `modifiedDate` bigint(20) DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  `extraData` longtext,
  `receiverUserId` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`requestId`),
  UNIQUE KEY `IX_36A90CA7` (`userId`,`classNameId`,`classPK`,`type_`,`receiverUserId`),
  UNIQUE KEY `IX_4F973EFE` (`uuid_`,`groupId`),
  KEY `IX_D3425487` (`classNameId`,`classPK`,`type_`,`receiverUserId`,`status`),
  KEY `IX_A90FE5A0` (`companyId`),
  KEY `IX_32292ED1` (`receiverUserId`),
  KEY `IX_D9380CB7` (`receiverUserId`,`status`),
  KEY `IX_80F7A9C2` (`userId`),
  KEY `IX_CC86A444` (`userId`,`classNameId`,`classPK`,`type_`,`status`),
  KEY `IX_AB5906A8` (`userId`,`status`),
  KEY `IX_49D5872C` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialrequest`
--

LOCK TABLES `socialrequest` WRITE;
/*!40000 ALTER TABLE `socialrequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `socialrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscription` (
  `subscriptionId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `frequency` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`subscriptionId`),
  UNIQUE KEY `IX_2E1A92D4` (`companyId`,`userId`,`classNameId`,`classPK`),
  KEY `IX_786D171A` (`companyId`,`classNameId`,`classPK`),
  KEY `IX_54243AFD` (`userId`),
  KEY `IX_E8F34171` (`userId`,`classNameId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `teamId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  PRIMARY KEY (`teamId`),
  UNIQUE KEY `IX_143DC786` (`groupId`,`name`),
  KEY `IX_AE6E9907` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `ticketId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `key_` varchar(75) DEFAULT NULL,
  `type_` int(11) DEFAULT NULL,
  `extraInfo` longtext,
  `expirationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`ticketId`),
  KEY `IX_B2468446` (`key_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_`
--

DROP TABLE IF EXISTS `user_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_` (
  `uuid_` varchar(75) DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `defaultUser` tinyint(4) DEFAULT NULL,
  `contactId` bigint(20) DEFAULT NULL,
  `password_` varchar(75) DEFAULT NULL,
  `passwordEncrypted` tinyint(4) DEFAULT NULL,
  `passwordReset` tinyint(4) DEFAULT NULL,
  `passwordModifiedDate` datetime DEFAULT NULL,
  `digest` varchar(255) DEFAULT NULL,
  `reminderQueryQuestion` varchar(75) DEFAULT NULL,
  `reminderQueryAnswer` varchar(75) DEFAULT NULL,
  `graceLoginCount` int(11) DEFAULT NULL,
  `screenName` varchar(75) DEFAULT NULL,
  `emailAddress` varchar(75) DEFAULT NULL,
  `facebookId` bigint(20) DEFAULT NULL,
  `openId` varchar(1024) DEFAULT NULL,
  `portraitId` bigint(20) DEFAULT NULL,
  `languageId` varchar(75) DEFAULT NULL,
  `timeZoneId` varchar(75) DEFAULT NULL,
  `greeting` varchar(255) DEFAULT NULL,
  `comments` longtext,
  `firstName` varchar(75) DEFAULT NULL,
  `middleName` varchar(75) DEFAULT NULL,
  `lastName` varchar(75) DEFAULT NULL,
  `jobTitle` varchar(100) DEFAULT NULL,
  `loginDate` datetime DEFAULT NULL,
  `loginIP` varchar(75) DEFAULT NULL,
  `lastLoginDate` datetime DEFAULT NULL,
  `lastLoginIP` varchar(75) DEFAULT NULL,
  `lastFailedLoginDate` datetime DEFAULT NULL,
  `failedLoginAttempts` int(11) DEFAULT NULL,
  `lockout` tinyint(4) DEFAULT NULL,
  `lockoutDate` datetime DEFAULT NULL,
  `agreedToTermsOfUse` tinyint(4) DEFAULT NULL,
  `emailAddressVerified` tinyint(4) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `IX_615E9F7A` (`companyId`,`emailAddress`),
  UNIQUE KEY `IX_C5806019` (`companyId`,`screenName`),
  UNIQUE KEY `IX_9782AD88` (`companyId`,`userId`),
  UNIQUE KEY `IX_5ADBE171` (`contactId`),
  KEY `IX_3A1E834E` (`companyId`),
  KEY `IX_6EF03E4E` (`companyId`,`defaultUser`),
  KEY `IX_1D731F03` (`companyId`,`facebookId`),
  KEY `IX_89509087` (`companyId`,`openId`(255)),
  KEY `IX_F6039434` (`companyId`,`status`),
  KEY `IX_762F63C6` (`emailAddress`),
  KEY `IX_A18034A4` (`portraitId`),
  KEY `IX_E0422BDA` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_`
--

LOCK TABLES `user_` WRITE;
/*!40000 ALTER TABLE `user_` DISABLE KEYS */;
INSERT INTO `user_` VALUES ('06c238d6-0111-4c66-836c-f9ae68cf655d',10158,10154,'2012-08-14 02:47:54','2012-08-14 02:47:54',1,10159,'password',0,0,NULL,'5533ed38b5e33c076a804bb4bca644f9,528f53719430814f22dbf509e0faa0c4,528f53719430814f22dbf509e0faa0c4','','',0,'10158','default@liferay.com',0,'',0,'en_US','GMT','Welcome!','','','','','','2012-08-14 02:47:54','',NULL,'',NULL,0,0,NULL,1,0,0),('cc2dc218-ec17-4109-8c08-ebedf3cdc0f7',10196,10154,'2012-08-14 02:47:55','2012-08-14 02:47:55',0,10197,'qUqP5cyxm6YcTAhz05Hph5gvu9M=',1,0,NULL,'e5d86c6f3672e52795891c3597f20de0,751da756639bc033b572ba2e7849b589,8f3d267131c99bf7ba6ade3481d748b4','bcv','bcv',0,'test','test@liferay.com',0,'',0,'en_US','GMT','Welcome Test Test!','','Test','','Test','','2012-12-01 05:25:41','127.0.0.1','2012-11-30 23:03:58','127.0.0.1',NULL,0,0,NULL,1,1,0),('286af049-fcd1-432b-89ad-23891a136400',16562,10154,'2012-08-30 19:24:10','2012-08-30 19:24:33',0,16563,'44rSFJQ9qtHWTBAvrsKd5K/p2j0=',1,0,'2012-08-30 19:25:21','bae5621fd905d1d621b0a66fed8108d6,9a0a3923b9783fcd1f51543267c5e9ac,6cdc7da358546fb213c59d545c9bb497','bcv','bcv',0,'conxuser','conxuser@bconv.com',0,'',0,'en_US','Pacific/Midway','Welcome ConX User!','','ConX','','User','','2012-08-30 19:27:21','127.0.0.1','2012-08-30 19:24:59','127.0.0.1','2012-08-30 20:28:34',1,0,NULL,1,0,0);
/*!40000 ALTER TABLE `user_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergroup`
--

DROP TABLE IF EXISTS `usergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroup` (
  `userGroupId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `parentUserGroupId` bigint(20) DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `addedByLDAPImport` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`userGroupId`),
  UNIQUE KEY `IX_23EAD0D` (`companyId`,`name`),
  KEY `IX_524FEFCE` (`companyId`),
  KEY `IX_69771487` (`companyId`,`parentUserGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usergroup`
--

LOCK TABLES `usergroup` WRITE;
/*!40000 ALTER TABLE `usergroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `usergroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergroupgrouprole`
--

DROP TABLE IF EXISTS `usergroupgrouprole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroupgrouprole` (
  `userGroupId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userGroupId`,`groupId`,`roleId`),
  KEY `IX_CCBE4063` (`groupId`),
  KEY `IX_CAB0CCC8` (`groupId`,`roleId`),
  KEY `IX_1CDF88C` (`roleId`),
  KEY `IX_DCDED558` (`userGroupId`),
  KEY `IX_73C52252` (`userGroupId`,`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usergroupgrouprole`
--

LOCK TABLES `usergroupgrouprole` WRITE;
/*!40000 ALTER TABLE `usergroupgrouprole` DISABLE KEYS */;
/*!40000 ALTER TABLE `usergroupgrouprole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergrouprole`
--

DROP TABLE IF EXISTS `usergrouprole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergrouprole` (
  `userId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`groupId`,`roleId`),
  KEY `IX_1B988D7A` (`groupId`),
  KEY `IX_871412DF` (`groupId`,`roleId`),
  KEY `IX_887A2C95` (`roleId`),
  KEY `IX_887BE56A` (`userId`),
  KEY `IX_4D040680` (`userId`,`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usergrouprole`
--

LOCK TABLES `usergrouprole` WRITE;
/*!40000 ALTER TABLE `usergrouprole` DISABLE KEYS */;
/*!40000 ALTER TABLE `usergrouprole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergroups_teams`
--

DROP TABLE IF EXISTS `usergroups_teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroups_teams` (
  `userGroupId` bigint(20) NOT NULL,
  `teamId` bigint(20) NOT NULL,
  PRIMARY KEY (`userGroupId`,`teamId`),
  KEY `IX_31FB0B08` (`teamId`),
  KEY `IX_7F187E63` (`userGroupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usergroups_teams`
--

LOCK TABLES `usergroups_teams` WRITE;
/*!40000 ALTER TABLE `usergroups_teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `usergroups_teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useridmapper`
--

DROP TABLE IF EXISTS `useridmapper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useridmapper` (
  `userIdMapperId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `description` varchar(75) DEFAULT NULL,
  `externalUserId` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`userIdMapperId`),
  UNIQUE KEY `IX_41A32E0D` (`type_`,`externalUserId`),
  UNIQUE KEY `IX_D1C44A6E` (`userId`,`type_`),
  KEY `IX_E60EA987` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useridmapper`
--

LOCK TABLES `useridmapper` WRITE;
/*!40000 ALTER TABLE `useridmapper` DISABLE KEYS */;
/*!40000 ALTER TABLE `useridmapper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usernotificationevent`
--

DROP TABLE IF EXISTS `usernotificationevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usernotificationevent` (
  `uuid_` varchar(75) DEFAULT NULL,
  `userNotificationEventId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `type_` varchar(75) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `deliverBy` bigint(20) DEFAULT NULL,
  `payload` longtext,
  `archived` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`userNotificationEventId`),
  KEY `IX_3E5D78C4` (`userId`),
  KEY `IX_3DBB361A` (`userId`,`archived`),
  KEY `IX_ECD8CFEA` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usernotificationevent`
--

LOCK TABLES `usernotificationevent` WRITE;
/*!40000 ALTER TABLE `usernotificationevent` DISABLE KEYS */;
/*!40000 ALTER TABLE `usernotificationevent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_groups`
--

DROP TABLE IF EXISTS `users_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_groups` (
  `userId` bigint(20) NOT NULL,
  `groupId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`groupId`),
  KEY `IX_C4F9E699` (`groupId`),
  KEY `IX_F10B6C6B` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_groups`
--

LOCK TABLES `users_groups` WRITE;
/*!40000 ALTER TABLE `users_groups` DISABLE KEYS */;
INSERT INTO `users_groups` VALUES (10196,10180);
/*!40000 ALTER TABLE `users_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_orgs`
--

DROP TABLE IF EXISTS `users_orgs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_orgs` (
  `userId` bigint(20) NOT NULL,
  `organizationId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`organizationId`),
  KEY `IX_7EF4EC0E` (`organizationId`),
  KEY `IX_FB646CA6` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_orgs`
--

LOCK TABLES `users_orgs` WRITE;
/*!40000 ALTER TABLE `users_orgs` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_orgs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_permissions`
--

DROP TABLE IF EXISTS `users_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_permissions` (
  `userId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`permissionId`),
  KEY `IX_8AE58A91` (`permissionId`),
  KEY `IX_C26AA64D` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_permissions`
--

LOCK TABLES `users_permissions` WRITE;
/*!40000 ALTER TABLE `users_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `IX_C19E5F31` (`roleId`),
  KEY `IX_C1A01806` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (10196,10161),(10158,10162),(10196,10164),(16562,10164),(10196,10165),(16562,10165);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_teams`
--

DROP TABLE IF EXISTS `users_teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_teams` (
  `userId` bigint(20) NOT NULL,
  `teamId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`teamId`),
  KEY `IX_4D06AD51` (`teamId`),
  KEY `IX_A098EFBF` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_teams`
--

LOCK TABLES `users_teams` WRITE;
/*!40000 ALTER TABLE `users_teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_usergroups`
--

DROP TABLE IF EXISTS `users_usergroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_usergroups` (
  `userGroupId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`userGroupId`,`userId`),
  KEY `IX_66FF2503` (`userGroupId`),
  KEY `IX_BE8102D6` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_usergroups`
--

LOCK TABLES `users_usergroups` WRITE;
/*!40000 ALTER TABLE `users_usergroups` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_usergroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertracker`
--

DROP TABLE IF EXISTS `usertracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertracker` (
  `userTrackerId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `sessionId` varchar(200) DEFAULT NULL,
  `remoteAddr` varchar(75) DEFAULT NULL,
  `remoteHost` varchar(75) DEFAULT NULL,
  `userAgent` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`userTrackerId`),
  KEY `IX_29BA1CF5` (`companyId`),
  KEY `IX_46B0AE8E` (`sessionId`),
  KEY `IX_E4EFBA8D` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertracker`
--

LOCK TABLES `usertracker` WRITE;
/*!40000 ALTER TABLE `usertracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `usertracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertrackerpath`
--

DROP TABLE IF EXISTS `usertrackerpath`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertrackerpath` (
  `userTrackerPathId` bigint(20) NOT NULL,
  `userTrackerId` bigint(20) DEFAULT NULL,
  `path_` longtext,
  `pathDate` datetime DEFAULT NULL,
  PRIMARY KEY (`userTrackerPathId`),
  KEY `IX_14D8BCC0` (`userTrackerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertrackerpath`
--

LOCK TABLES `usertrackerpath` WRITE;
/*!40000 ALTER TABLE `usertrackerpath` DISABLE KEYS */;
/*!40000 ALTER TABLE `usertrackerpath` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualhost`
--

DROP TABLE IF EXISTS `virtualhost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `virtualhost` (
  `virtualHostId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `layoutSetId` bigint(20) DEFAULT NULL,
  `hostname` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`virtualHostId`),
  UNIQUE KEY `IX_A083D394` (`companyId`,`layoutSetId`),
  UNIQUE KEY `IX_431A3960` (`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualhost`
--

LOCK TABLES `virtualhost` WRITE;
/*!40000 ALTER TABLE `virtualhost` DISABLE KEYS */;
INSERT INTO `virtualhost` VALUES (10157,10154,0,'localhost');
/*!40000 ALTER TABLE `virtualhost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webdavprops`
--

DROP TABLE IF EXISTS `webdavprops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webdavprops` (
  `webDavPropsId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `props` longtext,
  PRIMARY KEY (`webDavPropsId`),
  UNIQUE KEY `IX_97DFA146` (`classNameId`,`classPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webdavprops`
--

LOCK TABLES `webdavprops` WRITE;
/*!40000 ALTER TABLE `webdavprops` DISABLE KEYS */;
/*!40000 ALTER TABLE `webdavprops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `website`
--

DROP TABLE IF EXISTS `website`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `website` (
  `websiteId` bigint(20) NOT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `url` longtext,
  `typeId` int(11) DEFAULT NULL,
  `primary_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`websiteId`),
  KEY `IX_96F07007` (`companyId`),
  KEY `IX_4F0F0CA7` (`companyId`,`classNameId`),
  KEY `IX_F960131C` (`companyId`,`classNameId`,`classPK`),
  KEY `IX_1AA07A6D` (`companyId`,`classNameId`,`classPK`,`primary_`),
  KEY `IX_F75690BB` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `website`
--

LOCK TABLES `website` WRITE;
/*!40000 ALTER TABLE `website` DISABLE KEYS */;
/*!40000 ALTER TABLE `website` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wikinode`
--

DROP TABLE IF EXISTS `wikinode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wikinode` (
  `uuid_` varchar(75) DEFAULT NULL,
  `nodeId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` longtext,
  `lastPostDate` datetime DEFAULT NULL,
  PRIMARY KEY (`nodeId`),
  UNIQUE KEY `IX_920CD8B1` (`groupId`,`name`),
  UNIQUE KEY `IX_7609B2AE` (`uuid_`,`groupId`),
  KEY `IX_5D6FE3F0` (`companyId`),
  KEY `IX_B480A672` (`groupId`),
  KEY `IX_6C112D7C` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wikinode`
--

LOCK TABLES `wikinode` WRITE;
/*!40000 ALTER TABLE `wikinode` DISABLE KEYS */;
/*!40000 ALTER TABLE `wikinode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wikipage`
--

DROP TABLE IF EXISTS `wikipage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wikipage` (
  `uuid_` varchar(75) DEFAULT NULL,
  `pageId` bigint(20) NOT NULL,
  `resourcePrimKey` bigint(20) DEFAULT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `nodeId` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `version` double DEFAULT NULL,
  `minorEdit` tinyint(4) DEFAULT NULL,
  `content` longtext,
  `summary` longtext,
  `format` varchar(75) DEFAULT NULL,
  `head` tinyint(4) DEFAULT NULL,
  `parentTitle` varchar(255) DEFAULT NULL,
  `redirectTitle` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `statusByUserId` bigint(20) DEFAULT NULL,
  `statusByUserName` varchar(75) DEFAULT NULL,
  `statusDate` datetime DEFAULT NULL,
  PRIMARY KEY (`pageId`),
  UNIQUE KEY `IX_3D4AF476` (`nodeId`,`title`,`version`),
  UNIQUE KEY `IX_2CD67C81` (`resourcePrimKey`,`nodeId`,`version`),
  UNIQUE KEY `IX_899D3DFB` (`uuid_`,`groupId`),
  KEY `IX_A2001730` (`format`),
  KEY `IX_C8A9C476` (`nodeId`),
  KEY `IX_E7F635CA` (`nodeId`,`head`),
  KEY `IX_65E84AF4` (`nodeId`,`head`,`parentTitle`),
  KEY `IX_9F7655DA` (`nodeId`,`head`,`parentTitle`,`status`),
  KEY `IX_432F0AB0` (`nodeId`,`head`,`status`),
  KEY `IX_46EEF3C8` (`nodeId`,`parentTitle`),
  KEY `IX_1ECC7656` (`nodeId`,`redirectTitle`),
  KEY `IX_546F2D5C` (`nodeId`,`status`),
  KEY `IX_997EEDD2` (`nodeId`,`title`),
  KEY `IX_E745EA26` (`nodeId`,`title`,`head`),
  KEY `IX_BEA33AB8` (`nodeId`,`title`,`status`),
  KEY `IX_B771D67` (`resourcePrimKey`,`nodeId`),
  KEY `IX_94D1054D` (`resourcePrimKey`,`nodeId`,`status`),
  KEY `IX_FBBE7C96` (`userId`,`nodeId`,`status`),
  KEY `IX_9C0E478F` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wikipage`
--

LOCK TABLES `wikipage` WRITE;
/*!40000 ALTER TABLE `wikipage` DISABLE KEYS */;
/*!40000 ALTER TABLE `wikipage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wikipageresource`
--

DROP TABLE IF EXISTS `wikipageresource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wikipageresource` (
  `uuid_` varchar(75) DEFAULT NULL,
  `resourcePrimKey` bigint(20) NOT NULL,
  `nodeId` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resourcePrimKey`),
  UNIQUE KEY `IX_21277664` (`nodeId`,`title`),
  KEY `IX_BE898221` (`uuid_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wikipageresource`
--

LOCK TABLES `wikipageresource` WRITE;
/*!40000 ALTER TABLE `wikipageresource` DISABLE KEYS */;
/*!40000 ALTER TABLE `wikipageresource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflowdefinitionlink`
--

DROP TABLE IF EXISTS `workflowdefinitionlink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflowdefinitionlink` (
  `workflowDefinitionLinkId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `typePK` bigint(20) DEFAULT NULL,
  `workflowDefinitionName` varchar(75) DEFAULT NULL,
  `workflowDefinitionVersion` int(11) DEFAULT NULL,
  PRIMARY KEY (`workflowDefinitionLinkId`),
  KEY `IX_A8B0D276` (`companyId`),
  KEY `IX_A4DB1F0F` (`companyId`,`workflowDefinitionName`,`workflowDefinitionVersion`),
  KEY `IX_B6EE8C9E` (`groupId`,`companyId`,`classNameId`),
  KEY `IX_1E5B9905` (`groupId`,`companyId`,`classNameId`,`classPK`),
  KEY `IX_705B40EE` (`groupId`,`companyId`,`classNameId`,`classPK`,`typePK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowdefinitionlink`
--

LOCK TABLES `workflowdefinitionlink` WRITE;
/*!40000 ALTER TABLE `workflowdefinitionlink` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflowdefinitionlink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflowinstancelink`
--

DROP TABLE IF EXISTS `workflowinstancelink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflowinstancelink` (
  `workflowInstanceLinkId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `userName` varchar(75) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  `classNameId` bigint(20) DEFAULT NULL,
  `classPK` bigint(20) DEFAULT NULL,
  `workflowInstanceId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`workflowInstanceLinkId`),
  KEY `IX_415A7007` (`groupId`,`companyId`,`classNameId`,`classPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflowinstancelink`
--

LOCK TABLES `workflowinstancelink` WRITE;
/*!40000 ALTER TABLE `workflowinstancelink` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflowinstancelink` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-12-01 13:25:54
