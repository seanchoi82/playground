<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String selected = request.getParameter("selected");

String memberSearch = "";
String payHistList = "";
String boardList = "";
String fmSearch = "";
String apkList = "";
String talkToMe = "";
String messageBox = "";
String missionMatch = "";
String chatServer = "";

if("memberSearch".equals(selected)) {
	memberSearch = " class='select'";
}else if("payHistList".equals(selected)) {
	payHistList = " class='select'";
}else if("noticeList".equals(selected) || "guideList".equals(selected) || "manToManList".equals(selected)) {
	boardList = " class='select'";
}else if("missionMatch".equals(selected)) {
	missionMatch = " class='select'";
}else if("fmSearch".equals(selected)) {
	fmSearch = " class='select'";
}else if("talkToMe".equals(selected)) {
	talkToMe = " class='select'";
}else if("messageBox".equals(selected)) {
	messageBox = " class='select'";
}else if("chatServer".equals(selected)) {
	chatServer = " class='select'";
}else if("apkList".equals(selected)) {
	apkList = " class='select'";
}
%>
<ul class="tabhost">
	<li <%= memberSearch %>><a href="/missingu/admin/memberList.html">회원관리</a></li>
	<li <%= payHistList %>><a href="/missingu/admin/payHistList.html">결제/포인트관리</a></li>
	<li <%= boardList %>><a href="/missingu/admin/manToManList.html">게시판관리</a></li>
	<!-- <li <%= fmSearch %>><a href="/missingu/admin/fmSearch.html">페이스매치 관리</a></li> -->
	<li <%= missionMatch %>><a href="/missingu/admin/missionMatchList.html?is_facematch=1">미션매치 관리</a></li>
	<li <%= talkToMe %>><a href="/missingu/admin/talkToMeList.html">톡투미 관리</a></li>
	<li <%= messageBox %>><a href="/missingu/admin/msgConversList.html">메세지박스 관리</a></li>
	<li <%= chatServer %>><a href="/missingu/admin/chat/chatServer.html">채팅 관리</a></li>
	<li <%= apkList %>><a href="/missingu/admin/apkList.html">개발 APK 관리</a></li>
</ul>

<form id="sendMsgFrom" target="blank" method="post" action="/missingu/admin/sendMessage.html">
	<input type="hidden" id="sendMsgFromReceiverIds" name="receiverIds" />
	<input type="hidden" id="sendMsgFromReceiverNickNames" name="receiverNickNames" />
</form>