-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2015-02-05 12:14:00
-- 服务器版本： 5.6.21
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

DELIMITER $$
--
-- 存储过程
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `test_multi_sets`()
    DETERMINISTIC
begin
        select user() as first_col;
        select user() as first_col, now() as second_col;
        select user() as first_col, now() as second_col, now() as third_col;
        end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- 表的结构 `tz_bugs`
--

CREATE TABLE IF NOT EXISTS `tz_bugs` (
`bug_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `product_version` varchar(24) NOT NULL,
  `bug_category_id` int(4) NOT NULL,
  `bug_status_id` int(4) NOT NULL,
  `bug_severity_id` int(4) NOT NULL,
  `bug_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `bug_hunter_id` bigint(20) NOT NULL,
  `bug_title` varchar(64) NOT NULL,
  `bug_description` text NOT NULL,
  `bug_screenshots` text
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tz_bugs`
--

INSERT INTO `tz_bugs` (`bug_id`, `product_id`, `product_version`, `bug_category_id`, `bug_status_id`, `bug_severity_id`, `bug_create_time`, `bug_hunter_id`, `bug_title`, `bug_description`, `bug_screenshots`) VALUES
(1000, 1000, '1.0 Beta', 5, 4, 3, '2015-01-22 08:05:00', 1001, 'Bug #1000', '# Marked in browser\\n\\nRendered by **marked**.', NULL),
(1001, 1000, '1.0 Beta', 12, 1, 2, '2015-02-03 08:05:00', 1000, 'Bug #1001', 'This is the *first* editor.\r\n------------------------------\r\n\r\nJust plain **Markdown**, except that the input is sanitized:\r\n\r\nand that it implements "fenced blockquotes" via a plugin:\r\n\r\n"""\r\nDo it like this:\r\n\r\n1. Have idea.\r\n2. ???\r\n3. Profit!\r\n"""', NULL),
(1002, 1001, '1.0 Beta', 2, 1, 1, '2015-02-03 12:11:20', 1001, 'Bug #1002', '# Bug #1002 DescriptionSome Text Here...', '');

-- --------------------------------------------------------

--
-- 表的结构 `tz_bug_categories`
--

CREATE TABLE IF NOT EXISTS `tz_bug_categories` (
`bug_category_id` int(4) NOT NULL,
  `bug_category_slug` varchar(24) NOT NULL,
  `bug_category_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tz_bug_categories`
--

INSERT INTO `tz_bug_categories` (`bug_category_id`, `bug_category_slug`, `bug_category_name`) VALUES
(1, 'crashes', 'Application Crashes'),
(2, 'exceptions', 'Exceptions'),
(3, 'functional', 'Functional Errors'),
(4, 'file-io', 'File I/O'),
(5, 'gui', 'Graphical User Interface'),
(6, 'database', 'Database'),
(7, 'network', 'Network'),
(8, 'optimization', 'Optimization'),
(9, 'portability', 'Portability'),
(10, 'security', 'Security'),
(11, 'threads', 'Threads Synchronization'),
(12, 'others', 'Others');

-- --------------------------------------------------------

--
-- 表的结构 `tz_bug_severities`
--

CREATE TABLE IF NOT EXISTS `tz_bug_severities` (
`bug_severity_id` int(4) NOT NULL,
  `bug_severity_slug` varchar(24) NOT NULL,
  `bug_severity_name` varchar(24) NOT NULL,
  `bug_severity_description` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tz_bug_severities`
--

INSERT INTO `tz_bug_severities` (`bug_severity_id`, `bug_severity_slug`, `bug_severity_name`, `bug_severity_description`) VALUES
(1, 'critical', 'Critical', 'The defect affects critical functionality or critical data. It does not have a workaround. Example: Unsuccessful installation, complete failure of a feature.'),
(2, 'major', 'Major', 'The defect affects major functionality or major data. It has a workaround but is not obvious and is difficult. Example: A feature is not functional from one module but the task is doable if 10 complicated indirect steps are followed in another module/s.'),
(3, 'minor', 'Minor', 'The defect affects minor functionality or non-critical data. It has an easy workaround. Example: A minor feature that is not functional in one module but the same task is easily doable from another module.'),
(4, 'trivial', 'Trivial', 'The defect does not affect functionality or data. It does not even need a workaround. It does not impact productivity or efficiency. It is merely an inconvenience. Example: Petty layout discrepancies, spelling/grammatical errors.');

-- --------------------------------------------------------

--
-- 表的结构 `tz_bug_status`
--

CREATE TABLE IF NOT EXISTS `tz_bug_status` (
`bug_status_id` int(4) NOT NULL,
  `bug_status_slug` varchar(24) NOT NULL,
  `bug_status_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tz_bug_status`
--

INSERT INTO `tz_bug_status` (`bug_status_id`, `bug_status_slug`, `bug_status_name`) VALUES
(1, 'unconfirmed', 'Unconfirmed'),
(2, 'confirmed', 'Confirmed'),
(3, 'fixed', 'Fixed'),
(4, 'nvalid', 'Invalid'),
(5, 'wontfix', 'Won''t Fix'),
(6, 'later', 'Fix Later'),
(7, 'duplicate', 'Duplicate'),
(8, 'worksforme', 'Can''t Reappear'),
(9, 'enhancement', 'Enhancement');

-- --------------------------------------------------------

--
-- 表的结构 `tz_mail_verification`
--

CREATE TABLE IF NOT EXISTS `tz_mail_verification` (
  `email` varchar(64) NOT NULL,
  `code` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tz_mail_verification`
--

INSERT INTO `tz_mail_verification` (`email`, `code`) VALUES
('zjhzxhz@qq.com', '3d6f91cd-2868-44ee-b907-df87146a512a');

-- --------------------------------------------------------

--
-- 表的结构 `tz_options`
--

CREATE TABLE IF NOT EXISTS `tz_options` (
  `option_id` int(8) NOT NULL,
  `option_key` varchar(32) NOT NULL,
  `option_value` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `tz_points_logs`
--

CREATE TABLE IF NOT EXISTS `tz_points_logs` (
`points_log_id` bigint(20) NOT NULL,
  `points_to_uid` bigint(20) NOT NULL,
  `points_get_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `points_rule_id` int(4) NOT NULL,
  `points_meta` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `tz_points_rules`
--

CREATE TABLE IF NOT EXISTS `tz_points_rules` (
`points_rule_id` int(4) NOT NULL,
  `points_rule_reputation` int(4) NOT NULL,
  `points_rule_credits` int(4) NOT NULL,
  `points_rule_title` varchar(32) NOT NULL,
  `points_rule_description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `tz_products`
--

CREATE TABLE IF NOT EXISTS `tz_products` (
`product_id` bigint(20) NOT NULL,
  `product_name` varchar(32) NOT NULL,
  `product_logo` varchar(128) NOT NULL,
  `product_category_id` int(4) NOT NULL,
  `product_latest_version` varchar(24) NOT NULL,
  `product_developer_id` bigint(20) NOT NULL,
  `product_prerequisites` varchar(128) NOT NULL,
  `product_url` varchar(256) NOT NULL,
  `product_description` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tz_products`
--

INSERT INTO `tz_products` (`product_id`, `product_name`, `product_logo`, `product_category_id`, `product_latest_version`, `product_developer_id`, `product_prerequisites`, `product_url`, `product_description`) VALUES
(1000, 'TestZilla', 'http://www.testzilla.org/assets/img/logo.png', 1, '1.0 Beta', 1000, 'IE 10+, Firefox, Chrome or Safari', 'http://www.testzilla.org/', 'TestZilla Description'),
(1001, 'IT Training Platform', 'http://itp.zjhzxhz.com/img/logo.png', 1, '1.0 Alpha', 1001, 'IE 7+, Firefox, Chrome or Safari', 'http://www.itraining.com/', 'IT Training Platform Description');

-- --------------------------------------------------------

--
-- 表的结构 `tz_product_categories`
--

CREATE TABLE IF NOT EXISTS `tz_product_categories` (
`product_category_id` int(4) NOT NULL,
  `product_category_slug` varchar(24) NOT NULL,
  `product_category_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tz_product_categories`
--

INSERT INTO `tz_product_categories` (`product_category_id`, `product_category_slug`, `product_category_name`) VALUES
(1, 'web', 'Web Application'),
(2, 'ios', 'iOS Application'),
(3, 'android', 'Android Application'),
(4, 'windows-phone', 'WindowsPhone Application'),
(5, 'windows', 'Windows Application'),
(6, 'mac', 'Mac Application'),
(7, 'others', 'Others');

-- --------------------------------------------------------

--
-- 表的结构 `tz_product_testers`
--

CREATE TABLE IF NOT EXISTS `tz_product_testers` (
  `product_id` bigint(20) NOT NULL,
  `tester_uid` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tz_product_testers`
--

INSERT INTO `tz_product_testers` (`product_id`, `tester_uid`) VALUES
(1000, 1000),
(1000, 1001),
(1001, 1001);

-- --------------------------------------------------------

--
-- 表的结构 `tz_users`
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
-- 转存表中的数据 `tz_users`
--

INSERT INTO `tz_users` (`uid`, `username`, `password`, `user_group_id`, `real_name`, `email`, `country`, `province`, `city`, `phone`, `website`, `is_individual`, `is_email_verified`) VALUES
(1000, 'Administrator', '785ee107c11dfe36de668b1ae7baacbb', 3, 'Administrator', 'support@testzilla.org', 'China', 'Zhejiang', 'Hangzhou', '+86-571-12345678', 'http://testzilla.org', 0, 1),
(1001, 'zjhzxhz', '785ee107c11dfe36de668b1ae7baacbb', 2, '谢浩哲', 'zjhzxhz@gmail.com', 'China', 'Zhejiang', 'Hangzhou', '+86-15695719136', 'http://zjhzxhz.com', 1, 0);

-- --------------------------------------------------------

--
-- 表的结构 `tz_user_groups`
--

CREATE TABLE IF NOT EXISTS `tz_user_groups` (
`user_group_id` int(4) NOT NULL,
  `user_group_slug` varchar(24) NOT NULL,
  `user_group_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tz_user_groups`
--

INSERT INTO `tz_user_groups` (`user_group_id`, `user_group_slug`, `user_group_name`) VALUES
(1, 'tester', 'Tester'),
(2, 'developer', 'Developer'),
(3, 'administrator', 'Administrator');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tz_bugs`
--
ALTER TABLE `tz_bugs`
 ADD PRIMARY KEY (`bug_id`), ADD KEY `product_id` (`product_id`), ADD KEY `bug_category_id` (`bug_category_id`,`bug_status_id`), ADD KEY `product_id_2` (`product_id`), ADD KEY `bug_status_id` (`bug_status_id`), ADD KEY `bug_hunter_id` (`bug_hunter_id`), ADD KEY `bug_severity_id` (`bug_severity_id`);

--
-- Indexes for table `tz_bug_categories`
--
ALTER TABLE `tz_bug_categories`
 ADD PRIMARY KEY (`bug_category_id`);

--
-- Indexes for table `tz_bug_severities`
--
ALTER TABLE `tz_bug_severities`
 ADD PRIMARY KEY (`bug_severity_id`);

--
-- Indexes for table `tz_bug_status`
--
ALTER TABLE `tz_bug_status`
 ADD PRIMARY KEY (`bug_status_id`);

--
-- Indexes for table `tz_mail_verification`
--
ALTER TABLE `tz_mail_verification`
 ADD PRIMARY KEY (`email`);

--
-- Indexes for table `tz_points_logs`
--
ALTER TABLE `tz_points_logs`
 ADD PRIMARY KEY (`points_log_id`), ADD KEY `points_uid` (`points_to_uid`,`points_rule_id`), ADD KEY `points_rule_id` (`points_rule_id`);

--
-- Indexes for table `tz_points_rules`
--
ALTER TABLE `tz_points_rules`
 ADD PRIMARY KEY (`points_rule_id`);

--
-- Indexes for table `tz_products`
--
ALTER TABLE `tz_products`
 ADD PRIMARY KEY (`product_id`), ADD KEY `product_developer_id` (`product_developer_id`), ADD KEY `product_category_id` (`product_category_id`);

--
-- Indexes for table `tz_product_categories`
--
ALTER TABLE `tz_product_categories`
 ADD PRIMARY KEY (`product_category_id`);

--
-- Indexes for table `tz_product_testers`
--
ALTER TABLE `tz_product_testers`
 ADD PRIMARY KEY (`product_id`,`tester_uid`), ADD KEY `tester_uid` (`tester_uid`);

--
-- Indexes for table `tz_users`
--
ALTER TABLE `tz_users`
 ADD PRIMARY KEY (`uid`), ADD UNIQUE KEY `username` (`username`), ADD UNIQUE KEY `email` (`email`), ADD KEY `tz_users_ibfk_1` (`user_group_id`);

--
-- Indexes for table `tz_user_groups`
--
ALTER TABLE `tz_user_groups`
 ADD PRIMARY KEY (`user_group_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tz_bugs`
--
ALTER TABLE `tz_bugs`
MODIFY `bug_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1003;
--
-- AUTO_INCREMENT for table `tz_bug_categories`
--
ALTER TABLE `tz_bug_categories`
MODIFY `bug_category_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `tz_bug_severities`
--
ALTER TABLE `tz_bug_severities`
MODIFY `bug_severity_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tz_bug_status`
--
ALTER TABLE `tz_bug_status`
MODIFY `bug_status_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `tz_points_logs`
--
ALTER TABLE `tz_points_logs`
MODIFY `points_log_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tz_points_rules`
--
ALTER TABLE `tz_points_rules`
MODIFY `points_rule_id` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tz_products`
--
ALTER TABLE `tz_products`
MODIFY `product_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1002;
--
-- AUTO_INCREMENT for table `tz_product_categories`
--
ALTER TABLE `tz_product_categories`
MODIFY `product_category_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `tz_users`
--
ALTER TABLE `tz_users`
MODIFY `uid` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1002;
--
-- AUTO_INCREMENT for table `tz_user_groups`
--
ALTER TABLE `tz_user_groups`
MODIFY `user_group_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- 限制导出的表
--

--
-- 限制表 `tz_bugs`
--
ALTER TABLE `tz_bugs`
ADD CONSTRAINT `tz_bugs_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `tz_products` (`product_id`),
ADD CONSTRAINT `tz_bugs_ibfk_2` FOREIGN KEY (`bug_category_id`) REFERENCES `tz_bug_categories` (`bug_category_id`),
ADD CONSTRAINT `tz_bugs_ibfk_3` FOREIGN KEY (`bug_status_id`) REFERENCES `tz_bug_status` (`bug_status_id`),
ADD CONSTRAINT `tz_bugs_ibfk_4` FOREIGN KEY (`bug_severity_id`) REFERENCES `tz_bug_severities` (`bug_severity_id`),
ADD CONSTRAINT `tz_bugs_ibfk_5` FOREIGN KEY (`bug_hunter_id`) REFERENCES `tz_users` (`uid`);

--
-- 限制表 `tz_points_logs`
--
ALTER TABLE `tz_points_logs`
ADD CONSTRAINT `tz_points_logs_ibfk_1` FOREIGN KEY (`points_to_uid`) REFERENCES `tz_users` (`uid`),
ADD CONSTRAINT `tz_points_logs_ibfk_2` FOREIGN KEY (`points_rule_id`) REFERENCES `tz_points_rules` (`points_rule_id`);

--
-- 限制表 `tz_products`
--
ALTER TABLE `tz_products`
ADD CONSTRAINT `tz_products_ibfk_1` FOREIGN KEY (`product_category_id`) REFERENCES `tz_product_categories` (`product_category_id`),
ADD CONSTRAINT `tz_products_ibfk_2` FOREIGN KEY (`product_developer_id`) REFERENCES `tz_users` (`uid`);

--
-- 限制表 `tz_product_testers`
--
ALTER TABLE `tz_product_testers`
ADD CONSTRAINT `tz_product_testers_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `tz_products` (`product_id`),
ADD CONSTRAINT `tz_product_testers_ibfk_2` FOREIGN KEY (`tester_uid`) REFERENCES `tz_users` (`uid`);

--
-- 限制表 `tz_users`
--
ALTER TABLE `tz_users`
ADD CONSTRAINT `tz_users_ibfk_1` FOREIGN KEY (`user_group_id`) REFERENCES `tz_user_groups` (`user_group_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
