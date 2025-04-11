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
-- Table structure for table `menuitemtranslations`
--

DROP TABLE IF EXISTS `menuitemtranslations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menuitemtranslations` (
  `itemId` int NOT NULL,
  `locale` varchar(10) NOT NULL,
  `itemName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`itemId`,`locale`),
  CONSTRAINT `menuitemtranslations_ibfk_1` FOREIGN KEY (`itemId`) REFERENCES `menuitems` (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menuitemtranslations`
--

LOCK TABLES `menuitemtranslations` WRITE;
/*!40000 ALTER TABLE `menuitemtranslations` DISABLE KEYS */;
INSERT INTO `menuitemtranslations` VALUES (1,'de','Burger'),(1,'en','Hamburger'),(1,'ie','Burgar'),(1,'ja','ハンバーガー'),(1,'pl','Burger'),(1,'ru','Бургер'),(2,'de','Pommes'),(2,'en','Chips'),(2,'ie','fries prátaí'),(2,'ja','ポテトフライ'),(2,'pl','Frytki'),(2,'ru','Картошка фри'),(3,'de','Lasagne'),(3,'en','Lasagna'),(3,'ie','Leasáine'),(3,'ja','ラザニア'),(3,'pl','Lazania'),(3,'ru','Лазанья'),(4,'de','Suppe'),(4,'en','Soup'),(4,'ie','Anraith'),(4,'ja','スープ'),(4,'pl','Zupa'),(4,'ru','Суп'),(5,'de','Sandwich'),(5,'en','Sandwich'),(5,'ie','Ceapaire'),(5,'ja','サンドイッチ'),(5,'pl','Kanapka'),(5,'ru','Бутерброд'),(6,'de','Wurst'),(6,'en','Sausage'),(6,'ie','Isbeag'),(6,'ja','ソーセージ'),(6,'pl','Kiełbasa'),(6,'ru','Сосиска'),(7,'de','Kuchen'),(7,'en','Cake'),(7,'ie','Císte'),(7,'ja','ケーキ'),(7,'pl','Ciasto'),(7,'ru','Торт'),(8,'de','Rote Soße'),(8,'en','Red Sauce'),(8,'ie','Anlann Dearg'),(8,'ja','レッドソース'),(8,'pl','Czerwony sos'),(8,'ru','Красный соус'),(9,'de','Pringles'),(9,'en','Pringles'),(9,'ie','Pringles'),(9,'ja','プリングルズ'),(9,'pl','Pringles'),(9,'ru','Принглс'),(10,'de','Fassbier'),(10,'en','Draft Beer'),(10,'ie','Beoir Dréachta'),(10,'ja','生ビール'),(10,'pl','Piwo z beczki'),(10,'ru','Разливное пиво'),(11,'de','Cola'),(11,'en','Coke'),(11,'ie','Cóc'),(11,'ja','コーク'),(11,'pl','Cola'),(11,'ru','Кола'),(12,'de','Salat'),(12,'en','Salad'),(12,'ie','Sailéad'),(12,'ja','サラダ'),(12,'pl','Sałatka'),(12,'ru','Салат'),(13,'de','Schweinekotelett'),(13,'en','PorkChop'),(13,'ie','Sop Muice'),(13,'ja','ポークチョップ'),(13,'pl','Kotlet wieprzowy'),(13,'ru','Свиная отбивная'),(14,'de','Würzige Pommes'),(14,'en','Spicy Chips'),(14,'ie','Slisíní Spíosracha'),(14,'ja','スパイシーチップス'),(14,'pl','Pikantne frytki'),(14,'ru','Острые чипсы'),(15,'de','Wasser'),(15,'en','Water'),(15,'ie','Uisce'),(15,'ja','水'),(15,'pl','Woda'),(15,'ru','Вода'),(16,'de','Asahi'),(16,'en','Asahi'),(16,'ie','Asahi'),(16,'ja','アサヒ'),(16,'pl','Asahi'),(16,'ru','Асахи'),(17,'de','Erdbeer-Daiquiri'),(17,'en','Strawberry Daiquiri'),(17,'ie','Daiquiri Sútha Talún'),(17,'ja','ストロベリーダイキリ'),(17,'pl','Truskawkowy Daiquiri'),(17,'ru','Клубничный дайкири'),(18,'de','Fosters'),(18,'en','Fosters'),(18,'ie','Fosters'),(18,'ja','フォスターズ'),(18,'pl','Fosters'),(18,'ru','Фостерс'),(19,'de','Hammelfleisch'),(19,'en','Mutton'),(19,'ie','Caoireoil'),(19,'ja','マトン'),(19,'pl','Baranina'),(19,'ru','Баранина'),(20,'de','Wodka'),(20,'en','Vodka'),(20,'ie','Vodca'),(20,'ja','ウォッカ'),(20,'pl','Wódka'),(20,'ru','Водка'),(21,'de','Pepsi'),(21,'en','Pepsi'),(21,'ie','Pepsi'),(21,'ja','ペプシ'),(21,'pl','Pepsi'),(21,'ru','Пепси');
/*!40000 ALTER TABLE `menuitemtranslations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-11  3:23:59
