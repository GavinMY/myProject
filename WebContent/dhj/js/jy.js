// JavaScript Document$(function () {

$(function () {
    $('#containe_jy').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
		 credits: {
            enabled: false
        },
        title: {
            text: ''
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}mg</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#30937f',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} mg'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '焦油含量',
            data: [
                ['本月控烟',   jiaoyou2],
                ['实际',       jiaoyou1]  
            ]
        }]
    });
});	




