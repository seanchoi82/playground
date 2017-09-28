------------------------------------
-- MySQL에 missingu 이름으로 데이터베이스 생성 
------------------------------------
CREATE DATABASE missingu;

------------------------------------
-- missingu계정 생성 및 권한 설정
------------------------------------
GRANT ALL PRIVILEGES ON missingu.* to missingu@localhost
identified by 'missingu'
with grant option;

------------------------------------
-- APK 이력정보 테이블 생성
------------------------------------
CREATE TABLE `apk_hist_info`
(
	`apk_id`               INTEGER(5) PRIMARY KEY AUTO_INCREMENT,
	`apk_desc`             VARCHAR(100) NULL,
	`apk_version`          CHAR(10) NULL,
	`register_name`        CHAR(15) NULL,
	`reg_date`             DATETIME NULL,
	`apk_file_name`        VARCHAR(40) NULL
);

------------------------------------
-- 회원정보 테이블 생성
------------------------------------
--DROP TABLE mu_member
CREATE TABLE mu_member
(
	member_id            INTEGER(10) PRIMARY KEY AUTO_INCREMENT,
	status               CHAR(1) NOT NULL,
	login_id             VARCHAR(50) NOT NULL,
	login_pw             VARCHAR(50) NOT NULL,
	nick_name            VARCHAR(30) NULL,
	gcm_reg_id           VARCHAR(200) NULL,
	gcm_use_yn           CHAR(1) NOT NULL,
	sex                  VARCHAR(10) NULL,
	blood_type_cd        VARCHAR(10) NULL,
	birth_date           VARCHAR(8) NULL,
	lunar_solar_cd       VARCHAR(10) NULL,
	birth_time           VARCHAR(10) NULL,
	appearance_type_cd   VARCHAR(10) NULL,
	body_type_cd         VARCHAR(10) NULL,
	purpose_cd           VARCHAR(10) NULL,
	hobby_cd             VARCHAR(10) NULL,
	drinking_habit_cd    VARCHAR(10) NULL,
	smoking_habit_cd     VARCHAR(10) NULL,
	self_pr              VARCHAR(255) NULL,
	main_photo_url       VARCHAR(150) NULL,
	created_date         DATETIME NOT NULL,
	updated_date         DATETIME NULL,
	area_cd              VARCHAR(10) NULL,
	g_pos_x              VARCHAR(20) NULL,
	g_pos_y              VARCHAR(20) NULL,
	location			 VARCHAR(100) NULL
) ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX mu_member_ux1 ON mu_member
(
	login_id
);

ALTER TABLE mu_member auto_increment=1000000001;

------------------------------------
-- 회원속성 테이블 생성
------------------------------------
CREATE TABLE mu_member_attr
(
	member_id            INTEGER(10) NOT NULL,
	attr_name            VARCHAR(100) NULL,
	attr_value           VARCHAR(200) NULL,
	created_date         DATETIME NOT NULL,
	updated_date         DATETIME NULL
);

ALTER TABLE mu_member_attr ADD PRIMARY KEY (member_id, attr_name);

ALTER TABLE mu_member_attr ADD FOREIGN KEY mu_member_attr_fk1 (member_id) REFERENCES mu_member (member_id);


------------------------------------
-- 톡투미 테이블 생성
------------------------------------
CREATE TABLE mu_talktome
( 
	talk_id              BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	member_id            INTEGER(10) NOT NULL,
	title                VARCHAR(200) NULL,
	contents             TEXT NULL,
	read_cnt             MEDIUMINT NULL,
	reply_cnt            MEDIUMINT NULL,
	talk_photo_url       VARCHAR(200) NULL,
	talk_photo_big_url   VARCHAR(200) NULL,
	talk_photo_org_url   VARCHAR(200) NULL,
	created_date         DATETIME NULL,
	updated_date         DATETIME NULL, 
	hide_tag					INTEGER(10) NOT NULL DEFAULT 0,
);

ALTER TABLE mu_talktome ADD FOREIGN KEY mu_talktome_fk1 (member_id) REFERENCES mu_member (member_id);

CREATE INDEX mu_talktome_ix1 ON mu_talktome
(
	member_id
);

------------------------------------
-- 톡투미 리플 테이블 생성
------------------------------------
CREATE TABLE mu_talktome_reply
(
	reply_id             BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	talk_id              BIGINT UNSIGNED NOT NULL,
	reply_content        VARCHAR(250) NULL,
	member_id            INTEGER(10) NOT NULL,
	created_date         DATETIME NULL,
	updated_date         DATETIME NULL
);

ALTER TABLE mu_talktome_reply ADD FOREIGN KEY mu_talktome_reply_fk1 (talk_id) REFERENCES mu_talktome (talk_id);
ALTER TABLE mu_talktome_reply ADD FOREIGN KEY mu_talktome_reply_fk2 (member_id) REFERENCES mu_member (member_id);


------------------------------------
-- 채팅방 테이블 생성
------------------------------------
CREATE TABLE mu_chatroom
(
	room_id              BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	room_pw              VARCHAR(100) NULL,
	room_title           VARCHAR(200) NULL,
	room_desc            VARCHAR(250) NULL,
	room_master_id       INTEGER NOT NULL,
	max_user_cnt         INTEGER(2) NOT NULL,
	cur_user_cnt         INTEGER(2) NOT NULL,
	created_date         DATETIME NOT NULL,
	updated_date         DATETIME NULL
);

------------------------------------
-- 채팅방참여자 테이블 생성
------------------------------------
CREATE TABLE mu_chatroom_member
(
	room_id              BIGINT UNSIGNED NOT NULL,
	member_id            INTEGER(10) NOT NULL,
	master_yn            CHAR(1) NULL,
	created_date         DATETIME NOT NULL,
	updated_date         DATETIME NULL, 
	status				CHAT(1) NOT NULL DEFAULT 'E' COMMENT '상태 A:Alive, R:Reject, E:Exit'
);


ALTER TABLE mu_chatroom_member ADD PRIMARY KEY (room_id, member_id);
ALTER TABLE mu_chatroom_member ADD FOREIGN KEY mu_chatroom_member_fk1 (room_id) REFERENCES mu_chatroom (room_id);


------------------------------------
-- 쪽지함 테이블 생성
------------------------------------
CREATE TABLE mu_msgbox
(
	msgbox_id            BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	member_id            INTEGER(10) NOT NULL,
	sender_id            INTEGER(10) NOT NULL,
	created_date         DATETIME NOT NULL,
	updated_date         DATETIME NULL,
	status               VARCHAR(10) NULL
);

ALTER TABLE mu_msgbox ADD FOREIGN KEY mu_msgbox_fk1 (member_id) REFERENCES mu_member (member_id);
ALTER TABLE mu_msgbox ADD FOREIGN KEY mu_msgbox_fk2 (sender_id) REFERENCES mu_member (member_id);

------------------------------------
-- 쪽지 테이블 생성
------------------------------------
CREATE TABLE mu_msg
(
	msg_id               BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	sender_id            INTEGER(10) NOT NULL,
	receiver_id          INTEGER(10) NOT NULL,
	receiver_read_yn     CHAR(1) NOT NULL,
	receiver_read_date   DATETIME NULL,
	created_date         DATETIME NOT NULL,
	msg_text             TEXT NOT NULL,
	updated_date         DATETIME NULL
);

ALTER TABLE mu_msg ADD FOREIGN KEY mu_msg_fk1 (sender_id) REFERENCES mu_member (member_id);
ALTER TABLE mu_msg ADD FOREIGN KEY mu_msg_fk2 (receiver_id) REFERENCES mu_member (member_id);

------------------------------------
-- 링크_쪽지함_쪽지 테이블 생성
------------------------------------
CREATE TABLE mu_lnk_msgbox_msg
(
	msgbox_id            BIGINT UNSIGNED NOT NULL,
	msg_id               BIGINT UNSIGNED NOT NULL,
	created_date         DATETIME NOT NULL,
	updated_date         DATETIME NULL
);

ALTER TABLE mu_lnk_msgbox_msg ADD PRIMARY KEY (msgbox_id,msg_id);
ALTER TABLE mu_lnk_msgbox_msg ADD FOREIGN KEY mu_lnk_msgbox_msg_fk1 (msgbox_id) REFERENCES mu_msgbox (msgbox_id);
ALTER TABLE mu_lnk_msgbox_msg ADD FOREIGN KEY mu_lnk_msgbox_msg_fk2 (msg_id) REFERENCES mu_msg (msg_id);


/**********************************/
/* Table Name: 내친구정보 */
/**********************************/
CREATE TABLE MU_FRIENDS(
		member_id                     		INTEGER(10)		 NOT NULL COMMENT '회원아이디',
		friend_id                     		INTEGER(10)		 NOT NULL COMMENT '친구아이디',
		friend_type                   		VARCHAR(5)		 NULL  COMMENT '친구구분',
		status                        		CHAR(1)		 NOT NULL COMMENT '상태',
		created_date                  		DATETIME		 NOT NULL COMMENT '생성일자',
		updated_date                  		DATETIME		 NULL  COMMENT '수정일자',
		PRIMARY KEY (member_id, friend_id)
) COMMENT='내친구정보';

/**********************************/
/* Table Name: 공지사항 */
/**********************************/
CREATE TABLE mu_notice(
		notice_id                     		INTEGER(10)		 NOT NULL AUTO_INCREMENT COMMENT '공지사항아이디',
		title                         		VARCHAR(100)		 NOT NULL COMMENT '제목',
		contents                      		TEXT		 NULL  COMMENT '내용',
		show_yn                       		CHAR(1)		 DEFAULT 'N'		 NOT NULL COMMENT '보여주기여부',
		read_cnt                      		INTEGER(10)		 DEFAULT '0'		 NOT NULL COMMENT '조회수',
		created_date                  		DATETIME		 NOT NULL COMMENT '생성일자',
		updated_date                  		DATETIME		 NULL  COMMENT '수정일자',
		PRIMARY KEY (notice_id)
) COMMENT='공지사항' DEFAULT CHARSET=utf8;

/**********************************/
/* Table Name: 사용자가이드 */
/**********************************/
CREATE TABLE mu_user_guide(
		menu_id                       		VARCHAR(10)		 NOT NULL COMMENT '메뉴아이디',
		menu_name                     		VARCHAR(100)		 NOT NULL COMMENT '메뉴이름',
		level                         		INTEGER(1)		 NOT NULL COMMENT '레벨',
		upper_menu_id                 		VARCHAR(10)		 NULL  COMMENT '상위메뉴아이디',
		contents                      		TEXT		 NULL  COMMENT '내용',
		show_yn                       		CHAR(1)		 NULL  COMMENT '보여주기여부',
		created_date                  		DATETIME		 NOT NULL COMMENT '생성일자',
		updated_date                  		DATETIME		 NULL  COMMENT '수정일자',
		PRIMARY KEY (menu_id)
) COMMENT='사용자가이드' DEFAULT CHARSET=utf8;


/**********************************/
/* Table Name: 페이스매치 이벤트 */
/**********************************/
CREATE TABLE mu_fm_event
(
	event_id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '이벤트 아이디',
	event_name varchar(100) NOT NULL COMMENT '이벤트명',
	event_desc text COMMENT '이벤트 설명',
	-- 0 : 디폴트(강제참여)
	-- 1 : 특별 이벤트
	event_type int(1) NOT NULL COMMENT '이벤트타입',
	-- A : Active(진행중)
	-- E : End(종료)
	status char(1) NOT NULL COMMENT '상태',
	-- 이벤트 시작일자
	start_date date NOT NULL COMMENT '시작일자',
	-- 이벤트 종료일자
	end_date date NOT NULL COMMENT '종료일자',
	max_entry_cnt int(10) unsigned COMMENT '최대참가자수',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (event_id)
) COMMENT = '페이스매치이벤트';

/**********************************/
/* Table Name: 페이스매치 결과 */
/**********************************/
CREATE TABLE mu_fm_result
(
	fm_id int unsigned NOT NULL AUTO_INCREMENT COMMENT '페이스매치아이디',
	event_id int(10) unsigned NOT NULL COMMENT '이벤트 아이디',
	member_id int(10) NOT NULL COMMENT '회원 아이디',
	sex VARCHAR(10) NOT NULL,
	photo_url varchar(150) NOT NULL COMMENT '사진URL',
	voted_cnt int(10) unsigned DEFAULT 0 NOT NULL COMMENT '투표수',
	-- 이전 페이스매치에서 1등으로 뽑힌 회원 아이디
	prefer_member_id int(10) COMMENT '선호회원아이디',
	matched_date datetime COMMENT '매치일자',
	use_yn char(1) DEFAULT 'Y' NOT NULL COMMENT '사용여부',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (fm_id),
	CONSTRAINT MU_FM_RESULT_UX1 UNIQUE (event_id, member_id)
) COMMENT = '페이스매치결과';

/* Create Foreign Keys */
ALTER TABLE mu_fm_result
	ADD FOREIGN KEY (event_id)
	REFERENCES mu_fm_event (event_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_fm_result
	ADD FOREIGN KEY (member_id)
	REFERENCES mu_member (member_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

/* Create Indexes 
CREATE INDEX MU_FM_RESULT_IX1 ON mu_fm_result (event_id ASC, voted_cnt ASC);
*/