<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/Home.css">
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/Menu.css">
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/Footer.css">
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/TopNavi.css">
    <link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/AdminManagement.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>관리자 관리</title>
</head>
<body>
	<script>
		var RequestPage = "${requestPage}";
		<c:choose>
			<c:when test="${empty requestSearch}">
				var RequestSearch = "";
			</c:when>
			<c:otherwise>
				var RequestSearch = "${requestSearch}";
			</c:otherwise>
		</c:choose>
		var RequestTag = "${requestTag}";
		var LastPage = parseInt("${lastPage}");
		var PageSection = parseInt("${pageSection}");
		var AdminList = [];
		<c:forEach items="${adminList}" var="adminVO">
			AdminList.push({
				ranking: parseInt("${adminVO.ranking}"),
				email: "${adminVO.email}",
				authority: parseInt("${adminVO.authority}"),
			});
		</c:forEach>
	</script>
    <div id="wallpaper"></div>
    <div id="pannel"></div>
    <div id="playground">
        <header>
            <%@ include file="/WEB-INF/view/Admin/TopNavi.jsp" %>
        </header>
        <main>
        	<%@ include file="/WEB-INF/view/Admin/Menu.jsp" %>
            <section id="workspace" style="overflow: auto">
            	<h1 class="title">관리자 관리</h1>
            	<fieldset>
            		<legend>관리자 추가/변경</legend>
	            	<button class="fieldset_button_frame" onclick="memberSearch()">사용자 검색</button>
            		<button onclick="updateAuthority('target')" class="fieldset_button_frame right">적용하기</button>
            		<button onclick="resetTarget()" class="fieldset_button_frame right">초기화</button>
            		<div id="wrap_management">
	            		<label id="target_email">사용자 검색을 해주세요</label>
	            		<label id="current_authority">현재권한: </label>
	            		<label>수정할 권한</label>
	            		<select id="select_target" disabled>
	            			<option value="none">선택</option>
	            			<option value="0">0(일반회원)</option>
	            			<option value="1">1</option>
	            			<option value="2">2</option>
	            			<option value="3">3</option>
	            		</select>
	            		<button class="fieldset_button_frame" onclick="addTarget()" id="button_add_target" disabled>추가하기</button>
            		</div>
            		<hr>
            		<div id="target_list">
            		</div>
            	</fieldset>
            	<div class="wrap">
	            	<div class="wrap_button">
				        <button id="button_rollback" class="button_frame" onclick="clearChangeAuthority()">되돌리기</button>
				        <button id="button_apply" class="button_frame" onclick="updateAuthority('table')">적용하기</button>
			        </div>
			        <table>
			            <thead>
			                <tr>
			                    <td>순번</td>
			                    <td>이메일</td>
			                    <td>권한</td>
			                </tr>
			            </thead>
			            <tbody>
			            </tbody>
			        </table>
			        <h2 id="message_table">표시할 목록이 없습니다.</h2>
			        <div class="wrap_paging_search">
				        <span class="wrap_paging">
				        </span>
				        <span class="wrap_search">
				        	<select id="select_tag">
				        	</select>
				        	<input id="input_search" type="text">
				        	<button id="button_search"></button>
			        	</span>
			        </div>
            	</div>
            </section>
        </main>
        <%@ include file="/WEB-INF/view/Admin/Footer.jsp" %>
    </div>
    <script src="/DoShop/javascript/Admin/Menu.js"></script>
    <script src="/DoShop/javascript/Admin/AdminManagement.js"></script>
</body>
</html>
