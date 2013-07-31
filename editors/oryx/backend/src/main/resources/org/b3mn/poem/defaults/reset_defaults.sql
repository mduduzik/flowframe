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
-- Dumping data for table `comment_`
--
-- ORDER BY:  `id`

LOCK TABLES `comment_` WRITE;
/*!40000 ALTER TABLE `comment_` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content_`
--
-- ORDER BY:  `id`

LOCK TABLES `content_` WRITE;
/*!40000 ALTER TABLE `content_` DISABLE KEYS */;
INSERT INTO `content_` (`id`, `erdf`, `svg`, `png_large`, `png_small`) VALUES (1,'{\"resourceId\":\"oryx-canvas123\",\"properties\":{},\"stencil\":{\"id\":\"ReportingDiagram\"},\"childShapes\":[{\"resourceId\":\"oryx_B65F3D08-DBDE-423F-93B3-A988B50D284C\",\"properties\":{},\"stencil\":{\"id\":\"node-start-event\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"oryx_EFC17C6E-51A4-4062-BDCE-1AC51EF30C73\"}],\"bounds\":{\"lowerRight\":{\"x\":163,\"y\":140},\"upperLeft\":{\"x\":133,\"y\":110}},\"dockers\":[]},{\"resourceId\":\"oryx_F1CE67FD-81FD-486F-BFD2-6F690116DF4C\",\"properties\":{\"datasource\":7},\"stencil\":{\"id\":\"node-datasource\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"oryx_F522B710-3109-43EC-8203-41F5B29ED7B4\"}],\"bounds\":{\"lowerRight\":{\"x\":316.0005,\"y\":155.0865},\"upperLeft\":{\"x\":253.9995,\"y\":94.9135}},\"dockers\":[]},{\"resourceId\":\"oryx_EFC17C6E-51A4-4062-BDCE-1AC51EF30C73\",\"properties\":{},\"stencil\":{\"id\":\"connector\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"oryx_F1CE67FD-81FD-486F-BFD2-6F690116DF4C\"}],\"bounds\":{\"lowerRight\":{\"x\":253.69921945348872,\"y\":124.9810950893847},\"upperLeft\":{\"x\":163.93788992151127,\"y\":124.8746236606153}},\"dockers\":[{\"x\":15,\"y\":15},{\"x\":30.575499999999984,\"y\":29.924500000000002}],\"target\":{\"resourceId\":\"oryx_F1CE67FD-81FD-486F-BFD2-6F690116DF4C\"}},{\"resourceId\":\"oryx_02F010E9-90ED-4638-BD8F-2E3C7C7632B8\",\"properties\":{\"template\":1},\"stencil\":{\"id\":\"node-report\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"oryx_2317C01C-5454-4289-9851-C0735F9001A1\"}],\"bounds\":{\"lowerRight\":{\"x\":449.0005,\"y\":173.5},\"upperLeft\":{\"x\":361.0005,\"y\":76.5}},\"dockers\":[]},{\"resourceId\":\"oryx_F522B710-3109-43EC-8203-41F5B29ED7B4\",\"properties\":{},\"stencil\":{\"id\":\"connector\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"oryx_02F010E9-90ED-4638-BD8F-2E3C7C7632B8\"}],\"bounds\":{\"lowerRight\":{\"x\":360.72258684232065,\"y\":124.9404360211787},\"upperLeft\":{\"x\":316.6221983139293,\"y\":124.8811108538213}},\"dockers\":[{\"x\":30.575499999999984,\"y\":29.924500000000002},{\"x\":44,\"y\":48.5}],\"target\":{\"resourceId\":\"oryx_02F010E9-90ED-4638-BD8F-2E3C7C7632B8\"}},{\"resourceId\":\"oryx_289B6892-BB0E-4E92-8A86-E9E1442D2B11\",\"properties\":{},\"stencil\":{\"id\":\"node-end-event\"},\"childShapes\":[],\"outgoing\":[],\"bounds\":{\"lowerRight\":{\"x\":586,\"y\":139},\"upperLeft\":{\"x\":558,\"y\":111}},\"dockers\":[]},{\"resourceId\":\"oryx_2317C01C-5454-4289-9851-C0735F9001A1\",\"properties\":{},\"stencil\":{\"id\":\"connector\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"oryx_289B6892-BB0E-4E92-8A86-E9E1442D2B11\"}],\"bounds\":{\"lowerRight\":{\"x\":557.9531640625,\"y\":125},\"upperLeft\":{\"x\":449.05505859374995,\"y\":125}},\"dockers\":[{\"x\":44,\"y\":48.5},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"oryx_289B6892-BB0E-4E92-8A86-E9E1442D2B11\"}}],\"bounds\":{\"lowerRight\":{\"x\":1485,\"y\":1050},\"upperLeft\":{\"x\":0,\"y\":0}},\"stencilset\":{\"url\":\"/oryx///stencilsets/reporting/reporting.json\",\"namespace\":\"http://b3mn.org/stencilset/reporting#\"},\"ssextensions\":[]}','<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:oryx=\"http://oryx-editor.org\" id=\"oryx_6C730FAA-1497-479B-847C-43683AF121C7\" width=\"503\" height=\"147\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:svg=\"http://www.w3.org/2000/svg\"><defs><marker id=\"oryx_11BBC287-E867-49B3-AEF1-43267E9A4BC0arrowEnd\" refX=\"10\" refY=\"5\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\">                          <path d=\"M 0 0 L 10 5 L 0 10 z\" fill=\"black\" stroke=\"black\" id=\"oryx_11BBC287-E867-49B3-AEF1-43267E9A4BC0_oryx_11BBC287-E867-49B3-AEF1-43267E9A4BC0_2\"/>                  </marker><marker id=\"oryx_639FC8A1-7E88-4E7E-988E-846F6E1FEB35arrowEnd\" refX=\"10\" refY=\"5\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\">                          <path d=\"M 0 0 L 10 5 L 0 10 z\" fill=\"black\" stroke=\"black\" id=\"oryx_639FC8A1-7E88-4E7E-988E-846F6E1FEB35_oryx_639FC8A1-7E88-4E7E-988E-846F6E1FEB35_2\"/>                  </marker><marker id=\"oryx_FDB6E5D1-3626-4ED1-9060-234B50B042F4arrowEnd\" refX=\"10\" refY=\"5\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\">                          <path d=\"M 0 0 L 10 5 L 0 10 z\" fill=\"black\" stroke=\"black\" id=\"oryx_FDB6E5D1-3626-4ED1-9060-234B50B042F4_oryx_FDB6E5D1-3626-4ED1-9060-234B50B042F4_2\"/>                  </marker></defs><g stroke=\"black\" font-family=\"Verdana, sans-serif\" font-size-adjust=\"none\" font-style=\"normal\" font-variant=\"normal\" font-weight=\"normal\" line-heigth=\"normal\" font-size=\"12\"><g class=\"stencils\" transform=\"translate(-108, -51.5)\"><g class=\"me\"/><g class=\"children\"><g id=\"oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8\"><g class=\"stencils\" transform=\"translate(133, 110)\"><g class=\"me\"><g pointer-events=\"fill\" id=\"oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8\" title=\"Start Event\">        <defs id=\"oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8_oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8_5\">   <radialGradient id=\"oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8background\" cx=\"10%\" cy=\"10%\" r=\"100%\" fx=\"10%\" fy=\"10%\">    <stop offset=\"0%\" stop-color=\"#ffffff\" stop-opacity=\"1\" id=\"oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8_oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8_6\"/>    <stop id=\"oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8fill_el\" offset=\"100%\" stop-color=\"#ffffff\" stop-opacity=\"1\"/>   </radialGradient>  </defs>       <circle id=\"oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8bg_frame\" cx=\"15\" cy=\"15\" r=\"15\" stroke=\"black\" fill=\"url(#oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8background) white\" stroke-width=\"1\"/>  <text font-size=\"11\" id=\"oryx_A8D34F9A-D423-4725-A447-03CAD202EAE8text_name\" x=\"15\" y=\"32\" oryx:align=\"top center\" stroke=\"black\" stroke-width=\"0pt\" letter-spacing=\"-0.01px\" text-anchor=\"middle\" transform=\"rotate(0 15 32)\" visibility=\"inherit\" oryx:fontSize=\"11\"/>   </g></g><g class=\"children\" style=\"overflow:hidden\"/><g class=\"edge\"/></g><g class=\"controls\"><g class=\"dockers\"/><g class=\"magnets\" transform=\"translate(133, 110)\"><g pointer-events=\"all\" display=\"none\" transform=\"translate(7, 7)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g></g></g></g><g id=\"oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCA\"><g class=\"stencils\" transform=\"translate(253.9995, 94.9135)\"><g class=\"me\"><g id=\"oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCA\" title=\"Data Source\">   <defs id=\"oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCA_oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCA_9\">    <radialGradient id=\"oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCAbackground\" cx=\"30%\" cy=\"30%\" r=\"50%\" fx=\"0%\" fy=\"0%\">     <stop offset=\"0%\" stop-color=\"#ffffff\" stop-opacity=\"1\" id=\"oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCA_oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCA_10\"/>     <stop offset=\"100%\" stop-color=\"#ffffff\" stop-opacity=\"1\" id=\"oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCAfill_el\"/>    </radialGradient>   </defs>      <path fill=\"url(#oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCAbackground) #ffffff\" stroke=\"#000000\" d=\" M30.708999999999985 0  c20.013 0 31.292 3.05 31.292 5.729  c0 2.678 0 45.096 0 48.244  c0 3.148 -16.42 6.2 -31.388 6.2  c-14.968 0 -30.613 -2.955 -30.613 -6.298  c0 -3.342 0 -45.728 0 -48.05  C-1.4210854715202004e-14 3.503 10.696999999999985 0 30.708999999999985 0  z\" id=\"oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCA_oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCA_11\"/>   <path id=\"oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCAbg_frame2\" fill=\"none\" stroke=\"#000000\" d=\" M62.00099999999999 15.027999999999999  c0 1.986 -3.62 6.551 -31.267 6.551  c-27.646 0 -30.734 -4.686 -30.734 -6.454  M-1.4210854715202004e-14 10.475000000000001  c0 1.769 3.088 6.455 30.734 6.455  c27.647 0 31.267 -4.565 31.267 -6.551  M-1.4210854715202004e-14 5.825000000000001  c0 2.35 3.088 6.455 30.734 6.455  c27.647 0 31.267 -3.912 31.267 -6.552  M62.00099999999999 5.729000000000001  v4.844  M0.0239999999999857 5.729000000000001  v4.844  M62.00099999999999 10.379000000000001  v4.844  M0.0239999999999857 10.379000000000001  v4.844 \"/>         <text font-size=\"12\" id=\"oryx_C4B2F4C8-65F5-4CF0-82AD-DA4A34800BCAtext_name\" x=\"30.074999999999985\" y=\"65.338\" oryx:align=\"center top\" stroke=\"black\" stroke-width=\"0pt\" letter-spacing=\"-0.01px\" text-anchor=\"middle\" transform=\"rotate(0 30.074999999999985 65.338)\" visibility=\"inherit\" oryx:fontSize=\"12\"/>       </g></g><g class=\"children\" style=\"overflow:hidden\"/><g class=\"edge\"/></g><g class=\"controls\"><g class=\"dockers\"/><g class=\"magnets\" transform=\"translate(253.9995, 94.9135)\"><g pointer-events=\"all\" display=\"none\" transform=\"translate(-8.925, 21.924500000000002)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g><g pointer-events=\"all\" display=\"none\" transform=\"translate(22.575499999999984, 52.511)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g><g pointer-events=\"all\" display=\"none\" transform=\"translate(54.07599999999997, 21.924500000000002)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g><g pointer-events=\"all\" display=\"none\" transform=\"translate(22.575499999999984, -8.662)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g><g pointer-events=\"all\" display=\"none\" transform=\"translate(22.575499999999984, 21.924500000000002)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g></g></g></g><g id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8B\"><g class=\"stencils\" transform=\"translate(361.0005, 76.5)\"><g class=\"me\"><g pointer-events=\"fill\" id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8B\" title=\"Report Template\">      <defs id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8B_oryx_16563323-4FEF-4033-A695-EF6AE5B52F8B_9\">    <radialGradient id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Bbackground\" cx=\"10%\" cy=\"10%\" r=\"100%\" fx=\"10%\" fy=\"10%\">     <stop offset=\"0%\" stop-color=\"#ffffff\" stop-opacity=\"1\" id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8B_oryx_16563323-4FEF-4033-A695-EF6AE5B52F8B_10\"/>     <stop id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Bfill_el\" offset=\"100%\" stop-color=\"#ffffff\" stop-opacity=\"1\"/>    </radialGradient>   </defs>      <polyline oryx:resize=\"vertical horizontal\" oryx:anchors=\"left right\" id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Bframe\" stroke=\"black\" fill=\"#ffffff\" stroke-width=\"1\" points=\"78,97 0,97 0,0 78,0\"/>      <polyline oryx:anchors=\"right top bottom\" id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Bcelem2\" stroke=\"black\" fill=\"#ffffff\" stroke-width=\"1\" points=\"78,97 88,97 88,10 78,10\"/>      <polyline oryx:anchors=\"right top\" id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Bcelem3\" stroke=\"black\" fill=\"url(#oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Bbackground) white\" stroke-width=\"1\" points=\"88,10 78,0 78,10 88,10\"/>      <rect id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8BunvisibleBorder\" x=\"0\" y=\"0\" height=\"97\" width=\"88\" stroke=\"none\" fill=\"none\" oryx:resize=\"horizontal vertical\"/>      <text font-size=\"12\" id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Btext_name\" x=\"44\" y=\"48.5\" oryx:align=\"middle center\" oryx:fittoelem=\"unvisibleBorder\" stroke=\"black\" stroke-width=\"0pt\" letter-spacing=\"-0.01px\" text-anchor=\"middle\" transform=\"rotate(0 44 48.5)\" visibility=\"inherit\" oryx:fontSize=\"12\"/>      <text font-size=\"11\" id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Btext_state\" x=\"44\" y=\"80\" oryx:align=\"middle center\" stroke=\"black\" oryx:anchors=\"bottom\" stroke-width=\"0pt\" letter-spacing=\"-0.01px\" text-anchor=\"middle\" transform=\"rotate(0 44 80)\" visibility=\"inherit\" oryx:fontSize=\"11\"/>      <polygon oryx:anchors=\"left top\" id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Binput\" stroke=\"black\" fill=\"white\" stroke-width=\"1.4\" points=\"9,13 19,13 19,10 25,16 19,22 19,19 9,19\" stroke-linecap=\"butt\" stroke-linejoin=\"miter\" stroke-miterlimit=\"10\"/>      <polygon oryx:anchors=\"left top\" id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Boutput\" stroke=\"black\" fill=\"black\" stroke-width=\"1\" points=\"9,13 19,13 19,10 25,16 19,22 19,19 9,19\" stroke-linecap=\"butt\" stroke-linejoin=\"miter\" stroke-miterlimit=\"10\"/>      <g id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8Bcollection\" transform=\"translate(0,-2)\">    <path oryx:anchors=\"bottom\" fill=\"none\" stroke=\"#000000\" stroke-width=\"2\" d=\"M41.833,86.5v10 M45.833,86.5v10 M49.833,86.5v10\" id=\"oryx_16563323-4FEF-4033-A695-EF6AE5B52F8B_oryx_16563323-4FEF-4033-A695-EF6AE5B52F8B_11\"/>   </g>  </g></g><g class=\"children\" style=\"overflow:hidden\"/><g class=\"edge\"/></g><g class=\"controls\"><g class=\"dockers\"/><g class=\"magnets\" transform=\"translate(361.0005, 76.5)\"><g pointer-events=\"all\" display=\"none\" transform=\"translate(-8, 40.5)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g><g pointer-events=\"all\" display=\"none\" transform=\"translate(36, 89)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g><g pointer-events=\"all\" display=\"none\" transform=\"translate(80, 40.5)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g><g pointer-events=\"all\" display=\"none\" transform=\"translate(36, -8)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g><g pointer-events=\"all\" display=\"none\" transform=\"translate(36, 40.5)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g></g></g></g><g id=\"oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BF\"><g class=\"stencils\" transform=\"translate(558, 111)\"><g class=\"me\"><g pointer-events=\"fill\" id=\"oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BF\" title=\"End Event\">       <defs id=\"oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BF_oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BF_5\">   <radialGradient id=\"oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BFbackground\" cx=\"10%\" cy=\"10%\" r=\"100%\" fx=\"10%\" fy=\"10%\">    <stop offset=\"0%\" stop-color=\"#ffffff\" stop-opacity=\"1\" id=\"oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BF_oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BF_6\"/>    <stop id=\"oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BFfill_el\" offset=\"100%\" stop-color=\"#ffffff\" stop-opacity=\"1\"/>   </radialGradient>  </defs>       <circle id=\"oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BFbg_frame\" cx=\"14\" cy=\"14\" r=\"14\" stroke=\"black\" fill=\"url(#oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BFbackground) white\" stroke-width=\"3\"/>          <circle cx=\"14\" cy=\"14\" r=\"9\" stroke=\"black\" fill=\"black\" stroke-width=\"1\" id=\"oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BF_oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BF_7\"/>  <text font-size=\"11\" id=\"oryx_2A9088A2-09FD-40E4-A9D6-474CA1AD48BFtext_name\" x=\"14\" y=\"30\" oryx:align=\"top center\" stroke=\"black\" stroke-width=\"0pt\" letter-spacing=\"-0.01px\" text-anchor=\"middle\" transform=\"rotate(0 14 30)\" visibility=\"inherit\" oryx:fontSize=\"11\"/>   </g></g><g class=\"children\" style=\"overflow:hidden\"/><g class=\"edge\"/></g><g class=\"controls\"><g class=\"dockers\"/><g class=\"magnets\" transform=\"translate(558, 111)\"><g pointer-events=\"all\" display=\"none\" transform=\"translate(6, 6)\"><circle cx=\"8\" cy=\"8\" r=\"4\" stroke=\"none\" fill=\"red\" fill-opacity=\"0.3\"/></g></g></g></g></g><g class=\"edge\"><g id=\"oryx_11BBC287-E867-49B3-AEF1-43267E9A4BC0\"><g class=\"stencils\"><g class=\"me\" title=\"Connector\"><g pointer-events=\"painted\"><path d=\"M163.93788992151127 124.9810950893847L253.69921945348872 124.8746236606153 \" stroke=\"black\" fill=\"none\" stroke-width=\"2\" marker-end=\"url(#oryx_11BBC287-E867-49B3-AEF1-43267E9A4BC0arrowEnd)\" id=\"oryx_11BBC287-E867-49B3-AEF1-43267E9A4BC0_1\"/></g></g><g class=\"children\" style=\"overflow:hidden\"/><g class=\"edge\"/></g><g class=\"controls\"><g class=\"dockers\"/><g class=\"magnets\"/></g></g><g id=\"oryx_639FC8A1-7E88-4E7E-988E-846F6E1FEB35\"><g class=\"stencils\"><g class=\"me\" title=\"Connector\"><g pointer-events=\"painted\"><path d=\"M316.6221983139293 124.8811108538213L360.72258684232065 124.9404360211787 \" stroke=\"black\" fill=\"none\" stroke-width=\"2\" marker-end=\"url(#oryx_639FC8A1-7E88-4E7E-988E-846F6E1FEB35arrowEnd)\" id=\"oryx_639FC8A1-7E88-4E7E-988E-846F6E1FEB35_1\"/></g></g><g class=\"children\" style=\"overflow:hidden\"/><g class=\"edge\"/></g><g class=\"controls\"><g class=\"dockers\"/><g class=\"magnets\"/></g></g><g id=\"oryx_FDB6E5D1-3626-4ED1-9060-234B50B042F4\"><g class=\"stencils\"><g class=\"me\" title=\"Connector\"><g pointer-events=\"painted\"><path d=\"M449.05505859374995 125L557.9531640625 125 \" stroke=\"black\" fill=\"none\" stroke-width=\"2\" marker-end=\"url(#oryx_FDB6E5D1-3626-4ED1-9060-234B50B042F4arrowEnd)\" id=\"oryx_FDB6E5D1-3626-4ED1-9060-234B50B042F4_1\"/></g></g><g class=\"children\" style=\"overflow:hidden\"/><g class=\"edge\"/></g><g class=\"controls\"><g class=\"dockers\"/><g class=\"magnets\"/></g></g></g></g></g></svg>',NULL,NULL);
/*!40000 ALTER TABLE `content_` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `friend_`
--
-- ORDER BY:  `id`

LOCK TABLES `friend_` WRITE;
/*!40000 ALTER TABLE `friend_` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend_` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `identity_`
--
-- ORDER BY:  `id`

LOCK TABLES `identity_` WRITE;
/*!40000 ALTER TABLE `identity_` DISABLE KEYS */;
INSERT INTO `identity_` (`id`, `uri`) VALUES (1,'root'),(2,'public'),(3,'groups'),(4,'ownership'),(5,'/model/5');
/*!40000 ALTER TABLE `identity_` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `interaction_`
--
-- ORDER BY:  `id`

LOCK TABLES `interaction_` WRITE;
/*!40000 ALTER TABLE `interaction_` DISABLE KEYS */;
INSERT INTO `interaction_` (`id`, `subject`, `subject_descend`, `object`, `object_self`, `object_descend`, `object_restrict_to_parent`, `scheme`, `term`) VALUES (1,'U2',1,'U2',0,1,1,'http://b3mn.org/http','owner');
/*!40000 ALTER TABLE `interaction_` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `model_rating_`
--
-- ORDER BY:  `id`

LOCK TABLES `model_rating_` WRITE;
/*!40000 ALTER TABLE `model_rating_` DISABLE KEYS */;
/*!40000 ALTER TABLE `model_rating_` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `plugin_`
--

LOCK TABLES `plugin_` WRITE;
/*!40000 ALTER TABLE `plugin_` DISABLE KEYS */;
/*!40000 ALTER TABLE `plugin_` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `representation_`
--
-- ORDER BY:  `id`

LOCK TABLES `representation_` WRITE;
/*!40000 ALTER TABLE `representation_` DISABLE KEYS */;
INSERT INTO `representation_` (`id`, `ident_id`, `mime_type`, `language`, `title`, `summary`, `created`, `updated`, `type`) VALUES (1,5,'deprecated','deprecated','Job #1','','2013-07-27 19:53:12','2013-07-27 19:53:12','http://b3mn.org/stencilset/reporting#');
/*!40000 ALTER TABLE `representation_` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `schema_info_`
--

LOCK TABLES `schema_info_` WRITE;
/*!40000 ALTER TABLE `schema_info_` DISABLE KEYS */;
/*!40000 ALTER TABLE `schema_info_` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `setting_`
--
-- ORDER BY:  `id`

LOCK TABLES `setting_` WRITE;
/*!40000 ALTER TABLE `setting_` DISABLE KEYS */;
/*!40000 ALTER TABLE `setting_` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `structure_`
--

LOCK TABLES `structure_` WRITE;
/*!40000 ALTER TABLE `structure_` DISABLE KEYS */;
INSERT INTO `structure_` (`hierarchy`, `ident_id`) VALUES ('U',1),('U1',2),('U20',2),('U2',4),('U3',3),('U201',5);
/*!40000 ALTER TABLE `structure_` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `subject_`
--
-- ORDER BY:  `ident_id`

LOCK TABLES `subject_` WRITE;
/*!40000 ALTER TABLE `subject_` DISABLE KEYS */;
INSERT INTO `subject_` (`ident_id`, `nickname`, `email`, `fullname`, `dob`, `gender`, `postcode`, `first_login`, `last_login`, `login_count`, `language_code`, `country_code`, `password`, `visibility`) VALUES (2,NULL,NULL,NULL,NULL,NULL,NULL,'2008-01-01','2008-01-01',0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `subject_` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tag_definition_`
--
-- ORDER BY:  `id`

LOCK TABLES `tag_definition_` WRITE;
/*!40000 ALTER TABLE `tag_definition_` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag_definition_` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `tag_relation_`
--
-- ORDER BY:  `id`

LOCK TABLES `tag_relation_` WRITE;
/*!40000 ALTER TABLE `tag_relation_` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag_relation_` ENABLE KEYS */;
UNLOCK TABLES;

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

-- Dump completed on 2013-07-27 20:39:08
