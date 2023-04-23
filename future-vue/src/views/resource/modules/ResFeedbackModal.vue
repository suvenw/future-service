<template>
    <a-drawer
          :title="feedbackTitle"
          :width="800"
          placement="right"
          @close="handleCancel"
          :visible="visible">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          style="display:none"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="hidden">
          <a-input v-decorator="['status', {}]" />
          <a-input v-decorator="['userId', {}]" />
          <a-input v-decorator="['imageUrl', {}]" />
          <a-input v-decorator="['reply', {}]" />
          <a-input v-decorator="['feedbackType', {}]" />
          <a-input v-decorator="['replyStatus', {}]" />
          <a-input v-decorator="['operator', {}]" />
          <a-input v-decorator="['modifyDate', {}]" />
        </a-form-item>        
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="ID">
          <a-input disabled placeholder="请输入状态" v-decorator="['userId',{}]" />
        </a-form-item> 
        <a-form-item label="反馈类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select disabled v-decorator="[ 'feedbackType', {}]" placeholder="请选择类型">
            <a-select-option :value="1">功能问题</a-select-option>
            <a-select-option :value="2">系统问题</a-select-option>
          </a-select>
         </a-form-item>
         <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="反馈时间">
          <a-input disabled placeholder="请输入反馈时间" v-decorator="['createDate',{}]" />
        </a-form-item>       
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="反馈内容">
          <a-textarea disabled :rows="8" placeholder="请输入反馈内容" v-decorator="['content',{}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="反馈图片">
          <img :src="this.model.imageUrlShow" class="feedbackImage" @click="handlePreview">
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          :label="replyTitle">
          <a-textarea :disabled="replyDisabled" :rows="8" placeholder="请输内容" v-decorator="['reply', validatorRules.reply]" />
        </a-form-item>    
        <a-form-item
          v-if="this.model.replyStatus == 2"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="回复时间">
          <a-input disabled placeholder="请输入回复时间" v-decorator="['modifyDate',{}]" />
        </a-form-item>       
        <a-form-item
          v-if="this.model.replyStatus == 2"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="操作人编号">
          <a-input disabled placeholder="请输入状态" v-decorator="['operator',{}]" />
        </a-form-item>         

      </a-form>
    </a-spin>
    <a-modal :visible="previewVisible" :footer="null" @cancel="previewHandleCancel">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>
    <a-button :disabled="submitDisable" type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
  import { httpAction,postAction,fileUpload } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import { } from "@/utils/validate"

  export default {
    name: "resFeedbackModal",
    data () {
      return {
        feedbackTitle:"操作",
        visible: false,
        visibleCheck: true,
        model: {},
        submitDisable:false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        headers:{},
        form: this.$form.createForm(this),
        previewVisible: false,
        //图片预览
        previewImage: '',
        //上传的图片列表
        fileList: [],
        // 表单验证
        validatorRules:{
          reply:{
            rules: [
              { min: 0, max: 500, message: '长度在 0 到 500 个字符', trigger: 'blur' }
            ]
          }
        },
        url: {
          add: "/resource/resfeedback/add",
          edit: "/resource/resfeedback/modify",
          fileUpload: "/resource/resfeedback/import",
          fileUploadImg: "/upload/file",
        },
        replyDisabled: false,
        replyTitle: '回复'
      }
    },
    created () {
    },
    computed:{
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        if (record.replyStatus == 1) {
          this.feedbackTitle = '意见反馈回复'
          this.replyTitle = '回复'
          this.replyDisabled = false
          this.submitDisable = false
        } else {
          this.feedbackTitle = '查看详情'
          this.replyTitle = '回复内容'
          this.replyDisabled = true
          this.submitDisable = true
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model
             ,'createDate'
             ,'modifyDate'
             ,'userId'
             ,'imageUrl'
             ,'content'
             ,'reply'
             ,'feedbackType'
             ,'status'
             ,'replyStatus'
             ,'operator'
           ))
          // //时间格式化
          this.form.setFieldsValue({createDate:this.model.createDate ? moment(this.model.createDate).format('YYYY-MM-DD HH:mm:ss'):null})
          this.form.setFieldsValue({modifyDate:this.model.modifyDate ? moment(this.model.modifyDate).format('YYYY-MM-DD HH:mm:ss'):null})
        });
      },
      close () {
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
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'post';
            }
            let formData = Object.assign(this.model, values);
            formData.operator = this.$store.getters.userInfo.id
            //时间格式化
            // formData.createDate = formData.createDate?formData.createDate.format('YYYY-MM-DD HH:mm:ss'):null;
            // formData.modifyDate = formData.modifyDate?formData.modifyDate.format('YYYY-MM-DD HH:mm:ss'):null;
            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{

                if(res.code == 0){ //res.success
                  that.$message.success(res.msg);
                  that.$emit('ok');
                }else{
                  that.$message.warning(res.msg);
                }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })



          }
        })
      },
      handleCancel () {
        this.previewVisible = false;
        this.close()
      },
      previewHandleCancel () {
        this.previewVisible = false;
      },
      //图片上传前 格式验证
      beforeUpload: function(file){
        var fileType = file.type;
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
        //TODO 验证文件大小
      },
      //文件上传触发事件
      handleChangeFileUpload (info) {
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          this.$message.success(info.file.name+'文件上传成功');
        } else if (info.file.status === 'error') {
          this.$message.error(info.file.name+'文件上传失败');
        }
      },
      //文件上传 自定义请求 进行上传
      customFileUploadRequest:function({ onSuccess, onError, file }) {
        //setTimeout(() => {
        postAction(this.url.fileUpload,fileUpload(file)).then((res)=>{
          if(res.code ==0){
            onSuccess(null, file);
          }else{
            onError();
          }
          //}, 100);
        }).finally(() => {
          this.confirmLoading = false;
        })
      },
      //图片点击放大预览
      handlePreview() {
        this.previewImage = this.model.imageUrlShow;
        this.previewVisible = true;
      },
      //图片上传触发事件
      handleChangeImageUpload({fileList}) {
        for (var i = fileList.length - 1; i >= 0; i--) {
          var obj = fileList[i]
          if (obj.status != 'done') {
            fileList.splice(i, 1);
          }
        }
        this.fileList = fileList;
      },
      //图片上传 自定义请求 进行上传
      customImageUploadRequest:function({ onSuccess, onError, file }) {
        postAction(this.url.fileUploadImg,fileUpload(file)).then((res)=> {
          let data=res.data;
          if (data.status == 0) {
            let file = {};
            file.uid = data.path;
            file.name = data.path;
            file.url = data.domain + data.path;
            file.status="done";
            this.fileList.push(file);
          }
        })
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
  .feedbackImage {
    width: 88px;
  }
</style>