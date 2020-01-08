<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript">
    $(function () {
        jQuery("#bannerTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/banner/queryBannerByPage",
                datatype : "json",
                autowidth: true,
                colNames : [ 'id', '标题', '图片路径', '跳转路径','创建日期', '描述','状态'],
                colModel : [
                    {name : 'id'},
                    {name : 'title',editable: true,editrules:{required:true}},
                    {
                        name : 'url',
                        formatter: function (data) {
                            return "<img style='width:140px;height:180px' src='"+data+"' />";
                        }
                        ,editable: true
                        ,edittype: "file",editOptions: {enctype:"multipart/form-data"}
                    },
                    {name : 'href',editable: true},
                    {name : 'createDate'},
                    {name : 'description',editable: true},
                    {
                        name : 'status',index: 'status',
                        editable: true,edittype: "select",editoptions: {value: "1:正常; 0:冻结;" },
                        formatter: function (cellvalue,options,rowObject) {
                            return cellvalue=="1" ? "正常":"冻结";
                        },editrules:{required:true}
                    }

                ],
                editurl: "${pageContext.request.contextPath}/banner/edit",
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#bannerPage',
                page : 1,
                viewrecords:true,
                styleUI: "Bootstrap",
                height: "500px",
                multiselect: true
            });
        jQuery("#bannerTable").jqGrid('navGrid', '#bannerPage', {edit : true,add : true,del : true},
            {
               //修改
                closeAfterEdit: true,
                afterSubmit: function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        type: "post",
                        datatype: "json",
                        data: {bannerId:bannerId},
                        fileElementId:"url",
                        success: function () {
                            $("#bannerTable").resetSelection();
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                //添加
                closeAfterAdd: true,
                afterSubmit: function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        type: "post",
                        datatype: "json",
                        data: {bannerId:bannerId},
                        fileElementId:"url",
                        success: function () {
                            $("#bannerTable").resetSelection();
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                //删除
                closeAfterDel: true
            });
    });
    function submitFile() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/banner/importController",
            type: "post",
            datatype: "json",
            fileElementId: "file",
            success: function () {
                $("#importModal").modal("hide");
                $("#bannerTable").trigger("reloadGrid");
            }
        });
    }
    function importBanner() {
        $("#importForm")[0].reset();
        $("#importModal").modal("show");
    }
</script>
<div class="panel panel-default">
    <!-- Default panel contents -->

    <div class="panel-heading">
        <a href="${pageContext.request.contextPath}/banner/outputController" >导出轮播图</a>
        <a href="javascript:void(0)" onclick="importBanner()">导入轮播图</a>
    </div>
    <!-- Table -->
    <table id="bannerTable" style="width: 100%">

    </table>
    <div id="bannerPage" style="height: 50px"></div>
</div>
<div class="modal fade" id="importModal">
    <div class="modal-dialog" >
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加文章</h4>
            </div>
            <form method="post" enctype="multipart/form-data" id="importForm">
                <div class="modal-body">
                    <div class="row" id="row">
                        <div class="form-group">
                            <label for="file" class="col-sm-2 control-label">上传文件</label>
                            <div class="col-sm-10">
                                <input type="file" class="form-control" id="file" name="file">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary" onclick="submitFile()">提交</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>