-- MySQL dump 10.13  Distrib 5.5.43, for Win64 (x86)
--
-- Host: localhost    Database: samyh
-- ------------------------------------------------------
-- Server version	5.5.43

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
-- Table structure for table `battery`
--

DROP TABLE IF EXISTS `battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `battery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sn` varchar(100) NOT NULL COMMENT '电池序列号',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '电池状态：0,正常；1，异常；2，报废',
  `bty_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '云电池标识:0,普通电池；1,云电池',
  `pub_sn` varchar(40) NOT NULL,
  `imei` varchar(100) NOT NULL,
  `sim_no` varchar(40) NOT NULL COMMENT 'sim卡号',
  `imsi` varchar(100) DEFAULT NULL,
  `gsim_no` varchar(40) DEFAULT NULL,
  `iccid` varchar(40) DEFAULT NULL,
  `reseller_id` int(11) DEFAULT NULL COMMENT '经销商Id',
  `sale_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已售出：0,未出售；1，已出售',
  `city_id` int(11) DEFAULT '0',
  `lock_longitude` varchar(30) DEFAULT NULL,
  `lock_latitude` varchar(30) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL COMMENT '生产日期',
  `sale_date` datetime DEFAULT NULL COMMENT '出售日期',
  `lock_date` datetime DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL COMMENT '失效日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_BTYINFO` (`sn`),
  UNIQUE KEY `IX_BTYIMEI` (`imei`),
  UNIQUE KEY `IX_BTYPUBSN` (`pub_sn`),
  UNIQUE KEY `IX_BTYSIMNO` (`sim_no`),
  KEY `IX_BTYSALESTS` (`sale_status`),
  KEY `IX_BTYSTS` (`status`),
  KEY `IX_BTYSLE` (`reseller_id`),
  KEY `IX_BTYCITYID` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battery`
--

LOCK TABLES `battery` WRITE;
/*!40000 ALTER TABLE `battery` DISABLE KEYS */;
INSERT INTO `battery` VALUES (1,'100','0',0,'A10000','10000','15618672987',NULL,NULL,NULL,1,0,1,NULL,NULL,'2015-06-05 16:42:29','2015-06-05 16:42:29','2015-06-05 16:42:29','2015-06-05 16:42:29'),(2,'101','3',0,'A10001','10001','1064600000001','9460067001010009','0',NULL,1,0,1,'121.294400','31.14400','2015-06-05 16:42:29','2015-06-05 16:42:29','2015-07-30 15:29:24','2015-06-05 16:42:29'),(3,'102','0',0,'A10002','10002','1064600000002','1235','1235',NULL,1,0,1,NULL,NULL,'2015-06-05 16:42:29','2015-06-05 16:42:29','2015-06-05 16:42:29','2015-06-05 16:42:29'),(4,'103','0',0,'A10003','10003','1064600000003',NULL,NULL,NULL,1,0,1,NULL,NULL,'2015-06-05 16:42:29','2015-06-05 16:42:29','2015-06-05 16:42:29','2015-06-05 16:42:29'),(5,'104','0',0,'A10004','10004','15200000004',NULL,NULL,NULL,1,0,1,NULL,NULL,'2015-06-05 16:42:29','2015-06-05 16:42:29','2015-06-05 16:42:29','2015-06-05 16:42:29'),(6,'105','1',1,'PsCTO4WB','10005','15200000005',NULL,NULL,NULL,1,1,NULL,NULL,NULL,'2015-06-18 04:16:27','2015-06-18 04:16:27',NULL,NULL),(7,'106','1',1,'n3IXnF7h','10006','15200000006',NULL,NULL,NULL,1,1,NULL,NULL,NULL,'2015-06-30 10:46:23','2015-06-30 10:46:23',NULL,NULL),(8,'107','1',1,'jTcDLBiq','10007','15200000007',NULL,NULL,NULL,1,1,NULL,NULL,NULL,'2015-06-30 11:26:05','2015-06-30 11:26:05',NULL,NULL),(9,'109','1',1,'3TqaUDrM','10009','15200000009','1235','1235',NULL,19,1,NULL,NULL,NULL,'2015-06-30 16:35:34','2015-06-30 16:35:34',NULL,NULL),(10,'110','1',1,'CeXJu8LO','10010','15200000010',NULL,NULL,NULL,19,1,NULL,NULL,NULL,'2015-06-30 16:36:05','2015-06-30 16:36:05',NULL,NULL),(11,'111','1',1,'fPJx24vM','10011','15200000011',NULL,NULL,NULL,19,1,NULL,NULL,NULL,'2015-06-30 16:36:34','2015-06-30 16:36:34',NULL,NULL),(12,'112','1',1,'SGlX2D3p','10012','15200000012',NULL,NULL,NULL,19,1,NULL,NULL,NULL,'2015-06-30 16:37:01','2015-06-30 16:37:01',NULL,NULL),(13,'113','1',1,'cpoJbVTt','10013','15200000013',NULL,NULL,NULL,19,1,NULL,NULL,NULL,'2015-06-30 16:37:22','2015-06-30 16:37:22',NULL,NULL),(14,'114','1',1,'70WQAfKe','10014','15200000014',NULL,NULL,NULL,19,1,2,NULL,NULL,'2015-07-03 09:12:27','2015-07-03 09:12:27',NULL,NULL),(15,'115','0',1,'CYRzCEp4','10015','15200000015',NULL,NULL,NULL,20,1,2,NULL,NULL,'2015-07-29 14:40:09','2015-07-29 14:40:09',NULL,NULL),(22,'117','0',1,'RpWjGOHw','10017','861064619001843',NULL,NULL,'8986061501000090533',2,1,2,NULL,NULL,'2015-08-14 10:26:33','2015-08-14 10:26:33',NULL,NULL);
/*!40000 ALTER TABLE `battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `battery_info`
--

DROP TABLE IF EXISTS `battery_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `battery_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `battery_id` int(11) NOT NULL,
  `longitude` varchar(30) NOT NULL COMMENT '经度',
  `latitude` varchar(30) NOT NULL COMMENT '纬度',
  `temperature` varchar(30) NOT NULL COMMENT '温度',
  `voltage` varchar(30) NOT NULL COMMENT '电压',
  `status` char(1) NOT NULL DEFAULT '0',
  `receive_date` datetime NOT NULL COMMENT '接收时间',
  `sample_date` datetime NOT NULL COMMENT '采样时间',
  PRIMARY KEY (`id`),
  KEY `IX_BTYINFOS` (`battery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battery_info`
--

LOCK TABLES `battery_info` WRITE;
/*!40000 ALTER TABLE `battery_info` DISABLE KEYS */;
INSERT INTO `battery_info` VALUES (1,1,'2','3','4','6','1','2015-06-18 02:32:28','2015-06-18 02:32:28'),(2,1,'2','4','4','6','1','2015-06-18 02:32:40','2015-06-18 02:32:40'),(3,2,'2','4','4','6','1','2015-06-18 02:32:50','2015-06-18 02:32:50'),(4,2,'5','5','5','6','1','2015-06-18 02:33:02','2015-06-18 02:33:02'),(5,3,'6','6','5','6','1','2015-06-18 02:33:13','2015-06-18 02:33:13'),(6,3,'7','7','5','6','1','2015-06-18 02:33:19','2015-06-18 02:33:19'),(7,4,'6','6','5','6','1','2015-06-18 02:33:30','2015-06-18 02:33:30'),(8,4,'8','8','5','6','1','2015-06-18 02:33:37','2015-06-18 02:33:37'),(9,5,'8','8','5','6','1','2015-06-18 02:33:41','2015-06-18 02:33:41'),(10,1,'2','3','4','600','1','2015-06-18 04:24:40','2015-06-18 04:24:40'),(11,9,'121.401422','31.175332','40','220','1','2015-06-30 16:51:33','2015-06-30 16:51:33'),(12,10,'121.401422','31.175332','40','220','1','2015-06-30 16:52:17','2015-06-30 16:52:17'),(13,11,'121.401422','31.175332','40','220','1','2015-06-30 16:52:30','2015-06-30 16:52:30'),(14,12,'121.401422','31.175332','40','220','1','2015-06-30 16:52:58','2015-06-30 16:52:58'),(15,13,'121.401422','31.175332','40','220','1','2015-06-30 16:53:10','2015-06-30 16:53:10'),(16,6,'121.401422','31.175332','230','220','1','2015-07-07 23:02:12','2015-07-07 23:02:12'),(17,2,'121.294400','31.1440','24','220','1','2015-07-10 22:29:14','2015-07-10 22:29:14'),(18,2,'121.294400','31.1440','24','220','1','2015-07-10 22:29:57','2015-07-10 22:29:57'),(19,2,'121.294400','31.1440','24','220','1','2015-07-10 22:30:35','2015-07-10 22:30:35'),(20,2,'121.294400','31.1440','24','220','1','2015-07-10 23:20:42','2015-07-10 23:20:42'),(21,6,'121.401422','31.175332','230','220','1','2015-07-10 23:26:37','2015-07-10 23:26:37'),(22,2,'121.294400','31.1440','24','220','1','2015-07-10 23:27:59','2015-07-10 23:27:59'),(23,3,'121.294400','31.1440','24','220','1','2015-07-10 23:31:16','2015-07-10 23:31:16'),(24,3,'121.294400','31.1440','24','20.5','1','2015-07-11 11:54:33','2015-07-11 11:54:33'),(25,3,'121.294400','31.1440','56','20.5','1','2015-07-11 11:55:22','2015-07-11 11:55:22'),(27,3,'121.294400','31.1440','56','20.5','0','2015-07-11 15:04:41','2015-07-11 15:04:41'),(28,3,'121.294400','31.1440','56','20.5','0','2015-07-11 15:05:05','2015-07-11 15:05:05'),(29,3,'121.294400','31.1440','56','20.5','0','2015-07-11 15:14:36','2015-07-11 15:14:36'),(30,9,'121','31','56','20.5','0','2015-07-11 16:28:26','2015-07-11 16:28:26'),(35,8,'121.401422','31.175332','42','20.5','1','2015-07-29 14:50:31','2015-07-29 14:50:31'),(36,8,'121.401422','31.175332','42','20.5','1','2015-07-29 14:50:56','2015-07-29 14:50:56'),(37,8,'121.401422','31.175332','42','20.5','1','2015-07-30 11:14:21','2015-07-30 11:14:21'),(38,2,'121.401422','31.175332','42','20.5','1','2015-07-30 11:15:06','2015-07-30 11:15:06'),(39,2,'121.401422','31.175332','42','20.5','1','2015-07-30 11:16:14','2015-07-30 11:16:14'),(40,2,'121.401422','31.175332','42','20.5','1','2015-07-30 11:17:10','2015-07-30 11:17:10'),(41,2,'121.401422','31.175332','42','20.5','1','2015-07-30 11:27:21','2015-07-30 11:27:21'),(42,2,'121.401422','31.175332','42','20.5','1','2015-07-30 11:27:43','2015-07-30 11:27:43'),(43,2,'121.294400','31.14400','42','20.5','1','2015-07-30 11:30:17','2015-07-30 11:30:17'),(45,2,'121.381392','31.178953','18','11.8','0','2015-11-22 15:16:42','2015-11-22 15:16:42'),(46,2,'121.381392','31.178953','18','11.8','0','2015-11-22 15:20:51','2015-11-22 15:20:51'),(47,2,'121.381392','31.178953','18','11.8','0','2015-11-22 15:21:13','2015-11-22 15:21:13'),(48,2,'121.381392','31.178953','18','11.8','0','2015-11-23 23:04:53','2015-11-23 23:04:53'),(49,3,'121.381392','31.178953','18','11.8','0','2015-11-23 23:05:23','2015-11-23 23:05:23'),(50,3,'121.381392','31.178953','18','11.8','0','2015-11-23 23:37:22','2015-11-23 23:37:22'),(51,2,'121.381392','31.178953','18','11.8','0','2015-11-23 23:37:38','2015-11-23 23:37:38'),(52,3,'121.381392','31.178953','18','11.8','0','2015-12-27 23:38:00','2015-12-27 23:38:00'),(53,2,'121.381392','31.178953','18','11.8','0','2015-12-27 23:39:10','2015-12-27 23:39:10'),(54,3,'121.381392','31.178953','18','11.8','0','2015-12-27 23:39:25','2015-12-27 23:39:25');
/*!40000 ALTER TABLE `battery_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_role`
--

DROP TABLE IF EXISTS `group_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(30) NOT NULL COMMENT '组名',
  `permission_id` int(11) NOT NULL,
  `description` varchar(100) DEFAULT NULL COMMENT '组角色描述',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_role`
--

LOCK TABLES `group_role` WRITE;
/*!40000 ALTER TABLE `group_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(30) NOT NULL COMMENT '权限名',
  `description` varchar(100) NOT NULL COMMENT '权限描述',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reseller`
--

DROP TABLE IF EXISTS `reseller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reseller` (
  `user_id` int(11) NOT NULL COMMENT '关联userId',
  `office_address` varchar(75) NOT NULL,
  `longitude` varchar(30) NOT NULL,
  `latitude` varchar(30) NOT NULL,
  `province_name` varchar(20) NOT NULL,
  `province_id` int(11) DEFAULT NULL COMMENT '所在省',
  `city_name` varchar(20) NOT NULL,
  `city_id` int(11) DEFAULT NULL COMMENT '所在市',
  `verify_status` int(11) NOT NULL COMMENT '经销商认证状态',
  `verify_date` datetime DEFAULT NULL COMMENT '认证时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reseller`
--

LOCK TABLES `reseller` WRITE;
/*!40000 ALTER TABLE `reseller` DISABLE KEYS */;
INSERT INTO `reseller` VALUES (2,'陆家嘴环路1000号恒生大厦8041','121.294400','31.1440','上海',2,'上海',2,1,'2015-07-02 10:57:52'),(19,'陆家嘴环路1000号恒生大厦8041','101','101','上海',2,'上海',2,1,'2015-06-30 15:24:12'),(20,'陆家嘴环路1000号恒生大厦8041','101','101','上海',2,'上海',2,1,'2015-06-30 15:25:16'),(21,'陆家嘴环路1000号恒生大厦8041','101','101','上海',2,'上海',2,1,'2015-06-30 15:25:37'),(22,'陆家嘴环路1000号恒生大厦8041','101','101','上海',2,'上海',2,1,'2015-06-30 15:25:58'),(23,'陆家嘴环路1000号恒生大厦8041','101','101','上海',2,'上海',2,1,'2015-06-30 15:26:13'),(24,'陆家嘴环路1000号恒生大厦8041','101','101','上海',2,'上海',2,1,'2015-06-30 15:26:28'),(25,'陆家嘴环路1000号恒生大厦8041','101','101','上海',2,'上海',2,1,'2015-06-30 15:26:45'),(26,'陆家嘴环路1000号恒生大厦8041','101','101','上海',2,'上海',2,1,'2015-07-08 22:28:40'),(27,'陆家嘴环路1000号恒生大厦8041','101','101','上海',2,'上海',2,1,'2015-07-08 22:34:54'),(28,'陆家嘴环路1000号恒生大厦8041','101','101','上海',2,'上海',2,1,'2015-07-08 22:37:27');
/*!40000 ALTER TABLE `reseller` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `uuid` varchar(75) DEFAULT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `user_type` char(1) NOT NULL DEFAULT '0',
  `salt` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `mobile_phone` varchar(11) NOT NULL COMMENT '手机号码',
  `passport_no` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  `lock_status` tinyint(1) DEFAULT NULL COMMENT '是否锁定',
  `device_info` varchar(100) DEFAULT NULL COMMENT '设备信息',
  `create_date` datetime DEFAULT NULL COMMENT '注册日期',
  `login_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `IX_USERMP` (`mobile_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('56bbc2b4738a4df0aa6d02797f8f7c01',1,'15618672987','2','bFv7v1wMye','123456789','15618672987',NULL,0,'XXXXXXXXXXX','2015-06-16 02:28:07','2015-06-30 11:08:55'),('f5b5a5a81cfc4efd847bd46a4691aca4',2,'13900000001','1','dCpV4aUeXA','123456789','13900000001',NULL,0,'XXXXXXXXXXX','2015-06-18 03:23:49','2015-06-30 11:08:27'),('0884fd409a8e4ff4ac7a26a44aef9cb4',3,'13900000002','0','18aUOBj8T1','123456789','13900000002',NULL,0,'AAAAAAAAAAAA','2015-06-18 03:24:33','2015-06-18 03:24:33'),('1373fefbced24419ac373f3254621af5',4,'13900000003','0','JIxgbhTS3x','123456789','13900000003',NULL,0,'AAAAAAAAAAAA','2015-06-18 03:25:45','2015-06-18 03:25:45'),('bc2cb5689900466b845935f4ba6df77e',5,'13900000004','0','a2jVjeJ4ye','123456789','13900000004',NULL,0,'AAAAAAAAAAAA','2015-06-18 03:26:34','2015-06-18 03:26:34'),('f6731c0390f747149be5fbc65ce78537',6,'nate','0','EaF44WOucj','NJTMLEGwmj','15618672989',NULL,1,NULL,'2015-06-18 04:01:00',NULL),('e3ab93a95d044902b0e8bcf1ab156479',8,'毛总','0','bsR9O29kSX','spSePVM52s','13900000005',NULL,0,NULL,'2015-06-24 23:27:13',NULL),('fb89e4a87df345ceab51e5f003e508bc',9,'毛总','0','9KybbUEc2V','577d8656371f7a58b94625049e65454f','13900000006',NULL,0,NULL,'2015-06-25 00:22:48',NULL),('27be305cba3f4595a371dd4e0c6595de',10,'毛总','0','iCjIyZoiSd','12345678','13900000007',NULL,0,'XXXXXXXXXXX','2015-06-25 00:38:13','2015-06-25 00:39:49'),('40a6a38ffe2a449b8c177cf8c74fb98b',11,'毛总','0','xKlrcHx7p5','123456789','13900000008',NULL,0,'XXXXXXXXXXX','2015-06-25 00:41:05','2015-06-25 01:06:08'),('8b9c955a50414df6823d5a34747c37c3',12,'毛总','0','FiRkLhQuBb','bec39c00ef22dd79babe98b93bc9f116','13900000009',NULL,0,NULL,'2015-06-25 01:55:23',NULL),('7c66baa9dc324a7282049153c369911f',16,'13900000013','0','4BkCPpVaWq','c24c78824f88e88600922cfefa799c42','13900000013',NULL,0,'XXXXXXXXXXX','2015-06-30 11:15:31','2015-06-30 11:24:19'),('cd8c3e0d02314585ad20e8a69201f8fe',17,'nate3','0','SxgEnaW24I','eb6f6c6e6dad56fd59ea6e4af4ecb2e5','13900000014',NULL,0,'XXXXXXXXXXX','2015-06-30 11:26:05','2015-07-23 16:41:15'),('88f4a8f8cc5b470cbce228c3ac9caefd',19,'三毛','1','IinrJ94bX4','c59ce38e266c81186249b52d375902ad','13900000016',NULL,0,NULL,'2015-06-30 15:24:11',NULL),('4d71357c0e204299838eccfc082e2bb1',20,'四毛','1','8iiwZcXX0s','56132a5409eebb505213eb8f6acb8870','13900000017',NULL,0,NULL,'2015-06-30 15:25:16',NULL),('ae5d47d6523f4275ad4d5f9e7172a6c6',21,'五毛','1','G5C4OF3tvZ','d9916d711d7b1225e72060399c530170','13900000018',NULL,0,NULL,'2015-06-30 15:25:37',NULL),('51c1cc938e784e59befabb4c473330af',22,'六毛','1','QzwrBrBNVo','247a61394e26162a4d8edecc54ba0534','13900000019',NULL,0,NULL,'2015-06-30 15:25:58',NULL),('d4e529f8a64a4cbd8552ecc76493e465',23,'六毛','1','1OVjqLXKXz','f606c2b215420bb0016cd492e198627c','13900000020',NULL,0,NULL,'2015-06-30 15:26:13',NULL),('ed8ef8580ac0480999c96f34e22ae82f',24,'六毛','1','IOCvee50hi','cc744ab04452e1be19d59fab45af5792','13900000021',NULL,0,NULL,'2015-06-30 15:26:28',NULL),('c4506bb60cca4f1ebad814b752214939',25,'六毛','1','dygzj5nbOI','43c6cf0adaeacf0678d09fef9c407690','13900000022',NULL,0,NULL,'2015-06-30 15:26:45',NULL),('7834e12731534e3e9a8a8f3b621edc71',26,'六毛','1','yV8m2HsC79','431dd1e34d92d721231f7b574663c7e5','13900000023',NULL,0,NULL,'2015-07-08 22:28:40',NULL),('ec250925b3724e7f8d82ac041827f41d',27,'六毛','1','vqoXCLJ3xJ','5bf14ac6c5c02a2ded516df0751ec13b','13900000024',NULL,0,NULL,'2015-07-08 22:34:30',NULL),('41a93f0ec90c4ee4aa7349ba64652c40',28,'六毛','1','jHxRtxiw91','61e35d8bf85b6ef71521deacff3449ca','13900000025',NULL,0,'XXXXXXXXXXX','2015-07-08 22:37:25','2015-07-23 16:41:30');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_battery`
--

DROP TABLE IF EXISTS `user_battery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_battery` (
  `user_id` int(11) NOT NULL,
  `battery_id` int(11) NOT NULL,
  `buy_date` datetime NOT NULL COMMENT '购买日期',
  PRIMARY KEY (`user_id`,`battery_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_battery`
--

LOCK TABLES `user_battery` WRITE;
/*!40000 ALTER TABLE `user_battery` DISABLE KEYS */;
INSERT INTO `user_battery` VALUES (1,1,'2015-06-05 16:42:29'),(1,2,'2015-06-05 16:42:29'),(1,3,'2015-06-05 16:42:29'),(1,22,'2015-08-14 10:26:33'),(2,4,'2015-06-05 16:42:29'),(2,5,'2015-06-05 16:42:29'),(13,7,'2015-06-30 10:46:23'),(17,8,'2015-06-30 11:26:05'),(20,9,'2015-06-30 16:35:34'),(20,10,'2015-06-30 16:36:05'),(20,11,'2015-06-30 16:36:34'),(20,12,'2015-06-30 16:37:01'),(20,13,'2015-06-30 16:37:23'),(20,14,'2015-07-03 09:12:27'),(21,15,'2015-07-29 14:40:09');
/*!40000 ALTER TABLE `user_battery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_code`
--

DROP TABLE IF EXISTS `user_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile_phone` varchar(100) DEFAULT NULL,
  `dynamic_code` varchar(20) DEFAULT NULL,
  `code_type` int(11) NOT NULL COMMENT '验证码业务类型',
  `send_times` int(11) NOT NULL DEFAULT '0' COMMENT '发送次数',
  `status` tinyint(1) NOT NULL COMMENT '是否验证状态标识：0,未验证；1，已验证',
  `send_date` datetime NOT NULL COMMENT '发送时间',
  `expiry_date` datetime NOT NULL COMMENT '失效时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_USERPHONE` (`mobile_phone`,`code_type`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_code`
--

LOCK TABLES `user_code` WRITE;
/*!40000 ALTER TABLE `user_code` DISABLE KEYS */;
INSERT INTO `user_code` VALUES (1,'15618672987','483051',3,1,0,'2015-06-16 02:27:13','2015-06-16 02:57:13'),(2,'13900000001','421646',3,2,1,'2015-06-18 00:09:01','2015-06-18 00:39:01'),(3,'13900000001','895374',1,1,0,'2015-06-18 03:22:41','2015-06-18 03:52:41'),(4,'13900000002','926503',1,1,0,'2015-06-18 03:24:05','2015-06-18 03:54:05'),(5,'13900000003','228858',1,1,0,'2015-06-18 03:24:52','2015-06-18 03:54:52'),(6,'13900000004','095625',1,1,0,'2015-06-18 03:26:02','2015-06-18 03:56:02'),(7,'13900000008','276168',1,1,1,'2015-06-25 00:54:15','2015-06-25 01:24:15'),(8,'13900000008','695653',2,1,0,'2015-06-25 00:58:53','2015-06-25 01:28:53'),(9,'13900000010','748702',1,1,0,'2015-06-30 10:14:51','2015-06-30 10:44:51'),(10,'13900000013','340410',1,1,0,'2015-06-30 11:14:09','2015-06-30 11:44:09'),(11,'13900000013','868112',2,2,1,'2015-07-23 16:40:48','2015-07-23 17:10:48'),(12,'13900000014','364296',1,1,0,'2015-06-30 11:26:05','2015-06-30 11:56:05'),(13,'10002','15618672987',5,1,1,'2015-07-11 15:04:41','2015-07-11 15:34:41'),(14,'10009','13900000017',5,1,1,'2015-07-11 16:28:37','2015-07-11 16:58:37'),(15,'10007','13900000014',5,2,1,'2015-07-30 11:14:21','2015-07-30 11:44:21'),(16,'10001','15618672987',5,1,1,'2015-07-30 11:15:06','2015-07-30 11:45:06'),(17,'10001','15618672987',6,4,1,'2015-12-27 23:39:10','2015-12-28 00:09:10');
/*!40000 ALTER TABLE `user_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_follow`
--

DROP TABLE IF EXISTS `user_follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_follow` (
  `user_id` int(11) NOT NULL,
  `battery_id` int(11) NOT NULL,
  `follow_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否关注',
  `follow_date` datetime NOT NULL,
  PRIMARY KEY (`user_id`,`battery_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_follow`
--

LOCK TABLES `user_follow` WRITE;
/*!40000 ALTER TABLE `user_follow` DISABLE KEYS */;
INSERT INTO `user_follow` VALUES (1,4,1,'2015-06-18 04:07:35'),(1,5,1,'2015-06-16 02:32:32'),(2,2,0,'2015-07-23 16:41:19'),(3,2,1,'2015-06-28 16:07:13'),(4,2,1,'2015-06-28 16:07:30');
/*!40000 ALTER TABLE `user_follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_t`
--

DROP TABLE IF EXISTS `user_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(40) NOT NULL,
  `password` varchar(255) NOT NULL,
  `age` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_t`
--

LOCK TABLES `user_t` WRITE;
/*!40000 ALTER TABLE `user_t` DISABLE KEYS */;
INSERT INTO `user_t` VALUES (1,'nate','123456',29);
/*!40000 ALTER TABLE `user_t` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-28 22:42:03
