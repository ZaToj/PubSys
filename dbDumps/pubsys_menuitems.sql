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
-- Table structure for table `menuitems`
--

DROP TABLE IF EXISTS `menuitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menuitems` (
  `itemId` int NOT NULL AUTO_INCREMENT,
  `itemName` varchar(45) DEFAULT NULL,
  `itemCost` int DEFAULT NULL,
  `imgFilePath` varchar(45) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `hasAlco` int DEFAULT NULL,
  `pointsAmount` int DEFAULT NULL,
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menuitems`
--

LOCK TABLES `menuitems` WRITE;
/*!40000 ALTER TABLE `menuitems` DISABLE KEYS */;
INSERT INTO `menuitems` VALUES (1,'Burger',1000,'Imgs\\snack.jpg','food',0,10),(2,'Chips',10,'Imgs\\chips.jpg','drink',0,4),(3,'Lasagna',16,'Imgs\\lasagna.jpg','food',0,3),(4,'Soup',5,'Imgs\\soup.jpg','food',0,7),(5,'Sandwhich',14,'Imgs\\sambo.jpg','food',0,3),(6,'Sausage',1,'Imgs\\sosig.jpg','food',0,7),(7,'Cake',4,'Imgs\\cake.jpg','food',0,9),(8,'Red Sauce',9,'Imgs\\redSauce.jpg','food',0,2),(9,'Pringles',122,'Imgs\\pringles.jpg','food',0,4),(10,'Draft Beer',5,'Imgs\\DraftBeer.jpg','drink',1,3),(11,'Coke',2,'Imgs\\Coke.jpg','drink',0,2),(12,'Salad',4,'Imgs\\Salad.jpg','food',0,2),(13,'PorkChop',6,'Imgs\\Porkchop.jpg','food',0,4),(14,'Spicy Chips',3,'Imgs\\SChips.jpg','food',0,12),(15,'Water',0,'Imgs/water.jpg','drink',0,1),(16,'Asahi',6,'Imgs\\Asahi.jpg','drink',1,5),(17,'Strawberry Daiquiri',8,'Imgs\\strawberryDaiquiri.jpg','drink',1,6),(18,'Fosters',5,'Imgs\\fosters.jpg','drink',1,3),(19,'Mutton',12,'Imgs\\mutton.jpg','food',0,23),(20,'Vodka',6,'Imgs\\vodka.jpg','drink',1,12),(21,'Pepsi',3,'Imgs\\pepsi.jpg','drink',0,2);
/*!40000 ALTER TABLE `menuitems` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-08 19:25:18
