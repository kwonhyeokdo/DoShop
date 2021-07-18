<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DoShop</title>
    <link rel="stylesheet" type="text/css" href="/DoShop/css/Home.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
    <main class="main">
        <section class="banner">
            <div class="pannel"></div>
            <a id="banner_logo" href="/DoShop" >DoShop</a>
            <div id="wrap_search">
                <input placeholder="상품명, 브랜드 등을 입력하세요." type="text" spellcheck="false">
                <button></button>
            </div>
        </section>
    </main>
    <%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
</body>
</html>