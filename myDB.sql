drop database rent_a_car;
create database rent_a_car;
use rent_a_car;

CREATE TABLE IF NOT EXISTS roles (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (name)
) ENGINE = InnoDB DEFAULT CHARACTER SET = cp1251 AUTO_INCREMENT=3; 

CREATE TABLE IF NOT EXISTS users (
  id INT(11) NOT NULL AUTO_INCREMENT,
  role_id INT(11) NOT NULL,
  login VARCHAR(20) NOT NULL,
  password VARCHAR(20) NOT NULL,
  user_status TINYINT(1) DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE (login)
) ENGINE = InnoDB DEFAULT CHARACTER SET = cp1251 AUTO_INCREMENT=3;

CREATE TABLE IF NOT EXISTS users_info (
  id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  first_name VARCHAR(20)NOT NULL,
  last_name VARCHAR(20)NOT NULL,
  passport_number VARCHAR(20)NOT NULL,
  phone_number VARCHAR(20)NOT NULL,
  email VARCHAR(40) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (email),
  UNIQUE (passport_number)
) ENGINE = InnoDB DEFAULT CHARACTER SET = cp1251;

CREATE TABLE IF NOT EXISTS cars (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  mark VARCHAR(20) NOT NULL,
  car_class VARCHAR(5) NOT NULL,
  car_price INT(11) NOT NULL DEFAULT 0,
  car_driver_price INT(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (id) 
) ENGINE = InnoDB DEFAULT CHARACTER SET = cp1251 AUTO_INCREMENT=11;

CREATE TABLE IF NOT EXISTS statuses (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (name)
) ENGINE = InnoDB DEFAULT CHARACTER SET = cp1251 AUTO_INCREMENT=5;

CREATE TABLE IF NOT EXISTS car_orders (
  id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  car_id INT(11) NOT NULL,
  driver_status TINYINT(1) DEFAULT 0,
  order_data DATE NOT NULL,
  return_data DATE NOT NULL,
  order_price INT(11) DEFAULT 0,
  status_id INT(11) NOT NULL DEFAULT 0,
  rejection_reason VARCHAR(45) DEFAULT NULL,
  damage TINYINT(1) DEFAULT 0,
  price_for_repairs INT(11) DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = cp1251;


ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE users_info ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE car_orders ADD FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE car_orders ADD FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE car_orders ADD FOREIGN KEY (status_id) REFERENCES statuses(id) ON DELETE CASCADE ON UPDATE RESTRICT;

INSERT INTO roles (id, name) VALUES
(0, 'admin'),
(1, 'manager'),
(2, 'client');

INSERT INTO users (id, role_id, login, password) VALUES
(1, 0, 'admin', 'admin'),
(2, 1, 'manager', 'manager'),
(3, 2, 'user', 'user');

INSERT INTO statuses (id, name) VALUES
(0, 'opend'),
(1, 'confirmed'),
(2, 'rejected'),
(3, 'paid'),
(4, 'closed');

INSERT INTO cars (id, name, mark, car_class, car_price, car_driver_price) VALUES 
(1, 'Opel Corsa', 'Opel', 'B', 500, 600),
(2, 'Camaro', 'Chevrolet', 'S', 5000, 5200),
(3, 'Audi A6', 'Audi', 'E', 1200, 1300),
(4, 'Porsche 911', 'Porsche', 'S', 3000, 3100),
(5, 'Audi A8', 'Audi', 'F', 3500, 3600),
(6, 'Ford Focus', 'Ford', 'C', 900, 1100),
(7, 'Jaguar XK', 'Jaguar', 'S', 3500, 3600),
(8, 'Maserati GranTurismo', 'Maserati', 'S', 6000, 6150),
(9, 'Peugeot 508', 'Peugeot', 'D', 900, 1000),
(10, 'Kia Rio', 'Kio Motors', 'B', 850, 1000);

INSERT INTO users_info (id, user_id, first_name, last_name, passport_number, phone_number, email) VALUES 
(1, 3, 'Alex', 'Yegorov', 'MB12345678', '0990304292', 'gjh@list.ru');

