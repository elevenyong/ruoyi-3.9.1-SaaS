<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input v-model="deptName" placeholder="请输入部门名称" clearable size="small" prefix-icon="el-icon-search"
                    style="margin-bottom: 20px"/>
        </div>
        <div class="head-container">
          <el-tree :data="deptOptions" :props="defaultProps" :expand-on-click-node="false"
                   :filter-node-method="filterNode" ref="tree" default-expand-all @node-click="handleNodeClick"/>
        </div>
      </el-col>
      <!-- 岗位数据 -->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px">
          <el-form-item label="岗位编码" prop="postCode">
            <el-input v-model="queryParams.postCode" placeholder="请输入岗位编码" clearable
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="岗位名称" prop="postName">
            <el-input v-model="queryParams.postName" placeholder="请输入岗位名称" clearable
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="岗位状态" clearable>
              <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
                         :value="dict.value"/>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                       v-hasPermi="['system:post:add']">新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                       v-hasPermi="['system:post:edit']">修改
            </el-button>
          </el-col>
          <!-- <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
              v-hasPermi="['system:post:remove']">删除</el-button>
          </el-col> -->
          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                       v-hasPermi="['system:post:export']">导出
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="岗位编号" align="center" prop="postId"/>
          <!-- <el-table-column label="岗位编码" align="center" prop="postCode" /> -->
          <el-table-column label="所属部门" align="center" prop="deptName"/>
          <el-table-column label="岗位名称" align="center" prop="postName"/>
          <el-table-column label="岗位排序" align="center" prop="postSort"/>
          <el-table-column label="状态" align="center" prop="status">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                         v-hasPermi="['system:post:edit']">修改
              </el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                         v-hasPermi="['system:post:remove']">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                    :limit.sync="queryParams.pageSize" @pagination="getList"/>
      </el-col>
    </el-row>


    <!-- 添加或修改岗位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="归属部门" prop="deptId">
          <!-- :disabled="isEdit" -->
          <treeselect v-model="form.deptId" :options="deptOptions" placeholder="选择归属部门" :disabled="isEdit"/>
        </el-form-item>
        <el-form-item label="岗位名称" prop="postName">
          <el-input v-model="form.postName" placeholder="请输入岗位名称"/>
        </el-form-item>
        <!-- <el-form-item label="岗位编码" prop="postCode">
          <el-input v-model="form.postCode" placeholder="请输入编码名称" />
        </el-form-item> -->
        <el-form-item label="岗位顺序" prop="postSort">
          <el-input-number v-model="form.postSort" controls-position="right" :min="0"/>
        </el-form-item>
        <el-form-item label="岗位状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value">{{
                dict.label
              }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">展开/折叠</el-checkbox>
          <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">全选/全不选
          </el-checkbox>
          <el-checkbox v-model="form.menuCheckStrictly" @change="handleCheckedTreeConnect($event, 'menu')">父子联动
          </el-checkbox>
          <el-tree class="tree-border" :data="menuOptions" show-checkbox ref="menu" node-key="id"
                   :check-strictly="!form.menuCheckStrictly" empty-text="请选择归属部门"
                   :props="defaultProps"></el-tree>
        </el-form-item>
        <!--  <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addPost, delPost, getPost, listPost, updatePost } from '@/api/system/post'
import { treeselect } from '@/api/system/dept'
import { roleMenuTreeselect } from '@/api/system/menu'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'Post',
  dicts: ['sys_normal_disable'],
  components: { Treeselect },
  data() {
    return {
      // 编辑弹框
      isEdit: false,
      // 权限表
      roleForm: {},
      // 权限菜单列表
      menuOptions: [],
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      //
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      // 部门名称
      deptName: undefined,
      // 部门树选项
      deptOptions: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 岗位表格数据
      postList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postCode: undefined,
        postName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        postName: [
          { required: true, message: '岗位名称不能为空', trigger: 'blur' }
        ],
        postCode: [
          { required: true, message: '岗位编码不能为空', trigger: 'blur' }
        ],
        postSort: [
          { required: true, message: '岗位顺序不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val)
    },
    'form.deptId': 'onSelected'
  },
  created() {

    this.getTreeselect()

  },
  methods: {
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      }
    },
    /** 根据部门ID查询菜单树结构 */
    getRoleMenuTreeselect(roleId) {
      return roleMenuTreeselect(roleId).then(response => {
        this.menuOptions = response.menus
        return response
      })
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value, type) {
      if (type == 'menu') {
        let treeList = this.menuOptions
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value
        }
      } else if (type == 'dept') {
        let treeList = this.deptOptions
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.dept.store.nodesMap[treeList[i].id].expanded = value
        }
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value, type) {
      if (type == 'menu') {
        this.$refs.menu.setCheckedNodes(value ? this.menuOptions : [])
      } else if (type == 'dept') {
        this.$refs.dept.setCheckedNodes(value ? this.deptOptions : [])
      }
    },
    // 树权限（父子联动）
    handleCheckedTreeConnect(value, type) {
      if (type == 'menu') {
        this.form.menuCheckStrictly = value ? true : false
      } else if (type == 'dept') {
        this.form.deptCheckStrictly = value ? true : false
      }
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data
        this.queryParams.deptId = this.deptOptions[0].id
        this.getList()
      })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id
      this.handleQuery()
    },
    /** 查询岗位列表 */
    getList() {
      this.loading = true
      listPost(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false

        for (let i = 0; i < this.postList.length; i++) {
          this.postList[i].deptName = this.findDeptNameById(this.deptOptions, this.postList[i].deptId)
          //console.log(this.postList[i].deptName)
        }
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        postId: undefined,
        postCode: undefined,
        postName: undefined,
        postSort: 0,
        status: '0',
        remark: undefined
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.postId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      // 标志是否为编辑
      this.isEdit = false

      this.open = true
      this.title = '添加岗位'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()

      // 当前为Edit操作
      this.isEdit = true

      // 获取当前行权限，初始权限树
      const roleId = row.postId || this.ids[0]
      const roleMenu = this.getRoleMenuTreeselect(roleId)

      const postId = row.postId || this.ids[0]
      getPost(postId).then(response => {
        this.form = response.data
        this.open = true

        this.form.menuCheckStrictly = true
        this.$nextTick(() => {
          roleMenu.then(res => {
            let postMenuOpt = res.menus
            let checkedKeys = res.checkedKeys
            /* checkedKeys.forEach((v) => {
              this.$nextTick(() => {
                this.$refs.menu.setChecked(v, true, false);
              })
            }) */
            // 直接使用deptId，可选项是部门权限的子集
            this.getRoleMenuTreeselect(row.deptId).then(res1 => {
              this.menuOptions = postMenuOpt
              let deptCheckedKeys = res1.checkedKeys
              checkedKeys.forEach((v) => {
                this.$nextTick(() => {
                  this.$refs.menu.setChecked(v, true, false)
                })
              })
              // 禁用
              this.disMenuSelectionByDept(this.menuOptions, deptCheckedKeys)
            })
          })
        })

        this.title = '修改岗位'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      let _this = this
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.postId != undefined) {
            // 修改
            this.form.postCode = this.form.deptId + '-' + this.form.postName
            // role操作
            this.form.menuIds = _this.getMenuAllCheckedKeys()
            updatePost(this.form).then(response => {
              console.log('response', response)
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
              /* if (response.code === 200) {
                getRole(this.form.postId).then(response => {
                  if (response.code === 200) {
                    _this.roleForm = response.data
                    _this.roleForm.menuIds = _this.getMenuAllCheckedKeys()
                    _this.roleForm.roleName = _this.findDeptNameById(_this.deptOptions, _this.form.deptId) + '-' + _this.form.postName + '-' + _this.form.postId
                    // 更新
                    updateRole(_this.roleForm).then(response => {
                      if (response.code === 200) {
                        this.$modal.msgSuccess('修改成功')
                        this.open = false
                        this.getList()
                      } else {

                      }
                    })
                  }
                })
              } */
            })
          } else {
            // 新增
            let _this = this
            this.form.postCode = this.form.deptId + '-' + this.form.postName
            // role操作
            this.form.menuIds = _this.getMenuAllCheckedKeys()
            addPost(this.form).then(response => {
              console.log('response', response)
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
              /* if (response.code === 200) {
                // 岗位添加成功
                listPost().then(response => {
                  let postId = _this.getNewAddedPostId(response, this.form.deptId, _this.form.postName)
                  let submitForm = {
                    roleId: postId,
                    roleName: _this.findDeptNameById(_this.deptOptions, _this.form.deptId) + "-" + _this.form.postName + "-" + postId,
                    roleKey: postId,
                    roleSort: 0,
                    status: "0",
                    menuIds: [],
                    deptIds: [],
                    menuCheckStrictly: true,
                    deptCheckStrictly: true,
                    remark: undefined,
                    dataScope: '4'
                  };
                  submitForm.menuIds = _this.getMenuAllCheckedKeys();
                  addRole(submitForm).then(resp => {
                    if (resp.code === 200) {
                      this.$modal.msgSuccess("新增成功");
                      this.open = false;
                      this.getList();
                    } else {
                      delPost(postId)
                    }
                  })
                });
              } */
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      //const postIds = row.postId || this.ids;
      const postIds = row.postId || this.ids[0]
      this.$modal.confirm('是否确认删除岗位编号为"' + postIds + '"的数据项？').then(function() {
        return delPost(postIds)/* .then(response => {
          if (response.code === 200) {
            return delRole(postIds)
          }
        }) */
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/post/export', {
        ...this.queryParams
      }, `post_${new Date().getTime()}.xlsx`)
    },
    /**
     * 当所选部门改变时，更新菜单内容
     * @param {*} id
     */
    onSelected(id) {
      //console.log(id)
      if (id != undefined && !this.isEdit) {
        const roleId = id
        const roleMenu = this.getRoleMenuTreeselect(roleId)
        this.form.menuCheckStrictly = true
        this.$nextTick(() => {
          roleMenu.then(res => {
            let checkedKeys = res.checkedKeys
            checkedKeys.forEach((v) => {
              this.$nextTick(() => {
                this.$refs.menu.setChecked(v, true, false)
              })
            })

            // 禁用
            this.disMenuSelectionByDept(this.menuOptions, checkedKeys)
          })
        })
      }
    },
    // 获取新增的岗位ID
    getNewAddedPostId(data, deptId, postName) {
      let arrData = data.rows
      let postId = -1
      for (let i = 0; i < arrData.length; i++) {
        if (arrData[i].deptId === deptId && arrData[i].postName === postName) {
          postId = arrData[i].postId
          break
        }
      }
      return postId
    },
    // 所有菜单节点数据
    getMenuAllCheckedKeys() {
      // 目前被选中的菜单节点
      let checkedKeys = this.$refs.menu.getCheckedKeys()
      // 半选中的菜单节点
      let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys()
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys)
      return checkedKeys
    },
    findDeptNameById(node, deptId) {
      let _this = this
      //let node = _this.deptOptions;
      let name = ''
      for (let i = 0; i < node.length; i++) {
        if (node[i].id === deptId) {
          name = node[i].label
          break
        }
        if (node[i].children) {
          name = _this.findDeptNameById(node[i].children, deptId)
          if (name !== '') return name
        }
      }
      return name
    },
    /**
     * 依据权限列表禁用权限
     * @param {*} node 权限树
     * @param {*} checked 不会被禁用的id列表
     */
    disMenuSelectionByDept(node, allowedList) {
      let _this = this
      let m = false
      let base = false
      for (let i = 0; i < node.length; i++) {
        if (node[i].children) {
          // 非叶节点
          if (_this.disMenuSelectionByDept(node[i].children, allowedList)) {
            m = true
            this.$set(node[i], 'disabled', true)
          }
        } else if (allowedList.indexOf(node[i].id) === -1) {
          // 叶节点
          // 如果ID不在allowedList里，就会被禁用
          //console.log("ban:", node[i].id, node[i].label)
          this.$set(node[i], 'disabled', true)
          // 叶节点中已经有值被禁用了
          m = true
        }
      }
      return m
    }
  }
}
</script>
