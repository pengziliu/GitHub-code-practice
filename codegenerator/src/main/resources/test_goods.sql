CREATE TABLE `test_goods` (
  `id` bigint(20) DEFAULT NULL COMMENT 'id',
  `goods_sn` varchar(45) DEFAULT NULL COMMENT '商品编码',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `title` varchar(80) DEFAULT NULL COMMENT '标题',
  `price` decimal(10,2) DEFAULT NULL COMMENT '售价',
  `status` int(2) DEFAULT NULL COMMENT '商品状态',
  `sale_count` int(11) DEFAULT NULL COMMENT '销量',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8