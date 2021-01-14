getUserByNickRequest=SELECT * FROM users WHERE nick_user = 'lani'
createNewUserRequest=INSERT INTO users (nick_user, role_user, status_user) VALUES ('lani', 'USER'::role_enum, 'OFFLINE'::status_enum)
changeUserStatusRequest=UPDATE users SET status_user = 'ONLINE'::status_enum WHERE ID = 3
getSystemUserRequest=SELECT * FROM users WHERE role_user = 'ADMIN'::role_enum
getUserByUserIDRequest=SELECT * FROM users WHERE ID = 3
checkingStatusRequest=SELECT status_user FROM users WHERE nick_user = 'Obama'
getAllUsersByStatusRequest=SELECT * FROM users WHERE status_user = 'LOGIN'::status_enum
sendMessageRequest=INSERT INTO messages (user_ID, data_message, text_message) VALUES(1, '2020-11-24 14:30:45', 'Good')
getLastRequest=SELECT * FROM messages m JOIN users u on m.user_ID = u.ID ORDER BY data_message DESC LIMIT 3





