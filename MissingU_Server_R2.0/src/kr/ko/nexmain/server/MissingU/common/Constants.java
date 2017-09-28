/*****************************************************************************************
 * @project : MissingU 프로젝트 1차
 * @title   : MissingU 공통변수
 * @description : MissingU 서버단에서 사용되는 공통변수
 * @author  : CSH
 * ------------------------------------------------------------
 * @history : 
 *****************************************************************************************/
package kr.ko.nexmain.server.MissingU.common;

/***************************************************************************
Constants 
=========
MissingU 서버단에서 사용되는 공통변수
*******************************************************************************/
public interface Constants {
	
	interface TSTORE {
		public static final String APP_ID_MISSINGU = "OA00384638";
		public static final boolean DEBUG_MODE = true;
		public static final String RECEIPTS_VARIFICATION_URL = "https://iap.tstore.co.kr/digitalsignconfirm.iap";
		public static final String RECEIPTS_VARIFICATION_URL_DEBUG = "https://iapdev.tstore.co.kr/digitalsignconfirm.iap";
		public static final String PRODUCT_ID_10000_POINT = "0910007257";
		public static final String PRODUCT_ID_24000_POINT = "0910007259";
		public static final String PRODUCT_ID_36000_POINT = "0910007260";
	}
	
	/**********************************************
	C2DM 관련 정보
	==============
	***********************************************/
	interface C2DM {
		public static final String	C2DM_SENDER_ID		= "missinguc2dm@gmail.com";
		public static final String	C2DM_SENDER_PW		= "altlddb0314";
		public static final String	C2DM_AUTH_TOKEN		= "DQAAAL8AAAAshTCEUtpdyfYerw4kZFu8VwNZ_JcEIt_rDAGUIcSV8V7uIMdAgTn1C58ZlZBdMR5jXpHIU7YYtzt0JG7TgQATrRAiWS0eyIqFsc56O00ITKm--6yXTe_MPDpmHwAc0dGEuI5-tIWSLvh0j0Kvk6G7fUwB9WcKg9QS_5zlZqIKsFb0lmKJjMAj_PsIautkDsRLPo14MZPW24my9oJ74EXdO9kxeJb-M0AJClhLCPxTKSb_fhDBONJ9Rqfv7BgSFfw";
	}
	
	interface GCM {
		public static final String  GCM_PROJECT_CODE	= "677882472313";
		public static final String  API_SERVER_KEY		= "AIzaSyDz5Lsz2WQMLtCyf7WKd58HEkR2KRZkOaM";
	}
	
	interface GoogleBilling {
		public static final String  LICENSE_KEY			= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg4Ezun7ud/7mEBzvDNy4gSW7F/4SyXOPMtWhFheTPohfe7CqngjwBXGilZ4TbPIqu9tC6lBYTkNcytIQA/QCuE1ireJ0GficsZiUu/jQvTeou/Cq336B3GGWJ0CEv9cLxDHwhh+ZOQAR46E6UtVQL+QKr4WCyEmpg7Gf+08F3AqeJBlYKcaNyHMIV/7SZkRVlk8VcbVlJBkfuJrehbmYfbdi8gQxfkpBBcLlWB3sPSfYz0Fw6cKO9V2V5syD1VWyVOcsBPIWYYh8IG9/5iVAiJiL/LFndmf7gGMQq6CkhSCK+WCZb8Cw5Eo4ZU4ThUqrIifdJAnWkL4dyGbTMOOOtwIDAQAB";
	}
	
	/**********************************************
	서비스 리턴 코드
	==============
	***********************************************/
	interface ReturnCode {
		public static final String	SUCCESS			= "0";
		public static final String	PARAM_ERROR		= "1";
		public static final String	LOGIC_ERROR		= "2";
		public static final String	SYSTEM_ERROR	= "3";
		public static final String	AUTH_ERROR		= "4";
		public static final String	OTHER_ERROR		= "9";
	}
	
	/**********************************************
	C2DM 메세지 타입
	==============
	***********************************************/
	interface ActionType {
		public static final String	MSG					= "msg";			//쪽지 메세지
		public static final String	CHAT_MSG			= "chatMsg";		//실시간채팅 메세지
		public static final String	ROOM_IN_MSG			= "roomInMsg";		//실시간채팅 입장시 보내지는 메세지
		public static final String	ROOM_OUT_MSG		= "roomOutMsg";		//실시간채팅 퇴장시 보내지는 메세지
		public static final String	KICK_OUT_MSG		= "kickOutMsg";		//강제 퇴장시 보내지는 메세지
		public static final String	TALK_MSG			= "talkMsg";		//톡투미 댓글 알림 메세지
		public static final String	WINK_MSG			= "winkMsg";		//윙크 수신알림 메세지
		public static final String	PASS_MSG			= "passMsg";		//임시 패스워드 발급 메세지
		public static final String	GIFT_MSG			= "giftMsg";		//윙크 수신알림 메세지
	}
	
	/**********************************************
	회원 상태
	==============
	***********************************************/
	interface MemberStatus {
		public static final String	ACTIVE			= "A";
		public static final String	PROCESSING		= "P";
		public static final String	CANCEL			= "C";
	}
	
	interface FileExt {
		public static final String	JPEG					= "jpg";
	}
	
	interface UploadFileUsageType {
		public static final String	MAIN_PHOTO				= "mainPhoto";
		public static final String	SUB_PHOTO_01			= "subPhoto01";
		public static final String	SUB_PHOTO_02			= "subPhoto02";
		public static final String	SUB_PHOTO_03			= "subPhoto03";
		public static final String	SUB_PHOTO_04			= "subPhoto04";
		public static final String	SUB_PHOTO_05			= "subPhoto05";
		public static final String	SUB_PHOTO_06			= "subPhoto06";
		public static final String	SUB_PHOTO_07			= "subPhoto07";
		public static final String	SUB_PHOTO_08			= "subPhoto08";
	}
	
	interface MemberAttrName {
		public static final String	MAIN_PHOTO				= "mainPhoto";
		public static final String	SUB_PHOTO_01			= "subPhoto01";
		public static final String	SUB_PHOTO_02			= "subPhoto02";
		public static final String	SUB_PHOTO_03			= "subPhoto03";
		public static final String	SUB_PHOTO_04			= "subPhoto04";
		public static final String	SUB_PHOTO_05			= "subPhoto05";
		public static final String	SUB_PHOTO_06			= "subPhoto06";
		public static final String	SUB_PHOTO_07			= "subPhoto07";
		public static final String	SUB_PHOTO_08			= "subPhoto08";
		public static final String	LAST_READ_NOTICE_ID		= "lastReadNoticeId";
		public static final String	ONESELF_CERTIFICATION	= "oneselfCertification";
		public static final String	LAST_LOGIN_DAY			= "lastLoginDay";
		public static final String	PASS_EXPIRE_DAY			= "passExpireDay";
	}
	
	interface ItemGroup {
		public static final String	POINT					= "G00";	//포인트
		public static final String	MULTIPLE_USE_TICKET		= "G01";	//정액권
		public static final String	WINK					= "G02";	//윙크
		public static final String	GIFT					= "G03";	//선물
	}
	
	interface ItemCode {
		public static final String	POINT					= "T00000";	//포인트
		public static final String	PASS_1W					= "T01001";	//정액권(1주일)
		public static final String	PASS_1M					= "T01002";	//정액권(1달)
		public static final String	WINK					= "T02001";	//윙크
		public static final String	GIFT_FLOWER				= "T03001";	//꽃다발
	}
	
	interface CheckPlus {
		public static final String	SITE_CODE				= "G1519";
		public static final String	SITE_PASSWD				= "D5JD6GNBB2TB";
		public static final String	SUCCESS_RTN_URL			= "/missingu/theshop/checkplus_success.html";
		public static final String	ERROR_RTN_URL			= "/missingu/theshop/checkplus_error.html";
	}
	
	interface DaouPay {
		public static final String	CPID				= "CTS10200";
		public static final String	SUCCESS_MSG			= "SUCCESS";
		public static final String	FAIL_MSG			= "FAIL";
		public static final String	HOMEURL				= "/missingu/pay/orderComplete.html";
		public static final String	FAILURL				= "/missingu/theshop/pointCharge.html";
		public static final String	CLOSEURL			= "/missingu/theshop/pointCharge.html";
		
	}
	
	interface RequestURI {
		public static final String	CREATE_ROOM				= "/missingu/chat/createRoom.html";		//채팅방 개설
		public static final String	ROOM_IN					= "/missingu/chat/roomIn.html";			//채팅방 입장
		public static final String	SEND_MSG				= "/missingu/msgbox/sendMsg.html";		//쪽지 보내기
		public static final String	OPEN_MSG				= "/missingu/msgbox/openMsg.html";		//쪽지 개봉
		public static final String	SAVE_TALK				= "/missingu/talktome/saveTalk.html";	//톡투미 글쓰기
		public static final String	SAVE_TALK_REPLY			= "/missingu/talktome/saveTalkReply.html";	//톡투미 댓글달기
		public static final String	SEND_WINK				= "/missingu/friends/sendWink.html";	//윙크 보내기
		public static final String	SEND_GIFT				= "/missingu/friends/sendGift.html";	//윙크 보내기
		public static final String	LACK_OF_POINT_ERROR		= "/missingu/common/lackOfPointError.html";	//포인트 부족
		public static final String	CONFIRM_TO_USE_POINT	= "/missingu/common/confirmUsingPoint.html";//포인트 사용 컨펌
	}
	
	interface SexCode {
		public static final String	MALE				= "G01001";		//남자
		public static final String	FEMALE				= "G01002";		//여자
	}
	
	interface BloodTypeCode {
		public static final String	A				= "B01001";		//남자
		public static final String	B				= "B01002";		//남자
		public static final String	O				= "B01003";		//남자
		public static final String	AB				= "B01004";		//남자
	}
		
	interface EventTypeCd {
		public static final String	INCOME				= "I";		//수입
		public static final String	OUTCOME				= "O";		//지출
	}
	
	interface LunarSolarCd {
		public static final String  SOLAR		= "A00101"; //양력
		public static final String  LUNAR		= "A00102"; //음력(편달)
		public static final String  LUNAR_LEAP	= "A00103"; //음력(윤달)
	}
	
	interface emailType {
		public static final String  MEMBERSHIP_JOIN_CONFIRM	= "ET00001"; //회원가입 완료
		public static final String  TEMP_PASSWORD			= "ET00002"; //임시 패스워드 발급
	}
	
	/**********************************************
	궁합 천간코드
	==============
	***********************************************/
	interface ChunganCd {
		public static final String  GAB		= "C01"; //갑
		public static final String  EUL		= "C02"; //을
		public static final String  BYOUNG	= "C03"; //병
		public static final String  JUNG	= "C04"; //정
		public static final String  MU		= "C05"; //무
		public static final String  GI		= "C06"; //기
		public static final String  KYUNG	= "C07"; //경
		public static final String  SHIN	= "C08"; //신
		public static final String  IM		= "C09"; //임
		public static final String  GAE		= "C10"; //계
	}
	
	/**********************************************
	궁합 지지코드
	==============
	***********************************************/
	interface JijiCd {
		public static final String  JA		= "A01"; //자
		public static final String  CHOOK	= "A02"; //축
		public static final String  IN		= "A03"; //인
		public static final String  MYO		= "A04"; //묘
		public static final String  JIN		= "A05"; //진
		public static final String  SA		= "A06"; //사
		public static final String  OH		= "A07"; //오
		public static final String  ME		= "A08"; //미
		public static final String  SHIN	= "A09"; //신
		public static final String  YOO		= "A10"; //유
		public static final String  SOOL	= "A11"; //술
		public static final String  HAE		= "A12"; //해
	}
	
	/**********************************************
	궁합 타입
	==============
	***********************************************/
	interface HarmonyType {
		public static final String  INNER	= "inner"; //속궁합
		public static final String  OUTER	= "outer"; //겉궁합
		public static final String  BLOOD	= "blood"; //혈액형궁합
		public static final String  SIGN	= "sign";  //띠궁합
	}
	
	/**********************************************
	오행 코드
	==============
	***********************************************/
	interface FiveElement {
		public static final String  MOK		= "E01"; //목
		public static final String  WHA		= "E02"; //화
		public static final String  TOU		= "E03"; //토
		public static final String  GUM		= "E04"; //금
		public static final String  SU		= "E05"; //수
	}
	
	/**********************************************
	MissingU Common Constant
	==============
	***********************************************/
	public static final String	AES_KEY			= "0OKM9IJN8UHB7YGV";
	public static final String	IV_PARAMETER	= "0OKM9IJN8UHB7YGV";
	
	public static final byte[] 	AES_KEY_SYNC				= new byte[] { 0x39, 0x4d, 0x38, 0x49, 0x37, 0x53, 0x36, 0x53, 0x35, 0x49, 0x34, 0x4e, 0x33, 0x47, 0x32, 0x55 };
	public static final byte[]	IV_PARAMETER_SYNC		= new byte[] { 0x4d, 0x39, 0x49, 0x30, 0x53, 0x31, 0x53, 0x32, 0x49, 0x33, 0x4e, 0x34, 0x47, 0x35, 0x55, 0x36 };
	
	public static final String	YES				= "Y";
	public static final String	NO				= "N";
	public static final String	URL_SUFFIX		= "Url";
	
	//public static final String  APK_SAVE_PATH			= "/opt/lampp/tomcat60/files/apk/";
	//public static final String  APPLICATION_ROOT_PATH	= "/opt/lampp/tomcat60/webapps/ROOT";
	public static final String  PR_IMAGE_SAVE_PATH		= "/files/profile";
	public static final String  TALK_IMG_SAVE_PATH		= "/files/talktome";
	public static final String  MANTOMAN_IMG_SAVE_PATH		= "/files/mantoman";
	public static final String  MISSIONMATCH_IMG_SAVE_PATH		= "/files/mission";
	
	public static final String  ORIGINAL_IMAGE_SUFFIX	= "org";
	public static final String  BIG_IMAGE_SUFFIX		= "big";

}