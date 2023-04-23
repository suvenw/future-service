<template>
  <a-drawer
    :title="title"
    :width="800"
    placement="right"
    @close="handleCancel"
    :visible="visible">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="功能描述">
          <a-input placeholder="请输入功能描述" :disabled="roleDisabled" v-decorator="['configDescribe',
                   {}]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="值描述">
          <a-input placeholder="请输入对应的值" v-decorator="['configValue',
              validatorRules.configValue]"/>
        </a-form-item>

      </a-form>
    </a-spin>
    <a-button :disabled="disableSubmit" type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
    import {httpAction, postAction, fileUpload} from '@/api/manage'
    import pick from 'lodash.pick'
    import moment from "moment"
    import {} from "@/utils/validate"

    export default {
        name: "configInfoModal",
        data() {
            return {
                title: "操作",
                visible: false,
                visibleCheck: true,
                roleDisabled: false,
                model: {
                    configKey: '',
                },
                selectedRole: [],
                disableSubmit: false,
                roleList: [
                    {"id": "1", "roleName": "测试1", "roleCode": "test1"},
                    {"id": "2", "roleName": "测试2", "roleCode": "test2"},
                    {"id": "3", "roleName": "测试3", "roleCode": "test3"}],
                labelCol: {
                    xs: {span: 24},
                    sm: {span: 5},
                },
                wrapperCol: {
                    xs: {span: 24},
                    sm: {span: 16},
                },
                confirmLoading: false,
                headers: {},
                form: this.$form.createForm(this),
                previewVisible: false,
                //图片预览
                previewImage: '',
                //上传的图片列表
                fileList: [],
                // 表单验证
                validatorRules: {
                    configValue: {
                        rules: [
                            // {required: true, message: '请输入值!'},
                            {validator: this.validateValue}
                        ]
                    }
                },
                url: {
                    add: "/sys/config/add",
                    edit: "/sys/config/modify",
                    fileUpload: "/sys/config/import",
                    fileUploadImg: "/upload/file",
                },
            }
        },
        created() {

        },
        computed: {},
        methods: {
            validateValue(rule, value, callback) {
                // console.log(rule);
                // console.log(value);
                let configKey = this.model.configKey;
                // console.log(configKey);
                console.log(value.startwith);
                if (value.length == 0) {
                    callback('输入框不能为空');
                } else if (configKey == "VIDEO_PLAY_SECOND" ||
                    configKey == "VIDEO_WATCH_COUNT" ||
                    configKey == "INVITE_USER_EXP" ||
                    configKey == "BE_INVITE_USER_EXP" ||
                    configKey == "LOGIN_VALIDATE_TIME" ||
                    configKey == "VIDEO_DRAG_SECOND" ||
                    configKey == "VIDEO_APPROVAL_TYPE_COUNT") {
                    if (value < 1 || value > 9999) {
                        callback('请输入1～9999以内的数字');
                    } else if (value >= 1 || value <= 9999) {
                        callback();
                    } else {
                        callback('请输入1～9999以内的数字');
                    }
                } else if (configKey == "BANNER_COUNTDOWN_SECOUND" ||
                    configKey == "START_ BANNER_COUNTDOWN") {
                    if (value < 0 || value > 9999) {
                        callback('请输入0～9999以内的数字');
                    } else if (value >= 0 || value <= 9999) {
                        callback();
                    } else {
                        callback('请输入0～9999以内的数字');
                    }
                } else if (configKey == "USER_VIDEO_SECOND") {
                    if (value < 5 || value > 300) {
                        callback('请输入5～300以内的数字');
                    } else if (value >= 5 || value <= 300) {
                        callback();
                    } else {
                        callback('请输入5～300以内的数字');
                    }
                } else if (configKey == "BANNER_INSERT_GAP") {
                    if (value < 5 || value > 1000) {
                        callback('请输入5～1000以内的数字');
                    } else if (value >= 5 || value <= 1000) {
                        callback();
                    } else {
                        callback('请输入5～1000以内的正整数数字');
                    }
                } else if (configKey == "BANNER_IS_CLOSE" ||
                    configKey == "START_BANNER_COUNTDOWN") {
                    if (value == 0 || value == 1) {
                        callback();
                    } else if (value != 0 || value != 1) {
                        callback('请输入数字0或者1');
                    } else {
                        callback('请输入数字0或者1');
                    }
                } else if (configKey == "VIDEO_APPROVAL_TYPE") {
                    if (value == 1 || value == 2) {
                        callback();
                    } else if (value != 1 || value != 2) {
                        callback('请输入数字1或者2');
                    } else {
                        callback('请输入数字1或者2');
                    }
                } else if (configKey == "COPY_URL_ADDRESS") {
                    console.log(value.length);
                    if (value.length <= 300 && value.length >= 1) {
                        callback();
                    } else {
                        callback('文本内容最多300字符');
                    }
                } else if (configKey == "SHARE_TITLE") {
                    console.log(value.length);
                    if (value.length <= 20  && value.length >= 1) {
                        callback();
                    }  else {
                        callback('文本内容最多20字符');
                    }
                } else if (configKey == "SHARE_DESC" || configKey == "SHARE_VIDEO_CONTENT") {
                    console.log(value.length);
                    if (value.length <=50  && value.length >= 1) {
                        callback();
                    } else {
                        callback('文本内容最多50 字符');
                    }
                } else if (configKey == "SHARE_URL" || configKey == "SHARE_VIDEO_URL" || configKey == "SHARE_IMAGE") {
                    if (value.indexOf("http://") || value.indexOf("https://")) {
                        callback();
                    } else {
                        callback('URL必须以http://或https://开头');
                    }
                } else {
                    callback();
                }
            },
            add() {
                this.edit({});
            },
            edit(record) {
                this.form.resetFields();
                this.model = Object.assign({}, record);
                this.visible = true;

                //编辑页面禁止修改角色编码
                if (this.model.id) {
                    this.roleDisabled = true;
                } else {
                    this.roleDisabled = false;
                }

                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model
                        , 'configValue'
                        , 'configDescribe'
                    ))
//时间格式化
                });

            },
            close() {
                this.$emit('close');
                this.visible = false;
            },
            onChose(checked) {
                if (checked) {
                    this.status = 1;
                    this.visibleCheck = true;
                } else {
                    this.status = 0;
                    this.visibleCheck = false;
                }
            },
            handleOk() {
                const that = this;
                // 触发表单验证
                this.form.validateFields((err, values) => {
                    if (!err) {
                        that.confirmLoading = true;
                        let httpurl = '';
                        let method = '';
                        if (!this.model.id) {
                            httpurl += this.url.add;
                            method = 'post';
                        } else {
                            httpurl += this.url.edit;
                            method = 'post';
                        }
                        let formData = Object.assign(this.model, values);
                        //时间格式化
                        formData.selectedroles = this.selectedRole.length > 0 ? this.selectedRole.join(",") : '';
                        console.log(formData)
                        httpAction(httpurl, formData, method).then((res) => {

                            if (res.code == 0) { //res.success
                                that.$message.success(res.msg);
                                that.$emit('ok');
                            } else {
                                that.$message.warning(res.msg);
                            }
                        }).finally(() => {
                            that.confirmLoading = false;
                            that.close();
                        })

                    }
                })
            },
            handleCancel() {
                this.previewVisible = false;
                this.close()
            },
        }
    }
</script>

<style lang="less" scoped>
  /** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>


<style>
  /* you can make up upload button and sample style by using stylesheets */
  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
</style>