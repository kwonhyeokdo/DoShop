<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>비밀번호 변경</title>
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Member/ChangePassword.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
	<main class="main">
		<div class="pannel"></div>
		<div id="form_password"class="inner">
			<input name="inputEmail" type="hidden" value="${inputEmail}">
			<h1 class="password_title">비밀번호 변경</h1>
			<h2 class="password_text">변경할 비밀번호</h2>
			<input name="inputPassword" id="input_password" class="input_password" type="password" placeholder="비밀번호 입력" spellcheck="false" autocomplete="off">
			<h2 id="passord_notice" class="password_notice">비밀번호 변경</h2>
			<h2 id="passord_security_notice" class="passord_security password_notice">비밀번호 보안</h2>
			<h2 class="password_text">변경할 비밀번호 확인</h2>
			<input id="input_password_confirm" class="input_password" type="password" placeholder="비밀번호 확인 입력" spellcheck="false" autocomplete="off">
			<h2 id="passord_notice_confirm" class="password_notice">비밀번호 변경</h2>
			<button id="button_submit" class="button_submit">변경하기</button>
		</div>
	</main>
	<%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
	<script src="/DoShop/javascript/Member/ChangePassword.js"></script>
</body>
</html>