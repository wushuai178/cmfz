<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript">
    $(function () {
        $("#userTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/user/queryUserByPage",
                datatype : "json",
                autowidth: true,
                colNames : [ 'id', '手机','密码', '盐值', '状态','头像', '姓名','昵称','性别','签名','城市','注册时间','最后登录时间'],
                colModel : [
                    {name : 'id'},
                    {name : 'phone',editable: true},
                    {name : 'password',editable: true},
                    {name : 'salt'},
                    {name : 'status',editable: true,edittype: "select",editoptions: {value: "1:正常; 0:冻结;" },
                        formatter: function (cellvalue,options,rowObject) {
                            return cellvalue=="1" ? "正常":"冻结";
                        }
                    },
                    {name : 'photo',
                        formatter: function (data) {
                            return "<img style='width:140px;height:180px' src='"+data+"'/>";
                        }
                    ,editable: true,edittype: "file",editOptions: {enctype:"multipart/form-data"}},
                    {name : 'name',editable: true},
                    {name : 'nickName',editable: true},
                    {name : 'sex',editable: true,edittype: "select",editoptions: {value: "1:男;0:女;" },
                        formatter: function (cellvalue,options,rowObject) {
                            return cellvalue=="1" ? "男":"女";
                        }
                    },
                    {name : 'sign',editable: true},
                    {name : 'location',editable: true},
                    {name : 'registDate'},
                    {name: 'lastLogin',editable: true,edittype: "date"}
                ],
                editurl: "${pageContext.request.contextPath}/user/edit",
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#userPage',
                page : 1,
                viewrecords:true,
                styleUI: "Bootstrap",
                height: "500px",
            });
        $("#userTable").jqGrid('navGrid', '#userPage', {edit : true,add : true,del : false},
            {
                closeAfterEdit: true,
                afterSubmit: function (response,postData) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/user/upload",
                        type: "post",
                        datatype: "json",
                        data: {"userId":userId},
                        fileElementId: "photo",
                        success: function (data) {
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                closeAfterAdd: true,
                afterSubmit: function (response,postData) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/user/upload",
                        type: "post",
                        datatype: "json",
                        data: {"userId":userId},
                        fileElementId: "photo",
                        success: function (data) {
                            $("#userTable").trigger("reloadGrid");
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
    <div class="panel-heading">用户管理</div>
    <!-- Table -->
    <table id="userTable" style="width: 100%">

    </table>
    <div id="userPage" style="height: 50px"></div>
</div>

