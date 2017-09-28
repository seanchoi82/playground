<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<values>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	
	<resultSet>
		<c:forEach items="${resultObjList}" var="chatRoom">
		<value>
			<roomId>${chatRoom.roomId}</roomId>
			<roomPass>${chatRoom.roomPass}</roomPass>
			<roomName><![CDATA[${chatRoom.roomName}]]></roomName>
			<memberId>${chatRoom.memberId}</memberId>
			<curUser>${chatRoom.curUser}</curUser>
			<maxUser>${chatRoom.maxUser}</maxUser>
			<memo><![CDATA[${chatRoom.memo}]]></memo>
			<regDate>${chatRoom.regDate}</regDate>
			<nick><![CDATA[${chatRoom.nick}]]></nick>
			<gender><![CDATA[${chatRoom.gender}]]></gender>
			<age>${chatRoom.age}</age>
			<area>${chatRoom.area}</area>
			<photo>${chatRoom.photo}</photo>
		</value>
		</c:forEach>
	</resultSet>
</values>