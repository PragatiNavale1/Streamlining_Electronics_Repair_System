-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: electronic_repair
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `repair_requests`
--

DROP TABLE IF EXISTS `repair_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repair_requests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `description` text,
  `date_requested` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `taken_by` varchar(255) DEFAULT NULL,
  `appointment_date` date DEFAULT NULL,
  `repairman_email` varchar(255) DEFAULT NULL,
  `serial_number` varchar(100) DEFAULT NULL,
  `product_details` text,
  `date_of_purchase` date DEFAULT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `repair_requests_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair_requests`
--

LOCK TABLES `repair_requests` WRITE;
/*!40000 ALTER TABLE `repair_requests` DISABLE KEYS */;
INSERT INTO `repair_requests` VALUES (1,1,'Not working','2025-07-29','taken','Tanmay Kulkarni','2025-07-31','tanmay@gmail.com','1009','Power Adapter','2025-07-21','uploads/1753796984265_adapter.png'),(2,1,'Display Problem','2025-07-30','assigned',NULL,NULL,'tanmay@gmail.com','1101','Computer','2025-07-22','uploads/1753871808169_com_display.jpeg'),(3,6,'On my laptop screen i am getting a blue lines.','2025-07-31','assigned',NULL,NULL,'tanmay@gmail.com','101','Laptop display','2025-07-22','uploads/1753986299084_7862218.jpg'),(4,6,'On my laptop screen i am getting a blue lines.','2025-07-31','assigned',NULL,NULL,'repairman1@fixithub.com','101','Laptop display','2025-07-22','uploads/1753986529111_7862218.jpg'),(5,5,'On my mobile screen i am getting a blue lines.','2025-08-01','assigned',NULL,NULL,'tanmay@gmail.com','121','mobile display','2025-08-03','uploads/1753987495366_7862218.jpg'),(6,6,'I am getting noise from washing machine','2025-08-03','assigned',NULL,NULL,'repairman1@fixithub.com','45','Washing machine ','2025-07-29','uploads/1754202707779_7862218.jpg'),(7,6,'phone display','2025-08-03','assigned',NULL,NULL,'repairman1@fixithub.com','32','Phone','2025-07-27','uploads/1754218739810_7862218.jpg'),(8,5,'Mobile battery','2025-08-03','assigned',NULL,NULL,'repairman1@fixithub.com','5','mobile battery','2025-06-11','uploads/1754220647254_7862218.jpg');
/*!40000 ALTER TABLE `repair_requests` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-03 17:08:43
