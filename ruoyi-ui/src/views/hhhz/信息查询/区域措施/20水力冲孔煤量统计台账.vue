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
        <el-select
          v-model="queryRoadWay"
          multiple
          collapse-tags
          placeholder="请选择钻孔地点"
        >
          <el-option
            v-for="item in zkddOptions"
            :key="item.id"
            :label="item.roadway"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="钻孔时间" prop="checkingDate">
        <el-date-picker
          v-model="queryCheckingdate"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
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
                    v-hasPermi="['hhhz:tActualFlushing:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                    v-hasPermi="['hhhz:tActualFlushing:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                    @click="handleDelete" v-hasPermi="['hhhz:tActualFlushing:remove']">删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    v-hasPermi="['hhhz:tActualFlushing:export']">导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row> -->

    <el-table
      v-loading="loading"
      :data="tActualFlushingList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <!-- <el-table-column label="${comment}" align="center" prop="id" /> -->
      <el-table-column label="序号" align="center" prop="boreholeNo"/>
      <el-table-column label="日期" align="center" prop="dateflushing"/>
      <el-table-column label="冲孔地点" align="center" prop="roadway"/>
      <el-table-column label="孔号" align="center" prop="holeId"/>
      <el-table-column label="孔深" align="center" prop="boreholedepth"/>
      <el-table-column label="冲孔段" align="center" prop="chongkongduan"/>
      <el-table-column
        label="冲孔段长度(m)"
        align="center"
        prop="chongkongduanchangdu"
      />
      <el-table-column
        label="冲孔时间"
        align="center"
        prop="totalTimeFlushing"
      />
      <el-table-column
        label="施工负责人"
        align="center"
        prop="personConstruction"
      />
      <el-table-column
        label="冲出煤量(m³)"
        align="center"
        prop="actualCoalAmountFlushed"
      />
      <el-table-column label="验收时间" align="center" prop="receptiontime"/>

      <!--<el-table-column label="孔号ID" align="center" prop="boreholeId" />
            <el-table-column label="冲孔日期" align="center" prop="dateflushing" width="180">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.dateflushing, '{y}-{m}-{d}') }}</span>
                </template>
            </el-table-column>
            <el-table-column label="开始冲孔时间" align="center" prop="timeBeginFlushing" width="180">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.timeBeginFlushing) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="冲孔结束时间" align="center" prop="timeEndFlushing" width="180">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.timeEndFlushing) }}</span>
                </template>
            </el-table-column>
             <el-table-column label="巷道" align="center" prop="roadway" />
            <el-table-column label="累计冲孔时间" align="center" prop="totalTimeFlushing" />
            <el-table-column label="冲孔压力" align="center" prop="waterPressureDuringFlushing" />
            <el-table-column label="冲孔前孔口瓦斯浓度" align="center" prop="gasconcbeforeflushing" />
            <el-table-column label="冲孔后孔口瓦斯浓度" align="center" prop="gasconcafterflushing" />
            <el-table-column label="冲孔段1" align="center" prop="punchingParagraph1" />
            <el-table-column label="冲孔段2" align="center" prop="punchingParagraph2" />
            <el-table-column label="见岩情况" align="center" prop="depthtrockinfo" />
            <el-table-column label="实际冲孔煤量" align="center" prop="actualCoalAmountFlushed" />
            <el-table-column label="冲煤完成比例" align="center" prop="completionRatioFlushedCoal" />
            <el-table-column label="施工人员" align="center" prop="personConstruction" />
            <el-table-column label="验收时间" align="center" prop="receptiontime" width="180">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.receptiontime) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="冲孔过程中现场异常标注" align="center" prop="exceptionPhenomena" />
            <el-table-column label="验收人员" align="center" prop="personChecking" />
            <el-table-column label="跟班队员" align="center" prop="otherPerson" />
            <el-table-column label="备注" align="center" prop="remark" />-->
      <el-table-column
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
            v-hasPermi="['hhhz:tActualFlushing:edit']"
          >修改
          </el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hhhz:tActualFlushing:remove']"
          >删除
          </el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改tActualFlushing对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="孔号对应ID" prop="boreholeId">
          <el-input v-model="form.boreholeId" placeholder="请输入孔号对应ID"/>
        </el-form-item>
        <el-form-item label="班次ID" prop="shiftId">
          <el-input v-model="form.shiftId" placeholder="请输入班次ID"/>
        </el-form-item>
        <el-form-item label="冲孔日期" prop="dateflushing">
          <el-date-picker
            clearable
            v-model="form.dateflushing"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择冲孔日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开始冲孔时间" prop="timeBeginFlushing">
          <el-date-picker
            clearable
            v-model="form.timeBeginFlushing"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始冲孔时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="冲孔结束时间" prop="timeEndFlushing">
          <el-date-picker
            clearable
            v-model="form.timeEndFlushing"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择冲孔结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="累计冲孔时间" prop="totalTimeFlushing">
          <el-input
            v-model="form.totalTimeFlushing"
            placeholder="请输入累计冲孔时间"
          />
        </el-form-item>
        <el-form-item label="冲孔压力" prop="waterPressureDuringFlushing">
          <el-input
            v-model="form.waterPressureDuringFlushing"
            placeholder="请输入冲孔压力"
          />
        </el-form-item>
        <el-form-item label="冲孔前孔口瓦斯浓度" prop="gasconcbeforeflushing">
          <el-input
            v-model="form.gasconcbeforeflushing"
            placeholder="请输入冲孔前孔口瓦斯浓度"
          />
        </el-form-item>
        <el-form-item label="冲孔后孔口瓦斯浓度" prop="gasconcafterflushing">
          <el-input
            v-model="form.gasconcafterflushing"
            placeholder="请输入冲孔后孔口瓦斯浓度"
          />
        </el-form-item>
        <el-form-item label="冲孔段1" prop="punchingParagraph1">
          <el-input
            v-model="form.punchingParagraph1"
            placeholder="请输入冲孔段1"
          />
        </el-form-item>
        <el-form-item label="冲孔段2" prop="punchingParagraph2">
          <el-input
            v-model="form.punchingParagraph2"
            placeholder="请输入冲孔段2"
          />
        </el-form-item>
        <el-form-item label="见岩情况" prop="depthtrockinfo">
          <el-input
            v-model="form.depthtrockinfo"
            placeholder="请输入见岩情况"
          />
        </el-form-item>
        <el-form-item label="实际冲孔煤量" prop="actualCoalAmountFlushed">
          <el-input
            v-model="form.actualCoalAmountFlushed"
            placeholder="请输入实际冲孔煤量"
          />
        </el-form-item>
        <el-form-item label="冲煤完成比例" prop="completionRatioFlushedCoal">
          <el-input
            v-model="form.completionRatioFlushedCoal"
            placeholder="请输入冲煤完成比例"
          />
        </el-form-item>
        <el-form-item label="施工人员" prop="personConstruction">
          <el-input
            v-model="form.personConstruction"
            placeholder="请输入施工人员"
          />
        </el-form-item>
        <el-form-item label="验收时间" prop="receptiontime">
          <el-date-picker
            clearable
            v-model="form.receptiontime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择验收时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="冲孔过程中现场异常标注" prop="exceptionPhenomena">
          <el-input
            v-model="form.exceptionPhenomena"
            placeholder="请输入冲孔过程中现场异常标注"
          />
        </el-form-item>
        <el-form-item label="验收人员" prop="personChecking">
          <el-input
            v-model="form.personChecking"
            placeholder="请输入验收人员"
          />
        </el-form-item>
        <el-form-item label="跟班队员" prop="otherPerson">
          <el-input v-model="form.otherPerson" placeholder="请输入跟班队员"/>
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
import { gRoadWayList } from '@/api/hhhz/zuankong'
import { gShuilichongkongtaizhang } from '@/api/hhhz/19slcktz'

export default {
  name: 'shuilichongkongtz',
  data() {
    return {
      // 钻孔地点
      queryRoadWay: [],
      // 选中时间
      queryCheckingdate: [],
      // 钻孔地点
      zkddOptions: [],
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
      // tActualFlushing表格数据
      tActualFlushingList: [],
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
    this.getList()
  },
  methods: {
    // 获取钻孔地点
    async getRoadWayList() {
      this.loading = true
      await gRoadWayList({}).then((response) => {
        console.log('gRoadWayList', response)
        this.zkddOptions = response.rows
      })
    },
    /** 查询tActualFlushing列表 */
    getList() {
      this.loading = true
      // 钻孔地点
      console.log('钻孔地点:', this.queryRoadWay)
      if (this.queryRoadWay && this.queryRoadWay.length >= 1) {
        this.queryParams.roadwayId = JSON.stringify(this.queryRoadWay)
          .replace('[', '')
          .replace(']', '')
      } else {
        delete this.queryParams.boreholeNo
      }
      // 时间
      console.log('时间', this.queryCheckingdate)
      if (this.queryCheckingdate && this.queryCheckingdate.length >= 1) {
        this.queryParams.startdate = this.queryCheckingdate[0]
          .toLocaleString()
          .replaceAll('/', '-')
        this.queryParams.enddate = this.queryCheckingdate[1]
          .toLocaleString()
          .replaceAll('/', '-')
      } else {
        delete this.queryParams.startdate
        delete this.queryParams.enddate
      }
      gShuilichongkongtaizhang(this.queryParams).then((response) => {
        let resp = response.rows
        this.actual_list = []
        let ysList = []
        resp.forEach((e) => {
          e.chongkongduan =
            (e.punchingParagraph1 ? 1 : 0) + (e.punchingParagraph2 ? 1 : 0)
          e.chongkongduanchangdu =
            (e.punchingParagraph1 ? e.punchingParagraph1 : 0) +
            (e.punchingParagraph2 ? e.punchingParagraph2 : 0)
          e.holeId = e.roadway + '-' + e.groupNo + '-' + e.boreholeNo
          ysList.push(e)
        })
        this.tActualFlushingList = ysList
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
        dateflushing: null,
        timeBeginFlushing: null,
        timeEndFlushing: null,
        totalTimeFlushing: null,
        waterPressureDuringFlushing: null,
        gasconcbeforeflushing: null,
        gasconcafterflushing: null,
        punchingParagraph1: null,
        punchingParagraph2: null,
        depthtrockinfo: null,
        actualCoalAmountFlushed: null,
        completionRatioFlushedCoal: null,
        personConstruction: null,
        receptiontime: null,
        exceptionPhenomena: null,
        personChecking: null,
        otherPerson: null,
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
      this.queryParams.index = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryRoadWay = []
      this.queryCheckingdate = []
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
      this.title = '添加tActualFlushing'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTActualFlushing(id).then((response) => {
        this.form = response.data
        this.open = true
        this.title = '修改tActualFlushing'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTActualFlushing(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addTActualFlushing(this.form).then((response) => {
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
        .confirm('是否确认删除tActualFlushing编号为"' + ids + '"的数据项？')
        .then(function() {
          return delTActualFlushing(ids)
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
        'tActualFlushing/exportStatisticsBook',
        {},
        `水力冲孔煤量统计台账_${new Date().getTime()}.xlsx`
      )
    }
  }
}
</script>
