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
          label="平台分类:">
            <a-select  v-decorator="['platform', validatorRules.platform]" placeholder="请选择平台分类" >
              <a-select-option :value="0">请选择</a-select-option>
              <a-select-option :value="1">IOS</a-select-option>
              <a-select-option :value="2">Android</a-select-option>
              <a-select-option :value="3">PC</a-select-option>
            </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="版本名称:">
                    <a-input placeholder="请输入版本名称,格式:100001 六位整数" v-decorator="['version',
                   {}]" />
         </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="下载地址">
                    <a-input placeholder="开头http://或者https://" v-decorator="['download',
                   {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="强制更新:">
          <a-select  v-decorator="['forceUpdate', validatorRules.forceUpdate]" placeholder="是否强制更新" >
            <a-select-option :value="1">是</a-select-option>
            <a-select-option :value="0">否</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="版本介绍内容:">
                    <a-input placeholder="请输入版本介绍内容" v-decorator="['description',
                   {}]" />
        </a-form-item>

      </a-form>
    </a-spin>
    <a-button :disabled="disableSubmit" type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import {} from "@/utils/validate"

  export default {
    name: "versionInfoModal",
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
                                                                                                                                                                                },
        url: {
          add: "/resource/version/add",
          edit: "/resource/version/modify",
          fileUpload: "/resource/version/import",
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
                       ,'platform'
                                     ,'version'
                                     ,'download'
                                     ,'description',
                                      'forceUpdate'
                                 ))
//时间格式化
// this.form.setFieldsValue({publishDate:this.model.publishDate ? moment(this.model.publishDate):null})
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
            //时间格式化
            //                                                                                                                                                                          formData.publishDate = formData.publishDate?formData.publishDate.format('YYYY-MM-DD HH:mm:ss'):null;
                                            formData.selectedroles = this.selectedRole.length>0?this.selectedRole.join(","):'';
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