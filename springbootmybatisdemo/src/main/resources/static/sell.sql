drop database if exists sell;
create database sell default character set utf8 collate utf8_general_ci;
use sell;

create table `product_category`
(
  `category_id` int not null auto_increment,
  `category_name` varchar(64),
  primary key (`category_id`)
);






