<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>MissingU Administrator</title>
<%@ include file="/jsp/common/admin_header.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/jqpagination/jqpagination.css">
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
			        <jsp:param name="selected" value="messageBox"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li><a href="msgConversList.html">최근 메세지 목록(NEW)</a></li>
				<li class="select"><a href="messageBoxList.html">최근 메세지 목록</a></li>
				<li><a href="messageBoxConversation.html">쪽지 대화 목록</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">최근 메세지 목록</h2>
				 
				 <form id="searchForm">
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="회원 검색조건">
						<caption>회원 검색조건</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>발신자</th>
							<td>
								<div id="cond_senderIdImg" class="profile_img">${ reqParams.senderId }</div>
								<input type="hidden" id="cond_senderId" name="senderId" />
								<input type="hidden" id="cond_senderLang" name="gLang" value="${ reqParams.gLang }" />
								<input type="hidden" id="cond_senderCountry" name="gCountry"  value="${ reqParams.gCountry }"/>
								<button type="button" onclick="choiceMember('sender')">선택</button>
							</td>
							<th>수신자</th>
							<td>
								<div id="cond_receiverIdImg" class="profile_img">${ reqParams.receiverId }</div>
								<input type="hidden" id="cond_receiverId" name="receiverId" />
								<button type="button" onclick="choiceMember('receiver')">선택</button>
							</td>
						</tr>
						<tr>
							<th>내용</th>
							<td colspan="3">
								<textarea name="msgTxt" id="msgTxt">${ reqParams.msgTxt }</textarea>
							</td>
						</tr>
					</table> 
				</div>
				<div class="ui-btns alignLeft">
					<button id="btn_search" data-click-callback="search()">조회</button>
					<button id="btn_search" data-click-callback="msgSend()">쪽지보내기</button>
				</div>
				</form>
				
				<div class="pt30">
				</div>
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="쪽지 목록">
						<caption>쪽지 목록</caption>
						<colgroup>
							<col width="80">
							<col width="150">
							<col width="30">
							<col width="150">
							<col>
							<col width="80">
							<col width="150">
						</colgroup>
						<tr id="tb_header">
							<th><input type="checkbox" id="replyAll" data-name="msgIds" class="checkInverse"/></th>
							<th>발신자</th>
							<th></th>
							<th>수신자</th>
							<th>내용</th>
							<th>읽기상태</th>
							<th>발송일자</th>
						</tr>
						<c:if test="${fn:length(response.msgList) == 0}">
							<tr>
								<td align="center" colspan="6">데이터가 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach items="${response.msgList}" var="item">
							<tr class="record">
								<td align="center"><input type="checkbox" name="msgIds" value="${ item.msgId }" /></td>
								<td>
									<img src="${ item.senderMainPhotoUrl }" class="profile-img"/>
									${ item.senderNickName }
								</td>
								<td>
								▶
								</td>
								<td>
									<img src="${ item.receiverMainPhotoUrl }" class="profile-img"/>
									${ item.receiverNickName }
								</td>
								<td align="center">
									<a href="messageBoxListConvers.html?senderId=${ item.senderId }&receiverId=${ item.receiverId }" target="_blank">${item.msgText}</a>
								</td>
								<td align="center">${item.receiverReadYn}</td>
								<td align="center">${item.sendDate}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="ui-btns alignLeft">
					<button type="button" onclick="deleteMsg()">쪽지 삭제</button>
				</div>
				
				<!-- Content 끝 -->
			</div>
			<jsp:include page="/jsp/common/paging.jsp">
			     <jsp:param name="actionPath" value="messageBoxList.html"/>
			     <jsp:param name="totalCount" value="${ response.totalCnt }"/>
			     <jsp:param name="countPerPage" value="${ reqParams.pageSize }"/>
			     <jsp:param name="blockCount" value="5"/>
			     <jsp:param name="nowPage" value="${ reqParams.nowPage }"/>
			     <jsp:param name="addQuerys" value="senderId=${ reqParams.senderId }&amp;receiverId=${ reqParams.receiverId }&amp;msgTxt=${ reqParams.msgTxt }"/>
			     
			</jsp:include>
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
	
	if(token == "sender") {
		$("#cond_senderId").val(memberId);
		$("#cond_senderIdImg").html("<img src='" + photoUrl + "' class='profile-img'/> " + nickName);
		$("#cond_senderCountry").val(country);
		$("#cond_senderLang").val(lang);
	}else if(token == "receiver") { 
		$("#cond_receiverId").val(memberId);
		$("#cond_receiverIdImg").html("<img src='" + photoUrl + "' class='profile-img'/> " + nickName);
	}
}

function choiceMember(token) {
	window.open("memberSearchPop.html?token=" + token, "", "width=600, height=600");
	return;
}

function search() {
	if(!$("#cond_senderId").val() && !$("#cond_receiverId").val() && !$("#msgTxt").val()) {
		alert("발신자, 수신자 또는 검색 내용을 입력 해주세요.");
		return;
	} 
	
	$("#searchForm").submit();
}

function deleteMsg() {
	if(!confirm("정말 삭제 하시겠습니까?"))
		return;
	
	var arrs = [];
	$("input[name='msgIds']").each(function() {
		if($(this).is(":checked")) {
			arrs.push($(this).val());
		}
	});
	
	var ids = "";
	for(var i=0;i<arrs.length;i++) {
		if(i>0 )ids += ",";
		ids += arrs[i];
	}
	
	$.ajax({
		url : "delMsgArr.html",
		type : "POST",
		cache : false,
		data : { msgIds:ids}, 
		dataType : "json",
		success : function(data) {
			
			var rsltCd = data.result.rsltCd;
			var rsltMsg = data.result.rsltMsg;
			if(rsltCd == 0) { //저장성공
				alert("삭제 되었습니다.");
				location.href="messageBoxList.html";
			} else {
				alert(rsltMsg);
			}
		},
		error : function(data) {
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
}

//즉시발송
function msgSend() {
	if(!$("#cond_senderId").val() || !$("#cond_receiverId").val() || !$("#msgTxt").val()) {
		alert("발신자, 수신자 또는 검색 내용을 입력 해주세요.");
		return;
	}
	
	var param = {
			gMemberId:$("#cond_senderId").val(),
			gCountry:$("#cond_senderCountry").val(), 
			gLang:$("#cond_senderLang").val(), 
			receiverList:$("#cond_receiverId").val(), 
			msgText:$("#msgTxt").val()
	};
	
	$.ajax({
		url : "/missingu/admin/sendMsg.html",
		type : "POST",
		cache : false,
		data : param, 
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

</script>
</body>
</html>