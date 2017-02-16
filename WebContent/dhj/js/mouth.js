// JavaScript Document$(function () {
$(function () {
    
    var colors = Highcharts.getOptions().colors,
        categories =qujian,
        name = '本月吸烟花销',
        data = showdata;

   

    var chart = $('#containe_mouth').highcharts({
		legend: {
            enabled: false
        },
        chart: {
			backgroundColor: '#30937f',
            type: 'column'
        },
		 credits: {
            enabled: false
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
			labels:{                    
				style: {
				color:'#FFFFFF'
				}
			},
            categories: categories
        },
        yAxis: {
			max:50, // 定义Y轴 最大值
            min:0, // 定义最小值
            minPadding: 0.2,
            maxPadding: 0.2,
            tickInterval:10, // 刻度值
			 labels:{                    
				style: {
				color:'#FFFFFF'
				}
			},
            title: {
                text: ''
            }
        },
        plotOptions: {
            column: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function() {
                            var drilldown = this.drilldown;
                            if (drilldown) { // drill down
                                setChart(drilldown.name, drilldown.categories, drilldown.data, drilldown.color);
                            } else { // restore
                                setChart(name, categories, data);
                            }
                        }
                    }
                },
                dataLabels: {
                    enabled: true,
                    color: colors[0],
                    style: {
                        fontWeight: 'bold'
                    },
                    formatter: function() {
                        return this.y +'元';
                    }
                }
            }
        },
        tooltip: {
            formatter: function() {
                var point = this.point,
                    s = this.x +':<b>'+'吸烟花销'+ this.y +'元 </b><br/>';
               
			   
                return s;
            }
        },
        series: [{
            name: name,
            data: data,
            color: 'white'
        }],
        exporting: {
            enabled: false
        }
    })
    .highcharts(); // return chart
});	