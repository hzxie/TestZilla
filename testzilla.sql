-- phpMyAdmin SQL Dump
-- version 4.4.11
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 23, 2015 at 03:11 下午
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `testzilla`
--

-- --------------------------------------------------------

--
-- Table structure for table `tz_issues`
--

CREATE TABLE IF NOT EXISTS `tz_issues` (
  `issue_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `product_version` varchar(24) NOT NULL,
  `issue_category_id` int(4) NOT NULL,
  `issue_status_id` int(4) NOT NULL,
  `issue_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `issue_hunter_id` bigint(20) NOT NULL,
  `issue_title` varchar(64) NOT NULL,
  `issue_description` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_issues`
--

INSERT INTO `tz_issues` (`issue_id`, `product_id`, `product_version`, `issue_category_id`, `issue_status_id`, `issue_create_time`, `issue_hunter_id`, `issue_title`, `issue_description`) VALUES
(1000, 1000, '1.0 Beta', 5, 4, '2015-01-22 08:05:00', 1001, 'Bug #1000', '# Marked in browser\\n\\nRendered by **marked**.'),
(1001, 1000, '1.0 Beta', 12, 1, '2015-02-03 08:05:00', 1000, 'Bug #1001', 'This is the *first* editor.\r\n------------------------------\r\n\r\nJust plain **Markdown**, except that the input is sanitized:\r\n\r\nand that it implements "fenced blockquotes" via a plugin:\r\n\r\n"""\r\nDo it like this:\r\n\r\n1. Have idea.\r\n2. ???\r\n3. Profit!\r\n"""'),
(1002, 1001, '1.0 Beta', 2, 1, '2015-02-03 12:11:20', 1001, 'Bug #1002', '# Marked in browser\\n\\nRendered by **marked**.');

-- --------------------------------------------------------

--
-- Table structure for table `tz_issue_categories`
--

CREATE TABLE IF NOT EXISTS `tz_issue_categories` (
  `issue_category_id` int(4) NOT NULL,
  `issue_category_slug` varchar(24) NOT NULL,
  `issue_category_name` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_issue_categories`
--

INSERT INTO `tz_issue_categories` (`issue_category_id`, `issue_category_slug`, `issue_category_name`) VALUES
(1, 'crashes', '{"en": "Application Crashes", "zh": "应用程序崩溃"}'),
(2, 'exceptions', '{"en": "Exceptions", "zh": "异常"}'),
(3, 'functional', '{"en": "Functional Errors", "zh": "功能性错误"}'),
(4, 'file-io', '{"en": "File I/O", "zh": "文件 I/O"}'),
(5, 'gui', '{"en": "Graphical User Interface", "zh": "图形用户界面"}'),
(6, 'database', '{"en": "Database", "zh": "数据库"}'),
(7, 'network', '{"en": "Network", "zh": "网络"}'),
(8, 'optimization', '{"en": "Optimization", "zh": "优化"}'),
(9, 'portability', '{"en": "Portability", "zh": "可移植性"}'),
(10, 'security', '{"en": "Security", "zh": "安全"}'),
(11, 'threads', '{"en": "Threads Safety", "zh": "线程安全性"}'),
(12, 'others', '{"en": "Others", "zh": "其他"}');

-- --------------------------------------------------------

--
-- Table structure for table `tz_issue_status`
--

CREATE TABLE IF NOT EXISTS `tz_issue_status` (
  `issue_status_id` int(4) NOT NULL,
  `issue_status_slug` varchar(24) NOT NULL,
  `issue_status_name` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_issue_status`
--

INSERT INTO `tz_issue_status` (`issue_status_id`, `issue_status_slug`, `issue_status_name`) VALUES
(1, 'unconfirmed', '{"en":"Unconfirmed", "zh": "未确认"}'),
(2, 'confirmed', '{"en": "Confirmed", "zh": "已确认"}'),
(3, 'fixed', '{"en": "Fixed", "zh": "已修复"}'),
(4, 'nvalid', '{"en": "Invalid", "zh": "无效"}'),
(5, 'wontfix', '{"en", "Won''t Fix", "zh": "将不修复"}'),
(6, 'later', '{"en": "Fix Later", "zh": "在以后修复"}'),
(7, 'duplicate', '{"en": "Duplicate", "zh": "重复"}'),
(8, 'worksforme', '{"en": "Can''t Reappear", "zh": "无法复现"}');

-- --------------------------------------------------------

--
-- Table structure for table `tz_mail_verification`
--

CREATE TABLE IF NOT EXISTS `tz_mail_verification` (
  `email` varchar(64) NOT NULL,
  `code` varchar(36) NOT NULL,
  `expires_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_mail_verification`
--

INSERT INTO `tz_mail_verification` (`email`, `code`, `expires_time`) VALUES
('zjhzxhz@qq.com', '3d6f91cd-2868-44ee-b907-df87146a512a', '0000-00-00 00:00:00');

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
  `product_logo` varchar(128) NOT NULL,
  `product_category_id` int(4) NOT NULL,
  `product_latest_version` varchar(24) NOT NULL,
  `product_developer_id` bigint(20) NOT NULL,
  `product_url` varchar(256) NOT NULL,
  `product_name` text NOT NULL,
  `product_prerequisites` text NOT NULL,
  `product_description` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_products`
--

INSERT INTO `tz_products` (`product_id`, `product_logo`, `product_category_id`, `product_latest_version`, `product_developer_id`, `product_url`, `product_name`, `product_prerequisites`, `product_description`) VALUES
(1000, 'http://www.testzilla.org/img/logo.png', 1, '2.0 Alpha', 1002, 'http://www.testzilla.org/', '{"en":"TestZilla", "zh":"TestZilla"}', '{"en": "IE 7+, Firefox, Chrome or Safari", "zh": "IE 7+, Firefox, Chrome or Safari"}', '{"en": "", "zh": ""}'),
(1001, 'http://www.courseocean.com/img/logo.png', 1, '1.0 Alpha', 1001, 'http://www.courseocean.com/', '{"en":"CourseOcean", "zh":"学海无涯"}', '{"zh": "IE 7+, Firefox, Chrome or Safari"}', '{"zh": "# Hello World"}');

-- --------------------------------------------------------

--
-- Table structure for table `tz_product_categories`
--

CREATE TABLE IF NOT EXISTS `tz_product_categories` (
  `product_category_id` int(4) NOT NULL,
  `product_category_slug` varchar(24) NOT NULL,
  `product_category_name` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_product_categories`
--

INSERT INTO `tz_product_categories` (`product_category_id`, `product_category_slug`, `product_category_name`) VALUES
(1, 'web', '{ "en": "Web Application", "zh": "Web 应用程序" }'),
(2, 'ios', '{ "en": "iOS Application", "zh": "iOS 应用程序" }'),
(3, 'android', '{ "en": "Android Application", "zh": "Andriod 应用程序" }'),
(4, 'windows-phone', '{ "en": "Windows Phone Application", "zh": "Windows Phone 应用程序" }'),
(5, 'windows', '{ "en": "Windows Application", "zh": "Windows 应用程序" }'),
(6, 'mac', '{ "en": "Mac Application", "zh": "Mac 应用程序" }'),
(7, 'others', '{ "en": "Others", "zh": "其他" }');

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
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tz_users`
--

INSERT INTO `tz_users` (`uid`, `username`, `password`, `user_group_id`, `email`, `is_email_verified`) VALUES
(1000, 'Administrator', '785ee107c11dfe36de668b1ae7baacbb', 2, 'webmaster@testzilla.org', 1),
(1001, 'zjhzxhz', '785ee107c11dfe36de668b1ae7baacbb', 1, 'zjhzxhz@gmail.com', 1),
(1002, 'TestZilla', '785ee107c11dfe36de668b1ae7baacbb', 1, 'support@testzilla.org', 1),
(1003, 'tester', '785ee107c11dfe36de668b1ae7baacbb', 1, 'noreply@zjhzxhz.com', 0);

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
-- Indexes for table `tz_issues`
--
ALTER TABLE `tz_issues`
  ADD PRIMARY KEY (`issue_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `bug_category_id` (`issue_category_id`,`issue_status_id`),
  ADD KEY `product_id_2` (`product_id`),
  ADD KEY `bug_status_id` (`issue_status_id`),
  ADD KEY `bug_hunter_id` (`issue_hunter_id`);

--
-- Indexes for table `tz_issue_categories`
--
ALTER TABLE `tz_issue_categories`
  ADD PRIMARY KEY (`issue_category_id`);

--
-- Indexes for table `tz_issue_status`
--
ALTER TABLE `tz_issue_status`
  ADD PRIMARY KEY (`issue_status_id`);

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
-- AUTO_INCREMENT for table `tz_issues`
--
ALTER TABLE `tz_issues`
  MODIFY `issue_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1003;
--
-- AUTO_INCREMENT for table `tz_issue_categories`
--
ALTER TABLE `tz_issue_categories`
  MODIFY `issue_category_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `tz_issue_status`
--
ALTER TABLE `tz_issue_status`
  MODIFY `issue_status_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
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
  MODIFY `uid` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1004;
--
-- AUTO_INCREMENT for table `tz_user_groups`
--
ALTER TABLE `tz_user_groups`
  MODIFY `user_group_id` int(4) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tz_issues`
--
ALTER TABLE `tz_issues`
  ADD CONSTRAINT `tz_issues_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `tz_products` (`product_id`),
  ADD CONSTRAINT `tz_issues_ibfk_2` FOREIGN KEY (`issue_category_id`) REFERENCES `tz_issue_categories` (`issue_category_id`),
  ADD CONSTRAINT `tz_issues_ibfk_3` FOREIGN KEY (`issue_status_id`) REFERENCES `tz_issue_status` (`issue_status_id`),
  ADD CONSTRAINT `tz_issues_ibfk_4` FOREIGN KEY (`issue_hunter_id`) REFERENCES `tz_users` (`uid`);

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
