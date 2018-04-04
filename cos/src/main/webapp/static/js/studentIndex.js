$(function() {
	GetArticle(0);/*
	GetMessage();
	setInterval('GetMessage()',60000);*/
	setInterval('GetArticle(0)',60000);
});

function GetArticle(page) {	
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
                	if(index == page){
                		$('.carousel-inner').append('<div class="item active"><img src="/cos/static/images/articleBg'+i+'.jpg"><div class="carousel-caption"><h3>'+item.title+'</h3><h4>'+item.content+'</h4><button class="button button-glow button-circle button-action button-jumbo" onclick="AddAttention('+index+','+item.id+')"><i class="fa fa-thumbs-up"></i></button><h5>'+item.attention+'</h5></div></div>'); 
                	}
                	else{          
                		$('.carousel-inner').append('<div class="item"><img src="/cos/static/images/articleBg'+i+'.jpg"><div class="carousel-caption"><h3>'+item.title+'</h3><h4>'+item.content+'</h4><button class="button button-glow button-circle button-action button-jumbo" onclick="AddAttention('+index+','+item.id+')"><i class="fa fa-thumbs-up"></i></button><h5>'+item.attention+'</h5></div></div>');  	
                	}
                	
                });
            }	
		}
	});
}

function AddAttention(page, id) {
	$.ajax({
		type : "POST",
		url : "AddAttention",
		data : {
			id : id
		},
		success : function(data) {
			if (data[0].msg == "success")
				GetArticle(page);
		}
	});
}
