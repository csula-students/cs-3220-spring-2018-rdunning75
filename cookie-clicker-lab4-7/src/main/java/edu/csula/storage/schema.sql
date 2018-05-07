# noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS generators;


CREATE TABLE users (
  id int PRIMARY KEY auto_increment,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL
);


CREATE TABLE generators (
  id int PRIMARY KEY auto_increment,
  name varchar(255) NOT NULL,
  description TEXT,
  rate INTEGER,
  base_cost INTEGER,
  unlock_at INTEGER,
  created_by int,
  INDEX (created_by)
);



CREATE TABLE events (
  id INT PRIMARY KEY auto_increment,
  name varchar(255) NOT NULL ,
  description text,
  trigger_at INT,
  created_by INT,
  INDEX (created_by)
);


INSERT INTO events (id, name, description, trigger_at) VALUE (1,"test","this is a test", 0);
INSERT INTO events (id, name, description, trigger_at) VALUE (2,"test","another test", 0);