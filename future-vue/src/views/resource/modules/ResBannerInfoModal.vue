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
          label="广告名称">
          <a-input placeholder="请输入广告名称" v-decorator="['name',
                   validatorRules.name]"/>
        </a-form-item>

        <a-form-item label="是否默认广告" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select v-decorator="[ 'isDefault', validatorRules.isDefault]" placeholder="请选择状态">
            <a-select-option :value="2">是</a-select-option>
            <a-select-option :value="1">否</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上架时间">
          <a-date-picker format='YYYY-MM-DD' v-decorator="[ 'startDate', validatorRules.startDate]" @change="onChangeStartDate"/>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="下架时间">
          <a-date-picker format='YYYY-MM-DD' v-decorator="[ 'endDate', validatorRules.endDate]"  @change="onChangeEndDate"/>
        </a-form-item>


        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select v-decorator="[ 'status', validatorRules.status]" placeholder="请选择状态">
            <a-select-option :value="2">上架</a-select-option>
            <a-select-option :value="1">下架</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="广告(图片地址)">
          <a-upload
            v-decorator="[ 'url', validatorRules.url]"
            listType="picture-card"
            class="avatar-uploader"
            :showUploadList="false"
            :beforeUpload="imageBeforeUpload"
            @change="imageHandleChange"
            :customRequest="imageUploadRequest">

            <a-row>
              <a-col :span="12">
                <img class="bannerImage" v-if="bannerImageShow" :src="bannerImageShow" alt="image"/>
              </a-col>
            </a-row>
            <a-row >
              <a-col :span="24">
                <a-icon type="plus"/>
                <div v-if="bannerImageShow" class="ant-upload-text">重新上传图片</div>
                <div v-else class="ant-upload-text">图片上传</div>
              </a-col>
            </a-row>
          </a-upload>

          <a-row>
            <a-col :span="20">
              <div>说明：<br/>
                （1）只能上传jpg/png/jpeg格式文件 <br/>
                （2）图片尺寸大小不能超过2MB </div>
            </a-col>
          </a-row>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="广告跳转url地址">
          <a-input placeholder="请输入广告跳转url地址" v-decorator="['link',
                    validatorRules.link]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="排序">
          <a-input-number placeholder="请输入排序" v-decorator="['sort',
                   validatorRules.sort]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="备注">
          <a-textarea placeholder="请输入备注" v-decorator="['remarks', validatorRules.remarks]"  />
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
        name: "resBannerInfoModal",
        data() {
            return {
                title: "操作",
                visible: false,
                visibleCheck: true,
                model: {
                    url: '',
                    bannerImage: '',
                    startDate: '',
                    endDate: ''
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
                    name: {
                        rules: [
                            {required: true, message: '请输入广告名称!'},
                            {min: 1, max: 20, message: '广告名称最多输入20个字符', trigger: 'blur'}
                        ]
                    },
                    isDefault: {
                        rules: [
                            {required: true, message: '请选择默认广告'}
                        ]
                    },
                    validDate: {
                        rules: [
                            {required: true, message: '请选择开始广告时间和广告结束时间'}
                        ]
                    },
                    status: {
                        rules: [
                            {required: true, message: '请选择状态'}
                        ]
                    },
                    url: {
                        rules: [
                            {required: true, message: '请选择图片地址'}
                        ]
                    },
                    link: {
                        rules: [
                            {required: true, message: '请选择图片地址'}
                        ]
                    },
                    sort:{
                        rules: [
                            { required: true, message: '请输入排序序号!' },
                            { validator: this.validateCount }
                        ]
                    },
                    remarks:{
                        rules: [
                            { required: true, message: '请输入广告备注!' },
                            {min: 1, max: 200, message: '广告备注最多输入200个字符', trigger: 'blur'}
                        ]
                    },
                    startDate:{
                        rules: [
                            { required: true, message: '选择上架时间' },
                            { validator: this.startDateValidator }
                        ]
                    },
                    endDate:{
                        rules: [
                            { required: true, message: '请选择下架时间' },
                            { validator: this.endDateValidator }
                        ]
                    },
                },
                picUrl: "",
                url: {
                    add: "/resource/banner/add",
                    edit: "/resource/banner/modify",
                    fileUpload: "/upload/file",
                },
                imageLoading: false,
                bannerImageShow: '',
                validDate: [],
                stDate: '',
                edDate: '',
            }
        },
        created() {

        },
        computed: {},
        methods: {

            validateCount  (rule, value, callback) {
                if (value < 0 || value > 9999) {
                    callback('数字的输入范围为：0～9999');
                }
                callback();
            },
            startDateValidator (rule, value, callback) {
                if (this.edDate != undefined && this.edDate != '') {
                    if (this.edDate < value) {
                        callback('上架时间不能大于下架时间');
                    }

                }
                callback();
            },
            endDateValidator (rule, value, callback) {
                if (this.stDate != undefined && this.stDate != '') {
                    if (this.stDate > value) {
                        callback('下架时间不能小于上架时间');
                    }
                }
                callback();
            },
            onChangeStartDate (date) {
                this.stDate = date
            },
            onChangeEndDate (date) {
                this.edDate = date
            },
            add() {
                this.bannerImageShow = '';
                this.imageLoading = false;
                this.edit({});
            },
            edit(record) {
                this.form.resetFields();
                this.model = Object.assign({}, record);
                this.bannerImageShow = record.urlShow;
                this.stDate = record.startDate;
                this.edDate = record.endDate;
                console.info(record);
                this.visible = true;
                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model
                        , 'status'
                        , 'name'
                        , 'link'
                        , 'url'
                        , 'startDate'
                        , 'endDate'
                        , 'sort'
                        , 'linkCount'
                        , 'isDefault'
                        , 'createDate'
                        , 'modifyDate'
                        , 'remarks'
                    ))
                    //时间格式化
                    this.form.setFieldsValue({startDate: this.model.startDate ? moment(this.model.startDate) : null});
                    this.form.setFieldsValue({endDate: this.model.endDate ? moment(this.model.endDate) : null});
                    // this.form.setFieldsValue({startDate: this.model.createDate ? moment(this.model.createDate) : null})
                    // this.form.setFieldsValue({endDate: this.model.modifyDate ? moment(this.model.modifyDate) : null})
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
                        let url = this.model.bannerImage
                        let formData = Object.assign(this.model, values);
                        //时间格式化
                        formData.startDate = formData.startDate ? formData.startDate.format('YYYY-MM-DD HH:mm:ss') : null;
                        formData.endDate = formData.endDate ? formData.endDate.format('YYYY-MM-DD HH:mm:ss') : null;
                        formData.selectedroles = this.selectedRole.length > 0 ? this.selectedRole.join(",") : '';
                        formData.url = url;
                        formData.category = '3';
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

            imageBeforeUpload(file) {
                const isJPG = file.type === 'image/jpg'
                const isJPEG = file.type === 'image/jpeg'
                const isPng = file.type === 'image/png'
                if (!isJPG && !isJPEG && !isPng) {
                    this.$message.error('请上传jpg/jpeg/png格式的图片!')
                    return false
                }
                const isLt2M = file.size / 1024 / 1024 < 2
                if (!isLt2M) {
                    this.$message.error('上传图片文件的大小不能超过2M!')
                    return false
                }

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
            imageHandleChange(info) {
                if (info.file.status === 'uploading') {
                    this.imageLoading = true;
                    return;
                }
                if (info.file.status === 'done') {
                    this.imageLoading = false;
                }
            },
            imageUploadRequest({onSuccess, onError, file}) {
                postAction(this.url.fileUpload, fileUpload(file)).then((res) => {
                    let data = res.data;
                    if (data.status == 0) {
                        console.log(data.domain + data.path)
                        this.bannerImageShow = data.domain + data.path
                        this.model.bannerImage = data.domain + data.path
                    }
                })
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
            base64ImgtoFile(dataurl, filename = 'file') {
                let arr = dataurl.split(',')
                let mime = arr[0].match(/:(.*?);/)[1]
                let suffix = mime.split('/')[1]
                let bstr = atob(arr[1])
                let n = bstr.length
                let u8arr = new Uint8Array(n)
                while (n--) {
                    u8arr[n] = bstr.charCodeAt(n)
                }
                return new File([u8arr], `${filename}.${suffix}`, {
                    type: mime
                })
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
  .bannerImage {
    width: 90px;
    height: 90px;
  }
</style>