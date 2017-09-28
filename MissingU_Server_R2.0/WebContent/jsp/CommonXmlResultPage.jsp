<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<values>
	<resultCode>${resultCode}</resultCode>
	<resultMsg></resultMsg>
	<c:forEach items="${returnValues}" var="returnVal">
	</c:forEach>
</values>