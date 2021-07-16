$(document).ready(function(){
	$('#menu_admin_management').click(function(){
		let form = document.createElement("form");
        form.setAttribute("charset", "UTF-8");
        form.setAttribute("method", "Get");
        form.setAttribute("action", "/DoShop/Admin/AdminManagement/");
	
		document.body.appendChild(form);
		form.submit();
	});
	
	$('#menu_category_management').click(function(){
		let form = document.createElement("form");
        form.setAttribute("charset", "UTF-8");
        form.setAttribute("method", "Get");
        form.setAttribute("action", "/DoShop/Admin/CategoryManagement/");
	
		document.body.appendChild(form);
		form.submit();
	});
});