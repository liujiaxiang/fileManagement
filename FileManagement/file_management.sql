/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : file_management

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2016-11-23 12:02:58
*/
use file_management;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` char(20) NOT NULL,
  `department_des` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('7', '教务处', '教务处文件管理');
INSERT INTO `department` VALUES ('8', '数学组', '数学组工作计划');
INSERT INTO `department` VALUES ('15', '语文组', '语文组工作文件管理');
INSERT INTO `department` VALUES ('16', '英语组', '英语组文件管理');

-- ----------------------------
-- Table structure for `files`
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `f_id` int(11) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `upload_user` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `f_id` (`f_id`),
  CONSTRAINT `f_id` FOREIGN KEY (`f_id`) REFERENCES `folders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of files
-- ----------------------------
INSERT INTO `files` VALUES ('29', '23', './views/asserts/upload/2.jpg', '2.jpg', 'admin');
INSERT INTO `files` VALUES ('31', '27', './views/asserts/upload/1.jpg', '1.jpg', 'admin');
INSERT INTO `files` VALUES ('33', '25', './views/asserts/upload/2.jpg', '2.jpg', 'admin');
INSERT INTO `files` VALUES ('34', '25', './views/asserts/upload/mysql.png', 'mysql.png', 'admin');

-- ----------------------------
-- Table structure for `folders`
-- ----------------------------
DROP TABLE IF EXISTS `folders`;
CREATE TABLE `folders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `d_id` int(11) NOT NULL,
  `u_id` int(11) NOT NULL,
  `folder_name` varchar(255) NOT NULL,
  `folder_des` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_id` (`u_id`),
  KEY `d_id` (`d_id`),
  CONSTRAINT `d_id` FOREIGN KEY (`d_id`) REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `u_id` FOREIGN KEY (`u_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of folders
-- ----------------------------
INSERT INTO `folders` VALUES ('23', '7', '1', '工作计划', null);
INSERT INTO `folders` VALUES ('24', '7', '1', '工作总结', null);
INSERT INTO `folders` VALUES ('25', '8', '1', '工作计划', null);
INSERT INTO `folders` VALUES ('26', '8', '1', '工作总结', null);
INSERT INTO `folders` VALUES ('27', '15', '1', '工作计划', null);
INSERT INTO `folders` VALUES ('28', '15', '1', '工作总结', null);

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `department` char(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', 'admin', '教务处');
INSERT INTO `users` VALUES ('7', '张霞', '123456', '语文组');
