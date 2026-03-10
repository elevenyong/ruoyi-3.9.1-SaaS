<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input v-model="deptName" placeholder="请输入部门名称" clearable size="small" prefix-icon="el-icon-search"
            style="margin-bottom: 20px" />
        </div>
        <div class="head-container">
          <el-tree :data="deptOptions" :props="defaultProps" :expand-on-click-node="false"
            :filter-node-method="filterNode" ref="tree" default-expand-all @node-click="handleNodeClick" />
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
          label-width="68px">
          <el-form-item label="用户名称" prop="userName">
            <el-input v-model="queryParams.userName" placeholder="请输入用户名称" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="手机号码" prop="phonenumber">
            <el-input v-model="queryParams.phonenumber" placeholder="请输入手机号码" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="用户状态" clearable style="width: 240px">
              <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
                :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
              range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
              v-hasPermi="['system:user:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
              v-hasPermi="['system:user:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
              v-hasPermi="['system:user:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImport"
              v-hasPermi="['system:user:import']">导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
              v-hasPermi="['system:user:export']">导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="用户编号" align="center" key="userId" prop="userId" v-if="columns[0].visible" />
          <el-table-column label="用户名称" align="center" key="userName" prop="userName" v-if="columns[1].visible"
            :show-overflow-tooltip="true" />
          <el-table-column label="用户昵称" align="center" key="nickName" prop="nickName" v-if="columns[2].visible"
            :show-overflow-tooltip="true" />
          <el-table-column label="部门" align="center" key="deptName" prop="dept.deptName" v-if="columns[3].visible"
            :show-overflow-tooltip="true" />
          <!--20260309禁用岗位-->
          <!-- <el-table-column label="岗位" align="center" key="postName" prop="roles[0].roleName" v-if="columns[4].visible"
            :show-overflow-tooltip="true" /> -->
          <el-table-column label="手机号码" align="center" key="phonenumber" prop="phonenumber" v-if="columns[4].visible"
            width="120" />
          <el-table-column label="状态" align="center" key="status" v-if="columns[5].visible">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"
                @change="handleStatusChange(scope.row)"></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[6].visible" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
            <template slot-scope="scope" v-if="scope.row.userId !== 1">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']">删除</el-button>
              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)"
                v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
                <span class="el-dropdown-link">
                  <i class="el-icon-d-arrow-right el-icon--right"></i>更多
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="handleResetPwd" icon="el-icon-key" v-hasPermi="['system:user:resetPwd']">
                    重置密码</el-dropdown-item>
                  <!-- <el-dropdown-item command="handleAuthRole" icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']">分配角色</el-dropdown-item> -->
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize" @pagination="getList" />
      </el-col>
    </el-row>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户姓名" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入用户姓名" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-button>创建下级部门</el-button>
          </el-col>
        </el-row> -->
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="用户账号" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户账号" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="用户密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="form.sex" placeholder="请选择性别">
                <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value">
                  {{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <!--20260309禁用岗位-->
        <!-- <el-row>
          <el-col :span="12">
            <el-form-item label="岗位" prop="postIds">
              <el-select v-model="form.postIds" placeholder="请选择岗位">
                <el-option v-for="item in postOptions" :key="item.postId" :label="item.postName" :value="item.postId"
                  :disabled="item.status == 1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row> -->
          <el-col :span="16">
            <el-form-item label="账号失效时间" prop="expireTime">
              <el-date-picker :disabled="banEditExpire" v-model="form.expireTime" type="datetime" placeholder="选择日期时间"
                align="right" :picker-options="pickerOptions" value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
              <i class="el-icon-edit" style="margin-left:5px" v-hasPermi="['system:user:expire']"
                @click="editExpire"></i>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="菜单权限">
              <!-- <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">展开/折叠</el-checkbox>
              <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">全选/全不选</el-checkbox>
              <el-checkbox v-model="form.menuCheckStrictly" @change="handleCheckedTreeConnect($event, 'menu')">父子联动
              </el-checkbox> -->
              <!--20260309禁用岗位-->
              <!-- <el-tree class="tree-border" :data="menuOptions" show-checkbox ref="menu" node-key="id"
                :check-strictly="!form.menuCheckStrictly" empty-text="请先选择归属部门或岗位" :props="menuDefaultProps"></el-tree> -->
              <el-tree class="tree-border" :data="menuOptions" show-checkbox ref="menu" node-key="id"
                :check-strictly="!form.menuCheckStrictly" empty-text="请先选择归属部门" :props="menuDefaultProps"></el-tree>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
            @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, resetUserPwd, changeUserStatus } from "@/api/system/user";
import { getToken } from "@/utils/auth";
//20260309禁用岗位管理
// import { addRole, updateRole, delRole, getRole } from "@/api/system/role";
// import { treeselect as menuTreeselect, roleMenuTreeselect } from "@/api/system/menu";
import { roleMenuTreeselect } from "@/api/system/menu";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "User",
  dicts: ['sys_normal_disable', 'sys_user_sex', 'sys_user_expire'],
  components: { Treeselect },
  data() {
    return {
      banEditExpire: true,
      // 日期时间选择器快捷选项
      pickerOptions: {
        shortcuts: [{
          text: '三天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 3);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }, {
          text: '一个月',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 31);
            picker.$emit('pick', date);
          }
        }, {
          text: '半年',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 180);
            picker.$emit('pick', date);
          }
        }, {
          text: '一年',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 366);
            picker.$emit('pick', date);
          }
        }, {
          text: '永久',
          onClick(picker) {
            const date = new Date('2100-01-01 00:00:00');
            date.setTime(date.getTime());
            picker.$emit('pick', date);
          }
        }]
      },
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
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      //20260309禁用岗位管理
      // // 所有岗位选项
      // postAllOptions: [],
      // // 岗位选项
      // postOptions: [],
      // // 角色选项
      // roleOptions: [],

      // 权限菜单列表
      menuOptions: [],
      //{ "id": 1, "label": "系统管理", "children": [{ "id": 100, "label": "用户管理", "children": [{ "id": 1001, "label": "用户查询" }, { "id": 1002, "label": "用户新增" }, { "id": 1003, "label": "用户修改" }, { "id": 1004, "label": "用户删除" }, { "id": 1005, "label": "用户导出" }, { "id": 1006, "label": "用户导入" }, { "id": 1007, "label": "重置密码" }] }, { "id": 101, "label": "角色管理", "children": [{ "id": 1008, "label": "角色查询" }, { "id": 1009, "label": "角色新增" }, { "id": 1010, "label": "角色修改" }, { "id": 1011, "label": "角色删除" }, { "id": 1012, "label": "角色导出" }] }, { "id": 102, "label": "菜单管理", "children": [{ "id": 1013, "label": "菜单查询" }, { "id": 1014, "label": "菜单新增" }, { "id": 1015, "label": "菜单修改" }, { "id": 1016, "label": "菜单删除" }] }, { "id": 103, "label": "部门管理", "children": [{ "id": 1017, "label": "部门查询" }, { "id": 1018, "label": "部门新增" }, { "id": 1019, "label": "部门修改" }, { "id": 1020, "label": "部门删除" }] }, { "id": 104, "label": "岗位管理", "children": [{ "id": 1021, "label": "岗位查询" }, { "id": 1022, "label": "岗位新增" }, { "id": 1023, "label": "岗位修改" }, { "id": 1024, "label": "岗位删除" }, { "id": 1025, "label": "岗位导出" }] }, { "id": 105, "label": "字典管理", "children": [{ "id": 1026, "label": "字典查询" }, { "id": 1027, "label": "字典新增" }, { "id": 1028, "label": "字典修改" }, { "id": 1029, "label": "字典删除" }, { "id": 1030, "label": "字典导出" }] }, { "id": 106, "label": "参数设置", "children": [{ "id": 1031, "label": "参数查询" }, { "id": 1032, "label": "参数新增" }, { "id": 1033, "label": "参数修改" }, { "id": 1034, "label": "参数删除" }, { "id": 1035, "label": "参数导出" }] }, { "id": 107, "label": "通知公告", "children": [{ "id": 1036, "label": "公告查询" }, { "id": 1037, "label": "公告新增" }, { "id": 1038, "label": "公告修改" }, { "id": 1039, "label": "公告删除" }] }, { "id": 108, "label": "日志管理", "children": [{ "id": 500, "label": "操作日志", "children": [{ "id": 1040, "label": "操作查询" }, { "id": 1041, "label": "操作删除" }, { "id": 1042, "label": "日志导出" }] }, { "id": 501, "label": "登录日志", "children": [{ "id": 1043, "label": "登录查询" }, { "id": 1044, "label": "登录删除" }, { "id": 1045, "label": "日志导出" }] }] }] }, { "id": 2070, "label": "U3D数字孪生系统管理", "children": [{ "id": 2071, "label": "用户管理", "children": [{ "id": 2072, "label": "用户查询" }, { "id": 2073, "label": "用户新增" }, { "id": 2074, "label": "用户修改" }, { "id": 2075, "label": "用户删除" }, { "id": 2076, "label": "用户导出" }, { "id": 2077, "label": "用户导入" }, { "id": 2078, "label": "重置密码" }] }, { "id": 2079, "label": "菜单管理", "children": [{ "id": 2080, "label": "菜单查询" }, { "id": 2081, "label": "菜单新增" }, { "id": 2082, "label": "菜单修改" }, { "id": 2083, "label": "菜单删除" }] }, { "id": 2084, "label": "热更新" }] }, { "id": 2, "label": "系统监控", "children": [{ "id": 109, "label": "在线用户", "children": [{ "id": 1046, "label": "在线查询" }, { "id": 1047, "label": "批量强退" }, { "id": 1048, "label": "单条强退" }] }, { "id": 2000, "label": "KJ370Monitor", "children": [{ "id": 2001, "label": "KJ370Monitor查询" }, { "id": 2002, "label": "KJ370Monitor新增" }, { "id": 2003, "label": "KJ370Monitor修改" }, { "id": 2004, "label": "KJ370Monitor删除" }, { "id": 2005, "label": "KJ370Monitor导出" }] }, { "id": 110, "label": "定时任务", "children": [{ "id": 1049, "label": "任务查询" }, { "id": 1050, "label": "任务新增" }, { "id": 1051, "label": "任务修改" }, { "id": 1052, "label": "任务删除" }, { "id": 1053, "label": "状态修改" }, { "id": 1054, "label": "任务导出" }] }, { "id": 111, "label": "数据监控" }, { "id": 112, "label": "服务监控" }, { "id": 113, "label": "缓存监控" }] }, { "id": 2085, "label": "Web数字孪生系统管理" }, { "id": 3, "label": "系统工具", "children": [{ "id": 114, "label": "表单构建" }, { "id": 115, "label": "代码生成", "children": [{ "id": 1055, "label": "生成查询" }, { "id": 1056, "label": "生成修改" }, { "id": 1058, "label": "导入代码" }, { "id": 1057, "label": "生成删除" }, { "id": 1059, "label": "预览代码" }, { "id": 1060, "label": "生成代码" }] }, { "id": 116, "label": "系统接口" }, { "id": 2067, "label": "甘特图1" }, { "id": 2068, "label": "甘特图2" }, { "id": 2069, "label": "甘特图3" }] }, { "id": 2086, "label": "系统设置" }, { "id": 2006, "label": "HHHZ", "children": [{ "id": 2007, "label": "首页", "children": [{ "id": 2024, "label": "矿井总览" }, { "id": 2025, "label": "抽采报表" }, { "id": 2026, "label": "抽采系统运行图" }, { "id": 2027, "label": "瓦斯治理人员信息" }, { "id": 2028, "label": "施工信息汇总" }] }, { "id": 2008, "label": "漫游导航", "children": [{ "id": 2018, "label": "矿井总览" }, { "id": 2029, "label": "巷道漫游" }, { "id": 2030, "label": "抽采站漫游" }, { "id": 2031, "label": "管网系统漫游" }] }, { "id": 2009, "label": "精细地质", "children": [{ "id": 2032, "label": "工作面地质" }, { "id": 2033, "label": "瓦斯地质图" }, { "id": 2034, "label": "地质单元划分" }, { "id": 2035, "label": "构造带分布" }, { "id": 2036, "label": "地质资料汇集" }] }, { "id": 2010, "label": "抽采设计", "children": [{ "id": 2019, "label": "单独钻孔设计" }, { "id": 2020, "label": "条带治理钻孔组设计" }, { "id": 2021, "label": "定向钻孔通用设计" }, { "id": 2022, "label": "高位钻孔设计" }, { "id": 2042, "label": "短定向孔设计" }, { "id": 2043, "label": "揭煤设计" }] }, { "id": 2011, "label": "工程集控", "children": [{ "id": 2037, "label": "抽采站监控" }, { "id": 2038, "label": "抽采管网监控" }, { "id": 2039, "label": "抽采施工集控" }, { "id": 2040, "label": "钻孔施工管理" }, { "id": 2041, "label": "智能化设备监控" }] }, { "id": 2044, "label": "抽采动态", "children": [{ "id": 2045, "label": "条带抽采" }, { "id": 2046, "label": "工作面中部抽采" }, { "id": 2065, "label": "采空区抽采" }, { "id": 2047, "label": "揭煤与特殊抽采" }, { "id": 2048, "label": "抽采管网动态监控" }, { "id": 2051, "label": "瓦斯抽采云图" }] }, { "id": 2052, "label": "信息管理", "children": [{ "id": 2053, "label": "数据分析与管理" }, { "id": 2054, "label": "人员管理" }, { "id": 2055, "label": "用户管理" }, { "id": 2066, "label": "报表管理" }] }, { "id": 2014, "label": "达标评判", "children": [{ "id": 2061, "label": "抽采数据" }, { "id": 2062, "label": "抽采钻孔数据" }, { "id": 2063, "label": "取样钻孔数据" }, { "id": 2064, "label": "报告辅助生成" }] }, { "id": 2056, "label": "异常告警", "children": [{ "id": 2057, "label": "异常信息告警" }, { "id": 2058, "label": "安全生产信息管理" }, { "id": 2059, "label": "处理预案" }, { "id": 2060, "label": "推送通知管理" }] }] }      
      // 表单参数
      form: {},
      menuDefaultProps: {
        children: "children",
        label: "label",
        disabled: () => { return true }
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true }
        // { key: 4, label: `岗位`, visible: true },
        // { key: 5, label: `手机号码`, visible: true },
        // { key: 6, label: `状态`, visible: true },
        // { key: 7, label: `创建时间`, visible: true }
      ],
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户账号名称不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '用户账号名称长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: "请选择用户所属部门", trigger: "blur" },
        //20260309禁用岗位管理
        // ],
        // postIds: [
        //   { required: true, message: "请选择用户所属岗位", trigger: "blur" },
        ],
        nickName: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
        ],
        email: [
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        expireTime: [
          { required: true, message: "账号失效时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
    //20260309禁用岗位管理
    // "form.deptId": "onDeptSelected",
    // "form.postIds": "onPostSelected"
    "form.deptId": "onDeptSelected"
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getConfigKey("sys.user.initPassword").then(response => {
      this.initPassword = response.msg;
    });
  },
  methods: {
    // 修改过期时间
    editExpire() {
      this.banEditExpire = false;
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.userList = response.rows;
        for (let i = 0; i < this.userList.length; i++) {
          if (this.userList[i].roles != undefined && this.userList[i].roles.length == 1 && this.userList[i].roles[0].roleName.indexOf("-") != -1)
            this.userList[i].roles[0].roleName = this.userList[i].roles[0].roleName.substring(0, this.userList[i].roles[0].roleName.length - 5)
        }
        this.total = response.total;
        this.loading = false;
      }
      );
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.userName + '"用户吗？').then(function () {
        return changeUserStatus(row.userId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        userId: undefined,
        deptId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        // postIds: undefined,
        roleIds: [],
        expireTime: undefined,
        ////
        menuOptions: []
      };
      this.banEditExpire = true;
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        default:
          break;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      getUser().then(response => {
        // 设置默认有效期1个月
        let date = new Date();
        date.setTime(date.getTime() + 3600 * 1000 * 24 * 31);
        this.form.expireTime = date.toLocaleString().replaceAll("/", "-");
        //20260309禁用岗位管理
        // this.postAllOptions = response.posts;
        // this.postOptions = [];
        // this.roleOptions = response.roles;
        this.open = true;
        this.title = "添加用户";
        this.form.password = this.initPassword;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const userId = row.userId || this.ids;
      getUser(userId).then(response => {
        // this.postAllOptions = response.posts;
        // //this.postOptions = response.posts;
        // /* this.roleOptions = response.roles;
        // console.log("response.postIds", response.postIds);
        // this.form.postIds = response.postIds[0];
        // console.log("this.form.postIds", this.form.postIds)
        // this.form.roleIds = response.roleIds; */
        // let tform = response.data;
        // tform.postIds = response.postIds[0];
        // tform.roleIds = response.roleIds;
        // //this.form = response.data;
        // this.form = tform;

        // //this.form.deptId = this.form.postIds[0];
        // //this.form.postId = this.form.postIds[0];
        let tform = response.data || {};
        tform.roleIds = response.roleIds || [];
        this.form = tform;
        if (this.form.deptId) {
          this.onDeptSelected(this.form.deptId);
        } else {
          this.menuOptions = [];
        }
        this.open = true;
        this.title = "修改用户";
        this.form.password = "";
      });
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: "用户密码长度必须介于 5 和 20 之间"
      }).then(({ value }) => {
        resetUserPwd(row.userId, value).then(response => {
          this.$modal.msgSuccess("修改成功，新密码是：" + value);
        });
      }).catch(() => { });
    },
    /** 分配角色操作 */
    handleAuthRole: function (row) {
      const userId = row.userId;
      this.$router.push("/system/user-auth/role/" + userId);
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.roleIds = this.form.deptId ? [this.form.deptId] : [];
          delete this.form.postIds;
          if (this.form.userId != undefined) {
            //20260309禁用岗位管理
            // console.log(this.form)
            // let postOrRoleId = this.form.postIds;
            // console.log(typeof postOrRoleId)
            // if (typeof postOrRoleId === "number") {
            //   this.form.postIds = [postOrRoleId];
            //   this.form.roleIds = [postOrRoleId];
            // } else {
            //   this.form.postIds = postOrRoleId;
            //   this.form.roleIds = postOrRoleId;
            // }
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            //20260309禁用岗位管理
            // console.log(this.form)
            // let postOrRoleId = this.form.postIds;
            // console.log(typeof postOrRoleId)
            // if (typeof postOrRoleId === "number") {
            //   this.form.postIds = [postOrRoleId];
            //   this.form.roleIds = [postOrRoleId];
            // } else {
            //   this.form.postIds = postOrRoleId;
            //   this.form.roleIds = postOrRoleId;
            // }
            addUser(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userIds = row.userId || this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(function () {
        return delUser(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/user/importTemplate', {
      }, `user_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /**
     * 当所选部门改变时，更新菜单内容
     * @param {*} id 
     */
    onDeptSelected(id) {
      // let _this = this;
      // //console.log("onDeptSelected", id)
      if (id != undefined) {
        const roleId = id;
        const roleMenu = this.getRoleMenuTreeselect(roleId);
        this.form.menuCheckStrictly = true;
        this.$nextTick(() => {
          roleMenu.then(res => {
            // console.log(this.$refs.menu)

            let checkedKeys = res.checkedKeys
            checkedKeys.forEach((v) => {
              this.$nextTick(() => {
                this.$refs.menu.setChecked(v, true, false);
              })
            })
          });
        });
      } else {
        this.menuOptions = [];
        this.$nextTick(() => {
          if (this.$refs.menu) {
            this.$refs.menu.setCheckedKeys([]);
          }
        });
      }
    },
    //     _this.postOptions = [];
    //     // 计算post下拉选项
    //     for (let i = 0; i < _this.postAllOptions.length; i++) {
    //       if (_this.postAllOptions[i].deptId === id) {
    //         //console.log("postAllOptions[i]", _this.postAllOptions[i])
    //         _this.postOptions.push(_this.postAllOptions[i])
    //       }
    //     }

    //     //console.log("_this.postOptions", _this.postOptions)
    //     //console.log("_this.form.postIds", _this.form.postIds)
    //     // 解决岗位回显失效问题
    //     let contain = false;
    //     for (let i = 0; i < _this.postOptions.length; i++) {
    //       _this.postOptions[i].postId === _this.form.postIds;
    //       contain = true;
    //     }
    //     if (!contain) {
    //       // 如果不包含的话就全部情况，重新算一遍
    //       _this.form.postIds = undefined;
    //       _this.postOptions = [];
    //       for (let i = 0; i < _this.postAllOptions.length; i++) {
    //         if (_this.postAllOptions[i].deptId === id) {
    //           console.log("postAllOptions[i]", _this.postAllOptions[i])
    //           _this.postOptions.push(_this.postAllOptions[i])
    //         }
    //       }
    //     }
    //   }
    // },
    // /**
    //  * 当所选岗位改变时，更新菜单内容
    //  * @param {*} id 
    //  */
    // onPostSelected(ids) {
    //   //console.log("onPostSelected", ids)
    //   if ((ids != undefined && ids.length > 0) || ids > 2000) {
    //     console.log("onPostSelected", ids)
    //     const roleId = ids;
    //     const roleMenu = this.getRoleMenuTreeselect(roleId);
    //     this.form.menuCheckStrictly = true;
    
    //     this.$nextTick(() => {
    //       roleMenu.then(res => {
    //         console.log(this.$refs.menu)
    //         for (let i = 0; i < this.$refs.menu.length; i++) {
    //           this.$set(this.$refs.menu[i], "disabled", true);
    //           console.log(this.$refs.menu[i]);
    //         }

    //         let checkedKeys = res.checkedKeys
    //         checkedKeys.forEach((v) => {
    //           this.$nextTick(() => {
    //             this.$refs.menu.setChecked(v, true, false);
    //           })
    //         })
    //       });
    //     });
    //   }
    // },
    /** 根据部门ID查询菜单树结构 */
    getRoleMenuTreeselect(roleId) {
      return roleMenuTreeselect(roleId).then(response => {
        this.menuOptions = response.menus;
        return response;
      });
    },
  },

};
</script>