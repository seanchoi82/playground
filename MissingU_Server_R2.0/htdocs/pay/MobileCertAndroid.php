<?
include "../missing_u/xml_writer.php";

$xml = new SimpleXmlWriter(); 

header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<Values>");
/* ========================================================================== */
/* =   프로그램 명	:	MobileCert.php                              = */
/* =   프로그램 설명	:	Mobile 인증 샘플  파일                      = */
/* =   작성일		:	2009-04      		                    = */
/* =   저작권		:	(주)다우기술                                = */
/* ========================================================================== */

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
$opcode		=	"301";	                        // 요청(변경불가)
$orderno	=	$_POST["orderno"];	// 주문번호
$producttype	=	$_POST["producttype"];	// 상품구분
$billtype	=	$_POST["billtype"];	// 과금유형
$amount		=	$_POST["amount"];	// 결제금액
$mobilecompany	=	$_POST["mobilecompany"];	// 이동통신사 항목(SKT,KTF,LGT)
$mobileno	=	$_POST["mobileno"];	// 휴대폰번호
$userssn	=	$_POST["userssn"];	// 주민번호
$ipaddress	=	getenv("REMOTE_ADDR");	// 요청 IP

/* ========================================================================== */
/* = 	 NULL 허용				     	                    = */
/* ========================================================================== */
$email		=	$_POST["email"];	// 고객E-MAIL
$userid		=	$_POST["userid"];	// 고객 ID
$username	=	$_POST["username"];	// 고객명
$productcode	=	$_POST["productcode"];	// 상품코드
$productname	=	$_POST["productname"];	// 상품명
$reservedindex1	=	$_POST["reservedindex1"];	// 예약항목
$reservedindex2	=	$_POST["reservedindex2"];	// 예약항목
$reservedstring	=	$_POST["reservedstring"];	// 예약항목

/* ========================================================================== */
/* = 	 인증 요청				     	                    = */
/* ========================================================================== */
MobileCert( BIN_URL, SERVER_IP, MOBILE_PORT, CPID, ENCKEY, TIMEOUT );		

/* ========================================================================== */
/* = 	 리턴값 로그					   	            = */
/* ========================================================================== */
error_log(print_r($_POST,true));
error_log(print_r('#####'.$_SERVER['PHP_SELF'].'#####',true));
error_log("res_resultcode    : ".$res_resultcode    );
error_log("res_errormessage  : ".$res_errormessage  );
error_log("res_orderno       : ".$res_orderno       );
error_log("res_amount        : ".$res_amount        );
error_log("res_certdate      : ".$res_certdate      );
error_log("res_daoutrx       : ".$res_daoutrx       );
error_log("res_limamount     : ".$res_limamount     );
error_log("reservedindex1    : ".$reservedindex1    );
error_log("reservedindex2    : ".$reservedindex2    );
error_log("res_reservedstring: ".$res_reservedstring);
error_log(print_r('#####'.$_SERVER['PHP_SELF'].'#####',true));

/* ========================================================================== */
/* =      결과코드 0000 : 성공	그외 : 실패                                 = */
/* ========================================================================== */
if( $res_resultcode != "0000" ) {
	//include("MobileFail.html");  // 실패 페이지
	$xml->Add('resultcode', $res_resultcode);
	$xml->Add('errormessage', iconv("EUC-KR", "UTF-8", $res_errormessage));
	echo $xml->xml;
	echo("<result>2</result>");		// 실패
	
} else {
	//include("MobileCertSucc.html"); // 성공 페이지
	$xml->Add('daoutrx', $res_daoutrx);
	$xml->Add('mobileno', $mobileno);
	$xml->Add('userssn', $userssn);
	$xml->Add('productname', $productname);
	$xml->Add('email', $email);
	$xml->Add('resultcode', $res_resultcode);
	$xml->Add('errormessage', iconv("EUC-KR", "UTF-8", $res_errormessage));
	$xml->Add('orderno', $res_orderno);
	$xml->Add('amount', $res_amount);
	$xml->Add('certdate', $res_certdate);
	$xml->Add('limitamount', $res_limamount);
	$xml->Add('reservedindex1', $res_reservedindex1);
	$xml->Add('reservedindex2', $res_reservedindex2);
	$xml->Add('reservedstring', $res_reservedstring);
	echo $xml->xml;
	echo("<result>1</result>");		// 성공
}

echo("</Values>");
?>
