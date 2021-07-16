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
    <link rel="stylesheet" type="text/css" href="/DoShop/css/Admin/CategoryManagement.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>관리자 관리</title>
</head>
<body>
    <div id="wallpaper"></div>
    <div id="pannel"></div>
    <div id="playground">
        <header>
            <%@ include file="/WEB-INF/view/Admin/TopNavi.jsp" %>
        </header>
        <main>
        	<%@ include file="/WEB-INF/view/Admin/Menu.jsp" %>
            <section id="workspace" style="overflow: auto">
            	<h1 class="title">카테고리 관리</h1>
            	<hr>
            	<h2 class="message"> * 하위 카테고리 설정은 상위 카테고리 [적용] 후에 설정 가능합니다.</h2>
				<div class="category_management">
					<div id="primary_category" class="category_order">
						<div class="category_title">
							<div class="category_title_name">1차 카테고리</div>
							<button onclick="buttonCategoryReset('primary')" class="category_title_option">초기화</button>
							<button onclick="buttonCategoryAdd('primary')" class="category_title_option">추가</button>
							<button onclick="buttonCategorySubmit('primary')" class="category_title_option">적용</button>
						</div>
						<div id="primary_category_list" class="category_list">
						</div>
					</div>
					
					<div id="secondary_category" class="category_order">
						<div class="category_title">
							<div class="category_title_name">2차 카테고리</div>
							<button onclick="buttonCategoryReset('secondary')" class="category_title_option">초기화</button>
							<button onclick="buttonCategoryAdd('secondary')" class="category_title_option">추가</button>
							<button onclick="buttonCategorySubmit('secondary')" class="category_title_option">적용</button>
						</div>
						<div id="secondary_category_list" class="category_list">
						</div>
					</div>
					
					<div id="tertiary_category" class="category_order">
						<div class="category_title">
							<div class="category_title_name">3차 카테고리</div>
							<button onclick="buttonCategoryReset('tertiary')" class="category_title_option">초기화</button>
							<button onclick="buttonCategoryAdd('tertiary')" class="category_title_option">추가</button>
							<button onclick="buttonCategorySubmit('tertiary')" class="category_title_option">적용</button>
						</div>
						<div id="tertiary_category_list" class="category_list">
						</div>
					</div>
				</div>
            </section>
        </main>
        <%@ include file="/WEB-INF/view/Admin/Footer.jsp" %>
    </div>
    <script src="/DoShop/javascript/Admin/Menu.js"></script>
    <script src="/DoShop/javascript/Admin/CategoryManagement.js"></script>
</body>
</html>
