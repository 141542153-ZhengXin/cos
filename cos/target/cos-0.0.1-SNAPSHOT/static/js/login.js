$(document).ready(function() {
	if ($.cookie("rmbUser") == "true") {
		$("#rmbUser").attr("checked", true);
		$("#userId").val($.cookie("userId"));
		$("#passWord").val($.cookie("passWord"));
	}
});

function saveUserInfo() {
	if ($("#rmbUser").is(':checked') == true) {
		var userId = $("#userId").val();
		var passWord = $("#passWord").val();
		$.cookie("rmbUser", "true", {
			expires : 7
		}); // 存储一个带7天期限的 cookie
		$.cookie("userId", userId, {
			expires : 7
		}); // 存储一个带7天期限的 cookie
		$.cookie("passWord", passWord, {
			expires : 7
		}); // 存储一个带7天期限的 cookie
	} else {
		$.cookie("rmbUser", "false", {
			expires : -1
		}); // 删除 cookie
		$.cookie("userId", '', {
			expires : -1
		});
		$.cookie("passWord", '', {
			expires : -1
		});
	}
}

function validate() {
	var flag = true;
	$("input:not(#rmbUser)").parent().removeClass("has-error");
	$("input:not(#rmbUser)").next("span").text("");
	if ($.trim($("#userId").val()) == "") {
		$("#userId").parent().addClass("has-error");
		$("#userId").next("span").text("用户名不能为空");
		flag = false;
	}
	if ($.trim($("#passWord").val()) == "") {
		$("#passWord").parent().addClass("has-error");
		$("#passWord").next("span").text("密码不能为空");
		flag = false;
	}
	if (flag) {
		saveUserInfo();
	}
	return flag;
}