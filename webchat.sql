/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : webchat

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-15 21:05:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `friend_id` int(11) DEFAULT NULL COMMENT '好友id',
  `build_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立时间',
  `type_id` int(11) DEFAULT NULL COMMENT '好友分组id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='好友表';

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('1', '1', '2', '2017-01-16 21:13:47', '1');
INSERT INTO `friend` VALUES ('2', '1', '3', '2017-01-16 21:14:01', '1');
INSERT INTO `friend` VALUES ('3', '1', '4', '2017-01-16 21:14:10', '1');
INSERT INTO `friend` VALUES ('4', '1', '5', '2017-01-16 21:14:17', '1');
INSERT INTO `friend` VALUES ('5', '1', '6', '2017-01-16 21:14:23', '1');
INSERT INTO `friend` VALUES ('6', '1', '7', '2017-01-16 21:14:35', '1');
INSERT INTO `friend` VALUES ('7', '2', '1', '2017-01-16 21:15:37', '2');
INSERT INTO `friend` VALUES ('8', '2', '3', '2017-01-16 21:15:51', '2');
INSERT INTO `friend` VALUES ('9', '2', '4', '2017-01-16 21:16:15', '2');
INSERT INTO `friend` VALUES ('10', '2', '5', '2017-01-16 21:16:25', '2');
INSERT INTO `friend` VALUES ('11', '3', '1', '2017-01-16 21:16:35', '3');
INSERT INTO `friend` VALUES ('12', '3', '2', '2017-01-16 21:16:40', '3');
INSERT INTO `friend` VALUES ('13', '3', '4', '2017-01-16 21:16:46', '3');
INSERT INTO `friend` VALUES ('14', '3', '5', '2017-01-16 21:16:53', '3');
INSERT INTO `friend` VALUES ('15', '4', '1', '2017-01-16 21:17:02', '4');
INSERT INTO `friend` VALUES ('16', '4', '2', '2017-01-16 21:17:06', '4');
INSERT INTO `friend` VALUES ('17', '4', '3', '2017-01-16 21:17:17', '4');
INSERT INTO `friend` VALUES ('18', '4', '5', '2017-01-16 21:17:23', '4');
INSERT INTO `friend` VALUES ('19', '5', '1', '2017-01-16 21:17:28', '5');
INSERT INTO `friend` VALUES ('20', '5', '2', '2017-01-16 21:17:34', '5');
INSERT INTO `friend` VALUES ('21', '5', '3', '2017-01-16 21:17:41', '5');
INSERT INTO `friend` VALUES ('22', '5', '4', '2017-01-16 21:17:59', '5');

-- ----------------------------
-- Table structure for friend_message
-- ----------------------------
DROP TABLE IF EXISTS `friend_message`;
CREATE TABLE `friend_message` (
  `id` int(11) NOT NULL,
  `from_user_id` int(11) DEFAULT NULL COMMENT '发消息的人的id',
  `to_user_id` int(11) DEFAULT NULL COMMENT '收消息的人的id',
  `content` varchar(0) DEFAULT NULL COMMENT '消息内容',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `is_read` int(11) DEFAULT NULL COMMENT '是否已读，1是0否',
  `is_del` int(11) DEFAULT NULL COMMENT '是否删除，1是0否',
  `is_back` int(11) DEFAULT NULL COMMENT '是否撤回，1是0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='好友消息表';

-- ----------------------------
-- Records of friend_message
-- ----------------------------

-- ----------------------------
-- Table structure for friend_type
-- ----------------------------
DROP TABLE IF EXISTS `friend_type`;
CREATE TABLE `friend_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键\r\n            ',
  `type_name` varchar(255) DEFAULT NULL COMMENT '分组名',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `build_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_default` int(11) DEFAULT '0' COMMENT '是否为默认分组：1为默认，0为不是默认分组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='好友分组';

-- ----------------------------
-- Records of friend_type
-- ----------------------------
INSERT INTO `friend_type` VALUES ('1', '好友', '1', '2017-01-16 21:11:45', '1');
INSERT INTO `friend_type` VALUES ('2', '好友', '2', '2017-01-16 21:12:12', '1');
INSERT INTO `friend_type` VALUES ('3', '好友', '3', '2017-01-16 21:12:25', '1');
INSERT INTO `friend_type` VALUES ('4', '好友', '4', '2017-01-16 21:12:35', '1');
INSERT INTO `friend_type` VALUES ('5', '好友', '5', '2017-01-16 21:12:37', '1');
INSERT INTO `friend_type` VALUES ('6', '好友', '6', '2017-01-16 21:13:19', '1');
INSERT INTO `friend_type` VALUES ('7', '好友', '7', '2017-01-16 21:13:35', '1');

-- ----------------------------
-- Table structure for group_message
-- ----------------------------
DROP TABLE IF EXISTS `group_message`;
CREATE TABLE `group_message` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `group_id` int(11) DEFAULT NULL COMMENT '群id',
  `content` varchar(0) DEFAULT NULL COMMENT '群消息内容',
  `is_del` int(11) DEFAULT NULL COMMENT '是否删除，1是 0否',
  `is_read` int(11) DEFAULT NULL COMMENT '是否已读，1是，0否。',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_back` int(11) DEFAULT NULL COMMENT '是否撤回，1是 0否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群消息表';

-- ----------------------------
-- Records of group_message
-- ----------------------------

-- ----------------------------
-- Table structure for group_user
-- ----------------------------
DROP TABLE IF EXISTS `group_user`;
CREATE TABLE `group_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `group_id` int(11) DEFAULT NULL COMMENT '群id\r\n            ',
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入群时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='群成员表';

-- ----------------------------
-- Records of group_user
-- ----------------------------
INSERT INTO `group_user` VALUES ('1', '1', '1', '2017-01-17 21:46:31');
INSERT INTO `group_user` VALUES ('2', '2', '1', '2017-01-17 21:47:15');
INSERT INTO `group_user` VALUES ('3', '3', '1', '2017-01-17 21:47:28');
INSERT INTO `group_user` VALUES ('4', '4', '1', '2017-01-17 21:47:37');
INSERT INTO `group_user` VALUES ('5', '5', '1', '2017-01-17 21:47:46');
INSERT INTO `group_user` VALUES ('6', '6', '1', '2017-01-17 21:47:51');
INSERT INTO `group_user` VALUES ('7', '7', '1', '2017-01-17 21:47:56');

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `id` int(11) NOT NULL COMMENT '自增主键\r\n            ',
  `group_num` varchar(255) DEFAULT NULL COMMENT '群号',
  `group_name` varchar(255) DEFAULT NULL COMMENT '群名称',
  `avatar` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `build_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(0) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群';

-- ----------------------------
-- Records of t_group
-- ----------------------------
INSERT INTO `t_group` VALUES ('1', '1', 'oldriver技术交流', 'images/boy-01.png', '1', '2017-01-16 22:00:07', null, null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_name` varchar(255) DEFAULT NULL COMMENT '帐号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `gender` int(11) DEFAULT '0' COMMENT '性别：0为男，1为女',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `role_code` int(11) DEFAULT '1' COMMENT '角色code：1为用户，2为管理员',
  `version` varchar(0) DEFAULT NULL,
  `sign` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'jk', 'jk', '跳跳猴', '0', '/images/me.jpg', null, null, '2', null, '666');
INSERT INTO `t_user` VALUES ('2', 'u2', '123', '马云', '0', '/images/boy-01.png', null, null, '1', null, '不就是钱嘛！');
INSERT INTO `t_user` VALUES ('3', 'u3', '123', '马化腾', '0', '/images/boy-02.png', null, null, '1', null, 'sugar');
INSERT INTO `t_user` VALUES ('4', 'u4', '123', '雷军', '0', '/images/boy-03.png', null, null, '1', null, '椰子皮');
INSERT INTO `t_user` VALUES ('5', 'u5', '123', '新垣结衣', '1', '/images/girl-01.png', null, null, '1', null, '个性签名');
INSERT INTO `t_user` VALUES ('6', 'u6', '123', '梭梭酱', '1', '/images/girl-02.png', null, null, '1', null, '朴实无华，你的根本');
INSERT INTO `t_user` VALUES ('7', 'u7', '123', 'papi酱', '1', '/images/girl-03.png', null, null, '1', null, '美貌与才华于一身');
