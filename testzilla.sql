-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 05, 2015 at 04:34 下午
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `testzilla`
--

-- --------------------------------------------------------

--
-- Table structure for table `tz_users`
--

CREATE TABLE IF NOT EXISTS `tz_users` (
`uid` bigint(20) NOT NULL,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `user_group_id` int(4) NOT NULL,
  `real_name` varchar(32) NOT NULL,
  `email` varchar(64) NOT NULL,
  `country` varchar(24) NOT NULL,
  `province` varchar(24) NOT NULL,
  `city` varchar(24) DEFAULT NULL,
  `phone` varchar(24) NOT NULL,
  `website` varchar(64) DEFAULT NULL,
  `is_individual` tinyint(1) NOT NULL DEFAULT '1',
  `is_email_verified` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_users`
--

INSERT INTO `tz_users` (`uid`, `username`, `password`, `user_group_id`, `real_name`, `email`, `country`, `province`, `city`, `phone`, `website`, `is_individual`, `is_email_verified`) VALUES
(1000, 'Administrator', '785ee107c11dfe36de668b1ae7baacbb', 3, 'Administrator', 'support@testzilla.org', 'China', 'Zhejiang', 'Hangzhou', '+86-571-12345678', 'http://testzilla.org', 0, 1),
(1001, 'zjhzxhz', '785ee107c11dfe36de668b1ae7baacbb', 1, '谢浩哲', 'zjhzxhz@gmail.com', 'China', 'Zhejiang', 'Hangzhou', '+86-15695719136', 'http://zjhzxhz.com', 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tz_users`
--
ALTER TABLE `tz_users`
 ADD PRIMARY KEY (`uid`), ADD UNIQUE KEY `username` (`username`,`email`), ADD KEY `user_group_id` (`user_group_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tz_users`
--
ALTER TABLE `tz_users`
MODIFY `uid` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1002;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tz_users`
--
ALTER TABLE `tz_users`
ADD CONSTRAINT `tz_users_ibfk_1` FOREIGN KEY (`user_group_id`) REFERENCES `tz_user_groups` (`user_group_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
