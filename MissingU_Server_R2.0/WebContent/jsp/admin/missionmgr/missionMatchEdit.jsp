<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/admin_header.jsp"%>
<title>MissingU Administrator</title>
<style type="text/css">
	.tbl_nor input[type='text'].datepicker { width:100px; }
</style>
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
			        <jsp:param name="selected" value="missionMatch"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li class="select"><a href="missionMatchList.html">미션매치 목록</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">미션매치 관리</h2>
				
				<div>
				<form id="editForm" name="editForm" method="post" enctype="multipart/form-data">
					<input type="hidden" name="mId" id="mId" value="${reqParams.mId}" />
					<table class="tbl_nor" cellspacing="0" summary="미션매치">
						<caption>미션매치</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>미션유형</th>
							<td colspan="3">
								<input type="radio" name="type" value="0" id="type_0" <c:if test="${ mission.type eq '0' }">checked</c:if> /><label for="type_0">페이스매치</label>
								<input type="radio" name="type" value="1" id="type_1" <c:if test="${ mission.type eq '1' }">checked</c:if> /><label for="type_1">일반매치</label>
							</td>
						</tr>
						<tr>
							<th>제목</th>
							<td colspan="3"><input type="text" id="title" name="title" value="${ mission.title }" ></td>
						</tr>
						<tr>
							<th>설명</th>
							<td colspan="3"><textarea id="description" name="description" rows="10" style="width:90%">${ mission.description }</textarea></td>
						</tr>
						<tr>
							<th>운영기간</th>
							<td colspan="3">
								<input type="text" name="start_date" class="datepicker" value="${ mission.startDate }">
								~ 
								<input type="text" name="end_date" class="datepicker" value="${ mission.endDate }">
							</td>
						</tr>
						<tr>
							<th>사용여부</th>
							<td>
								<label for="status">사용</label>
								<input type="checkbox" name="status" value="1" id="status" <c:if test="${ mission.status eq '1' }">checked</c:if> />
							</td>
							<th>언어</th>
							<td>
							<select name="lang">
									<option value="">전체</option>
									<option value="ko" <c:if test='${ mission.lang eq "ko" }'>selected</c:if>>한국어</option>
									<option value="ja" <c:if test='${ mission.lang eq "ja" }'>selected</c:if>>일본어</option>
									<option value="zh" <c:if test='${ mission.lang eq "zh" }'>selected</c:if>>중국어</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>최대참여수</th>
							<td colspan="3">
								<input type="number" id="max_count" name="max_count" value="${ mission.maxCount }" />
							</td>
						</tr>
						<tr>
							<th>중복허용</th>
							<td colspan="3">
								<label for="use_multi_vote">중복투표허용</label>
								<input type="checkbox" name="use_multi_vote" value="1" id="use_multi_vote" <c:if test="${ mission.useMultiVote eq '1' }">checked</c:if> />
								&nbsp; &nbsp;
								<label for="multi_vote_interval">최소 투표 간격(시간)</label>
								<input type="number" id="multi_vote_interval" name="multi_vote_interval" value="${ mission.multiVoteInterval }" />
							</td>
						</tr>
					</table>
				</form>
				</div>
				
				<div class="ui-btns alignLeft">
					<button id="btn_back" data-click-callback="javascript:history.go(-1)">뒤로</button>
					<button id="btn_save" data-click-callback="save()">저장</button>
					<button id="btn_delete" data-click-callback="deleteMissionMatch()">삭제</button>
				</div>
				
				<!-- Content 끝 -->
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

function save() {
	if(!validateSaveParams()) {
		return false;
	}
	$.ajax({
		url : "/missingu/admin/saveMissionMatch.html",
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
					location.href="/missingu/admin/missionMatchList.html";
				} else {
					location.href="/missingu/admin/missionMatchEdit.html?mId="+$("#mId").val();
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

function deleteMissionMatch() {
	if($('#mId').val()) {
		if(confirm("삭제 하시겠습니까?")) {
			$.ajax({
				url : "/missingu/admin/deleteMissionMatch.html",
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
						location.href="/missingu/admin/missionMatchList.html";
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
	
	return confirm("저장하시겠습니까?");
}	
</script>
</body>
</html>