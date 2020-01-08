<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript">
    $(function () {
        $("#albumTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/album/queryAlbumByPage",
                datatype : "json",
                autowidth: true,
                colNames : [ 'id', '标题','封面', '评分', '作者','播音', '集数','描述','状态','创建日期'],
                colModel : [
                    {name : 'id'},
                    {name : 'title',editable: true,editrules:{required:true}},
                    {name: 'img',editable: true,edittype: "file",editOptions: {enctype:"multipart/form-data"},
                    formatter: function (data) {
                        return "<img style='height: 180px;width: 100px' src='"+data+"'/>";
                    }},
                    {name: 'score',editable: true},
                    {name: 'author',editable: true},
                    {name: 'broadcast',editable: true},
                    {name: 'count'},
                    {name : 'desc',editable: true,edittype:"textarea"},
                    {
                        name : 'status',index: 'status',
                        editable: true,edittype: "select",editoptions: {value: "1:正常; 0:冻结;" },
                        formatter: function (cellvalue,options,rowObject) {
                            return cellvalue=="1" ? "正常":"冻结";
                        },editrules:{required:true}
                    },
                    {name : 'createDate',editable:true,edittype:"date"}
                ],
                editurl: "${pageContext.request.contextPath}/album/edit",
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#albumPage',
                page : 1,
                viewrecords:true,
                styleUI: "Bootstrap",
                height: "500px",
                multiselect: true,
                subGrid : true,
                caption : "专辑管理",
                subGridRowExpanded : function(subgrid_id, row_id) {
                    var sid, spage;
                    sid = subgrid_id + "_t";
                    spage = "p_" + sid;
                    $("#" + subgrid_id).html(
                        "<table id='" + sid
                        + "' class='scroll'></table><div id='"
                        + spage + "' class='scroll'></div>");
                    $("#" + sid).jqGrid(
                        {
                            url : "${pageContext.request.contextPath}/chapter/queryChapterByPage?albumId="+row_id,
                            datatype : "json",
                            colNames : [ 'id', '标题', '音频', '时长','大小','创建日期'],
                            colModel : [
                                {name : "id"},
                                {name : "title",editable: true},
                                {name : "url",editable: true,edittype: "file",editOptions: {enctype:"multipart/form-data"},
                                    formatter: function (cellvalue,options,rowObject) {
                                        var button = "<button class='btn btn-default' onclick=\"downloads('"+cellvalue+"')\">下载</button>";
                                        button+="<button class='btn btn-primary' onclick=\"onPlay('"+cellvalue+"')\">在线播放</button>"
                                        return button;
                                    }},
                                {name : "time"},
                                {name : "size"},
                                {name : "createTime",editable: true,edittype:"date"},
                            ],
                            rowNum : 10,
                            pager : spage,
                            height : '100%',
                            styleUI: "Bootstrap",
                            editurl: "${pageContext.request.contextPath}/chapter/edit?albumId="+row_id,
                            autowidth: true,
                            multiselect: true
                        });
                    $("#" + sid).jqGrid('navGrid',
                        "#" + spage, {
                            edit : true,
                            add : true,
                            del : true,
                        },
                        {
                            closeAfterEdit: true,
                            afterSubmit: function (response,postData) {
                                var chapterId = response.responseJSON.chapterId;
                                $.ajaxFileUpload({
                                    url: "${pageContext.request.contextPath}/chapter/upload",
                                    type: "post",
                                    datatype: "json",
                                    data: {"chapterId":chapterId},
                                    fileElementId: "url",
                                    success: function (data) {
                                        $("#albumTable").trigger("reloadGrid");
                                    }
                                });
                                return postData;
                            }
                        },
                        {
                            closeAfterAdd: true,
                            afterSubmit: function (response,postData) {
                                var chapterId = response.responseJSON.chapterId;
                                $.ajaxFileUpload({
                                    url: "${pageContext.request.contextPath}/chapter/upload",
                                    type: "post",
                                    datatype: "json",
                                    data: {"chapterId":chapterId},
                                    fileElementId: "url",
                                    success: function (data) {
                                        $("#albumTable").resetSelection();
                                        $("#albumTable").trigger("reloadGrid");
                                    }
                                });
                                return postData;
                            }
                        },
                        {
                            closeAfterDel: true
                        });
                },
                subGridRowColapsed : function(subgrid_id, row_id) {
                }
            });
        $("#albumTable").jqGrid('navGrid', '#albumPage', {edit : true,add : true,del : true},
            {
                closeAfterEdit: true,
                afterSubmit: function (response,postData) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/album/upload",
                        type: "post",
                        datatype: "json",
                        data: {"albumId":albumId},
                        fileElementId: "img",
                        success: function (data) {
                            $("#albumTable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                closeAfterAdd: true,
                afterSubmit: function (response,postData) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/album/upload",
                        type: "post",
                        datatype: "json",
                        data: {"albumId":albumId},
                        fileElementId: "img",
                        success: function (data) {
                            $("#albumTable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                closeAfterDel: true
            });
    });
    function downloads(cellvalue) {
        location.href = "${pageContext.request.contextPath}/chapter/download?url="+cellvalue;
    }
    function onPlay(cellvalue) {
        alert(cellvalue);
        $("#myModal").modal("show");
        $("#music").attr("src",cellvalue);
    }
</script>
<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading">专辑管理</div>
    <!-- Table -->
    <table id="albumTable" style="width: 100%">

    </table>
    <div id="albumPage" style="height: 50px"></div>
</div>
<div class="modal fade">
    <div id="myModal" class="modal-dialog">
        <audio id="music" src="" controls="controls">

        </audio>
    </div>
</div>
