<template xmlns:display="http://www.w3.org/1999/xhtml">
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
          label="推荐广告标题">
          <a-input placeholder="请输入推荐广告标题" v-decorator="['videoName',validatorRules.videoName]"/>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上架时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'startDate',validatorRules.startDate]" @change="onChangeStartDate" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="下架时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'endDate',validatorRules.endDate]" @change="onChangeEndDate" />
        </a-form-item>

        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select v-decorator="[ 'status', validatorRules.status]" placeholder="请选择状态">
            <a-select-option :value="1">上架</a-select-option>
            <a-select-option :value="2">下架</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item label="广告类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select v-decorator="[ 'bannerType', validatorRules.bannerType]"
                    @change="bannerTypeChange" placeholder="请选择状态">
            <a-select-option :value="1">图片</a-select-option>
            <a-select-option :value="2">视频</a-select-option>
          </a-select>
        </a-form-item>

        <div v-show="videoShowItem">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="视频文件">
          <a-upload
            v-decorator="[ 'videoUrl', validatorRules.videoUrl]"
            listType="picture-card"
            class="avatar-uploader"
            :showUploadList="false"
            :beforeUpload="fileBeforeUpload"
            @change="fileHandleChange"
            :customRequest="fileUploadRequest">

            <a-row>
              <a-col :span="12">
                <video class="videoUrl" v-if="this.model.videoUrl" :src="this.videoUrlShow" alt="video"/>
              </a-col>
            </a-row>
            <a-row >
              <a-col :span="24">
                <a-icon type="plus"/>
                <div v-if="this.model.videoUrl" class="ant-upload-text">重新上传视频</div>
                <div v-else class="ant-upload-text">视频上传</div>
              </a-col>
            </a-row>
          </a-upload>

          <a-row>
            <a-col :span="24">
              <div>说明：<br/>
                （1）只能上传jpg/png/jpeg格式文件 <br/>
                （2）封面图片尺寸大小为181 x 322像素，且不能超过5MB  <br/>
                （3）视频只能上传mov/avi/mp4/flv/mkv格式的文件，且不能超过2048MB </div>
            </a-col>
          </a-row>

        </a-form-item>
        </div >

       <div v-show="imageShowItem">
         <a-form-item
           :labelCol="labelCol"
           :wrapperCol="wrapperCol"
           label="广告图片">
           <a-upload
             v-decorator="[ 'videoImage', validatorRules.videoImage]"
             listType="picture-card"
             class="avatar-uploader"
             :showUploadList="false"
             :beforeUpload="imageBeforeUpload"
             @change="imageHandleChange"
             :customRequest="imageUploadRequest"
           >

             <a-row>
               <a-col :span="12">
                 <img class="videoImage" v-if="videoImageShow" :src="videoImageShow" alt="image"/>
               </a-col>
             </a-row>
             <a-row >
               <a-col :span="24">
                 <a-icon type="plus"/>
                 <div v-if="videoImageShow" class="ant-upload-text">重新上传图片</div>
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
       </div>


        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="链接地址">
          <a-input placeholder="请输入链接地址" v-decorator="['advUrl',
                   validatorRules.advUrl]"/>
        </a-form-item>


        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="排序">
          <a-input placeholder="请输入排序" v-decorator="['sort',
                   validatorRules.sort]"/>
        </a-form-item>


        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="广告备注">
          <a-input placeholder="请输入广告备注" v-decorator="['videoDesc',
                  validatorRules.videoDesc]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="虚假点赞次数">
          <a-input-number v-decorator="[ 'virtualPraiseCount', validatorRules.virtualPraiseCount]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="虚假分享次数">
          <a-input-number v-decorator="[ 'virtualShareCount', validatorRules.virtualShareCount]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="虚假播放次数">
          <a-input-number v-decorator="[ 'virtualPlayCount', validatorRules.virtualPlayCount]"/>
        </a-form-item>

      </a-form>
    </a-spin>
    <a-button :disabled="disableSubmit" type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>

    <a-modal
      :visible="fileUploadVisible"
      :footer="null"
      :closable="false"
    >
      <a-progress type="circle" :percent="filePercent"/>
    </a-modal>

  </a-drawer>
</template>

<script>
    import {httpAction, postAction, fileUpload} from '@/api/manage'
    import pick from 'lodash.pick'
    import moment from "moment"
    import {} from "@/utils/validate"

    export default {
        name: "videoInfoModal",
        data() {
            return {
                title: "操作",
                visible: false,
                visibleCheck: true,
                fileUploadVisible: false,
                filePercent: 99,
                model: {
                    createDate: new Date(),
                    videoUrl: '',
                    videoImage: '',
                    width: 0,
                    high: 0,
                    playLength: 0,
                    playSize: 0},
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
                    videoName:{
                        rules: [
                            { required: true, message: '请输入推荐广告名称（标题）!' },
                            { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
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
                    status: {
                        rules: [
                            {required: true, message: '请选择状态'}
                        ]
                    },
                    bannerType: {
                        rules: [
                            {required: true, message: '请选择状态'}
                        ]
                    },
                    videoUrl: {
                        rules: [
                            { required: false, message: '请上传视频文件!' }
                        ]
                    },
                    videoImage: {
                        rules: [
                            { required: false, message: '请上传视频图片!' }
                        ]
                    },
                    advUrl: {
                        rules: [
                             {required: true, message: '请输入链接地址!'},
                            {validator: this.validateValue}
                        ]
                    },
                    sort:{
                        rules: [
                            { required: true, message: '请输入排序序号!' },
                            { validator: this.validateCount }
                        ]
                    },
                    videoDesc: {
                        rules: [
                            {required: false, message: '请输入广告备注!'},
                            {validator: this.validateDesc}
                        ]
                    },
                    virtualPraiseCount:{
                        rules: [
                            { validator: this.validateCount }
                        ]
                    },
                    virtualShareCount:{
                        rules: [
                            { validator: this.validateCount }
                        ]
                    },

                },
                url: {
                    add: "/resource/bannerVideo/add",
                    edit: "/resource/bannerVideo/modify",
                    fileUpload: "/upload/file",
                },
                imageLoading: false,
                videoLoading: false,
                videoImageShow: '',
                videoUrlShow: '',
                videoShowItem: false,
                imageShowItem: false,
                stDate: '',
                edDate: '',
            }
        },
        created() {

        },
        computed: {},
        methods: {
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
            validateDesc(rule, value, callback) {
                if (value.length <= 200 && value.length >= 1) {
                    callback();
                } else {
                    callback('广告备注最多200字符');
                }
            },
            validateCount  (rule, value, callback) {
                if (value < 0 || value > 9999) {
                    callback('数字的输入范围为：0～9999');
                }
                callback();
            },
            validateValue(rule, value, callback) {
                if (value.indexOf("http://") || value.indexOf("https://")) {
                    callback();
                }else {
                    callback('URL必须以http://或https://开头');
                }
            },
            onChangeStartDate (date) {
                this.stDate = date
            },
            onChangeEndDate (date) {
                this.edDate = date
            },
            add() {
                this.edit({});
            },
            edit(record) {
                this.form.resetFields();
                this.model = Object.assign({}, record);
                this.visible = true;
                this.videoImageShow = record.videoImageShow;
                this.videoUrlShow = record.videoUrlShow;
                this.stDate = record.startDate;
                this.edDate = record.endDate;
                //设置页面上参数值
                this.$nextTick(() => {
                    this.form.setFieldsValue(pick(this.model
                        , 'videoName'
                        , 'startDate'
                        , 'endDate'
                        , 'status'
                        , 'videoType'
                        , 'videoImage'
                        , 'advUrl'
                        , 'videoUrl'
                        , 'videoDesc'
                        , 'sort'
                        , 'videoType'
                        , 'advUrl'
                        , 'bannerType'
                    ));
//时间格式化
                    this.form.setFieldsValue({startDate: this.model.startDate ? moment(this.model.startDate) : null});
                    this.form.setFieldsValue({endDate: this.model.endDate ? moment(this.model.endDate) : null});
                    this.form.setFieldsValue({virtualPraiseCount: this.model.videoReport ? this.model.videoReport.virtualPraiseCount : null})
                    this.form.setFieldsValue({virtualShareCount: this.model.videoReport ? this.model.videoReport.virtualShareCount : null})
                    this.form.setFieldsValue({virtualPlayCount: this.model.videoReport ? this.model.videoReport.virtualPlayCount : null})
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
            //根据广告类型改变 上传图片视频选项
            bannerTypeChange(value) {
                console.log(value);
                if ( value == 1) {
                    this.videoShowItem = false;
                    this.imageShowItem = true;
                } else if (value == 2) {
                    this.imageShowItem = false;
                    this.videoShowItem = true;
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

                        let videoImage = this.model.videoImage
                        let videoUrl = this.model.videoUrl

                        if (!this.model.id) {
                            httpurl += this.url.add;
                            method = 'post';
                        } else {
                            httpurl += this.url.edit;
                            method = 'post';
                        }
                        let formData = Object.assign(this.model, values);
                        //时间格式化
                        formData.startDate = formData.startDate ? formData.startDate.format('YYYY-MM-DD HH:mm:ss') : null;
                        formData.endDate = formData.endDate ? formData.endDate.format('YYYY-MM-DD HH:mm:ss') : null;
                        formData.selectedroles = this.selectedRole.length > 0 ? this.selectedRole.join(",") : '';
                        formData.operator = this.$store.getters.userInfo.id;
                        formData.videoImage = videoImage;
                        formData.videoUrl = videoUrl;
                        console.log(formData);
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
                    this.$message.error('请上传2MB以内的开屏广告图片!')
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
                    }).catch(() => {});
                });
            },
            //文件上传触发事件
            imageHandleChange(info) {
                if (info.file.status === 'uploading') {
                    this.imageLoading = true;
                    return;
                }
                if (info.file.status === 'done') {
                    this.imageLoading = false;
                }
            },
            //文件上传 自定义请求 进行上传
            imageUploadRequest ({file}) {
                postAction(this.url.fileUpload,fileUpload(file)).then((res)=> {
                    let data=res.data;
                    if (data.status == 0) {
                        console.log(data.domain + data.path)
                        this.videoImageShow =  data.domain + data.path
                        this.model.videoImage = data.domain + data.path
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
            //图片点击放大预览
            handlePreview(file) {
                this.previewImage = file.url || file.thumbUrl;
                this.previewVisible = true;
            },
            fileHandleChange(info) {
                if (info.file.status === 'uploading') {
                    this.videoLoading = true;
                    return;
                }
                if (info.file.status === 'done') {
                    this.videoLoading = false;
                }

            },
            fileBeforeUpload(file) {
                let _this = this
                let isMOV = file.type === 'video/mov'
                let isAVI = file.type === 'video/avi'
                let isMP4 = file.type === 'video/mp4'
                let isFLV = file.type === 'video/flv'
                let isMKV = file.type === 'video/mkv'
                if (!isMOV && !isAVI && !isMP4 && !isFLV && !isMKV) {
                    this.$message.error('请上传mov/avi/mp4/flv/mkv格式的视频文件!')
                    return false
                }
                let isLt2048M = file.size / 1024 / 1024 < 2048
                if (!isLt2048M) {
                    this.$message.error('请上传2048MB以内的视频文件!')
                    return false
                }
                return true
            },
            fileUploadRequest ({ onSuccess, onError, file }) {
                let _this = this;
                _this.fileUploadVisible = true;
                //加载视频信息
                let url = URL.createObjectURL(file);
                let videoElement = document.createElement('video');
                videoElement.src = url;

                videoElement.addEventListener("loadedmetadata", function (_event) {
                    _this.model.playLength = Math.ceil(videoElement.duration); //获取录音时长
                    _this.model.playSize = file.size;
                    _this.model.width =  this.videoWidth;
                    _this.model.high =  this.videoHeight;

                    let canvas = document.createElement("canvas");
                    canvas.width = "181";
                    canvas.height = "322";
                    // 等待video获取到第一帧后
                    videoElement.currentTime = 1; // 第一帧
                    videoElement.oncanplay = () => {
                        // 利用canvas对象方法绘图
                        canvas.getContext("2d").drawImage(videoElement, 0, 0, canvas.width, canvas.height);
                        // 转换成base64形式
                        let base64 = canvas.toDataURL("image/jpeg") ;// 这个就是图片的base64
                        let file = _this.base64ImgtoFile(base64);
                        // 第一帧上传
                        postAction(_this.url.fileUpload,fileUpload(file)).then((res)=> {
                            let data=res.data;
                            if (data.status == 0) {
                                _this.videoImageShow = data.domain + data.path;
                                _this.model.videoImage = data.path
                            }
                        })
                    }
                });
                //视频文件上传
                postAction(this.url.fileUpload,fileUpload(file)).then((res)=> {
                    let data=res.data;
                    if (data.status == 0) {
                        this.model.videoUrl = data.path;
                        this.videoUrlShow = data.domain + data.path;
                        onSuccess(null, file);
                    }else{
                        onError();
                    }
                    this.fileUploadVisible = false
                })
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

<style scoped>
  .ant-progress-circle-wrap,
  .ant-progress-line-wrap {
    margin-right: 8px;
    margin-bottom: 5px;
  }
  .ant-modal-body {
    text-align: center;
  }
</style>

<style>
  /* you can make up upload button and sample style by using stylesheets */
  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }

  .videoImage {
    width: 90px;
    height: 90px;
  }
  .videoUrl {
    width: 90px;
    height: 90px;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
</style>