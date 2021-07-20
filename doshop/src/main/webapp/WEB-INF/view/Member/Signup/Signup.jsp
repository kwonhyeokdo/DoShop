<%@page import="java.util.ArrayList"%>
<%@page import="database.vo.TemporaryMemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/DoShop/css/Member/Signup.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>DoShopSignUp</title>
</head>
<body>
	<div id="wallpaper"></div>
	<div id="pannel"></div>
    <nav id="navi_top">
        <a id="navi_logo" href="/DoShop/">DoShop</a>
        <a class="sign" href="/DoShop/Member/Signin">로그인</a>
    </nav>
    <header class="background">
        <div class="container">
            <div id="wrap_logo">
                <a href="/DoShop/" id="logo">DoShop</a>
            </div>
        </div>
    </header>
    <main class="background">
        <div class="container">
            <form:form modelAttribute="temporaryMemberVO" id="member_information" action="/DoShop/Member/Signup/RegistTemporaryMember" method="post">
                <h2>이메일</h2>
                <div class="wrap_input">
                	<form:input path="email" name="email" id="input_email" class="input_hidden" type="text" spellcheck="false" autocomplete="off"/>
                	<img id="image_email" class="image_check" src="/DoShop/image/unenable.png">
                    <p class="float_clear"></p>
                </div>
                <h3 id="message_email" class="space">이메일 메시지</h3>

                <h2>비밀번호</h2>
                <div class="wrap_input">
                    <input name="password" id="input_password" class="input_hidden" type="password" spellcheck="false" autocomplete="off"/>
                    <img id="image_password" class="image_check" src="/DoShop/image/unenable.png">
                    <span id="message_password_security" class="text_security_grade">매우 취약</span>
                    <p class="float_clear"></p>
                </div>
                <h3 id="message_password" class="space">비밀번호 메시지</h3>

                <h2>비밀번호 확인</h2>
                <div class="wrap_input">
                    <input id="input_password_confirm" class="input_hidden" type="password" spellcheck="false" autocomplete="off"/>
                    <img id="image_password_confirm" class="image_check" src="/DoShop/image/unenable.png">
                    <p class="float_clear"></p>
                </div>
                <h3 id="message_password_confirm" class="space">비밀번호 확인 메시지</h3>

                <h2>이름</h2>
                <div class="wrap_input">
                    <form:input path="name" name="name" id="input_name" class="input_hidden" type="text" spellcheck="false" autocomplete="off"/>
                    <img id="image_name" class="image_check" src="/DoShop/image/unenable.png">
                    <p class="float_clear"></p>
                </div>
                <h3 id="message_name" class="space">이름 메시지</h3>

                <h2>성별</h2>
                <div id="wrap_select">
                    <form:select path="sex" name="sex" id="select_sex">
                        <form:option value="none">선택</form:option>
                        <form:option value='M'>남자</form:option>
                        <form:option value='F'>여자</form:option>
                    </form:select>
                    <img id="image_sex" class="image_check" src="/DoShop/image/unenable.png">
                    <p class="float_clear"></p>
                </div>
                <h3 id="message_sex" class="space">성별 메시지</h3>

                <h2>생일</h2>
                <div class="wrap_input">
                    <form:input path="birthday" name="birthday" id="input_birth" class="input_hidden" type="text" spellcheck="false" autocomplete="off"/>
                    <img id="image_birth" class="image_check" src="/DoShop/image/unenable.png">
                    <p class="float_clear"></p>
                </div>
                <h3 id="message_birth" class="space">생일 메시지</h3>

                
                <h2>휴대전화</h2>
                <div id="wrap_phone_number">
                    <form:input path="phoneNumber" name="phoneNumber" id="input_phone_number" type="text" spellcheck="false" autocomplete="off"/>
                    <input id="button_send_phone_number" type="button" value="인증하기"/>
                    <p class="float_clear"></p>
                </div>
                <div id="wrap_authentication_number">
                    <div id="wrap_input_authentication_number">
                        <input id="input_authentication_number" type="text" disabled spellcheck="false" autocomplete="off"/>
                        <div id="authentication_timer">00:00</div>
                        <p class="float_clear"></p>
                    </div>
                    <input id="button_send_authentication_number" type="button" value="확인하기" disabled/>
                    <p class="float_clear"></p>
                </div>
                <h3 id="message_phone" class="space">휴대전화 메시지</h3>
                
                <h2>주소</h2>
                <div id="wrap_address">
                    <form:input path="postcode" name="postcode" type="text" id="input_postcode" placeholder="우편번호" spellcheck="false" autocomplete="off"/>
                    <input type="button" id="button_find_postcode" value="우편번호 찾기">
                    <form:input path="address" name="address" type="text" id="input_address" placeholder="주소" spellcheck="false" autocomplete="off"/>
                    <form:input path="detailAddress" name="detailAddress" type="text" id="input_detail_address" placeholder="상세주소" spellcheck="false" autocomplete="off"/>
                    <form:input path="extraAddress" name="extraAddress" type="text" id="input_extra_address" placeholder="참고항목" spellcheck="false" autocomplete="off"/>
                </div>
                
                <h3 id="message_address" class="space">주소 메시지</h3>

                <input id="button_completion" type="button" value="가입하기"/>
            </form:form>
        </div>
    </main>
    <footer class="background">
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
    </footer>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
var errorMessage;
</script>
<%
ArrayList<String> errorList = (ArrayList<String>)request.getAttribute("errorList");
if(errorList != null && !errorList.isEmpty()){
	String errorMessage = "";
	for(String errorCode : errorList){
		switch(errorCode){
			case "email":
				errorMessage += "이메일";
				System.out.println(errorMessage);
				break;
			case "password":
				errorMessage += "비밀번호";
				System.out.println(errorMessage);
				break;
			case "name":
				errorMessage += "이름";
				System.out.println(errorMessage);
				break;
			case "sex":
				errorMessage += "성별";
				System.out.println(errorMessage);
				break;
			case "birthday":
				errorMessage += "생일";
				System.out.println(errorMessage);
				break;
			case "phoneNumber":
				errorMessage += "핸드폰 번호";
				System.out.println(errorMessage);
				break;
			case "address":
				errorMessage += "주소";
				System.out.println(errorMessage);
				break;
		}
		errorMessage += ", ";
	}
	errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
	errorMessage += "를(을) 잘못 입력하셨습니다.";
%>
<script>
	errorMessage = "<%=errorMessage %>";
</script>
<%
}
%>
    <script src="/DoShop/javascript/Member/Signup.js"></script>
</body>
</html>