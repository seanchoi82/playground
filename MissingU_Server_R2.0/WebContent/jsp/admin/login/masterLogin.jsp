<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/missingu.css"">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<head>
<title>관리자 로그인 화면</title>
</head>
<body>
<div align="center" class="body">
<h2>관리자 로그인 화면</h2>
<c:if test="${not empty param.login_error}">
	<font color="red">${SPRING_SECURITY_LAST_EXCEPTION.message}</font>
</c:if>
<form action="j_spring_security_check" method="post">
<table>
	<tr height="40px">
		<td>유저ID</td>
		<td><input class="userId" type="text" class="text"
			name="j_username" size="20"></td>
	</tr>
	<tr height="40px">
		<td>패스워드</td>
		<td><input class="password" type="password" class="text"
			name="j_password" size="20"></td>
	</tr>
	<tr>
		<td colspan="2" align="center"></td>
	</tr>
</table>
<table>
	<tr>
		<td align="center"><input type="submit" value="로그인"></td>
	</tr>
</table>
</form>
<br>
</div>
</body>
</html>