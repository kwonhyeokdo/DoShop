<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<body>
	<nav id="top_navi">
		<a href="/DoShop/Admin/" id="admin_home_logo">DoAdmin</a>
		<a id="signout" href="/DoShop/Admin/Signout">로그아웃</a>
		<span id="admin_info">${signinSession.name}[${signinSession.authority}]</span>
		<div class="clear"></div>
       </nav>
</body>