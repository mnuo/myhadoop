<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
	<title>这是首页</title>
</head>
<body>
<%@include file="header.jsp" %>
<!-- sec:authentication property="name" 是springSecurity自定义标签库中获取登录者信息 -->
<h2>Hello <sec:authentication property="name"/>!这是首页了!</h2>
<!-- 有选择的显示连接 -->
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<a href="admin.jsp">进入admin.jsp</a>
</sec:authorize>
</body>
</html>
