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
.msgbox li span em { cursor: pointer;}
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
				<li><a href="messageBoxList.html">최근 메세지 목록</a></li>
				<li class="select"><a href="messageBoxConversation.html">쪽지 대화 목록</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">쪽지 대화 목록</h2>
				<h4>최근 쪽지를 수신한 회원순으로 정렬 됩니다. </h4>
				<div class="conversation">
					<form id="searchForm">
						<input type="hidden" id="pageSize" name="pageSize" value="10"/>
						<input type="hidden" id="page" name="nowPage" value="1"/>
					</form>
					<div class="friends">
						<ul class="ubox">
						</ul>
						<div class="gigantic pagination">
						    <a href="#" class="first" data-action="first">&laquo;</a>
						    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
						    <input type="text" readonly="readonly" />
						    <a href="#" class="next" data-action="next">&rsaquo;</a>
						    <a href="#" class="last" data-action="last">&raquo;</a>
						</div>
					</div>
					<div class="msgs">
						<ul class="msgbox">
						</ul>
						<div class="rbox" style='margin-left:5px;'>
						<input type="hidden" id="send_senderId" />
						<input type="hidden" id="send_senderCountry" />
						<input type="hidden" id="send_senderLang" />
						<input type="hidden" id="send_receiverId" />
						<input type="hidden" id="send_receiverCountry" />
						<input type="hidden" id="send_receiverLang" />
						
						
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
$(document).ready(function() {
	$('.pagination').jqPagination({
		paged		: function(page) {
			if($("#page").val() != page) {
				$("#page").val(page);
				search();
			}
		}
	});
});

// 친구목록 가져오기
function search() {
	
	$(".friends .ubox li").remove();
	$(".msgbox li").remove();
	
	$.ajax({
		url : "/missingu/admin/getMsgBoxConversationFriends.html",
		type : "POST",
		cache : false,
		data : $("#searchForm").serialize(),
		dataType : "json",
		success : function(data) {
			
			//$("#memberPoint").html(data.response.memberPoint).append(" P");
			var rsltCd = data.result.rsltCd;
			
			if(rsltCd == 0) { //조회성공
				var msgList = data.response.msgList;
				var htmls = "";
				$.each(msgList, function(i){
					
					htmls += "<li data-senderId='" + this.senderId + "' data-receiverId='" + this.receiverId + "' data-senderCountry='" + this.senderCountry + "' data-senderLang='" + this.senderLang + "'  data-receiverCountry='" + this.receiverCountry + "' data-receiverLang='" + this.receiverLang + "'>";
					htmls +=  "<div class=h><img src='" + this.senderMainPhotoUrl + "' class=profile-img align=left/>'" + this.senderNickName + "</div><div class=sh>▶</div><div class=h>" + "<img src='" + this.receiverMainPhotoUrl + "' class=popimg align=left/>'" + this.receiverNickName + "</div>";
					htmls += "</li>";
				});
				
				$(".friends .ubox").append(htmls);
			} else {
				$(".friends .ubox").append("<li>데이터가 없습니다.</li>");
				alert(data.result.rsltMsg);
			}
			
			var totalCnt =Number(data.response.totalCnt);
			var maxPage = Math.ceil(totalCnt / $("#pageSize").val());
			
			$('.pagination').jqPagination('option', 'max_page' , maxPage);
			$('.pagination').jqPagination('option', 'current_page' , data.reqParams.page);
			
		},
		error : function(data) {
			
			$(".friends .ubox").append("<li>데이터가 없습니다.</li>");
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
}

$(document).on("click", ".friends .ubox li", function() {
	$(this).addClass("selected");
	$(".friends .ubox li").not($(this)).removeClass("selected");
	
	var senderId =$(this).attr("data-senderId"); 
	var receiverId =$(this).attr("data-receiverId");
	var senderCountry = $(this).attr("data-senderCountry");
	var senderLang = $(this).attr("data-senderLang");
	var receiverCountry = $(this).attr("data-receiverCountry");
	var receiverLang = $(this).attr("data-receiverLang");
	
	$("#send_senderId").val(senderId);
	$("#send_senderCountry").val(senderCountry);
	$("#send_senderLang").val(senderLang);
	
	$("#send_receiverId").val(receiverId);
	$("#send_receiverCountry").val(receiverCountry);
	$("#send_receiverLang").val(receiverLang);
	
	searchConversation();
});

function searchConversation() {
	
	$(".msgbox li").remove();
	
	var senderId = $("#send_senderId").val();
	var receiverId = $("#send_receiverId").val();
	
	$.ajax({
		url : "/missingu/admin/getMsgBoxConversationByFriendsId.html",
		type : "POST",
		cache : false,
		data : { 
			senderId:senderId, 
			receiverId:receiverId
		},
		dataType : "json",
		success : function(data) {
			
			//$("#memberPoint").html(data.response.memberPoint).append(" P");
			var rsltCd = data.result.rsltCd;
			
			if(rsltCd == 0) { //조회성공
				var msgList = data.response.msgList;
				var htmls = "";
				$.each(msgList, function(i){
					
					if(senderId == this.senderId) {
						htmls += "<li class='sender'>";
						htmls += "<img src='" + this.senderMainPhotoUrl + "' class='profile-img'/>";
						htmls += "<span class='msg'>" + this.msgText + "</span>";
						htmls += "<span class='date'>" +this.sendDate + "<var>" + this.receiverReadYn+"</var><em data-msg-id='" + this.msgId + "'>[삭제]</em></span>";
						htmls += "</li>";
					}else{
						htmls += "<li class='receiver'>";
						htmls += "<span class='date'>" +this.sendDate + "<var>" + this.receiverReadYn+"</var><em data-msg-id='" + this.msgId + "'>[삭제]</em></span>";
						htmls += "<span class='msg'>" + this.msgText + "</span>";
						htmls += "<img src='" + this.senderMainPhotoUrl + "' class='profile-img'/>";
						htmls += "</li>";
					}
					
					// <li class="receiver"><span class="date">2013-10-10 23:12:123</span><span class="msg">저기요.. 자리에 계신가요?</span><img src='' class='profile-img'/> </li>
					// <li class="sender"><img src='' class='profile-img'/><span class="msg">소개좀?</span><span class="date">2013-10-10 23:12:123</span></li>
					
					// htmls += "<li data-senderId='" + this.senderId + "' data-receiverId='" + this.receiverId + "'>";
					// htmls +=  this.senderNickName + " ▶ " + this.receiverNickName;
					// htmls += "</li>";
				});
				
				$(".msgbox").append(htmls);
			} else {
				$(".msgbox").append("<li>데이터가 없습니다.</li>");
				alert(data.result.rsltMsg);
			}
		},
		error : function(data) {
			$(".msgbox").append("<li>데이터가 없습니다.</li>");
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
}

$(document).on("click", ".msgbox li span em", function() {
	
	$.ajax({
		url : "delMsgArr.html",
		type : "POST",
		cache : false,
		data : { msgId:$(this).attr("data-msg-id")}, 
		dataType : "json",
		success : function(data) {
			
			var rsltCd = data.result.rsltCd;
			var rsltMsg = data.result.rsltMsg;
			if(rsltCd == 0) { //저장성공
				alert("삭제 되었습니다.");
				searchConversation();
			} else {
				alert(rsltMsg);
			}
		},
		error : function(data) {
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
	
});

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
				searchConversation();
			} else {
				alert(data.result.rsltMsg);
			}
		},
		error : function(data) {
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
}

$(document).ready(function() {
	search();
});
</script>
</body>
</html>