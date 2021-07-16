<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 로그인</title>
    <link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/Signin.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
    <div id="wallpaper"></div>
    <div id="pannel"></div>
    <header>
        <nav id="top_navi">
            <a id="top_navi_logo" href="/DoShop/">DoShop</a>
        </nav>
    </header>
    <main id="main_frame">
        <h1 id="banner_logo">Admin<br>DoShop</h1>
        <form id="admin_signin" method="post" action="/DoShop/Admin/Signin">
            <label class="label_signin">관리자 계정</label>
            <input name="inputEmail" class="input_signin" type="text" placeholder="계정을 입력하세요." spellcheck="false" autocomplete="off">
            <label class="label_signin">비밀번호</label>
            <input name="inputPassword" class="input_signin" type="password" placeholder="비밀번호를 입력하세요." spellcheck="false" autocomplete="off">
            <input id="button_signin" type="submit" value="로그인">
            <label id="label_signin_alarm">${errorMessage}</label>
        </form>
    </main>
    <footer id="footer_frame">
        <span id="wrap_footer">
            <a class="content_footer" href="#">이용약관</a>
            |
            <a class="content_footer" href="#">개인정보처리방침</a>
            |
            <a class="content_footer" href="#">고객센터</a>
        </span>
    </footer>
    <script src="/DoShop/javascript/Admin/Signin.js"></script>
</body>
</html>