ALTER TABLE `base_mall`.`goods_spu` ADD COLUMN `freight_templat_id` VARCHAR(32) NULL COMMENT '运费模板ID' AFTER `points_deduct_amount`; 
ALTER TABLE `base_mall`.`goods_sku` CHANGE `weight` `weight` DECIMAL(10,2) DEFAULT 0 NULL COMMENT '重量（kg）', CHANGE `volume` `volume` DECIMAL(10,2) DEFAULT 0 NULL COMMENT '体积（m³）'; 
ALTER TABLE `base_mall`.`order_info` CHANGE `logistics_price` `freight_price` DECIMAL(10,2) DEFAULT 0.00 NOT NULL COMMENT '运费金额' AFTER `appraises_status`, CHANGE `payment_price` `payment_price` DECIMAL(10,2) DEFAULT 0.00 NOT NULL COMMENT '款项金额（销售金额-积分抵扣金额-电子券抵扣金额）'; 
ALTER TABLE `base_mall`.`order_info` CHANGE `payment_price` `payment_price` DECIMAL(10,2) DEFAULT 0.00 NOT NULL COMMENT '支付金额（销售金额+运费金额-积分抵扣金额-电子券抵扣金额）' AFTER `payment_coupon_price`; 
ALTER TABLE `base_mall`.`order_item` ADD COLUMN `freight_price` DECIMAL(10,2) DEFAULT 0.00 NULL COMMENT '运费金额' AFTER `sales_price`, CHANGE `payment_price` `payment_price` DECIMAL(10,2) DEFAULT 0.00 NULL COMMENT '支付金额（购买单价*商品数量+运费金额-积分抵扣金额-电子券抵扣金额）'; 
UPDATE `base_mall`.`goods_sku` SET `weight` = 0 WHERE `weight` IS NULL;
UPDATE `base_mall`.`goods_sku` SET `volume` = 0 WHERE `volume` IS NULL;
ALTER TABLE `base_mall`.`goods_sku` CHANGE `weight` `weight` DECIMAL(10,2) DEFAULT 0.00 NOT NULL COMMENT '重量（kg）', CHANGE `volume` `volume` DECIMAL(10,2) DEFAULT 0.00 NOT NULL COMMENT '体积（m³）'; 


