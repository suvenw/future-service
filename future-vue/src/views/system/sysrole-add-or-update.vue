<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="创建时间" prop="createDate">
      <el-input v-model="dataForm.createDate" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="更新时间" prop="modifyDate">
      <el-input v-model="dataForm.modifyDate" placeholder="更新时间"></el-input>
    </el-form-item>
    <el-form-item label="删除状态(1-正常,0-已删除)" prop="status">
      <el-input v-model="dataForm.status" placeholder="删除状态(1-正常,0-已删除)"></el-input>
    </el-form-item>
    <el-form-item label="排序字段,默认按升级排序" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="排序字段,默认按升级排序"></el-input>
    </el-form-item>
    <el-form-item label="角色名称" prop="roleName">
      <el-input v-model="dataForm.roleName" placeholder="角色名称"></el-input>
    </el-form-item>
    <el-form-item label="角色编码" prop="roleCode">
      <el-input v-model="dataForm.roleCode" placeholder="角色编码"></el-input>
    </el-form-item>
    <el-form-item label="描述" prop="description">
      <el-input v-model="dataForm.description" placeholder="描述"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          createDate: '',
          modifyDate: '',
          status: '',
          sort: '',
          roleName: '',
          roleCode: '',
          description: ''
        },
        dataRule: {
          createDate: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          modifyDate: [
            { required: true, message: '更新时间不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '删除状态(1-正常,0-已删除)不能为空', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: '排序字段,默认按升级排序不能为空', trigger: 'blur' }
          ],
          roleName: [
            { required: true, message: '角色名称不能为空', trigger: 'blur' }
          ],
          roleCode: [
            { required: true, message: '角色编码不能为空', trigger: 'blur' }
          ],
          description: [
            { required: true, message: '描述不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/system/sysrole/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.createDate = data.sysRole.createDate
                this.dataForm.modifyDate = data.sysRole.modifyDate
                this.dataForm.status = data.sysRole.status
                this.dataForm.sort = data.sysRole.sort
                this.dataForm.roleName = data.sysRole.roleName
                this.dataForm.roleCode = data.sysRole.roleCode
                this.dataForm.description = data.sysRole.description
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/system/sysrole/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'createDate': this.dataForm.createDate,
                'modifyDate': this.dataForm.modifyDate,
                'status': this.dataForm.status,
                'sort': this.dataForm.sort,
                'roleName': this.dataForm.roleName,
                'roleCode': this.dataForm.roleCode,
                'description': this.dataForm.description
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
