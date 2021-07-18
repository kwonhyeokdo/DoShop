<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" type="text/css" href="/DoShop/css/Member/FindPasswordStep2.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
	<main class="main">
		<div class="pannel"></div>
		<div id="choice_box" class="choice_box inner">
			<button id="buttonPhoneAuthority" class="select">
				핸드폰으로 인증하기
			</button>
			<button class="select">
				이메일 인증
			</button>
		</div>
		<div id="phone_box" class="phone_box inner">
			<h2 class="phone_title">핸드폰 인증</h2>
			<div class="frame_style">010-2XXX-2337</div>
			<button class="button_style">발송하기</button>
			<input class="frame_style" type="text" placeholder="인증번호 입력">
			<button class="button_style">인증하기</button>
			<button class="button_other">다른 인증수단</button>
		</div>
	</main>
	<%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
	<script src="/DoShop/javascript/Member/FindPasswordStep2.js"></script>
</body>
</html>