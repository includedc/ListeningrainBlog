#
# SQL Export
# Created by Querious (201026)
# Created: December 13, 2019 at 8:43:55 PM GMT+8
# Encoding: Unicode (UTF-8)
#

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `user_show_information`;
DROP TABLE IF EXISTS `tags`;
DROP TABLE IF EXISTS `metas`;
DROP TABLE IF EXISTS `contents`;
DROP TABLE IF EXISTS `comments`;


CREATE TABLE `comments` (
  `coid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'comment表主键',
  `cid` int(10) unsigned DEFAULT '0' COMMENT 'content表主键,关联字段',
  `parent` int(10) DEFAULT '0' COMMENT '父级评论',
  `top_level_id` int(11) DEFAULT NULL COMMENT '一级评论的id',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论生成时的GMT unix时间戳',
  `author` varchar(200) DEFAULT NULL COMMENT '评论作者',
  `avator` varchar(100) DEFAULT NULL COMMENT '头像地址',
  `author_id` int(10) unsigned DEFAULT '0' COMMENT '评论所属用户id',
  `owner_id` int(10) unsigned DEFAULT '0' COMMENT '评论所属内容作者id',
  `mail` varchar(200) DEFAULT NULL COMMENT '评论者邮件',
  `url` varchar(200) DEFAULT NULL COMMENT '评论者网址',
  `ip` varchar(64) DEFAULT NULL COMMENT '评论者ip地址',
  `agent` varchar(200) DEFAULT NULL COMMENT '评论者客户端',
  `content` text COMMENT '评论内容',
  `type` varchar(16) DEFAULT 'comment' COMMENT '评论类型',
  `status` varchar(16) DEFAULT 'approved' COMMENT '评论状态',
  `os_name` varchar(20) DEFAULT NULL COMMENT '评论人的操作系统',
  `address` varchar(20) DEFAULT NULL COMMENT '评论人的地址',
  `browser` varchar(10) DEFAULT NULL COMMENT '评论人的浏览器',
  PRIMARY KEY (`coid`),
  KEY `cid` (`cid`),
  KEY `created` (`created`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 COMMENT='评论表';


CREATE TABLE `contents` (
  `cid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'post表主键',
  `title` varchar(200) DEFAULT NULL COMMENT '内容标题',
  `slug` varchar(200) DEFAULT NULL COMMENT '内容缩略名',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '内容生成时的GMT unix时间戳',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '内容更改时的GMT unix时间戳',
  `content` text COMMENT '内容文字',
  `author_id` int(10) unsigned DEFAULT '0' COMMENT '内容所属用户id',
  `type` varchar(16) DEFAULT 'md' COMMENT '内容类别',
  `status` varchar(16) DEFAULT 'publish' COMMENT '内容状态',
  `tags` varchar(200) DEFAULT NULL COMMENT '标签列表',
  `categories` varchar(200) DEFAULT NULL COMMENT '分类列表',
  `hits` int(10) unsigned DEFAULT '0' COMMENT '点击次数',
  `comments_num` int(10) unsigned DEFAULT '0' COMMENT '内容所属评论数',
  `allow_comment` tinyint(1) DEFAULT '1' COMMENT '是否允许评论',
  `allow_ping` tinyint(1) DEFAULT '1' COMMENT '是否允许ping',
  `allow_feed` tinyint(1) DEFAULT '1' COMMENT '允许出现在聚合中',
  `address` varchar(30) DEFAULT NULL COMMENT '评论时的地址',
  `os_name` varchar(10) DEFAULT NULL COMMENT '评论人的操作系统',
  `browser` varchar(10) DEFAULT NULL COMMENT '评论人的浏览器',
  PRIMARY KEY (`cid`),
  UNIQUE KEY `slug` (`slug`),
  KEY `created` (`created`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8 COMMENT='内容表';


CREATE TABLE `metas` (
  `mid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '项目主键',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `slug` varchar(200) DEFAULT NULL COMMENT '项目缩略名',
  `type` varchar(32) NOT NULL DEFAULT '' COMMENT '项目类型',
  `description` varchar(200) DEFAULT NULL COMMENT '选项描述',
  `sort` int(10) unsigned DEFAULT '0' COMMENT '项目排序',
  `parent` int(10) unsigned DEFAULT '0',
  `status` tinyint(3) unsigned DEFAULT NULL COMMENT '状态',
  `content` text,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`mid`),
  KEY `slug` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='项目';


CREATE TABLE `tags` (
  `id` varchar(64) NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '标签名',
  `tid` varchar(64) DEFAULT NULL,
  `state` tinyint(4) NOT NULL COMMENT '1:正常 0:删除',
  `created` int(10) DEFAULT NULL COMMENT '标签创建时间',
  `updated` int(10) DEFAULT NULL COMMENT '最后更新的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';


CREATE TABLE `user_show_information` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户的id',
  `nickname` varchar(10) DEFAULT NULL COMMENT '昵称',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `address` varchar(50) DEFAULT NULL COMMENT '家庭住址',
  `famous_says` varchar(50) DEFAULT NULL COMMENT '座右铭',
  `follower` int(11) DEFAULT NULL COMMENT '粉丝',
  `touxiang` varchar(80) DEFAULT NULL COMMENT '头像地址',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='个人信息配置页';


CREATE TABLE `users` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'user表主键',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `password` varchar(64) DEFAULT NULL COMMENT '用户密码',
  `email` varchar(200) DEFAULT NULL COMMENT '用户的邮箱',
  `home_url` varchar(200) DEFAULT NULL COMMENT '用户的主页',
  `screen_name` varchar(32) DEFAULT NULL COMMENT '用户显示的名称',
  `created` int(10) unsigned DEFAULT '0' COMMENT '用户注册时的GMT unix时间戳',
  `activated` int(10) unsigned DEFAULT '0' COMMENT '最后活动时间',
  `logged` int(10) unsigned DEFAULT '0' COMMENT '上次登录最后活跃时间',
  `group_name` varchar(16) DEFAULT 'visitor' COMMENT '用户组',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`username`),
  UNIQUE KEY `mail` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';


INSERT INTO `comments` (`coid`, `cid`, `parent`, `top_level_id`, `author`, `avator`, `author_id`, `owner_id`, `mail`, `url`, `ip`, `agent`, `content`, `type`, `status`, `os_name`, `address`, `browser`) VALUES 
	(1,1,0,-1,'静心听雨',NULL,0,0,'listeningrain.cn@gmail.com','',NULL,NULL,'楼主你好帅啊<img src="https://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/2d/2018new_xiaoerbuyu_org.png" height="22" width="22" />','comment','approved',NULL,NULL,NULL);

INSERT INTO `contents` (`cid`, `title`, `slug`, `content`, `author_id`, `type`, `status`, `tags`, `categories`, `hits`, `comments_num`, `allow_comment`, `allow_ping`, `allow_feed`, `address`, `os_name`, `browser`) VALUES 
	(1,'第一篇文章',NULL,'> 第一篇文章该写点什么呢？\n\n    public static void main(String[] args){\n       System.out.println("Hello ListeningrainBlog!");\n    }\n\n<br>\n\n各位看官，既然来了，就在下方留下你们的评论吧!',0,'md','publish','','4',0,1,1,1,1,NULL,NULL,NULL);

INSERT INTO `metas` (`mid`, `name`, `slug`, `type`, `description`, `sort`, `parent`, `status`, `content`) VALUES 
	(1,'关于',NULL,'ABOUT',NULL,0,0,NULL,'## Hello World\n\n      这是程序员专用打招呼用语，你懂的！这是我的关于页面，其他的你自己来写吧！'),
	(2,'静心听雨的博客','https://listeningrain.cn','LINK',NULL,0,0,NULL,NULL),
	(3,'静心听雨的github','https://github.com/youyaa','LINK',NULL,1,0,NULL,NULL),
	(4,'默认分类',NULL,'CATEGORY',NULL,0,0,NULL,NULL),
	(5,'许嵩',NULL,'MOTTO','最爱的一句话',0,0,NULL,'如果你回头，不要放下我。。。');


INSERT INTO `user_show_information` (`Id`, `user_id`, `nickname`, `age`, `address`, `famous_says`, `follower`, `touxiang`) VALUES 
	(1,123,'我的站点',NULL,NULL,'站点描述',NULL,NULL);


INSERT INTO `users` (`uid`, `username`, `password`, `email`, `home_url`, `screen_name`, `created`, `activated`, `logged`, `group_name`) VALUES 
	(1,'root','cm9vdDEyMzQ1',NULL,NULL,NULL,6,1,7,'visitor');
