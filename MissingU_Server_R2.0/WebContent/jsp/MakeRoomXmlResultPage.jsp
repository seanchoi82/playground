<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<values>
	<resultCode>${resultCode}</resultCode>
	<resultMsg></resultMsg>
	<c:if test="${!empty roomId}"> 
	<roomId>${roomId}</roomId>
	</c:if>
	<c:if test="${!empty maxUser}"> 
	<maxUser>${maxUser}</maxUser>
	</c:if>
</values>