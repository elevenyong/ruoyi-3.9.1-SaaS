<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="98px">
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
      <el-form-item label="" prop="roadwayId" label-width="80px">
        <el-popover placement="right" trigger="click">
          <el-form>
            <el-form-item label="钻孔类型" prop="boreholeType" label-width="70px">
              <el-select v-model="boreholeFilter.boreholeType" multiple collapse-tags placeholder="钻孔类型"
                         @change="setCurrentBoreholeType" style="width: 200px;">
                <el-option v-for="item in boreholeCategoryList" :key="item.categoryCode"
                           :label="item.boreholeCategory+'('+item.numCount+')'"
                           :value="item.categoryCode">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="组号" prop="groupNo" label-width="70px">
              <el-input v-model="boreholeFilter.groupNo" placeholder="组号" style="width: 200px;"/>
            </el-form-item>
            <el-form-item label="孔号" prop="boreholeNo" label-width="70px">
              <el-input v-model="boreholeFilter.boreholeNo" placeholder="孔号" style="width: 200px;"/>
            </el-form-item>
            <el-form-item label="成孔时间" prop="timeRange" label-width="70px">
              <el-date-picker
                style="width: 220px;"
                v-model="boreholeFilter.timeRange"
                type="daterange"
                value-format="timestamp"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="钻孔倾角" prop="timeRange" label-width="70px">
              <el-input type="number" v-model="boreholeFilter.minInclinationAngle" placeholder="最小"
                        style="width: 90px;"/>
              -
              <el-input type="number" v-model="boreholeFilter.maxInclinationAngle" placeholder="最大"
                        style="width: 90px;"/>
            </el-form-item>
            <el-form-item label="钻孔位置" prop="position" label-width="70px">
              <el-input type="number" v-model="boreholeFilter.startDistance" placeholder="最近" style="width: 90px;"/>
              -
              <el-input type="number" v-model="boreholeFilter.endDistance" placeholder="最远" style="width: 90px;"/>
            </el-form-item>
            <el-form-item label="施工异常" prop="boreholeException" label-width="70px">
              <el-select v-model="boreholeFilter.boreholeException" placeholder="" clearable
                         @clear="()=>{boreholeFilter.boreholeException = null}" style="width: 200px;">
                <el-option v-for="item in boreholeExceptionSelect" :key="item.key" :label="item.label"
                           :value="item.key">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleFilter">筛选</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetFilter">重置</el-button>
            </el-form-item>
          </el-form>
          <el-button type="primary" plain slot="reference" icon="el-icon-search">按条件筛选</el-button>
        </el-popover>
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
      <el-col :span="1.5">
        <el-popover
          placement="right"
          trigger="click">
          <div>
            钻孔数：{{ selectionCountInfo.boreholeNumCount }}
            <br>
            钻孔总长度：{{ Number(selectionCountInfo.totalLength.toFixed(2)) }}m
            <br>
            煤孔总长度：{{ Number(selectionCountInfo.mLength.toFixed(2)) }}m
            <br>
            孔均长度：{{ Number(selectionCountInfo.averageLength.toFixed(2)) }}m
          </div>
          <el-button type="primary" plain icon="el-icon-pie-chart" size="mini" slot="reference">工程量统计
          </el-button>
        </el-popover>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>
    <el-table
      v-loading="loading"
      :data="filterBoreholeList"
      @selection-change="handleSelectionChange"
      height="70vh"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <!-- <el-table-column label="${comment}" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index" width="80"/>
      <el-table-column v-for="c in columns" :label="c.label" :key="c.value" :prop="c.value" v-if="c.visible"
                       :width="c.width" align="center"/>
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div>
      钻孔数：{{ countInfo.boreholeNumCount }};
      钻孔总长度：{{ Number(countInfo.totalLength.toFixed(2)) }}m;
      煤孔总长度：{{ Number(countInfo.mLength.toFixed(2)) }}m;
      孔均长度：{{ Number(countInfo.averageLength.toFixed(2)) }}m
    </div>
    <el-dialog title="钻孔信息" :visible.sync="showBoreholeDialog" width="50%" append-to-body>
      <Borehole :boreholeInfo="aoeBoreholeInfo" :currentRoad="currentRoadway"
                :boreholeCategoryList="boreholeCategoryList" :currentWorkfaceId="currentWorkfaceId"></Borehole>
    </el-dialog>
  </div>
</template>

<script>
import { getCoalminingWorkingfaceById, listCoalminingworkingface } from '@/api/hhhz/coalMiningWorkingFace'
import { gRoad } from '@/api/hhhz/hangdao'
import { listActualBorehole } from '@/api/hhhz/actBoreholeInfo'
import { listBoreholeCategory } from '@/api/hhhz/boreholeCategory'
import Borehole from './Borehole.vue'
import Roadway from './Roadway.vue'

export default {
  name: 'BoreholeManage',
  components: { Roadway, Borehole },
  data() {
    return {
      aoeBoreholeInfo: {},
      showBoreholeDialog: false,
      currentRoadway: null,
      selectionCountInfo: {
        boreholeNumCount: 0,
        totalLength: 0,
        mLength: 0,
        averageLength: 0
      },
      countInfo: {
        boreholeNumCount: 0,
        totalLength: 0,
        mLength: 0,
        averageLength: 0
      },
      filterBoreholeList: [],
      boreholeCategoryList: [],
      boreholeFilter: {
        boreholeType: [],
        boreholeNo: null,
        groupNo: null,
        timeRange: [],
        startDistance: null,
        endDistance: null,
        boreholeException: null,
        minInclinationAngle: null,
        maxInclinationAngle: null
      },
      boreholeExceptionSelect: [
        { key: true, label: '异常' },
        { key: false, label: '无异常' }
      ],
      showFaultPointsDialog: false,
      faultPointsList: [],
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
        { width: 120, key: 15, label: '钻孔类型', value: 'boreholeCategory', visible: true },
        { width: 80, key: 68, label: '组号', value: 'groupNo', visible: true },
        { width: 80, key: 18, label: '孔号', value: 'boreholeNo', visible: true },
        { width: 80, key: 88, label: '倾角', value: 'inclinationAngle', visible: true },
        { width: 80, key: 8, label: '方位角', value: 'azimuthAngle', visible: true },
        { width: 80, key: 33, label: '岩1', value: 'r1', visible: true },
        { width: 80, key: 32, label: '煤1', value: 'm1', visible: true },
        { width: 80, key: 37, label: '岩2', value: 'r2', visible: true },
        { width: 80, key: 36, label: '煤2', value: 'm2', visible: true },
        { width: 80, key: 41, label: '岩3', value: 'r3', visible: true },
        { width: 80, key: 40, label: '煤3', value: 'm3', visible: true },
        { width: 80, key: 44, label: '岩4(过煤长度)', value: 'r4', visible: true },
        /* { width: 80, key: 33, label: '第一见煤', value: 'depth1stCoalSeamStart', visible: true },
        { width: 80, key: 32, label: '第一止煤', value: 'depth1stCoalSeamEnd', visible: true },
        { width: 80, key: 37, label: '第二见煤', value: 'depth2stCoalSeamStart', visible: true },
        { width: 80, key: 36, label: '第二止煤', value: 'depth2stCoalSeamEnd', visible: true },
        { width: 80, key: 41, label: '第三见煤', value: 'depth3stCoalSeamStart', visible: true },
        { width: 80, key: 40, label: '第三止煤', value: 'depth3stCoalSeamEnd', visible: true },
        { width: 80, key: 44, label: '第四过煤', value: 'depth4stRockSeamEnd', visible: true }, */
        { width: 80, key: 17, label: '实际孔深', value: 'boreholeLength', visible: true },
        { width: 80, key: 49, label: '距导线点距离', value: 'distanceToNavigationPoint', visible: true },
        { width: 160, key: 126, label: '终孔日期', value: 'terminalHoleDate', visible: true },
        { width: 80, key: 63, label: '施工异常情况', value: 'exceptionConstruction', visible: true },
        { width: 80, key: 4, label: '实际冲出煤量', value: 'actualCoalAmountFlushed', visible: true },
        { width: 80, key: 7, label: '施工孔径', value: 'aperture', visible: true },
        { width: 80, key: 66, label: '抽采半径', value: 'extractionRadius', visible: true },
        { width: 80, key: 0, label: '验收情况', value: 'acceptanceStatus', visible: false },
        { width: 80, key: 1, label: '实际囊袋注浆量', value: 'actualAmountBagGrouting', visible: false },
        { width: 80, key: 2, label: '实际中间段注浆量', value: 'actualAmountGapGrouting', visible: false },
        { width: 80, key: 3, label: '囊袋注浆压力', value: 'actualBagPressure', visible: false },
        { width: 80, key: 5, label: '临近孔号', value: 'adjacentBoreholeNo', visible: false },
        { width: 80, key: 6, label: '夹角', value: 'angle', visible: false },
        { width: 80, key: 9, label: '绑定第一段煤层属性', value: 'bindAttributesCoal1st', visible: false },
        { width: 80, key: 10, label: '绑定第二段煤层属性', value: 'bindAttributesCoal2st', visible: false },
        { width: 80, key: 11, label: '绑定第三段煤层属性', value: 'bindAttributesCoal3st', visible: false },
        { width: 80, key: 12, label: '绑定第一段煤层地层ID', value: 'bindCoal1st', visible: false },
        { width: 80, key: 13, label: '绑定第二段煤层地层ID', value: 'bindCoal2st', visible: false },
        { width: 80, key: 14, label: '绑定第三段煤层地层ID', value: 'bindCoal3st', visible: false },
        { width: 80, key: 16, label: '钻孔编号', value: 'boreholeCode', visible: false },
        { width: 80, key: 19, label: '钻孔类型', value: 'categoryCode', visible: false },
        { width: 80, key: 20, label: '钻孔负责人', value: 'chargehand', visible: false },
        { width: 80, key: 21, label: '冲孔负责人', value: 'chargehandFlushing', visible: false },
        { width: 80, key: 22, label: '验收夹角', value: 'checkAngle', visible: false },
        { width: 80, key: 23, label: '验收孔径', value: 'checkAperture', visible: false },
        { width: 80, key: 24, label: '验收方位角', value: 'checkAzimuthAngle', visible: false },
        { width: 80, key: 25, label: '验收孔深', value: 'checkBoreholeLength', visible: false },
        { width: 80, key: 26, label: '验收开孔高度', value: 'checkHoleAltitude', visible: false },
        { width: 80, key: 27, label: '验收孔间距', value: 'checkHoleSpace', visible: false },
        { width: 80, key: 28, label: '验收倾角', value: 'checkInclinationAngle', visible: false },
        { width: 80, key: 29, label: '验收列间距', value: 'checkLineSpace', visible: false },
        { width: 80, key: 30, label: '验收日期', value: 'checkingDate', visible: false },
        { width: 80, key: 31, label: '冲煤完成比例', value: 'completionRatioFlushedCoal', visible: false },
        { width: 80, key: 34, label: '弃用：第一岩止', value: 'depth1stRockSeamEnd', visible: false },
        { width: 80, key: 35, label: '弃用：第一岩起', value: 'depth1stRockSeamStart', visible: false },
        { width: 80, key: 38, label: '弃用：第二岩止', value: 'depth2stRockSeamEnd', visible: false },
        { width: 80, key: 39, label: '弃用：第二岩起', value: 'depth2stRockSeamStart', visible: false },
        { width: 80, key: 42, label: '弃用：第三岩止', value: 'depth3stRockSeamEnd', visible: false },
        { width: 80, key: 43, label: '弃用：第三岩起', value: 'depth3stRockSeamStart', visible: false },
        { width: 80, key: 45, label: '弃用：第四岩起', value: 'depth4stRockSeamStart', visible: false },
        { width: 80, key: 46, label: '导线点方向', value: 'directionToNavigation', visible: false },
        { width: 80, key: 47, label: '与临近孔距离', value: 'distanceAdjacentBorehole', visible: false },
        { width: 80, key: 48, label: '距巷道中线距离', value: 'distanceToCentrallineTunnel', visible: false },
        { width: 80, key: 50, label: '下管负责人', value: 'downTubeChargehand', visible: false },
        { width: 80, key: 51, label: '下管时间止', value: 'downTubeTimeEnd', visible: false },
        { width: 80, key: 52, label: '下管时间起', value: 'downTubeTimeStart', visible: false },
        { width: 80, key: 53, label: '钻孔日期', value: 'drillingDate', visible: false },
        { width: 80, key: 54, label: '钻孔位置', value: 'drillingLocation', visible: false },
        { width: 80, key: 55, label: '钻机名称', value: 'drillingName', visible: false },
        { width: 80, key: 56, label: '钻孔起点位置X', value: 'drillingStartX', visible: false },
        { width: 80, key: 57, label: '钻孔起点位置Y', value: 'drillingStartY', visible: false },
        { width: 80, key: 58, label: '钻孔起点位置Z', value: 'drillingStartZ', visible: false },
        { width: 80, key: 59, label: '钻机信息ID', value: 'drillinginfoId', visible: false },
        { width: 80, key: 60, label: '防突科', value: 'eliminate', visible: false },
        { width: 80, key: 61, label: '防突矿长', value: 'eliminateBarmaster', visible: false },
        { width: 80, key: 62, label: '防突副总', value: 'eliminateDeputyGeneralManager', visible: false },
        { width: 80, key: 64, label: '打钻过程中异常现象', value: 'exceptionDuringDrilling', visible: false },
        { width: 80, key: 65, label: '冲孔过程中异常标注', value: 'exceptionPhenomena', visible: false },
        { width: 80, key: 67, label: '原冲孔验收单文件路径', value: 'file', visible: false },
        { width: 80, key: 69, label: '注浆水泥量', value: 'groutingCements', visible: false },
        { width: 80, key: 70, label: '注浆负责人', value: 'groutingChargehand', visible: false },
        { width: 80, key: 71, label: '注浆日期', value: 'groutingDate', visible: false },
        { width: 80, key: 72, label: '注浆结束时间', value: 'groutingEndTime', visible: false },
        { width: 80, key: 73, label: '注浆异常现象标注', value: 'groutingExceptionLabel', visible: false },
        { width: 80, key: 74, label: '注浆人员', value: 'groutingPerson', visible: false },
        { width: 80, key: 75, label: '注浆施工人员', value: 'groutingPersonConstruction', visible: false },
        { width: 80, key: 76, label: '注浆班次', value: 'groutingShift', visible: false },
        { width: 80, key: 77, label: '注浆班次ID', value: 'groutingShiftId', visible: false },
        { width: 80, key: 78, label: '注浆开始时间', value: 'groutingStartTime', visible: false },
        { width: 80, key: 79, label: '注浆水量', value: 'groutingWaters', visible: false },
        { width: 80, key: 80, label: '开孔高度', value: 'holeAltitude', visible: false },
        { width: 80, key: 81, label: '封孔规格', value: 'holeSealingSpecification', visible: false },
        { width: 80, key: 82, label: '施工孔间距', value: 'holeSpace', visible: false },
        { width: 80, key: 83, label: '封孔负责人', value: 'holesealChargehand', visible: false },
        { width: 80, key: 84, label: '封孔时间', value: 'holesealDate', visible: false },
        { width: 80, key: 85, label: '封孔深度', value: 'holesealDepth', visible: false },
        { width: 80, key: 86, label: '封孔段长度', value: 'holesealLength', visible: false },
        { width: 80, key: 87, label: '实钻钻孔系统内ID', value: 'id', visible: false },
        { width: 80, key: 89, label: '实际中间段注浆压力', value: 'interludeGroutingPressure', visible: false },
        { width: 80, key: 90, label: '是否用于煤层创建', value: 'isUsedCreateCoal', visible: false },
        { width: 80, key: 91, label: '第一煤段是否用于煤层创建', value: 'isUsedCreateCoal1st', visible: false },
        { width: 80, key: 92, label: '第二煤段是否用于煤层创建', value: 'isUsedCreateCoal2st', visible: false },
        { width: 80, key: 93, label: '第三煤段是否用于煤层创建', value: 'isUsedCreateCoal3st', visible: false },
        { width: 80, key: 94, label: '施工列间距', value: 'lineSpace', visible: false },
        { width: 80, key: 95, label: '班长', value: 'monitor', visible: false },
        { width: 80, key: 96, label: '导线点ID', value: 'navigationPointId', visible: false },
        { width: 80, key: 97, label: '导线点名称', value: 'navigationPointName', visible: false },
        { width: 80, key: 98, label: '跟班人员', value: 'otherPerson', visible: false },
        { width: 80, key: 99, label: '现场验收人员', value: 'personCheckingSite', visible: false },
        { width: 80, key: 100, label: '注浆现场验收人员', value: 'personCheckingSiteGrouting', visible: false },
        { width: 80, key: 101, label: '视频验收人员', value: 'personCheckingVideo', visible: false },
        { width: 80, key: 102, label: '注浆视频验收人员', value: 'personCheckingVideoGrouting', visible: false },
        { width: 80, key: 103, label: '钻孔施工人员', value: 'personConstruction', visible: false },
        { width: 80, key: 104, label: '冲孔施工人员', value: 'personConstructionFlushing', visible: false },
        { width: 80, key: 105, label: '拔钻日期', value: 'pullDrillDate', visible: false },
        { width: 80, key: 106, label: '冲孔段1', value: 'punchingParagraph1', visible: false },
        { width: 80, key: 107, label: '冲孔段2', value: 'punchingParagraph2', visible: false },
        { width: 80, key: 108, label: '钻孔用途', value: 'purposeBorehole', visible: false },
        { width: 80, key: 109, label: '钻孔用途id', value: 'purposeBoreholeId', visible: false },
        { width: 80, key: 110, label: '备注', value: 'remark', visible: false },
        { width: 80, key: 111, label: '冲孔备注', value: 'remarkFlushing', visible: false },
        { width: 80, key: 112, label: '所属巷道名称', value: 'roadway', visible: false },
        { width: 80, key: 113, label: '所属巷道ID', value: 'roadwayId', visible: false },
        { width: 80, key: 114, label: '所属区段ID', value: 'roadwayZoneId', visible: false },
        { width: 80, key: 115, label: '封孔直径', value: 'sealingHoleDiameter', visible: false },
        { width: 80, key: 116, label: '封孔材料', value: 'sealingMaterial', visible: false },
        { width: 80, key: 117, label: '见岩见煤情况', value: 'seeRockCoalCase', visible: false },
        { width: 80, key: 118, label: '班次ID', value: 'shiftId', visible: false },
        { width: 80, key: 119, label: '班次名称', value: 'shiftInfo', visible: false },
        { width: 80, key: 120, label: '封孔花管长度', value: 'sievePipelineLength', visible: false },
        { width: 80, key: 121, label: '筛管长度', value: 'sieveTubeLength', visible: false },
        { width: 80, key: 122, label: '钻孔标识', value: 'sign', visible: false },
        { width: 80, key: 123, label: '封孔实管长度', value: 'solidPipelineLength', visible: false },
        { width: 80, key: 124, label: '钻孔喷孔情况', value: 'sprayHoleCondition', visible: false },
        { width: 80, key: 125, label: '定向钻孔点位', value: 'tactDirectionalDrillingPoints', visible: false },
        { width: 80, key: 127, label: '开始冲孔时间', value: 'timeBeginFlushing', visible: false },
        { width: 80, key: 128, label: '冲孔结束时间', value: 'timeEndFlushing', visible: false },
        { width: 80, key: 129, label: '累计冲孔时间', value: 'totalTimeFlushing', visible: false },
        { width: 80, key: 130, label: '下管长度', value: 'tubeLength', visible: false },
        { width: 80, key: 131, label: '有效进尺', value: 'validFootage', visible: false },
        { width: 80, key: 132, label: '区长', value: 'warden', visible: false },
        { width: 80, key: 133, label: '冲孔压力', value: 'waterPressureDuringFlushing', visible: false },
        { width: 80, key: 134, label: '区段名称', value: 'zoneName', visible: false }
      ]
    }
  },
  async created() {
    await this.getWorkfaceList()
    await this.getBoreholeCategoryList()
    //this.getList()
    this.$message.success('请选择工作面！')
  },
  methods: {
    handleAdd() {
      this.showBoreholeDialog = true
    },
    handleEdit(row) {
      this.showBoreholeDialog = true
      this.aoeBoreholeInfo = row
    },
    handleUpdate() {
      this.showBoreholeDialog = true
    },
    handleDelete() {

    },
    handleExport() {

    },
    handleImport() {

    },
    resetFilter() {
      this.boreholeFilter = {
        boreholeType: [],
        boreholeNo: null,
        groupNo: null,
        timeRange: [],
        startDistance: null,
        endDistance: null,
        boreholeException: null,
        minInclinationAngle: null,
        maxInclinationAngle: null
      }
      this.handleFilter()
      //this.filterBoreholeList = this.boreholeList
    },
    handleFilter() {
      let _this = this
      this.countInfo = {
        boreholeNumCount: 0,
        totalLength: 0,
        mLength: 0,
        averageLength: 0
      }
      _this.filterBoreholeList = []
      console.log(_this.boreholeFilter)
      let tArray = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      for (let i = 0; i < _this.boreholeList.length; i++) {
        let borehole = _this.boreholeList[i]
        tArray[borehole.categoryCode]++

        if (_this.boreholeFilter.boreholeType !== null && _this.boreholeFilter.boreholeType.length > 0) {
          // 类型筛选
          if (_this.boreholeFilter.boreholeType.indexOf(borehole.categoryCode) === -1) {
            continue
          }
        }
        if (_this.boreholeFilter.groupNo !== null && _this.boreholeFilter.groupNo !== '') {
          // 筛选组号
          if (borehole.groupNo.search(_this.boreholeFilter.groupNo) === -1) {
            continue
          }
        }
        if (_this.boreholeFilter.boreholeNo !== null && _this.boreholeFilter.boreholeNo !== '') {
          // 筛选孔号
          if (borehole.boreholeNo.search(_this.boreholeFilter.boreholeNo) === -1) {
            continue
          }
        }
        if (_this.boreholeFilter.timeRange !== null && _this.boreholeFilter.timeRange.length > 0) {
          // 筛选终孔时间
          if (borehole.terminalHoleDate === null ||
            Date.parse(new Date(borehole.terminalHoleDate)) < _this.boreholeFilter.timeRange[0] ||
            Date.parse(new Date(borehole.terminalHoleDate)) > _this.boreholeFilter.timeRange[1]) {
            continue
          }
        }
        // 筛选钻孔倾角
        if (_this.boreholeFilter.minInclinationAngle !== null) {
          if (borehole.inclinationAngle < _this.boreholeFilter.minInclinationAngle) {
            continue
          }
        }
        if (_this.boreholeFilter.maxInclinationAngle !== null) {
          if (borehole.inclinationAngle > _this.boreholeFilter.maxInclinationAngle) {
            continue
          }
        }
        // 筛选距离
        if (_this.boreholeFilter.startDistance !== null) {
          if (borehole.distanceToNavigationPoint < _this.boreholeFilter.startDistance) {
            continue
          }
        }
        if (_this.boreholeFilter.endDistance !== null) {
          if (borehole.distanceToNavigationPoint > _this.boreholeFilter.endDistance) {
            continue
          }
        }
        // 筛选施工异常
        if (_this.boreholeFilter.boreholeException !== null) {
          if (_this.boreholeFilter.boreholeException) {
            // 存在异常
            if (borehole.exceptionConstruction === null || borehole.exceptionConstruction === '' || borehole.exceptionConstruction.search('无') !== -1) {
              continue
            }
          } else {
            // 不存在异常
            if (borehole.exceptionConstruction !== null && borehole.exceptionConstruction !== '' && borehole.exceptionConstruction.search('无') === -1) {
              continue
            }
          }
        }
        _this.filterBoreholeList.push(borehole)
        // 数据统计
        this.countInfo.totalLength += Number(borehole.boreholeLength.toFixed(2))
        this.countInfo.mLength += borehole.m1
        this.countInfo.mLength += borehole.m2
        this.countInfo.mLength += borehole.m3
      }
      // 钻孔分类统计
      for (let i = 0; i < _this.boreholeCategoryList.length; i++) {
        _this.boreholeCategoryList[i].numCount = tArray[_this.boreholeCategoryList[i].categoryCode]
      }

      // 数据统计
      this.countInfo.boreholeNumCount = _this.filterBoreholeList.length
      this.countInfo.averageLength = this.countInfo.boreholeNumCount !== 0 ? (this.countInfo.totalLength / this.countInfo.boreholeNumCount) : 0
    },
    setCurrentBoreholeType() {
      console.log(this.boreholeFilter.boreholeType)
    },
    async getBoreholeCategoryList() {
      let _this = this
      await listBoreholeCategory({}).then(res => {
        _this.boreholeCategoryList = res.rows
      })
    },
    async getWorkfaceList() {
      let _this = this
      await listCoalminingworkingface({}).then(res => {
        _this.workfaceList = res.rows
      })
    },
    async setCurrentWorkface() {
      let _this = this
      _this.loading = true
      _this.boreholeList = []
      await gRoad({ workfaceId: _this.currentWorkfaceId }).then(res => {
        _this.roadwayList = res.rows
        _this.currentRoadwayId = null
      })
    },
    async setCurrentRoadway() {
      let _this = this
      _this.loading = true
      for (let i = 0; i < _this.roadwayList.length; i++) {
        if (_this.roadwayList[i].id === _this.currentRoadwayId) {
          _this.currentRoadway = _this.roadwayList[i]
        }
      }

      await listActualBorehole({ roadwayId: _this.currentRoadwayId }).then(res => {
        _this.boreholeList = res.rows
        _this.dataReclean()
        _this.filterBoreholeList = _this.boreholeList
        this.handleFilter()
        _this.loading = false
      })
    },
    dataReclean() {
      for (let i = 0; i < this.boreholeList.length; i++) {
        // 煤孔岩孔段
        let hole = this.boreholeList[i]
        if (hole.depth1stCoalSeamStart === null || hole.depth1stCoalSeamStart === '') {
          hole.depth1stCoalSeamStart = 0
        }
        if (hole.depth1stCoalSeamEnd === null || hole.depth1stCoalSeamEnd === '') {
          hole.depth1stCoalSeamEnd = 0
        }
        if (hole.depth2stCoalSeamStart === null || hole.depth2stCoalSeamStart === '') {
          hole.depth2stCoalSeamStart = 0
        }
        if (hole.depth2stCoalSeamEnd === null || hole.depth2stCoalSeamEnd === '') {
          hole.depth2stCoalSeamEnd = 0
        }
        if (hole.depth3stCoalSeamStart === null || hole.depth3stCoalSeamStart === '') {
          hole.depth3stCoalSeamStart = 0
        }
        if (hole.depth3stCoalSeamEnd === null || hole.depth3stCoalSeamEnd === '') {
          hole.depth3stCoalSeamEnd = 0
        }
        if (hole.depth4stRockSeamEnd === null || hole.depth4stRockSeamEnd === '') {
          hole.depth4stRockSeamEnd = 0
        }
        hole.depth1stCoalSeamStart > 0 ? hole.r1 = Number((hole.depth1stCoalSeamStart).toFixed(2)) : hole.r1 = 0
        hole.depth1stCoalSeamEnd > 0 ? hole.m1 = Number((hole.depth1stCoalSeamEnd - hole.depth1stCoalSeamStart).toFixed(2)) : hole.m1 = 0
        hole.depth2stCoalSeamStart > 0 ? hole.r2 = Number((hole.depth2stCoalSeamStart - hole.depth1stCoalSeamEnd).toFixed(2)) : hole.r2 = 0
        hole.depth2stCoalSeamEnd > 0 ? hole.m2 = Number((hole.depth2stCoalSeamEnd - hole.depth2stCoalSeamStart).toFixed(2)) : hole.m2 = 0
        hole.depth3stCoalSeamStart > 0 ? hole.r3 = Number((hole.depth3stCoalSeamStart - hole.depth2stCoalSeamEnd).toFixed(2)) : hole.r3 = 0
        hole.depth3stCoalSeamEnd > 0 ? hole.m3 = Number((hole.depth3stCoalSeamEnd - hole.depth3stCoalSeamStart).toFixed(2)) : hole.m3 = 0
        hole.r4 = Number((hole.boreholeLength - (hole.r1 + hole.r2 + hole.r3 + hole.m1 + hole.m2 + hole.m3)).toFixed(2))
        // 钻孔位置
        hole.distanceToNavigationPoint = Number(hole.distanceToNavigationPoint.toFixed(2))
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      console.log(selection)
      this.selectionCountInfo = {
        boreholeNumCount: 0,
        totalLength: 0,
        mLength: 0,
        averageLength: 0
      }
      this.selectionCountInfo.boreholeNumCount = selection.length
      for (let i = 0; i < selection.length; i++) {
        let hole = selection[i]
        this.selectionCountInfo.totalLength += hole.boreholeLength
        this.selectionCountInfo.mLength += hole.m1
        this.selectionCountInfo.mLength += hole.m2
        this.selectionCountInfo.mLength += hole.m3
      }
      this.selectionCountInfo.averageLength = this.selectionCountInfo.boreholeNumCount !== 0 ? (this.selectionCountInfo.totalLength / this.selectionCountInfo.boreholeNumCount) : 0

      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    }
  }
}
</script>


<style scoped>

</style>
