<?
include "../missing_u/xml_writer.php";

$xml = new SimpleXmlWriter(); 

header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<Values>");
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
	//include("MobileFail.html");  // 실패 페이지
	$xml->Add('resultcode', $res_resultcode);
	$xml->Add('errormessage', iconv("EUC-KR", "UTF-8", $res_errormessage));
	echo $xml->xml;
	echo("<result>2</result>");		// 실패
} else {
        // 성공처리
	$xml->Add('daoutrx', $daoutrx);
	$xml->Add('mobileno', $mobileno);
	//$xml->Add('userssn', $userssn);
	$xml->Add('productname', $productname);
	//$xml->Add('email', $email);
	//$xml->Add('resultcode', $res_resultcode);
	//$xml->Add('errormessage', iconv("EUC-KR", "UTF-8", $res_errormessage));
	//$xml->Add('orderno', $res_orderno);
	$xml->Add('amount', $amount);
	//$xml->Add('certdate', $res_certdate);
	//$xml->Add('limitamount', $res_limamount);
	//$xml->Add('reservedindex1', $res_reservedindex1);
	//$xml->Add('reservedindex2', $res_reservedindex2);
	//$xml->Add('reservedstring', $res_reservedstring);
	echo $xml->xml;
	echo("<result>1</result>");		// 성공
}
echo("</Values>");
?>
