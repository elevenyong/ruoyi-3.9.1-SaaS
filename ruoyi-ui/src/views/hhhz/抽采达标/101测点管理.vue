<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="巷道" prop="roadway">
        <!-- @keyup.enter.native="handleQuery" -->
        <el-select v-model="queryParams.roadwayId" placeholder="请选择巷道">
          <el-option v-for="item in roadwayList" :key="item.id" :label="item.roadway" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="抽采单元" prop="drillUnit">
        <el-select v-model="queryParams.drainageUnitId" placeholder="请选择抽采单元">
          <el-option v-for="item in drainageUnitList" :key="item.id" :label="item.unitName" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="管路类型" prop="drillUnit">
        <el-select v-model="queryParams.pipeTypeId" placeholder="请选择管路类型">
          <el-option v-for="item in pipeTypeList" :key="item.id" :label="item.pipeType" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <!--        <el-button icon="el-icon-refresh" size="mini" @click="() => { console.log('导出') }">导出</el-button>-->
        <el-button style="margin:0 5px" type="primary" size="mini"
                   @click="()=>{this.pipeTypeShow = true;}">
          管理管道类型
        </el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="stationInfoList">
      <el-table-column label="编号" align="center" prop="id"/>
      <el-table-column label="巷道" align="center" prop="roadwayName"/>
      <el-table-column label="抽采单元" align="center" prop="drainageUnitName"/>
      <el-table-column label="测点安装位置" align="center" prop="measurePointLocal"/>
      <el-table-column label="管路类型" align="center" prop="pipeTypeName"/>
      <el-table-column label="位置顺序" align="center" prop="serial"/>
      <el-table-column label="测点编码" align="center" prop="measurePointCode"/>
      <el-table-column label="系统编码" align="center" prop="sysCode"/>
      <el-table-column label="分站编码" align="center" prop="substationCode"/>
      <el-table-column label="传感器类型名称" align="center" prop="sensorTypeName"/>
      <!--<el-table-column label="测点数据类型" align="center" prop="measurePointValueType"/>
      <el-table-column label="测点数值单位" align="center" prop="measurePointValueUnit"/>
      <el-table-column label="高量程" align="center" prop="hightRange"/>
      <el-table-column label="低量程" align="center" prop="lowRange"/>
            <el-table-column label="上限报警门限" align="center" prop="upWarn"/>
            <el-table-column label="上限解报门限" align="center" prop="upCancelWarn"/>
            <el-table-column label="下限报警门限" align="center" prop="lowWarn"/>
            <el-table-column label="下限解报门限" align="center" prop="lowCancelWarn"/>
            <el-table-column label="上限断电门限" align="center" prop="upOutage"/>
            <el-table-column label="上限复电门限" align="center" prop="upOnpower"/>
            <el-table-column label="下限断电门限" align="center" prop="lowOutage"/>
            <el-table-column label="下限复电门限" align="center" prop="lowOnpower"/>
      <el-table-column label="开描述" align="center" prop="onDescribe"/>
      <el-table-column label="停描述" align="center" prop="stopDescribe"/>
      <el-table-column label="X坐标" align="center" prop="X"/>
      <el-table-column label="Y坐标" align="center" prop="Y"/>
      <el-table-column label="Z坐标" align="center" prop="Z"/>-->
      <el-table-column label="传感器关联关系" align="center" prop="sensorRelevance"/>
      <el-table-column label="数据时间" align="center" prop="time"/>
      <el-table-column label="是否是管路" align="center" prop="piping">
        <template slot-scope="scope">
          <div v-if="scope.row.piping === true" class="true-color">
            是
          </div>
          <div v-else-if="scope.row.piping === false" class="false-color">
            否
          </div>
          <div v-else class="noFinish-color">
            -
          </div>
        </template>
      </el-table-column>
      <el-table-column label="是否为最远端" align="center" prop="farthestEnd">
        <template slot-scope="scope">
          <div v-if="scope.row.farthestEnd === true" class="true-color">
            是
          </div>
          <div v-else-if="scope.row.farthestEnd === false" class="false-color">
            否
          </div>
          <div v-else class="default-color">
            -
          </div>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['system:actual_borehole:edit']">修改
          </el-button>
          <!--          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                               v-hasPermi="['system:actual_borehole:remove']">删除
                    </el-button>-->
        </template>
      </el-table-column>
    </el-table>
    <!--  显示管道类型信息  -->
    <el-dialog :title="pipeTypeTitle" :visible.sync="pipeTypeShow" width="25%">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="()=>{this.aoePipeTypeShow = true;this.aoePipeTypeTitle = '新增管道类型';}"
          >新增
          </el-button>
        </el-col>
      </el-row>
      <el-table v-loading="false" :data="pipeTypeList">
        <el-table-column label="id" align="center" prop="id" width="50px"/>
        <el-table-column label="类型名称" align="center" prop="pipeType"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleEditPipeType(scope.row)"
            >修改
            </el-button>
            <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDeletePipeType(scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="()=>{this.pipeTypeShow = false;}">取 消</el-button>
      </div>
    </el-dialog>
    <!--  新增|编辑管道类型信息  -->
    <el-dialog :title="aoePipeTypeTitle" :visible.sync="aoePipeTypeShow" width="25%">
      <el-form ref="coalTypeDialog" :model="aoePipeType" label-width="120px">
        <el-form-item label="管道类型名称" prop="type">
          <el-input v-model="aoePipeType.pipeType" placeholder="请输入管道类型名称"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPipeTypeForm">确 定</el-button>
        <el-button @click="()=>{this.aoePipeTypeShow = false;this.aoePipeType = {};}">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 添加或修改实钻基础信息t_actual_borehole对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" label-width="140px">
        <el-form-item label="测点安装位置" prop="groupNo">
          <el-input v-model="form.measurePointLocal" disabled/>
        </el-form-item>
        <el-form-item label="绑定巷道" prop="roadway">
          <!-- @keyup.enter.native="handleQuery" -->
          <el-select v-model="form.roadwayId" placeholder="请选择巷道">
            <el-option v-for="item in roadwayList" :key="item.id" :label="item.roadway" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="绑定抽采单元" prop="drillUnit">
          <el-select v-model="form.drainageUnitId" placeholder="请选择抽采单元">
            <el-option v-for="item in drainageUnitList" :key="item.id" :label="item.unitName" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="序号" prop="serial">
          <el-input v-model="form.serial"/>
        </el-form-item>
        <el-form-item label="绑定管道类型" prop="drillUnit">
          <el-select v-model="form.pipeTypeId" placeholder="请选择管道类型">
            <el-option v-for="item in pipeTypeList" :key="item.id" :label="item.pipeType" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否是管路" prop="drillUnit">
          <el-radio-group v-model="form.piping">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否是最远端" prop="drillUnit">
          <el-radio-group v-model="form.farthestEnd">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
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
import { listStationInfo, updateStationInfo } from '@/api/hhhz/station_info'
import { gRoad } from '@/api/hhhz/hangdao'
import { addPipeType, delPipeType, listPipeType, updatePipeType } from '@/api/hhhz/pipe_type'
import { listDrainageUnit } from '@/api/hhhz/drainage_unit'

export default {
  name: 'StationInfo',
  data() {
    return {
      drainageUnitList: [],
      pipeTypeList: [],
      roadwayList: [],
      stationInfo: {},
      pipeTypeShow: false,
      pipeTypeTitle: '管道类型',
      aoePipeTypeShow: false,
      aoePipeTypeTitle: '',
      aoePipeType: { id: null, pipeType: null },
      // 遮罩层
      loading: false,
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
      // 测点数据
      stationInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {},
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  async created() {
    await this.getDrainageUnitList()
    await this.getPipeTypeList()
    await this.getRoadwayList()
    await this.getStationInfoList()
  },
  methods: {
    /* 抽采单元 */
    async getDrainageUnitList() {
      await listDrainageUnit({}).then(response => {
        this.drainageUnitList = response.rows
        this.loading = false
      })
    },
    /** 巷道 */
    async getRoadwayList() {
      await gRoad({}).then(response => {
        this.roadwayList = response.rows
        this.loading = false
      })
    },
    /** 管道类型 */
    async getPipeTypeList() {
      await listPipeType({}).then(response => {
        this.pipeTypeList = response.rows
        this.loading = false
      })
    },
    /** 测点信息 */
    async getStationInfoList() {
      this.loading = true
      let _this = this
      await listStationInfo(this.queryParams).then(response => {
        let rowsData = response.rows
        for (let i = 0; i < rowsData.length; i++) {
          rowsData[i].roadwayName = '-'
          rowsData[i].drainageUnitName = '-'
          for (let j = 0; j < _this.roadwayList.length; j++) {
            if (rowsData[i].roadwayId === _this.roadwayList[j].id) {
              rowsData[i].roadwayName = _this.roadwayList[j].roadway
              break
            }
          }
          for (let j = 0; j < _this.drainageUnitList.length; j++) {
            if (rowsData[i].drainageUnitId === _this.drainageUnitList[j].id) {
              rowsData[i].drainageUnitName = _this.drainageUnitList[j].unitName
              break
            }
          }
          for (let j = 0; j < _this.pipeTypeList.length; j++) {
            if (rowsData[i].pipeTypeId === _this.pipeTypeList[j].id) {
              rowsData[i].pipeTypeName = _this.pipeTypeList[j].pipeType
              break
            }
          }
        }
        _this.stationInfoList = rowsData
        _this.loading = false
      })
    },
    handleEditPipeType(pipeType) {
      this.aoePipeTypeShow = true
      this.aoePipeTypeTitle = '修改管道类型'
      this.aoePipeType.id = pipeType.id
      this.aoePipeType.pipeType = pipeType.pipeType
    },
    // 提交管路类型信息
    submitPipeTypeForm() {
      let _this = this
      console.log(this.aoePipeType)
      if (this.aoePipeType.id != null) {
        updatePipeType(this.aoePipeType).then(response => {
          _this.$modal.msgSuccess('修改成功')
          _this.aoePipeTypeShow = false
          _this.aoePipeType = {}
          _this.getPipeTypeList()
        })
      } else {
        addPipeType(this.aoePipeType).then(response => {
          this.$modal.msgSuccess('新增成功')
          _this.aoePipeTypeShow = false
          _this.aoePipeType = {}
          _this.getPipeTypeList()
        })
      }
    },
    // 删除煤层点
    handleDeletePipeType(pipeType) {
      let _this = this
      this.$modal.confirm('是否确认删除编号为"' + pipeType.id + '"的管道类型？').then(function() {
        return delPipeType({ id: pipeType.id })
      }).then((response) => {
        _this.getPipeTypeList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {}
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      //this.queryParams.index = 1
      /* this.queryParams.pageNum = 1; */
      this.getStationInfoList()
    },
    /** 重置按钮操作 */
    async resetQuery() {
      this.resetForm('queryForm')
      this.queryParams = {}
      await this.getDrainageUnitList()
      await this.getPipeTypeList()
      await this.getRoadwayList()
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
      this.title = '添加实钻基础信息t_actual_borehole'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.open = true
      this.title = '修改测点信息'
      let selectRow = JSON.parse(JSON.stringify(row))
      selectRow.piping = selectRow.piping ? 1 : 0
      selectRow.farthestEnd = selectRow.farthestEnd ? 1 : 0
      this.form = selectRow
    },
    /** 提交按钮 */
    submitForm() {
      updateStationInfo(this.form).then(response => {
        this.$modal.msgSuccess('修改成功')
        this.open = false
        this.getStationInfoList()
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除实钻基础信息t_actual_borehole编号为"' + ids + '"的数据项？').then(function() {
        return delActual_borehole(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      let _this = this
      this.download('tActualBorehole/export',
          _this.actual_boreholeList
          , `actual_borehole_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
<style scoped>
.default-color {
  background-color: #1c4d84;
  text-align: center;
}

.false-color {
  background-color: #ee6f6f;
  text-align: center;
}

.true-color {
  background-color: #74bedf;
  text-align: center;
}
</style>
