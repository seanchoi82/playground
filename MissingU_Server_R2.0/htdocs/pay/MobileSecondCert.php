<?
/* ========================================================================== */
/* =   프로그램 명	:	MobileSecondCert.php                        = */
/* =   프로그램 설명	:	Mobile 2차인증 샘플 파일                    = */
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
$opcode		= "302";			// 요청(변경불가)
$daoutrx	= $_POST["daoutrx"];	// 다우거래번호
$mobileno	= $_POST["mobileno"]; 	// 핸드폰번호
$userssn	= $_POST["userssn"]; 	// 주민번호
$certno		= $_POST["certno"]; 	// SMS인증번호
$amount		= $_POST["amount"]; 	// 결제금액

$productname= $_POST["productname"];	// 상품명
    
/* ========================================================================== */
/* = 	  2차 인증 요청							    = */
/* ========================================================================== */
MobileSecondCert( BIN_URL, SERVER_IP, MOBILE_PORT, CPID, ENCKEY, TIMEOUT );

/* ========================================================================== */
/* = 	 리턴값 로그							    = */
/* ========================================================================== */
error_log(print_r($_POST,true));
error_log(print_r('#####'.$_SERVER['PHP_SELF'].'#####',true));
error_log("res_resultcode    : ".$res_resultcode);
error_log("res_errormessage  : ".$res_errormessage);
error_log(print_r('#####'.$_SERVER['PHP_SELF'].'#####',true));

/* ========================================================================== */
/* =  결과코드 0000 : 성공	그외 : 실패				    = */
/* ========================================================================== */
if( $res_resultcode != "0000" ) {
        // 실패 처리
       	include("MobileFail.html");  // 실패 페이지
} else {
        // 성공처리
?>
<html>
<head>
</head>
<body onload="javascript:pay_2cert.submit();">
	<form name="pay_2cert" method="post" action="MobileAuth.php">
		<input type="hidden" name="daoutrx"  	value="<?=$daoutrx?>">
		<input type="hidden" name="amount"  	value="<?=$amount?>">
		<input type="hidden" name="mobileno"  	value="<?=$mobileno?>">
        	<input type="hidden" name="productname"  value="<?=$productname?>">
	</form>
</body>
</html>

<?
}
?>
