create table if not exists `user` (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email char(255),
  password char(255),
  name char(255),
  gender boolean default 1,
  school char(255) default 'Sun Yat-sen University',
  enrollment int,
  dormitory char(255),
  studentCardURL char(255),
  avatarURL char(255),
  signature char(255),
  phone char(20),
  wechat char(30),
  qq char(20),
  Jin float default 0
) AUTO_INCREMENT = 1 default charset = utf8;

create table if not exists `task` (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  userid INT UNSIGNED,
  taskStatus boolean default 1,
  taskType boolean, 
  statusCode int,
  allocation boolean,
  totalValue char(255),
  value char(255),
  title char(255),
  descr char(255),
  startPosition char(255),
  endPosition char(255),
  beginTime char(255),
  ddl char(255),
  finishNum int default 0,
  acceptNum int default 0,
  confirmNum int default 0,
  totalNum int,
  acceptor char(255) default '',
  finishor char(255) default '',
  confiror char(255) default '',
  FOREIGN KEY (userid) REFERENCES user(id) ON DELETE CASCADE
) AUTO_INCREMENT = 1 default charset = utf8;

create table if not exists `notice` (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  taskid INT UNSIGNED,
  userid INT UNSIGNED,
  cuserid INT UNSIGNED,
  userType boolean,
  taskType boolean,
  title char(255),
  descr char(255),
  time char(255),
  status boolean,
  FOREIGN KEY (userid) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (cuserid) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (taskid) REFERENCES task(id) ON DELETE CASCADE
) AUTO_INCREMENT = 1 default charset = utf8;


create table if not exists `feedback` (
  id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  taskid INT UNSIGNED,
  userid INT UNSIGNED,
  puserid INT UNSIGNED,
  photourl char(255),
  finishtime char(255),
  descr char(255),
  whetherConfirm boolean default 0,
  FOREIGN KEY (userid) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (puserid) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (taskid) REFERENCES task(id) ON DELETE CASCADE
) AUTO_INCREMENT = 1 default charset = utf8;