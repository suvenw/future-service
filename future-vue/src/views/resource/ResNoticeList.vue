<template>
  <a-card :bordered="false">

    <!-- 查询区域-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="公告标题">
              <a-input placeholder="请输入公告标题" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="12" :sm="8">
            <a-form-item label="发送时间">
              <a-range-picker :showTime="{ format: 'HH:mm:ss' }" format="YYYY-MM-DD HH:mm:ss" :value="createDate" @change="onChangeDate" />
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
    <resNoticeModal ref="modalForm" @ok="modalFormOk"></resNoticeModal>
  </a-card>
</template>

<script>
  import moment from 'moment'
  import resNoticeModal from './modules/ResNoticeModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction,fileUpload } from '@/api/manage'

  export default {
    name: "resNoticeList",
    mixins:[JeecgListMixin],
    components: {
      resNoticeModal
    },
    data () {
      return {
        description: '用户消息表管理页面',
        // 表头
        columns: [
           {
              title: 'ID',
              align:"center",
              dataIndex: 'id',
              width: 50
           },       
           {
            title: '标题',
            align:"center",
            dataIndex: 'title',
            width: 100
           },     
           {
            title: '内容',
            align:"center",
            dataIndex: 'content'
           },     
           {
            title: '链接地址',
            align:"center",
            dataIndex: 'url',
            width: 150
           },                        
           {
              title: '发送时间',
              align:"center",
              dataIndex: 'createDate',
              customRender:function (t,r,index) {
                if (r.createDate == 0) {
                  return ''
                }
                return moment(r.createDate).format("YYYY-MM-DD HH:mm:ss")
              },         
              width: 120
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
          list: "/resource/resnotice/list",
          detail: "/resource/resnotice/detail",
          delete: "/resource/resnotice/delete",
          deleteBatch: "/resource/resnotice/delete",
          exportXlsUrl: "resource/resnotice/export",
          importXlsUrl: "resource/resnotice/import",
        },
        ipagination: {
         pageSize: 10,
        },
        createDate: []
    }
  },
   methods: {
      onChangeDate (date, dateString) {
        this.createDate = date
        if (date[0] != undefined && date[1] != undefined ) {
          this.queryParam.startDate = moment(date[0],'YYYY-MM-DD HH:mm:ss').valueOf()
          this.queryParam.endDate = moment(date[1],'YYYY-MM-DD HH:mm:ss').valueOf()
        }
      },
      searchReset () {
        this.queryParam={};
        this.createDate = []
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