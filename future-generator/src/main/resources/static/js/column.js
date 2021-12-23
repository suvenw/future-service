$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/generator/columns',
        datatype: "json",
        colModel: [			
			{ label: '字段名', name: 'columnName', width: 100, key: true },
			{ label: '数据类型', name: 'dataType', width: 70},
			{ label: '描述', name: 'comments', width: 100 },
			{ label: 'dataType', name: 'dataType', width: 100 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50,100,200],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
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
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			tableName: null,
            mainPath: 'com.sixeco.framework',
            packageName: 'com.sixeco.car',
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
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'tableName': vm.q.tableName},
                page:1
            }).trigger("reloadGrid");
        },
        columns: function(){
            var tableNames =   getSelectedRow();
            if(tableNames == null){
                return ;
            }
            $.ajax({
                url: "sys/generator/columns?tables=" + tableNames,
                type:'get',
                dataType:'json',
                success: function (data) {
                    if(data.code == 0){

                        alert("数据返回成功,待开发");
                        // location.reload()
                    }else {
                        alert(data.msg);
                    }

                }

            })
        },
        download: function() {
            var tableNames = getSelectedRows();
            if(tableNames == null){
                return ;
            }
            // console.log("sys/generator/code?tables=" + tableNames.join()+ this.filterParamsStr)
            location.href = "sys/generator/code?tables=" + tableNames.join()+ '&'+ this.filterParamsStr;
        },
        generator: function() {
            var tableNames = getSelectedRows();
            if(tableNames == null){
                return ;
            }
            $.ajax({
                url: "sys/generator/file?tables=" + tableNames.join()+ '&'+ this.filterParamsStr,
                type:'get',
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

        }
    }
});

