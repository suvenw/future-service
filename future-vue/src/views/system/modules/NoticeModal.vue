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
          label="公告内容">
         <a-input placeholder="请输入公告内容" v-decorator="['content',validatorRules.content]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="排序序号">
          <a-input-number :min="1" v-decorator="['sort', validatorRules.classifyOrder]"/>
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

  export default {
    name: "noticeModal",
    data () {
      return {
        title:"操作",
        visible: false,
        visibleCheck: true,
        model: {},
        selectedRole:[],
        disableSubmit:false,
        roleList:[
          {"id":"1","roleName":"测试1","roleCode":"test1"},
          {"id":"2","roleName":"测试2","roleCode":"test2"},
          {"id":"3","roleName":"测试3","roleCode":"test3"}],
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
          content:{
            rules: [
                { required: true, message: '请输入公告内容' },
                { min: 1, max: 30, message: '公告内容最多输入30个字符', trigger: 'blur' }
            ]
          },
          classifyOrder:{
              rules: [
                  { required: true, message: '请输入排序序号!' },
                  { validator: this.validateCount }
              ]
          },
        },
        url: {
          add: "/sys/notice/add",
          edit: "/sys/notice/modify",
          fileUpload: "/sys/notice/import",
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
                                     ,'content'
                                     ,'sort'
                                 ))});
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
              formData.title = '游戏公告';
              formData.operator = this.$store.getters.userInfo.id;
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
      validateCount  (rule, value, callback) {
          if (value < 0 || value > 9999) {
              callback('数字的输入范围为：0～9999');
          }
          callback();
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