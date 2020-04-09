/*
 Navicat Premium Data Transfer

 Source Server         : 测试外_101.200.52.215
 Source Server Type    : MySQL
 Source Server Version : 50630
 Source Host           : 101.200.52.215:3306
 Source Schema         : wmh

 Target Server Type    : MySQL
 Target Server Version : 50630
 File Encoding         : 65001

 Date: 08/04/2020 16:15:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `permission` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限',
  `add_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 1, '*', '2019-01-01 00:00:00', '2019-01-01 00:00:00', 0);
INSERT INTO `permission` VALUES (2, 2, 'wmh:book:list', '2019-01-07 15:18:53', '2019-01-07 15:18:53', 0);

SET FOREIGN_KEY_CHECKS = 1;
