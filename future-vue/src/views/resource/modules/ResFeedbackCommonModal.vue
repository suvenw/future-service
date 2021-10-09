<template>
  <a-drawer
    :title="title"
    :width="800"
    placement="right"
    @close="handleCancel"
    :visible="visible">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <!--修改重新传参数 隐藏该值 -->
        <a-form-item
          style="display:none"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="hidden">
          <a-input v-decorator="['pid', {}]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="问题">
          <a-input placeholder="请输入问题" v-decorator="['question', validatorRules.question]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="回答">
          <a-textarea placeholder="请输入回答" v-decorator="['answer', validatorRules.answer]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="排序">
          <a-input-number v-decorator="[ 'sort', validatorRules.sort]"/>
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
        name: "resFeedbackCommonModal",
        data() {
            return {
                title: "操作",
                visible: false,
                visibleCheck: true,
                model: {},
                disableSubmit: false,
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
                    question: {
                        rules: [
                            {required: true, message: '请输入问题!'},
                            {min: 1, max: 30, message: '问题最多30个字符', trigger: 'blur'}
                        ]
                    },
                    answer: {
                        rules: [
                            {required: true, message: '请输入回答!'},
                            {min: 1, max: 300, message: '回答最多300个字符', trigger: 'blur'}
                        ]
                    },
                    sort: {
                        rules: [
                            {required: true, message: '请输入排序!'},
                            {validator: this.validateSort}
                        ]
                    }
                },
                url: {
                    add: "/resource/resfeedbackcommon/add",
                    edit: "/resource/resfeedbackcommon/modify"
                },
            }
        },
        created() {

        },
        computed: {},
        methods: {
            validateSort(rule, value, callback) {
                if (value < 0 || value > 9999) {
                    callback('排序输入范围为：0～9999');
                }
                callback();
            },
            add() {
                this.edit({});
            },
            edit(record) {
                this.form.resetFields();
                this.model = Object.assign({}, record);
                this.visible = true;
                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model
                        , 'question'
                        , 'answer'
                        , 'sort'
                        , 'pid'
                    ))
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
                        formData.operator = this.$store.getters.userInfo.id
                        //时间格式化
                        // formData.createDate = formData.createDate?formData.createDate.format('YYYY-MM-DD HH:mm:ss'):null;
                        // formData.modifyDate = formData.modifyDate?formData.modifyDate.format('YYYY-MM-DD HH:mm:ss'):null;
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
            }
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