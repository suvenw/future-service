$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/path/list',
        datatype: "json",
        colModel: [			
			{ label: '序号唯一标识', name: 'id', width: 80, key: true },
            { label: '用户名', name: 'userNme', width: 80 },
            { label: '项目根路径', name: 'baseProjectPath', width: 280 },
            { label: 'html路径', name: 'htmlPath', width: 80 },
            { label: 'HTTP项目路径', name: 'httpProjectPath', width: 80 },
            { label: 'RPC项目路径', name: 'rpcProjectPath', width: 80 },
            { label: 'API项目路径', name: 'apiProjectPath', width:80 },
            { label: 'SYS项目路径', name: 'sysProjectPath', width:80 },
            { label: '模板项目路径', name: 'templatesPath', width: 80 },
            { label: '是否使用', name: 'isUse', width: 80 }

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
            id: '',
            userNme: '',
            htmlPath: '',
            baseProjectPath: '',
            httpProjectPath:'',
            rpcProjectPath:'',
            apiProjectPath : '',
            sysProjectPath : '',
            templatesPath: '',
            isUse: ''
		}
	},
    computed: {
        filterParamsStr: function () {
            var params = {}
            var paramsStr = ''
            if (this.q.id) {
                params.id = this.q.id
            } else {
                delete params.id
            }

            if (this.q.userNme) {
                params.userNme = this.q.userNme
            } else {
                delete params.userNme
            }
            if (this.q.htmlPath) {
                params.htmlPath = this.q.htmlPath
            } else {
                delete params.htmlPath
            }
            if (this.q.baseProjectPath) {
                params.baseProjectPath = this.q.baseProjectPath
            } else {
                delete params.baseProjectPath
            }
            if (this.q.httpProjectPath) {
                params.httpProjectPath = this.q.httpProjectPath
            } else {
                delete params.httpProjectPath
            }
            if (this.q.rpcProjectPath) {
                params.rpcProjectPath = this.q.rpcProjectPath
            } else {
                delete params.rpcProjectPath
            }
            if (this.q.apiProjectPath) {
                params.apiProjectPath = this.q.apiProjectPath
            } else {
                delete params.apiProjectPath
            }
            if (this.q.sysProjectPath) {
                params.sysProjectPath = this.q.sysProjectPath
            } else {
                delete params.sysProjectPath
            }
            if (this.q.templatesPath) {
                params.templatesPath = this.q.templatesPath
            } else {
                delete params.templatesPath
            }
            if (this.q.isUse) {
                params.isUse = this.q.isUse
            } else {
                delete params.isUse
            }

            console.log(params)
            for(var key in params) {
                paramsStr += '&' + key + '=' + params[key]
            }
            return paramsStr
        }
    },
	methods: {
        pathUpdate: function() {

            $.ajax({
                url: "sys/path/update?" +  this.filterParamsStr,
                type:'get',
                dataType:'json',
                success: function (data) {
                    if(data.code == 0){
                        location.reload()
                    }else {
                        alert(data.msg);
                    }

                }

            })

		},
        select: function() {
            var path = getSelectedRowVal();
            if(path == null){
                return ;
            }
            this.q.id=path.id;
            this.q.userNme=path.userNme;
            this.q.htmlPath=path.htmlPath;
            this.q.baseProjectPath=path.baseProjectPath;
            this.q.httpProjectPath=path.httpProjectPath;
            this.q.rpcProjectPath=path.rpcProjectPath;
            this.q.apiProjectPath=path.apiProjectPath;
            this.q.templatesPath=path.templatesPath;
            this.q.isUse=path.isUse;


        }
	}

});

