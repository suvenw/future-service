<template>
  <a-drawer
    :title="title"
    :maskClosable="true"
    width=650
    placement="right"
    :closable="true"
    @close="close"
    :visible="visible"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">

    <a-form>
      <a-form-item label='所拥有的权限'>
        <a-tree
          checkable
          @check="onCheck"
          :checkedKeys="checkedKeys"
          :treeData="treeData"
          @expand="onExpand"
          @select="onTreeNodeSelect"
          :expandedKeys="expandedKeysss"
          :checkStrictly="checkStrictly">
          <span slot="hasDatarule" slot-scope="{slotTitle,ruleFlag}">
            {{ slotTitle }}<a-icon v-if="ruleFlag" type="align-left" style="margin-left:5px;color: red;"></a-icon>
          </span>
        </a-tree>
      </a-form-item>
    </a-form>

    <div class="drawer-bootom-button">
      <a-dropdown style="float: left" :trigger="['click']" placement="topCenter">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="switchCheckStrictly(1)">父子关联</a-menu-item>
          <a-menu-item key="2" @click="switchCheckStrictly(2)">取消关联</a-menu-item>
          <a-menu-item key="3" @click="checkALL">全部勾选</a-menu-item>
          <a-menu-item key="4" @click="cancelCheckALL">取消全选</a-menu-item>
          <a-menu-item key="5" @click="expandAll">展开所有</a-menu-item>
          <a-menu-item key="6" @click="closeAll">合并所有</a-menu-item>
        </a-menu>
        <a-button>
          树操作 <a-icon type="up" />
        </a-button>
      </a-dropdown>
      <a-popconfirm title="确定放弃编辑？" @confirm="close" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
    </div>

    <role-datarule-modal ref="datarule"></role-datarule-modal>

  </a-drawer>

</template>
<script>
  import {queryTreeListForRole,queryRolePermission,saveRolePermission} from '@/api/api'
  import RoleDataruleModal from './RoleDataruleModal.vue'

  export default {
    name: "RoleModal",
    components:{
      RoleDataruleModal
    },
    data(){
      return {
        roleId:"",
        treeData: [],
        defaultCheckedKeys:[],
        checkedKeys:[],
        expandedKeysss:[],
        allTreeKeys:[],
        autoExpandParent: true,
        checkStrictly: true,
        title:"角色权限配置",
        visible: false,
        loading: false,
      }
    },
    methods: {
      onTreeNodeSelect(id){
        this.$refs.datarule.show(id[0],this.roleId)
      },
      onCheck (o) {
        if(this.checkStrictly){
          this.checkedKeys = o.checked;
        }else{
          this.checkedKeys = o
        }
      },
      show(roleId){
        this.roleId=roleId
        this.visible = true;
      },
      close () {
        this.reset()
        this.$emit('close');
        this.visible = false;
      },
      onExpand(expandedKeys){
        this.expandedKeysss = expandedKeys;
        this.autoExpandParent = false
      },
      reset () {
        this.expandedKeysss = []
        this.checkedKeys = []
        this.defaultCheckedKeys = []
        this.loading = false
      },
      expandAll () {
        this.expandedKeysss = this.allTreeKeys
      },
      closeAll () {
        this.expandedKeysss = []
      },
      checkALL () {
        this.checkedKeys = this.allTreeKeys
      },
      cancelCheckALL () {
        //this.checkedKeys = this.defaultCheckedKeys
        this.checkedKeys = []
      },
      switchCheckStrictly (v) {
        if(v==1){
          this.checkStrictly = false
        }else if(v==2){
          this.checkStrictly = true
        }
      },
      handleCancel () {
        this.close()
      },
      handleSubmit(){
        let that = this;
        console.log('checkedKeys:',that.checkedKeys)
        var key = String.fromCharCode(7)

        var permissionIds = ''
        var lastpermissionIds = ''
        for (var a = 0; a < that.checkedKeys.length; a++) {
          permissionIds += that.checkedKeys[a] + key
        }
        for (var a = 0; a < that.defaultCheckedKeys.length; a++) {
          lastpermissionIds += that.defaultCheckedKeys[a] + key
        }

        let params = new FormData()
        params.append('roleId',that.roleId)
        if (permissionIds != '') {
          params.append('permissionIds',permissionIds)
        }
        if (lastpermissionIds != '') {
          params.append('lastpermissionIds',lastpermissionIds)
        }
      
        that.loading = true;
        
        saveRolePermission(params).then((res)=>{
          if(res.code ==0){
            that.$message.success(res.msg);
            that.loading = false;
            that.close();
          }else {
            that.$message.error(res.msg);
            that.loading = false;
            that.close();
          }
        })
      },
    },
  watch: {
    visible () {
      if (this.visible) {
        queryTreeListForRole().then((res) => {
          console.log('res.data',res.data)
          this.treeData = res.data.treeList
          this.allTreeKeys = res.data.ids
          queryRolePermission({roleId:this.roleId}).then((res)=>{
              this.checkedKeys = [...res.data];
              this.defaultCheckedKeys = [...res.data];
              this.expandedKeysss = this.allTreeKeys;
          })
        })
      }
    }
  }
  }

</script>
<style lang="scss" scoped>
  .drawer-bootom-button {
    position: absolute;
    bottom: 0;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    left: 0;
    background: #fff;
    border-radius: 0 0 2px 2px;
  }

</style>