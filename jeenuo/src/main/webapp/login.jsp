<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body>
	 <h2>请输入您的用户名与密码</h2>  
    <form name='f' action='${pageContext.request.contextPath }/login' method='POST'>
        <table>
            <tr>
                <td>用户:</td>
                <td><input type='text' name='username' value=''></td>
            </tr>
            <tr>
                <td>密码:</td>
                <td><input type='password' name='password' /></td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit"
                    value="登录" /></td>
            </tr>
        </table>
    </form>
</body>
</html>