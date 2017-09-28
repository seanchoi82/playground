<?
/* ========================================================================== */
/* =   프로그램 명	:	MobileAuth.php                              = */
/* =   프로그램 설명	:	Mobile 승인요청 샘플  파일                  = */
/* =   작성일		:	2009-04      		                    = */
/* =   저작권		:	(주)다우기술                                = */
/* ========================================================================== */
?>
<?
/* ========================================================================== */
/* =   데이터 셋업 					                    = */
/* ========================================================================== */
require "./library/Mobile_library.php";
require "./config/common_config.cfg";

/* ========================================================================== */
/* =   요청 정보 설정                                   		    = */
/* ========================================================================== */
/* = 	 NOT NULL	                                                    = */
/* ========================================================================== */
$opcode		=	"303";				// 요청(변경불가)
$daoutrx	=	$_POST["daoutrx"];		// 다우거래번호
$amount		=	$_POST["amount"];		// 결제금액
$mobileno	= $_POST["mobileno"]; 	// 핸드폰번호å
$productname	=	$_POST["productname"];	// 상품명
    
/* ========================================================================== */
/* = 	 승인 요청				     	                    = */
/* ========================================================================== */
MobileAuth( BIN_URL, SERVER_IP, MOBILE_PORT, CPID, ENCKEY, TIMEOUT );

/* ========================================================================== */
/* = 	 리턴값 로그		  			                    = */
/* ========================================================================== */
error_log(print_r($_POST,true));
error_log(print_r('#####'.$_SERVER['PHP_SELF'].'#####',true));
error_log("res_resultcode    : ".$res_resultcode    );
error_log("res_errormessage  : ".$res_errormessage  );
error_log("res_daoutrx       : ".$res_daoutrx       );
error_log("res_amount        : ".$res_amount        );
error_log("res_orderno       : ".$res_orderno       );
error_log("res_limamount     : ".$res_limamount     );
error_log("res_authdate      : ".$res_authdate      );
error_log("res_cpname	     : ".$res_cpname        );
error_log("res_cpurl	     : ".$res_cpurl         );
error_log("res_cptelno	     : ".$res_cptelno       );
error_log(print_r('#####'.$_SERVER['PHP_SELF'].'#####',true));

/* ========================================================================== */
/* =  결과코드 0000 : 성공	그외 : 실패				    = */
/* ========================================================================== */
if( $res_resultcode != "0000" ) {
	include("MobileFail.html");  // 실패 페이지
} else {
        // DB처리 : 성공에 대한 처리
        include("MobileAuthSucc.html"); // 성공 페이지
}
?>
