<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>비밀번호 확인</title>
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Member/CheckMember.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
	<main class="main">
		<div class="pannel"></div>
		<div class="inner">
			<h1 class="title">회원 정보</h1>
			<form class="input_box" method="post" action="/DoShop/Member/Information/">
				<input name="inputPassword" class="input_password" type="password" placeholder="비밀번호를 입력하세요.">
				<input class="input_submit" type="submit" value="확인">
			</form>
			<h2 class="notice">${errorMessage}</h2>
		</div>
	</main>
	<%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
</body>
</html>