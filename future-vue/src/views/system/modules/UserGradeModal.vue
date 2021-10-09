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
          label="等级">
          <a-input-number :disabled="displayDisabledGrade" placeholder="请输入等级数" v-decorator="[ 'grade', validatorRules.grade]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="等级称谓">
          <a-input placeholder="请输入等级称谓" v-decorator="['gradeDesc',
                   validatorRules.gradeDesc]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="升级等级所需经验">
          <a-input :disabled="displayDisableds" placeholder="请输入升级等级所需经验" v-decorator="['gradeExp',
                   validatorRules.gradeExp]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="视频拍摄时长（秒）">
          <a-input-number placeholder="拍摄时长" v-decorator="['playLength',
                   validatorRules.playLength]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="每日观看次数（次）">
          <a-input-number :disabled="displayDisabled" placeholder="请输入每日观看次数" v-decorator="['playCount',
                   validatorRules.playCount]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否限制播放：">
          <a-input @change="handleChangePlayCount" placeholder="请输入是否限制播放：0：限制次数 1：播放次数不限制（无限次）" v-decorator="['restrictPlay',
                validatorRules.restrictPlay]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="上传升级图片">
            <a-upload
              name="file"
              :multiple="false"
              v-decorator="[ 'icon', validatorRules.icon]"
              listType="picture-card"
              class="avatar-uploader"
              :showUploadList="false"
              @preview="handlePreview"
              :beforeUpload="beforeUpload"
              @change="handleChangeImageUpload"
              :customRequest="imageUploadRequest">
              <a-row>
                <a-col :span="12">
                  <img class="previewImage" v-if="previewImage" :src="previewImage" alt="image"/>
                </a-col>
              </a-row>
              <a-row >
                <a-col :span="24">
                  <a-icon type="plus"/>
                  <div v-if="previewImage" class="ant-upload-text">重新上传图片</div>
                  <div v-else class="ant-upload-text">图片上传</div>
                </a-col>
              </a-row>

            </a-upload>

            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage"/>
            </a-modal>

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
        name: "userGradeModal",
        data() {
            return {
                title: "操作",
                visible: false,
                visibleCheck: true,
                model: {
                    gradeImage: '',
                    width: 181,
                    high: 322,
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
                //图片加载
                imageLoading: false,
                //上传的图片列表
                // fileList: [],
                // 表单验证
                validatorRules: {
                    grade:{
                        rules: [{
                            required: true,
                            pattern:/^[1-9]\d{0,1}$/,
                            message: '请输入2位以内数字!'
                        }],
                    },
                    gradeDesc: {
                        rules: [
                            {required: true, message: '请输入等级称谓!'},
                            {min: 1, max: 8, message: '回答最多8个字符', trigger: 'blur'}
                        ]
                    },
                    gradeExp: {
                        rules: [
                            { required: true,
                                pattern:/^[0-9]\d{0,5}$/,
                                message: '请输入6位以内数字!'},
                        ]
                    },
                    playLength: {
                        rules: [
                            { required: true,}
                                // pattern:/^([1-9]\d{0,2}|[1-6]\d{3}|7[0-1]\d{2}|7200)$/,
                                // message: '请输入不能超过7200（秒）拍摄时长!'},
                        ]
                    },
                    playCount: {
                        rules: [
                            { required: true,
                                pattern:/^[1-9]\d{0,3}$/,
                                message: '请输入观看次数（5位以内数字!）'},
                        ]
                    },
                    restrictPlay: {
                        rules: [
                            { required: true,
                                pattern:/^[0-10]\d{0,0}$/,
                                message: '请输入是否限制播放：0：限制播放 1：播放次数不限制（无限次）'},
                        ]
                    },

                },
                url: {
                    add: "/sys/userGrade/add",
                    edit: "/sys/userGrade/modify",
                    fileUpload: "/upload/file",
                },
                displayDisabled: false,
                displayDisableds: false,
                displayDisabledGrade: false,
            }
        },
        created() {

        },
        computed: {},
        methods: {
            add() {
                this.previewImage = '';
                // this.imageLoading = false;
                this.edit({});
            },
            edit(record) {
                if (record.id) {
                    this.displayDisabledGrade = true
                    // this.displayDisabled = true
                    this.displayDisableds = true
                }else{
                    this.displayDisabledGrade = false
                    // this.displayDisabled = false
                    this.displayDisableds = false
                }
                this.form.resetFields();
                this.previewImage = record.icon;
                this.model = Object.assign({}, record);
                this.visible = true;
                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model
                        , 'createDate'
                        , 'modifyDate'
                        , 'grade'
                        , 'gradeDesc'
                        , 'playCount'
                        , 'playLength'
                        , 'gradeExp'
                        , 'restrictPlay'
                        , 'icon'
                    ))
                    // this.form.setFieldsValue({createDate: this.model.createDate ? moment(this.model.createDate) : null})
                    // this.form.setFieldsValue({modifyDate: this.model.modifyDate ? moment(this.model.modifyDate) : null})
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
                        let icon = this.model.gradeImage
                        let formData = Object.assign(this.model, values);
                        formData.icon = icon;
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
            //图片上传前 格式验证
            beforeUpload: function (file) {
                const isJPG = file.type === 'image/jpg'
                const isJPEG = file.type === 'image/jpeg'
                const isPng = file.type === 'image/png'
                if (!isJPG && !isJPEG && !isPng) {
                    this.$message.error('请上传jpg/jpeg/png格式的图片!')
                    return false
                }
                // const isLt2M = file.size / 1024 / 1024 < 2
                // if (!isLt2M) {
                //     this.$message.error('请上传2MB以内的开屏广告图片!')
                //     return false
                // }
                return new Promise((resolve, reject) => {
                    this.imageRead(file).then((img) => {
                        // if (img.width != 117 && img.height != 160) {
                        //     this.$message.error('请上传181 x 322像素尺寸的图片!')
                        //     reject(file);
                        // } else {
                        resolve(file);
                        // }
                    }).catch(() => {
                    });
                });
            },
            //文件上传触发事件
            handleChangeFileUpload(info) {
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
            imageUploadRequest: function ({file}) {
                postAction(this.url.fileUpload, fileUpload(file)).then((res) => {
                    let data = res.data;
                    if (data.status == 0) {
                        console.log(data)
                        this.previewImage = data.domain + data.path;
                        this.model.gradeImage = data.domain + data.path
                    }
                })
            },
            //图片点击放大预览
            handlePreview(file) {
                this.previewImage = file.url || file.thumbUrl;
                this.previewVisible = true;
            },
            //图片上传触发事件
            handleChangeImageUpload(info) {
                if (info.file.status === 'uploading') {
                    this.imageLoading = true;
                    return;
                }
                if (info.file.status === 'done') {
                    this.imageLoading = false;
                }
            },
            //图片上传 自定义请求 进行上传
            customImageUploadRequest: function ({onSuccess, onError, file}) {
                postAction(this.url.fileUploadImg, fileUpload(file)).then((res) => {
                    let data = res.data;
                    if (data.status == 0) {
                        let file = {};
                        file.uid = data.path;
                        file.name = data.path;
                        file.url = data.domain + data.path;
                        file.status = "done";
                        this.fileList.push(file);
                    }
                })
            },
            //是否限制观看
            handleChangePlayCount(value) {
                console.log(value.data);
                if (value.data == 1){
                    this.displayDisabled = true
                } else {
                    this.displayDisabled = false
                }
            },
            imageRead(file) {
                return new Promise((resolve, reject) => {
                    let reader = new FileReader()
                    reader.onload = function (e) {
                        let data = e.target.result
                        let img = new Image()
                        img.src = data
                        img.onload = function () {
                            resolve({
                                width: img.width,
                                height: img.height
                            })
                        }
                    }
                    reader.readAsDataURL(file)
                }).catch((e) => {
                });
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
  .previewImage {
    width: 90px;
    height: 90px;
  }
</style>