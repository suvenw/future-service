<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @submit.prevent="searchQuery">
        <a-row :gutter="24">

<!--          <a-col :md="6" :sm="12">
            <a-form-item label="ID">
              <a-input placeholder="请输入查询" v-model="queryParam.id"></a-input>
            </a-form-item>
          </a-col>-->
          <a-col :md="6" :sm="12">
            <a-form-item label="用户昵称">
              <a-input placeholder="请输入会员昵称查询" v-model="queryParam.nickName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="手机号码">
              <a-input placeholder="请输入手机号码查询" v-model="queryParam.phone"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="性别">
                <a-select v-model="queryParam.sex" placeholder="请选择性别查询">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option value="0">未知</a-select-option>
                  <a-select-option value="1">男性</a-select-option>
                  <a-select-option value="2">女性</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>

<!--            <a-col :md="6" :sm="8">
              <a-form-item label="角色">
                <a-select v-model="queryParam.roleName" placeholder="请选择角色查询">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option value="1">管理员</a-select-option>
                  <a-select-option value="2">普通用户</a-select-option>
                  <a-select-option value="3">运营专员</a-select-option>
                  <a-select-option value="4">运营经理</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>-->

            <!-- <a-col :md="6" :sm="8">
              <a-form-item label="评论状态">
                <a-select v-model="queryParam.banned" placeholder="请选择是否禁言查询">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option value="1">启用</a-select-option>
                  <a-select-option value="2">禁用</a-select-option>
                </a-select>
              </a-form-item>
            </a-col> -->

            <a-col :md="6" :sm="8">
              <a-form-item label="账号状态">
                <a-select v-model="queryParam.status" placeholder="请选择账号状态查询">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option value="0">禁用</a-select-option>
                  <a-select-option value="1">启用</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>


            <a-col :md="12" :sm="8">
              <a-form-item label="注册时间">
                <a-range-picker :showTime="{ format: 'HH:mm:ss' }" format="YYYY-MM-DD HH:mm:ss" :value="validDate"
                                @change="onChangeDate"/>
              </a-form-item>
            </a-col>

            <!--            <a-col :md="12" :sm="18">-->
            <!--              <a-form-item label="注册时间">-->
            <!--                <j-date v-model="queryParam.startDate" :showTime="true" date-format="YYYY-MM-DD HH:mm:ss" style="width:45%" placeholder="请选择开始时间" ></j-date>-->
            <!--                <span style="width: 10px;">~</span>-->
            <!--                <j-date v-model="queryParam.endDate" :showTime="true" date-format="YYYY-MM-DD HH:mm:ss" style="width:45%" placeholder="请选择结束时间"></j-date>-->
            <!--              </a-form-item>-->
            <!--            </a-col>-->

          </template>

          <a-col :md="6" :sm="8">
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

    <!-- table区域-begin -->
    <div>
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

        <template slot="avatarslot" slot-scope="text, record ">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="getAvatarView(record.avatar)" icon="user"/>
          </div>
        </template>

        <span slot="action" slot-scope="text, record">
<!--          <a style="color:#DC143C;" v-if="record.banned == 0" @click="handleMuted(record,1)">封禁评论</a>
          <a v-else @click="handleMuted(record,0)">解禁评论</a>-->
          <a-divider type="vertical"/>
          <a style="color:#DC143C;" v-if="record.status == 0" @click="handleBan(record,1)">封禁账号</a>
          <a v-else @click="handleBan(record,0)">解禁账号</a>
          <a-divider type="vertical"/>
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <user-modal ref="modalForm" @ok="modalFormOk"></user-modal>
    <sys-user-agent-modal ref="sysUserAgentModal"></sys-user-agent-modal>
  </a-card>
</template>

<script>
    import moment from 'moment'
    import UserModal from './modules/UserModal'
    import {frozenBatch} from '@/api/api'
    import {JeecgListMixin} from '@/mixins/JeecgListMixin'
    import SysUserAgentModal from "./modules/SysUserAgentModal";
    import {postAction} from "../../api/manage";

    export default {
        name: "UserList",
        mixins: [JeecgListMixin],
        components: {
            SysUserAgentModal,
            UserModal,
        },
        data() {
            return {
                description: '用户列表',
                queryParam: {},
                columns: [
                    {
                        title: '用户ID',
                        align: "center",
                        dataIndex: 'id',
                        width: 75
                    },
                    {
                        title: '手机号码',
                        align: "center",
                        width: 75,
                        dataIndex: 'phone'
                    },
                    {
                        title: '用户昵称',
                        align: "center",
                        width: 75,
                        dataIndex: 'nickName',
                    }/*,
                    {
                        title: '用户等级',
                        align: "center",
                        width: 75,
                        dataIndex: 'grade',
                        customRender: function (t, r, index) {
                            return "Lv" + r.grade;
                        }
                    }*/,
                    {
                        title: '性别',
                        align: "center",
                        width: 75,
                        dataIndex: 'sex_dictText',
                        customRender: function (t, r, index) {
                            if (r.sex == 1) {
                                return "男";
                            } else if (r.sex == 2) {
                                return "女";
                            } else {
                                return "未知";
                            }
                        }
                    },
                    {
                        title: '出生日期',
                        align: "center",
                        width: 100,
                        dataIndex: 'birthday',
                        customRender: function (t, r, index) {
                            if (r.birthday == 0|| r.birthday == null || r.birthday == undefined) {
                                return ''
                            }
                            return moment(r.birthday).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        title: '地区',
                        align: "center",
                        dataIndex: 'address',
                        width: 75,
                    }/*,
                    {
                        title: '角色',
                        align: "center",
                        dataIndex: 'roleName',
                        width: 75,
                    }*/,
                    {
                        title: '账号状态',
                        align: "center",
                        width: 75,
                        dataIndex: 'status',
                        customRender: function (t, r, index) {
                            if (r.status == 1) {
                                return "启用";
                            } else {
                                return "禁用";
                            }
                        }
                    },
                    {
                        title: '邮箱',
                        align: "center",
                        dataIndex: 'email',
                        width: 75,
                    },
                    {
                        title: '注册时间',
                        align: "center",
                        width: 100,
                        dataIndex: 'createDate',
                        customRender: function (t, r, index) {
                            if (r.createDate == 0 || r.createDate == null || r.createDate == undefined) {
                                return ''
                            }
                            return moment(r.createDate).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        title: '上次登录时间',
                        align: "center",
                        width: 75,
                        dataIndex: 'lastLoginDate',
                        customRender: function (t, r, index) {
                            if (r.lastLoginDate == 0 || r.lastLoginDate == null || r.lastLoginDate == undefined) {
                                return ''
                            }
                            return moment(r.lastLoginDate).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        title: '操作',
                        dataIndex: 'action',
                        scopedSlots: {customRender: 'action'},
                        align: "center",
                        width: 240
                    }

                ],
                url: {
                    //   imgerver: window._CONFIG['domianURL'] + "/sys/common/view",
                    syncUser: "/process/extActProcess/doSyncUser",
                    list: "/sys/user/list",
                    handleMuted: "/sys/user/handleMuted",
                    handleBan: "/sys/user/handleBan",
                },
                ipagination: {
                    pageSize: 10,
                },
                validDate: [],
            }
        },
        computed: {
            importExcelUrl: function () {
                return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
            }
        },
        methods: {
            getAvatarView: function (avatar) {
                return this.url.imgerver + "/" + avatar;
            },
            handleMuted(obj, muted) {
                postAction(this.url.handleMuted, {id: obj.id, banned: muted}).then((res) => {
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

            handleBan(obj, banned) {
                postAction(this.url.handleBan, {id: obj.id, ban: banned}).then((res) => {
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
