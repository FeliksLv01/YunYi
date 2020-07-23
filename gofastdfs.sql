/*
 Navicat Premium Data Transfer

 Source Server         : Mysql-Mac
 Source Server Type    : MySQL
 Source Server Version : 50647
 Source Host           : localhost:3306
 Source Schema         : gofastdfs

 Target Server Type    : MySQL
 Target Server Version : 50647
 File Encoding         : 65001

 Date: 23/07/2020 17:58:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL COMMENT '权限等级',
  `parent_id` int(11) DEFAULT NULL COMMENT '改权限对应的主权限id',
  `position` varchar(255) DEFAULT NULL COMMENT '权限路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (100, '控制台', '1', 0, '');
INSERT INTO `permission` VALUES (101, '控制台数据', '2', 100, 'console');
INSERT INTO `permission` VALUES (102, '用户管理', '1', 0, '');
INSERT INTO `permission` VALUES (103, '用户列表', '2', 102, 'users');
INSERT INTO `permission` VALUES (104, '添加用户', '3', 103, 'users');
INSERT INTO `permission` VALUES (105, '删除用户', '3', 103, 'users');
INSERT INTO `permission` VALUES (107, '获取用户详情', '3', 103, 'users');
INSERT INTO `permission` VALUES (108, '分配用户角色', '3', 103, 'users');
INSERT INTO `permission` VALUES (109, '设置管理状态', '3', 103, 'users');
INSERT INTO `permission` VALUES (110, '权限管理', '1', 0, '');
INSERT INTO `permission` VALUES (111, '角色列表', '2', 110, 'roles');
INSERT INTO `permission` VALUES (112, '添加角色', '3', 111, 'roles');
INSERT INTO `permission` VALUES (113, '删除角色', '3', 111, 'roles');
INSERT INTO `permission` VALUES (114, '角色授权', '3', 111, 'roles');
INSERT INTO `permission` VALUES (115, '取消角色授权', '3', 111, 'roles');
INSERT INTO `permission` VALUES (116, '获取角色列表', '3', 111, 'roles');
INSERT INTO `permission` VALUES (117, '获取角色详情', '3', 111, 'roles');
INSERT INTO `permission` VALUES (118, '更新角色信息', '3', 111, 'roles');
INSERT INTO `permission` VALUES (119, '更新角色权限', '3', 111, 'roles');
INSERT INTO `permission` VALUES (120, '权限列表', '2', 110, 'rights');
INSERT INTO `permission` VALUES (121, '查看权限', '3', 120, 'rights');
INSERT INTO `permission` VALUES (122, '文件管理', '1', 0, '');
INSERT INTO `permission` VALUES (123, '文件上传', '2', 122, 'upload');
INSERT INTO `permission` VALUES (124, '文件列表', '2', 122, 'filelist');
INSERT INTO `permission` VALUES (125, '下载', '3', 124, '');
INSERT INTO `permission` VALUES (126, '删除', '3', 124, '');
INSERT INTO `permission` VALUES (127, '个人中心', '1', 0, '');
INSERT INTO `permission` VALUES (128, '修改个人信息', '2', 127, 'userinfo');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '角色·描述',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'test', '');
INSERT INTO `role` VALUES (2, '超级管理员', '超级管理员admin');
COMMIT;

-- ----------------------------
-- Table structure for role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions` (
  `role_id` int(11) NOT NULL,
  `permissions_id` int(11) NOT NULL,
  KEY `FKlodb7xh4a2xjv39gc3lsop95n` (`role_id`) USING BTREE,
  CONSTRAINT `FKlodb7xh4a2xjv39gc3lsop95n` FOREIGN KEY (`role_id`) REFERENCES `role` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of role_permissions
-- ----------------------------
BEGIN;
INSERT INTO `role_permissions` VALUES (0, 102);
INSERT INTO `role_permissions` VALUES (0, 103);
INSERT INTO `role_permissions` VALUES (0, 104);
INSERT INTO `role_permissions` VALUES (0, 105);
INSERT INTO `role_permissions` VALUES (0, 106);
INSERT INTO `role_permissions` VALUES (0, 107);
INSERT INTO `role_permissions` VALUES (0, 108);
INSERT INTO `role_permissions` VALUES (0, 109);
INSERT INTO `role_permissions` VALUES (0, 110);
INSERT INTO `role_permissions` VALUES (0, 111);
INSERT INTO `role_permissions` VALUES (0, 112);
INSERT INTO `role_permissions` VALUES (0, 113);
INSERT INTO `role_permissions` VALUES (0, 114);
INSERT INTO `role_permissions` VALUES (0, 115);
INSERT INTO `role_permissions` VALUES (0, 116);
INSERT INTO `role_permissions` VALUES (0, 117);
INSERT INTO `role_permissions` VALUES (0, 118);
INSERT INTO `role_permissions` VALUES (0, 119);
INSERT INTO `role_permissions` VALUES (0, 120);
INSERT INTO `role_permissions` VALUES (0, 121);
INSERT INTO `role_permissions` VALUES (0, 122);
INSERT INTO `role_permissions` VALUES (0, 123);
INSERT INTO `role_permissions` VALUES (0, 124);
INSERT INTO `role_permissions` VALUES (0, 125);
INSERT INTO `role_permissions` VALUES (0, 126);
INSERT INTO `role_permissions` VALUES (0, 127);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `state` tinyint(1) NOT NULL COMMENT '0表示该用户被封，1表示没有',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`) USING BTREE,
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'test', '$2a$10$nyEQvy0N/NAio37M3miKGOH9cqLssAbrcq0EuIHNOCJHna0mfp9E.', '', '', 1, NULL, NULL);
INSERT INTO `user` VALUES (3, 'admin', '$2a$10$nyEQvy0N/NAio37M3miKGOH9cqLssAbrcq0EuIHNOCJHna0mfp9E.', '13659852966', '1350925894@qq.com', 1, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`) USING BTREE,
  KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`) USING BTREE,
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d` FOREIGN KEY (`roles_id`) REFERENCES `role` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
BEGIN;
INSERT INTO `user_roles` VALUES (0, 0);
INSERT INTO `user_roles` VALUES (1, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
