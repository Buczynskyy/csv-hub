DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id int AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  birth_date TIMESTAMP NOT NULL,
  phone_number int
);