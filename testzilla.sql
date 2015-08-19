-- phpMyAdmin SQL Dump
-- version 4.4.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 19, 2015 at 08:34 上午
-- Server version: 5.6.24
-- PHP Version: 5.6.8

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
-- Table structure for table `tz_bugs`
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
-- Dumping data for table `tz_bugs`
--

INSERT INTO `tz_bugs` (`bug_id`, `product_id`, `product_version`, `bug_category_id`, `bug_status_id`, `bug_severity_id`, `bug_create_time`, `bug_hunter_id`, `bug_title`, `bug_description`, `bug_screenshots`) VALUES
(1000, 1000, '1.0 Beta', 5, 4, 3, '2015-01-22 08:05:00', 1001, 'Bug #1000', '# Marked in browser\\n\\nRendered by **marked**.', NULL),
(1001, 1000, '1.0 Beta', 12, 1, 2, '2015-02-03 08:05:00', 1000, 'Bug #1001', 'This is the *first* editor.\r\n------------------------------\r\n\r\nJust plain **Markdown**, except that the input is sanitized:\r\n\r\nand that it implements "fenced blockquotes" via a plugin:\r\n\r\n"""\r\nDo it like this:\r\n\r\n1. Have idea.\r\n2. ???\r\n3. Profit!\r\n"""', NULL),
(1002, 1001, '1.0 Beta', 2, 1, 1, '2015-02-03 12:11:20', 1001, 'Bug #1002', '# Marked in browser\\n\\nRendered by **marked**.', '');

-- --------------------------------------------------------

--
-- Table structure for table `tz_bug_categories`
--

CREATE TABLE IF NOT EXISTS `tz_bug_categories` (
  `bug_category_id` int(4) NOT NULL,
  `bug_category_slug` varchar(24) NOT NULL,
  `bug_category_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_bug_categories`
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
-- Table structure for table `tz_bug_severities`
--

CREATE TABLE IF NOT EXISTS `tz_bug_severities` (
  `bug_severity_id` int(4) NOT NULL,
  `bug_severity_slug` varchar(24) NOT NULL,
  `bug_severity_name` varchar(24) NOT NULL,
  `bug_severity_description` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_bug_severities`
--

INSERT INTO `tz_bug_severities` (`bug_severity_id`, `bug_severity_slug`, `bug_severity_name`, `bug_severity_description`) VALUES
(1, 'critical', 'Critical', 'The defect affects critical functionality or critical data. It does not have a workaround. Example: Unsuccessful installation, complete failure of a feature.'),
(2, 'major', 'Major', 'The defect affects major functionality or major data. It has a workaround but is not obvious and is difficult. Example: A feature is not functional from one module but the task is doable if 10 complicated indirect steps are followed in another module/s.'),
(3, 'minor', 'Minor', 'The defect affects minor functionality or non-critical data. It has an easy workaround. Example: A minor feature that is not functional in one module but the same task is easily doable from another module.'),
(4, 'trivial', 'Trivial', 'The defect does not affect functionality or data. It does not even need a workaround. It does not impact productivity or efficiency. It is merely an inconvenience. Example: Petty layout discrepancies, spelling/grammatical errors.');

-- --------------------------------------------------------

--
-- Table structure for table `tz_bug_status`
--

CREATE TABLE IF NOT EXISTS `tz_bug_status` (
  `bug_status_id` int(4) NOT NULL,
  `bug_status_slug` varchar(24) NOT NULL,
  `bug_status_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_bug_status`
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
-- Table structure for table `tz_mail_verification`
--

CREATE TABLE IF NOT EXISTS `tz_mail_verification` (
  `email` varchar(64) NOT NULL,
  `code` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_mail_verification`
--

INSERT INTO `tz_mail_verification` (`email`, `code`) VALUES
('zjhzxhz@qq.com', '3d6f91cd-2868-44ee-b907-df87146a512a');

-- --------------------------------------------------------

--
-- Table structure for table `tz_options`
--

CREATE TABLE IF NOT EXISTS `tz_options` (
  `option_id` int(8) NOT NULL,
  `option_key` varchar(32) NOT NULL,
  `option_value` text NOT NULL,
  `is_autoload` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_options`
--

INSERT INTO `tz_options` (`option_id`, `option_key`, `option_value`, `is_autoload`) VALUES
(1, 'WebsiteName', 'TestZilla', 1),
(2, 'Description', 'Crowd Test Platform Based on Phalcon Framework.', 1),
(3, 'Copyright', '<a href="http://www.testzilla.org">TestZilla</a>', 1),
(4, 'GoogleAnalyticsCode', '<script type="text/javascript">   (function(i,s,o,g,r,a,m){i[''GoogleAnalyticsObject'']=r;i[r]=i[r]||function(){   (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),   m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)   })(window,document,''script'',''//www.google-analytics.com/analytics.js'',''ga'');    ga(''create'', ''UA-56635442-3'', ''auto'');   ga(''send'', ''pageview'');  </script>', 1),
(5, 'ContactAddress', '', 1),
(6, 'ContactPhone', '', 1),
(7, 'ContactEmail', 'support@testzilla.org', 1),
(8, 'SocialLinks', '{}', 1),
(9, 'SensitiveWords', '["法轮","中央","六四","军区","共产党","国民党"]', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tz_points_logs`
--

CREATE TABLE IF NOT EXISTS `tz_points_logs` (
  `points_log_id` bigint(20) NOT NULL,
  `points_to_uid` bigint(20) NOT NULL,
  `points_get_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `points_rule_id` int(4) NOT NULL,
  `points_meta` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_points_logs`
--

INSERT INTO `tz_points_logs` (`points_log_id`, `points_to_uid`, `points_get_time`, `points_rule_id`, `points_meta`) VALUES
(1000, 1002, '2014-12-31 00:00:00', 1, ''),
(1001, 1001, '2014-12-31 00:32:25', 1, ''),
(1002, 1002, '2015-01-27 23:00:08', 2, ''),
(1003, 1001, '2015-02-05 15:30:06', 2, '');

-- --------------------------------------------------------

--
-- Table structure for table `tz_points_rules`
--

CREATE TABLE IF NOT EXISTS `tz_points_rules` (
  `points_rule_id` int(4) NOT NULL,
  `points_rule_slug` varchar(32) NOT NULL,
  `points_rule_reputation` int(4) NOT NULL,
  `points_rule_credits` int(4) NOT NULL,
  `points_rule_title` varchar(32) NOT NULL,
  `points_rule_description` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_points_rules`
--

INSERT INTO `tz_points_rules` (`points_rule_id`, `points_rule_slug`, `points_rule_reputation`, `points_rule_credits`, `points_rule_title`, `points_rule_description`) VALUES
(1, 'create-account', 0, 100, 'Create Account', 'Once your account was created and your email address was verified, you''ll get 100 credits.'),
(2, 'create-product', 5, -75, 'Create Product', 'Creating a product will cost you 75 credits.'),
(3, 'edit-bug-status-unconfirmed-conf', 10, 50, 'Bug reported has been confirmed', 'Bug reported has been confirmed, and you got 10 reputation and 50 credits.'),
(4, 'edit-bug-status-confirmed-unconf', -10, -50, 'Bug reported has been unconfirme', 'Bug reported has been uconfirmed, and you lost 10 reputation and 50 credits.'),
(5, 'edit-bug-status-unconfirmed-fixe', 10, 50, 'Bug reported has been fixed', 'Bug reported has been fixed, and you got 10 reputation and 50 credits.'),
(6, 'edit-bug-status-fixed-unconfirme', -10, -50, 'Bug reported has been unconfirme', 'Bug reported has been uconfirmed, and you lost 10 reputation and 50 credits.'),
(7, 'edit-bug-status-unconfirmed-wont', 5, 25, 'Bug reported won''t be fixed', 'Bug reported is confirmed, but won''t be fixed, and you got 5 reputation and 25 credits.'),
(8, 'edit-bug-status-wontfix-unconfir', -5, -25, 'Bug reported has been unconfirme', 'Bug reported has been uconfirmed, and you lost 5 reputation and 25 credits.');

-- --------------------------------------------------------

--
-- Table structure for table `tz_products`
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
-- Dumping data for table `tz_products`
--

INSERT INTO `tz_products` (`product_id`, `product_name`, `product_logo`, `product_category_id`, `product_latest_version`, `product_developer_id`, `product_prerequisites`, `product_url`, `product_description`) VALUES
(1000, 'TestZilla', 'http://www.testzilla.org/assets/img/logo.png', 1, '1.0 Beta', 1002, 'IE 10+, Firefox, Chrome or Safari', 'http://www.testzilla.org/', 'Global crowd testing platform designed for web, mobile and desktop applications.'),
(1001, 'CourseOcean', 'http://www.courseocean.com/img/logo.png', 1, '1.0 Alpha', 1001, 'IE 7+, Firefox, Chrome or Safari', 'http://www.courseocean.com/', 'IT training platform that can provide courses and training for IT practitioners or companies. ');

-- --------------------------------------------------------

--
-- Table structure for table `tz_product_categories`
--

CREATE TABLE IF NOT EXISTS `tz_product_categories` (
  `product_category_id` int(4) NOT NULL,
  `product_category_slug` varchar(24) NOT NULL,
  `product_category_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_product_categories`
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
-- Table structure for table `tz_users`
--

CREATE TABLE IF NOT EXISTS `tz_users` (
  `uid` bigint(20) NOT NULL,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `user_group_id` int(4) NOT NULL,
  `email` varchar(64) NOT NULL,
  `is_email_verified` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_users`
--

INSERT INTO `tz_users` (`uid`, `username`, `password`, `user_group_id`, `email`, `is_email_verified`) VALUES
(1000, 'Administrator', '785ee107c11dfe36de668b1ae7baacbb', 2, 'webmaster@testzilla.org', 1),
(1001, 'zjhzxhz', '785ee107c11dfe36de668b1ae7baacbb', 1, 'zjhzxhz@gmail.com', 1),
(1002, 'TestZilla', '785ee107c11dfe36de668b1ae7baacbb', 1, 'support@testzilla.org', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tz_user_groups`
--

CREATE TABLE IF NOT EXISTS `tz_user_groups` (
  `user_group_id` int(4) NOT NULL,
  `user_group_slug` varchar(24) NOT NULL,
  `user_group_name` varchar(24) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_user_groups`
--

INSERT INTO `tz_user_groups` (`user_group_id`, `user_group_slug`, `user_group_name`) VALUES
(1, 'user', 'User'),
(2, 'administrator', 'Administrator');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tz_bugs`
--
ALTER TABLE `tz_bugs`
  ADD PRIMARY KEY (`bug_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `bug_category_id` (`bug_category_id`,`bug_status_id`),
  ADD KEY `product_id_2` (`product_id`),
  ADD KEY `bug_status_id` (`bug_status_id`),
  ADD KEY `bug_hunter_id` (`bug_hunter_id`),
  ADD KEY `bug_severity_id` (`bug_severity_id`);

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
-- Indexes for table `tz_options`
--
ALTER TABLE `tz_options`
  ADD PRIMARY KEY (`option_id`);

--
-- Indexes for table `tz_points_logs`
--
ALTER TABLE `tz_points_logs`
  ADD PRIMARY KEY (`points_log_id`),
  ADD KEY `points_uid` (`points_to_uid`,`points_rule_id`),
  ADD KEY `points_rule_id` (`points_rule_id`);

--
-- Indexes for table `tz_points_rules`
--
ALTER TABLE `tz_points_rules`
  ADD PRIMARY KEY (`points_rule_id`);

--
-- Indexes for table `tz_products`
--
ALTER TABLE `tz_products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `product_developer_id` (`product_developer_id`),
  ADD KEY `product_category_id` (`product_category_id`);

--
-- Indexes for table `tz_product_categories`
--
ALTER TABLE `tz_product_categories`
  ADD PRIMARY KEY (`product_category_id`);

--
-- Indexes for table `tz_users`
--
ALTER TABLE `tz_users`
  ADD PRIMARY KEY (`uid`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `tz_users_ibfk_1` (`user_group_id`);

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
-- AUTO_INCREMENT for table `tz_options`
--
ALTER TABLE `tz_options`
  MODIFY `option_id` int(8) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `tz_points_logs`
--
ALTER TABLE `tz_points_logs`
  MODIFY `points_log_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1004;
--
-- AUTO_INCREMENT for table `tz_points_rules`
--
ALTER TABLE `tz_points_rules`
  MODIFY `points_rule_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
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
  MODIFY `uid` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1003;
--
-- AUTO_INCREMENT for table `tz_user_groups`
--
ALTER TABLE `tz_user_groups`
  MODIFY `user_group_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tz_bugs`
--
ALTER TABLE `tz_bugs`
  ADD CONSTRAINT `tz_bugs_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `tz_products` (`product_id`),
  ADD CONSTRAINT `tz_bugs_ibfk_2` FOREIGN KEY (`bug_category_id`) REFERENCES `tz_bug_categories` (`bug_category_id`),
  ADD CONSTRAINT `tz_bugs_ibfk_3` FOREIGN KEY (`bug_status_id`) REFERENCES `tz_bug_status` (`bug_status_id`),
  ADD CONSTRAINT `tz_bugs_ibfk_4` FOREIGN KEY (`bug_severity_id`) REFERENCES `tz_bug_severities` (`bug_severity_id`),
  ADD CONSTRAINT `tz_bugs_ibfk_5` FOREIGN KEY (`bug_hunter_id`) REFERENCES `tz_users` (`uid`);

--
-- Constraints for table `tz_points_logs`
--
ALTER TABLE `tz_points_logs`
  ADD CONSTRAINT `tz_points_logs_ibfk_1` FOREIGN KEY (`points_to_uid`) REFERENCES `tz_users` (`uid`),
  ADD CONSTRAINT `tz_points_logs_ibfk_2` FOREIGN KEY (`points_rule_id`) REFERENCES `tz_points_rules` (`points_rule_id`);

--
-- Constraints for table `tz_products`
--
ALTER TABLE `tz_products`
  ADD CONSTRAINT `tz_products_ibfk_1` FOREIGN KEY (`product_category_id`) REFERENCES `tz_product_categories` (`product_category_id`),
  ADD CONSTRAINT `tz_products_ibfk_2` FOREIGN KEY (`product_developer_id`) REFERENCES `tz_users` (`uid`);

--
-- Constraints for table `tz_users`
--
ALTER TABLE `tz_users`
  ADD CONSTRAINT `tz_users_ibfk_1` FOREIGN KEY (`user_group_id`) REFERENCES `tz_user_groups` (`user_group_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
