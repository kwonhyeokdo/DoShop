var ChangeAuthorityTable=[];
var ChangeAuthorityTarget=[];
var ChildWindow;
$(document).ready(function(){
	(function createTable(){
		AdminList.forEach(function(adminVO, index){
			if(!(AdminList.length === 0)){	
				$('table').css('display', 'table');	
					
				let tag = "<tr>";
				tag += "<td>" + adminVO.ranking +"</td>";
				tag += "<td>" + adminVO.email + "</td>";
				tag += "<td>";
				tag += "<select onchange='changeAuthorityTable(this, "+index+")'>";
				if(adminVO.authority === 0){
					tag += "<option value='0' selected>0(일반회원)</option>";	
				}else{
					tag += "<option value='0'>0(일반회원)</option>";
				}
				if(adminVO.authority === 1){
					tag += "<option value='1' selected>1</option>";	
				}else{
					tag += "<option value='1'>1</option>";
				}
				if(adminVO.authority === 2){
					tag += "<option value='2' selected>2</option>";	
				}else{
					tag += "<option value='2'>2</option>";
				}
				if(adminVO.authority === 3){
					tag += "<option value='3' selected>3</option>";	
				}else{
					tag += "<option value='3'>3</option>";
				}
				tag += "</select>";
				tag += "</td>";
				tag += "</tr>";
				$('#workspace tbody').append(tag);
				
				
				$('#message_table').css('display', 'none');
				$('#button_rollback').css('display', 'inline');
				$('#button_apply').css('display', 'inline');
			}
		});
		
		(function createPageButton(){
			let tag = "<button onclick='goToPage(1)'><<</button>";
			tag += "<button onclick='goToPage(" + ((PageSection - 9 <= 0) ? 1 : PageSection) +")'><</button>";
			for(let i = 1; i <= 10; i++){
				let page = PageSection + i;
				tag += "<button onclick='goToPage(" + page + ")'>" + page + "</button>";
				if(page >= LastPage){
					break;
				}
			}
			tag += "<button onclick='goToPage(" + (((PageSection + 11) > LastPage) ? LastPage : (PageSection + 11)) +")'>></button>";
			tag += "<button onclick='goToPage(" + LastPage + ")'>>></button>";
			$('#workspace .wrap_paging').append(tag);
		})();
		
		(function createSearchAndTag(){	
			let selectTag = $('#select_tag');
			let tag;
			if(RequestTag === "email"){
				tag = "<option value='email' selected>이메일</option><option value='authority'>권한</option>";	
			}else if(RequestTag === "authority"){
				tag = "<option value='email'>이메일</option><option value='authority' selected>권한</option>";
			}
			selectTag.append(tag);
			$('#input_search').val(RequestSearch);
			$('#input_search').on("keyup", function(event) {
		    	if (event.keyCode === 13) {
				    event.preventDefault();
				    $('#button_search').trigger("click");
			    }
			});
		})();
	})();
	
	$('#button_search').click(function(){
		let inputSearch = $('#input_search');
		let selectTag = $('#select_tag');
		
		if(inputSearch.val().length === 0){
			selectTag.val("email");			
		}
		
		goToPage(1, inputSearch.val(), selectTag.val());
	});
});

function goToPage(requestPage, requestSearch = RequestSearch, requestTag = RequestTag){
	let form = document.createElement("form");
    form.setAttribute("charset", "UTF-8");
    form.setAttribute("method", "Get");
    form.setAttribute("action", "/DoShop/Admin/AdminManagement/");
	let hidden1 = document.createElement("input");
	hidden1.setAttribute("name", "requestPage");
	if(typeof requestPage === "string"){
		hidden1.setAttribute("value", parseInt(requestPage));
	}else{
		hidden1.setAttribute("value", requestPage);
	}
	form.appendChild(hidden1);
	let hidden2 = document.createElement("input");
	hidden2.setAttribute("name", "requestSearch");
	hidden2.setAttribute("value", requestSearch);
	form.appendChild(hidden2);
	let hidden3 = document.createElement("input");
	hidden3.setAttribute("name", "requestTag");
	hidden3.setAttribute("value", requestTag);
	form.appendChild(hidden3);
	document.body.appendChild(form);
	form.submit();
}

function changeAuthorityTable(target, index){
	let currentAuthority = AdminList[index].authority;
	let selectAuthority = parseInt($(target).val());
	let i;
	if(currentAuthority === selectAuthority){
		$(target).parent().parent().css('background-color', 'rgba(255, 255, 255, 1)');
		for(i = 0; i < ChangeAuthorityTable.length; i++){
			if(ChangeAuthorityTable[i].email == AdminList[index].email){
				return;
			}
		}
		ChangeAuthorityTable.splice(i, 1);
	}else{
		$(target).parent().parent().css('background-color', 'rgba(220, 220, 220, 1)');
		ChangeAuthorityTable.push({
			email: AdminList[index].email,
			authority: selectAuthority
		});
	}
}

function clearChangeAuthorityTable(){
	ChangeAuthorityTable = [];
	$('tbody tr').css('background-color', 'rgba(255, 255, 255, 1)');
	let i = 0;
	$('tbody').children().each(function(){
		$(this).children().last().children().val(AdminList[i++].authority);
	});
}


function updateAuthority(method){
	let jsonData;
	if(method === "target"){
		jsonData = JSON.stringify(ChangeAuthorityTarget);
	}else if(method === "table"){
		jsonData = JSON.stringify(ChangeAuthorityTable);	
	}
	jQuery.ajaxSettings.traditional = true;
	
	$.ajax({
		type: "post",
		url: "/DoShop/Admin/AdminManagement/ChangeAuthority",
		data: {"jsonData" : jsonData},
		dataType: 'json',
		async: false
	}).done(function(){
		goToPage(RequestPage, RequestSearch, RequestTag);
	}).fail(function(){
		alert("서버 에러");
	});
}

function memberSearch(){
	ChildWindow = window.open("/DoShop/Admin/AdminManagement/PopUpMemberSearch", "회원 검색", "width=570, height=350, resizable = no");
}

function addTarget(){
	let inputEmail = $('#target_email');
	let selectAuthority = $('#select_target');
	let curretAuthority = $('#current_authority');
	
	if(selectAuthority.val() === "none"){
		alert("수정할 권한을 입력해주세요!");
		return;
	}
	
	for(let i = 0; i < ChangeAuthorityTarget.length; i++){
		if(inputEmail.text() === ChangeAuthorityTarget[i].email){
			alert('이미 변경 목록에 등록되어 있습니다.');
			return;
		}
	}
	
	ChangeAuthorityTarget.push({
		email: inputEmail.text(),
		authority: parseInt(selectAuthority.val())
	});
			
	let tag = '<div class="wrap_target">';
	tag += '<div class="ele1">';
	tag += '<span>' + inputEmail.text() + '</span>';
	tag += '<br>';
	tag += '<span>' + curretAuthority.text() +'</span>';
	tag += '<span>, </span>';
	tag += '<span>변경 권한: ' + selectAuthority.val() + '</span>';
	tag += '</div>';
	tag += '<button class="ele2" onclick="removeTarget(this)")>X</button>';
	tag += '</div>';
	
	$('#target_list').append(tag);
}

function resetTarget(){
	ChangeAuthorityTarget = [];
	$('#target_list').empty();
}

function removeTarget(target){
	let inputEmail = $(target).prev().children().first().text();
	let i = 0;
	for(i = 0; i < ChangeAuthorityTarget.length; i++){
		if(inputEmail === ChangeAuthorityTarget[i].email){
			break;
		}
	}
	ChangeAuthorityTable.splice(i, 1);
	$(target).parent().remove();
}