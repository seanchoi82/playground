<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/admin_header.jsp"%>
<title>MissingU Administrator</title>
<script type="text/javascript">
/* <![CDATA[ */
$(document).ready(function() {
	//blockUI 셋팅(모든 ajax 호출 시)
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$("#lang").val("${guide.lang}").attr("selected", "selected");			//언어 셋팅
	$("#showYn").val("${guide.showYn}").attr("selected", "selected"); 		//출력여부 셋팅
});
/* ]]> */
</script>
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
			        <jsp:param name="selected" value="guideList"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li class="select"><a href="javascript:guideList()">사용자가이드 조회</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">사용자가이드 수정</h2>
				
				<form id="editForm">
				<input type="hidden" id="actionType" name="actionType" value="${reqParams.actionType}" />
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="사용자가이드">
						<caption>사용자가이드</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>언어</th>
							<td>
								<select id="lang" name="lang">
									<option value="ko">한국어</option>
									<option value="ja">일본어</option>
									<option value="zh">중국어</option>
								</select>
							</td>
							<th>출력여부</th>
							<td>
								<select id="showYn" name="showYn">
									<option value="Y">Y</option>
									<option value="N">N</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>메뉴ID</th>
							<td>
								<input type="text" id="menuId" value="${guide.menuId}" name="menuId">
								<input type="hidden" id="oldMenuId" value="${guide.menuId}" name="oldMenuId">
							</td>
							<th>메뉴명</th>
							<td><input type="text" id="menuName" value="${guide.menuName}" name="menuName"></td>
						</tr>
						<tr>
							<th>내용</th>
							<td colspan="3">
								<textarea rows="15" name="contents">${guide.contents}</textarea>
							</td>
						</tr>
					</table>
				</div>
				</form>
				
				<div class="ui-btns alignLeft">
					<button id="btn_back" data-click-callback="javascript:history.go(-1)">뒤로</button>
					<button id="btn_save" data-click-callback="save()">저장</button>
					<button id="btn_delete" data-click-callback="deleteNotice()">삭제</button>
				</div>
				
				<div class="pt50">
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
	function guideList() {
		location.href="/missingu/admin/guideList.html";
	}

	function save() {
		if(!validateSaveParams()) {
			return false;
		}
		$.ajax({
			url : "/missingu/admin/saveGuide.html",
			type : "POST",
			cache : false,
			data : $("#editForm").serialize(),
			dataType : "json",
			success : function(data) {
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				var rsltMsg = data.result.rsltMsg;
				
				if(rsltCd == 0) { //저장성공
					alert("성공");
					if(data.actionType == 'add') {
						guideList();
					} else {
						location.href="/missingu/admin/guideEdit.html?actionType=modify&menuId="+$("#menuId").val();
					}
					
				} else {
					alert(data.result.rsltMsg);
				}
			},
			error : function(data) {
				alert('<spring:message code="comm.systemError.msgText" />');
			}
		});
	}
	
	function deleteNotice() {
		if($('#actionType').val() == 'modify') {
			if(confirm("삭제 하시겠습니까?")) {
				$.ajax({
					url : "/missingu/admin/deleteGuide.html",
					type : "POST",
					cache : false,
					data : $("#editForm").serialize(),
					dataType : "json",
					success : function(data) {
						//$("#memberPoint").html(data.response.memberPoint).append(" P");
						var rsltCd = data.result.rsltCd;
						var rsltMsg = data.result.rsltMsg;
						
						if(rsltCd == 0) { //저장성공
							alert("성공");
							guideList();
						} else {
							alert(data.result.rsltMsg);
						}
					},
					error : function(data) {
						alert('<spring:message code="comm.systemError.msgText" />');
					}
				});
			}
		}
		else {
			alert("아직 저장되지 않은 데이타 입니다.");
		}

	}

	function validateSaveParams() {
		if($("#menuId").val() == '') {
			alert("메뉴ID를 입력해 주세요. ex) ZH_TALKTOME");
			$("#menuId").focus();
			return false;
		}
		
		return true;
	}
	
</script>
</body>
</html>