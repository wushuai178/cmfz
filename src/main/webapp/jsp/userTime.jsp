<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '用户注册活跃度'
        },
        tooltip:{

        },
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["今天注册的数量","一周内注册数量","一个月内注册数量","一年内注册数量"]
        },
        yAxis: {}
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $.ajax({
        url:"${pageContext.request.contextPath}/user/queryUserByDay",
        type:"post",
        datatype:"json",
        success: function (data) {
            myChart.setOption({
                series: [{
                    name: '男',
                    type: 'bar',
                    data: data.man
                },
                {
                    name: '女',
                    type: 'bar',
                    data: data.woman
                }]
            });
        }
    });

    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-84a4d8cfd86d40d3b8b8c0283093e80e" //替换为您的应用appkey
    });
    goEasy.subscribe({
        channel: "cmfz", //替换为您自己的channel
        onMessage: function (message) {
            console.log(message.content);
            var data = JSON.parse(message.content);
            myChart.setOption({
                series: [{
                    name: '男',
                    type: 'bar',
                    data: data.man
                },
                    {
                        name: '女',
                        type: 'bar',
                        data: data.woman
                    }]
            });
        }
    });
</script>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 1000px;height:400px;"></div>

</body>
