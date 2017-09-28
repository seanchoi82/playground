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
			<span>관리자</span>님 안녕하세요! | <a href="/logout.html">로그아웃</a>
		</div>
		<div class="logo">MissingU 관리자모드</div>		
		<nav>
			<jsp:include page="/jsp/common/admin_top_nav.jsp" flush="false">
			        <jsp:param name="selected" value="apkList"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li><a href="javascript:apkList()">APK 목록</a></li>
				<li class="select"><a href="javascript:apkAdd();">APK 업로드</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">APK 업로드</h2>
				
				<div class="pt30">
				<form:form id="apkAddForm" modelAttribute="apkItem" action="/missingu/admin/apkAddSubmit.html"	enctype="multipart/form-data">
					<div align="center" class="body">
					<table>
						<tr height="40px">
							<td>설명</td>
							<td><form:input path="apkDesc" cssClass="apkDesc" maxlength="50" /></td>
							<td><font color="red"><form:errors path="apkDesc" /></font></td>
						</tr>
						<tr height="40px">
							<td>버전</td>
							<td><form:input path="apkVersion" cssClass="apkVersion" maxlength="50" /></td>
							<td><font color="red"><form:errors path="apkVersion" /></font></td>
						</tr>
						<tr height="40px">
							<td>등록자</td>
							<td><form:input path="registerName" cssClass="registerName" maxlength="50" /></td>
							<td><font color="red"><form:errors path="registerName" /></font></td>
						</tr>
						<tr height="40px">
							<td>APK 파일</td>
							<td><input type="file" name="apkFile" /></td>
							<td></td>
						</tr>
					</table>
					<br>
				</form:form>
				</div>
				<div class="ui-btns">
					<button id="btn_submit" data-click-callback="submit()">등록</button>
					<button id="btn_reset" data-click-callback="reset()">다시 입력</button>
				</div>
				<!-- Content 끝 -->
			</div>
			<ol class="info">
				<li></li>
			</ol>
		</div>
	</div>
</div>
<script type="text/javascript">
	function apkList() {
		location.href="/missingu/admin/apkList.html";
	}
	
	function apkAdd() {
		location.href="/missingu/admin/apkAdd.html";
	}
	
	function submit() {
		$("#apkAddForm").submit();
	}
	
	function reset() {
		$("#apkAddForm").each(function(){
			this.reset();
		});
	}
</script>
</body>
</html>