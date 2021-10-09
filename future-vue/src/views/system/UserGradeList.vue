<template>
  <a-card :bordered="false">

    <!-- 查询区域 11111-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="4" :sm="6">
            <a-form-item label="等级">
                            <a-input placeholder="请输入等级" v-model="queryParam.grade"></a-input>
                        </a-form-item>
          </a-col>

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

        <template slot="iconslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="record.icon" @click="handlePreview(record)" />
          </div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 图片预览   -->
    <a-modal :visible="previewVisible" :footer="null" @cancel="previewHandleCancel">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>

    <!-- 表单区域 -->
    <userGradeModal ref="modalForm" @ok="modalFormOk"></userGradeModal>
  </a-card>
</template>

<script>
  import userGradeModal from './modules/UserGradeModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction,fileUpload } from '@/api/manage'

  export default {
    name: "userGradeList",
    mixins:[JeecgListMixin],
    components: {
      userGradeModal
    },
    data () {
      return {
        description: '用户等级管理页面',
        // 表头
        columns: [
            {
                title: '等级',
                align:"center",
                dataIndex: 'grade'
            },
           		              {
            title: '等级称谓',
            align:"center",
            dataIndex: 'gradeDesc'
           },
            {
                title: '勋章图标',
                align:"center",
                width: 120,
                scopedSlots: {customRender: "iconslot"}
            },
            {
                title: '等级升级所需经验',
                align:"center",
                dataIndex: 'gradeExp'
            },
            {
                title: '拍摄时长（秒）',
                align:"center",
                dataIndex: 'playLength'
            },
           	{
            title: '每日观看次数',
            align:"center",
            dataIndex: 'playCount'
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
          list: "/sys/userGrade/list",
          delete: "/sys/userGrade/delete",
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
          total: 0
        },
        previewImage: '',
        previewVisible: false,
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
      searchReset() {
          this.previewVisible = false;
          this.loadData();
      },
      handlePreview(record) {
          console.log(record);
          this.previewImage = record.icon;
          this.previewVisible = true;
      },
      previewHandleCancel () {
          this.previewVisible = false;
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