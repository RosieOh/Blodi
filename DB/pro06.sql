CREATE database pro06_test;

use pro06_test;

show tables pro06_test;

CREATE TABLE member(
                       mno BIGINT PRIMARY KEY AUTO_INCREMENT,            -- 고유번호
                       id VARCHAR(20) UNIQUE KEY NOT NULL,             -- 로그인 아이디
                       pw VARCHAR(300) NOT NULL,                       -- 비밀번호
                       membername VARCHAR(100) NOT NULL,                     -- 이름
                       tel VARCHAR(20) NOT NULL,                       -- 전화번호
                       email VARCHAR(100),                             -- 이메일
                       addr1 VARCHAR(100),                             -- 주소
                       addr2 VARCHAR(200),                             -- 상세 주소
                       addrSchool VARCHAR(100),                        -- 학교 주소
                       role_id int,									-- role 테이블 참조
                       status VARCHAR(50) DEFAULT 'ACTIVE',            -- REMOVE(삭제), DORMANT(휴면), ACTIVE(활동)
                       createAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- 회원 등록일
                       loginAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    -- 마지막 로그인
                       constraint key_name unique(id);					-- 아이디 중복 방지
FOREIGN KEY (role_id) REFERENCES role(role_id)  --
);


CREATE TABLE role(
                     role_id INT PRIMARY KEY AUTO_INCREMENT,
                     role VARCHAR(255) DEFAULT NULL
);

-- 역할 추가
INSERT INTO role (role) VALUES ('ADMIN');
INSERT into role (role) values ('TEACHER');
Insert into role (role) values ('STUDENT');


-- 'admin' 역할에 대한 role_id 확인
SELECT role_id FROM role WHERE role = 'admin';

-- member 테이블에 있는 특정 회원에게 'admin' 역할 할당
UPDATE member SET role_id = [admin] WHERE id = '[dhxogns]';

SELECT * from role;

-- 업로드 파일 관리
CREATE TABLE fileData(
                         fileNo BIGINT PRIMARY KEY AUTO_INCREMENT,
                         tableName VARCHAR(100),                     -- 테이블 이름
                         columnNo INT,                               -- 테이블 PK 번호
                         originName VARCHAR(255),                    -- 원래 이름
                         saveName VARCHAR(255),                      -- 저장 이름
                         savePath VARCHAR(255),                      -- 저장 파일
                         fileType VARCHAR(100),                      -- 파일 종류
                         status VARCHAR(50) DEFAULT 'ACTIVE',         -- REMOVE(삭제), ACTIVE(활동)
                         bno bigint
);


create table board(
                      bno bigint auto_increment primary key,
                      title varchar(500) not null,
                      content varchar(2000) not null,
                      img VARCHAR(1000),
                      writer varchar(50) not null,
                      resdate timestamp DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY(writer) REFERENCES member(mno) ON DELETE CASCADE;
);

CREATE TABLE notice(
                       no bigINT PRIMARY KEY AUTO_INCREMENT,
                       title VARCHAR(500) NOT NULL,
                       content VARCHAR(1000) NOT NULL,
                       writer VARCHAR(50),
                       img VARCHAR(1000),
                       resdate timestamp DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY(writer) REFERENCES member(mno) ON DELETE CASCADE
)


CREATE table faq(
                    fno bigint auto_increment primary key,
                    question varchar(1000) not null,
                    answer varchar(1000) not null,
                    cnt int default 0 not null
);

create table qna (
                     qno bigint auto_increment primary key,
                     title varchar(500) not null,
                     content varchar(2000) not null,
                     writer varchar(50) not null,
                     resdate timestamp DEFAULT CURRENT_TIMESTAMP,
                     FOREIGN KEY(writer) REFERENCES member(mno) ON DELETE CASCADE
);

create table studentCTLboard(
                                sbno bigint auto_increment primary key,
                                title varchar(500) not null,
                                content varchar(2000) not null,
                                writer varchar(50) not null,
                                saveFile VARCHAR(300) NOT NULL,
                                resdate timestamp DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY(writer) REFERENCES member(mno) ON DELETE CASCADE
);

create table teacherCTLboard(
                                tbno bigint auto_increment primary key,
                                title varchar(500) not null,
                                content varchar(2000) not null,
                                writer varchar(50) not null,
                                saveFile VARCHAR(300) NOT NULL,
                                resdate timestamp DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY(writer) REFERENCES member(mno) ON DELETE CASCADE
);

create table teacherForStudentPortfolioBoard(
                                                tspbno bigint auto_increment primary key,
                                                title varchar(500) not null,
                                                content varchar(2000) not null,
                                                writer varchar(50) not null,
                                                saveFile VARCHAR(300) NOT NULL,
                                                resdate timestamp DEFAULT CURRENT_TIMESTAMP,
                                                FOREIGN KEY(writer) REFERENCES member(mno) ON DELETE CASCADE
);

create table teacherEntranceBoard(
                                     tebno bigint auto_increment primary key,
                                     title varchar(500) not null,
                                     content varchar(2000) not null,
                                     writer varchar(50) not null,
                                     saveFile VARCHAR(300) NOT NULL,
                                     resdate timestamp DEFAULT CURRENT_TIMESTAMP,
                                     FOREIGN KEY(writer) REFERENCES member(mno) ON DELETE CASCADE
);

create table studentEntranceBoard(
                                     sebno bigint auto_increment primary key,
                                     title varchar(500) not null,
                                     content varchar(2000) not null,
                                     writer varchar(50) not null,
                                     saveFile VARCHAR(300) NOT NULL,
                                     resdate timestamp DEFAULT CURRENT_TIMESTAMP,
                                     FOREIGN KEY(writer) REFERENCES member(mno) ON DELETE CASCADE
);


CREATE TABLE recomment(
                          NO INT PRIMARY KEY AUTO_INCREMENT,
                          mem_id VARCHAR(100) NOT NULL,
                          COMMENT VARCHAR(300) NOT null
);


-- 채팅방
CREATE TABLE chatRoom (
                          roomNo BIGINT PRIMARY KEY AUTO_INCREMENT,  -- 고유 번호
                          memId VARCHAR(20) NOT NULL,            -- member.id
                          status VARCHAR(50) DEFAULT 'ON',        -- ON(진행), OFF(차단)
                          UNIQUE (memId)                         -- memId를 UNIQUE 제약 설정
);

-- 채팅 메시지
CREATE TABLE chatMessage(
                            chatNo BIGINT PRIMARY KEY AUTO_INCREMENT,   -- 채팅 번호
                            type VARCHAR(20) NOT NULL,                  -- 채팅 타입: ENTER, TALK, LEAVE, NOTICE
                            roomNo INT NOT NULL,                        -- 채팅방 번호
                            sender VARCHAR(20) NOT NULL,                -- 송신자
                            receiver VARCHAR(20) NOT NULL,              -- 수신자
                            message VARCHAR(2000) NOT NULL,             -- 채팅 메시지
                            status VARCHAR(50) DEFAULT 'UNREAD',        -- 읽음 여부
                            time TIMESTAMP DEFAULT CURRENT_TIMESTAMP    -- 채팅 발송 시간
);





