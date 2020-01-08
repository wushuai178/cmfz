<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-3.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script src="../kindeditor/kindeditor-all-min.js"></script>
    <script src="../kindeditor/lang/zh-CN.js"></script>
    <script src="../echarts/echarts.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="../echarts/china.js" charset="UTF-8"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        $(function () {
           $("#bannerMag").click(function () {
               $("#centerLay").load("${pageContext.request.contextPath}/jsp/banner.jsp");
           });
           $("#albumMag").click(function () {
               $("#centerLay").load("${pageContext.request.contextPath}/jsp/album.jsp");
           });
            $("#articleMag").click(function () {
                $("#centerLay").load("${pageContext.request.contextPath}/jsp/article.jsp");
            });
            $("#userMag").click(function () {
                $("#centerLay").load("${pageContext.request.contextPath}/jsp/user.jsp");
            });
            $("#guruMag").click(function () {
                $("#centerLay").load("${pageContext.request.contextPath}/jsp/guru.jsp");
            });
            $("#userTimeMag").click(function () {
                $("#centerLay").load("${pageContext.request.contextPath}/jsp/userTime.jsp");
            })
            $("#userMapMag").click(function () {
                $("#centerLay").load("${pageContext.request.contextPath}/jsp/map.jsp");
            })
            $("#chatMag").click(function () {
                $("#centerLay").load("${pageContext.request.contextPath}/jsp/chat.jsp");
            })
        });

        KindEditor.ready(function (K) {
            window.editor = K.create('#input3',{
                uploadJson: "${pageContext.request.contextPath}/article/uploadImage",
                fileManagerJson: "${pageContext.request.contextPath}/article/showImage",
                allowFileManager: true,
                afterBlur: function () {
                    this.sync();
                }
            });
            window.editor = K.create('#edit3',{
                uploadJson: "${pageContext.request.contextPath}/article/uploadImage",
                fileManagerJson: "${pageContext.request.contextPath}/article/showImage",
                allowFileManager: true,
                afterBlur: function () {
                    this.sync();
                }
            });
        });

        function submitAdd() {
            $.ajaxFileUpload({
                url: "${pageContext.request.contextPath}/article/addArticle",
                data:{"title":$("#input1").val(),"content":$("#input3").val(),"status":$("#input4").val(),"guruId":$("#input6").val()},
                fileElementId:"imgageAdd",
                datatype:"json",
                type:"post",
                success: function () {
                    $("#articleTable").trigger("reloadGrid");
                    $("#addModal").modal("hide");
                }
            });
        }
        function submitEdit() {
            $.ajaxFileUpload({
                url: "${pageContext.request.contextPath}/article/updateArticle",
                data:{"id":$("#id").val(),"title":$("#edit1").val(),"content":$("#edit3").val(),"status":$("#edit4").val(),"guruId":$("#edit6").val()},
                fileElementId:"imgageEdit",
                datatype:"json",
                type:"post",
                success: function () {
                    $("#articleTable").trigger("reloadGrid");
                    $("#editModal").modal("hide");
                }
            });
        }
    </script>
</head>
<body>
<!-- 导航栏-->
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法州后台系统</a>
        </div>
            <!--向右对齐-->
            <p class="navbar-text navbar-right"><a href="${pageContext.request.contextPath}/admin/exit">退出登录</a></p>
            <p class="navbar-text navbar-right">当前用户: ${sessionScope.admin.username}</p>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <!-- 右侧-->
        <div class="col-xs-2">
            <!-- 手风琴-->
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                                用户模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:void(0)" id="userMag">用户管理</a></li>
                                <li><a href="javascript:void(0)" id="userTimeMag">用户日活度</a></li>
                                <li><a href="javascript:void(0)" id="userMapMag">用户地区分布</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                上师模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:void(0)" id="guruMag">上师管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                专辑模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav" >
                                <li><a href="javascript:void(0)" id="albumMag">专辑管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive">
                                轮播图模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav" >
                                <li><a href="javascript:void(0)" id="bannerMag">轮播图管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                文章模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav" >
                                <li><a href="javascript:void(0)" id="articleMag">文章管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseSix">
                                聊天室模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseSix" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav" >
                                <li><a href="javascript:void(0)" id="chatMag">聊天室</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 左侧-->
        <div class="col-xs-10">
            <div class="container" id="centerLay">
                <div class="jumbotron" >
                    <h2>欢迎使用持明法州后台系统！</h2>

                    <div id="myCarousel" class="carousel slide">
                        <!-- 轮播（Carousel）指标 -->
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                        </ol>
                        <!-- 轮播（Carousel）项目 -->
                        <div class="carousel-inner">
                            <div class="item active">
                                <img src="${pageContext.request.contextPath}/images/img1.jpg" alt="First slide">
                                <div class="carousel-caption">冬至</div>
                            </div>
                            <div class="item">
                                <img src="${pageContext.request.contextPath}/images/img2.jpg" alt="Second slide">
                                <div class="carousel-caption">初春</div>
                            </div>
                            <div class="item">
                                <img src="${pageContext.request.contextPath}/images/img3.jpg" alt="Third slide">
                                <div class="carousel-caption">大雪</div>
                            </div>
                        </div>
                        <!-- 轮播（Carousel）导航 -->
                        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 页脚-->
    <div class="panel-footer">
        <p style="text-align: center">@百知教育 @baizhi.wus.com.cn</p>
    </div>
</div>


<div class="modal fade" id="addModal">
    <div class="modal-dialog" >
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加文章</h4>
            </div>
            <form method="post" enctype="multipart/form-data" id="addForm">
                <div class="modal-body">
                    <div class="row" id="row">
                        <div class="form-group">
                            <label for="input1" class="col-sm-2 control-label">标题</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="input1" name="title">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="imgageAdd" class="col-sm-2 control-label">封面</label>
                            <div class="col-sm-10">
                                <input type="file" class="form-control" id="imgageAdd" name="imgageAdd">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="input3" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-10">
                                <textarea style="width: 100%" id="input3" name="content"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="input4" class="col-sm-2 control-label">状态</label>
                            <div class="col-sm-10">
                                <select id="input4" name="status" class="form-control">
                                    <option value="1" >正常</option>
                                    <option value="0">冻结</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="input6" class="col-sm-2 control-label">上师</label>
                            <div class="col-sm-10">
                            <select name='guruId' id="input6" class="form-control">

                            </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary" onclick="submitAdd()">提交</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="editModal">
    <div class="modal-dialog" >
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改文章</h4>
            </div>
            <form method="post"  enctype="multipart/form-data" id="editForm">
                <div class="modal-body">
                    <input name="id" id="id" type="hidden"/>
                    <div class="row">
                        <div class="form-group">
                            <label for="edit1" class="col-sm-2 control-label">标题</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="edit1" name="title">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="imgageEdit" class="col-sm-2 control-label">封面</label>
                            <div class="col-sm-10">
                                <input type="file" class="form-control" id="imgageEdit" name="imgageEdit">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="edit3" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-10">
                                <textarea style="width: 100%" id="edit3" name="content"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="edit4" class="col-sm-2 control-label">状态</label>
                            <div class="col-sm-10">
                                <select id="edit4" name="status" class="form-control">
                                    <option value="1" id="normal">正常</option>
                                    <option value="0" id="frozen">冻结</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="edit6" class="col-sm-2 control-label">上师</label>
                            <div class="col-sm-10">
                                <select name='guruId' id="edit6" class="form-control">

                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary" onclick="submitEdit()">提交</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


</body>