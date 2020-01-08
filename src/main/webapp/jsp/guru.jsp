<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript">
    $(function () {
        $("#guruTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/guru/queryByPage",
                datatype : "json",
                autowidth: true,
                colNames : [ 'id', '姓名','头像', '状态', '昵称'],
                colModel : [
                    {name : 'id'},
                    {name : 'name',editable: true},
                    {name : 'photo',
                        formatter: function (data) {
                            return "<img style='width:140px;height:180px' src='"+data+"'/>";
                        }
                        ,editable: true,edittype: "file",editOptions: {enctype:"multipart/form-data"}
                    },
                    {name : 'status',editable: true,edittype: "select",editoptions: {value: "1:正常; 0:冻结;" },
                        formatter: function (cellvalue,options,rowObject) {
                            return cellvalue=="1" ? "正常":"冻结";
                        }
                    },
                    {name : 'nickName',editable: true}
                ],
                editurl: "${pageContext.request.contextPath}/guru/edit",
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#guruPage',
                page : 1,
                viewrecords:true,
                styleUI: "Bootstrap",
                height: "500px",
                multiselect: true
            });
        $("#guruTable").jqGrid('navGrid', '#guruPage', {edit : true,add : true,del : true},
            {
                closeAfterEdit: true,
                afterSubmit: function (response,postData) {
                    var guruId = response.responseJSON.guruId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/guru/upload",
                        type: "post",
                        datatype: "json",
                        data: {"guruId":guruId},
                        fileElementId: "photo",
                        success: function (data) {
                            $("#guruTable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                closeAfterAdd: true,
                afterSubmit: function (response,postData) {
                    var guruId = response.responseJSON.guruId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/guru/upload",
                        type: "post",
                        datatype: "json",
                        data: {"guruId":guruId},
                        fileElementId: "photo",
                        success: function (data) {
                            $("#guruTable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                closeAfterDel: true
            });
    });
</script>
<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading">上师管理</div>
    <!-- Table -->
    <table id="guruTable" style="width: 100%">

    </table>
    <div id="guruPage" style="height: 50px"></div>
</div>

