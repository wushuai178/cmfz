<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript">
    $(function () {
        $("#articleTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/article/queryAllByPage",
                datatype : "json",
                autowidth: true,
                colNames : [ 'id', '标题', '封面', '内容', '发布日期','状态','操作'],
                colModel : [
                    {name : 'id',hidden:true},
                    {name : 'title'},
                    {
                        name : 'img',
                        formatter: function (data) {
                            return "<img style='width:140px;height:180px' src='"+data+"' />";
                        }
                    },
                    {name : 'content'},
                    {name : 'publishDate'},
                    {
                        name : 'status',index: 'status',
                        editable: true,edittype: "select",editoptions: {value: "1:正常; 0:冻结;" },
                        formatter: function (cellvalue,options,rowObject) {
                            return cellvalue=="1" ? "正常":"冻结";
                        },editrules:{required:true}
                    },
                    {
                        name:'xx',
                        formatter: function (cellvalue,options,rowObject) {
                            var buntton = "<button class='btn btn-primary' onclick=\"edit('"+rowObject.id+"')\">修改</button>";
                            buntton += "<button class='btn btn-danger' onclick=\"remove('"+rowObject.id+"')\">删除</button>";
                            return buntton;
                        }
                    }

                ],
                editurl: "${pageContext.request.contextPath}/article/edit",
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#articlePage',
                page : 1,
                viewrecords:true,
                styleUI: "Bootstrap",
                height: "500px",
            });
    });
    function remove(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/remove",
            data:{"id":id},
            type: "post",
            datatype: "json",
            success: function () {
                $("#articleTable").trigger("reloadGrid");
            }
        });
    }
    function edit(id) {
        $("#editForm")[0].reset();
        KindEditor.html("#edit3","");
        $.ajax({
            url: "${pageContext.request.contextPath}/article/queryArticleById",
            type:"post",
            data:{"id":id},
            datatype:"json",
            success: function (data) {
                $("#id").val(data.id);
                $("#edit1").val(data.title);
                KindEditor.html("#edit3",data.content);
                var dataGuru = data.guruId;
                $("#normal").attr("selected",null);
                $("#frozen").attr("selected",null);
                if (data.status=="1"){
                    $("#normal").attr("selected","selected");
                }else {
                    $("#frozen").attr("selected","selected");
                }
                $.ajax({
                    url: "${pageContext.request.contextPath}/guru/queryAllGuru",
                    type:"post",
                    datatype:"json",
                    success: function (value) {
                        var select =$("#edit6");
                        select.empty();
                        for (var i=0;i<value.length;i++){
                            var option =$("<option value='"+value[i].id+"'>"+value[i].name+"</option>");
                            if (dataGuru==value[i].id) {
                                option.attr("selected","selected");
                            }
                            select.append(option);
                        }
                    }
                });
            }
        });
        $("#editModal").modal("show");
    }

    function add() {
        $("#addForm")[0].reset();
        KindEditor.html("#input3","");
        $.ajax({
            url: "${pageContext.request.contextPath}/guru/queryAllGuru",
            type:"post",
            datatype:"json",
            success: function (data) {
                var select =$("#input6");
                select.empty();
                for (var i=0;i<data.length;i++){
                    var option =$("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                    select.append(option);
                }
                div.append(select);
            }
        });
        $("#addModal").modal("show");
    }

</script>
<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading">文章管理</div>
    <div class="panel-body">
        <button class="btn btn-primary" onclick="add()">添加</button>
    </div>
    <!-- Table -->
    <table id="articleTable" style="width: 100%">

    </table>
    <div id="articlePage" style="height: 50px"></div>
</div>



