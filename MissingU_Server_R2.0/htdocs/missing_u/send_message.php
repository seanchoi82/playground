<?php

include "dbconn.php";
include "xml_writer.php";

$sender_id = $_REQUEST["sender_id"]; 
$receiver_id = $_REQUEST["receiver_id"]; 
$message = $_REQUEST["message"]; 


$sql = "insert into message (sender_id, receiver_id, sender_read, receiver_read, message, send_date) ";
$sql .= "values ($sender_id, $receiver_id, 'Y', 'N', '$message', now() )";
$result=mysql_query($sql);
$RoomId = mysql_insert_id();
if ($result == TRUE)
{

    $sql1 = "select * from member where member_id = $sender_id ";
	$result1 = mysql_query($sql1);
	//$total_records = mysql_num_rows($result);


	if ($result1 != FALSE){
		while ($row = mysql_fetch_array($result1, MYSQL_ASSOC)){
			$answerer = urlencode($row['nick']);
		}
		
		$sql2 = "select * from member where member_id = $receiver_id and reg_id is not null ";
		$result2 = mysql_query($sql2);
		
		if ($result2 != FALSE){
			while ($row2 = mysql_fetch_array($result2, MYSQL_ASSOC)){
				$registration_id = $row2['reg_id'];
			    // 아래는 기본 폼이지만 몇가지 개발자에게 맞춰야 하는 것이 있다.
    			$accountType = "HOSTED_OR_GOOGLE";
    			// 앱에서 Registration ID를 얻기 위해 기입했던 개발자의 구글 계정
    			$Email = "missinguc2dm@gmail.com";
    			// 그 계정의 비밀번호
    			$Passwd = "altlddb0314";
    			$source = "test-1.0";
    			$service = "ac2dm";
    			// 앱에서 서버로 전송할 때 사용했던 Registration ID
    			//$registration_id = RegistrationIDFromDB();
    
    			$ch = curl_init();  
    			// 아래 부분은 Sender Auth Token을 얻는 부분이다.
    			curl_setopt($ch, CURLOPT_URL, "https://www.google.com/accounts/ClientLogin");
    			curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
    			curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
    			curl_setopt($ch, CURLOPT_POST, true);
    			curl_setopt($ch, CURLOPT_HEADER, true);
    			curl_setopt($ch, CURLOPT_FRESH_CONNECT, true);
    			curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_ANY);
    			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    
    			$account_info = array("accountType" => $accountType, "Email" => $Email, "Passwd" => $Passwd, "source" => $source, "service" => $service);
    
    			curl_setopt($ch, CURLOPT_POSTFIELDS, $account_info);
    
    			$response = curl_exec($ch);
    			$auth_tmp = substr(strstr($response, "Auth="), 5);
    			$auth = substr($auth_tmp, 0, strlen($auth_tmp)-1);
    
    			$collapse_key = mt_rand(1,1000);
    
    
    			
    			$no = 1;
    
    			// 사용자 기기로 전달할 메세지와 기타 데이터를 나열한다. registration_id와 collapse_key는 필수이나 data에 해당하는 부분은 선택사항이다.
    			$data = "registration_id=".$registration_id."&collapse_key=".$collapse_key."&data.answerer=".$answerer."&data.action=msg&data.no=$no&data.msg=".urlencode($message);
    			// 만약 data로 한글을 전달하고 싶은데 막상 단말에서 받았을 때 값이 없거나 알 수 없는 문자가 입력된다면 다음과 같이 해보라.
    			// "&data.answerer='관리자'"
    			// "&data.answerer=".urlencode("관리자")."
    			curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
    
    			$headers = array(
    				"Content-Type: application/x-www-form-urlencoded", 
    				"Content-Length: ".strlen($data), 
    				"Authorization: GoogleLogin auth=".$auth 
    			);
    
    			$ch = curl_init();
    			curl_setopt($ch, CURLOPT_URL, "https://android.apis.google.com/c2dm/send");
    			curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    			curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    			curl_setopt($ch, CURLOPT_POST, true);
    			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    			curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
    			$result_curl = curl_exec($ch);
			}
			curl_close($ch);
		}
		
	}


	echo("<result>1</result>");

}
else
{
	echo("<result>2</result>"); //DB 에러
	echo("<query>" . $sql . "</query>");
}


mysql_close();


?>
