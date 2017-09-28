<?
include "../missing_u/xml_writer.php";

$xml = new SimpleXmlWriter(); 

header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<Values>");
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
$mobileno	= $_POST["mobileno"]; 	// 핸드폰번호
$productname	=	$_POST["productname"];	// 상품명
$member_id	=	$_POST["member_id"];
    
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
	//include("MobileFail.html");  // 실패 페이지
	$xml->Add('resultcode', $res_resultcode);
	$xml->Add('errormessage', iconv("EUC-KR", "UTF-8", $res_errormessage));
	echo $xml->xml;
	echo("<result>2</result>");		// 실패
	echo("</Values>");
} else {
    // DB처리 : 성공에 대한 처리
    // 성공처리
    $xml->Add('shopname', iconv("EUC-KR", "UTF-8", $res_cpname.'('.$res_cpurl.')'));  
    $xml->Add('shoptel', $res_cptelno); 
    $xml->Add('productname', $productname);
    $xml->Add('amount', $res_amount); 
    $xml->Add('mobileno', $mobileno);
    $xml->Add('resulttime', preg_replace('/^(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})$/', '\1년 \2월 \3일 \4시 \5분 ',trim($res_authdate)));
	//$xml->Add('daoutrx', $res_daoutrx);	
	//$xml->Add('userssn', $userssn);
	//$xml->Add('email', $email);
	//$xml->Add('resultcode', $res_resultcode);
	//$xml->Add('errormessage', iconv("EUC-KR", "UTF-8", $res_errormessage));
	//$xml->Add('orderno', $res_orderno);
	//$xml->Add('certdate', $res_certdate);
	//$xml->Add('limitamount', $res_limamount);
	//$xml->Add('reservedindex1', $res_reservedindex1);
	//$xml->Add('reservedindex2', $res_reservedindex2);
	//$xml->Add('reservedstring', $res_reservedstring);
	echo $xml->xml;
	echo("<result>1</result>");		// 성공 
	echo("</Values>");       
        //include("MobileAuthSucc.html"); // 성공 페이지
}
include "../missing_u/dbconn.php";

$remarks = $productname . " 구매";

$sql = "insert into daoupay (daoupay_id, member_id, remarks, gubun, amount, pay_date ) ";
$sql .= "values ('$res_daoutrx', $member_id, '$remarks', '-', $res_amount, now() )";

$result=mysql_query($sql);
		
if ($result != FALSE)
{

	$pointStr = str_replace("포인트" , "", $productname);
	$pointStr2 = str_replace("," , "", $pointStr);
	
	$sql3 = "insert into point (member_id, remarks, gubun, amount, mod_date ) ";
	$sql3 .= "values ($member_id, '$remarks', '+', $pointStr2, now() )";

	$result3=mysql_query($sql3);
	
	if ($result != FALSE)
	{
		// 성공
		$sql2 = "update member set point = point+$pointStr2, moddate = now() where member_id=$member_id ";
		$result2=mysql_query($sql2);
		//echo("<result>1</result>");
	}

}
else
{
	// 추가 실패 시 결제 취소 페이지로 이동
	//echo("<result>2</result>");
	//echo $sql;
		
}

mysql_close();
?>
