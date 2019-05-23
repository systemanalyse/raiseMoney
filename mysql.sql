create table if not exists `UserInformation` (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username char(255),
  password char(255),
  name char(255),
  sex char(1) default 'M',
  school char(255) default 'Sun Yat-sen University',
  enrollment int default 2015,
  dormitorysite char(255),
  introduction char(255),
  phonenumber char(20),
  wechatid char(30),
  ppid char(20),
  email char(255),
  money int default 0,
  PPPnumber int default 0,
  PPRnumber int default 0,
  DDPnumber int default 0,
  DDRnumber int default 0,
  online boolean,
  time timestamp DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT = 1 default charset = utf8;

create table if not exists `DD` (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username char(255),
  title char(255),
  deadline char(255),
  totalnumber int,
  bonus int,
  bonus_pay_way int default 0,
  picture char(255),
  start_time char(255),
  received_number int default 0,
  time timestamp DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT = 1 default charset = utf8;

create table if not exists `PP` (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username char(255),
  title char(255),
  site char(255),
  deadline char(255),
  weight int,
  bonus int,
  picture char(255),
  status int,
  start_time char(255),
  time timestamp DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT = 1 default charset = utf8;

create table if not exists `Receiver` (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  uid char(255),
  received_time char(255),
  bonus int,
  time timestamp DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT = 1 default charset = utf8;
