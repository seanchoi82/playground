<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/admin_header.jsp"%>
<title>MissingU Administrator</title>
</head>
<body>
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
			        <jsp:param name="selected" value="memberSearch"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li class="select"><a href="memberList.html">회원조회</a></li>
				<!-- <li><a href="memberSearch.html">(구)회원조회</a></li> -->
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">회원조회</h2>
				
				
				<form name="search" id="search" method="post" action="memberList.html">
				<h4>기본 검색 옵션</h4>
				<div class="rbox">
					<ul class="search">
						<li><label for="nick_name">닉네임</label><input type="text" id="nick_name" name="nick_name" value="${ req.nick_name }" /></li>
						<li>
							<span>성별</span> 
							<input type="radio" id="sex_1" name="sex" value="${ SexCode.MALE }"  <c:if test='${ req.sex eq SexCode.MALE }'>selected</c:if> /><label for="sex_1">남자</label>
							<input type="radio" id="sex_2" name="sex" value="${ SexCode.FEMALE }" <c:if test='${ req.sex eq SexCode.MALE }'>selected</c:if> /><label for="sex_2">여자</label>
						</li>
						<li>
							<label for="age">나이</label><input type="number" id="age" name="age_start" value="${ req.age_start }"  class="input-age"/>
							~ <input type="number" name="age_end" value="${ req.age_end }"  class="input-age"/>	
						</li>
						<li>
							<span>언어</span> 
							<input type="radio" id="lang_1" name="lang" value="ko"  <c:if test='${ req.lang eq "ko" }'>selected</c:if> /><label for="lang_1">한국어</label>
							<input type="radio" id="lang_2" name="lang" value="ne_ko"  <c:if test='${ req.lang ne "ko" }'>selected</c:if> /><label for="lang_2">외국어</label>	
						</li>
						<li>
							<span>국가</span> 
							<input type="radio" id="country_1" name="country" value="kr"  <c:if test='${ req.country eq "kr" }'>selected</c:if> /><label for="country_1">한국</label>
							<input type="radio" id="country_2" name="country" value="ne_kr"  <c:if test='${ req.country ne "kr" }'>selected</c:if> /><label for="country_2">외국</label>
						</li>
						<li><label for="hp_nm">휴대폰</label><input type="text" id="hp_nm" name="hp_nm" value="${ req.hp_nm }" /></li>
						<li>
							<span>본인인증</span>
							<input type="checkbox" id="certification" name="certification" value="Y"  <c:if test='${ req.certification eq "Y" }'>checked</c:if> /><label for="certification">인증</label>
						</li>
						<li>	
							<span>정회원</span>
							<input type="radio" id="membership_1" name="membership" value="Y"  <c:if test='${ req.membership eq "Y" }'>selected</c:if> /><label for="membership_1">정회원</label>
							<input type="radio" id="membership_2" name="membership" value="N"  <c:if test='${ req.membership ne "Y" }'>selected</c:if> /><label for="membership_2">일반회원</label>
						</li>
						<li>
							<label for="area">지역</label>
							<input type="text" id="area" name="area" value="${ req.area }"  placeholder="서울,부산,,,,"/>
						</li>
						<li>
							<label for="location">현위치</label>
							<input type="text" id="location" name="location" value="${ req.location }" placeholder="충무로,역삼동,," />
						</li>
						<li>	
							<label for="status">회원상태</label>
							<select id="status" name="status">
								<option value=''>전체</option>
								<option value="A" <c:if test="${ req.status eq 'A' }">selected</c:if>>사용중</option>
								<option value="P" <c:if test="${ req.status eq 'P' }">selected</c:if>>가입진행중</option>
								<option value="C" <c:if test="${ req.status eq 'C' }">selected</c:if>>탈퇴</option>
							</select>
						</li>
						<li class="two-line">
							<span>가입기간</span>
							<input type="text" name="created_date_start" class="datepicker" value="${ req.created_date_start }">
							~ 
							<input type="text" name="created_date_end" class="datepicker" value="${ req.created_date_end }">
						</li>
					</ul>
				</div>
				<div class="mt10">
					<button type="submit">검색</button>
					<button type="button" onclick="reset()">초기화</button>
					<a href="memberEdit.html?actionType=add" style="float:right;" class="button" target="_blank">신규 회원등록</a>
				</div>
				</form>
				
				<br /><br />
				총 ${ response.totalCnt } 명<br />
				<table class="tbl_nor" id="resultTable" cellspacing="0" summary="회원조회">
					<tbody>
						<caption>회원조회</caption>
						<colgroup>
							<col width="80">
							<col width="220">
							<col width="auto">							
							<col width="150">
							<col width="80">
							<col width="120">
						</colgroup>
						<tr id="tb_header">
							<th><input type="checkbox" id="replyAll" data-name="memberIds" class="checkInverse"/></th>
							<th>프로필</th> <!-- 사진, 닉네임, 성별 -->
							<th>연락처/이메일</th>
							<th>생년월일(나이)</th>
							<th>상태/포인트</th>
							<th>가입일자</th>
						</tr>
						<c:if test="${fn:length(response.dataList) == 0}">
							<tr>
								<td align="center" colspan="7">데이터가 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach items="${response.dataList}" var="item">
							<tr>
								<td align="center" style="width:80px"><input type="checkbox" data-label="${ item.nickName }" name="memberIds" value="${ item.memberId }" /></td>
								<td style="padding-left:10px; width:200px;">
									<div class="profile">
										<img src="${ item.mainPhotoUrl }" class="profile-img" />
										<ul>
											<li><a href="memberEdit.html?actionType=modify&memberId=${ item.memberId}" target="_blank"><strong>${ item.nickName }</strong>(${ item.name })</a></li>
											<li><c:if test="${ item.sex eq 'G01001' }">남자</c:if>
												<c:if test="${ item.sex eq 'G01002' }">여자</c:if>
												 - <c:choose>
													<c:when test="${ item.membership eq 'Y'}">정회원</c:when>
													<c:otherwise>일반회원</c:otherwise>
												</c:choose>
											</li>
											<li>(${ item.lang }/${ item.country })-<c:choose>
													<c:when test="${ item.certification eq 'Y'}">인증</c:when>
													<c:otherwise>미인증</c:otherwise>
												</c:choose></li>
										</ul>
									</div>
								</td>
								<td align="center" style="width:200px">
									<p>${ item.hpNm }</p>
									<p>${ item.loginId }</p>
								</td>
								<td align="center" style="width:150px">
									<p>${ item.birthDate }</p>
									<p>${ item.age }세</p>
								</td>
								<td align="center" style="width:80px;">
									<c:choose>
										<c:when test="${ item.status eq 'A' }">사용중</c:when>
										<c:when test="${ item.status eq 'P' }">가입중</c:when>
										<c:when test="${ item.status eq 'C' }">가입탈퇴</c:when>
										<c:otherwise>${item.status } ::::상태미확정::::</c:otherwise>
									</c:choose>
									/ ${ item.point }
								</td>
								<td align="center" style="width:120px">
									${ item.createdDate  }
								</td>
							</tr>
							<tr>
								<td colspan="7" style="background:#f2f2f2; min-height:40px;">
								${ item.selfPr } &nbsp;
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table> 
				<div class="mt10">
					<button type="button" onclick="sendMsg('sendMsgPop')">쪽지보내기</button>
				</div>
				<!-- 페이징영역 -->
				<c:set var="appendUrl" value="" />
				<c:forEach items="${ req }" var="entry" varStatus="status">
					<c:if test="${ entry.key ne 'nowPage' and entry.key ne 'startRow'  and entry.key ne 'pageSize' }">
						<c:if test="${ status.index > 0 }"><c:set var="appendUrl" value='${ appendUrl}&amp;'/></c:if>
						<c:set var="appendUrl" value='${ appendUrl }${ entry.key }=${ entry.value }'/>
					</c:if>
				</c:forEach>
				<jsp:include page="/jsp/common/paging.jsp">
				    <jsp:param name="actionPath" value="memberList.html"/>
				    <jsp:param name="totalCount" value="${ response.totalCnt }"/>
				    <jsp:param name="countPerPage" value="20"/>
				    <jsp:param name="blockCount" value="5"/>
				    <jsp:param name="addQuerys" value="${ appendUrl }"/>
				</jsp:include>
				<!-- // 페이징영역 -->
				<!-- Content 끝 -->
			</div>
		</div>
	</div>
</div>
<!-- 쪽지발송 팝업 레이어 -->
<div id="sendMsgPop" style="display:none;">
	<div class="content">
		<div class="ui-box ui-box-white ui-box-shadow">
			<h2 class="icon-arrow-green">쪽지발송</h2>
			
			<form id="sendMsgForm">
			<table class="tbl_nor" cellspacing="0" summary="쪽지발송">
				<caption>쪽지발송</caption>
				<colgroup>
					<col width="140">
				</colgroup>
				<tr>
					<th>발신자선택</th>
					<td>
						<button type="button" onclick="choiceMember()">선택</button>
						<input type="hidden" id="gCountry" name="gCountry" value="${ response.talk.country }"/>
						<input type="hidden" id="gLang" name="gLang" value="${ response.talk.lang }" />
						<input type="hidden" id="gMemberId" name="gMemberId"  value="${ response.talk.memberId }"/>
						<div id="memberDiv">
						</div>
					</td>
				</tr>
				<tr>
					<th>수신자</th>
					<td>
						<textarea rows="6" id="receiverList" name="receiverList" readonly="readonly"></textarea>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<textarea rows="6" id="msgText" name="msgText" ></textarea>
					</td>
				</tr>
			</table>
			</form>
			
			<div class="ui-btns">
				<button id="btn_msg_close" data-click-callback="javascript:$.unblockUI();">닫기</button>
				<button id="btn_msg_send" data-click-callback="callSendMsg()">전송</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function rebind(token, memberId, nickName, country, lang, photoUrl) {
	if(token == "sendmsg") {
		$("#gMemberId").val(memberId);
		$("#gCountry").val(country);
		$("#gLang").val(lang);
		$("#memberDiv").html("<img src='" + photoUrl + "' class='profile-img'/> " + nickName);
	}
}

function choiceMember() {
	window.open("memberSearchPop.html?token=sendmsg", "", "width=600, height=600");
	return;
}

//쪽지발송
function sendMsg(popupId) {
	var strCheckedMember = getCheckedMemberString();
	alert(strCheckedMember);
	
	if(strCheckedMember != '') {
		$('#receiverList').val(strCheckedMember);
		showPopup(popupId);
	} else {
		alert("대상 회원을 선택해 주세요.");
	}
}

//선택된 회원 memberId 문자열 리턴(구분자 : ,)
function getCheckedMemberString() {
	var rtnStr = '';
	
	$("input[name='memberIds']:checked").each(function() {		
			if(rtnStr != '') {
				rtnStr += ',';
			}
			rtnStr += $(this).val();
	});	
	return rtnStr;
	
}

//GCM 메세지 발송 서비스 호출
function callSendMsg() {
	$.ajax({
		url : "/missingu/admin/sendMsg.html",
		type : "POST",
		cache : false,
		data : $("#sendMsgForm").serialize(),
		dataType : "json",
		success : function(data) {
			var rsltCd = data.result.rsltCd;
			
			if(rsltCd == 0) { //조회성공
				alert("메세지를 전송하였습니다.");
			} else {
				alert(data.result.rsltMsg);
			}
		},
		error : function(data) {
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
}
function reset() {
	$("#search").each(function() {
		this.reset();
	});
}

// 신규회원등록
function regist() {
	location.href="memberEdit.html?actionType=add";
}

$(document).ready(function() {
	$(".datepicker").datepicker();
});
</script>
</body> 
</html>