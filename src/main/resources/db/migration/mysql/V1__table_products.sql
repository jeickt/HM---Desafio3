DROP TABLE IF EXISTS `tb_user_roles`;
DROP TABLE IF EXISTS `tb_product`;
DROP TABLE IF EXISTS `tb_category`;
DROP TABLE IF EXISTS `tb_user`;
DROP TABLE IF EXISTS `tb_role`;


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'user@gmail.com','user','User','$2a$10$fW/SZS8PIXZMtsT6wTv9n.cuyWFVeo2CWRk8M6mK5V9WFmRy9IWva'),(2,'admin@gmail.com','admin','Admin','$2a$10$SNytTGkIbTdKvclyjWhvGuD9WtOB3//yq/xiniBqrmIvn5NIXHlWC');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKft1jmfcluls775jqp5142wvl8` (`role_id`),
  CONSTRAINT `FK19t64ocsol5x06fy2cytp7gey` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKft1jmfcluls775jqp5142wvl8` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_roles`
--

LOCK TABLES `tb_user_roles` WRITE;
/*!40000 ALTER TABLE `tb_user_roles` DISABLE KEYS */;
INSERT INTO `tb_user_roles` VALUES (1,1),(2,1),(2,2);
/*!40000 ALTER TABLE `tb_user_roles` ENABLE KEYS */;
UNLOCK TABLES;


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_category`
--

LOCK TABLES `tb_category` WRITE;
/*!40000 ALTER TABLE `tb_category` DISABLE KEYS */;
INSERT INTO `tb_category` VALUES 
(1,'ESCRITÓRIO'),
(2,'ALIMENTAÇÃO E BEBIDAS'),
(3,'VESTUÁRIO'),
(4,'HIGIENE E SAÚDE'),
(5,'MEIO DE TRANSPORTE');
/*!40000 ALTER TABLE `tb_category` ENABLE KEYS */;
UNLOCK TABLES;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_product` (
  `id` varchar(255) NOT NULL,
  `barCode` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `grossValue` double DEFAULT NULL,
  `manufacturingDate` datetime DEFAULT NULL,
  `material` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `series` varchar(255) DEFAULT NULL,
  `taxes` double DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8i0sq9mfbpsrabrm2pum9fspo` (`category_id`),
  CONSTRAINT `FK8i0sq9mfbpsrabrm2pum9fspo` FOREIGN KEY (`category_id`) REFERENCES `tb_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_product`
--

LOCK TABLES `tb_product` WRITE;
/*!40000 ALTER TABLE `tb_product` DISABLE KEYS */;
INSERT INTO `tb_product` VALUES 
('t0n75ytr','945923371680','colorido','Teclado mecânico para jogos com luzes RGB',NULL,120.99,'2016-12-23 00:00:00','plástico','Teclado Gamer',0,'1/2017',35.0,1),
('7t0do00n','629936360072','n/a','Livro sobre padrões de projeto de Erich Gamma, Ralph Johnson, Richard Helm e John Vlissides',NULL,101.11,'2016-05-18 00:00:00','papel','Livro Design Patterns',0,'2/2016',25.0,1),
('oh8u4qy2','651165685459','n/a','Café extra-forte para programadores','2022-11-18 00:00:00',9.45,'2019-11-18 00:00:00','n/a','Café Solúvel South',0,'4/2019',10.0,2),
('am45zbvz','80902033183','preto','Camiseta comemoração 7 anos da South System',NULL,35.21,'2021-01-15 00:00:00','algodão','Camiseta South',0,'1/2021',15.0,3),
('hrcilce1','242717521829','n/a','Cerveja IPA comemoração South System', '2021-08-19 00:00:00',28.17,'2021-02-05 00:00:00','n/a','Cerveja Foguete South',0,'1/2021',12.6,2),
('heqti1q0','553100780294','branco','Máscara de proteção facial modelo PFFS, logo South System',NULL,10.02,'2021-03-16 00:00:00','pff2','Máscara PFF2 South',0,'1/2020',5.0,4),
('iyu2uudy','932205549597','laranja','Patinete movido a bateria com logo South System',NULL,301.11,'2020-02-27 00:00:00','n/a','Patinete Elétrico South',0,'1/2020',20.2,5);
/*!40000 ALTER TABLE `tb_product` ENABLE KEYS */;
UNLOCK TABLES;