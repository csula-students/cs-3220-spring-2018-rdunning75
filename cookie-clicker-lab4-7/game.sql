CREATE TABLE users (
  id int PRIMARY KEY auto_increment,
  username varchar(255) NOT NULL, 
  password varchar(255) NOT NULL 
);
DESCRIBE users;

CREATE TABLE generators (
  id int PRIMARY KEY auto_increment,
  name varchar(255) NOT NULL, 
  description TEXT,
  rate INTEGER,
  base_cost INTEGER,
  unlock_at INTEGER
);
ALTER TABLE generators ADD FOREIGN KEY (created_by) REFERENCES users(id);
DESCRIBE generators;

CREATE TABLE events (
    id INT PRIMARY KEY auto_increment,
    name varchar(255) NOT NULL ,
    description text,
    trigger_at INT,
    created_by INT
);
DESCRIBE events;

CREATE TABLE quantities (
    generator_id int NOT NULL,
    token varchar(255),
    quantity int
);
DESCRIBE quantities;

INSERT INTO users (id, password, username) VALUES (1, "cs3220password", "admin"), (2, "notapassword", "me");
SELECT * FROM users;

INSERT INTO generators (id,name,description,rate,base_cost,unlock_at,created_by) VALUES 
(1,"Grandma","Grandma likes to make cookies", 5, 10,10,1),
(2, "Factory", "Factory to produce cookies",10,50,50,1),
(3, "Mine", "Mining cookies", 20,200,200,2);
SELECT * FROM generators;

INSERT INTO events (created_by, description, id, name, trigger_at ) VALUES
(1,"You always know grandma likes to make cookies",1,"Grandma shows up",10),
(1, "Factory to produce cookies",2,"You can construct factory now!",50),
(2,"Mining cookies",3,"	We've found cookies in deep mountain ... in the mine?",200),
(2,"This is a sample event. Please delete me",4,"sample event",99999);
SELECT * FROM events;

INSERT INTO quantities (generator_id, quantity, token) VALUES 
(1,2,"c7a69d44e0b9b415b2d9956cb26b944a"),
(2,1,"c7a69d44e0b9b415b2d9956cb26b944a"),
(1,20,"80516ce4663c3bd0c8385309a2fe226e"),
(2,30,"80516ce4663c3bd0c8385309a2fe226e");
SELECT * FROM quantities;

UPDATE generators SET unlock_at=10, rate=1 WHERE name="Grandma";
SELECT * FROM generators;


SELECT generators.name, generators.description, generators.rate, generators.base_cost, generators.unlock_at, quantities.quantity FROM quantities left outer JOIN generators ON generators.id = quantities.generator_id WHERE token = "80516ce4663c3bd0c8385309a2fe226e"; 

SELECT * FROM generators WHERE generators.unlock_at = (SELECT MAX(unlock_at) FROM generators);

SELECT * FROM generators ORDER BY generators.unlock_at;

DELETE FROM events WHERE name ="sample event";
SELECT * FROM events;