<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:5%;height: 85%;overflow-y: hidden">

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
          label="更新时间">
                    <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'modifyDate', {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="删除状态(1-正常,0-已删除)">
                    <a-input-number v-decorator="[ 'status',
                {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="排序字段,默认按升级排序">
                    <a-input-number v-decorator="[ 'sort',
                {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="角色名称">
                    <a-input placeholder="请输入角色名称" v-decorator="['roleName',
               {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="角色编码">
                    <a-input placeholder="请输入角色编码" v-decorator="['roleCode',
               {}]" />
                  </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="描述">
                    <a-input placeholder="请输入描述" v-decorator="['description',
               {}]" />
                  </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "sysRoleModal2",
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
          add: "/sys/sysRole/add",
          edit: "/sys/sysRole/modify",
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
                                     ,'status'
                                     ,'sort'
                                     ,'roleName'
                                     ,'roleCode'
                                     ,'description'
                                 ))
		  //时间格式化
                                                   this.form.setFieldsValue({createDate:this.model.createDate ? moment(this.model.createDate):null})
                                        this.form.setFieldsValue({modifyDate:this.model.modifyDate ? moment(this.model.modifyDate):null})
                                        // this.form.setFieldsValue({status:this.model.status })
                                        // this.form.setFieldsValue({sort:this.model.sort})
                                        // this.form.setFieldsValue({roleName:this.model.roleName ? moment(this.model.roleName):null})
                                        // this.form.setFieldsValue({roleCode:this.model.roleCode ? moment(this.model.roleCode):null})
                                        // this.form.setFieldsValue({description:this.model.description ? moment(this.model.description):null})
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
               method = 'post';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
                                                                                     formData.createDate = formData.createDate?formData.createDate.format('YYYY-MM-DD HH:mm:ss'):null;
                                                                        formData.modifyDate = formData.modifyDate?formData.modifyDate.format('YYYY-MM-DD HH:mm:ss'):null;

            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{

     if(res.code ==0){
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