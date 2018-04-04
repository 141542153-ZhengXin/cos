$(function () {
  ResumeStatistics();
  GetArticleTop5();
  GetMsgByAd();
  setInterval('GetArticleTop5()', 60000);
  var sendId;
  InitWebSocket();
});
function ResumeStatistics() {
  var userId = $('#userId').val();
  $.ajax({
    type: 'POST',
    url: 'ResumeStatistics',
    data: {
      userId: userId
    },
    dataType: 'json',
    success: function (data) {
      for (var i in data) {
        $('tbody tr:eq(' + i + ') td:eq(0)').text(data[i][0]);
        $('tbody tr:eq(' + i + ') td:eq(1)').text(data[i][1]);
      }
    }
  });
}
function GetArticleTop5() {
  var userId = $('#userId').val();
  $.ajax({
    type: 'POST',
    url: 'GetArticleTop5',
    dataType: 'json',
    data: {
      adminId: userId
    },
    success: function (data) {
      if (data != null) {
        for (var i = 0; i < 5; i++) {
          $('#article').find('li').remove();
        }
        $.each(data, function (index, item) {
          $('#article').append('<li class="list-group-item"><a tabindex="0" style="text-decoration: none;" title="'
          + item.title
          + '" data-container="body" data-toggle="popover" data-trigger="hover" data-placement="right" data-html="true" data-content="'
          + item.content
          + '"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;'
          + item.title
          + '<label class="pull-right">热度&nbsp;&nbsp;<span class="badge">'
          + item.attention
          + '</span></label></a></li>');
          $('[data-toggle=\'popover\']').popover();
        });
      }
    }
  });
}
function GetMsgByAd() {
  $.ajax({
    type: 'POST',
    url: 'GetMsgByAd',
    data: {
        id: $('#userId').val()
    },
    dataType: 'json',
    success: function (data) {
      if (data != null) {
        $('#adminmsg a').remove();
        $.each(data, function (index, item) {
          $('#adminmsg').append('<a href="javascript:void(0);" class="list-group-item" type="button" data-toggle="modal" data-target="#adminModal" onclick="HistoryMsgList('
          + item[0]
          + ')">'
          + item[1]
          + ':'
          + item[2]
          + '</a>');
        });
      }
    }
  });
}
function HistoryMsgList(id) {
  sendId = id;
  $.ajax({
    type: 'POST',
    url: 'HistoryMsgList',
    data: {
      mId: $('#userId').val(),
      id: id,
    },
    dataType: 'json',
    success: function (data) {
      if (data != null) {
        $.each(data, function (index, item) {
          if (item.substr(0, 2) == 's&') {
            $('#msgDiv1').append('<div class=\'media well media-style\'>'
            + '<div class=\'media-left\'>'
            + '<a href=\'#\'><img class=\'media-object wh45\' src=\'/cos/static/images/man.png\' alt=\'boy\'></a></div>'
            + '<div class=\'media-body\'><h5 class=\'media-heading\'>同学</h5>'
            + item.substr(2, item.length)
            + '</div></div>');
          } else {
            $('#msgDiv1').append('<div class=\'media well media-style\'>'
            + '<div class=\'media-body  text-right\'>'
            + '<h5 class=\'media-heading\'>我</h5>'
            + item.substr(2, item.length)
            + '</div>'
            + '<div class=\'media-right\'>'
            + '<a href=\'#\'><img class=\'media-object wh45\' src=\'/cos/static/images/woman.png\' alt=\'girl\'></a></div></div>');
          }
        });
      }
    }
  });
  GetMsgByAd();
}

$(function () { $('#adminModal').on('hide.bs.modal', function () {
	$('#msgDiv1 div').remove();
 });
});

function InitWebSocket() {
  var websocket;
  if ('WebSocket' in window) {
    websocket = new WebSocket('ws://localhost:8080/cos/ws?userId='
    + $('#userId').val());
  } else if ('MozWebSocket' in window) {
    websocket = new MozWebSocket('ws://localhost:8080/cos/ws'
    + $('#userId').val());
  } else {
    websocket = new SockJS('http://localhost:8080/cos/ws/sockjs'
    + $('#userId').val());
  }
  websocket.onmessage = function (event) {
    var data = JSON.parse(event.data);
    if($('#msgDiv1 div').length>0){
    	$('#msgDiv1').find('div').eq(0).before('<div class=\'media well media-style\'>'
	            + '<div class=\'media-left\'>'
	            + '<a href=\'#\'><img class=\'media-object wh45\' src=\'/cos/static/images/man.png\' alt=\'boy\'></a></div>'
	            + '<div class=\'media-body\'><h5 class=\'media-heading\'>同学</h5>'
	            + data.text
	            + '</div></div>');
    }else{
    	 /*$('#msgDiv1').append('<div class=\'media well media-style\'>'
 	            + '<div class=\'media-left\'>'
 	            + '<a href=\'#\'><img class=\'media-object wh45\' src=\'/cos/static/images/man.png\' alt=\'boy\'></a></div>'
 	            + '<div class=\'media-body\'><h5 class=\'media-heading\'>同学</h5>'
 	            + data.text
 	            + '</div></div>');*/
    }
    GetMsgByAd();
  }
  websocket.onclose = function (e) {
    websocket.close();
    //关闭TCP连接  
  }
  $('#sendMsg1').click(function () {
    if ($('#admintext').val() == '') {
      toastr.warning('内容不能为空');
    }else{
	    var data = {
	    };
	    data['from'] = $('#userId').val();
	    data['to'] = sendId;
	    data['text'] = $('#admintext').val();
	    $('#msgDiv1').find('div').eq(0).before('<div class=\'media well media-style\'>'
	            + '<div class=\'media-body  text-right\'>'
	            + '<h5 class=\'media-heading\'>我</h5>'
	            + data['text']
	            + '</div>'
	            + '<div class=\'media-right\'>'
	            + '<a href=\'#\'><img class=\'media-object wh45\' src=\'/cos/static/images/woman.png\' alt=\'girl\'></a></div></div>');
	    $('#admintext').val("");
	    websocket.send(JSON.stringify(data));
    }
  });
}
