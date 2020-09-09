/*
 Navicat Premium Data Transfer

 Source Server         : mysql-win
 Source Server Type    : MySQL
 Source Server Version : 50647
 Source Host           : localhost:3306
 Source Schema         : application

 Target Server Type    : MySQL
 Target Server Version : 50647
 File Encoding         : 65001

 Date: 09/09/2020 17:49:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (4);
INSERT INTO `hibernate_sequence` VALUES (4);
INSERT INTO `hibernate_sequence` VALUES (4);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (100, '控制台', 1, 0, '');
INSERT INTO `permission` VALUES (101, '控制台数据', 2, 100, 'console');
INSERT INTO `permission` VALUES (102, '用户管理', 1, 0, '');
INSERT INTO `permission` VALUES (103, '用户列表', 2, 102, 'users');
INSERT INTO `permission` VALUES (104, '添加用户', 3, 103, 'users');
INSERT INTO `permission` VALUES (105, '删除用户', 3, 103, 'users');
INSERT INTO `permission` VALUES (106, '获取用户详情', 3, 103, 'users');
INSERT INTO `permission` VALUES (107, '分配用户角色', 3, 103, 'users');
INSERT INTO `permission` VALUES (108, '设置管理状态', 3, 103, 'users');
INSERT INTO `permission` VALUES (109, '权限管理', 1, 0, '');
INSERT INTO `permission` VALUES (110, '角色列表', 2, 109, 'roles');
INSERT INTO `permission` VALUES (111, '添加角色', 3, 110, 'roles');
INSERT INTO `permission` VALUES (112, '删除角色', 3, 110, 'roles');
INSERT INTO `permission` VALUES (113, '角色授权', 3, 110, 'roles');
INSERT INTO `permission` VALUES (114, '取消角色授权', 3, 110, 'roles');
INSERT INTO `permission` VALUES (115, '获取角色列表', 3, 110, 'roles');
INSERT INTO `permission` VALUES (116, '获取角色详情', 3, 110, 'roles');
INSERT INTO `permission` VALUES (117, '更新角色信息', 3, 110, 'roles');
INSERT INTO `permission` VALUES (118, '更新角色权限', 3, 110, 'roles');
INSERT INTO `permission` VALUES (119, '权限列表', 2, 109, 'rights');
INSERT INTO `permission` VALUES (120, '查看权限', 3, 119, 'rights');
INSERT INTO `permission` VALUES (121, '文件管理', 1, 0, '');
INSERT INTO `permission` VALUES (122, '文件上传', 2, 121, 'upload');
INSERT INTO `permission` VALUES (123, '文件列表', 2, 121, 'filelist');
INSERT INTO `permission` VALUES (124, '下载', 3, 123, '');
INSERT INTO `permission` VALUES (125, '删除文件', 3, 123, '');
INSERT INTO `permission` VALUES (126, '个人中心', 1, 0, '');
INSERT INTO `permission` VALUES (127, '修改个人信息', 2, 126, 'userinfo');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (0, 'admin', '超级管理员admin');
INSERT INTO `role` VALUES (1, 'test', '测试用户');

-- ----------------------------
-- Table structure for role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions`  (
  `role_id` int(11) NOT NULL,
  `permissions_id` int(11) NOT NULL,
  INDEX `FKclluu29apreb6osx6ogt4qe16`(`permissions_id`) USING BTREE,
  INDEX `FKlodb7xh4a2xjv39gc3lsop95n`(`role_id`) USING BTREE,
  CONSTRAINT `FKclluu29apreb6osx6ogt4qe16` FOREIGN KEY (`permissions_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKlodb7xh4a2xjv39gc3lsop95n` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_permissions
-- ----------------------------
INSERT INTO `role_permissions` VALUES (0, 100);
INSERT INTO `role_permissions` VALUES (0, 101);
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
INSERT INTO `role_permissions` VALUES (1, 100);
INSERT INTO `role_permissions` VALUES (1, 101);
INSERT INTO `role_permissions` VALUES (1, 121);
INSERT INTO `role_permissions` VALUES (1, 122);
INSERT INTO `role_permissions` VALUES (1, 123);
INSERT INTO `role_permissions` VALUES (1, 124);
INSERT INTO `role_permissions` VALUES (1, 125);
INSERT INTO `role_permissions` VALUES (1, 126);
INSERT INTO `role_permissions` VALUES (1, 127);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` bit(1) NOT NULL COMMENT '0表示该用户被封，1表示没有',
  `create_time` datetime(6) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKn82ha3ccdebhokx3a8fgdqeyy`(`role_id`) USING BTREE,
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (0, 'admin', '$2a$10$nyEQvy0N/NAio37M3miKGOH9cqLssAbrcq0EuIHNOCJHna0mfp9E.', '13659952867', '13565@qq.com', b'1', NULL, 0);
INSERT INTO `user` VALUES (1, 'test', '$2a$10$nyEQvy0N/NAio37M3miKGOH9cqLssAbrcq0EuIHNOCJHna0mfp9E.', '15923762843', 'kc@qq.com', b'1', NULL, 1);
INSERT INTO `user` VALUES (2, '0121809361426', '$2a$10$701XiT4Fb0pkKuh5BGA/I.9.CV397Wcwew2N0Ef7ChpJVnhYcWX0W', '13659852967', '2333@163.com', b'1', '2020-09-09 09:30:28.961000', 1);
INSERT INTO `user` VALUES (3, 'test1', '$2a$10$0VsgOfkEB66kbEofaQl6dedknG/ZhdohLR9NtsXC5QBsAZZtBvsHu', '15951245079', '3344@gmail.com', b'1', '2020-09-09 09:33:13.931000', 1);

SET FOREIGN_KEY_CHECKS = 1;
