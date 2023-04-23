<template>
  <a-drawer
    :title="title"
    :maskClosable="true"
    :width="drawerWidth"
    placement="right"
    :closable="true"
    @close="handleCancel"
    :visible="visible"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">

    <template slot="title">
      <div style="width: 100%;">
        <span>{{ title }}</span>
        <span style="display:inline-block;width:calc(100% - 51px);padding-right:10px;text-align: right">
          <a-button @click="toggleScreen" icon="appstore" style="height:20px;width:20px;border:0px"></a-button>
        </span>
      </div>

    </template>

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input disabled placeholder="" v-decorator="[ 'id', {}]"/>
        </a-form-item>

        <a-form-item label="用户昵称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input disabled placeholder="" v-decorator="[ 'nickname', {}]"/>
        </a-form-item>

        <a-form-item label="角色分配" :labelCol="labelCol" :wrapperCol="wrapperCol" v-show="!roleDisabled">
          <a-select
            mode="multiple"
            style="width: 100%"
            placeholder="请选择用户角色"
            optionFilterProp="children"
            v-model="selectedRole">
            <a-select-option v-for="(role,roleindex) in roleList" :key="roleindex.toString()" :value="role.id">
              {{ role.roleName }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <!--        &lt;!&ndash;部门分配&ndash;&gt;-->
        <!--        <a-form-item label="部门分配" :labelCol="labelCol" :wrapperCol="wrapperCol" v-show="!departDisabled">-->
        <!--          <a-input-search-->
        <!--            placeholder="点击右侧按钮选择部门"-->
        <!--            v-model="checkedDepartNameString"-->
        <!--            disabled-->
        <!--            @search="onSearch">-->
        <!--            <a-button slot="enterButton" icon="search">选择</a-button>-->
        <!--          </a-input-search>-->
        <!--        </a-form-item>-->

        <a-form-item label="等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input disabled placeholder="请输入用户等级" v-decorator="[ 'grade', validatorRules.grade]"/>
        </a-form-item>

        <a-form-item label="生日" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-date-picker
            style="width: 100%"
            placeholder="请选择生日"
            v-decorator="['birthday', {initialValue:!validatorRules.birthday?null:moment(validatorRules.birthday,dateFormat)}]"/>
        </a-form-item>

        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select v-decorator="[ 'sex', {}]" placeholder="请选择性别">
            <a-select-option :value="0">未知</a-select-option>
            <a-select-option :value="1">男</a-select-option>
            <a-select-option :value="2">女</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input placeholder="请输入邮箱" v-decorator="[ 'email', validatorRules.email]"/>
        </a-form-item>

        <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input placeholder="请输入手机号码" disabled
                   v-decorator="[ 'phone', validatorRules.phone]"/>
        </a-form-item>

        <a-form-item label="个人简介" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input placeholder="请输入个人简介"
                   v-decorator="[ 'remarks', validatorRules.remarks]"/>
        </a-form-item>

<!--        <a-form-item label="地区" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-cascader :options={options} @change="onChange" placeholder="Please select"/>-->
<!--        </a-form-item>-->

      </a-form>
    </a-spin>
<!--    <depart-window ref="departWindow" @ok="modalFormOk"></depart-window>-->

    <div class="drawer-bootom-button" v-show="!disableSubmit">
      <a-popconfirm title="确定放弃编辑？" @confirm="handleCancel" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button :loading="confirmLoading" @click="handleOk" type="primary">提交</a-button>
    </div>
  </a-drawer>
</template>

<script>
    import pick from 'lodash.pick'
    import moment from 'moment'
    import Vue from 'vue'
    // 引入搜索部门弹出框的组件
    import departWindow from './DepartWindow'
    import {getAction} from '@/api/manage'
    import {httpAction} from '@/api/manage'
    import {addUser, editUser, queryUserRole, queryall} from '@/api/api'
    import {disabledAuthFilter} from "@/utils/authFilter"
    import {duplicateCheck} from '@/api/api'

    export default {
        name: "RoleModal",
        components: {
            departWindow,
        },
        data() {
            return {


                departDisabled: false, //是否是我的部门调用该页面
                roleDisabled: false, //是否是角色维护调用该页面
                modalWidth: 800,
                drawerWidth: 700,
                modaltoggleFlag: true,
                confirmDirty: false,
                selectedDepartKeys: [], //保存用户选择部门id
                checkedDepartKeys: [],
                checkedDepartNames: [], // 保存部门的名称 =>title
                checkedDepartNameString: "", // 保存部门的名称 =>title
                userId: "", //保存用户id
                disableSubmit: false,
                userDepartModel: {userId: '', departIdList: []}, // 保存SysUserDepart的用户部门中间表数据需要的对象
                dateFormat: "YYYY-MM-DD",
                dateFormatHour: "YYYY-MM-DD HH:mm:ss",
                validatorRules: {
                    username: {
                        rules: [{
                            required: true, message: '请输入用户账号!'
                        }, {
                            validator: this.validateUsername,
                        }]
                    },
                    password: {
                        rules: [{
                            required: true,
                            pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/,
                            message: '密码由6-16位数字、大小写字母组成!'
                        }, {
                            validator: this.validateToNextPassword,
                        }],
                    },
                    confirmpassword: {
                        rules: [{
                            required: true, message: '请重新输入登陆密码!',
                        }, {
                            validator: this.compareToFirstPassword,
                        }],
                    },
                    phone: {rules: [{validator: this.validatePhone}]},
                    email: {
                        rules: [{
                            validator: this.validateEmail
                        }],
                    },
                    birthday: {
                        rules: [{
                            validator: this.validateBirthday
                        }],
                    },
                    roles: {}
                    //  sex:{initialValue:((!this.model.sex)?"": (this.model.sex+""))}
                },
                title: "操作",
                visible: false,
                model: {},
                roleList: [],
                selectedRole: [],
                labelCol: {
                    xs: {span: 24},
                    sm: {span: 5},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 16},
                },
                uploadLoading: false,
                confirmLoading: false,
                headers: {},
                formLayout: 'horizontal',
                form: this.$form.createForm(this),
                areaOptions: [],
                picUrl: "",
                url: {
                    add: "/sys/user/add",
                    modify: "/sys/user/modify",
                    fileUpload: window._CONFIG['domianURL'] + "/sys/common/upload",
                    //  imgerver: window._CONFIG['domianURL']+"/sys/common/view",
                    userWithDepart: "/sys/user/userDepartList", // 引入为指定用户查看部门信息需要的url
                    //userId:"/sys/user/generateUserId", // 引入生成添加用户情况下的url
                    syncUserByUserName: "/process/extActProcess/doSyncUserByUserName",//同步用户到工作流
                },
            }
        },
        computed: {
            uploadAction: function () {
                return this.url.fileUpload;
            }
        },
        methods: {
            isDisabledAuth(code) {
                return disabledAuthFilter(code);
            },
            //窗口最大化切换
            toggleScreen() {
                if (this.modaltoggleFlag) {
                    this.modalWidth = window.innerWidth;
                } else {
                    this.modalWidth = 800;
                }
                this.modaltoggleFlag = !this.modaltoggleFlag;
            },
            initialRoleList() {
                queryall().then((res) => {
                    if (res.code == 0) {
                        this.roleList = res.data.list;
                    } else {
                        console.log(res.msg);
                    }
                });
            },
            loadUserRoles(userid) {
                queryUserRole({userid: userid}).then((res) => {
                    if (res.code == 0) {
                        console.log( res.data.list);
                        this.selectedRole = res.data.list;
                    } else {
                        console.log(res.msg);
                    }
                });
            },
            // refresh() {
            //     this.selectedDepartKeys = [];
            //     this.checkedDepartKeys = [];
            //     this.checkedDepartNames = [];
            //     this.checkedDepartNameString = "";
            //     this.userId = ""
            // },
            add() {
                this.picUrl = "";
                // this.refresh();
                this.edit({});
            },
            edit(record) {
                this.resetScreenSize(); // 调用此方法,根据屏幕宽度自适应调整抽屉的宽度
                let that = this;
                that.initialRoleList();
                // that.checkedDepartNameString = "";
                that.form.resetFields();
                if (record.hasOwnProperty("id")) {
                    that.loadUserRoles(record.id);
                    this.picUrl = "Has no pic url yet";
                }
                that.userId = record.id;
                that.visible = true;
                that.model = Object.assign({}, record);
                that.$nextTick(() => {
                    that.form.setFieldsValue(pick(this.model, 'id', 'nickname', 'roleName',
                        'userImage', 'grade', 'sex', 'email', 'phone', 'address', 'remarks'));
                    that.form.setFieldsValue({birthday: this.model.birthday ? moment(this.model.birthday) : null})
                });
                // 调用查询用户对应的部门信息的方法
                // that.checkedDepartKeys = [];
                // that.loadCheckedDeparts();
            },
            //
            // loadCheckedDeparts() {
            //     let that = this;
            //     if (!that.userId) {
            //         return
            //     }
            //     getAction(that.url.userWithDepart, {userId: that.userId}).then((res) => {
            //         that.checkedDepartNames = [];
            //         if (res.code == 0) {
            //             for (let i = 0; i < res.data.list.length; i++) {
            //                 that.checkedDepartNames.push(res.data.list[i].title);
            //                 this.checkedDepartNameString = this.checkedDepartNames.join(",");
            //                 that.checkedDepartKeys.push(res.data.list[i].key);
            //             }
            //             that.userDepartModel.departIdList = that.checkedDepartKeys
            //         } else {
            //             console.log(res.msg);
            //         }
            //     })
            // },
            close() {
                this.$emit('close');
                this.visible = false;
                this.disableSubmit = false;
                this.selectedRole = [];
                this.userDepartModel = {userId: '', departIdList: []};
                this.checkedDepartNames = [];
                this.checkedDepartNameString = '';
                this.checkedDepartKeys = [];
                this.selectedDepartKeys = [];
            },
            moment,
            handleOk() {
                let that = this;
                // 触发表单验证
                this.form.validateFields((err, values) => {
                    if (!err) {
                        that.confirmLoading = true;
                        if(!values.birthday){
                            values.birthday = '';
                        }else{
                            values.birthday = values.birthday.format(this.dateFormatHour);
                        }
                        Object.assign(this.model, values);
                        let params = new FormData();
                        if (that.model.username != undefined) {
                            params.append('nickname', that.model.username);
                        }
                        if (values.grade) {
                            params.append('grade', values.grade);
                        }
                        if (that.model.sex != undefined) {
                            params.append('sex', that.model.sex);
                        }
                        if (that.model.email != undefined) {
                            params.append('email', that.model.email);
                        }
                        if (that.model.phone != undefined) {
                            params.append('phone', that.model.phone);
                        }
                        if (that.model.birthday != undefined) {
                            params.append('cityName', that.model.cityName);
                        }
                        if (values.birthday){
                            params.append('birthdayDate',values.birthday);
                        }
                        if (that.model.id){
                            params.append('id',this.model.id);
                        }
                        if (that.model.remarks) {
                            params.append('remarks',this.model.remarks);
                        }
                        //拼接 字符串数组
                        var keys = String.fromCharCode(7);
                        var roleIds = '';
                        // var departIds = '';
                        for (var a = 0; a < this.selectedRole.length; a++) {
                                roleIds += this.selectedRole[a] + keys
                        }
                        // for (var a = 0; a < this.userDepartModel.departIdList.length; a++) {
                        //     departIds += this.userDepartModel.departIdList[a] + keys
                        // }
                        if (roleIds != '') {
                            params.append('roleIds', roleIds);
                        }
                        // if (departIds != '') {
                        //     params.append('departIds', departIds);
                        // }
                       console.info(params);
                        let httpurl = '';
                        let method = '';
                        if (!this.model.id) {
                            httpurl += this.url.add;
                            method = 'post';
                        } else {
                            httpurl += this.url.modify;
                            method = 'post';
                        }
                        httpAction(httpurl, params, method).then((res) => {
                            if (res.code == 0) {
                                that.$message.success(res.msg);
                                that.$emit('ok');
                            } else {
                                that.$message.warning(res.msg);
                            }
                        }).finally(() => {
                            that.confirmLoading = false;
                            // that.checkedDepartNames = [];
                            // that.userDepartModel.departIdList = {userId: '', departIdList: []};
                            that.close();
                        })

                    }
                })
            },
            handleCancel() {
                this.close()
            },
            validatePhone(rule, value, callback) {
                if (!value) {
                    callback()
                } else {
                    if (new RegExp(/^1[3|4|5|7|8][0-9]\d{8}$/).test(value)) {
                        var params = {
                            tableName: 'sys_user',
                            fieldName: 'phone',
                            fieldVal: value,
                            dataId: this.userId
                        };
                        duplicateCheck(params).then((res) => {
                            if (res.code == 0) {
                                callback()
                            } else {
                                callback("手机号已存在!")
                            }
                        })
                    } else {
                        callback("请输入正确格式的手机号码!");
                    }
                }
            },
            validateEmail(rule, value, callback) {
                if (!value) {
                    callback()
                } else {
                    if (new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/).test(value)) {
                        var params = {
                            tableName: 'sys_user',
                            fieldName: 'email',
                            fieldVal: value,
                            dataId: this.userId
                        };
                        duplicateCheck(params).then((res) => {
                            console.log(res)
                            if (res.code == 0) {
                                callback()
                            } else {
                                callback("邮箱已存在!")
                            }
                        })
                    } else {
                        callback("请输入正确格式的邮箱!")
                    }
                }
            },
            validateUsername(rule, value, callback) {
                var params = {
                    tableName: 'sys_user',
                    fieldName: 'username',
                    fieldVal: value,
                    dataId: this.userId
                };
                duplicateCheck(params).then((res) => {
                    if (res.code == 0) {
                        callback()
                    } else {
                        callback("用户名已存在!")
                    }
                })
            },
            handleConfirmBlur(e) {
                const value = e.target.value;
                this.confirmDirty = this.confirmDirty || !!value
            },
            beforeUpload: function (file) {
                var fileType = file.type;
                if (fileType.indexOf('image') < 0) {
                    this.$message.warning('请上传图片');
                    return false;
                }
                //TODO 验证文件大小
            },
            handleChange(info) {
                this.picUrl = "";
                if (info.file.status === 'uploading') {
                    this.uploadLoading = true;
                    return
                }
                if (info.file.status === 'done') {
                    var response = info.file.response;
                    this.uploadLoading = false;
                    console.log(response);
                    if (response.success) {
                        this.model.avatar = response.msg;
                        this.picUrl = "Has no pic url yet";
                    } else {
                        this.$message.warning(response.msg);
                    }
                }
            },
            getAvatarView() {
                // return this.url.imgerver +"/"+ this.model.avatar;
            },
            // 搜索用户对应的部门API
            // onSearch() {
            //     this.$refs.departWindow.add(this.checkedDepartKeys, this.userId);
            // },

            // 获取用户对应部门弹出框提交给返回的数据
            // modalFormOk(formData) {
            //     this.checkedDepartNames = [];
            //     this.selectedDepartKeys = [];
            //     this.checkedDepartNameString = '';
            //     this.userId = formData.userId;
            //     this.userDepartModel.userId = formData.userId;
            //     for (let i = 0; i < formData.departIdList.length; i++) {
            //         this.selectedDepartKeys.push(formData.departIdList[i].key);
            //         this.checkedDepartNames.push(formData.departIdList[i].title);
            //         this.checkedDepartNameString = this.checkedDepartNames.join(",");
            //     }
            //     this.userDepartModel.departIdList = this.selectedDepartKeys;
            //     this.checkedDepartKeys = this.selectedDepartKeys  //更新当前的选择keys
            // },
            // 根据屏幕变化,设置抽屉尺寸
            resetScreenSize() {
                let screenWidth = document.body.clientWidth;
                if (screenWidth < 500) {
                    this.drawerWidth = screenWidth;
                } else {
                    this.drawerWidth = 700;
                }
            },
            created() {
                getAction('/api/area').then((res) => {
                    console.log("------------")
                    console.log(res)
                    this.areaOptions = res;
                })
            },
            filter(inputValue, path) {
                return (path.some(option => (option.label).toLowerCase().indexOf(inputValue.toLowerCase()) > -1));
            },
        }
    }
</script>

<style scoped>
  .avatar-uploader > .ant-upload {
    width: 104px;
    height: 104px;
  }

  .ant-upload-select-picture-card i {
    font-size: 49px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 10px;
    padding-bottom: 10px;
  }

  .drawer-bootom-button {
    position: absolute;
    bottom: -8px;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    left: 0;
    background: #fff;
    border-radius: 0 0 2px 2px;
  }
</style>