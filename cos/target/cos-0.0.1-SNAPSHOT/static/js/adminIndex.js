$(function() {
	ResumeStatistics();
	GetArticleTop5();
	GetMsgByAd();
	setInterval('GetMsgByAd()',60000);
	setInterval('GetArticleTop5()',60000);
	var sendId;
});

function ResumeStatistics() {
	var userId = $("#userId").val();
	$.ajax({
		type : "POST",
		url : "ResumeStatistics",
		data : {
			userId : userId
		},
		dataType : "json",
		success : function(data) {
			for (var i in data) {			
				$('tbody tr:eq(' + i + ') td:eq(0)').text(data[i][0]);
				$('tbody tr:eq(' + i + ') td:eq(1)').text(data[i][1]);
			}
		}
	});
}

function GetArticleTop5() {	
	var userId = $("#userId").val();
	$.ajax({
		type : "POST",
		url : "GetArticleTop5",
		dataType : "json",
		data : {
			adminId : userId
		},
		success : function(data) {
			if (data != null) {
				for(var i=0;i<5;i++){
					$('#article').find('li').remove();
				}
                $.each(data, function(index, item) {                 	
                	$('#article').append('<li class="list-group-item"><a tabindex="0" style="text-decoration: none;" title="'+item.title+'" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="right" data-html="true" data-content="'+item.content+'"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;'+item.title+'<label class="pull-right">热度&nbsp;&nbsp;<span class="badge">'+item.attention+'</span></label></a></li>');
                	$("[data-toggle='popover']").popover();
                });
            }	
		}
	});
}

function GetMsgByAd() {	
	$.ajax({
		type : "POST",
		url : "GetMsgByAd",
		dataType : "json",
		success : function(data) {
			if (data != null) {
				$("#msg a").remove();
                $.each(data, function(index, item) {
                	$("#msg").append('<a href="javascript:void(0);" class="list-group-item" type="button" data-toggle="modal" data-target="#myModal" onclick="HistoryMsgList('+item[0]+')">'+item[1]+':'+item[2].substr(2, item[2].length)+'</a>');
                });
            }	
		}
	});
}

function HistoryMsgList(id){
	sendId = id;
	$.ajax({
		type : "POST",
		url : "HistoryMsgList",
		data : {
			id : id
		},
		dataType : "json",
		success : function(data) {
			if (data != null) {
				$("#msgDiv div").remove();
				$.each(data, function(index, item) {
					if(item.substr(0, 2)=="s&"){
						$("#msgDiv").append("<div class='media well media-style'>"+
								"<div class='media-left'>"+
								"<a href='#'><img class='media-object wh45' src='/cos/static/images/man.png' alt='boy'></a></div>"+
								"<div class='media-body'><h5 class='media-heading'>同学</h5>"+item.substr(2, item.length)+"</div></div>");
					}else{
						$("#msgDiv").append("<div class='media well media-style'>"+
								"<div class='media-body  text-right'>"+
								"<h5 class='media-heading'>我</h5>"+item.substr(2, item.length)+"</div>"+
								"<div class='media-right'>"+
								"<a href='#'><img class='media-object wh45' src='/cos/static/images/woman.png' alt='girl'></a></div></div>");
					}
				});
            }	
		}
	});
}

function sendMsgByAd(){
	if($("#text").val()==""){
		alert("请选择对话框进行回复")
	}
	$.ajax({
		type : "POST",
		url : "sendMsgByAd",
		data : {
			id : sendId,
			receiveMsg : $("#text").val()
		},
		dataType : "json",
		success : function(data) {
			if(data[0].msg == "success"){
				toastr.success('发送成功');
				GetMsgByAd();
				$("#text").val("");
			}
			else{
				toastr.error('发送失败');
			}
		}
	});
}

