<template>
  <div class="app-container">
    <div>
      <el-row :gutter="20">
        <!--工作面列表-->
        <el-col :span="6" :xs="24">
          <el-card style="height: 80vh; margin: 10px 5px;overflow-y: auto">
            <div slot="header" class="clearfix">
              <span>工作面列表</span>
              <div style="float: right">
                <el-button size="mini" type="primary" @click="setCurrentWorkFace(1,null)"
                >新增采煤工作面
                </el-button>
                <!--                <el-button size="mini" type="primary" @click="setCurrentWorkFace(2,{})"
                                >新增揭煤工作面
                                </el-button>-->
              </div>
            </div>
            <div class="head-container">
              <el-scrollbar style="height: 70vh;overflow-x: hidden;">
                <el-menu
                  @close="handleClose"
                  @open="handleOpen"
                  :unique-opened="true"
                >
                  <el-submenu index="1">
                    <template slot="title">
                      <i class="el-icon-location"></i>
                      <span>采煤工作面</span>
                    </template>
                    <el-menu-item
                      v-for="(item) in coalMiningWFList"
                      :key="item.id.toString()"
                      :index="item.type+'-'+item.id"
                      @click="setCurrentWorkFace(1,item)"
                    >
                      {{ item.workfaceName }}
                    </el-menu-item>
                  </el-submenu>
                  <el-submenu index="2" disabled>
                    <template slot="title">
                      <i class="el-icon-location"></i>
                      <span>掘进工作面</span>
                    </template>
                    <el-menu-item
                      v-for="item in workFaces"
                      :key="item.id.toString()"
                      :index="item.id.toString()"
                      @click="setCurrentWorkFace(0,item)"
                    >
                      {{ item.workfaceName }}
                    </el-menu-item>
                  </el-submenu>
                  <el-submenu index="3" disabled>
                    <template slot="title">
                      <i class="el-icon-location"></i>
                      <span>揭煤工作面</span>
                    </template>
                    <el-menu-item
                      v-for="(item) in uncoverCoalWFList"
                      :key="item.id.toString()"
                      :index="item.type+'-'+item.id"
                      @click="setCurrentWorkFace(2,item)"
                    >
                      {{ item.workfaceName }}
                    </el-menu-item>
                  </el-submenu>
                </el-menu>
              </el-scrollbar>
            </div>
          </el-card>
        </el-col>
        <!--采煤工作面数据信息-->
        <el-col :span="18" :xs="24" v-loading="loading">
          <!--:rules="rules"-->
          <h4>基本信息</h4>
          <el-divider/>
          <el-form ref="coalMiningWFForm" :model="coalMiningWF" label-width="130px" :inline="true">
            <el-form-item label="采煤工作面名称" prop="workfaceName">
              <el-input v-model="coalMiningWF.workfaceName" placeholder="请输入采煤工作面名称" style="width: 180px"/>
            </el-form-item>
            <el-form-item label="走向长度" prop="towardsLength">
              <el-input v-model="coalMiningWF.towardsLength" placeholder="请输入走向长度" style="width: 180px">
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="倾向长度" prop="cutLength">
              <el-input v-model="coalMiningWF.cutLength" placeholder="请输入倾向长度" style="width: 180px">
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="煤层数量" prop="height">
              <el-input v-model="coalMiningWF.coalNumber" placeholder="煤层数量" style="width: 180px">
                <template slot="append">层</template>
              </el-input>
            </el-form-item>
            <el-form-item label="倾角" prop="inclinationAngle">
              <el-input
                v-model="coalMiningWF.inclinationAngle"
                placeholder="请输入倾角"
                style="width: 180px"
              >
                <template slot="append">°</template>
              </el-input>
            </el-form-item>
            <el-form-item label="方位角" prop="azimuthAngle">
              <el-input
                v-model="coalMiningWF.azimuthAngle"
                placeholder="请输入方位角"
                style="width: 180px"
              >
                <template slot="append">°</template>
              </el-input>
            </el-form-item>
            <el-form-item label="工作面倾向" prop="dipDirection">
              <el-select
                style="width: 180px"
                v-model="coalMiningWF.dipDirection"
                placeholder="工作面倾向"
              >
                <el-option
                  v-for="dip in dipDirectionMenu"
                  :key="dip.id"
                  :label="dip.dipDirection"
                  :value="dip.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="主煤层平均厚度" prop="defaultCoalThickness">
              <el-input
                v-model="coalMiningWF.defaultCoalThickness"
                placeholder="请输入"
                style="width: 180px"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="工作面边界" prop="dipDirection">
              <el-button :disabled="!coalMiningWF.id" type="text" icon="el-icon-view" @click="showBoundary">查看工作面边界</el-button>
            </el-form-item>
          </el-form>
          <el-divider/>
          <h4>巷道关联</h4>
          <el-form ref="coalMiningWFForm" :model="coalMiningWF" :inline="true" label-width="130px">
            <el-form-item label="工作面进风巷" prop="airIntakeRoadway">
              <el-button icon="el-icon-edit" size="mini" @click="showRoadway(coalMiningWF.airIntakeRoadwayData,1)">
                {{ coalMiningWF.airIntakeRoadwayData ? coalMiningWF.airIntakeRoadwayData.roadway : '无' }}
              </el-button>
            </el-form-item>
            <el-form-item label="工作面进风底抽巷" prop="dcAirIntakeRoadway">
              <el-button icon="el-icon-edit" size="mini" @click="showRoadway(coalMiningWF.dcAirIntakeRoadwayData,4)">
                {{ coalMiningWF.dcAirIntakeRoadwayData ? coalMiningWF.dcAirIntakeRoadwayData.roadway : '无' }}
              </el-button>
            </el-form-item>
            <br>
            <el-form-item label="工作面回风巷" prop="airReturnRoadway">
              <el-button icon="el-icon-edit" size="mini" @click="showRoadway(coalMiningWF.airReturnRoadwayData,2)">
                {{ coalMiningWF.airReturnRoadwayData ? coalMiningWF.airReturnRoadwayData.roadway : '无' }}
              </el-button>
            </el-form-item>
            <el-form-item label="工作面回风底抽巷" prop="dcAirIntakeRoadway">
              <el-button icon="el-icon-edit" size="mini" @click="showRoadway(coalMiningWF.dcAirReturnRoadwayData,5)">
                {{ coalMiningWF.dcAirReturnRoadwayData ? coalMiningWF.dcAirReturnRoadwayData.roadway : '无' }}
              </el-button>
            </el-form-item>
            <br>
            <el-form-item label="工作面切眼巷" prop="cutEyeRoadway">
              <el-button icon="el-icon-edit" size="mini" @click="showRoadway(coalMiningWF.cutEyeRoadwayData,3)">
                {{ coalMiningWF.cutEyeRoadwayData ? coalMiningWF.cutEyeRoadwayData.roadway : '无' }}
              </el-button>
            </el-form-item>
            <el-form-item label="工作面切眼底抽巷" prop="dcCutEyeRoadway">
              <el-button icon="el-icon-edit" size="mini" @click="showRoadway(coalMiningWF.dcCutEyeRoadwayData,6)">
                {{ coalMiningWF.dcCutEyeRoadwayData ? coalMiningWF.dcCutEyeRoadwayData.roadway : '无' }}
              </el-button>
            </el-form-item>
            <br>
            <el-form-item label="工作面中煤巷" prop="middleRoadway">
              <el-button icon="el-icon-edit" size="mini" @click="showRoadway(coalMiningWF.middleRoadwayData,9)">
                {{ coalMiningWF.middleRoadwayData ? coalMiningWF.middleRoadwayData.roadway : '无' }}
              </el-button>
            </el-form-item>
            <el-form-item label="工作面中间底抽巷" prop="dcMiddleRoadway">
              <el-button icon="el-icon-edit" size="mini" @click="showRoadway(coalMiningWF.dcMiddleRoadwayData,7)">
                {{ coalMiningWF.dcMiddleRoadwayData ? coalMiningWF.dcMiddleRoadwayData.roadway : '无' }}
              </el-button>
            </el-form-item>
            <br>
            <el-form-item label="其他巷道" prop="otherRoadway">
              <el-button v-for="otherRoad in coalMiningWF.otherRoadwayData" @click="showRoadway(otherRoad,8)"
                         size="mini"
                         icon="el-icon-edit" style="margin-right: 10px;">
                {{ otherRoad.roadway }}
              </el-button>

              <el-button @click="showRoadway(otherRoad,8)" size="mini" icon="el-icon-edit" style="margin-right: 10px;">
                新增
              </el-button>
            </el-form-item>
            <br>

            <el-button
              icon="el-icon-check"
              size="mini"
              type="primary"
              plain
              @click="UOACoalMiningWFForm"
            >保存
            </el-button>
            <el-popconfirm
              title="确定删除此工作面吗？"
              @confirm="delCoalMiningWorkFace"
            >
              <el-button
                style="margin-left: 20px"
                slot="reference"
                icon="el-icon-delete"
                size="mini"
                type="danger"
                plain
              >删除
              </el-button>
            </el-popconfirm>
          </el-form>
        </el-col>
      </el-row>
    </div>
    <el-dialog :title="showUOABoundaryPointTitle" :visible.sync="showUOABoundaryPoint" width="300px" append-to-body>
      <el-form ref="boundaryPointForm" :model="boundaryPointForm" label-width="70px" :inline="true">
        <el-form-item label="x" prop="x">
          <el-input v-model="boundaryPointForm.x" placeholder="" style="width: 120px"/>
        </el-form-item>
        <el-form-item label="y" prop="y">
          <el-input v-model="boundaryPointForm.y" placeholder="" style="width: 120px"/>
        </el-form-item>
        <el-form-item label="z" prop="z">
          <el-input v-model="boundaryPointForm.z" placeholder="" style="width: 120px"/>
        </el-form-item>
        <br>
        <el-button type="primary" size="mini" @click="saveBoundaryPointToList" style="float: right">确定</el-button>
      </el-form>
    </el-dialog>
    <el-dialog title="工作面边界" :visible.sync="showBoundaryDialog" width="500px" append-to-body>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-upload
            ref="upload"
            action="xlsx.html"
            :show-file-list="false"
            :auto-upload="false"
            accept=".xlsx,.xls"
            :on-change="getMyExcel"
          >
            <el-button size="mini" plain type="primary" icon="el-icon-upload2">导入</el-button>
          </el-upload>
        </el-col>
        <el-col :span="1.5">
          <el-button type="info" plain icon="el-icon-download" size="mini" @click="exportTemplate">导出模板
          </el-button>
        </el-col>
      </el-row>
      <el-row :gutter="10" class="mb8">
        <el-table
          key="uuid"
          :data="boundaryList"
        >
          <el-table-column label="序号" type="index" align="center"/>
          <el-table-column label="x" prop="x" align="center"/>
          <el-table-column label="y" prop="y" align="center"/>
          <el-table-column label="z" prop="z" align="center"/>
          <el-table-column label="操作" prop="volume" width="120px" align="center">
            <template slot-scope="scope">
              <el-button @click="handleUpdate(scope.row)" icon="el-icon-edit" type="text" size="small">修改
              </el-button>
              <el-button @click="handleDelete(scope.$index)" icon="el-icon-delete" type="text" style="color: red" size="small">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <el-row :gutter="10" class="mb8">
        <el-col :span="12" style="text-align:center">
          <el-button type="info" plain icon="el-icon-close" size="mini" @click="cancelBoundaryPoint">取消</el-button>
        </el-col>
        <el-col :span="12" style="text-align:center">
          <el-button type="primary" plain icon="el-icon-check" size="mini" @click="saveBoundaryPoint">保存</el-button>
        </el-col>
      </el-row>
    </el-dialog>
    <el-dialog title="巷道信息" @delRoadMsg="handleDelRoadMsg" :visible.sync="showRoadwayDialog"
               :before-close="handleCloseRoadway" width="80%" append-to-body>
      <Roadway :currentRoad="currentRoadway" :roadEditable="false"/>
    </el-dialog>
  </div>
</template>
<script>
import {
  addCoalminingworkingface, delCoalminingworkingface,
  getCoalminingWorkingfaceById,
  listCoalminingworkingface,
  updateCoalminingworkingface
} from '@/api/hhhz/coalMiningWorkingFace'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import Roadway from './Roadway.vue'
import * as XLSX from 'xlsx'
import { downloadTemp } from '@/utils/request'
import { uWorkFace } from '@/api/hhhz/gongzuomian'

export default {
  name: 'WorkfaceManage',
  dicts: ['sys_normal_disable', 'sys_user_sex', 'sys_user_expire'],
  components: { Treeselect, Roadway },
  data() {
    return {
      showUOABoundaryPoint: false,
      showUOABoundaryPointTitle: '',
      boundaryPointForm: { x: null, y: null, z: null },
      currentRoadway: {},
      loading: true,
      // 新增采煤工作面
      addCoalMiningWFShow: false,
      coalMiningWF: {},
      coalMiningWFList: [],
      coalMiningWFQueryParams: {},
      // 新增揭煤工作面
      addUncoverMiningWFShow: false,
      dipDirectionMenu: [
        {
          id: 0,
          dipDirection: '顺时针(面向切眼左高右低)'
        },
        {
          id: 1,
          dipDirection: '逆时针(面向切眼左低右高)'
        }
      ],
      boundaryPointEdit: false,
      boundaryList: [],
      showBoundaryDialog: false,
      showRoadwayDialog: false,
      uncoverCoalWF: {},
      uncoverCoalWFList: [],
      uncoverCoalWFQueryParams: {}
    }
  },
  watch: {},
  async created() {
    await this.getCoalMiningWFList()
    this.$message.success('请选择工作面！')
  },
  methods: {
    delCoalMiningWorkFace() {
      let _this = this
      delCoalminingworkingface(_this.coalMiningWF.id).then(res => {
        if (res.code === 200) {
          this.$message.success('删除成功！')
          this.getCoalMiningWFList()
          _this.coalMiningWF = {}
          _this.loading = true
          this.$message.success('请选择工作面！')
        }
      })
    },
    UOACoalMiningWFForm() {
      let _this = this
      if (_this.coalMiningWF.id) {
        // 更新
        let updateWFInfo = {
          id: _this.coalMiningWF.id,
          workfaceName: _this.coalMiningWF.workfaceName,
          coalNumber: Number.parseInt(_this.coalMiningWF.coalNumber),
          defaultCoalThickness: Number.parseFloat(_this.coalMiningWF.defaultCoalThickness),
          towardsLength: Number.parseFloat(_this.coalMiningWF.towardsLength),
          cutLength: Number.parseFloat(_this.coalMiningWF.cutLength),
          height: Number.parseFloat(_this.coalMiningWF.height),
          inclinationAngle: Number.parseFloat(_this.coalMiningWF.inclinationAngle),
          azimuthAngle: Number.parseFloat(_this.coalMiningWF.azimuthAngle),
          dipDirection: Number.parseFloat(_this.coalMiningWF.dipDirection)
        }
        updateCoalminingworkingface(updateWFInfo).then(res => {
          if (res.code === 200) {
            _this.getCoalMiningWFById(_this.coalMiningWF.id)
            this.$modal.msgSuccess('工作面更新成功')
          } else {
            this.$modal.msgError('工作面更新失败')
          }
        })
      } else {
        // 新增
        let addWFInfo = {
          workfaceName: _this.coalMiningWF.workfaceName,
          coalNumber: Number.parseInt(_this.coalMiningWF.coalNumber),
          defaultCoalThickness: Number.parseFloat(_this.coalMiningWF.defaultCoalThickness),
          towardsLength: Number.parseFloat(_this.coalMiningWF.towardsLength),
          cutLength: Number.parseFloat(_this.coalMiningWF.cutLength),
          height: Number.parseFloat(_this.coalMiningWF.height),
          inclinationAngle: Number.parseFloat(_this.coalMiningWF.inclinationAngle),
          azimuthAngle: Number.parseFloat(_this.coalMiningWF.azimuthAngle),
          dipDirection: Number.parseFloat(_this.coalMiningWF.dipDirection)
        }
        addCoalminingworkingface(addWFInfo).then(res => {
          if (res.code === 200) {
            _this.coalMiningWF = res.data
            this.$modal.msgSuccess('创建工作面成功！')
          }
        })
      }
    },
    handleDelRoadMsg(result) {
      console.log('result', result)
      this.showRoadwayDialog = !result
    },
    saveBoundaryPoint() {
      let _this = this
      const updateData = {
        id: this.coalMiningWF.id,
        boundaryPoints: this.boundaryList
      }
      updateCoalminingworkingface(updateData).then(resp => {
        if (resp.code === 200) {
          _this.getCoalMiningWFById(_this.coalMiningWF.id)
          this.$modal.msgSuccess('工作面更新成功')
        } else {
          this.$modal.msgError('工作面更新失败')
        }
      })
      _this.showBoundaryDialog = false
    },
    cancelBoundaryPoint() {
      let _this = this
      _this.showBoundaryDialog = false
      _this.getCoalMiningWFById(_this.coalMiningWF.id)
    },
    exportTemplate() {
      this.downloadTemp('exportTemplate/工作面边界_模板.xlsx', `工作面边界_模板.xlsx`)
    },
    getMyExcel(file, fileList) {
      let _this = this
      // 用FileReader来完成数据流的转换
      let reader = new FileReader()
      reader.onload = function(e) {
        let data = e.target.result
        //console.log(XLSX)
        //通过XLSX来解析出workbook（工作簿）也就导入的excel文件
        let wb = XLSX.read(data, { type: 'binary' })
        const sheetsName = wb.SheetNames[0]
        const jsonList = XLSX.utils.sheet_to_json(wb.Sheets[sheetsName])
        _this.boundaryList = []
        jsonList.forEach((item) => {
          _this.boundaryList.push({
            x: Number(Number(item['x']).toFixed(4)),
            y: Number(Number(item['y']).toFixed(4)),
            z: Number(Number(item['z']).toFixed(4))
          })
        })
        console.log(_this.boundaryList)
      }
      reader.readAsBinaryString(file.raw)
    },
    /* 保存数据到列表 */
    saveBoundaryPointToList() {
      let _this = this
      if (!_this.boundaryPointEdit) {
        let newPoint = {}
        newPoint.x = _this.boundaryPointForm.x
        newPoint.y = _this.boundaryPointForm.y
        newPoint.z = _this.boundaryPointForm.z
        _this.boundaryList.push(_this.boundaryPointForm)
      }
      _this.boundaryPointForm = { x: null, y: null, z: null }
      _this.showUOABoundaryPoint = false
    },
    handleDelete(index) {
      let _this = this
      _this.boundaryList.splice(index, 1)
    },
    /** 新增按钮操作 */
    handleAdd() {
      let _this = this
      _this.boundaryPointEdit = false
      this.boundaryPointForm = { x: null, y: null, z: null }
      this.showUOABoundaryPoint = true
      this.showUOABoundaryPointTitle = '添加边界点'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      let _this = this
      _this.boundaryPointEdit = true
      _this.boundaryPointForm = row
      /* this.boundaryPointForm = {}
      this.boundaryPointForm.x = row.x
      this.boundaryPointForm.y = row.y
      this.boundaryPointForm.z = row.z */
      _this.showUOABoundaryPoint = true
      _this.showUOABoundaryPointTitle = '修改边界点'
    },
    showRoadway(roadway, roadType) {
      let _this = this
      if (roadway != null) {
        _this.currentRoadway = roadway
      } else {
        let newRoadway = {
          roadway: null,
          faultShapId: null,
          azimuthAngle: null,
          wide: null,
          height: null,
          elevation: null,
          stripCtrlLeft: null,
          stripCtrlRight: null,
          roadOriginX: null,
          roadOriginY: null,
          roadOriginZ: null,
          length: null,
          nearFloor: null,
          inOrOut: null,
          inoutDistance: null,
          distanceCoalRoadway: null,
          tnavigationPointList: [],
          id: null,
          type: roadType,
          workfaceId: _this.coalMiningWF.id
        }
        _this.currentRoadway = newRoadway
      }
      _this.showRoadwayDialog = true
    },
    showBoundary() {
      let _this = this
      _this.boundaryList = _this.coalMiningWF.boundaryPoints
      _this.showBoundaryDialog = true
    },
    async getCoalMiningWFById(id) {
      await getCoalminingWorkingfaceById(id).then(res => {
        this.coalMiningWF = res.data
      })
    },
    async getCoalMiningWFList() {
      //this.loading = true;
      await listCoalminingworkingface(this.coalMiningWFQueryParams).then(response => {
        this.coalMiningWFList = response.rows
      })
    },
    async setCurrentWorkFace(index, wf) {
      let _this = this
      _this.loading = true
      if (wf != null) {
        await _this.getCoalMiningWFById(wf.id)
      } else {
        let initWFInfo = {
          id: null,
          workfaceName: null,
          coalNumber: null,
          defaultCoalThickness: null,
          towardsLength: null,
          cutLength: null,
          height: null,
          inclinationAngle: null,
          azimuthAngle: null,
          dipDirection: null,
          boundaryPoints: [],
          otherRoadway: null,
          dcMiddleRoadway: null,
          middleRoadway: null,
          dcCutEyeRoadway: null,
          cutEyeRoadway: null,
          dcAirReturnRoadway: null,
          airReturnRoadway: null,
          dcAirIntakeRoadway: null,
          airIntakeRoadway: null,
          otherRoadwayData: [],
          dcMiddleRoadwayData: null,
          middleRoadwayData: null,
          dcCutEyeRoadwayData: null,
          cutEyeRoadwayData: null,
          dcAirReturnRoadwayData: null,
          airReturnRoadwayData: null,
          dcAirIntakeRoadwayData: null,
          airIntakeRoadwayData: null
        }
        _this.coalMiningWF = initWFInfo
      }
      _this.loading = false
    },
    handleCloseRoadway() {
      let _this = this
      _this.getCoalMiningWFById(_this.coalMiningWF.id)
      _this.showRoadwayDialog = false
    }
  }
}
</script>
