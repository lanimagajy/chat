DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS users;
DROP TYPE IF EXISTS role_enum;
DROP TYPE IF EXISTS status_enum;


CREATE TYPE role_enum AS ENUM (
 'ADMIN',
 'USER',
 'SYSTEM'
);

CREATE TYPE status_enum AS ENUM (
 'ONLINE',
 'OFFLINE',
 'KICKED'
);

CREATE TABLE users (
    ID SERIAL,
    nick_user VARCHAR(25) NOT NULL,
	role_user role_enum NOT NULL,
	status_user status_enum NOT NULL,
  PRIMARY KEY(ID)
);

CREATE TABLE messages (
    ID SERIAL,
    user_ID INTEGER,
	data_message TIMESTAMP NOT NULL,
	text_message VARCHAR NOT NULL,
  PRIMARY KEY(ID),
	FOREIGN KEY(user_ID) REFERENCES users(ID)
);


 		INSERT INTO users (ID, nick_user, role_user, status_user)
 VALUES
 		('0', 'System', 'SYSTEM', 'OFFLINE');
		INSERT INTO users (nick_user, role_user, status_user)
 VALUES
		('lanimagajy@epam.com', 'ADMIN', 'ONLINE'),
		('Taras', 'USER', 'OFFLINE'),
		('Lebowski', 'USER', 'KICKED');
		INSERT INTO messages (user_ID, data_message, text_message)
 VALUES
		('1', '2020-11-24 14:30:45', 'First message'),
		('2', '2020-11-24 14:30:47', 'Second message'),
		('2', '2020-11-24 14:30:53', 'End message');