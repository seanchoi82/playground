<?php

include "dbconn.php";


$member_id = $_REQUEST["member_id"]; 
$reply_id = $_REQUEST["reply_id"]; 
$content = $_REQUEST["content"]; 


$sql = "insert into talktome (writer_id, reply_id, title, content, reg_date ) ";
$sql .= "values ($member_id, $reply_id, 're', '$content', now() )";

$result=mysql_query($sql);

header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<values>");

if ($result != FALSE)
{
	$sql2 = "update talktome set reply_count = reply_count + 1 where talk_id = $reply_id ";
	
	$result2=mysql_query($sql2);
	
	if ($result2 != FALSE) {
		// 성공
		echo("<result>1</result>");
	} else {
		// 카운트 추가 실패
		echo("<result>3</result>");
		//echo $sql;
	}

}
else
{
	// 추가 실패
	echo("<result>2</result>");
	//echo("<query>" . $sql . "</query>");
	//echo $sql;
		
}

echo("</values>");

mysql_close();


?>