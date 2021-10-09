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
          <a-input v-decorator="['imageUrl', {}]" />
          <a-input v-decorator="['questionId', {}]" />
          <a-input-number v-decorator="['videoId', {}]"/>
          <a-input v-decorator="['isReply', {}]" />
          <a-input v-decorator="['operator', {}]" />
          <a-input  v-decorator="['replyContent',{}]"/>
        </a-form-item>  
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="举报人编号">
          <a-input v-decorator="['userId',{}]" disabled/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="举报时间">
          <a-input disabled v-decorator="['createDate',{}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="举报类型">
          <a-select disabled v-decorator="[ 'accusationType', {}]" >
            <a-select-option :value="1">视频举报</a-select-option>
            <a-select-option :value="2">用户举报</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          v-if="this.model.accusationType == 1"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="视频ID">
          <a-input-number v-decorator="['videoId', {}]" disabled/>
          <a-button type="primary"  @click="handleVideoPreview()">视频播放</a-button>
        </a-form-item>
        <a-form-item
          v-else
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="ID">
          <a-input-number v-decorator="['userAccusationId', {}]" disabled/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="举报分类">
          <a-input disabled placeholder="请输入举报分类" v-decorator="['category',{}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="举报理由">
           <a-input placeholder="举报理由" v-decorator="['reason',{}]" disabled/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="内容描述">
            <a-textarea disabled :rows="8" placeholder="请输入举报内容" v-decorator="['content', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="相关图片">
          <a-row>
            <a-col :span="6" v-for="(item, i) in this.model.imageUrlList":key="i"><img style="width: 120px" :src="item"  @click="handlePreview(item)" ></a-col>
          </a-row>
         </a-form-item>
         <hr/> 
          <a-form-item
           v-if="this.model.isReply == 2"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          class = "resultForm"
          label="处理结果">
            <a-input  v-decorator="['replyContent',{}]" disabled/>
         </a-form-item>
         <a-form-item
          v-else
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          class = "resultForm"
          label="处理结果">
            <a-button   style="float: left;" v-for="(item, i) in defaultButtonList" :key="item.id"  @click="changeButton(item)" :class='{active:item.id === isActive}' >{{item.name}}</a-button>
         </a-form-item>
         <a-form-item
          v-if="this.model.isReply == 2"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="处理时间">
          <a-input disabled v-decorator="['replyDate', {}]" />
        </a-form-item>
        <a-form-item
          v-if="this.model.isReply == 2"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="操作人">
          <a-input disabled v-decorator="['operator', {}]" />
        </a-form-item>
      </a-form>
    </a-spin>
    <a-modal :visible="previewVisible" :footer="null" @cancel="previewHandleCancel">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>

    <a-modal
      title="预览"
      destroyOnClose
      footer
      @cancel="previewVideoHandleCancel"
      v-model="videoVisible">
      <video-player  class="video-player vjs-custom-skin"
           ref="videoPlayer"
           :playsinline="true"
           :options="playerOptions">
      </video-player>
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
  import { videoPlayer } from 'vue-video-player'
  import 'video.js/dist/video-js.css'  

  export default {
    name: "resAccusationModal",
    components: {
      videoPlayer
    },    
    data () {
      return {
        title:"操作",
        visible: false,
        visibleCheck: true,
        videoVisible: false,
        model: {
        },
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
        // 表单验证
        validatorRules:{},
        url: {
          add: "/resource/resaccusation/add",
          edit: "/resource/resaccusation/modify",
          fileUpload: "/resource/resaccusation/import",
          fileUploadImg: "/upload/file",
        },
        defaultButtonList: [],
        userButtonList: [
          {id:1, name: '封禁账号'},
          {id:2, name: '封禁评论'},
          {id:4, name: '不予处理'}
        ],
        videoButtonList: [
          {id:3, name: '视频下架'},
          {id:4, name: '不予处理'}
        ],        
        isActive: 4,
        previewImage: '',
        previewVisible: false,
        playerOptions : {},
        replyContent: ''
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
        //类型
        if (record.accusationType == 1) {
          this.defaultButtonList = this.videoButtonList
        } else {
          this.defaultButtonList = this.userButtonList
        }
        //是否编辑
        if (record.isReply == 2) {
          this.submitDisable = true
        } else {
          this.submitDisable = false
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model
             ,'createDate'
             ,'accusationType'
             ,'videoId'
             ,'userAccusationId'
             ,'userId'
             ,'questionId'
             ,'content'
             ,'imageUrl'
             ,'isReply'
             ,'replyContent'
             ,'replyDate'
             ,'operator'
             ,'category'
             ,'reason'
           ))
            // //时间格式化
            this.form.setFieldsValue({createDate:this.model.createDate ? moment(this.model.createDate).format('YYYY-MM-DD HH:mm:ss'):null})
            this.form.setFieldsValue({replyDate:this.model.replyDate ? moment(this.model.replyDate).format('YYYY-MM-DD HH:mm:ss'):null})
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
            formData.replyContent = that.replyContent
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
        this.videoVisible = false
        this.close()
      },
      changeButton (item) {
        this.replyContent = item.name
        this.isActive = item.id
      },
      handlePreview(item) {
        this.previewImage = item;
        this.previewVisible = true;
      },
      previewHandleCancel () {
        this.previewVisible = false;
      },
      previewVideoHandleCancel() {
        this.videoVisible = false
      },
      handleVideoPreview () {
        let info = this.model
        var options = { autoplay: false, muted: false, loop: false, preload: 'auto', aspectRatio: '16:9', fluid: true, 
          sources: [{ src: info.videoUrlShow, type: 'video/mp4' }], poster: info.videoImageShow, notSupportedMessage: '此视频暂无法播放，请稍后再试', 
          controlBar: { timeDivider: true, durationDisplay: true, remainingTimeDisplay: false, fullscreenToggle: true } };
        this.playerOptions = options
        this.videoVisible = true
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
  .active{
     color: red;
  }
  .resultForm {
    top: 15px;
  }
  hr {
    height:1px;
    border:none;
    border-top:1px solid #e8e8e8;
  }

</style>