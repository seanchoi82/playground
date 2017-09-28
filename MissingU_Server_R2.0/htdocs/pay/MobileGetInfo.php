<?
/* ========================================================================== */
/* =   프로그램 명	:	MobileGetInfo.php                           = */
/* =   프로그램 설명	:	Mobile 결제정보 요청 샘플 파일              = */
/* =   작성일		:	2009-04      		                    = */
/* =   저작권		:	(주)다우기술                                = */
/* ========================================================================== */
?>
<?
/* ========================================================================== */
/* =   데이터 셋업 							    = */
/* ========================================================================== */
require "./library/Mobile_library.php";     
require "./config/common_config.cfg";  		

/* ========================================================================== */
/* =   요청 정보 설정                                             	    = */
/* ========================================================================== */
/* = 	 NOT NULL							    = */
/* ========================================================================== */
$opcode 	=	"305";		        // 요청(변경불가)
$daoutrx	=	$_POST["daoutrx"];    // 다우거래번호
    
/* ========================================================================== */
/* = 	 결제정보 요청							    = */
/* ========================================================================== */
MobileGetInfo( BIN_URL, SERVER_IP, MOBILE_PORT, CPID, ENCKEY, TIMEOUT );

/* ========================================================================== */
/* = 	 리턴값 로그							    = */
/* ========================================================================== */
error_log(print_r($_POST,true));
error_log(print_r('#####'.$_SERVER['PHP_SELF'].'#####',true));
error_log("res_resultcode    : ".$res_resultcode);
error_log("res_errormessage  : ".$res_errormessage);
error_log("res_daoutrx       : ".$res_daoutrx);
error_log("res_certyn        : ".$res_certyn);
error_log("res_orderno       : ".$res_orderno);
error_log("res_amount        : ".$res_amount);
error_log("res_producttype   : ".$res_producttype);
error_log("res_billtype      : ".$res_billtype);
error_log("res_certdate      : ".$res_certdate);
error_log("res_mobilecompany : ".$res_mobilecompany);
error_log("res_mobileno      : ".$res_mobileno);
error_log("res_email	     : ".$res_email);
error_log("res_userid        : ".$res_userid);
error_log("res_username      : ".$res_username);
error_log("res_productcode   : ".$res_productcode);
error_log("res_productname   : ".$res_productname);
error_log("res_reservedindex1: ".$res_reservedindex1);
error_log("res_reservedindex2: ".$res_reservedindex2);
error_log("res_reservedstring: ".$res_reservedstring);
error_log(print_r('#####'.$_SERVER['PHP_SELF'].'#####',true));

/* ========================================================================== */
/* =  결과코드 0000 : 성공	그외 : 실패				    = */
/* ========================================================================== */
if( $res_resultcode != "0000" ) {
        // 실패 처리
} else {
        // 성공처리
}
?>
