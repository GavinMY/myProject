// JavaScript Document$(function () {
$(function () {
    $('#container').highcharts({ 
	legend: {
            enabled: false
        },
		
	 chart: {
            backgroundColor: '#30937f',
			 style:{
						color:'#FFF'
				  }
        },    
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
			labels:{                    
				style: {
				color:'#FFFFFF'
				}
			},
            categories: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日']
        },
        yAxis: {
			max:30, // 定义Y轴 最大值
            min:0, // 定义最小值
            minPadding: 0.2,
            maxPadding: 0.2,
            tickInterval:5, // 刻度值

            title: {
                text: ''
            },
			labels:{                    
				style: {
				color:'#FFFFFF'
				}
			},
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
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
            data: [13,15,10,23,8,6,8]
        }, {
            name: '吸烟数',
            data: [10,13,12, 15, 14,5,9]
        }]
    });
});
		