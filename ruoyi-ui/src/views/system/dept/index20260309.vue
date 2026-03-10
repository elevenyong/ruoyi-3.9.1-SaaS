<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="部门名称" prop="deptName">
        <el-input v-model="queryParams.deptName" placeholder="请输入部门名称" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="部门状态" clearable>
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
                   v-hasPermi="['system:dept:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-sort" size="mini" @click="toggleExpandAll">展开/折叠</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-if="refreshTable" v-loading="loading" :data="deptList" row-key="deptId"
              :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
      <el-table-column prop="deptName" label="部门名称" width="260"></el-table-column>
      <el-table-column prop="orderNum" label="排序" width="200"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="200">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['system:dept:edit']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)"
                     v-hasPermi="['system:dept:add']">新增
          </el-button>
          <!-- v-if="scope.row.parentId != 0" -->
          <el-button v-show="!(scope.row.deptId === deptList[0].deptId)" size="mini" type="text" icon="el-icon-delete"
                     @click="handleDelete(scope.row)" v-hasPermi="['system:dept:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改部门对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row v-show="!rootDept">
          <el-col :span="24" v-if="form.parentId !== 0">
            <el-form-item label="上级部门" prop="parentId">
              <treeselect v-model="form.parentId" :options="deptOptions" :normalizer="normalizer"
                          :disabled="isEdit || disParentSelect" placeholder="选择上级部门"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部门名称" prop="deptName">
              <el-input v-model="form.deptName" placeholder="请输入部门名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-show="!rootDept || form.parentId === 0">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0"/>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row>
          <el-col :span="12">
            <el-form-item label="负责人" prop="leader">
              <el-input v-model="form.leader" placeholder="请输入负责人" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门状态">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value">
                  {{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row> -->
        <el-row v-show="!rootDept || form.parentId === 0">
          <el-col :span="24">
            <el-form-item label="菜单权限">
              <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">展开/折叠
              </el-checkbox>
              <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">全选/全不选
              </el-checkbox>
              <el-checkbox v-model="form.menuCheckStrictly" @change="handleCheckedTreeConnect($event, 'menu')">父子联动
              </el-checkbox>
              <el-tree class="tree-border" :data="menuOptions" show-checkbox ref="menu" node-key="id"
                       :check-strictly="!form.menuCheckStrictly" empty-text="加载中，请稍候"
                       :props="defaultProps"></el-tree>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addDept, delDept, getDept, listDept, listDeptExcludeChild, updateDept } from '@/api/system/dept'
import { roleMenuTreeselect } from '@/api/system/menu'

import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'Dept',
  dicts: ['sys_normal_disable'],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 表格树数据
      deptList: [],
      // 部门树选项
      deptOptions: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        deptName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 权限参数
      roleForm: {},
      //{ "id": 1, "label": "系统管理", "children": [{ "id": 100, "label": "用户管理", "children": [{ "id": 1001, "label": "用户查询" }, { "id": 1002, "label": "用户新增" }, { "id": 1003, "label": "用户修改" }, { "id": 1004, "label": "用户删除" }, { "id": 1005, "label": "用户导出" }, { "id": 1006, "label": "用户导入" }, { "id": 1007, "label": "重置密码" }] }, { "id": 101, "label": "角色管理", "children": [{ "id": 1008, "label": "角色查询" }, { "id": 1009, "label": "角色新增" }, { "id": 1010, "label": "角色修改" }, { "id": 1011, "label": "角色删除" }, { "id": 1012, "label": "角色导出" }] }, { "id": 102, "label": "菜单管理", "children": [{ "id": 1013, "label": "菜单查询" }, { "id": 1014, "label": "菜单新增" }, { "id": 1015, "label": "菜单修改" }, { "id": 1016, "label": "菜单删除" }] }, { "id": 103, "label": "部门管理", "children": [{ "id": 1017, "label": "部门查询" }, { "id": 1018, "label": "部门新增" }, { "id": 1019, "label": "部门修改" }, { "id": 1020, "label": "部门删除" }] }, { "id": 104, "label": "岗位管理", "children": [{ "id": 1021, "label": "岗位查询" }, { "id": 1022, "label": "岗位新增" }, { "id": 1023, "label": "岗位修改" }, { "id": 1024, "label": "岗位删除" }, { "id": 1025, "label": "岗位导出" }] }, { "id": 105, "label": "字典管理", "children": [{ "id": 1026, "label": "字典查询" }, { "id": 1027, "label": "字典新增" }, { "id": 1028, "label": "字典修改" }, { "id": 1029, "label": "字典删除" }, { "id": 1030, "label": "字典导出" }] }, { "id": 106, "label": "参数设置", "children": [{ "id": 1031, "label": "参数查询" }, { "id": 1032, "label": "参数新增" }, { "id": 1033, "label": "参数修改" }, { "id": 1034, "label": "参数删除" }, { "id": 1035, "label": "参数导出" }] }, { "id": 107, "label": "通知公告", "children": [{ "id": 1036, "label": "公告查询" }, { "id": 1037, "label": "公告新增" }, { "id": 1038, "label": "公告修改" }, { "id": 1039, "label": "公告删除" }] }, { "id": 108, "label": "日志管理", "children": [{ "id": 500, "label": "操作日志", "children": [{ "id": 1040, "label": "操作查询" }, { "id": 1041, "label": "操作删除" }, { "id": 1042, "label": "日志导出" }] }, { "id": 501, "label": "登录日志", "children": [{ "id": 1043, "label": "登录查询" }, { "id": 1044, "label": "登录删除" }, { "id": 1045, "label": "日志导出" }] }] }] }, { "id": 2070, "label": "U3D数字孪生系统管理", "children": [{ "id": 2071, "label": "用户管理", "children": [{ "id": 2072, "label": "用户查询" }, { "id": 2073, "label": "用户新增" }, { "id": 2074, "label": "用户修改" }, { "id": 2075, "label": "用户删除" }, { "id": 2076, "label": "用户导出" }, { "id": 2077, "label": "用户导入" }, { "id": 2078, "label": "重置密码" }] }, { "id": 2079, "label": "菜单管理", "children": [{ "id": 2080, "label": "菜单查询" }, { "id": 2081, "label": "菜单新增" }, { "id": 2082, "label": "菜单修改" }, { "id": 2083, "label": "菜单删除" }] }, { "id": 2084, "label": "热更新" }] }, { "id": 2, "label": "系统监控", "children": [{ "id": 109, "label": "在线用户", "children": [{ "id": 1046, "label": "在线查询" }, { "id": 1047, "label": "批量强退" }, { "id": 1048, "label": "单条强退" }] }, { "id": 2000, "label": "KJ370Monitor", "children": [{ "id": 2001, "label": "KJ370Monitor查询" }, { "id": 2002, "label": "KJ370Monitor新增" }, { "id": 2003, "label": "KJ370Monitor修改" }, { "id": 2004, "label": "KJ370Monitor删除" }, { "id": 2005, "label": "KJ370Monitor导出" }] }, { "id": 110, "label": "定时任务", "children": [{ "id": 1049, "label": "任务查询" }, { "id": 1050, "label": "任务新增" }, { "id": 1051, "label": "任务修改" }, { "id": 1052, "label": "任务删除" }, { "id": 1053, "label": "状态修改" }, { "id": 1054, "label": "任务导出" }] }, { "id": 111, "label": "数据监控" }, { "id": 112, "label": "服务监控" }, { "id": 113, "label": "缓存监控" }] }, { "id": 2085, "label": "Web数字孪生系统管理" }, { "id": 3, "label": "系统工具", "children": [{ "id": 114, "label": "表单构建" }, { "id": 115, "label": "代码生成", "children": [{ "id": 1055, "label": "生成查询" }, { "id": 1056, "label": "生成修改" }, { "id": 1058, "label": "导入代码" }, { "id": 1057, "label": "生成删除" }, { "id": 1059, "label": "预览代码" }, { "id": 1060, "label": "生成代码" }] }, { "id": 116, "label": "系统接口" }, { "id": 2067, "label": "甘特图1" }, { "id": 2068, "label": "甘特图2" }, { "id": 2069, "label": "甘特图3" }] }, { "id": 2086, "label": "系统设置" }, { "id": 2006, "label": "HHHZ", "children": [{ "id": 2007, "label": "首页", "children": [{ "id": 2024, "label": "矿井总览" }, { "id": 2025, "label": "抽采报表" }, { "id": 2026, "label": "抽采系统运行图" }, { "id": 2027, "label": "瓦斯治理人员信息" }, { "id": 2028, "label": "施工信息汇总" }] }, { "id": 2008, "label": "漫游导航", "children": [{ "id": 2018, "label": "矿井总览" }, { "id": 2029, "label": "巷道漫游" }, { "id": 2030, "label": "抽采站漫游" }, { "id": 2031, "label": "管网系统漫游" }] }, { "id": 2009, "label": "精细地质", "children": [{ "id": 2032, "label": "工作面地质" }, { "id": 2033, "label": "瓦斯地质图" }, { "id": 2034, "label": "地质单元划分" }, { "id": 2035, "label": "构造带分布" }, { "id": 2036, "label": "地质资料汇集" }] }, { "id": 2010, "label": "抽采设计", "children": [{ "id": 2019, "label": "单独钻孔设计" }, { "id": 2020, "label": "条带治理钻孔组设计" }, { "id": 2021, "label": "定向钻孔通用设计" }, { "id": 2022, "label": "高位钻孔设计" }, { "id": 2042, "label": "短定向孔设计" }, { "id": 2043, "label": "揭煤设计" }] }, { "id": 2011, "label": "工程集控", "children": [{ "id": 2037, "label": "抽采站监控" }, { "id": 2038, "label": "抽采管网监控" }, { "id": 2039, "label": "抽采施工集控" }, { "id": 2040, "label": "钻孔施工管理" }, { "id": 2041, "label": "智能化设备监控" }] }, { "id": 2044, "label": "抽采动态", "children": [{ "id": 2045, "label": "条带抽采" }, { "id": 2046, "label": "工作面中部抽采" }, { "id": 2065, "label": "采空区抽采" }, { "id": 2047, "label": "揭煤与特殊抽采" }, { "id": 2048, "label": "抽采管网动态监控" }, { "id": 2051, "label": "瓦斯抽采云图" }] }, { "id": 2052, "label": "信息管理", "children": [{ "id": 2053, "label": "数据分析与管理" }, { "id": 2054, "label": "人员管理" }, { "id": 2055, "label": "用户管理" }, { "id": 2066, "label": "报表管理" }] }, { "id": 2014, "label": "达标评判", "children": [{ "id": 2061, "label": "抽采数据" }, { "id": 2062, "label": "抽采钻孔数据" }, { "id": 2063, "label": "取样钻孔数据" }, { "id": 2064, "label": "报告辅助生成" }] }, { "id": 2056, "label": "异常告警", "children": [{ "id": 2057, "label": "异常信息告警" }, { "id": 2058, "label": "安全生产信息管理" }, { "id": 2059, "label": "处理预案" }, { "id": 2060, "label": "推送通知管理" }] }] }
      defaultProps: {
        children: 'children',
        label: 'label'
        /* disabled: function (data, node) {
          if (handleEdit) {
            return false
          } else {
            if (!node.checked) {
              return true
            }
          }
        } */
      },
      // 权限菜单列表
      menuOptions: [],
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      // 当前弹框是否为修改，影响onSelected事件
      isEdit: false,
      // 禁用上级部门下拉树
      disParentSelect: false,
      // 当前根部门，不显示上级部门
      rootDept: false,
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: '上级部门不能为空', trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: '部门名称不能为空', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '显示排序不能为空', trigger: 'blur' }
        ],
        email: [
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change']
          }
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  watch: {
    'form.parentId': 'onSelected'
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询部门列表 */
    getList() {
      this.loading = true
      listDept(this.queryParams).then(response => {
        this.deptList = this.handleTree(response.data, 'deptId')
        this.loading = false
      })
    },
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
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([])
      }
      this.menuExpand = false,
        this.menuNodeAll = false,
        this.deptExpand = true,
        this.deptNodeAll = false,
        this.rootDept = false,
        this.form = {
          deptId: undefined,
          parentId: undefined,
          deptName: undefined,
          orderNum: undefined,
          leader: undefined,
          phone: undefined,
          email: undefined,
          status: '0',
          /////
          roleId: undefined,
          roleName: undefined,
          roleKey: undefined,
          roleSort: 0,
          menuIds: [],
          deptIds: [],
          menuCheckStrictly: true,
          deptCheckStrictly: true,
          remark: undefined
        }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },

    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      // 当前为Edit操作
      this.isEdit = true

      // 兼容树表格/历史数据，统一提取 deptId
      const deptId = row && (row.deptId !== undefined && row.deptId !== null)
        ? row.deptId
        : row && (row.id !== undefined && row.id !== null)
          ? row.id
          : undefined

      if (deptId === undefined || deptId === null || deptId === '' || deptId === 'undefined') {
        this.$modal.msgError('未获取到部门ID，无法修改')
        return
      }

      // 获取当前行权限，初始权限树
      const roleId = deptId
      const roleMenu = this.getRoleMenuTreeselect(roleId)

      getDept(deptId).then(response => {
        this.form = response.data || {}

        // 防止后端返回空对象导致 watcher 再次触发异常
        if (this.form.parentId === undefined || this.form.parentId === null) {
          this.form.parentId = 0
        }

        this.open = true
        this.form.menuCheckStrictly = true

        roleMenu.then(res => {
          let checkedKeys = (res && res.checkedKeys) ? res.checkedKeys : []

          // 先清空当前选中，避免残留
          this.$nextTick(() => {
            if (this.$refs.menu) {
              this.$refs.menu.setCheckedKeys([])
            }
          })

          // 这里使用 parentId，因为可选项是上级权限的子集
          // 如果 parentId 是 0，说明没有上级部门可查了，不进行禁用操作
          if (row.parentId !== 0 && row.parentId !== undefined && row.parentId !== null) {
            this.getRoleMenuTreeselect(row.parentId).then(parentRes => {
              let parentCheckedKeys = (parentRes && parentRes.checkedKeys) ? parentRes.checkedKeys : []

              checkedKeys.forEach((v) => {
                this.$nextTick(() => {
                  if (this.$refs.menu) {
                    this.$refs.menu.setChecked(v, true, false)
                  }
                })
              })

              // 禁用
              this.disMenuSelectionByDept(this.menuOptions, parentCheckedKeys)
            })
          } else {
            checkedKeys.forEach((v) => {
              this.$nextTick(() => {
                if (this.$refs.menu) {
                  this.$refs.menu.setChecked(v, true, false)
                }
              })
            })
          }
        })

        this.title = '修改部门'
      })

      listDeptExcludeChild(deptId).then(response => {
        this.deptOptions = this.handleTree(response.data, 'deptId')
        if (this.deptOptions === undefined || this.deptOptions.length === 0) {
          this.rootDept = true
        }
      })
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      // 标志是否为编辑
      this.isEdit = false
      const deptId = row && (row.deptId !== undefined && row.deptId !== null)
        ? row.deptId
        : row && (row.id !== undefined && row.id !== null)
          ? row.id
          : undefined
      if (deptId !== undefined && deptId !== null && deptId !== '' && deptId !== 'undefined') {
        // 有上层部门的新增，设置parentId
        this.form.parentId = deptId
        this.disParentSelect = true
      } else {
        // 未选定上层部门的新增
        this.disParentSelect = false
        this.open = true
        this.title = '添加部门'
        listDept().then(response => {
          // 更新部门树
          this.deptOptions = this.handleTree(response.data, 'deptId')
        })
        return
      }

      // 获取初始权限树
      // 角色ID设置为上层部门ID，完成后续权限树初始化，因为权限是树层关系
      // 因为是要往选中的部门下添加部门，所以当前部门的Id，就为下层部门的父部门，下层权限是当前权限的子集
      const roleId = deptId
      const roleMenu = this.getRoleMenuTreeselect(roleId)

      this.open = true
      this.title = '添加部门'
      listDept().then(response => {
        // 成树操作
        this.deptOptions = this.handleTree(response.data, 'deptId')
        console.log(this.deptOptions)
        // 父子关联
        this.form.menuCheckStrictly = true
        this.$nextTick(() => {
          roleMenu.then(res => {
            console.log(this.$refs.menu)
            /* for (let i = 0; i < this.$refs.menu.length; i++) {
              this.$set(this.$refs.menu[i], "disabled", true);
              console.log(this.$refs.menu[i]);
            } */
            // 选中
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
      })
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
          // console.log("ban:", node[i].id, node[i].label)
          this.$set(node[i], 'disabled', true)
          // 叶节点中已经有值被禁用了
          m = true
        }
      }
      return m
    },

    /** 提交按钮 */
    submitForm: function() {
      let _this = this
      this.$refs['form'].validate(valid => {
        // 参数校验
        if (valid) {
          this.form.menuIds = _this.getMenuAllCheckedKeys()
          if (this.form.deptId != undefined) {
            // 更新操作
            updateDept(this.form).then(response => {
              console.log('response', response)
              if (response.code === 200) {
                this.$modal.msgSuccess('修改成功')
                this.open = false
                this.getList()
              }
              // 更新成功
              /* if (response.code === 200) {
                getRole(this.form.deptId).then(response => {
                  if (response.code === 200) {
                    _this.roleForm = response.data;
                    _this.roleForm.menuIds = _this.getMenuAllCheckedKeys();
                    _this.roleForm.roleName = _this.form.deptName + "-" + _this.form.deptId;
                    // 更新
                    updateRole(_this.roleForm).then(response => {
                      if (response.code === 200) {
                        this.$modal.msgSuccess("修改成功");
                        this.open = false;
                        this.getList();
                      } else {

                      }
                    })
                  }
                })
              } */
            })
          } else {
            addDept(this.form).then(response => {
              console.log('response', response)
              if (response.code === 200) {
                this.$modal.msgSuccess('新增成功')
                this.open = false
                this.getList()
              }
              /* if (response.code === 200) {
                listDept().then(response => {
                  let deptId = _this.getNewAddedDeptId(response, this.form.parentId, _this.form.deptName)
                  //let names = _this.getAllParentDeptNames(response, this.form.parentId)
                  //console.log(names)
                  let submitForm = {
                    roleId: deptId,
                    roleName: _this.form.deptName + "-" + deptId,
                    roleKey: deptId,
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
                  console.log(submitForm)
                  addRole(submitForm).then(resp => {
                    this.$modal.msgSuccess("新增成功");
                    this.open = false;
                    this.getList();
                  })
                })
              } else { } */
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除名称为"' + row.deptName + '"的数据项？').then(function() {
        return delDept(row.deptId)/* .then(response => {
          if (response.code === 200) {
            return delRole(row.deptId)
          }
        }) */
        //return delDept(row.deptId);
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 根据部门ID查询菜单树结构 */
    getRoleMenuTreeselect(roleId) {
      return roleMenuTreeselect(roleId).then(response => {
        this.menuOptions = response.menus
        return response
      })
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
    // 获取所有父节点连接字符串
    getAllParentDeptNames(data, deptId) {
      let arrData = data.data
      let names = ''
      if (arrData != undefined && arrData.length >= 1) {
        let ancestor = ''

        // 获取父节点IDs
        for (let i = 0; i < arrData.length; i++) {
          if (arrData[i].deptId === deptId) {
            //names = arrData[i].deptName,
            ancestor = arrData[i].ancestors
            break
          }
        }

        // 获取父节点IdArr
        let parentIds = ancestor.split(',')
        let tempName = ''
        // 遍历父节点Ids
        for (let i = 0; i < parentIds.length; i++) {
          tempName = parentIds[i]
          for (let j = 0; j < arrData.length; j++) {
            if (arrData[j].deptId === Number(parentIds[i])) {
              //names = names + "-" + arrData[j].deptName
              tempName = arrData[j].deptName
              break
            }
          }
          //names = names + "-" + parentIds[i]
          names = names + '-' + tempName
        }

        /* for (let i = 0; i < arrData.length; i++) {
          if (arrData[i].deptId === deptId) {
            names = arrData[i].deptName
            parentId = arrData[i].parentId
            break;
          }
        }
        if (parentId === 0) {
          return names;
        }
        while (parentId != 0) {
          for (let i = 0; i < arrData.length; i++) {
            if (arrData[i].deptId === parentId) {
              names = arrData[i].deptName + "-" + names
              parentId = arrData[i].parentId
            }
          }
        } */
        return names
      }
    },
    // 获取新增的部门ID
    getNewAddedDeptId(data, parentId, deptName) {
      let arrData = data.data
      let deptId = -1
      for (let i = 0; i < arrData.length; i++) {
        if (arrData[i].parentId === parentId && arrData[i].deptName === deptName) {
          deptId = arrData[i].deptId
          break
        }
      }
      return deptId
    },
    /**
     * 当上级部门改变时，更新菜单内容
     * @param {*} id
     */
    onSelected(id) {
      console.log('parentId', id)
      // 严格拦截各种非法值，避免请求 /undefined
      if (
        id === undefined ||
        id === null ||
        id === '' ||
        id === 'undefined' ||
        id === 'null' ||
        this.isEdit ||
        Number(id) === 0
      ) {
        return
      }
      const roleId = Number(id)
      if (isNaN(roleId)) {
        return
      }
      const roleMenu = this.getRoleMenuTreeselect(roleId)
      this.form.menuCheckStrictly = true
      roleMenu.then(res => {
        if (this.$refs.menu) {
          this.$refs.menu.setCheckedKeys([])
        }
        let checkedKeys = (res && res.checkedKeys) ? res.checkedKeys : []
        this.disMenuSelectionByDept(this.menuOptions, checkedKeys)
        checkedKeys.forEach((v) => {
          this.$nextTick(() => {
            if (this.$refs.menu) {
              this.$refs.menu.setChecked(v, true, false)
            }
          })
        })
      })
    },
  }
}
</script>
