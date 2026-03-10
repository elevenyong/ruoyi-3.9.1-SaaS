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
import { BoxGeometry, Mesh, Vector3 } from 'three'
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
    this.init()
    this.handleRoadPoint()
  },
  methods: {

    getMiddlePoint(p0, p1) {
      return new THREE.Vector3(p0.x + ((p1.x - p0.x) / 2.), p0.y + ((p1.y - p0.y) / 2.), p0.z + ((p1.z - p0.z) / 2.))
    },

    // 获取重复节点
    getDuplicates(arr) {
      const counts = {}
      const duplicates = {}

      // 统计每个坐标对象出现的次数
      arr.forEach(item => {
        const key = `${item.x},${item.y},${item.z}` // 将坐标转为唯一的字符串
        counts[key] = (counts[key] || 0) + 1
      })

      // 筛选出出现次数大于1的坐标
      for (const [key, value] of Object.entries(counts)) {
        if (value > 1) {
          const [x, y, z] = key.split(',').map(Number) // 将字符串解析回坐标
          duplicates[key] = { x, y, z, count: value } // 存储坐标和出现次数
        }
      }

      return duplicates
    },

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

    },

    // 处理巷道点位
    handleRoadPoint() {
      // 定义点位
      let lines = [
        [
          new THREE.Vector3(5, 0, -1),
          new THREE.Vector3(0, 0, 0),
          new THREE.Vector3(0, 0, 10),
          new THREE.Vector3(5, 0, 20),
          new THREE.Vector3(0, 0, 30),
          new THREE.Vector3(5, 0, 40),
          new THREE.Vector3(0, 0, 50),
          new THREE.Vector3(5, 0, 60),
          new THREE.Vector3(10, 0, 70)
        ],
        [
          new THREE.Vector3(-30, -15, 20),
          new THREE.Vector3(-20, -10, 15),
          new THREE.Vector3(-10, -5, 20),
          new THREE.Vector3(0, 0, 10),
          new THREE.Vector3(10, 0, 10)
        ]/* ,
        [
          new THREE.Vector3(-30, 0, 20 + 20),
          new THREE.Vector3(-20, 0, 15 + 20),
          new THREE.Vector3(-6, 0, 20 + 20),
          new THREE.Vector3(0, 0, 10 + 20)
        ],
        [
          new THREE.Vector3(-30, 0, 20 + 40),
          new THREE.Vector3(-20, 0, 15 + 40),
          new THREE.Vector3(-10, 0, 20 + 40),
          new THREE.Vector3(0, 0, 10 + 40),
          new THREE.Vector3(10, 0, 10 + 40)
        ],
        [
          new THREE.Vector3(-10, 0, 20 + 30),
          new THREE.Vector3(0, 0, 10 + 40),
        ] */
      ]

      // 寻找交叉点
      // 先判断并获取交叉节点
      let allPoints = lines.flat()
      let crossPoints = this.getDuplicates(allPoints)
      console.log('crossPoints', crossPoints)

      // 创建原始点
      let originBox = new BoxGeometry(1.5, 1.5, 1.5)
      let originPointMeshMat = new THREE.MeshLambertMaterial({ color: 0xffffff })
      originPointMeshMat.wireframe = false
      originPointMeshMat.side = THREE.DoubleSide
      originPointMeshMat.depthWrite = true
      originPointMeshMat.transparent = true
      originPointMeshMat.opacity = 0.3

      for (let j = 0; j < allPoints.length; j++) {
        let bottomBox = new THREE.Mesh(originBox, originPointMeshMat)
        bottomBox.position.set(allPoints[j].x, allPoints[j].y, allPoints[j].z)
        scene.add(bottomBox)
      }

      // 创建交叉点
      let crossBox = new BoxGeometry(1, 1, 1)
      let crossPointMeshMat = new THREE.MeshLambertMaterial({ color: 0x0000ff })
      crossPointMeshMat.wireframe = false
      crossPointMeshMat.side = THREE.DoubleSide
      crossPointMeshMat.depthWrite = true
      crossPointMeshMat.transparent = false
      crossPointMeshMat.opacity = 1

      for (let key in crossPoints) {
        let bottomBox = new THREE.Mesh(crossBox, crossPointMeshMat)
        bottomBox.position.set(crossPoints[key].x, crossPoints[key].y, crossPoints[key].z)
        scene.add(bottomBox)
      }

      // 重构巷道点位，分为简单段和交叉段。针对交叉点单独提取建模，与交叉点相连的巷道，取1/2并入简单段模型，1/2并入交叉段模型
      let crossRoad = {}
      let simpleRoad = {}
      for (let i = 0; i < lines.length; i++) {
        let points = lines[i]
        for (let j = 0; j < points.length; j++) {
          if (points.length <= 1) {
            // 点位少于1个
            continue
          }

          // 初始化简单巷道段
          if (!simpleRoad[i]) {
            simpleRoad[i] = [[]]
          }

          let p = points[j]
          if (crossPoints[`${p.x},${p.y},${p.z}`]) {
            // 是交叉点（1. 维护交叉段；2. 维护简单段）
            if (j === 0) {
              // 在巷道起点
              let midP = this.getMiddlePoint(p, points[j + 1])
              let ang = this.calculateAngleWithXAxis(p, midP)
              crossRoad[`${p.x},${p.y},${p.z}`]
                ? crossRoad[`${p.x},${p.y},${p.z}`].push([midP, ang])
                : crossRoad[`${p.x},${p.y},${p.z}`] = [[midP, ang]]
              simpleRoad[i][simpleRoad[i].length - 1].push(midP)
            } else if (j === points.length - 1) {
              // 巷道终点
              let midP = this.getMiddlePoint(points[j - 1], p)
              let ang = this.calculateAngleWithXAxis(p, midP)
              crossRoad[`${p.x},${p.y},${p.z}`] ? crossRoad[`${p.x},${p.y},${p.z}`].push([midP, ang]) : crossRoad[`${p.x},${p.y},${p.z}`] = [[midP, ang]]
              simpleRoad[i][simpleRoad[i].length - 1].push(midP)
            } else {
              // 巷道中间点
              let midP0 = this.getMiddlePoint(points[j - 1], p)
              let ang0 = this.calculateAngleWithXAxis(p, midP0)
              let midP1 = this.getMiddlePoint(p, points[j + 1])
              let ang1 = this.calculateAngleWithXAxis(p, midP1)
              crossRoad[`${p.x},${p.y},${p.z}`] ? crossRoad[`${p.x},${p.y},${p.z}`].push([midP0, ang0]) : crossRoad[`${p.x},${p.y},${p.z}`] = [[midP0, ang0]]
              crossRoad[`${p.x},${p.y},${p.z}`] ? crossRoad[`${p.x},${p.y},${p.z}`].push([midP1, ang1]) : crossRoad[`${p.x},${p.y},${p.z}`] = [[midP1, ang1]]
              // 结束上一简单段
              simpleRoad[i][simpleRoad[i].length - 1].push(this.getMiddlePoint(points[j - 1], p))
              // 新增下一简单段
              // 如果两个交叉点相邻，简单段中会出现重复点，构建时须剔除
              simpleRoad[i].push([this.getMiddlePoint(p, points[j + 1])])
            }
          } else {
            // 不是交叉点
            // 在最新一段巷道中增加当前点
            simpleRoad[i][simpleRoad[i].length - 1].push(p)
          }
        }
      }

      console.log('simpleRoad', simpleRoad)
      console.log('crossRoad', crossRoad)

      let simpleRoadMeshMat = new THREE.MeshLambertMaterial({ color: 0xffffff })
      simpleRoadMeshMat.wireframe = false
      simpleRoadMeshMat.side = THREE.DoubleSide
      simpleRoadMeshMat.depthWrite = true
      simpleRoadMeshMat.transparent = false
      simpleRoadMeshMat.opacity = 1

      let virRoadMeshMat = new THREE.MeshLambertMaterial({ color: 0xff0000 })
      virRoadMeshMat.wireframe = false
      virRoadMeshMat.side = THREE.DoubleSide
      virRoadMeshMat.depthWrite = true
      virRoadMeshMat.transparent = false
      virRoadMeshMat.opacity = 1

      let box = new BoxGeometry(1, 1, 1)
      for (let simpleRoadKey in simpleRoad) {
        let v = simpleRoad[simpleRoadKey]
        for (let i = 0; i < v.length; i++) {
          let ps = v[i]
          for (let j = 0; j < ps.length; j++) {
            let bottomBox = new THREE.Mesh(box, cutBoxMeshMat)
            bottomBox.position.set(ps[j].x, ps[j].y, ps[j].z)
            scene.add(bottomBox)
          }
        }
      }

      let simpleLineMaterial = new THREE.LineBasicMaterial({ color: 0xffffff })
      for (let simpleRoadKey in simpleRoad) {
        let v = simpleRoad[simpleRoadKey]
        for (let i = 0; i < v.length; i++) {
          let ps = v[i]
          let cutFaceGeometry = new THREE.BufferGeometry().setFromPoints(ps)
          let cuteFaceLineObj = new THREE.Line(cutFaceGeometry, simpleLineMaterial)
          scene.add(cuteFaceLineObj)

          // 创建简单巷道
          //this.createSimpleRoad(ps)
        }
      }

      for (let crossRoadKey in crossRoad) {
        let cRP = crossRoad[crossRoadKey]
        // 按照中心线角度排序
        cRP.sort((a, b) => {
          return a[1] - b[1]
        })
        for (let i = 0; i < cRP.length; i++) {
          let bottomBox = new THREE.Mesh(box, virRoadMeshMat)
          bottomBox.position.set(cRP[i][0].x, cRP[i][0].y, cRP[i][0].z)
          scene.add(bottomBox)
        }
      }
      this.createCrossRoad(crossRoad)

      let crossLineMaterial = new THREE.LineBasicMaterial({ color: 0xff0000 })
      for (let crossRoadKey in crossRoad) {
        let cRP = crossRoad[crossRoadKey]
        for (let i = 0; i < cRP.length; i++) {
          let split = crossRoadKey.split(',')
          let cro = new Vector3(Number(split[0]), Number(split[1]), Number(split[2]))
          let cutFaceGeometry = new THREE.BufferGeometry().setFromPoints([cro, cRP[i]])
          let cuteFaceLineObj = new THREE.Line(cutFaceGeometry, crossLineMaterial)
          scene.add(cuteFaceLineObj)
        }
      }

    },

    sortByAng(a, b) {
      console.log('a', a)
      console.log('b', b)
      return a[1] - b[1]
    },

    // 创建巷道Mesh
    createCrossRoad(crossRoadPoints) {
      console.log('crossRoadPoints', crossRoadPoints)
      let box = new BoxGeometry(0.5, 0.5, 0.5)
      let virRoadMeshMat = new THREE.MeshLambertMaterial({ color: 0xf0f0f0 })
      virRoadMeshMat.wireframe = false
      virRoadMeshMat.side = THREE.DoubleSide
      virRoadMeshMat.depthWrite = true
      virRoadMeshMat.transparent = false
      virRoadMeshMat.opacity = 1

      let projPoiMeshMat = new THREE.MeshLambertMaterial({ color: 0xfff0f0 })

      let lcrossLineMaterial = new THREE.LineBasicMaterial({ color: 0xff0000 })
      let rcrossLineMaterial = new THREE.LineBasicMaterial({ color: 0xff00ff })
      for (let keyPoint in crossRoadPoints) {
        let split = keyPoint.split(',')
        // 中心点
        let centerP = new THREE.Vector3(Number(split[0]), Number(split[1]), Number(split[2]))
        // 中心点顶点
        let centerTop = new THREE.Vector3(Number(split[0]), Number(split[1]) + 4.5, Number(split[2]))
        // 周边点集[点，角度]
        let crossPS = crossRoadPoints[keyPoint]
        for (let i = 0; i < crossPS.length; i++) {
          // 遍历，循环创建，1.求边线 2.求边线交点/两最近点的中点 3. 构建底面，侧面，顶面
          if (i === crossPS.length - 1) {
            // 遍历到最后一点
            // 两侧点
            let offsetPoints0 = this.offsetPoints(crossPS[i][0], centerP, 2.5)
            let offsetPoints1 = this.offsetPoints(crossPS[0][0], centerP, 2.5)

            // 画出侧线
            /* let lg = new THREE.BufferGeometry().setFromPoints([offsetPoints0.leftOffset.A, offsetPoints0.leftOffset.B])
            let lo = new THREE.Line(lg, lcrossLineMaterial)
            scene.add(lo)
            let rg = new THREE.BufferGeometry().setFromPoints([offsetPoints0.rightOffset.A, offsetPoints0.rightOffset.B])
            let ro = new THREE.Line(rg, rcrossLineMaterial)
            scene.add(ro) */

            // 计算相交/最近点
            let closePt0 = this.closestPointsOnLines(offsetPoints0.leftOffset.A, offsetPoints0.leftOffset.B, offsetPoints1.rightOffset.A, offsetPoints1.rightOffset.B)

            // 画出相交/最近点
            let l1bottomBox = new THREE.Mesh(box, virRoadMeshMat)
            l1bottomBox.position.set(closePt0.x, closePt0.y, closePt0.z)
            scene.add(l1bottomBox)

            // 创建底面
            this.createBottomLine([crossPS[i][0], offsetPoints0.leftOffset.A, closePt0, centerP], lcrossLineMaterial)
            this.createBottomLine([crossPS[0][0], offsetPoints1.rightOffset.A, closePt0, centerP], rcrossLineMaterial)

            // 创建侧面
            this.createSideLine([offsetPoints0.leftOffset.A, closePt0], 2, lcrossLineMaterial)
            this.createSideLine([offsetPoints1.rightOffset.A, closePt0], 2, lcrossLineMaterial)

            // 创建顶面
            let cClone00 = crossPS[i][0].clone()
            cClone00.y += 4.5
            let cClone01 = crossPS[i][0].clone()
            cClone01.y += 2
            let lOffsetP = offsetPoints0.leftOffset.A.clone()
            lOffsetP.y += 2
            this.createTopLine([cClone01, cClone00, lOffsetP], lcrossLineMaterial, [crossPS[i][0].clone(), closePt0.clone(), centerP.clone(), centerTop.clone()])

            let cClone10 = crossPS[0][0].clone()
            cClone10.y += 4.5
            let cClone11 = crossPS[0][0].clone()
            cClone11.y += 2
            let rOffsetP = offsetPoints1.rightOffset.A.clone()
            rOffsetP.y += 2
            this.createTopLine([cClone11, cClone10, rOffsetP], lcrossLineMaterial, [crossPS[0][0].clone(), closePt0.clone(), centerP.clone(), centerTop.clone()])

            // 交叉点中心初顶面线
            let nPt = closePt0.clone()
            nPt.z += 2
            //this.calcCenterTopPoints(centerTop.clone(), nPt)
          } else {
            // 不是最后一点
            // 两侧点
            let offsetPoints0 = this.offsetPoints(crossPS[i][0], centerP, 2.5)
            let offsetPoints1 = this.offsetPoints(crossPS[i + 1][0], centerP, 2.5)

            // 画出侧线
            /* let lg = new THREE.BufferGeometry().setFromPoints([offsetPoints0.leftOffset.A, offsetPoints0.leftOffset.B])
            let lo = new THREE.Line(lg, lcrossLineMaterial)
            scene.add(lo)
            let rg = new THREE.BufferGeometry().setFromPoints([offsetPoints0.rightOffset.A, offsetPoints0.rightOffset.B])
            let ro = new THREE.Line(rg, rcrossLineMaterial)
            scene.add(ro) */

            // 计算最近点
            let closePt0 = this.closestPointsOnLines(offsetPoints0.leftOffset.A, offsetPoints0.leftOffset.B, offsetPoints1.rightOffset.A, offsetPoints1.rightOffset.B)

            // 画出最近点
            let l1bottomBox = new THREE.Mesh(box, virRoadMeshMat)
            l1bottomBox.position.set(closePt0.x, closePt0.y, closePt0.z)
            scene.add(l1bottomBox)

            // 创建底面
            this.createBottomLine([crossPS[i][0], offsetPoints0.leftOffset.A, closePt0, centerP], lcrossLineMaterial)
            this.createBottomLine([crossPS[i + 1][0], offsetPoints1.rightOffset.A, closePt0, centerP], rcrossLineMaterial)

            // 创建侧面
            this.createSideLine([offsetPoints0.leftOffset.A, closePt0], 2, lcrossLineMaterial)
            this.createSideLine([offsetPoints1.rightOffset.A, closePt0], 2, lcrossLineMaterial)

            // 创建顶面
            let cClone00 = crossPS[i][0].clone()
            cClone00.y += 4.5
            let cClone01 = crossPS[i][0].clone()
            cClone01.y += 2
            let lOffsetP = offsetPoints0.leftOffset.A.clone()
            lOffsetP.y += 2
            this.createTopLine([cClone01, cClone00, lOffsetP], lcrossLineMaterial, [crossPS[i][0].clone(), closePt0.clone(), centerP.clone(), centerTop.clone()])

            let cClone10 = crossPS[i + 1][0].clone()
            cClone10.y += 4.5
            let cClone11 = crossPS[i + 1][0].clone()
            cClone11.y += 2
            let rOffsetP = offsetPoints1.rightOffset.A.clone()
            rOffsetP.y += 2
            this.createTopLine([cClone11, cClone10, rOffsetP], lcrossLineMaterial, [crossPS[i + 1][0].clone(), closePt0.clone(), centerP.clone(), centerTop.clone()])

            // 交叉点中心初顶面线
            let nPt = closePt0.clone()
            nPt.y += 2
            this.calcCenterTopPoints(centerTop, nPt)

          }
          if (i === 0) {
            break
          }
        }
      }
    },

    // 创建侧面
    createBottomLine(points, material) {
      let side0LGeometry = new THREE.BufferGeometry().setFromPoints([points[0], points[1], points[2], points[3], points[0]])
      let side0Line = new THREE.Line(side0LGeometry, material)
      scene.add(side0Line)
    },

    // 创建侧面
    createSideLine(points, height, material) {
      let aClone = points[0].clone()
      aClone.y += height
      let closePClone = points[1].clone()
      closePClone.y += height
      let side0LGeometry = new THREE.BufferGeometry().setFromPoints([aClone, points[0], points[1], closePClone, aClone])
      let side0Line = new THREE.Line(side0LGeometry, material)
      scene.add(side0Line)
    },

    // 创建顶面
    createTopLine(points, material, points1) {
      // 定义圆心、起点和终点
      const center = points[0].clone()
      const start = points[1].clone()
      const end = points[2].clone()

      // 计算半径
      const radius = center.distanceTo(start)

      // 创建局部坐标系的单位向量
      const xDir = new THREE.Vector3().subVectors(start, center).normalize() // X 轴：center -> start
      const zDir = new THREE.Vector3().crossVectors(xDir, new THREE.Vector3().subVectors(end, center)).normalize() // Z 轴为法向量
      const yDir = new THREE.Vector3().crossVectors(zDir, xDir).normalize() // Y 轴完成右手系

      // 计算四分之一圆的点
      const segments = 4 // 可以增加以提高曲线平滑度
      const curvePoints = []
      for (let i = 0; i <= segments; i++) {
        const angle = (Math.PI / 2) * i / segments // 四分之一圆的角度从 0 到 π/2
        const localPoint = new THREE.Vector3(
          Math.cos(angle) * radius,
          Math.sin(angle) * radius,
          0
        )
        // 将局部点转换到全局坐标系
        const globalPoint = new THREE.Vector3()
          .addScaledVector(xDir, localPoint.x)
          .addScaledVector(yDir, localPoint.y)
          .addScaledVector(zDir, localPoint.z)
          .add(center) // 平移到圆心位置
        curvePoints.push(globalPoint)
      }

      // 投影
      /* let projPoiMeshMat = new THREE.MeshLambertMaterial({ color: 0xfff0f0 })
      let box = new BoxGeometry(0.2, 0.2, 0.2)
      for (let j = 0; j < curvePoints.length; j++) {
        let l1bottomBox = new THREE.Mesh(box, projPoiMeshMat)
        l1bottomBox.position.set(curvePoints[j].x, curvePoints[j].y, curvePoints[j].z)
        scene.add(l1bottomBox)

        let projPoi = this.clacProjectPoint(curvePoints[j].clone(), new THREE.Vector3().subVectors(points1[2], points1[0]), points1[1], points1[2], points1[3])
        let projBox = new THREE.Mesh(box, projPoiMeshMat)
        projBox.position.set(projPoi.x, projPoi.y, projPoi.z)
        scene.add(projBox)
      } */

      // 创建几何体和材质，并将点转换为线
      /* const geometry = new THREE.BufferGeometry().setFromPoints(curvePoints);
      const arc = new THREE.Line(geometry, material);
      scene.add(arc); */

      return curvePoints

      /* // 计算半径
      const radius = center.distanceTo(start)

      // 计算起点和终点相对于圆心的角度
      let startAngle = Math.atan2(start.y - center.y, start.x - center.x)
      let endAngle = Math.atan2(end.y - center.y, end.x - center.x)
      console.log("startAngle",startAngle,"endAngle",endAngle)
      let trans = 0
      if (startAngle < endAngle) {
        trans = startAngle
        startAngle = endAngle
        endAngle = trans
      }

      // 创建半圆的曲线（顺时针方向）
      const halfCircleCurve = new THREE.EllipseCurve(
        0, 0,           // 圆心位置
        radius, radius,                // 半径
        startAngle, endAngle,          // 起点和终点角度
        false                          // 顺时针绘制
      )

      // 生成曲线的点
      const curvePoints = halfCircleCurve.getPoints(8) // 细分点数决定平滑度
      const geometry = new THREE.BufferGeometry().setFromPoints(curvePoints)

      const arc = new THREE.Line(geometry, material)

      arc.position.copy(center)


      // 计算旋转：确定旋转轴和旋转角度
      const v1 = new THREE.Vector3().subVectors(start, center);  // 中心到起点的向量
      const v2 = new THREE.Vector3().subVectors(end, center);    // 中心到终点的向量
      const normal = new THREE.Vector3().crossVectors(v1, v2).normalize();  // 法向量

      // 创建四元数表示旋转
      const quaternion = new THREE.Quaternion();
      quaternion.setFromUnitVectors(new THREE.Vector3(0, 0, 1), normal);  // 从 Z 轴旋转到法向量方向
      arc.applyQuaternion(quaternion);  // 应用旋转

      // 将半圆添加到场景中
      scene.add(arc) */

      /* let control = new THREE.Vector3(
        points[1].x,
        points[0].y,
        (points[0].z + points[1].z) / 2
      )

      let bzCurve = new THREE.QuadraticBezierCurve3(points[0], control, points[1])
      let bzPoints = bzCurve.getPoints(10)
      //let arc0Curve = new THREE.ArcCurve(0, 0, 2.5, Math.PI / 2., 0)
      //let arc1Curve = new THREE.ArcCurve(0, 0, 2.5, 0, Math.PI / 2.)
      let geometry = new THREE.BufferGeometry().setFromPoints(bzPoints)
      let line = new THREE.Line(geometry, material)
      scene.add(line) */
    },

    createSimpleRoad(path) {

      const roadPath = new THREE.CatmullRomCurve3(path)

      const roadShape = new THREE.Shape()
        .moveTo(-2, -3)
        .lineTo(-2, 0)
        .absarc(0, 0, 2, Math.PI, 0, true)
        .lineTo(2, -3).lineTo(-2, -3)

      const roadShapeHole = new THREE.Shape()
        .moveTo(-1.95, -2.95)
        .lineTo(-1.95, 0)
        .absarc(0, 0, 1.95, Math.PI, 0, true)
        .lineTo(1.95, -2.95).lineTo(-1.95, -2.95)

      roadShape.holes.push(roadShapeHole)
      //let startWP = new THREE.Vector3(this.workPoints[0]['x'], this.workPoints[0]['z'], this.workPoints[0]['y'])
      //let endWP = new THREE.Vector3(this.workPoints[this.workPoints.length - 1]['x'], this.workPoints[this.workPoints.length - 1]['z'], this.workPoints[this.workPoints.length - 1]['y'])
      //let roadLen = this.calDistance(startWP.x, startWP.y, startWP.z, endWP.x, endWP.y, endWP.z)
      const extrudeSettings = {
        extrudePath: roadPath,
        //bevelEnabled: true,
        //bevelSegments: 1,
        steps: 100
        //bevelSize: 0,
        //bevelThickness: 1
      }
      const roadGeometry = new THREE.ExtrudeGeometry(roadShape, extrudeSettings)

      const roadMesh = new THREE.Mesh(roadGeometry, originDrillRangeMatS)
      roadMesh.position.set(0, 0, 0)

      //let direction = new THREE.Vector3().subVectors(endWP, startWP).normalize()
      //let currentDir = new THREE.Vector3(0, 0, 1)
      //let quaternion = new THREE.Quaternion()
      //quaternion.setFromUnitVectors(currentDir, direction)
      //roadMesh.applyQuaternion(quaternion)
      //roadMesh.updateMatrix()
      //roadMesh.rotateY(0.01)

      scene.add(roadMesh)
    },

    calcCenterTopPoints(center, sideTop) {
      let box = new BoxGeometry(0.1, 0.1, 0.1)
      let virRoadMeshMat = new THREE.MeshLambertMaterial({ color: 0xfaf0fa })
      const arcMaterial = new THREE.LineBasicMaterial({ color: 0x0077ff });

      // 给定点 A 和点 B 的坐标，以及 A 点的切线方向
      const pointA = center.clone()
      const pointB = sideTop.clone()
      const tangentA = new THREE.Vector3().subVectors(new Vector3(pointB.x, pointA.y, pointB.z), pointA).normalize() // 切线方向向量

      let bottomBox00 = new THREE.Mesh(box, virRoadMeshMat)
      bottomBox00.position.set(pointA.x, pointA.y, pointA.z)
      scene.add(bottomBox00)

      let bottomBox01 = new THREE.Mesh(box, virRoadMeshMat)
      bottomBox01.position.set(pointB.x, pointB.y, pointB.z)
      scene.add(bottomBox01)


      let bottomBox0 = new THREE.Mesh(box, virRoadMeshMat)
      bottomBox0.position.set(pointB.x, pointA.y, pointB.z)
      scene.add(bottomBox0)

      // 计算向量 AB
      const AB = new THREE.Vector3().subVectors(pointB, pointA);
      const AB_length = AB.length();

// 计算平面的法向量（由 A 的切线和 AB 向量构成）
      const normal = new THREE.Vector3().crossVectors(tangentA, AB).normalize();

// 计算圆的半径和圆心
      const halfAB = AB_length / 2;
      const radius = halfAB / Math.sin(tangentA.angleTo(AB) / 2);

// 圆心在 AB 的中点基础上，沿法向量偏移
      const midPoint = new THREE.Vector3().addVectors(pointA, pointB).multiplyScalar(0.5);
      const circleCenter = new THREE.Vector3().addVectors(midPoint, normal.multiplyScalar(Math.sqrt(radius * radius - halfAB * halfAB)));

// 定义局部坐标系：X' 轴指向 A 的方向
      const xPrime = new THREE.Vector3().subVectors(pointA, circleCenter).normalize();
      const yPrime = new THREE.Vector3().crossVectors(normal, xPrime).normalize(); // Y' 轴

// 计算点 A 和点 B 在局部坐标系中的角度
      const localA = new THREE.Vector2(0, 0); // A 在局部坐标系中为原点
      const localB = new THREE.Vector2(
        new THREE.Vector3().subVectors(pointB, circleCenter).dot(xPrime),
        new THREE.Vector3().subVectors(pointB, circleCenter).dot(yPrime)
      );
      const startAngle = 0; // A 的角度为 0
      const endAngle = Math.atan2(localB.y, localB.x); // B 的角度

// 创建弧段几何（在局部坐标系中）
      const arcPoints = [];
      const segments = 64;
      for (let i = 0; i <= segments; i++) {
        const theta = startAngle + (endAngle - startAngle) * (i / segments);
        const x = radius * Math.cos(theta);
        const y = radius * Math.sin(theta);
        // 将局部坐标点转换为全局坐标系
        const globalPoint = new THREE.Vector3()
          .addScaledVector(xPrime, x)
          .addScaledVector(yPrime, y)
          .add(circleCenter);
        arcPoints.push(globalPoint);
      }

// 创建弧线
      const arcGeometry = new THREE.BufferGeometry().setFromPoints(arcPoints);
      const arc = new THREE.Line(arcGeometry, arcMaterial);

// 将弧线添加到场景
      scene.add(arc);

    },

    clacProjectPoint(p0, dir, p1, p2, p3) {
      // 计算平面的法向量
      const v1 = new THREE.Vector3().subVectors(p2, p1)
      const v2 = new THREE.Vector3().subVectors(p3, p1)
      const normal = new THREE.Vector3().crossVectors(v1, v2).normalize()

      // 定义需要投影的点和方向
      dir = dir.normalize() // 投影方向（单位向量）

      // 计算比例因子 t
      const numerator = normal.dot(new THREE.Vector3().subVectors(p1, p0))
      const denominator = normal.dot(dir)
      const t = numerator / denominator

      // 计算投影后的点
      const projectedPoint = new THREE.Vector3().copy(p0).addScaledVector(dir, t)

      return projectedPoint
    },

    // 计算角度
    calculateAngleWithXAxis(pointA, pointB) {
      const dx = pointB.x - pointA.x
      const dz = pointB.z - pointA.z

      // 计算与 X 轴的夹角（弧度）
      const theta = Math.atan2(dx, dz)

      // 转换为角度
      const angleInDegrees = theta * (180 / Math.PI)

      return angleInDegrees
    },

    // 计算两直线最近点
    closestPointsOnLines(A1, B1, A2, B2) {
      // 计算方向向量和起点差向量
      const D1 = new THREE.Vector3().subVectors(B1, A1)  // L1 的方向向量
      const D2 = new THREE.Vector3().subVectors(B2, A2)  // L2 的方向向量
      const R = new THREE.Vector3().subVectors(A2, A1)   // 两条线起点的向量差

      // 计算三重积 D1 · (D2 × R)
      const crossD2R = new THREE.Vector3().crossVectors(D2, R)
      const tripleProduct = D1.dot(crossD2R)

      // 如果三重积为 0，则两条直线共面
      const epsilon = 1e-6  // 容差
      if (Math.abs(tripleProduct) < epsilon) {
        // 检查是否平行，平行时没有交点
        const crossD1D2 = new THREE.Vector3().crossVectors(D1, D2)
        if (crossD1D2.length() < epsilon) {
          return { type: 'parallel', message: 'The lines are parallel and do not intersect.' }
        } else {
          // 共面且不平行，求解交点
          const D1D2 = D1.dot(D2)
          const D1D1 = D1.dot(D1)
          const D2D2 = D2.dot(D2)
          const RD1 = R.dot(D1)
          const RD2 = R.dot(D2)
          const denominator = D1D1 * D2D2 - D1D2 * D1D2

          const s = (D2D2 * RD1 - D1D2 * RD2) / denominator
          const intersection = new THREE.Vector3().addVectors(A1, D1.clone().multiplyScalar(s))

          return intersection
          /* return { pointOnL1: intersection, pointOnL2: intersection } */
        }
      } else {
        // 不共面，求两条直线的最近点
        const D1D1 = D1.dot(D1)
        const D2D2 = D2.dot(D2)
        const D1D2 = D1.dot(D2)
        const RD1 = R.dot(D1)
        const RD2 = R.dot(D2)

        const denominator = D1D1 * D2D2 - D1D2 * D1D2
        let s, t

        if (Math.abs(denominator) > epsilon) {  // 确保分母不为 0
          s = (D2D2 * RD1 - D1D2 * RD2) / denominator
          t = (D1D1 * RD2 - D1D2 * RD1) / denominator
        } else {  // 如果平行，选择 s = 0 和 t = RD2 / D2D2
          s = 0
          t = RD2 / D2D2
        }

        // 计算最近的点
        const closestPointL1 = new THREE.Vector3().addVectors(A1, D1.clone().multiplyScalar(s))
        const closestPointL2 = new THREE.Vector3().addVectors(A2, D2.clone().multiplyScalar(t))

        return new THREE.Vector3().copy(closestPointL1).lerp(closestPointL2, 0.5)
        /* return {
          pointOnL1: closestPointL1,
          pointOnL2: closestPointL2
        } */
      }
    },

    // 计算偏移点
    offsetPoints(A, B, distance) {
      // 计算方向向量 AB
      const direction = new THREE.Vector3(B.x - A.x, B.y - A.y, B.z - A.z)

      // 使用水平面法向量 (0, 1, 0) 计算垂直向量 P
      const normal = new THREE.Vector3(0, 1, 0)
      const offsetDirection = new THREE.Vector3().crossVectors(direction, normal)

      // 单位化偏移方向向量
      offsetDirection.normalize()

      // 计算左侧和右侧偏移的点
      const A_left = new THREE.Vector3(A.x, A.y, A.z).add(offsetDirection.clone().multiplyScalar(distance))
      const B_left = new THREE.Vector3(B.x, B.y, B.z).add(offsetDirection.clone().multiplyScalar(distance))
      const A_right = new THREE.Vector3(A.x, A.y, A.z).sub(offsetDirection.clone().multiplyScalar(distance))
      const B_right = new THREE.Vector3(B.x, B.y, B.z).sub(offsetDirection.clone().multiplyScalar(distance))

      return {
        leftOffset: { A: A_left, B: B_left },
        rightOffset: { A: A_right, B: B_right }
      }
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

class iD {
  constructor(e) {
    jO.add(this),
      this.options = e,
      this.update()
  }

  coordHash(e) {
    return `${e[0].toFixed(this.options.decimalPrecision)},${e[1].toFixed(this.options.decimalPrecision)},${e[2].toFixed(this.options.decimalPrecision)}`
  }

  update() {
    var e, t, i, n, s, o, a, l, h, c, d, u, p, f, m, g, v, y, x, w, b, _, S, M, C, A, E, T, P
    this.topo = {
      tubes: [],
      nodes: new Map
    },
      this.data = [],
      this.geometries = [],
    null === (e = this.entity) || void 0 === e || e.remove(),
      this.entity = new UP
    let O = 2 * (null !== (t = this.options.nodeRadius) && void 0 !== t ? t : .3)
      ,
      D = null !== (i = this.options.isMapMode) && void 0 !== i ? i : null === (n = Cy.app) || void 0 === n ? void 0 : n.isMapMode
    null !== (s = (x = this.options).outerMaterial) && void 0 !== s || (x.outerMaterial = new ce({
      color: 8257515,
      side: I
    }))
    let B = []
      , R = new Map
    null !== (o = (w = this.options).decimalPrecision) && void 0 !== o || (w.decimalPrecision = 6)
    const k = {}
    let L = this.options.data
    if ('nodeEqual' !== this.options.topoStrategy) {
      let e = []
      for (let t = 0; t < this.options.data.length; t++) {
        e.push(this.options.data[t].paths.map((e => Vv(e))))
      }
      let t = AG(e, Math.pow(.1, this.options.decimalPrecision))
        , i = []
      for (let e = 0; e < t.lines.length; e++) {
        let n = t.lines[e].map((e => e.toArray()))
          , s = L[t.indexs[e]]
        i.push(Object.assign(Object.assign({}, s), {
          paths: n
        }))
      }
      L = i
    }
    for (let e = 0; e < L.length; e++) {
      let t = L[e].paths
        , i = {
        paths: t,
        startId: this.coordHash(t[0]),
        endId: this.coordHash(t[t.length - 1]),
        dist: WW(t),
        clipPath: t,
        data: L[e]
      }
      B.push(i)
      let n = 0
      R.has(i.startId) && (n = R.get(i.startId)),
        R.set(i.startId, n + 1),
        n = 0,
      R.has(i.endId) && (n = R.get(i.endId)),
        R.set(i.endId, n + 1)
      let s = yv(i.startId).substring(0, 8)
        , r = yv(i.endId).substring(0, 8)
      this.topo.tubes.push({
        tubeIndex: e,
        data: L[e],
        startNodeId: s,
        endNodeId: r
      }),
        this.topo.nodes.has(s) ? this.topo.nodes.get(s).push(e) : this.topo.nodes.set(s, [e]),
        this.topo.nodes.has(r) ? this.topo.nodes.get(r).push(e) : this.topo.nodes.set(r, [e])
    }
    const z = {}
    for (let e = 0; e < B.length; e++) {
      let t = B[e]
      2 == t.paths.length && Tv(t.paths[0][0], t.paths[1][0], 1e-6) && Tv(t.paths[0][D ? 1 : 2], t.paths[1][D ? 1 : 2], 1e-6) && (t.isVerticalLine = !0,
        t.paths[0][0] += 1e-6)
    }
    this.inputData = L,
    null !== (a = (b = this.options).drawRules) && void 0 !== a || (b.drawRules = [15, 4, 25, 3, 35, 2]),
    null !== (l = (_ = this.options).maxNodeRadiusRatio) && void 0 !== l || (_.maxNodeRadiusRatio = .5),
    null !== (h = (S = this.options).verticalShape) && void 0 !== h || (S.verticalShape = {
      shape: 'circle',
      radius: this.options.nodeRadius,
      clipRadius: this.options.nodeRadius
    })
    const U = {}
    if (this.options.drawRules.length > 0) {
      for (let e = 0; e < B.length; e++) {
        let t = B[e]
        if (t.isVerticalLine) {
          continue
        }
        let i = t.startId
          , n = t.endId
          , s = R.get(i)
          , o = R.get(n)
          , a = {}
          , l = {}
        if (s >= 2 && (a.tubeIndex = e,
          a.angle = new r(t.paths[1][0], D ? t.paths[1][1] : t.paths[1][2]).sub(new r(t.paths[0][0], D ? t.paths[0][1] : t.paths[0][2])).angle(),
        null !== (c = U[M = t.startId]) && void 0 !== c || (U[M] = []),
          U[t.startId].push(a)),
        o >= 2) {
          l.tubeIndex = e
          let i = t.paths.length - 1
          l.angle = new r(t.paths[i - 1][0], D ? t.paths[i - 1][1] : t.paths[i - 1][2]).sub(new r(t.paths[i][0], D ? t.paths[i][1] : t.paths[i][2])).angle(),
          null !== (d = U[C = t.endId]) && void 0 !== d || (U[C] = []),
            U[t.endId].push(l)
        }
      }
      for (let e in U) {
        U[e].sort(((e, t) => e.angle - t.angle))
      }
    }
    for (let e = 0; e < B.length; e++) {
      let t = B[e]
        , i = t.startId
        , n = t.endId
        , s = R.get(i)
        , o = R.get(n)
        , a = t.disableMergeNode || t.data.disableMergeNode
      if (!0 === a || t.isVerticalLine) {
        let i
        if (t.isVerticalLine && (i = this.options.verticalShape,
        i.clipRadius > 0 && !a)) {
          if (s >= 2) {
            let e = []
            YW(t.paths, i.clipRadius / t.dist, {
              distance: t.dist,
              remainingPoints: e
            }),
              t.paths = e
          }
          if (o >= 2) {
            let e = []
            t.paths = YW(t.paths, 1 - i.clipRadius / t.dist, {
              remainingPoints: e
            })
          }
        }
        null !== (u = z[A = t.startId]) && void 0 !== u || (z[A] = []),
        z[t.startId].indexOf(e) < 0 && z[t.startId].push(e),
        null !== (p = z[E = t.endId]) && void 0 !== p || (z[E] = []),
        z[t.endId].indexOf(e) < 0 && z[t.endId].push(e)
        let n = qv(this, jO, 'm', HO).call(this, t.paths, e, O, !1, i);
        (null == n ? void 0 : n.geometry) && this.geometries.push(n.geometry)
        continue
      }
      let l = {}
        , h = {}
      if (s >= 2) {
        l.tubeIndex = e,
          l.angle = new r(t.paths[1][0], D ? t.paths[1][1] : t.paths[1][2]).sub(new r(t.paths[0][0], D ? t.paths[0][1] : t.paths[0][2])).angle()
        let n = O
        if (this.options.drawRules.length > 0) {
          let t = U[i]
            , s = t.findIndex((t => t.tubeIndex == e))
          if (s >= 0 && (l.preConnect = t[s - 1 < 0 ? t.length - 1 : s - 1],
            l.nextConnect = t[s + 1 > t.length - 1 ? 0 : s + 1]),
            l.nextConnect) {
            let e = l.nextConnect.angle
            e < l.angle && (e += 2 * Math.PI)
            let t = e - l.angle
            t = 180 * t / Math.PI
            for (let e = 0; e < this.options.drawRules.length; e += 2) {
              if (t < this.options.drawRules[e]) {
                let i = this.options.drawRules[e + 1] * O
                if (e >= 2) {
                  let n = this.options.drawRules[e - 2]
                  i = Cv(this.options.drawRules[e + 1 - 2] * O, i, (t - n) / (this.options.drawRules[e] - n))
                }
                if (n < i) {
                  n = i
                  break
                }
              }
            }
          }
          if (l.preConnect) {
            let e = l.preConnect.angle
            e > l.angle && (e -= 2 * Math.PI)
            let t = l.angle - e
            t = 180 * t / Math.PI
            for (let e = 0; e < this.options.drawRules.length; e += 2) {
              if (t < this.options.drawRules[e]) {
                let i = this.options.drawRules[e + 1] * O
                if (e >= 2) {
                  let n = this.options.drawRules[e - 2]
                  i = Cv(this.options.drawRules[e + 1 - 2] * O, i, (t - n) / (this.options.drawRules[e] - n))
                }
                if (n < i) {
                  n = i
                  break
                }
              }
            }
          }
        }
        this.options.maxNodeRadiusRatio && this.options.maxNodeRadiusRatio * t.dist < n && (n = this.options.maxNodeRadiusRatio * t.dist)
        let s = []
        YW(t.paths, n / t.dist, {
          distance: t.dist,
          remainingPoints: s
        }),
          t.paths = s,
        null !== (f = k[T = t.startId]) && void 0 !== f || (k[T] = []),
          l.points = [t.paths[0]],
          k[t.startId].push(l)
      }
      if (o >= 2) {
        h.tubeIndex = e
        let i = t.paths.length - 1
        h.angle = new r(t.paths[i - 1][0], D ? t.paths[i - 1][1] : t.paths[i - 1][2]).sub(new r(t.paths[i][0], D ? t.paths[i][1] : t.paths[i][2])).angle()
        let s = O
        if (this.options.drawRules.length > 0) {
          let t = U[n]
            , i = t.findIndex((t => t.tubeIndex == e))
          if (i >= 0 && (h.preConnect = t[i - 1 < 0 ? t.length - 1 : i - 1],
            h.nextConnect = t[i + 1 > t.length - 1 ? 0 : i + 1]),
            h.nextConnect) {
            let e = h.nextConnect.angle
            e < h.angle && (e += 2 * Math.PI)
            let t = e - h.angle
            t = 180 * t / Math.PI
            for (let e = 0; e < this.options.drawRules.length; e += 2) {
              if (t < this.options.drawRules[e]) {
                let i = this.options.drawRules[e + 1] * O
                if (e >= 2) {
                  let n = this.options.drawRules[e - 2]
                  i = Cv(this.options.drawRules[e + 1 - 2] * O, i, (t - n) / (this.options.drawRules[e] - n))
                }
                if (s < i) {
                  s = i
                  break
                }
              }
            }
          }
          if (h.preConnect) {
            let e = h.preConnect.angle
            e > h.angle && (e -= 2 * Math.PI)
            let t = h.angle - e
            t = 180 * t / Math.PI
            for (let e = 0; e < this.options.drawRules.length; e += 2) {
              if (t < this.options.drawRules[e]) {
                let i = this.options.drawRules[e + 1] * O
                if (e >= 2) {
                  let n = this.options.drawRules[e - 2]
                  i = Cv(this.options.drawRules[e + 1 - 2] * O, i, (t - n) / (this.options.drawRules[e] - n))
                }
                if (s < i) {
                  s = i
                  break
                }
              }
            }
          }
        }
        this.options.maxNodeRadiusRatio && this.options.maxNodeRadiusRatio * t.dist < s && (s = this.options.maxNodeRadiusRatio * t.dist)
        let o = []
        t.paths = YW(t.paths, 1 - s / t.dist, {
          remainingPoints: o
        }),
        null !== (m = k[P = t.endId]) && void 0 !== m || (k[P] = []),
          h.points = [t.paths[t.paths.length - 1]],
          k[t.endId].push(h)
      }
      let c = qv(this, jO, 'm', HO).call(this, t.paths, e, O)
      if ((null == c ? void 0 : c.geometry) && this.geometries.push(c.geometry),
      'rectNoTop' == (null === (v = null === (g = this.options) || void 0 === g ? void 0 : g.sect) || void 0 === v ? void 0 : v.shape)) {
        let i = t.paths
        i.length >= 6 && (i = [t.paths[0], t.paths[1], t.paths[2], t.paths[t.paths.length - 3], t.paths[t.paths.length - 2], t.paths[t.paths.length - 1]]),
          c = qv(this, jO, 'm', HO).call(this, i, e, O, !1, {
            shape: 'rect'
          }, !0)
      }
      let d = null == c ? void 0 : c.geometry.sectDataParam()
      l.sectPoints = c.geometry.attributes.position.array.slice(0, 3 * d.ptCount),
        l.isStartNode = !0
      let y = c.geometry.attributes.position.array.length - 3 * d.ptCount
      h.sectPoints = c.geometry.attributes.position.array.slice(y, y + 3 * d.ptCount),
        h.isStartNode = !1
    }
    for (let e in k) {
      let t = e.split(',').map((e => +e))
        , i = k[e]
      i.sort(((e, t) => e.angle - t.angle))
      let n = []
        , s = new Set
      for (let e = 0; e < i.length; e++) {
        let r = e + 1
        r == i.length && (r = 0)
        let o = qv(this, jO, 'm', HO).call(this, [i[e].points[0], t, i[r].points[0]], -1, 0, !0)
          , a = o.geometry.sectDataParam()
          , l = o.geometry.attributes.position.array
        if ('circleHalf' == o.geometry.options.shape) {
          const e = o.geometry.options.radius || .5
          l[3 * a.halfCount + 0] = t[0],
            l[3 * a.halfCount + 1] = t[1] - (D ? 0 : e),
            l[3 * a.halfCount + 2] = t[2] - (D ? e : 0)
        }
        if (i[e].isStartNode) {
          let t = []
            , n = a.ptCount - a.halfIndex - 1
          for (let s = a.halfStartIndex; s <= a.halfEndIndex; s++) {
            let r = n - s
            t.push(i[e].sectPoints[3 * r]),
              t.push(i[e].sectPoints[3 * r + 1]),
              t.push(i[e].sectPoints[3 * r + 2])
          }
          for (let e = 0; e < t.length; e++) {
            l[e + 3 * a.halfIndex] = t[e]
          }
        } else {
          for (let t = a.halfStartIndex; t < 3 * (a.halfEndIndex + 1); t++) {
            l[t - a.halfStartIndex + 3 * a.halfIndex] = i[e].sectPoints[t]
          }
        }
        if (i[r].isStartNode) {
          for (let e = a.halfStartIndex; e < 3 * (a.halfEndIndex + 1); e++) {
            l[e - a.halfStartIndex + 2 * a.halfCount * 3 + 3 * a.halfIndex] = i[r].sectPoints[e]
          }
        } else {
          let e = []
            , t = a.ptCount - a.halfIndex - 1
          for (let n = a.halfStartIndex; n < 3 * (a.halfEndIndex + 1); n++) {
            let s = t - n
            e.push(i[r].sectPoints[3 * s]),
              e.push(i[r].sectPoints[3 * s + 1]),
              e.push(i[r].sectPoints[3 * s + 2])
          }
          for (let t = 0; t < e.length; t++) {
            l[t + 2 * a.halfCount * 3 + 3 * a.halfIndex] = e[t]
          }
        }
        (null == o ? void 0 : o.geometry) && this.geometries.push(o.geometry),
          n.push(o.geometry),
          s.add(i[e].tubeIndex)
      }
      let r = $i(n)
      qv(this, jO, 'm', VO).call(this, r, !0, [...Array.from(s), ...null !== (y = z[e]) && void 0 !== y ? y : []])
    }
  }

  addTo(e) {
    this.entity.addTo(e)
  }

  getSectPoints(e, t) {
    var n, s
    let r
    if (void 0 === t) {
      let t = Vv(e)
        , i = this.data.filter((e => e.box3d.containsPoint(t)))
        , n = 0
      for (let t = 0; t < i.length; t++) {
        let s = i[t].pathPointList.array.map((e => new Nb(e.pos.x, e.pos.y, e.pos.z)))
          , o = pS(new Nb(e[0], e[1], e[2]), s);
        (0 == t || o.closestLength < n) && (n = o.closestLength,
          r = i[t])
      }
    } else {
      r = this.data[t]
    }
    if (!r) {
      return
    }
    let o = r.pathPointList.array.map((e => new Nb(e.pos.x, e.pos.y, e.pos.z)))
      , a = pS(new Nb(e[0], e[1], e[2]), o)
      , l = r.mesh.geometry.attributes.position.array
      , h = r.mesh.geometry.sectDataParam()
      ,
      c = l.slice(3 * (null !== (n = h.posPtCount) && void 0 !== n ? n : h.ptCount) * a.closestIndex, 3 * (null !== (s = h.posPtCount) && void 0 !== s ? s : h.ptCount) * (a.closestIndex + 1))
      , d = []
    for (let e = 0; e < c.length; e += 3) {
      d.push(new i(c[e], c[e + 1], c[e + 2]))
    }
    const u = (e, t) => {
      let i
      return i = Array.isArray(e) ? d[e[0]].clone().add(t[e[1]].clone()).multiplyScalar(.5) : d[e],
        i
    }

    let p = u(h.posBaseIndex, d)
      , f = new i(a.closestPoint.x, a.closestPoint.y, a.closestPoint.z).sub(p)
    for (let e = 0; e < d.length; e++) {
      d[e].add(f)
    }
    return {
      points: d,
      posBase: u(h.posBaseIndex, d),
      posLeft: u(h.posLeftIndex, d),
      posRight: u(h.posRightIndex, d),
      posTop: u(h.posTopIndex, d),
      posLeftTop: u(h.posLeftTopIndex, d),
      posRightTop: u(h.posRightTopIndex, d),
      data: r
    }
  }

  showEdgeLine(e, t) {
    this.removeEdgeLine()
    let i = this.getEdgeLines(e)
      , n = new KO({
      data: i.map((t => {
          var i
          return {
            coordinates: t,
            color: null !== (i = null == e ? void 0 : e.color) && void 0 !== i ? i : 65535
          }
        }
      ))
    })
    return n.addTo(null != t ? t : Cy.app),
    !1 !== (null == e ? void 0 : e.disableRayQuery) && pW(n.node, !1),
      this._edgeLineEntity = n,
      {
        lineEntity: n,
        coordinates: i
      }
  }

  removeEdgeLine() {
    this._edgeLineEntity && (this._edgeLineEntity.remove(),
      this._edgeLineEntity = null)
  }

  getEdgeLines(e) {
    var t, i
    null != e || (e = {}),
    null !== (t = e.bottom) && void 0 !== t || (e.bottom = !0),
    null !== (i = e.top) && void 0 !== i || (e.top = !0)
    let n = []
    const s = (e, t, i) => {
      if (Array.isArray(e)) {
        let n = [t[i + 3 * e[0]], t[i + 3 * e[0] + 1], t[i + 3 * e[0] + 2]]
          , s = [t[i + 3 * e[1]], t[i + 3 * e[1] + 1], t[i + 3 * e[1] + 2]]
        return [(n[0] + s[0]) / 2, (n[1] + s[1]) / 2, (n[2] + s[2]) / 2]
      }
      return [t[i + 3 * e], t[i + 3 * e + 1], t[i + 3 * e + 2]]
    }

    return this.geometries.forEach((t => {
        var i, r, o, a
        let l = t.attributes.position.array
          , h = t.sectDataParam()
          , c = t.options.shape.indexOf('Half') > 0
          , d = c ? h.halfCount : null !== (i = h.posPtCount) && void 0 !== i ? i : h.ptCount
        if (c) {
          if (e.bottom) {
            let e = []
              , t = null !== (r = h.posHalfLeftIndex) && void 0 !== r ? r : h.posLeftIndex
            for (let i = 0; i < l.length; i += 3 * d) {
              e.push(s(t, l, i))
            }
            n.push(e)
          }
          if (e.top) {
            let e = []
              , t = null !== (o = h.posHalfLeftTopIndex) && void 0 !== o ? o : h.posLeftTopIndex
            for (let i = 0; i < l.length; i += 3 * d) {
              e.push(s(t, l, i))
            }
            n.push(e)
          }
          if (e.center) {
            let e = []
              , t = null !== (a = h.posHalfBaseIndex) && void 0 !== a ? a : h.posBaseIndex
            for (let i = 0; i < l.length; i += 3 * d) {
              e.push(s(t, l, i))
            }
            n.push(e)
          }
        } else {
          if (e.bottom) {
            let e = []
            for (let t = 0; t < l.length; t += 3 * d) {
              e.push(s(h.posLeftIndex, l, t))
            }
            n.push(e)
            let t = []
            for (let e = 0; e < l.length; e += 3 * d) {
              t.push(s(h.posRightIndex, l, e))
            }
            n.push(t)
          }
          if (e.top) {
            let e = []
            for (let t = 0; t < l.length; t += 3 * d) {
              e.push(s(h.posLeftTopIndex, l, t))
            }
            n.push(e)
            let t = []
            for (let e = 0; e < l.length; e += 3 * d) {
              t.push(s(h.posRightTopIndex, l, e))
            }
            n.push(t)
          }
          if (e.center) {
            let e = []
              , t = h.posBaseIndex
            for (let i = 0; i < l.length; i += 3 * d) {
              e.push(s(t, l, i))
            }
            n.push(e)
          }
        }
      }
    )),
      n
  }

  remove() {
    this.entity && (this.entity.remove(),
      this.entity = null)
  }
}
</script>
