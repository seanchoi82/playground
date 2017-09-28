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
				<table class="tbl_nor" cellspacing="0" summary="미션매치 목록">
					<caption>미션매치 목록</caption>
					<colgroup>
						<col width="*">
						<col width="200">
						<col width="100">
						<col width="130">
					</colgroup>
					<tr id="tb_header">
						<th>제목</th>
						<th>기간</th>
						<th>상태</th>
						<th>배틀수</th>
					</tr>
					<tr class="record">
						<td>
							[<c:choose>
									<c:when test="${ mission.lang eq 'ko' }">한국어</c:when>
									<c:when test="${ mission.lang eq 'ja' }">일본어</c:when>
									<c:when test="${ mission.lang eq 'zh' }">중국어</c:when>
								</c:choose>]
							<a href="<c:url value="/missingu/admin/missionMatchEdit.html">
								<c:param name="mId" value="${mission.mId}"></c:param>
							</c:url>">
							${mission.title}(${ mission.maxCount })
							</a>
						</td>
						<td align="center">
						${mission.startDate}
						~
						${mission.endDate}
						</td>
						<td align="center">
							<c:if test="${mission.status eq '1'}">사용</c:if>
							<c:if test="${mission.status eq '0'}">사용안함</c:if>
						</td>
						<td align="center">${mission.battleCnt}</td>
					</tr>
				</table>
				<form id="editForm" name="editForm" method="post" enctype="multipart/form-data">
					<input type="hidden" name="mId" id="mId" value="${reqParams.mId}" />
					<input type="hidden" name="mJId" id="mJId" value="${reqParams.mJId}" />
					<table class="tbl_nor" cellspacing="0" summary="미션매치 참여">
						<caption>미션매치 참여</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>참여자</th>
							<td colspan="3">
								<input type="hidden" id="gMemberId" name="gMemberId"  value="${ missionJoin.memberId }"/>
								<div id="memberDiv">
									<img src="${ missionJoin.mainPhotoUrl }" class="profile-img"/> ${missionJoin.nickName }
								</div>
								<button type="button" onclick="choiceMember()">회원선택</button>
							</td>
						</tr>
						<tr>
							<th>코멘트</th>
							<td colspan="3"><input type="text" id="comment" name="comment" value="${ missionJoin.comment }" ></td>
						</tr>
						<tr>
							<th>파일</th>
							<td colspan="3">
							<input type="file" id="saveFile" name="saveFile"  accept="image/*" />
								<input type="hidden" id="missionMatchJoinPhotoUrl"><br />
								
								<a href="${ missionJoin.uploadfileorg }" class="nyroModal" title="미션매치 첨부파일"><img src="${missionJoin.uploadfile}" class="profile-img"/></a>
							</td>
						</tr>
						<tr>
							<th>배틀수</th>
							<td>
								<input type="number" id="battleJoinCnt" name="battleJoinCnt" value="${ missionJoin.battleJoinCnt }" />
							</td>
							<th>투표수</th>
							<td>
								전체 : <input type="number" id="vote" name="vote" value="${ missionJoin.vote }" />
								당월 : <input type="number" id="voteByMonth" name="voteByMonth" value="${ missionJoin.voteByMonth }" />
							</td>
						</tr>
					</table>
				</form>
				</div>
				
				<div class="ui-btns alignLeft">
					<button id="btn_back" data-click-callback="javascript:history.go(-1)">뒤로</button>
					<button id="btn_save" data-click-callback="save()">저장</button>
				</div>
				
				<!-- Content 끝 -->
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

function rebind(token, memberId, nickName, country, lang, photoUrl) {
	
	if(token == "missionmatch") {
		$("#gMemberId").val(memberId);
		$("#memberDiv").html("<img src='" + photoUrl + "' class='profile-img'/> " + nickName);
	}
}

function choiceMember() {
	window.open("memberSearchPop.html?token=missionmatch", "", "width=600, height=600");
	return;
}


function save() {
	if(!validateSaveParams()) {
		return false;
	}
	
	
	var options = {
		url : "/missingu/admin/missionMatchJoinSave.html",
		type : "POST",
		dataType : "json",
		success : function(data) {
			$.unblockUI();
			//$("#memberPoint").html(data.response.memberPoint).append(" P");
			var rsltCd = data.result.rsltCd;
			
			if(rsltCd == 0) { //저장성공
				alert("성공");
				location.href="/missingu/admin/missionMatchJoinEdit.html?mId="+ data.mId + "&mJId="+ data.mJId;
			} else {
				alert(data.result.rsltMsg);
			}
		},
		error : function(data) {
			$.unblockUI();
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	};
    
    if(!$("#saveFile").val()) {
    	options.cache = false;
    	options.data = $("#editForm").serialize();
    	$.ajax(options);
    }else{
		// bind form using 'ajaxForm'
		$('#editForm').ajaxSubmit(options);
		$.blockUI();
    }
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