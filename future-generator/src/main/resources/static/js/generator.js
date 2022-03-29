$(function () {
    tables();
});


function tables(){
    $("#jqGrid").jqGrid({
        url: 'sys/generator/list',
        datatype: "json",
        colModel: [
            { label: '表名d', name: 'tableName', width: 100, key: true },
            { label: 'Engine', name: 'engine', width: 70},
            { label: '表备注', name: 'tableComment', width: 100 },
            { label: '创建时间', name: 'createTime', width: 100 }
        ],
        viewrecords: true,//定义是否要显示总记录数
        height: 385,
        rowNum: 10,//在grid上显示记录条数，这个参数是要被传递到后台
        rowList : [10,30,50,100,200],//一个下拉选择框，用来改变显示记录数，当选择时会覆盖rowNum参数传递到后台
        rownumbers: true, //添加左侧行号
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        multiboxonly:true,
        pager: "#jqGridPager",//定义翻页用的导航栏，必须是有效的html元素。翻页工具栏可以放置在html页面任意位置
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        //实现每次选择之前重置一下选择项
        beforeSelectRow: function(rowid, e){
            $("#jqGrid").jqGrid('resetSelection');
            return(true);
        },
        //实现每次选择之前重置一下选择项
        onSelectRow: function(rowid, e){
            tableNames =   getSelectedRow();
            if(tableNames == null){
                return ;
            }
            $("#tableNames").val(tableNames);
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            //隐藏表格列表第一行的复选框
            //$("#jqGrid").jqGrid('hideCol','cb');
            //移除多选表头上的多选框
            var myGrid = $("#jqGrid");
            $("#cb_"+myGrid[0].id).hide();
        }
    });
}


var tableNames;
var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            mainPath: 'com.suven.framework',
            packageName: 'com.suven.module',
            moduleName:'user',
            author: 'suven',
            email:'suvenw@163.com',
            tablePrefix:''
        }
    },
    computed: {
        filterParamsStr: function () {
            var params = {}
            var paramsStr = ''
            if (this.q.mainPath) {
                params.mainPath = this.q.mainPath
            } else {
                delete params.mainPath
            }
            if (this.q.packageName) {
                params.packageName = this.q.packageName
            } else {
                delete params.packageName
            }
            if (this.q.moduleName) {
                params.moduleName = this.q.moduleName
            } else {
                delete params.moduleName
            }
            if (this.q.author) {
                params.author = this.q.author
            } else {
                delete params.author
            }
            if (this.q.email) {
                params.email = this.q.email
            } else {
                delete params.email
            }
            if (this.q.tablePrefix) {
                params.tablePrefix = this.q.tablePrefix
            } else {
                delete params.tablePrefix
            }
            console.log(params);
            for(var key in params) {
                paramsStr += '&' + key + '=' + params[key]
            }
            return paramsStr
        }
    },
    methods: {
        query: function () {
            this.tablesShow();
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'tableName': $("#tableNames").val()},
                page:1
            }).trigger("reloadGrid");
        },
        columns: function(){
            tableNames =   getSelectedRow();
            if(tableNames == null){
                return ;
            }
            console.info(tableNames);
            $("#tableNames").val(tableNames);
            //隐藏 数据库表的 列表组件
            $("#jqGrid").jqGrid('setGridState','hidden');
            //隐藏界面div对象
            $("#jqGrid").hide();
            //隐藏分页对象
            $("#jqGridPager").hide();
            $("#columns").hide();

            $("#generatorCode").show();
            $("#generatorCodeZip").show();
            $("#tables").show();
            $("#jqGrid2").show();
            $("#jqGrid2").jqGrid('setGridState','visible');
            $("#jqGrid2").jqGrid({
                url: "sys/generator/columns?tables="+tableNames,
                datatype: "json",
                colModel: [
                    { label: '字段名称', name: 'columnName', width: 100, key: true},
                    { label: '字段备注', name: 'comments', width: 70,  editable: true},
                    { label: '字段长度', name: 'charmaxLength',width: 100 },
                    { label: '主键', name: 'columnKey', width: 100 },
                    { label: '允许空值', name: 'nullable', width: 100 ,  editable: true,edittype : "checkbox",editoptions : {value:'是:否'},
                        formatter:function(cellvalue, options, rowObject){
                            var temp="否";
                            if(cellvalue=="YES" || cellvalue=="是"){
                                temp = "是";
                            }
                            return temp;
                        }},
                    { label: '数据类型', name: 'dataType', width: 100 },
                    { label: '表单显示', name: 'isShowAdd', width: 100 ,  editable: true,edittype : "checkbox",editoptions : {value : '是:否'}},
                    { label: '列表显示', name: 'isShowList', width: 100 ,  editable: true,edittype : "checkbox",editoptions : {value : '是:否'}},
                    { label: '显示类型', name: 'showType', width: 100 ,  editable: true,edittype : "select",
                        editoptions : {value : "1:文本;2:日期时间;3:下拉框;4:单选框;5:多选框;6:多行文本;7:文件;8:树形;9:图片"}},
                    { label: '控件长度', name: 'inputlength', width: 100 ,  editable: true},
                    { label: '是否查询', name: 'isQuery', width: 100 ,  editable: true,edittype : "checkbox",editoptions : {value : '是:否'}},
                    { label: '查询类型', name: 'queryMode', width: 100 ,  editable: true,edittype : "select",
                        editoptions : {value : "0:为空;1:等于;2:不等于;3:大于;4:大于等于;5:小于;6:小于等于;7:like模糊查询;8:左模糊查询;9:右模糊查询"}},
                    { label: '验证规则', name: 'regex', width: 100 ,  editable: true,edittype : "select",
                        editoptions : {value : "0:为空;1:非空;2:唯一校验;3:电话号码;4:手机号码;5:电子邮件;6:网址;7:数字;8:整数;9:邮政编码;10:密码;11:身份证;12:中文;13:日期;14:日期时间;15:月份;16:天数;17:小数数字"}}
                ],
                reload:true,
                viewrecords: true,
                height: 385,
                rowNum: 30,
                rownumbers: true,
                rownumWidth: 25,
                autowidth:true,
                scrollrows:true,
                altRows: true,			//设置为交替行表格,默认为false
                multiselect: false,
                multiboxonly: true,
                cellsubmit: "clientArray",
                cellEdit: true, //启用单元格编辑功能，
                jsonReader : {
                    root: "page.list",
                    page: "page.currPage",
                    total: "page.totalPage",
                    records: "page.totalCount"
                },
                prmNames : {
                    page:"page",
                    rows:"limit",
                    order: "order"
                },
                gridComplete:function(){
                    //隐藏grid底部滚动条
                    $("#jqGrid2").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                    //隐藏表格列表第一行的复选框
                    $("#jqGrid2").jqGrid('hideCol','cb');
                }
            });

            //必须重新请求服务端 及重新加载表格 才能刷新 当前选择的表 的字段信息，否则会出现 表格数据不变化 还是上次选择的表字段信息
            $("#jqGrid2").jqGrid('setGridParam',{
                postData:{'tables2': tableNames},
                page:1,
                rows:50,
                reload:true
            }).trigger("reloadGrid");


        },
        download: function() {
            var tablesData = new Array();
            var columnsData = new Array();
            this.getformData(tablesData,columnsData);
            //用jquery 封装的$.ajax  目测不支持 post请求类型并返回 流的实现，这里只能用原生Ajax XMLReques对象进行处理
            var xhr = new XMLHttpRequest();
            xhr.datatype="json";
            //设置响应类型为blob类型
            xhr.responseType = "blob";
            xhr.onload = function () {
                if (this.status == "200") {
                    var fileName = "导出文件"+$("#tableNames").val()+"_"+new Date().getTime();
                    var data=this.response;
                    if (typeof window.navigator.msSaveBlob !== 'undefined') {
                        console.info(window.navigator.msSaveBlob);
                        window.navigator.msSaveBlob(new Blob([data]), fileName+'.zip');
                    }else{
                        console.info("他来了");
                        console.info(data);
                        let url = window.URL.createObjectURL(new Blob([data]));
                        let link = document.createElement('a');
                        link.style.display = 'none';
                        link.href = url;
                        link.setAttribute('download', fileName+'.zip');
                        document.body.appendChild(link);
                        link.click();
                        document.body.removeChild(link); //下载完成移除元素
                        window.URL.revokeObjectURL(url); //释放掉blob对象
                    }
                }
            }

            xhr.open("post", "sys/generator/codeZip", true);
            xhr.setRequestHeader('content-type', 'application/json');
            xhr.send(JSON.stringify({tables:JSON.stringify(tablesData),columns:JSON.stringify(columnsData) }));

        },
        getformData:function(tablesData,columnsData){
            tableNames = $("#tableNames").val();
            if(tableNames == null){
                return ;
            }
            console.info(tableNames);
            this.q.tables=tableNames;
            var obj = $("#jqGrid2");
            //获取grid表中所有的rowid值
            var rowIds = obj.getDataIDs();
            //初始化一个数组arrayData容器，用来存放rowData
            //var columnsData = new Array();
            if (rowIds.length > 0) {
                for (var i = 0; i < rowIds.length; i++) {
                    //rowData=obj.getRowData(rowid);//这里rowid=rowIds[i];
                    columnsData.push(obj.getRowData(rowIds[i]));
                }
            }
            //var tablesData = new Array();
            tablesData.push(this.q);
            console.info(JSON.stringify(tablesData));
            console.info(JSON.stringify(columnsData));
        },
        codeCreate: function() {
            var tablesData = new Array();
            var columnsData = new Array();
            this.getformData(tablesData,columnsData);
            $.ajax({
                url: "sys/generator/codeCreate",
                type: 'post',
                data:
                JSON.stringify({
                    tables: JSON.stringify(tablesData),
                    columns: JSON.stringify(columnsData)
                }),
                dataType:'json',
                success: function (data) {
                    if(data.code == 0){
                        alert("生成文件成功");
                        // location.reload()
                    }else {
                        alert(data.msg);
                    }

                }

            })
        },
        tablesShow: function() {
            //隐藏 字段相关表格
            $("#jqGrid2").jqGrid('setGridState','hidden');
            //隐藏界面div对象
            $("#jqGrid2").hide();
            $("#tables").hide();
            $("#generatorCode").hide();
            $("#generatorCodeZip").hide();

            //显示 数据表 相关表格
            $("#jqGrid").show();
            //分页对象
            $("#jqGridPager").show();
            $("#columns").show();
            $("#jqGrid").jqGrid('setGridState','visible');
            //tables();
        }
    }
});

