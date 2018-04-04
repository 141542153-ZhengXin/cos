$(function() {
	StudentTable();
	ArticleTable();
	setInterval('StudentTable()', 300000);
	setInterval('ArticleTable()', 300000);
});

function StudentTable() {
	$.ajax({
		type : 'POST',
		url : 'StudentTable',
		dataType : 'json',
		success : function(data) {
			if (data != null) {
				$('#stbody tr').remove();
				$.each(data, function(index, item) {
					$('#stbody').append(
							'<tr><td>' + item.id + '</td>' + '<td>' + item.name
									+ '</td>' + '<td>' + item.sex + '</td>'
									+ '<td>' + item.area + '</td>' + '<td>'
									+ item.departments + '</td>' + '<td>'
									+ item.major + '</td>' + '<td>'
									+ item.telephone + '</td>' + '<td>'
									+ item.organization + '</td>' + '<td>'
									+ item.hobby + '</td>' + '<td>'
									+ item.evaluation + '</td>' + '<td>'
									+ item.isPass + '</td>' + '<td>'
									+ item.isDelivery + '</td></tr>');
				});
			}
		}
	});
}

function ArticleTable() {
	$.ajax({
		type : 'POST',
		url : 'ArticleTable',
		dataType : 'json',
		success : function(data) {
			if (data != null) {
				$('#atbody tr').remove();
				$.each(data, function(index, item) {
					$('#atbody').append(
							'<tr><td>' + item.id + '</td>' + '<td>'
									+ item.title + '</td>' + '<td>'
									+ item.content + '</td>' + '<td>'
									+ item.adminId + '</td>' + '<td>'
									+ item.adminName + '</td>' + '<td>'
									+ item.department + '</td>' + '<td>'
									+ item.attention + '</td></tr>');
				});
			}
		}
	});
}
