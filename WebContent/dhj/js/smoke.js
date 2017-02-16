// JavaScript Document$(function () {
$(function () {
    $('#container').highcharts({     
	 title: {
            text: '',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
		 credits: {
            enabled: false
        },
        xAxis: {
            categories: riqi
        },
        yAxis: {
			max:50, // 定义Y轴 最大值
            min:0, // 定义最小值
            minPadding: 0.2,
            maxPadding: 0.2,
            tickInterval:5, // 刻度值

            title: {
                text: ''
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#FFFFFF'
            }]
        },
        tooltip: {
            valueSuffix: '支'
        }
		/*,
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        }*/,
        series: [{
            name: '控烟目标',
            data: riqikongyan
        }, {
            name: '吸烟数',
            data: riqicount
        }]
    });
});
	
$(function () {
    $('#container1').highcharts({     
	 title: {
            text: '',
            x: -20 //center
        },
        subtitle: {
            text: '',
            x: -20
        },
		 credits: {
            enabled: false
        },
        xAxis: {
            categories: qujian
        },
        yAxis: {
			max:50, // 定义Y轴 最大值
            min:0, // 定义最小值
            minPadding: 0.2,
            maxPadding: 0.2,
            tickInterval:5, // 刻度值

            title: {
                text: ''
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#FFFFFF'
            }]
        },
        tooltip: {
            valueSuffix: '支'
        }
		/*,
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        }*/,
        series: [{
            name: '控烟目标',
            data: kongyan
        }, {
            name: '吸烟数',
            data: mouthcount
        }]
    });
});	