<script>
import { getCoalminingWorkingfaceById, listCoalminingworkingface } from '@/api/hhhz/coalMiningWorkingFace'
import { gRoad } from '@/api/hhhz/hangdao'
import { listDirActualBorehole } from '@/api/hhhz/actDirBoreholeInfo'

export default {
  name: 'directionalBorehole',
  data() {
    return {
      currentWorkfaceId: null,
      workfaceList: [],
      roadwayList: [],
      currentRoadwayId: null,
      boreholeList: [],
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
      rules: {},
      columns: [
        {width:80, key: 14, label: '组号', value: 'groupNo', visible: true },
        {width:80, key: 5, label: '孔号', value: 'boreholeNo', visible: true },
        {width:110, key: 9, label: '钻孔位置(m)', value: 'distanceToNavigationPoint', visible: true },
        {width:80, key: 1, label: '冲煤量(t)', value: 'actualCoalAmountFlushed', visible: true },
        {width:110, key: 13, label: '施工异常', value: 'exceptionConstruction', visible: true },
        {width:150, key: 21, label: '终孔日期', value: 'terminalHoleDate', visible: true },
        {width:110, key: 17, label: '用于煤层创建', value: 'isUsedCreateCoal', visible: true },
        {width:80, key: 17, label: '孔深(m)', value: 'boreholeTotalLength', visible: true },
        {width:80, key: 10, label: '钻杆长度', value: 'drillPipeLength', visible: false },
        {width:80, key: 2, label: '设计孔径', value: 'aperture', visible: false },
        {width:80, key: 3, label: '只读：钻孔类型', value: 'boreholeCategory', visible: false },
        {width:80, key: 4, label: '钻孔编号', value: 'boreholeCode', visible: false },
        {width:80, key: 6, label: '钻孔类型ID', value: 'categoryCode', visible: false },
        {width:80, key: 7, label: '导线点方向', value: 'directionToNavigation', visible: false },
        {width:80, key: 8, label: '距巷道中线距离', value: 'distanceToCentrallineTunnel', visible: false },
        {width:80, key: 15, label: '开孔高度', value: 'holeAltitude', visible: false },
        {width:80, key: 16, label: '实钻定向钻ID', value: 'id', visible: false },
        {width:80, key: 18, label: '导线点ID', value: 'navigationPointId', visible: false },
        {width:80, key: 19, label: '巷道ID', value: 'roadwayId', visible: false },
        {width:80, key: 22, label: '在巷道中开孔位置', value: 'topOrBottom', visible: false },
        {width:80, key: 11, label: '目标孔段方位角', value: 'endAzimuth', visible: false },
        {width:80, key: 12, label: '目标孔段倾角', value: 'endTilt', visible: false }
      ],
      showDirBoreholeDialog: false,
      dirBoreholePointList: []
    }
  },
  async created() {
    await this.getWorkfaceList()
    //this.getList()
    this.$message.success('请选择工作面！')
  },
  methods: {
    async getWorkfaceList() {
      let _this = this
      await listCoalminingworkingface({}).then(res => {
        _this.workfaceList = res.rows
      })
    },
    async setCurrentWorkface() {
      let _this = this
      _this.loading = true
      await gRoad({ workfaceId: _this.currentWorkfaceId }).then(res => {
        _this.roadwayList = res.rows
      })
    },
    async setCurrentRoadway() {
      let _this = this
      _this.loading = true
      await listDirActualBorehole({ roadwayId: _this.currentRoadwayId }).then(res => {
        _this.boreholeList = res.rows
        _this.boreholeList.forEach((c) => {
          let len = c.tDirectionalDrillingPointList
          let lenArr = []
          len.forEach(i => {lenArr.push(i.boreholeLength)})
          c['boreholeTotalLength'] = Math.max(...lenArr)
        })
        _this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    showDirBorehole(row){
      this.dirBoreholePointList = row.tDirectionalDrillingPointList
      this.showDirBoreholeDialog = true
    }
  }
}
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="选择工作面" prop="workfaceId" label-width="98px">
        <el-select v-model="currentWorkfaceId" placeholder="请选择工作面" @change="setCurrentWorkface">
          <el-option v-for="item in workfaceList" :key="item.id" :label="item.workfaceName"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选择巷道" prop="roadwayId" label-width="80px">
        <el-select v-model="currentRoadwayId" placeholder="选择巷道" @change="setCurrentRoadway">
          <el-option v-for="item in roadwayList" :key="item.id" :label="item.roadway"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <br>
      <el-form-item label="筛选钻孔类型" prop="roadwayId" label-width="98px">
        <el-select v-model="currentRoadwayId" placeholder="钻孔类型" @change="setCurrentRoadway" style="width: 120px;">
          <el-option v-for="item in roadwayList" :key="item.id" :label="item.roadway"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="组号" prop="roadwayId" label-width="45px">
        <el-select v-model="currentRoadwayId" placeholder="组号" @change="setCurrentRoadway" style="width: 120px;">
          <el-option v-for="item in roadwayList" :key="item.id" :label="item.roadway"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="孔号" prop="roadwayId" label-width="45px">
        <el-input v-model="currentRoadwayId" placeholder="孔号" style="width: 60px;"/>
      </el-form-item>
      <el-form-item label="成孔时间" prop="roadwayId" label-width="80px">
        <el-input v-model="currentRoadwayId" placeholder="孔号" style="width: 60px;"/>
        -
        <el-input v-model="currentRoadwayId" placeholder="孔号" style="width: 60px;"/>
      </el-form-item>
      <el-form-item label="钻孔位置（距巷口）" prop="roadwayId" label-width="150px">
        <el-input v-model="currentRoadwayId" placeholder="孔号" style="width: 60px;"/>
        -
        <el-input v-model="currentRoadwayId" placeholder="孔号" style="width: 60px;"/>
      </el-form-item>
      <el-form-item label="施工异常" prop="roadwayId" label-width="70px">
        <el-switch></el-switch>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">
          删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImport">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>
    <el-table
      v-loading="loading"
      :data="boreholeList"
      @selection-change="handleSelectionChange"
      height="70vh"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <!-- <el-table-column label="${comment}" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index" width="80"/>
      <el-table-column v-for="c in columns" :label="c.label" :key="c.value" :width="c.width" :prop="c.value" v-if="c.visible"
                       align="center"/>
      <el-table-column label="钻孔轨迹" align="center" width="110">
        <template slot-scope="scope">
          <el-button type="text" @click="showDirBorehole(scope.row)">显示钻孔轨迹</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEditCoalPoiType(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDeleteCoalPoiType(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="定向钻孔轨迹点" :visible.sync="showDirBoreholeDialog" width="920px" append-to-body>
      <el-table
        :data="dirBoreholePointList"
        height="40vh"
      >
        <el-table-column label="序号" align="center" type="index" width="80" />
        <el-table-column label="孔深" align="center" prop="boreholeLength" width="80" />
        <el-table-column label="方位角" align="center" prop="azimuthAngle" width="80" />
        <el-table-column label="倾角" align="center" prop="inclinationAngle" width="80" />
        <el-table-column label="投影孔深" align="center" prop="projectionDepth" width="80" />
        <el-table-column label="左/右偏移量" align="center" prop="offsetLR" width="80" />
        <el-table-column label="上/下偏移量" align="center" prop="offsetTD" width="80" />
        <el-table-column label="大地东坐标" align="center" prop="offsetEast" width="80" />
        <el-table-column label="大地北坐标" align="center" prop="offsetNorth" width="80" />
        <el-table-column label="高程" align="center" prop="altitude" width="80" />
        <el-table-column label="煤岩情况" align="center" prop="coalRockSituation" width="80" />
      </el-table>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>
