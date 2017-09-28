<?php
include "dbconn.php";

$today = date("Ymd");
$target_path = "files/" . $today;

$dirRoot = $_SERVER["DOCUMENT_ROOT"];
 
if(is_dir($dirRoot."/missing_u/".$target_path)){
//echo "폴더 존재 O"; 
}else{
//echo "폴더 존재 X";
	@mkdir($dirRoot."/missing_u/".$target_path, 0755);
}

$thumbnail_name_org = $_FILES["uploadedfile"]["name"];	
$ext_array = explode('.',$thumbnail_name_org);	
$ext = strtolower(array_pop($ext_array));	
$contents_name = md5(uniqid(rand(), true)) . "." . $ext;
	
//$tmp_img = explode("." ,$_FILES['uploadedfile']['name']); 
//$img_name = $tmp_img[0]."_".date('Y-m-d_H_i_s').".".$tmp_img[1];
//$target_path = $target_path . basename($img_name);

$target_path = $target_path . "/" . basename($contents_name);

header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<values>");

if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $target_path)) {
	//echo "The file ".$contents_name." has been uploaded";
	$upload_file_name = substr($thumbnail_name_org, 0, strlen($thumbnail_name_org) - strlen($ext) - 1);
	$file_name_array = explode('_',$upload_file_name);
	$image_index = array_pop($file_name_array);
	$mail = substr($upload_file_name, 0, strlen($upload_file_name) - strlen($image_index) - 1);
	if($image_index == 10){
		$sql = "update talktome ";
	} else {
		$sql = "update member ";
	}
	
	switch($image_index){
		case 0:
			$sql .= "set facematchphoto = '$target_path' ";
			break;	
		case 1:
			$sql .= "set orgphoto1 = '$target_path' ";
			break;
		case 2:
			$sql .= "set orgphoto2 = '$target_path' ";
			break;	
		case 3:
			$sql .= "set orgphoto3 = '$target_path' ";
			break;	
		case 4:
			$sql .= "set orgphoto4 = '$target_path' ";
			break;
		case 5:
			$sql .= "set orgphoto5 = '$target_path' ";
			break;
		case 6:
			$sql .= "set orgphoto6 = '$target_path' ";
			break;
		case 7:
			$sql .= "set orgphoto7 = '$target_path' ";
			break;
		case 8:
			$sql .= "set orgphoto8 = '$target_path' ";
			break;
		case 9:
			$sql .= "set orgphoto9 = '$target_path' ";
			break;	
		case 10:
			$sql .= "set talkphoto = '$target_path' ";
			break;	
	}
	
	if($image_index == 10){
		$sql .= "where writer_id=(select member_id from member where mail='$mail') order by talk_id DESC limit 1";
	} else {
		$sql .= ", moddate = now() where mail='$mail' ";
	}	
	
	
	
	$result=mysql_query($sql);
		
	if ($result != FALSE)
	{

		echo("<result>1</result>");		// 성공
		
	}
	else
	{
		// DB 저장 실패
		echo("<result>2</result>");
	}

} else {
	// 이미지 파일 저장 실패
	echo("<result>3</result>");
}

echo("</values>");

mysql_close();

?>
