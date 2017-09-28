<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/admin_header.jsp"%>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<title>MissingU Administrator</title>
</head>
<body>
<div id="container">
<form action="j_spring_security_check" method="post">
	<div class="loginbox">
		<div id="accordion">
			<div><h3>관리자 로그인</h3></div>
			<div>
				<div class="login"><button data-click-callback="login()">로그인</button></div>
				<ul>
					<li><label for="j_username">ID : </label> <input type="text" name="j_username" id="j_username" /></li>
					<li><label for="j_password">PW : </label> <input type="password" name="j_password" id="j_password" /></li>
				</ul>
				<ul>
					<c:if test="${not empty param.login_error}">
						<font color="red">${SPRING_SECURITY_LAST_EXCEPTION.message}</font>
					</c:if>
				</ul>
			</div>
		</div>
		<ol class="info">
			<li>* 로그인 3회 실패시 IP가 차단됩니다.</li>
		</ol>
	</div>
</form>
</div>
<script type="text/javascript">
$( "#accordion" ).accordion();
$(".loginbox").center();

function login() {
	//alert("login!");
}
</script>
</body>
</html>