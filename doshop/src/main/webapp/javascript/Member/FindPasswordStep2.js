var AuthenticationTime;
var Timer;
var PageAuthCode = "";
$(document).ready(function(){
	$('#button_phone_authority').click(function(){
		$('#choice_box').css('display', 'none');
		$('#phone_box').css('display', 'flex');
		let phoneNumber = getPhoneNumber(InputEmail);
		$('#phone_number').text(phoneNumber);
	});
	
	$('#button_email_authority').click(function(){
		$('#choice_box').css('display', 'none');
		$('#email_box').css('display', 'flex');
		createAuthenticationEmail(InputEmail);
	}); 
	
	$('#button_get_authority_number').click(function(){
		getAuthorityNumber(InputEmail);
	});
	
	$('#button_check_authority_number').click(function(){
		let authenticationNumber = $('#input_authentication_number').val();
		checkAuthorityNumber(authenticationNumber, InputEmail);
	});
	
	$('#button_email_code').click(function(){
		let authenticationCode = $('#input_email_code').val();
		checkAuthorityEmailCode(authenticationCode, InputEmail);
	})
});

function getPhoneNumber(inputEmail){
	let phoneNumber;
	$.ajax({
		url: '/DoShop/Member/FindPassword/GetPhoneNumber',
		method: 'post',
		async: false,
		data: {inputEmail: inputEmail}
	}).done(function(data){
		phoneNumber = data;
	}).fail(function(){
		alert('서버 문제 발생');
	})
	
	return phoneNumber;
}

function getAuthorityNumber(inputEmail){
	$.ajax({
		url: '/DoShop/Member/FindPassword/GetPhoneAuthorityNumber',
		method: 'post',
		async: false,
		data: {"inputEmail": inputEmail}
	}).done(function(data){
		authenticationTimer(parseInt(data));
		$('#input_authentication_number').attr("disabled", false);
			$('#button_check_authority_number').attr("disabled", false);
		alert('인증번호가 발송되었습니다.');
	}).fail(function(){
		alert('서버 문제 발생');
	})
}

function authenticationTimer(timeSec){
	AuthenticationTime = timeSec;
	clearInterval(Timer);
	Timer = setInterval(function(){
		displayAuthenticationTimer(AuthenticationTime);
		if(AuthenticationTime > 0){
			AuthenticationTime--;
		}else{
			$('#input_authentication_number').attr("disabled", true);
			$('#button_check_authority_number').attr("disabled", true);
			$('#input_authentication_number').val('');
			clearInterval(Timer);
		}
	}, 1000);
}
	
function displayAuthenticationTimer(timeSec){
	let min = parseInt(timeSec / 60);
	let sec = timeSec % 60;
	
	if(min < 10){
		min = "0" + min;
	}
	if(sec < 10){
		sec = "0" + sec;
	}
    $('#count_timer').text("" + min + " : " + sec);
}

function checkAuthorityNumber(authorityNumber, inputEmail){
	$.ajax({
		url: '/DoShop/Member/FindPassword/CheckAuthorityNumber',
		data: {
			authorityNumber: authorityNumber,
			inputEmail: inputEmail
		},
		method: "post",
		async: false
	}).done(function(data){
		if(data === "false"){
			alert('맞지 않은 인증번호입니다!');
		}else{
			PageAuthCode = data;
			goToChangePassword(inputEmail);
		}
	}).fail(function(){
		alert('서버오류');
	})
}

function checkAuthorityEmailCode(authenticationCode, inputEmail){
	$.ajax({
		url: '/DoShop/Member/FindPassword/CheckAuthorityEmailCode',
		data: {
			authenticationCode: authenticationCode,
			inputEmail: inputEmail
		},
		method: "post",
		async: false
	}).done(function(data){
		if(data === "false"){
			alert('맞지 않은 인증번호입니다!');
		}else{
			PageAuthCode = data;
			goToChangePassword(inputEmail);
		}
	}).fail(function(){
		alert('서버오류');
	})
}

function createAuthenticationEmail(inputEmail){
	$.ajax({
		url: '/DoShop/Member/FindPassword/CreateAuthenticationEmail',
		method: 'post',
		data: {inputEmail: inputEmail}
	});
}

function goToChangePassword(inputEmail){
	let form = document.createElement("form");
    form.setAttribute("charset", "UTF-8");
    form.setAttribute("method", "Post");
    form.setAttribute("action", "/DoShop/Member/FindPassword/GoToChangePassword");

	let hidden = document.createElement("input");
	hidden.setAttribute("name", "inputEmail");
	hidden.setAttribute("value", inputEmail);
	form.appendChild(hidden);
	let hidden2 = document.createElement("input");
	hidden2.setAttribute("name", "pageAuthCode");
	hidden2.setAttribute("value", PageAuthCode);
	form.appendChild(hidden2);

	document.body.appendChild(form);
	form.submit();
}