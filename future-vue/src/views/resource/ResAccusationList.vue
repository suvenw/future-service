<template>
  <a-card :bordered="false">

    <!-- 查询区域-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="状态">
              <a-select v-model="queryParam.isReply" placeholder="请选择反馈类型">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">未处理</a-select-option>
                <a-select-option value="2">已处理</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="举报分类">
              <a-select v-model="queryParam.questionId" placeholder="请选择反馈类型">
                <a-select-option value="">全部</a-select-option>
                <a-select-option v-for="item in categoryList" :key="item.id">{{item.question}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="视频ID">
                <a-input-number style="width: 100%"  placeholder="请输入视频ID" v-model="queryParam.videoId"></a-input-number>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="ID">
                  <a-input-number style="width: 100%"  placeholder="请输入ID" v-model="queryParam.userAccusationId"></a-input-number>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="举报类型">
                <a-select v-model="queryParam.accusationType" placeholder="请选择反馈类型">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option value="1">视频</a-select-option>
                  <a-select-option value="2">用户</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>                   
             <a-col :md="12" :sm="8">
              <a-form-item label="举报时间">
                <a-range-picker :showTime="{ format: 'HH:mm:ss' }" format="YYYY-MM-DD HH:mm:ss" :value="accusationDate" @change="onChangeDate" />
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

        <span slot="action" slot-scope="text, record">
          <a v-if="record.isReply == 0 || record.isReply == 1 " @click="handleEdit(record)">处理</a>
          <a v-else @click="handleEdit(record)">查看详情</a>          
          <a-divider type="vertical" />
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <resAccusationModal ref="modalForm" @ok="modalFormOk"></resAccusationModal>
  </a-card>
</template>

<script>
  import moment from 'moment'
  import resAccusationModal from './modules/ResAccusationModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction, fileUpload, getAction } from '@/api/manage'

  export default {
    name: "resAccusationList",
    mixins:[JeecgListMixin],
    components: {
      resAccusationModal
    },
    data () {
      return {
        description: '举报表（视频或用户）管理页面',
        // 表头
        columns: [
          {
            title: 'ID',
            align:"center",
            dataIndex: 'id'
           },
           {
            title: '举报类型',
            align:"center",
            dataIndex: 'accusationType',
            customRender:function (t,r,index) {
              if(r.accusationType == 1) {
               return "视频举报";
              }else if(r.accusationType == 2) {
               return "用户举报";
              }else{
                return "未知";
              }
            }
           },
           {
            title: '举报分类',
            align:"center",
            dataIndex: 'category'
           },
           {
            title: '举报理由',
            align:"center",
            dataIndex: 'reason'
           },  
           {
            title: '内容描述',
            align:"center",
            dataIndex: 'content'
           },                     
           {
            title: '用户ID',
            align:"center",
            dataIndex: 'userAccusationId'
           },
           {
            title: '视频ID',
            align:"center",
            dataIndex: 'videoId',
            customRender:function (t,r,index) {
              if (r.videoId == 0 || r.videoId == null) {
                return '无'
              }
              return r.videoId
            }            
           },
           {
            title: '举报人',
            align:"center",
            dataIndex: 'userId'
           },
           {
            title: '举报时间',
            align:"center",
            dataIndex: 'createDate',
            customRender:function (t,r,index) {
              if (r.createDate == 0 || r.createDate == null) {
                return ''
              }
              return moment(r.createDate).format("YYYY-MM-DD HH:mm:ss")
            }
           },
           {
            title: '状态',
            align:"center",
            dataIndex: 'isReply',
            customRender:function (t,r,index) {
              if(r.isReply == 1) {
               return "未处理";
              }if(r.isReply == 2) {
               return "已处理";
              }else{
                return "未知";
              }
            }
           },
           {
            title: '处理时间',
            align:"center",
            dataIndex: 'replyDate',
            customRender:function (t,r,index) {
              if (r.replyDate == 0 || r.replyDate == null) {
                return ''
              }
              return moment(r.replyDate).format("YYYY-MM-DD HH:mm:ss")
            }            
           },           
           {
            title: '处理结果',
            align:"center",
            dataIndex: 'replyContent'
           },
           {
             title: '操作人编号',
             align:"center",
             dataIndex: 'operator',
             customRender:function (t,r,index) {
               if(r.operator == 0 || r.operator == null) {
                return "";
               }
               return r.operator;
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
          list: "/resource/resaccusation/list",
          detail: "/resource/resaccusation/detail",
          delete: "/resource/resaccusation/delete",
          deleteBatch: "/resource/resaccusation/delete",
          categorylist: "/resource/resfeedbackcommon/categoryList",
        },
        ipagination: {
         pageSize: 10,
        },
        accusationDate: [],
        categoryList: []
    }
  },
    created() {
      this.initCategoryList();
    },
    methods: {
      onChangeDate (date, dateString) {
        this.accusationDate = date
        if (date[0] != undefined && date[1] != undefined ) {
          this.queryParam.startDate = moment(date[0],'YYYY-MM-DD HH:mm:ss').valueOf()
          this.queryParam.endDate = moment(date[1],'YYYY-MM-DD HH:mm:ss').valueOf()
        }
      },
      searchReset () {
        this.queryParam={};
        this.accusationDate = []
        this.loadData(1);
      },
      initCategoryList () {
        getAction(this.url.categorylist,{}).then((res)=>{
          if(res.code ==0){
            this.categoryList = res.data
          }
        }).finally(() => {
        })
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