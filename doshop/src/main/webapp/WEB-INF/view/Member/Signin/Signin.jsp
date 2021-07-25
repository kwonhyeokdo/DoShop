<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link rel="stylesheet" type="text/css" href="/DoShop/css/Member/Signin/Signin.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
    <div id=wallpaper></div>
    <div id="pannel"></div>
    <header>
        <nav id="navi_top">
            <a id="navi_logo" href="/DoShop/">DoShop</a>
        </nav>
    </header>
    <main>
        <div class="background">
            <div class="wrap_inline_center"><a id="logo" href="/DoShop/">DoShop</a></div>
            <div class="container">
                <form id="wrap_signin" method="post" action="/DoShop/Member/Signin/">
                    <h2>이메일</h2>
                    <input name="inputEmail" class="input_text input_margin_bottem" id="input_email" type="text" spellcheck="false" autocomplete="off" value="${rememberEmail}"/>
                    <h2>비밀번호</h2>
                    <input name="inputPassword" class="input_text" id="input_password" type="password" spellcheck="false" autocomplete="off"/>
                    <input id="button_signin" type="submit" value="로그인"/>
                    <h3>${errorMessage}</h3>
                	<input name="checkRememberEmail" type="hidden">
                	<input name="checkAutoSignin" type="hidden">
                </form>
                <div class="wrap_signin_option">
                	<c:choose>
                		<c:when test="${empty rememberEmail}">
                			<input id="checkRememberEmail" type="checkbox">
                		</c:when>
                		<c:otherwise>
                			<input id="checkRememberEmail" type="checkbox" checked>
                		</c:otherwise>
                	</c:choose>
                	<span class="signin_option_text">아이디 저장</span>
                	<br>
                	<c:choose>
                		<c:when test="${!empty autoSignin and autoSignin eq 'true'}">
	                		<input id="checkAutoSignin" type="checkbox" checked>
                		</c:when>
					    <c:otherwise>
                			<input id="checkAutoSignin" type="checkbox">
                		</c:otherwise>
                	</c:choose>
                	<span class="signin_option_text">자동 로그인</span>
                </div>
                <a class="signup_text" href="/DoShop/Member/Signup/">회원가입</a>
                <span class="find_text"> / </span> 
                <a class="find_text" href="/DoShop/Member/FindPassword/">비밀번호 찾기</a>
            </div>
        </div>
    </main>
    <footer>
        <div class="background">
            <div class="container">
                <hr class="separator">
                <div id="wrap_footer">
                    <a class="content_footer" href="#">이용약관</a>
                    |
                    <a class="content_footer" href="#">개인정보처리방침</a>
                    |
                    <a class="content_footer" href="#">고객센터</a>
                </div>
            </div>
        </div>
    </footer>
    <script src="/DoShop/javascript/Member/Signin/Signin.js"></script>
</body>
</html>