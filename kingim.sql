use kingim;
CREATE TABLE kingim.friend
(
    id int(11) PRIMARY KEY NOT NULL COMMENT '自增主键' AUTO_INCREMENT,
    user_id varchar(20) COMMENT '用户id',
    friend_id varchar(20) COMMENT '好友id',
    build_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '建立时间',
    type_id int(11) COMMENT '好友分组id'
);

CREATE TABLE kingim.friend_apply
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    from_user_id varchar(20) NOT NULL COMMENT '申请人userId',
    to_user_id varchar(20) NOT NULL COMMENT '被申请人userId',
    remark varchar(255) COMMENT '申请原因',
    apply_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '申请时间',
    status int(11) NOT NULL COMMENT '申请状态：0待处理，1同意，2拒绝',
    friend_type_id int(11) NOT NULL COMMENT '好友分组id'
);

CREATE TABLE kingim.friend_message
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    from_user_id varchar(20) COMMENT '发消息的人的id',
    to_user_id varchar(20) COMMENT '收消息的人的id',
    content varchar(2000) COMMENT '消息内容',
    send_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '发送时间',
    is_read int(11) COMMENT '是否已读，1是0否',
    is_del int(11) COMMENT '是否删除，1是0否',
    is_back int(11) COMMENT '是否撤回，1是0否'
);

CREATE TABLE kingim.friend_type
(
    id int(11) PRIMARY KEY NOT NULL COMMENT '自增主键' AUTO_INCREMENT,
    type_name varchar(255) COMMENT '分组名',
    user_id varchar(20) COMMENT '用户id',
    build_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    is_default int(11) DEFAULT '0' COMMENT '是否为默认分组：1为默认，0为不是默认分组'
);

CREATE TABLE kingim.group_message
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id varchar(20) COMMENT '用户id',
    group_id int(11) COMMENT '群id',
    content varchar(2000) COMMENT '群消息内容',
    is_del int(11) COMMENT '是否删除，1是 0否',
    is_read int(11) COMMENT '是否已读，1是，0否。',
    send_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_back int(11) COMMENT '是否撤回，1是 0否'
);

CREATE TABLE kingim.group_user
(
    id int(11) unsigned PRIMARY KEY NOT NULL COMMENT '自增主键' AUTO_INCREMENT,
    user_id varchar(20) COMMENT '用户id',
    group_id int(11) COMMENT '群id',
    join_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '入群时间'
);

CREATE TABLE kingim.t_group
(
    id int(11) PRIMARY KEY NOT NULL COMMENT '自增' AUTO_INCREMENT,
    group_num varchar(255) COMMENT '群号',
    group_name varchar(255) COMMENT '群名称',
    avatar varchar(255) COMMENT '群logo',
    user_id varchar(20) COMMENT '群主userId',
    build_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '群创建时间',
    description varchar(200) COMMENT '群简介',
    status int(1) DEFAULT '1' COMMENT '群状态：1为正常，0为禁用'
);


CREATE TABLE kingim.t_user
(
    id int(11) PRIMARY KEY NOT NULL COMMENT '自增主键' AUTO_INCREMENT,
    user_name varchar(20) COMMENT '帐号',
    password varchar(255) COMMENT '密码',
    nick_name varchar(255) COMMENT '昵称',
    gender int(11) DEFAULT '0' COMMENT '性别：0为男，1为女',
    avatar varchar(255) COMMENT '头像',
    email varchar(255) COMMENT '电子邮箱',
    phone varchar(11) COMMENT '手机号码',
    role_code int(11) DEFAULT '1' COMMENT '角色code：1为用户，2为管理员',
    user_status int(11)  DEFAULT '1' COMMENT '用户状态：1为正常，0为禁止登录，2为禁止聊天',
    sign varchar(255) COMMENT '签名'
);
