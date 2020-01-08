<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                url:"${pageContext.request.contextPath}/chat/queryAllContent",
                type:"post",
                datatype:"json",
                success: function (data) {
                    var text = $("#input3");
                    text.empty();
                    for (var i=0;i<data.length;i++){
                        var userName = data[i].userName;
                        var content = data[i].content;
                        var date = data[i].createDate;
                        text.append(date+":"+userName+"</br>");
                        text.append(content+"</br>");
                    }
                }
            });

            var goEasy = new GoEasy({
                host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: "BC-84a4d8cfd86d40d3b8b8c0283093e80e" //替换为您的应用appkey
            });
            goEasy.subscribe({
                channel: "cmfz", //替换为您自己的channel
                onMessage: function (message) {
                    var data = JSON.parse(message.content);
                    var text = $("#input3");
                    text.empty();
                    for (var i=0;i<data.length;i++){
                        var userName = data[i].userName;
                        var content = data[i].content;
                        var date = data[i].createDate;
                        text.append(date+":"+userName+"</br>");
                        text.append(content+"</br>");
                    }
                }
            });
        });
        $("#send").click(function () {
            var content=$("#content").val();
            $.ajax({
                url:"${pageContext.request.contextPath}/chat/addChat",
                type:"post",
                data:{"content":content},
                datatype:"json",
                success: function (data) {
                    $("#content").val("");
                }
            })
        })
    </script>
<body>

<div class="panel panel-default">
    <div class="panel-heading">聊天室</div>
    <form id="form">
    <div class="panel-body">
        <div class="form-group">
            <label for="input3" class="col-sm-2 control-label">聊天记录</label>
            <div class="col-sm-10" id="input3">

            </div>
        </div>
    </div>
    <div class="panel-footer">
        <div class="input-group">
            <input type="text" class="form-control" name="content" id="content"/>
            <span class="input-group-btn">
        <button class="btn btn-default" type="button" id="send">发送</button>
      </span>
        </div>
    </div>
    </form>
</div>
</body>