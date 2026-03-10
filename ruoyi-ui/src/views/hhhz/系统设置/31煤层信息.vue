<template>
  <div class="app-container">
    <div v-show="showzbd" class="tablediv">
      <el-row :gutter="20">
        <el-col :span="12" :xs="12">
          <el-card>
            <div slot="header" class="clearfix">
              <span>煤层坐标信息录入</span>
            </div>
            <div>
              <span>请选择煤层：</span>
              <el-select v-model="currentCoalId" placeholder="请选择煤层" @change="setCurrentCoal">
                <el-option v-for="item in coalInfoList" :key="item.id" :label="item.coalSerial"
                           :value="item.id">
                </el-option>
              </el-select>
              <el-button style="margin:0 5px" type="primary" size="mini"
                         @click="()=>{this.coalInfoShow = true;this.aoeCoalInfo = {};this.coalInfoTitle='新增煤层'}">
                添加煤层
              </el-button>
              <el-button style="margin:0 5px" type="success" size="mini"
                         @click="()=>{this.coalInfoShow = true;this.aoeCoalInfo = currentCoal;this.coalInfoTitle='编辑煤层'}"
                         v-show="currentCoal.id!=null">
                编辑煤层
              </el-button>
              <el-button style="margin:0 5px" type="danger" size="mini"
                         @click="handleDeleteCoal" v-show="currentCoal.id!=null">
                删除煤层
              </el-button>
              <el-button style="margin:0 5px;float:right" type="primary" size="mini"
                         @click="()=>{this.coalPoiShow = true;this.aoeCoalPoi = {'coalinfoId':this.currentCoal.id};this.coalPoiTitle = '新增煤层点'}"
                         v-show="currentCoal.id!=null">
                新增煤层点
              </el-button>
              <el-button style="margin:0 5px;float:right" type="primary" size="mini" v-show="currentCoal.id!=null"
                         disabled>
                导入坐标点
              </el-button>
              <el-button style="margin:0 5px" type="primary" size="mini"
                         @click="()=>{this.coalPoiTypeShow = true;}">
                管理煤层点类型
              </el-button>
              <el-button style="margin:0 5px" type="primary" size="mini"
                         @click="handleShowCoal3D">
                三维展示煤层
              </el-button>
              <el-table v-loading="false" :data="currentCoal.coalPointinfoList">
                <el-table-column
                  type="index"
                >
                </el-table-column>
                <el-table-column label="煤层点ID" align="center" key="id" prop="id" width="100px"/>
                <el-table-column label="底板坐标x（m）" align="center" key="floorCoordinatex" prop="floorCoordinatex"/>
                <el-table-column label="底板坐标y（m）" align="center" key="floorCoordinatey" prop="floorCoordinatey"/>
                <el-table-column label="底板坐标z（m）" align="center" key="floorCoordinatez" prop="floorCoordinatez"/>
                <el-table-column label="煤层厚度（m）" align="center" key="coalThick" prop="coalThick"/>
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
                    <el-button size="mini" type="text" icon="el-icon-delete"
                               @click="handleDeleteCoalPoi(scope.row)">删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12" :xs="12">
          <div id="container"></div>
        </el-col>
      </el-row>
    </div>

    <!--<el-dialog :title="coal3DTitle" :visible.sync="coal3DShow" width="55%">
      <div id="container"></div>
      <el-button @click="createCoal">生成煤层</el-button>
    </el-dialog>-->

    <!--  新增|编辑煤层信息  -->
    <el-dialog :title="coalInfoTitle" :visible.sync="coalInfoShow" width="20%">
      <el-form ref="coalInfoDialog" :model="aoeCoalInfo" label-width="120px">
        <el-form-item label="煤层编号" prop="coalSerial">
          <el-input
            v-model="aoeCoalInfo.coalSerial"
            placeholder="请输入煤层编号"
          />
        </el-form-item>
        <el-form-item label="煤层储量" prop="coalReserves">
          <el-input
            v-model="aoeCoalInfo.coalReserves"
            placeholder="请输入煤层储量"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="煤层煤厚" prop="coalThick">
          <el-input
            v-model="aoeCoalInfo.coalThick"
            placeholder="请输入煤层煤厚"
          />
        </el-form-item>
        <el-form-item label="煤层倾角" prop="coalDip">
          <el-input
            v-model="aoeCoalInfo.coalDip"
            placeholder="请输入煤层倾角"
          />
        </el-form-item>
        <el-form-item label="坚固性系数" prop="coalHardness">
          <el-input
            v-model="aoeCoalInfo.coalHardness"
            placeholder="请输入坚固性系数"
          />
        </el-form-item>
        <el-form-item label="煤层层理" prop="coalBedding">
          <el-input
            v-model="aoeCoalInfo.coalBedding"
            placeholder="请输入煤层层理"
          />
        </el-form-item>
        <el-form-item label="煤层节理" prop="coalJoint">
          <el-input
            v-model="aoeCoalInfo.coalJoint"
            placeholder="请输入煤层节理"
          />
        </el-form-item>
        <el-form-item label="自然发火期" prop="selfIgnite">
          <el-input
            v-model="aoeCoalInfo.selfIgnite"
            placeholder="请输入自然发火期"
          />
        </el-form-item>
        <el-form-item label="稳定程度" prop="stabilize">
          <el-input
            v-model="aoeCoalInfo.stabilize"
            placeholder="请输入稳定程度"
          />
        </el-form-item>
        <el-form-item label="煤尘爆炸指数/%" prop="explodeCoeffcient">
          <el-input
            v-model="aoeCoalInfo.explodeCoeffcient"
            placeholder="请输入煤尘爆炸指数/%"
          />
        </el-form-item>
        <el-form-item label="地温" prop="groundTemperature">
          <el-input
            v-model="aoeCoalInfo.groundTemperature"
            placeholder="请输入地温"
          />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="aoeCoalInfo.remark" type="textarea" placeholder="请输入备注内容"/>
        </el-form-item>
        <!--        <el-form-item label="煤层名称" prop="coalSerial">
                  <el-input v-model="aoeCoalInfo.coalSerial" placeholder="请输入煤层名称"/>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="aoeCoalInfo.remark" type="textarea" placeholder="请输入备注内容"/>
                </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCoalInfoForm">确 定</el-button>
        <el-button @click="()=>{this.coalInfoShow = false;this.aoeCoalInfo = {};}">取 消</el-button>
      </div>
    </el-dialog>

    <!--  新增|编辑坐标点信息  -->
    <el-dialog :title="coalPoiTitle" :visible.sync="coalPoiShow" width="20%">
      <el-form ref="coalPoiDialog" :model="aoeCoalPoi" label-width="120px">
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

let renderer, camera, scene, outputContainer, light, axesHelper
let controls, transformControls
let coalCubeObj, coalCubeWireframeObj, coalGeometry, coalBottomMesh, coalTopMesh, faultMesh
let coalTopMeshMat, coalBottomMeshMat, coalCubeMat, innerBoxMeshMat, borderBoxMeshMat, coalCubeWireframeMat
let mouse = new THREE.Vector2()
//let raycaster = new THREE.Raycaster()
let pointsBottomToSceneList, pointsTopToSceneList, faultsMeshList
let boxMesh
export default {
  name: 'CoalInfo',
  dicts: ['sys_normal_disable', 'sys_user_sex', 'sys_user_expire'],
  data() {
    return {
      showzbd: true,
      showzkxx: false,
      coalInfoTitle: '新增煤层信息',
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
      // 断层信息
      geoFaultList: [],
      selectFaultInfo: null,
      currentCoal: {},
      currentCoalId: ''
    }
  },
  watch: {},
  async created() {
    await this.getCoalInfoList()
    await this.getCoalPoiTypeList()
    await this.getGeoFaultList()
  },
  methods: {
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
     * 初始化场景
     */
    initScene() {
      const bgColor = 0x000000
      outputContainer = document.getElementById('container')
      // 渲染初始化
      renderer = new THREE.WebGLRenderer({ antialias: true })
      renderer.setPixelRatio(window.devicePixelRatio)
      console.log(window.innerHeight, window.innerWidth)
      let wid = window.innerWidth * 0.4
      let height = window.innerHeight * 0.8

      renderer.setSize(wid, height)
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
      camera = new THREE.PerspectiveCamera(75, wid / height, 0.1, 1000)
      camera.position.set(-10, 25, -10).multiplyScalar(2)
      camera.far = 1000
      camera.updateProjectionMatrix()

      // 控制器
      controls = new OrbitControls(camera, renderer.domElement)
      transformControls = new TransformControls(camera, renderer.domElement)
      transformControls.setSize(0.75)
      transformControls.addEventListener('dragging-changed', e => {
        controls.enabled = !e.value
      })
      scene.add(transformControls)

      // 坐标轴
      axesHelper = new THREE.AxesHelper(25)
      scene.add(axesHelper)

      // 煤层块
      coalCubeMat = new THREE.MeshLambertMaterial()
      coalCubeMat.side = THREE.DoubleSide
      coalCubeMat.transparent = true
      coalCubeMat.opacity = 0.6
      coalCubeMat.depthWrite = true
      //coalCubeMat.wireframe = false
      coalCubeMat.color.set(0xDCDCDC).convertSRGBToLinear()

      coalCubeWireframeMat = new THREE.MeshPhongMaterial()
      //coalCubeMat.side = THREE.DoubleSide
      coalCubeWireframeMat.transparent = true
      coalCubeWireframeMat.opacity = 0.5
      coalCubeWireframeMat.depthWrite = false
      coalCubeWireframeMat.wireframe = true
      coalCubeWireframeMat.color.set(0x98FB98).convertSRGBToLinear()

      // 煤层顶底板点位材质
      coalTopMeshMat = new THREE.MeshLambertMaterial({ color: 0xff0000 })
      coalTopMeshMat.wireframe = false
      coalTopMeshMat.side = THREE.DoubleSide
      coalTopMeshMat.depthWrite = false
      coalTopMeshMat.transparent = false
      coalTopMeshMat.opacity = 1

      coalBottomMeshMat = new THREE.MeshLambertMaterial({ color: 0x00ff00 })
      coalBottomMeshMat.wireframe = false
      coalBottomMeshMat.side = THREE.DoubleSide
      coalBottomMeshMat.depthWrite = true
      coalBottomMeshMat.transparent = false
      coalBottomMeshMat.opacity = 1

      innerBoxMeshMat = new THREE.MeshLambertMaterial({ color: 0xff00ff })
      innerBoxMeshMat.wireframe = false
      innerBoxMeshMat.side = THREE.DoubleSide
      innerBoxMeshMat.depthWrite = true
      innerBoxMeshMat.transparent = false
      innerBoxMeshMat.opacity = 1

      borderBoxMeshMat = new THREE.MeshLambertMaterial({ color: 0x00ffff })
      borderBoxMeshMat.wireframe = false
      borderBoxMeshMat.side = THREE.DoubleSide
      borderBoxMeshMat.depthWrite = true
      borderBoxMeshMat.transparent = false
      borderBoxMeshMat.opacity = 1
      document.body.addEventListener('click', this.onClick, false)
      let boxGeo = new THREE.BoxGeometry(100, 0.1, 100)
      boxMesh = new THREE.Mesh(boxGeo, coalCubeMat)
      boxMesh.position.setX(0)
      boxMesh.position.setY(0)
      boxMesh.position.setZ(0)
      //scene.add(boxMesh)
      if (this.edges.length > 0) {
        this.createCoal()
        this.createFault()
      } else {
        this.createCoalPoints()
      }
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
    createCoalPoints() {
      pointsBottomToSceneList = []
      pointsTopToSceneList = []
      let coalPoints = this.currentCoal.coalPointinfoList
      let pointsAll = []
      this.points3DBottom = []
      this.points3DTop = []

      let box = new BoxGeometry(0.5, 0.5, 0.5)
      for (let i = 0; i < coalPoints.length; i++) {
        if (coalPoints[i].dataType !== 3) {
          // 不是断层面上的点
          if (coalPoints[i].dataType === 1) {
            let innerBox = new THREE.Mesh(box, innerBoxMeshMat)
            innerBox.position.setX(coalPoints[i].floorCoordinatex)
            innerBox.position.setZ(coalPoints[i].floorCoordinatey)
            innerBox.position.setY(coalPoints[i].floorCoordinatez)
            innerBox.name = i + ''
            pointsBottomToSceneList.push(innerBox)
          } else if (coalPoints[i].dataType === 2) {
            let borderBox = new THREE.Mesh(box, borderBoxMeshMat)
            borderBox.position.setX(coalPoints[i].floorCoordinatex)
            borderBox.position.setZ(coalPoints[i].floorCoordinatey)
            borderBox.position.setY(coalPoints[i].floorCoordinatez)
            borderBox.name = i + ''
            pointsBottomToSceneList.push(borderBox)
          }
        }
      }

      /* for (let i = 0; i < coalPoints.length; i++) {
        if (coalPoints[i].dataType !== 3) {
          // 不是断层面上的点
          if (coalPoints[i].dataType === 1) {
            let innerBox = new THREE.Mesh(box, innerBoxMeshMat)
            innerBox.position.setX(coalPoints[i].floorCoordinatex)
            innerBox.position.setZ(coalPoints[i].floorCoordinatey)
            innerBox.position.setY(coalPoints[i].floorCoordinatez + coalPoints[i].coalThick)
            pointsTopToSceneList.push(innerBox)
          } else if (coalPoints[i].dataType === 2) {
            let borderBox = new THREE.Mesh(box, borderBoxMeshMat)
            borderBox.position.setX(coalPoints[i].floorCoordinatex)
            borderBox.position.setZ(coalPoints[i].floorCoordinatey)
            borderBox.position.setY(coalPoints[i].floorCoordinatez + coalPoints[i].coalThick)
            pointsTopToSceneList.push(borderBox)
          }
        }
      } */
      for (let i = 0; i < pointsBottomToSceneList.length; i++) {
        scene.add(pointsBottomToSceneList[i])
      }
      /* for (let i = 0; i < pointsTopToSceneList.length; i++) {
        scene.add(pointsTopToSceneList[i])
      } */
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
    // 渲染断层面
    renderFault(fNo, fps, edges, under) {
      faultsMeshList = []
      let faultPoints = []
      let xyPoints = []
      for (let i = 0; i < fps.length; i++) {
        // 断层点位
        faultPoints.push(new THREE.Vector3(fps[i].floorCoordinatex, fps[i].floorCoordinatez, fps[i].floorCoordinatey))
        xyPoints.push([fps[i].floorCoordinatex, fps[i].floorCoordinatey])
      }
      if (xyPoints.length > 3) {
        let faultGeom = new THREE.BufferGeometry().setFromPoints(faultPoints)
        let cdtTri = cdt2d(xyPoints, edges, { exterior: false })
        let meshIndex1 = []
        for (let i = 0; i < cdtTri.length; i++) {
          meshIndex1.push(cdtTri[i][0])
          meshIndex1.push(cdtTri[i][1])
          meshIndex1.push(cdtTri[i][2])
        }

        faultGeom.setIndex(meshIndex1)
        faultGeom.computeVertexNormals()
        if (under) {
          let faultMesh = new THREE.Mesh(
            faultGeom,
            coalBottomMeshMat
          )
          faultMesh.name = fNo
          faultsMeshList.push(faultMesh)
          scene.add(faultMesh)
        } else {
          let faultMesh = new THREE.Mesh(
            faultGeom,
            coalTopMeshMat
          )
          faultMesh.name = fNo
          faultsMeshList.push(faultMesh)
          scene.add(faultMesh)
        }
      }
    },
    /**
     * 创建煤层
     */
    createCoal() {
      this.coalCreated = true
      let coalPoints = this.currentCoal.coalPointinfoList
      let pointsAll = []
      this.points3DBottom = []
      this.points3DTop = []

      for (let i = 0; i < coalPoints.length; i++) {
        if (coalPoints[i].dataType !== 3) {
          // 不是断层面上的点
          // 底板
          this.points3DBottom.push(new THREE.Vector3(coalPoints[i].floorCoordinatex, coalPoints[i].floorCoordinatez, coalPoints[i].floorCoordinatey))
          pointsAll.push(new THREE.Vector3(coalPoints[i].floorCoordinatex, coalPoints[i].floorCoordinatez, coalPoints[i].floorCoordinatey))
        }
      }
      for (let i = 0; i < coalPoints.length; i++) {
        if (coalPoints[i].dataType !== 3) {
          // 顶板
          this.points3DTop.push(new THREE.Vector3(coalPoints[i].floorCoordinatex, coalPoints[i].floorCoordinatez + coalPoints[i].coalThick, coalPoints[i].floorCoordinatey))
          pointsAll.push(new THREE.Vector3(coalPoints[i].floorCoordinatex, coalPoints[i].floorCoordinatez + coalPoints[i].coalThick, coalPoints[i].floorCoordinatey))
        }
      }

      // 添加上下钻孔点位
      let coalBottomGeom = new THREE.BufferGeometry().setFromPoints(this.points3DBottom)
      let coalTopGeom = new THREE.BufferGeometry().setFromPoints(this.points3DTop)

      let xyPoints = [] // [[x,y],[x,y],[x,y],[x,y],[x,y]]
      //let edges = [[0, 1], [1, 2], [2, 7], [7, 8], [8, 6], [6, 4], [4, 3], [3, 0]]  // 边界
      for (let i = 0; i < this.points3DBottom.length; i++) {
        xyPoints.push([this.points3DBottom[i].x, this.points3DBottom[i].z])
      }

      // 划分三角网
      let cdtTri = cdt2d(xyPoints, this.edges, { exterior: false })
      //console.log('cdt2d:', cdtTri)

      let allIndex = []
      // 底面
      for (let i = 0; i < cdtTri.length; i++) {
        allIndex.push(cdtTri[i][0])
        allIndex.push(cdtTri[i][1])
        allIndex.push(cdtTri[i][2])
      }
      // 顶面
      for (let i = 0; i < cdtTri.length; i++) {
        allIndex.push(cdtTri[i][0] + this.points3DBottom.length)
        allIndex.push(cdtTri[i][2] + this.points3DBottom.length)
        allIndex.push(cdtTri[i][1] + this.points3DBottom.length)
      }
      let finalBorderList = []
      if (this.edges.length > 0) {
        for (let i = 0; i < this.edges.length; i++) {
          finalBorderList.push(this.edges[i][0])
        }
        finalBorderList.push(this.edges[0][0])
      }
      //let finalBorderList = [0, 1, 2, 7, 8, 6, 4, 3, 0]
      // 构建边界面
      for (let i = 0; i < finalBorderList.length; i++) {
        if (i === 0) {
          allIndex.push(Number(finalBorderList[0]))
          allIndex.push(Number(finalBorderList[0]) + this.points3DBottom.length)
          allIndex.push(Number(finalBorderList[1]))

          allIndex.push(Number(finalBorderList[0]))
          allIndex.push(Number(finalBorderList[finalBorderList.length - 2]) + this.points3DBottom.length)
          allIndex.push(Number(finalBorderList[0]) + this.points3DBottom.length)
        } else if (i === finalBorderList.length - 1) {
          allIndex.push(Number(finalBorderList[i]))
          allIndex.push(Number(finalBorderList[i]) + this.points3DBottom.length)
          allIndex.push(Number(finalBorderList[0]))

          allIndex.push(Number(finalBorderList[i]))
          allIndex.push(Number(finalBorderList[i - 1]) + this.points3DBottom.length)
          allIndex.push(Number(finalBorderList[i]) + this.points3DBottom.length)
        } else {
          allIndex.push(Number(finalBorderList[i]))
          allIndex.push(Number(finalBorderList[i]) + this.points3DBottom.length)
          allIndex.push(Number(finalBorderList[i + 1]))

          allIndex.push(Number(finalBorderList[i]))
          allIndex.push(Number(finalBorderList[i - 1]) + this.points3DBottom.length)
          allIndex.push(Number(finalBorderList[i]) + this.points3DBottom.length)
        }
      }

      let cPointsAll = [...this.points3DBottom, ...this.points3DTop]
      coalGeometry = new THREE.BufferGeometry().setFromPoints(cPointsAll)
      coalGeometry.setIndex(allIndex)
      coalGeometry.computeVertexNormals()
      console.log('coalAllGeom:', coalGeometry)
      coalCubeObj = new THREE.Mesh(
        coalGeometry,
        coalCubeMat
      )
      scene.add(coalCubeObj)

      coalCubeWireframeObj = new THREE.Mesh(
        coalGeometry,
        coalCubeWireframeMat
      )
      scene.add(coalCubeWireframeObj)

      /* let meshIndex1 = [] // delaunay index => three.js index
      for (let i = 0; i < indexDelaunay1.triangles.length; i++) {
        meshIndex1.push(indexDelaunay1.triangles[i])
      }

      let meshIndex2 = [] // delaunay index => three.js index
      for (let i = 0; i < indexDelaunay2.triangles.length; i++) {
        meshIndex2.push(indexDelaunay2.triangles[i])
      } */

      let meshIndex1 = []
      for (let i = 0; i < cdtTri.length; i++) {
        meshIndex1.push(cdtTri[i][0])
        meshIndex1.push(cdtTri[i][1])
        meshIndex1.push(cdtTri[i][2])
      }

      coalBottomGeom.setIndex(meshIndex1)
      coalBottomGeom.computeVertexNormals()
      coalBottomMesh = new THREE.Mesh(
        coalBottomGeom,
        coalBottomMeshMat
      )
      //scene.add(coalBottomMesh)

      coalTopGeom.setIndex(meshIndex1)
      coalTopGeom.computeVertexNormals()
      coalTopMesh = new THREE.Mesh(
        coalTopGeom,
        coalTopMeshMat
      )
      //scene.add(coalTopMesh)

      // 煤层实体
      /* coalGeometry = new ConvexGeometry(pointsAll)
      coalCubeObj = new Mesh(coalGeometry, coalCubeMat)
      scene.add(coalCubeObj) */
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
    // 提交煤层信息
    submitCoalInfoForm() {
      console.log(this.aoeCoalInfo)
      if (this.aoeCoalInfo.id != null) {
        uCoalInfo(this.aoeCoalInfo).then(response => {
          this.$modal.msgSuccess('修改成功')
          this.coalInfoShow = false
          this.aoeCoalInfo = false
          this.getCoalInfoList()
        })
      } else {
        aCoalInfo([this.aoeCoalInfo]).then(response => {
          this.$modal.msgSuccess('新增成功')
          this.coalInfoShow = false
          this.aoeCoalInfo = false
          this.getCoalInfoList()
        })
      }
    },
    // 提交煤层点信息
    submitCoalPoiForm() {
      console.log(this.aoeCoalPoi)
      if (this.aoeCoalPoi.id != null) {
        uCoalPoi(this.aoeCoalPoi).then(response => {
          this.$modal.msgSuccess('修改成功')
          this.coalPoiShow = false
          this.aoeCoalPoi = false
          this.getCoalInfoList().then(() => {
            this.coalInfoList.forEach((item) => {
              if (item.id === this.currentCoalId) {
                this.currentCoal = item
              }
            })
          })
        })
      } else {
        aCoalPoi([this.aoeCoalPoi]).then(response => {
          this.$modal.msgSuccess('新增成功')
          this.coalPoiShow = false
          this.aoeCoalPoi = false
          this.getCoalInfoList().then(() => {
            this.coalInfoList.forEach((item) => {
              if (item.id === this.currentCoalId) {
                this.currentCoal = item
              }
            })
          })

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
    // 删除煤层点
    handleDeleteCoalPoi(coalPoi) {
      this.$modal.confirm('是否确认删除编号为"' + coalPoi.id + '"的煤层点？').then(function() {
        return dCoalPoi({ ids: coalPoi.id })
      }).then((response) => {
        console.log('dCoalPoi', response)
        this.getCoalInfoList().then(() => {
          this.coalInfoList.forEach((item) => {
            if (item.id === this.currentCoalId) {
              this.currentCoal = item
            }
          })
        })
        this.currentCoal = {}
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
