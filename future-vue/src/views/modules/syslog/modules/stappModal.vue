<template>
  <a-drawer
      :title="title"
      :width="800"
      placement="right"
      :closable="false"
      @close="close"
      :visible="visible"
  >

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">


        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="创建时间">
                    <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'createDate', {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="修改时间">
                    <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'modifyDate', {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="应用名称">
                    <a-input placeholder="请输入应用名称" v-decorator="['appName',
               {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="应用路径">
                    <a-input placeholder="请输入应用路径" v-decorator="['path',
               {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="APP版本">
                    <a-input placeholder="请输入APP版本" v-decorator="['version',
               {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="备注">
                    <a-input placeholder="请输入备注" v-decorator="['remark',
               {}]" />
                  </a-form-item>

      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "stAppModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
                                                                                                                                                                                                                                },
        url: {
          add: "/user/stapp/add",
          edit: "/user/stapp/edit",
        },
      }
    },
    created () {
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
                                               ,'createDate'
                                     ,'modifyDate'
                                     ,'appName'
                                     ,'path'
                                     ,'version'
                                     ,'remark'
                                 ))
		  //时间格式化
                                                   this.form.setFieldsValue({createDate:this.model.createDate ? moment(this.model.createDate):null})
                                        this.form.setFieldsValue({modifyDate:this.model.modifyDate ? moment(this.model.modifyDate):null})
                                        this.form.setFieldsValue({appName:this.model.appName ? moment(this.model.appName):null})
                                        this.form.setFieldsValue({path:this.model.path ? moment(this.model.path):null})
                                        this.form.setFieldsValue({version:this.model.version ? moment(this.model.version):null})
                                        this.form.setFieldsValue({remark:this.model.remark ? moment(this.model.remark):null})
                            });

      },
      close () {
        this.$emit('close');
        this.visible = false;
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
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
                                                                                     formData.createDate = formData.createDate?formData.createDate.format('YYYY-MM-DD HH:mm:ss'):null;
                                                                        formData.modifyDate = formData.modifyDate?formData.modifyDate.format('YYYY-MM-DD HH:mm:ss'):null;
                                                                                                                                                                
            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })



          }
        })
      },
      handleCancel () {
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