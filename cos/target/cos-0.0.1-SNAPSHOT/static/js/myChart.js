

var pieData = {
	labels : [ "Red", "Blue", "Yellow" ],
	datasets : [ {
		data : [ 10, 20, 30 ],
		backgroundColor : [ 'rgba(255, 99, 132, 0.2)',
				'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)',
				'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)',
				'rgba(255, 159, 64, 0.2)' ]
	} ]
}

$(function() {
	
	var ctx2 = $("#pieChart").get(0).getContext("2d");
	new Chart(ctx2).Pie(pieData);
});