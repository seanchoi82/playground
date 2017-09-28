<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<values>
	<resultCode>${resultCode}</resultCode>
	<resultMsg>${resultMsg}</resultMsg>
	<resultSet>
		<c:forEach items="${resultObjList}" var="msgbox">
		<value>
			<senderId>${msgbox.senderId}</senderId>
			<gender><![CDATA[${msgbox.gender}]]></gender>
			<nick><![CDATA[${msgbox.nick}]]></nick>
			<sendDate>${msgbox.sendDate}</sendDate>
			<message><![CDATA[${msgbox.message}]]></message>
			<unreadCnt>${msgbox.unreadCnt}</unreadCnt>
			<photo>${msgbox.photo}</photo>
		</value>
		</c:forEach>
	</resultSet>
</values>