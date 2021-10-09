<template>
  <a-card :bordered="false">

    <!-- 查询区域 11111-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

        <a-col :md="6" :sm="8">
            <a-form-item label="公告内容">
              <a-input placeholder="请输入标题" v-model="queryParam.content"></a-input>
            </a-form-item>
          </a-col>


          <a-col :md="12" :sm="8">
            <a-form-item label="创建时间">
              <a-range-picker :showTime="{ format: 'HH:mm:ss' }" format="YYYY-MM-DD HH:mm:ss" :value="validDate"
                              @change="onChangeDate"/>
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
    <noticeModal ref="modalForm" @ok="modalFormOk"></noticeModal>
  </a-card>
</template>

<script>
  import moment from 'moment'
  import noticeModal from './modules/NoticeModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction,fileUpload } from '@/api/manage'
  import JDate from '@/components/jeecg/JDate'

  export default {
    name: "noticeList",
    mixins:[JeecgListMixin],
    components: {
      noticeModal,
      JDate
    },
    data () {
      return {
        description: '用户消息表管理页面',
         // 查询条件
        queryParam: {title:'',createDateBegin:'',createDateEnd:''},
        // 表头
        columns: [
          {
              title: '排序号',
              align:"center",
              dataIndex: 'sort'
          },
          {
            title: '公告内容',
            align:"center",
            dataIndex: 'content'
           },
            {
            title: '创建时间',
            align:"center",
            dataIndex: 'createDate',
            customRender:function (value) {
              return moment(value).format('YYYY-MM-DD HH:mm:ss')
            }
           },
           {
            title: '修改时间',
            align:"center",
            dataIndex: 'modifyDate',
            customRender:function (value) {
              return moment(value).format('YYYY-MM-DD HH:mm:ss')
            }
           },
          {
            title: '操作人',
            align:"center",
            dataIndex: 'operator'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        confirmLoading: false,
		url: {
          list: "/sys/notice/list",
          delete: "/sys/notice/delete",
          deleteBatch: "/sys/notice/delete",
          exportXlsUrl: "sys/notice/export",
          importXlsUrl: "sys/notice/import",
       },
         ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0,
          validDate: [],
        }
    }
  },
  computed: {
    iimportAction:function () {
      return this.url.importXlsUrl;
    },
  },
    methods: {

      handleChange (info) {
        console.log(info.file);
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          this.$message.success(info.file.name+'文件上传成功');
        } else if (info.file.status === 'error') {
          this.$message.error(info.file.name+'文件上传失败');
        }
      },
      customRequest:function({ onSuccess, onError, file }) {
        postAction(this.importAction,fileUpload(file)).then((res)=>{
          if(res.code ==0){
            onSuccess(null, file);
          }else{
            onError();
          }
        }).finally(() => {
          this.confirmLoading = false;
        })
      },
      onChangeDate(date) {
          this.validDate = date
          if (date[0] != undefined && date[1] != undefined) {
              this.queryParam.createDateBegin = moment(date[0], 'YYYY-MM-DD HH:mm:ss').valueOf()
              this.queryParam.createDateEnd = moment(date[1], 'YYYY-MM-DD HH:mm:ss').valueOf()
          }
      },

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