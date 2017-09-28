<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>MissingU Administrator</title>
<%@ include file="/jsp/common/admin_header.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/jqpagination/jqpagination.css">
<script src="/js/jquery.jqpagination.js"></script>
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
			        <jsp:param name="selected" value="manToManList"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li class="select"><a href="chatServer.html">일반채팅</a></li>
				<li><a href="chatServerRandom.html">랜덤채팅</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">일반채팅</h2>
				<div class="chatserver">
					<div class="listbox groupList">
						<div class="tit">
							<h4>그룹</h4>
							<button class="small" id="group-reflush">새로고침</button>
						</div>
						<ul id="chat-group-list" class="list">
							<li>
								<input type="text" name="group_nm" value="방제목 1번" class="group_nm_update" data-idx="1" />
								<span>[삭제]</span>
							</li>
							<li>
								<input type="text" name="group_nm" value="방제목 1번" class="group_nm_update" data-idx="2" />
								<span>[삭제]</span>
							</li>
							<li>
								<input type="text" name="group_nm" value="방제목 1번" class="group_nm_update" data-idx="3" />
								<span>[삭제]</span>
							</li>
						</ul>
						<hr />
						<div>
							<input type="text" value="" id="group_nm" class="group_nm_update" />
							<button class="small" id="group-add">추가</button>	
						</div>
						
					</div>
					<div class="roomList">
						<h4>방목록</h4>
					b
					</div>
					<div class="room">
						<h4><span id="room_owner"></span>님의 방-<span id="room_name"></span></h4>
					c
					</div>
				</div>				
			</div> 
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		
		// 그룹수정
		$('.group_nm_update').bind('keypress', function(e) {
			if(e.keyCode==13 || e.which == 13){
				$this = $(e.target);
				var id = $this.attr("data-idx");
				var val = $this.val();
				
				alert("방번호 : " + id + ", 방 제목 : " + val);
			}
		});
		
		// 그룹 새로고침
		$("#group-reflush").click(function(e) {
			groupList();
		});
		
		// 그룹추가
		$("#group-add").click(function(e) {
		});
		
		groupList();
	}) ;
	
	/**
	* 그룹 목록 가져오기
	*/
	function groupList() {
		
	}
	
</script>

</body>
</html>