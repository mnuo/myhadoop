<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>管理</title>
</head>
<body>
	<div class="list">
		<h1 class="list-title">员工信息管理</h1>
		<table class="list-table">
			<thead>
				<tr>
				    <th></th>
					<th>ID</th>
					<th>NAME</th>
					<th>COMMENT</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${claslist }" varStatus="status">
					<tr>
						<td class="list-table-seq">${status.count }<input
								value="${item.id }" type="hidden" />
						</td>
						<td>${item.id }</td>
						<td>${item.name }</td>
						<td>${item.comment }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>