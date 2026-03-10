<template>
  <div class="app-container">
    <!-- 查询区域 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="租户编码" prop="tenantCode">
        <el-input v-model="queryParams.tenantCode" placeholder="请输入租户编码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="租户名称" prop="tenantName">
        <el-input v-model="queryParams.tenantName" placeholder="请输入租户名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="租户状态" clearable>
          <el-option label="正常" value="0" />
          <el-option label="停用" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:tenant:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="tenantList">
      <el-table-column label="租户ID" align="center" prop="tenantId" width="80" />
      <el-table-column label="租户编码" align="center" prop="tenantCode" width="180" />
      <el-table-column label="租户名称" align="center" prop="tenantName" min-width="160" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
          <el-tag v-else type="danger">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" />

      <el-table-column label="操作" align="center" width="120" fixed="right">
        <template slot-scope="scope">
          <!-- 统一操作入口：一个按钮下拉菜单，包含修改/停用/初始化/重置密码 -->
          <el-dropdown
            trigger="click"
            @command="(cmd) => handleCommand(cmd, scope.row)"
          >
            <el-button size="mini" type="text" icon="el-icon-more">
              操作<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <!-- ✅ 新增：修改（原来被注释掉了） -->
              <el-dropdown-item command="edit" icon="el-icon-edit" v-hasPermi="['system:tenant:edit']">修改</el-dropdown-item>

              <el-dropdown-item
                command="toggle"
                :icon="scope.row.status === '0' ? 'el-icon-close' : 'el-icon-check'"
                v-hasPermi="['system:tenant:edit']"
              >{{ scope.row.status === '0' ? '停用' : '启用' }}</el-dropdown-item>

              <el-dropdown-item command="init" icon="el-icon-refresh" v-hasPermi="['system:tenant:init']">初始化基础</el-dropdown-item>
              <el-dropdown-item command="resetPwd" icon="el-icon-key" v-hasPermi="['system:tenant:resetPwd']">重置密码</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 新增租户弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="720px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-divider content-position="left">租户信息</el-divider>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="租户编码" prop="tenantCode">
              <el-input v-model="form.tenantCode" :disabled="!!form.tenantId" placeholder="如 pmbk08（唯一）" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="租户名称" prop="tenantName">
              <el-input v-model="form.tenantName" placeholder="如 平煤八矿" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio label="0">正常</el-radio>
                <el-radio label="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <!-- ✅ 只新增：失效时间 expire_time（前端字段：expireTime） -->
          <el-col :span="12">
            <el-form-item label="失效时间" prop="adminExpireTime">
              <el-date-picker
                v-model="form.adminExpireTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择失效时间"
                clearable
                style="width: 100%;"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="form.remark" :rows="2" placeholder="可选" />
        </el-form-item>

        <el-divider content-position="left" v-if="!form.tenantId">租户管理员账号</el-divider>

        <el-row :gutter="20" v-if="!form.tenantId">
          <el-col :span="12">
            <el-form-item label="管理员账号" prop="adminUserName">
              <el-input v-model="form.adminUserName" placeholder="租户内唯一，如 admin" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="管理员密码" prop="adminPassword">
              <el-input v-model="form.adminPassword" placeholder="建议 Admin@123456" show-password />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" v-if="!form.tenantId">
          <el-col :span="12">
            <el-form-item label="管理员昵称" prop="adminNickName">
              <el-input v-model="form.adminNickName" placeholder="如 八矿管理员" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="adminPhone">
              <el-input v-model="form.adminPhone" placeholder="可选" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" v-if="!form.tenantId">
          <el-col :span="12">
            <el-form-item label="邮箱" prop="adminEmail">
              <el-input v-model="form.adminEmail" placeholder="可选" />
            </el-form-item>
          </el-col>
        </el-row>

        <div v-if="!form.tenantId" style="color:#999; line-height: 20px;">
          创建后系统会自动初始化：默认部门、租户管理员角色（全菜单权限）、租户管理员账号（绑定该角色）。
        </div>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 初始化租户基础数据弹窗 -->
    <el-dialog title="初始化租户基础数据" :visible.sync="initOpen" width="520px" append-to-body>
      <el-form ref="initFormRef" :model="initForm" label-width="120px">
        <el-form-item label="租户ID">
          <el-input v-model="currentTenantId" disabled />
        </el-form-item>
        <el-form-item label="模板租户ID">
          <el-input-number v-model="initForm.templateTenantId" :min="1" :max="999999999" />
          <div class="el-form-item__tip">一般填 default 租户 tenant_id（你现在是 1）</div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="initOpen=false">取 消</el-button>
        <el-button type="primary" @click="submitInitBase">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 重置租户管理员密码弹窗 -->
    <el-dialog title="重置租户管理员密码" :visible.sync="resetOpen" width="520px" append-to-body>
      <el-form ref="resetPwdFormRef" :model="resetPwdForm" label-width="120px">
        <el-form-item label="租户ID">
          <el-input v-model="currentTenantId" disabled />
        </el-form-item>
        <el-form-item label="管理员账号">
          <el-input v-model="resetPwdForm.adminUserName" placeholder="如 user_admin_7" />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="resetPwdForm.newPassword" placeholder="请输入新密码" show-password />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetOpen=false">取 消</el-button>
        <el-button type="primary" @click="submitResetPwd">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTenant, getTenant, addTenant, updateTenant, changeTenantStatus, initTenantBase, resetTenantAdminPwd } from "@/api/system/tenant";

export default {
  name: "Tenant",
  data() {
    const vm = this;
    const requiredIfCreate = (msg) => {
      return (rule, value, callback) => {
        // 编辑租户时不校验管理员信息
        if (vm.form && vm.form.tenantId) {
          callback();
          return;
        }
        if (!value) {
          callback(new Error(msg));
          return;
        }
        callback();
      };
    };

    return {
      // 列表
      loading: true,
      total: 0,
      tenantList: [],

      // 查询
      showSearch: true,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tenantCode: undefined,
        tenantName: undefined,
        status: undefined
      },

      // 弹窗
      open: false,
      title: "",
      form: {},

      // 初始化基础
      initOpen: false,
      initForm: {
        templateTenantId: 1
      },

      // 重置密码
      resetOpen: false,
      resetPwdForm: {
        adminUserName: "admin",
        newPassword: ""
      },

      currentTenantId: undefined,

      rules: {
        tenantCode: [
          { required: true, message: "租户编码不能为空", trigger: "blur" },
          { min: 2, max: 64, message: "长度在 2 到 64 个字符", trigger: "blur" }
        ],
        tenantName: [
          { required: true, message: "租户名称不能为空", trigger: "blur" },
          { min: 2, max: 128, message: "长度在 2 到 128 个字符", trigger: "blur" }
        ],
        adminUserName: [
          { validator: requiredIfCreate("管理员账号不能为空"), trigger: "blur" },
          { min: 2, max: 30, message: "长度在 2 到 30 个字符", trigger: "blur" }
        ],
        adminPassword: [
          { validator: requiredIfCreate("管理员密码不能为空"), trigger: "blur" },
          { min: 6, max: 50, message: "长度在 6 到 50 个字符", trigger: "blur" }
        ],
        adminNickName: [
          { validator: requiredIfCreate("管理员昵称不能为空"), trigger: "blur" },
          { min: 2, max: 30, message: "长度在 2 到 30 个字符", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      listTenant(this.queryParams).then(res => {
        this.tenantList = res.rows || [];
        this.total = res.total || 0;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    /** 搜索 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 下拉操作入口 */
    handleCommand(cmd, row) {
      switch (cmd) {
        case 'edit':
          this.handleUpdate(row);
          break;
        case 'toggle':
          this.handleStatusChange(row);
          break;
        case 'init':
          this.handleInitBase(row);
          break;
        case 'resetPwd':
          this.handleResetPwd(row);
          break;
        default:
          break;
      }
    },
    /** 编辑租户 */
    handleUpdate(row) {
      this.reset();
      const tenantId = row.tenantId;
      getTenant(tenantId).then(res => {
        this.form = res.data;
        this.form.adminExpireTime = this.form.adminExpireTime || this.form.expireTime || this.form.expire_time;
        this.open = true;
        this.title = "编辑租户";
      });
    },
    /** 启用/停用 */
    handleStatusChange(row) {
      const text = row.status === '0' ? '停用' : '启用';
      const newStatus = row.status === '0' ? '1' : '0';
      this.$modal.confirm(`确认要${text}租户【${row.tenantCode}】吗？`).then(() => {
        return changeTenantStatus({ tenantId: row.tenantId, status: newStatus });
      }).then(() => {
        this.$modal.msgSuccess(`${text}成功`);
        this.getList();
      }).catch(() => {});
    },
    /** 点击“初始化基础” */
    handleInitBase(row) {
      this.currentTenantId = row.tenantId;
      this.initForm.templateTenantId = 1;
      this.initOpen = true;
    },
    /** 提交初始化 */
    submitInitBase() {
      initTenantBase(this.currentTenantId, this.initForm.templateTenantId).then(() => {
        this.$modal.msgSuccess("初始化成功");
        this.initOpen = false;
      });
    },
    /** 点击“重置密码” */
    handleResetPwd(row) {
      this.currentTenantId = row.tenantId;
      this.resetPwdForm.adminUserName = "admin";
      this.resetPwdForm.newPassword = "";
      this.resetOpen = true;
    },
    /** 提交重置密码 */
    submitResetPwd() {
      resetTenantAdminPwd({
        tenantId: this.currentTenantId,
        adminUserName: this.resetPwdForm.adminUserName,
        newPassword: this.resetPwdForm.newPassword
      }).then(() => {
        this.$modal.msgSuccess("重置成功");
        this.resetOpen = false;
      });
    },
    /** 新增 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增租户（同步创建租户管理员）";
    },
    reset() {
      this.form = {
        tenantId: undefined,
        tenantCode: "",
        tenantName: "",
        status: "0",
        remark: "",
        adminUserName: "admin",
        adminPassword: "admin@123",
        adminNickName: "租户管理员",
        adminPhone: "",
        adminEmail: "",
        // ✅ 新增：失效时间
        adminExpireTime: null
      };
      this.resetForm("form");
    },
    /** 提交表单：新增/编辑 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) return;

        if (this.form.tenantId) {
          updateTenant(this.form).then(() => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        } else {
          addTenant(this.form).then(() => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      });
    }
  }
};
</script>