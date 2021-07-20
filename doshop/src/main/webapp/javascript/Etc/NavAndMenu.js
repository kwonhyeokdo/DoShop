jQuery.ajaxSettings.traditional = true;
var IsOpeningPrimaryMenu = false;
var IsOpeningSecondaryMenu = false;
var IsOpeningTertiaryMenu = false;
var PrimaryUrl;
var SecondaryUrl;
var SigninSession;
var IsOpeningMemberInfo = false;
const PRIMARY_CATEGORY_PARENT = 1;

$(document).ready(function(){
	if(checkSignin()){
		let tag = '<div id="member_wrap" class="member_wrap">';
		tag += '<div class="member_icon"></div>';
		tag += '<span class="member_name">' + SigninSession.name + '</span>';
		tag += '</div>';
		tag += '<div id="member_menu_list" class="member_menu_list">';
		tag += '<button class="member_menu">주문/배송</button>';
		tag += '<button class="member_menu">장바구니</button>';
		tag += '<button class="member_menu">회원정보</button>';
		tag += '<button onclick="signout()" class="member_menu">로그아웃</button>';
		tag += '</div>';
		$('.nav_and_menu #sign').append(tag);
	}else{
		$('.nav_and_menu #sign').append('<a class="not_signin" href="/DoShop/Member/Signin/">로그인</a>');
	}
	
	$(document).on(
		"mouseenter", ".nav_and_menu .primary_menu", function(){
			$(".nav_and_menu #secondary_menu").css("display", "none");
			$(".nav_and_menu #tertiary_menu").css("display", "none");
			$(".nav_and_menu #quaternary_menu").css("display", "none");
			IsOpeningSecondaryMenu = false;
			IsOpeningTertiaryMenu = false;
		}
	).on(
		"mouseenter", ".nav_and_menu .primary_menu .menu", function(){
			$(".nav_and_menu #secondary_menu").css("display", "block");
			$(".nav_and_menu #tertiary_menu").css("display", "none");
			let categoryParentId = parseInt($(this).children('input').val());
			IsOpeningSecondaryMenu = true;
			printCategory('.nav_and_menu #secondary_menu_list', getServerCategoryList(categoryParentId));
		}
	).on(
		"mouseenter", ".nav_and_menu .secondary_menu .menu", function(){
			$(".nav_and_menu #tertiary_menu").css("display", "block");
			$(".nav_and_menu #quaternary_menu").css("display", "block");
			let categoryParentId = parseInt($(this).children('input').val());
			printCategory('.nav_and_menu #tertiary_menu_list', getServerCategoryList(categoryParentId));
			IsOpeningTertiaryMenu = true;
		}
	).on(
		"mouseenter", ".nav_and_menu .quaternary_menu", function(){
			$(".nav_and_menu #secondary_menu").css("display", "none");	
			$(".nav_and_menu #tertiary_menu").css("display", "none");
			$(".nav_and_menu #quaternary_menu").css("display", "none");
			IsOpeningSecondaryMenu = false;
			IsOpeningTertiaryMenu = false;
		}
	).on(
		"mouseleave", ".nav_and_menu .tertiary_menu", function(){
			IsOpeningTertiaryMenu = false;
			$(".nav_and_menu #tertiary_menu").css("display", "none");
			$(".nav_and_menu #quaternary_menu").css("display", "none");
		}
	).on(
		"mouseleave", ".nav_and_menu .secondary_menu", function(){
			if(!IsOpeningTertiaryMenu){
				$(".nav_and_menu #secondary_menu").css("display", "none");	
			}
		}
	).on(
		"mouseenter", "nav", function(){
			if(IsOpeningSecondaryMenu){
				$(".nav_and_menu #secondary_menu").css("display", "none");
				$(".nav_and_menu #tertiary_menu").css("display", "none");
				$(".nav_and_menu #quaternary_menu").css("display", "none");
				IsOpeningSecondaryMenu = false;
				IsOpeningTertiaryMenu = false;	
			}
		}
	);
	
	$('#member_wrap').on({
		'click':function(){
			if(!IsOpeningMemberInfo){
				$(this).css('background-color', 'rgba(255, 255, 255, 0.2)');
				$('#member_menu_list').css('display', 'flex');
				IsOpeningMemberInfo = true;
			}else{
				$(this).css('background-color', 'rgba(255, 255, 255, 0)');
				$('#member_menu_list').css('display', 'none');
				IsOpeningMemberInfo = false;
			}
		}
	});
});

function getServerCategoryList(categoryParentId){
	let serverCategoryList;
	$.ajax({
		url: "/DoShop/Etc/GetServerCategory",
		method: 'post',
		data: {'categoryParentId': categoryParentId},
		async: false,
	}).done(function(data){
		serverCategoryList = data;
	}).fail(function(){
		alert('서버 오류');
	})
	return serverCategoryList;
}

function openPrimaryMenu(){
	if(!IsOpeningPrimaryMenu){
		$(".nav_and_menu #primary_menu").animate({
			width: "toggle"
			}, 100, "swing"
		);
		$(".nav_and_menu #secondary_menu").css("display", "none");
		$(".nav_and_menu #tertiary_menu").css("display", "none");
		$(".nav_and_menu #quaternary_menu").css("display", "none");
 		IsOpeningPrimaryMenu = true;
 		IsOpeningSecondaryMenu = false;
 		IsOpeningTertiaryMenu = false;
		printCategory('.nav_and_menu #primary_menu_list', getServerCategoryList(PRIMARY_CATEGORY_PARENT));
	}else{
		closeMenu();
	}
}

function printCategory(categoryListId, serverCategoryList){
	let categoryList = $(categoryListId);
	
	if(serverCategoryList.length === 0){
		if(categoryListId === '.nav_and_menu #secondary_menu_list'){
			$(".nav_and_menu #secondary_menu").css("display", "none");
			IsOpeningSecondaryMenu = false;
		}
		$('.nav_and_menu #tertiary_menu').css('display', 'none');
		$('.nav_and_menu #quaternary_menu').css('display', 'none');
		IsOpeningTertiaryMenu = false;
		return;
	}
	
	categoryList.empty();
	serverCategoryList.forEach(function(item){
		let url = "'" + item.url + "'";
		let	tag = '<li class="menu">';
		tag += '<button onclick="goToCategory(' + url  + ')" class="category">' + item.name + '</button>';
		tag += '<input type="hidden" value=' + item.selfId + '">';
		tag += '</li>';
		categoryList.append(tag);
	});
}

function closeMenu(){
	$(".nav_and_menu #primary_menu").animate({
		width: "toggle"
		}, 100, "swing"
	);
	$(".nav_and_menu #secondary_menu").css("display", "none");
	$(".nav_and_menu #tertiary_menu").css("display", "none");
	$(".nav_and_menu #quaternary_menu").css("display", "none");
	IsOpeningPrimaryMenu = false;
	IsOpeningSecondaryMenu = false;
	IsOpeningTertiaryMenu = false;
}

function goToCategory(url){
	window.location.href='/DoShop/Shopping' + url;
}

function checkSignin(){
	let isSignin;
	$.ajax({
		url: '/DoShop/Member/Signin/GetSigninSession',
		method: 'post',
		async: false
	}).done(function(data){
		if(data === ""){
			isSignin = false;			
		}else{
			SigninSession = data;
			isSignin = true;
		}
	}).fail(function(){
		alert('서버 오류');
	})
	
	return isSignin;
}

function signout(){
	let form = document.createElement("form");
    form.setAttribute("charset", "UTF-8");
    form.setAttribute("method", "Post");
    form.setAttribute("action", "/DoShop/Member/Signout/");

	document.body.appendChild(form);
	form.submit();
}