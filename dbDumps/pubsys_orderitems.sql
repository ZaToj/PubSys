-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pubsys
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `orderitems`
--

DROP TABLE IF EXISTS `orderitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderitems` (
  `orderitemid` int NOT NULL AUTO_INCREMENT,
  `orderid` int DEFAULT NULL,
  `itemid` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`orderitemid`),
  KEY `orderid_idx` (`orderid`),
  KEY `itemid_idx` (`itemid`),
  CONSTRAINT `itemid` FOREIGN KEY (`itemid`) REFERENCES `menuitems` (`itemId`) ON DELETE CASCADE,
  CONSTRAINT `orderid` FOREIGN KEY (`orderid`) REFERENCES `orders` (`orderid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitems`
--

LOCK TABLES `orderitems` WRITE;
/*!40000 ALTER TABLE `orderitems` DISABLE KEYS */;
INSERT INTO `orderitems` VALUES (1,2,4,1),(2,2,4,1),(3,2,5,1),(4,2,9,1),(5,3,1,1),(6,3,1,1),(7,3,2,1),(8,3,5,1),(9,4,1,1),(10,4,1,1),(11,4,5,1),(12,5,1,1),(13,5,1,1),(14,5,1,1),(15,5,1,1),(16,5,1,1),(17,5,1,1),(18,5,1,1),(19,5,1,1),(20,5,1,1),(21,5,1,1),(22,5,4,1),(23,6,4,1),(24,6,4,1),(25,6,4,1),(26,6,4,1),(27,6,5,1),(28,6,5,1),(29,6,2,1),(30,7,1,1),(31,7,1,1),(32,7,1,1),(33,7,1,1),(34,7,1,1),(35,7,1,1),(40,9,4,1),(41,9,4,1),(42,9,5,1),(43,9,5,1),(44,9,5,1),(45,9,1,1),(46,9,1,1),(47,9,2,1),(48,10,6,1),(49,10,5,1),(50,10,5,1),(51,10,6,1),(52,10,2,1),(53,10,2,1),(54,10,3,1),(55,10,1,1),(56,11,1,1),(57,11,9,1),(58,11,5,1),(59,12,7,1),(60,12,7,1),(61,12,7,1),(62,12,4,1),(63,12,5,1),(64,13,4,1),(65,13,4,1),(66,13,4,1),(67,14,7,1),(68,14,7,1),(69,14,7,1),(70,15,7,1),(71,15,7,1),(72,16,4,1),(73,16,4,1),(74,16,4,1),(75,17,4,1),(76,17,4,1),(77,17,4,1),(78,17,4,1),(79,17,4,1),(80,17,4,1);
/*!40000 ALTER TABLE `orderitems` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-01 16:07:32
