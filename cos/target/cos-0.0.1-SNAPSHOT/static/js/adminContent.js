$(function() {
	GetArticle();
	RadioEvent();
});

function GetArticle() {	
	$(".carousel").carousel({  
        interval:10000  
    })
	$.ajax({
		type : "POST",
		url : "GetArticle",
		dataType : "json",
		success : function(data) {
			if (data != null) {
				$('.carousel-inner div').remove();
                $.each(data, function(index, item) {
                	var i = index%6;
                	if(index == 0){
                		$('.carousel-inner').append('<div class="item active"><img src="/cos/static/images/articleBg'+i+'.jpg"><div class="carousel-caption"><h3>'+item.title+'</h3><h4>'+item.content+'</h4><button type="button" class="btn btn-default button button-large button-plain button-border button-box" onclick="deleteEvent('+item.id+')">删除</button>&nbsp;&nbsp;<button type="button" class="btn btn-default button button-large button-plain button-border button-box" data-toggle="modal" data-target="#updateModal" onclick="updateData('+item.id+')">修改</button></div></div>'); 
                	}
                	else{          
                		$('.carousel-inner').append('<div class="item"><img src="/cos/static/images/articleBg'+i+'.jpg"><div class="carousel-caption"><h3>'+item.title+'</h3><h4>'+item.content+'</h4><button type="button" class="btn btn-default button button-large button-plain button-border button-box" onclick="deleteEvent('+item.id+')">删除</button>&nbsp;&nbsp;<button type="button" class="btn btn-default button button-large button-plain button-border button-box" data-toggle="modal" data-target="#updateModal" onclick="updateData('+item.id+')">修改</button></div></div>');  	
                	}
                	
                });
            }	
		}
	});
}

function RadioEvent() {
    $("#optionsRadios1").click(function() {
        $('#msgArea').attr("disabled", false);
    });
    $("#optionsRadios2").click(function() {
        $("#msgArea").val("");
        $('#msgArea').attr("disabled", true);
    });
}

function addEvent(){	
	$.ajax({
		type : "POST",
		url : "AddArticle",
		data : $('#addForm').serialize(),
		dataType : "json",
		success : function(data){
			if(data[0].msg == "success"){
				toastr.success('文章添加成功');
				GetArticle();
				$("#addInput").val("");
				$("#addTextarea").val("");
			}
			else{
				toastr.error('文章添加失败');
			}	
			
		}
	});
}

function deleteEvent(id){
	$.ajax({
		type : "POST",
		url : "DeleteArticle",
		dataType : "json",
		data : {
			id : id,
			adminId : $('#userId').val()
		},
		success : function(data){
			if(data[0].msg == "success"){
				toastr.success('文章删除成功');
				GetArticle();
			}
			else{
				toastr.error('文章删除失败');
			}		
		}
	});
}

function updateData(id){
	$.ajax({
		type : "POST",
		url : "UpdateData",
		data : {
			id : id
		},
		dataType : "json",
		success : function(data){
			$('#id').val(data[0].id);
			$('#updateInput').val(data[0].title);
			$('#updateTextarea').val(data[0].content);
		}
	});
	
}

function updateEvent(){
	$.ajax({
		type : "POST",
		url : "UpdateArticle",
		data : $('#updateForm').serialize(),
		dataType : "json",
		success : function(data){
			if(data[0].msg == "success"){
				toastr.success('文章修改成功');
				GetArticle();
			}
			else{
				toastr.error('文章修改失败');
			}	
		}
	});
}