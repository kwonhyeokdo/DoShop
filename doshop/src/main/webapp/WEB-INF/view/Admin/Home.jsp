<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/Home.css">
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/Menu.css">
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/Footer.css">
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/TopNavi.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>관리자</title>
</head>
<body>
    <div id="wallpaper"></div>
    <div id="pannel"></div>
    <div id="playground">
        <header>
            <%@ include file="/WEB-INF/view/Admin/TopNavi.jsp" %>
        </header>
        <main>
        	<%@ include file="/WEB-INF/view/Admin/Menu.jsp" %>
            <section id="workspace" style="overflow: auto">
				<%-- <%@ include file="/WEB-INF/view/Admin/AdminManagement.jsp" %> --%>
            </section>
        </main>
        <%@ include file="/WEB-INF/view/Admin/Footer.jsp" %>
    </div>
    <script src="/DoShop/javascript/Admin/Menu.js"></script>
</body>
</html>