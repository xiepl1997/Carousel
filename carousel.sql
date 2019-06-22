/*
Navicat MySQL Data Transfer

Source Server         : Test
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : carousel

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-06-22 20:37:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cargo
-- ----------------------------
DROP TABLE IF EXISTS `cargo`;
CREATE TABLE `cargo` (
  `cid` varchar(30) NOT NULL COMMENT '货物批次号',
  `clocation` varchar(20) NOT NULL COMMENT '货物位置',
  `type` char(30) NOT NULL DEFAULT 'empty' COMMENT '货物名字',
  `volume` float NOT NULL COMMENT '单个货物大小',
  `count` int(11) NOT NULL COMMENT '该批货物数量',
  `sid` varchar(30) NOT NULL COMMENT '所在货位ID',
  `label_1` int(11) NOT NULL COMMENT '一级位置标签（所在货斗）',
  `label_2` int(11) NOT NULL COMMENT '二级位置标签（所在货斗中的货位）',
  `label_3` int(11) NOT NULL COMMENT '三级标签（货位中的位置）',
  PRIMARY KEY (`cid`,`clocation`),
  KEY `FK_cargo_slotting` (`sid`),
  CONSTRAINT `FK_cargo_slotting` FOREIGN KEY (`sid`) REFERENCES `slotting` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='货物信息表';

-- ----------------------------
-- Records of cargo
-- ----------------------------
INSERT INTO `cargo` VALUES ('20190406125622238', '1-2-1', '锉刀', '10', '50', '20190402091320254', '1', '2', '1');
INSERT INTO `cargo` VALUES ('20190406174210545', '1-3-1', '齿轮', '12', '30', '20190402091445420', '1', '3', '1');
INSERT INTO `cargo` VALUES ('20190407093346456', '1-4-1', '量具', '10', '30', '20190402091620141', '1', '4', '1');
INSERT INTO `cargo` VALUES ('20190407100505054', '2-1-1', '抽芯铆钉', '2', '200', '20190402091731211', '2', '1', '1');
INSERT INTO `cargo` VALUES ('20190421110515427', '1-3-2', '齿轮', '12', '10', '20190402091445420', '1', '3', '2');
INSERT INTO `cargo` VALUES ('20190421183900125', '1-1-1', '螺栓', '1', '100', '20190402091020001', '1', '1', '1');
INSERT INTO `cargo` VALUES ('20190421184310845', '2-2-1', '卷尺', '8', '50', '20190421184310221', '2', '2', '1');
INSERT INTO `cargo` VALUES ('20190421184406442', '2-3-1', '螺栓', '1', '100', '20190421184406231', '2', '3', '1');
INSERT INTO `cargo` VALUES ('20190422012823689', '2-4-1', '齿轮', '12', '30', '20190422012823241', '2', '4', '1');
INSERT INTO `cargo` VALUES ('20190427235728600', '1-4-2', '量具', '10', '10', '20190402091620141', '1', '4', '2');
INSERT INTO `cargo` VALUES ('20190427235728600', '3-2-1', '量具', '10', '20', '20190427235728816', '3', '2', '1');
INSERT INTO `cargo` VALUES ('20190605102340836', '1-3-3', '齿轮', '12', '10', '20190402091445420', '1', '3', '3');
INSERT INTO `cargo` VALUES ('20190607112615027', '3-1-1', '新华字典', '20', '10', '20190427234907549', '3', '1', '1');
INSERT INTO `cargo` VALUES ('20190613084354130', '1-1-2', '螺栓', '1', '100', '20190402091020001', '1', '1', '2');
INSERT INTO `cargo` VALUES ('20190613131205022', '3-3-1', '书本', '20', '120', '20190613131205024', '3', '3', '1');
INSERT INTO `cargo` VALUES ('20190613131338182', '1-1-3', '螺栓', '1', '200', '20190402091020001', '1', '1', '3');
INSERT INTO `cargo` VALUES ('20190613131338182', '2-3-2', '螺栓', '1', '100', '20190421184406231', '2', '3', '2');

-- ----------------------------
-- Table structure for cargo_slotting_mess
-- ----------------------------
DROP TABLE IF EXISTS `cargo_slotting_mess`;
CREATE TABLE `cargo_slotting_mess` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` char(30) NOT NULL COMMENT '货物名',
  `cargo_volume` float NOT NULL COMMENT '单个货物的体积大小',
  `slotting_capacity` float NOT NULL COMMENT '该货物对应的货位的容积',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='回转库系统中所支持的入库货物的信息，包括货物大小，所对应货位的容积等。';

-- ----------------------------
-- Records of cargo_slotting_mess
-- ----------------------------
INSERT INTO `cargo_slotting_mess` VALUES ('1', '书本', '20', '800');
INSERT INTO `cargo_slotting_mess` VALUES ('2', '卷尺', '8', '400');
INSERT INTO `cargo_slotting_mess` VALUES ('3', '压缩弹簧', '15', '300');
INSERT INTO `cargo_slotting_mess` VALUES ('4', '呆扳手', '15', '600');
INSERT INTO `cargo_slotting_mess` VALUES ('5', '圆头锤', '30', '800');
INSERT INTO `cargo_slotting_mess` VALUES ('6', '抽芯铆钉', '2', '500');
INSERT INTO `cargo_slotting_mess` VALUES ('7', '滚动轴承', '12', '600');
INSERT INTO `cargo_slotting_mess` VALUES ('8', '螺栓', '1', '400');
INSERT INTO `cargo_slotting_mess` VALUES ('9', '螺母', '2', '400');
INSERT INTO `cargo_slotting_mess` VALUES ('10', '量具', '10', '500');
INSERT INTO `cargo_slotting_mess` VALUES ('11', '钢锯', '10', '500');
INSERT INTO `cargo_slotting_mess` VALUES ('12', '锉刀', '10', '500');
INSERT INTO `cargo_slotting_mess` VALUES ('13', '齿轮', '12', '600');
INSERT INTO `cargo_slotting_mess` VALUES ('14', '活扳手', '20', '500');
INSERT INTO `cargo_slotting_mess` VALUES ('16', '新华字典', '20', '400');
INSERT INTO `cargo_slotting_mess` VALUES ('17', '百瑞特小号弹簧', '5', '500');
INSERT INTO `cargo_slotting_mess` VALUES ('18', '百瑞特大号弹簧', '10', '500');
INSERT INTO `cargo_slotting_mess` VALUES ('19', '螺丝钉1号', '2', '300');

-- ----------------------------
-- Table structure for slotting
-- ----------------------------
DROP TABLE IF EXISTS `slotting`;
CREATE TABLE `slotting` (
  `sid` varchar(30) NOT NULL COMMENT '货位ID',
  `type` char(30) NOT NULL DEFAULT 'empty' COMMENT '货物种类',
  `label_1` int(11) NOT NULL COMMENT '一级位置标签（所在货斗）',
  `label_2` int(11) NOT NULL COMMENT '二级标签（所在货斗的货位位置）',
  `capacity` float NOT NULL DEFAULT '2000' COMMENT '货位大小',
  `capacity_available` float NOT NULL DEFAULT '2000' COMMENT '可用容量',
  `status` char(20) NOT NULL DEFAULT '正常' COMMENT '货位状态',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='货位信息表';

-- ----------------------------
-- Records of slotting
-- ----------------------------
INSERT INTO `slotting` VALUES ('20190402091020001', '螺栓', '1', '1', '400', '0', '正常');
INSERT INTO `slotting` VALUES ('20190402091320254', '锉刀', '1', '2', '500', '0', '正常');
INSERT INTO `slotting` VALUES ('20190402091445420', '齿轮', '1', '3', '600', '0', '正常');
INSERT INTO `slotting` VALUES ('20190402091620141', '量具', '1', '4', '500', '100', '正常');
INSERT INTO `slotting` VALUES ('20190402091731211', '抽芯铆钉', '2', '1', '500', '100', '正常');
INSERT INTO `slotting` VALUES ('20190402093045420', 'empty', '5', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190402102016551', 'empty', '6', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190402102342021', 'empty', '7', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190402102420102', 'empty', '8', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190402102532223', 'empty', '9', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190402102613470', 'empty', '10', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190402102711009', 'empty', '11', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190402131008423', 'empty', '12', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190403152439306', 'empty', '13', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190403153025123', 'empty', '14', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190403160225250', 'empty', '15', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190403160522314', 'empty', '16', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190421184310221', '卷尺', '2', '2', '400', '0', '正常');
INSERT INTO `slotting` VALUES ('20190421184406231', '螺栓', '2', '3', '400', '200', '正常');
INSERT INTO `slotting` VALUES ('20190422012823241', '齿轮', '2', '4', '600', '240', '正常');
INSERT INTO `slotting` VALUES ('20190427234907549', '新华字典', '3', '1', '400', '200', '正常');
INSERT INTO `slotting` VALUES ('20190427235728816', '量具', '3', '2', '500', '300', '正常');
INSERT INTO `slotting` VALUES ('20190507232301783', 'empty', '4', '1', '2000', '2000', '正常');
INSERT INTO `slotting` VALUES ('20190613131205024', '书本', '3', '3', '800', '-1600', '正常');
INSERT INTO `slotting` VALUES ('20190613132107781', 'empty', '2', '5', '100', '100', '正常');
INSERT INTO `slotting` VALUES ('20190613132107843', 'empty', '3', '4', '300', '300', '正常');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('100110', '54321', '汤姆', '男', '18041362394');
INSERT INTO `user` VALUES ('admin', 'admin', '大卫', '男', '18540150219');

-- ----------------------------
-- View structure for v_sc
-- ----------------------------
DROP VIEW IF EXISTS `v_sc`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_sc` AS select `s`.`sno` AS `sno`,`s`.`sname` AS `sname`,`c`.`cname` AS `cname`,`sc`.`grade` AS `grade` from ((`s0211` `s` join `c0211` `c`) join `sc0211` `sc`) where ((`s`.`sno` = `sc`.`sno`) and (`c`.`cno` = `sc`.`cno`)) ;
