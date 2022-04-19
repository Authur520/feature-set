# WEEK06

6.（必做）基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面 2 周的作业依然要是用到这个表结构）。

``` sql
DROP TABLE IF EXISTS user_base_info;
CREATE TABLE user_base_info(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
    user_name varchar(32) NOT NULL COMMENT '用户名',
    user_pass_hash varchar(32) NOT NULL COMMENT '密码hash数值',
    create_time INT UNSIGNED NOT NULL COMMENT '创建用户时间戳',
    update_time INT UNSIGNED NOT NULL COMMENT '用户基本信息变更时间戳',
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户基本信息';

DROP TABLE IF EXISTS user_detail_info;
CREATE TABLE user_detail_info(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
    user_id BIGINT UNSIGNED UNIQUE NOT NULL COMMENT '用户ID',
    nick_name varchar(32) NOT NULL COMMENT '昵称',
    email VARCHAR(100) NOT NULL COMMENT '用户邮箱',
    gender ENUM('0', '1', '2') NOT NULL DEFAULT '0' COMMENT '性别',
    birthday DATE NOT NULL COMMENT '生日',
    phone_num VARCHAR(20) NOT NULL COMMENT '用户手机号',
		create_time INT UNSIGNED NOT NULL COMMENT '创建用户时间戳',
    update_time INT UNSIGNED NOT NULL COMMENT '用户基本信息变更时间戳',
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户详细信息';

DROP TABLE IF EXISTS product_base;
CREATE TABLE product_base(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
    product_name varchar(32) NOT NULL COMMENT '商品名称',
    cate_id BIGINT UNSIGNED NOT NULL COMMENT '商品分类id',
    create_time INT UNSIGNED NOT NULL COMMENT '创建商品信息时间戳',
    update_time INT UNSIGNED NOT NULL COMMENT '商品信息变更时间戳',
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '商品基本信息';

DROP TABLE  IF EXISTS product_detailed_info;
CREATE TABLE product_detailed_info(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
    product_id BIGINT UNSIGNED UNIQUE NOT NULL COMMENT '商品ID',
    title VARCHAR(50) NOT NULL DEFAULT '' COMMENT '商品标题',
    description VARCHAR(100) COMMENT '商品描述',
    num BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '库存',
    price DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '单价'
    is_hot ENUM('0', '1') NOT NULL DEFAULT '0' COMMENT '是否热卖',
    is_on_sale ENUM('0', '1') NOT NULL DEFAULT '0' COMMENT '是否上架',
    is_recommended ENUM('0', '1') NOT NULL DEFAULT '0' COMMENT '是否推荐',
    create_time INT UNSIGNED NOT NULL COMMENT '创建商品信息时间戳',
    update_time INT UNSIGNED NOT NULL COMMENT '商品信息变更时间戳',
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '商品详细信息';


DROP TABLE IF EXISTS order_info;
CREATE TABLE IF NOT EXISTS order_info (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单id',
    user_id BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '买方用户id',
  	product_id BIGINT UNSIGNED NULL DEFAULT '0' COMMENT '商品id',
    status TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '订单状态',
    trade_no VARCHAR(100) NOT NULL DEFAULT '' COMMENT '支付交易号',
  	transaction_price DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '订单交易价格',
    express_info_id VARCHAR(100) NOT NULL DEFAULT '' COMMENT '快递信息'
    create_time BIGINT UNSIGNED NOT NULL COMMENT '创建订单时间戳',
    update_time BIGINT UNSIGNED NOT NULL COMMENT '订单基本信息变更时间戳',
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '订单表';

DROP TABLE IF EXISTS express_info;
CREATE TABLE IF NOT EXISTS express_info(
    id BIGINT UNSIGNED NOT NULL COMMENT '订单id',
    customer_address VARCHAR(100) NOT NULL COMMENT '收货地址',
    express_id INT UNSIGNED NOT NULL DEFAULT '0' COMMENT '快递公司id',
    express_no VARCHAR(50) NOT NULL DEFAULT '' COMMENT '快递单号',
    status TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '快递状态',
		create_time BIGINT UNSIGNED NOT NULL COMMENT '快递信息创建时间',
    update_time BIGINT UNSIGNED NOT NULL COMMENT '快递信息更新时间',
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '快递详情';


```

