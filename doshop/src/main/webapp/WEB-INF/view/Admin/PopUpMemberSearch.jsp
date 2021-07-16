<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 검색</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
/* font-family: 'Noto Sans KR', sans-serif; */
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100&display=swap');
	*{
		padding: 0;
		margin: 0;
		box-sizing: border-box;
		font-family: 'Noto Sans KR', sans-serif;
	}
	body{
		padding: 15px;
	}
	#wrap_search{
		width: 300px;
		height: 25px;
		margin-top: 10px;
		display: flex;
	}
	#input_email{
		width: calc(100% - 25px);
		padding: 3px;
		outline: none;
		border-collapse: collapse;
	}
	#button_search{
		width: 25px;
		height: 25px;
	    background-image: url('/DoShop/image/search.png');
	    background-position: center;
	    background-repeat: no-repeat;
	    background-size: cover;
	    background-color: rgba(255, 255, 255, 0);
		outline: none;
		border: solid 1px rgba(200, 200, 200, 1);
		cursor: ponint;
	}
	table{
		display: none;
		width: 100%;
		margin-top: 10px;
		margin-bottom: 5px;
		border-spacing: 0;
		border-collapse: collapse;
	}
	thead tr td{
		padding: 8px 0;
		background-color: black;
		color: white;
		border: solid 1px black;
	}
	thead tr td:nth-of-type(1){
		width: 20%;
		text-align: center;
		border-right: solid 1px lightgray;
	}
	thead tr td:nth-of-type(2){
		width: 60%;
		text-align: center;
		border-right: solid 1px lightgray;
	}
	thead tr td:nth-of-type(3){
		width: 20%;
		text-align: center;
	}
	tbody tr{
		cursor: pointer;
	}
	tbody tr:hover{
		background-color: rgba(230, 230, 230, 1);
	}
	tbody tr td{
		border: solid 1px black;
		padding: 8px 0;
	}
	tbody tr td:nth-of-type(1){
		width: 20%;
		text-align: center;
	}
	tbody tr td:nth-of-type(2){
		width: 60%;
		text-align: center;
	}
	tbody tr td:nth-of-type(3){
		width: 20%;
		text-align: center;
	}
	
	#wrap_page{
		display: flex;
		justify-content: center;
	}
	
	#wrap_page button{
		margin: 0 4px;
		background-color: rgba(0, 0, 0 ,0);
		cursor: pointer;
		outline: none;
		border: none;
	}
	
	#wrap_page .current_page_button{
		border-bottom: solid 1px black;
	}
	
	#message_table{
		display: none;
		justify-content: center;
		align-items: center;
	}
</style>
</head>
<body>
	<h3>사용자 검색</h3>
	<div id="wrap_search">
		<input id="input_email" type="text" placeholder="사용자 이메일을 입력하세요">
		<button id="button_search"onclick="searchEmail()"></button>
	</div>
	<table>
		<thead>
			<tr>
				<td>순번</td>
				<td>이메일</td>
				<td>현재 권한</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<h2 id="message_table">검색 결과가 없습니다.</h2>
	<div id="wrap_page"></div>
	<script>
		var AdminList;
		
		$('#input_email').on("keyup", function(event) {
	    	if (event.keyCode === 13) {
			    event.preventDefault();
			    searchEmail();
		    }
		});
		
		function searchEmail(){
			let inputEmail = $('#input_email');
			$.ajax({
				type: "post",
				url: "/DoShop/Admin/AdminManagement/MemberSearch",
				data: {"inputEmail": inputEmail.val()},
				dataType: 'text',
				async: false
			}).done(function(data){
				AdminList = JSON.parse(data);
				goToPage(1);
			}).fail(function(){
				alert('서버 오류');
			});
		}
		function createTable(requestPage){
			let count = 0;
			$('table').css('display', 'table');
			for(let i = 0; i < 10; i++){
				if(AdminList.length <= (requestPage - 1) * 10 + i){
					break;
				}
				let tag = "<tr>";
				tag += "<td>"+AdminList[(requestPage - 1) * 10 + i].ranking+"</td>";
				tag += "<td>"+AdminList[(requestPage - 1) * 10 + i].email+"</td>";
				tag += "<td>"+AdminList[(requestPage - 1) * 10 + i].authority+"</td>";
				tag += "</tr>";
				$('tbody').append(tag);
				count++;
			}
			
			if(count === 0){
				$("#message_table").css('display', "flex");
				$("table").css('display', 'none');
			}else{
				$("#message_table").css('display', "none");
				$("table").css('display', 'table');
			}
		}
		
		function createPage(requestPage){
			let pageSection = getPageSection(requestPage);
			let lastPage = getLastPage(AdminList.length);
			let tag = "<button onclick='goToPage(1)'><<</button>";
			tag += "<button onclick='goToPage(" + ((pageSection - 9 <= 0) ? 1 : pageSection) +")'><</button>";
			for(let i = 1; i <= 10; i++){
				let page = pageSection + i;
				if(page === requestPage){
					tag += "<button class='current_page_button' onclick='goToPage(" + page + ")'>" + page + "</button>";	
				}else{
					tag += "<button onclick='goToPage(" + page + ")'>" + page + "</button>";
				}
				if(page >= lastPage){
					break;
				}
			}
			tag += "<button onclick='goToPage(" + (((pageSection + 11) > lastPage) ? lastPage : (pageSection + 11)) +")'>></button>";
			tag += "<button onclick='goToPage(" + lastPage +")'>>></button>";
			
			$('#wrap_page').append(tag);
		}
		
		function getPageSection(requestPage){
			if(requestPage <= 10){
				return 0;	
			} 
			
			requestPage = String(requestPage);
			let pageSection = "1";
			for(let i = 1; i < requestPage.length; i++){
				pageSection += "0";
			}
			return parseInt(pageSection);
		}
		
		function getLastPage(listSize){
			let lastPage = parseInt(listSize / 10);
			if(lastPage === 0){
				return 1;
			}
			
			return ((lastPage % 10) > 0) ? (lastPage += 1) : (lastPage = lastPage);
		}
		
		function goToPage(requestPage){
			if(typeof requestPage === "string"){
				requestPage = parseInt(requestPage);
			}
			
			$('tbody').empty();
			$('#wrap_page').empty();
			createTable(requestPage)
			createPage(requestPage);
		}
		
		$(document).on("click", "tbody tr",function(){
			let email = $(this).children().eq(1).text();
			let authority = $(this).children().eq(2).text();
			let parent = window.opener;
			$(opener.document).find('#target_email').text(email);
			$(opener.document).find('#select_target').val("none");
			$(opener.document).find('#select_target').attr('disabled', false);
			$(opener.document).find('#select_target').children().attr('disabled', false);
			$(opener.document).find('#select_target').children().css('background-color', 'rgb(255, 255, 255)');
			$(opener.document).find('#select_target').children().eq(parseInt(authority)+1).attr('disabled', true);
			$(opener.document).find('#select_target').children().eq(parseInt(authority)+1).css('background-color', 'rgb(235, 235, 235)');
			$(opener.document).find("#button_add_target").attr('disabled', false);
			$(opener.document).find('#current_authority').text("현재권한: " + authority);
			window.close();
		});
	</script>
</body>
</html>