$(function() {
    ResumeProcessTable(0);
    AreaStatistics();
    MajorStatistics()
    RadioEvent();
});

function ResumeProcessTable(flag) {
    if (flag == 0) {
        $("#resume_1").removeClass("active");
        $("#resume_0").addClass("active");
    } else if(flag == 1) {
        $("#resume_0").removeClass("active");
        $("#resume_1").addClass("active");
    }
    var page = 1;
    $.ajax({
        type: "POST",
        url: "ResumeProcessTable",
        data: {
            page: page,
            flag: flag
        },
        dataType: "json",
        success: function(data) {
            $('tbody tr').remove();
            if (data != null) {
                $.each(data, function(index, item) {
                    if (index != data.length - 1 && index != data.length - 2) {
                        $('tbody').append('<tr><td>' + item.id + '</td>' + 
                        		'<td>' + item.name + '</td>' + 
                        		'<td>' + item.organization + '</td>' + 
                        		'<td><div class="dropdown">' + 
                        		'<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">操作'+
                        		'<span class="caret"></span></a>' + '<ul class="dropdown-menu">' + 
                        		'<li><a href="javascript:void(0);" onclick ="CheckResume(' + item.id + ')" id="check_a" data-toggle="modal" data-target="#checkModal">查看</a></li>' + 
                        		'<li><a href="javascript:void(0);" onclick ="WorkResume(' + item.id + ')" id="work_a" data-toggle="modal" data-target="#workModal">处理</a></li>' + 
                        		'</ul></div></td></tr>');
                        if (flag != 0) {
                            $('#work_a').remove();
                        }
                    }
                });
                var currentPage = data[data.length - 2];
                var pageSize = data[data.length - 1];
                var options = {
                    bootstrapMajorVersion: 3,
                    // 版本
                    currentPage: currentPage,
                    // 当前页数
                    totalPages: pageSize,
                    // 总页数
                    itemTexts: function(type, page, current) {
                        switch (type) {
                        case "first":
                            return "首页";
                        case "prev":
                            return "<<";
                        case "next":
                            return ">>";
                        case "last":
                            return "末页";
                        case "page":
                            return page;
                        }
                    },
                    onPageClicked: function(event, originalEvent, type, page) {
                        $.ajax({
                            url: "ResumeProcessTable",
                            type: "POST",
                            data: {
                                page: page,
                                flag: flag
                            },
                            success: function(data1) {
                            	$('tbody tr').remove();
                                if (data1 != null) {
                                    $.each(data1, function(index, item) {
                                        if (index != data1.length - 1 && index != data1.length - 2) {
                                        	$('tbody').append('<tr><td>' + item.id + '</td>' + 
                                            		'<td>' + item.name + '</td>' + 
                                            		'<td>' + item.organization + '</td>' + 
                                            		'<td><div class="dropdown">' + 
                                            		'<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">操作'+
                                            		'<span class="caret"></span></a>' + '<ul class="dropdown-menu">' + 
                                            		'<li><a href="javascript:void(0);" onclick ="CheckResume(' + item.id + ')" id="check_a" data-toggle="modal" data-target="#checkModal">查看</a></li>' + 
                                            		'<li><a href="javascript:void(0);" onclick ="WorkResume(' + item.id + ')" id="work_a" data-toggle="modal" data-target="#workModal">处理</a></li>' + 
                                            		'</ul></div></td></tr>');
                                            if (flag != 0) {
                                                $('#work_a').remove();
                                            }}
                                    });
                                }
                            }
                        });
                    }
                }
            }
            $('#page').bootstrapPaginator(options);
        }
    });
}

function modalEvent() {
    $("#check_a").click(function() {
        $("checkModal").modal({
            backdrop: 'static'
        });
    });

    $("#work_a").click(function() {
        $("workModal").modal({
            backdrop: 'static'
        });
    });
}

function CheckResume(sId) {
    $.ajax({
        type: "POST",
        url: "CheckResume",
        data: {
            sId: sId
        },
        dataType: "json",
        success: function(data) {
            $(".form-group p").eq(0).text(data.id); 
            $(".form-group p").eq(1).text(data.name);
            $(".form-group p").eq(2).text(data.sex == 0 ? "男" : "女");
            $(".form-group p").eq(3).text(data.departments + "-" + data.major);
            $(".form-group p").eq(4).text(data.telephone);
            $(".form-group p").eq(5).text(data.organization);
            $(".form-group p").eq(6).text(data.hobby);
            $(".form-group p").eq(7).text(data.evaluation);
        }
    });
}

function WorkResume(sId) {
	$("#msgArea").val("");
    $("#msgButton").val(sId);
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

function MsgModelProcess() {
    $.ajax({
        type: "POST",
        url: "MsgModelProcess",
        data: {
            sId: $("#msgButton").val(),
            isPass: $('input:radio[name="optionsRadios"]:checked').val()
        },
        dataType: "text",
        success: function(data) {
            $("#msgArea").val(data);
        }
    });
}

function ResumeProcess() {
    $.ajax({
        type: "POST",
        url: "ResumeProcess",
        data: {
            sId: $("#msgButton").val(),
            isPass: $('input:radio[name="optionsRadios"]:checked').val(),
            msg : $("#msgArea").val()
        },
        success: function(data) {
        	if(data[0].msg == "success"){
				toastr.success('发送成功');
				ResumeProcessTable(0);
			}
			else{
				toastr.error('发送失败');
			}	
        }
    });
}

function ResumeProcessTableById(){
	var page = 1;
	var flag;
    $.ajax({
        type: "POST",
        url: "ResumeProcessTableById",
        data: {
            page: page,
            sId: $("#search").val()
        },
        dataType: "json",
        success: function(data) {
            $('tbody tr').remove();
            if (data != null) {
                $.each(data, function(index, item) {
                    if (index != data.length - 1 && index != data.length - 2) {
                        $('tbody').append('<tr><td>' + item.id + '</td>' + '<td>' + item.name + '</td>' + '<td>' + item.organization + '</td>' + '<td><div class="dropdown">' + '<a href="#" class="dropdown-toggle"' + 'data-toggle="dropdown" role="button" aria-haspopup="true"' + 'aria-expanded="false">操作<span class="caret"></span></a>' + '<ul class="dropdown-menu">' + '<li><a href="javascript:void(0);" onclick ="CheckResume(' + item.id + ')" id="check_a" data-toggle="modal" data-target="#checkModal">查看</a></li>' + '<li><a href="javascript:void(0);" onclick ="WorkResume(' + item.id + ')" id="work_a" data-toggle="modal" data-target="#workModal">处理</a></li>' + '</ul></div></td></tr>');
                        flag=item.isPass;
                        if (flag == 0) {
                            $("#resume_1").removeClass("active");
                            $("#resume_0").addClass("active");
                        } else if(flag == 1) {
                            $("#resume_0").removeClass("active");
                            $("#resume_1").addClass("active");
                        }
                    }
                });
                var currentPage = data[data.length - 2];
                var pageSize = data[data.length - 1];
                var options = {
                    bootstrapMajorVersion: 3,
                    // 版本
                    currentPage: currentPage,
                    // 当前页数
                    totalPages: pageSize,
                    // 总页数
                    itemTexts: function(type, page, current) {
                        switch (type) {
                        case "first":
                            return "首页";
                        case "prev":
                            return "<<";
                        case "next":
                            return ">>";
                        case "last":
                            return "末页";
                        case "page":
                            return page;
                        }
                    },
                    onPageClicked: function(event, originalEvent, type, page) {
                        $.ajax({
                            url: "ResumeProcessTableById",
                            type: "POST",
                            data: {
                                page: page,
                                sId: sId
                            },
                            success: function(data1) {
                                $('tbody tr').remove();
                                if (data1 != null) {
                                    $.each(data1, function(index, item) {
                                        if (index != data1.length - 1 && index != data1.length - 2) {
                                        	$('tbody').append('<tr><td>' + item.id + '</td>' + 
                                            		'<td>' + item.name + '</td>' + 
                                            		'<td>' + item.organization + '</td>' + 
                                            		'<td><div class="dropdown">' + 
                                            		'<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">操作'+
                                            		'<span class="caret"></span></a>' + '<ul class="dropdown-menu">' + 
                                            		'<li><a href="javascript:void(0);" onclick ="CheckResume(' + item.id + ')" id="check_a" data-toggle="modal" data-target="#checkModal">查看</a></li>' + 
                                            		'<li><a href="javascript:void(0);" onclick ="WorkResume(' + item.id + ')" id="work_a" data-toggle="modal" data-target="#workModal">处理</a></li>' + 
                                            		'</ul></div></td></tr>');                                          
                                            flag=item.isPass;
                                            if (flag == 0) {
                                                $("#resume_1").removeClass("active");
                                                $("#resume_0").addClass("active");
                                            } else if(flag == 1){
                                                $("#resume_0").removeClass("active");
                                                $("#resume_1").addClass("active");
                                                $('#work_a').remove();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }if (flag != 0) {
                $('tbody tr ul li').eq(1).remove();
            }
            $('#page').bootstrapPaginator(options);
        }
    });
}

function AreaStatistics(){
	var ctx = document.getElementById("barChart");
	var myChart = new Chart(ctx, {
	    type: 'bar',
	    data: { 	
	        labels: [],
	        datasets: [{
	        	label: '地区',
	            data: [],
	            backgroundColor: [
	                'rgba(255, 99, 132, 0.2)',
	                'rgba(54, 162, 235, 0.2)',
	                'rgba(255, 206, 86, 0.2)',
	                'rgba(75, 192, 192, 0.2)',
	                'rgba(153, 102, 255, 0.2)',
	                'rgba(255, 159, 64, 0.2)'
	            ],
	            borderColor: [
	                'rgba(255,99,132,1)',
	                'rgba(54, 162, 235, 1)',
	                'rgba(255, 206, 86, 1)',
	                'rgba(75, 192, 192, 1)',
	                'rgba(153, 102, 255, 1)',
	                'rgba(255, 159, 64, 1)'
	            ],
	            borderWidth: 1
	        }]
	    }
	});
	
	$.ajax({
        type: "POST",
        url: "AreaStatistics",
        dataType: "json",
        success: function(data) {
        	if (data != null) {
	        	$.each(data, function(index, item) {
	        		myChart.data.labels[index] = item.area;
	        		myChart.data.datasets[0].data[index] = item.sumArea;
	        	});	 
	        	myChart.update();
        	}
        }
    });
}

function MajorStatistics(){
	var ctx = document.getElementById("pieChart");
	var myChart = new Chart(ctx, {
	    type: 'doughnut',
	    data: {
	        labels: [],
	        datasets: [{
	            data: [],
	            backgroundColor: [
	                'rgba(255, 99, 132, 0.2)',
	                'rgba(54, 162, 235, 0.2)',
	                'rgba(255, 206, 86, 0.2)',
	                'rgba(75, 192, 192, 0.2)',
	                'rgba(153, 102, 255, 0.2)',
	                'rgba(255, 159, 64, 0.2)'
	            ],
	            borderColor: [
	                'rgba(255,99,132,1)',
	                'rgba(54, 162, 235, 1)',
	                'rgba(255, 206, 86, 1)',
	                'rgba(75, 192, 192, 1)',
	                'rgba(153, 102, 255, 1)',
	                'rgba(255, 159, 64, 1)'
	            ],
	            borderWidth: 1
	        }]
	    }
	});
	
	$.ajax({
        type: "POST",
        url: "MajorStatistics",
        dataType: "json",
        success: function(data) {
        	if (data != null) {
	        	$.each(data, function(index, item) {
	        		myChart.data.labels[index] = item.major;
	        		myChart.data.datasets[0].data[index] = item.sumMajor;
	        	});	 
	        	myChart.update();
        	}
        }
    });
}

