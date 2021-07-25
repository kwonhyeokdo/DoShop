<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>비밀번호 확인</title>
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Member/UpdateInformation/CheckPassword.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<script>
		let errorMessage = "${errorMessage}"; 
		if(errorMessage.length !== 0){
			alert(errorMessage);
		}
	</script>
	<%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
	<main class="main">
		<div class="pannel"></div>
		<div class="inner">
			<h1 class="title">회원 정보</h1>
			<div class="input_box">
				<input name="inputPassword" class="input_password" type="password" placeholder="비밀번호를 입력하세요.">
				<input id="input_submit" class="input_submit" type="button" value="확인">
			</div>
			<h2 id="errorMessage" class="notice"></h2>
		</div>
	</main>
	<%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
	<script src="/DoShop/javascript/Member/UpdateInformation/CheckPassword.js"></script>
</body>
</html>