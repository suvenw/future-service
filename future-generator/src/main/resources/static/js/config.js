$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/config/list',
        datatype: "json",
        colModel: [
            { label: '使用模板', name: 'tempEnum', width: 80 },
			{ label: '数据库类型', name: 'databaseType', width: 80, key: true },
            { label: 'Dao类名称', name: 'entityDao', width: 80 },
            { label: '基础Bean是否状态和排序', name: 'entity', width: 80 },
            { label: '只生成界面Val', name: 'pageVal', width: 80 },
            { label: '是否使用Dubbo', name: 'dubbo', width: 80 },
            { label: '是否使用SpringMVC', name: 'mvc', width: 80 },
             { label: '是否强制覆盖Bean:', name: 'isOverrideWrite', width: 80 }

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
            databaseType: '',
            dubbo: '',
            mvc:'',
            isOverrideWrite:'',
            pageVal:'',
            tempEnum : '',
            entity : '',
            entityDao: ''
		}
	},
    computed: {
        filterParamsStr: function () {
            var params = {}
            var paramsStr = ''
            if (this.q.databaseType) {
                params.databaseType = this.q.databaseType
            } else {
                delete params.databaseType
            }

            if (this.q.dubbo) {
                params.dubbo = this.q.dubbo
            } else {
                delete params.dubbo
            }
            if (this.q.mvc) {
                params.mvc = this.q.mvc
            } else {
                delete params.mvc
            }
             if (this.q.isOverrideWrite) {
                params.isOverrideWrite = this.q.isOverrideWrite
            } else {
                delete params.isOverrideWrite
            }
            if (this.q.pageVal) {
                params.pageVal = this.q.pageVal
            } else {
                delete params.pageVal
            }
            if (this.q.tempEnum) {
                params.tempEnum = this.q.tempEnum
            } else {
                delete params.tempEnum
            }
            if (this.q.entity) {
                params.entity = this.q.entity
            } else {
                delete params.entity
            }
            if (this.q.entityDao) {
                params.entityDao = this.q.entityDao
            } else {
                delete params.entityDao
            }

            console.log(params)
            for(var key in params) {
                paramsStr += '&' + key + '=' + params[key]
            }
            return paramsStr
        }
    },
	methods: {
        config: function() {

            $.ajax({
                url: "sys/config/update?" +  this.filterParamsStr,
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

		}
	}

});

