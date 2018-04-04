$(function() {
	getAdmin();
	InitWebSocket();
	var sendId;
});

function getAdmin(){
	$.ajax({
		type : "POST",
		url : "getAdmin",
		dataType : "json",
		success : function(data) {
			if(data!=null){
				$("#studentmsg a").remove();
                $.each(data, function(index, item) {
                	$("#studentmsg").append('<a href="javascript:void(0);" class="list-group-item" type="button" data-toggle="modal" data-target="#studentModal" onclick="HistoryMsgList('+item.id+')">'+item.name+'('+item.organization+')</a>');
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
			id : id,
			mId : $("#userId").val(),
		},
		dataType : "json",
		success : function(data) {
			if (data != null) {
				$("#msgDiv2 div").remove();
				$.each(data, function(index, item) {
					if(item.substr(0, 2)=="s&"){
						$("#msgDiv2").append("<div class='media well media-style'>"+
								"<div class='media-body  text-right'>"+
								"<h5 class='media-heading'>我</h5>"+item.substr(2, item.length)+"</div>"+
								"<div class='media-right'>"+
								"<a href='#'><img class='media-object wh45' src='/cos/static/images/woman.png' alt='girl'></a></div></div>");
					}else{
						$("#msgDiv2").append("<div class='media well media-style'>"+
								"<div class='media-left'>"+
								"<a href='#'><img class='media-object wh45' src='/cos/static/images/man.png' alt='boy'></a></div>"+
								"<div class='media-body'><h5 class='media-heading'>管理员</h5>"+item.substr(2, item.length)+"</div></div>");
					}
				});
            }	
		}
	});
}

function InitWebSocket(){
    var websocket;
    if ('WebSocket' in window) {
		websocket = new WebSocket("ws://localhost:8080/cos/ws?userId="+$("#userId").val());
	} else if ('MozWebSocket' in window) {
		websocket = new MozWebSocket("ws://localhost:8080/cos/ws"+$("#userId").val());
	} else {
		websocket = new SockJS("http://localhost:8080/cos/ws/sockjs"+$("#userId").val());
	}
    websocket.onmessage = function(event) {
    	var data = JSON.parse(event.data);
    	$("#msgDiv2").find('div').eq(0).before("<div class='media well media-style'>"+
				"<div class='media-left'>"+
				"<a href='#'><img class='media-object wh45' src='/cos/static/images/man.png' alt='boy'></a></div>"+
				"<div class='media-body'><h5 class='media-heading'>管理员</h5>"+data.text+"</div></div>");
    } 
    
    websocket.onclose=function(e){ 
    	websocket.close(); 
		  //关闭TCP连接  
	}
    
    $("#sendMsg2").click(function(){
		if ($("#studenttext").val() == "") {
			toastr.warning("内容不能为空");
		}else{
			var data = {};
			data["from"] = $("#userId").val();
			data["to"] = sendId;
			data["text"] = $("#studenttext").val();
	    	if($("#msgDiv2 div").length > 0){
				$("#msgDiv2").find('div').eq(0).before("<div class='media well media-style'>"+
						"<div class='media-body  text-right'>"+
						"<h5 class='media-heading'>我</h5>"+data["text"]+"</div>"+
						"<div class='media-right'>"+
						"<a href='#'><img class='media-object wh45' src='/cos/static/images/woman.png' alt='girl'></a></div></div>");
	    	}
			else{
	    		$("#msgDiv2").append("<div class='media well media-style'>"+
	    				"<div class='media-body  text-right'>"+
						"<h5 class='media-heading'>我</h5>"+data["text"]+"</div>"+
						"<div class='media-right'>"+
						"<a href='#'><img class='media-object wh45' src='/cos/static/images/woman.png' alt='girl'></a></div></div>");
			}
			$('#studenttext').val("");
			websocket.send(JSON.stringify(data));
		}
	});
}