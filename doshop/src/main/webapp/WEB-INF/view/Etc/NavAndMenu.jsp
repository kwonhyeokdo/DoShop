<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/DoShop/css/Etc/NavAndMenu.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<header class="nav_and_menu">
	<nav>
       	<div class="nav_wrap">
        	<button onclick="openPrimaryMenu()" id="button_menu" class="button_menu"></button>
            <a class="logo" href="/DoShop">DoShop</a>
       	</div>
       	<div class="nav_wrap">
       	</div>
       	<div id="sign" class="nav_wrap">
       	</div>
	</nav>
	
	<div id="primary_menu" class="primary_menu">
	  	<div class="primary_menu_header">
	  		<h2 class="menu_title">카테고리</h2>
	  		<button onclick="closeMenu()" class="menu_close">X</button>
	  	</div>
	   	<ul id="primary_menu_list" class="primary_menu_list">
	   	</ul>
	</div>
	<div id="secondary_menu" class="secondary_menu">
		<ul id="secondary_menu_list" class="secondary_menu_list">
		</ul>
	</div>
	<div id="tertiary_menu" class="tertiary_menu">
		<ul id="tertiary_menu_list" class="tertiary_menu_list">
		</ul>
	</div>
	<div id="quaternary_menu" class="quaternary_menu">
		<ul id="quaternary_menu_list" class="quaternary_menu_list">
		</ul>
	</div>
  	
  	<script src="/DoShop/javascript/Etc/NavAndMenu.js"></script>
	<div class="space"></div>
</header>
</html>