<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" type="text/css" href="/DoShop/css/Member/FindPassword/FindPasswordStep2.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<script>
		var InputEmail = "${inputEmail}";
	</script>
	<%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
	<main class="main">
		<div class="pannel"></div>
		<div id="choice_box" class="choice_box inner">
			<button id="button_phone_authority" class="select">
				핸드폰으로 인증하기
			</button>
			<button id="button_email_authority" class="select">
				이메일 인증
			</button>
		</div>
		<div id="phone_box" class="phone_box inner">
			<h2 class="phone_title">핸드폰 인증</h2>
			<div id="phone_number" class="frame_style"></div>
			<button id="button_get_authority_number" class="button_style">발송하기</button>
			<input id="input_authentication_number" class="frame_style" type="text" placeholder="인증번호 입력" disabled spellcheck="false" autocomplete="off">
			<button id="button_check_authority_number" class="button_style" disabled>인증하기</button>
			<div id="count_timer" class="timer">00 : 00</div>
			<button class="button_other">다른 인증수단</button>
		</div>
		<div id="email_box" class="email_box inner">
			<h2 class="email_title">이메일 인증</h2>
			<h3 class="email_text">본인 인증 코드가 이메일로 발송되었습니다.</h3>
			<h3 class="email_text">인증 코드를 입력해주세요.</h3>
			<input id="input_email_code" class="frame_style input_email" type="text" placeholder="인증번호 입력" spellcheck="false" autocomplete="off">
			<button id="button_email_code" class="button_style button_email">인증하기</button>
			<button class="button_other">다른 인증수단</button>
		</div>
	</main>
	<%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
	<script src="/DoShop/javascript/Member/FindPassword/FindPasswordStep2.js"></script>
</body>
</html>