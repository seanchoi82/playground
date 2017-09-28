<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>MissingU Administrator</title>
<%@ include file="/jsp/common/admin_header.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/jqpagination/jqpagination.css">
<script src="/js/jquery.jqpagination.js"></script>
<style>
.friends .ubox li { cursor: pointer; overflow: auto; overflow-y:hidden;}
.friends .ubox li.selected { color:red; font-weight: bold; }
.friends .ubox li img { padding-right:10px; }
.friends .ubox li .h { width:45%; display:inline-block; float:left; }
.friends .ubox li .sh { width:10%; display:inline-block; float:left; text-align:center; margin-top:20px; }
.msgbox li span em { cursor: pointer; } 
.msgbox li span var { cursor: pointer;}
.msgbox li span.delete { text-decoration: line-through; }
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
			        <jsp:param name="selected" value="messageBox"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li class="select"><a href="msgConversList.html">최근 메세지 목록(NEW)</a></li>
				<li><a href="messageBoxList.html">최근 메세지 목록</a></li>
				<li><a href="messageBoxConversation.html">쪽지 대화 목록</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">쪽지 대화 목록</h2>
				<h4>최근 쪽지를 수신한 회원순으로 정렬 됩니다. </h4>
				<div class="conversation">
					<div class="msgs" style="width:100%">
						<ul class="msgbox">
							<c:forEach items="${ response.dataList }" var="item">
								<c:choose>
									<c:when test="${ item.senderId eq reqParams.senderId }">
										<li class='sender'>
											<img src="${ item.senderMainPhotoUrl }" class='profile-img'/>
											<span class='msg<c:if test="${ item.status eq 'D' }"> delete</c:if>'>${ item.msgText } 
												<c:if test="${ item.status eq 'D' }"><p>--사용자에 의해 삭제되었습니다.--</p></c:if>
											</span>
											<span class='date'>${ item.createdDate }<var data-msg-id='${ item.msgId }'>${ item.receiverReadYn }</var><em data-msg-id='${ item.msgId }'>[삭제]</em></span>
										</li>
									</c:when>
									<c:otherwise>
									<li class='receiver'>
											<span class='date'>${ item.createdDate }<var data-msg-id='${ item.msgId }'>${ item.receiverReadYn }</var><em data-msg-id='${ item.msgId }'>[삭제]</em></span>
											<span class='msg<c:if test="${ item.status eq 'D' }"> delete</c:if>'>${ item.msgText } 
												<c:if test="${ item.status eq 'D' }"><p>--사용자에 의해 삭제되었습니다.--</p></c:if>
											</span>
											<img src="${ item.senderMainPhotoUrl }" class='profile-img'/>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
						<div class="rbox" style='margin-left:5px;'>
							<input type="hidden" id="send_senderId"  value="${ sender.memberId }" />
							<input type="hidden" id="send_senderCountry" value="${ sender.country }"  />
							<input type="hidden" id="send_senderLang" value="${ sender.lang }"  />
							<input type="hidden" id="send_receiverId"  value="${ receiver.memberId }" />
							<input type="hidden" id="send_receiverCountry"  value="${ receiver.country }" />
							<input type="hidden" id="send_receiverLang"  value="${ receiver.lang }" />
							<select id="send_msg_target">
								<option value="sender">발신자</option>
								<option value="receiver">수신자</option>
							</select>
						로 
						<input type="text" id="send_msg_txt" placeholder="내용을 입력하세요." />
						<button type="button" onclick="sendMsg()">발송합니다.</button>
						</div>
						
					</div>
				</div>
				
			</div> 
		</div>
	</div>
</div>
<script type="text/javascript">



function sendMsg() {
	if(!$("#send_senderId").val() || !$("#send_receiverId").val()) {
		alert("먼저 왼쪽 대화 회원을 클릭해주세요.");
		return;	
	}
	
	if(!$("#send_msg_txt").val()) {
		alert("전송을 내용을 입력해 주세요.");
		return;	
	}
	
	var senderId = $("#send_senderId").val();
	var senderCountry = $("#send_senderCountry").val();
	var senderLang = $("#send_senderLang").val();
	var receiverId = $("#send_receiverId").val();
	var receiverCountry = $("#send_receiverCountry").val();
	var receiverLang = $("#send_receiverLang").val();
	
	var param;
	if($("#send_msg_target").val() == "sender") {
		param = {
			gMemberId:senderId,
			gCountry:senderCountry, 
			gLang:senderLang, 
			receiverList:receiverId, 
			msgText:$("#send_msg_txt").val()
		};
	}else{
		param = {
				gMemberId:receiverId,
				gCountry:receiverCountry, 
				gLang:receiverLang, 
				receiverList:senderId, 
				msgText:$("#send_msg_txt").val()
			};
	}
	
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
				location.reload();
			} else {
				alert(data.result.rsltMsg);
			}
		},
		error : function(data) {
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
}

$(document).on("click", ".msgbox li span em", function() {
	
	$.ajax({
		url : "deleteMsgConvers.html",
		type : "POST",
		cache : false,
		data : { msgId:$(this).attr("data-msg-id")}, 
		dataType : "json",
		success : function(data) {
			
			var rsltCd = data.result.rsltCd;
			var rsltMsg = data.result.rsltMsg;
			if(rsltCd == 0) { //저장성공
				alert("삭제 되었습니다.");
				location.reload();
			} else {
				alert(rsltMsg);
			}
		},
		error : function(data) {
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
	
});


$(document).on("click", ".msgbox li span var", function() {
	
	$.ajax({
		url : "updateMsgConversReadYn.html",
		type : "POST",
		cache : false,
		data : { msgId:$(this).attr("data-msg-id")}, 
		dataType : "json",
		success : function(data) {
			
			var rsltCd = data.result.rsltCd;
			var rsltMsg = data.result.rsltMsg;
			if(rsltCd == 0) {
				location.reload();
			} else {
				alert(rsltMsg);
			}
		},
		error : function(data) {
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
	
});

$(document).ready(function() {
	search();
});
</script>
</body>
</html>