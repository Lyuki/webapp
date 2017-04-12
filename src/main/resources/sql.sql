--DROP TABLE users,user_roles,topic,reply,attachment,poll,poll_ans,vote;

CREATE TABLE users (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(200) NOT NULL,
    role VARCHAR(100) NOT NULL
);

CREATE TABLE topic (
    id INTEGER  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    title VARCHAR(50) NOT NULL,
    msg VARCHAR(200),
    category VARCHAR(20) NOT NULL,
    username VARCHAR(50) NOT NULL
);

CREATE TABLE reply (
    id INTEGER  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    msg VARCHAR(200),
    topic_id INTEGER NOT NULL
);

CREATE TABLE attachment (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    topic_id INTEGER,
    reply_id INTEGER,
    username VARCHAR(50) NOT NULL,
    filename VARCHAR(255) DEFAULT NULL,
    content_type VARCHAR(255) DEFAULT NULL,
    content BLOB DEFAULT NULL
);

CREATE TABLE poll (
    id INTEGER  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    question VARCHAR(100),
    ans1 VARCHAR(100),
    ans2 VARCHAR(100),
    ans3 VARCHAR(100),
    ans4 VARCHAR(100)
);

CREATE TABLE vote (
    id INTEGER  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    poll_id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    ans_id INTEGER NOT NULL
);

INSERT INTO users(username, password) VALUES ('demo', '$2a$10$EjJpHUZWhb6rh7vhGIqfEeQtZcZpk6Hv0QdqbeH6GTXWeJWuw0y16');
INSERT INTO user_roles(username, role) VALUES ('demo', 'ROLE_ADMIN');

INSERT INTO users(username, password) VALUES ('test', '$2a$10$vKTzkyqhE6n8fRAdkbXV4.EI/FjPswxhGpDaH9H3TtVHxzspJ2WVK');
INSERT INTO user_roles(username, role) VALUES ('test', 'ROLE_USER');

INSERT INTO users(username, password) VALUES ('test2', '$2a$10$JV7pNjBQ1iJFjLMjSDkSAO/OTzO1Z/1Co8LdY/s31YZF.iNcRu2ii');
INSERT INTO user_roles(username, role) VALUES ('test2', 'ROLE_USER');

INSERT INTO poll(question,ans1,ans2,ans3,ans4) VALUES ('Select the fruit you like to eat', 'Apple', 'Orange', 'Lemon', 'Banana');
INSERT INTO poll(question,ans1,ans2,ans3,ans4) VALUES ('Do you have it is too much homework?', 'Of course!!!!', 'ok la', 'is enough', 'no!!!!');
INSERT INTO poll(question,ans1,ans2,ans3,ans4) VALUES ('Which mobile game you like most?', 'Shironeko', 'PAD', 'Tsum Tsum', 'idolist7');

INSERT INTO topic(title,msg,category,username) VALUES ('Welcome','I am demo.','Lecture','demo');
INSERT INTO topic(title,msg,category,username) VALUES ('Question','Why this course so boring?','Lecture','test');
INSERT INTO topic(title,msg,category,username) VALUES ('Nothing','I have nothing to say.','Lecture','demo');
INSERT INTO topic(title,msg,category,username) VALUES ('Lab','I dont like the course.','Lab','test');
INSERT INTO topic(title,msg,category,username) VALUES ('Level','The level of the course is too hard.','Lab','test');
INSERT INTO topic(title,msg,category,username) VALUES ('Hey!Say!JUMP','This is the best idol team ever.','Other','test');
INSERT INTO topic(title,msg,category,username) VALUES ('Arashi','Their film so fuuny','Other','test');
INSERT INTO topic(title,msg,category,username) VALUES ('Sexy Zone','Dont tell me you dont know','Other','test');

INSERT INTO reply(username,msg,topic_id) VALUES ('test','Hello, I am test.^^',1);
INSERT INTO reply(username,msg,topic_id) VALUES ('demo','Welcome test.',1);
INSERT INTO reply(username,msg,topic_id) VALUES ('test2','Hi.I am test2.',1);
INSERT INTO reply(username,msg,topic_id) VALUES ('demo','I also think that',2);
INSERT INTO reply(username,msg,topic_id) VALUES ('test','Why you say that?',3);
INSERT INTO reply(username,msg,topic_id) VALUES ('demo','No feeling.',4);
INSERT INTO reply(username,msg,topic_id) VALUES ('demo','Yes,also complex XD',5);
INSERT INTO reply(username,msg,topic_id) VALUES ('demo','I love them too.',6);
INSERT INTO reply(username,msg,topic_id) VALUES ('test','I love the song-Ainokatamari.',6);
INSERT INTO reply(username,msg,topic_id) VALUES ('demo','Me too. The lyrics is written by Domoto Tsuyoshi.',6);
INSERT INTO reply(username,msg,topic_id) VALUES ('demo','Yes.They are so famous.',7);
INSERT INTO reply(username,msg,topic_id) VALUES ('demo','I can tell you...I KNOW!!!!!',8);
INSERT INTO reply(username,msg,topic_id) VALUES ('test','haha, but I hate Fuma.',8);
INSERT INTO reply(username,msg,topic_id) VALUES ('test2','I hate him too. Syori is too handsome!!!!!',8);

INSERT INTO vote(poll_id,username,ans_id) VALUES (1,'demo',3);
INSERT INTO vote(poll_id,username,ans_id) VALUES (1,'test',3);
INSERT INTO vote(poll_id,username,ans_id) VALUES (1,'test2',4);
INSERT INTO vote(poll_id,username,ans_id) VALUES (2,'demo',1);
INSERT INTO vote(poll_id,username,ans_id) VALUES (2,'test2',1);
INSERT INTO vote(poll_id,username,ans_id) VALUES (2,'test',1);
INSERT INTO vote(poll_id,username,ans_id) VALUES (3,'demo',4);


