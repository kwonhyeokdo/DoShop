$(document).ready(function(){
	$('#button_signin').click(function(){
		let isChecked;
		if($('#checkRememberEmail').is(":checked")){
			isChecked = "true";
		}else{
			isChecked = "false";
		}
		$('input[name="checkRememberEmail"]').val(isChecked);
		
		if($('#checkAutoSignin').is(":checked")){
			isChecked = "true";
		}else{
			isChecked = "false";
		}
		$('input[name="checkAutoSignin"]').val(isChecked);
		
		$('#wrap_signin').submit();
	});
});