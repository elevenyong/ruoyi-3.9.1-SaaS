<template>
    <div class="app-container">
      <!-- 查询区域 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="租户名称" prop="tenantName">
          <el-input
            v-model="queryParams.tenantName"
            placeholder="请输入租户名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="租户编码" prop="tenantCode">
          <el-input
            v-model="queryParams.tenantCode"
            placeholder="请输入租户编码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="租户状态" clearable>
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
  
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
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:tenant:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:tenant:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:tenant:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <!-- 数据表格 -->
      <el-table v-loading="loading" :data="tenantList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="租户ID" align="center" prop="tenantId" width="100" />
        <el-table-column label="租户名称" align="center" prop="tenantName" :show-overflow-tooltip="true" />
        <el-table-column label="租户编码" align="center" prop="tenantCode" width="140" />
        <el-table-column label="联系人" align="center" prop="contactUser" width="120" />
        <el-table-column label="联系电话" align="center" prop="contactPhone" width="140" />
        <el-table-column label="状态" align="center" prop="status" width="100">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="160">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:tenant:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:tenant:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
  
      <!-- 添加或修改租户对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="90px">
          <el-form-item label="租户名称" prop="tenantName">
            <el-input v-model="form.tenantName" placeholder="请输入租户名称" />
          </el-form-item>
          <el-form-item label="租户编码" prop="tenantCode">
            <el-input v-model="form.tenantCode" placeholder="请输入租户编码" />
          </el-form-item>
          <el-form-item label="联系人" prop="contactUser">
            <el-input v-model="form.contactUser" placeholder="请输入联系人" />
          </el-form-item>
          <el-form-item label="联系电话" prop="contactPhone">
            <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { listTenant, getTenant, addTenant, updateTenant, delTenant, exportTenant, changeTenantStatus } from "@/api/system/tenant";
  
  export default {
    name: "Tenant",
    dicts: ['sys_normal_disable'],
    data() {
      return {
        loading: true,
        ids: [],
        single: true,
        multiple: true,
        showSearch: true,
        total: 0,
        tenantList: [],
        title: "",
        open: false,
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          tenantName: undefined,
          tenantCode: undefined,
          status: undefined
        },
        form: {},
        rules: {
          tenantName: [
            { required: true, message: "租户名称不能为空", trigger: "blur" }
          ],
          tenantCode: [
            { required: true, message: "租户编码不能为空", trigger: "blur" }
          ],
          contactPhone: [
            { pattern: /^1[3-9]\d{9}$/, message: "请输入正确的手机号", trigger: "blur" }
          ]
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        this.loading = true;
        listTenant(this.queryParams).then(response => {
          this.tenantList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      cancel() {
        this.open = false;
        this.reset();
      },
      reset() {
        this.form = {
          tenantId: undefined,
          tenantName: undefined,
          tenantCode: undefined,
          contactUser: undefined,
          contactPhone: undefined,
          status: "0",
          remark: undefined
        };
        this.resetForm("form");
      },
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.tenantId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加租户";
      },
      handleUpdate(row) {
        this.reset();
        const tenantId = row.tenantId || this.ids
        getTenant(tenantId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改租户";
        });
      },
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.tenantId != undefined) {
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
          }
        });
      },
      handleDelete(row) {
        const tenantIds = row.tenantId || this.ids;
        this.$modal.confirm('是否确认删除租户编号为"' + tenantIds + '"的数据项？').then(() => {
          return delTenant(tenantIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      handleExport() {
        this.download('system/tenant/export', {
          ...this.queryParams
        }, `tenant_${new Date().getTime()}.xlsx`)
      },
      handleStatusChange(row) {
        const text = row.status === "0" ? "启用" : "停用";
        this.$modal.confirm('确认要"' + text + '""' + row.tenantName + '"租户吗？').then(() => {
          return changeTenantStatus(row.tenantId, row.status);
        }).then(() => {
          this.$modal.msgSuccess(text + "成功");
        }).catch(() => {
          row.status = row.status === "0" ? "1" : "0";
        });
      }
    }
  };
  </script>