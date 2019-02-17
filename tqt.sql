/*
 navicat premium data transfer

 source server         : local
 source server type    : mysql
 source server version : 50718
 source host           : localhost
 source database       : tqt

 target server type    : mysql
 target server version : 50718
 file encoding         : utf-8

 date: 02/17/2019 12:00:42 pm
*/

SET NAMES UTF8MB4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  TABLE STRUCTURE FOR `CLASSES_P`
-- ----------------------------
DROP TABLE IF EXISTS `CLASSES_P`;
CREATE TABLE `CLASSES_P` (
  `ID` VARCHAR(150) NOT NULL,
  `NAME` VARCHAR(150) DEFAULT NULL COMMENT '班级名字',
  `BEGIN_TIME` DATETIME DEFAULT NULL COMMENT '开班日期',
  `END_TIME` DATETIME DEFAULT NULL COMMENT '结束日期',
  `TYPE` VARCHAR(150) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- ----------------------------
--  records of `classes_p`
-- ----------------------------
begin;
insert into `CLASSES_P` values ('1', '201811', '2019-01-31 22:48:56', '2019-04-30 22:48:59', 'java'), ('2', '201901', '2019-01-31 22:49:22', '2019-01-31 22:49:28', 'html5');
commit;

-- ----------------------------
--  table structure for `course_p`
-- ----------------------------
drop table if exists `course_p`;
create table `course_p` (
  `id` varchar(150) collate utf8mb4_bin not null,
  `name` varchar(150) collate utf8mb4_bin default null comment '名字',
  `state` int(11) default null comment '状态',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_bin;

-- ----------------------------
--  records of `course_p`
-- ----------------------------
begin;
insert into `COURSE_P` values ('-1', 'vue', '1'), ('0', 'store', '1'), ('1', 'hibernate', '1'), ('2', 'springmvc', '1'), ('3', 'spring', '1'), ('4', 'maven', '1'), ('5', 'oracle', '1'), ('6', 'jk', '1');
commit;

-- ----------------------------
--  TABLE STRUCTURE FOR `FEEDBACK_P`
-- ----------------------------
DROP TABLE IF EXISTS `FEEDBACK_P`;
CREATE TABLE `FEEDBACK_P` (
  `ID` VARCHAR(150) COLLATE UTF8MB4_BIN NOT NULL,
  `ADJUSTMENT` TEXT COLLATE UTF8MB4_BIN COMMENT '调整方案',
  `BACK_TIME` DATETIME DEFAULT NULL COMMENT '时间',
  `DAY_NUM` INT(11) DEFAULT NULL COMMENT '课程第几天',
  `NOT_CLEAR` TEXT COLLATE UTF8MB4_BIN COMMENT '不清楚的地方',
  `PLAN` TEXT COLLATE UTF8MB4_BIN COMMENT '计划',
  `SELF_CHECK` TEXT COLLATE UTF8MB4_BIN COMMENT '自我觉察',
  `TODO` TEXT COLLATE UTF8MB4_BIN COMMENT '实施方案',
  `COURSE_ID` VARCHAR(150) COLLATE UTF8MB4_BIN DEFAULT NULL COMMENT '课程',
  `USER_ID` VARCHAR(150) COLLATE UTF8MB4_BIN DEFAULT NULL COMMENT '用户',
  `ABSORPTION` VARCHAR(150) COLLATE UTF8MB4_BIN DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKHOT4FB35G9IRRFWIE08CIEUVO` (`COURSE_ID`),
  KEY `USER_ID` (`USER_ID`),
  CONSTRAINT `FKHOT4FB35G9IRRFWIE08CIEUVO` FOREIGN KEY (`COURSE_ID`) REFERENCES `COURSE_P` (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE=UTF8MB4_BIN;

-- ----------------------------
--  records of `feedback_p`
-- ----------------------------
begin;
insert into `FEEDBACK_P` values ('40289f6d68dc9e7a0168ea096d200001', '测试', '2019-02-14 11:25:13', '1', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '1', null, '0-30'), ('40289f6d68dc9e7a0168ea0c306e0002', '测试', '2019-02-14 11:28:14', '2', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '1', null, '0-30'), ('40289f6e68d787590168d79d539b0001', '测试', '2019-02-10 21:33:59', '1', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '1', '40289f6e68d6b54b0168d6b85f770001', '70-90'), ('40289f6e68d787590168d79fdf760003', '测试', '2019-02-10 21:36:45', '2', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '1', '40289f6e68d787590168d79f584a0002', '90以上'), ('40289f6e68d7a6180168d7ba15480006', '测试', '2019-02-10 22:05:23', '1', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '1', '40289f6e68d7a6180168d7b973770005', '70-90'), ('40289f6e68ea3d1a0168ea3df6ed0000', '测试', '2019-02-14 12:22:36', '1', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '1', 'e0de22fe-2c50-4216-ad75-ed0494d2dc92', '0-30'), ('40289f6e68ea8cb60168ea9363920000', '测试', '2019-02-14 13:55:54', '1', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '1', '40289f6e68d6b54b0168d6b85f770001', '50-70'), ('40289f6e68ea8cb60168ea93d3090001', '测试', '2019-02-14 13:56:23', '2', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '1', '40289f6e68d6b54b0168d6b85f770001', '30-50'), ('40289f6e68ea9e340168eaa8f8110000', '测试', '2019-02-14 14:19:29', '1', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '-1', '40289f6e68d6b54b0168d6b85f770001', '70-90'), ('40289f6e68ea9e340168eaa9577a0001', '测试', '2019-02-14 14:19:53', '2', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '-1', '40289f6e68d6b54b0168d6b85f770001', '90以上'), ('40289f6e68ea9e340168eaaee3930002', '测试', '2019-02-14 14:25:57', '1', '梳理快点放假啊师傅阿斯利康的风景sd发生地方\nasdfa是阿三a时代方式\n阿斯顿发阿三\n阿斯顿发\n阿三\n撒地方 ', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '-1', 'e0de22fe-2c50-4216-ad75-ed0494d2dc92', '90以上'), ('40289f6e68f0515c0168f05df5600002', '测试', '2019-02-15 16:55:16', '2', '清楚', '预习hibernate', '睡得太晚', '11：30之前一定要睡觉', '4', '40289f6e68d6b54b0168d6b85f770001', '30-50');
commit;

-- ----------------------------
--  TABLE STRUCTURE FOR `GRADE_EVENT_P`
-- ----------------------------
DROP TABLE IF EXISTS `GRADE_EVENT_P`;
CREATE TABLE `GRADE_EVENT_P` (
  `ID` VARCHAR(150) NOT NULL,
  `REMARK` TEXT NOT NULL COMMENT '描述',
  `CREATE_TIME` DATETIME NOT NULL COMMENT '创建日期',
  `CREATE_BY` VARCHAR(150) NOT NULL COMMENT '操作人',
  PRIMARY KEY (`ID`),
  KEY `CREATE_BY` (`CREATE_BY`),
  KEY `CREATE_BY_2` (`CREATE_BY`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- ----------------------------
--  TABLE STRUCTURE FOR `GRADE_P`
-- ----------------------------
DROP TABLE IF EXISTS `GRADE_P`;
CREATE TABLE `GRADE_P` (
  `ID` VARCHAR(150) NOT NULL,
  `USER_ID` VARCHAR(150) NOT NULL COMMENT '学员',
  `GRADE` VARCHAR(150) NOT NULL COMMENT '成绩',
  `STAGE_ID` VARCHAR(150) NOT NULL COMMENT '阶段',
  PRIMARY KEY (`ID`),
  KEY `STAGE_ID` (`STAGE_ID`),
  KEY `USER_ID` (`USER_ID`),
  KEY `USER_ID_2` (`USER_ID`),
  CONSTRAINT `FK_STAGE_ID` FOREIGN KEY (`STAGE_ID`) REFERENCES `STAGE_P` (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- ----------------------------
--  TABLE STRUCTURE FOR `LEAVE_P`
-- ----------------------------
DROP TABLE IF EXISTS `LEAVE_P`;
CREATE TABLE `LEAVE_P` (
  `ID` VARCHAR(150) NOT NULL,
  `START_DATE` DATETIME DEFAULT NULL COMMENT '开始日期',
  `END_DATE` DATETIME DEFAULT NULL COMMENT '结束日期',
  `TOTAL_DAY` INT(11) DEFAULT NULL COMMENT '天数',
  `REASON` VARCHAR(150) DEFAULT NULL COMMENT '原因',
  `PROCESS_INSTANCE_ID` VARCHAR(150) DEFAULT NULL COMMENT '任务编号',
  `CREATE_BY` VARCHAR(150) DEFAULT NULL COMMENT '请假人',
  `CREATE_TIME` DATETIME DEFAULT NULL COMMENT '请假日期',
  `REVIEWER` VARCHAR(150) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- ----------------------------
--  records of `leave_p`
-- ----------------------------
begin;
insert into `LEAVE_P` values ('40289f6d68be38080168be5170dd0000', '2019-02-06 08:00:00', '2019-02-08 08:00:00', '3', '哈', '', null, null, null), ('40289f6d68be38080168be5964b00001', '2019-02-05 08:00:00', '2019-02-08 08:00:00', '4', '生病', '', null, null, null), ('40289f6d68be618c0168be6241740000', '2019-02-05 08:00:00', '2019-02-08 08:00:00', '4', '结婚', '7501', 'cgx', '2019-02-05 23:58:57', null), ('40289f6d68dc9e7a0168ea02f22a0000', '2019-02-13 08:00:00', '2019-02-14 08:00:00', '2', '哈哈', '40009', 'cgx', '2019-02-14 11:18:08', 'txl'), ('40289f6e68d6c25b0168d6c351ec0000', '2019-02-11 08:00:00', '2019-02-12 08:00:00', '2', '结婚', '27508', 'xs', '2019-02-10 17:35:51', 'txl'), ('40289f6e68d6c25b0168d6cc72a90001', '2019-02-13 08:00:00', '2019-02-14 08:00:00', '2', '唱歌', '27526', 'xs', '2019-02-10 17:45:49', 'txl'), ('40289f6e68d6e5630168d73b10cb0000', '2019-02-10 08:00:00', '2019-02-11 08:00:00', '2', '结婚', '30001', 'xs', '2019-02-10 19:46:39', 'txl'), ('40289f6e68d6e5630168d73e9d2b0001', '2019-02-11 08:00:00', '2019-02-12 08:00:00', '2', '结婚生娃', '30024', 'xs', '2019-02-10 19:50:31', 'txl'), ('40289f6e68d6e5630168d740b8ff0002', '2019-02-05 08:00:00', '2019-02-06 08:00:00', '2', 'shengwa', '30043', 'xs', '2019-02-10 19:52:50', null), ('40289f6e68d6e5630168d7416d510003', '2019-02-11 08:00:00', '2019-02-12 08:00:00', '2', '生娃', '30054', 'xs', '2019-02-10 19:53:36', 'txl'), ('40289f6e68d6e5630168d7485d710004', '2019-02-10 08:00:00', '2019-02-14 08:00:00', '5', '娃娃啊我', '30073', 'xs', '2019-02-10 20:01:11', 'txl'), ('40289f6e68d772a90168d7789a580000', '2019-03-04 08:00:00', '2019-03-05 08:00:00', '2', '生娃娃', '32501', 'xs', '2019-02-10 20:53:52', 'txl'), ('40289f6e68d772a90168d784b8c00001', '2019-02-13 08:00:00', '2019-02-14 08:00:00', '2', 'shengb', '32530', 'xs', '2019-02-10 21:07:06', 'txl'), ('40289f6e68d787590168d78f7e4f0000', '2019-02-06 08:00:00', '2019-02-07 08:00:00', '2', '123', '35007', 'xs', '2019-02-10 21:18:52', 'txl'), ('40289f6e68d7a6180168d7b73cd90004', '2019-02-12 08:00:00', '2019-02-14 08:00:00', '3', '123123', '37501', '测试学生', '2019-02-10 22:02:17', 'txl'), ('40289f6e68f0515c0168f055b19c0000', '2019-02-15 08:00:00', '2019-02-16 08:00:00', '2', '肾病了', '45001', 'xs', '2019-02-15 16:46:14', 'txl'), ('40289f6e68f0515c0168f0583b680001', '2019-02-15 08:00:00', '2019-02-16 08:00:00', '2', 'changge', '45026', 'xs', '2019-02-15 16:49:01', 'txl'), ('4028ab5468c637e40168c638c00e0000', '2019-02-07 08:00:00', '2019-02-09 08:00:00', '3', 'bushufu', '12501', 'cgx', '2019-02-07 12:30:34', null), ('4028ab5468c637e40168c6a194cb0001', '2019-02-07 08:00:00', '2019-02-14 08:00:00', '8', '123', '12509', 'cgx', '2019-02-07 14:25:05', null), ('4028ab5468c6a9ce0168c6aa68dc0000', '2019-02-07 08:00:00', '2019-02-08 08:00:00', '2', '结婚', '15001', 'cgx', '2019-02-07 14:34:43', ''), ('4028ab5468c6a9ce0168c6ab25dc0001', '2019-02-07 08:00:00', '2019-02-08 08:00:00', '2', '手动', '15009', 'cgx', '2019-02-07 14:35:32', ''), ('4028ab5468c6a9ce0168c6acce230002', '2019-02-07 08:00:00', '2019-02-08 08:00:00', '1', '结婚', '15017', 'cgx', '2019-02-07 14:37:20', ''), ('4028ab5468c6a9ce0168c6adfac30003', '2019-02-07 08:00:00', '2019-02-08 08:00:00', '2', '121212', '15025', 'cgx', '2019-02-07 14:38:37', ''), ('4028ab5468c6a9ce0168c6afb9ea0004', '2019-02-07 08:00:00', '2019-02-08 08:00:00', '2', 'ceshi ', '15033', 'cgx', '2019-02-07 14:40:32', '谭小龙'), ('4028ab5468c6b1b10168c6b150460000', '2019-02-07 08:00:00', '2019-02-08 08:00:00', '2', '123', '17501', 'cgx', '2019-02-07 14:43:23', '谭小龙'), ('7e00808068c342170168c3429fac0000', '2019-02-06 08:00:00', '2019-02-09 08:00:00', '4', '生病', '10001', 'cgx', '2019-02-06 22:42:30', null), ('7e00808068cd7fdc0168d68e41b20000', '2019-02-10 08:00:00', '2019-02-12 08:00:00', '3', '结婚', '20050', 'cgx', '2019-02-10 16:37:54', '谭小龙');
commit;

-- ----------------------------
--  TABLE STRUCTURE FOR `MODULE_P`
-- ----------------------------
DROP TABLE IF EXISTS `MODULE_P`;
CREATE TABLE `MODULE_P` (
  `MODULE_ID` VARCHAR(150) COLLATE UTF8_BIN NOT NULL,
  `PARENT_ID` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `PARENT_NAME` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `NAME` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `LAYER_NUM` BIGINT(20) DEFAULT NULL,
  `IS_LEAF` BIGINT(20) DEFAULT NULL,
  `ICO` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CPERMISSION` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CURL` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CTYPE` BIGINT(20) DEFAULT NULL,
  `STATE` BIGINT(20) DEFAULT NULL,
  `BELONG` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CWHICH` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `QUOTE_NUM` BIGINT(20) DEFAULT NULL,
  `REMARK` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `ORDER_NO` BIGINT(20) DEFAULT NULL,
  `CREATE_BY` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CREATE_DEPT` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CREATE_TIME` DATETIME DEFAULT NULL,
  `UPDATE_BY` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `UPDATE_TIME` DATETIME DEFAULT NULL,
  PRIMARY KEY (`MODULE_ID`),
  KEY `FK9TYMO1KY2W3YRYDV8223OGQV7` (`PARENT_ID`),
  CONSTRAINT `MODULE_P_IBFK_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `MODULE_P` (`MODULE_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_BIN;

-- ----------------------------
--  records of `module_p`
-- ----------------------------
begin;
insert into `MODULE_P` values ('1', null, '', '系统首页', '1', '0', '', '系统首页', 'home', '0', '1', '', '', null, 'home', '1', '', '', null, '', null), ('101', '1', '系统首页', '我的留言板', '2', '0', '', '我的留言板', '', '1', '1', '', '', null, 'home', '6', '', '', null, '', null), ('102', '1', '系统首页', '我的代办任务', '2', '0', '', '我的代办任务', '', '1', '1', '', '', null, 'home', '7', '', '', null, '', null), ('103', '1', '系统首页', '请假单管理', '2', '0', '', '请假单管理', '', '1', '1', '', '', null, 'home', '8', '', '', null, '', null), ('3', null, '', '统计分析', '1', '0', '', '统计分析', 'stat', '0', '1', '', '', null, 'stat', '3', '', '', null, '', null), ('301', '3', '统计分析', '吸收情况', '2', '0', '', '吸收情况', '吸收情况', '1', '1', '', '', null, '吸收情况', '11', '', '', null, '', null), ('302', '3', '统计分析', '不清楚知识点', '2', '0', '', '不清楚知识点', '不清楚知识点', '1', '1', '', '', null, '不清楚知识点', '10', '', '', null, '', null), ('402881e561f9a7790161f9cdc7a20000', '5', '系统管理', 'test2', '22', '0', '', 'test2', 'test2', '1', '0', '', '', '0', 'test2', '1', '', '', null, '', null), ('40289f6d68bc05490168bc24c0120000', null, null, '工作流', '1', null, null, '工作流', '', '0', '1', null, null, null, '工作流', null, null, null, null, null, null), ('40289f6d68bc05490168bc250e170001', '40289f6d68bc05490168bc24c0120000', null, '请假申请', '2', null, null, '请假申请', '', '1', '1', null, null, null, '请假申请', null, null, null, null, null, null), ('40289f6d68be618c0168c337adce0001', '40289f6d68bc05490168bc24c0120000', null, '我的任务', '2', null, null, '我的任务', '', '1', '1', null, null, null, '我的任务', null, null, null, null, null, null), ('40289f6e68a8776e0168a8780e540000', null, null, '学习管理', '1', null, null, '学习管理', '学习管理', '0', '1', null, null, null, '学习管理', '1', null, null, null, null, null), ('40289f6e68a880bb0168a882da220000', '40289f6e68a8776e0168a8780e540000', null, '学习反馈', '2', null, null, '学习反馈', '学习反馈', '1', '1', null, null, null, '学习反馈', '1', null, null, null, null, null), ('40289f6e68aea8780168b243058b0002', null, null, '教学管理', '1', null, null, '教学管理', '', '0', '1', null, null, null, '教学管理', null, null, null, null, null, null), ('40289f6e68aea8780168b2435ce10003', '40289f6e68aea8780168b243058b0002', null, '教学反馈', '2', null, null, '教学反馈', '', '1', '1', null, null, null, '教学反馈', null, null, null, null, null, null), ('40289f6e68aea8780168b24390880004', '40289f6e68aea8780168b243058b0002', null, '学员信息跟踪', '2', null, null, '学员信息跟踪', '', '1', '1', null, null, null, '学员信息跟踪', null, null, null, null, null, null), ('40289f6e68b2b2f00168b3ef8be80002', '40289f6e68aea8780168b243058b0002', null, '课程管理', '2', null, null, '课程管理', '', '1', '1', null, null, null, '课程管理', null, null, null, null, null, null), ('5', null, '', '系统管理', '1', '0', '', '系统管理', 'sysadmin', '0', '1', '', '', null, 'sysadmin', '5', '', '', null, '', null), ('501', '5', '系统管理', '部门管理', '2', '0', '', '部门管理', 'system/dept', '1', '1', '', '', null, 'system', '18', '', '', null, '', null), ('502', '5', '系统管理', '用户管理', '2', '0', '', '用户管理', 'system/user', '1', '1', '', '', null, 'system', '19', '', '', null, '', null), ('503', '5', '系统管理', '角色管理', '2', '0', '', '角色管理', 'system/role', '1', '1', '', '', null, 'system', '20', '', '', null, '', null), ('504', '5', '系统管理', '模块管理', '2', '0', '', '模块管理', 'system/module', '1', '1', '', '', null, 'system', '21', '', '', null, '', null), ('6', null, '', '流程管理', '1', '0', '', '流程管理', 'activiti', '0', '1', '', '', null, 'activiti', '60', '', '', null, '', null), ('601', '6', '流程管理', '部署流程', '2', '0', '', '部署流程', 'flow/flowaction_deploy.action', '1', '1', '', '', null, 'activiti', '61', '', '', null, '', null), ('602', '6', '流程管理', '查询流程', '2', '0', '', '查询流程', 'flow/flowaction_queryprocessdefinition.action', '1', '1', '', '', null, 'activiti', '62', '', '', null, '', null), ('603', '6', '流程管理', '添加采购单', '2', '0', '', '添加采购单', 'flow/orderflowaction_addorder.action', '1', '1', '', '', null, 'activiti', '63', '', '', null, '', null), ('604', '6', '流程管理', '查询采购单', '2', '0', '', '查询采购单', 'flow/orderflowaction_ordertasklist.action', '1', '1', '', '', null, 'activiti', '64', '', '', null, '', null);
commit;

-- ----------------------------
--  table structure FOR `ROLE_MODULE_P`
-- ----------------------------
DROP TABLE IF EXISTS `ROLE_MODULE_P`;
CREATE TABLE `ROLE_MODULE_P` (
  `MODULE_ID` VARCHAR(150) COLLATE UTF8_BIN NOT NULL,
  `ROLE_ID` VARCHAR(150) COLLATE UTF8_BIN NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`MODULE_ID`),
  KEY `FK1LTRAY4ICRB1KT6EHGO70X8NM` (`MODULE_ID`),
  CONSTRAINT `ROLE_MODULE_P_IBFK_1` FOREIGN KEY (`MODULE_ID`) REFERENCES `MODULE_P` (`MODULE_ID`),
  CONSTRAINT `ROLE_MODULE_P_IBFK_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLE_P` (`ROLE_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_BIN;

-- ----------------------------
--  records of `role_module_p`
-- ----------------------------
begin;
insert into `ROLE_MODULE_P` values ('1', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('101', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('102', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('103', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('3', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('301', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('302', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('402881e561f9a7790161f9cdc7a20000', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('40289f6d68bc05490168bc24c0120000', '40289f6e68a880bb0168a8864eb90001'), ('40289f6d68bc05490168bc24c0120000', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('40289f6d68bc05490168bc250e170001', '40289f6e68a880bb0168a8864eb90001'), ('40289f6d68bc05490168bc250e170001', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('40289f6d68be618c0168c337adce0001', '40289f6e68a880bb0168a8864eb90001'), ('40289f6d68be618c0168c337adce0001', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('40289f6e68a8776e0168a8780e540000', '40289f6e68a880bb0168a8864eb90001'), ('40289f6e68a8776e0168a8780e540000', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('40289f6e68a880bb0168a882da220000', '40289f6e68a880bb0168a8864eb90001'), ('40289f6e68a880bb0168a882da220000', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('40289f6e68aea8780168b243058b0002', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('40289f6e68aea8780168b2435ce10003', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('40289f6e68aea8780168b24390880004', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('40289f6e68b2b2f00168b3ef8be80002', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('5', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('501', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('502', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('503', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('504', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('6', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('601', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('602', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('603', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('604', '4028a1cd4ee2d9d6014ee2df4c6a0007');
commit;

-- ----------------------------
--  table structure foR `ROLE_P`
-- ----------------------------
DROP TABLE IF EXISTS `ROLE_P`;
CREATE TABLE `ROLE_P` (
  `ROLE_ID` VARCHAR(150) COLLATE UTF8_BIN NOT NULL,
  `NAME` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `REMARK` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `ORDER_NO` INT(11) DEFAULT NULL,
  `CREATE_BY` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CREATE_DEPT` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CREATE_TIME` DATETIME DEFAULT NULL,
  `UPDATE_BY` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `UPDATE_TIME` DATETIME DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_BIN;

-- ----------------------------
--  RECORDS OF `ROLE_P`
-- ----------------------------
BEGIN;
INSERT INTO `ROLE_P` Values ('40289f6e68a880bb0168a8864eb90001', '学生', '学生', '2', null, null, null, null, null), ('40289f6e68a89b110168a89cd1550001', '测试', '测试', '1', null, null, null, null, null), ('4028a1cd4ee2d9d6014ee2df4c6a0007', '老师', '老师', '7', '', '', null, '', null);
commit;

-- ----------------------------
--  TABLE STRUCTURE FOR `ROLE_USER_P`
-- ----------------------------
DROP TABLE IF EXISTS `ROLE_USER_P`;
CREATE TABLE `ROLE_USER_P` (
  `USER_ID` VARCHAR(150) COLLATE UTF8_BIN NOT NULL,
  `ROLE_ID` VARCHAR(150) COLLATE UTF8_BIN NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FKIGODWQBUWMMOX4U8I9FKPGCLP` (`ROLE_ID`),
  CONSTRAINT `ROLE_USER_P_IBFK_1` FOREIGN KEY (`USER_ID`) REFERENCES `USER_P` (`USER_ID`),
  CONSTRAINT `ROLE_USER_P_IBFK_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `ROLE_P` (`ROLE_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_BIN;

-- ----------------------------
--  RECORDS OF `ROLE_USER_P`
-- ----------------------------
BEGIN;
INSERT INTO `ROLE_USER_P` values ('40289f6e68d6b54b0168d6b85f770001', '40289f6e68a880bb0168a8864eb90001'), ('40289f6e68d787590168d79f584a0002', '40289f6e68a880bb0168a8864eb90001'), ('40289f6e68d7a6180168d7a684c20000', '40289f6e68a880bb0168a8864eb90001'), ('40289f6e68d7a6180168d7ac9bd40001', '40289f6e68a880bb0168a8864eb90001'), ('40289f6e68d7a6180168d7b549320002', '40289f6e68a880bb0168a8864eb90001'), ('40289f6e68d7a6180168d7b6f7540003', '40289f6e68a880bb0168a8864eb90001'), ('40289f6e68d7a6180168d7b973770005', '40289f6e68a880bb0168a8864eb90001'), ('40289f6e68d694480168d698abef0001', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('40289f6e68d6b54b0168d6b6f84b0000', '4028a1cd4ee2d9d6014ee2df4c6a0007'), ('e0de22fe-2c50-4216-ad75-ed0494d2dc92', '4028a1cd4ee2d9d6014ee2df4c6a0007');
commit;

-- ----------------------------
--  TABLE STRUCTURE FOR `STAGE_P`
-- ----------------------------
DROP TABLE IF EXISTS `STAGE_P`;
CREATE TABLE `STAGE_P` (
  `ID` VARCHAR(150) NOT NULL,
  `NAME` VARCHAR(150) NOT NULL COMMENT '阶段名字',
  `TEACHER` VARCHAR(150) NOT NULL COMMENT '阶段老师',
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- ----------------------------
--  TABLE STRUCTURE FOR `TRACK_P`
-- ----------------------------
DROP TABLE IF EXISTS `TRACK_P`;
CREATE TABLE `TRACK_P` (
  `ID` VARCHAR(150) NOT NULL,
  `DESCRIPTION` TEXT COMMENT '描述',
  `USER_ID` VARCHAR(150) DEFAULT NULL COMMENT '学生',
  `CREATE_TIME` DATETIME DEFAULT NULL COMMENT '创建日期',
  `CREATE_BY` VARCHAR(150) DEFAULT NULL COMMENT '创建者',
  `STATUS` INT(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`ID`),
  KEY `USER_ID` (`USER_ID`),
  KEY `USER_ID_2` (`USER_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- ----------------------------
--  RECORDS OF `TRACK_P`
-- ----------------------------
BEGIN;
INSERT INTO `TRACK_P` values ('40289f6e68f022400168f02ccd090003', '121212', '40289f6e68d6b54b0168d6b85f770001', null, null, '3'), ('40289f6e68f022400168f02ed07b0004', '表达的粗来', '40289f6e68d7a6180168d7ac9bd40001', null, null, '3'), ('40289f6e68f02f2e0168f0303ca70000', '1，上课打瞌睡\n2，不尊重别人', '40289f6e68d6b54b0168d6b85f770001', '2019-02-15 16:05:13', 'cgx', '4'), ('40289f6e68f033fc0168f034c1b10000', '非常难得啊', '40289f6e68d6b54b0168d6b85f770001', '2019-02-15 16:10:09', 'cgx', '3'), ('40289f6e68f035d90168f03a8f800000', '5555', '40289f6e68d7a6180168d7ac9bd40001', '2019-02-15 16:16:36', 'cgx', '3'), ('40289f6e68f03bd40168f03c82a50000', '11111', '40289f6e68d7a6180168d7b973770005', '2019-02-15 16:18:44', 'cgx', '3'), ('40289f6e68f04ea30168f04eda120000', null, null, '2019-02-15 16:38:46', 'cgx', null), ('40289f6e68f04ea30168f04f21570001', null, null, '2019-02-15 16:39:04', 'cgx', null);
commit;

-- ----------------------------
--  TABLE STRUCTURE FOR `USER_INFO_P`
-- ----------------------------
DROP TABLE IF EXISTS `USER_INFO_P`;
CREATE TABLE `USER_INFO_P` (
  `USER_INFO_ID` VARCHAR(150) COLLATE UTF8_BIN NOT NULL,
  `NAME` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `JOIN_DATE` DATETIME DEFAULT NULL,
  `SALARY` DOUBLE DEFAULT NULL,
  `BIRTHDAY` DATETIME DEFAULT NULL,
  `GENDER` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `STATION` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `TELEPHONE` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `DEGREE` INT(11) DEFAULT NULL,
  `REMARK` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `ORDER_NO` INT(11) DEFAULT NULL,
  `EMAIL` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CREATE_BY` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CREATE_DEPT` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `CREATE_TIME` DATETIME DEFAULT NULL,
  `UPDATE_BY` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `UPDATE_TIME` DATETIME DEFAULT NULL,
  `MANAGER_ID` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  PRIMARY KEY (`USER_INFO_ID`),
  KEY `FKBSWA345IK76FTBQS90ASGHCF` (`MANAGER_ID`),
  CONSTRAINT `USER_INFO_P_IBFK_1` FOREIGN KEY (`MANAGER_ID`) REFERENCES `USER_P` (`USER_ID`),
  CONSTRAINT `USER_INFO_P_IBFK_2` FOREIGN KEY (`USER_INFO_ID`) REFERENCES `USER_P` (`USER_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_BIN;

-- ----------------------------
--  RECORDS OF `USER_INFO_P`
-- ----------------------------
BEGIN;
INSERT INTO `USER_INFO_P` values ('40289f6e68d694480168d698abef0001', '谭小龙', null, '0', null, '1', '', '', '4', '', null, 'yaorange_mail@sina.com', null, null, '2019-02-10 16:49:16', null, null, null), ('40289f6e68d6b54b0168d6b6f84b0000', '杨雅志', null, '0', null, '1', '', '', '4', '', null, 'yaorange_mail@sina.com', null, null, '2019-02-10 17:22:22', null, null, null), ('40289f6e68d6b54b0168d6b85f770001', '张三', null, '0', null, '1', '', '', '4', '', null, 'yaorange_mail@sina.com', null, null, '2019-02-10 17:23:54', null, null, null), ('40289f6e68d787590168d79f584a0002', 'xs1', null, null, null, null, null, null, null, null, null, null, null, null, '2019-02-10 21:36:11', null, null, null), ('40289f6e68d7a6180168d7a684c20000', 'andy', null, null, null, null, null, null, null, null, null, null, null, null, '2019-02-10 21:44:01', null, null, null), ('40289f6e68d7a6180168d7ac9bd40001', 'xs3', null, null, null, null, null, null, null, null, null, null, null, null, '2019-02-10 21:50:31', null, null, null), ('40289f6e68d7a6180168d7b549320002', 'andy23', null, null, null, null, null, null, null, null, null, null, null, null, '2019-02-10 22:00:09', null, null, null), ('40289f6e68d7a6180168d7b6f7540003', '测试学生', null, null, null, null, null, null, null, null, null, null, null, null, '2019-02-10 22:01:59', null, null, null), ('40289f6e68d7a6180168d7b973770005', 'xs5', null, null, null, null, null, null, null, null, null, null, null, null, '2019-02-10 22:04:42', null, null, null), ('e0de22fe-2c50-4216-ad75-ed0494d2dc92', '刘德华', '2018-03-29 00:00:00', '1000', '2018-03-01 00:00:00', '1', '拍片', '138802222221', '1', '1', '1', '3332222@qq.com', null, null, null, null, '2019-02-01 16:28:32', null);
commit;

-- ----------------------------
--  TABLE STRUCTURE FOR `USER_P`
-- ----------------------------
DROP TABLE IF EXISTS `USER_P`;
CREATE TABLE `USER_P` (
  `USER_ID` VARCHAR(150) COLLATE UTF8_BIN NOT NULL,
  `USER_NAME` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL COMMENT '姓名',
  `PASSWORD` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL COMMENT '密码',
  `STATE` INT(11) DEFAULT NULL COMMENT '状态',
  `CREATE_BY` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL COMMENT '创建者',
  `CREATE_DEPT` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL COMMENT '创建部门',
  `CREATE_TIME` DATETIME DEFAULT NULL COMMENT '创建日期',
  `UPDATE_BY` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL,
  `UPDATE_TIME` DATETIME DEFAULT NULL,
  `DEPT_ID` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL COMMENT '创建部门',
  `CLASS_ID` VARCHAR(150) COLLATE UTF8_BIN DEFAULT NULL COMMENT '班级',
  `NOTE_URL` VARCHAR(1024) COLLATE UTF8_BIN DEFAULT NULL COMMENT '笔记地址',
  PRIMARY KEY (`USER_ID`),
  KEY `FKI2Y187PXEHIQAY4RWH8CR6DLF` (`DEPT_ID`),
  KEY `CLASS_ID` (`CLASS_ID`),
  KEY `CLASS_ID_2` (`CLASS_ID`),
  KEY `CLASS_ID_3` (`CLASS_ID`),
  KEY `CLASS_ID_4` (`CLASS_ID`),
  KEY `CLASS_ID_5` (`CLASS_ID`),
  CONSTRAINT `USER_P_IBFK_1` FOREIGN KEY (`DEPT_ID`) REFERENCES `DEPT_P` (`DEPT_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_BIN;

-- ----------------------------
--  RECORDS OF `USER_P`
-- ----------------------------
BEGIN;
INSERT INTO `USER_P` Values ('40289f6e68d694480168d698abef0001', 'txl', '$2a$10$cgc.tpgib26bvltuzxoumu2xoftjjltpo0tt88hitgxyutkstrjj.', '1', null, null, null, null, null, null, null, null), ('40289f6e68d6b54b0168d6b6f84b0000', 'yyz', '$2a$10$hoi9oqrh1rrzc6dl9nm0e.q.e/fdit3uh8dioxbzylhvhdwz8sove', '1', null, null, null, null, null, null, null, null), ('40289f6e68d6b54b0168d6b85f770001', 'xs', '$2a$10$soyzsacp5g2/dlz0eocc7ul1slipl241s9ix/qmtwtzx2wsr0bbwq', '1', null, null, null, null, null, null, '1', null), ('40289f6e68d787590168d79f584a0002', 'xs1', '$2a$10$3hhezri1awsq0q7evpzou.jbaujaoiledwbsy8c7fnvq39brgkajs', '1', null, null, null, null, null, null, null, 'www.baidu.com'), ('40289f6e68d7a6180168d7a684c20000', 'admin', '$2a$10$sloc6jei1vgyl0ondn63cediywsjxdcqqsqksgriufhcemgz5nrt2', '1', null, null, null, null, null, null, null, 'www.baidu.com'), ('40289f6e68d7a6180168d7ac9bd40001', 'xs3', '$2a$10$duwyqfwzsql/5ujugwkxiecwyt/clhbjevj6fgcbn.6pemegfwfmm', '1', null, null, null, null, null, null, '2', 'www.baidu.com'), ('40289f6e68d7a6180168d7b549320002', 'andy23', '$2a$10$skbqpj/r28tdrcvnhtgwueyfb.si3liqvkiy1h2w2nlgfs64bm.bu', '1', null, null, null, null, null, null, '1', 'www.baidu.com'), ('40289f6e68d7a6180168d7b6f7540003', '测试学生', '$2a$10$rs06wvelscbx/y1jt/kd.obagcwk9ysyvolhchli58twjkglauyx.', '1', null, null, null, null, null, null, '1', 'www.baidu.com'), ('40289f6e68d7a6180168d7b973770005', 'xs5', '$2a$10$hbkx3.tryyn6tjj/rp8e1oyucpzsglzgbdrhqddevrs40jnzhrppa', '1', null, null, null, null, null, null, '2', 'www.baidu.com'), ('e0de22fe-2c50-4216-ad75-ed0494d2dc92', 'cgx', '$2a$10$ye4iikasurs7so4werx1duxsbpjbq6sbaumpdzycdj1ic4qrswl7g', '1', null, null, null, null, null, null, null, null);
commit;

set foreign_key_checks = 1;
