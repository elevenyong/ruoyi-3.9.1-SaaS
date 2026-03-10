<template>
  <div class="app-container">
    <div v-show="showzbd" class="tablediv">
      <el-row :gutter="20">
        <el-col :span="7" :xs="7">
          <el-collapse v-model="activeNames">
            <el-collapse-item title="验证目标" name="1">
              1. 单排钻孔设计在地质体起伏较大位置，对空白带产生的影响
              <br>
              2. 成组钻孔设计在地质体起伏较大位置，对空白带产生的影响
              <br>
              3. 对排间距加入冗余系数，对空白带产生的影响
            </el-collapse-item>
            <el-collapse-item title="煤层坐标信息录入" name="2">
              <el-card>
                <!--<div slot="header" class="clearfix">
                  <span>煤层坐标信息录入</span>
                </div>-->
                <div>
                  <el-button style="margin:0 5px;float:right" type="primary" size="mini"
                             @click="()=>{this.coalPoiShow = true;this.aoeCoalPoi = {'coalinfoId':this.currentCoal.id};this.coalPoiTitle = '新增煤层点'}">
                    新增煤层点
                  </el-button>
                  <el-upload
                    style="margin:0 5px;float:right"
                    ref="upload"
                    action="xlsx.html"
                    :auto-upload="false"
                    :on-change="importPoints"
                    :show-file-list="false"
                  >
                    <el-button style="margin:0 5px;float:right" type="primary" size="mini" icon="el-icon-upload2">
                      导入坐标点
                    </el-button>
                  </el-upload>
                  <el-button style="margin:0 5px" type="primary" size="mini"
                             @click="handleShowCoal3D">
                    三维展示煤层
                  </el-button>
                  <el-table v-loading="false" :data="coalPointInfoList" height="500">
                    <!--<el-table-column
                        type="index"
                      >
                      </el-table-column>-->
                    <el-table-column label="巷道id" align="center" prop="roadwayid"/>
                    <el-table-column label="煤层点对id" type="index" align="center" width="100"/>
                    <el-table-column label="底板x" align="center" key="bottomX" prop="bottomX"/>
                    <el-table-column label="底板y" align="center" key="bottomY" prop="bottomY"/>
                    <el-table-column label="底板z" align="center" key="bottomZ" prop="bottomZ"/>
                    <el-table-column label="顶板x" align="center" key="topX" prop="topX"/>
                    <el-table-column label="顶板y" align="center" key="topY" prop="topY"/>
                    <el-table-column label="顶板z" align="center" key="topZ" prop="topZ"/>
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
            </el-collapse-item>
            <el-collapse-item title="设计信息录入" name="3">
              <el-form :inline="true">
                <el-form-item label="钻孔起点X位置" prop="currentPositionX">
                  <el-input
                    v-model="currentPositionX"
                    style="width: 200px"
                  >
                    <template slot="append">m</template>
                  </el-input>
                  <el-button type="text" @click="calcDesParam(0,0)">计算设计参数</el-button>
                </el-form-item>
                <br>
                <el-form-item label="恒定排间距" prop="equalLineSpace">
                  <el-switch
                    v-model="equalLineSpace"
                    active-color="#13ce66"
                    inactive-color="#ff4949">
                  </el-switch>
                </el-form-item>
                <el-form-item label="三花布孔选择较厚位置" prop="thickerPosition">
                  <el-switch
                    v-model="thickerPosition"
                    active-color="#13ce66"
                    inactive-color="#ff4949">
                  </el-switch>
                </el-form-item>
                <br>
                <el-form-item label="设计参数" prop="currentPosition">
                  <br>
                  煤层厚度：{{ queryParams.hc }}m，
                  煤层倾角：{{ queryParams.alf }}°<br>
                  当前点煤层顶板走向倾角：{{ currentSlope }}°，
                  原始方形布孔排间距：{{ queryParams.lw4 }}m<br>
                  原始三花布孔排间距：{{ queryParams.lw3 }}m，
                  投影排间距：{{ currentLw }}m
                </el-form-item>
                <br>
                <el-form-item label="钻孔设计" prop="drillDes">
                  <el-button type="primary" @click="()=>{this.singleDesShow = true}">单排钻孔设计</el-button>
                </el-form-item>
              </el-form>
            </el-collapse-item>
            <el-collapse-item title="设计钻孔导入" name="4">
              <el-card>
                <div>
                  <el-button style="margin:0 5px;float:right" type="primary" size="mini"
                             @click="()=>{this.coalPoiShow = true;this.aoeCoalPoi = {'coalinfoId':this.currentCoal.id};this.coalPoiTitle = '新增煤层点'}">
                    新增钻孔
                  </el-button>
                  <el-upload
                    style="margin:0 5px;float:right"
                    ref="upload"
                    action="xlsx.html"
                    :auto-upload="false"
                    :on-change="importDrill"
                    :show-file-list="false"
                  >
                    <el-button style="margin:0 5px;float:right" type="primary" size="mini" icon="el-icon-upload2">
                      导入钻孔
                    </el-button>
                  </el-upload>
                  <el-button style="margin:0 5px" type="primary" size="mini"
                             @click="handleShowCoal3D">
                    钻孔上图
                  </el-button>
                  <el-table v-loading="false" :data="allImportDrillInfoList" height="500">
                    <el-table-column label="列号" align="center" prop="realGroup"/>
                    <el-table-column label="序号" prop="id" align="center" width="100"/>
                    <el-table-column label="钻孔倾角" align="center" key="inclination" prop="inclination"/>
                    <el-table-column label="钻孔方位角" align="center" key="azimuth" prop="azimuth"/>
                    <!--<el-table-column label="x距离" align="center" key="bottomZ" prop="bottomZ"/>-->
                    <el-table-column label="见煤深度" align="center" key="seeLen" prop="seeLen"/>
                    <el-table-column label="穿煤长度" align="center" key="throughLen" prop="throughLen"/>
                    <el-table-column label="距离基线高度" align="center" key="disBase" prop="disBase"/>
                    <el-table-column label="据中心线距离" align="center" key="disCenter" prop="disCenter"/>
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
            </el-collapse-item>
          </el-collapse>
        </el-col>
        <el-col :span="14" :xs="14">
          <div id="container"></div>
        </el-col>
      </el-row>
    </div>
    <el-dialog title="钻孔数据导入" :visible.sync="importDrillShow" width="40%">
      <el-form :inline="true">
        <el-form-item label="本排钻孔起点X位置" prop="currentPositionX">
          <el-input
            v-model="currentPositionX"
            style="width: 200px"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="排间距" prop="currentPositionX">
          <el-input
            v-model="setLineWidth"
            style="width: 200px"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="自动计算下一排位置" prop="currentPositionX">
          <el-switch
            v-model="autoUpdateX"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" style="margin-left: 20px" @click="addDrillToScene">钻孔上图</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="false" :data="importDrillInfoList" height="500">
        <el-table-column label="列号" align="center" prop="realGroup"/>
        <el-table-column label="序号" prop="id" align="center" width="100"/>
        <el-table-column label="钻孔倾角" align="center" key="inclination" prop="inclination"/>
        <el-table-column label="钻孔方位角" align="center" key="azimuth" prop="azimuth"/>
        <!--<el-table-column label="x距离" align="center" key="bottomZ" prop="bottomZ"/>-->
        <el-table-column label="钻孔长度" align="center" key="totalLen" prop="totalLen"/>
        <el-table-column label="见煤深度" align="center" key="seeLen" prop="seeLen"/>
        <el-table-column label="穿煤长度" align="center" key="throughLen" prop="throughLen"/>
        <el-table-column label="距离基线高度" align="center" key="disBase" prop="disBase"/>
        <el-table-column label="据中心线距离" align="center" key="disCenter" prop="disCenter"/>
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
    </el-dialog>
    <!--  新增|编辑坐标点信息  -->
    <el-dialog :title="coalPoiTitle" :visible.sync="coalPoiShow" width="40%">
      <el-form ref="coalPoiDialog" :model="aoeCoalPoi" label-width="80px">
        <el-form-item label="底板x" prop="bottomX">
          <el-input v-model="aoeCoalPoi.bottomX"
                    @blur="aoeCoalPoi.bottomX = Number(aoeCoalPoi.bottomX)"
                    placeholder="底板坐标x">
          </el-input>
        </el-form-item>
        <el-form-item label="底板y" prop="bottomY">
          <el-input v-model="aoeCoalPoi.bottomY"
                    @blur="aoeCoalPoi.bottomY = Number(aoeCoalPoi.bottomY)"
                    placeholder="底板坐标y">
          </el-input>
        </el-form-item>
        <el-form-item label="底板z" prop="bottomZ">
          <el-input v-model="aoeCoalPoi.bottomZ"
                    @blur="aoeCoalPoi.bottomZ = Number(aoeCoalPoi.bottomZ)"
                    placeholder="底板坐标z">
          </el-input>
        </el-form-item>
        <br>
        <el-form-item label="顶板x" prop="topX">
          <el-input v-model="aoeCoalPoi.topX"
                    @blur="aoeCoalPoi.topX = Number(aoeCoalPoi.topX)"
                    placeholder="顶板坐标x">
          </el-input>
        </el-form-item>
        <el-form-item label="顶板y" prop="topY">
          <el-input v-model="aoeCoalPoi.topY"
                    @blur="aoeCoalPoi.topY = Number(aoeCoalPoi.topY)"
                    placeholder="顶板坐标y">
          </el-input>
        </el-form-item>
        <el-form-item label="顶板z" prop="topZ">
          <el-input v-model="aoeCoalPoi.topZ"
                    @blur="aoeCoalPoi.topZ = Number(aoeCoalPoi.topZ)"
                    placeholder="顶板坐标z">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCoalPoiForm">确 定</el-button>
        <el-button @click="()=>{this.coalPoiShow = false;this.aoeCoalPoi = {};}">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="单组钻孔设计参数" :visible.sync="singleDesShow" width="40%">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="188px">
        <el-form-item label="煤层厚度" prop="hc">
          <el-input
            v-model="queryParams.hc"
            placeholder="煤层厚度"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="煤层倾角" prop="alf">
          <el-input
            v-model="queryParams.alf"
            placeholder="煤层倾角"
          >
            <template slot="append">°</template>
          </el-input>
        </el-form-item>
        <el-form-item label="煤层底板到底抽巷间距" prop="h2">
          <el-input
            v-model="queryParams.h2"
            placeholder="煤层底板到底抽巷间距"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="底抽巷宽" prop="wd">
          <el-input
            v-model="queryParams.wd"
            placeholder="底抽巷宽"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="底抽巷高" prop="hd">
          <el-input
            v-model="queryParams.hd"
            placeholder="底抽巷高"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="底抽巷方位角" prop="betd">
          <el-input
            v-model="queryParams.betd"
            placeholder="底抽巷方位角"
          >
            <template slot="append">°</template>
          </el-input>
        </el-form-item>
        <el-form-item label="煤巷宽" prop="wm">
          <el-input
            v-model="queryParams.wm"
            placeholder="煤巷宽"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="煤巷高" prop="hm">
          <el-input
            v-model="queryParams.hm"
            placeholder="煤巷高"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="抽采半径" prop="radius">
          <el-input
            v-model="queryParams.radius"
            placeholder="抽采半径"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="底抽巷与煤巷水平投影间距" prop="l">
          <el-input
            v-model="queryParams.l"
            placeholder="底抽巷与煤巷水平投影间距"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="左侧抽采控制范围" prop="lx">
          <el-input
            v-model="queryParams.lx"
            placeholder="抽采控制范围"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="右侧抽采控制范围" prop="ls">
          <el-input
            v-model="queryParams.ls"
            placeholder="右侧抽采控制范围"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="内错|外错" prop="neicuo">
          <el-select v-model="queryParams.neicuo" placeholder="请选择">
            <el-option
              v-for="item in neiwaicuos"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <!--              <el-input
                          v-model="queryParams.neicuo"
                          placeholder="内错|外错"
                        />-->
        </el-form-item>
        <el-form-item label="布孔类型" prop="type">
          <el-select v-model="queryParams.type" placeholder="请选择">
            <el-option
              v-for="item in bukongs"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自动计算下一排位置" prop="autoUpdateX">
          <el-switch
            v-model="autoUpdateX"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
        <el-form-item style="margin: auto;display: block;text-align: center;">
          <el-button type="primary" size="mini" @click="getSingleDes">生成</el-button>
        </el-form-item>
      </el-form>
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
  async created() {
    await this.getCoalInfoList()
    await this.getCoalPoiTypeList()
    await this.getGeoFaultList()
  },
  methods: {
    addDrillToScene() {
      let bd = { navX: 0, navY: 0, navZ: 0, azimuth: 0, inclination: 0 }

      for (let i = 0; i < this.importDrillInfoList.length; i++) {
        let dd = this.importDrillInfoList[i]
        dd.disNav = this.currentPositionX
        dd.seeLen = dd.seeLen + (this.queryParams.wd / 2)
        dd.overLen = dd.seeLen + dd.throughLen
        let xm = 0, ym = 0, zm = 0, xn = 0, yn = 0, zn = 0
        if (dd.azimuth <= 360 && dd.azimuth > 270) {
          xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(360 - dd.azimuth)))
          ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(360 - dd.azimuth)))
          zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
          xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(360 - dd.azimuth)))
          yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(360 - dd.azimuth)))
          zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
        } else if (dd.azimuth <= 270 && dd.azimuth > 180) {
          xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth - 180)))
          ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth - 180)))
          zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
          xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth - 180)))
          yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth - 180)))
          zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
        } else if (dd.azimuth <= 180 && dd.azimuth > 90) {
          xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(180 - dd.azimuth)))
          ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(180 - dd.azimuth)))
          zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
          xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(180 - dd.azimuth)))
          yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(180 - dd.azimuth)))
          zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
        } else if (dd.azimuth <= 90) {
          xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth)))
          ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth)))
          zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
          xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth)))
          yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth)))
          zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
        }
        //console.log(xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
        let start, end, origin
        if (this.queryParams.neicuo) {
          start = new THREE.Vector3(ym, zm - (this.queryParams.h2 + (this.queryParams.wd / 2)), xm + this.queryParams.l)
          end = new THREE.Vector3(yn, zn - (this.queryParams.h2 + (this.queryParams.wd / 2)), xn + this.queryParams.l)
          origin = new THREE.Vector3(yn, 0 - (this.queryParams.h2 + (this.queryParams.wd / 2)), 0 + this.queryParams.l)
        } else {
          start = new THREE.Vector3(ym, zm - (this.queryParams.h2 + (this.queryParams.wd / 2)), xm - this.queryParams.l)
          end = new THREE.Vector3(yn, zn - (this.queryParams.h2 + (this.queryParams.wd / 2)), xn - this.queryParams.l)
          origin = new THREE.Vector3(yn, 0 - (this.queryParams.h2 + (this.queryParams.wd / 2)), 0 - this.queryParams.l)
        }
        this.addDrillRangeMeshToScene(start, end, 5, originDrillRangeMatS, false, this.originDrillRangeToSceneList, dd.id)
        this.addDrillMesh(origin, end, 0.2, originDrillMatS, this.originDrillToSceneList, dd.id)
        console.log('origin:', origin.x.toFixed(2), origin.y.toFixed(2), origin.z.toFixed(2), 'start:', start.x.toFixed(2), start.y.toFixed(2), start.z.toFixed(2), 'end:', end.x.toFixed(2), end.y.toFixed(2), end.z.toFixed(2))
      }
      if (this.autoUpdateX) {
        this.calcDesParam(Number(this.setLineWidth), 0)
      }
      this.importDrillShow = false
    },
    /**
     * 导入钻孔数据
     * @param file
     * @param fileList
     */
    importDrill(file, fileList) {
      this.importDrillInfoList = []
      let _this = this
      // 用FileReader来完成数据流的转换
      let reader = new FileReader()
      reader.onload = function(e) {
        let data = e.target.result
        //console.log(XLSX)
        //通过XLSX来解析出workbook（工作簿）也就导入的excel文件
        let wb = XLSX.read(data, { type: 'binary' })
        console.log(wb)
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
        for (let i = 2; i <= sortedList[0]; i++) {
          let obj = {
            realGroup: wb.Sheets['Sheet1']['A' + i]['v'],
            id: wb.Sheets['Sheet1']['B' + i]['v'],
            inclination: Number(wb.Sheets['Sheet1']['C' + i]['v']),
            azimuth: Number(wb.Sheets['Sheet1']['D' + i]['v']),
            angle: Number(wb.Sheets['Sheet1']['E' + i]['v']),
            totalLen: Number(wb.Sheets['Sheet1']['F' + i]['v']),
            seeLen: Number(wb.Sheets['Sheet1']['G' + i]['v']),
            throughLen: Number(wb.Sheets['Sheet1']['H' + i]['v']),
            disBase: Number(wb.Sheets['Sheet1']['K' + i]['v']),
            disCenter: Number(wb.Sheets['Sheet1']['L' + i]['v'])
          }
          _this.importDrillInfoList.push(obj)
        }
        console.log('共导入钻孔数据:' + _this.importDrillInfoList.length + '条')
        _this.importDrillShow = true
      }
      //readAsBinaryString需要File 或 Blob 对象的参数
      reader.readAsBinaryString(file.raw)
    },
    /**
     * 计算煤层分段坡度
     */
    calcCoalSlope() {
      if (this.cutFaceLines.length >= 2) {
        for (let i = 0; i < this.cutFaceLines.length; i++) {
          if (i >= 1 && this.cutFaceLines[i][0].x !== this.cutFaceLines[i - 1][0].x) {
            let tanSlope = Math.atan((this.cutFaceLines[i][1].y - this.cutFaceLines[i - 1][1].y) / (this.cutFaceLines[i][1].x - this.cutFaceLines[i - 1][1].x))
            this.coalSlopes.push([this.cutFaceLines[i - 1][0].x, this.cutFaceLines[i][0].x, tanSlope])
          }
        }
      }
      console.log('coalSlopes:', this.coalSlopes)
    },
    /**
     * 计算设计参数
     * @param lw 排间距
     * @param addonLw 额外排间距
     */
    calcDesParam(lw, addonLw) {
      // 排间距考虑角度
      if (this.equalLineSpace) {
        this.currentPositionX = Number(this.currentPositionX) + Number(lw)
      } else {
        let targetX = Number(this.currentPositionX) + Number(lw)
        for (let i = 0; i < this.coalSlopes.length; i++) {
          if (targetX >= this.coalSlopes[i][0], targetX < this.coalSlopes[i][1]) {
            this.currentLw = (Math.cos(this.coalSlopes[i][2]) * Number(lw))
            this.currentSlope = this.rad2ang(this.coalSlopes[i][2])
            this.currentPositionX = Number(this.currentPositionX) + (Math.cos(this.coalSlopes[i][2]) * Number(lw))
            break
          }
        }
      }
      // 三花设计时选择更厚的位置
      let cpX
      if (this.thickerPosition) {
        cpX = Number(this.currentPositionX) + (Math.cos(this.ang2rad(this.currentSlope)) * addonLw)
      } else {
        cpX = Number(this.currentPositionX) + 0
      }
      if (cpX >= this.cutFaceLines[0][0].x && cpX <= this.cutFaceLines[this.cutFaceLines.length - 1][0].x) {
        let cp = new THREE.Vector3(0, 0, 0)
        for (let i = 0; i < this.cutFaceLines.length; i++) {
          if (cpX === this.cutFaceLines[i][0].x) {
            // 创建第一切点
            cp = this.cutFaceLines[i][0]
            break
          } else if (cpX < this.cutFaceLines[i][0].x) {
            cp = this.getCutPoint(cpX, this.cutFaceLines[i][0], this.cutFaceLines[i - 1][0])
            break
          }
        }
        let cutR1TopList = []
        let cutR3BottomList = []
        let cutR3TopList = []
        for (let i = 0; i < this.cutFaceLines.length; i++) {
          cutR1TopList.push(this.cutFaceLines[i][1])
          cutR3TopList.push(this.cutFaceLines[i][2])
          cutR3BottomList.push(this.cutFaceLines[i][3])
        }
        let cutFaceMaterial = new THREE.LineBasicMaterial({ color: 0xaaaa00 })
        let cutFacePoints = this.createCutPoints(cp, cutR1TopList, cutR3TopList, cutR3BottomList, cutFaceMaterial)
        console.log('cutFacePoints:', cutFacePoints)
        // 煤层厚度
        let mchd = cutFacePoints[1].y - cutFacePoints[0].y
        console.log('煤层:', mchd)
        this.queryParams.hc = mchd
        // 煤层倾角
        let alf = Math.atan(Math.abs(cutFacePoints[0].y - cutFacePoints[3].y) / Math.abs(cutFacePoints[0].z - cutFacePoints[3].z))
        alf = this.rad2ang(alf)
        console.log('煤层倾角:', alf)
        this.queryParams.alf = alf
      } else {
        console.log('超范围')
      }
    },
    ang2rad(ang) {
      return ang * (Math.PI / 180)
    },
    rad2ang(rad) {
      return rad * (180 / Math.PI)
    },
    getSingleDes() {
      let _this = this
      drillingSingle(_this.queryParams).then(response => {
        let respData = response.data
        _this.desDrillList = response.data.drillingData
        _this.queryParams.lw4 = respData.lw4
        _this.queryParams.lw3 = respData.lw3
        console.log('response:', respData)
        _this.singleDesShow = false
        for (let i = 0; i < _this.desDrillList.length; i++) {
          let currentX
          // 排间距考虑角度
          if (_this.equalLineSpace) {
            currentX = (Number(_this.currentPositionX) + Number(_this.desDrillList[i].seeCoalPosition.y))
          } else {
            currentX = (Number(_this.currentPositionX) + (Math.cos(_this.ang2rad(_this.currentSlope)) * Number(_this.desDrillList[i].seeCoalPosition.y)))
          }
          //console.log('currentX:', currentX)
          let origin, start, end
          if (respData.neicuo) {
            origin = new THREE.Vector3(currentX, _this.desDrillList[i].openingPosition.z - (respData.h2 + (respData.wd / 2)), _this.desDrillList[i].openingPosition.x + respData.l)
            start = new THREE.Vector3(currentX, _this.desDrillList[i].seeCoalPosition.z - (respData.h2 + (respData.wd / 2)), _this.desDrillList[i].seeCoalPosition.x + respData.l)
            end = new THREE.Vector3(currentX, _this.desDrillList[i].passedCoalPosition.z - (respData.h2 + (respData.wd / 2)), _this.desDrillList[i].passedCoalPosition.x + respData.l)
            _this.addDrillRangeMeshToScene(start, end, 5, originDrillRangeMatS, false, _this.originDrillRangeToSceneList, _this.desDrillList[i].drillId)
            _this.addDrillMesh(origin, end, 0.2, originDrillMatS, _this.originDrillToSceneList, _this.desDrillList[i].drillId)
          } else {
            origin = new THREE.Vector3(currentX, _this.desDrillList[i].openingPosition.z - (respData.h2 + (respData.wd / 2)), _this.desDrillList[i].openingPosition.x - respData.l)
            start = new THREE.Vector3(currentX, _this.desDrillList[i].seeCoalPosition.z - (respData.h2 + (respData.wd / 2)), _this.desDrillList[i].seeCoalPosition.x - respData.l)
            end = new THREE.Vector3(currentX, _this.desDrillList[i].passedCoalPosition.z - (respData.h2 + (respData.wd / 2)), _this.desDrillList[i].passedCoalPosition.x - respData.l)
            _this.addDrillRangeMeshToScene(start, end, 5, originDrillRangeMatS, false, _this.originDrillRangeToSceneList, _this.desDrillList[i].drillId)
            _this.addDrillMesh(origin, end, 0.2, originDrillMatS, _this.originDrillToSceneList, _this.desDrillList[i].drillId)
          }
          console.log('origin:', origin.x.toFixed(2), origin.y.toFixed(2), origin.z.toFixed(2), 'start:', start.x.toFixed(2), start.y.toFixed(2), start.z.toFixed(2), 'end:', end.x.toFixed(2), end.y.toFixed(2), end.z.toFixed(2))
        }
        if (_this.autoUpdateX) {
          if (respData.type === 4 || respData.type === 41) {
            /* let targetX = Number(_this.currentPositionX) + Number(respData.lw4)
            for (let i = 0; i < this.coalSlopes.length; i++) {
              if (targetX >= this.coalSlopes[i][0], targetX < this.coalSlopes[i][1]) {
                _this.currentLw = (Math.cos(this.coalSlopes[i][2]) * Number(respData.lw4))
                _this.currentSlope = _this.rad2ang(this.coalSlopes[i][2])
                _this.currentPositionX = Number(_this.currentPositionX) + (Math.cos(this.coalSlopes[i][2]) * Number(respData.lw4))
                break
              }
            } */
            //_this.currentPositionX = Number(_this.currentPositionX) + Number(respData.lw4)
            _this.calcDesParam(respData.lw4, 0)
          } else if (respData.type === 3) {
            //_this.currentPositionX = Number(_this.currentPositionX) + Number(respData.lw3 * 2)
            //_this.calcDesParam(respData.lw3)
            _this.calcDesParam(respData.lw3 * 2, respData.lw3)
          }
        }
      })
    },
    importPoints(file, fileList) {
      let _this = this
      // 用FileReader来完成数据流的转换
      let reader = new FileReader()
      reader.onload = function(e) {
        let data = e.target.result
        //console.log(XLSX)
        //通过XLSX来解析出workbook（工作簿）也就导入的excel文件
        let wb = XLSX.read(data, { type: 'binary' })
        console.log(wb)
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
        for (let i = 2; i <= sortedList[0]; i++) {
          let obj = {
            roadwayid: wb.Sheets['Sheet1']['A' + i]['v'],
            id: wb.Sheets['Sheet1']['B' + i]['v'],
            bottomX: Number(wb.Sheets['Sheet1']['C' + i]['v']),
            bottomY: Number(wb.Sheets['Sheet1']['D' + i]['v']),
            bottomZ: Number(wb.Sheets['Sheet1']['E' + i]['v']),
            topX: Number(wb.Sheets['Sheet1']['F' + i]['v']),
            topY: Number(wb.Sheets['Sheet1']['G' + i]['v']),
            topZ: Number(wb.Sheets['Sheet1']['H' + i]['v'])
          }
          _this.coalPointInfoList.push(obj)
        }
        console.log('共导入煤层点数据:' + _this.coalPointInfoList.length + '对')
      }
      //readAsBinaryString需要File 或 Blob 对象的参数
      reader.readAsBinaryString(file.raw)
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
</script>
