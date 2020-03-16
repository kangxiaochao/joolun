ALTER TABLE `base_mall`.`order_item` ADD COLUMN `is_refund` CHAR(2) NULL COMMENT '是否退款0:否 1：是' AFTER `remark`, ADD COLUMN `status` CHAR(2) NULL COMMENT '状态1：退款中；2、拒绝退款；3、同意退款' AFTER `is_refund`; 


ALTER TABLE `base_mall`.`order_item` CHANGE `status` `status` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '状态1：退款申请中；2：退货中；3、拒绝退款；4、同意退款'; 


ALTER TABLE `base_mall`.`order_refunds` CHANGE `status` `status` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '退款状态1：退款申请中；2：退货中；3、拒绝退款；4、同意退款'; 


ALTER TABLE `base_mall`.`order_item` CHANGE `status` `status` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' NULL COMMENT '状态0：正常；1：退款中；2：退货退款中'; 

ALTER TABLE `base_mall`.`order_refunds` CHANGE `status` `status` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '退款状态0：撤销退换申请；11：退款申请中；111、同意退款；112、拒绝退款；12：退货退款申请中；121：同意退货退款/退货中；122：拒绝退货退款；1211：收到退货同意退款；1211：收到退货拒绝退款'; 


ALTER TABLE `base_mall`.`order_item` CHANGE `is_refund` `is_refund` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' NULL COMMENT '是否退款0:否 1：是'; 

UPDATE `base_mall`.`order_item` SET `status` = '0' WHERE `status` IS NULL;
UPDATE `base_mall`.`order_item` SET `is_refund` = '0' WHERE `is_refund` IS NULL;


ALTER TABLE `base_mall`.`order_refunds` CHANGE `status` `status` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '退款状态0：撤销退换申请；1：退款申请中；11、同意退款；12、拒绝退款；2：退货退款申请中；21：同意退货退款/退货中；22：拒绝退货退款；211：收到退货同意退款；212：收到退货拒绝退款'; 

ALTER TABLE `base_mall`.`order_item` CHANGE `status` `status` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' NULL COMMENT '状态0：正常；1：退款中；2：退货退款中；3：完成退款；4：完成退货退款'; 

ALTER TABLE `base_mall`.`order_refunds` CHANGE `status` `status` CHAR(6) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '退款状态0：撤销退换申请；1：退款申请中；11、同意退款；12、拒绝退款；2：退货退款申请中；21：同意退货退款/退货中；22：拒绝退货退款；211：收到退货同意退款；212：收到退货拒绝退款'; 








