<?
/* ========================================================================== */
/* =   프로그램 명		:	Mobile_library.php                  = */
/* =   프로그램 설명		:	Library 파일 		            = */
/* =   작성일			:	2009-02      		            = */
/* =   저작권			:	(주)다우기술                        = */
/* ========================================================================== */
?>
<?
/* ========================================================================== */
/* =   함수 명			:	MobileCert                          = */
/* =   함수 설명		:	휴대폰 인증		            = */
/* =   작성일			:	2009-02      		            = */
/* =   저작권			:	(주)다우기술      	            = */
/* ========================================================================== */
function MobileCert( $bin_url, $server_ip, $port, $cpid, $enckey, $timeout ) 
{
	global $opcode;
	global $orderno;
	global $producttype;
	global $billtype;
	global $amount;
	global $mobilecompany;
	global $mobileno;
	global $userssn;
	global $ipaddress;
	global $email;
	global $userid;
	global $username;
	global $productcode;
	global $productname;
	global $reservedindex1;
	global $reservedindex2;
	global $reservedstring;
	
	global $res_resultcode;
	global $res_errormessage;
	global $res_orderno;
	global $res_amount;
	global $res_certdate;
	global $res_daoutrx;
	global $res_limamount;
	global $res_reservedindex1;
	global $res_reservedindex2;
	global $res_reservedstring;
	
	$sendBody = $orderno.'|'.
	            $producttype.'|'.
	            $billtype.'|'.
	            $amount.'|'.
	            $mobilecompany.'|'.
	            $mobileno.'|'.
	            $userssn.'|'.
	            $ipaddress.'|'.
	            $email.'|'.
	            $userid.'|'.
	            $username.'|'.
	            $productcode.'|'.
	            $productname.'|'.
	            $reservedindex1.'|'.
	            $reservedindex2.'|'.
	            $reservedstring;
    	
  	$recvArray = array();
  	          
  	$recvArray = explode( "|", bin_do( $bin_url, $server_ip, $port, $opcode, $cpid, $timeout, $enckey, $sendBody));
  	
  	$res_resultcode    	= $recvArray[0];
  	$res_errormessage  	= $recvArray[1];
  	$res_orderno       	= $recvArray[2];
  	$res_amount        	= $recvArray[3];
  	$res_certdate      	= $recvArray[4];
  	$res_daoutrx       	= $recvArray[5];
  	$res_limamount     	= $recvArray[6];
  	$res_reservedindex1	= $recvArray[7];
  	$res_reservedindex2	= $recvArray[8];
  	$res_reservedstring	= $recvArray[9];		  
}

/* ========================================================================== */
/* =   함수 명			:	MobileSecondCert                    = */
/* =   함수 설명		:	휴대폰 2차 인증		            = */
/* =   작성일			:	2009-02      		            = */
/* =   저작권			:	(주)다우기술      	            = */
/* ========================================================================== */
function MobileSecondCert( $bin_url, $server_ip, $port, $cpid, $enckey, $timeout ) 
{
	global $opcode;
	global $daoutrx;
	global $mobileno;
	global $userssn;
	global $certno;
	global $amount;

	global $res_resultcode;
	global $res_errormessage;

	$sendBody = $daoutrx.'|'.
	            $mobileno.'|'.
	            $userssn.'|'.
	            $certno.'|'.
	            $amount;

  	$recvArray = array();

  	$recvArray = explode( "|", bin_do( $bin_url, $server_ip, $port, $opcode, $cpid, $timeout, $enckey, $sendBody));

  	$res_resultcode   = $recvArray[0];
  	$res_errormessage = $recvArray[1];
}

/* ========================================================================== */
/* =   함수 명			:	MobileSendSMS                       = */
/* =   함수 설명		:	휴대폰 인증번호 재전송              = */
/* =   작성일			:	2009-02      		            = */
/* =   저작권			:	(주)다우기술      	            = */
/* ========================================================================== */
function MobileSendSMS( $bin_url, $server_ip, $port, $cpid, $enckey, $timeout ) 
{
	global $opcode;
	global $daoutrx;
	global $mobileno;
	global $userssn;

	global $res_resultcode;
	global $res_errormessage;

	$sendBody = $daoutrx.'|'.
	            $mobileno.'|'.
	            $userssn;

  	$recvArray = array();

  	$recvArray = explode( "|", bin_do( $bin_url, $server_ip, $port, $opcode, $cpid, $timeout, $enckey, $sendBody));

  	$res_resultcode   = $recvArray[0];
  	$res_errormessage = $recvArray[1];
}

/* ========================================================================== */
/* =   함수 명			:	MobileGetInfo                       = */
/* =   함수 설명		:	휴대폰 인증정보 요청                = */
/* =   작성일			:	2009-02      		            = */
/* =   저작권			:	(주)다우기술      	            = */
/* ========================================================================== */
function MobileGetInfo( $bin_url, $server_ip, $port, $cpid, $enckey, $timeout ) 
{
	global $opcode;
	global $daoutrx;

	global $res_resultcode;    
	global $res_errormessage;  
	global $res_daoutrx;       
	global $res_certyn;        
	global $res_orderno;       
	global $res_amount;        
	global $res_producttype;   
	global $res_billtype;      
	global $res_certdate;      
	global $res_mobilecompany; 
	global $res_mobileno;      
	global $res_email;         
	global $res_userid;        
	global $res_username;      
	global $res_productcode;   
	global $res_productname;   
	global $res_reservedindex1;
	global $res_reservedindex2;
	global $res_reservedstring;

	$sendBody = $daoutrx;

  	$recvArray = array();

  	$recvArray = explode( "|", bin_do( $bin_url, $server_ip, $port, $opcode, $cpid, $timeout, $enckey, $sendBody));

  	$res_resultcode     = $recvArray[0];
  	$res_errormessage   = $recvArray[1];
  	$res_daoutrx        = $recvArray[2];
  	$res_certyn         = $recvArray[3];
  	$res_orderno        = $recvArray[4];
  	$res_amount         = $recvArray[5];
  	$res_producttype    = $recvArray[6];
  	$res_billtype       = $recvArray[7];
  	$res_certdate       = $recvArray[8];
  	$res_mobilecompany  = $recvArray[9];
  	$res_mobileno       = $recvArray[10];
  	$res_email          = $recvArray[11];
  	$res_userid         = $recvArray[12];
  	$res_username       = $recvArray[13];
  	$res_productcode    = $recvArray[14];
  	$res_productname    = $recvArray[15];
  	$res_reservedindex1 = $recvArray[16];
  	$res_reservedindex2 = $recvArray[17];
  	$res_reservedstring = $recvArray[18];
}

/* ========================================================================== */
/* =   함수 명			:	MobileAuth                          = */
/* =   함수 설명		:	휴대폰 승인 요청                    = */
/* =   작성일			:	2009-02      		            = */
/* =   저작권			:	(주)다우기술      	            = */
/* ========================================================================== */
function MobileAuth( $bin_url, $server_ip, $port, $cpid, $enckey, $timeout ) 
{
	global $opcode;
	global $daoutrx;
	global $amount;

	global $res_resultcode;  
	global $res_errormessage;
	global $res_daoutrx;     
	global $res_amount;      
	global $res_orderno;     
	global $res_limamount;   
	global $res_authdate;    
	global $res_cpname;      
	global $res_cpurl;       
	global $res_cptelno;     

	$sendBody = $daoutrx.'|'.$amount;

  	$recvArray = array();

  	$recvArray = explode( "|", bin_do( $bin_url, $server_ip, $port, $opcode, $cpid, $timeout, $enckey, $sendBody));

  	$res_resultcode  	= $recvArray[0];
  	$res_errormessage	= $recvArray[1];
  	$res_daoutrx      	= $recvArray[2];
  	$res_amount       	= $recvArray[3];
  	$res_orderno      	= $recvArray[4];
  	$res_limamount    	= $recvArray[5];
  	$res_authdate     	= $recvArray[6];
  	$res_cpname       	= $recvArray[7];
  	$res_cpurl        	= $recvArray[8];
  	$res_cptelno      	= $recvArray[9];
}

/* ========================================================================== */
/* =   함수 명			:	MobileCancel                        = */
/* =   함수 설명		:	휴대폰 승인취소 요청                = */
/* =   작성일			:	2009-02      		            = */
/* =   저작권			:	(주)다우기술      	            = */
/* ========================================================================== */
function MobileCancel( $bin_url, $server_ip, $port, $cpid, $enckey, $timeout ) 
{
	global $opcode;
	global $daoutrx;
	global $amount;
	global $cancelmemo;

	global $res_resultcode;  
	global $res_errormessage;
	global $res_daoutrx;     
	global $res_amount;      
	global $res_canceldate;         

	$sendBody = $daoutrx.'|'.
	            $amount.'|'.
	            $cancelmemo;

  	$recvArray = array();

  	$recvArray = explode( "|", bin_do( $bin_url, $server_ip, $port, $opcode, $cpid, $timeout, $enckey, $sendBody));

  	$res_resultcode  	= $recvArray[0];
  	$res_errormessage	= $recvArray[1];
  	$res_daoutrx      	= $recvArray[2];
  	$res_amount       	= $recvArray[3];
  	$res_canceldate    	= $recvArray[4];
}

/* ========================================================================== */
/* =   함수 명			:	bin_do			            = */
/* =   함수 설명		:	bin 실행 		 	    = */
/* =   작성일		        :	2009-02      		            = */
/* =   저작권			:	(주)다우기술      	            = */
/* ========================================================================== */
function bin_do( $path, $argv1="", $argv2="", $argv3="", $argv4="", $argv5="", $argv6="", $argv7="")
{
	$exec_cmd = "$path '$argv1' '$argv2' '$argv3' '$argv4' '$argv5' '$argv6' '$argv7' ";
	error_log($exec_cmd);
	$rt = exec( $exec_cmd );

	return $rt;
}
?>
