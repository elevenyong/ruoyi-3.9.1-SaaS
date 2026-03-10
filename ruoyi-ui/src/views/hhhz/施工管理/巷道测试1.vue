<template>
  <div class="app-container">
    <div id="container"></div>
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
  aCoalPoiType,
  dCoalInfo,
  dCoalPoi,
  dCoalPoiType,
  gCoalInfo,
  lCoalPoiType,
  uCoalInfo,
  uCoalPoiType
} from '@/api/hhhz/meiceng'
import { listGeofault } from '@/api/hhhz/duanceng'
import * as THREE from 'three'
import { BoxGeometry, Mesh } from 'three'
import { TransformControls } from 'three/examples/jsm/controls/TransformControls.js'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js'
import cdt2d from 'cdt2d'
import * as XLSX from 'xlsx'
import { GridMaterial } from 'three-bvh-csg'
import { drillingSingle } from '@/api/hhhz/drillingdes'

let renderer, camera, scene, outputContainer, light, axesHelper
let controls, transformControls
let coalCubeObj, coalCubeWireframeObj, coalGeometry, coalBottomMesh, coalTopMesh, faultMesh
let coalTopMeshMat, coalBottomMeshMat, coalCubeMat, innerBoxMeshMat, borderBoxMeshMat, coalCubeWireframeMat,
  cutBoxMeshMat, originDrillRangeMatS, brushMat, originDrillMatS
let mouse = new THREE.Vector2()
//let raycaster = new THREE.Raycaster()
let pointsBottomToSceneList, pointsTopToSceneList, faultsMeshList
let boxMesh
export default {
  name: 'CoalTest',
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
      queryParams: {
        'hc': 10, //煤层厚度（hc）
        'alf': 0, //煤层倾角(α,[0,90))
        'h2': 15, //煤层底板到底抽巷间距(h2)
        'wd': 4, //底抽巷宽（wd）
        'hd': 3, //底抽巷高（hd）
        'betd': 0, //底抽巷方位角（βd）
        'wm': 4, //煤巷宽 （wm）
        'hm': 3, //煤巷高（hm）
        'radius': 5, //抽采半径(r)
        'l': 15, //底抽巷与煤巷水平投影间距(L)
        'lx': 15, //抽采控制范围(Lx)
        'ls': 15, //抽采控制范围(Ls)
        'neicuo': true, //内错|外错
        'type': 4 //布孔类型: 3=三花布孔,4=方形布孔
      },
      // 布孔方式
      bukongs: [
        { value: 3, label: '三花布孔(常规)' },
        { value: 4, label: '正方形布孔(常规)' },
        { value: 41, label: '正方形布孔(考虑角度)' },
        { value: 31, label: '三花布孔(考虑角度:横向)' },
        { value: 32, label: '三花布孔(考虑角度:纵向)' }
      ],
      // 内外错
      neiwaicuos: [
        { value: true, label: '内错' },
        { value: false, label: '外错' }
      ],
      // 煤层点类型
      coalPoiType: [],
      // 煤层信息
      coalInfoList: [],
      // 断层信息
      geoFaultList: [],
      // 设计钻孔
      desDrillList: [],
      // 导入钻孔数据
      importDrillInfoList: [],
      // 所有导入的钻孔数据
      allImportDrillInfoList: [],
      selectFaultInfo: null,
      currentCoal: {},
      currentCoalId: '',
      coalPointInfoList: [],
      originDrillRangeToSceneList: [],
      originDrillToSceneList: [],
      singleDesShow: false,
      desImportShow: false,
      //activeNames: ['1', '2', '3'],
      activeNames: [],
      currentPositionX: 0,
      cutFaceLines: [],
      autoUpdateX: true,
      coalSlopes: [],
      currentSlope: 0,// 当前段顶板倾角
      currentLw: 0,// 当前段投影排间距
      equalLineSpace: true, // 相同的排间距
      thickerPosition: true, // 选择更厚的位置
      importDrillShow: false, // 导入钻孔
      setLineWidth: 0,
      importDrillType: 4
    }
  },
  watch: {},
  created() {

  },
  mounted() {
    this.handleShowCoal3D()
  },
  methods: {
    ang2rad(ang) {
      return ang * (Math.PI / 180)
    },
    rad2ang(rad) {
      return rad * (180 / Math.PI)
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
     * 初始化场景
     */
    initScene() {
      const bgColor = 0x000000
      outputContainer = document.getElementById('container')
      // 渲染初始化
      renderer = new THREE.WebGLRenderer({ antialias: true })
      renderer.setPixelRatio(window.devicePixelRatio)
      console.log(window.innerHeight, window.innerWidth)
      let wid = window.innerWidth * 0.6
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
      camera.far = 2000
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
      //coalCubeMat.side = THREE.DoubleSide
      coalCubeMat.transparent = false
      //coalCubeMat.opacity = 0.5
      coalCubeMat.depthWrite = true
      coalCubeMat.depthTest = true
      //coalCubeMat.wireframe = false
      //coalCubeMat.color.set(0xDCDCDC).convertSRGBToLinear()
      coalCubeMat.color.set(0xff0000).convertSRGBToLinear()

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

      cutBoxMeshMat = new THREE.MeshLambertMaterial({ color: 0xffffff })
      cutBoxMeshMat.wireframe = false
      cutBoxMeshMat.side = THREE.DoubleSide
      cutBoxMeshMat.depthWrite = true
      cutBoxMeshMat.transparent = false
      cutBoxMeshMat.opacity = 1

      // 计算后空白带材质
      brushMat = new GridMaterial()
      brushMat.side = THREE.DoubleSide
      brushMat.depthWrite = true
      brushMat.color.set(0xffc400).convertSRGBToLinear()

      // 单双排钻孔抽采范围材质
      originDrillRangeMatS = brushMat.clone()
      originDrillRangeMatS.depthWrite = true
      originDrillRangeMatS.depthTest = true
      originDrillRangeMatS.transparent = false
      //originDrillRangeMatS.opacity = 0.2
      //originDrillRangeMatS.color.set(0xffc4ff).convertSRGBToLinear()
      originDrillRangeMatS.color.set(0x00f0f0).convertSRGBToLinear()

      // 原始钻孔材质
      originDrillMatS = brushMat.clone()
      originDrillMatS.depthWrite = true
      originDrillMatS.depthTest = true
      originDrillMatS.transparent = true
      originDrillMatS.opacity = 0.8
      //originDrillMatS.color.set(0xffc4ff).convertSRGBToLinear()
      originDrillMatS.color.set(0xffc400).convertSRGBToLinear()

      //document.body.addEventListener('click', this.onClick, false)
      let boxGeo = new THREE.BoxGeometry(100, 0.1, 100)
      boxMesh = new THREE.Mesh(boxGeo, coalCubeMat)
      boxMesh.position.setX(0)
      boxMesh.position.setY(0)
      boxMesh.position.setZ(0)
        //scene.add(boxMesh)
        /* if (this.edges.length > 0) {
          this.createCoal()
          this.createFault()
        } else {
          this.createCoalPoints()
        } */
        (new na).loadTunnels('https://mapchang.com/file/cgs/public/unzip/ef4a0d33aa9847ff87135999647aec5e/result.tunnel', {
        map: {
          arrowMap: 'https://mapchang.com/3d/assets/textures/tunnel/箭头.png',
          leftMap: 'https://mapchang.com/3d/assets/textures/tunnel/左.jpg',
          bottomMap: 'https://mapchang.com/3d/assets/textures/tunnel/底.jpg',
          rightMap: 'https://mapchang.com/3d/assets/textures/tunnel/右.jpg',
          topMap: 'https://mapchang.com/3d/assets/textures/tunnel/顶.jpg'
        }
      }).then((e => {
          ///t(e)
        }
      ))
    },
    /**
     * 添加点击事件, 射线捕捉获取点击物体
     * @param event
     */
    onClick(event) {
      //console.log(event)
      event.preventDefault()

      let sceneDocRect = outputContainer.getBoundingClientRect()
      mouse.x = ((event.clientX - sceneDocRect.left) / outputContainer.offsetWidth) * 2 - 1
      mouse.y = -((event.clientY - sceneDocRect.top) / outputContainer.offsetHeight) * 2 + 1
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

    // 创建煤层点
    createCoalPoints() {
      pointsBottomToSceneList = []
      pointsTopToSceneList = []
      let coalPoints = this.coalPointInfoList
      let pointsAll = []
      this.points3DBottom = []
      this.points3DTop = []

      let rw1BottomList = []
      let rw1TopList = []
      let rw2BottomList = []
      let rw2TopList = []
      let rw3BottomList = []
      let rw3TopList = []
      let box = new BoxGeometry(0.5, 0.5, 0.5)
      for (let i = 0; i < coalPoints.length; i++) {
        let bottomBox = new THREE.Mesh(box, innerBoxMeshMat)
        bottomBox.position.setX(coalPoints[i].bottomX)
        bottomBox.position.setY(coalPoints[i].bottomY)
        bottomBox.position.setZ(coalPoints[i].bottomZ)
        bottomBox.name = i + ''
        pointsBottomToSceneList.push(bottomBox)
        this.points3DBottom.push(new THREE.Vector3(coalPoints[i].bottomX, coalPoints[i].bottomY, coalPoints[i].bottomZ))
        let topBox = new THREE.Mesh(box, borderBoxMeshMat)
        topBox.position.setX(coalPoints[i].topX)
        topBox.position.setY(coalPoints[i].topY)
        topBox.position.setZ(coalPoints[i].topZ)
        topBox.name = i + ''
        pointsTopToSceneList.push(topBox)
        this.points3DTop.push(new THREE.Vector3(coalPoints[i].topX, coalPoints[i].topY, coalPoints[i].topZ))

        if (coalPoints[i].roadwayid === 1) {
          rw1TopList.push(new THREE.Vector3(coalPoints[i].topX, coalPoints[i].topY, coalPoints[i].topZ))
          rw1BottomList.push(new THREE.Vector3(coalPoints[i].bottomX, coalPoints[i].bottomY, coalPoints[i].bottomZ))
        } else if (coalPoints[i].roadwayid === 2) {
          rw2TopList.push(new THREE.Vector3(coalPoints[i].topX, coalPoints[i].topY, coalPoints[i].topZ))
          rw2BottomList.push(new THREE.Vector3(coalPoints[i].bottomX, coalPoints[i].bottomY, coalPoints[i].bottomZ))
        } else if (coalPoints[i].roadwayid === 3) {
          rw3TopList.push(new THREE.Vector3(coalPoints[i].topX, coalPoints[i].topY, coalPoints[i].topZ))
          rw3BottomList.push(new THREE.Vector3(coalPoints[i].bottomX, coalPoints[i].bottomY, coalPoints[i].bottomZ))
        }
      }
      // 拓展煤层区域，重设起点
      let minX = Math.min(this.points3DBottom[0].x, this.points3DTop[0].x, this.points3DBottom[this.points3DBottom.length - 1].x, this.points3DTop[this.points3DTop.length - 1].x)
      /* let originBottom = new THREE.Vector3(this.points3DBottom[0].x - 100, this.points3DBottom[0].y, this.points3DBottom[0].z)
      let finalBottom = new THREE.Vector3(this.points3DBottom[this.points3DBottom.length - 1].x - 100, this.points3DBottom[this.points3DBottom.length - 1].y, this.points3DBottom[this.points3DBottom.length - 1].z)
      let originTop = new THREE.Vector3(this.points3DTop[0].x - 100, this.points3DTop[0].y, this.points3DTop[0].z)
      let finalTop = new THREE.Vector3(this.points3DTop[this.points3DTop.length - 1].x - 100, this.points3DTop[this.points3DTop.length - 1].y, this.points3DTop[this.points3DTop.length - 1].z) */
      let originBottom = new THREE.Vector3(minX - 5, this.points3DBottom[0].y, this.points3DBottom[0].z)
      let finalBottom = new THREE.Vector3(minX - 5, this.points3DBottom[this.points3DBottom.length - 1].y, this.points3DBottom[this.points3DBottom.length - 1].z)
      let originTop = new THREE.Vector3(minX - 5, this.points3DTop[0].y, this.points3DTop[0].z)
      let finalTop = new THREE.Vector3(minX - 5, this.points3DTop[this.points3DTop.length - 1].y, this.points3DTop[this.points3DTop.length - 1].z)
      this.points3DBottom.unshift(originBottom)
      this.points3DBottom.push(finalBottom)
      this.points3DTop.unshift(originTop)
      this.points3DTop.push(finalTop)
      rw1BottomList.unshift(originBottom)
      rw1TopList.unshift(originTop)
      // 将第三条巷道点位数据反转，与第一条巷道顺序一致
      rw3BottomList.reverse()
      rw3TopList.reverse()
      rw3BottomList.unshift(finalBottom)
      rw3TopList.unshift(finalTop)
      let maxX = Math.max(rw1BottomList[rw1BottomList.length - 1].x, rw3BottomList[rw3BottomList.length - 1].x, rw1TopList[rw1TopList.length - 1].x, rw3TopList[rw3TopList.length - 1].x)
      let r1endBottom = new THREE.Vector3(maxX + 5, rw1BottomList[rw1BottomList.length - 1].y, rw1BottomList[rw1BottomList.length - 1].z)
      let r3endBottom = new THREE.Vector3(maxX + 5, rw3BottomList[rw3BottomList.length - 1].y, rw3BottomList[rw3BottomList.length - 1].z)
      let r1endTop = new THREE.Vector3(maxX + 5, rw1TopList[rw1TopList.length - 1].y, rw1TopList[rw1TopList.length - 1].z)
      let r3endTop = new THREE.Vector3(maxX + 5, rw3TopList[rw3TopList.length - 1].y, rw3TopList[rw3TopList.length - 1].z)
      rw1BottomList.push(r1endBottom)
      rw3BottomList.push(r3endBottom)
      rw1TopList.push(r1endTop)
      rw3TopList.push(r3endTop)

      // 创建切点
      this.cutFaceLines = []
      let cutFaceMaterial = new THREE.LineBasicMaterial({ color: 0xffffff })
      for (let i = 1; i < rw1BottomList.length - 1; i++) {
        let cl = this.createCutPoints(rw1BottomList[i], rw1TopList, rw3TopList, rw3BottomList, cutFaceMaterial)
        this.cutFaceLines.push(cl)
      }
      for (let i = 1; i < rw1TopList.length - 1; i++) {
        let cl = this.createCutPoints(rw1TopList[i], rw3TopList, rw3BottomList, rw1BottomList, cutFaceMaterial)
        this.cutFaceLines.push(cl)
      }
      for (let i = 1; i < rw3TopList.length - 1; i++) {
        let cl = this.createCutPoints(rw3TopList[i], rw3BottomList, rw1BottomList, rw1TopList, cutFaceMaterial)
        this.cutFaceLines.push(cl)
      }
      for (let i = 1; i < rw3BottomList.length - 1; i++) {
        let cl = this.createCutPoints(rw3BottomList[i], rw1BottomList, rw1TopList, rw3TopList, cutFaceMaterial)
        this.cutFaceLines.push(cl)
      }
      // 对所有切面排序
      this.cutFaceLines.sort(function(a, b) {
        return a[0].x - b[0].x
      })
      console.log('cutLines:', this.cutFaceLines)

      let rw2ExpBottom = []
      let rw2ExpTop = []
      // 切眼部分模型布线
      let xs = []
      let b1 = []
      let t1 = []
      let nb1 = []
      let nt1 = []
      if (rw2BottomList.length > 2) {

        for (let i = 0; i < this.cutFaceLines.length; i++) {
          xs.push(this.cutFaceLines[i][0].x)
        }
        let maxSX = Math.max(...xs)
        for (let i = 0; i < this.cutFaceLines.length; i++) {
          if (this.cutFaceLines[i][0].x === maxSX) {
            // 创建切眼连线
            // 连接底板
            for (let j = 0; j < rw2BottomList.length; j++) {
              let nearestP = this.closestPoint(rw2BottomList[j].x, rw2BottomList[j].y, rw2BottomList[j].z, this.cutFaceLines[i][0].x, this.cutFaceLines[i][0].y, this.cutFaceLines[i][0].z, this.cutFaceLines[i][3].x, this.cutFaceLines[i][3].y, this.cutFaceLines[i][3].z)
              let bottomBox = new THREE.Mesh(box, cutBoxMeshMat)
              bottomBox.position.set(nearestP[0], nearestP[1], nearestP[2])
              scene.add(bottomBox)
              let expPoint = this.calNewPoint2(nearestP[0], nearestP[1], nearestP[2], rw2BottomList[j].x, rw2BottomList[j].y, rw2BottomList[j].z, 15)
              nb1.push(new THREE.Vector3(nearestP[0], nearestP[1], nearestP[2]))
              b1.push(expPoint)
              let nearestPV3 = new THREE.Vector3(nearestP[0], nearestP[1], nearestP[2])
              let cutLineGeometry = new THREE.BufferGeometry().setFromPoints([nearestPV3, expPoint])
              let cutLineMaterial = new THREE.LineBasicMaterial({ color: 0xffffff })
              let cuteLineObj = new THREE.Line(cutLineGeometry, cutLineMaterial)
              scene.add(cuteLineObj)
            }
            // 连接顶板
            for (let j = 0; j < rw2TopList.length; j++) {
              let nearestP = this.closestPoint(rw2TopList[j].x, rw2TopList[j].y, rw2TopList[j].z, this.cutFaceLines[i][1].x, this.cutFaceLines[i][1].y, this.cutFaceLines[i][1].z, this.cutFaceLines[i][2].x, this.cutFaceLines[i][2].y, this.cutFaceLines[i][2].z)
              let bottomBox = new THREE.Mesh(box, cutBoxMeshMat)
              bottomBox.position.set(nearestP[0], nearestP[1], nearestP[2])
              scene.add(bottomBox)
              let expPoint = this.calNewPoint2(nearestP[0], nearestP[1], nearestP[2], rw2TopList[j].x, rw2TopList[j].y, rw2TopList[j].z, 15)
              nt1.push(new THREE.Vector3(nearestP[0], nearestP[1], nearestP[2]))
              t1.push(expPoint)
              let nearestPV3 = new THREE.Vector3(nearestP[0], nearestP[1], nearestP[2])
              let cutLineGeometry = new THREE.BufferGeometry().setFromPoints([nearestPV3, expPoint])
              let cutLineMaterial = new THREE.LineBasicMaterial({ color: 0xffffff })
              let cuteLineObj = new THREE.Line(cutLineGeometry, cutLineMaterial)
              scene.add(cuteLineObj)
            }
            break
          }
        }

        // 切眼控制范围外边界
        // 对齐边界角点（取rw2的x，rw1的z，avg(rw1,rw2)为y）
        let conner12Top = new THREE.Vector3(rw2TopList[0].x, (rw2TopList[0].y + rw1TopList[rw1TopList.length - 1].y) / 2, rw1TopList[rw1TopList.length - 1].z)
        let conner12Bottom = new THREE.Vector3(rw2BottomList[0].x, (rw2BottomList[0].y + rw1BottomList[rw1BottomList.length - 1].y) / 2, rw1BottomList[rw1BottomList.length - 1].z)
        let conner23Top = new THREE.Vector3(rw2TopList[rw2TopList.length - 1].x, (rw2TopList[rw2TopList.length - 1].y + rw3TopList[rw3TopList.length - 1].y) / 2, rw3TopList[rw3TopList.length - 1].z)
        let conner23Bottom = new THREE.Vector3(rw2BottomList[rw2BottomList.length - 1].x, (rw2BottomList[rw2BottomList.length - 1].y + rw3BottomList[rw3BottomList.length - 1].y) / 2, rw3BottomList[rw3BottomList.length - 1].z)
        /* let rw2ExpBottom = []
        let rw2ExpTop = [] */
        // 切眼起点
        rw2ExpBottom.push(new THREE.Vector3(conner12Bottom.x + 15, conner12Bottom.y, conner12Bottom.z - 15))
        rw2ExpTop.push(new THREE.Vector3(conner12Top.x + 15, conner12Top.y, conner12Top.z - 15))
        // 切眼中间
        /* for (let i = 0; i < rw2BottomList.length; i++) {
          let tempBottom = new THREE.Vector3(rw2BottomList[i].x + 15, rw2BottomList[i].y, rw2BottomList[i].z)
          rw2ExpBottom.push(tempBottom)
          let tempTop = new THREE.Vector3(rw2TopList[i].x + 15, rw2TopList[i].y, rw2TopList[i].z)
          rw2ExpTop.push(tempTop)
        } */
        for (let i = 0; i < b1.length; i++) {
          rw2ExpBottom.push(b1[i])
          rw2ExpTop.push(t1[i])
        }
        // 切眼终点
        rw2ExpBottom.push(new THREE.Vector3(conner23Bottom.x + 15, conner23Bottom.y, conner23Bottom.z + 15))
        rw2ExpTop.push(new THREE.Vector3(conner23Top.x + 15, conner23Top.y, conner23Top.z + 15))
      }

      // 勾勒控制范围外边缘
      let expendTop = []
      let expendBottom = []
      // 构建rw1拓展
      for (let i = 0; i < this.cutFaceLines.length; i++) {
        expendBottom.push(this.cutFaceLines[i][5])
        expendTop.push(this.cutFaceLines[i][6])
      }

      if (rw2BottomList.length > 2) {
        // 构建rw2拓展
        for (let i = 0; i < rw2ExpBottom.length; i++) {
          expendBottom.push(rw2ExpBottom[i])
          let tempBox = new THREE.Mesh(box, cutBoxMeshMat)
          tempBox.position.set(rw2ExpBottom[i].x, rw2ExpBottom[i].y, rw2ExpBottom[i].z)
          scene.add(tempBox)
        }
        for (let i = 0; i < rw2ExpTop.length; i++) {
          expendTop.push(rw2ExpTop[i])
          let tempBox = new THREE.Mesh(box, cutBoxMeshMat)
          tempBox.position.set(rw2ExpTop[i].x, rw2ExpTop[i].y, rw2ExpTop[i].z)
          scene.add(tempBox)
        }
      }
      // 构建rw3拓展，所以要逆序
      for (let i = this.cutFaceLines.length - 1; i >= 0; i--) {
        expendTop.push(this.cutFaceLines[i][7])
        expendBottom.push(this.cutFaceLines[i][8])
      }

      // 创建煤层模型
      let allPoints = []
      let allIndex = []
      for (let i = 0; i < this.cutFaceLines.length; i++) {
        allPoints.push(this.cutFaceLines[i][5])
        allPoints.push(this.cutFaceLines[i][6])
        allPoints.push(this.cutFaceLines[i][7])
        allPoints.push(this.cutFaceLines[i][8])
        if (i === 0) {
          // 第一个面
          //031
          allIndex.push(0)
          allIndex.push(3)
          allIndex.push(1)
          //321
          allIndex.push(3)
          allIndex.push(2)
          allIndex.push(1)
        }
        if (i !== 0 && (i !== this.cutFaceLines.length - 1)) {
          // 中间面
          //014
          allIndex.push((i - 1) * 4)
          allIndex.push((i - 1) * 4 + 1)
          allIndex.push((i) * 4)
          //415
          allIndex.push((i) * 4)
          allIndex.push((i - 1) * 4 + 1)
          allIndex.push((i) * 4 + 1)
          //125
          allIndex.push((i - 1) * 4 + 1)
          allIndex.push((i - 1) * 4 + 2)
          allIndex.push((i) * 4 + 1)
          //265
          allIndex.push((i - 1) * 4 + 2)
          allIndex.push((i) * 4 + 2)
          allIndex.push((i) * 4 + 1)
          //043
          allIndex.push((i - 1) * 4)
          allIndex.push((i) * 4)
          allIndex.push((i - 1) * 4 + 3)
          //347
          allIndex.push((i - 1) * 4 + 3)
          allIndex.push((i) * 4)
          allIndex.push((i) * 4 + 3)
          //372
          allIndex.push((i - 1) * 4 + 3)
          allIndex.push((i) * 4 + 3)
          allIndex.push((i - 1) * 4 + 2)
          //276
          allIndex.push((i - 1) * 4 + 2)
          allIndex.push((i) * 4 + 3)
          allIndex.push((i) * 4 + 2)
        }
        if (i === (this.cutFaceLines.length - 1)) {
          // 中间面
          //014
          allIndex.push((i - 1) * 4)
          allIndex.push((i - 1) * 4 + 1)
          allIndex.push((i) * 4)
          //415
          allIndex.push((i) * 4)
          allIndex.push((i - 1) * 4 + 1)
          allIndex.push((i) * 4 + 1)
          //125
          allIndex.push((i - 1) * 4 + 1)
          allIndex.push((i - 1) * 4 + 2)
          allIndex.push((i) * 4 + 1)
          //265
          allIndex.push((i - 1) * 4 + 2)
          allIndex.push((i) * 4 + 2)
          allIndex.push((i) * 4 + 1)
          //043
          allIndex.push((i - 1) * 4)
          allIndex.push((i) * 4)
          allIndex.push((i - 1) * 4 + 3)
          //347
          allIndex.push((i - 1) * 4 + 3)
          allIndex.push((i) * 4)
          allIndex.push((i) * 4 + 3)
          //372
          allIndex.push((i - 1) * 4 + 3)
          allIndex.push((i) * 4 + 3)
          allIndex.push((i - 1) * 4 + 2)
          //276
          allIndex.push((i - 1) * 4 + 2)
          allIndex.push((i) * 4 + 3)
          allIndex.push((i) * 4 + 2)
          // 最后面
          // 456
          allIndex.push((i) * 4)
          allIndex.push((i) * 4 + 1)
          allIndex.push((i) * 4 + 3)

          // 567
          allIndex.push((i) * 4 + 1)
          allIndex.push((i) * 4 + 2)
          allIndex.push((i) * 4 + 3)
        }
      }
      if (rw2BottomList.length > 2) {

        // 切面位置模型构建
        for (let i = 0; i < rw2ExpBottom.length; i++) {
          let cl = this.cutFaceLines.length
          if (i === 0) {
            allPoints.push(rw2ExpBottom[i])
            allPoints.push(rw2ExpTop[i])
            //最左
            allIndex.push((cl - 1) * 4)
            allIndex.push((cl - 1) * 4 + 1)
            allIndex.push((cl) * 4)
            //最左
            allIndex.push((cl) * 4)
            allIndex.push((cl - 1) * 4 + 1)
            allIndex.push((cl) * 4 + 1)
          }
          if (i === 1) {
            allPoints.push(nb1[i - 1])
            allPoints.push(nt1[i - 1])
            allPoints.push(rw2ExpBottom[i])
            allPoints.push(rw2ExpTop[i])
            //
            allIndex.push(cl * 4 + (i - 1) * 4 + 2)
            allIndex.push((cl - 1) * 4 + (i - 1) * 4 + 0)
            allIndex.push(cl * 4 + (i - 1) * 4 + 0)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 0)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 4)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 2)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 0)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 4)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 5)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 4)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 3)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 5)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 3)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 1)
            allIndex.push((cl - 1) * 4 + 1)
          }
          if (i > 1 && (i !== rw2ExpBottom.length - 1)) {
            allPoints.push(nb1[i - 1])
            allPoints.push(nt1[i - 1])
            allPoints.push(rw2ExpBottom[i])
            allPoints.push(rw2ExpTop[i])
            //
            allIndex.push(cl * 4 + (i - 1) * 4 + 2)
            allIndex.push((cl) * 4 + (i - 1) * 4 - 2)
            allIndex.push(cl * 4 + (i - 1) * 4 + 0)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 0)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 4)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 2)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 0)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 4)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 5)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 4)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 - 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 5)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 3)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 5)
            allIndex.push((cl) * 4 + (i - 1) * 4 - 1)
          }
          if (i === rw2ExpBottom.length - 1) {
            allPoints.push(rw2ExpBottom[i])
            allPoints.push(rw2ExpTop[i])
            //
            allIndex.push((cl - 1) * 4 + 3)
            allIndex.push((cl) * 4 + (i - 1) * 4 - 2)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 0)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 0)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 4 - 2)
            allIndex.push((cl - 1) * 4 + 3)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 0)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 4 - 2)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 5 - 2)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 4 - 2)
            //
            allIndex.push((cl) * 4 + (i - 1) * 4 + 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 - 1)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 5 - 2)
            //
            allIndex.push((cl - 1) * 4 + 2)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 5 - 2)
            allIndex.push((cl) * 4 + (i - 1) * 4 - 1)
            //最右
            allIndex.push((cl - 1) * 4 + 3)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 5 - 2)
            allIndex.push((cl - 1) * 4 + 2)
            //最右
            allIndex.push((cl) * 4 + (i - 1) * 4 + 5 - 2)
            allIndex.push((cl - 1) * 4 + 3)
            allIndex.push((cl) * 4 + (i - 1) * 4 + 4 - 2)
          }
        }
        // 倒数第二排切面构建
      }
      let coalGeo = new THREE.BufferGeometry().setFromPoints(allPoints)
      coalGeo.setIndex(allIndex)
      coalGeo.computeVertexNormals()
      let coalCubeObj = new THREE.Mesh(coalGeo, coalCubeMat)
      scene.add(coalCubeObj)

      for (let i = 0; i < pointsBottomToSceneList.length; i++) {
        scene.add(pointsBottomToSceneList[i])
      }
      for (let i = 0; i < pointsTopToSceneList.length; i++) {
        scene.add(pointsTopToSceneList[i])
      }

      let bLineGeometry = new THREE.BufferGeometry().setFromPoints(this.points3DBottom)
      let bLineMaterial = new THREE.LineBasicMaterial({ color: 0x00ffff })
      let bLine = new THREE.Line(bLineGeometry, bLineMaterial)
      scene.add(bLine)
      let tLineGeometry = new THREE.BufferGeometry().setFromPoints(this.points3DTop)
      let tLineMaterial = new THREE.LineBasicMaterial({ color: 0xff00ff })
      let tLine = new THREE.Line(tLineGeometry, tLineMaterial)
      scene.add(tLine)

      let expBLineGeometry = new THREE.BufferGeometry().setFromPoints(expendBottom)
      let expBLine = new THREE.Line(expBLineGeometry, bLineMaterial)
      scene.add(expBLine)
      let expTLineGeometry = new THREE.BufferGeometry().setFromPoints(expendTop)
      let expTLine = new THREE.Line(expTLineGeometry, tLineMaterial)
      scene.add(expTLine)

      // 计算煤层顶板走向坡度
      this.calcCoalSlope()
    },

    /**
     * 创建面切点
     * @param cPoint 选择的切点
     * @param points1 1点列
     * @param points2 2点列
     * @param points3 3点列
     * @param cutFaceMaterial 材质
     * @return cutFacePoints[] [0左下，1左上，2右上，3右下，4左下,5exp左下，6exp左上，7exp右上，8exp右下]
     */
    createCutPoints(cPoint, points1, points2, points3, cutFaceMaterial) {
      let box = new BoxGeometry(1, 1, 1)
      let cutLine = [cPoint]
      for (let i = 0; i < points1.length; i++) {
        if (cPoint.x < points1[i].x) {
          // 创建第一切点
          let cp = this.getCutPoint(cPoint.x, points1[i], points1[i - 1])
          let bottomBox = new THREE.Mesh(box, cutBoxMeshMat)
          bottomBox.position.set(cp.x, cp.y, cp.z)
          scene.add(bottomBox)
          cutLine.push(cp)
          break
        } else if (cPoint.x === points1[i].x) {
          let cp = points1[i]
          let bottomBox = new THREE.Mesh(box, cutBoxMeshMat)
          bottomBox.position.set(cp.x, cp.y, cp.z)
          scene.add(bottomBox)
          cutLine.push(cp)
          break
        }
      }
      for (let i = 0; i < points2.length; i++) {
        if (cPoint.x < points2[i].x) {
          // 创建第一切点
          let cp = this.getCutPoint(cPoint.x, points2[i], points2[i - 1])
          let bottomBox = new THREE.Mesh(box, cutBoxMeshMat)
          bottomBox.position.set(cp.x, cp.y, cp.z)
          scene.add(bottomBox)
          cutLine.push(cp)
          break
        } else if (cPoint.x === points2[i].x) {
          let cp = points2[i]
          let bottomBox = new THREE.Mesh(box, cutBoxMeshMat)
          bottomBox.position.set(cp.x, cp.y, cp.z)
          scene.add(bottomBox)
          cutLine.push(cp)
          break
        }
      }
      for (let i = 0; i < points3.length; i++) {
        if (cPoint.x < points3[i].x) {
          // 创建第一切点
          let cp = this.getCutPoint(cPoint.x, points3[i], points3[i - 1])
          let bottomBox = new THREE.Mesh(box, cutBoxMeshMat)
          bottomBox.position.set(cp.x, cp.y, cp.z)
          scene.add(bottomBox)
          cutLine.push(cp)
          break
        } else if (cPoint.x === points3[i].x) {
          let cp = points3[i]
          let bottomBox = new THREE.Mesh(box, cutBoxMeshMat)
          bottomBox.position.set(cp.x, cp.y, cp.z)
          scene.add(bottomBox)
          cutLine.push(cp)
          break
        }
      }
      // 将切点排序[左下，左上，右上，右下，左下]
      let cutFacePoints = []
      let maxSZ = Math.max(cutLine[0].z, cutLine[1].z, cutLine[2].z, cutLine[3].z)
      let leftLine = []
      let rightLine = []
      for (let j = 0; j < cutLine.length; j++) {
        if (cutLine[j].z === maxSZ) {
          rightLine.push(cutLine[j])
        } else {
          leftLine.push(cutLine[j])
        }
      }
      // rightLine[0] 下方点，rightLine[1]上方点
      if (rightLine.length >= 2 && (rightLine[0].y >= rightLine[1].y)) {
        rightLine.reverse()
      }
      if (leftLine.length >= 2 && (leftLine[0].y >= leftLine[1].y)) {
        leftLine.reverse()
      }
      // [左下，左上，右上，右下，左下]
      cutFacePoints.push(leftLine[0])
      cutFacePoints.push(leftLine[1])
      cutFacePoints.push(rightLine[1])
      cutFacePoints.push(rightLine[0])
      cutFacePoints.push(leftLine[0])
      let expandLB = this.calNewPoint2(rightLine[0].x, rightLine[0].y, rightLine[0].z, leftLine[0].x, leftLine[0].y, leftLine[0].z, 15)
      let expandLT = this.calNewPoint2(rightLine[1].x, rightLine[1].y, rightLine[1].z, leftLine[1].x, leftLine[1].y, leftLine[1].z, 15)
      let expandRT = this.calNewPoint2(leftLine[1].x, leftLine[1].y, leftLine[1].z, rightLine[1].x, rightLine[1].y, rightLine[1].z, 15)
      let expandRB = this.calNewPoint2(leftLine[0].x, leftLine[0].y, leftLine[0].z, rightLine[0].x, rightLine[0].y, rightLine[0].z, 15)
      let lbBox = new THREE.Mesh(box, cutBoxMeshMat)
      lbBox.position.set(expandLB.x, expandLB.y, expandLB.z)
      scene.add(lbBox)
      let ltBox = new THREE.Mesh(box, cutBoxMeshMat)
      ltBox.position.set(expandLT.x, expandLT.y, expandLT.z)
      scene.add(ltBox)
      let rtBox = new THREE.Mesh(box, cutBoxMeshMat)
      rtBox.position.set(expandRT.x, expandRT.y, expandRT.z)
      scene.add(rtBox)
      let rbBox = new THREE.Mesh(box, cutBoxMeshMat)
      rbBox.position.set(expandRB.x, expandRB.y, expandRB.z)
      scene.add(rbBox)
      cutFacePoints.push(expandLB)
      cutFacePoints.push(expandLT)
      cutFacePoints.push(expandRT)
      cutFacePoints.push(expandRB)
      cutFacePoints.push(expandLB)
      let cutFaceGeometry = new THREE.BufferGeometry().setFromPoints(cutFacePoints)

      let cuteFaceLineObj = new THREE.Line(cutFaceGeometry, cutFaceMaterial)
      scene.add(cuteFaceLineObj)
      return cutFacePoints
    },
    /**
     * 获取切点
     * @param x
     * @param p1
     * @param p2
     */
    getCutPoint(x, p1, p2) {
      let a = Number(p2.y) - Number(p1.y)
      let b = Number(p1.x) - Number(p2.x)
      let c = Number(p2.x) * Number(p1.y) - Number(p1.x) * Number(p2.y)

      return new THREE.Vector3(x, (a * Number(x) + c) / (-b), Number(p1.z))
    },
    /**
     * 计算沿某个方向某个距离的点
     * @returns {Vector3}
     */
    calNewPoint2(x1, y1, z1, x2, y2, z2, dis) {
      let vx = x2 - x1
      let vy = y2 - y1
      let vz = z2 - z1
      let endX = x2 + (vx * (dis / Math.sqrt(vx * vx + vy * vy + vz * vz)))
      let endY = y2 + (vy * (dis / Math.sqrt(vx * vx + vy * vy + vz * vz)))
      let endZ = z2 + (vz * (dis / Math.sqrt(vx * vx + vy * vy + vz * vz)))
      return new THREE.Vector3(endX, endY, endZ)
    },

    /**
     * 添加钻孔实体
     * @param start
     * @param end
     * @param radius
     * @param material
     * @param list
     * @param name
     */
    addDrillMesh(start, end, radius, material, list, name) {
      let sp = start
      let ep = end
      let dis = Math.sqrt(((ep.x - sp.x) * (ep.x - sp.x)) + ((ep.y - sp.y) * (ep.y - sp.y)) + ((ep.z - sp.z) * (ep.z - sp.z)))
      //const drillMesh = new Mesh(new THREE.CylinderGeometry(radius, radius, dis), material)
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
      drillMesh.name = name
      scene.add(drillMesh)
      //group.add(drillMesh)
      list.push(drillMesh)
    },

    /**
     * 向场景中添加添加钻孔范围实体
     * @param start 起点
     * @param end 终点
     * @param radius 半径
     * @param material 材质
     * @param showInOutPoint 是否显示起点终点
     * @param meshList
     * @param name
     */
    addDrillRangeMeshToScene(start, end, radius, material, showInOutPoint, meshList, name) {
      let sp = start
      let ep = end

      // 是否显示进煤点和出煤点
      if (showInOutPoint) {
        // 进煤点
        //let calBottomPoint = new THREE.Mesh(new THREE.SphereGeometry(0.2), new THREE.MeshBasicMaterial({ color: 0x0000ff }))//0xff0000设置材质颜色为红色
        let calBottomPoint = new THREE.Mesh(new THREE.SphereGeometry(0.2), new THREE.MeshBasicMaterial({ color: 0x0000ff }))//0xff0000设置材质颜色为红色
        calBottomPoint.position.set(sp.x, sp.y, sp.z)
        calBottomPoint.name = name
        this.addCoalBottomPointMeshList.push(calBottomPoint)
        scene.add(calBottomPoint)

        // 出煤点
        //let calTopPoint = new THREE.Mesh(new THREE.SphereGeometry(0.2), new THREE.MeshBasicMaterial({ color: 0xff0000 }))//0xff0000设置材质颜色为红色
        let calTopPoint = new THREE.Mesh(new THREE.SphereGeometry(0.2), new THREE.MeshBasicMaterial({ color: 0xff0000 }))//0xff0000设置材质颜色为红色
        calTopPoint.position.set(ep.x, ep.y, ep.z)
        calTopPoint.name = name
        this.addCoalTopPointMeshList.push(calTopPoint)
        scene.add(calTopPoint)
      }

      let dis = Math.sqrt(((sp.x - ep.x) * (sp.x - ep.x)) + ((sp.y - ep.y) * (sp.y - ep.y)) + ((sp.z - ep.z) * (sp.z - ep.z)))

      const capGeo = new Mesh(new THREE.CapsuleGeometry(radius, dis, 6, 16), material)
      capGeo.position.x = (sp.x + ep.x) / 2
      capGeo.position.y = (sp.y + ep.y) / 2
      capGeo.position.z = (sp.z + ep.z) / 2
      let direction = new THREE.Vector3().subVectors(ep, sp).normalize()
      let currentDir = new THREE.Vector3(0, 1, 0)
      let quaternion = new THREE.Quaternion()
      quaternion.setFromUnitVectors(currentDir, direction)
      capGeo.applyQuaternion(quaternion)
      capGeo.updateMatrix()
      capGeo.name = name
      meshList.push(capGeo)
      scene.add(capGeo)
    },

    /**
     * 创建断层
     */
    createFault() {

      // 构造断层点
      let coalPoints = this.coalPointInfoList
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
      let coalPoints = this.coalPointInfoList
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
      this.coalPointInfoList.push(this.aoeCoalPoi)
      this.coalPoiShow = false
      this.aoeCoalPoi = false
      /* if (this.aoeCoalPoi.id != null) {
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
      } */
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
    },
    /**
     * 获取最近点位
     * @returns {*[]}
     */
    closestPoint(x0, y0, z0, x1, y1, z1, x2, y2, z2) {
      let M0 = [x0, y0, z0]
      let M1 = [x1, y1, z1]
      let s = [x2 - x1, y2 - y1, z2 - z1]
      let M0M1 = [M0[0] - M1[0], M0[1] - M1[1], M0[2] - M1[2]]
      let projection = this.projection(M0M1, s)
      return [M1[0] + projection[0], M1[1] + projection[1], M1[2] + projection[2]]
    },

    projection(v, u) {
      let scalarProduct = this.scalarProduct(v, u)
      let magnitudeSquared = this.magnitudeSquared(u)
      return [scalarProduct * u[0] / magnitudeSquared, scalarProduct * u[1] / magnitudeSquared, scalarProduct * u[2] / magnitudeSquared]
    },

    scalarProduct(v1, v2) {
      return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2]
    },

    magnitudeSquared(vector) {
      return vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]
    }
  }

}

class na {
  constructor() {
    (this.up = new THREE.Vector3(0, 0, 1)),
      (this.upAxes = 'z'),
      (this._color = new THREE.Color('#bfff00')),
      (this.tunnels = []),
      (this.crossTunnels = []),
      (this._uniformsGroup = { time: { value: 0 } }),
      (this._outerVisible = !0),
      (this._outerColor = new THREE.Color('#bfff00')),
      (this._arrowVisible = !1),
      (this._arrowSpace = 0),
      (this._arrowSpeed = 1),
      (this._arrowDirection = 1),
      (this.object3D = new THREE.Group()),
      (this.object3D.mineEngine = this),
      (this.object3D.name = 'Tunnel'),
      (this.segments = 5),
      (this.radialSegments = 7),
      (this.lineMaterial = new THREE.LineBasicMaterial({ color: '#ff0000' })),
      (this._lineVisible = !1),
      (this._lineColor = new THREE.Color('#ff0000'))
  }

  get uniformsGroup() {
    return this._uniformsGroup
  }

  load(e) {
    const t = new THREE.FileLoader()
    return new Promise((a, n) => {
      t.load(
        e,
        function(e) {
          a(JSON.parse(e))
        },
        function() {
        },
        function(e) {
          n(e)
        }
      )
    })
  }

  async loadTunnels(e, { map: t, offset: a = { x: 0, y: 0, z: 0 } }) {
    let n = await this.load(e)
    return (
      (n = this.resolveData(n)),
        (this.offset = a),
        await this.loadTextures(t),
        this.createTunnel(n),
        (this.object3D.position.x += this.originOffset.x),
        (this.object3D.position.y += this.originOffset.y),
        (this.object3D.position.z += this.originOffset.z),
        this.object3D
    )
  }

  createLine(e) {
    const t = []
    for (let a = 0; a < e.length; a++) {
      const n = e[a],
        i = []
      for (let e = 0; e < n.LeadNode.length; e++) {
        i.push(this.getVector3(n.LeadNode[e]))
      }
      t.push(i)
    }
    let a = this.addLine(t);
    (a.visible = !0), this.object3D.add(a)
  }

  createTunnel(e) {
    for (let t = 0; t < e.length; t++) {
      const a = e[t]
      if (!a.leadNodes) continue
      const n = new THREE.Group();
      (n.name = a.name || 'SingleGroup' + t), (n.userData.code = a.code)
      const i = this.addTunnel(a.leadNodes),
        s = i.getObjectByName('lines'),
        o = new aa({
          engine: this,
          id: a.code,
          name: a.name,
          type: 'Tunnel'
        }),
        r = new ta({
          code: a.code,
          name: a.name,
          parent: a.laneway_parent,
          object3D: n,
          wall: i,
          lines: s,
          color: this.color,
          outerDrawRange: {
            start: 0,
            count: i.geometry.groups[0].count,
            visible: !0
          },
          innerDrawRange: {
            start: i.geometry.groups[0].count,
            count: 1 / 0,
            visible: !0
          }
        })
      o.addComponent('Tunnel', r),
        this.tunnels.push(o),
        n.add(i),
        n.add(s),
        this.object3D.add(n)
    }
    for (let e = 0; e < this.crossNodes.length; e++) {
      const t = new THREE.Group()
      t.name = 'CrossGroup' + e
      const a = this.crossNodes[e]
      let n
      if (3 === a.length) {
        n = this.addSimpleTunnel([a[0], a[2], a[1]], t)
      } else {
        const e = a.pop()
        n = this.addCrossTunnel(a, e, t)
      }
      const i = t.getObjectByName('lines'),
        s = new aa({ engine: this, type: 'Tunnel' }),
        o = new ta({
          object3D: n,
          wall: n,
          lines: i,
          color: this.color,
          outerDrawRange: {
            start: 0,
            count: n.geometry.groups[0].count,
            visible: !0
          },
          innerDrawRange: {
            start: n.geometry.groups[0].count,
            count: 1 / 0,
            visible: !0
          }
        })
      s.addComponent('CrossTunnel', o),
        this.crossTunnels.push(s),
        this.object3D.add(t)
    }
  }

  addTunnel(e, t) {
    const a = this.makeMaterial()
    let n = new Qt({
      leadNodes: e,
      segments: this.segments,
      radialSegments: this.radialSegments,
      offset: this.offset
    })
    const i = new THREE.Mesh(n, a)
    i.renderOrder = 50
    const s = this.addLine(n.userData.originPts)
    return i.add(s), i
  }

  addSimpleTunnel(e, t) {
    const a = this.makeMaterial()
    let n = new Qt({
      nodes: e,
      segments: this.segments,
      radialSegments: this.radialSegments,
      offset: this.offset
    })
    const i = new THREE.Mesh(n, a);
    (i.renderOrder = 50), t.add(i)
    const s = this.addLine(n.userData.originPts)
    return t.add(s), i
  }

  addCrossTunnel(e, t, a) {
    const n = this.makeMaterial('cross')
    let i = new Qt({
      nodes: e,
      crossNode: t,
      segments: this.segments,
      radialSegments: this.radialSegments,
      order: !0,
      offset: this.offset
    })
    const s = new THREE.Mesh(i, n)
    a.add(s)
    const o = this.addLine(i.userData.originPts)
    return a.add(o), s
  }

  addLine(e) {
    const t = new THREE.Group()
    if (((t.name = 'lines'), !e)) return t
    for (let a = 0; a < e.length; a++) {
      let n = e[a]
      if (n.length < 2) continue
      const i = new THREE.BufferGeometry().setFromPoints(n),
        s = new THREE.Line(i, this.lineMaterial)
      t.add(s)
    }
    return (t.visible = !1), t
  }

  resolveData(e) {
    const t = {},
      a = {},
      n = [];
    (this.topIdData = t),
      (this.crossNodes = []),
      (this.endIdData = a),
      (this.resolvedEndIds = {})
    const i = new THREE.Box3()
    for (let s = 0; s < e.length; s++) {
      const o = e[s]
      let r = new THREE.Vector3()
      for (let e = 0; e < o.LeadNode.length; e++) {
        this.getVector3(o.LeadNode[e], r), i.expandByPoint(r)
      }
      (o.uuid = THREE.MathUtils.generateUUID()),
        o.topo_startid
          ? (t[o.topo_startid] || (t[o.topo_startid] = []),
            t[o.topo_startid].push(o))
          : n.push(o),
      o.topo_endid &&
      (a[o.topo_endid] || (a[o.topo_endid] = []),
        a[o.topo_endid].push(o))
    }
    const s = new THREE.Vector3()
    i.getCenter(s), (this.originOffset = s)
    for (let t = 0; t < e.length; t++) {
      const a = e[t]
      let n
      for (let e = 0; e < a.LeadNode.length; e++) {
        (n = a.LeadNode[e].position),
          (a.LeadNode[e].originPosition = n),
          (a.LeadNode[e].position = {
            X: n.X - s.x,
            Y: n.Y - s.y,
            Z: n.Z - s.z
          })
      }
    }
    for (let e = 0; e < n.length; e++) this.traverse(n[e])
    return (
      Object.keys(t).forEach((e) => {
        t[e].forEach((e) => this.traverse(e))
      }),
        e
    )
  }

  traverse(e) {
    let t = e.LeadNode
    if (t.length < 2) return
    const a = e.topo_endid
    if (a && !this.resolvedEndIds[a]) {
      this.resolvedEndIds[a] = !0
      let n = this.endIdData[a] || []
      n = n
        .filter((t) => t.uuid !== e.uuid)
        .filter((e) => e.LeadNode.length > 0)
      let i = this.topIdData[a]
      if (i && i.length > 0) {
        i = i.filter((e) => e.LeadNode.length > 0)
        const a = t[t.length - 2],
          s = t[t.length - 1],
          o = [a],
          r = []
        for (let e = 0; e < i.length; e++) {
          o.push(i[e].LeadNode[1]), r.push(i[e].LeadNode[1])
        }
        for (let e = 0; e < n.length; e++) {
          const t = n[e].LeadNode
          o.push(t[t.length - 2])
        }
        if (o.length < 2) return
        let l = !1
        2 === o.length && r.length
        let { interpolateNodes: h, length: c } = this.getInterpolateNodes(
          o,
          s
        );
        (t = this.insertNewNode(t, h[0], s, c, !1, l)), (e.LeadNode = t)
        for (let e = 0; e < i.length; e++) {
          i[e].LeadNode = this.insertNewNode(
            i[e].LeadNode,
            h[e + 1],
            s,
            c,
            !0,
            l
          )
        }
        for (let e = 0; e < n.length; e++) {
          n[e].LeadNode = this.insertNewNode(
            n[e].LeadNode,
            h[e + i.length + 1],
            s,
            c,
            !1,
            l
          )
        }
        (h = this.sortNodes(h, s)),
          this.crossNodes.push([...h, s]),
          i.forEach((e) => {
            e.leadNodes = this.resolveInterpolateNodes(e.LeadNode)
          })
      }
    }
    e.leadNodes = this.resolveInterpolateNodes(t)
  }

  insertNewNodeTwo(e, t, a) {
    return (
      a
        ? ((e = e.slice(1)), (e = [t, ...e]))
        : ((e = e.slice(0, e.length - 1)), (e = [...e, t])),
        e
    )
  }

  insertNewNode(e, t, a, n, i, s) {
    if (s) return this.insertNewNodeTwo(e, t, i)
    let o,
      r = this.getVector3(a),
      l = this.getVector3(t),
      h = l.clone().sub(r).normalize(),
      c = l.add(h.clone().multiplyScalar(0.1)),
      d = this.getNewNode(t, c)
    if (((d.code += '_c'), i)) {
      let a = 0,
        i = !0,
        s = !1
      for (let t = 1; t < e.length; t++) {
        o = this.getVector3(e[t])
        let l = o.distanceTo(r)
        if ((l >= n && (s = !0), l > n)) {
          (a = t), (i = a >= 1)
          break
        }
      }
      a === e.length - 1 && (s = !0),
      (e = e.slice(a)).length <= 3 && (i = !0)
      let l = !0
      for (let t = 0; t < e.length; t++) {
        o = this.getVector3(e[t])
        let n = o.sub(c),
          i = h.clone().negate(),
          s = n.dot(i)
        if (s < -0.996) {
          (a = t), (l = !1)
          break
        }
        if (s < 0 && o.distanceTo(c) > 2) {
          (a = t), (l = !1)
          break
        }
      }
      (e = e.slice(a)),
      l && (e = []),
        i ? (s || e.shift(), (e = [t, d, ...e])) : (e = [t, ...e])
    } else {
      let a = e.length - 2,
        i = !0,
        s = !1
      0 === a && (s = !0)
      for (let t = e.length - 2; t >= 0; t--) {
        o = this.getVector3(e[t])
        let l = o.distanceTo(r)
        if ((l >= n && (s = !0), l > n)) {
          (a = t), (i = a <= e.length - 2)
          break
        }
      }
      0 === a && (s = !0), (e = e.slice(0, a + 1)).length <= 3 && (i = !0)
      let l = !0
      for (let t = e.length - 1; t >= 0; t--) {
        o = this.getVector3(e[t])
        let n = o.sub(c),
          i = h.clone().negate(),
          s = n.dot(i)
        if (s < -0.996) {
          (a = t), (l = !1)
          break
        }
        if (s < 0 && o.distanceTo(c) > 2) {
          (a = t), (l = !1)
          break
        }
      }
      (e = e.slice(0, a + 1)),
      l && (e = []),
        i ? (s || e.pop(), e.push(d, t)) : e.push(t)
    }
    return e
  }

  sortNodes(e, t) {
    const a = this.getVector3(t),
      n = e[0],
      i = this.getVector3(n),
      s = a.clone().sub(i).normalize(),
      o = [],
      r = []
    for (let t = 1; t < e.length; t++) {
      const n = this.getVector3(e[t]).sub(a).normalize(),
        i = s.dot(n),
        l = n.cross(s).normalize().dot(this.up)
      l > 0
        ? o.push({ node: e[t], cosA: i, cosB: l })
        : r.push({ node: e[t], cosA: i, cosB: l })
    }
    o.sort((e, t) => e.cosA - t.cosA), r.sort((e, t) => t.cosA - e.cosA)
    const l = [n]
    return (
      o.forEach((e) => l.push(e.node)), r.forEach((e) => l.push(e.node)), l
    )
  }

  resolveInterpolateNodes(e) {
    if (0 === e.length) return []
    if (1 === e.length) return
    if (2 === e.length) return [e]
    const t = []
    let a
    for (let n = 1; n < e.length - 1; n++) {
      a || (a = e[n - 1])
      const i = e[n],
        s = e[n + 1]
      let o = i.leftWidth + i.rightWidth
      const r = o / 2,
        l = this.getVector3(a),
        h = this.getVector3(i),
        c = this.getVector3(s),
        d = h.distanceTo(l),
        p = c.distanceTo(h),
        u = l.clone().sub(h).normalize(),
        m = c.clone().sub(h).normalize(),
        g = this.getInnerLength(l, c, h, r)
      if (
        ((o = Math.max(o, g)),
        o > 2 * r && (o += 1),
        u.dot(m) < -0.98 || d < o || p < o)
      ) {
        t.push([a, i]), (a = i), n === e.length - 1 && t.push([i, s])
      } else {
        u.multiplyScalar(o).add(h)
        const r = this.getNewNode(i, u);
        (r.code += '_c1'), t.push([a, r]), m.multiplyScalar(o).add(h)
        const l = this.getNewNode(i, m);
        (l.code += '_c2'),
          t.push([r, i, l]),
          (a = l),
        n === e.length - 1 && t.push([l, s])
      }
    }
    return t.push([a, e[e.length - 1]]), t
  }

  getInterpolateNodes(e, t) {
    e = JSON.parse(JSON.stringify(e))
    const a = this.sortNodes(e, t)
    let n = t.leftWidth + t.rightWidth,
      i = n / 2
    const s = this.getVector3(t),
      o = new THREE.Vector3()
    if (e.length > 2) {
      e.reduce((e, t) => e + t.position.Z, t.position.Z)
      e.length, e.forEach((e) => (e.position.Z = t.position.Z))
    }
    n = null
    for (let e = 0; e < a.length; e++) {
      const t = a[e]
      let o
      o = e === a.length - 1 ? a[0] : a[e + 1]
      let r = this.getInnerLength(
        this.getVector3(t),
        this.getVector3(o),
        s,
        i
      )
      r === 1 / 0 && (r = 0),
      n || (n = r),
        (n = 2 === a.length ? Math.max(Math.min(n, r), i) : Math.max(n, r))
    }
    const r = []
    for (let a = 0; a < e.length; a++) {
      const i = e[a]
      this.getVector3(i, o), o.sub(s).normalize().multiplyScalar(n).add(s)
      let l = ea.fromJson(i)
      0 === a && (l = ea.fromJson(t)),
        (l.code = '插值点' + a),
        (l.position.X = o.x),
        (l.position.Y = o.y),
        (l.position.Z = o.z),
        r.push(l)
    }
    return { interpolateNodes: r, length: n }
  }

  getInnerLength(e, t, a, n) {
    const i = e.clone().sub(a).normalize(),
      s = t.clone().sub(a).normalize(),
      o = Math.acos(i.dot(s).toFixed(3))
    return n / Math.tan(o / 2)
  }

  getNewNode(e, t) {
    const a = ea.fromJson(e)
    return (
      (a.position.X = t.x), (a.position.Y = t.y), (a.position.Z = t.z), a
    )
  }

  getVector3(e, t) {
    return (
      t || (t = new THREE.Vector3()),
        t.set(e.position.X, e.position.Y, e.position.Z),
        t
    )
  }

  addBallByLeadNode(e) {
    e.forEach((e) => {
      this.addBall(this.getVector3(e), e.code)
    })
  }

  addBall(e, t) {
    const a = new THREE.SphereGeometry(0.02, 32, 16),
      n = new THREE.MeshBasicMaterial({ color: 16776960 }),
      i = new THREE.Mesh(a, n)
    i.position.copy(e), this.object3D.add(i), t && this.addLabel(i, t)
  }

  addLabel(e, t) {
    const a = document.createElement('div');
    (a.className = 'label'),
      (a.textContent = t),
      (a.style.marginTop = '-1em')
    const n = new THREE.CSS2DObject(a)
    e.add(n)
  }

  get color() {
    return this._color
  }

  set color(e) {
    'string' == typeof e
      ? this._color.set(e)
      : e instanceof THREE.Color && this._color.copy(e)
  }

  makeMaterial(e) {
    if (this.materials) {
      if ('cross' === e) {
        const e = this.materials.slice(1, 6),
          t = e.pop()
        return e.unshift(t), e
      }
      return this.materials.slice(0, 5)
    }
    const t = this,
      a = this.images,
      n = new THREE.CanvasTexture(a[0]);
    (n.encoding = THREE.sRGBEncoding),
      (n.wrapS = THREE.RepeatWrapping),
      (n.wrapT = THREE.RepeatWrapping)
    const i = new THREE.MeshPhongMaterial({
      map: n,
      wireframe: !1,
      polygonOffset: !0,
      polygonOffsetFactor: -1,
      polygonOffsetUnits: -4,
      shininess: 50,
      vertexColors: !0,
      specular: new THREE.Color(0.0056, 0.0056, 0.0056)
    })
    i.onBeforeCompile = function(e) {
      (e.uniforms.time = t.uniformsGroup.time),
        (e.uniforms.aspect = { value: 1 }),
        (e.uniforms.space = { value: 4 }),
        (e.vertexShader = e.vertexShader
          .replace(
            '#include <common>',
            '#define GLSLIFY 1\n#include <common>\nattribute vec4 property;varying vec4 vProperty;'
          )
          .replace(
            '#include <begin_vertex>',
            '#define GLSLIFY 1\n#include <begin_vertex>\nvProperty=vec4(0.0,0.0,0.0,0.0);vProperty.x=property.x;vProperty.y=property.y;vProperty.z=abs(property.y)<=0.0 ? 1.0 : property.y/abs(property.y);vProperty.w=property.w;'
          )),
        (e.fragmentShader = e.fragmentShader
          .replace(
            '#include <common>',
            '#define GLSLIFY 1\n#include <common>\nvarying vec4 vProperty;uniform float time;uniform float aspect;'
          )
          .replace(
            '#include <map_fragment>',
            '#define GLSLIFY 1\n#ifdef USE_MAP\nfloat u=vUv.x/aspect-time*vProperty.y;float v=vUv.y;float f=1.0;float space=vProperty.w;if(space>0.0){u=mod(u,space+1.0);f=step(0.0,u)*(1.0-step(1.0,u));}vec4 texelColor=texture2D(map,vec2(u,v)*vProperty.z)*f;\n#endif\n'
          )
          .replace(
            '#include <output_fragment>',
            '#define GLSLIFY 1\n#ifdef OPAQUE\ndiffuseColor.a=1.0;\n#endif\n#ifdef USE_TRANSMISSION\ndiffuseColor.a*=transmissionAlpha+0.1;\n#endif\n#ifdef USE_MAP\noutgoingLight=mix(outgoingLight,texelColor.rgb,texelColor.a*vProperty.x);\n#endif\ngl_FragColor=vec4(outgoingLight,diffuseColor.a);'
          ))
    }
    const s = []
    for (let e = 1; e < a.length; e++) {
      const t = new THREE.CanvasTexture(a[e]);
      (t.wrapS = THREE.RepeatWrapping),
        (t.wrapT = THREE.RepeatWrapping),
        s.push(new THREE.MeshBasicMaterial({ map: t }))
    }
    const o = new THREE.MeshPhongMaterial({
      polygonOffset: !0,
      polygonOffsetFactor: -1,
      polygonOffsetUnits: -4,
      shininess: 50,
      vertexColors: !0
    })
    if (((this.materials = [i, ...s, o]), 'cross' === e)) {
      const e = this.materials.slice(1, 6),
        t = e.pop()
      return e.unshift(t), e
    }
    return this.materials.slice(0, 5)
  }

  makeBasicMaterial() {
    return (
      this.basicMaterial ||
      (this.basicMaterial = new THREE.MeshBasicMaterial({
        color: new THREE.Color(0, 1, 0),
        depthTest: !0,
        depthWrite: !1,
        opacity: 0.5,
        polygonOffset: !0,
        polygonOffsetFactor: -4,
        polygonOffsetUnits: -4,
        transparent: !0
      })),
        this.basicMaterial
    )
  }

  loadTextures(e) {
    const t = [e.arrowMap, e.leftMap, e.bottomMap, e.rightMap, e.topMap],
      a = new THREE.ImageBitmapLoader()
    return Promise.all(t.map((e) => this.loadImg(e, a))).then((e) => {
      this.images = e
    })
  }

  loadImg(e, t) {
    return new Promise((a) => {
      t.load(e, function(e) {
        a(e)
      })
    })
  }

  update(e) {
    this.uniformsGroup.time.value = e / 1e3
  }

  set outerVisible(e) {
    this._outerVisible = e
    for (let t = 0, a = this.tunnels.length; t < a; t++) {
      this.tunnels[t].getComponent('Tunnel').outerVisible = e
    }
    for (let t = 0, a = this.crossTunnels.length; t < a; t++) {
      this.crossTunnels[t].getComponent('CrossTunnel').outerVisible = e
    }
  }

  get outerVisible() {
    return this._outerVisible
  }

  set innerVisible(e) {
    this._outerVisible = e
    for (let t = 0, a = this.tunnels.length; t < a; t++) {
      this.tunnels[t].getComponent('Tunnel').innerVisible = e
    }
    for (let t = 0, a = this.crossTunnels.length; t < a; t++) {
      this.crossTunnels[t].getComponent('CrossTunnel').innerVisible = e
    }
  }

  get innerVisible() {
    return this._outerVisible
  }

  get outerColor() {
    return this._outerColor
  }

  set outerColor(e) {
    this._outerColor.set(e)
    for (let t = 0, a = this.tunnels.length; t < a; t++) {
      this.tunnels[t].getComponent('Tunnel').color = e
    }
    for (let t = 0, a = this.crossTunnels.length; t < a; t++) {
      this.crossTunnels[t].getComponent('CrossTunnel').color = e
    }
  }

  set lineVisible(e) {
    this._lineVisible = e
    for (let t = 0, a = this.tunnels.length; t < a; t++) {
      this.tunnels[t].getComponent('Tunnel').lineVisible = e
    }
    for (let t = 0, a = this.crossTunnels.length; t < a; t++) {
      this.crossTunnels[t].getComponent('CrossTunnel').lineVisible = e
    }
  }

  get lineVisible() {
    return this._lineVisible
  }

  set lineColor(e) {
    'string' == typeof e
      ? this._lineColor.set(e)
      : e instanceof THREE.Color && this._lineColor.copy(e),
      this.lineMaterial.color.copy(this._lineColor)
  }

  get lineColor() {
    return this._lineColor
  }

  get arrowVisible() {
    return this._arrowVisible
  }

  set arrowVisible(e) {
    this._arrowVisible = e
    for (let t = 0, a = this.tunnels.length; t < a; t++) {
      this.tunnels[t].getComponent('Tunnel').arrowVisible = e
    }
  }

  get arrowSpace() {
    return this._arrowSpace
  }

  set arrowSpace(e) {
    this._arrowSpace = e
    for (let t = 0, a = this.tunnels.length; t < a; t++) {
      this.tunnels[t].getComponent('Tunnel').arrowSpace = e
    }
  }

  get arrowSpeed() {
    return this._arrowSpeed
  }

  set arrowSpeed(e) {
    this._arrowSpeed = e
    for (let t = 0, a = this.tunnels.length; t < a; t++) {
      this.tunnels[t].getComponent('Tunnel').flowSpeed = e
    }
  }

  get arrowDirection() {
    return this._arrowSpeed
  }

  set arrowDirection(e) {
    this._arrowDirection = e
    for (let t = 0, a = this.tunnels.length; t < a; t++) {
      this.tunnels[t].getComponent('Tunnel').flowDirection = e
    }
  }
}

class ea {
  constructor() {
    Kt(this, 'code', ''),
      Kt(this, 'name', ''),
      Kt(this, 'height', 3.5),
      Kt(this, 'position', { X: 0, Y: 0, Z: 0 }),
      Kt(this, 'leftWidth', 2),
      Kt(this, 'leftWidth2', 0),
      Kt(this, 'rightWidth', 2),
      Kt(this, 'rightWidth2', 0),
      Kt(this, 'useSecond', !1),
      Kt(this, 'laneway_code', '')
  }

  normal(e) {
    return {
      x: e.position.X - this.position.X,
      y: e.position.Y - this.position.Y,
      z: e.position.Z - this.position.Z
    }
  }

  static fromJson(e) {
    let t = new ea()
    return (
      Object.assign(t, e),
        (t.position = {}),
        Object.assign(t.position, e.position),
        t
    )
  }
}

class ta {
  constructor(e = {}) {
    (this.code = e.code),
      (this.name = e.name),
      (this.parent = e.parent),
      (this._arrowSpace = 0),
      (this._entity = e.entity),
      (this._engine = e.engine),
      (this._flowDirection = 1),
      (this._flowSpeed = 1),
      (this._arrowVisible = !1),
      (this._object3D = e.object3D),
      (this._outerDrawRange = e.outerDrawRange),
      (this._innerDrawRange = e.innerDrawRange),
      (this._renderOrder = 50),
      (this._wall = e.wall),
      (this._visible = !0),
      (this._color = new THREE.Color()),
      (this._lines = e.lines),
    e.color &&
    ('string' == typeof e.color
      ? this._color.set(e.color)
      : e.color instanceof THREE.Color && this._color.copy(e.color)),
      this.addColor(),
      this.addProperty()
  }

  addColor() {
    const e = this._wall.geometry.attributes.position.array.length,
      t = []
    for (let a = 0; a < e; a += 3) {
      t.push(this.color.r, this.color.g, this.color.b)
    }
    this._wall.geometry.setAttribute(
      'color',
      new THREE.Float32BufferAttribute(t, 3)
    )
  }

  addProperty() {
    const e = this._wall.geometry.attributes.position.count,
      t = []
    for (let a = 0; a < e; a++) t.push(0, 1, 1, 0)
    this._wall.geometry.setAttribute(
      'property',
      new THREE.Float32BufferAttribute(t, 4)
    )
  }

  get arrowSpace() {
    return this._arrowSpace
  }

  set arrowSpace(e) {
    this._arrowSpace = e
    const t = this._wall.geometry.getAttribute('property')
    for (let e = 0; e < t.count; e++) t.array[4 * e + 3] = this._arrowSpace
    t.needsUpdate = !0
  }

  get arrowVisible() {
    return this._arrowVisible
  }

  set arrowVisible(e) {
    this._arrowVisible = e
    const t = this._wall.geometry.getAttribute('property')
    for (let e = 0; e < t.count; e++) t.array[4 * e] = this._arrowVisible
    t.needsUpdate = !0
  }

  get engine() {
    return this._engine
  }

  get entity() {
    return this._entity
  }

  get flowDirection() {
    return this._flowDirection
  }

  set flowDirection(e) {
    this._flowDirection = e
    const t = this._wall.geometry.getAttribute('property')
    for (let e = 0; e < t.count; e++) {
      t.array[4 * e + 1] = this._flowSpeed * this._flowDirection
    }
    t.needsUpdate = !0
  }

  get flowSpeed() {
    return this._flowSpeed
  }

  set flowSpeed(e) {
    this._flowSpeed = e
    const t = this._wall.geometry.getAttribute('property')
    for (let e = 0; e < t.count; e++) {
      t.array[4 * e + 1] = this._flowSpeed * this._flowDirection
    }
    t.needsUpdate = !0
  }

  get object3D() {
    return this._object3D
  }

  get visible() {
    return this._visible
  }

  set visible(e) {
    (this._visible = e), (this._object3D.visible = e)
  }

  get outerVisible() {
    return this._outerDrawRange.visible
  }

  set outerVisible(e) {
    (this._outerDrawRange.visible = e), this.updateDrawRange()
  }

  get innerVisible() {
    return this._innerDrawRange.visible
  }

  set innerVisible(e) {
    (this._innerDrawRange.visible = e), this.updateDrawRange()
  }

  get lineVisible() {
    return this._lines.visible
  }

  set lineVisible(e) {
    this._lines.visible = e
  }

  updateDrawRange() {
    this.outerVisible && this.innerVisible
      ? this._wall.geometry.setDrawRange(0, 1 / 0)
      : this.outerVisible && !this.innerVisible
        ? this._wall.geometry.setDrawRange(
          this._outerDrawRange.start,
          this._outerDrawRange.count
        )
        : !this.outerVisible && this.innerVisible
          ? this._wall.geometry.setDrawRange(
            this._innerDrawRange.start,
            this._innerDrawRange.count
          )
          : this._wall.geometry.setDrawRange(0, 0)
  }

  get color() {
    return this._color
  }

  set color(e) {
    'string' == typeof e
      ? this._color.set(e)
      : e instanceof THREE.Color && this._color.copy(e)
    const t = this._wall.geometry.getAttribute('color'),
      a = t.array
    for (let e = 0; e < a.length; e += 3) {
      (a[e] = this.color.r),
        (a[e + 1] = this.color.g),
        (a[e + 2] = this.color.b)
    }
    t.needsUpdate = !0
  }
}

class Qt extends THREE.BufferGeometry {
  constructor({
    nodes: e,
    crossNode: t,
    leadNodes: a,
    segments: n = 12,
    radialSegments: i = 12,
    order: s = !0,
    offset: o = { x: 0, y: 0, z: 0 }
  }) {
    super(),
    i % 2 != 0 && (i += 1),
    n < 2 && (n = 2),
      (this.parameters = {
        nodes: e,
        crossNode: t,
        radialSegments: i,
        segments: n,
        order: s,
        leadNodes: a,
        offset: o
      }),
      (this.unitAxes = {
        x: new THREE.Vector3(1, 0, 0),
        y: new THREE.Vector3(0, 1, 0),
        z: new THREE.Vector3(0, 0, 1)
      }),
      this.create()
  }

  create() {
    const e = this.getSections()
    let t
    this.parameters.crossNode && (t = e.pop().sections[0])
    const a = [],
      n = [],
      i = [],
      s = [],
      o = []
    let r = 0
    const l = [],
      h = [],
      c = this.parameters.radialSegments,
      d = c / 2,
      p = [],
      u = [],
      m = [],
      g = []
    for (let t = 0; t < e.length; t++) {
      let { sections: d, drawSide: f, needClose: y, type: E } = e[t]
      if (d.length < 2) return
      d = d.filter((e) => e.secType !== Qt.Circle)
      let v = d.length - 1
      const b = c / 2
      let w = b + 1 + 4
      f === Qt.DoubleSide && (w = c + 1 + 6)
      const C = [],
        R = [],
        S = []
      a.push(S)
      for (let e = 0; e < d.length; e++) {
        const t = d[e]
        S.push(t.Origin),
        f !== Qt.DoubleSide &&
        (C.push(r + e * w + 4), R.push(r + e * w + 3))
        const a = t.Points[0],
          l = t.Points[t.Points.length - 1],
          h = t.PointNormals[0],
          y = t.PointNormals[t.PointNormals.length - 1]
        if (f === Qt.LeftSide) {
          i.push(l.x, l.y, l.z),
            i.push(t.A.x, t.A.y, t.A.z),
            s.push(y.x, y.y, y.z),
            s.push(t.normalA.x, t.normalA.y, t.normalA.z),
            o.push(e / v, 0),
            o.push(e / v, 1),
            i.push(t.A.x, t.A.y, t.A.z),
            i.push(t.O.x, t.O.y, t.O.z),
            s.push(t.normalA2.x, t.normalA2.y, t.normalA2.z),
            s.push(t.normalO.x, t.normalO.y, t.normalO.z),
            o.push(1 - e / v, 1),
            o.push(1 - e / v, 0.4)
          for (let a = b, l = 0; a < t.Points.length; a++, l++) {
            let { x: h, y: u, z: m } = t.Points[a]
            i.push(h, u, m)
            let g = t.PointNormals[a]
            s.push(g.x, g.y, g.z),
              o.push(1 - e / v, (1 - a / c) / 1.25),
            e < d.length - 1 &&
            a < t.Points.length - 1 &&
            (n.push(
              r + e * w + l + 4,
              r + (e + 1) * w + (l + 1) + 4,
              r + e * w + (l + 1) + 4
            ),
              n.push(
                r + e * w + l + 4,
                r + (e + 1) * w + l + 4,
                r + (e + 1) * w + (l + 1) + 4
              ),
              p.push([
                r + e * w + l + 4,
                r + (e + 1) * w + (l + 1) + 4,
                r + e * w + (l + 1) + 4
              ]),
              p.push([
                r + e * w + l + 4,
                r + (e + 1) * w + l + 4,
                r + (e + 1) * w + (l + 1) + 4
              ]))
          }
          e < d.length - 1 &&
          (n.push(r + e * w, r + (e + 1) * w + 1, r + e * w + 1),
            n.push(r + e * w, r + (e + 1) * w, r + (e + 1) * w + 1),
            u.push([r + e * w, r + (e + 1) * w + 1, r + e * w + 1]),
            u.push([r + e * w, r + (e + 1) * w, r + (e + 1) * w + 1]),
            n.push(r + e * w + 2, r + (e + 1) * w + 3, r + e * w + 3),
            n.push(r + e * w + 2, r + (e + 1) * w + 2, r + (e + 1) * w + 3),
            g.push([r + e * w + 2, r + (e + 1) * w + 3, r + e * w + 3]),
            g.push([
              r + e * w + 2,
              r + (e + 1) * w + 2,
              r + (e + 1) * w + 3
            ]))
        } else if (f === Qt.RightSide) {
          i.push(a.x, a.y, a.z),
            i.push(t.B.x, t.B.y, t.B.z),
            s.push(h.x, h.y, h.z),
            s.push(t.normalB.x, t.normalB.y, t.normalB.z),
            o.push(e / v, 1),
            o.push(e / v, 0),
            i.push(t.B.x, t.B.y, t.B.z),
            i.push(t.O.x, t.O.y, t.O.z),
            s.push(t.normalB2.x, t.normalB2.y, t.normalB2.z),
            s.push(t.normalO.x, t.normalO.y, t.normalO.z),
            o.push(1 - e / v, 0),
            o.push(1 - e / v, 0.4)
          for (let a = b, l = 0; a >= 0; a--, l++) {
            let { x: h, y: c, z: u } = t.Points[a]
            i.push(h, c, u)
            let m = t.PointNormals[a]
            s.push(m.x, m.y, m.z),
              o.push(1 - e / v, 0.6 + l * (0.4 / b)),
            e < d.length - 1 &&
            a > 0 &&
            (n.push(
              r + e * w + (l + 1) + 4,
              r + (e + 1) * w + l + 4,
              r + e * w + l + 4
            ),
              n.push(
                r + e * w + (l + 1) + 4,
                r + (e + 1) * w + (l + 1) + 4,
                r + (e + 1) * w + l + 4
              ),
              p.push([
                r + e * w + (l + 1) + 4,
                r + (e + 1) * w + l + 4,
                r + e * w + l + 4
              ]),
              p.push([
                r + e * w + (l + 1) + 4,
                r + (e + 1) * w + (l + 1) + 4,
                r + (e + 1) * w + l + 4
              ]))
          }
          e < d.length - 1 &&
          (n.push(r + e * w + 3, r + (e + 1) * w + 2, r + e * w + 2),
            n.push(r + e * w + 3, r + (e + 1) * w + 3, r + (e + 1) * w + 2),
            g.push([r + e * w + 3, r + (e + 1) * w + 2, r + e * w + 2]),
            g.push([r + e * w + 3, r + (e + 1) * w + 3, r + (e + 1) * w + 2]),
            n.push(r + e * w + 1, r + (e + 1) * w, r + e * w),
            n.push(r + e * w + 1, r + (e + 1) * w + 1, r + (e + 1) * w),
            m.push([r + e * w + 1, r + (e + 1) * w, r + e * w]),
            m.push([r + e * w + 1, r + (e + 1) * w + 1, r + (e + 1) * w]))
        } else if (f === Qt.DoubleSide) {
          const f = t.distance / 4
          i.push(l.x, l.y, l.z),
            i.push(t.A.x, t.A.y, t.A.z),
            s.push(y.x, y.y, y.z),
            s.push(t.normalA.x, t.normalA.y, t.normalA.z),
            o.push(f, 0),
            o.push(f, 1),
            i.push(t.A.x, t.A.y, t.A.z),
            i.push(t.B.x, t.B.y, t.B.z),
            s.push(t.normalA2.x, t.normalA2.y, t.normalA2.z),
            s.push(t.normalB2.x, t.normalB2.y, t.normalB2.z),
            o.push(f, 0),
            o.push(f, 1),
            i.push(t.B.x, t.B.y, t.B.z),
            i.push(a.x, a.y, a.z),
            s.push(t.normalB.x, t.normalB.y, t.normalB.z),
            s.push(h.x, h.y, h.z),
            o.push(f, 0),
            o.push(f, 1)
          for (let a = 0; a < t.Points.length; a++) {
            let { x: l, y: h, z: u } = t.Points[a]
            i.push(l, h, u)
            let m = t.PointNormals[a]
            s.push(m.x, m.y, m.z),
              o.push(f, a / c),
            e < d.length - 1 &&
            a < t.Points.length - 1 &&
            (n.push(
              r + e * w + a + 6,
              r + (e + 1) * w + (a + 1) + 6,
              r + e * w + (a + 1) + 6
            ),
              n.push(
                r + e * w + a + 6,
                r + (e + 1) * w + a + 6,
                r + (e + 1) * w + (a + 1) + 6
              ),
              p.push([
                r + e * w + a + 6,
                r + (e + 1) * w + (a + 1) + 6,
                r + e * w + (a + 1) + 6
              ]),
              p.push([
                r + e * w + a + 6,
                r + (e + 1) * w + a + 6,
                r + (e + 1) * w + (a + 1) + 6
              ]))
          }
          e < d.length - 1 &&
          (n.push(r + e * w, r + (e + 1) * w + 1, r + e * w + 1),
            n.push(r + e * w, r + (e + 1) * w, r + (e + 1) * w + 1),
            u.push([r + e * w, r + (e + 1) * w + 1, r + e * w + 1]),
            u.push([r + e * w, r + (e + 1) * w, r + (e + 1) * w + 1]),
            n.push(r + e * w + 2, r + (e + 1) * w + 3, r + e * w + 3),
            n.push(r + e * w + 2, r + (e + 1) * w + 2, r + (e + 1) * w + 3),
            g.push([r + e * w + 2, r + (e + 1) * w + 3, r + e * w + 3]),
            g.push([r + e * w + 2, r + (e + 1) * w + 2, r + (e + 1) * w + 3]),
            n.push(r + e * w + 4, r + (e + 1) * w + 5, r + e * w + 5),
            n.push(r + e * w + 4, r + (e + 1) * w + 4, r + (e + 1) * w + 5),
            m.push([r + e * w + 4, r + (e + 1) * w + 5, r + e * w + 5]),
            m.push([
              r + e * w + 4,
              r + (e + 1) * w + 4,
              r + (e + 1) * w + 5
            ]))
        }
      }
      y && (l.push(C), h.push(R)), (r += w * d.length)
    }
    if (this.parameters.crossNode) {
      const a = i.length / 3
      i.push(t.Points[d].x, t.Points[d].y, t.Points[d].z),
        s.push(t.PointNormals[d].x, t.PointNormals[d].y, t.PointNormals[d].z),
        o.push(0.5, 0.5)
      const r = i.length / 3
      i.push(t.O.x, t.O.y, t.O.z),
        s.push(t.normalO.x, t.normalO.y, t.normalO.z),
        o.push(0.5, 0.5)
      for (let t = 0; t < l.length; t++) {
        const i = l[t],
          s = h[t],
          { drawSide: o } = e[t]
        if (!(i.length < 3)) {
          for (let e = 0; e < i.length - 1; e++) {
            o === Qt.LeftSide
              ? (n.push(a, i[e + 1], i[e]),
                n.push(r, s[e], s[e + 1]),
                p.push([a, i[e + 1], i[e]]),
                g.push([r, s[e], s[e + 1]]))
              : o === Qt.RightSide &&
              (n.push(a, i[e], i[e + 1]),
                n.push(r, s[e + 1], s[e]),
                p.push([a, i[e], i[e + 1]]),
                g.push([r, s[e + 1], s[e]]))
          }
        }
      }
    }

    function f(e) {
      n.push(e[0], e[2], e[1])
    }

    this.addGroup(0, n.length, 0),
      this.addGroup(n.length, 3 * u.length, 1),
      u.forEach(f),
      this.addGroup(n.length, 3 * g.length, 2),
      g.forEach(f),
      this.addGroup(n.length, 3 * m.length, 3),
      m.forEach(f),
      this.addGroup(n.length, 3 * p.length, 4),
      p.forEach(f),
      this.setIndex(n),
      this.setAttribute('position', new THREE.Float32BufferAttribute(i, 3)),
      this.setAttribute('normal', new THREE.Float32BufferAttribute(s, 3)),
      this.setAttribute('uv', new THREE.Float32BufferAttribute(o, 2)),
      (this.userData.originPts = a)
  }

  getSections() {
    if (this.parameters.leadNodes) {
      const e = this.parameters.leadNodes,
        t = [],
        a = []
      let n = []
      for (let i = 0; i < e.length; i++) {
        const s = this.getStraightSections(e[i])[0]
        if (0 === i) {
          t.push(s), (n = s.sections)
        } else {
          let e = n[n.length - 1],
            i = s.sections[0]
          this.unitAxes.z.dot(e.normalO) < this.unitAxes.z.dot(i.normalO)
            ? (s.sections[0] = e)
            : (n[n.length - 1] = i),
            a.push(...n),
            t.push(s),
            (n = s.sections)
        }
        i === e.length - 1 && a.push(...n)
      }
      let i,
        s,
        o = 0
      for (let e = 0; e < a.length; e++) {
        0 !== e
          ? ((i = a[e - 1].O),
            (s = a[e].O),
            (o += i.distanceTo(s)),
            (a[e].distance = o))
          : (a[0].distance = o)
      }
      return t
    }
    return this.parameters.crossNode
      ? this.getCrossSections()
      : this.getStraightSections()
  }

  getStraightSections(e) {
    const t = [],
      a = []
    e = e || this.parameters.nodes
    for (let a = 0; a < e.length; a++) {
      const n = e[a].position,
        i = new THREE.Vector3(n.X, n.Y, n.Z)
      t.push(i)
    }
    if (2 === t.length) {
      const n = t[1].clone().sub(t[0]),
        i = n.clone().normalize(),
        s = this.calculateSectionPoint(e[0], i),
        o = this.calculateSectionPoint(e[1], i),
        r = e[0].leftWidth + e[0].rightWidth,
        l = this.interpolateStraightSections(s, o, 4 * r, n)
      a.push({
        sections: l,
        drawSide: Qt.DoubleSide,
        type: Qt.StraightTunnel
      })
    } else {
      if (3 !== t.length) return
      {
        const n = t[0],
          i = t[1],
          s = t[2],
          o = i.clone().sub(n).normalize(),
          r = s.clone().sub(i).normalize(),
          l = this.calculateSectionPoint(e[0], o),
          h = this.calculateSectionPoint(e[2], r),
          c = this.interpolateSections(l, h, o, r)
        a.push({
          sections: c,
          drawSide: Qt.DoubleSide,
          type: Qt.CurveTunnel
        })
      }
    }
    return a
  }

  getCrossSections() {
    const e = this.parameters.nodes
    if (Array.isArray(e[0])) return this.getVerticalCrossSections()
    const t = this.parameters.crossNode.position,
      a = new THREE.Vector3(t.X, t.Y, t.Z),
      n = [],
      i = []
    let s
    for (let t = 0; t < e.length; t++) {
      const a = e[t].position,
        i = new THREE.Vector3(a.X, a.Y, a.Z)
      n.push(i)
    }
    for (let t = 0; t < n.length; t++) {
      const o = a.clone().sub(n[t]).normalize()
      let r, l
      t === n.length - 1
        ? ((r = n[0].clone().sub(a).normalize()), (l = e[0]))
        : ((r = n[t + 1].clone().sub(a).normalize()), (l = e[t + 1])),
      s || (s = this.calculateSectionPoint(this.parameters.crossNode, o))
      const h = this.calculateSectionPoint(e[t], o),
        c = this.calculateSectionPoint(l, r),
        d = this.interpolateSections(h, c, o, r),
        p = o.clone().cross(r).dot(h.normalA2)
      i.push({
        sections: d,
        drawSide: this.parameters.order ? Qt.RightSide : Qt.LeftSide,
        needClose: p > 0,
        type: Qt.CrossTunnel
      })
    }
    return i.push({ sections: [s], drawSide: Qt.DoubleSide }), i
  }

  getVerticalCrossSections() {
    const e = this.parameters.crossNode.position,
      t = new THREE.Vector3(e.X, e.Y, e.Z),
      a = this.parameters.nodes,
      n = a[0][0],
      i = a[0][1],
      s = a[1][0],
      o = new THREE.Vector3(n.position.X, n.position.Y, n.position.Z),
      r = new THREE.Vector3(i.position.X, i.position.Y, i.position.Z),
      l = new THREE.Vector3(s.position.X, s.position.Y, s.position.Z),
      h = o.clone().sub(t).normalize(),
      c = r.clone().sub(t).normalize(),
      d = l.clone().sub(t).normalize(),
      p = this.calculateSectionPoint(n, h),
      u = this.calculateSectionPoint(i, c.clone().negate()),
      m = this.calculateSectionPoint(s, d),
      g = []
    if (n.position.Y > s.position.Y) {
      const e = this.interpolateSections(p, u, h, c.clone().negate())
      if (
        (g.push({
          sections: e,
          drawSide: Qt.TopSide,
          type: Qt.VerticalCrossTunnel
        }),
        h.dot(d) > 0)
      ) {
        const e = this.interpolateSections(p, m, h.clone().negate(), d, !0)
        g.push({
          sections: e,
          drawSide: Qt.StraightCircleBottomSide,
          type: Qt.VerticalCrossTunnel
        })
        const t = this.interpolateSections(u, m, c.clone().negate(), d)
        g.push({
          sections: t,
          drawSide: Qt.StraightBottomSide,
          type: Qt.VerticalCrossTunnel
        })
      } else {
        const e = this.interpolateSections(u, m, c.clone().negate(), d, !0)
        g.push({
          sections: e,
          drawSide: Qt.StraightCircleBottomSide,
          type: Qt.VerticalCrossTunnel
        })
        const t = this.interpolateSections(p, m, h.clone().negate(), d)
        g.push({
          sections: t,
          drawSide: Qt.StraightBottomSide,
          type: Qt.VerticalCrossTunnel
        })
      }
    }
    return g
  }

  interpolateStraightSections(e, t, a, n) {
    const i = n.clone().normalize(),
      s = n.length(),
      o = Math.floor(s / a)
    if (((e.distance = 0), (t.distance = s), 0 === o)) return [e, t]
    const r = [e]
    for (let t = 1; t <= o; t++) {
      let n = i.clone().multiplyScalar(a * t)
      if (t === o) {
        const e = (s - a * (t - 1)) / 2
        n = i.clone().multiplyScalar(s - e)
      }
      const l = {
        A: e.A.clone().add(n),
        B: e.B.clone().add(n),
        O: e.O.clone().add(n),
        Origin: e.Origin.clone().add(n),
        Points: e.Points.map((e) => e.clone().add(n)),
        normalO: e.normalO,
        normalA: e.normalA,
        normalA2: e.normalA2,
        normalB: e.normalB,
        normalB2: e.normalB2,
        PointNormals: e.PointNormals,
        secType: e.secType,
        distance: n.length()
      }
      r.push(l)
    }
    return r.push(t), r
  }

  calculateSectionPointY(e, t) {
    let a
    t.dot(this.unitAxes.y), (a = Qt.CircularArch)
    const { position: n, leftWidth: i, height: s } = e,
      o = t.clone().normalize(),
      r = new THREE.Vector3()
    o.equals(this.unitAxes.y)
      ? r.set(0, 0, 1)
      : r.crossVectors(o, this.unitAxes.y),
      r.normalize()
    const l = new THREE.Vector3()
    l.crossVectors(r, o), l.normalize()
    let h = n.X - this.parameters.offset.x,
      c = n.Y - this.parameters.offset.y,
      d = n.Z - this.parameters.offset.z
    const p = new THREE.Vector3(h, c, d),
      u = new THREE.Vector3(0, -1, 0),
      m = new THREE.Matrix4()
    m.set(o.x, l.x, r.x, h, o.y, l.y, r.y, c, o.z, l.z, r.z, d, 0, 0, 0, 1)
    const g = new THREE.Vector3(0, 0, 0),
      f = new THREE.Vector3(0, -s, 0),
      y = new THREE.Vector3(0, -s, -i)
    let E = new THREE.Vector3(0, 0, -1).add(y),
      v = new THREE.Vector3(0, -1, 0).add(y)
    const b = new THREE.Vector3(0, -s, i)
    let w = new THREE.Vector3(0, 0, 1).add(b),
      C = new THREE.Vector3(0, -1, 0).add(b)
    const R = [],
      S = [],
      x = Math.PI / this.parameters.radialSegments
    let T = this.parameters.radialSegments + 1
    a === Qt.Circle && (T = 2 * this.parameters.radialSegments)
    for (let e = 0; e < T; e++) {
      let t = i * Math.cos(e * x),
        a = i * Math.sin(e * x),
        n = 0,
        s = new THREE.Vector3(n, a, t)
      R.push(s), S.push(s.clone().sub(g).add(s))
    }
    return (
      f.applyMatrix4(m),
        y.applyMatrix4(m),
        b.applyMatrix4(m),
        E.applyMatrix4(m),
        v.applyMatrix4(m),
        w.applyMatrix4(m),
        C.applyMatrix4(m),
        u.applyMatrix4(m),
        R.forEach((e) => e.applyMatrix4(m)),
        S.forEach((e, t) => e.applyMatrix4(m).sub(R[t]).normalize()),
        E.sub(y).normalize(),
        v.sub(y).normalize(),
        w.sub(b).normalize(),
        C.sub(b).normalize(),
        u.sub(p).normalize(),
        {
          A: y,
          B: b,
          O: f,
          Origin: p,
          Points: R,
          normalO: u,
          normalA: E,
          normalA2: v,
          normalB: w,
          normalB2: C,
          PointNormals: S,
          secType: a,
          radius: i
        }
    )
  }

  calculateSectionPoint(e, t) {
    let a
    t.dot(this.unitAxes.z), (a = Qt.CircularArch)
    const { position: n, leftWidth: i, height: s } = e,
      o = t.clone().normalize(),
      r = new THREE.Vector3()
    o.equals(this.unitAxes.z)
      ? r.set(1, 0, 0)
      : r.crossVectors(o, this.unitAxes.z),
      r.normalize()
    const l = new THREE.Vector3()
    l.crossVectors(r, o), l.normalize()
    let h = n.X - this.parameters.offset.x,
      c = n.Y - this.parameters.offset.y,
      d = n.Z - this.parameters.offset.z
    const p = new THREE.Vector3(h, c, d),
      u = new THREE.Vector3(0, -1, 0),
      m = new THREE.Matrix4()
    m.set(r.x, o.x, l.x, h, r.y, o.y, l.y, c, r.z, o.z, l.z, d, 0, 0, 0, 1)
    const g = new THREE.Vector3(0, 0, 0),
      f = new THREE.Vector3(0, 0, -s),
      y = new THREE.Vector3(-i, 0, -s)
    let E = new THREE.Vector3(-1, 0, 0).add(y),
      v = new THREE.Vector3(0, 0, -1).add(y)
    const b = new THREE.Vector3(i, 0, -s)
    let w = new THREE.Vector3(1, 0, 0).add(b),
      C = new THREE.Vector3(0, 0, -1).add(b)
    const R = [],
      S = [],
      x = Math.PI / this.parameters.radialSegments
    let T = this.parameters.radialSegments + 1
    a === Qt.Circle && (T = 2 * this.parameters.radialSegments)
    for (let e = 0; e < T; e++) {
      let t = i * Math.cos(e * x),
        a = i * Math.sin(e * x),
        n = 0,
        s = new THREE.Vector3(t, n, a)
      R.push(s), S.push(s.clone().sub(g).add(s))
    }
    return (
      f.applyMatrix4(m),
        y.applyMatrix4(m),
        b.applyMatrix4(m),
        E.applyMatrix4(m),
        v.applyMatrix4(m),
        w.applyMatrix4(m),
        C.applyMatrix4(m),
        u.applyMatrix4(m),
        R.forEach((e) => e.applyMatrix4(m)),
        S.forEach((e, t) => e.applyMatrix4(m).sub(R[t]).normalize()),
        E.sub(y).normalize(),
        v.sub(y).normalize(),
        w.sub(b).normalize(),
        C.sub(b).normalize(),
        u.sub(p).normalize(),
        {
          A: y,
          B: b,
          O: f,
          Origin: p,
          Points: R,
          normalO: u,
          normalA: E,
          normalA2: v,
          normalB: w,
          normalB2: C,
          PointNormals: S,
          secType: a,
          radius: i
        }
    )
  }

  interpolateSections(e, t, a, n, i = !1) {
    let s = a.dot(n)
    if (((e.distance = 0), this.isParallelByAngle(s))) {
      return (
        (e.distance = 0),
          (t.distance = t.Origin.clone().sub(e.Origin).length()),
          [e, t]
      )
    }
    (a = a.clone().normalize()), (n = n.clone().normalize())
    const o = a.clone().cross(n).normalize()
    let r = o.clone().cross(a).normalize(),
      l = o.clone().cross(n).normalize()
    const h = e.O.clone(),
      c = t.O.clone(),
      d = Math.acos(s.toFixed(3)),
      p = r.clone().dot(l)
    if (this.isParallelByAngle(p)) {
      return (
        (e.distance = 0),
          (t.distance = t.Origin.clone().sub(e.Origin).length()),
          [e, t]
      )
    }
    const u = this.getLineCrossPoint(h, c, r, l)
    if (i) return this.interpolateCircleStraightSections(e, t, r, l, d, u, o)
    const m = [e],
      g = this.parameters.segments,
      f = d / g
    for (let t = 1; t < g; t++) {
      const a = f * t,
        n = new THREE.Matrix4().makeTranslationAndRotationAxis(u, o, a),
        i = this.rotateNormal(e.O, e.normalO, n),
        s = this.rotateNormal(e.Origin, e.normalO, n),
        r = this.rotateNormal(e.A, e.normalA, n),
        l = this.rotateNormal(e.A, e.normalA2, n),
        h = this.rotateNormal(e.B, e.normalB, n),
        c = this.rotateNormal(e.B, e.normalB2, n),
        d = [],
        p = []
      for (let t = 0; t < e.Points.length; t++) {
        const a = this.rotateNormal(e.Points[t], e.PointNormals[t], n)
        d.push(a.point), p.push(a.normal)
      }
      m.push({
        A: r.point,
        B: h.point,
        O: i.point,
        Origin: s.point,
        normalO: i.normal,
        normalA: r.normal,
        normalA2: l.normal,
        normalB: h.normal,
        normalB2: c.normal,
        Points: d,
        PointNormals: p,
        distance:
          s.point
            .clone()
            .sub(m[m.length - 1].Origin)
            .length() + m[m.length - 1].distance
      })
    }
    return (
      (t.distance =
        t.Origin.clone()
          .sub(m[m.length - 1].Origin)
          .length() + m[m.length - 1].distance),
        m.push(t),
        m
    )
  }

  interpolateCircleStraightSections(e, t, a, n, i, s, o) {
    n.negate()
    const r = s.distanceTo(e.origin),
      l = s.distanceTo(e.O),
      h = this.parameters.segments,
      c = (l - r) / h
    let d,
      p = Math.PI / this.parameters.radialSegments
    const u = []
    for (let e = 0; e < t.Points.length; e++) {
      (d = t.radius * Math.sin(p * e)), u.push((l - (r - d)) / h)
    }
    const m = t.Points[0],
      g = t.Points[t.Points.length - 1],
      f = t.PointNormals[0],
      y = t.PointNormals[t.PointNormals.length - 1],
      E = [
        {
          Points: t.Points,
          PointNormals: t.PointNormals,
          SidePoints1: [m, m],
          SidePointNormals1: [f, f],
          SidePoints2: [g, g],
          SidePointNormals2: [y, y]
        }
      ]
    p = i / h
    for (let e = 1; e <= h; e++) {
      const a = -p * e,
        i = new THREE.Matrix4().makeTranslationAndRotationAxis(s, o, a),
        r = [],
        l = []
      let h, d
      for (let e = 0; e < t.Points.length; e++) {
        (h = t.Points[e].clone().add(n.clone().multiplyScalar(u[e]))),
          (d = this.rotateNormal(h, t.PointNormals[e], i)),
          r.push(d.point),
          l.push(d.normal)
      }
      const v = [],
        b = [],
        w = [],
        C = [];
      (d = this.rotateNormal(m, f, i)),
        v.push(d.point),
        w.push(d.normal),
        (h = m.clone().add(n.clone().multiplyScalar(c))),
        (d = this.rotateNormal(h, f, i)),
        v.push(d.point),
        w.push(d.normal),
        (d = this.rotateNormal(g, y, i)),
        b.push(d.point),
        C.push(d.normal),
        (h = g.clone().add(n.clone().multiplyScalar(c))),
        (d = this.rotateNormal(h, y, i)),
        b.push(d.point),
        C.push(d.normal),
        E.push({
          Points: r,
          PointNormals: l,
          SidePoints1: v,
          SidePointNormals1: w,
          SidePoints2: b,
          SidePointNormals2: C
        })
    }
    return E
  }

  getLineCrossPoint(e, t, a, n) {
    const i = a.clone().dot(n),
      s = t.clone().sub(e),
      o = s.clone().cross(a).length() / Math.sqrt(1 / Math.pow(i, 2) - 1),
      r = e.clone().add(a.clone().multiplyScalar(s.clone().dot(a))),
      l = r.clone().add(a.clone().multiplyScalar(o)),
      h = r.clone().sub(a.clone().multiplyScalar(o))
    return t.clone().sub(l).cross(n).lengthSq() <
    t.clone().sub(h).cross(n).lengthSq()
      ? l
      : h
  }

  rotateNormal(e, t, a) {
    const n = e.clone().applyMatrix4(a)
    return {
      point: n,
      normal: e.clone().add(t).applyMatrix4(a).sub(n).normalize()
    }
  }

  isParallelByAngle(e) {
    return Math.abs(Math.abs(e) - 1) <= Qt.EPS
  }

  isParallelByVector(e, t) {
    return (
      (e = e.clone().normalize()),
        (t = t.clone().normalize()),
        this.isParallelByAngle(e.dot(t))
    )
  }
}

class aa {
  constructor({ engine: e, id: t, name: a, type: n }) {
    (this.id = t),
      (this.layer = ''),
      (this.name = a),
      (this.transform = null),
      (this.type = n),
      (this._engine = e),
      (this._components = new Map()),
      (this._listeners = {})
  }

  get engine() {
    return this._engine
  }

  addComponent(e, t) {
    (t._engine = this._engine),
      (t._entity = this),
      this._components.set(e, t)
  }

  getComponent(e) {
    return this._components.get(e)
  }

  getAllComponents() {
    return this._components.values()
  }

  hasComponent(e) {
    return this._components.has(e)
  }

  removeComponent(e) {
    this._components.delete(e)
  }

  removeAllComponent() {
    this._components.clear()
  }

  destroy() {
  }
}

function qt(e) {
  var t = (function(e, t) {
    if ('object' != Zt(e) || !e) return e
    var a = e[Symbol.toPrimitive]
    if (void 0 !== a) {
      var n = a.call(e, t || 'default')
      if ('object' != Zt(n)) return n
      throw new TypeError('@@toPrimitive must return a primitive value.')
    }
    return ('string' === t ? String : Number)(e)
  })(e, 'string')
  return 'symbol' == Zt(t) ? t : t + ''
}

function Kt(e, t, a) {
  return (
    (t = qt(t)) in e
      ? Object.defineProperty(e, t, {
        value: a,
        enumerable: !0,
        configurable: !0,
        writable: !0
      })
      : (e[t] = a),
      e
  )
}

function Zt(e) {
  return (
    (Zt =
      'function' == typeof Symbol && 'symbol' == typeof Symbol.iterator
        ? function(e) {
          return typeof e
        }
        : function(e) {
          return e &&
          'function' == typeof Symbol &&
          e.constructor === Symbol &&
          e !== Symbol.prototype
            ? 'symbol'
            : typeof e
        }),
      Zt(e)
  )
}

(THREE.Box3.prototype.isInfinity = function() {
  return y.some((e) => e.equals(this))
}),
  (THREE.Matrix4.prototype.checkElement = function(e = 1e-6) {
    this.elements.forEach((t, a) => {
      Math.abs(t) < e && (this.elements[a] = 0)
    })
  }),
  (THREE.Matrix4.prototype.multiplyVec3 = function(e, t = !1) {
    return t ? e.applyMatrix4(this) : e.clone().applyMatrix4(this)
  }),
  (THREE.Matrix4.prototype.makeTranslationAndRotationAxis = function(
    e,
    t,
    a
  ) {
    let n = new THREE.Vector3(1, 0, 0),
      i = new THREE.Vector3(0, 1, 0),
      s = new THREE.Vector3(0, 0, 1)
    for (let t of Object.keys(e)) Math.abs(e[t]) < 1e-6 && (e[t] = 0)
    let o,
      r = new THREE.Matrix4().makeTranslation(-e.x, -e.y, -e.z);
    (o =
      0 === t.angleTo(n)
        ? new THREE.Matrix4().makeRotationX(a)
        : t.angleTo(n) === Math.PI
          ? new THREE.Matrix4().makeRotationX(-a)
          : 0 === t.angleTo(i)
            ? new THREE.Matrix4().makeRotationY(a)
            : t.angleTo(i) === Math.PI
              ? new THREE.Matrix4().makeRotationY(-a)
              : 0 === t.angleTo(s)
                ? new THREE.Matrix4().makeRotationZ(a)
                : t.angleTo(s) === Math.PI
                  ? new THREE.Matrix4().makeRotationZ(-a)
                  : new THREE.Matrix4().makeRotationAxis(t.clone().normalize(), a)),
      o.checkElement();
    let l = new THREE.Matrix4().makeTranslation(e.x, e.y, e.z);
    return this.multiplyMatrices(l, o).multiply(r), this;
  });

</script>
