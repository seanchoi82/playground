set session foreign_key_checks=0;

/* drop indexes 
drop index mu_mission_match_ix1 on mu_mission_match;
drop index mu_mission_match_join_ix1 on mu_mission_match_join;
drop index mu_mission_match_vote_ix1 on mu_mission_match_vote;
*/


/* drop tables 
drop table mu_mission_match_vote;
drop table mu_mission_match_join;
drop table mu_mission_match;
*/



/* create tables */

-- 미션매치 정보 테이블
create table mu_mission_match
(
	m_id bigint not null auto_increment,
	-- 유형
	type int not null comment '유형',
	-- 제목
	-- 
	title varchar(200) not null comment '제목
',
	-- 설명
	description text comment '설명',
	-- 참여가능 시작일
	start_date datetime  comment '참여가능 시작일',
	-- 참여가능 종료일
	end_date datetime comment '참여가능 종료일',
	-- 미션당 최대참여가능수
	max_count int comment '미션당 최대참여가능수',
	-- 사용상태여부
	status int comment '사용상태여부',
	primary key (m_id)
) comment = '미션매치 정보 테이블';


create table mu_mission_match_join
(
	mj_id bigint not null auto_increment,
	m_id bigint not null,
	-- 회원아이디
	member_id bigint comment '회원아이디',
	-- 댓글
	comment varchar(140) comment '댓글',
	-- 업로드파일
	uploadfile varbinary(200) comment '업로드파일',
	-- 업로드파일 큰 이미지
	uploadfilebig varbinary(200) comment '업로드파일 큰 이미지',
	-- 업로드 파일 원본이미지
	uploadfileorg varchar(200) comment '업로드 파일 원본이미지',
	-- 추천수
	vote int comment '추천수',
	-- 등록일
	created_date datetime not null comment '등록일',
	-- 수정일
	updated_date datetime comment '수정일',
	primary key (mj_id)
);


create table mu_mission_match_vote
(
	m_id bigint not null,
	mj_id bigint not null,
	-- 회원아이디
	member_id bigint not null comment '회원아이디',
	-- 추천일
	created_date datetime not null comment '추천일'
);



/* create foreign keys */

alter table mu_mission_match_join
	add foreign key (m_id)
	references mu_mission_match (m_id)
	on update cascade 
	on delete cascade 
;


alter table mu_mission_match_vote
	add foreign key (m_id)
	references mu_mission_match (m_id)
	on update cascade 
	on delete cascade 
;


alter table mu_mission_match_vote
	add foreign key (mj_id)
	references mu_mission_match_join (mj_id)
	on update cascade 
	on delete cascade 
;



/* create indexes */

create index mu_mission_match_ix1 on mu_mission_match (start_date asc, end_date asc, status asc);
create index mu_mission_match_join_ix1 on mu_mission_match_join (member_id asc, m_id asc, mj_id asc);
create index mu_mission_match_vote_ix1 on mu_mission_match_vote (m_id asc, mj_id asc, member_id asc);



