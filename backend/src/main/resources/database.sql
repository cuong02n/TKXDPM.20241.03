-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.36 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for aims
CREATE DATABASE IF NOT EXISTS `aims` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `aims`;

-- Dumping structure for table aims.favorite_product_user
CREATE TABLE IF NOT EXISTS `favorite_product_user` (
                                                       `created_time` datetime(6) DEFAULT NULL,
    `updated_time` datetime(6) DEFAULT NULL,
    `product_id` bigint NOT NULL,
    `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`product_id`,`user_email`),
    KEY `FK4g5ro7l61ny5mgta47yyua6m9` (`user_email`),
    CONSTRAINT `FK4g5ro7l61ny5mgta47yyua6m9` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`),
    CONSTRAINT `FK5naqqahk6kxmm3cjynn24ounm` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.favorite_product_user: ~5 rows (approximately)
DELETE FROM `favorite_product_user`;
INSERT INTO `favorite_product_user` (`created_time`, `updated_time`, `product_id`, `user_email`) VALUES
                                                                                                     ('2024-12-19 23:50:17.000000', '2024-12-19 23:50:17.000000', 1, 'cuong02n@gmail.com'),
                                                                                                     ('2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', 1, 'john.doe@example.com'),
                                                                                                     ('2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', 2, 'alice.smith@example.com'),
                                                                                                     ('2024-12-21 23:07:29.240857', '2024-12-21 23:07:29.240857', 2, 'cuong02n@gmail.com'),
                                                                                                     ('2024-12-21 23:10:43.320134', '2024-12-21 23:10:43.320134', 3, 'cuong02n@gmail.com');

-- Dumping structure for table aims.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
                                         `order_id` bigint NOT NULL,
                                         `created_time` datetime(6) DEFAULT NULL,
    `updated_time` datetime(6) DEFAULT NULL,
    `is_paid` bit(1) NOT NULL,
    `shipping_fee` bigint NOT NULL,
    `total_amount_include_shipping_fee` bigint NOT NULL,
    `total_amount_includevat` bigint NOT NULL,
    `total_amount_withoutvat` bigint NOT NULL,
    PRIMARY KEY (`order_id`),
    CONSTRAINT `FKr27vrfyll0shs80upv1rmctie` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.invoice: ~0 rows (approximately)
DELETE FROM `invoice`;

-- Dumping structure for table aims.order
CREATE TABLE IF NOT EXISTS `order` (
                                       `order_id` bigint NOT NULL AUTO_INCREMENT,
                                       `created_time` datetime(6) DEFAULT NULL,
    `updated_time` datetime(6) DEFAULT NULL,
    `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `is_rush` bit(1) NOT NULL,
    `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `shipping_instruction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `time_in_minute` int NOT NULL,
    `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`order_id`),
    KEY `FKcpl0mjoeqhxvgeeeq5piwpd3i` (`user_email`) USING BTREE,
    CONSTRAINT `FKcpl0mjoeqhxvgeeeq5piwpd3i` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
    ) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.order: ~2 rows (approximately)
DELETE FROM `order`;
INSERT INTO `order` (`order_id`, `created_time`, `updated_time`, `address`, `is_rush`, `phone`, `province`, `shipping_instruction`, `time_in_minute`, `user_email`) VALUES
                                                                                                                                                                        (1, '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', '123 Main St, City, Country', b'0', '123-456-7890', 'Province A', 'Leave at door', 30, 'john.doe@example.com'),
                                                                                                                                                                        (2, '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', '456 Side St, Town, Country', b'1', '098-765-4321', 'Province B', 'Call upon arrival', 45, 'alice.smith@example.com');

-- Dumping structure for table aims.order_product
CREATE TABLE IF NOT EXISTS `order_product` (
                                               `quantity` int NOT NULL,
                                               `order_id` bigint NOT NULL,
                                               `product_id` bigint NOT NULL,
                                               `created_time` datetime(6) DEFAULT NULL,
    `updated_time` datetime(6) DEFAULT NULL,
    `is_rush` bit(1) NOT NULL,
    PRIMARY KEY (`order_id`,`product_id`),
    KEY `FKhnfgqyjx3i80qoymrssls3kno` (`product_id`),
    CONSTRAINT `FKhnfgqyjx3i80qoymrssls3kno` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `FKm6igrp4lwucj1me05axmv885c` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.order_product: ~0 rows (approximately)
DELETE FROM `order_product`;

-- Dumping structure for table aims.product
CREATE TABLE IF NOT EXISTS `product` (
                                         `id` bigint NOT NULL AUTO_INCREMENT,
                                         `created_time` datetime(6) DEFAULT NULL,
    `updated_time` datetime(6) DEFAULT NULL,
    `additional_data` json DEFAULT NULL,
    `available` int NOT NULL,
    `category` enum('BOOK','CD','DVD') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `price` int NOT NULL,
    `weight` double NOT NULL,
    `is_supported_rush` bit(1) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.product: ~3 rows (approximately)
DELETE FROM `product`;
INSERT INTO `product` (`id`, `created_time`, `updated_time`, `additional_data`, `available`, `category`, `description`, `name`, `price`, `weight`, `is_supported_rush`) VALUES
                                                                                                                                                                            (1, '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', '{"size": "M", "color": "red"}', 100, 'BOOK', 'A thrilling mystery novel', 'Mystery Book', 15000, 0, b'0'),
                                                                                                                                                                            (2, '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', '{"size": "L", "color": "blue"}', 50, 'CD', 'Pop music album', 'Pop Hits 2024', 20000, 0, b'1'),
                                                                                                                                                                            (3, '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', '{"size": "XL", "color": "black"}', 200, 'DVD', 'Action movie DVD', 'Action Movie', 25000, 0, b'0');

-- Dumping structure for table aims.product_cart
CREATE TABLE IF NOT EXISTS `product_cart` (
                                              `created_time` datetime(6) DEFAULT NULL,
    `updated_time` datetime(6) DEFAULT NULL,
    `quantity` int NOT NULL,
    `product_id` bigint NOT NULL,
    `user_email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `is_rush` int DEFAULT NULL,
    PRIMARY KEY (`product_id`,`user_email`),
    KEY `FKibe7bwwc2pyukh9pm7mbrcp7y` (`user_email`),
    CONSTRAINT `FKhpnrxdy3jhujameyod08ilvvw` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `FKibe7bwwc2pyukh9pm7mbrcp7y` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.product_cart: ~8 rows (approximately)
DELETE FROM `product_cart`;
INSERT INTO `product_cart` (`created_time`, `updated_time`, `quantity`, `product_id`, `user_email`, `is_rush`) VALUES
                                                                                                                   ('2024-12-21 23:07:40.579670', '2024-12-21 23:07:40.579670', 1, 1, 'cuong02n@gmail.com', NULL),
                                                                                                                   ('2024-12-30 17:03:07.409800', '2024-12-30 17:03:07.409800', 1, 1, 'guest@guest.com', NULL),
                                                                                                                   ('2024-12-20 09:33:14.178395', '2024-12-20 09:33:14.178395', 1, 1, 'sveta311069@bankinnepal.com', NULL),
                                                                                                                   ('2024-12-21 23:07:40.579670', '2024-12-21 23:07:40.579670', 1, 2, 'cuong02n@gmail.com', NULL),
                                                                                                                   ('2024-12-30 16:17:24.002908', '2024-12-30 16:17:24.002908', 1, 2, 'guest@guest.com', NULL),
                                                                                                                   ('2024-12-20 09:42:24.955568', '2024-12-20 09:42:24.955568', 6, 2, 'sveta311069@bankinnepal.com', NULL),
                                                                                                                   ('2024-12-21 23:07:40.579670', '2024-12-21 23:07:40.579670', 1, 3, 'cuong02n@gmail.com', NULL),
                                                                                                                   ('2024-12-30 16:17:30.725814', '2024-12-30 16:17:30.725814', 1, 3, 'guest@guest.com', NULL),
                                                                                                                   ('2024-12-20 09:45:28.831874', '2024-12-20 09:45:28.831874', 2, 3, 'sveta311069@bankinnepal.com', NULL);

-- Dumping structure for table aims.product_media_urls
CREATE TABLE IF NOT EXISTS `product_media_urls` (
                                                    `product_id` bigint NOT NULL,
                                                    `media_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    KEY `FK23mquixq35vce83glx1ofyomw` (`product_id`),
    CONSTRAINT `FK23mquixq35vce83glx1ofyomw` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.product_media_urls: ~12 rows (approximately)
DELETE FROM `product_media_urls`;
INSERT INTO `product_media_urls` (`product_id`, `media_url`) VALUES
                                                                 (1, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-1-1.jpg.webp'),
                                                                 (2, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-5-2-1.jpg.webp'),
                                                                 (3, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-9-1.jpg.webp'),
                                                                 (1, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-2-1.jpg.webp'),
                                                                 (1, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-3-1.jpg.webp'),
                                                                 (1, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau.jpg.webp'),
                                                                 (2, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-6-1.jpg.webp'),
                                                                 (2, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-8-1.jpg.webp'),
                                                                 (2, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-84-1.jpg.webp'),
                                                                 (3, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-10-1.jpg.webp'),
                                                                 (3, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-11-1.jpg.webp'),
                                                                 (3, 'https://hoanghamobile.com/tin-tuc/wp-content/webp-express/webp-images/uploads/2024/05/anh-trai-xau-14-1.jpg.webp');

-- Dumping structure for table aims.review
CREATE TABLE IF NOT EXISTS `review` (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `created_time` datetime(6) DEFAULT NULL,
    `updated_time` datetime(6) DEFAULT NULL,
    `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `star` int NOT NULL,
    `product_id` bigint DEFAULT NULL,
    `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKiyof1sindb9qiqr9o8npj8klt` (`product_id`),
    KEY `FK6hqpdfb0b6wduxedrygq6hv32` (`user_email`),
    CONSTRAINT `FK6hqpdfb0b6wduxedrygq6hv32` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`),
    CONSTRAINT `FKiyof1sindb9qiqr9o8npj8klt` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.review: ~2 rows (approximately)
DELETE FROM `review`;
INSERT INTO `review` (`id`, `created_time`, `updated_time`, `content`, `star`, `product_id`, `user_email`) VALUES
                                                                                                               (1, '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', 'Great book, loved the mystery!', 5, 1, 'john.doe@example.com'),
                                                                                                               (2, '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', 'The CD was okay, but not great.', 3, 2, 'alice.smith@example.com');

-- Dumping structure for table aims.review_media_urls
CREATE TABLE IF NOT EXISTS `review_media_urls` (
                                                   `review_id` bigint NOT NULL,
                                                   `media_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    KEY `FKpsmh0fah3dfl3seej7d8hpkbt` (`review_id`),
    CONSTRAINT `FKpsmh0fah3dfl3seej7d8hpkbt` FOREIGN KEY (`review_id`) REFERENCES `review` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.review_media_urls: ~2 rows (approximately)
DELETE FROM `review_media_urls`;
INSERT INTO `review_media_urls` (`review_id`, `media_url`) VALUES
                                                               (1, 'https://example.com/review_media_1.jpg'),
                                                               (2, 'https://example.com/review_media_2.jpg');

-- Dumping structure for table aims.transaction_info
CREATE TABLE IF NOT EXISTS `transaction_info` (
                                                  `invoice_id` bigint NOT NULL,
                                                  `created_time` datetime(6) DEFAULT NULL,
    `updated_time` datetime(6) DEFAULT NULL,
    `transaction_id` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `transaction_message` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `transaction_time` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`invoice_id`),
    CONSTRAINT `FKq5i7p9l0qnjjfms1iy1d16aq` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`order_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.transaction_info: ~0 rows (approximately)
DELETE FROM `transaction_info`;

-- Dumping structure for table aims.user
CREATE TABLE IF NOT EXISTS `user` (
                                      `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `created_time` datetime(6) DEFAULT NULL,
    `updated_time` datetime(6) DEFAULT NULL,
    `active` bit(1) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `role` enum('ADMIN','CUSTOMER','PRODUCT_MANAGER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`email`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.user: ~6 rows (approximately)
DELETE FROM `user`;
INSERT INTO `user` (`email`, `created_time`, `updated_time`, `active`, `name`, `password`, `role`) VALUES
                                                                                                       ('admin@example.com', '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', b'1', 'Admin', 'adminpassword', 'ADMIN'),
                                                                                                       ('alice.smith@example.com', '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', b'1', 'Alice Smith', 'alicepassword', 'PRODUCT_MANAGER'),
                                                                                                       ('cuong02n@gmail.com', '2024-12-19 18:41:49.390647', '2024-12-19 18:41:49.390647', b'1', 'nguyenmanhcuong', '$2a$10$oJ.swwXTYwLJk8msQfgNYOhvqG9Pgck0UrVvyK/QHPRNVAmlBuHyi', 'CUSTOMER'),
                                                                                                       ('guest@guest.com', '2024-12-30 16:10:20.000000', '2024-12-30 16:10:20.000000', b'1', 'Guest', 'a_very_strong_password', 'CUSTOMER'),
                                                                                                       ('john.doe@example.com', '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000', b'1', 'John Doe', 'password123', 'CUSTOMER'),
                                                                                                       ('matt25310@thaitudang.xyz', '2024-12-21 22:56:13.730397', '2024-12-21 22:56:13.730397', b'1', 'meoVch1', '$2a$10$6sRvwBJrttc7YmBiXD7ckeCNsSRlRZ7HuyTRCuntZoy/07abJWzI.', 'CUSTOMER'),
                                                                                                       ('sveta311069@bankinnepal.com', '2024-12-20 09:27:40.037736', '2024-12-20 09:30:32.080777', b'1', 'meoVch1', '$2a$10$A6oii4o9ewmWSOkSENMAmertGz3AfWu3c7Ie2JwZPk9pu2eEjVCF.', 'CUSTOMER');

-- Dumping structure for table aims.user_cart
CREATE TABLE IF NOT EXISTS `user_cart` (
                                           `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `created_time` datetime(6) DEFAULT NULL,
    `updated_time` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`user_email`),
    CONSTRAINT `FKn8gfn4fx5uag77lvx22j40fap` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table aims.user_cart: ~2 rows (approximately)
DELETE FROM `user_cart`;
INSERT INTO `user_cart` (`user_email`, `created_time`, `updated_time`) VALUES
                                                                           ('alice.smith@example.com', '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000'),
                                                                           ('john.doe@example.com', '2024-12-17 13:39:02.000000', '2024-12-17 13:39:02.000000');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
