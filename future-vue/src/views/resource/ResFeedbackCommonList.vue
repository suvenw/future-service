<template>
  <a-card :bordered="false">

    <!-- 查询区域-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="问题">
              <a-input placeholder="请输入问题" v-model="queryParam.question"></a-input>
          </a-form-item>
          </a-col>
          <a-col :md="12" :sm="8">
            <a-form-item label="更新时间">
              <a-range-picker :showTime="{ format: 'HH:mm:ss' }" format="YYYY-MM-DD HH:mm:ss" :value="modifyDate" @change="onChangeDate"/>
            </a-form-item>
          </a-col>     

          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <resFeedbackCommonModal ref="modalForm" @ok="modalFormOk"></resFeedbackCommonModal>
  </a-card>
</template>

<script>
  import moment from 'moment'
  import resFeedbackCommonModal from './modules/ResFeedbackCommonModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction,fileUpload } from '@/api/manage'

  export default {
    name: "resFeedbackCommonList",
    mixins:[JeecgListMixin],
    components: {
      resFeedbackCommonModal
    },
    data () {
      return {
        description: '系统常见问题（反馈的问题和举报的问题数据）管理页面',
        // 表头
        columns: [
          {
            title: 'ID',
            align:"center",
            dataIndex: 'id'
           },
           {
            title: '常见问题',
            align:"center",
            dataIndex: 'question'
           }, 
           {
            title: '回答',
            align:"center",
            dataIndex: 'answer'
           },                    
           {
            title: '更新时间',
            align:"center",
            dataIndex: 'modifyDate',
            customRender:function (t,r,index) {
              if (r.modifyDate == 0) {
                return ''
              }
              return moment(r.modifyDate).format("YYYY-MM-DD HH:mm:ss")
            }              
           },
           {
             title: '操作人编号',
             align:"center",
             width: 120,
             dataIndex: 'operator',
             customRender:function (t,r,index) {
               if(r.operator == 0 || r.operator == null) {
                return "";
               }else{
                 return r.operator;
               }
             }   
            },  
           {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        confirmLoading: false,
        //界面请求URL
		    url: {
          list: "/resource/resfeedbackcommon/list",
          detail: "/resource/resfeedbackcommon/detail",
          delete: "/resource/resfeedbackcommon/delete",
          deleteBatch: "/resource/resfeedbackcommon/delete",
          exportXlsUrl: "resource/resfeedbackcommon/export",
          importXlsUrl: "resource/resfeedbackcommon/import",
        },
        ipagination: {
         pageSize: 10
        },
        modifyDate: []
    }
  },
  methods: {
      onChangeDate (date, dateString) {
        this.modifyDate = date
        if (date[0] != undefined && date[1] != undefined ) {
          this.queryParam.startDate = moment(date[0],'YYYY-MM-DD HH:mm:ss').valueOf()
          this.queryParam.endDate = moment(date[1],'YYYY-MM-DD HH:mm:ss').valueOf()
        }
      },
      searchReset () {
        this.queryParam={};
        this.modifyDate = []
        this.loadData(1);
      }
    }
  }
</script>
<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }
  .ant-card-body .table-operator{
    margin-bottom: 18px;
  }
  .ant-table-tbody .ant-table-row td{
    padding-top:15px;
    padding-bottom:15px;
  }
  .anty-row-operator button{margin: 0 5px}
  .ant-btn-danger{background-color: #ffffff}

  .ant-modal-cust-warp{height: 100%}
  .ant-modal-cust-warp .ant-modal-body{height:calc(100% - 110px) !important;overflow-y: auto}
  .ant-modal-cust-warp .ant-modal-content{height:90% !important;overflow-y: hidden}
</style>