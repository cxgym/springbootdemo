SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `product_category`;
create table `product_category`
(
  `category_id` int(11) not null auto_increment,
  `category_name` varchar(64) default NULL,
  primary key (`category_id`)
) engine=InnoDB auto_increment=1 default charset=utf8;





