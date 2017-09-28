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
			<span>김영환</span>님 안녕하세요! | <a href="/logout.html">로그아웃</a>
		</div>
		<div class="logo">MissingU 관리자모드</div>		
		<nav>
			<ul class="tabhost">
				<li><a href="/missingu/admin/memberSearch.html">회원관리</a></li>
				<li><a href="/missingu/admin/payHistList.html">결제/포인트 관리</a></li>
				<li><a href="/missingu/admin/noticeList.html">공지사항 관리</a></li>
				<li><a href="/missingu/admin/apkList.html">개발 APK 관리</a></li>
				<li class="select"><a href="/missingu/admin/apkList.html">위치정보 민원처리</a></li>
			</ul>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li><a href="javascript:apkList()">위치정보 이용/제공 자료</a></li>
				<li class="select"><a href="javascript:apkAdd();">위치정보 열람 확인자료</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">위치정보 열람 확인자료</h2>
				
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="결제정보 입력">
						<caption>결제정보입력</caption>
						<colgroup>
							<col width="150">
							<col width="150">
							<col width="150">
							<col width="*">
							<col width="150">
						</colgroup>
						<tr id="tb_header">
							<th>번호</th>
							<th>요청회원ID</th>
							<th>취급자</th>
							<th>목적</th>
							<th>처리일시</th>
						</tr>
						<tr class="record">
							<td align="center">5</td>
							<td align="center">1000000011</td>
							<td align="center">김영환</td>
							<td align="left">위치정보 제공사실 열람요청</td>
							<td align="center">2013-01-10 14:25:23</td>
						</tr>
						<tr class="record">
							<td align="center">4</td>
							<td align="center">1000000023</td>
							<td align="center">김영환</td>
							<td align="left">위치정보 제공사실 열람요청</td>
							<td align="center">2013-01-11 10:35:42</td>
						</tr>
						<tr class="record">
							<td align="center">3</td>
							<td align="center">1000000035</td>
							<td align="center">김영환</td>
							<td align="left">위치정보 제공사실 열람요청</td>
							<td align="center">2013-01-12 11:32:32</td>
						</tr>
						<tr class="record">
							<td align="center">2</td>
							<td align="center">1000000024</td>
							<td align="center">김영환</td>
							<td align="left">위치정보 제공사실 열람요청</td>
							<td align="center">2013-01-12 14:36:29</td>
						</tr>
						<tr class="record">
							<td align="center">1</td>
							<td align="center">1000000015</td>
							<td align="center">김영환</td>
							<td align="left">위치정보 제공사실 열람요청</td>
							<td align="center">2013-01-12 16:15:16</td>
						</tr>
					</table>
				</div>
				
				<div class="ui-btns">
					<button id="btn_new" data-click-callback="apkAdd()">입력</button>
				</div>
				<!-- Content 끝 -->
			</div>
			<ol class="info">
				<li>.</li>
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
	
	function deleteApk(apkId) {
		if(confirm("삭제하시겠습니까?")) {
			location.href="/missingu/admin/apkDelete.html?apkId="+apkId;
		}
	}
</script>
</body>
</html>