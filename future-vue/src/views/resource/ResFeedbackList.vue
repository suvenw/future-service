<template>
  <a-card :bordered="false">

    <!-- 查询区域-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="反馈类型">
              <a-select v-model="queryParam.feedbackType" placeholder="请选择反馈类型">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">功能问题</a-select-option>
                <a-select-option value="2">系统问题</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="状态">
              <a-select v-model="queryParam.replyStatus" placeholder="请选择状态查询">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">未回复</a-select-option>
                <a-select-option value="2">已回复</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
           <a-col :span="6">
              <a-form-item label="ID">
                <a-input-number style="width: 100%" placeholder="请输入ID" v-model="queryParam.userId"></a-input-number>
              </a-form-item>
            </a-col>

          <template v-if="toggleSearchStatus">
             <a-col :md="12" :sm="8">
              <a-form-item label="反馈时间">
                <a-range-picker :showTime="{ format: 'HH:mm:ss' }" format="YYYY-MM-DD HH:mm:ss" :value="feedbackDate" @change="onChangeDate" />
              </a-form-item>
            </a-col>     
           
          </template>                           

          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
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

        <template slot="contentslot" slot-scope="text, record, index">
          <a-tooltip placement="top" >
            <template slot="title">
              <span>{{ record.content }}</span>
            </template>
            <span v-if="record.content.length > 60" >{{ record.content.substring(0,60)+'...' }}</span>
            <span v-else>{{ record.content }}</span>
          </a-tooltip>
        </template> 
        <span slot="action" slot-scope="text, record">
          <a v-if="record.replyStatus == 1" @click="handleEdit(record)">回复</a>
          <a v-else @click="handleEdit(record)">详情</a>
          <a-divider type="vertical" />
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <resFeedbackModal ref="modalForm" @ok="modalFormOk"></resFeedbackModal>
  </a-card>
</template>

<script>
  import moment from 'moment'
  import resFeedbackModal from './modules/ResFeedbackModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction, fileUpload, getAction } from '@/api/manage'

  export default {
    name: "resFeedbackList",
    mixins:[JeecgListMixin],
    components: {
      resFeedbackModal
    },
    data () {
      return {
        description: '用户反馈问题管理页面',
        // 表头
        columns: [
          {
            title: 'ID',
            align: "center",
            dataIndex: 'id',
            width: 50
          },
          {
            title: '反馈类型',
            align:"center",
            dataIndex: 'feedbackType',
            width: 80,
            customRender:function (t,r,index) {
              if(r.feedbackType == 1) {
               return "功能问题";
              }else if(r.feedbackType == 2) {
               return "系统问题";
              }else{
                return "未知";
              }
            }
           },
           {
            title: '号',
            align:"center",
            dataIndex: 'userId',
            width: 80
           },
           {
            title: '反馈内容',
            align:"center",
            scopedSlots: {customRender: "contentslot"}
           },
           {
            title: '反馈时间',
            align:"center",
            dataIndex: 'createDate',
            customRender:function (t,r,index) {
              if (r.createDate == 0) {
                return ''
              }
              return moment(r.createDate).format("YYYY-MM-DD HH:mm:ss")
            },
            width: 120,
            sorter: true
           },          
           {
            title: '状态',
            align:"center",
            dataIndex: 'replyStatus',
            customRender:function (t,r,index) {
              if(r.replyStatus == 1) {
               return "未回复";
              }else if(r.replyStatus == 2) {
               return "已回复";
              }else{
                return "未知";
              }
            },
            width: 80
           },
           {
            title: '回复时间',
            align:"center",
            dataIndex: 'modifyDate',
            customRender:function (t,r,index) {
              if (r.modifyDate == 0) {
                return ''
              }
              return moment(r.modifyDate).format("YYYY-MM-DD HH:mm:ss")
            },
            width: 120,
            sorter: true
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
            width: 100
          }
        ],
        confirmLoading: false,
        //界面请求URL
    		url: {
          list: "/resource/resfeedback/list",
          detail: "/resource/resfeedback/detail",
          delete: "/resource/resfeedback/delete",
          deleteBatch: "/resource/resfeedback/delete"
        },
        ipagination: {
         pageSize: 10,
        },
        feedbackDate: [],
      }
    },
    methods: {
      onChangeDate (date, dateString) {
        this.feedbackDate = date
        if (date[0] != undefined && date[1] != undefined ) {
          this.queryParam.startDate = moment(date[0],'YYYY-MM-DD HH:mm:ss').valueOf()
          this.queryParam.endDate = moment(date[1],'YYYY-MM-DD HH:mm:ss').valueOf()
        }
      },
      searchReset () {
        this.queryParam={};
        this.feedbackDate = []
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