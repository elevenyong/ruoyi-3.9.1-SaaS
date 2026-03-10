<template>
  <div class="app-container">
    <span>请选择工作面：</span>
    <el-select v-model="currentWorkfaceId" placeholder="请选择工作面" @change="setCurrentWorkface">
      <el-option v-for="item in workfaceList" :key="item.id" :label="item.workfaceName"
                 :value="item.id">
      </el-option>
    </el-select>
    <el-button @click="()=>{this.coalInfoShow = true}" style="margin:0 5px" plain type="primary" size="mini">
      导入煤层点
    </el-button>
    <el-button style="margin:0 5px" type="primary" size="mini" plain
               @click="()=>{this.coalPoiShow = true;this.aoeCoalPoi = {'coalinfoId':this.currentCoal.id};this.coalPoiTitle = '新增煤层点'}"
               v-show="currentWorkfaceId!=null">
      新增煤层点
    </el-button>
    <el-button style="margin:0 5px" type="danger" size="mini" plain
               @click="handleDeleteCoalPoiList" v-show="currentWorkfaceId!=null">
      删除煤层点
    </el-button>

    <el-button style="margin:0 5px" type="primary" size="mini" plain
               @click="()=>{this.coalPoiTypeShow = true;}">
      管理煤层点类型
    </el-button>
    <el-table v-loading="false" :data="coalPointInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column label="序号" type="index"/>
      <el-table-column label="煤层点ID" align="center" key="id" prop="id" width="100px" />
      <el-table-column label="底板坐标x" align="center" key="floorCoordinatex" prop="floorCoordinatex" :formatter="(row)=>{return Number(row.floorCoordinatex.toFixed(2))}"/>
      <el-table-column label="底板坐标y" align="center" key="floorCoordinatey" prop="floorCoordinatey" :formatter="(row)=>{return Number(row.floorCoordinatey.toFixed(2))}"/>
      <el-table-column label="底板坐标z" align="center" key="floorCoordinatez" prop="floorCoordinatez" :formatter="(row)=>{return Number(row.floorCoordinatez.toFixed(2))}"/>
      <el-table-column label="煤层厚度（m）" align="center" key="coalThick" prop="coalThick" :formatter="(row)=>{return Number(row.coalThick.toFixed(2))}"/>
      <el-table-column label="对应煤层" :filters="coalInfoList" :filter-method="coalInfoFilterHandler" align="center"
                       key="coalinfoId" prop="coalinfoId">
        <template slot-scope="scope">
          {{ getLabel(coalInfoList, scope.row.coalinfoId, 'id', 'rockDesc') }}
        </template>
      </el-table-column>
      <el-table-column label="数据类型" align="center" key="dataType" prop="dataType" width="200">
        <template slot-scope="scope">
          {{ getLabel(coalPoiType, scope.row.dataType, 'id', 'type') }}
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" key="remark" prop="remark" width="200"/>
      <el-table-column label="操作" align="center" width="120">
        <template slot-scope="scope" v-if="scope.row.userId !== 1">
          <el-button size="mini" type="text" icon="el-icon-edit"
                     @click="handleEditCoalPoi(scope.row)">
            修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" style="color: red"
                     @click="handleDeleteCoalPoi(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  新增|编辑煤层信息  -->
    <el-dialog title="导入煤层点" :visible.sync="coalInfoShow" width="70%" :before-close="closeDialog">
      <el-row :gutter="20">
        <el-col :span="4">
          <span>请选择煤层：</span>
          <el-select v-model="currentCoalInfoId" placeholder="请选择煤层" @change="setCurrentCoalInfo"
                     style="width: 115px">
            <el-option v-for="item in coalInfoList" :key="item.id" :label="item.rockDesc"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2">
          <el-upload
            ref="upload"
            action="xlsx.html"
            :show-file-list="false"
            :auto-upload="false"
            accept=".xlsx,.xls"
            :on-change="getMyExcel"
          >
            <el-button size="mini" plain type="primary" icon="el-icon-upload2">导入数据</el-button>
          </el-upload>
        </el-col>
        <el-col :span="2">
          <el-button type="info" plain icon="el-icon-download" size="mini" @click="exportTemplate">导出模板
          </el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-table ref="coalPointForm" :data="uploadCoalPointsInfo" label-width="120px" height="700px">
          <el-table-column label="序号" type="index"/>
          <el-table-column label="对应煤层" align="center" key="combinaCoal" prop="combinaCoal"/>
          <el-table-column label="底板坐标x" align="center" key="floorCoordinatex" prop="floorCoordinatex"/>
          <el-table-column label="底板坐标y" align="center" key="floorCoordinatey" prop="floorCoordinatey"/>
          <el-table-column label="底板坐标z" align="center" key="floorCoordinatez" prop="floorCoordinatez"/>
          <el-table-column label="煤层厚度（m）" align="center" key="coalThick" prop="coalThick"/>
        </el-table>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCoalPointInfoForm">确 定</el-button>
        <el-button @click="closeDialog">取 消</el-button>
      </div>
    </el-dialog>

    <!--  新增|编辑坐标点信息  -->
    <el-dialog :title="coalPoiTitle" :visible.sync="coalPoiShow" width="20%">
      <el-form ref="coalPoiDialog" :model="aoeCoalPoi" label-width="120px">
        <el-form-item label="关联煤层" prop="coalSerial">
          <el-select v-model="aoeCoalPoi.coalinfoId" placeholder="请选择煤层"
                     style="width: 115px">
            <el-option v-for="item in coalInfoList" :key="item.id" :label="item.rockDesc"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="底板坐标x" prop="floorCoordinatex">
          <el-input v-model="aoeCoalPoi.floorCoordinatex"
                    @blur="aoeCoalPoi.floorCoordinatex = Number(aoeCoalPoi.floorCoordinatex)"
                    placeholder="底板坐标x">
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="底板坐标y" prop="floorCoordinatey">
          <el-input v-model="aoeCoalPoi.floorCoordinatey"
                    @blur="aoeCoalPoi.floorCoordinatey = Number(aoeCoalPoi.floorCoordinatey)"
                    placeholder="底板坐标y">
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="底板坐标z" prop="floorCoordinatez">
          <el-input v-model="aoeCoalPoi.floorCoordinatez"
                    @blur="aoeCoalPoi.floorCoordinatez = Number(aoeCoalPoi.floorCoordinatez)"
                    placeholder="底板坐标z">
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="煤厚" prop="coalThick">
          <el-input v-model="aoeCoalPoi.coalThick" @blur="aoeCoalPoi.coalThick = Number(aoeCoalPoi.coalThick)"
                    placeholder="煤厚">
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="煤层坐标点类型" prop="coalSerial">
          <!--          <el-input v-model="aoeCoalPoi.dataType" @blur="aoeCoalPoi.dataType = Number(aoeCoalPoi.dataType)"
                              placeholder="数据类型"/>-->
          <el-select v-model="aoeCoalPoi.dataType" placeholder="请选择坐标点类型">
            <el-option v-for="item in coalPoiType" :key="item.id" :label="item.type" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否启用" prop="isuse">
          <!--          <el-input v-model="aoeCoalPoi.dataType" @blur="aoeCoalPoi.dataType = Number(aoeCoalPoi.dataType)"
                              placeholder="数据类型"/>-->
          <el-switch v-model="aoeCoalPoi.isuse"></el-switch>
        </el-form-item>
        <el-form-item label="关联断层" prop="geoFaultId" v-if="aoeCoalPoi.dataType===3">
          <!--          <el-input v-model="aoeCoalPoi.dataType" @blur="aoeCoalPoi.dataType = Number(aoeCoalPoi.dataType)"
                              placeholder="数据类型"/>-->
          <el-select v-model="aoeCoalPoi.geoFaultId" placeholder="请选择断层">
            <el-option v-for="item in geoFaultList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="aoeCoalPoi.remark" type="textarea" placeholder="请输入备注内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCoalPoiForm">确 定</el-button>
        <el-button @click="()=>{this.coalPoiShow = false;this.aoeCoalPoi = {};}">取 消</el-button>
      </div>
    </el-dialog>

    <!--  显示煤层点类型信息  -->
    <el-dialog :title="coalPoiTypeTitle" :visible.sync="coalPoiTypeShow" width="25%">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="()=>{this.aoeCoalPoiTypeShow = true;this.aoeCoalPoiTypeTitle = '新增煤层点类型';}"
          >新增
          </el-button>
        </el-col>
        <!--        <el-col :span="1.5">
                  <el-button
                    type="warning"
                    plain
                    icon="el-icon-download"
                    size="mini"
                    @click="handleExport"
                  >导出
                  </el-button>
        </el-col>-->
      </el-row>
      <el-table v-loading="false" :data="coalPoiType">
        <el-table-column label="id" align="center" prop="id" width="50px"/>
        <el-table-column label="类型名称" align="center" prop="type"/>
        <el-table-column label="描述信息" align="center" prop="description"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="()=>{this.coalPoiTypeShow = false;}">取 消</el-button>
      </div>
    </el-dialog>

    <!--  新增|编辑煤层点类型信息  -->
    <el-dialog :title="aoeCoalPoiTypeTitle" :visible.sync="aoeCoalPoiTypeShow">
      <el-form ref="coalTypeDialog" :model="aoeCoalType" label-width="120px">
        <el-form-item label="煤层点类型名称" prop="type">
          <el-input v-model="aoeCoalType.type" placeholder="请输入煤层点类型名称"/>
        </el-form-item>
        <el-form-item label="备注" prop="description">
          <el-input v-model="aoeCoalType.description" type="textarea" placeholder="请输入描述内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCoalTypeForm">确 定</el-button>
        <el-button @click="()=>{this.aoeCoalPoiTypeShow = false;this.aoeCoalType = {};}">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.tablediv {
  margin-top: 10px;
}
</style>
<script>
import {
  aCoalInfo,
  aCoalPoi,
  aCoalPoiType,
  dCoalInfo,
  dCoalPoi,
  dCoalPoiType,
  gCoalInfo,
  lCoalPoiType,
  uCoalInfo,
  uCoalPoi,
  uCoalPoiType
} from '@/api/hhhz/meiceng'
import { listGeofault } from '@/api/hhhz/duanceng'
import * as THREE from 'three'
import { BoxGeometry } from 'three'
import { TransformControls } from 'three/examples/jsm/controls/TransformControls.js'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js'
import cdt2d from 'cdt2d'
import { getCoalminingWorkingfaceById, listCoalminingworkingface } from '@/api/hhhz/coalMiningWorkingFace'
import { insertCoalPointInfoList, listCoalPointInfo, updateCoalPointInfo } from '@/api/hhhz/coalpoint'
import { listRockinfo } from '@/api/hhhz/rockinfo'
import * as XLSX from 'xlsx'

export default {
  name: 'CoalInfo',
  dicts: ['sys_normal_disable', 'sys_user_sex', 'sys_user_expire'],
  data() {
    return {
      multipleSelection: [],
      uploadCoalPointsInfo: [],
      currentCoalInfoId: null,
      currentWorkfaceId: null,
      workfaceList: [],
      showzbd: true,
      showzkxx: false,
      coalInfoShow: false,
      aoeCoalInfo: {},
      coalPoiTitle: '新增煤层点信息',
      coalPoiShow: false,
      aoeCoalPoi: {},
      coalPoiTypeTitle: '煤层点类型',
      coalPoiTypeShow: false,
      aoeCoalPoiTypeTitle: '新增煤层点类型',
      aoeCoalPoiTypeShow: false,
      coal3DTitle: '预览煤层',
      coal3DShow: false,// 三维煤层展示
      points3DTop: [],
      points3DBottom: [],
      //edges: [[0, 1], [1, 2], [2, 7], [7, 8], [8, 11], [11, 12], [12, 13], [13, 16], [16, 18], [18, 17], [17, 4], [4, 3], [3, 0]],// 煤层边界点索引[[0,1],[1,2],[2,3]]
      //edges: [[0, 1], [1, 2], [2, 7], [7, 8], [8, 11], [11, 12], [12, 13], [13, 19], [19, 20], [20, 21], [21, 23], [23, 24], [24, 25], [25, 18], [18, 17], [17, 4], [4, 3], [3, 0]],
      edges: [],// 煤层边界点索引[[0,1],[1,2],[2,3]]
      coalCreated: false,
      clickPoint: null,
      aoeCoalType: {},
      queryParams: {},
      // 煤层点类型
      coalPoiType: [],
      // 煤层信息
      coalInfoList: [],
      // 煤层信息
      coalPointInfoList: [],
      // 断层信息
      geoFaultList: [],
      selectFaultInfo: null,
      currentCoal: {},
      currentCoalId: ''
    }
  },
  watch: {},
  async created() {
    await this.getWorkfaceList()
    await this.getCoalInfoList()
    await this.getCoalPoiTypeList()
    await this.getGeoFaultList()
    this.$message.success('请选择工作面！')
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    closeDialog() {
      let _this = this
      console.log('closeDialog')
      _this.coalInfoShow = false
      _this.uploadCoalPointsInfo = []
      _this.currentCoalInfoId = null
    },
    setCurrentCoalInfo(item) {
      let _this = this
      console.log(item)
      let combinaCoal = ''
      _this.coalInfoList.forEach(coalItem => {
        if (coalItem.id === item) {
          combinaCoal = coalItem.rockDesc
        }
      })
      _this.uploadCoalPointsInfo.forEach(item => {
        item.coalinfoId = item
        item.combinaCoal = combinaCoal
      })
    },
    exportTemplate() {
      this.downloadTemp('exportTemplate/煤层点_模板.xlsx', `煤层点_模板.xlsx`)
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
        let combinaCoal = ''
        _this.coalInfoList.forEach(coalItem => {
          if (coalItem.id === _this.currentCoalInfoId) {
            combinaCoal = coalItem.rockDesc
          }
        })
        _this.uploadCoalPointsInfo = []
        jsonList.forEach((item) => {
          console.log(item)
          _this.uploadCoalPointsInfo.push({
            workfaceId: _this.currentWorkfaceId,
            coalThick: Number(Number(item['煤厚']).toFixed(4)),
            floorCoordinatex: Number(Number(item['X']).toFixed(4)),
            floorCoordinatey: Number(Number(item['Y']).toFixed(4)),
            floorCoordinatez: Number(Number(item['Z']).toFixed(4)),
            coalinfoId: _this.currentCoalInfoId,
            dataType: 1,
            isuse: 1,
            combinaCoal: combinaCoal
          })
        })
      }
      reader.readAsBinaryString(file.raw)
    },
    coalInfoFilterHandler(value, row) {
      console.log(value)
      return row.coalinfoId === value
    },
    getCoalPointList() {
      let _this = this
      listRockinfo({ workfaceId: this.currentWorkfaceId, rockType: 1 }).then((res) => {
        _this.coalInfoList = res.rows
        for (let i = 0; i < _this.coalInfoList.length; i++) {
          _this.coalInfoList[i]['text'] = _this.coalInfoList[i].rockDesc
          _this.coalInfoList[i]['value'] = _this.coalInfoList[i].id
        }
        console.log(_this.coalInfoList)
      })
      listCoalPointInfo({ workfaceId: this.currentWorkfaceId, dataType: 1 }).then((res) => {
        _this.coalPointInfoList = res.rows
      })
    },
    setCurrentWorkface() {
      this.getCoalPointList()
    },
    async getWorkfaceList() {
      let _this = this
      await listCoalminingworkingface({}).then(res => {
        _this.workfaceList = res.rows
      })
    },
    handleShowCoal3D() {
      this.coal3DShow = true
      setTimeout(() => {
        this.init()
      }, 1000)
    },
    async init() {
      this.initScene()
      this.render()
    },
    render() {
      renderer.render(scene, camera)
      requestAnimationFrame(this.render)
    },

    /**
     * 添加点击事件, 射线捕捉获取点击物体
     * @param event
     */
    onClick(event) {
      //console.log(event)
      event.preventDefault()

      /* let bouLeft = renderer.domElement.getBoundingClientRect().left
      let bouTop = renderer.domElement.getBoundingClientRect().top
      let ofRight = renderer.domElement.offsetLeft
      let ofTop = renderer.domElement.offsetTop
      console.log('bouLeft:', bouLeft, 'bouTop:', bouTop, 'ofLeft:', ofRight, 'ofTop:', ofTop)
      let cw = renderer.domElement.clientWidth
      let ch = renderer.domElement.clientHeight
      let ow = renderer.domElement.offsetWidth
      let oh = renderer.domElement.offsetHeight
      console.log('cw:', cw, 'ch:', ch, 'ow:', ow, 'oh:', oh)
      let a = ((event.clientX - document.getElementById('container').getBoundingClientRect().left) / document.getElementById('container').offsetWidth) * 2 - 1
      let b = -((event.clientY - document.getElementById('container').getBoundingClientRect().top) / document.getElementById('container').offsetHeight) * 2 + 1
      let c = ((event.clientX - document.getElementById('container').offsetLeft) / document.getElementById('container').clientWidth) * 2 - 1
      let d = -((event.clientY - document.getElementById('container').offsetTop) / document.getElementById('container').clientHeight) * 2 + 1
      console.log('a:', a, 'b:', b, 'c:', c, 'd:', d, 'clientX', event.clientX, 'clientY', event.clientY)
      mouse.x = a
      mouse.y = b */
      let sceneDocRect = outputContainer.getBoundingClientRect()
      mouse.x = ((event.clientX - sceneDocRect.left) / outputContainer.offsetWidth) * 2 - 1
      mouse.y = -((event.clientY - sceneDocRect.top) / outputContainer.offsetHeight) * 2 + 1
      /* vet3.set(
        mouse.x,
        mouse.y,
        1)
      vet3.unproject(camera)
      console.log('camera.position:', camera.position, ',normalize:', vet3.sub(camera.position).normalize())
      let raycaster = new THREE.Raycaster(camera.position, vet3.sub(camera.position).normalize()) */
      let standardVector = new THREE.Vector3(mouse.x, mouse.y, 1)
      let worldVector = standardVector.unproject(camera)
      let ray = worldVector.sub(camera.position).normalize()
      let raycaster = new THREE.Raycaster(camera.position, ray)
      if (!this.coalCreated) {
        let poisIntersects = raycaster.intersectObjects(pointsBottomToSceneList)
        console.log('selected:', poisIntersects)
        console.log('this.edges:' + JSON.stringify(this.edges))
        if (poisIntersects && poisIntersects.length > 0) {
          let selected = poisIntersects[0]
          console.log('selected:', selected.object)
          this.clickPoint = new THREE.Mesh(new THREE.SphereGeometry(0.5), new THREE.MeshBasicMaterial({ color: 0xff0000 }))//0xff0000设置材质颜色为红色
          this.clickPoint.position.set(selected.point.x, selected.point.y, selected.point.z)
          scene.add(this.clickPoint)
          if (this.edges.length === 0) {
            this.edges.push([Number(selected.object.name)])
          } else if (this.edges.length === 1 && this.edges[0].length === 1) {
            this.edges[0].push(Number(selected.object.name))
          } else {
            let len = this.edges.length
            this.edges.push([this.edges[len - 1][1], Number(selected.object.name)])
            if (Number(selected.object.name) === this.edges[0][0]) {
              console.log('创建煤层')
              this.createCoal()
            }
          }
        }
      } else {
        let faultsSelected = raycaster.intersectObjects(faultsMeshList)
        if (faultsSelected && faultsSelected.length > 0) {
          let selected = faultsSelected[0]
          console.log('fault:', selected.object.name)
          for (let i = 0; i < this.geoFaultList.length; i++) {
            if (this.geoFaultList[i].id === Number(selected.object.name)) {
              this.selectFaultInfo = this.geoFaultList[i]
              console.log('faultInfo:', this.geoFaultList[i])
              break
            }
          }
        }
      }
    },

    /**
     * 创建断层
     */
    createFault() {

      // 构造断层点
      let coalPoints = this.currentCoal.coalPointinfoList
      let faultNoList = []
      let faults = {}
      let faultPs = []
      for (let i = 0; i < coalPoints.length; i++) {
        if (coalPoints[i].dataType === 3) {
          // 断层点位
          faultPs.push(coalPoints[i])
        }
      }
      // 构造faultNoList
      for (let i = 0; i < faultPs.length; i++) {
        if (!faultNoList.includes(faultPs[i].geoFaultId)) {
          faultNoList.push(faultPs[i].geoFaultId)
        }
      }
      // 构造faults
      for (let i = 0; i < faultNoList.length; i++) {
        faults[faultNoList[i]] = { up: [], under: [] }
      }
      // 填充faults
      for (let i = 0; i < faultPs.length; i++) {
        let fp = faultPs[i]
        if (fp.remark === '0') {
          faults[fp.geoFaultId].up.push(fp)
        } else if (fp.remark === '1') {
          faults[fp.geoFaultId].under.push(fp)
        }
      }

      // 渲染faults
      for (let i = 0; i < faultNoList.length; i++) {
        let fNo = faultNoList[i]
        if (faults[fNo].up.length > 3) {
          this.renderFault(fNo, faults[fNo].up, this.createEdges(faults[fNo].up.length), false)
        }
        if (faults[fNo].under.length > 3) {
          this.renderFault(fNo, faults[fNo].under, this.createEdges(faults[fNo].under.length), true)
        }

        //let edges =   [[0, 1], [1, 2], [2, 5], [5, 4], [4, 3], [3, 0]]
      }

    },
    // 创建边界
    createEdges(count) {
      let r = []
      for (let i = 0; i < count - 1; i++) {
        r.push([i, i + 1])
      }
      r.push([count - 1, 0])
      return r
    },

    handleEditCoalPoiType(coalPoiType) {
      this.aoeCoalPoiTypeShow = true
      this.aoeCoalPoiTypeTitle = '修改煤层点类型'
      this.aoeCoalType = coalPoiType
    },
    // 获取断层信息
    async getGeoFaultList() {
      await listGeofault({}).then((response) => {
        if (response.code === 200) {
          this.geoFaultList = response.rows
        }
      })
    },
    // 获取煤层点类型数据
    async getCoalPoiTypeList() {
      this.aoeCoalType = {}
      await lCoalPoiType({}).then((resp) => {
        if (resp.code === 200) {
          this.coalPoiType = resp.rows
        }
      })
    },
    // 获取煤层数据
    async getCoalInfoList() {
      await gCoalInfo({}).then((response) => {
        if (response.code === 200) {
          this.coalInfoList = response.rows
        }
      })
    },
    // 选择显示的煤层
    setCurrentCoal() {
      this.coalInfoList.forEach((item, index) => {
        if (item.id === this.currentCoalId) {
          this.currentCoal = item
        }
      })
    },
    handleEditCoalPoi(row) {
      this.coalPoiShow = true
      this.aoeCoalPoi = row
      this.coalPoiTitle = '修改煤层点'
    },
    // 提交煤层点信息
    submitCoalPointInfoForm() {
      let _this = this
      console.log(this.aoeCoalInfo)
      insertCoalPointInfoList(_this.uploadCoalPointsInfo).then(response => {
        this.$modal.msgSuccess('新增成功')
        this.coalInfoShow = false
        this.aoeCoalInfo = false
        this.getCoalPointList()
      })

    },
    // 提交煤层点信息
    submitCoalPoiForm() {
      console.log(this.aoeCoalPoi)
      this.aoeCoalPoi.workfaceId = this.currentWorkfaceId
      this.aoeCoalPoi.isuse = 1
      if (this.aoeCoalPoi.id != null) {
        uCoalPoi(this.aoeCoalPoi).then(response => {
          this.$modal.msgSuccess('修改成功')
          this.coalPoiShow = false
          this.aoeCoalPoi = false
          this.getCoalPointList()
        })
      } else {
        aCoalPoi(this.aoeCoalPoi).then(response => {
          this.$modal.msgSuccess('新增成功')
          this.coalPoiShow = false
          this.aoeCoalPoi = false
          this.getCoalPointList()
        })
      }
    },
    // 提交煤层点类型数据
    submitCoalTypeForm() {
      console.log(this.aoeCoalType)
      if (this.aoeCoalType.id != null) {
        uCoalPoiType(this.aoeCoalType).then(response => {
          this.$modal.msgSuccess('修改成功')
          this.aoeCoalPoiTypeShow = false
          this.getCoalPoiTypeList()
        })
      } else {
        aCoalPoiType(this.aoeCoalType).then(response => {
          this.$modal.msgSuccess('新增成功')
          this.aoeCoalPoiTypeShow = false
          this.getCoalPoiTypeList()
        })
      }
    },
    // 删除煤层
    handleDeleteCoal() {
      let _this = this
      this.$modal.confirm('是否确认删除编号为"' + this.currentCoal.coalSerial + '"的煤层？').then(function() {
        return dCoalInfo({ ids: _this.currentCoal.id })
      }).then((response) => {
        console.log('dCoalInfo', response)
        this.getCoalInfoList()
        this.currentCoal = {}
        this.currentCoalId = ''
        this.$modal.msgSuccess('删除成功')
      }).catch((res) => {
        console.log(res)
      })
    },
    handleDeleteCoalPoiList() {
      if (this.multipleSelection.length > 0) {
        let ids = ''
        for (let i = 0; i < this.multipleSelection.length; i++) {
          if (i !== this.multipleSelection.length - 1) {
            ids += (this.multipleSelection[i].id + ',')
          } else {
            ids += this.multipleSelection[i].id
          }
        }
        console.log(ids)
        this.handleDeleteCoalPoi(ids)
      }else {
        this.$modal.msgWarning('请选择煤层点后再删除！')
      }
    },
    // 删除煤层点
    handleDeleteCoalPoi(coalPoiIds) {
      this.$modal.confirm('是否确认删除编号为"' + coalPoiIds + '"的煤层点？').then(function() {
        return dCoalPoi({ ids: coalPoiIds })
      }).then((response) => {
        console.log('dCoalPoi', response)
        this.getCoalPointList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    // 删除煤层点类型数据
    handleDeleteCoalPoiType(coalPoiType) {
      this.$modal.confirm('是否确认删除编号为"' + coalPoiType.id + '"的煤层点类型？').then(function() {
        return dCoalPoiType({ ids: coalPoiType.id })
      }).then((response) => {
        console.log('dCoalPoi', response)
        this.getCoalPoiTypeList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    // getLabel
    getLabel(list, id, value, label) {
      if (id != '' && Array.isArray(list) && list.length != 0) {
        return !list.find(item => item[value] == id) ? id : list.find(item => item[value] == id)[label]
      } else {
        return id
      }
    }
  }

}
</script>
