
-- MySQL dump 10.13  Distrib 5.7.19, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: Hotel
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `Coupon`
--

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `hotelId` int(11) DEFAULT '-1',
  `couponType` int(11) NOT NULL,
  `couponName` varchar(255) NOT NULL,
  `target_money` int(11) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `discount_money` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Coupon`
--
BEGIN;
/*!40000 ALTER TABLE `Coupon` DISABLE KEYS */;
INSERT INTO `Coupon` VALUES (1,'满500-100优惠',2,3,'满减优惠券',500,0,1,NULL,NULL,100),
                            (2,'三间及以上预订特惠',2,2,'多间优惠券',200,0,1,NULL,NULL,100),
                            (3,'双十一购物狂欢节优惠',-1,5,'双十一优惠券',400,0,1,'2020-11-11 00:00:00','2020-11-11 23:59:59',40),
                            (4,'六一儿童节优惠',-1,5,'六一优惠券',0,0.9,1,'2020-06-01 00:00:00','2020-06-01 23:59:59',0),
                            (5,'限时优惠',2,4,'限时优惠券',50,0,1,'2020-05-31 00:00:00','2020-05-31 23:59:59',50);
/*!40000 ALTER TABLE `Coupon` ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `Hotel`
--

DROP TABLE IF EXISTS `Hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotelName` varchar(255) NOT NULL,
  `hotelDescription` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `bizRegion` varchar(255) DEFAULT NULL,
  `hotelStar` varchar(255) DEFAULT NULL,
  `phoneNum` varchar(255) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel`
--

BEGIN;
/*!40000 ALTER TABLE `Hotel` DISABLE KEYS */;
INSERT INTO `Hotel` VALUES (1,'汉庭酒店','欢迎您入住','南京市玄武区如意里20号','RegionA','Three',1829373819,4.5,1,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/hotel1.jpg'),
                           (2,'儒家酒店','欢迎您入住','南京市鼓楼区珠江路268号','RegionA','Five',1829373819,4.7,2,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/hotel2.jpg'),
                           (3,'桂圆酒店','欢迎您入住','南京市栖霞区珠江路268号','RegionB','Four',1829553719,4.8,3,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/hotel3.jpg'),
                           (4,'万达酒店','欢迎您入住','南京市江宁区竹山路55号','RegionB','Four',1829553719,4.6,4,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/hotel4.jpg'),
                           (5,'南京宾馆','欢迎您入住','南京市栖霞区仙林大道166号','RegionC','Four',1829553719,4.8,5,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/hotel5.png'),
                           (6,'希尔顿大酒店','欢迎您入住','南京市建邺区万景园路8号','RegionD','Five',1829553719,4.8,6,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/hotel6.jpg'),
                           (7,'七天酒店','欢迎您入住','南京市建邺区万景园路8号','RegionE','Three',1829553719,4.8,13,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/hotel7.jpg'),
                           (8,'金陵大酒店','欢迎您入住','南京市建邺区万景园路8号','RegionE','Five',1829553719,4.8,14,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/hotel8.jpg');
/*!40000 ALTER TABLE `Hotel` ENABLE KEYS */;
COMMIT;

--
-- Table structure for table `PictureList`
--

DROP TABLE IF EXISTS `PictureList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PictureList` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `hotelId` int(11) DEFAULT NULL,
    `linkStr` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PictureList`
--

/*!40000 ALTER TABLE `PictureList` DISABLE KEYS */;
INSERT INTO `PictureList` VALUES (1,1,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/one.jpg'),
                                 (2,1,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/two.jpg'),
                                 (3,2,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/three.jpg'),
                                 (4,2,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/four.jpg'),
                                 (5,2,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/five.jpg'),
                                 (6,2,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/six.jpg'),
                                 (7,2,'http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E5%9B%BE%E7%89%87/seven.jpg');
/*!40000 ALTER TABLE `PictureList` ENABLE KEYS */;

--
-- Table structure for table `OrderList`
--

DROP TABLE IF EXISTS `OrderList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderList` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `hotelId` int(11) DEFAULT NULL,
  `hotelName` varchar(255) DEFAULT NULL,
  `checkInDate` varchar(255) DEFAULT NULL,
  `checkOutDate` varchar(255) DEFAULT NULL,
  `roomType` varchar(255) DEFAULT NULL,
  `roomNum` int(255) DEFAULT NULL,
  `peopleNum` int(255) DEFAULT NULL,
  `haveChild` tinytext,
  `createDate` varchar(255) DEFAULT NULL,
  `price` decimal(65,0) DEFAULT NULL,
  `clientName` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `orderState` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderList`
--

/*!40000 ALTER TABLE `OrderList` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrderList` ENABLE KEYS */;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` decimal(65,0) DEFAULT NULL,
  `curNum` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `roomType` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `peopleNum` int(11) DEFAULT NULL,
  `breakfast` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

BEGIN;
/*!40000 ALTER TABLE `Room` DISABLE KEYS */;
INSERT INTO `Room` VALUES (1,199,20,20,1,'豪华大床房','单床 35㎡ 7层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel1-BigBed.jpg',2,1),
                          (2,299,30,30,1,'商务双床房','多床 35㎡ 6层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel1-DoubleBed.jpg',2,0),
                          (3,399,10,10,1,'家庭房','多床 38㎡ 3层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel1-family.jpg',3,0),
                          (4,399,10,10,2,'家庭房','多床 38㎡ 6层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel2-family.jpg',3,0),
                          (5,399,10,10,3,'高级大床房','单床 25-35㎡ 5层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel3-BigBed.jpg',2,1),
                          (6,419,10,10,3,'豪华大床房','单床 28-35㎡ 6层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel3-KingBed.jpg',2,1),
                          (7,469,10,10,3,'豪华双床房','多床 40-45㎡ 8层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel3-DoubleBed.jpg',2,1),
                          (8,199,20,20,4,'豪华大床房','单床 35㎡ 7层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel1-BigBed.jpg',2,1),
                          (9,299,30,30,4,'商务双床房','多床 35㎡ 6层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel1-DoubleBed.jpg',2,0),
                          (10,399,10,10,4,'家庭房','多床 38㎡ 3层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel1-family.jpg',3,0),
                          (11,399,10,10,5,'家庭房','多床 38㎡ 6层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel2-family.jpg',3,0),
                          (12,399,10,10,5,'高级大床房','单床 25-35㎡ 5层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel3-BigBed.jpg',2,1),
                          (13,419,10,10,6,'豪华大床房','单床 28-35㎡ 6层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel3-KingBed.jpg',2,1),
                          (14,469,10,10,6,'豪华双床房','多床 40-45㎡ 8层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel3-DoubleBed.jpg',2,1),
                          (15,376,10,10,7,'高级大床房','单床 25-35㎡ 5层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel7-BigBed.jpg',2,1),
                          (16,423,10,10,7,'豪华双床房','多床 25-35㎡ 5层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel7-DoubleBed.jpg',2,1),
                          (17,419,10,10,8,'豪华大床房','单床 28-35㎡ 6层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel3-KingBed.jpg',2,1),
                          (18,469,10,10,8,'豪华双床房','多床 40-45㎡ 8层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel3-DoubleBed.jpg',2,1),
                          (19,480,10,10,8,'温馨亲子房','多床 25-35㎡ 5层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel8-Family.jpg',3,1),
                          (20,550,10,10,8,'豪华家庭房','多床 25-35㎡ 5层 有窗 WIFI免费 有线带宽免费','http://siege-lion-programme.oss-cn-shanghai.aliyuncs.com/%E9%85%92%E5%BA%97%E6%88%BF%E9%97%B4%E5%9B%BE%E7%89%87/hotel8-KingFamily.jpg',3,1);
/*!40000 ALTER TABLE `Room` ENABLE KEYS */;
COMMIT;


--
-- Table structure for table `Credit`
--
DROP TABLE IF EXISTS `Credit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Credit` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `orderId` int(11) DEFAULT NULL,
    `userId` int(11) DEFAULT NULL,
    `createDate` varchar(255) DEFAULT NULL,
    `actionType` varchar(255) DEFAULT NULL,
    `creditChange` decimal(65,0) DEFAULT NULL,
    `res` decimal(65,0) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data for table `Credit`
--

/*!40000 ALTER TABLE `Credit` DISABLE KEYS */;
/*!40000 ALTER TABLE `Credit` ENABLE KEYS */;


--
-- Table structure for table `CommentList`
--
DROP TABLE IF EXISTS `CommentList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CommentList` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `userId` int(11) DEFAULT NULL,
    `hotelId` int(11) DEFAULT NULL,
    `orderId` int(11) DEFAULT NULL,
    `createDate` varchar(255) DEFAULT NULL,
    `descriptionScore` double DEFAULT NULL,
    `serviceScore` double DEFAULT NULL,
    `environmentScore` double(65, 0) DEFAULT NULL,
    `finalScore` double(65, 0) DEFAULT NULL,
    `userEvaluation` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Comment`
--

/*!40000 ALTER TABLE `CommentList` DISABLE KEYS */;
/*!40000 ALTER TABLE `CommentList` ENABLE KEYS */;


--
-- Table structure for table `VIP`
--
DROP TABLE IF EXISTS `VIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VIP` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `userId` int(11) DEFAULT NULL,
    `vipName` varchar(255) DEFAULT NULL,
    `vipType` varchar(255) DEFAULT NULL,
    `birthday` varchar(255) DEFAULT NULL,
    `enterpriseName` varchar(255) DEFAULT NULL,
    `vipLevel` int(11) DEFAULT NULL,
    `vipDiscount` double DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VIP`
--

/*!40000 ALTER TABLE `VIP` DISABLE KEYS */;
INSERT INTO `VIP` VALUES (1,8,'测试一号','普通会员','2000-02-20',NULL,1,0.95),
                         (2,9,'测试二号','企业会员',NULL,'南京大学',1,0.95);
/*!40000 ALTER TABLE `VIP` ENABLE KEYS */;


--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `credit` double(255,0) DEFAULT NULL,
  `usertype` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

BEGIN;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'001@qq.com','123456','酒店管理员一号','12345678919',100,'HotelManager'),
                          (2,'002@qq.com','123456','酒店管理员二号','12345678919',100,'HotelManager'),
                          (3,'003@qq.com','123456','酒店管理员三号','12345678919',100,'HotelManager'),
                          (4,'004@qq.com','123456','酒店管理员四号','12345678919',100,'HotelManager'),
                          (5,'005@qq.com','123456','酒店管理员五号','12345678919',100,'HotelManager'),
                          (6,'006@qq.com','123456','酒店管理员六号','12345678919',100,'HotelManager'),
                          (7,'666@qq.com','123456','manager',NULL,100,'Manager'),
                          (8,'123@qq.com','123456','测试一号','12345678919',100,'Client'),
                          (9,'456@qq.com','123456','测试二号','12345678911',100,'Client'),
                          (10,'789@qq.com','123456','测试三号','12345678911',100,'Client'),
                          (11,'321@qq.com','123456','测试四号','12345678911',100,'Client'),
                          (12,'007@qq.com','123456','酒店管理员七号','12345678919',100,'HotelManager'),
                          (13,'008@qq.com','123456','酒店管理员八号','12345678919',100,'HotelManager');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
COMMIT;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-12 10:08:42
SET FOREIGN_KEY_CHECKS = 1;