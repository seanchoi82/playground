<?php

include "dbconn.php";


$member_id = $_REQUEST["member_id"]; 
$title = $_REQUEST["title"]; 
$content = $_REQUEST["content"]; 

header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<values>");

$sql = "insert into talktome (writer_id, title, content, reg_date ) ";
$sql .= "values ($member_id, '$title', '$content', now() )";

$result=mysql_query($sql);
		
if ($result != FALSE)
{
	// 성공
	echo("<result>1</result>");
}
else
{
	// 추가 실패
	echo("<result>2</result>");
	//echo $sql;
		
}

echo("</values>");

mysql_close();


?>