function openModal(){
	$.ajax({
		type : "POST",
		url : "getPassWord",
		data : {
			userId : $('#userId').val()
		},
		dataType : "json",
		success : function(data) {
			$('#inputPassword1').val(data[0].passWord);
		}
	});	
	
	$('#inputPassword2').parent().removeClass('has-error');
	$("#inputPassword2").next("span").text("");
	$("#inputPassword2").val("");
}

function UpdatePassWord(){
	if($('#inputPassword1').val()==$('#inputPassword2').val()){
		$('#inputPassword2').parent().addClass('has-error');
		$("#inputPassword2").next("span").text("修改的密码与原密码一致");
	}
	else{
		$.ajax({
			type : "POST",
			url : "updatePassWord",
			data : $('#passwordform').serialize(),
			dataType : "json",
			success : function(data) {
				if(data[0].msg=='success'){
					toastr.success('密码修改成功');
					$.cookie("passWord", $("#inputPassword2").val(), {
						expires : 7
					});
					$('#passWordModal').modal('hide');
				}
				else{
					toastr.error('密码修改失败');
				}
			}
		});
	}
}

