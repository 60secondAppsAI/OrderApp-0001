CREATE DATABASE IF NOT EXISTS OrderApp;

CREATE TABLE OrderApp.orders (
    `order_id` INT AUTO_INCREMENT,
    `location_id` INT,
    `guest_id` INT,
    `order_type` VARCHAR(255),
    `status` VARCHAR(255),
    `created_at` DATETIME,
    `total_amount` DOUBLE,
PRIMARY KEY (order_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE OrderApp.order_items (
    `order_item_id` INT AUTO_INCREMENT,
    `order_id` INT,
    `menu_item_id` INT,
    `quantity` INT,
    `unit_price` DOUBLE,
PRIMARY KEY (order_item_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE OrderApp.order_modifiers (
    `order_modifier_id` INT AUTO_INCREMENT,
    `order_item_id` INT,
    `modifier_name` VARCHAR(255),
    `price_adjustment` DOUBLE,
PRIMARY KEY (order_modifier_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE OrderApp.order_statuss (
    `order_status_id` INT AUTO_INCREMENT,
    `order_id` INT,
    `status` VARCHAR(255),
    `changed_at` DATETIME,
PRIMARY KEY (order_status_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
