const UNENABLE_PNG = "/DoShop/image/unenable.png";
const ENABLE_PNG = "/DoShop/image/enable.png";
var Validity = {
    email : false,
    duplicateEmail : false,
    password : false,
    passwordConfirm : false,
    name : false,
    sex : false,
    birth : false,
    phone : false,
    address : false
};
var authenticationTime;
var timer;

const EMAIL_MESSAGE = document.querySelector('#message_email');
const EMAIL_IMAGE = document.querySelector('#image_email');
const PASSWORD_MESSAGE = document.querySelector('#message_password');
const PASSWORD_SECURITY_MESSAGE = document.querySelector('#message_password_security');
const PASSWORD_IMAGE = document.querySelector('#image_password');
const PASSWORD_CONFIRM_MESSAGE = document.querySelector('#message_password_confirm');
const PASSWORD_CONFIRM_IMAGE = document.querySelector('#image_password_confirm');
const NAME_MESSAGE = document.querySelector('#message_name');
const NAME_IMAGE = document.querySelector('#image_name');
const SEX_MESSAGE = document.querySelector('#message_sex');
const SEX_IMAGE = document.querySelector('#image_sex');
const BIRTH_MESSAGE = document.querySelector('#message_birth');
const BIRTH_IMAGE = document.querySelector('#image_birth');
const PHONE_MESSAGE = document.querySelector('#message_phone');
const ADDRESS_MESSAGE = document.querySelector('#message_address');

const EMAIL_FAILURE_MESSAGE = "올바른 이메일을 입력해주세요!";
const EMAIL_SUCCESS_MESSAGE = "멋진 이메일이에요!";
const EMAIL_DUPLICATE_MESSAGE = "중복된 아이디에요!";
const PASSWORD_FAILURE_MESSAGE = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
const PASSWORD_SUCCESS_MESSAGE1 = "좋은 패스워드에요!";
const PASSWORD_SUCCESS_MESSAGE2 = "아주 좋은 패스워드에요!";
const PASSWORD_SECURITY_MESSAGE1 = "매우 취약";
const PASSWORD_SECURITY_MESSAGE2 = "취약";
const PASSWORD_SECURITY_MESSAGE3 = "보통";
const PASSWORD_SECURITY_MESSAGE4 = "강력";
const PASSWORD_CONFIRM_FAILURE_MESSAGE = "비밀번호와 같게 입력해주세요!";
const PASSWORD_CONFIRM_SUCCESS_MESSAGE = "비밀번호 확인을 옳바르게 입력하셨어요!";
const NAME_SUCCESS_MESSAGE = "멋진 이름이네요!";
const NAME_FAILURE_MESSAGE = "이름을 옳바르게 입력해주세요!";
const SEX_FAILURE_MESSAGE = "성별을 선택해주세요.";
const SEX_SUCCESS_MESSAGE = "바르게 선택되었어요!";
const BIRTH_FAILURE_MESSAGE = "예시와 같이 생년월일을 입력해주세요. 예) 19961209";
const BIRTH_SUCCESS_MESSAGE = "생일이 옳바르게 입력되었어요!";
const PHONE_FAILURE_MESSAGE1 = "예시와 같이 휴대전화 번호를 입력해주세요. 예) 01011112222";
const PHONE_FAILURE_MESSAGE2 = "인증하기 버튼을 눌러 인증번호를 받으세요!";
const PHONE_FAILURE_MESSAGE3 = "휴대전화 SMS로 인증번호가 발송되었어요!";
const PHONE_FAILURE_MESSAGE4 = "인증번호를 잘못 입력하셨어요!";
const PHONE_SUCCESS_MESSAGE = "인증 되었습니다!";
const ADDRESS_SUCCESS_MESSAGE = "해당 주소가 기본주소로 지정돼요!";
const ADDRESS_FAILURE_MESSAGE = "주소를 입력해주세요!";

window.onload = function(){
    document.querySelector('#input_email').addEventListener('input', emailCheck);
	document.querySelector('#input_email').addEventListener('focusout', emailDuplicateCheck);
    document.querySelector('#input_password').addEventListener('input', passwordCheck);
    document.querySelector('#input_password_confirm').addEventListener('input', passwordConfirmCheck);
    document.querySelector('#input_name').addEventListener('input', nameCheck);
    document.querySelector('#select_sex').addEventListener('change', sexCheck);
    document.querySelector('#input_birth').addEventListener('input', birthCheck);
    document.querySelector('#input_phone_number').addEventListener('input', phoneNumberCheck);
    document.querySelector('#button_send_phone_number').addEventListener('click', sendPhoneNumber);
	document.querySelector('#button_send_authentication_number').addEventListener('click', sendAuthenticationNumber);
	document.querySelector('#button_find_postcode').addEventListener('click', findPostcode);
	document.querySelector('#input_postcode').addEventListener('input', addressCheck);
	document.querySelector('#input_address').addEventListener('input', addressCheck);
	document.querySelector('#input_detail_address').addEventListener('input', addressCheck);
	document.querySelector('#input_extra_address').addEventListener('input', addressCheck);
	document.querySelector('#button_completion').addEventListener('click', complete);
	
	if(errorMessage !== undefined){
		alert(errorMessage);
	}
	
	emailCheck();
	passwordCheck();
	passwordConfirmCheck();
	nameCheck();
	sexCheck();
	birthCheck();
	phoneNumberCheck();
	addressCheck();
};

function regularExpressionCheck(REGULAR_EXPRESSION, TEXT){
    if(!(/test/.constructor == REGULAR_EXPRESSION.constructor) || 
       !("test".constructor == TEXT.constructor)){
        console.log("regularExpressionCheck() 함수에 잘못된 매개변수가 입력 되었습니다.");
        return;
    }
    
    return (REGULAR_EXPRESSION.exec(TEXT) === null) ? false : true;
}

function changeMessage(MESSAGE_SELECTOR, MESSAGE, MESSAGE_COLOR){
    MESSAGE_SELECTOR.innerText = MESSAGE;
    MESSAGE_SELECTOR.style.color = MESSAGE_COLOR;
}

function changeImage(IMAGE_SELECTOR, IMAGE){
    IMAGE_SELECTOR.src = IMAGE;
}
	
function changeMessageImage(MESSAGE_SELECTOR, IMAGE_SELECTOR, MESSAGE, MESSAGE_COLOR, IMAGE){
    changeMessage(MESSAGE_SELECTOR, MESSAGE, MESSAGE_COLOR);
	changeImage(IMAGE_SELECTOR, IMAGE);
}
    
function emailCheck(){
    const EMAIL = document.querySelector('#input_email').value;
    const REGULAR_EXPRESSION = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[0-9a-zA-Z]{1,3}$/i;
   
    let regCheck = regularExpressionCheck(REGULAR_EXPRESSION, EMAIL);
    if(regCheck === undefined){
        console.log("emailCheck() 함수에 오류가 발생했습니다.");
		Validity.email = false;
    }else if(regCheck === true){
		changeMessageImage(EMAIL_MESSAGE, EMAIL_IMAGE, EMAIL_SUCCESS_MESSAGE, "blue", ENABLE_PNG);
		Validity.email = true;
    }else if(regCheck === false){
		changeMessageImage(EMAIL_MESSAGE, EMAIL_IMAGE, EMAIL_FAILURE_MESSAGE, "red", UNENABLE_PNG);
        Validity.email = false;
    }
}

function emailDuplicateCheck(){
	const EMAIL = document.querySelector('#input_email').value;
	$.ajax({
		url: "http://localhost:8090/DoShop/Member/send_email",
		data: {email : EMAIL},
		type: "post",
		dataType: "text",
		async:true
	}).done(function(data){
		if(data === "false"){
			//changeMessageImage(EMAIL_MESSAGE, EMAIL_IMAGE, EMAIL_SUCCESS_MESSAGE, "blue", ENABLE_PNG);
			Validity.duplicateEmail = true;
			emailCheck();
		}else{
			changeMessageImage(EMAIL_MESSAGE, EMAIL_IMAGE, EMAIL_DUPLICATE_MESSAGE, "red", UNENABLE_PNG);
			Validity.duplicateEmail = false;
		}
	}).fail(function(){
			alert("[서버 에러]\n관리자에게 문의해주세요!");
	});
}
    
function passwordCheck(){
    const PASSWORD = document.querySelector('#input_password').value;
    const REGULAR_EXPRESSION = /^[0-9a-zA-Z~!@#$%^&*()_+]{8,16}$/;
    let level = 0;

    passwordConfirmCheck();

    // 정규식체크
    if(!regularExpressionCheck(REGULAR_EXPRESSION, PASSWORD)){
		changeMessageImage(PASSWORD_MESSAGE, PASSWORD_IMAGE, "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", "red", UNENABLE_PNG);
        PASSWORD_SECURITY_MESSAGE.innerText = "";
        Validity.password = false;
		return;
    }

    if(/[0-9]/.exec(PASSWORD)){ level++; }
    if(/[a-z]/.exec(PASSWORD)){ level++; }
    if(/[A-Z]/.exec(PASSWORD)){ level++; }
    if(/[~!@#$%^&*()_+]/.exec(PASSWORD)){ level++; }
    
    switch(level){
        case 1:
            changeMessageImage(PASSWORD_MESSAGE, PASSWORD_IMAGE, PASSWORD_FAILURE_MESSAGE, "red", UNENABLE_PNG);
            changeMessage(PASSWORD_SECURITY_MESSAGE, PASSWORD_SECURITY_MESSAGE1, "red");
            break;
        case 2:
            changeMessageImage(PASSWORD_MESSAGE, PASSWORD_IMAGE, PASSWORD_FAILURE_MESSAGE, "red", UNENABLE_PNG);
			changeMessage(PASSWORD_SECURITY_MESSAGE, PASSWORD_SECURITY_MESSAGE2, "orange");
            break;
        case 3:
			changeMessageImage(PASSWORD_MESSAGE, PASSWORD_IMAGE, PASSWORD_SUCCESS_MESSAGE1, "green", ENABLE_PNG);
            changeMessage(PASSWORD_SECURITY_MESSAGE, PASSWORD_SECURITY_MESSAGE3, "green");
            break;
        case 4:
			changeMessageImage(PASSWORD_MESSAGE, PASSWORD_IMAGE, PASSWORD_SUCCESS_MESSAGE2, "blue", ENABLE_PNG);
            changeMessage(PASSWORD_SECURITY_MESSAGE, PASSWORD_SECURITY_MESSAGE4, "blue");
            break;
    }

	if(level < 3){
		Validity.password = false
	}else{
		Validity.password = true
	}
}

function passwordConfirmCheck(){
    const PASSWORD = document.querySelector('#input_password').value;
    const PASSWORD_CONFIRM = document.querySelector('#input_password_confirm').value;

    if(PASSWORD_CONFIRM.length === 0 || PASSWORD !== PASSWORD_CONFIRM){
		changeMessageImage(PASSWORD_CONFIRM_MESSAGE, PASSWORD_CONFIRM_IMAGE, PASSWORD_CONFIRM_FAILURE_MESSAGE, "red", UNENABLE_PNG);
        Validity.passwordConfirm = false;
    }else{
		changeMessageImage(PASSWORD_CONFIRM_MESSAGE, PASSWORD_CONFIRM_IMAGE, PASSWORD_CONFIRM_SUCCESS_MESSAGE, "blue", ENABLE_PNG);
        Validity.passwordConfirm = true;
    }
}

function nameCheck(){
    const NAME = document.querySelector('#input_name').value;
	const REGULAR_EXPRESSION = /^[^0-9~!@#$%^&*()_+]*$/;

    if(NAME.length > 0 && regularExpressionCheck(REGULAR_EXPRESSION, NAME)){
		changeMessageImage(NAME_MESSAGE, NAME_IMAGE, NAME_SUCCESS_MESSAGE, "blue", ENABLE_PNG);
        Validity.name = true;
    }else{
		changeMessageImage(NAME_MESSAGE, NAME_IMAGE, NAME_FAILURE_MESSAGE, "red", UNENABLE_PNG);
        Validity.name = false;
    }
}

function sexCheck(){
    const SEX = document.querySelector('#select_sex').value;

    if(SEX === "none"){
		changeMessageImage(SEX_MESSAGE, SEX_IMAGE, SEX_FAILURE_MESSAGE, "red", UNENABLE_PNG);
        Validity.sex = false;
    }else{
		changeMessageImage(SEX_MESSAGE, SEX_IMAGE, SEX_SUCCESS_MESSAGE, "blue", ENABLE_PNG);
        Validity.sex = true;
    }
}

function birthCheck(){
    const BIRTH = document.querySelector('#input_birth').value;
    const MIN_YEAR = 1900;
    const MAX_YEAR = 2021;
    const REGULAR_EXPRESSION = /^[0-9]{8}$/;

    //if(BIRTH.length !== 8 | !regularExpressionCheck(REGULAR_EXPRESSION, BIRTH)){
	if(!regularExpressionCheck(REGULAR_EXPRESSION, BIRTH)){
		changeMessageImage(BIRTH_MESSAGE, BIRTH_IMAGE, BIRTH_FAILURE_MESSAGE, "red", UNENABLE_PNG);
        Validity.birth = false;
        return;
    }

    let year = parseInt(BIRTH.substr(0, 4));
    let month = parseInt(BIRTH.substr(4, 2));
    let day = parseInt(BIRTH.substr(6, 2));

    if((year >= MIN_YEAR && year <= MAX_YEAR) && 
        (month >= 1 && month <= 12) &&
        (day >= 1 && month <= 31)){
			changeMessageImage(BIRTH_MESSAGE, BIRTH_IMAGE, BIRTH_SUCCESS_MESSAGE, "blue", ENABLE_PNG);
        	Validity.birth = true;
    }else{
        changeMessageImage(BIRTH_MESSAGE, BIRTH_IMAGE, BIRTH_FAILURE_MESSAGE, "red", UNENABLE_PNG);
       	Validity.birth = false;
    }
}

function phoneNumberCheck(){
    const PHONE_NUMBER = document.querySelector('#input_phone_number').value;
    const REGULAR_EXPRESSION = /^[0-9]{10,11}$/;
    const AUTHENTICATION_NUMBER = document.querySelector('#input_authentication_number');
    const SEND_AUTHENTICATION_NUMBER = document.querySelector('#button_send_authentication_number');
	
	Validity.phone = false;
	clearInterval(timer);
	authenticationTime = 0;
	displayAuthenticationTimer(authenticationTime);
	AUTHENTICATION_NUMBER.value ="";
    AUTHENTICATION_NUMBER.disabled = true;
    SEND_AUTHENTICATION_NUMBER.disabled = true;
    
	if(!regularExpressionCheck(REGULAR_EXPRESSION, PHONE_NUMBER)){
		changeMessage(PHONE_MESSAGE, PHONE_FAILURE_MESSAGE1, "red");
		return false;
    }else{
		changeMessage(PHONE_MESSAGE, PHONE_FAILURE_MESSAGE2, "red");
        return true;
    }
}

function sendPhoneNumber(){
	const PHONE_NUMBER = document.querySelector('#input_phone_number').value;
    const AUTHENTICATION_NUMBER = document.querySelector('#input_authentication_number');
    const SEND_AUTHENTICATION_NUMBER = document.querySelector('#button_send_authentication_number');
	
    if(phoneNumberCheck()){
		changeMessage(PHONE_MESSAGE, PHONE_FAILURE_MESSAGE3, "green");
        $.ajax({
			url: "http://localhost:8090/DoShop/Member/send_phone_number",
			data: {phone_number : PHONE_NUMBER},
			type: "post",
			dataType: "text",
			async:true
		}).done(function(data){
			authenticationTimer(parseInt(data));
        	AUTHENTICATION_NUMBER.disabled = false;
        	SEND_AUTHENTICATION_NUMBER.disabled = false;
		}).fail(function(){
			alert("[서버 에러]\n관리자에게 문의해주세요!");
		});
    }
}

function sendAuthenticationNumber(){
	const PHONE_NUMBER = document.querySelector('#input_phone_number').value;
	const AUTHENTICATION_NUMBER = document.querySelector('#input_authentication_number').value;
    const AUTHENTICATION_NUMBER_INPUT = document.querySelector('#input_authentication_number');
    const SEND_AUTHENTICATION_NUMBER = document.querySelector('#button_send_authentication_number');
			
	$.ajax({
		url: "http://localhost:8090/DoShop/Member/send_authentication_number",
		data: {
			phone_number : PHONE_NUMBER,
			authentication_number : AUTHENTICATION_NUMBER
		},
		type: "post",
		dataType: "text",
		async:true
	}).done(function(data){
		if(data === "true"){
			changeMessage(PHONE_MESSAGE, PHONE_SUCCESS_MESSAGE, "blue");
			clearInterval(timer);
			authenticationTime = 0;
			displayAuthenticationTimer(authenticationTime);
    		AUTHENTICATION_NUMBER_INPUT.disabled = true;
    		SEND_AUTHENTICATION_NUMBER.disabled = true;
			Validity.phone = true;
		}else{
			changeMessage(PHONE_MESSAGE, PHONE_FAILURE_MESSAGE4, "red");
		}
	}).fail(function(){
		alert("[서버 에러]\n관리자에게 문의해주세요!");
	});
}

function authenticationTimer(timeSec){
	authenticationTime = timeSec;
	clearInterval(timer);
	timer = setInterval(function(){
		displayAuthenticationTimer(authenticationTime);
		if(authenticationTime > 0){
			authenticationTime--;
		}else{
			const AUTHENTICATION_NUMBER = document.querySelector('#input_authentication_number');
    		const SEND_AUTHENTICATION_NUMBER = document.querySelector('#button_send_authentication_number');
			AUTHENTICATION_NUMBER.value = "";	            
			AUTHENTICATION_NUMBER.disabled = true;
            SEND_AUTHENTICATION_NUMBER.disabled = true;
			Validity.phone = false;
			clearInterval(timer);
			phoneNumberCheck();
		}
	}, 1000);
}
	
function displayAuthenticationTimer(timeSec){
	const AUTHENTICATION_TIMER = document.querySelector('#authentication_timer');
	let min = parseInt(timeSec / 60);
	let sec = timeSec % 60;
	
	if(min < 10){
		min = "0" + min;
	}
	if(sec < 10){
		sec = "0" + sec;
	}
    AUTHENTICATION_TIMER.innerText = "" + min + " : " + sec;
}
	
function findPostcode(){
	new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("input_extra_address").value = extraAddr;
            
            } else {
                document.getElementById("input_extra_address").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('input_postcode').value = data.zonecode;
            document.getElementById("input_address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("input_detail_address").focus();
			addressCheck();
        }
    }).open();
}
	
function addressCheck(){
	const POSTCODE = document.querySelector('#input_postcode').value;
	const ADDRESS = document.querySelector('#input_address').value;
	const DETAIL_ADDRESS = document.querySelector('#input_detail_address').value;
	
	
	if(POSTCODE.length > 0 && ADDRESS.length > 0 && DETAIL_ADDRESS.length > 0){
		changeMessage(ADDRESS_MESSAGE, ADDRESS_SUCCESS_MESSAGE, "blue");
        Validity.address = true;
	}else{
		changeMessage(ADDRESS_MESSAGE, ADDRESS_FAILURE_MESSAGE, "red");
        Validity.address = false;
	}
}
	
function complete(){
	if(!Validity.email){
        const EMAIL = document.querySelector('#input_email');
		changeMessageImage(EMAIL_MESSAGE, EMAIL_IMAGE, EMAIL_FAILURE_MESSAGE, "red", UNENABLE_PNG);
		EMAIL.scrollIntoView(false);
		EMAIL.focus();
		return;
	}else if(!Validity.duplicateEmail){
        const EMAIL = document.querySelector('#input_email');
		changeMessageImage(EMAIL_MESSAGE, EMAIL_IMAGE, EMAIL_DUPLICATE_MESSAGE, "red", UNENABLE_PNG);
		EMAIL.scrollIntoView(false);
		EMAIL.focus();
		return;
	}else if(!Validity.password){
        const PASSWORD = document.querySelector('#input_password');
		changeMessageImage(PASSWORD_MESSAGE, PASSWORD_IMAGE, PASSWORD_FAILURE_MESSAGE, "red", UNENABLE_PNG);
		PASSWORD.scrollIntoView(false);
		PASSWORD.focus();
		return;
	}else if(!Validity.passwordConfirm){
        const PASSWORD_CONFIRM = document.querySelector('#input_password_confirm');
		changeMessageImage(PASSWORD_CONFIRM_MESSAGE, PASSWORD_CONFIRM_IMAGE, PASSWORD_CONFIRM_FAILURE_MESSAGE, "red", UNENABLE_PNG);
		PASSWORD_CONFIRM.scrollIntoView(false);
		PASSWORD_CONFIRM.focus();
		return;
	}else if(!Validity.name){
        const NAME = document.querySelector('#input_name');
		changeMessageImage(NAME_MESSAGE, NAME_IMAGE, NAME_FAILURE_MESSAGE, "red", UNENABLE_PNG);
		NAME.scrollIntoView(false);
		NAME.focus();
		return;
	}else if(!Validity.sex){
        const SEX = document.querySelector('#select_sex');
        changeMessageImage(SEX_MESSAGE, SEX_IMAGE, SEX_FAILURE_MESSAGE, "red", UNENABLE_PNG);
		SEX.scrollIntoView(false);
		SEX.focus();
		return;
	}else if(!Validity.birth){
    	const BIRTH = document.querySelector('#input_birth');
        changeMessageImage(BIRTH_MESSAGE, BIRTH_IMAGE, BIRTH_FAILURE_MESSAGE, "red", UNENABLE_PNG);
		BIRTH.scrollIntoView(true);
		BIRTH.focus();
		return;
	}else if(!Validity.phone){
        const PHONE_NUMBER = document.querySelector('#input_phone_number');
        const AUTHENTICATION_NUMBER = document.querySelector('#input_authentication_number');
        const SEND_AUTHENTICATION_NUMBER = document.querySelector('#button_send_authentication_number');
		AUTHENTICATION_NUMBER.value ="";
        AUTHENTICATION_NUMBER.disabled = true;
        SEND_AUTHENTICATION_NUMBER.disabled = true;
        changeMessage(PHONE_MESSAGE, PHONE_FAILURE_MESSAGE1, "red");
		PHONE_NUMBER.scrollIntoView(true);
		PHONE_NUMBER.focus();
		return;
	}else if(!Validity.address){
		const POSTCODE = document.querySelector('#input_postcode');
        changeMessage(ADDRESS_MESSAGE, ADDRESS_FAILURE_MESSAGE, "red");
		POSTCODE.scrollIntoView(true);
		return;
	}
	
	const FORM_MEMBER_INFORMATION = document.querySelector('#member_information');
	FORM_MEMBER_INFORMATION.submit();
}