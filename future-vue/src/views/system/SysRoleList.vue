<template>
  <a-card :bordered="false">

    <!-- 查询区域 11111-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

        <a-col :md="6" :sm="8">
            <a-form-item label="id">
              <a-input placeholder="请输入id" v-model="queryParam.id"></a-input>
            </a-form-item>
          </a-col>
        <a-col :md="6" :sm="8">
            <a-form-item label="创建时间">
              <a-input placeholder="请输入创建时间" v-model="queryParam.createDate"></a-input>
            </a-form-item>
          </a-col>
        <a-col :md="6" :sm="8">
            <a-form-item label="更新时间">
              <a-input placeholder="请输入更新时间" v-model="queryParam.modifyDate"></a-input>
            </a-form-item>
          </a-col>
        <a-col :md="6" :sm="8">
            <a-form-item label="删除状态(1-正常,0-已删除)">
              <a-input placeholder="请输入删除状态(1-正常,0-已删除)" v-model="queryParam.status"></a-input>
            </a-form-item>
          </a-col>
        <a-col :md="6" :sm="8">
            <a-form-item label="排序字段,默认按升级排序">
              <a-input placeholder="请输入排序字段,默认按升级排序" v-model="queryParam.sort"></a-input>
            </a-form-item>
          </a-col>

        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="角色名称">
              <a-input placeholder="请输入角色名称" v-model="queryParam.roleName"></a-input>
            </a-form-item>
          </a-col>
        <a-col :md="6" :sm="8">
            <a-form-item label="角色编码">
              <a-input placeholder="请输入角色编码" v-model="queryParam.roleCode"></a-input>
            </a-form-item>
          </a-col>
        <a-col :md="6" :sm="8">
            <a-form-item label="描述">
              <a-input placeholder="请输入描述" v-model="queryParam.description"></a-input>
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
      <a-button @click="handleAdd" type="primary" icon="plus">新增(抽屉模式)</a-button>
      <a-button @click="handleAdd2" type="primary" icon="plus">新增(弹出模式)</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('导出角色信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false"  :customRequest="customRequest"  @change="handleChanges">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-menu-item>
                  <a href="javascript:;" @click="handleDetail(record)">详情</a>
                </a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <SysRoleModal ref="modalForm" @ok="modalFormOk"></SysRoleModal>
    <SysRoleModal2 ref="modalForm2" @ok="modalFormOk"></SysRoleModal2>
  </a-card>
</template>

<script>
  import SysRoleModal from './modules/SysRoleModal'
  import SysRoleModal2 from './modules/SysRoleModal2'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { postAction,fileUpload } from '@/api/manage'

  export default {
    name: "sysRoleList",
    mixins:[JeecgListMixin],
    components: {
      SysRoleModal,SysRoleModal2
    },
    data () {
      return {
        description: '角色表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
                     		              {
            title: '创建时间',
            align:"center",
            dataIndex: 'createDate'
           },
           		              {
            title: '更新时间',
            align:"center",
            dataIndex: 'modifyDate'
           },
           		              {
            title: '删除状态(1-正常,0-已删除)',
            align:"center",
            dataIndex: 'status'
           },
           		              {
            title: '排序字段,默认按升级排序',
            align:"center",
            dataIndex: 'sort'
           },
           		              {
            title: '角色名称',
            align:"center",
            dataIndex: 'roleName'
           },
           		              {
            title: '角色编码',
            align:"center",
            dataIndex: 'roleCode'
           },
           		              {
            title: '描述',
            align:"center",
            dataIndex: 'description'
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
          list: "/sys/sysRole/list",
          delete: "/sys/sysRole/delete",
          deleteBatch: "/sys/sysRole/delete",
          exportXlsUrl: "/sys/sysRole/export",
          importXlsUrl: "/sys/sysRole/import",
       },
    }
  },
  computed: {
    importAction:function () {
      return this.url.importXlsUrl;
    },
  },
    methods: {


      handleChanges (info) {
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