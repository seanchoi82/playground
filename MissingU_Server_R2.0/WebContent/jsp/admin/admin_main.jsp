<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/admin_header.jsp"%>
<title>MissingU Administrator</title>
</head>
<body>
<div id="container">
	<header>
		<div class="tmenu">
			<span>관리자</span>님 안녕하세요 (2012-12-15 16:00:21) | <a href="/logout.html">로그아웃</a>
		</div>
		<div class="logo">MissingU 관리자모드</div>		
		<nav>
			<ul class="tabhost">
				<li><a href="#">회원관리</a></li>
				<li><a href="#">결제/포인트 관리</a></li>
				<li><a href="#">공지사항 관리</a></li>
				<li class="select"><a href="/missingu/admin/apkList.html">개발 APK 관리</a></li>
			</ul>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li class="select"><a href="javascript:apkList()">APK 목록</a></li>
				<li><a href="javascript:apkAdd();">APK 업로드</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<iframe id="iframe_content" src="/missingu/admin/apkList.html" width="100%" height="80%"></iframe>
			</div>
			<ol class="info">
				<li>하단 설명 글 위치</li>
			</ol>
		</div>
	</div>
</div>
<script type="text/javascript">
	function apkList() {
		$("#iframe_content").attr("src", "/missingu/admin/apkList.html");
	}
	
	function apkAdd() {
		$("#iframe_content").attr("src", "/missingu/admin/apkAdd.html");
	}
</script>
</body>
</html>