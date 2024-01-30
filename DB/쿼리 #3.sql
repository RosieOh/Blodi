CREATE DATABASE vviewnego;

USE vviewnego;

CREATE DATABASE edutech;

USE edutech;

CREATE DATABASE springboottest1;

USE springboottest1;

DROP DATABASE springboottest1;

-- 윈도우 노트북 한글 인코딩 안될 때 사용
ALTER DATABASE springboottest1 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

INSERT INTO member (mpw, mname, nickname, email, ACTIVE) VALUES ('1234', '관리자', 'ADMIN', 'sirious920@naver.com', 1);

SELECT * FROM member;

SHOW TABLES;

SELECT * FROM member_role_set;
