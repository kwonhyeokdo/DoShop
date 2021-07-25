$(document).ready(function(){
	$('#input_submit').click(function(){
		$.ajax({
			url: "/DoShop/Member/UpdateInformation/CheckPassword",
			method: "post",
			data: {inputPassword: $('input[name=inputPassword]').val()},
			async: false 
		}).done(function(data){
			if(data === "false"){
				$('#errorMessage').text('일치하지 않는 비밀번호입니다.');
			}else{
				let form = document.createElement("form");
			    form.setAttribute("charset", "UTF-8");
			    form.setAttribute("method", "Post");
			    form.setAttribute("action", "/DoShop/Member/UpdateInformation/");
			
				let hidden = document.createElement("input");
				hidden.setAttribute("name", "pageAuthCode");
				hidden.setAttribute("value", data);
				form.appendChild(hidden);
			
				document.body.appendChild(form);
				form.submit();
			}
		}).fail(function(){
			alert('서버오류');
		});
	});
	
});