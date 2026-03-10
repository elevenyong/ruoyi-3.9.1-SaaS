<template>
  <div>
    <div id="box" style="display: flex">
      <div id="container"></div>
      <div id="tGUI"></div>
      <div id="output"></div>
    </div>
    <div style="margin-top: 10px">
      <el-form size="small" :inline="true">
        <el-form-item label="钻孔终点位置" prop="endPosition">
          {{
            '(' + (endPoint.x ? endPoint.x : 0) + ',' + (endPoint.y ? endPoint.y : 0) + ',' + (endPoint.z ? endPoint.z : 0) + ')'
          }}
        </el-form-item>
        <el-form-item label="钻孔施工位置" prop="startPosition">
          <el-select v-model="startPointId" placeholder="请选择施工位置" @change="changeWP">
            <el-option
              v-for="item in workPoints"
              :key="item.id"
              :label="item.x+','+item.y+','+item.z"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" @click="addDrillLoading">手动补钻</el-button>
          <el-button type="primary" size="mini" @click="autoAddDrillLoading">自动补钻</el-button>
        </el-form-item>
      </el-form>
      <el-form :model="baseData" ref="baseDataForm" size="small" :inline="true">
        <el-form-item label="巷道方位角" prop="azimuth">
          <el-input
            v-model="baseData.azimuth"
            placeholder="请输入巷道方位角"
            clearable
            style="width: 130px"
            @blur="baseData.azimuth !='' ? Number(baseData.azimuth):0"
          />
        </el-form-item>
        <el-form-item label="巷道倾角" prop="inclination">
          <el-input
            v-model="baseData.inclination"
            placeholder="请输入巷道倾角"
            clearable
            style="width: 130px"
            @blur="baseData.inclination !='' ? Number(baseData.inclination):0"
          />
        </el-form-item>
        <el-form-item label="导线点X" prop="navX">
          <el-input
            v-model="baseData.navX"
            placeholder="请输入导线点X"
            clearable
            style="width: 130px"
            @blur="baseData.navX !='' ? Number(baseData.navX):0"
          />
        </el-form-item>
        <el-form-item label="导线点Y" prop="navY">
          <el-input
            v-model="baseData.navY"
            placeholder="请输入导线点Y"
            clearable
            style="width: 130px"
            @blur="baseData.navY !='' ? Number(baseData.navY):0"
          />
        </el-form-item>
        <el-form-item label="导线点Z" prop="navZ">
          <el-input
            v-model="baseData.navZ"
            placeholder="请输入导线点Z"
            clearable
            style="width: 130px"
            @blur="baseData.navZ !='' ? Number(baseData.navZ):0"
          />
        </el-form-item>
        <el-form-item label="抽采半径" prop="radius">
          <el-input
            v-model="radius"
            placeholder="请输入导线点Z"
            clearable
            style="width: 130px"
            @blur="radius !='' ? Number(radius):0"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini">确定</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="">修改</el-button>
          <el-upload
            class="upload-demo"
            ref="upload"
            action="xlsx.html"
            :auto-upload="false"
            :on-change="getMyExcel"
          >
            <el-button size="small" type="primary">选取文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <el-form :model="drillingData" ref="drillingDataForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="孔号" prop="id">
          <el-input
            v-model="drillingData.id"
            placeholder="请输入钻孔孔号"
            clearable
            style="width: 130px"
          />
        </el-form-item>
        <el-form-item label="钻孔方位角" prop="azimuth">
          <el-input
            v-model="drillingData.azimuth"
            placeholder="请输入钻孔方位角"
            clearable
            style="width: 130px"
            @blur="drillingData.azimuth !='' ? Number(drillingData.azimuth):0"
          />
        </el-form-item>
        <el-form-item label="钻孔倾角" prop="inclination">
          <el-input
            v-model="drillingData.inclination"
            placeholder="请输入钻孔倾角"
            clearable
            style="width: 130px"
            @blur="drillingData.inclination !='' ? Number(drillingData.inclination):0"
          />
        </el-form-item>
        <el-form-item label="与导线点距离" prop="disNav">
          <el-input
            v-model="drillingData.disNav"
            placeholder="请输入与导线点距离"
            clearable
            style="width: 130px"
            @blur="drillingData.disNav !='' ? Number(drillingData.disNav):0"
          />
        </el-form-item>
        <el-form-item label="距顶板高度" prop="disTop">
          <el-input
            v-model="drillingData.disTop"
            placeholder="请输入距顶板高度"
            clearable
            style="width: 130px"
            @blur="drillingData.disTop !='' ? Number(drillingData.disTop):0"
          />
        </el-form-item>
        <el-form-item label="据中心线距离" prop="disCenter">
          <el-input
            v-model="drillingData.disCenter"
            placeholder="据中心线距离"
            clearable
            style="width: 130px"
            @blur="drillingData.disCenter !='' ? Number(drillingData.disCenter):0"
          />
        </el-form-item>
        <el-form-item label="见煤深度" prop="seeLen">
          <el-input
            v-model="drillingData.seeLen"
            placeholder="见煤深度"
            clearable
            style="width: 130px"
            @blur="drillingData.seeLen !='' ? Number(drillingData.seeLen):0"
          />
        </el-form-item>
        <el-form-item label="止煤深度" prop="overLen">
          <el-input
            v-model="drillingData.overLen"
            placeholder="见煤深度"
            clearable
            style="width: 130px"
            @blur="drillingData.overLen !='' ? Number(drillingData.overLen):0"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="addDrillingData">添加</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="()=>{this.drillingData={}}">重置</el-button>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="calModelDataLoading">计算上图</el-button>
        </el-form-item>
      </el-form>

      <el-table
        key="id"
        :data="drillingDataList"
        height="350"
        style="width: 50%"
      >
        <el-table-column label="组号" prop="group" width="50"/>
        <el-table-column label="序号" prop="id" width="50"/>
        <el-table-column label="钻孔方位角" prop="azimuth"/>
        <el-table-column label="钻孔倾角" prop="inclination"/>
        <el-table-column label="与导线点距离" prop="disNav"/>
        <el-table-column label="距顶板高度" prop="disTop"/>
        <el-table-column label="据中心线距离" prop="disCenter"/>
        <el-table-column label="见煤深度" prop="seeLen"/>
        <el-table-column label="止煤深度" prop="overLen"/>
      </el-table>
      <!--      <el-table
              key="uuid"
              :data="emptyBandList"
              height="350"
              style="width: 40%"
            >
              <el-table-column label="空白带编号" prop="uuid"/>
              <el-table-column label="中心点坐标" prop="position"/>
              <el-table-column label="体积" prop="volume"/>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-edit"
                             @click="autoAddDrillLoading">
                    自动补钻
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-table
              key="uuid"
              :data="workPoints"
              height="350"
              style="width: 40%"
            >
              <el-table-column label="钻场编号" prop="id"/>
              <el-table-column label="x" prop="x"/>
              <el-table-column label="y" prop="y"/>
              <el-table-column label="z" prop="z"/>
            </el-table>-->
    </div>
  </div>
</template>

<script>

import * as THREE from 'three'
import { Mesh } from 'three'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js'
import { TransformControls } from 'three/examples/jsm/controls/TransformControls.js'
import { VertexNormalsHelper } from 'three/examples/jsm/helpers/VertexNormalsHelper'
import { computeMeshVolume, Evaluator, GridMaterial, Operation, OperationGroup, SUBTRACTION } from 'three-bvh-csg'
//import { GUI } from 'three/examples/jsm/libs/lil-gui.module.min.js'
import { GUI } from 'lil-gui';
import { Delaunay as Delaunator } from 'd3-delaunay'
import { ConvexGeometry } from 'three/examples/jsm/geometries/ConvexGeometry.js'
import * as YUKA from '@/utils/yuka/yuka.module.js'
import { createOBBHelper } from '@/utils/yuka/BVHelper'
import * as XLSX from 'xlsx'

const params = {
  snap: true,
  wireframe: false,
  displayControls: false,
  transparentBrushes: true,
  showOutCube: false,// 显示外部包围体
  showCoalCube: false,// 显示煤体
  display: 'OVERLAY',
  drillDesMode: false, // 开启补钻模式
  showOriginDrill: true, // 显示原始钻孔
  showOriginDrillRange: true, // 显示原始钻孔抽采范围
  showAddDrill: true, // 显示补钻钻孔
  showAddDrillRange: true, // 显示补钻钻孔抽采范围
  showOriginEmptyBand: true, // 显示原始空白带信息
  showCoalMesh: true, // 显示煤层顶底板网格
  showEmptyBand: true, // 显示空白带
  showDrillInCoal: true // 显示煤孔段
}

let renderer, camera, scene, gui, outputContainer, textOutput, axesHelper, tGUIContainer
let controls, transformControls
let light
let initCSGEvaluator, addCSGEvaluator
let initResult, initRoot, partsResult, partsRoot
let originDrillGroup, originDrillRangeGroup, addDrillGroup, addDrillRangeGroup
let rootPartsObject, wireframeObject, coalCubeObj
let coalGeometry
let drillInCoalMat, coalTopMeshMat, coalBottomMeshMat, roadMat, brushMat, addDrillMat, addDrillRangeMat, originDrillMat,
  originDrillRangeMat, resultGridMat
let mouse = new THREE.Vector2()
let partMeshs = []
let partCubes = []
export default {
  name: 'EmptyBandModel',
  components: {},

  data() {
    return {
      // 自动补钻标识
      autoAdd: false,
      autoIndex: 1,
      // 计算加载中
      loading: false,
      // 基础信息
      baseData: { navX: 0, navY: 0, navZ: 0, azimuth: 30, inclination: 0 },
      // 添加钻孔信息
      drillingData: {},
      // tempList
      tempList: [
        /* { group: 0, id: 1, inclination: 68, azimuth: 290, disNav: 10, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        { group: 0, id: 2, inclination: 73, azimuth: 290, disNav: 10, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.5 },
        { group: 0, id: 3, inclination: 79, azimuth: 290, disNav: 10, disTop: 0.4, disCenter: 1.2, seeLen: 19.7, overLen: 24.9 },
        { group: 0, id: 4, inclination: 84, azimuth: 290, disNav: 10, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.3 },
        { group: 0, id: 5, inclination: 90, azimuth: 290, disNav: 10, disTop: 0, disCenter: 0, seeLen: 19, overLen: 24 },
        { group: 0, id: 6, inclination: 84, azimuth: 100, disNav: 10, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.3 },
        { group: 0, id: 7, inclination: 79, azimuth: 100, disNav: 10, disTop: 0.4, disCenter: 1.2, seeLen: 19.7, overLen: 24.9 },
        { group: 0, id: 8, inclination: 73, azimuth: 100, disNav: 10, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { group: 0, id: 9, inclination: 68, azimuth: 100, disNav: 10, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 } */
      ],
      // 钻孔列表
      drillingDataList: [],
      // 见煤点list
      bottomPointList: [],
      // 止煤点list
      topPointList: [],
      // 抽采半径
      radius: 5.5,
      // 空白带
      emptyBandList: [],
      // 显示外部包围体
      showOutCube: false,
      // 显示煤层
      showCoalCube: false,
      // 施工点(巷道)
      workPoints: [],
      // 分割后空白带块儿点位
      emptyObjPoints: [],
      endPoint: {},
      // 钻场位置
      startPoint: {},
      startPointId: null,
      // 选中钻孔终点
      clickPoint: null,
      // 施工点位
      workPointsMesh: [],
      // 原始钻孔点
      points3DBottom: [],
      points3DTop: [],
      loadingInstance: null,
      // 原始钻孔
      originDrillMeshList: [],
      originDrillInCoalMeshList: [],
      // 补钻钻孔
      addDrillMeshList: [],
      // 钻孔行数
      groupNum: 10
    }
  },

  mounted() {
    //this.init()
  },
  methods: {
    getMyExcel(file, fileList) {
      let _this = this
      // 用FileReader来完成数据流的转换
      let reader = new FileReader()
      reader.onload = function(e) {
        let data = e.target.result
        console.log(XLSX)
        //通过XLSX来解析出workbook（工作簿）也就导入的excel文件
        let wb = XLSX.read(data, { type: 'binary' })
        console.log(wb)
        let bd = _this.baseData
        let base = 10
        let thisGroup = -1
        for (let i = 5; i < 520; i++) {
          let obj = {
            group: Number((wb.Sheets['11104工作面底板穿层钻孔台账']['A' + i]['v'] + '').replaceAll('列', '')) - 63,
            id: wb.Sheets['11104工作面底板穿层钻孔台账']['B' + i]['v'],
            disNav: base + ((Number((wb.Sheets['11104工作面底板穿层钻孔台账']['A' + i]['v'] + '').replaceAll('列', '')) - 63) * 4),
            disCenter: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['J' + i]['v']),
            disTop: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['K' + i]['v']),
            azimuth: Math.abs(Number(wb.Sheets['11104工作面底板穿层钻孔台账']['L' + i]['v'])),
            inclination: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['M' + i]['v']),
            kg: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['N' + i]['v']),
            overLen: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['O' + i]['v']),
            ccml: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['P' + i]['v']),
            seeLen: Number(Number((wb.Sheets['11104工作面底板穿层钻孔台账']['O' + i]['v']) - Number(wb.Sheets['11104工作面底板穿层钻孔台账']['Q' + i]['v'])))
          }
          _this.drillingDataList.push(obj)
          if (thisGroup !== obj.group) {
            let drillPointX = bd.navX + ((base + (obj.group * 4)) * Math.sin(_this.ang2rad(bd.azimuth)))
            let drillPointY = bd.navY + ((base + (obj.group * 4)) * Math.cos(_this.ang2rad(bd.azimuth)))
            let drillPointZ = bd.navZ + ((base + (obj.group * 4)) * Math.tan(_this.ang2rad(bd.inclination)))
            //console.log('x,y,z:', drillPointX, drillPointY, drillPointZ)
            _this.workPoints.push({ id: obj.group, x: drillPointX, y: drillPointY, z: drillPointZ })
            thisGroup = obj.group
          }
        }
        console.log('_this.workPoints:', _this.workPoints)

      }
      //readAsBinaryString需要File 或 Blob 对象的参数
      reader.readAsBinaryString(file.raw)
      // console.log(XLSX.read(file, { type: "array" }));
    },
    // 选择施工位置
    changeWP(index) {
      console.log(index)
      for (let i = 0; i < this.workPointsMesh.length; i++) {
        this.workPointsMesh[i].material.color.set(0xffffff)
      }
      this.workPointsMesh[index].material.color.set(0xff00ff)
    },
    // 手动添加钻孔
    addDrillLoading() {
      this.loadingInstance = this.$loading({
        lock: true,
        text: '正在计算中,请稍后...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      //this.addDrill()
      setTimeout(() => {
        this.addDrill()
      }, 1000)
    },
    // 自动补钻
    autoAddDrillLoading() {
      this.loadingInstance = this.$loading({
        lock: true,
        text: '正在计算中,请稍后...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      setTimeout(() => {
        console.log('开始计算自动补钻')
        this.autoAdd = true
        this.autoAddDrill()
      }, 1000)
    },
    // 自动补钻
    autoAddDrill() {
      for (let i = 0; i < partMeshs.length; i++) {
        console.log('计算第' + i + '块空白补钻数据')
        const positions = partMeshs[i].geometry.attributes.position
        let tempX = null
        let index = 0
        // 取空白带实体X最大值位置
        for (let j = 0; j < positions.count; j++) {
          if (tempX === null) {
            tempX = positions.getX(j)
            index = j
          } else {
            if (tempX < positions.getX(j)) {
              tempX = positions.getX(j)
              index = j
            }
          }
        }
        // 确定终点位置
        let endPoint = new THREE.Vector3(positions.getX(index), positions.getY(index), positions.getZ(index))
        console.log('终点位置:', endPoint)

        let disList = []
        // 筛选出施工位置
        for (let j = 0; j < this.topPointList.length; j++) {
          // 计算最近的钻孔
          let dis = this.calDistance(this.topPointList[j][0], this.topPointList[j][2], this.topPointList[j][1], endPoint.x, endPoint.y, endPoint.z)
          // 添加钻孔-距离数据
          disList.push({
            id: j,
            dis: dis,
            group: this.topPointList[j][3],
            x: this.topPointList[j][0],
            y: this.topPointList[j][2],
            z: this.topPointList[j][1]
          })
        }
        // 对距离值排序
        disList.sort((x, y) => {
          return x.dis - y.dis
        })
        //console.log('disList:', disList)

        let finalGroupsObj = {}
        let groupCountObj = {}
        let targetGroup = -1
        for (let j = 0; j < disList.length; j++) {
          let tempGroup = disList[j].group
          if (groupCountObj[tempGroup] != null) {
            groupCountObj[tempGroup].push(disList[j])
          } else {
            groupCountObj[tempGroup] = []
            groupCountObj[tempGroup].push(disList[j])
          }
          Object.keys(groupCountObj).forEach(function(key) {
            if (groupCountObj[key].length === 2) {
              //targetGroup = key
              finalGroupsObj[key] = groupCountObj[key]
            }
          })
          // 如果有两组最近点, 就不再向下寻找
          if (Object.keys(finalGroupsObj).length === 2) {
            //console.log('finalGroupsObj:', finalGroupsObj)
            break
          }
        }
        let finalDisKeysObj = {}
        let _this = this
        Object.keys(finalGroupsObj).forEach(function(key) {
          let x0 = (finalGroupsObj[key][0].x + finalGroupsObj[key][1].x) / 2
          let y0 = (finalGroupsObj[key][0].y + finalGroupsObj[key][1].y) / 2
          let z0 = (finalGroupsObj[key][0].z + finalGroupsObj[key][1].z) / 2
          //console.log('xyz:', x0, y0, z0)
          // 计算这一组点的中点与选定点的距离
          finalDisKeysObj[key] = _this.calDistance(
            x0, y0, z0,
            endPoint.x,
            endPoint.y,
            endPoint.z
          )
        })
        //console.log('finalDisKeysObj:', finalDisKeysObj)
        let disKeysList = Object.keys(finalGroupsObj)
        if (finalDisKeysObj[disKeysList[0]] <= finalDisKeysObj[disKeysList[1]]) {
          targetGroup = disKeysList[0]
        } else {
          targetGroup = disKeysList[1]
        }
        //console.log('this.workPoints:', this.workPoints)
        let wkPoint = null
        for (let j = 0; j < this.workPoints.length; j++) {
          // 确定出施工点位置
          if (this.workPoints[j].id === Number(targetGroup)) {
            console.log('施工点位:', this.workPoints[j])
            wkPoint = this.workPoints[j]
            break
          }
        }
        if (wkPoint !== null) {
          this.addDrillToWorld(new THREE.Vector3(wkPoint.x, wkPoint.z, wkPoint.y), endPoint)
        }
      }
    },
    /**
     * 计算距离值
     */
    calDistance(x1, y1, z1, x2, y2, z2) {
      return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1))
    },

    /**
     * 添加钻孔实体
     * @param start
     * @param end
     * @param radius
     * @param material
     * @param list
     * @param group
     */
    addDrillMesh(start, end, radius, material, list) {
      let sp = start
      let ep = end
      let dis = Math.sqrt(((ep.x - sp.x) * (ep.x - sp.x)) + ((ep.y - sp.y) * (ep.y - sp.y)) + ((ep.z - sp.z) * (ep.z - sp.z)))
      const drillMesh = new Mesh(new THREE.CylinderGeometry(radius, radius, dis), material)
      // 设置位置
      drillMesh.position.x = (sp.x + ep.x) / 2
      drillMesh.position.y = (sp.y + ep.y) / 2
      drillMesh.position.z = (sp.z + ep.z) / 2
      let direction = new THREE.Vector3().subVectors(sp, ep).normalize()
      let currentDir = new THREE.Vector3(0, 1, 0)
      let quaternion = new THREE.Quaternion()
      // 旋转到对应方向
      quaternion.setFromUnitVectors(currentDir, direction)
      drillMesh.applyQuaternion(quaternion)
      drillMesh.updateMatrix()
      scene.add(drillMesh)
      //group.add(drillMesh)
      list.push(drillMesh)
    },

    /**
     * 添加钻孔范围实体(球+圆柱+球)
     * @param start 起点
     * @param end 终点
     * @param radius 半径
     * @param material 材质
     * @param showInOutPoint 是否显示起点终点
     * @param rootObject 参与计算的root
     */
    addDrillRangeMesh(start, end, radius, material, showInOutPoint, rootObject) {
      let sp = start
      let ep = end

      // 是否显示进煤点和出煤点
      if (showInOutPoint) {
        // 进煤点
        let calBottomPoint = new THREE.Mesh(new THREE.SphereGeometry(0.2), new THREE.MeshBasicMaterial({
          color: 0x0000ff//0xff0000设置材质颜色为红色
        }))
        calBottomPoint.position.set(sp.x, sp.y, sp.z)
        scene.add(calBottomPoint)

        // 出煤点
        let calTopPoint = new THREE.Mesh(new THREE.SphereGeometry(0.2), new THREE.MeshBasicMaterial({
          color: 0xff0000//0xff0000设置材质颜色为红色
        }))
        calTopPoint.position.set(ep.x, ep.y, ep.z)
        scene.add(calTopPoint)
      }

      let dis = Math.sqrt(((sp.x - ep.x) * (sp.x - ep.x)) + ((sp.y - ep.y) * (sp.y - ep.y)) + ((sp.z - ep.z) * (sp.z - ep.z)))
      /* const ccylinder = new Operation(new THREE.CylinderGeometry(radius, radius, dis), material)
      ccylinder.operation = SUBTRACTION
      ccylinder.position.y = 0
      const ssphere1 = new Operation(new THREE.SphereGeometry(radius), material)
      ssphere1.operation = SUBTRACTION
      ssphere1.position.y = -dis / 2
      const ssphere2 = new Operation(new THREE.SphereGeometry(radius), material)
      ssphere2.operation = SUBTRACTION
      ssphere2.position.y = dis / 2 */

      const capGeo = new Operation(new THREE.CapsuleGeometry(radius, dis, 6, 16), material)
      capGeo.operation = SUBTRACTION

      const drillGroup = new OperationGroup()
      //drillGroup.add(ccylinder, ssphere1, ssphere2)
      drillGroup.add(capGeo)

      drillGroup.position.x = (sp.x + ep.x) / 2
      drillGroup.position.y = (sp.y + ep.y) / 2
      drillGroup.position.z = (sp.z + ep.z) / 2
      let direction = new THREE.Vector3().subVectors(ep, sp).normalize()
      let currentDir = new THREE.Vector3(0, 1, 0)
      let quaternion = new THREE.Quaternion()
      quaternion.setFromUnitVectors(currentDir, direction)
      drillGroup.applyQuaternion(quaternion)
      drillGroup.updateMatrix()
      rootObject.add(drillGroup)
      //group.add(drillGroup)
      transformControls.attach(drillGroup)
    },

    // 补钻使用, 添加钻孔到模型中参与计算
    addDrillToWorld(startPoint, endPoint) {
      let sp1 = startPoint
      let sp = startPoint
      let ep = endPoint

      //console.log('sp:', sp, ',norm:', new THREE.Vector3(ep.x - sp.x, ep.y - sp.y, ep.z - sp.z).normalize())
      let rayToBottom = new THREE.Raycaster(new THREE.Vector3(sp.x, sp.y, sp.z), new THREE.Vector3(ep.x - sp.x, ep.y - sp.y, ep.z - sp.z).normalize())
      let bottomIntersect = rayToBottom.intersectObject(coalCubeObj)
      //console.log('coalCubeObj', coalCubeObj)
      //console.log('交底板', bottomIntersect)
      if (bottomIntersect.length > 0) {
        let bottom = bottomIntersect[0]  //取第一个点位, 入煤点
        sp = new THREE.Vector3(bottom.point.x, bottom.point.y, bottom.point.z)
        if (bottomIntersect.length > 1) {
          let top = bottomIntersect[1]   //取第二个点位, 出煤点
          ep = new THREE.Vector3(top.point.x, top.point.y, top.point.z)
        }

        // 添加补钻钻孔
        this.addDrillMesh(sp1, ep, 0.2, addDrillMat, this.addDrillMeshList, addDrillGroup)

        // 添加补孔钻孔范围
        this.addDrillRangeMesh(sp, ep, this.radius, addDrillRangeMat, true, partsRoot, addDrillRangeGroup)

        if (this.clickPoint != null) {
          this.clickPoint.geometry.dispose()
          this.clickPoint.parent.remove(this.clickPoint)
          this.clickPoint = null
        }
      }
    },
    // 手动补钻
    addDrill() {
      let sp = new THREE.Vector3(this.workPoints[this.startPointId].x, this.workPoints[this.startPointId].z, this.workPoints[this.startPointId].y)
      let ep = this.endPoint
      this.addDrillToWorld(sp, ep)
    },
    /**
     * 添加点击事件, 射线捕捉获取点击物体
     * @param event
     */
    onClick(event) {
      //event.preventDefault()
      if (params.drillDesMode) {
        let vet3 = new THREE.Vector3()//三维坐标对象
        mouse.x = ((event.clientX - document.getElementById('container').getBoundingClientRect().left) / document.getElementById('container').offsetWidth) * 2 - 1
        mouse.y = -((event.clientY - document.getElementById('container').getBoundingClientRect().top) / document.getElementById('container').offsetHeight) * 2 + 1
        vet3.set(
          mouse.x,
          mouse.y,
          1)
        vet3.unproject(camera)
        //console.log('camera.position:', camera.position, ',normalize:', vet3.sub(camera.position).normalize())
        let raycaster = new THREE.Raycaster(camera.position, vet3.sub(camera.position).normalize())
        let intersects = raycaster.intersectObjects(partMeshs)
        if (intersects.length > 0) {
          if (this.clickPoint != null) {
            this.clickPoint.geometry.dispose()
            this.clickPoint.parent.remove(this.clickPoint)
            this.clickPoint = null
          }
          let selected = intersects[0]//取第一个物体
          this.endPoint = selected.point
          console.log('坐标:' + selected.point.x.toFixed(4) + ',' + selected.point.y.toFixed(4) + ',' + selected.point.z.toFixed(4))
          this.clickPoint = new THREE.Mesh(new THREE.SphereGeometry(0.5), new THREE.MeshBasicMaterial({
            color: 0xff0000//0xff0000设置材质颜色为红色
          }))
          this.clickPoint.position.set(selected.point.x, selected.point.y, selected.point.z)
          scene.add(this.clickPoint)
        }
      } else {
        if (this.clickPoint != null) {
          this.clickPoint.geometry.dispose()
          this.clickPoint.parent.remove(this.clickPoint)
          this.clickPoint = null
        }
      }
    },
    rad2ang(rad) {
      return (180 / Math.PI) * rad
    },
    ang2rad(ang) {
      return (Math.PI / 180.) * ang
    },
    /**
     * 添加钻孔数据
     */
    addDrillingData() {
      this.drillingDataList.push(this.drillingData)
      this.drillingData = {}
    },
    /**
     * 计算上图
     */
    calModelDataLoading() {
      this.loadingInstance = this.$loading({
        lock: true,
        text: '正在渲染中,请稍后...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      //this.addDrill()
      setTimeout(() => {
        this.calModelData()
      }, 1000)
    },
    /**
     * 根据施工数据计算见煤点止煤点数据
     */
    calModelData() {
      let bd = this.baseData
      let base = 10
      /* for (let i = 0; i < this.groupNum; i++) {
        // 随机出一个孔号
        let ignoreHole = Math.floor(Math.random() * 8 + 1)
        for (let j = 0; j < this.tempList.length; j++) {
          // 每一组随机删除一个孔模拟空白带
          if (ignoreHole !== j) {
            let t = JSON.parse(JSON.stringify(this.tempList[j]))
            t.disNav = base + (i * 3)
            t.group = i
            this.drillingDataList.push(t)
          }
        }
        let drillPointX = bd.navX + ((base + (i * 3)) * Math.sin(this.ang2rad(bd.azimuth)))
        let drillPointY = bd.navY + ((base + (i * 3)) * Math.cos(this.ang2rad(bd.azimuth)))
        let drillPointZ = bd.navZ + ((base + (i * 3)) * Math.tan(this.ang2rad(bd.inclination)))
        //console.log('x,y,z:', drillPointX, drillPointY, drillPointZ)
        this.workPoints.push({ id: i, x: drillPointX, y: drillPointY, z: drillPointZ })
      } */

      for (let i = 0; i < this.drillingDataList.length; i++) {
        let dd = this.drillingDataList[i]
        if (dd.azimuth <= 360 && dd.azimuth > 270) {
          let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(360 - dd.azimuth)))
          let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(360 - dd.azimuth)))
          let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
          this.bottomPointList.push([xm, ym, zm, dd.group])
          let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(360 - dd.azimuth)))
          let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(360 - dd.azimuth)))
          let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
          this.topPointList.push([xn, yn, zn, dd.group])
          //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
        } else if (dd.azimuth <= 270 && dd.azimuth > 180) {
          let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth - 180)))
          let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth - 180)))
          let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
          this.bottomPointList.push([xm, ym, zm, dd.group])
          let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth - 180)))
          let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth - 180)))
          let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
          this.topPointList.push([xn, yn, zn, dd.group])
          //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
        } else if (dd.azimuth <= 180 && dd.azimuth > 90) {
          let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(180 - dd.azimuth)))
          let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(180 - dd.azimuth)))
          let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
          this.bottomPointList.push([xm, ym, zm, dd.group])
          let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(180 - dd.azimuth)))
          let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(180 - dd.azimuth)))
          let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
          this.topPointList.push([xn, yn, zn, dd.group])
          //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
        } else if (dd.azimuth <= 90) {
          let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth)))
          let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth)))
          let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
          this.bottomPointList.push([xm, ym, zm, dd.group])
          let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth)))
          let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth)))
          let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
          this.topPointList.push([xn, yn, zn, dd.group])
          //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
        }
      }
      this.init()
    },

    /**
     * 分割不相连部分
     * @param resultBottom
     * @returns {*[]}
     */
    calcParts(resultBottom) {
      let parts = []
      const posMap = {}
      const positions = resultBottom.geometry.attributes.position
      if (positions.count === 0) {
        return []
      }
      const normals = resultBottom.geometry.attributes.normal
      //const uvs = resultBottom.geometry.attributes.uv
      const cache = {}
      const hashify = (i) => {
        if (cache[i]) {
          return cache[i]
        }
        cache[i] = positions.getX(i).toFixed(2) + positions.getY(i).toFixed(2) + positions.getZ(i).toFixed(2)
        return cache[i]
      }
      for (let i = 0; i < positions.count; i += 3) {
        const vert1 = hashify(i)
        const vert2 = hashify(i + 1)
        const vert3 = hashify(i + 2)
        if (!posMap[vert1]) {
          posMap[vert1] = []
        }
        if (!posMap[vert2]) {
          posMap[vert2] = []
        }
        if (!posMap[vert3]) {
          posMap[vert3] = []
        }
        posMap[vert1].push(vert2, vert3)
        posMap[vert2].push(vert1, vert3)
        posMap[vert3].push(vert1, vert2)
      }
      let noPoses = {}
      while (Object.keys(noPoses).length < positions.count) {
        const availableIs = [] //Array(positions.length).fill()
        for (let i = 0; i < positions.count; i++) {
          if (!noPoses[hashify(i)]) {
            availableIs.push(i)
          }
        }
        if (availableIs.length === 0) {
          break
        }
        const randomPos = hashify(availableIs[Math.floor(Math.random() * availableIs.length)])
        const posesCollected = {}
        const posesToGoThrough = [randomPos]
        while (true) {
          if (posesToGoThrough.length === 0) {
            break
          }
          const pos = posesToGoThrough.pop()
          posesCollected[pos] = true
          const cs = posMap[pos]
          for (let i = 0; i < cs.length; i++) {
            if (!posesCollected[cs[i]]) {
              posesToGoThrough.push(cs[i])
            }
          }
        }
        const part = new THREE.BufferGeometry()
        const newPositions = []
        const newNormals = []
        //const newUvs = []
        const madeTries = {}
        for (let i = 0; i < positions.count; i += 3) {
          const vert1 = hashify(i)
          const vert2 = hashify(i + 1)
          const vert3 = hashify(i + 2)
          const combined = vert1 + vert2 + vert3
          if (posesCollected[vert1] && posesCollected[vert2] && posesCollected[vert3] && !madeTries[combined]) {
            madeTries[vert1 + vert2 + vert3] = true
            madeTries[vert2 + vert3 + vert1] = true
            madeTries[vert3 + vert1 + vert2] = true
            noPoses[vert1] = true
            noPoses[vert2] = true
            noPoses[vert3] = true
            newPositions.push(positions.getX(i), positions.getY(i), positions.getZ(i))
            newPositions.push(positions.getX(i + 1), positions.getY(i + 1), positions.getZ(i + 1))
            newPositions.push(positions.getX(i + 2), positions.getY(i + 2), positions.getZ(i + 2))
            newNormals.push(normals.getX(i), normals.getY(i), normals.getZ(i))
            newNormals.push(normals.getX(i + 1), normals.getY(i + 1), normals.getZ(i + 1))
            newNormals.push(normals.getX(i + 2), normals.getY(i + 2), normals.getZ(i + 2))
            //newUvs.push(uvs.getX(i), uvs.getY(i))
            //newUvs.push(uvs.getX(i + 1), uvs.getY(i + 1))
            //newUvs.push(uvs.getX(i + 2), uvs.getY(i + 2))
          }
        }
        part.setAttribute('position', new THREE.BufferAttribute(new Float32Array(newPositions), 3))
        part.setAttribute('normal', new THREE.BufferAttribute(new Float32Array(newNormals), 3))
        //part.setAttribute('uv', new THREE.BufferAttribute(new Float32Array(newUvs), 3))
        parts.push(part)
      }
      return parts
    },

    /**
     * 计算施工点位
     */
    calWorkPoints() {

    },

    /**
     * 初始化
     * @returns {Promise<void>}
     */
    async init() {
      const bgColor = 0x111111
      textOutput = document.getElementById('output')
      outputContainer = document.getElementById('container')
      tGUIContainer = document.getElementById('tGUI')
      // 渲染初始化
      renderer = new THREE.WebGLRenderer({ antialias: true })
      renderer.setPixelRatio(window.devicePixelRatio)
      renderer.setSize(1920, 1080)
      //renderer.setSize(window.innerWidth, window.innerHeight)
      renderer.setClearColor(bgColor, 1)
      renderer.outputEncoding = THREE.sRGBEncoding
      outputContainer.appendChild(renderer.domElement)

      // 场景初始化
      scene = new THREE.Scene()

      // 光照初始化
      light = new THREE.DirectionalLight(0xffffff, 1)
      light.position.set(-1, 2, 3)
      scene.add(light, light.target)
      scene.add(new THREE.AmbientLight(0xb0bec5, 0.1))

      // 相机初始化
      camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 50)
      camera.position.set(50, 50, 50).multiplyScalar(2)
      camera.far = 1000
      camera.updateProjectionMatrix()

      // 控制器
      controls = new OrbitControls(camera, renderer.domElement)
      transformControls = new TransformControls(camera, renderer.domElement)
      transformControls.setSize(0.75)
      transformControls.addEventListener('dragging-changed', e => {
        controls.enabled = !e.value
      })
      transformControls.addEventListener('objectChange', e => {
        if (params.snap) {
          const o = transformControls.object
          o.position.x = Math.floor(o.position.x * 1e1) * 1e-1
          o.position.y = Math.floor(o.position.y * 1e1) * 1e-1
          o.position.z = Math.floor(o.position.z * 1e1) * 1e-1
        }
      })
      scene.add(transformControls)

      // 坐标轴
      axesHelper = new THREE.AxesHelper(150)
      scene.add(axesHelper)

      // 初始化钻孔,钻杆组
      originDrillGroup = new THREE.Group()
      originDrillRangeGroup = new THREE.Group()
      addDrillGroup = new THREE.Group()
      addDrillRangeGroup = new THREE.Group()
      scene.add(originDrillGroup)
      scene.add(originDrillRangeGroup)
      scene.add(addDrillGroup)
      scene.add(addDrillRangeGroup)

      // 射线捕捉初始化
      document.body.addEventListener('click', this.onClick, false)

      // 原始钻孔计算初始化
      initCSGEvaluator = new Evaluator()
      initCSGEvaluator.attributes = ['position', 'normal']
      initCSGEvaluator.useGroups = false

      // 补钻钻孔计算初始化
      addCSGEvaluator = new Evaluator()
      addCSGEvaluator.attributes = ['position', 'normal']
      addCSGEvaluator.useGroups = false

      // 材质初始化
      this.createMat()

      // 结果实体材质
      /* wireframeObject = new THREE.Mesh(undefined, new THREE.MeshBasicMaterial({ color: 0, wireframe: true }))
      wireframeObject.material.color.set(0xffc400).convertSRGBToLinear().multiplyScalar(0.1)
      scene.add(wireframeObject) */

      // 优化空白带实体
      rootPartsObject = new THREE.Mesh(undefined, new THREE.MeshBasicMaterial({ color: 0, wireframe: false }))
      rootPartsObject.material.color.set(0xffc400).convertSRGBToLinear().multiplyScalar(0.1)
      scene.add(rootPartsObject)

      this.createCoalPoints()
      this.createWorkPoints()
      this.createRoad()
      this.createOriginDrill()

      initRoot = new Operation(coalGeometry, brushMat)
      scene.add(initRoot)

      this.createDefaultOp(initRoot)

      for (let i = 0; i < this.points3DBottom.length; i++) {
        // 添加原始钻孔抽采范围, 并计算
        this.addDrillRangeMesh(this.points3DBottom[i], this.points3DTop[i], this.radius, originDrillRangeMat, false, initRoot, originDrillRangeGroup)
      }
      this.createGUI()

      let startTime = window.performance.now()
      initResult = initCSGEvaluator.evaluateHierarchy(initRoot)
      let deltaTime = window.performance.now() - startTime
      console.log('初次渲染时长:' + deltaTime)
      //result.material = resultGridMat
      /*scene.add(result)
       wireframeObject.geometry = result.geometry
      wireframeObject.visible = false */
      //wireframeObject.visible = params.wireframe

      this.render()
    },

    /**
     * 渲染
     */
    render() {
      requestAnimationFrame(this.render)
      const startTime = window.performance.now()

      // 原始钻孔抽采范围
      if (params.showOriginDrillRange) {
        originDrillRangeMat.opacity = 0.15
      } else {
        originDrillRangeMat.opacity = 0
      }

      if (params.transparentBrushes) {
        brushMat.depthWrite = false
        brushMat.transparent = true
        brushMat.opacity = 0

        addDrillMat.depthWrite = false
        addDrillMat.transparent = true
        addDrillMat.opacity = 0.5

        originDrillMat.depthWrite = false
        originDrillMat.transparent = true
        originDrillMat.opacity = 0.5
      } else {
        brushMat.depthWrite = true
        brushMat.transparent = false
        brushMat.opacity = 0

        addDrillMat.depthWrite = true
        addDrillMat.transparent = false
        addDrillMat.opacity = 1

        originDrillMat.depthWrite = true
        originDrillMat.transparent = false
        originDrillMat.opacity = 1
      }

      if (params.showCoalMesh) {
        coalBottomMeshMat.wireframe = true
        coalBottomMeshMat.side = THREE.DoubleSide
        coalBottomMeshMat.depthWrite = true
        coalBottomMeshMat.transparent = false
        coalBottomMeshMat.opacity = 1

        coalTopMeshMat.wireframe = true
        coalTopMeshMat.side = THREE.DoubleSide
        coalTopMeshMat.depthWrite = true
        coalTopMeshMat.transparent = false
        coalTopMeshMat.opacity = 1
      } else {
        coalBottomMeshMat.wireframe = false
        coalBottomMeshMat.side = THREE.DoubleSide
        coalBottomMeshMat.depthWrite = true
        coalBottomMeshMat.transparent = true
        coalBottomMeshMat.opacity = 1

        coalTopMeshMat.wireframe = false
        coalTopMeshMat.side = THREE.DoubleSide
        coalTopMeshMat.depthWrite = true
        coalTopMeshMat.transparent = true
        coalTopMeshMat.opacity = 1
      }

      if (params.showOriginDrill) {
        originDrillMat.side = THREE.DoubleSide
        originDrillMat.depthWrite = true
        originDrillMat.transparent = false
        originDrillMat.opacity = 1
      } else {
        originDrillMat.side = THREE.DoubleSide
        originDrillMat.depthWrite = true
        originDrillMat.transparent = true
        originDrillMat.opacity = 0
      }

      if (params.showDrillInCoal) {
        drillInCoalMat.transparent = false
        drillInCoalMat.opacity = 1
      } else {
        drillInCoalMat.transparent = true
        drillInCoalMat.opacity = 0
      }

      /* if (initResult) {
        initResult.geometry.dispose()
        initResult.material.dispose()
        initResult.parent.remove(initResult)
      } */

      if (partsResult) {
        this.cleanCache(partsResult)
      }

      // 计算结果
      /* result = csgEvaluator.evaluateHierarchy(root)
      //result.material = resultGridMat
      scene.add(result)
      wireframeObject.geometry = result.geometry
      wireframeObject.visible = params.wireframe */
      coalCubeObj.visible = params.showCoalCube

      // 优化
      if (!partsRoot) {

        partsRoot = new Operation(initResult.geometry, resultGridMat)
        scene.add(partsRoot)
        this.createDefaultOp(partsRoot)

        partsResult = addCSGEvaluator.evaluateHierarchy(partsRoot)
        partsResult.material = resultGridMat
        scene.add(partsResult)
        rootPartsObject.geometry = partsResult.geometry
        partsRoot.visible = true
        rootPartsObject.visible = true

      } else {
        partsResult = addCSGEvaluator.evaluateHierarchy(partsRoot)
        partsResult.material = resultGridMat
        scene.add(partsResult)
        rootPartsObject.geometry = partsResult.geometry
        partsRoot.visible = params.showEmptyBand
        rootPartsObject.visible = params.showEmptyBand
      }

      const deltaTime = window.performance.now() - startTime
      if (deltaTime >= 10) {
        console.log('计算耗时:' + deltaTime)
      }
      if (this.showOutCube !== params.showOutCube || deltaTime >= 10 || this.autoAdd !== false) {
        this.showOutCube = params.showOutCube
        //console.log('result', rootPartsObject)
        //wireframeObject.geometry.computeBoundingBox()
        //console.log('position', wireframeObject.geometry.attributes.position)
        //console.log('boundingBox', wireframeObject.geometry.boundingBox)
        rootPartsObject.geometry.computeBoundingBox()
        console.log('position', rootPartsObject.geometry.attributes.position)
        console.log('boundingBox', rootPartsObject.geometry.boundingBox)

        // 清除实体
        if (partMeshs.length > 0) {
          for (let i = 0; i < partMeshs.length; i++) {
            this.cleanCache(partMeshs[i])
            //partMeshs[i].geometry.dispose()
            //partMeshs[i].parent.remove(partMeshs[i])
          }
        }
        // 清除partCube实体
        if (partCubes.length > 0) {
          for (let i = 0; i < partCubes.length; i++) {
            this.cleanCache(partCubes[i])
            //partCubes[i].geometry.dispose()
            //partCubes[i].parent.remove(partCubes[i])
          }
        }

        partMeshs = []
        partCubes = []
        this.emptyBandList = []
        // 分割不相连的部分
        //let ppparts = this.calcParts(wireframeObject)
        let ppparts = this.calcParts(rootPartsObject)
        let nIndex = 0
        for (let i = 0; i < ppparts.length; i++) {
          // 筛除只有三个点的部分
          if (ppparts[i].attributes.position.count > 3) {
            this.loading = true

            // 先计算外部包围盒
            // 外部包围盒材质
            let r = Math.random()
            let cubeMaterial = new THREE.MeshLambertMaterial({
              color: 0xFFFFFF * r,
              transparent: true,
              opacity: params.showOutCube ? 0.55 : 0
            })
            this.emptyObjPoints[nIndex] = []
            const positions = ppparts[i].getAttribute('position')
            //console.log('positions', positions)
            for (let j = 0; j < positions.count; j++) {
              let x = positions.getX(j)
              let y = positions.getY(j)
              let z = positions.getZ(j)
              this.emptyObjPoints[nIndex].push(new YUKA.Vector3(x, y, z))
            }
            let obb = null
            try {
              obb = new YUKA.OBB().fromPoints(this.emptyObjPoints[nIndex])
            } catch (e) {
              obb = null
              console.log('生成包围体失败,无效实体:' + i, this.emptyObjPoints[nIndex])
            }
            if (obb !== null) {
              // 视为有效, 创建并添加空白带实体
              let vol = computeMeshVolume(ppparts[i])
              // 分割后不同部分使用不同的颜色显示
              let partMaterial = new THREE.MeshLambertMaterial({
                //color:color.fromArray(colorArray)
                color: 0xFFFFFF * r
              })
              let partMesh = new Mesh(ppparts[i], partMaterial)
              console.log('index:' + nIndex + ',有效实体' + i + ':' + partMesh.uuid + ',' + ',体积:', vol)
              // 空白带实体
              partMeshs.push(partMesh)

              let helper = createOBBHelper(obb, cubeMaterial)
              // 空白带包围块
              partCubes.push(helper)
              //console.log('partCube', helper)

              // 表格信息List
              this.emptyBandList.push({
                uuid: partMesh.uuid,
                position: '(' + helper.position.x.toFixed(2) + ',' + helper.position.y.toFixed(2) + ',' + helper.position.z.toFixed(2) + ')',
                volume: vol
              })

              nIndex++
            }

          } else {
            //console.log('完全无效实体' + i)
          }
        }
        if (this.autoAdd === true && nIndex > 0 && this.autoIndex < 5) {
          console.log('自动补钻循环次数:' + this.autoIndex)
          this.autoAddDrill()
          this.autoIndex++
        } else {
          this.autoAdd = false
          this.autoIndex = 1
        }
        //let vol = computeMeshVolume(wireframeObject)
        let vol = computeMeshVolume(rootPartsObject)
        console.log('全部体积:', vol)

        // 渲染part实体
        for (let i = 0; i < partMeshs.length; i++) {
          scene.add(partMeshs[i])
        }

        // 渲染partCube实体
        for (let i = 0; i < partCubes.length; i++) {
          scene.add(partCubes[i])
        }

        if (this.loadingInstance !== null && this.autoAdd === false) {
          this.loadingInstance.close()
          console.log('结束Loading')
        }
      }

      for (let i = 0; i < partMeshs.length; i++) {
        partMeshs[i].visible = params.showEmptyBand
        partCubes[i].visible = params.showOutCube
      }

      textOutput.innerText = `${deltaTime.toFixed(3)}ms`

      transformControls.enabled = params.displayControls
      transformControls.visible = params.displayControls

      //initResult.visible = params.showOriginDrillRange
      //partsResult.visible = params.display !== 'BRUSHES'
      //initRoot.visible = (params.display !== 'RESULT')
      initRoot.visible = params.showOriginDrillRange
      partsRoot.visible = params.showOriginEmptyBand
      originDrillRangeGroup.visible = params.showOriginDrillRange
      originDrillGroup.visible = params.showOriginDrill
      addDrillRangeGroup.visible = params.showAddDrillRange
      addDrillGroup.visible = params.showAddDrill
      renderer.render(scene, camera)
    },

    /**
     * 清除缓存
     * @param obj
     */
    cleanCache(obj) {
      obj.geometry.dispose()
      obj.material.dispose()
      obj.parent.remove(obj)
    },

    /**
     * 实时更新抽采半径
     * @param bursh
     * @param num
     */
    updateRadius(bursh, num) {
      bursh.geometry.dispose()
    },

    /**
     * 创建煤层点位
     */
    createCoalPoints() {

      let pointsAll = []

      for (let i = 0; i < this.bottomPointList.length; i++) {
        this.points3DBottom.push(new THREE.Vector3(this.bottomPointList[i][0], this.bottomPointList[i][2], this.bottomPointList[i][1]))
        pointsAll.push(new THREE.Vector3(this.bottomPointList[i][0], this.bottomPointList[i][2], this.bottomPointList[i][1]))
      }
      for (let i = 0; i < this.topPointList.length; i++) {
        this.points3DTop.push(new THREE.Vector3(this.topPointList[i][0], this.topPointList[i][2], this.topPointList[i][1]))
        pointsAll.push(new THREE.Vector3(this.topPointList[i][0], this.topPointList[i][2], this.topPointList[i][1]))
      }

      // 添加上下钻孔点位
      let coalBottomGeom = new THREE.BufferGeometry().setFromPoints(this.points3DBottom)
      let coalTopGeom = new THREE.BufferGeometry().setFromPoints(this.points3DTop)
      let bottomCloud = new THREE.Points(
        coalBottomGeom,
        new THREE.PointsMaterial({ color: 0x99ccff, size: 0.5 })
      )
      let topCloud = new THREE.Points(
        coalTopGeom,
        new THREE.PointsMaterial({ color: 0x99cc00, size: 0.5 })
      )
      /* scene.add(cloud1)
      scene.add(cloud2) */
      let pointGeo = new THREE.SphereGeometry(0.5)
      let pointMat = new THREE.MeshBasicMaterial({ color: 0x99cc00 })
      for (let i = 0; i < this.points3DBottom.length; i++) {
        let point = new THREE.Mesh(pointGeo, pointMat)
        point.position.x = this.points3DBottom.x
        point.position.y = this.points3DBottom.y
        point.position.z = this.points3DBottom.z
        scene.add(point)
      }

      for (let i = 0; i < this.points3DTop.length; i++) {
        let point = new THREE.Mesh(pointGeo, pointMat)
        point.position.x = this.points3DTop.x
        point.position.y = this.points3DTop.y
        point.position.z = this.points3DTop.z
        scene.add(point)
      }

      let indexDelaunay1 = Delaunator.from(
        this.points3DBottom.map(v => {
          return [v.x, v.z]
        })
      )

      let indexDelaunay2 = Delaunator.from(
        this.points3DTop.map(v => {
          return [v.x, v.z]
        })
      )

      let meshIndex1 = [] // delaunay index => three.js index
      for (let i = 0; i < indexDelaunay1.triangles.length; i++) {
        meshIndex1.push(indexDelaunay1.triangles[i])
      }

      let meshIndex2 = [] // delaunay index => three.js index
      for (let i = 0; i < indexDelaunay2.triangles.length; i++) {
        meshIndex2.push(indexDelaunay2.triangles[i])
      }

      coalBottomGeom.setIndex(meshIndex1) // add three.js index to the existing geometry
      coalBottomGeom.computeVertexNormals()
      let coalBottomMesh = new THREE.Mesh(
        coalBottomGeom, // re-use the existing geometry
        coalBottomMeshMat
      )
      scene.add(coalBottomMesh)

      const bottomNormal = coalBottomMesh.geometry.attributes.normal
      if (!bottomNormal) {
      } else {
        let helper = new VertexNormalsHelper(coalBottomMesh, 1, 0xfff000)
        scene.add(helper)
      }

      coalTopGeom.setIndex(meshIndex2) // add three.js index to the existing geometry
      coalTopGeom.computeVertexNormals()
      let coalTopMesh = new THREE.Mesh(
        coalTopGeom, // re-use the existing geometry
        coalTopMeshMat
      )
      scene.add(coalTopMesh)

      const topNormal = coalTopMesh.geometry.attributes.normal
      if (!topNormal) {
      } else {
        const helper = new VertexNormalsHelper(coalTopMesh, 1, 0xfff000)
        scene.add(helper)
      }

      coalGeometry = new ConvexGeometry(pointsAll)
      coalCubeObj = new Mesh(coalGeometry, new THREE.MeshBasicMaterial({
        side: THREE.DoubleSide,
        color: 0x718c77,
        opacity: 0.2,
        transparent: true
      }))
      scene.add(coalCubeObj)
    },
    /**
     * 添加施工点位
     */
    createWorkPoints() {
      // 添加施工点位
      let workPoints3d = []
      console.log('workPoints', this.workPoints)
      for (let i = 0; i < this.workPoints.length; i++) {
        //workPoints3d.push(new THREE.Vector3(this.workPoints[i]['x'], this.workPoints[i]['z'], this.workPoints[i]['y']))
        let wkGeo = new THREE.BoxGeometry(0.5, 0.5, 0.5)
        let wkMater = new THREE.MeshBasicMaterial({ color: 0xffffff })
        let wkMesh = new THREE.Mesh(wkGeo, wkMater)
        wkMesh.position.x = this.workPoints[i]['x']
        wkMesh.position.y = this.workPoints[i]['z']
        wkMesh.position.z = this.workPoints[i]['y']
        this.workPointsMesh.push(wkMesh)
        scene.add(wkMesh)
      }
    },
    /**
     * 创建巷道
     */
    createRoad() {
      const roadShape = new THREE.Shape()
        .moveTo(-2, -3)
        .lineTo(-2, 0)
        .absarc(0, 0, 2, Math.PI, 0, true)
        .lineTo(2, -3).lineTo(-2, -3)

      const roadShapeHole = new THREE.Shape()
        .moveTo(-1.8, -2.5)
        .lineTo(-1.8, 0)
        .absarc(0, 0, 1.8, Math.PI, 0, true)
        .lineTo(1.8, -2.5).lineTo(-1.8, -2.5)

      roadShape.holes.push(roadShapeHole)
      let startWP = new THREE.Vector3(this.workPoints[0]['x'], this.workPoints[0]['z'], this.workPoints[0]['y'])
      let endWP = new THREE.Vector3(this.workPoints[this.workPoints.length - 1]['x'], this.workPoints[this.workPoints.length - 1]['z'], this.workPoints[this.workPoints.length - 1]['y'])
      let roadLen = this.calDistance(startWP.x, startWP.y, startWP.z, endWP.x, endWP.y, endWP.z)
      const extrudeSettings = {
        depth: roadLen,
        bevelEnabled: true,
        bevelSegments: 1,
        steps: 1,
        bevelSize: 0,
        bevelThickness: 1
      }
      const roadGeometry = new THREE.ExtrudeGeometry(roadShape, extrudeSettings)

      const roadMesh = new THREE.Mesh(roadGeometry, roadMat)
      roadMesh.position.set(this.workPoints[0]['x'], this.workPoints[0]['z'], this.workPoints[0]['y'])

      let direction = new THREE.Vector3().subVectors(endWP, startWP).normalize()
      let currentDir = new THREE.Vector3(0, 0, 1)
      let quaternion = new THREE.Quaternion()
      quaternion.setFromUnitVectors(currentDir, direction)
      roadMesh.applyQuaternion(quaternion)
      roadMesh.updateMatrix()
      //roadMesh.rotateY(0.01)

      scene.add(roadMesh)
    },
    /**
     * 创建GUI面板
     */
    createGUI() {
      // gui
      gui = new GUI({ autoPlace: false })
      tGUIContainer.append(gui.domElement)
      gui.add(params, 'wireframe').name('显示线框')
      gui.add(params, 'snap')
      gui.add(params, 'displayControls').name('显示钻孔控制组件')
      gui.add(params, 'showOriginDrill').name('显示原始钻孔')
      gui.add(params, 'showDrillInCoal').name('显示煤孔段')
      gui.add(params, 'showOriginDrillRange').name('显示原始钻孔抽采范围')
      gui.add(params, 'showAddDrill').name('显示补钻钻孔')
      gui.add(params, 'showAddDrillRange').name('显示补钻钻孔抽采范围')
      gui.add(params, 'showOutCube').name('显示空白带外部包围体')
      gui.add(params, 'showCoalCube').name('显示原始煤层信息')
      gui.add(params, 'showOriginEmptyBand').name('显示原始空白带实体')
      gui.add(params, 'showEmptyBand').name('显示空白带实体')
      gui.add(params, 'showCoalMesh').name('显示煤层顶底板网格')
      gui.add(params, 'transparentBrushes').name('钻孔透明显示')
      gui.add(params, 'drillDesMode').name('开启补钻模式')
      gui.add(params, 'display', ['OVERLAY', 'BRUSHES', 'RESULT']).name('显示内容')

      window.addEventListener('resize', function() {

        camera.aspect = window.innerWidth / window.innerHeight
        camera.updateProjectionMatrix()

        renderer.setSize(window.innerWidth, window.innerHeight)

      }, false)

      window.addEventListener('keydown', function(e) {
        switch (e.code) {
          case 'KeyW':
            transformControls.setMode('translate')
            break
          case 'KeyE':
            transformControls.setMode('rotate')
            break
          case 'KeyR':
            transformControls.setMode('scale')
            break
        }
      })
    },
    /**
     * 添加原始钻孔
     */
    createOriginDrill() {
      console.log('-------------------------顶板点位-------------------------------')
      // 添加原始钻孔
      for (let i = 0; i < this.topPointList.length; i++) {
        let group = this.topPointList[i][3]
        let sp = new THREE.Vector3(this.workPoints[group]['x'], this.workPoints[group]['z'], this.workPoints[group]['y'])
        let ep0 = new THREE.Vector3(this.bottomPointList[i][0], this.bottomPointList[i][2], this.bottomPointList[i][1])
        let ep1 = new THREE.Vector3(this.topPointList[i][0], this.topPointList[i][2], this.topPointList[i][1])
        this.addDrillMesh(sp, ep0, 0.2, originDrillMat, this.originDrillMeshList, originDrillGroup)
        this.addDrillMesh(ep0, ep1, 0.2, drillInCoalMat, this.originDrillInCoalMeshList, originDrillGroup)
        let botV = new THREE.Vector3(ep1.x - ep0.x, ep1.y - ep0.y, ep1.z - ep0.z).normalize()
        let topV = new THREE.Vector3(ep0.x - ep1.x, ep0.y - ep1.y, ep0.z - ep1.z).normalize()
        // 底板
        console.log(ep0.x, ep0.y, ep0.z)
        // 顶板
        //console.log(ep1.x, ep1.y, ep1.z)
        //console.log(ep0.x, ep0.y, ep0.z, topV.x, topV.y, topV.z)
        //console.log(ep1.x, ep1.y, ep1.z, botV.x, botV.y, botV.z)
      }
      console.log('--------------------------------------------------------')
    },
    /**
     * 创建默认计算体
     * @param rootObj
     */
    createDefaultOp(rootObj) {
      // 添加默认计算体, 防止出错
      const defaultOperation = new Operation(new THREE.SphereGeometry(0.1), addDrillRangeMat)
      defaultOperation.operation = SUBTRACTION
      defaultOperation.position.y = 0
      const defaultGroup = new OperationGroup()
      defaultGroup.add(defaultOperation)
      defaultGroup.position.x = 0
      defaultGroup.position.y = 0
      defaultGroup.position.z = 0
      rootObj.add(defaultGroup)
    },
    /**
     * 初始化材质
     */
    createMat() {
      // 计算后空白带材质
      brushMat = new GridMaterial()
      brushMat.side = THREE.DoubleSide
      brushMat.polygonOffset = true
      brushMat.polygonOffsetFactor = 2
      brushMat.polygonOffsetUnits = 1
      brushMat.opacity = 0.15
      brushMat.transparent = true
      brushMat.depthWrite = false
      brushMat.color.set(0xffc400).convertSRGBToLinear()

      // 原始钻孔材质
      originDrillMat = brushMat.clone()
      originDrillMat.transparent = true
      originDrillMat.opacity = 0.5
      originDrillMat.color.set(0xffc400).convertSRGBToLinear()

      // 原始钻孔抽采范围材质
      originDrillRangeMat = brushMat.clone()
      originDrillRangeMat.transparent = true
      originDrillRangeMat.opacity = 0.15
      originDrillRangeMat.color.set(0xffc400).convertSRGBToLinear()

      // 补孔钻孔材质
      addDrillMat = brushMat.clone()
      addDrillMat.transparent = true
      addDrillMat.opacity = 0.5
      addDrillMat.color.set(0x00f0f0).convertSRGBToLinear()

      // 补孔抽采范围材质
      addDrillRangeMat = brushMat.clone()
      addDrillRangeMat.transparent = true
      addDrillRangeMat.opacity = 0.15
      addDrillRangeMat.color.set(0x00f0f0).convertSRGBToLinear()

      // 巷道材质
      roadMat = new THREE.MeshPhongMaterial()
      roadMat.side = THREE.DoubleSide
      roadMat.transparent = true
      roadMat.opacity = 0.5
      roadMat.color.set(0xb7af7e).convertSRGBToLinear()

      // 结果材质
      resultGridMat = brushMat.clone()
      resultGridMat.transparent = true
      resultGridMat.opacity = 0
      resultGridMat.depthWrite = true
      resultGridMat.polygonOffsetFactor = 1
      resultGridMat.polygonOffsetUnits = 1
      resultGridMat.color.set(0xffffff).convertSRGBToLinear()

      // 煤孔段材质
      drillInCoalMat = brushMat.clone()
      drillInCoalMat.transparent = false
      drillInCoalMat.opacity = 1
      drillInCoalMat.depthWrite = true
      drillInCoalMat.polygonOffsetFactor = 1
      drillInCoalMat.polygonOffsetUnits = 1
      drillInCoalMat.color.set(0xffffff).convertSRGBToLinear()

      // 煤层顶底板点位材质
      coalTopMeshMat = new THREE.MeshLambertMaterial({ color: 0x99cc00, wireframe: true })
      coalTopMeshMat.side = THREE.DoubleSide
      coalTopMeshMat.depthWrite = true
      coalTopMeshMat.transparent = true
      coalTopMeshMat.opacity = 0.2

      coalBottomMeshMat = new THREE.MeshLambertMaterial({ color: 0x99ccff, wireframe: true })
      coalBottomMeshMat.side = THREE.DoubleSide
      coalBottomMeshMat.depthWrite = true
      coalBottomMeshMat.transparent = true
      coalBottomMeshMat.opacity = 0.2
    }
  }
  /* //三维顶点数据
      var arr = [
        0, 0, 0, //顶点1坐标
        100, 0, 9, //顶点2坐标
        150, 25, 0, //顶点3坐标
        100, 50, 16, //顶点4坐标
        0, 50, 0, //顶点5坐标
      ]
      var geometry = new THREE.BufferGeometry();
      //一个五边形多边形的顶点位置数据
      var vertices = new Float32Array(arr);
      // 创建属性缓冲区对象
      var attribue = new THREE.BufferAttribute(vertices, 3); //3个为一组
      // 设置几何体attributes属性的位置position属性
      geometry.attributes.position = attribue

      // 三角形顶点索引计算
      var trianglesIndex = earcut(arr,null,3);

      // Uint16Array类型数组创建顶点索引数据
      var indexes = new Uint16Array(trianglesIndex)
      // 索引数据赋值给几何体的index属性
      geometry.index = new THREE.BufferAttribute(indexes, 1); //1个为一组
      // 不执行computeVertexNormals，没有顶点法向量数据
      geometry.computeVertexNormals(); //不计算法线，表面比较暗，计算了比较亮，
      console.log(`查看几何体顶点数据`, geometry);
      //材质对象
      var material = new THREE.MeshLambertMaterial({
        color: 0x0000ff, //三角面颜色
        side: THREE.DoubleSide, //两面可见
        // 查看剖分的三角形效果
        wireframe:true,
      });
      var mesh = new THREE.Mesh(geometry, material); //网格模型对象Mesh
      scene.add(mesh); //网格模型添加到场景中 */
}
/* { id: 1, inclination: 68, azimuth: 290, disNav: 10, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        { id: 2, inclination: 73, azimuth: 290, disNav: 10, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.5 },
        { id: 3, inclination: 79, azimuth: 290, disNav: 10, disTop: 0.4, disCenter: 1.2, seeLen: 19.7, overLen: 24.9 },
        { id: 4, inclination: 84, azimuth: 290, disNav: 10, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.3 },
        { id: 5, inclination: 90, azimuth: 290, disNav: 10, disTop: 0, disCenter: 0, seeLen: 19, overLen: 24 },
        { id: 6, inclination: 84, azimuth: 100, disNav: 10, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.3 },
        { id: 7, inclination: 79, azimuth: 100, disNav: 10, disTop: 0.4, disCenter: 1.2, seeLen: 19.7, overLen: 24.9 },
        { id: 8,inclination: 73,azimuth: 100,disNav: 10,disTop: 0.8,disCenter: 1.6,seeLen: 20.7,overLen: 25.9},
        { id: 9, inclination: 68, azimuth: 100, disNav: 10, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        { id: 10, inclination: 68, azimuth: 290, disNav: 14, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        { id: 11, inclination: 73, azimuth: 290, disNav: 14, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { id: 13, inclination: 84, azimuth: 290, disNav: 14, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.3 },
        { id: 14, inclination: 90, azimuth: 290, disNav: 14, disTop: 0, disCenter: 0, seeLen: 19, overLen: 24 },
        { id: 15, inclination: 84, azimuth: 100, disNav: 14, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.3 },
        { id: 16, inclination: 79, azimuth: 100, disNav: 14, disTop: 0.4, disCenter: 1.2, seeLen: 19.7, overLen: 24.9 },
        { id: 17, inclination: 73, azimuth: 100, disNav: 14, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { id: 18, inclination: 68, azimuth: 100, disNav: 14, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        ///
        { id: 19, inclination: 68, azimuth: 290, disNav: 18, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        { id: 20, inclination: 73, azimuth: 290, disNav: 18, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { id: 22, inclination: 84, azimuth: 290, disNav: 18, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.3 },
        { id: 23, inclination: 90, azimuth: 290, disNav: 18, disTop: 0, disCenter: 0, seeLen: 19, overLen: 24 },
        { id: 24, inclination: 84, azimuth: 100, disNav: 18, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.8 },
        { id: 25, inclination: 79, azimuth: 100, disNav: 18, disTop: 0.4, disCenter: 1.2, seeLen: 19.7, overLen: 24.9 },
        { id: 26, inclination: 73, azimuth: 100, disNav: 18, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { id: 27, inclination: 68, azimuth: 100, disNav: 18, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        ///
        { id: 19, inclination: 68, azimuth: 290, disNav: 22, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        { id: 20, inclination: 73, azimuth: 290, disNav: 22, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { id: 22, inclination: 84, azimuth: 290, disNav: 22, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.3 },
        { id: 23, inclination: 90, azimuth: 290, disNav: 22, disTop: 0, disCenter: 0, seeLen: 19, overLen: 24 },
        { id: 24, inclination: 84, azimuth: 100, disNav: 22, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.8 },
        { id: 25, inclination: 79, azimuth: 100, disNav: 22, disTop: 0.4, disCenter: 1.2, seeLen: 19.7, overLen: 24.9 },
        { id: 26, inclination: 73, azimuth: 100, disNav: 22, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { id: 27, inclination: 68, azimuth: 100, disNav: 22, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        ///
        { id: 19, inclination: 68, azimuth: 290, disNav: 26, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        { id: 20, inclination: 73, azimuth: 290, disNav: 26, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { id: 22, inclination: 84, azimuth: 290, disNav: 26, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.3 },
        { id: 23, inclination: 90, azimuth: 290, disNav: 26, disTop: 0, disCenter: 0, seeLen: 19, overLen: 24 },
        { id: 24, inclination: 84, azimuth: 100, disNav: 26, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.8 },
        { id: 25, inclination: 79, azimuth: 100, disNav: 26, disTop: 0.4, disCenter: 1.2, seeLen: 19.7, overLen: 24.9 },
        { id: 26, inclination: 73, azimuth: 100, disNav: 26, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { id: 27, inclination: 68, azimuth: 100, disNav: 26, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        ///
        { id: 19, inclination: 68, azimuth: 290, disNav: 30, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 },
        { id: 20, inclination: 73, azimuth: 290, disNav: 30, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { id: 22, inclination: 84, azimuth: 290, disNav: 30, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.3 },
        { id: 23, inclination: 90, azimuth: 290, disNav: 30, disTop: 0, disCenter: 0, seeLen: 19, overLen: 24 },
        { id: 24, inclination: 84, azimuth: 100, disNav: 30, disTop: 0.1, disCenter: 0.6, seeLen: 19.2, overLen: 24.8 },
        { id: 25, inclination: 79, azimuth: 100, disNav: 30, disTop: 0.4, disCenter: 1.2, seeLen: 19.7, overLen: 24.9 },
        { id: 26, inclination: 73, azimuth: 100, disNav: 30, disTop: 0.8, disCenter: 1.6, seeLen: 20.7, overLen: 25.9 },
        { id: 27, inclination: 68, azimuth: 100, disNav: 30, disTop: 1.3, disCenter: 1.9, seeLen: 21.9, overLen: 27.3 } */

/* let dis = Math.sqrt(((points3d1[i].x - points3d2[i].x) * (points3d1[i].x - points3d2[i].x)) + ((points3d1[i].y - points3d2[i].y) * (points3d1[i].y - points3d2[i].y)) + ((points3d1[i].z - points3d2[i].z) * (points3d1[i].z - points3d2[i].z)))
const ccylinder = new Operation(new THREE.CylinderGeometry(this.radius, this.radius, dis), brushMat)
ccylinder.operation = SUBTRACTION
ccylinder.position.y = 0
const ssphere1 = new Operation(new THREE.SphereGeometry(this.radius), brushMat)
ssphere1.operation = SUBTRACTION
ssphere1.position.y = -dis / 2
const ssphere2 = new Operation(new THREE.SphereGeometry(this.radius), brushMat)
ssphere2.operation = SUBTRACTION
ssphere2.position.y = dis / 2
//const drillGroup = new OperationGroup()

const drillGroup = new OperationGroup()
drillGroup.add(ccylinder, ssphere1, ssphere2)

drillGroup.position.x = (points3d1[i].x + points3d2[i].x) / 2
drillGroup.position.y = (points3d1[i].y + points3d2[i].y) / 2
drillGroup.position.z = (points3d1[i].z + points3d2[i].z) / 2
let direction = new THREE.Vector3().subVectors(points3d1[i], points3d2[i]).normalize()
let currentDir = new THREE.Vector3(0, 1, 0)
let quaternion = new THREE.Quaternion()
quaternion.setFromUnitVectors(currentDir, direction)
drillGroup.applyQuaternion(quaternion)
drillGroup.updateMatrix()
root.add(drillGroup)
transformControls.attach(drillGroup) */

/* {
        for (let i = 0; i < 5; i++) {
          for (let j = 0; j < 5; j++) {
            const ccylinder = new Operation(new THREE.CylinderGeometry(1, 1, 6), brushMat)
            ccylinder.operation = SUBTRACTION
            ccylinder.position.y = 0

            const ssphere1 = new Operation(new THREE.SphereGeometry(1), brushMat)
            ssphere1.operation = SUBTRACTION
            ssphere1.position.y = 3

            const ssphere2 = new Operation(new THREE.SphereGeometry(1), brushMat)
            ssphere2.operation = SUBTRACTION
            ssphere2.position.y = -3

            const drillGroup = new OperationGroup()
            if (i === 2 && j === 3) {
              drillGroup.position.x = -2.5 + (10 * i)
              drillGroup.position.z = -2.5 + (10 * j)
            } else if (i === 5 && j === 4) {
              drillGroup.position.x = -2.5 + (1.5 * i)
              drillGroup.position.z = -2.5 + (1.5 * j)
            } else {
              drillGroup.position.x = -2.5 + (1.1 * i)
              drillGroup.position.z = -2.5 + (1.1 * j)
            }
            drillGroup.add(ccylinder, ssphere1, ssphere2)
            root.add(drillGroup)
            transformControls.attach(drillGroup)
          }
        }
      } */


/* let dis = Math.sqrt(((sp.x - ep.x) * (sp.x - ep.x)) + ((sp.y - ep.y) * (sp.y - ep.y)) + ((sp.z - ep.z) * (sp.z - ep.z)))
        const ccylinder = new Operation(new THREE.CylinderGeometry(this.radius, this.radius, dis), addDrillMat)
        ccylinder.operation = SUBTRACTION
        ccylinder.position.y = 0
        const ssphere1 = new Operation(new THREE.SphereGeometry(this.radius), addDrillMat)
        ssphere1.operation = SUBTRACTION
        ssphere1.position.y = -dis / 2
        const ssphere2 = new Operation(new THREE.SphereGeometry(this.radius), addDrillMat)
        ssphere2.operation = SUBTRACTION
        ssphere2.position.y = dis / 2
        //const drillGroup = new OperationGroup()

        const drillGroup = new OperationGroup()
        drillGroup.add(ccylinder, ssphere1, ssphere2)

        let calBottomPoint = new THREE.Mesh(new THREE.SphereGeometry(0.2), new THREE.MeshBasicMaterial({
          color: 0x0000ff//0xff0000设置材质颜色为红色
        }))
        calBottomPoint.position.set(sp.x, sp.y, sp.z)
        scene.add(calBottomPoint)

        let calTopPoint = new THREE.Mesh(new THREE.SphereGeometry(0.2), new THREE.MeshBasicMaterial({
          color: 0xff0000//0xff0000设置材质颜色为红色
        }))
        calTopPoint.position.set(ep.x, ep.y, ep.z)
        scene.add(calTopPoint)

        drillGroup.position.x = (sp.x + ep.x) / 2
        drillGroup.position.y = (sp.y + ep.y) / 2
        drillGroup.position.z = (sp.z + ep.z) / 2
        let direction = new THREE.Vector3().subVectors(ep, sp).normalize()
        let currentDir = new THREE.Vector3(0, 1, 0)
        let quaternion = new THREE.Quaternion()
        quaternion.setFromUnitVectors(currentDir, direction)
        drillGroup.applyQuaternion(quaternion)
        drillGroup.updateMatrix()
        root.add(drillGroup)
        transformControls.attach(drillGroup) */

// 外部加载模型
/* fbxLoader.load('model/test002.fbx', function(object) {
  //self.scene.add(object);
  console.log('test002', object)
  const drillGroup = new OperationGroup()
  console.log('length', object.children.length)
  for (let i = 0; i < object.children.length; i++) {
    let zuankong002geo = object.children[i].geometry
    zuankong002geo.computeVertexNormals()
    const md2 = new Operation(zuankong002geo, brushMat)
    md2.operation = SUBTRACTION
    drillGroup.add(md2)
  }
  root.add(drillGroup)
  transformControls.attach(drillGroup)
}) */

// 外部加载模型
/* let fbxLoader = new FBXLoader()
fbxLoader.load('model/mei001.fbx', function(object) {
  //self.scene.add(object);
  console.log('mei001', object)
  let mei001geo = object.children[0].geometry
  mei001geo.computeVertexNormals()
  root = new Operation(mei001geo, brushMat)
  scene.add(root)
}) */

/*let bbox = new THREE.Box3().setFromBufferAttribute(ppparts[i].attributes, true)
            console.log('bbox', bbox)
            //let partCube = new Mesh(bbox, cubeMaterial)
            let helper = new THREE.Box3Helper(bbox, new THREE.Color(0, 255, 0))
            let size = bbox.getSize(new THREE.Vector3()) // HEREyou get the size

            let centerX = (ppparts[i].boundingBox.max.x + ppparts[i].boundingBox.min.x) / 2.
            let centerY = (ppparts[i].boundingBox.max.y + ppparts[i].boundingBox.min.y) / 2.
            let centerZ = (ppparts[i].boundingBox.max.z + ppparts[i].boundingBox.min.z) / 2.
             let width = ppparts[i].boundingBox.max.x - ppparts[i].boundingBox.min.x
            let height = ppparts[i].boundingBox.max.y - ppparts[i].boundingBox.min.y
            let depth = ppparts[i].boundingBox.max.z - ppparts[i].boundingBox.min.z
            let partCube = new Mesh(new THREE.BoxBufferGeometry(width, height, depth), cubeMaterial)
            partCube.position.x = centerX
            partCube.position.y = centerY
            partCube.position.z = centerZ*/
</script>

<style>


</style>
