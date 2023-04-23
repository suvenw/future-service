<template>
  <a-card :bordered="false">

    <!-- 查询区域-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">


          <a-col :md="6" :sm="8">
            <a-form-item label="广告名称">
              <a-input placeholder="请输入广告名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="4" :sm="6">
            <a-form-item label="状态">
              <a-select v-model="queryParam.status" placeholder="请选择上下架状态查询">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="2">上架</a-select-option>
                <a-select-option value="1">下架</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :md="4" :sm="6">
            <a-form-item label="默认广告：">
              <a-select v-model="queryParam.isDefault" placeholder="请选择是否默认广告查询">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="2">是</a-select-option>
                <a-select-option value="1">否</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>


          <a-col :md="12" :sm="8">
            <a-form-item label="有效期限：">
              <a-range-picker :showTime="{ format: 'HH:mm:ss' }" format="YYYY-MM-DD HH:mm:ss" :value="validDate"
                              @change="onChangeDate"/>
            </a-form-item>
          </a-col>

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
        :loading="loading"  :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :sortDirections="['descend', 'ascend']">

        <template slot="urlslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="record.urlShow" @click="handlePreview(record)" />
          </div>
        </template>

        <span slot="action" slot-scope="text, record">
        <a v-if="record.status == 2" @click="handleStatus(record,1)">下架</a>
        <a v-else @click="handleStatus(record,2)">上架</a>
        <a-divider type="vertical"/>
        <a @click="handleEdit(record)">编辑</a>
        <a-divider type="vertical"/>
        <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
          <a>删除</a>
        </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 图片预览   -->
    <a-modal :visible="previewVisible" :footer="null" @cancel="previewHandleCancel">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>

    <!-- 表单区域 -->
    <resBannerInfoModal ref="modalForm" @ok="modalFormOk"></resBannerInfoModal>
  </a-card>
</template>

<script>
    import moment from 'moment'
    import resBannerInfoModal from './modules/ResBannerInfoModal'
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import {postAction, deleteAction} from '@/api/manage'
    // import JDate from '@/components/jeecg/JDate'

    export default {
        name: "resBannerInfoList",
        mixins: [JeecgListMixin],
        components: {
            resBannerInfoModal,
            // JDate
        },
        data() {
            return {
                description: '推荐广告管理页面',
                // 查询条件
                queryParam: {category: 3, name: '', status: '', isDefault: '', startDate: '', endDate: ''},
                // 表头
                columns: [
                    {
                        title: '序号',
                        dataIndex: '',
                        key: 'rowIndex',
                        overflow: scroll,
                        align: "center",
                        customRender: function (t, r, index) {
                            return parseInt(index) + 1;
                        }
                    },
                    {
                        title: '广告名称',
                        align: "center",
                        dataIndex: 'name'
                    },
                    {
                        title: '广告图展示',
                        align: "center",
                        width: 120,
                        scopedSlots: {customRender: "urlslot"}
                    },
                    {
                        title: '点击数',
                        align: "center",
                        sortDirections: ['descend' | 'ascend'],
                        dataIndex: 'linkCount'
                    },
                    {
                        title: '广告状态',
                        align: "center",
                        dataIndex: 'status',
                        customRender: function (t, r, index) {
                            if (r.status == 2) {
                                return "上架";
                            } else {
                                return "下架";
                            }
                        }
                    },
                    {
                        title: '是否默认广告',
                        align: "center",
                        dataIndex: 'isDefault',
                        customRender: function (t, r, index) {
                            if (r.isDefault == 2) {
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    },
                    {
                        title: '上架时间',
                        align: "center",
                        dataIndex: 'startDate',
                        sorter: (a, b) => moment(a.startDate - b.startDate),//比较时间
                        customRender: function (t, r, index) {
                            if (r.startDate == 0) {
                                return ''
                            }
                            return moment(r.startDate).format("YYYY-MM-DD")
                        }
                    },
                    {
                        title: '下架时间',
                        align: "center",
                        dataIndex: 'endDate',
                        sorter: (a, b) => moment(a.startDate - b.startDate),//比较时间
                        customRender: function (t, r, index) {
                            if (r.endDate == 0) {
                                return ''
                            }
                            return moment(r.endDate).format("YYYY-MM-DD")
                        }
                    },
                    {
                        title: '创建时间',
                        align: "center",
                        dataIndex: 'createDate',
                        sorter: (a, b) => moment(a.createDate - b.createDate),//比较时间
                        customRender: function (t, r, index) {
                            if (r.createDate == 0) {
                                return ''
                            }
                            return moment(r.createDate).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        title: '修改时间',
                        align: "center",
                        dataIndex: 'modifyDate',
                        sorter: (a, b) => moment(a.modifyDate - b.modifyDate),//比较时间
                        customRender: function (t, r, index) {
                            if (r.modifyDate == 0) {
                                return ''
                            }
                            return moment(r.modifyDate).format("YYYY-MM-DD HH:mm:ss")
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
                    list: "/resource/banner/list",
                    delete: "/resource/banner/delete",
                    handleStatus: "/resource/banner/handleStatus",
                },
                ipagination: {
                    pageSize: 10,
                },
                validDate: [],
                previewImage: '',
                previewVisible: false,
            }
        },
        computed: {
            importAction: function () {
                return this.url.importXlsUrl;
            },
        },

        methods: {
            handleStatus(obj, status) {
                postAction(this.url.handleStatus, {id: obj.id, status: status}).then((res) => {
                    if (res.code == 0) {
                        this.$message.success('操作成功');
                        this.loadData();
                    } else {
                        that.$message.warning('操作失败');
                    }
                }).finally(() => {
                    this.confirmLoading = false;
                })
            },
            // handleDelete(obj) {
            //     var ids = "";
            //     for (var a = 0; a < this.selectedRowKeys.length; a++) {
            //         ids += this.selectedRowKeys[a] + String.fromCharCode(7);
            //     }
            //     deleteAction(this.url.delete, {idList: obj.id}).then((res) => {
            //         if (res.code == 0) {
            //             that.$message.success('操作成功');
            //             that.loadData()
            //         } else {
            //             that.$message.warning('操作失败');
            //         }
            //     })
            // },
            onChangeDate(date) {
                this.validDate = date
                if (date[0] != undefined && date[1] != undefined) {
                    this.queryParam.startDate = moment(date[0], 'YYYY-MM-DD HH:mm:ss').valueOf()
                    this.queryParam.endDate = moment(date[1], 'YYYY-MM-DD HH:mm:ss').valueOf()
                }
            },
            searchReset() {
                this.previewVisible = false;
                this.queryParam = {category: 3};
                this.validDate = []
                this.loadData(1);
            },
            handlePreview(record) {
                console.log(record);
                this.previewImage = record.urlShow;
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

