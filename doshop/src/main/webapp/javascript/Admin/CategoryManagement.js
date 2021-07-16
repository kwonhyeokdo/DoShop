var DragTarget;
var IsDragging = false;
var IsDragOvering = false;
var DragParent;
var IsWorking = false;
var SecondaryCategoryParent = '0';
var TertiaryCategoryParent = '0';
var TmpCateogryName;
var TmpCateogryUrl;
jQuery.ajaxSettings.traditional = true;

$(document).ready(function(){
	(function load(){
		printCategory('#primary_category_list', getServerCategory(1));
	})()
	
	$(document).on(
		"drag", ".category_box", function(){
			if(!IsDragging){
				DragParent = $(this).parent().attr('id');
				DragTarget = $(this);
				$('#' + DragParent + ' .category_box').css('border', 'solid 1px rgb(50, 50, 200)');
				$('#' + DragParent + ' .category_separator_padding').css('height', '10px');
				$('#' + DragParent + ' .category_separator').css('height', '1px');
				$('#' + DragParent + ' .category_separator').css('background-color', 'black');
				console.log('drag');
				IsDragging = true;
			}
		}
	).on(
		"dragend", ".category_box", function(){
			if(IsDragging){
				$(this).css('border', 'solid 1px rgb(0, 0, 0)');
				$('.category_separator_padding').css('height', '0px');
				$('.category_separator').css('height', '0px');
				$('.category_separator_wrap').css('border', 'none');
				console.log('end');
			}
			IsDragging = false;
			IsDragOvering = false;
		}
	).on("dragover", ".category_separator_wrap", function(e){
			if(!IsDragOvering && DragParent === $(this).parent().attr('id')){
				$(this).css('border', 'dashed 1px blue');
				$(this).children('.category_separator_padding').css('height', '25px');
				$(this).children('.category_separator').css('background-color', 'rgba(255, 255, 255, 1)');
				console.log('over');
				IsDragOvering = true;
			}
			e.preventDefault();  
		}
	).on("dragleave", ".category_separator_wrap", function(){
			if(IsDragging && DragParent === $(this).parent().attr('id')){
				$(this).css('border', 'none');
				$(this).children('.category_separator_padding').css('height', '5px');
				$(this).children('.category_separator').css('background-color', 'black');
				console.log('leave');
				IsDragOvering = false;
			}
		}
	).on("drop", ".category_separator_wrap", function(e){
			e.preventDefault();
			console.log('drop');
			if(DragParent === $(this).parent().attr('id')){
				IsDragging = false;
				IsDragOvering = false;
				let separator = DragTarget.next();
				$(this).after(DragTarget);
				$(DragTarget).after(separator);
				$('.category_box').css('border', 'solid 1px rgb(0, 0, 0)');
				$('.category_separator_padding').css('height', '0px');
				$('.category_separator').css('height', '0px');
				$('.category_separator_wrap').css('border', 'none');
			}
		}
	);
});

function getServerCategory(parentId){
	let serverCategory;
	$.ajax({
		url: '/DoShop/Admin/CategoryManagement/GetCategoryList',
		method: 'post',
		data: {'parentId': parentId},
		async: false
	}).done(function(data){
		serverCategory = data;
	}).fail(function(){
		alert('서버 오류');
	});
	return serverCategory;
}

function createCategory(positionSelector, id = -1, name = '', url = ''){
	let tag = '<div class="category_box" draggable="true">';
	tag += '<input type="hidden" value="' + id + '">';
	tag += '<div class="category_wrap1">';
	tag += '<span class="category_name">' + name + '</span>';
	tag += '<span class="category_url">[' + url + ']</span>';
	tag += '</div>';
	tag += '<div class="category_wrap2">';
	tag += '<button onclick="buttonCategoryEdit(this)" class="category_option">수정</button>';
	tag += '<button onclick="buttonCategoryDelete(this)" class="category_option">삭제</button>';
	if(!(id === -1 || id === '0')){
		tag += '<button onclick="buttonToSubCategory(this)" class="category_option go_sub">></button>';
	}
	tag += '</div>';
	tag += '</div>';
	tag += '<div class="category_separator_wrap">';
	tag += '<div class="category_separator_padding"></div>';
	tag += '<hr class="category_separator">';
	tag += '<div class="category_separator_padding"></div>';
	tag += '</div>';
	
	
	if(typeof positionSelector === "string"){
		$(positionSelector).find('.category_separator_wrap').last().after(tag);
	}else{
		positionSelector.after(tag);
	}
	
	$('#tertiary_category').find('.go_sub').remove();
}

function createCategoryEdit(positionSelector, id = -1, name = '', url  = ''){
	let tag = '<div class="category_box" draggable="true">';
	tag += '<input type="hidden" value="' + id +'">';
	tag += '<div class="category_wrap1">';
	tag += '<input class="category_input" placeholder="카테고리명 입력" spellcheck="false" autocomplete="off" value="' + name + '">';
	tag += '<input class="category_input" placeholder="url명 입력" spellcheck="false" autocomplete="off" value="' + url + '">';
	tag += '</div>';
	tag += '<div class="category_wrap2">';
	tag += '<button onclick="buttonCategoryAddCancel(this)" class="category_option">취소</button>';
	tag += '<button onclick="buttonCategoryAddApply(this)" class="category_option">적용</button>';
	if(!(id === -1 || id === '0')){
		tag += '<button class="category_option">></button>';
	}
	tag += '</div>';
	tag += '</div>';
	tag += '<div class="category_separator_wrap">';	
	tag += '<div class="category_separator_padding"></div>';
	tag += '<hr class="category_separator">';
	tag += '<div class="category_separator_padding"></div>';
	tag += '</div>';
	
	if(typeof positionSelector === "string"){
		$(positionSelector).find('.category_separator_wrap').last().after(tag);
	}else{
		positionSelector.after(tag);
	}
	
	$('#tertiary_category').find('.go_sub').remove();
}

function printCategory(categoryListId, categoryList){
	$(categoryListId).empty();
	let tag = '<div class="category_separator_wrap">';
	tag += '<div class="category_separator_padding"></div>';
	tag += '<hr class="category_separator">';						
	tag += '<div class="category_separator_padding"></div>';
	tag += '</div>';
	$(categoryListId).append(tag);		
							
	categoryList.forEach(function(arr){
		let id = arr.selfId;
		let name = arr.name;
		let url = arr.url;
					
		createCategory(categoryListId, id, name, url);
	});
	
	switch(categoryListId){
		case '#primary_category_list':
			$(categoryListId).find('input[type="hidden"]').each(function(index, item){
				if($(item).val() === SecondaryCategoryParent){
					$(item).parent().css('background-color', 'rgba(180, 180, 180, 1)');
					return false;
				}
			});
			break;
		case '#secondary_category_list':
			$(categoryListId).find('input[type="hidden"]').each(function(index, item){
				if($(item).val() === TertiaryCategoryParent){
					$(item).parent().css('background-color', 'rgba(180, 180, 180, 1)');
					return false;
				}
			});
			break;
	}
}

function buttonCategoryAdd(targetLevel){
	if(!IsWorking){
		if(targetLevel === "primary"){
			createCategoryEdit('#primary_category_list');
		}else if(targetLevel === "secondary"){
			if(SecondaryCategoryParent === '0'){
				alert('1차 카테고리를 선택해주세요.');
				return;
			}
			createCategoryEdit('#secondary_category_list');
		}else if(targetLevel === "tertiary"){
			if(TertiaryCategoryParent === '0'){
				alert('2차 카테고리를 선택해주세요.');
				return;
			}
			createCategoryEdit('#tertiary_category_list');
		}
	}else{
		alert('앞선 작업을 완료해주세요!');
	}
	IsWorking = true;
	$('.category_input:nth-of-type(1)').focus();
}

function buttonCategoryReset(targetLevel){
	if(IsWorking){
		alert('앞선 작업을 완료해주세요!');
		$('.category_input:nth-of-type(1)').focus();
		return;
	}
	
	switch(targetLevel){
		case "primary":
			printCategory('#primary_category_list', getServerPrimaryCategory());
			break;
		case "secondary":
			printCategory('#secondary_category_list', getServerSecondaryCategory(SecondaryCategoryParent));
			break;
		case "tertiary":
			printCategory('#tertiary_category_list', getServerTertiaryCategory(TertiaryCategoryParent));
			break;
	}
}

function buttonCategorySubmit(targetLevel){
	if(IsWorking){
		alert('진행 중인 작업을 완료해주세요!');
		$('.category_input:nth-of-type(1)').focus();
		return;
	}
	
	let message = "변동 사항을 적용하시겠습니까?\n";
	message += "* 상위 카테고리 삭제시 하위 카테고리 내용이 삭제됩니다.";
	if(!confirm(message)){
		return;
	}
	
	let submit= {
		parentId:null,
		new: [],
		update: [],
		delete: [],
		sequence: [],
		print: function(){
			let str = "parentId: " + this.parentId + "\n";
			str += '[new]\n';
			this.new.forEach(function(item){
				str += "{" + item.name + ", " + item.url +"}\n";
			});
			str += '\n[update]\n';
			this.update.forEach(function(item){
				str += "{" + item.selfId + ", " + item.name + ", " + item.url +"}\n";
			});
			str += '\n[delete]\n';
			this.delete.forEach(function(item){
				str += "{" + item.selfId + "}\n";
			});
			str += '\n[sequence]\n';
			this.sequence.forEach(function(item){
				str += "{" + item.name + ", " + item.order +"}\n";
			});
			
			console.log(str);
		}
		
	};
	
	let category_list_id;
	switch(targetLevel){
		case "primary":
			category_list_id = '#primary_category_list';
			submit.parentId = 1;
			break;
		case "secondary":
			category_list_id = '#secondary_category_list';
			submit.parentId = parseInt(SecondaryCategoryParent);
			break;
		case "tertiary":
			category_list_id = '#tertiary_category_list';
			submit.parentId = parseInt(TertiaryCategoryParent);
			break;
	}
	
	let category_list = $(category_list_id).find('.category_box');
	let serverCategoryList = getServerCategory(submit.parentId);

	for(let i = 0; i < category_list.length; i++){
		let categoryId = parseInt($(category_list[i]).children('input[type="hidden"]').val());
		let categoryName = $(category_list[i]).find('span').eq(0).text();
		let categoryUrl = filterUrl($(category_list[i]).find('span').eq(1).text());
		
		submit.sequence.push({name: categoryName, order: i + 1});
		
		if(categoryId === 0){
			submit.new.push({name: categoryName, url: categoryUrl});
			continue;
		}
		
		for(let j = 0; j < serverCategoryList.length; j++){
			if(categoryId === serverCategoryList[j].selfId){
				if(serverCategoryList[j].name === categoryName &&
				   serverCategoryList[j].url === categoryUrl){
					break;
				}else if(serverCategoryList[j].name !== categoryName ||
						 serverCategoryList[j].url !== categoryUrl){
					submit.update.push({selfId: parseInt(categoryId), name: categoryName, url: categoryUrl});
					break;
				}
			}
		}
	}
	
	let haveNumber = [];
	for(let i = 0; i < serverCategoryList.length; i++){
		for(let j = 0; j < category_list.length; j++){
			let categoryId = parseInt($(category_list[j]).children('input[type="hidden"]').val());
			if(serverCategoryList[i].selfId === categoryId){
				haveNumber.push(i);
			}
		}
	}
	for(let i = 0; i < serverCategoryList.length; i++){
		let flag = true;
		for(let j = 0; j < haveNumber.length; j++){
			if(haveNumber[j] === i){
				flag = false;
				break;
			}
		}
		if(flag){
			submit.delete.push({selfId: serverCategoryList[i].selfId});
		}
	}
	
	submit.print();
		
	$.ajax({
		url: '/DoShop/Admin/CategoryManagement/UpdateCategory',
		method: 'post',
		data: {"updateCategoryList": JSON.stringify(submit)},
		async: false,
		dataType: "text"
	}).done(function(data){
		if(Boolean(data)){
			printCategory(category_list_id, getServerCategory(submit.parentId));
			alert('적용되었습니다!');
		}else{
			alert('서버 오류');
		}
	}).fail(function(){
		alert('서버 오류');
	});
	
}

function buttonCategoryAddCancel(target){
	let categoryBox = $(target).parent().parent();
	let categoryId = categoryBox.children('input[type="hidden"]').val();
	
	if(categoryId !== '-1'){
		createCategory(categoryBox, categoryId, TmpCateogryName, TmpCateogryUrl);
	}
	
	categoryBox.next().next().remove();
	categoryBox.remove();
	IsWorking = false;
}

function buttonCategoryAddApply(target){
	let categoryListId = "#" + $(target).parent().parent().parent().attr('id');
	let categoryName = $(target).parent().siblings('.category_wrap1').children().first().val();
	let categoryUrl = $(target).parent().siblings('.category_wrap1').children().last().val();
	let categoryBox = $(target).parent().parent();
	let categoryId = categoryBox.children('input[type="hidden"]').val();
	let nameCheck = true;
	let urlCheck = true;
	if(categoryName.length === 0 || categoryUrl.length === 0 ){
		alert('카테고리 이름 또는 URL을 입력해주세요!');
		$('.category_input:nth-of-type(1)').focus();
		return;
	}
	
	$(categoryListId).find('.category_name').each(function(){
		if(categoryName === $(this).text()){
			nameCheck = false;
			return false;
		}
	});
	$(categoryListId).find('.category_url').each(function(){
		if(categoryUrl === filterUrl($(this).text())){
			urlCheck = false;
			return false;
		}
	});
	
	if(!nameCheck || !urlCheck){
		alert('중복된 카테고리 이름 또는 중복된 URL입니다!');
		$('.category_input:nth-of-type(1)').focus();
		return;
	}
	
	if(categoryId === '-1'){
		categoryId = '0';
	}
	
	categoryBox.next().remove();
	createCategory(categoryBox, categoryId, categoryName, categoryUrl);
	categoryBox.remove();
	
	IsWorking = false;
}

function buttonCategoryEdit(target){
	if(!IsWorking){
		let categoryName = $(target).parent().siblings('.category_wrap1').children().first().text();
		let categoryUrl = filterUrl($(target).parent().siblings('.category_wrap1').children().last().text());
		let categoryBox = $(target).parent().parent();
		let categoryId = categoryBox.children('input[type="hidden"]').val();
		
		TmpCateogryName = categoryName;
		TmpCateogryUrl = categoryUrl;
		
		categoryBox.next().remove();
		createCategoryEdit(categoryBox, categoryId, categoryName, categoryUrl);
		categoryBox.remove();
		IsWorking = true;
	}else{
		alert('앞선 작업을 완료해주세요!');
	}
	$('.category_input:nth-of-type(1)').focus();
}

function buttonCategoryDelete(target){
	let categoryListId = $(target).parent().parent().parent().attr('id');
	let categoryId = $(target).parent().parent().find('input[type="hidden"]').val();
	let categoryName = $(target).parent().siblings('.category_wrap1').children().first().text();
	let categoryUrl = filterUrl($(target).parent().siblings('.category_wrap1').children().last().text());
	let message = categoryName + "[" + categoryUrl + "]을 삭제하시겠습니까?\n";
	message += "* 상위 카테고리 삭제시 하위 카테고리 내용이 삭제됩니다.";
	if(confirm(message)){
		let categoryBox = $(target).parent().parent();
		categoryBox.next().remove();
		categoryBox.remove();

		alert(SecondaryCategoryParent);
		switch(categoryListId){
			case 'primary_category_list':
				if(SecondaryCategoryParent === categoryId){
					printCategory("#secondary_category_list", []);
					printCategory("#tertiary_category_list", []);
				}
				break;
			case 'secondary_category_list':
				if(TertiaryCategoryParent === categoryId){
					printCategory("#tertiary_category_list", []);
				}
				break;
		}
	}
}

function buttonToSubCategory(target){
	if(IsWorking){
		alert('앞선 작업을 완료해주세요!');
		$('.category_input:nth-of-type(1)').focus();
		return;
	}
	
	let categoryBox = $(target).parent().parent();
	let categoryList = categoryBox.parent(); 
	
	categoryList.children('.category_box').css('background-color', 'rgba(230, 230, 230, 1)');
	categoryBox.css('background-color', 'rgba(180, 180, 180, 1)');
	
	let categoryListId = "#" + categoryList.attr('id');
	
	switch(categoryListId){
		case '#primary_category_list':
			SecondaryCategoryParent  = categoryBox.find("input[type='hidden']").val();
			printCategory("#secondary_category_list", getServerCategory(SecondaryCategoryParent));
			$("#tertiary_category_list").empty();
			TertiaryCategoryParent = '0';
			break;
		case '#secondary_category_list':
			TertiaryCategoryParent  = categoryBox.find("input[type='hidden']").val();
			printCategory("#tertiary_category_list", getServerCategory(TertiaryCategoryParent));	
			break;
	}	
}

function filterUrl(inputUrl){
	return url = inputUrl.substring(1, inputUrl.length - 1);
}