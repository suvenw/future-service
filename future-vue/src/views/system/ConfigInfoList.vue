<template>
  <a-card :bordered="false">

    <!-- 查询区域-->
<!--    <div class="table-page-search-wrapper">-->
<!--      <a-form layout="inline">-->
<!--        <a-row :gutter="24">-->
<!--          <a-col :md="6" :sm="8" >-->
<!--            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">-->
<!--              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>-->
<!--              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>-->
<!--              <a @click="handleToggleSearch" style="margin-left: 8px">-->
<!--                {{ toggleSearchStatus ? '收起' : '展开' }}-->
<!--                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
<!--              </a>-->
<!--            </span>-->
<!--          </a-col>-->

<!--        </a-row>-->
<!--      </a-form>-->
<!--    </div>-->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--    </div>-->

<!--    &lt;!&ndash; table区域-begin &ndash;&gt;-->
<!--    <div>-->
<!--      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">-->
<!--        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项-->
<!--        <a style="margin-left: 24px" @click="onClearSelected">清空</a>-->
<!--      </div>-->

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
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <configInfoModal ref="modalForm" @ok="modalFormOk"></configInfoModal>
  </a-card>
</template>

<script>
  import configInfoModal from './modules/ConfigInfoModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction,fileUpload } from '@/api/manage'

  export default {
    name: "configInfoList",
    mixins:[JeecgListMixin],
    components: {
      configInfoModal
    },
    data () {
      return {
        description: '配置信息KEY-VALUE管理页面',
        // 表头
        columns: [
          {
            title: '序号',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
            {
                title: '功能描述',
                align:"center",
                dataIndex: 'configDescribe'
            },
            {
            title: '环境配置自定义key对应的value',
            align:"center",
            dataIndex: 'configValue'
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
          list: "/sys/config/list",
          delete: "/sys/config/delete",
          deleteBatch: "/sys/config/delete",
          exportXlsUrl: "sys/config/export",
          importXlsUrl: "sys/config/import",
       },
       ipagination: {
            pageSize: 10,
       }
    }
  },
  computed: {
    iimportAction:function () {
      return this.url.importXlsUrl;
    },
  },
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