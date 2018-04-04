<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>校园组织管理系统</title>
<%
	pageContext.setAttribute("PATH", request.getContextPath());
%>
<script type="text/javascript" src="${PATH }/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${PATH }/static/js/jquery.cookie.js"></script>
<link href="${PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<link href="${PATH }/static/css/login.css" rel="stylesheet">
<script type="text/javascript" src="${PATH }/static/js/login.js"></script>
</head>
<body style="background-image: url('${PATH }/static/images/background.jpg');">
	<div class="container">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="signin-div">
					<form class="form-signin" action="user/login" method="post">
						<h2 class="form-signin-heading" style="color: white; font-family: '楷体'">校园组织管理系统</h2>
						<div>
							<input id="userId" name="userId" type="text" class="form-control" placeholder="Username" />
							<span class="help-block"></span>
						</div>
						<div>
							<input id="passWord" name="passWord" type="password" class="form-control" placeholder="Password" />
							<span class="help-block"></span>
						</div>
						<div class="has-error">
							<span id="message" class="help-block">
						</div>
						<div class="checkbox">
							<label style="color: white;">
								<input id="rmbUser" type="checkbox" />
								记住用户名和密码
							</label>
						</div>
						<button class="btn btn-lg btn-primary btn-block" type="submit" onclick="return validate()">登 陆</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>