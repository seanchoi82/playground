
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
-- 아래 태이블 쿼리는 의존관계 때문에 실패 무시하고 두번 정도는 실행해야 합니다. 
------------------------------------

CREATE TABLE `mu_version_info` (
  `id` bigint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '아이디',
  `lang` varchar(20) NOT NULL COMMENT '언어설정',
  `version_code` int(10) NOT NULL COMMENT '최신버전 코드',
  `version_name` varchar(40) NOT NULL COMMENT '최신버전 이름',
  `required_upgrade` int(2) NOT NULL COMMENT '필수 업그레이드 여부',
  `required_upgrade_for_vc` int(2) NOT NULL COMMENT '필수 업그레이드 하위버전 코드',
  `show_yn` char(1) NOT NULL DEFAULT 'Y' COMMENT '보여주기여부',
  PRIMARY KEY (`id`),
  KEY `mu_version_info_ix1` (`show_yn`,`id`,`lang`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='업그레이드 버전정보';

CREATE TABLE `mu_version_info_randomchat` (
  `id` bigint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '아이디',
  `lang` varchar(20) NOT NULL COMMENT '언어설정',
  `version_code` int(10) NOT NULL COMMENT '최신버전 코드',
  `version_name` varchar(40) NOT NULL COMMENT '최신버전 이름',
  `required_upgrade` int(2) NOT NULL COMMENT '필수 업그레이드 여부',
  `required_upgrade_for_vc` int(2) NOT NULL COMMENT '필수 업그레이드 하위버전 코드',
  `show_yn` char(1) NOT NULL DEFAULT 'Y' COMMENT '보여주기여부',
  PRIMARY KEY (`id`),
  KEY `mu_version_info_randomchat_ix1` (`show_yn`,`id`,`lang`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='업그레이드 버전정보';



CREATE TABLE `mu_user_guide` (
  `menu_id` varchar(10) NOT NULL COMMENT '메뉴아이디',
  `menu_name` varchar(100) NOT NULL COMMENT '메뉴이름',
  `level` int(1) NOT NULL DEFAULT '1' COMMENT '레벨',
  `upper_menu_id` varchar(10) DEFAULT NULL COMMENT '상위메뉴아이디',
  `contents` text COMMENT '내용',
  `lang` varchar(5) NOT NULL,
  `show_yn` char(1) DEFAULT NULL COMMENT '보여주기여부',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자가이드';


CREATE TABLE `mu_talktome_reply` (
  `reply_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `talk_id` bigint(20) unsigned NOT NULL,
  `reply_content` varchar(250) DEFAULT NULL,
  `member_id` int(10) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`reply_id`),
  KEY `mu_talktome_reply_fk1` (`talk_id`),
  KEY `mu_talktome_reply_fk2` (`member_id`),
  CONSTRAINT `mu_talktome_reply_ibfk_1` FOREIGN KEY (`talk_id`) REFERENCES `mu_talktome` (`talk_id`),
  CONSTRAINT `mu_talktome_reply_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `mu_member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;


CREATE TABLE `mu_talktome` (
  `talk_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(10) NOT NULL,
  `title` varchar(200) DEFAULT NULL,
  `contents` text,
  `read_cnt` mediumint(9) DEFAULT NULL,
  `reply_cnt` mediumint(9) DEFAULT NULL,
  `talk_photo_url` varchar(200) DEFAULT NULL,
  `talk_photo_big_url` varchar(200) DEFAULT NULL,
  `talk_photo_org_url` varchar(200) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `hide_tag` int(1) DEFAULT '0' COMMENT '숨김여부',
  PRIMARY KEY (`talk_id`),
  KEY `mu_talktome_ix1` (`member_id`),
  CONSTRAINT `mu_talktome_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `mu_member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;


CREATE TABLE `mu_service_access_log` (
  `id` bigint(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '아이디',
  `member_id` int(10) NOT NULL COMMENT '회원 아이디',
  `request_uri` varchar(150) NOT NULL COMMENT '요청URI',
  `request_type` varchar(100) DEFAULT NULL,
  `service_cd` varchar(20) DEFAULT NULL COMMENT 'service_cd',
  `service_name` varchar(100) DEFAULT NULL,
  `rslt_cd` varchar(5) DEFAULT NULL COMMENT '결과코드',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`id`),
  KEY `mu_service_access_log_ibfk_1` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8845 DEFAULT CHARSET=utf8 COMMENT='mu_service_access_log';


CREATE TABLE `mu_point_usage_hist` (
  `point_hist_id` bigint(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '포인트이력ID',
  `member_id` int(10) NOT NULL COMMENT '회원 아이디',
  `event_type_cd` varchar(5) NOT NULL COMMENT '이벤트타입코드',
  `usage_cd` varchar(10) NOT NULL COMMENT '사용코드',
  `use_desc` varchar(200) DEFAULT NULL COMMENT '사용설명',
  `use_point` int(10) NOT NULL COMMENT '사용포인트',
  `remain_point` int(10) NOT NULL DEFAULT '0' COMMENT '잔여포인트',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`point_hist_id`),
  KEY `mu_point_usage_hist_ix1` (`member_id`),
  CONSTRAINT `mu_point_usage_hist_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `mu_member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=606 DEFAULT CHARSET=utf8 COMMENT='미싱유 포인트 사용이력';


CREATE TABLE `mu_pay_hist` (
  `pay_id` bigint(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '결제ID',
  `member_id` int(10) NOT NULL COMMENT '회원 아이디',
  `order_num` varchar(50) NOT NULL COMMENT '주문번호',
  `status` char(1) CHARACTER SET latin1 DEFAULT NULL COMMENT '상태',
  `pay_method` varchar(10) DEFAULT NULL COMMENT '결제방식',
  `prod_cd` varchar(10) DEFAULT NULL COMMENT '상품코드',
  `prod_name` varchar(100) DEFAULT NULL COMMENT '상품명',
  `amount` int(10) DEFAULT NULL COMMENT '결제금액',
  `pay_date` varchar(14) DEFAULT NULL COMMENT '결제일자',
  `cpid` varchar(20) DEFAULT NULL COMMENT '상점ID',
  `trx_id` varchar(20) DEFAULT NULL COMMENT '트랜잭션ID',
  `mobile_com` varchar(10) DEFAULT NULL COMMENT '무선통신사',
  `mobile_no` varchar(15) DEFAULT NULL COMMENT '휴대폰번호',
  `email` varchar(100) DEFAULT NULL COMMENT '이메일',
  `keyword1` varchar(100) DEFAULT NULL COMMENT '키워드1',
  `keyword2` varchar(20) DEFAULT NULL COMMENT '키워드2',
  `keyword3` varchar(20) DEFAULT NULL COMMENT '키워드3',
  `token` varchar(250) DEFAULT NULL,
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`pay_id`),
  UNIQUE KEY `mu_pay_hist_ux1` (`member_id`,`order_num`),
  CONSTRAINT `mu_pay_hist_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `mu_member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='미싱유_결제_내역';


CREATE TABLE `mu_notice` (
  `notice_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '공지사항아이디',
  `title` varchar(100) NOT NULL COMMENT '제목',
  `contents` text COMMENT '내용',
  `lang` varchar(5) DEFAULT NULL,
  `show_yn` char(1) NOT NULL DEFAULT 'N' COMMENT '보여주기여부',
  `read_cnt` int(10) NOT NULL DEFAULT '0' COMMENT '조회수',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='공지사항';


CREATE TABLE `mu_msgbox` (
  `msgbox_id` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(10) NOT NULL,
  `sender_id` int(10) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`msgbox_id`),
  KEY `mu_msgbox_fk1` (`member_id`),
  KEY `mu_msgbox_fk2` (`sender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;


CREATE TABLE `mu_msg` (
  `msg_id` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
  `sender_id` int(10) NOT NULL,
  `receiver_id` int(10) NOT NULL,
  `receiver_read_yn` char(1) NOT NULL,
  `receiver_read_date` datetime DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `msg_text` text NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`msg_id`),
  KEY `mu_msg_ibfk_2` (`receiver_id`),
  KEY `mu_msg_ibfk_1` (`sender_id`),
  CONSTRAINT `mu_msg_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `mu_member` (`member_id`),
  CONSTRAINT `mu_msg_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `mu_member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=utf8;


CREATE TABLE `mu_member_attr` (
  `member_id` int(10) NOT NULL,
  `attr_name` varchar(100) NOT NULL DEFAULT '',
  `attr_value` varchar(200) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`member_id`,`attr_name`),
  CONSTRAINT `mu_member_attr_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `mu_member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `mu_member` (
  `member_id` int(10) NOT NULL AUTO_INCREMENT,
  `status` char(1) NOT NULL,
  `login_id` varchar(50) NOT NULL,
  `login_pw` varchar(50) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `nick_name` varchar(30) DEFAULT NULL,
  `gcm_reg_id` varchar(200) DEFAULT NULL,
  `gcm_use_yn` char(1) CHARACTER SET latin1 NOT NULL DEFAULT 'Y',
  `sex` varchar(10) DEFAULT NULL,
  `blood_type_cd` varchar(10) DEFAULT NULL,
  `birth_date` varchar(8) DEFAULT NULL,
  `lunar_solar_cd` varchar(10) DEFAULT NULL,
  `birth_time` varchar(10) DEFAULT NULL,
  `appearance_type_cd` varchar(10) DEFAULT NULL,
  `body_type_cd` varchar(10) DEFAULT NULL,
  `purpose_cd` varchar(10) DEFAULT NULL,
  `hobby_cd` varchar(10) DEFAULT NULL,
  `drinking_habit_cd` varchar(10) DEFAULT NULL,
  `smoking_habit_cd` varchar(10) DEFAULT NULL,
  `self_pr` varchar(255) DEFAULT NULL,
  `main_photo_url` varchar(150) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `area_cd` varchar(10) DEFAULT NULL,
  `g_pos_x` varchar(20) DEFAULT NULL,
  `g_pos_y` varchar(20) DEFAULT NULL,
  `location` varchar(500) DEFAULT NULL,
  `lang` varchar(5) DEFAULT NULL,
  `country` varchar(5) DEFAULT NULL,
  `hp_nm` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `mu_member_ux1` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001105 DEFAULT CHARSET=utf8;


CREATE TABLE `mu_location_info_req_hist` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(10) NOT NULL COMMENT '회원 아이디',
  `purpose` varchar(200) NOT NULL COMMENT '목적',
  `trg_member_id` int(10) NOT NULL COMMENT '대상회원아이디',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `trg_member_id` (`trg_member_id`),
  CONSTRAINT `mu_location_info_req_hist_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `mu_member` (`member_id`),
  CONSTRAINT `mu_location_info_req_hist_ibfk_2` FOREIGN KEY (`trg_member_id`) REFERENCES `mu_member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='mu_location_info_req_hist';


CREATE TABLE `mu_lnk_msgbox_msg` (
  `msgbox_id` bigint(15) unsigned NOT NULL,
  `msg_id` bigint(15) unsigned NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`msgbox_id`,`msg_id`),
  KEY `mu_lnk_msgbox_msg_ibfk_2` (`msg_id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `mu_item_snd_rcv_hist` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '아이디',
  `sender_id` int(10) NOT NULL COMMENT '송신자아이디',
  `receiver_id` int(10) NOT NULL COMMENT '수신자아이디',
  `receiver_read_yn` char(1) NOT NULL COMMENT '수신자확인여부',
  `receiver_read_date` datetime DEFAULT NULL COMMENT '수신자확인날짜',
  `item_cd` varchar(10) NOT NULL COMMENT '아이템코드',
  `item_amount` int(10) NOT NULL COMMENT '아이템수량',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`id`),
  KEY `item_cd` (`item_cd`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `mu_item_snd_rcv_hist_ibfk_1` FOREIGN KEY (`item_cd`) REFERENCES `mu_item` (`item_cd`),
  CONSTRAINT `mu_item_snd_rcv_hist_ibfk_2` FOREIGN KEY (`sender_id`) REFERENCES `mu_member` (`member_id`),
  CONSTRAINT `mu_item_snd_rcv_hist_ibfk_3` FOREIGN KEY (`receiver_id`) REFERENCES `mu_member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COMMENT='미싱유_아이템_송수신_이력';


CREATE TABLE `mu_item` (
  `item_cd` varchar(10) NOT NULL COMMENT '아이템코드',
  `item_name` varchar(100) NOT NULL COMMENT '아이템명',
  `item_order` int(5) unsigned NOT NULL COMMENT '아이템순서',
  `item_group` varchar(5) NOT NULL DEFAULT '1' COMMENT '아이템그룹',
  `use_yn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`item_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='미싱유_아이템';


CREATE TABLE `mu_inventory` (
  `inventory_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '인벤토리아이디',
  `member_id` int(10) NOT NULL COMMENT '회원 아이디',
  `item_cd` varchar(10) NOT NULL COMMENT '아이템코드',
  `item_amount` int(10) NOT NULL DEFAULT '0' COMMENT '아이템수량',
  `show_yn` char(1) NOT NULL DEFAULT 'Y' COMMENT '보여주기여부',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`inventory_id`),
  UNIQUE KEY `MU_INVENTORY_UX1` (`member_id`,`item_cd`),
  KEY `item_cd` (`item_cd`),
  CONSTRAINT `mu_inventory_ibfk_1` FOREIGN KEY (`item_cd`) REFERENCES `mu_item` (`item_cd`),
  CONSTRAINT `mu_inventory_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `mu_member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=697 DEFAULT CHARSET=utf8 COMMENT='미싱유_인벤토리';


CREATE TABLE `mu_harmony_sign_data` (
  `id` int(5) NOT NULL COMMENT '아이디',
  `key1` varchar(10) NOT NULL COMMENT 'key1',
  `key2` varchar(10) NOT NULL COMMENT 'key2',
  `lang` varchar(3) DEFAULT NULL,
  `score` int(5) DEFAULT NULL COMMENT '점수',
  `result_text` text COMMENT '결과내용',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='mu_sign_harmony_data';


CREATE TABLE `mu_harmony_outer_data` (
  `id` int(5) NOT NULL COMMENT '아이디',
  `key1` varchar(10) NOT NULL COMMENT 'key1',
  `key2` varchar(10) NOT NULL COMMENT 'key2',
  `lang` varchar(3) DEFAULT NULL,
  `score` int(5) DEFAULT NULL COMMENT '점수',
  `result_text` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='겉궁합데이타';


CREATE TABLE `mu_harmony_inner_data` (
  `id` int(5) NOT NULL COMMENT '아이디',
  `key1` varchar(10) NOT NULL COMMENT 'key1',
  `key2` varchar(10) NOT NULL COMMENT 'key2',
  `lang` varchar(3) DEFAULT NULL,
  `score` int(5) DEFAULT NULL,
  `data` varchar(10) DEFAULT NULL COMMENT '데이타',
  `result_text` text COMMENT '결과내용',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='속궁합데이타';


CREATE TABLE `mu_harmony_blood_data` (
  `id` int(5) NOT NULL COMMENT '아이디',
  `key1` varchar(10) NOT NULL COMMENT 'key1',
  `key2` varchar(10) NOT NULL COMMENT 'key2',
  `lang` varchar(3) DEFAULT NULL,
  `score` int(5) DEFAULT NULL COMMENT '점수',
  `result_text` text COMMENT '결과내용',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='혈액형궁합데이터';


CREATE TABLE `mu_friends` (
  `member_id` int(10) NOT NULL COMMENT '회원아이디',
  `friend_id` int(10) NOT NULL COMMENT '친구아이디',
  `friend_type` varchar(5) DEFAULT NULL COMMENT '친구구분',
  `status` char(1) NOT NULL COMMENT '상태',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`member_id`,`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='내친구정보';


CREATE TABLE `mu_fm_result` (
  `fm_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '페이스매치아이디',
  `event_id` int(10) unsigned NOT NULL COMMENT '이벤트 아이디',
  `member_id` int(10) NOT NULL COMMENT '회원 아이디',
  `sex` varchar(10) NOT NULL,
  `photo_url` varchar(150) DEFAULT NULL COMMENT '사진URL',
  `join_cnt` int(10) unsigned NOT NULL DEFAULT '0',
  `win_cnt` int(10) unsigned NOT NULL DEFAULT '0',
  `voted_cnt` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '투표수',
  `accum_join_cnt` int(10) unsigned NOT NULL DEFAULT '0',
  `accum_win_cnt` int(10) unsigned NOT NULL DEFAULT '0',
  `accum_voted_cnt` int(10) unsigned NOT NULL DEFAULT '0',
  `prefer_member_id` int(10) DEFAULT NULL COMMENT '선호회원아이디',
  `matched_date` datetime DEFAULT NULL,
  `use_yn` char(1) CHARACTER SET latin1 DEFAULT NULL,
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`fm_id`),
  UNIQUE KEY `MU_FM_RESULT_UX1` (`event_id`,`member_id`),
  KEY `MU_FM_RESULT_IX1` (`event_id`,`voted_cnt`),
  KEY `mu_fm_result_ibfk_2` (`member_id`),
  CONSTRAINT `mu_fm_result_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `mu_fm_event` (`event_id`),
  CONSTRAINT `mu_fm_result_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `mu_member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8 COMMENT='페이스매치결과';


CREATE TABLE `mu_fm_event` (
  `event_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '이벤트 아이디',
  `event_name` varchar(100) NOT NULL COMMENT '이벤트명',
  `event_desc` text COMMENT '이벤트 설명',
  `event_type` int(1) NOT NULL COMMENT '이벤트타입',
  `status` char(1) NOT NULL COMMENT '상태',
  `start_date` date NOT NULL COMMENT '시작일자',
  `end_date` date NOT NULL COMMENT '종료일자',
  `max_entry_cnt` int(10) unsigned DEFAULT NULL COMMENT '최대참가자수',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='페이스매치이벤트';


CREATE TABLE `mu_emr_notice` (
  `emr_notice_id` bigint(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '아이디',
  `lang` varchar(20) NOT NULL COMMENT '언어설정',
  `onday` int(2) NOT NULL COMMENT '하루에 한번 노출 여부',
  `msg` varchar(200) NOT NULL COMMENT '내용',
  `positive_usable` int(2) NOT NULL COMMENT '확인버튼 사용여부',
  `positive_label` varchar(20) NOT NULL COMMENT '확인버튼 표시이름',
  `positive_action` int(2) NOT NULL COMMENT '확인버튼 액션',
  `positive_url` varchar(200) NOT NULL COMMENT '확인버튼 액션시 사용할 url',
  `negative_usable` int(2) NOT NULL COMMENT '취소버튼 사용여부',
  `negative_label` varchar(20) NOT NULL COMMENT '취소버튼 표시이름',
  `negative_action` int(2) NOT NULL COMMENT '무시버튼 액션',
  `negative_url` varchar(200) NOT NULL COMMENT '취소버튼 액션시 사용할 url',
  `ignore_usable` int(2) NOT NULL COMMENT '무시버튼 사용여부',
  `ignore_label` varchar(20) NOT NULL COMMENT '무시버튼 표시이름',
  `ignore_action` int(2) NOT NULL COMMENT '무시버튼 액션',
  `ignore_url` varchar(200) NOT NULL COMMENT '무시버튼 액션시 사용할 url',
  `show_yn` char(1) NOT NULL DEFAULT 'Y' COMMENT '보여주기여부',
  PRIMARY KEY (`emr_notice_id`),
  KEY `mu_emr_notice_ix1` (`show_yn`,`emr_notice_id`,`lang`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='긴급공지사항';

CREATE TABLE `mu_emr_notice_randomchat` (
  `emr_notice_id` bigint(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '아이디',
  `lang` varchar(20) NOT NULL COMMENT '언어설정',
  `onday` int(2) NOT NULL COMMENT '하루에 한번 노출 여부',
  `msg` varchar(200) NOT NULL COMMENT '내용',
  `positive_usable` int(2) NOT NULL COMMENT '확인버튼 사용여부',
  `positive_label` varchar(20) NOT NULL COMMENT '확인버튼 표시이름',
  `positive_action` int(2) NOT NULL COMMENT '확인버튼 액션',
  `positive_url` varchar(200) NOT NULL COMMENT '확인버튼 액션시 사용할 url',
  `negative_usable` int(2) NOT NULL COMMENT '취소버튼 사용여부',
  `negative_label` varchar(20) NOT NULL COMMENT '취소버튼 표시이름',
  `negative_action` int(2) NOT NULL COMMENT '무시버튼 액션',
  `negative_url` varchar(200) NOT NULL COMMENT '취소버튼 액션시 사용할 url',
  `ignore_usable` int(2) NOT NULL COMMENT '무시버튼 사용여부',
  `ignore_label` varchar(20) NOT NULL COMMENT '무시버튼 표시이름',
  `ignore_action` int(2) NOT NULL COMMENT '무시버튼 액션',
  `ignore_url` varchar(200) NOT NULL COMMENT '무시버튼 액션시 사용할 url',
  `show_yn` char(1) NOT NULL DEFAULT 'Y' COMMENT '보여주기여부',
  PRIMARY KEY (`emr_notice_id`),
  KEY `mu_emr_notice_randomchat_ix1` (`show_yn`,`emr_notice_id`,`lang`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='긴급공지사항';


CREATE TABLE `mu_chatroom_member` (
  `room_id` bigint(20) unsigned NOT NULL,
  `member_id` int(10) NOT NULL,
  `master_yn` char(1) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `status` char(1) CHARACTER SET latin1 NOT NULL DEFAULT 'E' COMMENT '상태 A:Alive, R:Reject, E:Exit',
  PRIMARY KEY (`room_id`,`member_id`),
  CONSTRAINT `mu_chatroom_member_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `mu_chatroom` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `mu_chatroom` (
  `room_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `room_pw` varchar(100) DEFAULT NULL,
  `room_title` varchar(200) DEFAULT NULL,
  `room_desc` varchar(250) DEFAULT NULL,
  `room_master_id` int(11) NOT NULL,
  `max_user_cnt` int(2) NOT NULL,
  `cur_user_cnt` int(2) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;


CREATE TABLE `mu_admin_access_log` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '아이디',
  `login_id` varchar(30) NOT NULL COMMENT '로그인아이디',
  `ip_addr` varchar(20) DEFAULT NULL COMMENT 'IP주소',
  `rslt_cd` varchar(5) DEFAULT NULL COMMENT '결과코드',
  `created_date` datetime NOT NULL COMMENT '생성일자',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='미싱유관리자접근로그';


CREATE TABLE `apk_hist_info` (
  `apk_id` int(5) NOT NULL AUTO_INCREMENT,
  `apk_desc` varchar(100) DEFAULT NULL,
  `apk_version` char(10) DEFAULT NULL,
  `register_name` char(15) DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  `apk_file_name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`apk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

CREATE TABLE `mu_man_to_man_question` (
  `m_id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '인덱스',
  `member_id` int(10) NOT NULL COMMENT '회원아이디',
  `code` varchar(6) NOT NULL COMMENT '문의 구분',
  `contract` varchar(100) NOT NULL COMMENT '연락처',
  `content` text CHARACTER SET latin1 NOT NULL COMMENT '문의 내용',
  `file` varchar(200) DEFAULT NULL COMMENT '업로드파일',
  `created_date` datetime NOT NULL COMMENT '입력일자',
  `updated_date` datetime DEFAULT NULL COMMENT '변경일자',
  `memo` text CHARACTER SET latin1 COMMENT '관리자 메모',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '상태코드 0: 요청, 1:처리중, 2:완료, 3:보류',
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='1:1문의 게시판';


CREATE TABLE `mu_mission_match` (
  `m_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL COMMENT '유형',
  `title` varchar(200) NOT NULL COMMENT '제목',
  `description` text COMMENT '설명',
  `start_date` datetime DEFAULT NULL COMMENT '참여가능 시작일',
  `end_date` datetime DEFAULT NULL COMMENT '참여가능 종료일',
  `max_count` int(11) DEFAULT NULL COMMENT '미션당 최대참여가능수',
  `status` int(11) DEFAULT NULL COMMENT '사용상태여부',
  `use_multi_vote` int(11) DEFAULT NULL COMMENT '다중참여 가능여부(0일 경우 1회만 참여됨)',
  `multi_vote_interval` int(11) DEFAULT NULL COMMENT '다중참여시 참여제한 간격',
  PRIMARY KEY (`m_id`),
  KEY `mu_mission_match_ix1` (`start_date`,`end_date`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='미션매치 정보 테이블';


CREATE TABLE `mu_mission_match_join` (
  `mj_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `m_id` bigint(20) NOT NULL,
  `member_id` bigint(20) DEFAULT NULL COMMENT '회원아이디',
  `comment` varchar(140) DEFAULT NULL COMMENT '댓글',
  `uploadfile` varbinary(200) DEFAULT NULL COMMENT '업로드파일',
  `uploadfilebig` varbinary(200) DEFAULT NULL COMMENT '업로드파일 큰 이미지',
  `uploadfileorg` varchar(200) DEFAULT NULL COMMENT '업로드 파일 원본이미지',
  `vote` int(11) DEFAULT NULL COMMENT '추천수',
  `created_date` datetime NOT NULL COMMENT '등록일',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일',
  `view_cnt` bigint(20) DEFAULT '0' COMMENT '조회수',
  `vote_by_month` int(11) DEFAULT '0' COMMENT '이번달 추천수',
  PRIMARY KEY (`mj_id`),
  KEY `mu_mission_match_join_ix1` (`member_id`,`m_id`,`mj_id`),
  KEY `mu_mission_match_join_ibfk_1` (`m_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

CREATE TABLE `mu_mission_match_result` (
  `mr_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `vote` bigint(20) NOT NULL,
  `member_id` bigint(20) NOT NULL,
  `view_cnt` bigint(20) NOT NULL,
  PRIMARY KEY (`mr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `mu_mission_match_vote` (
  `m_id` bigint(20) NOT NULL,
  `mj_id` bigint(20) NOT NULL,
  `member_id` bigint(20) NOT NULL COMMENT '회원아이디',
  `created_date` datetime NOT NULL COMMENT '추천일',
  KEY `mj_id` (`mj_id`),
  KEY `mu_mission_match_vote_ix1` (`m_id`,`mj_id`,`member_id`),
  CONSTRAINT `mu_mission_match_vote_ibfk_1` FOREIGN KEY (`m_id`) REFERENCES `mu_mission_match` (`m_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mu_mission_match_vote_ibfk_2` FOREIGN KEY (`mj_id`) REFERENCES `mu_mission_match_join` (`mj_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





DROP FUNCTION IF EXISTS missingu.decrypt;
CREATE FUNCTION missingu.`decrypt`(data varchar(200)) RETURNS varchar(200) CHARSET utf8
    DETERMINISTIC
BEGIN

  DECLARE result varchar(200);
  SELECT CAST(AES_DECRYPT(UNHEX(data), '0OKM9IJN8UHB7YGV') AS CHAR) INTO result;

	RETURN result;
END;

DROP FUNCTION IF EXISTS missingu.f_decrypt;
CREATE FUNCTION missingu.`f_decrypt`(data varchar(200)) RETURNS varchar(200) CHARSET utf8
    DETERMINISTIC
BEGIN

  DECLARE result varchar(200);
  SELECT CAST(AES_DECRYPT(UNHEX(data), '0OKM9IJN8UHB7YGV') AS CHAR) INTO result;

	RETURN result;
END;


DROP FUNCTION IF EXISTS missingu.f_encrypt;
CREATE FUNCTION missingu.`f_encrypt`(data varchar(200)) RETURNS varchar(200) CHARSET utf8
    DETERMINISTIC
BEGIN

  DECLARE result varchar(200);
  SELECT IFNULL(HEX(AES_ENCRYPT(data,'0OKM9IJN8UHB7YGV')), data) INTO result;

	RETURN result;
END;

DROP FUNCTION IF EXISTS missingu.get_certification;
CREATE FUNCTION missingu.`get_certification`(in_member_id  int) RETURNS varchar(1) CHARSET utf8
    DETERMINISTIC
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
END;

DROP FUNCTION IF EXISTS missingu.get_membership;
CREATE FUNCTION missingu.`get_membership`(in_member_id  int) RETURNS varchar(1) CHARSET utf8
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
END;

DROP FUNCTION IF EXISTS get_gps_distance;
CREATE FUNCTION `get_gps_distance`(g_pos_x DOUBLE, g_pos_y DOUBLE, gPosX DOUBLE, gPosY DOUBLE) RETURNS DOUBLE
    DETERMINISTIC
BEGIN

  DECLARE result DOUBLE DEFAULT 0.0;

    SELECT
     (6371 * acos( cos( radians(g_pos_x) ) * cos( radians( gPosX ) ) * cos( radians( gPosY ) - radians(g_pos_y) ) + sin( radians(g_pos_x) ) * sin( radians( gPosX ) ) ) ) 
    INTO result;



 RETURN result;
END;

	
	-- 페이스매치 한달에 한번씩 랭킹 정리를 위한 프로시져
	DROP PROCEDURE IF EXISTS missingu.mission_match_month_report;
	CREATE PROCEDURE missingu.`mission_match_month_report`()
	BEGIN
		
	  declare varYear int;
	  declare varMonth int; 
	  declare varDay int;
	  set varYear = year(now());
	  set varMonth = month(now());
	  set varDay = day(now());
	
	  -- 매월 1일 마다 실행(스케쥴로 1일 1회 실행되기 때문에 한달에 한번만 실행 됨. missingu_event_facematch_month)
	  if varDay = 1 then 
	  
	    -- 일괄적으로 업데이트 때리기
	    insert into mu_mission_match_result
	    (
	      `year`
	      , `month`
	      , vote
	      , member_id
	      , view_cnt
	      , battle_join_cnt
	    )
	    select
	      varYear
	      , varMonth
	      , vote
	      , member_id
	      , view_cnt
	      , battle_join_cnt
	    FROM mu_mission_match_join;
	    
	    -- 이번달 월간 추천수는 초기화 한다.
	    update mu_mission_match_join set
	    vote_by_month = 0;
	  
	  end if;
	  
	END;

-- 1분 마다 작동 되는 이벤트
create event missingu_event_chatroom_remove
on schedule every 1 minute
do 
  DELETE FROM mu_chatroom
  WHERE 
	    updated_date IS NOT NULL
		AND LENGTH(updated_date) > 0
		AND TIMESTAMPDIFF(MINUTE, updated_date, now()) > 120;
		
-- 1일 마다 작동되는 이벤트		
create event missingu_event_facematch_month
on schedule every 1 day 
do 
call mission_match_month_report();