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
          v-model="queryParams.roadwayId"
          placeholder="请选择钻孔地点"
          @change="getGroupIdList"
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
      <el-form-item label="钻孔组号" prop="zkbh">
        <el-select
          v-model="queryZuankongzuhao"
          collapse-tags
          placeholder="请选择钻孔组号"
          @change="getZuankongkonghaoList"
        >
          <el-option
            v-for="item in zkzhOptions"
            :key="item"
            :label="item"
            :value="item"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="钻孔序号" prop="zkbh">
        <el-select
          v-model="queryZuankongxuhao"
          multiple
          collapse-tags
          placeholder="请选择钻孔序号"
        >
          <el-option
            v-for="item in zkxhOptions"
            :key="item"
            :label="item"
            :value="item"
          >
          </el-option>
        </el-select>
      </el-form-item> -->
      <el-form-item label="钻孔用途" prop="zkyt">
        <el-select v-model="queryZuankongyongtu" placeholder="请选择钻孔用途">
          <el-option
            v-for="item in zkytOptions"
            :key="item.id"
            :label="item.purposeBorehole"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="验收日期" prop="checkingDate">
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
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column label="${comment}" align="center" prop="id" /> -->
      <el-table-column label="序号" align="center" prop="boreholeNo"/>
      <el-table-column label="孔号" align="center" prop="holeId"/>
      <el-table-column label="拔钻日期" align="center" prop="pullDrillDate">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pullDrillDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="设计夹角" align="center" prop="desAngle"/>
      <el-table-column label="实际夹角" align="center" prop="actAngle"/>
      <el-table-column
        label="设计倾角"
        align="center"
        prop="desInclinationAngle"
      />
      <el-table-column
        label="实际倾角"
        align="center"
        prop="actInclinationAngle"
      />
      <el-table-column
        label="设计见煤孔深"
        align="center"
        prop="desCoalDepth"
      />
      <el-table-column
        label="实际见煤孔深"
        align="center"
        prop="actCoalDepth"
      />
      <el-table-column
        label="钻孔见煤情况"
        align="center"
        prop="seeRockCoalCase"
      />
      <el-table-column
        label="临近孔号"
        align="center"
        prop="adjacentBoreholeNo"
      />
      <el-table-column
        label="临近孔距离"
        align="center"
        prop="distanceAdjacentBorehole"
      />
      <el-table-column
        label="距基线高度"
        align="center"
        prop="holeAltitude"
      />
      <el-table-column
        label="导线点"
        align="center"
        prop="navigationPointName"
      />
      <el-table-column
        label="导线点距离"
        align="center"
        prop="distanceToNavigationPoint"
      />
      <el-table-column
        label="距巷道中线距离"
        align="center"
        prop="distanceToCentrallineTunnel"
      />
      <el-table-column
        label="施工人员"
        align="center"
        prop="personConstruction"
      />
      <el-table-column label="验收人员" align="center" prop="personChecking"/>
      <el-table-column
        label="拔钻后孔口瓦斯浓度"
        align="center"
        prop="gasConcentration"
      />
      <el-table-column
        label="拔钻后孔口CO浓度"
        align="center"
        prop="pulledcoconcentration"
      />
      <el-table-column label="封孔方式" align="center" prop="sealingMaterial"/>
      <el-table-column label="冲孔长度" align="center" prop="lengthFlushing"/>
      <el-table-column
        label="基础参数验收人员"
        align="center"
        prop="personChecking"
      />
      <el-table-column
        label="倾角误差"
        align="center"
        prop="errInclinationAngle"
      />
      <el-table-column
        label="冲孔煤量"
        align="center"
        prop="actualCoalAmountFlushed"
      />
      <el-table-column
        label="冲孔煤量完成比例"
        align="center"
        prop="completionRatioFlushedCoal"
      />
      <!-- -------------------------- -->
      <!-- <el-table-column label="roadway" align="center" prop="roadway" />
            <el-table-column label="groupNo" align="center" prop="groupNo" />
            <el-table-column label="boreholeNo" align="center" prop="boreholeNo" />
            <el-table-column label="pullDrillDate" align="center" prop="pullDrillDate" />
            <el-table-column label="desAzimuthAngle" align="center" prop="desAzimuthAngle" />
            <el-table-column label="actazimuthangle" align="center" prop="actazimuthangle" />
            <el-table-column label="desInclinationAngle" align="center" prop="desInclinationAngle" />
            <el-table-column label="actInclinationAngle" align="center" prop="actInclinationAngle" />
            <el-table-column label="actCoalDepth" align="center" prop="actCoalDepth" />
            <el-table-column label="desCoalDepth" align="center" prop="desCoalDepth" />
            <el-table-column label="desBoreholeLength" align="center" prop="desBoreholeLength" />
            <el-table-column label="actBoreholeLength" align="center" prop="actBoreholeLength" />
            <el-table-column label="adjacentBoreholeNo" align="center" prop="adjacentBoreholeNo" />
            <el-table-column label="distanceAdjacentBorehole" align="center" prop="distanceAdjacentBorehole" />
            <el-table-column label="roadway" align="center" prop="roadway" />
            <el-table-column label="holeAltitude" align="center" prop="holeAltitude" />
            <el-table-column label="navigationPointName" align="center" prop="navigationPointName" />
            <el-table-column label="distanceToNavigationPoint" align="center" prop="distanceToNavigationPoint" />
            <el-table-column label="distanceToCentrallineTunnel" align="center" prop="distanceToCentrallineTunnel" />
            <el-table-column label="personConstruction" align="center" prop="personConstruction" />
            <el-table-column label="personChecking" align="center" prop="personChecking" />
            <el-table-column label="gasConcentration" align="center" prop="gasConcentration" />
            <el-table-column label="pulledcoconcentration" align="center" prop="pulledcoconcentration" />
            <el-table-column label="sealingMaterial" align="center" prop="sealingMaterial" />
            <el-table-column label="lengthFlushing" align="center" prop="lengthFlushing" />
            <el-table-column label="errInclinationAngle" align="center" prop="errInclinationAngle" />
            <el-table-column label="actualCoalAmountFlushed" align="center" prop="actualCoalAmountFlushed" />
            <el-table-column label="complentionratioflushedcoal" align="center" prop="complentionratioflushedcoal" /> -->
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
            v-hasPermi="['hhhz:tActualFlushing:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hhhz:tActualFlushing:remove']"
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
import {
  addTActualFlushing,
  delTActualFlushing,
  getTActualFlushing,
  getTGasDrillingStandingBookList,
  updateTActualFlushing
} from '@/api/hhhz/t_actual_flushing'
import { gGroupIdList, gHoleNoList, gRoadWayList, gZuankongyongtuList } from '@/api/hhhz/zuankong'

export default {
  name: 'shuilichongkongtz',
  data() {
    return {
      // 选中孔序号
      queryZuankongxuhao: [],
      // 选中组号
      queryZuankongzuhao: [],
      // 选中钻孔用途
      queryZuankongyongtu: [],
      // 选中时间
      queryCheckingdate: [],
      // 钻孔地点
      zkddOptions: [],
      // 钻孔用途
      zkytOptions: [],
      // 钻孔组号
      zkzhOptions: [],
      // 钻孔编号
      zkxhOptions: [],
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
    this.getZuankongyongtuList()
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
    // 获取钻孔用途
    async getZuankongyongtuList() {
      await gZuankongyongtuList({}).then((response) => {
        console.log('gZuankongyongtuList', response)
        this.zkytOptions = response.rows
      })
    },
    // 获取钻孔组号
    async getGroupIdList(rwId) {
      // 清空
      this.initZkzh()
      await gGroupIdList({ roadwayId: rwId }).then((response) => {
        console.log('gGroupIdList', response)
        this.zkzhOptions = response.data
      })
    },
    // 获取钻孔孔号
    async getZuankongkonghaoList(zkzh) {
      console.log('groupNo', zkzh)
      this.initZkxh()
      if (zkzh) {
        await gHoleNoList({
          roadwayId: this.queryParams.roadwayId,
          groupNo: zkzh
        }).then((response) => {
          console.log('gHoleNoList', response)
          this.zkxhOptions = response
        })
      } else {
        this.initZkxh()
      }
    },
    // 初始化操作会级联清空下层
    // 初始化钻孔地点
    initZkdd() {
      this.initZkzh()
      delete delete this.queryParams.roadwayId
    },
    // 初始化钻孔组号
    initZkzh() {
      this.initZkxh()
      this.zkzhOptions = []
      this.queryZuankongzuhao = []
    },
    // 初始化钻孔序号
    initZkxh() {
      this.zkxhOptions = []
      this.queryZuankongxuhao = []
    },
    // 初始化所有
    initAll() {
      this.initZkdd()
      this.queryZuankongyongtu = []
      this.queryCheckingdate = []
    },
    /** 查询tActualFlushing列表 */
    getList() {
      this.loading = true
      // 钻孔组号
      console.log('组号:', this.queryZuankongzuhao)
      if (this.queryZuankongzuhao) {
        this.queryParams.groupNo = this.queryZuankongzuhao
      } else {
        delete this.queryParams.groupNo
      }
      // 钻孔序号
      console.log('序号:', this.queryZuankongxuhao)
      if (this.queryZuankongxuhao && this.queryZuankongxuhao.length >= 1) {
        this.queryParams.boreholeNo = JSON.stringify(this.queryZuankongxuhao)
          .replace('[', '')
          .replace(']', '')
      } else {
        delete this.queryParams.boreholeNo
      }
      // 钻孔用途
      console.log('钻孔用途:', this.queryZuankongyongtu)
      if (this.queryZuankongyongtu && this.queryZuankongyongtu.length >= 1) {
        this.queryParams.purposeBoreholeId = JSON.stringify(
          this.queryZuankongyongtu
        )
          .replace('[', '')
          .replace(']', '')
      } else {
        delete this.queryParams.purposeBoreholeId
      }
      // 时间
      console.log('时间', this.queryCheckingdate)
      if (this.queryCheckingdate && this.queryCheckingdate.length >= 1) {
        this.queryParams.startdate = this.queryCheckingdate[0]
          .toLocaleString()
          .replaceAll('/', '-')
      } else {
        delete this.queryParams.startdate
      }
      console.log('queryParams', this.queryParams)
      getTGasDrillingStandingBookList(this.queryParams).then((response) => {
        let resp = response.rows
        this.actual_list = []
        let ysList = []
        resp.forEach((e) => {
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
      this.queryParams = {
        pageNum: 1,
        pageSize: 10
      }
      this.initAll()
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
        .confirm('是否确认删除编号为"' + ids + '"的数据项？')
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
        'tGasDrillingStandingBook/export',
        {},
        `瓦斯抽采钻孔施工台账_${new Date().getTime()}.xlsx`
      )
    }
  }
}
</script>
