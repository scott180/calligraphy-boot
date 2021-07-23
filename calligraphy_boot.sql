-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.20

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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '活动名称',
  `beginTime` datetime DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '结束时间',
  `address` varchar(255) DEFAULT NULL COMMENT '活动地址',
  `type` varchar(255) DEFAULT NULL COMMENT '活动类型',
  `content` text COMMENT '活动内容',
  `host` varchar(255) DEFAULT NULL COMMENT '组织者',
  `limitUser` int(11) DEFAULT NULL COMMENT '限制人数',
  `del` int(11) DEFAULT NULL COMMENT '是否删除',
  `cost` decimal(10,2) DEFAULT NULL COMMENT '费用',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(255) DEFAULT NULL COMMENT '修改人',
  `createDate` datetime DEFAULT NULL COMMENT '创建日期',
  `modifyDate` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (1,'周六凤凰山','2020-01-04 09:00:00','2020-01-04 18:00:00','凤凰山','1','&lt;div&gt;hello world凤凰山&lt;/div&gt;&lt;p&gt;&amp;nbsp;&lt;/p&gt; ','admin',0,1,0.00,'admin','admin','2020-01-01 18:58:06','2020-01-11 19:03:21'),(2,'周日电影','2020-01-04 14:00:00','2020-01-04 18:00:00','电影院','1','五道口电影院','admin',0,0,0.00,'admin','admin','2020-01-01 18:58:06','2020-01-01 18:58:10'),(3,'武功山之旅',NULL,NULL,'武功山',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'武功山之旅',NULL,NULL,'武功山',NULL,'',NULL,NULL,0,NULL,NULL,NULL,'2020-01-04 18:54:27','2020-01-04 18:54:27'),(5,'武功山之旅',NULL,NULL,'武功山',NULL,'&lt;!DOCTYPE html&gt;\n&lt;html&gt;\n&lt;head&gt;\n&lt;style&gt;\n.cities {\n    background-color:black;\n    color:white;\n    margin:20px;\n    padding:20px;\n} \n&lt;/style&gt;\n&lt;/head&gt;\n\n&lt;body&gt;\n\n&lt;div class=&quot;cities&quot;&gt;\n&lt;h2&gt;London&lt;/h2&gt;\n&lt;p&gt;\n活动介绍 活动主题  London is the capital city of England. \nIt is the most populous city in the United Kingdom, \nwith a metropolitan area of over 13 million inhabitants.\n&lt;/p&gt;\n&lt;/div&gt; \n\n&lt;/body&gt;\n&lt;/html&gt;',NULL,NULL,0,NULL,NULL,NULL,'2020-01-04 19:08:22','2020-01-04 19:08:22'),(6,'武功山之旅',NULL,NULL,'武功山',NULL,'&lt;!DOCTYPE html&gt;\n&lt;html&gt;\n&lt;head&gt;\n&lt;style&gt;\n.cities {\n    background-color:black;\n    color:white;\n    margin:20px;\n    padding:20px;\n} \n&lt;/style&gt;\n&lt;/head&gt;\n\n&lt;body&gt;\n\n&lt;div class=&quot;cities&quot;&gt;\n&lt;h2&gt;London&lt;/h2&gt;\n&lt;p&gt;\n活动介绍 活动主题  London is the capital city of England. \nIt is the most populous city in the United Kingdom, \nwith a metropolitan area of over 13 million inhabitants.\n&lt;/p&gt;\n&lt;/div&gt; \n\n&lt;/body&gt;\n&lt;/html&gt;',NULL,NULL,0,NULL,NULL,NULL,'2020-01-04 19:09:20','2020-01-04 19:09:20'),(7,'武功山之旅','2020-01-04 09:00:00','2020-01-04 14:00:00','武功山',NULL,'&lt;!DOCTYPE html&gt;\n&lt;html&gt;\n&lt;head&gt;\n&lt;style&gt;\n.cities {\n    background-color:black;\n    color:white;\n    margin:20px;\n    padding:20px;\n} \n&lt;/style&gt;\n&lt;/head&gt;\n\n&lt;body&gt;\n\n&lt;div class=&quot;cities&quot;&gt;\n&lt;h2&gt;London&lt;/h2&gt;\n&lt;p&gt;\n活动介绍 活动主题  London is the capital city of England. \nIt is the most populous city in the United Kingdom, \nwith a metropolitan area of over 13 million inhabitants.\n&lt;/p&gt;\n&lt;/div&gt; \n\n&lt;/body&gt;\n&lt;/html&gt;',NULL,NULL,1,NULL,NULL,NULL,'2020-01-04 19:11:11','2020-01-04 19:11:11');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `loginName` varchar(255) DEFAULT NULL COMMENT '登录名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `home` varchar(255) DEFAULT NULL COMMENT '家乡',
  `address` varchar(255) DEFAULT NULL COMMENT '住址',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(255) DEFAULT NULL COMMENT '修改人',
  `createDate` datetime DEFAULT NULL COMMENT '创建日期',
  `modifyDate` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin',NULL,'管理员',1,30,'安徽','芜湖','admin','admin','2019-12-29 12:52:26','2019-12-29 12:52:31'),(2,'test',NULL,'张三',2,30,'浙江','杭州','admin','admin','2020-01-01 13:26:32','2020-01-01 13:26:32'),(3,'test1',NULL,'张三1',0,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36'),(4,'test2',NULL,'张三2',1,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36'),(5,'test3',NULL,'张三3',2,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36'),(6,'test4',NULL,'张三4',NULL,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36'),(7,'test5',NULL,'张三5',NULL,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36'),(8,'test6',NULL,'张三6',NULL,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36'),(9,'test7',NULL,'张三7',NULL,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36'),(10,'test8',NULL,'张三8',NULL,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36'),(11,'test9',NULL,'张三9',NULL,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36'),(12,'test10',NULL,'张三10',NULL,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36'),(13,'test11',NULL,'张三11',NULL,30,NULL,NULL,NULL,NULL,'2020-01-01 13:28:36','2020-01-01 13:28:36');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_activity`
--

DROP TABLE IF EXISTS `user_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_activity` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userId` bigint(11) NOT NULL COMMENT '用户id',
  `activityId` bigint(11) NOT NULL COMMENT '活动id',
  `del` int(11) DEFAULT NULL COMMENT '是否删除',
  `cancel` int(255) DEFAULT NULL COMMENT '是否取消',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(255) DEFAULT NULL COMMENT '修改人',
  `createDate` datetime DEFAULT NULL COMMENT '创建日期',
  `modifyDate` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_activity`
--

LOCK TABLES `user_activity` WRITE;
/*!40000 ALTER TABLE `user_activity` DISABLE KEYS */;
INSERT INTO `user_activity` VALUES (5,2,6,NULL,1,NULL,NULL,'2020-01-04 19:48:25','2020-01-04 20:06:38'),(6,1,1,1,1,NULL,NULL,'2020-01-04 20:07:28','2020-01-11 18:17:03'),(7,1,1,1,0,NULL,NULL,'2020-01-05 14:23:13','2020-01-11 18:17:03');
/*!40000 ALTER TABLE `user_activity` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-11 23:10:27
