<template>
  <a-card :bordered="false">

    <!-- 查询区域-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="4" :sm="6">
            <a-form-item label="平台">
              <a-select v-model="queryParam.platform" placeholder="请选择平台">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">IOS</a-select-option>
                <a-select-option value="2">Android</a-select-option>
                <a-select-option value="3">PC</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :md="4" :sm="6">
            <a-form-item label="强制更新">
              <a-select v-model="queryParam.forceUpdate" placeholder="请选择是否强制更新">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">是</a-select-option>
                <a-select-option value="2">否</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :md="12" :sm="8">
            <a-form-item label="更新时间">
              <a-range-picker :showTime="{ format: 'HH:mm:ss' }" format="YYYY-MM-DD HH:mm:ss" :value="publishDate"
                              @change="onChangeDate"/>
            </a-form-item>
          </a-col>

          <!--          <a-col :md="10" :sm="16">-->
          <!--            <a-form-item label="强制更新时间">-->
          <!--              <j-date v-model="queryParam.publishStartDate" :showTime="true" date-format="YYYY-MM-DD HH:mm:ss" style="width:45%" placeholder="请选择更新开始时间" ></j-date>-->
          <!--              <span style="width: 10px;">~</span>-->
          <!--              <j-date v-model="queryParam.publishEndDate" :showTime="true" date-format="YYYY-MM-DD HH:mm:ss" style="width:45%" placeholder="请选择更新结束时间"></j-date>-->
          <!--            </a-form-item>-->
          <!--          </a-col>-->

          <a-col :md="6" :sm="8">
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
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{
        selectedRowKeys.length }}</a>项
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

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;" @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

    </a-table>
    </div>
    <!--     table区域-end -->

    <!-- 表单区域 -->
    <versionInfoModal ref="modalForm" @ok="modalFormOk"></versionInfoModal>
  </a-card>
</template>

<script>
    import versionInfoModal from './modules/VersionInfoModal'
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import {postAction, fileUpload} from '@/api/manage'
    // import JDate from '@/components/jeecg/JDate'
    import moment from 'moment'

    export default {
        name: "versionInfoList",
        mixins: [JeecgListMixin],
        components: {
            versionInfoModal,
            // JDate
        },
        data() {
            return {
                description: 'App版本信息管理页面',
                // 查询条件
                queryParam: {platform: '', forceUpdate: '', publishStartDate: '', publishEndDate: ''},
                // 表头
                columns: [
                    {
                        title: '#',
                        dataIndex: '',
                        key: 'rowIndex',
                        width: 60,
                        align: "center",
                        customRender: function (t, r, index) {
                            return parseInt(index) + 1;
                        }
                    },
                    {
                        title: '平台',
                        align: "center",
                        dataIndex: 'platform',
                        customRender: function (t, r, index) {
                            if (r.platform == 1) {
                                return "IOS";
                            } else if (r.platform == 2) {
                                return "Android";
                            } else {
                                return "PC";
                            }
                        }
                    },
                    {
                        title: '版本名称;',
                        align: "center",
                        dataIndex: 'version',
                        customRender: function (t, r, index) {
                            return "V" + r.version;
                        }
                    },
                    {
                        title: 'url地址',
                        align: "center",
                        dataIndex: 'download'
                    },
                    {
                        title: '强制更新',
                        align: "center",
                        dataIndex: 'forceUpdate',
                        customRender: function (t, r, index) {
                            if (r.forceUpdate == 1) {
                                return "是"
                            } else {
                                return "否"
                            }
                        }
                    },
                    {
                        title: '更新时间',
                        align: "center",
                        dataIndex: 'publishDate',
                        customRender: function (t, r, index) {
                            if (r.publishDate == 0) {
                                return ''
                            }
                            return moment(r.publishDate).format("YYYY-MM-DD")
                        }
                    },
                    {
                        title: '操作',
                        dataIndex: 'action',
                        align: "center",
                        scopedSlots: {customRender: 'action'},
                    }
                ],
                confirmLoading: false,
                //界面请求URL
                url: {
                    list: "/resource/version/list",
                    detail: "/resource/version/detail",
                    delete: "/resource/version/delete",
                },
                ipagination: {
                    pageSize: 10,
                },
                publishDate: [],
            }
        },
        computed: {
            importAction: function () {
                return this.url.importXlsUrl;
            },
        },
        methods: {
            //文件上传触发事件
            handleChange(info) {
                console.log(info.file);
                if (info.file.status !== 'uploading') {
                    console.log(info.file, info.fileList);
                }
                if (info.file.status === 'done') {
                    this.$message.success(info.file.name + '文件上传成功');
                } else if (info.file.status === 'error') {
                    this.$message.error(info.file.name + '文件上传失败');
                }
            },
            //文件上传 自定义请求 进行上传
            customRequest: function ({onSuccess, onError, file}) {
                postAction(this.importAction, fileUpload(file)).then((res) => {
                    if (res.code == 0) {
                        onSuccess(null, file);
                    } else {
                        onError();
                    }
                }).finally(() => {
                    this.confirmLoading = false;
                })
            },
            onChangeDate(date) {
                this.publishDate = date
                if (date[0] != undefined && date[1] != undefined) {
                    this.queryParam.publishStartDate = moment(date[0], 'YYYY-MM-DD HH:mm:ss').valueOf()
                    this.queryParam.publishEndDate = moment(date[1], 'YYYY-MM-DD HH:mm:ss').valueOf()
                }
            },
            searchReset() {
                this.queryParam = {};
                this.publishDate = []
                this.loadData(1);
            },
        }
    }
</script>
<style lang="less" scoped>
  /** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }

  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }
</style>