<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 완료</title>
    <link rel="stylesheet" type="text/css" href="/DoShop/css/Member/Congratulations.css">
</head>
<body>
    <%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
    <main class="main">
    	<div class="pannel"></div>
    	<div class="wrap">
        	<h1 class="title">DoShop 회원가입을 축하합니다.</h1>
        	<a class="link" href="/DoShop/Member/Signin/" id="go_to_login">로그인하기</a>
    	</div>
    </main>
    <%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
</body>
</html>