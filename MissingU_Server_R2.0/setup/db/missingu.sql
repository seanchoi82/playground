SET SESSION FOREIGN_KEY_CHECKS=0;


/* Create Tables */

CREATE TABLE mu_admin_access_log
(
	id bigint(15) NOT NULL AUTO_INCREMENT COMMENT '아이디',
	login_id varchar(30) NOT NULL COMMENT '로그인아이디',
	ip_addr varchar(20) COMMENT 'IP주소',
	rslt_cd varchar(5) COMMENT '결과코드',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (id)
) COMMENT = '미싱유관리자접근로그';


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
) COMMENT = '페이스매치이벤트' DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE mu_fm_result
(
	fm_id int unsigned NOT NULL AUTO_INCREMENT COMMENT '페이스매치아이디',
	event_id int(10) unsigned NOT NULL COMMENT '이벤트 아이디',
	member_id int(10) NOT NULL COMMENT '회원 아이디',
	sex varchar(10) NOT NULL COMMENT '성별코드',
	photo_url varchar(150) NOT NULL COMMENT '사진URL',
	voted_cnt int(10) unsigned DEFAULT 0 NOT NULL COMMENT '투표수',
	join_cnt int(10) unsigned DEFAULT 0 NOT NULL COMMENT '',
	win_cnt int(10) unsigned DEFAULT 0 NOT NULL COMMENT '',
	accum_join_cnt int(10) unsigned DEFAULT 0 NOT NULL COMMENT '',
	accum_win_cnt int(10) unsigned DEFAULT 0 NOT NULL COMMENT '',
	accum_voted_cnt int(10) unsigned DEFAULT 0 NOT NULL COMMENT '',
	-- 이전 페이스매치에서 1등으로 뽑힌 회원 아이디
	
	prefer_member_id int(10) COMMENT '선호회원아이디',
	matched_date datetime COMMENT '매치일자',
	use_yn char(1) DEFAULT 'Y' NOT NULL COMMENT '사용여부',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (fm_id),
	CONSTRAINT MU_FM_RESULT_UX1 UNIQUE (event_id, member_id)
) COMMENT = '페이스매치결과';


CREATE TABLE mu_friends
(
	member_id int(10) NOT NULL COMMENT '회원 아이디',
	-- 친구 회원 아이디
	friend_id int(10) NOT NULL COMMENT '친구 아이디',
	friend_type varchar(5) COMMENT '친구구분',
	status char(1) NOT NULL COMMENT '상태',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (member_id, friend_id)
) COMMENT = '친구정보';


CREATE TABLE mu_harmony_blood_data
(
	id int(5) NOT NULL UNIQUE COMMENT '아이디',
	-- 남자 혈액형 코드
	key1 varchar(10) NOT NULL COMMENT 'key1',
	-- 여자 혈액형 코드
	key2 varchar(10) NOT NULL COMMENT 'key2',
	lang varchar(3) COMMENT '언어',
	score int(5) COMMENT '점수',
	result_text text COMMENT '결과내용',
	PRIMARY KEY (id)
) COMMENT = '혈액형궁합데이터';


CREATE TABLE mu_harmony_inner_data
(
	id int(5) NOT NULL UNIQUE COMMENT '아이디',
	key1 varchar(10) NOT NULL COMMENT 'key1',
	key2 varchar(10) NOT NULL COMMENT 'key2',
	lang varchar(3) COMMENT '언어',
	score int(5) COMMENT '점수',
	data varchar(10) COMMENT '데이타',
	result_text text COMMENT '결과내용',
	PRIMARY KEY (id)
) COMMENT = '속궁합데이타';


CREATE TABLE mu_harmony_outer_data
(
	id int(5) NOT NULL UNIQUE COMMENT '아이디',
	key1 varchar(10) NOT NULL COMMENT 'key1',
	key2 varchar(10) NOT NULL COMMENT 'key2',
	lang varchar(3) COMMENT '언어',
	score int(5) COMMENT '점수',
	result_text text COMMENT '결과내용',
	PRIMARY KEY (id)
) COMMENT = '겉궁합데이타';


CREATE TABLE mu_harmony_sign_data
(
	id int(5) NOT NULL UNIQUE COMMENT '아이디',
	-- 남자 별자리 코드
	key1 varchar(10) NOT NULL COMMENT 'key1',
	-- 여자 별자리 코드
	key2 varchar(10) NOT NULL COMMENT 'key2',
	lang varchar(3) COMMENT '언어',
	-- 별자리 궁합 점수
	score int(5) COMMENT '점수',
	-- 별자리 궁합 결과
	result_text text COMMENT '결과내용',
	PRIMARY KEY (id)
) COMMENT = '별자리궁합데이타';


CREATE TABLE mu_inventory
(
	inventory_id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '인벤토리아이디',
	member_id int(10) NOT NULL COMMENT '회원 아이디',
	item_cd varchar(10) NOT NULL COMMENT '아이템코드',
	item_amount int(10) DEFAULT 0 NOT NULL COMMENT '아이템수량',
	show_yn char(1) DEFAULT 'Y' NOT NULL COMMENT '보여주기여부',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (inventory_id)
) COMMENT = '미싱유_인벤토리';


CREATE TABLE mu_item
(
	item_cd varchar(10) NOT NULL COMMENT '아이템코드',
	item_name varchar(100) NOT NULL COMMENT '아이템명',
	item_order int(5) unsigned NOT NULL COMMENT '아이템순서',
	item_group varchar(5) DEFAULT '1' NOT NULL COMMENT '아이템그룹',
	use_yn char(1) DEFAULT 'Y' NOT NULL COMMENT '사용여부',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (item_cd)
) COMMENT = '미싱유_아이템';


CREATE TABLE mu_item_snd_rcv_hist
(
	id int(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '아이디',
	sender_id int(10) NOT NULL COMMENT '송신자아이디',
	receiver_id int(10) NOT NULL COMMENT '수신자아이디',
	receiver_read_yn char(1) NOT NULL COMMENT '수신자확인여부',
	receiver_read_date datetime COMMENT '수신자확인날짜',
	item_cd varchar(10) NOT NULL COMMENT '아이템코드',
	item_amount int(10) NOT NULL COMMENT '아이템수량',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (id)
) COMMENT = '미싱유_아이템_송수신_이력';


CREATE TABLE mu_location_info_handle_hist
(
	id bigint(15) NOT NULL AUTO_INCREMENT COMMENT '아이디',
	PRIMARY KEY (id)
) COMMENT = 'mu_location_info_handle_hist';


CREATE TABLE mu_location_info_req_hist
(
	id bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'id',
	member_id int(10) NOT NULL COMMENT '회원 아이디',
	purpose varchar(200) NOT NULL COMMENT '목적',
	trg_member_id int(10) NOT NULL COMMENT '대상회원아이디',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (id)
) COMMENT = 'mu_location_info_req_hist';


CREATE TABLE mu_member
(
	member_id int(10) NOT NULL AUTO_INCREMENT COMMENT '회원 아이디',
	login_id varchar(50) NOT NULL COMMENT '로그인 아이디',
	login_pw varchar(50) NOT NULL COMMENT '로그인 비밀번호',
	PRIMARY KEY (member_id)
) COMMENT = '회원';


CREATE TABLE mu_pay_hist
(
	pay_id bigint(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '결제ID',
	member_id int(10) NOT NULL COMMENT '회원 아이디',
	order_num varchar(50) NOT NULL COMMENT '주문번호',
	status char(1) NOT NULL COMMENT '상태',
	pay_method varchar(10) COMMENT '결제방식',
	prod_cd varchar(10) COMMENT '상품코드',
	prod_name varchar(100) COMMENT '상품명',
	amount int(10) COMMENT '결제금액',
	pay_date varchar(14) COMMENT '결제일자',
	cpid varchar(20) COMMENT '상점ID',
	trx_id varchar(20) COMMENT '트랜잭션ID',
	mobile_com varchar(10) COMMENT '무선통신사',
	mobile_no varchar(15) COMMENT '휴대폰번호',
	email varchar(100) COMMENT '이메일',
	keyword1 varchar(100) COMMENT '키워드1',
	keyword2 varchar(20) COMMENT '키워드2',
	keyword3 varchar(20) COMMENT '키워드3',
	token varchar(250) COMMENT '토큰',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (pay_id)
) COMMENT = '미싱유_결제_내역';


CREATE TABLE mu_point_usage_hist
(
	point_hist_id bigint(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '포인트이력ID',
	member_id int(10) NOT NULL COMMENT '회원 아이디',
	-- I : 수입
	-- O : 지출
	event_type_cd varchar(5) NOT NULL COMMENT '이벤트타입코드',
	-- 사용코드
	usage_cd varchar(10) NOT NULL COMMENT '사용코드',
	use_desc varchar(200) COMMENT '사용설명',
	-- 충전 or 사용 포인트
	use_point int(10) NOT NULL COMMENT '사용포인트',
	remain_point int(10) DEFAULT 0 NOT NULL COMMENT '잔여포인트',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (point_hist_id)
) COMMENT = '미싱유 포인트 사용이력';


CREATE TABLE mu_service_access_log
(
	id bigint(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '아이디',
	member_id int(10) NOT NULL COMMENT '회원 아이디',
	request_uri varchar(150) NOT NULL COMMENT '목적',
	request_type varchar(100) COMMENT '요청타입',
	service_cd varchar(20) COMMENT '서비스코드',
	rslt_cd varchar(5) COMMENT '결과코드',
	created_date datetime NOT NULL COMMENT '생성일자',
	updated_date datetime COMMENT '수정일자',
	PRIMARY KEY (id)
) COMMENT = 'mu_service_access_log';


CREATE TABLE mu_emr_notice 
(
   emr_notice_id bigint(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '아이디',
   lang varchar(20) NOT NULL COMMENT '언어설정', 
   onday int(2) NOT NULL COMMENT '하루에 한번 노출 여부',
   msg varchar(200) NOT NULL COMMENT '내용',
   positive_usable int(2) NOT NULL COMMENT '확인버튼 사용여부',
   positive_label varchar(20) NOT NULL COMMENT '확인버튼 표시이름',
   positive_action int(2) NOT NULL COMMENT '확인버튼 액션',
   positive_url varchar(200) NOT NULL COMMENT '확인버튼 액션시 사용할 url',
   negative_usable int(2) NOT NULL COMMENT '취소버튼 사용여부',
   negative_label varchar(20) NOT NULL COMMENT '취소버튼 표시이름',
   negative_action int(2) NOT NULL COMMENT '무시버튼 액션',
   negative_url varchar(200) NOT NULL COMMENT '취소버튼 액션시 사용할 url',
   ignore_usable int(2) NOT NULL COMMENT '무시버튼 사용여부',
   ignore_label varchar(20) NOT NULL COMMENT '무시버튼 표시이름',
   ignore_action int(2) NOT NULL COMMENT '무시버튼 액션',
   ignore_url varchar(200) NOT NULL COMMENT '무시버튼 액션시 사용할 url',
   show_yn char(1) DEFAULT 'Y' NOT NULL COMMENT '보여주기여부', 
   PRIMARY KEY (emr_notice_id)
) COMMENT = '긴급공지사항';


CREATE TABLE mu_version_info 
(
	id bigint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '아이디',
   lang varchar(20) NOT NULL COMMENT '언어설정',	
	version_code int(10) NOT NULL COMMENT '최신버전 코드', 
	version_name varchar(40) NOT NULL COMMENT '최신버전 이름',
	required_upgrade int(2) NOT NULL COMMENT '필수 업그레이드 여부', 
	required_upgrade_for_vc int(2) NOT NULL COMMENT '필수 업그레이드 하위버전 코드',
	show_yn char(1) DEFAULT 'Y' NOT NULL COMMENT '보여주기여부',
	PRIMARY KEY (id)
) COMMENT = '업그레이드 버전정보';

/* Create Foreign Keys */

ALTER TABLE mu_fm_result
	ADD FOREIGN KEY (event_id)
	REFERENCES mu_fm_event (event_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_inventory
	ADD FOREIGN KEY (item_cd)
	REFERENCES mu_item (item_cd)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_item_snd_rcv_hist
	ADD FOREIGN KEY (item_cd)
	REFERENCES mu_item (item_cd)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_fm_result
	ADD FOREIGN KEY (member_id)
	REFERENCES mu_member (member_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_inventory
	ADD FOREIGN KEY (member_id)
	REFERENCES mu_member (member_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_item_snd_rcv_hist
	ADD FOREIGN KEY (sender_id)
	REFERENCES mu_member (member_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_item_snd_rcv_hist
	ADD FOREIGN KEY (receiver_id)
	REFERENCES mu_member (member_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_location_info_req_hist
	ADD FOREIGN KEY (trg_member_id)
	REFERENCES mu_member (member_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_location_info_req_hist
	ADD FOREIGN KEY (member_id)
	REFERENCES mu_member (member_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_pay_hist
	ADD FOREIGN KEY (member_id)
	REFERENCES mu_member (member_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mu_point_usage_hist
	ADD FOREIGN KEY (member_id)
	REFERENCES mu_member (member_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Create Indexes */

CREATE INDEX MU_FM_RESULT_IX1 ON mu_fm_result (event_id ASC, voted_cnt ASC);
CREATE UNIQUE INDEX MU_INVENTORY_UX1 ON mu_inventory (member_id ASC, item_cd ASC);
CREATE UNIQUE INDEX mu_pay_hist_ux1 ON mu_pay_hist (member_id ASC, order_num ASC);
CREATE INDEX mu_point_usage_hist_ix1 ON mu_point_usage_hist (member_id ASC);
CREATE INDEX mu_emr_notice_ix1 ON mu_emr_notice(show_yn DESC, emr_notice_id DESC, lang ASC);
CREATE INDEX mu_version_info_ix1 ON mu_version_info (show_yn DESC, id DESC, lang ASC);

/*** function */
DELIMITER $$
DROP FUNCTION IF EXISTS get_membership$$

CREATE FUNCTION get_membership(in_member_id  int) RETURNS varchar(1) CHARSET utf8
    DETERMINISTIC
BEGIN

  DECLARE result varchar(1) DEFAULT 'N';

    SELECT
    IFNULL(
    (SELECT
          CASE  WHEN DATEDIFF(str_to_date(attr_value, '%Y%m%d'), now()) > 0
                THEN 'Y'
                ELSE 'N'
          END
          AS membership
          FROM mu_member_attr a
          WHERE 
                member_id = in_member_id
              AND attr_name = 'passExpireDay' LIMIT 1), 'N')
          INTO result
;



 RETURN result;
END$$
DELIMITER ;

DELIMITER $$
DROP FUNCTION IF EXISTS get_certification$$
CREATE FUNCTION get_certification(in_member_id  int) RETURNS varchar(1) CHARSET utf8 DETERMINISTIC
BEGIN

  DECLARE result varchar(1) DEFAULT 'N';
	
    SELECT 
    IFNULL(
      (SELECT 
          attr_value
          FROM mu_member_attr 
          WHERE member_id = in_member_id 
                AND attr_name = 'oneselfCertification' limit 1)
       , 'N') INTO result;
    
	RETURN result;
END$$
DELIMITER ;


DELIMITER $$
DROP FUNCTION IF EXISTS f_decrypt$$
CREATE FUNCTION f_decrypt(data varchar(200)) RETURNS varchar(200) CHARSET utf8 DETERMINISTIC
BEGIN

  DECLARE result varchar(200);
  SELECT CAST(AES_DECRYPT(UNHEX(data), '0OKM9IJN8UHB7YGV') AS CHAR) INTO result;

	RETURN result;
END$$
DELIMITER ;

DELIMITER $$
DROP FUNCTION IF EXISTS f_encrypt$$
CREATE FUNCTION f_encrypt(data varchar(200)) RETURNS varchar(200) CHARSET utf8 DETERMINISTIC
BEGIN

  DECLARE result varchar(200);
  SELECT IFNULL(HEX(AES_ENCRYPT(data,'0OKM9IJN8UHB7YGV')), data) INTO result;

	RETURN result;
END$$
DELIMITER ;