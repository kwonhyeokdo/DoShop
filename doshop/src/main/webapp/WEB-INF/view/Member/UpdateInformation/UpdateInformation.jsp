<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원정보</title>
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Member/UpdateInformation/UpdateInformation.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<script>
		var ServerMember ={
			name: "${memberVO.name}",
			birthday: "${memberVO.birthday}",
			sex: "${memberVO.sex}",
			phoneNumber: "${memberVO.phoneNumber}",
			postcode: "${memberVO.postcode}",
			address: "${memberVO.address}",
			detailAddress: "${memberVO.detailAddress}",
			extraAddress: "${memberVO.extraAddress}"
		}
		var PageAuthCode = "${pageAuthCode}";
	</script>
	<%@ include file="/WEB-INF/view/Etc/NavAndMenu.jsp" %>
	<main class="main">
		<div class="background"></div>
		<div class="pannel"></div>
		<form:form modelAttribute="memberVO" class="memeber_box" id="member_information" action="/DoShop/Member/Information/UpdateMember" method="post">
			<form:input path="memberNumber" name="memberNumber" type="hidden"/>
			<h1 class="title">회원정보</h1>
	        <h2 class="member_label">비밀번호</h2>
	        <div class="wrap_input">
	            <input id="input_password" name="password" class="input_text_password" type="password" spellcheck="false" autocomplete="off"/>
	            <span id="message_password_security" class="inner_text">매우 취약</span>
	            <img id="image_password" class="icon" src="/DoShop/image/unenable.png">
	        </div>
	        <h3 id="message_password" class="notice"></h3>
	
	        <h2 class="member_label">비밀번호 확인</h2>
	        <div class="wrap_input">
	            <input id="input_password_confirm" class="input_text" type="password" spellcheck="false" autocomplete="off"/>
	            <img id="image_password_confirm" class="icon" src="/DoShop/image/unenable.png">
	        </div>
	        <h3 id="message_password_confirm" class="notice"></h3>
	
	        <h2 class="member_label">이름</h2>
	        <div class="wrap_input">
	            <form:input id="input_name" path="name" name="name" class="input_text" type="text" spellcheck="false" autocomplete="off"/>
	            <img id="image_name" class="icon" src="/DoShop/image/unenable.png">
	        </div>
	        <h3 id="message_name" class="notice"></h3>
	
	        <h2 class="member_label">성별</h2>
	        <div class="wrap_sex">
	            <form:select id="select_sex" path="sex" name="sex" class="select_sex">
	                <form:option class="option_sex" value="none">선택</form:option>
	                <form:option class="option_sex" value='M'>남자</form:option>
	                <form:option class="option_sex" value='F'>여자</form:option>
	            </form:select>
	            <img id="image_sex" class="icon" src="/DoShop/image/unenable.png">
	        </div>
	        <h3 id="message_sex" class="notice"></h3>
	
	        <h2 class="member_label">생일</h2>
	        <div class="wrap_input">
	            <form:input id="input_birth" path="birthday" name="birthday" class="input_text" type="text" spellcheck="false" autocomplete="off"/>
	            <img id="image_birth" class="icon" src="/DoShop/image/unenable.png">
	        </div>
	        <h3 id="message_birth" class="notice"></h3>
	
	        
	        <h2 class="member_label">휴대전화</h2>
	        <div class="wrap_phone_number">
	            <form:input id="input_phone_number" class="input_phone" path="phoneNumber" name="phoneNumber" type="text" spellcheck="false" autocomplete="off"/>
	            <input id="button_send_phone_number" class="button_phone" type="button" value="인증하기"/>
	        </div>
	        <div class="wrap_authentication">
	            <div class="wrap_input_timer">
	                <input id="input_authentication_number" class="input_authentication" type="text" disabled spellcheck="false" autocomplete="off"/>
	                <div id="authentication_timer" class="authentication_timer">00:00</div>
	            </div>
	            <input id="button_send_authentication_number" class="button_phone" type="button" value="확인하기" disabled/>
	        </div>
	        <h3 id="message_phone" class="notice"></h3>
	        
	        <h2 class="member_label">주소</h2>
	        <div class="wrap_address">
	            <form:input id="input_postcode" class="input_postcode" path="postcode" name="postcode" type="text" placeholder="우편번호" spellcheck="false" autocomplete="off"/>
	            <input id="button_find_postcode" class="button_find_postcode" type="button" value="우편번호 찾기">
	            <form:input id="input_address" class="input_address" path="address" name="address" type="text" placeholder="주소" spellcheck="false" autocomplete="off"/>
	            <form:input id="input_detail_address" class="input_detail_address" path="detailAddress" name="detailAddress" type="text" placeholder="상세주소" spellcheck="false" autocomplete="off"/>
	            <form:input id="input_extra_address" class="input_extra_address" path="extraAddress" name="extraAddress" type="text" placeholder="참고항목" spellcheck="false" autocomplete="off"/>
	        </div>
	        <h3 id="message_address" class="notice"></h3>
	
	        <input id="button_completion" class="button_fixed" type="button" value="수정하기"/>
	    </form:form>
	</main>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="/DoShop/javascript/Member/UpdateInformation/UpdateInformation.js"></script>
	<%@ include file="/WEB-INF/view/Etc/Footer.jsp" %>
</body>
</html>