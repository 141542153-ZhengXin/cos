$(function() {
	getMsg();
	setInterval('getMsg()',60000);
	GetMessage();	
});

function getMsg(){
	$.ajax({
		type : "POST",
		url : "getMsg",
		data : {
			id : $("#userId").val()
		},
		dataType : "json",
		success : function(data) {
			if(data!=null){
				$("#msgDiv div").remove();
				$.each(data, function(index, item) {
					if(item.substr(0, 2)=="s&"){
						$("#msgDiv").append("<div class='media well'>"+
								"<div class='media-left'>"+
								"<a href='#'><img class='media-object wh64' src='/cos/static/images/man.png' alt='boy'></a></div>"+
								"<div class='media-body'><h4 class='media-heading'>我</h4>"+item.substr(2, item.length)+"</div></div>");
					}else{
						$("#msgDiv").append("<div class='media well'>"+
								"<div class='media-body  text-right'>"+
								"<h4 class='media-heading'>管理员</h4>"+item.substr(2, item.length)+"</div>"+
								"<div class='media-right'>"+
								"<a href='#'><img class='media-object wh64' src='/cos/static/images/woman.png' alt='girl'></a></div></div>");
					}
				});
			}
		}		
	});
}

function sendMsg(){
	$.ajax({
		type : "POST",
		url : "sendMsg",
		data : {
			id : $("#userId").val(),
			receiveMsg : $("#text").val()
		},
		dataType : "json",
		success : function(data) {
			if(data[0].msg == "success"){
				toastr.success('发送成功');
				getMsg();
			}
			else{
				toastr.error('发送失败');
			}
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
				/*$('[data-toggle="popover"]').popover('show');*/
				/*toastr.info('您有一条面试消息，点击左上角（学生）即可查看');*/
			}
			else{
				$('[data-toggle="popover"]').popover({
					trigger : manual
				});	
				$('[data-toggle="popover"]').popover('hide');
			}
		}
	});
}