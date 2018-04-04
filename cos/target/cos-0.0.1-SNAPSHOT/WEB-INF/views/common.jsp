<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<%
	pageContext.setAttribute("PATH", request.getContextPath());
%>
<script type="text/javascript" src="${PATH }/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${PATH }/static/js/jquery.cookie.js"></script>
<link href="${PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${PATH }/static/css/bootstrap-cos.css" rel="stylesheet">
<script src="${PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${PATH }/static/js/common.js"></script>
<link href="${PATH }/static/css/toastr.css" rel="stylesheet" />
<script src="${PATH }/static/js/toastr.min.js"></script>
<link href="${PATH }/static/css/buttons.css" rel="stylesheet" />
<link href="http://cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<script type="text/javascript">
	toastr.options.positionClass = 'toast-bottom-right';
</script>
</head>
<body>
	<div class="modal fade" id="passWordModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">密码修改</h4>
				</div>
				<div class="modal-body">
					<form id="passwordform" class="form-horizontal">
						<div class="form-group">
							<label for="input1" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-10">
								<input type="hidden" class="form-control" id="inputUserId" placeholder="userId" name="userId" value="${sessionScope.userId}">
								<input type="text" class="form-control" id="inputUserName" placeholder="userName" name="userName" value="${sessionScope.userName}"
									readonly="readonly">
							</div>
							<label for="inputPassword1" class="col-sm-2 control-label">原密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="inputPassword1" placeholder="Password">
							</div>
							<label for="inputPassword2" class="col-sm-2 control-label">新密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="inputPassword2" name="passWord" placeholder="Password">
								<span class="help-block"></span>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="UpdatePassWord()">修改</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header"></div>
				<div class="modal-body">
					<h4></h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>