<template>
  <a-card :bordered="false">

    <!-- 查询区域-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="广告名称">
              <a-input placeholder="请输入广告名称" v-model="queryParam.videoName"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="4" :sm="6">
            <a-form-item label="状态">
              <a-select v-model="queryParam.status" placeholder="请选择上下架状态查询">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">上架</a-select-option>
                <a-select-option value="2">下架</a-select-option>
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

        <template slot="videoslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="record.videoImageShow" @click="handlePreview(record)"/>
          </div>
        </template>

        <template slot="videoNameslot" slot-scope="text, record, index">
          <a-tooltip placement="top">
            <template slot="title">
              <span>{{ record.videoName }}</span>
            </template>
            <span v-if="record.videoName.length > 8">{{ record.videoName.substring(0,8)+'...' }}</span>
                 <span v-else>{{ record.videoName }}</span>
          </a-tooltip>
        </template>

        <template slot="videoDescslot" slot-scope="text, record, index">
          <a-tooltip placement="top">
            <template slot="title">
              <span>{{ record.videoDesc }}</span>
            </template>
            <span v-if="record.videoDesc.length > 8">{{ record.videoDesc.substring(0,8)+'...' }}</span>
                 <span v-else>{{ record.videoDesc }}</span>
          </a-tooltip>
        </template>

        <span slot="action" slot-scope="text, record">
          <a v-if="record.status == 1" @click="handleStatus(record,2)">下架</a>
          <a v-else @click="handleStatus(record,1)">上架</a>
          <a-divider type="vertical"/>
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-popconfirm title="你确定要删除当前记录吗" @confirm="() => handleDelete(record.id)">
          <a>删除</a>
        </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->
    <a-modal
      title="预览"
      destroyOnClose
      footer
      v-model="visible">
      <video-player class="video-player vjs-custom-skin"
                    ref="videoPlayer"
                    :playsinline="true"
                    :options="playerOptions">
      </video-player>
    </a-modal>

    <!-- 表单区域 -->
    <videoInfoModal ref="modalForm" @ok="modalFormOk"></videoInfoModal>
  </a-card>
</template>

<script>
    import moment from 'moment'
    import {videoPlayer} from 'vue-video-player'
    import videoInfoModal from './modules/BannerVideoModal'
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import {postAction, fileUpload, deleteAction} from '@/api/manage'
    import JDate from '@/components/jeecg/JDate'

    export default {
        name: "videoInfoList",
        mixins: [JeecgListMixin],
        components: {
            videoInfoModal,
            JDate,
            videoPlayer
        },
        data() {
            return {
                description: '视频信息管理页面',
                // 表头
                columns: [
                    {
                        title: '排序序号',
                        dataIndex: '',
                        key: 'rowIndex',
                        width: 75,
                        align: "center",
                        customRender: function (t, r, index) {
                            return parseInt(index) + 1;
                        }
                    },
                    {
                        title: '视频名称（标题）',
                        align: "center",
                        width: 75,
                        // dataIndex: 'videoName'
                        scopedSlots: {customRender: "videoNameslot"}
                    },
                    {
                        title: '视频文件',
                        align: "center",
                        width: 75,
                        scopedSlots: {customRender: "videoslot"}
                    },
                    {
                        title: '广告类型',
                        align: 'center',
                        width: 75,
                        dataIndex: 'bannerType',
                        customRender: function (t, r, index) {
                            if (r.bannerType == 1) {
                                return "图片";
                            } else if (r.bannerType == 2){
                                return "视频";
                            } else {
                                return "未知";
                            }
                        }
                    },
                    {
                        title: '视频介绍信息',
                        align: "center",
                        width: 75,
                        // dataIndex: 'videoDesc'
                        scopedSlots: {customRender: "videoDescslot"}
                    },
                    {
                        title: '播放数',
                        align: "center",
                        width: 75,
                        dataIndex: 'videoReport.playCount'
                    },
                    {
                        title: '点赞数',
                        align: "center",
                        width: 75,
                        dataIndex: 'videoReport.praiseCount'
                    },
                    {
                        title: '分享数',
                        align: "center",
                        width: 75,
                        dataIndex: 'videoReport.shareCount'
                    },
                    {
                        title: '评论数',
                        align: "center",
                        width: 75,
                        dataIndex: 'videoReport.commentCount'
                    },
                    {
                        title: '广告状态',
                        align: "center",
                        width: 75,
                        dataIndex: 'status',
                        customRender: function (t, r, index) {
                            if (r.status == 1) {
                                return "上架";
                            } else if (r.status == 2) {
                                return "下架";
                            } else {
                                return "未知";
                            }
                        }
                    },
                    {
                        title: '上架时间',
                        align: "center",
                        dataIndex: 'startDate',
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
                        width: 75,
                        scopedSlots: {customRender: 'action'},
                    }
                ],
                confirmLoading: false,
                //界面请求URL
                url: {
                    list: "/resource/bannerVideo/list",
                    delete: "/resource/bannerVideo/delete",
                    deleteBatch: "/resource/bannerVideo/delete",
                    handleStatus: "/resource/bannerVideo/handleStatus",
                },
                ipagination: {
                    pageSize: 10,
                },
                validDate: [],
                playerOptions: {},
                visible: false,
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
            //     deleteAction(this.url.delete, {idList: obj.id}).then((res) => {
            //         if (res.code == 0) {
            //             that.$message.success('操作成功');
            //             that.loadData(1)
            //         } else {
            //             that.$message.warning('操作失败');
            //         }
            //     })
            // },
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
            //视频文件展示
            handlePreview(info) {
                var options = {
                    autoplay: false,
                    muted: false,
                    loop: false,
                    preload: 'auto',
                    aspectRatio: '16:9',
                    fluid: true,
                    sources: [{src: info.videoUrlShow, type: 'video/mp4'}],
                    poster: info.videoImage,
                    notSupportedMessage: '此视频暂无法播放，请稍后再试',
                    controlBar: {
                        timeDivider: true,
                        durationDisplay: true,
                        remainingTimeDisplay: false,
                        fullscreenToggle: true
                    }
                };
                this.playerOptions = options
                this.visible = true
            },
            onChangeDate(date) {
                this.validDate = date
                if (date[0] != undefined && date[1] != undefined) {
                    this.queryParam.startDate = moment(date[0], 'YYYY-MM-DD HH:mm:ss').valueOf()
                    this.queryParam.endDate = moment(date[1], 'YYYY-MM-DD HH:mm:ss').valueOf()
                }
            },
            searchReset() {
                this.queryParam = {};
                this.validDate = []
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