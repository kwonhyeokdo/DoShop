<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원정보</title>
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Member/Information.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
	<main class="main">
		<div class="background"></div>
		<div class="pannel"></div>
		<form:form modelAttribute="memberVO" class="memeber_box" id="member_information" action="/DoShop/Member/Signup/RegistTemporaryMember" method="post">
	        <h2 class="member_label">비밀번호</h2>
	        <div class="wrap_input">
	            <input name="password" id="input_password" class="input_text" type="password" spellcheck="false" autocomplete="off"/>
	            <img id="image_password" class="icon" src="/DoShop/image/unenable.png">
	            <span id="message_password_security" class="inner_text">매우 취약</span>
	            <p class="clear"></p>
	        </div>
	        <h3 id="message_password" class="space">비밀번호 메시지</h3>
	
	        <h2 class="member_label">비밀번호 확인</h2>
	        <div class="wrap_input">
	            <input id="input_password_confirm" class="input_text" type="password" spellcheck="false" autocomplete="off"/>
	            <img id="image_password_confirm" class="icon" src="/DoShop/image/unenable.png">
	            <p class="float_clear"></p>
	        </div>
	        <h3 id="message_password_confirm" class="space">비밀번호 확인 메시지</h3>
	
	        <h2 class="member_label">이름</h2>
	        <div class="wrap_input">
	            <form:input path="name" name="name" id="input_name" class="input_hidden" type="text" spellcheck="false" autocomplete="off"/>
	            <img id="image_name" class="icon" src="/DoShop/image/unenable.png">
	            <p class="float_clear"></p>
	        </div>
	        <h3 id="message_name" class="space">이름 메시지</h3>
	
	        <h2 class="member_label">성별</h2>
	        <div id="wrap_select">
	            <form:select path="sex" name="sex" id="select_sex">
	                <form:option value="none">선택</form:option>
	                <form:option value='M'>남자</form:option>
	                <form:option value='F'>여자</form:option>
	            </form:select>
	            <img id="image_sex" class="icon" src="/DoShop/image/unenable.png">
	            <p class="float_clear"></p>
	        </div>
	        <h3 id="message_sex" class="space">성별 메시지</h3>
	
	        <h2 class="member_label">생일</h2>
	        <div class="wrap_input">
	            <form:input path="birthday" name="birthday" id="input_birth" class="input_hidden" type="text" spellcheck="false" autocomplete="off"/>
	            <img id="image_birth" class="icon" src="/DoShop/image/unenable.png">
	            <p class="float_clear"></p>
	        </div>
	        <h3 id="message_birth" class="space">생일 메시지</h3>
	
	        
	        <h2 class="member_label">휴대전화</h2>
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
	        
	        <h2 class="member_label">주소</h2>
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
	</main>
	<%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
</body>
</html>