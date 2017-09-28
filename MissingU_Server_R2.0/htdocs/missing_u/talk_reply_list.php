<?php

include "dbconn.php";
include "xml_writer.php";

$talk_id = $_REQUEST["talk_id"]; 
$already_view = $_REQUEST["already_view"];
$sql = "select *, (select gender from member where member_id=writer_id) as gender, ";
$sql .= "(select nick from member where member_id=writer_id) as nick, ";
$sql .= "(select thumbnailphoto from member where member_id=writer_id) as thumbstr ";
$sql .= "from talktome where reply_id=$talk_id order by reg_date ASC ";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);

header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<values>");

if ($result != FALSE){
	if($already_view != 1){
		$sql2 = "update talktome set count = count + 1 where talk_id = $talk_id ";
	
		$result2=mysql_query($sql2);
	}


	if ($total_records > 0) {
		echo("<result>1</result>");		// 성공
		echo("<articles>");
		
		while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
			echo("<article>");
			$xml = new SimpleXmlWriter();

			$xml->Add('content', $row['content']);
			$xml->Add('reg_date', $row['reg_date']);
			$xml->Add('gender', $row['gender']);
			$xml->Add('nick', $row['nick']);
			$xml->Add('thumbstr', $row['thumbstr']);
			
			echo $xml->xml;
			echo("</article>");
		}
		echo("</articles>");
		
	} else {
		echo("<result>2</result>");	// 등록된 톡이 없습니다
	}
	

} else {
	echo("<result>3</result>"); // 데이터베이스 에러입니다
	//echo("<query>" . $sql . "</query>");
}

echo("</values>");

mysql_close();
?>