<template>
  <div>
    <el-row>
      <h4>基础信息</h4>
      <el-form
        :model="boreholeInfo"
        ref="queryForm"
        size="small"
        :inline="true"
        v-show="showSearch"
        label-width="138px"
      >
        <el-col :span="12">
          <el-form-item label="钻孔地点" prop="roadway">
            {{ currentRoad.roadway }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="钻孔类型" prop="boreholeCategory">
            <el-select
              v-model="boreholeInfo.boreholeCategory"
              placeholder="请选择钻孔类型"
              style="width: 250px"
            >
              <el-option
                v-for="item in boreholeCategoryList"
                :key="item.id"
                :label="item.boreholeCategory"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="钻孔编号" prop="zkbh">
            <el-input placeholder="组号" style="width: 120px" v-model="boreholeInfo.groupNo"></el-input>
            -
            <el-input placeholder="孔号" style="width: 120px" v-model="boreholeInfo.boreholeNo"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="成孔时间" prop="checkingDate">
            <el-date-picker
              v-model="boreholeInfo.terminalHoleDate"
              type="datetime"
              style="width: 250px"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="施工人员" prop="personConstruction">
            <el-input
              v-model="boreholeInfo.personConstruction"
              placeholder=""
              class="my_el-form-item"
              style="width: 250px"

            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="施工过程中异常现象" prop="exceptionConstruction">
            <el-input
              v-model="boreholeInfo.exceptionConstruction"
              style="width: 250px;"
            >
            </el-input>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <el-row>
      <h4>钻孔位置信息</h4>
      <el-form
        :model="boreholeInfo"
        ref="queryForm"
        size="small"
        :inline="true"
        label-width="138px"
      >
        <el-col :span="12">
          <el-form-item label="导线点" prop="zkdd">
            <el-select
              v-model="boreholeInfo.navigationPointId"
              placeholder="请选择导线点"
              style="width: 130px"
            >
              <el-option
                v-for="item in currentRoad.tnavigationPointList"
                :key="item.id"
                :label="item.navigationPointName"
                :value="item.id"
              >
              </el-option>
            </el-select>
            <el-select
              v-model="boreholeInfo.directionToNavigation"
              placeholder="与导线点方向"
              style="width: 130px"
            >
              <el-option
                v-for="item in navDirectionsOptions"
                :key="item.id"
                :label="item.label"
                :value="item.id"
              >
              </el-option>
            </el-select>
            <!--                <el-input v-model="form.navigationPointName"/>-->
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="距导线点距离" prop="distanceToNavigationPoint">
            <el-input
              v-model="boreholeInfo.distanceToNavigationPoint"
              placeholder=""
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="距巷道中心线距离" prop="distanceToCentrallineTunnel">
            <el-input
              v-model="boreholeInfo.distanceToCentrallineTunnel"
              placeholder=""
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="距底板距离" prop="holeAltitude">
            <el-input
              v-model="boreholeInfo.holeAltitude"
              placeholder=""
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <el-row>
      <h4>钻孔物理信息</h4>
      <el-form
        :model="boreholeInfo"
        ref="queryForm"
        size="small"
        :inline="true"
        label-width="138px"
      >
        <el-col :span="12">
          <el-form-item label="实际倾角" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.inclinationAngle"
              placeholder="请输入实际倾角"
              class="my_el-form-item"
              style="width: 250px"
            >
              <template slot="append">°</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实际方位角" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.azimuthAngle"
              placeholder="请输入实际方位角"
              class="my_el-form-item"
              style="width: 250px"
            >
              <template slot="append">°</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="钻孔孔径" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.aperture"
              placeholder="请输入钻孔孔径"
              class="my_el-form-item"
              style="width: 250px"
            >
              <template slot="append">mm</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="抽采半径" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.extractionRadius"
              placeholder="请输入抽采半径"
              class="my_el-form-item"
              style="width: 250px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="岩段1" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.r1"
              placeholder=""
              style="width: 250px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="煤段1" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.m1"
              placeholder=""
              style="width: 250px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="岩段2" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.r2"
              placeholder=""
              style="width: 250px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="煤段2" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.m2"
              placeholder=""
              style="width: 250px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="岩段3" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.r3"
              placeholder=""
              style="width: 250px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="煤段3" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.m3"
              placeholder=""
              style="width: 250px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="岩段4" prop="zkdd">
            <el-input
              type="number"
              v-model="boreholeInfo.r4"
              placeholder=""
              style="width: 250px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <!--            <el-col :span="12">
                      <el-form-item label="第一岩段" prop="zkdd">
                        <el-input
                          type="number"
                          v-model="boreholeInfo.depth1stRockSeamStart"
                          placeholder=""
                          style="width: 130px"
                        >
                          <template slot="append">m</template>
                        </el-input>
                        -
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth1stCoalSeamStart"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="第一煤段" prop="zkdd">
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth1stCoalSeamStart"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                        -
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth1stCoalSeamEnd"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="第二岩段" prop="zkdd">
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth1stCoalSeamEnd"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                        -
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth2stCoalSeamStart"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="第二煤段" prop="zkdd">
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth2stCoalSeamStart"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                        -
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth2stCoalSeamEnd"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="第三岩段" prop="zkdd">
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth2stCoalSeamEnd"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                        -
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth3stCoalSeamStart"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="第三煤段" prop="zkdd">
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth3stCoalSeamStart"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                        -
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth3stCoalSeamEnd"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="第四岩段" prop="zkdd">
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth3stCoalSeamEnd"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                        -
                        <el-input
                          type="number"
                          style="width: 130px"
                          v-model="boreholeInfo.depth4stRockSeamEnd"
                          placeholder=""
                        >
                          <template slot="append">m</template>
                        </el-input>
                      </el-form-item>
                    </el-col>-->
      </el-form>
    </el-row>
    <el-row>
      <div>
        <h4 style="float: left">参数测定信息</h4>
        <el-button
          size="mini"
          type="primary"
          style="float: right;vertical-align: center;horiz-align: center;margin-right: 20px;margin-top:10px"
        >新增
        </el-button>
      </div>
      <el-table
        :data="boreholeInfo.bindAttributesCoal1st"
      >
        <el-table-column label="属性类型" prop="attributeTypeId">
          <template slot-scope="scope">
            {{ getAttributeLabel(scope.row.attributeTypeId) }}
          </template>
        </el-table-column>
        <el-table-column label="关联地层" prop="rockCoalId">
          <template slot-scope="scope">
            {{ getRockCoalLabel(scope.row.rockCoalId) }}
          </template>
        </el-table-column>
        <el-table-column label="取样深度" prop="holeMeasureDepth">
          <template slot-scope="scope">
            {{ scope.row.holeMeasureDepth + ' m' }}
          </template>
        </el-table-column>
        <el-table-column label="测定参数值" prop="attributeValue1">
          <template slot-scope="scope">
            {{ scope.row.attributeValue1 + getAttrUnit(scope.row.attributeTypeId) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              style="color: #1e84e9;margin-left: 10px"
            >修改
            </el-button>
            <el-popconfirm
              title="确定删除此属性值吗？"
              @confirm=""
            >
              <el-button
                slot="reference"
                size="mini"
                type="text"
                icon="el-icon-delete"
                style="color: #e12d30;margin-left: 10px"
              >删除
              </el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <!--      <el-col :span="6" class="cans">
            <el-card header="钻孔位置示意图">
              <el-row>
                <div class="can">
                  <span style="font-size: 12px">平面图</span>
                  <canvas ref="can1" width="300px" height="300px"></canvas>
                </div>
              </el-row>
              <el-row>
                <div class="can">
                  <span style="font-size: 12px">开孔布置图</span>
                  <canvas ref="can2" width="300px" height="300px"></canvas>
                </div>
              </el-row>
            </el-card>
          </el-col>-->
  </div>
</template>
<style>
</style>
<script>
import { gGongzuobanci } from '@/api/hhhz/sys'
import { gGroupIdList, gHoleNoList, gRoadWayList, gZuankongyongtuList } from '@/api/hhhz/zuankong'
import { gZuankongyuanshiyanshoudan, uZuankongyuanshiyanshoudan } from '@/api/hhhz/13zkysysd'

import { gRoad } from '@/api/hhhz/hangdao'
import { aActualBorehole } from '@/api/hhhz/actual_borehole'
import { listAttributeType } from '@/api/hhhz/attributeType'
import { listRockinfo } from '@/api/hhhz/rockinfo'

export default {
  props: ['boreholeInfo', 'currentRoad', 'boreholeCategoryList', 'currentWorkfaceId'],
  name: 'Borehole',
  data() {
    return {
      rockInfoList: [],
      attributeTypes: [],
      navDirectionsOptions: [
        { id: 0, label: '向外（向后）（向巷口）' },
        { id: 1, label: '向里（向前）（向切眼）' }
      ],
      showPatchHole: false,
      patchHoleForm: {},
      // 选中孔序号
      queryZuankongxuhao: [],
      // 选中组号
      queryZuankongzuhao: [],
      // 选中钻孔用途
      queryZuankongyongtu: [],
      // 选中时间
      queryCheckingdate: null,
      // 工作班次
      gzbcOptions: [],
      // 钻孔地点
      zkddOptions: [],
      // 钻孔用途
      zkytOptions: [],
      // 钻孔组号
      zkzhOptions: [],
      // 钻孔编号
      zkxhOptions: [],
      // 导线点数据
      dxdList: [],
      exceptionList: [],
      ycxx: '',
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
      // tGasContentPressure表格数据
      tGasContentPressureList: [],
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
    this.getRockInfoList()
    this.getAttributeType()
    this.getRoadWayList()
    this.getZuankongyongtuList()
    this.getGongzuobanciList()
    //this.getList();
  },
  mounted() {

  },
  methods: {
    getAttrUnit(id) {
      for (let i = 0; i < this.attributeTypes.length; i++) {
        if (this.attributeTypes[i].id === id) {
          return ' ' + this.attributeTypes[i].dataUnit
        }
      }
    },
    getRockInfoList() {
      let _this = this
      listRockinfo({ workfaceId: this.currentWorkfaceId }).then(res => {
        _this.rockInfoList = res.rows
      })
    },
    getRockCoalLabel(id) {
      for (let i = 0; i < this.rockInfoList.length; i++) {
        if (this.rockInfoList[i].id === id) {
          return this.rockInfoList[i].rockDesc === '' ? this.rockInfoList[i].rockTypeData.rockType : this.rockInfoList[i].rockDesc
        }
      }
    },
    getAttributeLabel(id) {
      for (let i = 0; i < this.attributeTypes.length; i++) {
        if (this.attributeTypes[i].id === id) {
          return this.attributeTypes[i].attributeName + '(' + this.attributeTypes[i].dataUnit + ')'
        }
      }
    },
    getAttributeType() {
      let _this = this
      listAttributeType({}).then(res => {
        _this.attributeTypes = res.rows
      })
    },
    handlePatchHole() {
      this.patchHoleForm = {}
      this.patchHoleForm.roadwayId = this.queryParams.roadwayId
      this.showPatchHole = true
    },

    submitPatchHoleForm() {
      this.$refs['patchHoleForm'].validate((valid) => {
        if (valid) {
          if (this.patchHoleForm.id != null) {
            /* uDesignedBorehole(this.desHoleForm).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.showPatchHole = false
              this.listDesignedBorehole()
            }) */
          } else {
            aActualBorehole([this.patchHoleForm]).then((response) => {
              this.$modal.msgSuccess('补孔成功')
              this.showPatchHole = false
              this.getRoadWayList()
            })
          }
        }
      })
    },

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
      await gRoad({ id: rwId }).then((response) => {
        console.log('gWorkFace', response)
        if (response.total !== 1) {
          console.log('err', response)
        } else {
          console.log('导线点', response.rows[0])
          this.dxdList = response.rows[0].tnavigationPointList
        }
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
      this.dxdList = []
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
    /** 查询tGasContentPressure列表 */
    /** 查询tDrainageParameter列表 */
    getList() {
      this.loading = true

      this.loading = false
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.patchHoleForm = {}
      this.showPatchHole = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        samplingpot: null,
        boreholeId: null,
        samplingDepth: null,
        gasContent: null,
        gasPressure: null,
        measureDate: null,
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
      this.resetForm('queryForm')
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
      this.title = '添加tGasContentPressure'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTGasContentPressure(id).then((response) => {
        this.form = response.data
        this.open = true
        this.title = '修改tGasContentPressure'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTGasContentPressure(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addTGasContentPressure(this.form).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    // 提交更新
    submitUpdate() {
      this.form.downTubeTimeStart = this.form.downtubetime[0]
      this.form.downTubeTimeEnd = this.form.downtubetime[1]
      this.form.exceptionDuringDrilling = JSON.stringify(
        this.exceptionList
      )
        .replaceAll('[', '')
        .replaceAll(']', '')
        .replaceAll('\\', '')
        .replaceAll('"', '')
      uZuankongyuanshiyanshoudan(this.form).then((response) => {
        this.$modal.msgSuccess('修改成功')
        this.open = false
        this.getList()
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除tGasContentPressure编号为"' + ids + '"的数据项？')
        .then(function() {
          return delTGasContentPressure(ids)
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
        '/tGasContentPressure/export',
        {
          ...this.queryParams
        },
        `tGasContentPressure_${new Date().getTime()}.xlsx`
      )
    }
  }
}
</script>
<!--    <el-row>
      <h4>其他</h4>
      <el-form
        :model="boreholeInfo"
        ref="queryForm"
        size="small"
        :inline="true"
        label-width="138px"
      >

        <el-col :span="12">
          <el-form-item label="拔钻后孔口CO浓度" prop="zkdd">
            <el-input
              style="width: 250px"
              v-model="boreholeInfo.gasConcentration"
              placeholder=""
            >
              <template slot="append">%</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="拔钻后孔口瓦斯浓度" prop="zkdd">
            <el-input
              style="width: 250px"
              v-model="boreholeInfo.pulledcoconcentration"
              placeholder=""
            >
              <template slot="append">ppm</template>
            </el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="封孔方式" prop="holeSealingType">
            <el-select
              v-model="boreholeInfo.holeSealingType"
              placeholder="请选择"
              class="my_el-form-item"
              style="width: 250px"
            >
              <el-option
                v-for="item in zkddOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="下管时间" prop="downTubeTime">
            <el-date-picker
              arrow-control
              type="datetime"
              v-model="boreholeInfo.downTubeTimeStart"
              placeholder="开始时间"
              class="time-select"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 120px"
            >
            </el-date-picker>
            至
            <el-date-picker
              arrow-control
              type="datetime"
              v-model="boreholeInfo.downTubeTimeEnd"
              placeholder="开始时间"
              class="time-select"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 120px"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="封孔实管长度" prop="solidPipelineLength">
            <el-input
              type="number"
              v-model="boreholeInfo.solidPipelineLength"
              placeholder=""
              class="my_el-form-item"
              style="width: 250px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="封孔花管长度" prop="sievePipelineLength">
            <el-input
              type="number"
              v-model="boreholeInfo.sievePipelineLength"
              placeholder=""
              class="my_el-form-item"
              style="width: 250px"

            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="封孔规格" prop="holeSealingSpecification">
            <el-select
              v-model="boreholeInfo.holeSealingSpecification"
              placeholder="请选择"
              class="my_el-form-item"
              style="width: 250px"

            >
              <el-option
                v-for="item in zkddOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="验收人员" prop="personCheckingSite">
            <el-input
              v-model="boreholeInfo.personCheckingSite"
              placeholder=""
              class="my_el-form-item"
              style="width: 250px"

            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="验收时间" prop="checkingDate">
            <el-date-picker
              v-model="boreholeInfo.checkingDate"
              placeholder=""
              type="datetime"
              class="my_el-form-item"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 250px"

            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="拔钻时间" prop="pullDrillDate">
            <el-date-picker
              style="width: 250px"
              v-model="boreholeInfo.pullDrillDate"
              class="time-select"
            />
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>-->
