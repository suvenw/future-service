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
          style="display:none"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="hidden">
          <a-input v-decorator="['type', {}]" />
          <a-input v-decorator="['showTitle', {}]" />
          <a-input v-decorator="['startDate', {}]" />
          <a-input v-decorator="['endDate', {}]" />
          <a-input v-decorator="['platform', {}]" />
          <a-input v-decorator="['videoCommentId', {}]" />
          <a-input v-decorator="['sort', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="公告标题">
          <a-input placeholder="请输入公告标题" v-decorator="['title', validatorRules.title]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="公告内容">
          <a-input placeholder="请输入公告内容" v-decorator="['content', validatorRules.content]" />
        </a-form-item>


        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="链接地址">
          <a-input placeholder="请输入链接地址" v-decorator="['url', {}]" />
        </a-form-item>

        

      </a-form>
    </a-spin>
    <a-button :disabled="disableSubmit" type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
  import { httpAction,postAction,fileUpload } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import {} from "@/utils/validate"

  export default {
    name: "resNoticeModal",
    data () {
      return {
        title:"操作",
        visible: false,
        visibleCheck: true,
        model: {},
        disableSubmit:false,
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
          title:{
            rules: [
              { required: true, message: '请输入公告标题!' },
              { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
            ]
          },
          content:{
            rules: [
              { required: true, message: '请输入公告内容!' },
              { min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur' }
            ]
          }
        },       
        url: {
          add: "/resource/resnotice/add",
          edit: "/resource/resnotice/modify",
          fileUpload: "/resource/resnotice/import",
          fileUploadImg: "/upload/file",
        },
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
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model
             ,'url'
             ,'title'
             ,'content'
             ,'type'
             ,'showTitle'
             ,'startDate'
             ,'endDate'
             ,'platform'
             ,'videoCommentId'
             ,'sort'
          ))
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
      handlePreview(file) {
        this.previewImage = file.url || file.thumbUrl;
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
</style>