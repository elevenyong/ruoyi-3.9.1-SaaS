<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="钻孔地点" prop="zkdd">
        <el-select v-model="queryParams.roadwayId" placeholder="请选择钻孔地点">
          <el-option
            v-for="item in zkddOptions"
            :key="item.id"
            :label="item.roadway"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="注浆日期" prop="checkingDate" label-width="98px">
        <el-date-picker
          v-model="queryParams.groutingDate"
          type="date"
          value-format="yyyy-MM-dd"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="班次" prop="zkyt">
        <el-select v-model="queryParams.shiftId" placeholder="请选择工作班次">
          <el-option
            v-for="item in gzbcOptions"
            :key="item.id"
            :label="item.shiftInfo"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="注浆人" prop="groutingPerson">
        <!-- @keyup.enter.native="handleQuery" -->
        <el-input
          v-model="queryParams.groutingPerson"
          placeholder=""
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
        >搜索
        </el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
        >重置
        </el-button
        >
        <el-button icon="el-icon-download" size="mini" @click="handleExport"
        >导出
        </el-button>
      </el-form-item>
    </el-form>

    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['hhhz:tSealingGrouting:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['hhhz:tSealingGrouting:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['hhhz:tSealingGrouting:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['hhhz:tSealingGrouting:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row> -->

    <el-table
      v-loading="loading"
      :data="tSealingGroutingList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <!-- <el-table-column label="${comment}" align="center" prop="id" /> -->
      <el-table-column label="序号" align="center" prop="id"/>
      <el-table-column label="钻孔地点" align="center" prop="roadway"/>
      <el-table-column label="孔号" align="center" prop="holeId"/>
      <el-table-column label="注浆日期" align="center" prop="groutingDate"/>
      <el-table-column
        label="下管长度(m)"
        align="center"
        prop="lengthpipeinsertion"
      />
      <el-table-column label="合成树胶(袋)" align="center" prop="boreholeId"/>
      <el-table-column label="注浆开始时间" align="center" prop="starttime"/>
      <el-table-column label="注浆结束时间" align="center" prop="endtime"/>
      <el-table-column
        label="设计囊袋注浆量(L)"
        align="center"
        prop="needamountbaggrouting"
      />
      <el-table-column
        label="实际囊袋注浆量(L)"
        align="center"
        prop="actualAmountBagGrouting"
      />
      <el-table-column
        label="设计中间段注浆量(L)"
        align="center"
        prop="designedamountgapgrouting"
      />
      <el-table-column
        label="实际中间段注浆量(L)"
        align="center"
        prop="actualAmountGapGrouting"
      />
      <el-table-column label="注浆人员" align="center" prop="groutingPerson"/>
      <el-table-column
        label="异常现象标注"
        align="center"
        prop="exceptionlabel"
      />
      <el-table-column label="记录人" align="center" prop="recorder"/>

      <!-- <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hhhz:tSealingGrouting:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hhhz:tSealingGrouting:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改tSealingGrouting对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="孔号对应ID" prop="boreholeId">
          <el-input v-model="form.boreholeId" placeholder="请输入孔号对应ID"/>
        </el-form-item>
        <el-form-item label="班次ID" prop="shiftId">
          <el-input v-model="form.shiftId" placeholder="请输入班次ID"/>
        </el-form-item>
        <el-form-item label="注浆日期" prop="groutingDate">
          <el-date-picker
            clearable
            v-model="form.groutingDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择注浆日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="下管长度" prop="lengthpipeinsertion">
          <el-input
            v-model="form.lengthpipeinsertion"
            placeholder="请输入下管长度"
          />
        </el-form-item>
        <el-form-item label="封孔方式ID" prop="holesealtypeid">
          <el-input
            v-model="form.holesealtypeid"
            placeholder="请输入封孔方式ID"
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="starttime">
          <el-date-picker
            clearable
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endtime">
          <el-date-picker
            clearable
            v-model="form.endtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="需要囊袋注浆量" prop="needamountbaggrouting">
          <el-input
            v-model="form.needamountbaggrouting"
            placeholder="请输入需要囊袋注浆量"
          />
        </el-form-item>
        <el-form-item label="实际囊袋注浆量" prop="actualAmountBagGrouting">
          <el-input
            v-model="form.actualAmountBagGrouting"
            placeholder="请输入实际囊袋注浆量"
          />
        </el-form-item>
        <el-form-item label="囊袋注浆压力" prop="actualBagPressure">
          <el-input
            v-model="form.actualBagPressure"
            placeholder="请输入囊袋注浆压力"
          />
        </el-form-item>
        <el-form-item label="所需中间段注浆量" prop="designedamountgapgrouting">
          <el-input
            v-model="form.designedamountgapgrouting"
            placeholder="请输入所需中间段注浆量"
          />
        </el-form-item>
        <el-form-item label="实际中间段注浆量" prop="actualAmountGapGrouting">
          <el-input
            v-model="form.actualAmountGapGrouting"
            placeholder="请输入实际中间段注浆量"
          />
        </el-form-item>
        <el-form-item label="中间段注浆压力" prop="interludeGroutingPressure">
          <el-input
            v-model="form.interludeGroutingPressure"
            placeholder="请输入中间段注浆压力"
          />
        </el-form-item>
        <el-form-item label="注浆人员" prop="groutingPerson">
          <el-input
            v-model="form.groutingPerson"
            placeholder="请输入注浆人员"
          />
        </el-form-item>
        <el-form-item label="始抽瓦斯浓度" prop="startdrainagegasconcentration">
          <el-input
            v-model="form.startdrainagegasconcentration"
            placeholder="请输入始抽瓦斯浓度"
          />
        </el-form-item>
        <el-form-item label="始抽浓度测量日期" prop="startdrainagedate">
          <el-date-picker
            clearable
            v-model="form.startdrainagedate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择始抽浓度测量日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="始抽浓度测量人员" prop="startdrainageperson">
          <el-input
            v-model="form.startdrainageperson"
            placeholder="请输入始抽浓度测量人员"
          />
        </el-form-item>
        <el-form-item label="异常现象标注" prop="exceptionlabel">
          <el-input
            v-model="form.exceptionlabel"
            placeholder="请输入异常现象标注"
          />
        </el-form-item>
        <el-form-item label="记录人" prop="recorder">
          <el-input v-model="form.recorder" placeholder="请输入记录人"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
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
import { gGongzuobanci } from '@/api/hhhz/sys'
import { gRoadWayList } from '@/api/hhhz/zuankong'
import { gFengkongzhujiang } from '@/api/hhhz/17fkzjjl'

export default {
  name: 'TSealingGrouting',
  data() {
    return {
      // 选中时间
      queryCheckingdate: [],
      // 钻孔地点
      zkddOptions: [],
      // 工作班次
      gzbcOptions: [],
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
      // tSealingGrouting表格数据
      tSealingGroutingList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getRoadWayList()
    this.getGongzuobanciList()
    this.getList()
  },
  methods: {
    // 获取工作班次
    async getGongzuobanciList() {
      await gGongzuobanci().then((response) => {
        console.log('gGongzuobanci', response)
        this.gzbcOptions = response.rows
      })
    },
    // 获取钻孔地点
    async getRoadWayList() {
      this.loading = true
      await gRoadWayList({}).then((response) => {
        console.log('gRoadWayList', response)
        this.zkddOptions = response.rows
      })
    },
    /** 查询tSealingGrouting列表 */
    getList() {
      this.loading = true
      gFengkongzhujiang(this.queryParams).then((response) => {
        let resp = response.rows
        this.actual_list = []
        let ysList = []
        resp.forEach((e) => {
          e.holeId = e.roadway + '-' + e.groupNo + '-' + e.boreholeNo
          ysList.push(e)
        })
        this.tSealingGroutingList = ysList
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
        boreholeId: null,
        shiftId: null,
        groutingDate: null,
        lengthpipeinsertion: null,
        holesealtypeid: null,
        starttime: null,
        endtime: null,
        needamountbaggrouting: null,
        actualAmountBagGrouting: null,
        actualBagPressure: null,
        designedamountgapgrouting: null,
        actualAmountGapGrouting: null,
        interludeGroutingPressure: null,
        groutingPerson: null,
        startdrainagegasconcentration: null,
        startdrainagedate: null,
        startdrainageperson: null,
        exceptionlabel: null,
        recorder: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      /* this.resetForm("queryForm"); */
      this.queryParams = {
        pageNum: 1,
        pageSize: 10
      }
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加tSealingGrouting'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTSealingGrouting(id).then((response) => {
        this.form = response.data
        this.open = true
        this.title = '修改tSealingGrouting'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTSealingGrouting(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addTSealingGrouting(this.form).then((response) => {
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
      this.$modal
        .confirm('是否确认删除tSealingGrouting编号为"' + ids + '"的数据项？')
        .then(function() {
          return delTSealingGrouting(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        'tSealingGrouting/export',
        {},
        `封孔注浆记录_${new Date().getTime()}.xlsx`
      )
    }
  }
}
</script>
