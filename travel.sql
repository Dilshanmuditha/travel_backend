-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: agency
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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `pick_location` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `vehicle_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2025-03-23 10:49:48.403000',1,'2025-03-25 00:00:00.000000','Panadura','2025-03-24 00:00:00.000000',100,1),(2,'2025-03-23 10:49:56.259000',1,'2025-03-27 00:00:00.000000','Panadura','2025-03-24 00:00:00.000000',300,1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2025-03-23 09:40:47.373000','dilshan@gmail.com','123123123','Muditha','123123123','admin'),(3,'2025-03-23 09:48:50.234000','dilshan1@gmail.com','123123123','Muditha','123123123','customer');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text,
  `driver_email` varchar(255) DEFAULT NULL,
  `driver_mobile` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `price_perday` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
INSERT INTO `vehicles` VALUES (1,'Toyota','Car','2025-03-23 00:24:11.522000','abc abc abc','dilshan@gmail.com','123123123','1742669651522_award2.jpg','123123123',100),(2,'Toyota','Jeep','2025-03-23 14:16:39.588000','abc abc abc','dilshan1@gmail.com','123123123','1742719599588_award2.jpg','123123123',200),(3,'Nissan','suv','2025-03-23 14:17:00.465000','abc abc abc','dilshan2@gmail.com','123123123','1742719620465_award2.jpg','123123123',300),(4,'Nissan','Car','2025-03-23 14:17:15.100000','abc abc abc','dilshan3@gmail.com','123123123','1742719635100_award2.jpg','123123123',150),(5,'Nissan','Van','2025-03-23 14:19:41.098000','abc abc abc','dilshan4@gmail.com','123123123','1742719781098_IMG_20180331_090944.jpg','123123123',200);
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-23 23:40:26
