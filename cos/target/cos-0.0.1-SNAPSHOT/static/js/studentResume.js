$(function() {
	ResumeProcess(0);
	Validator();
	GetMessage();
	var rflag;
});

function isResume() {
	$.ajax({
		type : "POST",
		url : "isResume",
		data : {
			id : $("#userId").val()
		},
		dataType : "json",
		success : function(data) {
			if (data[0].msg == "success") {
				rflag = true;
			} else {
				rflag = false;
			}
		}
	});
}

function ResumeProcess(flag) {
	$.ajax({
		type : "POST",
		url : "ResumeUpdate",
		data : {
			id : $("#userId").val()
		},
		dataType : "json",
		success : function(data) {
			if (data != null) {
				$.each(data, function(index, item) {
					$("#input1").val(item.id);
					$("#input2").val(item.name);
					$("#input3").val(item.sex);
					$("#input4").val(item.area);
					$("#input5").val(item.departments);
					$("#input6").val(item.major);
					if (flag == 0) {
						$("#resume_1").removeClass("active");
						$("#resume_0").addClass("active");
						$("#input7,#input8,#input9,#input10").val("");
						$("#sendForm").data("bootstrapValidator").resetForm();
						isResume();
					} else if (flag == 1) {
						$("#resume_0").removeClass("active");
						$("#resume_1").addClass("active");
						$("#input7").val(item.telephone);
						$("#input8").val(item.organization);
						$("#input9").val(item.hobby);
						$("#input10").val(item.evaluation);
						$("#sendForm").data("bootstrapValidator").resetForm();
						rflag = true;
					}
				});
			}
		}
	});
}

function SendResume() {
	if (rflag == false) {
		toastr.warning('您已投递过简历');
		return false;
	}
	$("#sendForm").data('bootstrapValidator').validate();
	if ($("#sendForm").data("bootstrapValidator").isValid() == false) {
		$("#sendForm").data("bootstrapValidator").resetForm();
		return false;
	}
	$.ajax({
		type : "POST",
		url : "SendResume",
		data : {
			id : $("#userId").val(),
			telephone : $("#input7").val(),
			organization : $("#input8").val(),
			hobby : $("#input9").val(),
			evaluation : $("#input10").val()
		},
		dataType : "json",
		success : function(data) {
			if (data[0].msg == "success"){
				toastr.success('简历投递成功');
			}else{
				toastr.error('简历投递失败');
			}		
			ResumeProcess(1);
		}
	});
}

function Validator() {
	$('#sendForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			telephone : {
				message : '手机号验证失败',
				validators : {
					notEmpty : {
						message : '手机号不能为空'
					},
					regexp : {
						regexp : /^1[34578]\d{9}$/,
						message : '手机号码有误'
					}
				}
			},
			organization : {
				message : '投递组织验证失败',
				validators : {
					notEmpty : {
						message : '投递组织不能为空'
					}
				}
			},
			hobby : {
				message : '爱好验证失败',
				validators : {
					notEmpty : {
						message : '爱好不能为空'
					}
				}
			},
			evaluation : {
				message : '自我评价验证失败',
				validators : {
					notEmpty : {
						message : '自我评价不能为空'
					}
				}
			},
		}
	});
}

function GetMessage() {
	$.ajax({
		type : "POST",
		url : "GetMessage",
		data : {
			id : $("#userId").val()
		},
		dataType : "json",
		success : function(data) {
			if (data != null) {
				$('[data-toggle="popover"]').popover({
					content : data[0].resumeMsg,
					animation : true
				});
				/* $('[data-toggle="popover"]').popover('show'); */
				/* toastr.info('您有一条面试消息，点击左上角（学生）即可查看'); */
			} else {
				$('[data-toggle="popover"]').popover({
					trigger : manual
				});
				$('[data-toggle="popover"]').popover('hide');
			}
		}
	});
}
