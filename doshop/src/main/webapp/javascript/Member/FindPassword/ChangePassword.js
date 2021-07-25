var PasswordValidity = false;
var OldPasswordValidity = false;
var PasswordConfirmValidity = false;
$(document).ready(function(){
	passwordCheck();
	passwordConfirmCheck();
	$("#input_password").on("change keyup paste", function() {
		passwordCheck();
	}).on("focusout", function(){
		oldPasswordCheck();
	});
	
	$("#input_password_confirm").on("change keyup paste", function() {
		passwordConfirmCheck();
	});
	
	$("#button_submit").click(function(){
		if(!PasswordValidity || !PasswordConfirmValidity || !OldPasswordValidity){
			alert('조건이 충족되지 않았습니다.');
			return;
		}else{
			$.ajax({
				url: '/DoShop/Member/FindPassword/ChangePassword',
				method: "post",
				async: false,
				data: {
					inputEmail: $('input[name=inputEmail]').val(),
					inputPassword: $('input[name=inputPassword]').val()
				}
			}).done(function(data){
				if(data){
					alert('비밀번호가 변경되었습니다.');
					window.location.href = '/DoShop/Member/Signin/';
				}else{
					alert('조건이 충족되지 않았습니다.');
				}
			}).fail(function(){
				alert('서버 오류');
			});
		}
	});
});

function oldPasswordCheck(){
	$.ajax({
		url: '/DoShop/Member/FindPassword/IsOldPassword',
		method: "post",
		async: false,
		data: {
			inputEmail: $('input[name=inputEmail]').val(),
			inputPassword: $('input[name=inputPassword]').val()
		}
	}).done(function(data){
		oldPassword = data;
	}).fail(function(){
		alert('서버 오류');
		oldPassword = false;
	});

	if(oldPassword){
		changeNotice('#passord_notice', "기존의 패스워드입니다. 새로 설정해주세요!", "red");
        changeNotice('#passord_security_notice', "", "red");
        OldPasswordValidity = false;
		return;
	}else{
		OldPasswordValidity = true;
	}
}

function passwordCheck(){
	let inputPassword = $('#input_password').val();
    const reg = /^[0-9a-zA-Z~!@#$%^&*()_+]{8,16}$/;
    let level = 0;

    passwordConfirmCheck();

    // 정규식체크
    if(!regularExpressionCheck(reg, inputPassword)){
		changeNotice('#passord_notice', "8~16자 영문 대/소문자, 숫자, 특수문자를 사용하세요.", "red");
        changeNotice('#passord_security_notice', "", "red");
        PasswordValidity = false;
		return;
    }

    if(/[0-9]/.exec(inputPassword)){ level++; }
    if(/[a-z]/.exec(inputPassword)){ level++; }
    if(/[A-Z]/.exec(inputPassword)){ level++; }
    if(/[~!@#$%^&*()_+]/.exec(inputPassword)){ level++; }
    
    switch(level){
        case 1:
			changeNotice('#passord_notice', "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", "red");
			changeNotice('#passord_security_notice', "매우 취약", "red");
            break;
        case 2:
			changeNotice('#passord_notice', "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", "red");
			changeNotice('#passord_security_notice', "취약", "orange");
            break;
        case 3:
			changeNotice('#passord_notice', "좋은 패스워드에요!", "yellow");
			changeNotice('#passord_security_notice', "보통", "yellow");
            break;
        case 4:
			changeNotice('#passord_notice', "아주 좋은 패스워드에요!", "lightgreen");
			changeNotice('#passord_security_notice', "강력", "lightgreen");
            break;
    }

	if(level < 3){
		PasswordValidity = false
	}else{
		PasswordValidity = true
	}
}

function passwordConfirmCheck(){
    let inputPassword = $('#input_password').val();
    let inputPasswordConfirm = $('#input_password_confirm').val();

    if(inputPasswordConfirm.length === 0 || inputPassword !== inputPasswordConfirm){
		changeNotice('#passord_notice_confirm', "비밀번호와 같게 입력해주세요!", "red");
		PasswordConfirmValidity = false;
    }else{
		changeNotice('#passord_notice_confirm', "비밀번호 확인을 옳바르게 입력하셨어요!", "lightgreen");
		PasswordConfirmValidity = true;
    }
}

function changeNotice(messageSelector, message, color){
	$(messageSelector).text(message);
	$(messageSelector).css('color', color);
}

function regularExpressionCheck(REGULAR_EXPRESSION, TEXT){
    if(!(/test/.constructor == REGULAR_EXPRESSION.constructor) || 
       !("test".constructor == TEXT.constructor)){
        console.log("regularExpressionCheck() 함수에 잘못된 매개변수가 입력 되었습니다.");
        return;
    }
    
    return (REGULAR_EXPRESSION.exec(TEXT) === null) ? false : true;
}