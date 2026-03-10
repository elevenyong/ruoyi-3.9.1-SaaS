<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="时间范围" prop="zkdd">
        <el-date-picker
          v-model="queryParams.timeflushing"
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          is-range
          arrow-control
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          placeholder="选择时间范围"
          :disabled="queryParams.disabled"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
      </el-form-item>
    </el-form>


    <el-table v-loading="false" :data="geofaultList" @selection-change="handleSelectionChange">
      <!--      <el-table-column type="selection" width="55" align="center"/>-->
      <el-table-column label="设备代码" align="center" prop="id"/>
      <el-table-column label="测点名称" align="center" prop="name"/>
      <el-table-column label="计算值" align="center" prop="azimuth"/>
      <el-table-column label="值状态" align="center" prop="inclination"/>
      <el-table-column label="开始时间" align="center" prop="centerX"/>
      <el-table-column label="结束时间" align="center" prop="centerY"/>
      <el-table-column label="采样次数" align="center" prop="centerZ"/>
      <el-table-column label="持续时间" align="center" prop="faultHeight"/>
      <!--      <el-table-column label="正逆断层" align="center" prop="faultType" :formatter="formatFault"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:geofault:edit']"
                >修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['system:geofault:remove']"
                >删除
                </el-button>
              </template>
            </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改断层对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="断层名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入断层名称"/>
        </el-form-item>
        <el-form-item label="正\逆断层" prop="faultType">
          <el-radio v-model="form.faultType" :label="0">正断层</el-radio>
          <el-radio v-model="form.faultType" :label="1">逆断层</el-radio>
        </el-form-item>
        <el-form-item label="方位角" prop="azimuth">
          <el-input v-model="form.azimuth" placeholder="请输入方位角"/>
        </el-form-item>
        <el-form-item label="倾角" prop="inclination">
          <el-input v-model="form.inclination" placeholder="请输入倾角"/>
        </el-form-item>
        <el-form-item label="x坐标" prop="centerX">
          <el-input v-model="form.centerX" placeholder="请输入x坐标"/>
        </el-form-item>
        <el-form-item label="y坐标" prop="centerY">
          <el-input v-model="form.centerY" placeholder="请输入y坐标"/>
        </el-form-item>
        <el-form-item label="z坐标" prop="centerZ">
          <el-input v-model="form.centerZ" placeholder="请输入z坐标"/>
        </el-form-item>
        <el-form-item label="断层高度" prop="faultHeight">
          <el-input v-model="form.faultHeight" placeholder="请输入断层高度"/>
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
import { addGeofault, delGeofault, getGeofault, listGeofault, updateGeofault } from '@/api/hhhz/duanceng'

export default {
  name: 'Geofault',
  data() {
    return {
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
      // 断层表格数据
      geofaultList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        azimuth: null,
        inclination: null,
        centerX: null,
        centerY: null,
        centerZ: null,
        faultHeight: null,
        faultType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    //this.getList()
  },
  methods: {
    formatFault(row) {
      return row.faultType === 0 ? '正断层' : row.faultType === 1 ? '逆断层' : ''
    },
    /** 查询断层列表 */
    getList() {
      this.loading = true
      listGeofault(this.queryParams).then(response => {
        this.geofaultList = response.rows
        this.total = response.total
        this.loading = false
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
        id: null,
        name: null,
        azimuth: null,
        inclination: null,
        centerX: null,
        centerY: null,
        centerZ: null,
        faultHeight: null,
        faultType: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加断层'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getGeofault(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改断层'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGeofault(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addGeofault(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除断层编号为"' + ids + '"的数据项？').then(function() {
        return delGeofault(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/geofault/export', {
        ...this.queryParams
      }, `geofault_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
