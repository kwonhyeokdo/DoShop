<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/DoShop/css/Member/NoticeEmail.css">
    <title>이메일 인증</title>
</head>
<body>
    <%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
    <main class="main">
    	<div class="pannel"></div>
        <div class="container">
        	<h1 class="title">가입하신 이메일로 인증 메일이 발송되었습니다.</h1>
            <p class="notice">
                한 시간안에 이메일 인증을하셔야 회원가입이 완료됩니다.<br>
                한 시간 이후 인증할 경우 등록하셨던 정보는 삭제되어 다시 가입절차를 진행하셔야 합니다. 
            </p>
            <a class="link" href="/DoShop/Member/Signin/">로그인하기</a>
        </div>
    </main>
    <%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
</body>
</html>