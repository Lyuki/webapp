--DROP TABLE users,user_roles,topic,reply,attachment,poll,poll_ans,vote;

CREATE TABLE users (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL
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

/*INSERT INTO users(username, password) VALUES ('demo', 'demo');
INSERT INTO user_roles(username, role) VALUES ('demo', 'ROLE_ADMIN');*/

INSERT INTO users(username, password) VALUES ('demo', '$2a$10$TJP6EP88VGdHGyBSi/9N1O9It1HrnSoUv6qsjl6fDL6FVf/w00//y');
INSERT INTO user_roles(username, role) VALUES ('demo', 'ROLE_ADMIN');

INSERT INTO poll(question,ans1,ans2,ans3,ans4) VALUES ('Select the fruit you like to eat', 'Apple', 'Orange', 'Lemon', 'Banana');


