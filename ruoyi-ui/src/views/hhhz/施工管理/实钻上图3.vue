<template>
  <div>

    <el-steps :active="currentStep" simple>
      <el-step title="实钻录入"></el-step>
      <el-step title="查看三维示意图"></el-step>
      <el-step title="空白带查询"></el-step>
      <el-step title="自动补钻计算"></el-step>
    </el-steps>

    <el-row v-show="!showThree">
      <el-col :span="3">
        <div style="width: 1px;height: 1px">
        </div>
      </el-col>
      <el-col :span="18">
        <div style="padding: 20px 20px">
          <el-row>
            <div class="inner-container">
              <el-form :model="baseData" ref="baseDataForm" size="small" :inline="true" label-width="90px">
                <el-form-item label="抽采半径" prop="radius">
                  <el-input
                    v-model="radius"
                    placeholder="请输入抽采半径"
                    clearable
                    style="width: 130px"
                    @change="changeRadius"
                    @blur="radius !='' ? Number(radius):0"
                  >
                    <template slot="append">m</template>
                  </el-input>
                </el-form-item>
              </el-form>
            </div>
          </el-row>

          <el-divider/>

          <el-row>
            <div style="text-align: justify">
              <div style="display: inline-block;width: 20%;vertical-align: top;">
                <h3>钻孔信息</h3>
              </div>
              <div style="display: inline-block;width: 80%;text-align: right;vertical-align: top;">
                <el-form style="display: inline-block;float: right" :inline="true">
                  <el-form-item label="渲染钻孔条数" prop="inputNumber">
                    <el-radio-group v-model="inputNumber" @change="changeRenderNum" size="small">
                      <el-radio-button label="100">100条</el-radio-button>
                      <el-radio-button label="300">300条</el-radio-button>
                      <el-radio-button label="500">500条</el-radio-button>
                      <el-radio-button label="999999">全部</el-radio-button>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item>
                    <el-button @click="clearDrillList">清空列表</el-button>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="calModelDataLoading">显示三维示意图</el-button>
                  </el-form-item>
                </el-form>
              </div>
            </div>
            <div class="inner-container">
              <el-table
                key="id"
                :data="drillingDataList"
                height="280"
              >
                <el-table-column label="列号" prop="group">
                  <template slot-scope="scope">
                    <el-input
                      v-if="!scope.row.disabled"
                      v-model="scope.row.group"
                      placeholder=""
                      :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.group : '' }}
                  </template>
                </el-table-column>
                <el-table-column label="序号" prop="id">
                  <template slot-scope="scope">
                    <el-input
                      v-if="!scope.row.disabled"
                      v-model="scope.row.id"
                      placeholder=""
                      :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.id : '' }}
                  </template>
                </el-table-column>
                <el-table-column label="钻孔方位角" prop="azimuth">
                  <template slot-scope="scope">
                    <el-input
                      v-if="!scope.row.disabled"
                      v-model="scope.row.azimuth"
                      placeholder=""
                      :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.azimuth : '' }}
                  </template>
                </el-table-column>
                <el-table-column label="钻孔倾角" prop="inclination">
                  <template slot-scope="scope">
                    <el-input
                      v-if="!scope.row.disabled"
                      v-model="scope.row.inclination"
                      placeholder=""
                      :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.inclination : '' }}
                  </template>
                </el-table-column>
                <el-table-column label="与导线点距离" prop="disNav">
                  <template slot-scope="scope">
                    <el-input
                      v-if="!scope.row.disabled"
                      v-model="scope.row.disNav"
                      placeholder=""
                      :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.disNav : '' }}
                  </template>
                </el-table-column>
                <el-table-column label="距顶板高度" prop="disTop">
                  <template slot-scope="scope">
                    <el-input
                      v-if="!scope.row.disabled"
                      v-model="scope.row.disTop"
                      placeholder=""
                      :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.disTop : '' }}
                  </template>
                </el-table-column>
                <el-table-column label="据中心线距离" prop="disCenter">
                  <template slot-scope="scope">
                    <el-input
                      v-if="!scope.row.disabled"
                      v-model="scope.row.disCenter"
                      placeholder=""
                      :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.disCenter : '' }}
                  </template>
                </el-table-column>
                <el-table-column label="见煤深度" prop="seeLen">
                  <template slot-scope="scope">
                    <el-input
                      v-if="!scope.row.disabled"
                      v-model="scope.row.seeLen"
                      placeholder=""
                      :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.seeLen : '' }}
                  </template>
                </el-table-column>
                <el-table-column label="止煤深度" prop="overLen">
                  <template slot-scope="scope">
                    <el-input
                      v-if="!scope.row.disabled"
                      v-model="scope.row.overLen"
                      placeholder=""
                      :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.overLen : '' }}
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作"
                  align="center"
                  class-name="small-padding fixed-width"
                >
                  <template slot-scope="scope">
                    <el-button
                      v-if="!scope.row.add && scope.row.disabled"
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="() => {scope.row.disabled = false;}">修改
                    </el-button>
                    <el-button
                      style="color: red"
                      v-if="!scope.row.add && scope.row.disabled"
                      size="mini"
                      type="text"
                      icon="el-icon-close"
                      @click="delDrillData(scope)">删除
                    </el-button>
                    <el-button
                      v-if="!scope.row.add && !scope.row.disabled"
                      size="mini"
                      type="text"
                      icon="el-icon-check"
                      @click="updateDrillData(scope.row)">
                      保存
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div style="margin-top:5px;float: right">
                <el-form style="display: inline-block;float: left" :inline="true">

                  <el-form-item>
                    <el-button type="primary" @click="addSingleDrillData">新增一条钻孔信息</el-button>
                  </el-form-item>
                  <el-form-item>
                    <el-upload
                      ref="upload"
                      action="xlsx.html"
                      :auto-upload="false"
                      :on-change="getMyExcel"
                      :show-file-list="false"
                    >
                      <el-button type="primary">批量导入数据</el-button>
                    </el-upload>
                  </el-form-item>
                </el-form>
              </div>
            </div>
          </el-row>
        </div>
      </el-col>
      <el-col :span="3">
        <div style="width: 1px;height: 1px">
        </div>
      </el-col>
    </el-row>

    <el-row v-show="showThree">
      <el-col :span="21">
        <div id="box" style="display: flex">
          <div id="container"></div>
          <div id="tGUI" style="width:250px;margin-left: -250px"></div>
          <!--<div id="output"></div>-->
        </div>
      </el-col>
      <el-col :span="3" v-if="currentStep===2">
        <el-row style="margin-top: 20px;width: 100%">
          <el-button @click="changePage(1)" style="float: right" icon="el-icon-back">返回数据页</el-button>
        </el-row>
        <el-row style="margin-top: 20px;width: 100%">
          <el-button v-if="param.showCoalTopMesh" @click="showOrHideTop" style="float: right">
            隐藏煤层顶板
          </el-button>
          <el-button type="primary" v-if="!param.showCoalTopMesh"
                     @click="showOrHideTop" style="float: right">显示煤层顶板
          </el-button>
        </el-row>
        <el-row style="margin-top: 20px;width: 100%">
          <el-button v-if="param.showCoalBottomMesh"
                     @click="showOrHideBottom" style="float: right">隐藏煤层底板
          </el-button>
          <el-button type="primary" v-if="!param.showCoalBottomMesh"
                     @click="showOrHideBottom" style="float: right">显示煤层底板
          </el-button>
        </el-row>
        <el-row style="margin-top: 20px;width: 100%">
          <el-button v-if="param.showCoalCube" @click="showOrHideCoal" style="float: right">隐藏煤体
          </el-button>
          <el-button type="primary" v-if="!param.showCoalCube" @click="showOrHideCoal" style="float: right">
            显示煤体
          </el-button>
        </el-row>
        <el-row style="margin-top: 20px;width: 100%">
          <el-button v-if="param.showOriginDrill" @click="showOrHideOriginDrill" style="float: right">隐藏钻杆
          </el-button>
          <el-button type="primary" v-if="!param.showOriginDrill" @click="showOrHideOriginDrill"
                     style="float: right">
            显示钻杆
          </el-button>
        </el-row>
        <el-row style="margin-top: 20px;width: 100%">
          <el-button v-if="param.showOriginDrillInCoal" @click="showOrHideOriginDrillInCoal" style="float: right">隐藏煤孔段
          </el-button>
          <el-button type="primary" v-if="!param.showOriginDrillInCoal" @click="showOrHideOriginDrillInCoal"
                     style="float: right">
            显示煤孔段
          </el-button>
        </el-row>
        <el-row></el-row>
        <el-row style="margin-top: 40px;width: 100%">
          <el-button type="primary" @click="calInitResultLoading" style="float: right">查找空白带</el-button>
        </el-row>
      </el-col>
      <el-col :span="3" v-if="currentStep===3||currentStep===4">

        <el-button style="margin-top: 20px;margin-left: -60px;" @click="changePage(1)" icon="el-icon-back">返回数据页
        </el-button>
        <el-collapse v-model="activeNames" style="margin-left: -60px;margin-right: 20px">
          <el-collapse-item title="" name="1">
            <template slot="title">
              <div style="width: 100%;padding-left: 10px;">空白带信息</div>
            </template>
            <el-table
              key="uuid"
              :data="emptyBandList"
              :default-sort="{prop: 'volume', order: 'descending'}"
              height="250"
            >
              <el-table-column label="空白带编号" prop="uuid" width="85px" align="center"/>
              <el-table-column label="中心点坐标" prop="position" align="center"/>
              <el-table-column label="体积" prop="volume" width="80px" align="center"/>
            </el-table>
          </el-collapse-item>
          <el-collapse-item name="2">
            <template slot="title">
              <div style="width: 100%;padding-left: 10px;">当前选中钻孔信息</div>
            </template>
            <div style="padding: 10px 10px 10px 10px">
              <el-descriptions :column="1">
                <el-descriptions-item label="列号">{{ selectedDrill.group }}</el-descriptions-item>
                <el-descriptions-item label="序号">{{ selectedDrill.id }}</el-descriptions-item>
                <el-descriptions-item label="钻孔方位角">{{ selectedDrill.azimuth }}</el-descriptions-item>
                <el-descriptions-item label="钻孔倾角">{{ selectedDrill.inclination }}</el-descriptions-item>
                <el-descriptions-item label="与导线点距离">{{ selectedDrill.disNav }}</el-descriptions-item>
                <el-descriptions-item label="距顶板高度">{{ selectedDrill.disTop }}</el-descriptions-item>
                <el-descriptions-item label="据中心线距离">{{ selectedDrill.disCenter }}</el-descriptions-item>
                <el-descriptions-item label="见煤深度">{{ selectedDrill.seeLen }}</el-descriptions-item>
                <el-descriptions-item label="止煤深度">{{ selectedDrill.overLen }}</el-descriptions-item>
                <el-descriptions-item label="类型">
                  <el-tag v-if="selectedDrill.type===1" size="small" type="warning">实钻钻孔</el-tag>
                  <el-tag v-if="selectedDrill.type===2" size="small">补空白设计钻孔</el-tag>
                </el-descriptions-item>
                <el-descriptions-item v-if="selectedDrill.type===2" label="操作">
                  <el-popover
                    placement="top"
                    width="160"
                    v-model="delAddDrillVisible">
                    <p>删除后将重新计算空白区域, 确定删除么？</p>
                    <div style="text-align: right; margin: 0">
                      <el-button size="mini" type="text" @click="delAddDrillVisible = false">取消</el-button>
                      <el-button size="mini" type="danger" @click="delAddDrill">确定</el-button>
                    </div>
                    <el-button slot="reference" type="danger" size="mini">
                      删除此钻孔
                    </el-button>
                  </el-popover>
                </el-descriptions-item>
              </el-descriptions>
            </div>
          </el-collapse-item>
          <el-collapse-item name="3">
            <template slot="title">
              <div style="width: 100%;padding-left: 10px;">补钻操作区</div>
            </template>
            <el-form style="display: inline-block;float: left" size="small" :inline="true">
              <div style="padding: 10px 10px 10px 10px">
                <el-form-item label="施工点选择">
                  <el-radio-group v-model="addDrillForm.workPlace">
                    <el-radio :label="1">原始施工点</el-radio>
                    <el-radio :label="2">巷道最近点</el-radio>
                  </el-radio-group>
                </el-form-item>
                <!--              <el-collapse>
                                <el-collapse-item title="手动补钻" name="1">-->
                <el-form-item label="开启手动补钻模式">
                  <el-switch v-model="addDrillForm.drillDesMode" @change="desModStatChange"></el-switch>
                </el-form-item>
                <br>
                <el-form-item label="全自动补钻模式">
                  <el-button type="primary" size="mini" @click="autoAddDrillLoading">一键自动补钻</el-button>
                </el-form-item>
                <el-button type="primary" size="mini" @click="showAddDrillData"
                           style="margin-bottom: 5px;margin-right: 5px">
                  显示补钻参数表
                </el-button>
              </div>
            </el-form>
          </el-collapse-item>
        </el-collapse>


      </el-col>
    </el-row>

    <el-dialog title="补钻参数表" :visible.sync="addDrillDataVisible">
      <el-table
        key="id"
        height="350"
        :data="addDrillingDataList"
      >
        <el-table-column label="组号" prop="group">
        </el-table-column>
        <el-table-column label="序号" prop="id">
        </el-table-column>
        <el-table-column label="钻孔方位角" prop="azimuth">
        </el-table-column>
        <el-table-column label="钻孔倾角" prop="inclination">
        </el-table-column>
        <el-table-column label="与导线点距离" prop="disNav">
        </el-table-column>
        <el-table-column label="距顶板高度" prop="disTop">
        </el-table-column>
        <el-table-column label="据中心线距离" prop="disCenter">
        </el-table-column>
        <el-table-column label="见煤深度" prop="seeLen">
        </el-table-column>
        <el-table-column label="止煤深度" prop="overLen">
        </el-table-column>
      </el-table>
      <el-button style="float: right;margin-bottom: 10px" type="primary" size="mini" @click="exportDrillData">导出
      </el-button>
    </el-dialog>

    <el-dialog title="空白带信息" :visible.sync="emptyDialogVisible">
      <el-table
        key="uuid"
        :data="emptyBandList"
        height="350"
        style="width: 40%"
      >
        <el-table-column label="空白带编号" prop="uuid" align="center"/>
        <el-table-column label="中心点坐标" prop="position" align="center"/>
        <el-table-column label="体积" prop="volume" align="center"/>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit"
                       @click="autoAddDrillLoading">
              自动补钻
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>

import * as THREE from 'three'
import {Mesh} from 'three'
import {OrbitControls} from 'three/examples/jsm/controls/OrbitControls.js'
import {TransformControls} from 'three/examples/jsm/controls/TransformControls.js'
import {CSS3DObject, CSS3DSprite} from 'three/examples/jsm/renderers/CSS3DRenderer.js'
import {computeMeshVolume, Evaluator, GridMaterial, Operation, OperationGroup, SUBTRACTION} from 'three-bvh-csg'
//import {GUI} from 'three/examples/jsm/libs/lil-gui.module.min.js'
import { GUI } from 'lil-gui';
import {Delaunay as Delaunator} from 'd3-delaunay'
import {PLYLoader} from 'three/examples/jsm/loaders/PLYLoader.js'
import * as XLSX from 'xlsx'
import {EffectComposer} from 'three/examples/jsm/postprocessing/EffectComposer.js'
import {RenderPass} from 'three/examples/jsm/postprocessing/RenderPass.js'
import {OutlinePass} from 'three/examples/jsm/postprocessing/OutlinePass.js'
import {ShaderPass} from 'three/examples/jsm/postprocessing/ShaderPass.js'
import {FXAAShader} from 'three/examples/jsm/shaders/FXAAShader.js'
import {ConvexGeometry} from 'three/examples/jsm/geometries/ConvexGeometry.js'
import {v4 as uuidv4} from 'uuid'
import * as YUKA from '@/utils/yuka/yuka.module.js'
import {createOBBHelper} from '@/utils/yuka/BVHelper'
import {Loading} from 'element-ui'
import ExportJsonExcel from 'js-export-excel'
import * as cdt2d from 'cdt2d'

const loader = new PLYLoader()

const params = {
  snap: true,
  displayControls: false,
  transparentBrushes: true,
  showOutCube: false,// 显示外部包围体
  showCoalCube: false,// 显示煤层体
  showCoalBottomMesh: true,
  showCoalTopMesh: true,
  display: 'OVERLAY',
  drillDesMode: false, // 开启补钻模式
  drillSelectMode: true, // 钻孔选择模式
  showOriginDrill: true, // 显示原始钻孔
  showOriginDrillRange: true, // 显示原始钻孔抽采范围
  oriDRMOpacity: 0.3, // 原始钻孔抽采范围透明度
  showAddDrill: true, // 显示补钻钻孔
  showAddDrillRange: true, // 显示补钻钻孔抽采范围
  addDRMOpacity: 0.3, // 原始钻孔抽采范围透明度
  showOriginEmptyBand: true, // 显示原始空白带信息
  showCoalMesh: true, // 显示煤层顶底板网格
  coalCubeOpacity: 0.2,// 煤层块儿透明度
  showEmptyBand: true, // 显示空白带
  showOriginDrillInCoal: true, // 显示原始煤孔段
  showAddDrillInCoal: false // 显示新增煤孔段
}

let renderer, camera, scene, gui, outputContainer, textOutput, axesHelper, tGUIContainer
let controls, transformControls
let light
let initCSGEvaluator, addCSGEvaluator
let initResult, initRoot, partsResult, partsRoot
let rootPartsObject, wireframeObject, coalCubeObj, loadedObj
let coalBottomMesh, coalTopMesh, roadMesh
let coalGeometry
let drillInCoalMat, coalTopMeshMat, coalBottomMeshMat, roadMat, brushMat, addDrillMat, addDrillRangeMat, originDrillMat,
  originDrillRangeMat, resultGridMat, originEmptyBandMat, originDrillMatS, originDrillMatD, originDrillRangeMatS,
  originDrillRangeMatD, coalCubeMat, tempSelectedMat, originSelectedMat
let mouse = new THREE.Vector2()
let partMeshs = []
let partCubes = []
let composer, outlinePass, renderPass
let canvas1, context1, texture1, sprite1
let tempSelected
export default {
  name: 'EmptyBandModel',
  components: {},

  data() {
    return {
      needReRender: true,// 是否需要重渲染
      renderNum: -1,
      activeNames: ['1', '2'],
      stopLoading: false,
      sceneInit: false,// 场景初始化
      needReCalculate: true,// 是否需要重计算
      currentStep: 1,// 当前所在页面
      initEmptyBand: false,// 是否计算了空白带
      param: {
        showCoalTopMesh: true,// 显示顶板
        showCoalBottomMesh: true,// 显示地板
        showCoalCube: false,// 显示煤体
        showOriginDrill: true,// 显示原始钻孔
        showOriginDrillInCoal: true// 显示原始煤孔段
      },
      // 补钻操作区参数
      addDrillForm: {
        workPlace: 1,//钻孔工作点位,1=原始施工点,2巷道最近点
        drillDesMode: false//开启手动补钻模式
      },
      // 渲染数据条数
      inputNumber: 100,
      showThree: false,
      // 自动补钻标识
      autoAdd: false,
      autoIndex: 1,
      // 计算加载中
      loading: false,
      // 基础信息
      baseData: {navX: 0, navY: 0, navZ: 0, azimuth: 0, inclination: 0},
      // 添加钻孔信息
      drillingData: {},
      // tempList
      tempList: [],
      // 原始钻孔施工数据列表
      drillingDataList: [],
      // 补钻钻孔施工数据列表
      addDrillingDataList: [],
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
      // 点选施工点位
      endPoint: null,
      // 钻场位置
      startPoint: null,
      startPointId: null,
      // 选中钻孔终点
      clickPoint: null,
      // 施工点位
      workPointsMeshList: [],
      workPointsNameMeshList: [],
      // 原始钻孔点
      points3DBottom: [],
      points3DTop: [],
      loadingInstance: null,
      // 原始钻孔
      originDrillMeshList: [],
      originDrillInCoalMeshList: [],
      originDrillGroupMeshList: [],
      originDrillRangeToSceneList: [],
      // 补钻钻孔
      addDrillMeshList: [],
      addDrillInCoalMeshList: [],
      addDrillGroupMeshList: [],
      addDrillRangeToSceneList: [],
      // 补钻上下点位
      addCoalTopPointMeshList: [],
      addCoalBottomPointMeshList: [],
      // 钻孔行数
      groupNum: 10,
      queryParams: {
        workfaceId: null,
        roadwayId: null,
        navPointId: null
      },
      selectedDrill: {
        group: '', id: '', inclination: '', azimuth: '', disNav: '', disTop: '', disCenter: '', seeLen: '', overLen: ''
      },
      // 补钻参数表显隐
      addDrillDataVisible: false,
      delAddDrillVisible: false,
      emptyDialogVisible: false,
      addDGLlength: -1,
      delDrill: false,
      addDrillDataNumber: 0,
      // 工作面列表
      coalMiningWFList: [],
      workingFace: {},
      roadWayList: [],
      roadWay: {},
      navPointList: [],
      navPoint: {}
    }
  },

  mounted() {
    //this.init()
  },
  methods: {
    /**
     * 初始化
     * @returns {Promise<void>}
     */
    async init() {

      if (!this.sceneInit) {
        // 如果场景没有初始化,先初始化场景
        // 初始化场景
        this.initScene()

        // 材质初始化
        this.createMat()

        // 创建GUI
        this.createGUI()
        this.sceneInit = true

        if (this.needReCalculate) {
          // 如果需要重新计算, 全部重新计算生成
          // 创建煤层
          this.createCoalPoints()

          // 创建施工点位
          this.calWorkPoints()

          // 创建施工点,已合并到calWorkPoints
          // this.createWorkPoints()

          // 创建巷道
          this.createRoad()

          // 创建原始钻杆实体
          this.createOriginDrill()

          // 计算初始化空白带
          //this.calInitResult()
          this.needReCalculate = false
        }
        this.render()
      }

      if (this.needReCalculate) {
        // 如果需要重新计算, 全部重新计算生成
        // 创建煤层
        this.createCoalPoints()
        // 创建施工点位
        this.calWorkPoints()
        // 创建施工点,已合并到calWorkPoints
        // this.createWorkPoints()
        // 创建巷道
        this.createRoad()
        // 创建原始钻杆实体
        this.createOriginDrill()
        // 计算初始化空白带
        //this.calInitResult()
        this.needReCalculate = false
      }
    },
    changeRadius(r) {
      this.radius = Number(r)
      this.needReCalculate = true
    },
    makeLabelCanvas(baseWidth, size, name) {
      const borderSize = 2
      const ctx = document.createElement('canvas').getContext('2d')
      const font = `${size}px bold sans-serif`
      ctx.font = font
      // console.log("ctx",ctx)
      // measure how long the name will be
      const textWidth = ctx.measureText(name).width

      const doubleBorderSize = borderSize * 2
      const width = baseWidth + doubleBorderSize
      const height = size + doubleBorderSize
      ctx.canvas.width = width
      ctx.canvas.height = height

      // need to set font again after resizing canvas
      ctx.font = font
      ctx.textBaseline = 'middle'
      ctx.textAlign = 'center'

      ctx.fillStyle = '#fffffc'
      ctx.fillRect(0, 0, width, height)

      // scale to fit but don't stretch
      const scaleFactor = Math.min(1, baseWidth / textWidth)
      ctx.translate(width / 2, height / 2)
      ctx.scale(scaleFactor, 1)
      ctx.fillStyle = 'black'
      ctx.fillText(name, 0, 0)

      return ctx.canvas
    },
    addCSS3DSpirit(object, text) {
      const div = document.createElement('div')
      div.className = 'red-box-label'
      div.setAttribute('style', 'color: red;')
      div.textContent = text
      // CSS3DSprite;CSS3DObject
      const css3DSp = new CSS3DSprite(div)
      css3DSp.position.set(0, -2, 0)
      css3DSp.scale.set(0.01, 0.01, 0.01)
      object.add(css3DSp)
    },
    addSpirit(name) {
      const canvas = this.makeLabelCanvas(40, 20, name)
      const canvasTexture = new THREE.CanvasTexture(canvas)
      canvasTexture.minFilter = THREE.LinearFilter
      canvasTexture.wrapS = THREE.ClampToEdgeWrapping
      canvasTexture.wrapT = THREE.ClampToEdgeWrapping
      const labelMaterial = new THREE.SpriteMaterial({
        map: canvasTexture,
        transparent: false
      })
      const label = new THREE.Sprite(labelMaterial)
      label.position.z = this.minY + 30
      const labelBaseScale = 0.05
      label.scale.x = canvas.width * labelBaseScale
      label.scale.y = canvas.height * labelBaseScale
      return label
    },
    exportDrillData() {
      this.download()
    },

    download() {
      let tableExcel = []
      this.addDrillingDataList.map(item => {
        tableExcel.push({
          '组号': item.group,
          '孔号': item.id,
          '方位角(°)': item.azimuth,
          '倾角(°)': item.inclination,
          '距导线点(m)': item.disNav,
          '距顶板(m)': item.disTop,
          '距中心线(m)': item.disCenter,
          '见煤深度(m)': item.seeLen,
          '止煤深度(m)': item.overLen
        })
      })
      this.exportToExcel(tableExcel)
      console.log(tableExcel)
    },
    exportToExcel(tableExcel) {
      let option = {}
      option.fileName = '空白带补钻_施工参数表'
      option.datas = [
        {
          sheetData: tableExcel,
          sheetName: '施工参数表',
          sheetFilter: [
            '组号',
            '孔号',
            '方位角(°)',
            '倾角(°)',
            '距导线点(m)',
            '距顶板(m)',
            '距中心线(m)',
            '见煤深度(m)',
            '止煤深度(m)'
          ],
          sheetHeader: [
            '组号',
            '孔号',
            '方位角(°)',
            '倾角(°)',
            '距导线点(m)',
            '距顶板(m)',
            '距中心线(m)',
            '见煤深度(m)',
            '止煤深度(m)'
          ]
        }
      ]
      var toExcel = new ExportJsonExcel(option)
      toExcel.saveExcel()
    },
    changeRenderNum() {
      this.needReCalculate = true
    },
    showOrHideTop() {
      this.param.showCoalTopMesh = !this.param.showCoalTopMesh
      params.showCoalTopMesh = !params.showCoalTopMesh
    },
    showOrHideBottom() {
      this.param.showCoalBottomMesh = !this.param.showCoalBottomMesh
      params.showCoalBottomMesh = !params.showCoalBottomMesh
    },
    showOrHideCoal() {
      this.param.showCoalCube = !this.param.showCoalCube
      params.showCoalCube = !params.showCoalCube
    },
    showOrHideOriginDrill() {
      this.param.showOriginDrill = !this.param.showOriginDrill
      params.showOriginDrill = !params.showOriginDrill
    },
    showOrHideOriginDrillInCoal() {
      this.param.showOriginDrillInCoal = !this.param.showOriginDrillInCoal
      params.showOriginDrillInCoal = !params.showOriginDrillInCoal
    },
    changePage(page) {
      if (page === 1) {
        this.showThree = false
      } else if (page === 2) {
        if (this.needReCalculate === false && this.initEmptyBand === true) {
          page = 3
          if (this.addDrillingDataList.length > 0) {
            page = 4
          }
        }
        this.showThree = true
      }
      this.currentStep = page
    },

    clearDrillList() {
      this.drillingDataList = []
      this.needReCalculate = true
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
    },

    /**
     * 自动补钻模式切换
     * @param mode
     */
    desModStatChange(mode) {
      params.drillDesMode = mode
      this.addDrillForm.drillDesMode = mode
    },
    /**
     * 删除补孔
     */
    delAddDrill() {
      this.delAddDrillVisible = false
      console.log('addDrillGroupList:', this.addDrillGroupMeshList)
      console.log('children0:', partsRoot.children)
      let name = this.selectedDrill.name
      this.cleanAddDrill(name, this.addDrillMeshList)
      this.cleanAddDrill(name, this.addDrillInCoalMeshList)
      this.cleanAddDrill(name, this.addDrillRangeToSceneList)
      this.cleanAddDrill(name, this.addCoalTopPointMeshList)
      this.cleanAddDrill(name, this.addCoalBottomPointMeshList)
      this.cleanAddDrill(name, this.addDrillingDataList)
      let index = -1
      for (let i = 0; i < partsRoot.children.length; i++) {
        if (partsRoot.children[i].name === name) {
          index = i
          break
        }
      }
      if (index !== -1) {
        partsRoot.children.splice(index, 1)
        this.createDefaultOp(partsRoot)
        this.delDrill = true
      }
      this.selectedDrill = {
        group: '', id: '', inclination: '', azimuth: '', disNav: '', disTop: '', disCenter: '', seeLen: '', overLen: ''
      }
      console.log('children1:', partsRoot.children)
      this.loadingInstance = Loading.service({
        lock: true,
        text: '正在计算中,请稍后...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.stopLoading = true
      this.needReRender = true
    },

    /**
     * 清道夫
     * @param name
     * @param list
     */
    cleanAddDrill(name, list) {
      let indexArr = []
      for (let i = 0; i < list.length; i++) {
        if (list[i].name === name) {
          indexArr.push(i)
          //this.cleanCache(list[i])
        }
      }
      for (let i = 0; i < indexArr.length; i++) {
        this.cleanCache(list[indexArr[i]])
        list.splice(indexArr[i], 1)
      }
    },

    // 显示补钻参数表
    showAddDrillData() {
      this.addDrillDataVisible = true
    },
    // 显示空白带参数表
    showEmptyBandData() {
      this.emptyDialogVisible = true
    },
    // 删除钻孔数据
    delDrillData(row) {
      console.log(row)
      this.drillingDataList.splice(row.$index, 1)
    },
    // 更新钻孔数据
    updateDrillData(row) {
      row.group = Number(row.group)
      row.disNav = Number(row.disNav)
      row.disCenter = Number(row.disCenter)
      row.disTop = Number(row.disTop)
      row.azimuth = Number(row.azimuth)
      row.inclination = Number(row.inclination)
      row.kg = Number(row.kg)
      row.overLen = Number(row.overLen)
      row.ccml = Number(row.ccml)
      row.seeLen = Number(row.seeLen)
      console.log(row)
      row.disabled = true
      this.needReCalculate = true
    },
    addSingleDrillData() {
      let obj = {
        group: 0,
        id: 0,
        disNav: 0,
        disCenter: 0,
        disTop: 0,
        azimuth: 0,
        inclination: 0,
        kg: 0,
        overLen: 0,
        ccml: 0,
        seeLen: 0,
        disabled: false,
        type: 1
      }
      this.drillingDataList.push(obj)
    },
    getMyExcel(file, fileList) {
      let _this = this
      // 用FileReader来完成数据流的转换
      let reader = new FileReader()
      reader.onload = function (e) {
        let data = e.target.result
        //console.log(XLSX)
        //通过XLSX来解析出workbook（工作簿）也就导入的excel文件
        let wb = XLSX.read(data, {type: 'binary'})
        console.log(wb)
        let base = 10
        console.log('SheetNames:', wb.SheetNames)
        console.log('sheetData:', wb.Sheets[wb.SheetNames[0]])
        console.log('keys:', Object.keys(wb.Sheets[wb.SheetNames[0]]))
        let keys = Object.keys(wb.Sheets[wb.SheetNames[0]])
        let rowList = []
        for (let i = 0; i < keys.length; i++) {
          if (keys[i].startsWith('A')) {
            rowList.push(Number(keys[i].replaceAll('A', '')))
          }
        }
        let sortedList = rowList.sort((a, b) => {
          return b - a
        })
        console.log('sortedList:', sortedList)
        for (let i = 5; i <= sortedList[0]; i++) {
          let obj = {
            group: Number((wb.Sheets['11104工作面底板穿层钻孔台账']['A' + i]['v'] + '').replaceAll('列', '')) - 63,
            id: wb.Sheets['11104工作面底板穿层钻孔台账']['B' + i]['v'],
            disNav: base + ((Number((wb.Sheets['11104工作面底板穿层钻孔台账']['A' + i]['v'] + '').replaceAll('列', '')) - 63) * 4),
            disCenter: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['J' + i]['v']).toFixed(2),
            disTop: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['K' + i]['v']).toFixed(2),
            azimuth: Math.abs(Number(wb.Sheets['11104工作面底板穿层钻孔台账']['L' + i]['v'])).toFixed(2),
            inclination: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['M' + i]['v']).toFixed(2),
            kg: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['N' + i]['v']).toFixed(2),
            overLen: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['O' + i]['v']).toFixed(2),
            ccml: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['P' + i]['v']).toFixed(2),
            seeLen: Number(Number((wb.Sheets['11104工作面底板穿层钻孔台账']['O' + i]['v']) - Number(wb.Sheets['11104工作面底板穿层钻孔台账']['Q' + i]['v']))).toFixed(2),
            disabled: true,// 代表非编辑状态
            type: 1 // 1代表实钻钻孔
          }
          _this.drillingDataList.push(obj)
        }
        console.log('共导入施工数据:' + _this.drillingDataList.length + '条')
      }
      //readAsBinaryString需要File 或 Blob 对象的参数
      reader.readAsBinaryString(file.raw)
      // console.log(XLSX.read(file, { type: "array" }));
    },

    // 选择施工位置
    changeWP(index) {
      console.log(index)
      for (let i = 0; i < this.workPointsMeshList.length; i++) {
        this.workPointsMeshList[i].material.color.set(0xffffff)
      }
      this.workPointsMeshList[index].material.color.set(0xff00ff)
    },
    // 手动添加钻孔
    addDrillLoading() {
      this.loadingInstance = Loading.service({
        lock: true,
        text: '正在计算中,请稍后...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.changePage(4)
      //this.addDrill()
      setTimeout(() => {
        this.stopLoading = true
        this.addDrill()
      }, 1000)
    },
    // 自动补钻
    autoAddDrillLoading() {
      this.loadingInstance = Loading.service({
        lock: true,
        text: '正在计算中,请稍后...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.changePage(4)
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

        let wkPoint = null
        if (this.addDrillForm.workPlace === 1) {
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
            Object.keys(groupCountObj).forEach(function (key) {
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
          Object.keys(finalGroupsObj).forEach(function (key) {
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
          for (let j = 0; j < this.workPoints.length; j++) {
            // 确定出施工点位置
            if (this.workPoints[j].group === Number(targetGroup)) {
              console.log('施工点位:', this.workPoints[j])
              wkPoint = this.workPoints[j]
              break
            }
          }
          if (wkPoint !== null) {
            this.addDrillToCal(new THREE.Vector3(wkPoint.x, wkPoint.z, wkPoint.y), endPoint, '补钻-' + uuidv4().replaceAll('-', ''))
          }
        } else {
          let closestPoint = this.closestPoint(endPoint.x, endPoint.y, endPoint.z,
            this.workPoints[0].x, this.workPoints[0].z, this.workPoints[0].y,
            this.workPoints[this.workPoints.length - 1].x, this.workPoints[this.workPoints.length - 1].z, this.workPoints[this.workPoints.length - 1].y)
          wkPoint = new THREE.Vector3(closestPoint[0], closestPoint[1], closestPoint[2])
          if (wkPoint !== null) {
            this.addDrillToCal(wkPoint, endPoint, '补钻-' + uuidv4().replaceAll('-', ''))
          }
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
     * @param name
     */
    addDrillMesh(start, end, radius, material, list, name) {
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
        let calBottomPoint = new THREE.Mesh(new THREE.SphereGeometry(0.2), new THREE.MeshBasicMaterial({
          color: 0x0000ff//0xff0000设置材质颜色为红色
        }))
        calBottomPoint.position.set(sp.x, sp.y, sp.z)
        calBottomPoint.name = name
        this.addCoalBottomPointMeshList.push(calBottomPoint)
        scene.add(calBottomPoint)

        // 出煤点
        let calTopPoint = new THREE.Mesh(new THREE.SphereGeometry(0.2), new THREE.MeshBasicMaterial({
          color: 0xff0000//0xff0000设置材质颜色为红色
        }))
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
     * 添加钻孔范围实体并参与计算
     * @param start 起点
     * @param end 终点
     * @param radius 半径
     * @param material 材质
     * @param rootObject 参与计算的root
     * @param drillGroupList 钻孔范围Mesh加入的List
     * @param name
     */
    addDrillRangeMeshToCalc(start, end, radius, material, rootObject, drillGroupList, name) {
      let sp = start
      let ep = end

      let dis = Math.sqrt(((sp.x - ep.x) * (sp.x - ep.x)) + ((sp.y - ep.y) * (sp.y - ep.y)) + ((sp.z - ep.z) * (sp.z - ep.z)))

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
      drillGroup.name = name
      drillGroupList.push(drillGroup)
      rootObject.add(drillGroup)
      //group.add(drillGroup)
      //transformControls.attach(drillGroup)
    },

    /**
     * 补钻时, 添加抽采范围进去计算
     * @param startPoint
     * @param endPoint
     * @param name
     */
    addDrillToCal(startPoint, endPoint, name) {
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

        // 添加补钻钻孔,煤孔段
        this.addDrillMesh(sp1, ep, 0.2, addDrillMat, this.addDrillMeshList, name)
        this.addDrillMesh(sp, ep, 0.2, drillInCoalMat, this.addDrillInCoalMeshList, name)

        // 添加施工数据
        this.calDrillData(sp1, sp, ep, name)

        // 添加补孔钻孔范围,计算,场景
        this.addDrillRangeMeshToCalc(sp, ep, this.radius, addDrillRangeMat, partsRoot, this.addDrillGroupMeshList, name)
        this.addDrillRangeMeshToScene(sp, ep, this.radius, addDrillRangeMat, true, this.addDrillRangeToSceneList, name)
        if (this.clickPoint != null) {
          this.clickPoint.geometry.dispose()
          this.clickPoint.parent.remove(this.clickPoint)
          this.clickPoint = null
        }
      }
      this.needReRender = true
    },
    /**
     * 计算施工参数
     * @param workPoint
     * @param seeCoalPoint
     * @param overCoalPoint
     * @param name
     */
    calDrillData(workPoint, seeCoalPoint, overCoalPoint, name) {
      let addDrillData = {
        group: '',
        id: '',
        inclination: '',
        azimuth: '',
        disNav: '',
        disTop: '',
        disCenter: '',
        seeLen: '',
        overLen: '',
        type: 2
      }
      let disNav = this.calDistance(workPoint.x, workPoint.y, workPoint.z, this.baseData.navX, this.baseData.navY, this.baseData.navZ)
      let seeCoalLen = this.calDistance(workPoint.x, workPoint.y, workPoint.z, seeCoalPoint.x, seeCoalPoint.y, seeCoalPoint.z)
      let overCoalLen = this.calDistance(workPoint.x, workPoint.y, workPoint.z, overCoalPoint.x, overCoalPoint.y, overCoalPoint.z)
      let inclAng = this.rad2ang(Math.atan((seeCoalPoint.y - workPoint.y) / (this.calDistance(seeCoalPoint.x, 0, seeCoalPoint.z, workPoint.x, 0, workPoint.z))))
      //let azimAng = this.rad2ang(Math.atan(seeCoalPoint.x / seeCoalPoint.z))
      let azimAng = 90 - this.angleBetweenVectors([seeCoalPoint.x - workPoint.x, seeCoalPoint.y - workPoint.y, seeCoalPoint.z - workPoint.z],
        [this.workPoints[this.workPoints.length - 1].x - this.workPoints[0].x, this.workPoints[this.workPoints.length - 1].y - this.workPoints[0].y, this.workPoints[this.workPoints.length - 1].z - this.workPoints[0].z])
      addDrillData.group = 0
      addDrillData.id = this.addDrillDataNumber
      addDrillData.inclination = inclAng.toFixed(2)
      addDrillData.azimuth = azimAng.toFixed(2)
      addDrillData.disNav = disNav.toFixed(2)
      addDrillData.disTop = (5 - (2.5 * Math.sin(this.ang2rad(inclAng)))).toFixed(2)
      addDrillData.disCenter = (2.5 * Math.cos(this.ang2rad(inclAng))).toFixed(2)
      addDrillData.seeLen = seeCoalLen.toFixed(2)
      addDrillData.overLen = overCoalLen.toFixed(2)
      addDrillData.name = name
      this.addDrillingDataList.push(addDrillData)
      // 补钻计数+1
      this.addDrillDataNumber++
    },
    /**
     * 计算两向量夹角
     * @param vector1
     * @param vector2
     * @returns {number}
     */
    angleBetweenVectors(vector1, vector2) {
      let dotProduct = this.dotProduct(vector1, vector2)
      let magnitude1 = this.vectorMagnitude(vector1)
      let magnitude2 = this.vectorMagnitude(vector2)
      let angleInRadians = Math.acos(dotProduct / (magnitude1 * magnitude2))
      //let angleInDegrees = angleInRadians * (180 / Math.PI)
      return angleInRadians
    },
    vectorMagnitude(vector) {
      let sumOfSquares = 0
      for (let i = 0; i < vector.length; i++) {
        sumOfSquares += vector[i] * vector[i]
      }
      return Math.sqrt(sumOfSquares)
    },
    dotProduct(vector1, vector2) {
      let result = 0
      for (let i = 0; i < vector1.length; i++) {
        result += vector1[i] * vector2[i]
      }
      return result
    },

    /**
     * 手动补钻
     */
    addDrill() {
      //let sp = new THREE.Vector3(this.workPoints[this.startPointId].x, this.workPoints[this.startPointId].z, this.workPoints[this.startPointId].y)
      let sp = this.startPoint
      let ep = this.endPoint
      this.addDrillToCal(sp, ep, '补钻-' + uuidv4().replaceAll('-', ''))
    },
    /**
     * 添加点击事件, 射线捕捉获取点击物体
     * @param event
     */
    onClick(event) {
      //console.log(event)
      //event.preventDefault()
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

      if (params.drillDesMode) {
        // 钻孔设计模式
        // 选中空白带
        let empIntersects = raycaster.intersectObjects(partMeshs)
        // 选中施工点位
        let workIntersects = raycaster.intersectObjects(this.workPointsMeshList)

        if (workIntersects.length > 0) {
          let selected = workIntersects[0]
          this.startPoint = selected.object.position
        }

        if (empIntersects.length > 0) {
          if (this.clickPoint != null) {
            this.clickPoint.geometry.dispose()
            this.clickPoint.parent.remove(this.clickPoint)
            this.clickPoint = null
          }
          let selected = empIntersects[0]//取第一个物体
          this.endPoint = selected.point
          console.log('坐标:' + selected.point.x.toFixed(4) + ',' + selected.point.y.toFixed(4) + ',' + selected.point.z.toFixed(4))
          this.clickPoint = new THREE.Mesh(new THREE.SphereGeometry(0.5), new THREE.MeshBasicMaterial({
            color: 0xff0000//0xff0000设置材质颜色为红色
          }))
          this.clickPoint.position.set(selected.point.x, selected.point.y, selected.point.z)
          scene.add(this.clickPoint)

          if (this.addDrillForm.workPlace === 2 && this.endPoint != null) {
            this.startPoint = null
            let closestPoint = this.closestPoint(this.endPoint.x, this.endPoint.y, this.endPoint.z,
              this.workPoints[0].x, this.workPoints[0].z, this.workPoints[0].y,
              this.workPoints[this.workPoints.length - 1].x, this.workPoints[this.workPoints.length - 1].z, this.workPoints[this.workPoints.length - 1].y)
            this.startPoint = new THREE.Vector3(closestPoint[0], closestPoint[1], closestPoint[2])
          }
        }

        if (this.addDrillForm.drillDesMode && this.startPoint !== null && this.endPoint !== null) {
          this.addDrillLoading()
        }

      } else if (params.drillSelectMode) {
        // 钻孔点选模式
        let intersects = raycaster.intersectObjects(this.originDrillRangeToSceneList)
        let intersects2 = raycaster.intersectObjects(this.addDrillRangeToSceneList)
        if (intersects2 && intersects2.length > 0 && params.showAddDrillRange) {
          // 选中补钻
          if (this.clickPoint != null) {
            this.clickPoint.geometry.dispose()
            this.clickPoint.parent.remove(this.clickPoint)
            this.clickPoint = null
          }
          let selected = intersects2[0]//取第一个物体
          let indexName = selected.object.name
          //console.log('add', indexName)
          for (let i = 0; i < this.addDrillingDataList.length; i++) {
            if (this.addDrillingDataList[i].name === indexName) {
              this.selectedDrill = this.addDrillingDataList[i]
            }
          }
        } else if (intersects && intersects.length > 0 && params.showOriginDrillRange) {
          // 选中原始钻孔
          if (this.clickPoint != null) {
            this.clickPoint.geometry.dispose()
            this.clickPoint.parent.remove(this.clickPoint)
            this.clickPoint = null
          }
          let selected = intersects[0]
          let indexName = selected.object.name
          //console.log(intersects)
          //console.log('ori', indexName)
          for (let i = 0; i < this.drillingDataList.length; i++) {
            if (this.drillingDataList[i].name === indexName) {
              this.selectedDrill = this.drillingDataList[i]
            }
          }
        }/* else {
          // 初始化选中钻孔数据
          this.initSelectedDrill()
        } */
      } else {
        if (this.clickPoint != null) {
          this.clickPoint.geometry.dispose()
          this.clickPoint.parent.remove(this.clickPoint)
          this.clickPoint = null
        }
      }
    },
    initSelectedDrill() {
      this.selectedDrill = {
        group: '', id: '', inclination: '', azimuth: '', disNav: '', disTop: '', disCenter: '', seeLen: '', overLen: ''
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
      if (this.drillingDataList.length < 5) {
        this.$message.error('钻孔数据不足,无法操作!')
        return
      }
      this.loadingInstance = this.$loading({
        lock: true,
        text: '正在渲染中,请稍后...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      setTimeout(() => {
        this.stopLoading = true
        this.changePage(2)
        this.calModelData()
      }, 1000)
    },
    /**
     * 计算初始空白带
     */
    calInitResultLoading() {
      this.loadingInstance = Loading.service({
        lock: true,
        text: '正在计算中,请稍后...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      setTimeout(() => {
        this.changePage(3)
        this.stopLoading = true
        this.calInitResult()
      }, 1000)
    },
    cleanAll() {
      this.addDrillDataVisible = false
      this.delAddDrillVisible = false
      this.emptyDialogVisible = false
      this.addDGLlength = -1
      this.delDrill = false
      this.addDrillingDataList = []
      this.radius = 5.5
      this.emptyBandList = []
      this.showOutCube = false
      this.showCoalCube = false
      this.emptyObjPoints = []
      this.initEmptyBand = false
      this.needReRender = true

      for (let i = 0; i < this.workPointsNameMeshList.length; i++) {
        this.cleanCache(this.workPointsNameMeshList[i])
      }
      this.workPointsNameMeshList = []
      for (let i = 0; i < this.workPointsMeshList.length; i++) {
        this.cleanCache(this.workPointsMeshList[i])
      }
      this.workPointsMeshList = []
      for (let i = 0; i < this.originDrillMeshList.length; i++) {
        this.cleanCache(this.originDrillMeshList[i])
      }
      this.originDrillMeshList = []
      for (let i = 0; i < this.originDrillInCoalMeshList.length; i++) {
        this.cleanCache(this.originDrillInCoalMeshList[i])
      }
      this.originDrillInCoalMeshList = []
      for (let i = 0; i < this.originDrillGroupMeshList.length; i++) {
        this.cleanCache(this.originDrillGroupMeshList[i])
      }
      this.originDrillGroupMeshList = []
      for (let i = 0; i < this.originDrillRangeToSceneList.length; i++) {
        this.cleanCache(this.originDrillRangeToSceneList[i])
      }
      this.originDrillRangeToSceneList = []
      for (let i = 0; i < this.workPoints.length; i++) {
        this.cleanCache(this.workPoints[i])
      }
      this.workPoints = []
      for (let i = 0; i < this.addDrillMeshList.length; i++) {
        this.cleanCache(this.addDrillMeshList[i])
      }
      this.addDrillMeshList = []
      for (let i = 0; i < this.addDrillInCoalMeshList.length; i++) {
        this.cleanCache(this.addDrillInCoalMeshList[i])
      }
      this.addDrillInCoalMeshList = []
      for (let i = 0; i < this.addDrillGroupMeshList.length; i++) {
        this.cleanCache(this.addDrillGroupMeshList[i])
      }
      this.addDrillGroupMeshList = []
      for (let i = 0; i < this.addDrillRangeToSceneList.length; i++) {
        this.cleanCache(this.addDrillRangeToSceneList[i])
      }
      this.addDrillRangeToSceneList = []
      for (let i = 0; i < this.addCoalTopPointMeshList.length; i++) {
        this.cleanCache(this.addCoalTopPointMeshList[i])
      }
      this.addCoalTopPointMeshList = []
      for (let i = 0; i < this.addCoalBottomPointMeshList.length; i++) {
        this.cleanCache(this.addCoalBottomPointMeshList[i])
      }
      this.addCoalBottomPointMeshList = []
      initCSGEvaluator = null
      addCSGEvaluator = null
      this.cleanCache(initResult)
      initResult = null
      this.cleanCache(initRoot)
      initRoot = null
      this.cleanCache(partsResult)
      partsResult = null
      this.cleanCache(partsRoot)
      partsRoot = null
      this.cleanCache(rootPartsObject)
      rootPartsObject = null
      this.cleanCache(wireframeObject)
      wireframeObject = null
      this.cleanCache(coalCubeObj)
      coalCubeObj = null
      this.cleanCache(loadedObj)
      loadedObj = null
      this.cleanCache(coalBottomMesh)
      coalBottomMesh = null
      this.cleanCache(coalTopMesh)
      coalTopMesh = null
      this.cleanCache(roadMesh)
      roadMesh = null
      this.cleanCache(coalGeometry)
      for (let i = 0; i < partMeshs.length; i++) {
        this.cleanCache(partMeshs[i])
      }
      partMeshs = []
      for (let i = 0; i < partCubes.length; i++) {
        this.cleanCache(partCubes[i])
      }
      partCubes = []
    },
    /**
     * 根据施工数据计算见煤点止煤点数据
     */
    calModelData() {
      //this.cleanAll()
      let bd = this.baseData
      let base = 10
      if (this.needReCalculate) {
        this.cleanAll()
        this.bottomPointList = []
        this.topPointList = []
        this.renderNum = this.drillingDataList.length < this.inputNumber ? this.drillingDataList.length : this.inputNumber
        let tempGroupNum = -1;
        let startGroupNum = -1;
        let endGroupNum = -1;
        for (let i = 0; i < this.renderNum; i++) {

          this.drillingDataList[i].name = '实钻-' + i
          let dd = this.drillingDataList[i]
          if (i === 0) {
            startGroupNum = dd.group
          }
          if (i === this.renderNum - 1) {
            endGroupNum = dd.group
          }
          if (dd.azimuth <= 360 && dd.azimuth > 270) {
            let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(360 - dd.azimuth)))
            let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(360 - dd.azimuth)))
            let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
            this.bottomPointList.push([xm, ym, zm, dd.group, dd.name])
            let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(360 - dd.azimuth)))
            let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(360 - dd.azimuth)))
            let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
            this.topPointList.push([xn, yn, zn, dd.group, dd.name])
            //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
          } else if (dd.azimuth <= 270 && dd.azimuth > 180) {
            let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth - 180)))
            let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth - 180)))
            let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
            this.bottomPointList.push([xm, ym, zm, dd.group, dd.name])
            let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth - 180)))
            let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth - 180)))
            let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
            this.topPointList.push([xn, yn, zn, dd.group, dd.name])
            //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
          } else if (dd.azimuth <= 180 && dd.azimuth > 90) {
            let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(180 - dd.azimuth)))
            let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(180 - dd.azimuth)))
            let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
            this.bottomPointList.push([xm, ym, zm, dd.group, dd.name])
            let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(180 - dd.azimuth)))
            let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(180 - dd.azimuth)))
            let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
            this.topPointList.push([xn, yn, zn, dd.group, dd.name])
            //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
          } else if (dd.azimuth <= 90) {
            let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth)))
            let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth)))
            let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
            this.bottomPointList.push([xm, ym, zm, dd.group, dd.name])
            let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth)))
            let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth)))
            let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disTop + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
            this.topPointList.push([xn, yn, zn, dd.group, dd.name])
            //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
          }

          //console.log(this.bottomPointList[this.bottomPointList.length-1]);
          //console.log(this.topPointList[this.topPointList.length-1]);

          if (tempGroupNum !== dd.group) {
            // 当前组的起始点
            // 边界点
            if (this.bottomPointList.length - 2 > 0) {
              /*let tp2 = this.topPointList[this.topPointList.length - 2]
              console.log('bordert', tp2[0], tp2[1], tp2[2])*/
              let bp2 = this.bottomPointList[this.bottomPointList.length - 2]
              console.log('borderb', bp2[0], bp2[1], bp2[2], bp2[3], bp2[4])
            }
            if (this.bottomPointList.length - 1 > 0) {
              /*let tp1 = this.topPointList[this.topPointList.length - 1]
              console.log('bordert', tp1[0], tp1[1], tp1[2])*/
              let bp1 = this.bottomPointList[this.bottomPointList.length - 1]
              console.log('borderb', bp1[0], bp1[1], bp1[2], bp1[3], bp1[4])
            }
            tempGroupNum = dd.group
          }
        }

        /*for (let i = 0; i < this.topPointList.length; i++) {
          let tp = this.topPointList[i]
          if (tp[3] == startGroupNum) {
            console.log('startt', tp[0], tp[1], tp[2])
          }
          if (tp[3] == endGroupNum) {
            console.log('endt', tp[0], tp[1], tp[2])
          }
        }*/

        for (let i = 0; i < this.bottomPointList.length; i++) {
          let bp = this.bottomPointList[i]
          if (bp[3] == startGroupNum) {
            console.log('startb ', bp[0], bp[1], bp[2])
          }
          if (bp[3] == endGroupNum) {
            console.log('endb ', bp[0], bp[1], bp[2])
          }
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
      let _this = this
      let bd = _this.baseData
      let base = 10
      let thisGroup = -1
      for (let i = 0; i < this.renderNum; i++) {
        let obj = _this.drillingDataList[i]
        let have = false
        for (let j = 0; j < _this.workPoints.length; j++) {
          if (_this.workPoints[j].group === obj.group) {
            have = true
            break
          }
        }
        //if (thisGroup !== obj.group) {
        if (!have) {
          let drillPointX = bd.navX + ((base + (obj.group * 4)) * Math.sin(_this.ang2rad(bd.azimuth)))
          let drillPointY = bd.navY + ((base + (obj.group * 4)) * Math.cos(_this.ang2rad(bd.azimuth)))
          let drillPointZ = bd.navZ + ((base + (obj.group * 4)) * Math.tan(_this.ang2rad(bd.inclination)))
          _this.workPoints.push({group: obj.group, x: drillPointX, y: drillPointY, z: drillPointZ})
          thisGroup = obj.group
        }
      }

      // 创建施工点位模型
      for (let i = 0; i < this.workPoints.length; i++) {
        //workPoints3d.push(new THREE.Vector3(this.workPoints[i]['x'], this.workPoints[i]['z'], this.workPoints[i]['y']))
        let wkGeo = new THREE.BoxGeometry(0.5, 0.5, 0.5)
        let wkMater = new THREE.MeshBasicMaterial({color: 0xffffff})
        let wkMesh = new THREE.Mesh(wkGeo, wkMater)
        wkMesh.position.x = this.workPoints[i]['x']
        wkMesh.position.y = this.workPoints[i]['z']
        wkMesh.position.z = this.workPoints[i]['y']
        this.workPointsMeshList.push(wkMesh)

        // 施工点位名称
        //this.addCSS3DSpirit(wkMesh,this.workPoints[i]['group'] + '排')
        let WPNameMesh = this.addSpirit('第' + this.workPoints[i]['group'] + '排')
        WPNameMesh.position.x = wkMesh.position.x
        WPNameMesh.position.y = wkMesh.position.y - 1
        WPNameMesh.position.z = wkMesh.position.z
        this.workPointsNameMeshList.push(WPNameMesh)
        scene.add(WPNameMesh)
        scene.add(wkMesh)
      }
    },
    createTag(name) {
      const element = document.createElement('div')
      element.setAttribute('style', 'background-color:ffffff;width:10px')
      element.className = 'tag'
      element.innerHTML = name
      const tag = new CSS3DObject(element)
      tag.scale.set(1, 1, 1)
      return tag
    },
    /**
     * 初始化场景
     */
    initScene() {
      const bgColor = 0x111111
      textOutput = document.getElementById('output')
      outputContainer = document.getElementById('container')
      tGUIContainer = document.getElementById('tGUI')
      // 渲染初始化
      renderer = new THREE.WebGLRenderer({antialias: true})
      renderer.setPixelRatio(window.devicePixelRatio)
      console.log(window.innerHeight, window.innerWidth)
      let wid = 1640
      let height = 930
      if (window.innerWidth > 3000) {
        wid = 1920
        height = 1080
        renderer.setSize(1920, 1080)
      } else if (window.innerWidth > 2000) {
        wid = 2280
        height = 1154
      } else {
        wid = 1640
        height = 790
      }
      renderer.setSize(wid, height)
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
      camera = new THREE.PerspectiveCamera(75, wid / height, 0.1, 50)
      camera.position.set(-25, 25, -25).multiplyScalar(2)
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

      // 点击事件
      document.body.addEventListener('click', this.onClick, false)
    },

    /**
     * 计算默认空白带
     */
    calInitResult() {
      // 原始钻孔计算初始化
      initCSGEvaluator = new Evaluator()
      initCSGEvaluator.attributes = ['position', 'normal']
      initCSGEvaluator.useGroups = false

      // 补钻钻孔计算初始化
      addCSGEvaluator = new Evaluator()
      addCSGEvaluator.attributes = ['position', 'normal']
      addCSGEvaluator.useGroups = false

      // 优化空白带实体
      rootPartsObject = new THREE.Mesh(undefined, new THREE.MeshBasicMaterial({color: 0, wireframe: false}))
      rootPartsObject.material.color.set(0xffc400).convertSRGBToLinear().multiplyScalar(0.1)
      scene.add(rootPartsObject)

      initRoot = new Operation(coalGeometry, originEmptyBandMat)

      this.createDefaultOp(initRoot)

      for (let i = 0; i < this.points3DBottom.length; i++) {
        // 添加原始钻孔抽采范围, 并计算
        this.addDrillRangeMeshToCalc(this.points3DBottom[i], this.points3DTop[i], this.radius, originDrillRangeMat, initRoot, this.originDrillGroupMeshList, '实钻-' + i)
        this.addDrillRangeMeshToScene(this.points3DBottom[i], this.points3DTop[i], this.radius, originDrillRangeMat, false, this.originDrillRangeToSceneList, '实钻-' + i)
      }

      let startTime = window.performance.now()
      initResult = initCSGEvaluator.evaluateHierarchy(initRoot)
      scene.add(initResult)
      let deltaTime = window.performance.now() - startTime
      console.log('初次渲染时长:' + deltaTime)
      this.initEmptyBand = true
    },

    /**
     * 渲染
     */
    render() {
      requestAnimationFrame(this.render)

      // 可视化设置
      this.visibleSetting()

      if (partsResult) {
        this.cleanCache(partsResult)
      }

      if (this.initEmptyBand) {
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
          //console.log('计算计算:', partsRoot.children.length)
          partsResult.material = resultGridMat
          scene.add(partsResult)
          rootPartsObject.geometry = partsResult.geometry
          partsRoot.visible = params.showEmptyBand
          rootPartsObject.visible = params.showEmptyBand
        }
      }

      if (this.initEmptyBand) {
        // 已经完成空白带初始化, 才需要进行空白带分割渲染
        // 3种情况下,需要进行再次的分割渲染. 1.补钻 2.删除钻孔 3.显示或隐藏外部包围体
        //if (this.showOutCube !== params.showOutCube || deltaTime >= 10 || this.autoAdd !== false || this.delDrill) {
        if (this.needReRender) {
          // 重新分割,渲染
          this.showOutCube = params.showOutCube
          rootPartsObject.geometry.computeBoundingBox()

          // 清除实体
          if (partMeshs.length > 0) {
            for (let i = 0; i < partMeshs.length; i++) {
              this.cleanCache(partMeshs[i])
            }
          }
          // 清除partCube实体
          if (partCubes.length > 0) {
            for (let i = 0; i < partCubes.length; i++) {
              this.cleanCache(partCubes[i])
            }
          }

          partMeshs = []
          partCubes = []
          this.emptyBandList = []
          // 分割不相连的部分
          let ppparts = this.calcParts(rootPartsObject)
          // 处理空白带, 获得分块儿数量
          let nIndex = this.handleEmptyBand(ppparts)

          if (this.autoAdd === true) {
            if (nIndex > 0 && this.autoIndex < 5) {
              // 开始自动补钻, 仍有空白, 轮数<5轮
              console.log('自动补钻循环次数:' + this.autoIndex)
              this.autoAddDrill()
              this.autoIndex++
              this.stopLoading = false
            } else {
              // 自动补钻结束了
              this.autoAdd = false
              this.autoIndex = 1
              this.stopLoading = true
            }
          }

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

          if (this.stopLoading) {
            this.loadingInstance.close()

            this.endPoint = null
            this.startPoint = null

            this.stopLoading = false
          }

          /* if (this.stopLoading && this.loadingInstance !== null && this.autoAdd === false) {
            this.loadingInstance.close()
            this.stopLoading = false
            this.delDrill = false
            this.endPoint = null
            this.startPoint = null
            console.log('结束Loading')
          } */
        } else {
          if (this.stopLoading) {
            this.loadingInstance.close()
            this.stopLoading = false
          }
        }

        for (let i = 0; i < partMeshs.length; i++) {
          partMeshs[i].visible = params.showEmptyBand
          partCubes[i].visible = params.showOutCube
        }
        if (!this.autoAdd) {
          // 如果不是仍在自动补钻中, 结束重渲染
          this.needReRender = false
        }
      } else {
        if (this.stopLoading) {
          this.loadingInstance.close()
          this.stopLoading = false
        }
      }

      transformControls.enabled = params.displayControls
      transformControls.visible = params.displayControls
      renderer.render(scene, camera)
    },

    /**
     * 处理空白带
     * @param ppparts
     * @returns {number}
     */
    handleEmptyBand(ppparts) {
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
              uuid: partMesh.uuid.substring(0, 5),
              position: '(' + helper.position.x.toFixed(1) + ',' + helper.position.y.toFixed(1) + ',' + helper.position.z.toFixed(1) + ')',
              volume: Number((Number(vol.toFixed(2)) + 0.01).toFixed(2))
            })
            nIndex++
          }

        } else {
          //console.log('完全无效实体' + i)
        }
      }
      return nIndex
    },

    /**
     * 模型可视化控制
     */
    visibleSetting() {
      coalBottomMesh.visible = params.showCoalBottomMesh
      coalTopMesh.visible = params.showCoalTopMesh

      // 原始钻孔
      if (params.showOriginDrill) {
        for (let i = 0; i < this.originDrillMeshList.length; i++) {
          this.originDrillMeshList[i].visible = true
        }
      } else {
        for (let i = 0; i < this.originDrillMeshList.length; i++) {
          this.originDrillMeshList[i].visible = false
        }
      }

      // 显示补钻钻孔
      if (params.showAddDrill) {
        for (let i = 0; i < this.addDrillMeshList.length; i++) {
          this.addDrillMeshList[i].visible = true
        }
      } else {
        for (let i = 0; i < this.addDrillMeshList.length; i++) {
          this.addDrillMeshList[i].visible = false
        }
      }

      // 原始煤孔段
      if (params.showOriginDrillInCoal) {
        for (let i = 0; i < this.originDrillInCoalMeshList.length; i++) {
          this.originDrillInCoalMeshList[i].visible = true
        }
        /* drillInCoalMat.transparent = false
        drillInCoalMat.opacity = 1 */
      } else {
        for (let i = 0; i < this.originDrillInCoalMeshList.length; i++) {
          this.originDrillInCoalMeshList[i].visible = false
        }
      }

      // 新增煤孔段
      if (params.showAddDrillInCoal) {
        for (let i = 0; i < this.addDrillInCoalMeshList.length; i++) {
          this.addDrillInCoalMeshList[i].visible = true
        }
      } else {
        for (let i = 0; i < this.addDrillInCoalMeshList.length; i++) {
          this.addDrillInCoalMeshList[i].visible = false
        }
      }

      // 煤层体
      if (params.showCoalCube) {
        coalCubeMat.opacity = params.coalCubeOpacity
      } else {
        coalCubeMat.opacity = 0
      }
      coalCubeObj.visible = params.showCoalCube
    },

    /**
     * 清除缓存
     * @param obj
     */
    cleanCache(obj) {
      if (obj) {
        if (obj.geometry) {
          obj.geometry.dispose()
        }
        if (obj.material) {
          obj.material.dispose()
        }
        if (obj.parent) {
          obj.parent.remove(obj)
        }
      }
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
      this.points3DBottom = []
      this.points3DTop = []
      for (let i = 0; i < this.renderNum; i++) {
        this.points3DBottom.push(new THREE.Vector3(this.bottomPointList[i][0], this.bottomPointList[i][2], this.bottomPointList[i][1]))
        pointsAll.push(new THREE.Vector3(this.bottomPointList[i][0], this.bottomPointList[i][2], this.bottomPointList[i][1]))
      }
      for (let i = 0; i < this.renderNum; i++) {
        this.points3DTop.push(new THREE.Vector3(this.topPointList[i][0], this.topPointList[i][2], this.topPointList[i][1]))
        pointsAll.push(new THREE.Vector3(this.topPointList[i][0], this.topPointList[i][2], this.topPointList[i][1]))
      }
      let botts = {}
      let xyPoints = []
      let edges = []
      for (let i = 0; i < this.bottomPointList.length; i++) {
        xyPoints.push([this.bottomPointList[i][0], this.bottomPointList[i][1]])
        let g = this.bottomPointList[i][3]
        if (!botts[g]) {
          botts[g] = []
          botts[g].push(this.bottomPointList[i])
        } else {
          botts[g].push(this.bottomPointList[i])
        }
      }
      console.log("botts:", botts)
      let gs = Object.keys(botts)
      let sgs = gs.sort((a, b) => {
        return (a - b)
      })
      console.log(sgs)
      let startg = -1
      let endg = -1
      let startLine = []
      let endLine = []
      let upLine = []
      let underLine = []
      for (let i = 0; i < sgs.length; i++) {
        if (i === 0) {
          startg = Number(sgs[i])
          startLine = botts[startg]
        } else if (i === (sgs.length - 1)) {
          endg = Number(sgs[i])
          endLine = botts[endg]
        } else {
          let line = botts[Number(sgs[i])]
          underLine.push(line[0])
          if (line.length - 1 > 0) {
            upLine.push(line[line.length - 1])
          }
        }
      }
      // 最下边一行和最后一行要反转一下
      underLine = underLine.reverse()
      endLine = endLine.reverse()
      console.log("startLine:", startLine)
      console.log("upLine:", upLine)
      console.log("endLine:", endLine)
      console.log("underLine:", underLine)
      let finalBorderList = [...startLine, ...upLine, ...endLine, ...underLine]
      for (let i = 0; i < finalBorderList.length; i++) {
        if (i < finalBorderList.length - 2) {
          edges.push([Number(finalBorderList[i][4].replace("实钻-", "")), Number(finalBorderList[i + 1][4].replace("实钻-", ""))])
        } else {
          edges.push([Number(finalBorderList[i][4].replace("实钻-", "")), Number(finalBorderList[0][4].replace("实钻-", ""))])
        }
      }
      console.log("finalBorderList:", finalBorderList)
      console.log("xyPoints:", xyPoints)
      console.log("edges:", edges)
      let cdtTri = cdt2d(xyPoints, edges, {exterior: false})
      console.log("cdt2d:", cdtTri)


      let allIndex = []
      // 底面
      for (let i = 0; i < cdtTri.length; i++) {
        allIndex.push(cdtTri[i][0])
        allIndex.push(cdtTri[i][1])
        allIndex.push(cdtTri[i][2])
      }

      // 顶面
      for (let i = 0; i < cdtTri.length; i++) {
        allIndex.push(cdtTri[i][0] + this.bottomPointList.length)
        allIndex.push(cdtTri[i][2] + this.bottomPointList.length)
        allIndex.push(cdtTri[i][1] + this.bottomPointList.length)
      }

      // 构建边界面
      for (let i = 0; i < finalBorderList.length; i++) {
        if (i === 0) {
          allIndex.push(Number(finalBorderList[0][4].replace("实钻-", "")))
          allIndex.push(Number(finalBorderList[0][4].replace("实钻-", "")) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[1][4].replace("实钻-", "")))

          allIndex.push(Number(finalBorderList[0][4].replace("实钻-", "")))
          allIndex.push(Number(finalBorderList[finalBorderList.length - 2][4].replace("实钻-", "")) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[0][4].replace("实钻-", "")) + this.bottomPointList.length)
        } else if (i === finalBorderList.length - 1) {
          allIndex.push(Number(finalBorderList[i][4].replace("实钻-", "")))
          allIndex.push(Number(finalBorderList[i][4].replace("实钻-", "")) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[0][4].replace("实钻-", "")))

          allIndex.push(Number(finalBorderList[i][4].replace("实钻-", "")))
          allIndex.push(Number(finalBorderList[i - 1][4].replace("实钻-", "")) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[i][4].replace("实钻-", "")) + this.bottomPointList.length)
        } else {
          allIndex.push(Number(finalBorderList[i][4].replace("实钻-", "")))
          allIndex.push(Number(finalBorderList[i][4].replace("实钻-", "")) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[i + 1][4].replace("实钻-", "")))

          allIndex.push(Number(finalBorderList[i][4].replace("实钻-", "")))
          allIndex.push(Number(finalBorderList[i - 1][4].replace("实钻-", "")) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[i][4].replace("实钻-", "")) + this.bottomPointList.length)
        }
      }


      // 添加上下钻孔点位
      let coalBottomGeom = new THREE.BufferGeometry().setFromPoints(this.points3DBottom)
      let coalTopGeom = new THREE.BufferGeometry().setFromPoints(this.points3DTop)
      let bottomCloud = new THREE.Points(
        coalBottomGeom,
        new THREE.PointsMaterial({color: 0x99ccff, size: 0.5})
      )
      let topCloud = new THREE.Points(
        coalTopGeom,
        new THREE.PointsMaterial({color: 0x99cc00, size: 0.5})
      )
      /* scene.add(cloud1)
      scene.add(cloud2) */
      let pointGeo = new THREE.SphereGeometry(1)
      let pointMat = brushMat.clone()
      pointMat.color.set(0xffffff).convertSRGBToLinear()
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

      console.log("indexDelaunay1:", indexDelaunay1)

      let indexDelaunay2 = Delaunator.from(
        this.points3DTop.map(v => {
          return [v.x, v.z]
        })
      )
      console.log("indexDelaunay2:", indexDelaunay2)

      /*let meshIndex1 = [] // delaunay index => three.js index
      for (let i = 0; i < indexDelaunay1.triangles.length; i++) {
        meshIndex1.push(indexDelaunay1.triangles[i])
      }

      let meshIndex2 = [] // delaunay index => three.js index
      for (let i = 0; i < indexDelaunay2.triangles.length; i++) {
        meshIndex2.push(indexDelaunay2.triangles[i])
      }*/

      let meshIndex1 = []
      for (let i = 0; i < cdtTri.length; i++) {
        meshIndex1.push(cdtTri[i][0])
        meshIndex1.push(cdtTri[i][1])
        meshIndex1.push(cdtTri[i][2])
      }
      coalBottomGeom.setIndex(meshIndex1) // add three.js index to the existing geometry
      coalBottomGeom.computeVertexNormals()
      coalBottomMesh = new THREE.Mesh(
        coalBottomGeom, // re-use the existing geometry
        coalBottomMeshMat
      )
      scene.add(coalBottomMesh)

      //coalTopGeom.setIndex(meshIndex2)
      // 上下两煤层面用同一个三角网结构
      coalTopGeom.setIndex(meshIndex1)
      coalTopGeom.computeVertexNormals()
      coalTopMesh = new THREE.Mesh(
        coalTopGeom, // re-use the existing geometry
        coalTopMeshMat
      )
      scene.add(coalTopMesh)

      let cPointsAll = [...this.points3DBottom, ...this.points3DTop]
      coalGeometry = new THREE.BufferGeometry().setFromPoints(cPointsAll)
      coalGeometry.setIndex(allIndex)
      coalGeometry.computeVertexNormals()
      console.log("coalAllGeom:", coalGeometry)
      coalCubeObj = new THREE.Mesh(
        coalGeometry,
        coalCubeMat
      )
      scene.add(coalCubeObj)

      // 煤层实体
      /*coalGeometry = new ConvexGeometry(pointsAll)
      console.log("coalGeometry:", coalGeometry)
      coalCubeObj = new Mesh(coalGeometry, coalCubeMat)
      scene.add(coalCubeObj)*/
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
        let wkMater = new THREE.MeshBasicMaterial({color: 0xffffff})
        let wkMesh = new THREE.Mesh(wkGeo, wkMater)
        wkMesh.position.x = this.workPoints[i]['x']
        wkMesh.position.y = this.workPoints[i]['z']
        wkMesh.position.z = this.workPoints[i]['y']
        this.workPointsMeshList.push(wkMesh)
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

      roadMesh = new THREE.Mesh(roadGeometry, roadMat)
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
      if (!gui) {
        gui = new GUI({width: 250, autoPlace: false})
        //tGUIContainer.append(gui.domElement)
        let originDG = gui.addFolder('原始钻孔')
        originDG.add(params, 'showOriginDrill').name('显示原始钻孔')
        originDG.add(params, 'showOriginDrillRange').name('显示原始钻孔抽采范围')
        originDG.add(params, 'oriDRMOpacity').min(0).max(1).step(0.1).name('抽采范围透明度')
        originDG.add(params, 'showOriginDrillInCoal').name('显示原始钻孔煤孔段')
        let addDG = gui.addFolder('补钻钻孔')
        addDG.add(params, 'showAddDrill').name('显示补钻钻孔')
        addDG.add(params, 'showAddDrillRange').name('显示补钻钻孔抽采范围')
        addDG.add(params, 'addDRMOpacity').min(0).max(1).step(0.1).name('抽采范围透明度')
        addDG.add(params, 'showAddDrillInCoal').name('显示补钻钻孔煤孔段')
        /* addDG.add(params, 'drillDesMode').name('开启手动补钻模式') */
        let coalG = gui.addFolder('煤层信息')
        coalG.add(params, 'showCoalCube').name('显示原始煤层信息')
        coalG.add(params, 'coalCubeOpacity').min(0).max(1).step(0.1).name('煤层透明度')
        coalG.add(params, 'showCoalMesh').name('显示煤层顶底板网格')
        let emptyBandG = gui.addFolder('空白带信息')
        emptyBandG.add(params, 'showOriginEmptyBand').name('显示原始空白带实体')
        emptyBandG.add(params, 'showOutCube').name('显示空白带外部包围体')
        emptyBandG.add(params, 'showEmptyBand').name('显示空白带实体')

        //gui.add(params, 'wireframe').name('显示线框')
        /* gui.add(params, 'snap') */
        /* gui.add(params, 'displayControls').name('显示钻孔控制组件') */
        /* gui.add(params, 'transparentBrushes').name('钻孔透明显示') */
        gui.add(params, 'drillSelectMode').name('钻孔选择模式')

        window.addEventListener('resize', function () {

          camera.aspect = window.innerWidth / window.innerHeight
          camera.updateProjectionMatrix()

          renderer.setSize(window.innerWidth, window.innerHeight)

        }, false)

        window.addEventListener('keydown', function (e) {
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
      }
    },
    /**
     * 添加原始钻孔实体
     */
    createOriginDrill() {
      console.log('-------------------------底板点位-------------------------------')
      // 添加原始钻孔
      for (let i = 0; i < this.topPointList.length; i++) {
        let group = this.topPointList[i][3]
        let wp
        for (let j = 0; j < this.workPoints.length; j++) {
          if (this.workPoints[j].group === group) {
            wp = this.workPoints[j]
            break
          }
        }
        let sp = new THREE.Vector3(0, 0, 0)
        if (wp) {
          sp = new THREE.Vector3(wp.x, wp.z, wp.y)
        }
        let ep0 = new THREE.Vector3(this.bottomPointList[i][0], this.bottomPointList[i][2], this.bottomPointList[i][1])
        let ep1 = new THREE.Vector3(this.topPointList[i][0], this.topPointList[i][2], this.topPointList[i][1])
        // 单双排使用不同材质
        if (group % 2 === 0) {
          this.addDrillMesh(sp, ep0, 0.2, originDrillMatD, this.originDrillMeshList, this.bottomPointList[i][4])
        } else {
          this.addDrillMesh(sp, ep0, 0.2, originDrillMatS, this.originDrillMeshList, this.bottomPointList[i][4])
        }
        // 煤孔段
        this.addDrillMesh(ep0, ep1, 0.2, drillInCoalMat, this.originDrillInCoalMeshList, this.bottomPointList[i][4])
        // 底板
        //console.log(ep0.x, ep0.y, ep0.z)
        // 顶板
        //console.log(ep1.x, ep1.y, ep1.z)
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
      let indexArr = []
      for (let i = 0; i < rootObj.children.length; i++) {
        if (rootObj.children[i].name === 'defaultOp') {
          indexArr.push(i)
        }
      }
      for (let i = 0; i < indexArr.length; i++) {
        rootObj.children.splice(indexArr[i], 1)
      }
      defaultGroup.name = 'defaultOp'
      rootObj.add(defaultGroup)
    },

    /**
     * 外部发光
     * @param selectedObjects
     */
    outlineObj(selectedObjects) {
      // 创建一个EffectComposer（效果组合器）对象，然后在该对象上添加后期处理通道。
      composer = new EffectComposer(renderer)
      // 新建一个场景通道  为了覆盖到原理来的场景上
      renderPass = new RenderPass(scene, camera)
      composer.addPass(renderPass)
      // 物体边缘发光通道
      outlinePass = new OutlinePass(new THREE.Vector2(window.innerWidth, window.innerHeight), scene, camera, selectedObjects)
      outlinePass.selectedObjects = selectedObjects
      outlinePass.edgeStrength = 10.0 // 边框的亮度
      outlinePass.edgeGlow = 1// 光晕[0,1]
      outlinePass.usePatternTexture = false // 是否使用父级的材质
      outlinePass.edgeThickness = 1.0 // 边框宽度
      outlinePass.downSampleRatio = 1 // 边框弯曲度
      outlinePass.pulsePeriod = 5 // 呼吸闪烁的速度
      outlinePass.visibleEdgeColor.set(parseInt(0xffffff)) // 呼吸显示的颜色
      outlinePass.hiddenEdgeColor = new THREE.Color(0, 0, 0) // 呼吸消失的颜色
      outlinePass.clear = true
      composer.renderTarget1.texture.encoding = THREE.sRGBEncoding
      composer.renderTarget2.texture.encoding = THREE.sRGBEncoding
      composer.addPass(outlinePass)
      // 自定义的着色器通道 作为参数
      let effectFXAA = new ShaderPass(FXAAShader)
      effectFXAA.uniforms.resolution.value.set(1 / window.innerWidth, 1 / window.innerHeight)
      effectFXAA.renderToScreen = true
      composer.addPass(effectFXAA)
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
      brushMat.opacity = 0.3
      brushMat.transparent = true
      brushMat.depthWrite = false
      brushMat.color.set(0xffc400).convertSRGBToLinear()

      // 原始钻孔材质
      originDrillMat = brushMat.clone()
      originDrillMat.transparent = true
      originDrillMat.opacity = 0.5
      originDrillMat.color.set(0xffc400).convertSRGBToLinear()

      // 原始钻孔材质
      originDrillMatS = brushMat.clone()
      originDrillMatS.transparent = true
      originDrillMatS.opacity = 1
      originDrillMatS.color.set(0xffc4ff).convertSRGBToLinear()
      originDrillMatD = brushMat.clone()
      originDrillMatD.transparent = true
      originDrillMatD.opacity = 1
      originDrillMatD.color.set(0xffc400).convertSRGBToLinear()
      // 选中钻孔材质
      tempSelectedMat = brushMat.clone()
      tempSelectedMat.transparent = true
      tempSelectedMat.opacity = 1
      tempSelectedMat.color.set(0xff00ff).convertSRGBToLinear()

      // 原始钻孔抽采范围材质
      originDrillRangeMat = brushMat.clone()
      originDrillRangeMat.transparent = true
      originDrillRangeMat.opacity = 0.3
      originDrillRangeMat.color.set(0xffc400).convertSRGBToLinear()

      // 单双排钻孔抽采范围材质
      originDrillRangeMatS = brushMat.clone()
      originDrillRangeMatS.transparent = true
      originDrillRangeMatS.opacity = params.oriDRMOpacity
      originDrillRangeMatS.color.set(0xffc4ff).convertSRGBToLinear()

      originDrillRangeMatD = brushMat.clone()
      originDrillRangeMatD.transparent = true
      originDrillRangeMatD.opacity = params.oriDRMOpacity
      originDrillRangeMatD.color.set(0xffc400).convertSRGBToLinear()

      // 补孔钻孔材质
      addDrillMat = brushMat.clone()
      addDrillMat.transparent = true
      addDrillMat.opacity = 0.5
      addDrillMat.color.set(0x00f0f0).convertSRGBToLinear()

      // 补孔抽采范围材质
      addDrillRangeMat = brushMat.clone()
      addDrillRangeMat.transparent = true
      originDrillRangeMatS.opacity = params.addDRMOpacity
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
      drillInCoalMat.color.set(0xa0c69d).convertSRGBToLinear()

      // 煤层顶底板点位材质
      coalTopMeshMat = new THREE.MeshLambertMaterial({color: 0xff0000})
      //coalTopMeshMat.wireframe = true
      coalTopMeshMat.side = THREE.DoubleSide
      coalTopMeshMat.depthWrite = true
      coalTopMeshMat.transparent = false
      coalTopMeshMat.opacity = 1

      coalBottomMeshMat = new THREE.MeshLambertMaterial({color: 0x00ff00})
      //coalBottomMeshMat.wireframe = true
      coalBottomMeshMat.side = THREE.DoubleSide
      coalBottomMeshMat.depthWrite = true
      coalBottomMeshMat.transparent = false
      coalBottomMeshMat.opacity = 1

      // 原始空白带材质
      originEmptyBandMat = brushMat.clone()
      originEmptyBandMat.transparent = true
      originEmptyBandMat.opacity = 0
      originEmptyBandMat.depthWrite = true
      originEmptyBandMat.polygonOffsetFactor = 1
      originEmptyBandMat.polygonOffsetUnits = 1
      originEmptyBandMat.color.set(0xffffff).convertSRGBToLinear()

      // 煤层块
      /* coalCubeMat = brushMat.clone()
      coalCubeMat.transparent = true
      coalCubeMat.opacity = 0.2
      coalCubeMat.polygonOffset = false
      coalCubeMat.polygonOffsetFactor = 0
      coalCubeMat.polygonOffsetUnits = 0 */
      coalCubeMat = new THREE.MeshPhongMaterial()
      //coalCubeMat.side = THREE.DoubleSide
      coalCubeMat.transparent = false
      coalCubeMat.opacity = 1
      roadMat.color.set(0x718c77).convertSRGBToLinear()
    }
  }
}
</script>

<style>
.inner-container {
  padding: 5px 20px;
}

.el-collapse-item__header {
  background-color: rgb(0, 175, 255);;
}

.el-collapse-item__content {
  padding-bottom: 0;
}
</style>
