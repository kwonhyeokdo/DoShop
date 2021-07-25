<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" type="text/css" href="/DoShop/css/Member/FindPassword/FindPasswordStep1.css">
</head>
<body>
	<%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
	<main class="main">
		<div class="pannel"></div>
		<div class="inner">
			<h1 class="title">비밀번호 찾기</h1>
			<h2 class="info">이메일을 입력해주세요.</h2>
			<form class="input_wrap" method="post" action="/DoShop/Member/FindPassword/">
				<input name="inputEmail" class="input_email" type="text" spellcheck="false" autocomplete="off">
				<input class="submit_email" type="submit">
			</form>
			<h3 class="errorMessage">${errorMessage }</h3>
		</div>
	</main>
	<%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
</body>
</html>