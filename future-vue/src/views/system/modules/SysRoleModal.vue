<template>
  <a-drawer
      :title="title"
      :width="800"
      placement="right"
      @close="handleCancel"
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

        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select v-decorator="[ 'sex', {}]" placeholder="请选择性别">
            <a-select-option :value="1">男</a-select-option>
            <a-select-option :value="2">女</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="排序">
          <a-input-number v-decorator="[ 'departOrder',{'initialValue':0}]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否启用"
          hasFeedback>
          <a-switch checkedChildren="启用" unCheckedChildren="禁用" @change="onChose" v-model="visibleCheck"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="状态">
          <a-radio-group class="fontiframe" name="radioGroup" v-decorator="[ 'status', {}]">
            <a-radio class="radioGroup" value="1">有效</a-radio>
            <a-radio class="radioGroup" value="0">无效</a-radio>
          </a-radio-group>
        </a-form-item>

        <a-form-item label="角色分配" :labelCol="labelCol" :wrapperCol="wrapperCol" v-show="!roleDisabled" >
          <a-select
            mode="multiple"
            style="width: 100%"
            placeholder="请选择用户角色"
            optionFilterProp = "children"
            v-model="selectedRole">
            <a-select-option v-for="(role,roleindex) in roleList" :key="roleindex.toString()" :value="role.id">
              {{ role.roleName }}
            </a-select-option>
          </a-select>


          //文件上传
          <a-upload
            name="file"
            :multiple="true"
            @change="handleChanges"
            :customRequest="customRequest"
          >
            <a-button> <a-icon type="upload" /> Click to Upload </a-button>
          </a-upload>

          //图片上传  支持多张上传 及预览
          <div class="clearfix">
            <a-upload
              name="file"
              :multiple="false"
              listType="picture-card"
              :fileList="fileList"
              @preview="handlePreview"
              :beforeUpload="beforeUpload"
              @change="handleChange"
              :customRequest="customRequests"
            >
              <div v-if="fileList.length < 3">
                <a-icon type="plus" />
                <div class="ant-upload-text">上传</div>
              </div>
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </div>

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
    name: "sysRoleModal",
    data () {
      return {
        title:"操作",
        visible: false,
        roleDisabled: false, //是否是角色维护调用该页面
        visibleCheck: true,
        model: {},
        selectedRole:[],
        disableSubmit:false,
        roleList:[
          {"id":"e51758fa916c881624b046d26bd09230","roleName":"人力资源部","roleCode":"hr","description":null,"createBy":"admin","createTime":"2019-01-21 18:07:24","updateBy":"admin","updateTime":"2019-06-23 21:33:13"},
          {"id":"ee8626f80f7c2619917b6236f3a7f02b","roleName":"临时角色","roleCode":"test","description":"这是新建的临时角色123","createBy":null,"createTime":"2018-12-20 10:59:04","updateBy":"admin","updateTime":"2019-02-19 15:08:37"},
          {"id":"f6817f48af4fb3af11b9e8bf182f618b","roleName":"管理员","roleCode":"admin","description":"管理员","createBy":null,"createTime":"2018-12-21 18:03:39","updateBy":"admin","updateTime":"2019-02-22 19:49:42"}],
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
        previewVisible: false,
        previewImage: '',
        fileList: [],
        url: {
          add: "/sys/sysRole/add",
          edit: "/sys/sysRole/modify",
          fileUpload: "/sys/sysRole/import",
          fileUploadImg: "/upload/file",
          imgerver: window._CONFIG['domianURL']+"/sys/common/view",
        },
      }
    },
    created () {
      // const token = Vue.ls.get(ACCESS_TOKEN);
      // this.headers = {"accessToken":token,"channel":1,"sysVersion":"0.1.0","device":"5","userId":"userId","appId":5}
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
            formData.createDate = formData.createDate?formData.createDate.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.modifyDate = formData.modifyDate?formData.modifyDate.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.selectedroles = this.selectedRole.length>0?this.selectedRole.join(","):'';
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
        this.previewVisible = false;
        this.close()
      },
      beforeUpload: function(file){
        console.info("beforeUpload");
        console.info(file.type);
        var fileType = file.type;
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
        //TODO 验证文件大小
      },

      handleChanges (info) {
        console.log(info.file);
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          this.$message.success(info.file.name+'文件上传成功');
        } else if (info.file.status === 'error') {
          this.$message.error(info.file.name+'文件上传失败');
        }
      },
      customRequest:function({ onSuccess, onError, file }) {
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


      handlePreview(file) {
        this.previewImage = file.url || file.thumbUrl;
        this.previewVisible = true;
      },
      handleChange({fileList}) {
        for (var i = fileList.length - 1; i >= 0; i--) {
          var obj = fileList[i]
          if (obj.status != 'done') {
            fileList.splice(i, 1);
          }
        }
        this.fileList = fileList;
      },

      customRequests:function({ onSuccess, onError, file }) {
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