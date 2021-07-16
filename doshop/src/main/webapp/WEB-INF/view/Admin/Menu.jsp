<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul id="menu">
	<c:choose>
		<c:when test="${signinSession.authority == 1}">
			<li id="menu_admin_management">관리자 관리</li>
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${signinSession.authority >= 1 && signinSession.authority <= 2}">
			<li id="menu_category_management">카테고리 관리</li>
		</c:when>
	</c:choose>
	<li>회원 관리</li>
	<li>판매 관리</li>
</ul>