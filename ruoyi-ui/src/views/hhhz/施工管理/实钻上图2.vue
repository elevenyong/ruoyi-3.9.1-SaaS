<template xmlns="http://www.w3.org/1999/html">
  <div>

    <el-steps :active="currentStep-1" finish-status="wait" process-status="finish" simple>
      <el-step title="实钻录入"></el-step>
      <el-step title="查看三维示意图"></el-step>
      <el-step title="空白带查询"></el-step>
      <el-step title="自动补钻计算"></el-step>
      <el-step title="导出实钻CAD图"></el-step>
    </el-steps>

    <el-row v-show="!showThree">
      <el-col :span="3">
        <div style="width: 1px;height: 1px">
        </div>
      </el-col>
      <el-col :span="18">
        <div style="padding: 20px 20px">
          <el-row>
            <h3>基本信息</h3>
            <div class="inner-container">
              <el-form
                  :model="queryParams"
                  ref="queryForm"
                  size="small"
                  :inline="true"
                  label-width="90px"
              >
                <el-form-item label="所属工作面" prop="zkdd">
                  <el-select
                      v-model="queryParams.workfaceId"
                      placeholder="请选择工作面"
                      @change="selectWorkingFace"
                  >
                    <el-option
                        v-for="item in coalMiningWFList"
                        :key="item.id"
                        :label="item.workfaceName"
                        :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-form>
            </div>
          </el-row>
          <el-row>
            <div class="inner-container">
              <el-form :model="baseData" ref="baseDataForm" size="small" :inline="true" label-width="90px">
                <el-form-item label="所属巷道" prop="zkdd">
                  <el-select
                      v-model="queryParams.roadwayId"
                      placeholder="请选择巷道"
                      @change="selectRoadway"
                  >
                    <el-option
                        v-for="item in roadWayList"
                        :key="item.id"
                        :label="item.roadway"
                        :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="导线点" prop="zkdd">
                  <el-select
                      v-model="queryParams.navPointId"
                      placeholder="请选择导线点"
                      @change="selectNavPoint"
                  >
                    <el-option
                        v-for="item in navPointList"
                        :key="item.id"
                        :label="item.navigationPointName+':('+item.x+','+item.y+','+item.z+')'"
                        :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="抽采半径" prop="radius">
                  <el-input
                      v-model="radius"
                      placeholder="请输入"
                      style="width: 130px"
                      @change="changeRadius"
                      @blur="radius !='' ? Number(radius):4.5"
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
                    <!--                    <el-input
                                          v-model="inputNumber"
                                          placeholder=""
                                          clearable
                                          style="width: 130px"
                                          @blur="inputNumber !='' ? Number(inputNumber):0"
                                        />-->
                    <el-radio-group v-model="inputNumber" @change="changeRenderNum" size="small">
                      <el-radio-button label="30">30条</el-radio-button>
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
                    <el-button type="primary" @click="calModelDataLoading" icon="iconfont icon-sanweiduixiang">
                      显示三维示意图
                    </el-button>
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
                <!--                <el-table-column label="列序号" prop="group">
                                  <template slot-scope="scope">
                                    <el-input
                                        v-if="!scope.row.disabled"
                                        v-model="scope.row.group"
                                        placeholder=""
                                        :disabled="scope.row.disabled"
                                    />
                                    {{ scope.row.disabled ? scope.row.group : '' }}
                                  </template>
                                </el-table-column>-->
                <el-table-column label="列号" prop="realGroup">
                  <template slot-scope="scope">
                    <el-input
                        v-if="!scope.row.disabled"
                        v-model="scope.row.realGroup"
                        placeholder=""
                        :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.realGroup : '' }}
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
                <el-table-column label="距底板高度" prop="disBottom">
                  <template slot-scope="scope">
                    <el-input
                        v-if="!scope.row.disabled"
                        v-model="scope.row.disBottom"
                        placeholder=""
                        :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.disBottom : '' }}
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
                <el-table-column label="过煤进岩长度" prop="beyondLen">
                  <template slot-scope="scope">
                    <el-input
                        v-if="!scope.row.disabled"
                        v-model="scope.row.beyondLen"
                        placeholder=""
                        :disabled="scope.row.disabled"
                    />
                    {{ scope.row.disabled ? scope.row.beyondLen : '' }}
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
                    <el-button type="primary" @click="addSingleDrillData" icon="el-icon-plus">
                      新增一条钻孔信息
                    </el-button>
                  </el-form-item>
                  <el-form-item>
                    <el-upload
                        ref="upload"
                        action="xlsx.html"
                        :auto-upload="false"
                        :on-change="getMyExcel"
                        :show-file-list="false"
                    >
                      <el-button type="primary" icon="el-icon-upload2">
                        批量导入数据
                      </el-button>
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
      <el-col :span="20">
        <div id="box" style="display: flex">
          <div id="container"></div>
          <div id="tGUI" style="width:250px;margin-left: -250px"></div>
          <!--<div id="output"></div>-->
        </div>
      </el-col>
      <!--      <el-col :span="4" v-if="currentStep===2">-->
      <el-col :span="4">
        <div style="width: 160px;float: right">
          <!--    返回数据页面      -->
          <el-row style="margin-top: 20px;">
            <el-button @click="changePage(1)" style="float: right;width: 160px" icon="el-icon-back">返回数据页
            </el-button>
          </el-row>
          <!--    煤层三维展示      -->
          <el-row style="margin-top: 10px;">
            <VDropdown :arrow-padding="0" placement="left-start" :distance="10">
              <template #popper>
                <div style="width:160px;padding: 12px">
                  <el-row style="width: 100%">
                    <VDropdown :delay="200" :arrow-padding="8" placement="left-start" :distance="20">
                      <template #popper>
                        <div style="width:160px;padding: 12px">
                          <el-form size="mini">
                            <el-form-item label="显示/隐藏">
                              <el-switch
                                  v-model="param.showCoalPoints"
                                  active-color="#409eff"
                                  inactive-color="#eaecf0"
                                  @change="showOrHideCoalPoints"
                                  :width="30"
                              />
                            </el-form-item>
                            <el-form-item label="点大小">
                              <el-slider style="width: 80px;height: 18px;float: left;margin-left: 5px"
                                         :show-tooltip="true"
                                         v-model="param.coalPointsScale"
                                         @input="changeCoalPointsScale" :min="1" :max="5" :step="0.5"></el-slider>
                            </el-form-item>
                            <el-form-item label="止煤点颜色">
                              <el-color-picker v-model="param.topPointsColor"
                                               @change="changeTopPointsColor"></el-color-picker>
                            </el-form-item>
                            <el-form-item label="见煤点颜色">
                              <el-color-picker v-model="param.bottomPointsColor"
                                               @change="changeBottomPointsColor"></el-color-picker>
                            </el-form-item>
                          </el-form>
                        </div>
                      </template>
                      <el-button style="width: 136px;text-align: left" type="primary">点云</el-button>
                    </VDropdown>
                  </el-row>
                  <el-row style="margin-top: 10px;width: 100%">
                    <VDropdown :delay="200" :arrow-padding="8" placement="left-start" :distance="20">
                      <template #popper>
                        <div style="width:160px;padding: 12px">
                          <el-form size="mini">
                            <el-form-item label="显示/隐藏">
                              <el-switch
                                  v-model="param.showCoalLine"
                                  active-color="#409eff"
                                  inactive-color="#eaecf0"
                                  @change="showOrHideCoalLine"
                                  :width="30"
                              />
                            </el-form-item>
                            <el-form-item label="线宽">
                              <el-slider style="width: 80px;height: 18px;float: left;margin-left: 5px"
                                         :show-tooltip="false"
                                         v-model="param.linewidth"
                                         @input="changeLineWidth" :min="0.001" :max="0.01" :step="0.001"></el-slider>
                            </el-form-item>
                            <el-form-item label="止煤点连线颜色">
                              <el-color-picker v-model="param.topLineColor"
                                               @change="changeTopLineColor"></el-color-picker>
                            </el-form-item>
                            <el-form-item label="见煤点连线颜色">
                              <el-color-picker v-model="param.bottomLineColor"
                                               @change="changeBottomLineColor"></el-color-picker>
                            </el-form-item>
                            <el-form-item label="煤孔段连线颜色">
                              <el-color-picker v-model="param.sideLineColor"
                                               @change="changeSideLineColor"></el-color-picker>
                            </el-form-item>
                          </el-form>
                        </div>
                      </template>
                      <el-button style="width: 136px;text-align: left" type="primary">截面线</el-button>
                    </VDropdown>
                  </el-row>
                  <el-row style="margin-top: 10px;width: 100%">
                    <VDropdown :delay="200" :arrow-padding="8" placement="left-start" :distance="20">
                      <template #popper>
                        <div style="width:160px;padding: 12px">
                          <el-form size="mini">
                            <el-form-item label="显示/隐藏">
                              <el-switch
                                  v-model="param.showCoalCube"
                                  active-color="#409eff"
                                  inactive-color="#eaecf0"
                                  @change="showOrHideCoal"
                                  :width="30"
                              />
                            </el-form-item>
                            <el-form-item label="透明度">
                              <el-slider style="width: 80px;height: 18px;float: left;margin-left: 5px"
                                         :show-tooltip="false"
                                         v-model="param.coalCubeOpacity"
                                         @input="changeCoalCubeOpt" :min="0.55" :max="1" :step="0.01"></el-slider>
                            </el-form-item>
                            <el-form-item label="煤层体颜色">
                              <el-color-picker v-model="param.coalCubeColor"
                                               @change="changeCoalCubeColor"></el-color-picker>
                            </el-form-item>
                          </el-form>
                        </div>
                      </template>
                      <el-button style="width: 136px;text-align: left" type="primary">煤层体</el-button>
                    </VDropdown>
                  </el-row>
                </div>
              </template>
              <el-button style="float: right;width: 160px;text-align: left;" type="primary"
                         icon="iconfont icon-xianshi">
                煤层三维展示
              </el-button>
            </VDropdown>
          </el-row>
          <!--    钻孔三维展示      -->
          <el-row style="margin-top: 10px;width: 100%">
            <el-popover
                placement="left-start"
                width="170"
                trigger="click">
              <div style="width:160px;">
                <el-row style="width: 100%">
                  <el-button v-if="param.showOriginDrill"
                             @click="showOrHideOriginDrill(false)" icon="iconfont icon-yincang"
                             style="width: 145px;text-align: left;">
                    进岩段
                  </el-button>
                  <el-button type="primary" v-if="!param.showOriginDrill" icon="iconfont icon-xianshi"
                             @click="showOrHideOriginDrill(true)"
                             style="width: 145px;text-align: left;">
                    进岩段
                  </el-button>
                </el-row>
                <el-row style="margin-top: 10px;width: 100%">
                  <el-button v-if="param.showOriginDrillInCoal"
                             @click="showOrHideOriginDrillInCoal(false)" icon="iconfont icon-yincang"
                             style="width: 145px;text-align: left;">
                    煤孔段
                  </el-button>
                  <el-button type="primary" v-if="!param.showOriginDrillInCoal" icon="iconfont icon-xianshi"
                             @click="showOrHideOriginDrillInCoal(true)"
                             style="width: 145px;text-align: left;">
                    煤孔段
                  </el-button>
                </el-row>
                <el-row style="margin-top: 10px;width: 100%">
                  <el-button v-if="param.showOriginDrillBeyondCoal"
                             @click="showOrHideOriginDrillBeyondCoal(false)" icon="iconfont icon-yincang"
                             style="width: 145px;text-align: left;">
                    过煤进岩段
                  </el-button>
                  <el-button type="primary" v-if="!param.showOriginDrillBeyondCoal" icon="iconfont icon-xianshi"
                             @click="showOrHideOriginDrillBeyondCoal(true)"
                             style="width: 145px;text-align: left;">
                    过煤进岩段
                  </el-button>
                </el-row>
                <el-row style="margin-top: 10px;width: 100%">
                  <el-button v-if="param.showOriginDrillRange"
                             @click="showOrHideOriginDrillRange(false)" icon="iconfont icon-yincang"
                             style="width: 145px;text-align: left;">
                    抽采范围
                  </el-button>
                  <el-button type="primary" v-if="!param.showOriginDrillRange" icon="iconfont icon-xianshi"
                             @click="showOrHideOriginDrillRange(true)"
                             style="width: 145px;text-align: left;">
                    抽采范围
                  </el-button>
                </el-row>
                <el-row style="margin-top: 10px;width: 100%;">
                  按列显示元素
                  <my-cascader
                      :options="groupDrillOptions"
                      :is_deep="true"
                      :has_all_select="true"
                      :defaultSelectAll="true"
                      @getOptions="getOptions"
                      style="width: 150px">
                  </my-cascader>
                  <el-button type="text" style="width: 60px;float: left" @click="beforeLine">< 上一列</el-button>
                  <el-button type="text" style="width: 70px;float: right" @click="nextLine">下一列 ></el-button>
                </el-row>
              </div>

              <el-button style="float: right;width: 160px;text-align: left;" type="primary" slot="reference"
                         icon="iconfont icon-xianshi">
                钻孔三维展示
              </el-button>
            </el-popover>
          </el-row>
          <!--    其他元素展示      -->
          <el-row style="margin-top: 10px;width: 100%">
            <el-popover
                placement="left-start"
                width="170"
                trigger="click">
              <div style="width:160px">
                <el-row style="width: 100%">
                  <el-button v-if="param.showRoad" @click="showOrHideRoad(false)" icon="iconfont icon-yincang"
                             style="width: 145px;text-align: left;">
                    巷道
                  </el-button>
                  <el-button type="primary" v-if="!param.showRoad" @click="showOrHideRoad(true)"
                             icon="iconfont icon-xianshi"
                             style="width: 145px;text-align: left;">
                    巷道
                  </el-button>
                </el-row>

                <el-row style="margin-top: 10px;width: 100%">
                  <el-button v-if="param.showWorkPoint" @click="showOrHideWorkPoint(false)"
                             icon="iconfont icon-yincang"
                             style="width: 145px;text-align: left;">
                    施工点位
                  </el-button>
                  <el-button type="primary" v-if="!param.showWorkPoint" @click="showOrHideWorkPoint(true)"
                             icon="iconfont icon-xianshi"
                             style="width: 145px;text-align: left;">
                    施工点位
                  </el-button>
                </el-row>
              </div>
              <el-button style="float: right;width: 160px;text-align: left;" type="primary" slot="reference"
                         icon="iconfont icon-xianshi">
                其他元素展示
              </el-button>
            </el-popover>
          </el-row>

          <!--        <el-row style="margin-top: 10px;width: 100%">
                    <el-button type="danger" @click="createFault" style="float: right;width: 160px">识别断层</el-button>
                  </el-row>-->
          <el-row style="margin-top: 10px;width: 100%" v-show="currentStep===2">
            <el-popover
                placement="left-start"
                width="170"
                trigger="manuel" v-model="calcEmptyBandVisible">
              <el-row style="margin-top: 10px;width: 100%;">
              <span style="float: left">
              空白带体积阈值:
              </span>
                <el-input
                    v-model="calcEmptyVolumeThreshold"
                    placeholder=""
                    style="width: 146px"
                    @change="changeThreshold"
                    @blur="calcEmptyVolumeThreshold !='' ? Number(calcEmptyVolumeThreshold):0"
                >
                  <template slot="append">m³</template>
                </el-input>
              </el-row>
              <el-row style="margin-top: 10px;margin-bottom: 10px;width: 100%;">
                <el-button type="primary" @click="calInitResultLoading" style="width: 146px"
                           icon="el-icon-search">开始查找
                </el-button>
              </el-row>
              <el-button type="checkempty" style="text-align: left;" slot="reference" @click="showCalcEmptyBand"
                         icon="el-icon-search">
                查找空白带
              </el-button>
            </el-popover>
          </el-row>

          <el-row style="margin-top: 10px;width: 100%" v-show="currentStep===3||currentStep===4">
            <el-popover
                placement="left-start"
                width="400"
                trigger="click" v-model="emptyDialogVisible">
              <el-scrollbar style="overflow-x: hidden;">
                <el-table
                    key="uuid"
                    v-if="!noneEmptyBand && emptyBandInfoList.length>0"
                    :data="emptyBandInfoList"
                    :default-sort="{prop: 'volume', order: 'descending'}"
                    height="250"
                >
                  <el-table-column label="空白带编号" prop="uuid" width="85px" :formatter="sub5" align="center"/>
                  <el-table-column label="中心点坐标" prop="position" :formatter="fixed2"
                                   align="center"></el-table-column>
                  <el-table-column label="体积(m³)" prop="volume" width="75px" align="center"/>
                  <el-table-column label="操作" prop="volume" width="55px" align="center">
                    <template slot-scope="scope">
                      <el-button @click="seeEmptyBand(scope.row)" type="text" icon="el-icon-eye" size="small">定位
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <div v-if="noneEmptyBand || emptyBandInfoList.length===0" style="width: 100%;text-align: center">
                  <h3 style="color: #00afff">未发现任何空白带</h3>
                </div>
              </el-scrollbar>
              <el-button type="primary" style="float: right;width: 160px;text-align: left;" slot="reference"
                         icon="el-icon-document">
                空白带信息
              </el-button>
            </el-popover>
          </el-row>


          <!--        <el-row style="margin-top: 10px;width: 100%" v-show="emptyBandInfoList.length>0">-->
          <el-row style="margin-top: 10px;width: 100%" v-show="currentStep===3||currentStep===4">
            <el-popover
                placement="left-start"
                width="300"
                trigger="manuel" v-model="addDrillDialogVisible">
              <el-form style="display: inline-block;float: left" size="small" :inline="true">
                <div style="padding: 10px 10px 10px 10px">
                  <el-form-item label="补钻模式">
                    <el-radio-group v-model="addDrillForm.drillDesMode" :disabled="addDrillForm.startAddDrill">
                      <el-radio :label="false">自动</el-radio>
                      <el-radio :label="true">手动</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item label="补钻算法">
                    <el-select v-model="addDrillForm.workPlace" style="width: 150px"
                               :disabled="addDrillForm.startAddDrill">
                      <el-option
                          v-for="item in workPlaces"
                          :key="item.id"
                          :label="item.label"
                          :value="item.id"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="补钻抽采半径">
                    <el-input v-model="addDrillRadius"
                              size="mini"
                              style="width: 110px"
                              @blur="addDrillRadius !='' ? Number(addDrillRadius):0"
                              :disabled="addDrillForm.startAddDrill">
                      <template slot="append">m</template>
                    </el-input>
                  </el-form-item>
                  <div v-show="!addDrillForm.drillDesMode">
                    <el-form-item label="自动补钻阈值">
                      <el-input v-model="addEmptyVolumeThreshold"
                                size="mini"
                                style="width: 110px"
                                @blur="addEmptyVolumeThreshold !='' ? Number(addEmptyVolumeThreshold):0"
                                :disabled="addDrillForm.startAddDrill">
                        <template slot="append">m³</template>
                      </el-input>
                      <el-tooltip style="margin-left: 5px" effect="light" content="空白带体积小于该阈值将不会被自动补钻"
                                  placement="top">
                        <i class="el-icon-info"></i>
                      </el-tooltip>
                    </el-form-item>
                    <br>
                    <!--                  <el-form-item label="补钻参数">
                                        <el-button type="primary" size="mini" @click="showAddDrillData"
                                                   style="margin-bottom: 5px;margin-right: 5px">
                                          显示补钻参数表
                                        </el-button>
                                      </el-form-item>-->
                  </div>
                </div>
              </el-form>
              <div v-show="addDrillForm.drillDesMode"
                   style="display: inline-block;float: left;margin-left: 10px;margin-top: -10px">
                <el-button size="mini" type="success" v-if="!addDrillForm.startAddDrill" @click="startDesMode(true)">
                  开始补钻
                </el-button>
                <el-button size="mini" type="warning" v-if="addDrillForm.startAddDrill" @click="startDesMode(false)">
                  结束补钻
                </el-button>
              </div>
              <div v-show="!addDrillForm.drillDesMode"
                   style="display: inline-block;float: left;margin-left: 10px;margin-top: -10px">
                <el-button size="mini" type="success" @click="autoAddDrillLoading">开始补钻</el-button>
              </div>
              <el-button type="primary" size="mini" @click="showAddDrillData"
                         style="display: inline-block;float: right;margin-right: 10px;margin-top: -10px">
                显示补钻参数表
              </el-button>
              <el-button type="checkempty" style="float: right;width: 160px;text-align: left;"
                         @click="showAddDrillDialog"
                         slot="reference" icon="el-icon-place">
                补钻操作
              </el-button>
            </el-popover>
          </el-row>
          <el-row style="margin-top: 10px;width: 100%">
            <el-popover
                placement="left-start"
                width="170"
                trigger="manuel" v-model="exportCADDialogVisible">
              <!--<el-row style="width: 100%;">
                <el-button type="primary" @click="showCADPage(null,1,2)" style="width: 160px"
                           icon="el-icon-search">预览平面图
                </el-button>
              </el-row>
              <el-row style="margin-top: 10px;width: 100%;">
                <el-button type="primary" @click="showCADPage(null,1,3)" style="width: 160px"
                           icon="el-icon-search">预览剖面图
                </el-button>
              </el-row>-->
              <el-row style="margin-top:10px;margin-bottom:10px;margin-left:-5px;width: 100%;">
                <el-button type="primary" @click="showCADPage(null,1,4)" style="width: 155px;text-align: left"
                           icon="el-icon-download">导出竣工图
                </el-button>
              </el-row>
              <el-row style="margin-top:10px;margin-bottom:10px;margin-left:-5px;width: 100%;">
                <el-button type="primary" @click="showCADPage(null,1,5)" style="width: 155px;text-align: left"
                           icon="el-icon-download">导出补空白设计图
                </el-button>
              </el-row>
              <el-button type="success" style="float: right;width: 160px;text-align: left;" @click="showExportCADDialog"
                         slot="reference"
                         icon="iconfont icon-tubiao_daochuCAD">
                导出CAD
              </el-button>
            </el-popover>
          </el-row>
          <el-row style="margin-top: 10px;width: 100%">
            <el-button type="primary" style="float: right;width: 160px;text-align: left;" @click="showAnalyseDialog"
                       icon="el-icon-data-line">
              钻孔分析
            </el-button>
          </el-row>
          <el-row style="margin-top: 10px;width: 100%">
            <el-popover
                placement="left-start"
                width="170"
                trigger="click">
              <!--<el-row style="width: 100%;">
                <el-button type="primary" @click="showCADPage(null,1,2)" style="width: 160px"
                           icon="el-icon-search">预览平面图
                </el-button>
              </el-row>
              <el-row style="margin-top: 10px;width: 100%;">
                <el-button type="primary" @click="showCADPage(null,1,3)" style="width: 160px"
                           icon="el-icon-search">预览剖面图
                </el-button>
              </el-row>-->
              <el-row style="margin-top:10px;margin-bottom:10px;margin-left:-5px;width: 100%;">
                <el-button type="primary" @click="startMeasureMode(0)" style="width: 155px;text-align: left"
                           icon="iconfont icon-diandianjulikongjianjuli">
                  距离测量
                </el-button>
              </el-row>
              <el-row style="margin-top:10px;margin-bottom:10px;margin-left:-5px;width: 100%;">
                <el-button type="primary" @click="startMeasureMode(1)" style="width: 155px;text-align: left"
                           icon="iconfont icon-mianji">
                  面积测量
                </el-button>
              </el-row>
              <el-row style="margin-top:10px;margin-bottom:10px;margin-left:-5px;width: 100%;">
                <el-button type="primary" @click="startMeasureMode(2)" style="width: 155px;text-align: left"
                           icon="el-icon-share">
                  角度测量
                </el-button>
              </el-row>
              <el-button type="primary" style="float: right;width: 160px;text-align: left;"
                         slot="reference"
                         icon="iconfont icon-measure">
                量测工具
              </el-button>
            </el-popover>
          </el-row>
        </div>
      </el-col>

    </el-row>

    <div v-if="showDesc" class="drill-desc">
      <el-card>
        <div slot="header">
          <span>当前选中钻孔信息</span>
        </div>
        <el-descriptions :column="2">
          <el-descriptions-item label="列号">{{ selectedDrill.realGroup }}</el-descriptions-item>
          <el-descriptions-item label="序号">{{ selectedDrill.id }}</el-descriptions-item>
          <el-descriptions-item label="钻孔方位角">{{ selectedDrill.azimuth }}</el-descriptions-item>
          <el-descriptions-item label="钻孔倾角">{{ selectedDrill.inclination }}</el-descriptions-item>
          <el-descriptions-item label="与导线点距离">{{ selectedDrill.disNav }}</el-descriptions-item>
          <el-descriptions-item label="距底板高度">{{ selectedDrill.disBottom }}</el-descriptions-item>
          <el-descriptions-item label="据中心线距离">{{ selectedDrill.disCenter }}</el-descriptions-item>
          <el-descriptions-item label="见煤深度">{{ selectedDrill.seeLen }}</el-descriptions-item>
          <el-descriptions-item label="止煤深度">{{ selectedDrill.overLen }}</el-descriptions-item>
          <el-descriptions-item label="类型">
            <el-tag v-if="selectedDrill.type===1" size="small" type="warning">实钻钻孔</el-tag>
            <el-tag v-if="selectedDrill.type===2" size="small">补空白设计钻孔</el-tag>
          </el-descriptions-item>
          <el-descriptions-item v-if="selectedDrill.type===1" label="操作">
            <!--<el-button type="primary" style="margin-left: 5px" size="mini" @click="showCADPage(selectedDrill,2,1)">
              预览整组钻孔CAD图
            </el-button>-->
            <el-button type="primary" style="margin-left: 5px" size="mini" @click="showCADPage(selectedDrill,1,1)">
              导出单排CAD图
            </el-button>
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
            <el-button type="primary" style="margin-left: 5px" size="mini" @click="showCADPage(selectedDrill,2,1)">
              导出CAD图
            </el-button>
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>

    <el-dialog title="补钻参数表" :visible.sync="addDrillDataVisible">
      <el-table
          key="id"
          height="350"
          :data="addDrillingDataList"
      >
        <el-table-column label="最近列号" prop="group">
        </el-table-column>
        <el-table-column label="序号" prop="id">
        </el-table-column>
        <el-table-column label="钻孔方位角" prop="azimuth">
        </el-table-column>
        <el-table-column label="钻孔倾角" prop="inclination">
        </el-table-column>
        <el-table-column label="与导线点距离" prop="disNav">
        </el-table-column>
        <el-table-column label="距底板高度" prop="disBottom">
        </el-table-column>
        <el-table-column label="据中心线距离" prop="disCenter">
        </el-table-column>
        <el-table-column label="见煤深度" prop="seeLen">
        </el-table-column>
        <el-table-column label="止煤深度" prop="overLen">
        </el-table-column>
        <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <!--<el-button
                size="mini"
                type="text"
                @click="showCADPage(scope.row,2,1)"
            >预览CAD
            </el-button>-->
            <el-button
                size="mini"
                type="text"
                @click="showCADPage(scope.row,2,1)"
            >导出CAD
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button style="float: right;margin-bottom: 10px" type="primary" size="mini" @click="exportDrillData">导出参数表
      </el-button>
    </el-dialog>

    <!--    <el-dialog title="空白带信息" :visible.sync="emptyDialogVisible">
          <el-table
            key="uuid"
            :data="emptyBandInfoList"
            height="350"
            style="width: 40%"
          >
            <el-table-column label="空白带编号" prop="uuid" align="center"/>
            <el-table-column label="中心点坐标" prop="position" :formatter="fixed2" align="center">
            </el-table-column>
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
        </el-dialog>-->

    <el-dialog title="CAD图预览" :visible.sync="addDrillCADVisible">
      <template slot="title">
        <h4 style="display: inline">CAD图预览</h4>
        <el-button type="primary" size="mini" style="float: right;margin-right: 30px" @click="exportCAD">导出
        </el-button>
      </template>
      <!--            <DxfViewer ref="viewer" :dxfUrl="dxfUrl" :fonts="fonts"/>-->
      <MyDxfViewer :dxfUrl="dxfUrl" style="width: 100%;height: 70vh"></MyDxfViewer>
    </el-dialog>

    <el-dialog title="钻孔分析" :visible.sync="analyseDrillVisible">
      <el-table
          key="id"
          :data="groupNoFormList"
          height="380"
      >
        <!--                <el-table-column label="列序号" prop="group">
                          <template slot-scope="scope">
                            <el-input
                                v-if="!scope.row.disabled"
                                v-model="scope.row.group"
                                placeholder=""
                                :disabled="scope.row.disabled"
                            />
                            {{ scope.row.disabled ? scope.row.group : '' }}
                          </template>
                        </el-table-column>-->
        <el-table-column label="列号" align="center" prop="realGroup"/>
        <el-table-column label="钻孔数量" align="center" prop="count"/>
        <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="text"
                icon="el-icon-download"
                @click="showCADPage(scope.row,1,1)"
            >导出CAD图
            </el-button>
            <el-button
                style="color: green"
                size="mini"
                type="text"
                icon="el-icon-download">导出分析报告
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>

import * as THREE from 'three'
import { Mesh } from 'three'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js'
import { TransformControls } from 'three/examples/jsm/controls/TransformControls.js'
import * as BufferGeometryUtils from 'three/examples/jsm/utils/BufferGeometryUtils.js'
import { computeMeshVolume, Evaluator, GridMaterial, Operation, OperationGroup, SUBTRACTION } from 'three-bvh-csg'
//import { GUI } from 'three/examples/jsm/libs/lil-gui.module.min.js'
import { GUI } from 'lil-gui';
import * as XLSX from 'xlsx'
import { EffectComposer } from 'three/examples/jsm/postprocessing/EffectComposer.js'
import { OutlinePass } from 'three/examples/jsm/postprocessing/OutlinePass.js'
import { v4 as uuidv4 } from 'uuid'
import * as YUKA from '@/utils/yuka/yuka.module.js'
import { createOBBHelper } from '@/utils/yuka/BVHelper'
import { listCoalminingworkingface } from '@/api/hhhz/coalMiningWorkingFace'
import { gRoad, gRoadWayByWorkFace } from '@/api/hhhz/hangdao'
import { Loading } from 'element-ui'
import ExportJsonExcel from 'js-export-excel'
import cdt2d from 'cdt2d'
import TWEEN from '@tweenjs/tween.js'
import { RenderPass } from 'three/examples/jsm/postprocessing/RenderPass.js'
import { ShaderPass } from 'three/examples/jsm/postprocessing/ShaderPass.js'
import { GammaCorrectionShader } from 'three/examples/jsm/shaders/GammaCorrectionShader'
import { cadExport, cadExportDXF } from '@/api/hhhz/cad_export'
import DxfViewer from '@/views/hhhz/施工管理/DxfViewer.vue'
import MyDxfViewer from '@/views/hhhz/施工管理/MyDxfViewer.vue'
import MyCascader from '@/views/hhhz/施工管理/myCascader.vue'
import { LineMaterial } from 'three/examples/jsm/lines/LineMaterial'
import { Line2 } from 'three/examples/jsm/lines/Line2'
import { LineGeometry } from 'three/examples/jsm/lines/LineGeometry'
import { next } from 'lodash/seq'
import { CSS2DObject, CSS2DRenderer } from 'three/examples/jsm/renderers/CSS2DRenderer'

const loader = new THREE.BufferGeometryLoader()

const params = {
  snap: true,
  displayControls: false,
  transparentBrushes: true,
  showOutCube: false,// 显示外部包围体
  showCoalCube: true,// 显示煤层体
  showCoalBottomMesh: false,// 煤层底板
  showCoalTopMesh: false,// 煤层顶板
  showCoalPoints: true, // 顶底板点
  display: 'OVERLAY',
  drillDesMode: false, // 开启补钻模式
  drillSelectMode: true, // 钻孔选择模式
  showOriginDrill: true, // 显示原始钻孔
  showOriginDrillRange: true, // 显示原始钻孔抽采范围
  oriDRMOpacity: 0.2, // 原始钻孔抽采范围透明度
  showAddDrill: true, // 显示补钻钻孔
  showAddDrillRange: true, // 显示补钻钻孔抽采范围
  addDRMOpacity: 0.3, // 新增钻孔抽采范围透明度
  showOriginEmptyBand: true, // 显示原始空白带信息
  showCoalMesh: true, // 显示煤层顶底板网格
  coalCubeOpacity: 0.5,// 煤层块儿透明度
  showEmptyBand: true, // 显示空白带
  showOriginDrillInCoal: true, // 显示原始煤孔段
  showAddDrillInCoal: false, // 显示新增煤孔段
  showOriginDrillBeyondCoal: false,// 显示原始过煤段
  showRoad: true, // 显示巷道
  showWorkPoint: true, // 显示施工点位
  showCoalLine: false, // 显示煤层线
  coalPointsScale: 1, // 点大小缩放
  bottomPointsColor: 0xff0000, // 底板点颜色
  topPointsColor: 0x99cc00, // 顶板点颜色
  bottomLineColor: 0xff0000, // 底板线颜色
  topLineColor: 0x99cc00, // 顶板线颜色
  sideLineColor: 0xffffff, // 侧面线颜色
  coalCubeColor: 0x7D7C82, // 煤层颜色
  linewidth: 0.001, // 线宽
  showSpCoalLine: true // 下一列显示的线
}

let renderer, css2dRenderer, camera, scene, gui, outputContainer, textOutput, axesHelper, tGUIContainer
let controls, transformControls
let light
let initCSGEvaluator, addCSGEvaluator
let initResult, initRoot, partsResult, partsRoot, originEmptyBandResult
let rootPartsObject, wireframeObject, coalCubeObj, loadedObj
let coalBottomMesh, coalTopMesh, coalBottomWireMesh, coalTopWireMesh, roadMesh, focusBoxMesh, coalTopPointsMeshList = []
    , coalBottomPointsMeshList = [], pointsBottomLineMeshList = [], pointsTopLineMeshList = [], pointsLineMeshList = [],
    pointsSideLineMeshList = [], measurePointList = [], measureLineList = [], measureLabelList = [],
    measureCurveList = []
let coalGeometry
let drillInCoalMat, coalTopMeshMat, coalBottomMeshMat, coalBottomWireMeshMat, coalTopWireMeshMat, roadMat, brushMat,
    addDrillMat, addDrillRangeMat, originDrillMat, originDrillRangeMat, resultGridMat, originEmptyBandMat,
    originDrillMatS, originDrillMatD, originDrillRangeMatS, originDrillRangeMatD, coalCubeMat, tempSelectedMat,
    bottomLineMat, topLineMat, sideLineMat, allPointsLineMat
let mouse = new THREE.Vector2()
let originEmptyPartsMeshs = []
let partMeshs = []
let partCubes = []
let selObjs = []
let composer, outlinePass, renderPass, outline2Pass, composer2, renderPass2, tempLabel
let canvas1, context1, texture1, sprite1
let tempSelected
export default {
  name: 'EmptyBandModel',
  components: { MyCascader, MyDxfViewer, DxfViewer },

  data() {
    return {
      mouseMoved: false,// 鼠标是否移动
      measureMode: -1,// -1==退出测量
      measureNotify: null,// 量测工具
      addDrillNotify: null,// 手动补钻
      color1: null,
      color2: null,
      ///////////////////////////
      groupDrillSelects: [],// 选中显示的钻孔
      groupSelects: [],// 选中组号
      groupSpecialSelects: [],// 特别选中组号
      next: false,
      before: false,
      // 构造钻孔数据
      groupDrillOptions: [{ label: '全选', id: 0 }],
      xData: [],
      yData: [],
      dxfUrl: null,
      groupNoList: [], // 存储真实排号名称
      groupNoFormList: [],// 存储表格数据
      // 导出CAD数据
      cadData: null,
      workPlaces: [{
        id: 1,
        label: '原始施工点出钻'
      }, {
        id: 2,
        label: '巷道最近点出钻'
      }],
      roadLen: -1,// 巷道长度
      showDesc: false,
      noneEmptyBand: false,
      tween: null,// 动画
      needReRender: true,// 是否需要重渲染
      renderNum: -1,
      activeNames: ['1', '2'],
      stopLoading: false,
      sceneInit: false,// 场景初始化
      needReCalculate: true,// 是否需要重计算
      currentStep: 1,// 当前所在页面
      initEmptyBand: false,// 是否计算了空白带
      param: {
        showCoalTopMesh: false,// 显示顶板
        showCoalBottomMesh: false,// 显示底板
        showCoalPoints: true, // 顶底板点
        showCoalCube: true,// 煤体
        showOriginDrill: true,// 原始钻孔
        showOriginDrillInCoal: true,// 原始煤孔段
        showOriginDrillRange: true,// 原始抽采范围
        showAddDrill: true,// 补钻钻孔
        showAddDrillInCoal: true,// 补钻煤孔段
        showOriginDrillBeyondCoal: false,// 过煤进岩段
        showAddDrillRange: true,// 补钻抽采范围
        showOriginEmptyBand: true,// 原始空白带
        showEmptyBand: true,// 当前剩余空白带
        showOutCube: false,// 空白带外部Box
        showRoad: true,//巷道
        showWorkPoint: true,//施工点位
        showCoalLine: false,//煤层线
        coalCubeOpacity: 0.5, // 煤层透明度
        coalPointsScale: 1, // 点大小缩放
        bottomPointsColor: '#ff0000', // 底板点颜色
        topPointsColor: '#99cc00', // 顶板点颜色
        bottomLineColor: '#ff0000', // 底板点颜色
        topLineColor: '#99cc00', // 顶板点颜色
        sideLineColor: '#ffffff', // 顶板点颜色
        coalCubeColor: '#7D7C82',// 煤层颜色
        linewidth: 0.001, // 线宽
        showSpCoalLine: true // 下一列显示的线
      },
      // 补钻操作区参数
      addDrillForm: {
        workPlace: 2, //钻孔工作点位,1=原始施工点,2巷道最近点
        drillDesMode: false, //开启手动补钻模式,false=自动，true=手动
        startAddDrill: false //开启补钻，手动自动都使用这个字段，为true时锁定补钻参数值
      },
      // 渲染数据条数
      inputNumber: 999999,
      showThree: false,
      // 自动补钻标识
      autoAdd: false,
      autoIndex: 1,
      // 计算加载中
      loading: false,
      // 基础信息
      baseData: { navX: 0, navY: 0, navZ: 0, azimuth: 0, inclination: 0 },
      // 添加钻孔信息
      drillingData: {},
      // tempList
      tempList: [],
      // 原始钻孔施工数据列表
      drillingDataList: [],
      // 补钻钻孔施工数据列表
      addDrillingDataList: [],
      addDrillCADVisible: false,
      // 见煤点list
      bottomPointList: [],
      // 止煤点list
      topPointList: [],
      // 钻孔终点list
      finalPointList: [],
      // 抽采半径
      radius: 4.5,
      // 空白带
      emptyBandInfoList: [],
      // 显示外部包围体
      showOutCube: true,
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
      originDrillBeyondCoalMeshList: [],
      originDrillGroupMeshList: [],
      originDrillRangeToSceneList: [],
      originDrillDataList: [],
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
        group: '',
        id: '',
        inclination: '',
        azimuth: '',
        disNav: '',
        disBottom: '',
        disCenter: '',
        seeLen: '',
        overLen: ''
      },
      // 补钻参数表显隐
      addDrillDialogVisible: false,
      exportCADDialogVisible: false,
      addDrillDataVisible: false,
      delAddDrillVisible: false,
      emptyDialogVisible: false,
      calcEmptyBandVisible: false,
      analyseDrillVisible: false,
      addDGLlength: -1,
      delDrill: false,
      addDrillDataNumber: 0,
      // 补钻施工阈值
      addDrillRadius: 5.5,
      addEmptyVolumeThreshold: 0.1,
      calcEmptyVolumeThreshold: 0.1,
      // 工作面列表
      coalMiningWFList: [],
      workingFace: {},
      roadWayList: [],
      roadWay: {},
      navPointList: [],
      navPoint: {},
      rowSpacing: 3,
      //
      groupIndex: 0
    }
  },

  mounted() {
    //this.init()
    this.getCoalMiningWFList()
    window.addEventListener('keydown', this.onKeydown, false)
  },
  destroyed() {
    window.removeEventListener('keydown', event => {
      event.preventDefault()
    })
  },
  methods: {
    // 显示钻孔分析
    showAnalyseDialog() {
      this.analyseDrillVisible = true
    },
    // 补钻
    showAddDrillDialog() {
      this.addDrillDialogVisible = !this.addDrillDialogVisible
      this.calcEmptyBandVisible = false
      this.exportCADDialogVisible = false
      this.emptyDialogVisible = false
    },
    // 导出CAD
    showExportCADDialog() {
      this.exportCADDialogVisible = !this.exportCADDialogVisible
      this.calcEmptyBandVisible = false
      this.emptyDialogVisible = false
      this.addDrillDialogVisible = false
    },
    // 计算空白带
    showCalcEmptyBand() {
      this.calcEmptyBandVisible = !this.calcEmptyBandVisible
      this.emptyDialogVisible = false
      this.exportCADDialogVisible = false
      this.addDrillDialogVisible = false
    },
    changeCoalCubeOpt(opt) {
      this.param.coalCubeOpacity = opt
      params.coalCubeOpacity = opt
    },
    changeCoalPointsScale(scale) {
      this.param.coalPointsScale = scale
      params.coalPointsScale = scale
    },
    changeBottomPointsColor(color) {
      console.log('btmcolor:', color)
      this.param.bottomPointsColor = color
      params.bottomPointsColor = Number('0x' + color.substring(1))
    },
    changeTopPointsColor(color) {
      console.log('topcolor:', color)
      this.param.topPointsColor = color
      params.topPointsColor = Number('0x' + color.substring(1))
    },
    changeBottomLineColor(color) {
      console.log('btmcolor:', color)
      this.param.bottomLineColor = color
      params.bottomLineColor = Number('0x' + color.substring(1))
    },
    changeTopLineColor(color) {
      console.log('topcolor:', color)
      this.param.topLineColor = color
      params.topLineColor = Number('0x' + color.substring(1))
    },
    changeSideLineColor(color) {
      console.log('sidecolor:', color)
      this.param.sideLineColor = color
      params.sideLineColor = Number('0x' + color.substring(1))
    },
    changeCoalCubeColor(color) {
      console.log('coalcubecolor:', color)
      this.param.coalCubeColor = color
      params.coalCubeColor = Number('0x' + color.substring(1))
    },
    changeLineWidth(width) {
      this.param.linewidth = width
      params.linewidth = width
    },
    nextLine() {
      this.groupSpecialSelects = []
      // 修复多跳一级的问题
      if (!this.next) {
        this.groupIndex = this.groupIndex + 2
        this.next = true
        this.before = false
      } else {
        this.next = true
        this.before = false
      }
      if (this.groupIndex > this.groupNoList.length - 1) {
        this.groupIndex = 0
      }
      for (let i = this.groupIndex; i < this.groupNoList.length; i++) {
        if (this.groupSelects.includes(this.groupNoList[i])) {
          this.groupSpecialSelects.push(this.groupNoList[i])
          this.groupIndex = i + 1
          break
        } else {
          this.groupIndex = i - 1
        }
      }
      if (this.groupSpecialSelects.length <= 0) {
        this.groupIndex = 0
        for (let i = this.groupIndex; i < this.groupNoList.length; i++) {
          if (this.groupSelects.includes(this.groupNoList[i])) {
            this.groupSpecialSelects.push(this.groupNoList[i])
            this.groupIndex = i + 1
            break
          } else {
            this.groupIndex = i - 1
          }
        }
      }
      console.log('this.groupSpecialSelects:' + this.groupSpecialSelects)
    },
    beforeLine() {
      this.groupSpecialSelects = []
      // 修复多跳一级的问题
      if (!this.before) {
        this.groupIndex = this.groupIndex - 2
        this.before = true
        this.next = false
      } else {
        this.before = true
        this.next = false
      }
      if (this.groupIndex < 0) {
        this.groupIndex = this.groupNoList.length - 1
      }
      for (let i = this.groupIndex; i < this.groupNoList.length; i--) {
        if (this.groupSelects.includes(this.groupNoList[i])) {
          this.groupSpecialSelects.push(this.groupNoList[i])
          this.groupIndex = i - 1
          break
        } else {
          this.groupIndex = i + 1
        }
      }
      if (this.groupSpecialSelects.length <= 0) {
        this.groupIndex = this.groupNoList.length - 1
        for (let i = this.groupIndex; i < this.groupNoList.length; i--) {
          if (this.groupSelects.includes(this.groupNoList[i])) {
            this.groupSpecialSelects.push(this.groupNoList[i])
            this.groupIndex = i - 1
            break
          } else {
            this.groupIndex = i + 1
          }
        }
      }
      console.log('this.groupSpecialSelects:' + this.groupSpecialSelects)
    },
    // 测量功能
    measure() {

    },

    // 选择显示钻孔
    getOptions(opt) {
      //console.log('opt:', opt)
      this.groupDrillSelects = []
      this.groupSelects = []
      for (let i = 0; i < opt.length; i++) {
        if (opt[i].length > 1) {
          this.groupDrillSelects.push(opt[i][1])
          this.groupSelects.push(opt[i][0])
        }
      }
    },
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
          this.showOrHideOriginDrillInCoal(false)
          this.showOrHideOriginDrill(false)
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
          //camera.lookAt(coalCubeObj)
          // 计算初始化空白带
          //this.calInitResult()
          this.needReCalculate = false
        }
        this.render()
      }

      if (this.needReCalculate) {
        this.showOrHideOriginDrillInCoal(false)
        this.showOrHideOriginDrill(false)
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
        //camera.lookAt(coalCubeObj)
        // 计算初始化空白带
        //this.calInitResult()
        this.needReCalculate = false
      }

    },
    changeRadius(r) {
      this.radius = Number(r)
      this.needReCalculate = true
    },
    changeThreshold(th) {
      this.calcEmptyVolumeThreshold = Number(th)
      this.needReCalculate = true
    },
    sub5(row, column, cellValue) {
      if (cellValue !== null) {
        return cellValue.substring(0, 5)
      }
    },
    fixed2(row, column, cellValue) {
      //console.log(cellValue)
      if (cellValue !== null) {
        let pois = cellValue.split(',')
        return '(' + Number(pois[0]).toFixed(1) + ',' + Number(pois[1]).toFixed(1) + ',' + Number(pois[2]).toFixed(1) + ')'
      } else {
        return '(0,0,0)'
      }
    },
    maxSize(vec3) {
      let max
      if (vec3.x > vec3.y) {
        max = vec3.x
      } else {
        max = vec3.y
      }
      if (max > vec3.z) {
      } else {
        max = vec3.z
      }
      return max
    },
    /**
     * 修改视角到空白带
     * @param row
     */
    seeEmptyBand(row) {
      this.showOrHideEmptyBand(true)
      this.showOrHideEmptyBandCube(false)
      for (let i = 0; i < partMeshs.length; i++) {
        if (partMeshs[i].uuid === row.uuid) {
          //console.log(partCubes[i])
          let ps = []
          const positions = partCubes[i].geometry.getAttribute('position')
          for (let j = 0; j < positions.count; j++) {
            let x = positions.getX(j)
            let y = positions.getY(j)
            let z = positions.getZ(j)
            ps.push(new YUKA.Vector3(x, y, z))
          }
          let box3 = new YUKA.OBB().fromPoints(ps)
          //console.log('box3', box3)
          let size = new THREE.Vector3()
          box3.getSize(size) // 计算包围盒尺寸
          let center = new THREE.Vector3()
          let centerPois = row.position.split(',')
          center.set(Number(centerPois[0]), Number(centerPois[1]), Number(centerPois[2]))

          let max = this.maxSize(size) //包围盒长宽高中最大的一个值，用来表征模型的尺寸
          let cp = new THREE.Vector3().copy(center.clone().addScalar(max))
          cp.y = cp.y + 10
          let ct = new THREE.Vector3().copy(center)
          // 增加动画
          this.initTween(cp, ct, max)
          this.cleanCache(focusBoxMesh)
          focusBoxMesh = partCubes[i].clone()
          focusBoxMesh.visible = true
          scene.add(focusBoxMesh)
          this.tween.start()
        }
      }
      //controls.update()
    },

    /**
     * 添加施工点位名称
     * @param name
     * @returns {Sprite}
     */
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
    makeLabelCanvas(baseWidth, size, name) {
      const borderSize = 2
      const ctx = document.createElement('canvas').getContext('2d')
      const font = `${size}px bold sans-serif`
      ctx.font = font
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
    /**
     * 补空白带设计图
     */
    calcAllAddDrillView() {
      let cadDataList = []
      let finalCadData = {
        exportId: this.workingFace.workfaceName + ': 补空白带设计钻孔图',
        Texts: [],
        Lines: [],
        Circles: [],
        PLines: [],
        AlignedDIMs: [],
        Arcs: []
      }
      for (let i = 0; i < this.addDrillingDataList.length; i++) {
        let cadD = this.calcCADData(this.addDrillingDataList[i], 2)
        cadDataList.push(cadD)
      }

      let baseXOffset = 0
      let baseYOffset = 0
      let xOffs = []
      let yOffs = []
      // 2. 对每一个图纸数据计算偏移量
      for (let i = 0; i < cadDataList.length; i++) {
        let xBorder = this.findXBorder(cadDataList[i])
        let yBorder = this.findYBorder(cadDataList[i])
        xOffs.push(xBorder[1] - xBorder[0])
        yOffs.push(yBorder[1] - yBorder[0])
      }
      let maxXOff = Math.max(...xOffs)
      let maxYOff = Math.max(...yOffs)
      for (let i = 0; i < cadDataList.length; i++) {
        //let xBorder = this.findXBorder(cadDataList[i])
        //let yBorder = this.findYBorder(cadDataList[i])
        //xOffs.push(xBorder[1] - xBorder[0])
        //yOffs.push(yBorder[1] - yBorder[0])
        if (i % 3 === 0) {
          baseXOffset = 0
          //baseYOffset += Math.max(...yOffs) + 10
          baseYOffset -= maxYOff + 10
          xOffs = []
          yOffs = []
        } else {
          //baseXOffset += xBorder[1] - xBorder[0] + 10
          baseXOffset += maxXOff + 10
          baseYOffset += 0
        }
        let originCAD = cadDataList[i]
        // 3. 重新成图
        this.offSetCADData(originCAD, finalCadData, baseXOffset, baseYOffset)
      }
      let title = this.workingFace.workfaceName + ': 补空白带设计钻孔图'
      this.genCADBoard(finalCadData, title, 5)
      return finalCadData
    },
    /**
     * 生成竣工图
     */
    calcAllView() {
      let finalCadData = {
        exportId: this.workingFace.workfaceName + ': ' + this.groupNoList[0] + '-' + this.groupNoList[this.groupNoList.length - 1] + '钻孔竣工图',
        Texts: [],
        Lines: [],
        Circles: [],
        PLines: [],
        AlignedDIMs: [],
        Arcs: []
      }
      let sideView = this.calcAllSideView()
      let topView = this.calcTopView()
      let xBorderS = this.findXBorder(sideView)
      let yBorderS = this.findYBorder(sideView)
      let xBorderT = this.findXBorder(topView)
      let yBorderT = this.findYBorder(topView)
      this.offSetCADData(sideView, finalCadData, -xBorderS[1] - 20, -yBorderS[0] - 20)
      this.offSetCADData(topView, finalCadData, -xBorderT[0] + 20, (-20 + yBorderS[1] - yBorderS[0]) - yBorderT[1])
      return finalCadData
    },
    /**
     * 剖面图
     * @returns
     */
    calcAllSideView() {
      console.log('剖面图')
      let tempGroup = null
      let cadDataList = []
      let finalCadData = {
        exportId: this.workingFace.workfaceName + ': ' + this.groupNoList[0] + '-' + this.groupNoList[this.groupNoList.length - 1] + '钻孔剖面图',
        Texts: [],
        Lines: [],
        Circles: [],
        PLines: [],
        AlignedDIMs: [],
        Arcs: []
      }
      // 1. 把所有图纸数据都先算出来
      for (let i = 0; i < this.drillingDataList.length; i++) {
        if (this.drillingDataList[i].group !== tempGroup) {
          let cadD = this.calcCADData(this.drillingDataList[i], 1)
          cadDataList.push(cadD)
          tempGroup = this.drillingDataList[i].group
        }
      }
      let baseXOffset = 0
      let baseYOffset = 0
      let xOffs = []
      let yOffs = []
      // 2. 对每一个图纸数据计算偏移量
      for (let i = 0; i < cadDataList.length; i++) {
        let xBorder = this.findXBorder(cadDataList[i])
        let yBorder = this.findYBorder(cadDataList[i])
        xOffs.push(xBorder[1] - xBorder[0])
        yOffs.push(yBorder[1] - yBorder[0])
      }
      let maxXOff = Math.max(...xOffs)
      let maxYOff = Math.max(...yOffs)
      for (let i = 0; i < cadDataList.length; i++) {
        //let xBorder = this.findXBorder(cadDataList[i])
        //let yBorder = this.findYBorder(cadDataList[i])
        //xOffs.push(xBorder[1] - xBorder[0])
        //yOffs.push(yBorder[1] - yBorder[0])
        if (i % 6 === 0) {
          baseXOffset = 0
          //baseYOffset += Math.max(...yOffs) + 10
          baseYOffset -= maxYOff + 10
          xOffs = []
          yOffs = []
        } else {
          //baseXOffset += xBorder[1] - xBorder[0] + 10
          baseXOffset += maxXOff + 10
          baseYOffset += 0
        }
        let originCAD = cadDataList[i]
        // 3. 重新成图
        this.offSetCADData(originCAD, finalCadData, baseXOffset, baseYOffset)
      }
      let title = this.workingFace.workfaceName + ': ' + this.groupNoList[0] + '-' + this.groupNoList[this.groupNoList.length - 1] + '煤孔段剖面图'
      this.genCADBoard(finalCadData, title, 5)
      return finalCadData
    },
    /**
     * 对CAD数据进行偏移
     * @param originCAD
     * @param finalCadData
     * @param xOffset
     * @param yOffset
     */
    offSetCADData(originCAD, finalCadData, xOffset, yOffset) {
      this.offsetFun(originCAD, xOffset, yOffset)
      for (let key in originCAD) {
        if (key !== 'exportId') {
          for (let i = 0; i < originCAD[key].length; i++) {
            finalCadData[key].push(originCAD[key][i])
          }
        }
      }
    },
    /**
     * 对CAD数据进行偏移
     * @param originCAD
     * @param xOffset
     * @param yOffset
     */
    offsetFun(originCAD, xOffset, yOffset) {
      for (let key in originCAD) {
        console.log(typeof originCAD[key])
        if (typeof originCAD[key] === 'object') {
          this.offsetFun(originCAD[key], xOffset, yOffset)
        }
        if (key === 'x') {
          originCAD['x'] += xOffset
        } else if (key === 'y') {
          originCAD['y'] += yOffset
        }
      }
    },
    /**
     * 计算顶视图CAD
     * @returns
     */
    calcTopView() {
      let rw = this.roadWay
      let calcData = {
        exportId: this.workingFace.workfaceName + ' 钻孔平面图',
        PLines: [{
          points: [
            {
              x: -(rw.wide / 2.), y: 0
            },
            { x: -(rw.wide / 2.), y: this.roadLen }
          ],
          layer: '底抽巷',
          rgb: '255,140,0',
          width: 0.1
        }, {
          points: [
            {
              x: rw.wide / 2., y: 0
            },
            { x: rw.wide / 2., y: this.roadLen }
          ],
          layer: '底抽巷',
          rgb: '255,140,0',
          width: 0.1
        }],
        Texts: [{
          text: this.roadWay.roadway,
          location: {
            x: 0, y: -2
          },
          height: 0.5,
          vAlign: 3,
          hAlign: 1
        }],
        Lines: [],
        Circles: []
      }
      let drillDataList = this.originDrillDataList
      let tempGroupName = null
      for (let i = 0; i < drillDataList.length; i++) {
        calcData['Lines'].push({
          start: { x: -1 * drillDataList[i].bottom[0], y: drillDataList[i].bottom[1] },
          end: { x: -1 * drillDataList[i].top[0], y: drillDataList[i].top[1] },
          rgb: '255,255,255'
        })
        calcData['Circles'].push({
          center: { x: -1 * drillDataList[i].bottom[0], y: drillDataList[i].bottom[1] },
          radius: 0.5,
          rgb: '255,255,255'
        })
        calcData['Texts'].push({
          text: drillDataList[i].id,
          location: { x: -1 * drillDataList[i].bottom[0], y: drillDataList[i].bottom[1] - 1 },
          height: 0.5,
          vAlign: 3,
          hAlign: 1
        })
        if (drillDataList[i].realGroup !== tempGroupName) {
          calcData['Texts'].push({
            text: drillDataList[i].realGroup,
            location: { x: -1 * drillDataList[i].top[0] + 5, y: drillDataList[i].top[1] },
            color: 100,
            height: 0.5,
            vAlign: 3,
            hAlign: 1
          })
          tempGroupName = drillDataList[i].realGroup
        }
      }
      let title = this.workingFace.workfaceName + ': ' + this.groupNoList[0] + '-' + this.groupNoList[this.groupNoList.length - 1] + '煤孔段平面图'
      /* let xMinMax = this.findXBorder(calcData)
      console.log(xMinMax)
      let yMinMax = this.findYBorder(calcData)
      console.log(yMinMax) */
      this.genCADBoard(calcData, title, 5)

      return calcData
    },
    /**
     * 添加CAD边框
     */
    genCADBoard(calcData, title, margin) {
      let xMinMax = this.findXBorder(calcData)
      console.log(xMinMax)
      let yMinMax = this.findYBorder(calcData)
      console.log(yMinMax)
      calcData['PLines'].push(
          {
            points: [
              { x: xMinMax[0] - margin, y: yMinMax[0] - margin },
              { x: xMinMax[0] - margin, y: yMinMax[1] + margin },
              { x: xMinMax[1] + margin, y: yMinMax[1] + margin },
              { x: xMinMax[1] + margin, y: yMinMax[0] - margin },
              { x: xMinMax[0] - margin, y: yMinMax[0] - margin }
            ],
            layer: '边框'
          }
      )
      calcData['Texts'].push({
        text: title,
        location: { x: (xMinMax[0] + xMinMax[1]) / 2, y: yMinMax[1] + 15 },
        height: 1.5,
        vAlign: 3,
        hAlign: 1
      })
      calcData['Texts'].push({
        text: '比例尺：1:1000',
        location: { x: (xMinMax[0] + xMinMax[1]) / 2, y: yMinMax[1] + 10 },
        height: 1.5,
        vAlign: 3,
        hAlign: 1
      })
    },
    /**
     * 计算CAD数据
     * @param data
     * @param showType
     */
    calcCADData(data, showType) {
      let rw = this.roadWay
      let minX = 9999999
      let minY = 9999999
      let maxX = -9999999
      let maxY = -9999999
      let calcData = {
        PLines: [
          {
            points: [
              {
                x: rw.wide / 2., y: 0
              },
              {
                x: rw.wide / 2., y: -rw.height
              },
              {
                x: -(rw.wide / 2.), y: -rw.height
              },
              {
                x: -(rw.wide / 2.), y: 0
              }
            ],
            layer: '底抽巷',
            rgb: '255,140,0',
            width: 0.1
          }
          /* {
            points: [
              {
                x: 1.8, y: 0
              },
              {
                x: 1.8, y: -2.5
              },
              {
                x: -1.8, y: -2.5
              },
              {
                x: -1.8, y: 0
              }
            ],
            layer: '底抽巷',
            rgb: '255,140,0'
          } */
        ],
        AlignedDIMs: [
          {
            point1: {
              x: -(rw.wide / 2.), y: -rw.height
            },
            point2: {
              x: rw.wide / 2., y: -rw.height
            },
            dimtxt: 0.5,
            dimexe: 0.5,
            dimasz: 0.5,
            distance: 0.5
          },
          {
            point1: {
              x: rw.wide / 2., y: -rw.height
            },
            point2: {
              x: rw.wide / 2., y: 0
            },
            dimtxt: 0.5,
            dimexe: 0.5,
            dimasz: 0.5,
            dimtad: 4,
            distance: -0.5
          }
        ],
        Lines: [],
        Circles: [],
        Arcs: [
          {
            center: {
              x: 0, y: 0
            },
            radius: rw.wide / 2,
            startAngle: 0,
            endAngle: 180,
            layer: '底抽巷',
            rgb: '255,140,0'
          }
        ]
      }
      if (this.roadWay !== null) {
        calcData['Texts'] = [
          {
            text: this.roadWay.roadway,
            location: {
              x: 0, y: -rw.height - 0.5
            },
            height: 0.5,
            vAlign: 3,
            hAlign: 1
          }
        ]
      }

      let drillDataListD = []
      let drillDataListS = []
      let drillDataList = []
      let group = data.group
      if (showType === 1) {
        // 计算煤层, 只考虑单排
        //let drillDataList = []
        //let group = data.group
        for (let i = 0; i < this.originDrillDataList.length; i++) {
          if (this.originDrillDataList[i].group === group) {
            drillDataList.push(this.originDrillDataList[i])
          }
        }
        let points = []
        for (let i = 0; i < drillDataList.length; i++) {
          points.push({ x: -1 * drillDataList[i].bottom[0], y: drillDataList[i].bottom[2] })
        }
        for (let i = drillDataList.length - 1; i >= 0; i--) {
          points.push({ x: -1 * drillDataList[i].top[0], y: drillDataList[i].top[2] })
        }
        points.push({ x: -1 * drillDataList[0].bottom[0], y: drillDataList[0].bottom[2] })
        calcData['PLines'].push({
          points: points,
          layer: '煤层',
          color: 251,
          width: 0.1
        })
      } else {
        // 计算煤层, 考虑两排
        /* let drillDataListD = []
        let drillDataListS = []
        let drillDataList = []
        let group = data.group */
        if (group % 2 === 0) {
          for (let i = 0; i < this.originDrillDataList.length; i++) {
            if (this.originDrillDataList[i].group === group) {
              drillDataListD.push(this.originDrillDataList[i])
            }
            if (this.originDrillDataList[i].group === group + 1) {
              drillDataListS.push(this.originDrillDataList[i])
            }
          }
        } else {
          for (let i = 0; i < this.originDrillDataList.length; i++) {
            if (this.originDrillDataList[i].group === group) {
              drillDataListS.push(this.originDrillDataList[i])
            }
            if (this.originDrillDataList[i].group === group - 1) {
              drillDataListD.push(this.originDrillDataList[i])
            }
          }
        }
        for (let i = 0; i < drillDataListD.length; i++) {
          drillDataList.push(drillDataListD[i])
          if (drillDataListS[i]) {
            drillDataList.push(drillDataListS[i])
          }
        }
        let points = []
        for (let i = 0; i < drillDataList.length; i++) {
          points.push({ x: -1 * drillDataList[i].bottom[0], y: drillDataList[i].bottom[2] })
        }
        for (let i = drillDataList.length - 1; i >= 0; i--) {
          points.push({ x: -1 * drillDataList[i].top[0], y: drillDataList[i].top[2] })
        }
        points.push({ x: -1 * drillDataList[0].bottom[0], y: drillDataList[0].bottom[2] })
        calcData['PLines'].push({
          points: points,
          layer: '煤层',
          color: 251,
          width: 0.1
        })
      }
      if (data.type === 2) {
        calcData['exportId'] = '补空白带设计钻孔-' + data.id
        // 绘制补钻钻孔
        let finalEnd = this.calNewPoint2(-1 * data.scPoint.x, data.scPoint.y, 0, -1 * data.ePoint.x, data.ePoint.y, 0, 0.8)
        calcData['Lines'].push({
          start: {
            x: -1 * data.wPoint.x,
            y: data.wPoint.y
          },
          end: {
            x: -1 * data.scPoint.x,
            y: data.scPoint.y
          },
          rgb: '0,0,255'
        })
        calcData['Lines'].push({
          start: {
            x: -1 * data.scPoint.x,
            y: data.scPoint.y
          },
          end: {
            x: -1 * data.ePoint.x,
            y: data.ePoint.y
          },
          rgb: '255,0,255'
        })
        calcData['Lines'].push({
          start: {
            x: -1 * data.ePoint.x,
            y: data.ePoint.y
          },
          end: {
            x: finalEnd.x,
            y: finalEnd.y
          },
          rgb: '0,0,255'
        })
        calcData['Texts'].push({
          text: '空白带补钻设计-' + data.id,
          location: {
            x: finalEnd.x,
            y: finalEnd.y + 0.5
          },
          height: 0.5,
          vAlign: 0,
          hAlign: 0
        })
      } else {
        if (showType === 1) {
          calcData['exportId'] = data.realGroup + ' 实钻图'
        } else {
          calcData['exportId'] = (data.group % 2 === 0) ? (this.groupNoList[data.group] + ',' + (this.groupNoList[data.group + 1]) + '排实钻图') : (this.groupNoList[(data.group - 1)] + ',' + this.groupNoList[data.group] + '排实钻图')
        }
      }
      // 绘制原始钻孔原始钻孔
      for (let i = 0; i < drillDataList.length; i++) {
        /* let finalEnd = this.calNewPoint2(-1 * drillDataList[i].bottom[0], drillDataList[i].bottom[2], 0, -1 * drillDataList[i].top[0], drillDataList[i].top[2], 0, 0.8)
        // 起点终点一条线
        calcData['Lines'].push({
          start: {
            x: 0,
            y: 0
          },
          end: {
            x: finalEnd.x,
            y: finalEnd.y
          },
          layer: '实钻',
          rgb: '255,0,0'
        }) */

        // 起点终点多条线
        calcData['Lines'].push({
          start: {
            x: 0,
            y: 0
          },
          end: {
            x: -1 * drillDataList[i].bottom[0],
            y: drillDataList[i].bottom[2]
          },
          layer: '实钻',
          rgb: '255,0,0'
        })
        calcData['Lines'].push({
          start: {
            x: -1 * drillDataList[i].bottom[0],
            y: drillDataList[i].bottom[2]
          },
          end: {
            x: -1 * drillDataList[i].top[0],
            y: drillDataList[i].top[2]
          },
          layer: '实钻',
          rgb: '255,0,0'
        })
        let finalEnd = this.calNewPoint2(-1 * drillDataList[i].bottom[0], drillDataList[i].bottom[2], 0, -1 * drillDataList[i].top[0], drillDataList[i].top[2], 0, drillDataList[i].beyondLen)
        calcData['Lines'].push({
          start: {
            x: -1 * drillDataList[i].top[0],
            y: drillDataList[i].top[2]
          },
          end: {
            x: finalEnd.x,
            y: finalEnd.y
          },
          layer: '实钻',
          rgb: '255,0,0'
        })

        // 钻孔名称
        calcData['Texts'].push({
          text: drillDataList[i].realGroup + '-' + drillDataList[i].id,
          location: {
            x: finalEnd.x,
            y: finalEnd.y + 0.5
          },
          layer: '实钻',
          height: 0.5,
          vAlign: 0,
          hAlign: 0
        })

        // 钻孔终点圆圈
        /* calcData['Circles'].push({
              center: {
                x: finalEnd.x,
                y: finalEnd.y
              },
              radius: 0.5
            }
        ) */

        // 煤孔段
        calcData['PLines'].push({
          points: [{
            x: -1 * drillDataList[i].bottom[0],
            y: drillDataList[i].bottom[2]
          }, {
            x: -1 * drillDataList[i].top[0],
            y: drillDataList[i].top[2]
          }],
          layer: '煤孔段',
          color: 251,
          width: 0.2
        })
      }
      console.log('calcData,', calcData)
      let xMinMax = this.findXBorder(calcData)
      console.log(xMinMax)
      let yMinMax = this.findYBorder(calcData)
      console.log(yMinMax)
      this.createElevation(xMinMax, yMinMax, calcData, data)
      return calcData
    },

    /**
     * 创建标尺
     * @param xMinMax
     * @param yMinMax
     * @param calcData
     * @param data
     */
    createElevation(xMinMax, yMinMax, calcData, data) {
      let tMin = Math.ceil(Math.abs(yMinMax[0]) / 10)
      let tMax = Math.ceil(Math.abs(yMinMax[1]) / 10)
      for (let i = 0; i <= tMin; i++) {
        calcData.Lines.push({
          start: {
            x: xMinMax[0] - 5,
            y: -10 * i
          },
          end: {
            x: xMinMax[1] + 5,
            y: -10 * i
          }
        })

        // 左右侧文字
        calcData.Texts.push({
          text: (this.roadWay.elevation + (-10 * i)) > 0 ?
              ('+' + (this.roadWay.elevation + (-10 * i))) :
              ((this.roadWay.elevation + (-10 * i)) + ''),
          location: {
            x: xMinMax[0] - 6,
            y: -10 * i
          },
          height: 0.5,
          vAlign: 0,
          hAlign: 2
        })
        calcData.Texts.push({
          text: (this.roadWay.elevation + (-10 * i)) > 0 ?
              ('+' + (this.roadWay.elevation + (-10 * i))) :
              ((this.roadWay.elevation + (-10 * i)) + ''),
          location: {
            x: xMinMax[1] + 6,
            y: -10 * i
          },
          height: 0.5,
          vAlign: 2,
          hAlign: 0
        })
      }

      for (let i = 0; i <= tMax; i++) {
        calcData.Lines.push({
          start: {
            x: xMinMax[0] - 5,
            y: 10 * i
          },
          end: {
            x: xMinMax[1] + 5,
            y: 10 * i
          }
        })

        // 左右侧文字
        calcData.Texts.push({
          text: (this.roadWay.elevation + (10 * i)) > 0 ? ('+' + (this.roadWay.elevation + (10 * i))) : ((this.roadWay.elevation + (10 * i)) + ''),
          location: {
            x: xMinMax[0] - 6,
            y: 10 * i
          },
          height: 0.5,
          vAlign: 0,
          hAlign: 2
        })
        calcData.Texts.push({
          text: (this.roadWay.elevation + (10 * i)) > 0 ? ('+' + (this.roadWay.elevation + (10 * i))) : ((this.roadWay.elevation + (10 * i)) + ''),
          location: {
            x: xMinMax[1] + 6,
            y: 10 * i
          },
          height: 0.5,
          vAlign: 2,
          hAlign: 0
        })
      }

      // 左侧竖线
      calcData.Lines.push({
        start: {
          x: xMinMax[0] - 5,
          y: -10 * tMin
        },
        end: {
          x: xMinMax[0] - 5,
          y: 10 * tMax
        }
      })
      calcData.Lines.push({
        start: {
          x: xMinMax[0] - 4,
          y: -10 * tMin
        },
        end: {
          x: xMinMax[0] - 4,
          y: 10 * tMax
        }
      })
      calcData.Lines.push({
        start: {
          x: xMinMax[0] - 3,
          y: -10 * tMin
        },
        end: {
          x: xMinMax[0] - 3,
          y: 10 * tMax
        }
      })

      // 右侧竖线
      calcData.Lines.push({
        start: {
          x: xMinMax[1] + 5,
          y: -10 * tMin
        },
        end: {
          x: xMinMax[1] + 5,
          y: 10 * tMax
        }
      })
      calcData.Lines.push({
        start: {
          x: xMinMax[1] + 4,
          y: -10 * tMin
        },
        end: {
          x: xMinMax[1] + 4,
          y: 10 * tMax
        }
      })
      calcData.Lines.push({
        start: {
          x: xMinMax[1] + 3,
          y: -10 * tMin
        },
        end: {
          x: xMinMax[1] + 3,
          y: 10 * tMax
        }
      })

      let xxMinMax = this.findXBorder(calcData)
      console.log(xxMinMax)
      let yyMinMax = this.findYBorder(calcData)
      console.log(yyMinMax)

      //标题文字
      calcData.Texts.push({
        text: this.workingFace.workfaceName + ' : ' + calcData.exportId,
        location: {
          x: (xxMinMax[0] + xxMinMax[1]) / 2,
          y: yyMinMax[1] + 10
        },
        height: 1.5,
        vAlign: 3,
        hAlign: 1
      })

      //标题文字
      calcData.Texts.push({
        text: '比例尺：1:1000',
        location: {
          x: (xxMinMax[0] + xxMinMax[1]) / 2,
          y: yyMinMax[1] + 5
        },
        height: 1.5,
        vAlign: 3,
        hAlign: 1
      })
    },
    /**
     * 获取X边框
     * @param cadData
     * @returns {*[]}
     */
    findXBorder(cadData) {
      this.xData = []
      let xFlatData = this.flatten(cadData)
      let keysX = Object.keys(xFlatData)
      for (let i = 0; i < keysX.length; i++) {
        if (keysX[i].indexOf('.x') !== -1) {
          this.xData.push(xFlatData[keysX[i]])
        }
      }
      let xBorder = []
      xBorder.push(Math.min(...this.xData))
      xBorder.push(Math.max(...this.xData))
      return xBorder
    },
    /**
     * 获取Y边框
     * @param cadData
     * @returns {*[]}
     */
    findYBorder(cadData) {
      this.yData = []
      let yFlatData = this.flatten(cadData)
      let keysY = Object.keys(yFlatData)
      for (let i = 0; i < keysY.length; i++) {
        if (keysY[i].indexOf('.y') !== -1) {
          this.yData.push(yFlatData[keysY[i]])
        }
      }
      let yBorder = []
      yBorder.push(Math.min(...this.yData))
      yBorder.push(Math.max(...this.yData))
      return yBorder
    },
    /**
     * 遍历铺平数据
     * @param currentNode
     * @param target
     * @param flattenedKey
     */
    traverseAndFlatten(currentNode, target, flattenedKey) {
      for (let key in currentNode) {
        if (currentNode.hasOwnProperty(key)) {
          let newKey
          if (flattenedKey === undefined) {
            newKey = key
          } else {
            newKey = flattenedKey + '.' + key
          }
          let value = currentNode[key]
          if (typeof value === 'object') {
            this.traverseAndFlatten(value, target, newKey)
          } else {
            target[newKey] = value
          }
        }
      }
    },
    /**
     * 铺平结构
     * @param obj
     * @returns {{}}
     */
    flatten(obj) {
      let flattenedObject = {}
      this.traverseAndFlatten(obj, flattenedObject)
      return flattenedObject
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
    exportCAD() {
      if (this.cadData !== null) {
        cadExport(this.cadData, this.cadData.exportId + '.dwg')
      }
    },
    /**
     * 导出设计钻孔
     */
    exportCADByData(data, showType) {
      console.log(data)
      this.cadData = this.calcCADData(data, showType)
      cadExport(this.cadData, this.cadData.exportId + '.dwg')
    },
    exportDrillData() {
      this.download()
    },
    download() {
      let tableExcel = []
      this.addDrillingDataList.map(item => {
        tableExcel.push({
          '最近列号': item.group,
          '孔号': item.id,
          '方位角(°)': item.azimuth,
          '倾角(°)': item.inclination,
          '距导线点(m)': item.disNav,
          '距顶板(m)': item.disBottom,
          '距中心线(m)': item.disCenter,
          '见煤深度(m)': item.seeLen,
          '止煤深度(m)': item.overLen
        })
      })
      this.exportToExcel(tableExcel)
      console.log(tableExcel)
    }
    ,
    exportToExcel(tableExcel) {
      let option = {}
      option.fileName = '空白带补钻_施工参数表'
      option.datas = [
        {
          sheetData: tableExcel,
          sheetName: '施工参数表',
          sheetFilter: [
            '最近列号',
            '序号',
            '方位角(°)',
            '倾角(°)',
            '距导线点(m)',
            '距顶板(m)',
            '距中心线(m)',
            '见煤深度(m)',
            '止煤深度(m)'
          ],
          sheetHeader: [
            '最近列号',
            '序号',
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
      let toExcel = new ExportJsonExcel(option)
      toExcel.saveExcel()
    }
    ,

    changeRenderNum() {
      this.needReCalculate = true
    }
    ,
    async selectWorkingFace(id) {
      this.workingFace = null
      this.roadWayList = []
      this.navPointList = []
      this.roadWay = null
      this.navPoint = null
      this.queryParams.roadwayId = null
      this.queryParams.navPointId = null
      for (let i = 0; i < this.coalMiningWFList.length; i++) {
        if (this.coalMiningWFList[i].id === id) {
          this.workingFace = this.coalMiningWFList[i]
          await gRoadWayByWorkFace(this.workingFace.id).then((response) => {
            console.log('gRoad', response)
            this.roadWayList = response.data
            this.queryParams.roadwayId = 115
            this.selectRoadway(115)
          })
          this.needReCalculate = true
          break
        }
      }
    }
    ,
    selectRoadway(id) {
      this.navPointList = []
      this.navPoint = null
      this.queryParams.navPointId = null
      for (let i = 0; i < this.roadWayList.length; i++) {
        if (this.roadWayList[i].id === id) {
          this.roadWay = this.roadWayList[i]
          this.navPointList = this.roadWay.tnavigationPointList
          this.baseData.azimuth = Number(this.roadWay.azimuthAngle)
          this.baseData.inclination = Number(this.workingFace.inclinationAngle)
          this.needReCalculate = true

          this.queryParams.navPointId = 94
        }
      }
    }
    ,
    selectNavPoint(id) {
      for (let i = 0; i < this.navPointList.length; i++) {
        if (this.navPointList[i].id === id) {
          this.navPoint = this.navPointList[i]
          this.baseData.navX = Number(this.navPoint.x)
          this.baseData.navY = Number(this.navPoint.y)
          this.baseData.navZ = Number(this.navPoint.z)
          console.log('this.baseData:', this.baseData)
          this.needReCalculate = true
        }
      }
    }
    ,
    getCoalMiningWFList() {
      //this.loading = true;
      listCoalminingworkingface({}).then(response => {
        this.coalMiningWFList = response.rows
        this.queryParams.workfaceId = 23
        this.selectWorkingFace(23)
      })
    }
    ,
    showOrHideTop(status) {
      this.param.showCoalTopMesh = status
      params.showCoalTopMesh = status
    }
    ,
    showOrHideBottom(status) {
      this.param.showCoalBottomMesh = status
      params.showCoalBottomMesh = status
    }
    ,
    showOrHideCoal(status) {
      this.param.showCoalCube = status
      params.showCoalCube = status
    }
    ,
    showOrHideCoalPoints(status) {
      this.param.showCoalPoints = status
      params.showCoalPoints = status
    }
    ,
    showOrHideRoad(status) {
      this.param.showRoad = status
      params.showRoad = status
    }
    ,
    showOrHideWorkPoint(status) {
      this.param.showWorkPoint = status
      params.showWorkPoint = status
    },
    showOrHideCoalLine(status) {
      this.param.showCoalLine = status
      params.showCoalLine = status
    }
    ,
    showOrHideSpecialCoalLine(status) {
      this.param.showSpCoalLine = status
      params.showSpCoalLine = status
    }
    ,
    showOrHideOriginDrill(status) {
      this.param.showOriginDrill = status
      params.showOriginDrill = status
    }
    ,
    showOrHideOriginDrillInCoal(status) {
      this.param.showOriginDrillInCoal = status
      params.showOriginDrillInCoal = status
    }
    ,
    showOrHideOriginDrillRange(status) {
      this.param.showOriginDrillRange = status
      params.showOriginDrillRange = status
    }
    ,
    showOrHideAddDrill(status) {
      this.param.showAddDrill = status
      params.showAddDrill = status
    }
    ,
    showOrHideAddDrillInCoal(status) {
      this.param.showAddDrillInCoal = status
      params.showAddDrillInCoal = status
    },
    showOrHideOriginDrillBeyondCoal(status) {
      this.param.showOriginDrillBeyondCoal = status
      params.showOriginDrillBeyondCoal = status
    }
    ,
    showOrHideAddDrillRange(status) {
      this.param.showAddDrillRange = status
      params.showAddDrillRange = status
    }
    ,

    showOrHideOriginEmptyBand(status) {
      this.param.showOriginEmptyBand = status
      params.showOriginEmptyBand = status
    }
    ,
    showOrHideEmptyBand(status) {
      this.param.showEmptyBand = status
      params.showEmptyBand = status

      if (!status) {
        this.showOrHideEmptyBandCube(false)
      }
    }
    ,

    showOrHideEmptyBandCube(status) {
      this.cleanCache(focusBoxMesh)
      this.param.showOutCube = status
      params.showOutCube = status
    }
    ,

    changePage(page) {
      this.addDrillDialogVisible = false
      this.calcEmptyBandVisible = false
      this.exportCADDialogVisible = false
      this.emptyDialogVisible = false
      if (page === 1) {
        this.showThree = false
        this.showDesc = false
        if (this.addDrillNotify !== null) {
          this.addDrillNotify.close()
          this.addDrillNotify = null
        }
        this.exportCADDialogVisible = false
        this.calcEmptyBandVisible = false
        this.emptyDialogVisible = false
        this.addDrillDialogVisible = false
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
    }
    ,

    clearDrillList() {
      this.drillingDataList = []
      this.needReCalculate = true
    }
    ,

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
    }
    ,

    projection(v, u) {
      let scalarProduct = this.scalarProduct(v, u)
      let magnitudeSquared = this.magnitudeSquared(u)
      return [scalarProduct * u[0] / magnitudeSquared, scalarProduct * u[1] / magnitudeSquared, scalarProduct * u[2] / magnitudeSquared]
    }
    ,

    scalarProduct(v1, v2) {
      return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2]
    }
    ,

    magnitudeSquared(vector) {
      return vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]
    }
    ,

    /**
     * 自动补钻模式切换
     * @param mode
     */
    desModStatChange(mode) {
      //params.drillDesMode = mode
      this.addDrillForm.drillDesMode = mode
    },
    /**
     * 清空测量
     */
    cleanMeasure() {
      for (let i = 0; i < measurePointList.length; i++) {
        this.cleanCache(measurePointList[i])
      }
      measurePointList = []
      for (let i = 0; i < measureLabelList.length; i++) {
        this.cleanCache(measureLabelList[i])
      }
      measureLabelList = []
      for (let i = 0; i < measureLineList.length; i++) {
        this.cleanCache(measureLineList[i])
      }
      measureLineList = []
      let activeLine = scene.getObjectByName('active-line')
      if (activeLine) {
        this.cleanCache(activeLine)
      }
      scene.remove(tempLabel)
      tempLabel = undefined
    },
    /**
     * 开启测量模式
     * @param mode 0=距离测量,1=面积测量,2=面积测量,-1=退出测量
     */
    startMeasureMode(mode) {
      const h = this.$createElement
      console.log('mode:', mode)
      this.measureMode = mode
      if (mode === 0) {
        this.measureNotify = this.$notify({
          title: '距离测量模式',
          message: h('p', null, [
            h('span', null, '鼠标左键单击模型，依次选择测量位置'),
            h('br', null, ''),
            h('a', {
              style: 'color:green;float:left;',
              on: {
                click: () => {
                  this.cleanMeasure()
                }
              }
            }, '清空测量'),
            h('a', {
              style: 'color:green;float:right;',
              on: {
                click: () => {
                  this.startMeasureMode(-1)
                }
              }
            }, '退出测量')
          ]),
          duration: 0,
          offset: 180,
          position: 'top-left'
        })
        document.removeEventListener('keydown', this.measureNotify.keydown)
      } else if (mode === 1) {
        this.measureNotify = this.$notify({
          title: '面积测量模式',
          message: h('p', null, [
            h('span', null, '1. 鼠标左键单击模型选择测量位置'),
            h('br', null, ''),
            h('span', null, '2. 鼠标右键单击结束测量'),
            h('br', null, ''),
            h('a', {
              style: 'color:green;',
              on: {
                click: () => {
                  this.startMeasureMode(-1)
                }
              }
            }, '退出测量')
          ]),
          duration: 0,
          offset: 180,
          position: 'top-left'
        })
        document.removeEventListener('keydown', this.measureNotify.keydown)
      }else if (mode === 2) {
        this.measureNotify = this.$notify({
          title: '角度测量模式',
          message: h('p', null, [
            h('span', null, '鼠标左键单击模型，依次选择测量位置'),
            h('br', null, ''),
            h('a', {
              style: 'color:green;float:left;',
              on: {
                click: () => {
                  this.cleanMeasure()
                }
              }
            }, '清空测量'),
            h('a', {
              style: 'color:green;float:right;',
              on: {
                click: () => {
                  this.startMeasureMode(-1)
                }
              }
            }, '退出测量')
          ]),
          duration: 0,
          offset: 180,
          position: 'top-left'
        })
        document.removeEventListener('keydown', this.measureNotify.keydown)
      } else {
        console.log('退出量测')
        this.cleanMeasure()
        if (this.measureNotify !== null) {
          this.measureNotify.close()
          this.measureNotify = null
        }
      }
    },
    /**
     * 手动补钻开启/关闭
     * @param mode
     */
    startDesMode(mode) {
      this.addDrillForm.startAddDrill = mode
      if (mode) {
        this.showOrHideCoal(false)
        this.showOrHideWorkPoint(true)
        if (this.addDrillForm.workPlace === 1) {
          this.addDrillNotify = this.$notify({
            title: '开始手动补钻模式',
            message: '1. 鼠标左键点选空白带，确定钻孔终点位置\n2. 点选施工位置，确定钻孔起点',
            duration: 0,
            offset: 180,
            position: 'top-left'
          })
        } else {
          this.addDrillNotify = this.$notify({
            title: '开始手动补钻模式',
            message: '鼠标左键点选空白带，确定钻孔终点位置，将自动生成补钻钻孔',
            duration: 0,
            offset: 180,
            position: 'top-left'
          })
        }
      } else {
        if (this.addDrillNotify !== null) {
          this.addDrillNotify.close()
          this.addDrillNotify = null
        }
        this.exportCADDialogVisible = false
        this.calcEmptyBandVisible = false
        this.emptyDialogVisible = false
        this.addDrillDialogVisible = false
        this.showOrHideWorkPoint(true)
        this.showOrHideRoad(true)
        this.showAddDrillData()
      }
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
        group: '',
        id: '',
        inclination: '',
        azimuth: '',
        disNav: '',
        disBottom: '',
        disCenter: '',
        seeLen: '',
        overLen: ''
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
    }
    ,

    /**
     * 清理缓存
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
    }
    ,

// 显示补钻参数表
    showAddDrillData() {
      this.addDrillDataVisible = true
    }
    ,
// 显示空白带参数表
    showEmptyBandData() {
      this.emptyDialogVisible = true
    }
    ,
// 删除钻孔数据
    delDrillData(row) {
      console.log(row)
      this.drillingDataList.splice(row.$index, 1)
    }
    ,
// 更新钻孔数据
    updateDrillData(row) {
      row.group = Number(row.group)
      row.disNav = Number(row.disNav)
      row.disCenter = Number(row.disCenter)
      row.disBottom = Number(row.disBottom)
      row.azimuth = Number(row.azimuth)
      row.inclination = Number(row.inclination)
      row.kg = Number(row.kg)
      row.overLen = Number(row.overLen)
      row.seeLen = Number(row.seeLen)
      console.log(row)
      row.disabled = true
      this.needReCalculate = true
    }
    ,
    addSingleDrillData() {
      let obj = {
        group: 0,
        id: 0,
        disNav: 0,
        disCenter: 0,
        disBottom: 0,
        azimuth: 0,
        inclination: 0,
        kg: 0,
        overLen: 0,
        seeLen: 0,
        disabled: false,
        type: 1
      }
      this.drillingDataList.push(obj)
    }
    ,
    getMyExcel(file, fileList) {
      let _this = this
      // 用FileReader来完成数据流的转换
      let reader = new FileReader()
      reader.onload = function(e) {
        let data = e.target.result
        //console.log(XLSX)
        //通过XLSX来解析出workbook（工作簿）也就导入的excel文件
        let wb = XLSX.read(data, { type: 'binary' })
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
        for (let i = 2; i <= sortedList[0]; i++) {
          let obj = {
            realGroup: (wb.Sheets['Sheet1']['A' + i]['v'] + ''),
            id: wb.Sheets['Sheet1']['B' + i]['v'],
            disNav: wb.Sheets['Sheet1']['C' + i]['v'],
            disCenter: Number(wb.Sheets['Sheet1']['D' + i]['v']).toFixed(2),
            disBottom: Number(wb.Sheets['Sheet1']['E' + i]['v']).toFixed(2),
            azimuth: Math.abs(Number(wb.Sheets['Sheet1']['F' + i]['v'])).toFixed(2),
            inclination: Number(wb.Sheets['Sheet1']['G' + i]['v']).toFixed(2),
            kg: Number(wb.Sheets['Sheet1']['H' + i]['v']).toFixed(2),
            seeLen: Number(wb.Sheets['Sheet1']['I' + i]['v']).toFixed(2),
            overLen: Number(wb.Sheets['Sheet1']['J' + i]['v']).toFixed(2),
            beyondLen: Number(wb.Sheets['Sheet1']['K' + i]['v']).toFixed(2),
            disabled: true,// 代表非编辑状态
            type: 1 // 1代表实钻钻孔
          }
          _this.drillingDataList.push(obj)
        }
        /* for (let i = 5; i <= sortedList[0]; i++) {
          let obj = {
            group: Number((wb.Sheets['11104工作面底板穿层钻孔台账']['A' + i]['v'] + '').replaceAll('列', '')) - 63,
            id: wb.Sheets['11104工作面底板穿层钻孔台账']['B' + i]['v'],
            disNav: base + ((Number((wb.Sheets['11104工作面底板穿层钻孔台账']['A' + i]['v'] + '').replaceAll('列', '')) - 63) * _this.rowSpacing),
            disCenter: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['J' + i]['v']).toFixed(2),
            disBottom: Number(wb.Sheets['11104工作面底板穿层钻孔台账']['K' + i]['v']).toFixed(2),
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
        } */
        console.log('共导入施工数据:' + _this.drillingDataList.length + '条')
      }
      //readAsBinaryString需要File 或 Blob 对象的参数
      reader.readAsBinaryString(file.raw)
      // console.log(XLSX.read(file, { type: "array" }));
    },
    /**
     * @param index
     * 选择施工位置
     */
    changeWP(index) {
      console.log(index)
      for (let i = 0; i < this.workPointsMeshList.length; i++) {
        this.workPointsMeshList[i].material.color.set(0xffffff)
      }
      this.workPointsMeshList[index].material.color.set(0xff00ff)
    },
    /**
     * 手动补钻
     */
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
        this.showOrHideAddDrillRange(true)
        this.showOrHideAddDrillInCoal(true)
        this.showOrHideAddDrill(true)
        this.addDrill()
      }, 1000)
    },
    /**
     * 自动补钻
     */
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
        this.showOrHideAddDrillRange(true)
        this.showOrHideAddDrillInCoal(true)
        this.showOrHideAddDrill(true)
        this.autoAddDrill()
        this.startDesMode(false)
      }, 1000)
    },
    /**
     * todo
     * 针对单个空白补钻，未实现
     */
    autoAddDrillForSelected() {
      /**
       * 1. 知道补的是哪块儿
       * 2. 知道一次计算完之后, 这一块儿剩余的部分
       * 3. 对剩余部分再进行迭代
       *
       *
       * -- 折中, 阈值生效的情况下, 每次补钻只补一根, 再根据计算完之后的体积来确定要不要补下一次
       */
    },
    /**
     * 自动补钻
     */
    autoAddDrill() {
      let needStop = false
      if (this.addDrillRadius > 0.2) {
        for (let i = 0; i < partMeshs.length; i++) {
          let vol = computeMeshVolume(partMeshs[i])
          if (vol > this.addEmptyVolumeThreshold) {
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
              for (let j = 0; j < this.workPoints.length; j++) {
                // 确定出施工点位置
                if (this.workPoints[j].group === Number(targetGroup)) {
                  console.log('施工点位:', this.workPoints[j])
                  wkPoint = this.workPoints[j]
                  break
                }
              }
              if (wkPoint !== null) {
                let nearestGroup = wkPoint.group
                this.addDrillToCal(new THREE.Vector3(wkPoint.x, wkPoint.z, wkPoint.y), endPoint, '补钻-' + uuidv4().replaceAll('-', ''), nearestGroup)
              }
            } else {
              let closestPoint = this.closestPoint(endPoint.x, endPoint.y, endPoint.z,
                  this.workPoints[0].x, this.workPoints[0].z, this.workPoints[0].y,
                  this.workPoints[this.workPoints.length - 1].x, this.workPoints[this.workPoints.length - 1].z, this.workPoints[this.workPoints.length - 1].y)
              wkPoint = new THREE.Vector3(closestPoint[0], closestPoint[1], closestPoint[2])
              let nearestGroup = -1
              let nearestDis = 99999999
              for (let j = 0; j < this.workPoints.length; j++) {
                let dis = this.calDistance(this.workPoints[j].x, this.workPoints[j].z, this.workPoints[j].y, wkPoint.x, wkPoint.y, wkPoint.z)
                if (dis <= nearestDis) {
                  nearestDis = dis
                  nearestGroup = this.workPoints[j].group
                }
              }
              if (wkPoint !== null) {
                this.addDrillToCal(wkPoint, endPoint, '补钻-' + uuidv4().replaceAll('-', ''), nearestGroup)
              }
            }
          } else {
            needStop = true
          }
        }
      } else {
        this.autoAdd = false
        this.$message.error('请输入正确的抽采半径')
      }
      this.stopLoading = true
    },
    /**
     * 计算距离值
     */
    calDistance(x1, y1, z1, x2, y2, z2) {
      return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1))
    }
    ,

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
    }
    ,

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
    }
    ,

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

      //const capGeo = new Operation(new THREE.CapsuleGeometry(radius, dis, 6, 16), material)
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
     * @param nGroup
     */
    addDrillToCal(startPoint, endPoint, name, nGroup) {
      let sp1 = startPoint
      let sp = startPoint
      let ep = endPoint

      let rayToBottom = new THREE.Raycaster(new THREE.Vector3(sp.x, sp.y, sp.z), new THREE.Vector3(ep.x - sp.x, ep.y - sp.y, ep.z - sp.z).normalize())
      let bottomIntersect = rayToBottom.intersectObject(coalCubeObj)
      if (bottomIntersect.length > 0) {
        let bottom = bottomIntersect[0]  //取第一个点位, 入煤点
        sp = new THREE.Vector3(bottom.point.x, bottom.point.y, bottom.point.z)
        if (bottomIntersect.length > 1) {
          let top = bottomIntersect[1]   //取第二个点位, 出煤点
          ep = new THREE.Vector3(top.point.x, top.point.y, top.point.z)
        }

        // 添加补钻钻孔,煤孔段
        this.addDrillMesh(sp1, sp, 0.2, addDrillMat, this.addDrillMeshList, name)
        this.addDrillMesh(sp, ep, 0.2, drillInCoalMat, this.addDrillInCoalMeshList, name)

        // 添加施工数据
        this.calDrillData(sp1, sp, ep, name, nGroup)

        // 添加补孔钻孔范围,计算,场景
        this.addDrillRangeMeshToCalc(sp, ep, this.addDrillRadius, addDrillRangeMat, partsRoot, this.addDrillGroupMeshList, name)
        this.addDrillRangeMeshToScene(sp, ep, this.addDrillRadius, addDrillRangeMat, true, this.addDrillRangeToSceneList, name)
        if (this.clickPoint != null) {
          this.clickPoint.geometry.dispose()
          this.clickPoint.parent.remove(this.clickPoint)
          this.clickPoint = null
        }
      }
      this.needReRender = true
    }
    ,
    /**
     * 计算施工参数
     * @param workPoint
     * @param seeCoalPoint
     * @param overCoalPoint
     * @param name
     * @param nGroup
     */
    calDrillData(workPoint, seeCoalPoint, overCoalPoint, name, nGroup) {
      let rw = this.roadWay
      let addDrillData = {
        group: '',
        id: '',
        inclination: '',
        azimuth: '',
        disNav: '',
        disBottom: '',
        disCenter: '',
        seeLen: '',
        overLen: '',
        type: 2,
        wPoint: workPoint,
        scPoint: seeCoalPoint,
        ePoint: overCoalPoint
      }
      let disNav = this.calDistance(workPoint.x, workPoint.y, workPoint.z, this.baseData.navX, this.baseData.navY, this.baseData.navZ)
      let seeCoalLen = this.calDistance(workPoint.x, workPoint.y, workPoint.z, seeCoalPoint.x, seeCoalPoint.y, seeCoalPoint.z)
      let overCoalLen = this.calDistance(workPoint.x, workPoint.y, workPoint.z, overCoalPoint.x, overCoalPoint.y, overCoalPoint.z)
      let inclAng = this.rad2ang(Math.atan((seeCoalPoint.y - workPoint.y) / (this.calDistance(seeCoalPoint.x, 0, seeCoalPoint.z, workPoint.x, 0, workPoint.z))))
      //let azimAng = this.rad2ang(Math.atan(seeCoalPoint.x / seeCoalPoint.z))
      let azimAng = 90 - this.angleBetweenVectors([seeCoalPoint.x - workPoint.x, seeCoalPoint.y - workPoint.y, seeCoalPoint.z - workPoint.z],
          [this.workPoints[this.workPoints.length - 1].x - this.workPoints[0].x, this.workPoints[this.workPoints.length - 1].y - this.workPoints[0].y, this.workPoints[this.workPoints.length - 1].z - this.workPoints[0].z])
      addDrillData.group = nGroup
      addDrillData.id = this.addDrillDataNumber
      addDrillData.inclination = inclAng.toFixed(2)
      addDrillData.azimuth = azimAng.toFixed(2)
      addDrillData.disNav = disNav.toFixed(2)
      addDrillData.disBottom = ((rw.wide / 2) * Math.sin(this.ang2rad(inclAng))).toFixed(2)
      addDrillData.disCenter = ((rw.wide / 2) * Math.cos(this.ang2rad(inclAng))).toFixed(2)
      addDrillData.seeLen = seeCoalLen.toFixed(2)
      addDrillData.overLen = overCoalLen.toFixed(2)
      addDrillData.name = name
      this.addDrillingDataList.push(addDrillData)
      // 补钻计数+1
      this.addDrillDataNumber++
    }
    ,
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
    }
    ,

    /**
     * 手动补钻
     */
    addDrill() {
      //let sp = new THREE.Vector3(this.workPoints[this.startPointId].x, this.workPoints[this.startPointId].z, this.workPoints[this.startPointId].y)
      if (this.addDrillRadius > 0.2) {
        let sp = this.startPoint
        let ep = this.endPoint
        let nearestGroup = -1
        let nearestDis = 99999999
        for (let j = 0; j < this.workPoints.length; j++) {
          let dis = this.calDistance(this.workPoints[j].x, this.workPoints[j].z, this.workPoints[j].y, sp.x, sp.y, sp.z)
          if (dis <= nearestDis) {
            nearestDis = dis
            nearestGroup = this.workPoints[j].group
          }
        }
        this.addDrillToCal(sp, ep, '补钻-' + uuidv4().replaceAll('-', ''), nearestGroup)
      } else {
        this.$message.error('请输入正确的抽采半径')
      }
    }
    ,
    /**
     * 创建点位
     * @param pos
     * @param config
     * @returns {Mesh<SphereGeometry, MeshBasicMaterial>}
     */
    createPoint(pos, config = { color: 0x009bea, size: 0.3 }) {
      let mat = new THREE.MeshBasicMaterial({ color: config.color || 0x009bea })
      let sphereGeometry = new THREE.SphereGeometry(config.size || 0.3, 32, 32)
      let sphere = new THREE.Mesh(sphereGeometry, mat)
      sphere.position.set(pos.x, pos.y, pos.z)
      return sphere
    },
    createLine(p1, p2, config = { color: 0x009bea }) {
      const points = []
      points.push(p1, p2)
      let lineGeometry = new THREE.BufferGeometry().setFromPoints(points)
      let lineMaterial = new THREE.LineBasicMaterial({ color: config.color })

      let line = new THREE.Line(lineGeometry, lineMaterial)
      return line
    },
    getIntersects(event) {
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
      raycaster.camera = camera
      let intersects = raycaster.intersectObject(coalCubeObj)
      if (intersects.length > 0) {
        return intersects[0]
      } else {
        return null
      }
    },
    onMousedown(event) {
      this.mouseMoved = false
    },
    onKeydown(event) {
      if (event.keyCode === 27) {
        //esc
        if (this.measureMode !== -1) {
          // 取消上一个点位
          console.log('escesc')
          if (measurePointList.length >= 2) {
            this.cleanCache(measurePointList[measurePointList.length - 1])
            measurePointList.pop()
            this.cleanCache(measureLabelList[measureLabelList.length - 1])
            measureLabelList.pop()
            this.cleanCache(measureLineList[measureLineList.length - 1])
            measureLineList.pop()
            scene.remove(tempLabel)
            tempLabel = undefined
            let activeLine = scene.getObjectByName('active-line')
            if (activeLine) {
              let positionAttr = activeLine.geometry.getAttribute('position')
              positionAttr.setXYZ(0, measurePointList[measurePointList.length - 1].position.x, measurePointList[measurePointList.length - 1].position.y, measurePointList[measurePointList.length - 1].position.z)
              positionAttr.needsUpdate = true
            }
          } else {
            this.cleanMeasure()
          }
          event.preventDefault()
        }
      }
    },
    onRightClick(param) {
      this.showDesc = false
      outlinePass.selectedObjects = []
    },
    onMouseup(event) {
      if (!this.mouseMoved) {
        this.onClick(event)
      }
    },
    onMouseMove(event) {
      this.mouseMoved = true
      //console.log('event:', event)
      let activePoint = scene.getObjectByName('active-point')
      let activeLine = scene.getObjectByName('active-line')

      if (this.measureMode !== -1) {
        let obj = this.getIntersects(event)
        // 若点击位置不为空且场景中不存在活动的点，那么创建活动的点
        if (obj && !activePoint) {
          activePoint = this.createPoint(obj.point)
          activePoint.material.transparent = true
          activePoint.material.opacity = 0.6
          activePoint.name = 'active-point'
          scene.add(activePoint)
        } else if (obj) {
          // 更新点位置
          activePoint.position.set(obj.point.x, obj.point.y, obj.point.z)
          if (activeLine) {
            // 已经存在测量线，更新测量线
            let positionAttr = activeLine.geometry.getAttribute('position')
            positionAttr.setXYZ(1, obj.point.x, obj.point.y, obj.point.z)
            positionAttr.needsUpdate = true
            if (measurePointList.length >= 1 && this.measureMode === 0) {
              let p1 = measurePointList[measurePointList.length - 1]
              let dis = this.calDistance(p1.position.x, p1.position.y, p1.position.z, obj.point.x, obj.point.y, obj.point.z).toFixed(2)
              let tempPosition = new THREE.Vector3((p1.position.x + obj.point.x) / 2, (p1.position.y + obj.point.y) / 2, (p1.position.z + obj.point.z) / 2)
              this.addOrUpdateTempLabel(dis + ' m', tempPosition)
            }
          } else {
            // 不存在测量线，创建测量线
            if (measurePointList.length >= 1) {
              let p1 = measurePointList[measurePointList.length - 1]
              activeLine = this.createLine(p1.position, obj.point)
              activeLine.name = 'active-line'
              scene.add(activeLine)
            }
          }
        } else if (activePoint) {    // 否则点击位置为空且存在活动的点，清除活动的点
          scene.remove(activePoint)
        }
      } else if (activePoint) {    // 否则点击位置为空且存在活动的点，清除活动的点
        scene.remove(activePoint)
      }
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

      if (!this.addDrillCADVisible) {
        if (this.measureMode !== -1) {
          // 测量模式
          let intersects = raycaster.intersectObject(coalCubeObj)
          if (intersects.length > 0) {
            let measurePointMesh = this.createPoint(intersects[0].point)	// point 即点击坐标
            scene.add(measurePointMesh)
            measurePointList.push(measurePointMesh)
            if (measurePointList.length >= 2) {
              if (this.measureMode === 0) {
                // 距离测量
                console.log('measurePointList:', measurePointList[measurePointList.length - 2])
                let p1 = measurePointList[measurePointList.length - 2].position
                let p2 = measurePointList[measurePointList.length - 1].position
                let dis = this.calDistance(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z).toFixed(2)
                let measureLine = this.createLine(p1, p2)
                scene.add(measureLine)
                measureLineList.push(measureLine)

                let labelMesh = this.createLabel(dis + ' m')
                labelMesh.position.x = (p1.x + p2.x) / 2
                labelMesh.position.y = (p1.y + p2.y) / 2
                labelMesh.position.z = (p1.z + p2.z) / 2
                scene.add(labelMesh)
                measureLabelList.push(labelMesh)

                scene.remove(tempLabel)
                tempLabel = undefined

                let activeLine = scene.getObjectByName('active-line')
                if (activeLine) {
                  let positionAttr = activeLine.geometry.getAttribute('position')
                  positionAttr.setXYZ(0, intersects[0].point.x, intersects[0].point.y, intersects[0].point.z)
                  positionAttr.needsUpdate = true
                }
              } else if (this.measureMode === 1) {
                // 面积测量
              } else if (this.measureMode === 2) {
                let p1 = measurePointList[measurePointList.length - 2]
                let p2 = measurePointList[measurePointList.length - 1]
                let measureLine = this.createLine(p1.position, p2.position)
                scene.add(measureLine)
                measureLineList.push(measureLine)
                let activeLine = scene.getObjectByName('active-line')
                if (activeLine) {
                  let positionAttr = activeLine.geometry.getAttribute('position')
                  positionAttr.setXYZ(0, intersects[0].point.x, intersects[0].point.y, intersects[0].point.z)
                  positionAttr.needsUpdate = true
                }
                // 角度测量
                if (measurePointList.length >= 3) {
                  let p0 = measurePointList[measurePointList.length - 3]

                  let dir0 = new THREE.Vector3(p0.position.x - p1.position.x, p0.position.y - p1.position.y, p0.position.z - p1.position.z).normalize()
                  let dir1 = this.getAngleBisector(p0, p1, p2)
                  let dir2 = new THREE.Vector3(p2.position.x - p1.position.x, p2.position.y - p1.position.y, p2.position.z - p1.position.z).normalize()
                  let angle = this.calculateAngle(p0, p1, p2)
                  let d01 = this.calDistance(p0.position.x, p0.position.y, p0.position.z, p1.position.x, p1.position.y, p1.position.z)
                  let d12 = this.calDistance(p1.position.x, p1.position.y, p1.position.z, p2.position.x, p2.position.y, p2.position.z)
                  const distance = Math.min(d01,d12)
                  let d = distance * 0.3 // distance from label to p1
                  let p = p1.position.clone().add(new THREE.Vector3(dir1.x * d, dir1.y * d, dir1.z * d)) // label's position
                  let labelMesh = this.createLabel(angle.toFixed(2) + ' °')
                  labelMesh.position.set(p.x, p.y, p.z)
                  scene.add(labelMesh)
                  measureLabelList.push(labelMesh)
                  scene.remove(tempLabel)
                  d = distance * 0.2 // distance from arc to p1
                  p = p1.clone().add(new THREE.Vector3(dir1.x * d, dir1.y * d, dir1.z * d)) // arc's middle position
                  const arcP0 = p1.clone().add(new THREE.Vector3(dir0.x * d, dir0.y * d, dir0.z * d))
                  const arcP2 = p1.clone().add(new THREE.Vector3(dir2.x * d, dir2.y * d, dir2.z * d))
                  let curve = this.createCurve(arcP0, p, arcP2)
                  scene.add(curve)
                  measureCurveList.push(curve)
                }
              }
            }
          }
        } else if (this.addDrillForm.drillDesMode && this.addDrillForm.startAddDrill) {
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
            //this.clickPoint = new THREE.Mesh(new THREE.SphereGeometry(0.5), new THREE.MeshBasicMaterial({ color: 0xff0000}))//0xff0000设置材质颜色为红色
            this.clickPoint = new THREE.Mesh(new THREE.SphereGeometry(0.5), new THREE.MeshBasicMaterial({ color: 0xff0000 }))//0xff0000设置材质颜色为红色
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

            selObjs = []
            selObjs.push(selected.object)
            console.log('selObjs:', selObjs)
            outlinePass.selectedObjects = selObjs

            let indexName = selected.object.name
            //console.log('add', indexName)
            for (let i = 0; i < this.addDrillingDataList.length; i++) {
              if (this.addDrillingDataList[i].name === indexName) {
                this.selectedDrill = this.addDrillingDataList[i]
                this.showDesc = true
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

            selObjs = []
            selObjs.push(selected.object)
            console.log('selObjs:', selObjs)
            outlinePass.selectedObjects = selObjs

            let indexName = selected.object.name
            for (let i = 0; i < this.drillingDataList.length; i++) {
              if (this.drillingDataList[i].name === indexName) {
                this.selectedDrill = this.drillingDataList[i]
                this.showDesc = true
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
      }
    },
    initSelectedDrill() {
      this.selectedDrill = {
        group: '',
        id: '',
        inclination: '',
        azimuth: '',
        disNav: '',
        disBottom: '',
        disCenter: '',
        seeLen: '',
        overLen: ''
      }
    },
    rad2ang(rad) {
      return (180 / Math.PI) * rad
    },
    ang2rad(ang) {
      return (Math.PI / 180.) * ang
    },
    /**
     * 获取两条直线的角平分线
     */
    getAngleBisector(startPoint, middlePoint, endPoint) {
      const p0 = startPoint.position
      const p1 = middlePoint.position
      const p2 = endPoint.position
      const dir0 = new THREE.Vector3(p0.x - p1.x, p0.y - p1.y, p0.z - p1.z).normalize()
      const dir2 = new THREE.Vector3(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z).normalize()
      return new THREE.Vector3(dir0.x + dir2.x, dir0.y + dir2.y, dir0.z + dir2.z).normalize() // the middle direction between dir0 and dir2
    },
    /**
     * Gets included angle of two lines in degree
     */
    calculateAngle(startPoint, middlePoint, endPoint) {
      const p0 = startPoint.position
      const p1 = middlePoint.position
      const p2 = endPoint.position
      const dir0 = new THREE.Vector3(p0.x - p1.x, p0.y - p1.y, p0.z - p1.z)
      const dir1 = new THREE.Vector3(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z)
      const angle = dir0.angleTo(dir1)
      return angle * 180 / Math.PI // convert to degree
    },
    /**
     * Creates the arc curve to indicate the angle in degree
     */
    createCurve(p0, p1, p2) {
      const curve = new THREE.QuadraticBezierCurve3(p0, p1, p2)
      const points = curve.getPoints(4) // get points
      const geometry = new THREE.BufferGeometry().setFromPoints(points)
      const obj = new THREE.Line(geometry, new THREE.LineBasicMaterial({
        color: 0xfff000,
        linewidth: 2,
        opacity: 0.8,
        transparent: true,
        side: THREE.DoubleSide,
        depthWrite: false,
        depthTest: false
      }))
      obj.name = 'object_for_measure'
      return obj
    },
    /**
     * 添加钻孔数据
     */
    addDrillingData() {
      this.drillingDataList.push(this.drillingData)
      this.drillingData = {}
    },

    /**
     * 计算数据
     * @param data 数据
     * @param showType 1=单排，2=整租
     * @param viewType 1=剖视图，2=俯视图，3=剖面图，4=竣工图
     */
    showCADPage(data, showType, viewType) {
      //this.changePage(5)
      let _this = this
      this.addDrillCADVisible = true
      this.exportCADDialogVisible = false
      if (viewType === 1) {
        this.cadData = this.calcCADData(data, showType)
      } else if (viewType === 2) {
        this.cadData = this.calcTopView()
      } else if (viewType === 3) {
        this.cadData = this.calcAllSideView()
      } else if (viewType === 4) {
        this.cadData = this.calcAllView()
      } else if (viewType === 5) {
        this.cadData = this.calcAllAddDrillView()
      }
      cadExportDXF(this.cadData).then(response => {
        let blob = new Blob([response.data])
        _this.dxfUrl = URL.createObjectURL(blob)
        console.log(_this.dxfUrl)
      })
    }
    ,
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
    }
    ,
    /**
     * 计算初始空白带
     */
    calInitResultLoading() {
      this.calcEmptyBandVisible = false
      this.loadingInstance = Loading.service({
        lock: true,
        text: '正在计算中,请稍后...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      setTimeout(() => {
        this.changePage(3)
        this.showOrHideCoal(true)
        this.showOrHideBottom(false)
        this.showOrHideTop(false)
        this.showOrHideOriginDrill(false)
        this.showOrHideOriginDrillInCoal(false)
        this.showOrHideOriginDrillBeyondCoal(false)
        this.showOrHideOriginDrillRange(false)
        this.showOrHideOriginEmptyBand(false)
        this.showOrHideEmptyBand(true)
        this.stopLoading = true
        this.calInitResult()
      }, 1000)
    }
    ,
    cleanAll() {
      this.noneEmptyBand = false
      this.addDrillDataVisible = false
      this.delAddDrillVisible = false
      this.emptyDialogVisible = false
      this.addDGLlength = -1
      this.delDrill = false
      this.addDrillingDataList = []
      this.emptyBandInfoList = []
      this.groupDrillOptions = [{ label: '全选', id: 0 }]
      this.groupSelects = []
      this.groupSpecialSelects = []
      this.groupDrillSelects = []
      this.finalPointList = []
      this.showOutCube = true
      this.showCoalCube = false
      this.emptyObjPoints = []
      this.initEmptyBand = false
      this.needReRender = true
      this.showOrHideBottom(false)
      this.showOrHideTop(false)
      this.showOrHideCoal(false)
      this.showOrHideOriginDrillRange(false)
      this.showOrHideAddDrillRange(false)
      this.showOrHideOriginDrill(true)
      this.showOrHideOriginDrillInCoal(true)
      this.showOrHideWorkPoint(false)
      this.showOrHideRoad(false)
      if (this.addDrillNotify !== null) {
        this.addDrillNotify.close()
        this.addDrillNotify = null
      }
      for (let i = 0; i < pointsBottomLineMeshList.length; i++) {
        this.cleanCache(pointsBottomLineMeshList[i])
      }
      pointsBottomLineMeshList = []
      for (let i = 0; i < pointsTopLineMeshList.length; i++) {
        this.cleanCache(pointsTopLineMeshList[i])
      }
      pointsTopLineMeshList = []
      for (let i = 0; i < pointsLineMeshList.length; i++) {
        this.cleanCache(pointsLineMeshList[i])
      }
      pointsLineMeshList = []
      for (let i = 0; i < coalTopPointsMeshList.length; i++) {
        this.cleanCache(coalTopPointsMeshList[i])
      }
      coalTopPointsMeshList = []
      for (let i = 0; i < coalBottomPointsMeshList.length; i++) {
        this.cleanCache(coalBottomPointsMeshList[i])
      }
      coalBottomPointsMeshList = []
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
      for (let i = 0; i < this.originDrillBeyondCoalMeshList.length; i++) {
        this.cleanCache(this.originDrillBeyondCoalMeshList[i])
      }
      this.originDrillBeyondCoalMeshList = []
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
      this.cleanCache(coalBottomWireMesh)
      coalBottomMesh = null
      this.cleanCache(coalTopWireMesh)
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
      for (let i = 0; i < originEmptyPartsMeshs.length; i++) {
        this.cleanCache(originEmptyPartsMeshs[i])
      }
      originEmptyPartsMeshs = []
      this.cleanCache(focusBoxMesh)

      // 相机初始化
      if (camera) {
        camera.position.set(-25, 75, -25).multiplyScalar(2)
        camera.far = 1000
        camera.updateProjectionMatrix()
      }
    }
    ,
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
        this.originDrillDataList = []
        this.renderNum = this.drillingDataList.length < this.inputNumber ? this.drillingDataList.length : this.inputNumber
        this.groupNoList = []
        this.groupNoFormList = []
        this.finalPointList = []
        let baseGroupNo = -1
        for (let i = 0; i < this.renderNum; i++) {
          this.drillingDataList[i].name = '实钻-' + i
          let dd = this.drillingDataList[i]
          if (this.groupNoList.indexOf(dd.realGroup) === -1) {
            baseGroupNo++
            this.groupNoList.push(dd.realGroup)
            this.groupNoFormList.push({ group: baseGroupNo, realGroup: dd.realGroup, count: 0 })
          } else {
            for (let j = 0; j < this.groupNoFormList.length; j++) {
              if (this.groupNoFormList[j].realGroup === dd.realGroup) {
                this.groupNoFormList[j].count++
                break
              }
            }
          }
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
          this.bottomPointList.push([xm, ym, zm, baseGroupNo, dd.name, dd.realGroup])
          this.topPointList.push([xn, yn, zn, baseGroupNo, dd.name, dd.realGroup])
          let finalPoint = this.calNewPoint2(xm, ym, zm, xn, yn, zn, dd.beyondLen)
          this.finalPointList.push([finalPoint.x, finalPoint.y, finalPoint.z, baseGroupNo, dd.name, dd.realGroup])
          this.originDrillDataList.push({
            beyondLen: dd.beyondLen,
            realGroup: dd.realGroup,
            group: baseGroupNo,
            name: dd.name,
            id: dd.id,
            top: [xn, yn, zn],
            bottom: [xm, ym, zm],
            final: [finalPoint.x, finalPoint.y, finalPoint.z]
          })
          dd.group = baseGroupNo
          console.log(dd.realGroup, dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))

          /* if (dd.azimuth <= 360 && dd.azimuth > 270) {
            let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(360 - dd.azimuth)))
            let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(360 - dd.azimuth)))
            let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disBottom + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
            this.bottomPointList.push([xm, ym, zm, dd.group, dd.name])
            let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(360 - dd.azimuth)))
            let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(360 - dd.azimuth)))
            let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disBottom + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
            this.topPointList.push([xn, yn, zn, dd.group, dd.name])
            this.originDrillDataList.push({
              group: dd.group,
              name: dd.name,
              id: dd.id,
              top: [xn, yn, zn],
              bottom: [xm, ym, zm]
            })
            //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
          } else if (dd.azimuth <= 270 && dd.azimuth > 180) {
            let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth - 180)))
            let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth - 180)))
            let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disBottom + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
            this.bottomPointList.push([xm, ym, zm, dd.group, dd.name])
            let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth - 180)))
            let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth - 180)))
            let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disBottom + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
            this.topPointList.push([xn, yn, zn, dd.group, dd.name])
            this.originDrillDataList.push({
              group: dd.group,
              name: dd.name,
              id: dd.id,
              top: [xn, yn, zn],
              bottom: [xm, ym, zm]
            })
            //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
          } else if (dd.azimuth <= 180 && dd.azimuth > 90) {
            let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(180 - dd.azimuth)))
            let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(180 - dd.azimuth)))
            let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disBottom + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
            this.bottomPointList.push([xm, ym, zm, dd.group, dd.name])
            let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(180 - dd.azimuth)))
            let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) - (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(180 - dd.azimuth)))
            let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disBottom + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
            this.topPointList.push([xn, yn, zn, dd.group, dd.name])
            this.originDrillDataList.push({
              group: dd.group,
              name: dd.name,
              id: dd.id,
              top: [xn, yn, zn],
              bottom: [xm, ym, zm]
            })
            //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
          } else if (dd.azimuth <= 90) {
            let xm = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth)))
            let ym = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.seeLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth)))
            let zm = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disBottom + (dd.seeLen * Math.sin(this.ang2rad(dd.inclination)))
            this.bottomPointList.push([xm, ym, zm, dd.group, dd.name])
            let xn = bd.navX + (dd.disNav * Math.sin(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.sin(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.sin(this.ang2rad(dd.azimuth)))
            let yn = bd.navY + (dd.disNav * Math.cos(this.ang2rad(bd.azimuth))) + (dd.disCenter * Math.cos(this.ang2rad(dd.azimuth))) + (dd.overLen * Math.cos(this.ang2rad(dd.inclination)) * Math.cos(this.ang2rad(dd.azimuth)))
            let zn = bd.navZ + (dd.disNav * Math.tan(this.ang2rad(bd.inclination))) - dd.disBottom + (dd.overLen * Math.sin(this.ang2rad(dd.inclination)))
            this.topPointList.push([xn, yn, zn, dd.group, dd.name])
            this.originDrillDataList.push({
              group: dd.group,
              name: dd.name,
              id: dd.id,
              top: [xn, yn, zn],
              bottom: [xm, ym, zm]
            })
            //console.log(dd.id, xm.toFixed(2), ym.toFixed(2), zm.toFixed(2), xn.toFixed(2), yn.toFixed(2), zn.toFixed(2))
          } */
        }

        // 构造groupDrillOptions
        for (let i = 0; i < this.groupNoList.length; i++) {
          this.groupDrillOptions.push({ value: this.groupNoList[i], label: this.groupNoList[i], children: [] })
        }
        for (let i = 0; i < this.originDrillDataList.length; i++) {
          for (let j = 0; j < this.groupDrillOptions.length; j++) {
            if (this.groupDrillOptions[j].label === this.originDrillDataList[i].realGroup) {
              this.groupDrillOptions[j].children.push({
                value: this.originDrillDataList[i].name,
                label: this.originDrillDataList[i].id
              })
            }
          }
        }
        console.log('this.groupDrillOptions:', this.groupDrillOptions)
      }
      this.init()
    }
    ,
    /**
     * 分割不相连部分
     * @param resultBottom
     * @returns {*[]}
     */
    calcParts(resultBottom) {
      let parts = []
      const posMap = {}
      const positions = resultBottom.geometry.attributes.position
      if (!positions) {
        return []
      }
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
    }
    ,

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
          /* let drillPointX = bd.navX + ((base + (obj.group * _this.rowSpacing)) * Math.sin(_this.ang2rad(bd.azimuth)))
          let drillPointY = bd.navY + ((base + (obj.group * _this.rowSpacing)) * Math.cos(_this.ang2rad(bd.azimuth)))
          let drillPointZ = bd.navZ + ((base + (obj.group * _this.rowSpacing)) * Math.tan(_this.ang2rad(bd.inclination))) */

          let drillPointX = bd.navX + (obj.disNav * Math.sin(_this.ang2rad(bd.azimuth)))
          let drillPointY = bd.navY + (obj.disNav * Math.cos(_this.ang2rad(bd.azimuth)))
          let drillPointZ = bd.navZ + (obj.disNav * Math.tan(_this.ang2rad(bd.inclination)))
          _this.workPoints.push({
            realGroup: obj.realGroup,
            group: obj.group,
            x: drillPointX,
            y: drillPointY,
            z: drillPointZ
          })
          thisGroup = obj.group
        }
      }

      // 创建施工点位模型
      for (let i = 0; i < this.workPoints.length; i++) {
        let wkGeo = new THREE.BoxGeometry(0.5, 0.5, 0.5)
        let wkMaterial = new THREE.MeshBasicMaterial({ color: 0xffffff })
        let wkMesh = new THREE.Mesh(wkGeo, wkMaterial)
        wkMesh.position.x = this.workPoints[i]['x']
        wkMesh.position.y = this.workPoints[i]['z']
        wkMesh.position.z = this.workPoints[i]['y']
        this.workPointsMeshList.push(wkMesh)

        // 施工点位名称
        let WPNameMesh = this.addSpirit(this.workPoints[i]['realGroup'])
        WPNameMesh.position.x = wkMesh.position.x
        WPNameMesh.position.y = wkMesh.position.y - 1
        WPNameMesh.position.z = wkMesh.position.z
        this.workPointsNameMeshList.push(WPNameMesh)
        scene.add(WPNameMesh)
        scene.add(wkMesh)
      }
    }
    ,
    /**
     * 初始化场景
     */
    initScene() {
      const bgColor = 0x000000
      textOutput = document.getElementById('output')
      outputContainer = document.getElementById('container')
      tGUIContainer = document.getElementById('tGUI')
      // 渲染初始化
      renderer = new THREE.WebGLRenderer({ antialias: true })
      renderer.setPixelRatio(window.devicePixelRatio)
      console.log(window.innerHeight, window.innerWidth)
      let wid = 1640
      let height = 930
      if (window.innerWidth > 3000) {
        //wid = 1920
        //height = 1080
        wid = window.innerWidth - 280
        height = window.innerHeight - 130
        //renderer.setSize(1920, 1080)
      } else if (window.innerWidth > 2100) {
        //wid = 2280
        //height = 1154
        wid = window.innerWidth - 280
        height = window.innerHeight - 130
      } else if (window.innerWidth > 1930) {
        //wid = 1768
        //height = 870
        wid = window.innerWidth - 280
        height = window.innerHeight - 130 - 2
      } else if (window.innerWidth > 1600) {
        //wid = 1410
        wid = window.innerWidth - 280
        height = window.innerHeight - 130
      } else {
        wid = 1640
        height = 790
      }
      renderer.setSize(wid, height)
      //renderer.setSize(window.innerWidth, window.innerHeight)
      renderer.setClearColor(bgColor, 1)
      renderer.outputEncoding = THREE.sRGBEncoding
      //renderer.sortObjects = false
      outputContainer.appendChild(renderer.domElement)
      css2dRenderer = new CSS2DRenderer()
      css2dRenderer.setSize(wid, height)
      css2dRenderer.domElement.style.position = 'absolute'
      css2dRenderer.domElement.style.top = '0px'
      outputContainer.appendChild(css2dRenderer.domElement)

      // 场景初始化
      scene = new THREE.Scene()
      //scene.background = new THREE.Color(111, 255, 100)

      // 光照初始化
      light = new THREE.DirectionalLight(0xFFFFCC, 0.86)
      light.position.set(0, 100, 0)
      scene.add(light, light.target)
      scene.add(new THREE.AmbientLight(0xECF6F9, 0.35))

      // 相机初始化
      camera = new THREE.PerspectiveCamera(75, wid / height, 0.1, 50)
      camera.position.set(-25, 75, -25).multiplyScalar(2)
      camera.far = 1000
      camera.updateProjectionMatrix()

      // 控制器
      controls = new OrbitControls(camera, css2dRenderer.domElement)
      scene.add(controls)
      /* controls = new OrbitControls(camera, renderer.domElement)
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
      scene.add(transformControls) */

      this.initOutlineObj()
      //this.initFaultObj()

      // 坐标轴
      /* axesHelper = new THREE.AxesHelper(25)
      scene.add(axesHelper) */

      // 点击事件
      /* document.body.addEventListener('click', this.onClick, false)
      document.body.addEventListener('mousemove', this.onMouseMove, false) */
      outputContainer.addEventListener('pointerdown', this.onMousedown, false)
      outputContainer.addEventListener('pointermove', this.onMouseMove, false)
      outputContainer.addEventListener('pointerup', this.onMouseup, false)
      outputContainer.addEventListener('contextmenu', this.onRightClick, false)
    },

    addOrUpdateTempLabel(label, position) {
      if (!tempLabel) {
        tempLabel = this.createLabel(label)
        scene.add(tempLabel)
      }
      tempLabel.position.set(position.x, position.y, position.z)
      tempLabel.element.innerHTML = label
    },
    createLabel(text) {
      const div = document.createElement('div')
      // div.className = 'annotationLabel'
      div.innerHTML = text
      div.style.padding = '3px 6px'
      div.style.color = '#fff'
      div.style.fontSize = '12px'
      div.style.position = 'absolute'
      div.style.backgroundColor = 'rgba(25, 25, 25, 0.3)'
      div.style.borderRadius = '12px'
      // div.style.width = '200px'
      // div.style.height = '100px'
      div.style.top = '0px'
      div.style.left = '0px'
      // div.style.pointerEvents = 'none' // avoid html element to affect mouse event of the scene
      const obj = new CSS2DObject(div)
      obj.name = 'label_for_measure'
      return obj
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
      rootPartsObject = new THREE.Mesh(undefined, new THREE.MeshBasicMaterial({ color: 0, wireframe: false }))
      rootPartsObject.material.color.set(0xffc400).convertSRGBToLinear().multiplyScalar(0.1)
      rootPartsObject.material.polygonOffset = true
      rootPartsObject.material.polygonOffsetFactor = -16
      rootPartsObject.material.polygonOffsetUnits = 1
      scene.add(rootPartsObject)

      initRoot = new Operation(coalGeometry, originEmptyBandMat)

      this.createDefaultOp(initRoot)

      for (let i = 0; i < this.points3DBottom.length; i++) {
        // 添加原始钻孔抽采范围, 并计算
        this.addDrillRangeMeshToCalc(this.points3DBottom[i], this.points3DTop[i], this.radius, originDrillRangeMat, initRoot, this.originDrillGroupMeshList, '实钻-' + i)
        /* if (this.topPointList[i][3] % 2 === 0) {
          this.addDrillRangeMeshToScene(this.points3DBottom[i], this.points3DTop[i], this.radius, originDrillRangeMatD, false, this.originDrillRangeToSceneList, '实钻-' + i)
        } else {
          this.addDrillRangeMeshToScene(this.points3DBottom[i], this.points3DTop[i], this.radius, originDrillRangeMatS, false, this.originDrillRangeToSceneList, '实钻-' + i)
        } */
      }

      let startTime = window.performance.now()
      initResult = initCSGEvaluator.evaluateHierarchy(initRoot)
      initResult.material.polygonOffset = true
      initResult.material.polygonOffsetFactor = -18
      initResult.material.polygonOffsetUnits = 1
      scene.add(initResult)

      let calcOriginParts = this.calcParts(initResult)
      let calcNum = this.handleOriginEmptyBand(calcOriginParts)
      if (calcNum > 0) {
        let geo = new THREE.BufferGeometry()
        let geos = []
        for (let i = 0; i < originEmptyPartsMeshs.length; i++) {
          geos.push(originEmptyPartsMeshs[i].geometry)
          scene.add(originEmptyPartsMeshs[i])
        }
        geo = BufferGeometryUtils.mergeBufferGeometries(geos, false)
        originEmptyBandResult = new THREE.Mesh(geo, originEmptyBandMat)
        scene.add(originEmptyBandResult)
      } else {
        this.noneEmptyBand = true
        this.$notify({
          title: '未发现空白带',
          message: '经过计算未发现任何空白带',
          type: 'success'
        })
      }
      let deltaTime = window.performance.now() - startTime
      console.log('初次渲染时长:' + deltaTime)
      this.initEmptyBand = true
      this.showEmptyBandData()
    }
    ,

    /**
     * 渲染
     */
    render() {
      renderer.render(scene, camera)
      css2dRenderer.render(scene, camera)
      requestAnimationFrame(this.render)
      TWEEN.update()
      // 可视化设置
      this.visibleSetting()

      if (partsResult) {
        this.cleanCache(partsResult)
      }
      if (this.initEmptyBand && this.noneEmptyBand === false) {
        if (!partsRoot) {
          partsRoot = new Operation(originEmptyBandResult.geometry, originEmptyBandMat)
          scene.add(partsRoot)
          this.createDefaultOp(partsRoot)
          /* partsResult = addCSGEvaluator.evaluateHierarchy(partsRoot)
          partsResult.material = resultGridMat
          rootPartsObject.geometry = partsResult.geometry

          partsResult.visible = false
          partsRoot.visible = false
          rootPartsObject.visible = false */
        }
        partsResult = addCSGEvaluator.evaluateHierarchy(partsRoot)
        partsResult.material = resultGridMat
        rootPartsObject.geometry = partsResult.geometry

        partsResult.visible = false
        partsRoot.visible = false
        rootPartsObject.visible = false

      }

      if (this.initEmptyBand && this.noneEmptyBand === false) {
        // 已经完成空白带初始化, 才需要进行空白带分割渲染
        // 3种情况下,需要进行再次的分割渲染. 1.补钻 2.删除钻孔 3.显示或隐藏外部包围体
        if (this.needReRender) {
          this.cleanCache(focusBoxMesh)

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
          this.emptyBandInfoList = []

          // 分割不相连的部分
          let calcedParts = this.calcParts(rootPartsObject)
          // 处理空白带, 获得分块儿数量
          let nIndex = this.handleEmptyBand(calcedParts)

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

        } else {
          if (this.stopLoading) {
            this.loadingInstance.close()
            this.stopLoading = false
          }
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

      /* transformControls.enabled = params.displayControls
      transformControls.visible = params.displayControls */
      if (composer) {
        composer.render()
      }
    }
    ,

    /**
     * 初始化动画
     * @param cameraPosition
     * @param controlsTarget
     * @param max
     */
    initTween(cameraPosition, controlsTarget, max) {
      let target = controls.target
      let position = camera.position
      this.tween = new TWEEN.Tween({
        x1: target.x,
        y1: target.y,
        z1: target.z,
        x2: position.x,
        y2: position.y,
        z2: position.z
      })
          .to({
            x1: controlsTarget.x, y1: controlsTarget.y, z1: controlsTarget.z,
            x2: cameraPosition.x, y2: cameraPosition.y, z2: cameraPosition.z
          }, 2000)
          .easing(TWEEN.Easing.Quadratic.Out)
          .onUpdate((object) => {
            let { x1, y1, z1, x2, y2, z2 } = object
            controls.target.set(x1, y1, z1) // 控制器的焦点
            camera.position.set(x2, y2, z2) // 相机位置
            controls.update()
          }).onComplete((object) => {
            camera.near = max * 0.1
            camera.updateProjectionMatrix()
          })
    }
    ,

    /**
     * 处理原始空白带
     * @param parts
     */
    handleOriginEmptyBand(parts) {
      let nIndex = 0
      for (let i = 0; i < parts.length; i++) {
        // 筛除只有三个点的部分
        if (parts[i].attributes.position.count > 3) {
          let r = Math.random()
          let cubeMaterial = new THREE.MeshLambertMaterial({
            color: 0xFFFFFF * r,
            transparent: true,
            opacity: 0.55
          })
          let obbPoints = []
          let positions = parts[i].getAttribute('position')
          for (let j = 0; j < positions.count; j++) {
            let x = positions.getX(j)
            let y = positions.getY(j)
            let z = positions.getZ(j)
            obbPoints.push(new YUKA.Vector3(x, y, z))
          }
          let obb = null
          try {
            obb = new YUKA.OBB().fromPoints(obbPoints)
          } catch (e) {
            obb = null
            console.log('生成包围体失败,无效实体:' + i, obbPoints)
          }
          if (obb !== null) {
            // 利用射线相交, 检测是否在煤层内
            let isContains = false
            let helper = createOBBHelper(obb, cubeMaterial)
            let centerCoord = new THREE.Vector3(helper.position.x, helper.position.y, helper.position.z)
            for (let j = 0; j < positions.count; j++) {
              let x = positions.getX(j)
              let y = positions.getY(j)
              let z = positions.getZ(j)
              let vertexWorldCoord = new THREE.Vector3(x, y, z)
              let dir = new THREE.Vector3() //创建一个向量
              dir.subVectors(vertexWorldCoord, centerCoord)
              let rayn = new THREE.Raycaster(centerCoord, dir.clone().normalize())
              let intersects = rayn.intersectObjects([coalCubeObj])
              if (intersects.length > 0) {
                let le = dir.length()
                if (intersects[0].distance < le) {
                  console.log('相交11111')
                  isContains = true
                  break
                }
              }
            }
            if (isContains) {
              let vol = computeMeshVolume(parts[i])
              console.log('vol:', vol)
              if (vol > this.calcEmptyVolumeThreshold && vol < 200) {
                //if ((vol > 0.1 && vol < 0.5) || (vol > 1.5 && vol < 2)) {
                // 将大小也符合标准的空白带放入原始空白带List中
                let originPartMesh = new Mesh(parts[i], originEmptyBandMat)
                originEmptyPartsMeshs.push(originPartMesh)
                nIndex++
              }
            }
          }
        }
      }
      return nIndex
    }
    ,

    /**
     * 处理空白带
     * @param parts
     * @returns {number}
     */
    handleEmptyBand(parts) {
      let nIndex = 0
      for (let i = 0; i < parts.length; i++) {
        // 筛除只有三个点的部分
        if (parts[i].attributes.position.count > 3) {
          this.loading = true

          // 先计算外部包围盒
          // 外部包围盒材质
          let r = Math.random()
          let cubeMaterial = new THREE.MeshLambertMaterial({
            color: 0xFFFFFF * r,
            transparent: true,
            opacity: 0.3
            //opacity: params.showOutCube ? 0.55 : 0
          })
          this.emptyObjPoints[nIndex] = []
          const positions = parts[i].getAttribute('position')
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
            let isContains = false
            let helper = createOBBHelper(obb, cubeMaterial)
            // 检测空白带和煤层体内外部关系,来判断是否是有效的
            let centerCoord = new THREE.Vector3(helper.position.x, helper.position.y, helper.position.z)
            for (let j = 0; j < positions.count; j++) {
              let x = positions.getX(j)
              let y = positions.getY(j)
              let z = positions.getZ(j)
              let vertexWorldCoord = new THREE.Vector3(x, y, z)
              let dir = new THREE.Vector3()
              dir.subVectors(vertexWorldCoord, centerCoord)
              let rayn = new THREE.Raycaster(centerCoord, dir.clone().normalize())
              let intersects = rayn.intersectObjects([coalCubeObj])
              if (intersects.length > 0) {
                let le = dir.length()
                if (intersects[0].distance < le) {
                  console.log('相交')
                  isContains = true
                  break
                } else {
                  console.log('不相交')
                }
              }
            }
            if (isContains) {
              // 视为有效, 创建并添加空白带实体
              let vol = computeMeshVolume(parts[i])
              // 分割后不同部分使用不同的颜色显示
              let partMaterial = new THREE.MeshLambertMaterial({
                color: 0xFFFFFF * r
              })
              partMaterial.polygonOffset = true
              partMaterial.polygonOffsetFactor = -20
              partMaterial.polygonOffsetUnits = 1
              let partMesh = new Mesh(parts[i], partMaterial)
              // 空白带实体
              partMeshs.push(partMesh)
              // 空白带包围块
              partCubes.push(helper)

              console.log('index:' + nIndex + ',有效实体' + i + ':' + partMesh.uuid + ',' + ',体积:', vol, ',包围体:', helper.uuid)
              // 表格信息List
              this.emptyBandInfoList.push({
                uuid: partMesh.uuid,
                position: helper.position.x + ',' + helper.position.y + ',' + helper.position.z,
                volume: Number((Number(vol.toFixed(2)) + 0.01).toFixed(2)),
                cubeUUID: helper.uuid
              })
              nIndex++
            }
          }
        } else {
          console.log('点数<=3, 无效实体' + i)
        }
      }
      return nIndex
    },

    /**
     * 模型可视化控制
     */
    visibleSetting() {
      // 煤层顶底板面
      if (coalBottomMesh) {
        coalBottomMesh.visible = params.showCoalBottomMesh
      }
      if (coalTopMesh) {
        coalTopMesh.visible = params.showCoalTopMesh
      }

      // 顶底板线
      /* for (let i = 0; i < pointsTopLineMeshList.length; i++) {
        //pointsTopLineMeshList[i].visible = this.groupSelects.includes(pointsTopLineMeshList[i].name)
        pointsTopLineMeshList[i].visible = params.showCoalLine
      }
      for (let i = 0; i < pointsBottomLineMeshList.length; i++) {
        //pointsBottomLineMeshList[i].visible = this.groupSelects.includes(pointsBottomLineMeshList[i].name)
        pointsBottomLineMeshList[i].visible = params.showCoalLine
      }*/
      for (let i = 0; i < pointsLineMeshList.length; i++) {
        pointsLineMeshList[i].visible = (params.showSpCoalLine && this.groupSpecialSelects.includes(pointsLineMeshList[i].name))
      }

      // 底板点
      for (let i = 0; i < coalBottomPointsMeshList.length; i++) {
        coalBottomPointsMeshList[i].visible = params.showCoalPoints
        //coalBottomPointsMeshList[i].visible = this.groupDrillSelects.includes(coalBottomPointsMeshList[i].name)
        coalBottomPointsMeshList[i].scale.x = params.coalPointsScale
        coalBottomPointsMeshList[i].scale.y = params.coalPointsScale
        coalBottomPointsMeshList[i].scale.z = params.coalPointsScale
        coalBottomMeshMat.color.set(params.bottomPointsColor)
        //coalBottomPointsMeshList[i].visible = true
      }

      // 顶板点
      for (let i = 0; i < coalTopPointsMeshList.length; i++) {
        coalTopPointsMeshList[i].visible = params.showCoalPoints
        //coalTopPointsMeshList[i].visible = this.groupDrillSelects.includes(coalTopPointsMeshList[i].name)
        coalTopPointsMeshList[i].scale.x = params.coalPointsScale
        coalTopPointsMeshList[i].scale.y = params.coalPointsScale
        coalTopPointsMeshList[i].scale.z = params.coalPointsScale
        coalTopMeshMat.color.set(params.topPointsColor)
        //coalTopPointsMeshList[i].visible = true
      }

      // 原始钻孔
      /* if (params.showOriginDrill) {
        for (let i = 0; i < this.originDrillMeshList.length; i++) {
          this.originDrillMeshList[i].visible = true
        }
      } else {
        for (let i = 0; i < this.originDrillMeshList.length; i++) {
          this.originDrillMeshList[i].visible = false
        }
      } */
      for (let i = 0; i < this.originDrillMeshList.length; i++) {
        this.originDrillMeshList[i].visible = (params.showOriginDrill && this.groupDrillSelects.includes(this.originDrillMeshList[i].name))
      }

      // 原始钻孔抽采范围
      /* if (params.showOriginDrillRange) {
        for (let i = 0; i < this.originDrillRangeToSceneList.length; i++) {
          this.originDrillRangeToSceneList[i].visible = true
          this.originDrillRangeToSceneList[i].renderOrder = 1
        }
      } else {
        for (let i = 0; i < this.originDrillRangeToSceneList.length; i++) {
          this.originDrillRangeToSceneList[i].visible = false
        }
      } */
      for (let i = 0; i < this.originDrillRangeToSceneList.length; i++) {
        let v = (params.showOriginDrillRange && this.groupDrillSelects.includes(this.originDrillRangeToSceneList[i].name))
        this.originDrillRangeToSceneList[i].visible = v
        if (v) {
          this.originDrillRangeToSceneList[i].renderOrder = 1
        }
      }

      // 显示补钻钻孔
      if (params.showAddDrill) {
        for (let i = 0; i < this.addDrillMeshList.length; i++) {
          this.addDrillMeshList[i].visible = true
          this.addDrillMeshList[i].renderOrder = 1
        }
      } else {
        for (let i = 0; i < this.addDrillMeshList.length; i++) {
          this.addDrillMeshList[i].visible = false
        }
      }

      // 补钻钻孔抽采范围
      if (params.showAddDrillRange) {
        for (let i = 0; i < this.addDrillRangeToSceneList.length; i++) {
          this.addDrillRangeToSceneList[i].visible = true
        }
      } else {
        for (let i = 0; i < this.addDrillRangeToSceneList.length; i++) {
          this.addDrillRangeToSceneList[i].visible = false
        }
      }

      // 原始煤孔段
      /* if (params.showOriginDrillInCoal) {
        for (let i = 0; i < this.originDrillInCoalMeshList.length; i++) {
          this.originDrillInCoalMeshList[i].visible = true
        }
      } else {
        for (let i = 0; i < this.originDrillInCoalMeshList.length; i++) {
          this.originDrillInCoalMeshList[i].visible = false
        }
      } */
      for (let i = 0; i < this.originDrillInCoalMeshList.length; i++) {
        this.originDrillInCoalMeshList[i].visible = (params.showOriginDrillInCoal && this.groupDrillSelects.includes(this.originDrillInCoalMeshList[i].name))
      }

      // 原始过煤进岩段
      /* if (params.showOriginDrillBeyondCoal) {
        for (let i = 0; i < this.originDrillBeyondCoalMeshList.length; i++) {
          this.originDrillBeyondCoalMeshList[i].visible = true
        }
      } else {
        for (let i = 0; i < this.originDrillBeyondCoalMeshList.length; i++) {
          this.originDrillBeyondCoalMeshList[i].visible = false
        }
      } */
      for (let i = 0; i < this.originDrillBeyondCoalMeshList.length; i++) {
        this.originDrillBeyondCoalMeshList[i].visible = (params.showOriginDrillBeyondCoal && this.groupDrillSelects.includes(this.originDrillBeyondCoalMeshList[i].name))
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

      // 顶底板线
      for (let i = 0; i < pointsBottomLineMeshList.length; i++) {
        pointsBottomLineMeshList[i].visible = (params.showCoalLine && this.groupSelects.includes(pointsBottomLineMeshList[i].name))
        bottomLineMat.color.set(params.bottomLineColor)
        bottomLineMat.linewidth = params.linewidth
      }
      for (let i = 0; i < pointsTopLineMeshList.length; i++) {
        pointsTopLineMeshList[i].visible = (params.showCoalLine && this.groupSelects.includes(pointsTopLineMeshList[i].name))
        topLineMat.color.set(params.topLineColor)
        topLineMat.linewidth = params.linewidth
      }
      for (let i = 0; i < pointsSideLineMeshList.length; i++) {
        pointsSideLineMeshList[i].visible = (params.showCoalLine && this.groupSelects.includes(pointsSideLineMeshList[i].name))
        sideLineMat.color.set(params.sideLineColor)
        sideLineMat.linewidth = params.linewidth
      }

      // 巷道, 施工点位, 施工点名称
      roadMesh.visible = params.showRoad
      if (params.showWorkPoint) {
        for (let i = 0; i < this.workPointsMeshList.length; i++) {
          this.workPointsMeshList[i].visible = true
        }
        for (let i = 0; i < this.workPointsNameMeshList.length; i++) {
          this.workPointsNameMeshList[i].visible = true
        }
      } else {
        for (let i = 0; i < this.workPointsMeshList.length; i++) {
          this.workPointsMeshList[i].visible = false
        }
        for (let i = 0; i < this.workPointsNameMeshList.length; i++) {
          this.workPointsNameMeshList[i].visible = false
        }
      }

      // 煤层体
      if (coalCubeObj) {
        coalCubeObj.visible = params.showCoalCube
        if (params.showCoalCube) {
          coalCubeMat.opacity = params.coalCubeOpacity
          coalCubeMat.color.set(params.coalCubeColor)
        } else {
          coalCubeMat.opacity = 0
          coalCubeMat.color.set(params.coalCubeColor)
        }
      }

      // 空白带实体
      if (partMeshs) {
        for (let i = 0; i < partMeshs.length; i++) {
          partMeshs[i].visible = params.showEmptyBand
          //partCubes[i].visible = params.showOutCube
        }
      }

      // 空白带包裹体
      if (partCubes) {
        for (let i = 0; i < partCubes.length; i++) {
          partCubes[i].visible = params.showOutCube
        }
      }

      // 原始空白带信息, 初次计算后的结果, 无需展示直接隐藏
      if (initResult) {
        //initResult.visible = params.showOriginEmptyBand
        initResult.visible = false
      }
      if (rootPartsObject) {
        //rootPartsObject.visible = params.showOriginEmptyBand
        rootPartsObject.visible = false
      }
      if (originEmptyBandResult) {
        //originEmptyBandResult.visible = params.showOriginEmptyBand
        originEmptyBandResult.visible = false
      }

      // 原始空白带信息
      if (originEmptyPartsMeshs) {
        for (let i = 0; i < originEmptyPartsMeshs.length; i++) {
          originEmptyPartsMeshs[i].visible = params.showOriginEmptyBand
          //partCubes[i].visible = params.showOutCube
        }
      }
    }
    ,

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
    }
    ,

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

      // 添加上下钻孔点位
      let coalBottomGeom = new THREE.BufferGeometry().setFromPoints(this.points3DBottom)
      let coalTopGeom = new THREE.BufferGeometry().setFromPoints(this.points3DTop)
      /*let bottomCloud = new THREE.Points(
          coalBottomGeom,
          new THREE.PointsMaterial({ color: 0x99ccff, size: 0.5 })
      )
      let topCloud = new THREE.Points(
          coalTopGeom,
          new THREE.PointsMaterial({ color: 0x99cc00, size: 0.5 })
      )
      scene.add(bottomCloud)
      scene.add(topCloud)*/
      coalBottomPointsMeshList = []
      coalTopPointsMeshList = []
      let pointGeo = new THREE.SphereGeometry(0.2)
      for (let i = 0; i < this.points3DBottom.length; i++) {
        let point = new THREE.Mesh(pointGeo, coalBottomMeshMat)
        point.name = this.bottomPointList[i][4]
        point.position.x = this.points3DBottom[i].x
        point.position.y = this.points3DBottom[i].y
        point.position.z = this.points3DBottom[i].z
        coalBottomPointsMeshList.push(point)
      }

      for (let i = 0; i < this.points3DTop.length; i++) {
        let point = new THREE.Mesh(pointGeo, coalTopMeshMat)
        point.name = this.topPointList[i][4]
        point.position.x = this.points3DTop[i].x
        point.position.y = this.points3DTop[i].y
        point.position.z = this.points3DTop[i].z
        coalTopPointsMeshList.push(point)
      }
      for (let i = 0; i < coalBottomPointsMeshList.length; i++) {
        scene.add(coalBottomPointsMeshList[i])
      }

      for (let i = 0; i < coalTopPointsMeshList.length; i++) {
        scene.add(coalTopPointsMeshList[i])
      }

      // 创建每排钻孔连线
      pointsBottomLineMeshList = []
      pointsTopLineMeshList = []
      pointsLineMeshList = []
      for (let i = 0; i < this.groupNoList.length; i++) {
        let drillDataList = []
        let bottomLPoints = []
        let topLPoints = []
        let sideLPoints = []
        let lineAllPoints = []
        for (let j = 0; j < this.originDrillDataList.length; j++) {
          if (this.originDrillDataList[j].realGroup === this.groupNoList[i]) {
            drillDataList.push(this.originDrillDataList[j])
          }
        }

        // bottom
        for (let k = 0; k < drillDataList.length; k++) {
          //bottomLPoints.push(new THREE.Vector3(drillDataList[k].bottom[0], drillDataList[k].bottom[2], drillDataList[k].bottom[1]))
          bottomLPoints.push(drillDataList[k].bottom[0])
          bottomLPoints.push(drillDataList[k].bottom[2])
          bottomLPoints.push(drillDataList[k].bottom[1])
        }
        bottomLPoints = new Float32Array(bottomLPoints)
        let bottomLineGeo = new LineGeometry()
        bottomLineGeo.setPositions(bottomLPoints)
        let bottomLine = new Line2(bottomLineGeo, bottomLineMat)
        bottomLine.name = drillDataList[0].realGroup
        pointsBottomLineMeshList.push(bottomLine)

        // top
        //for (let l = drillDataList.length - 1; l >= 0; l--) {
        for (let k = 0; k < drillDataList.length; k++) {
          //topLPoints.push(new THREE.Vector3(drillDataList[l].top[0], drillDataList[l].top[2], drillDataList[l].top[1]))
          topLPoints.push(drillDataList[k].top[0])
          topLPoints.push(drillDataList[k].top[2])
          topLPoints.push(drillDataList[k].top[1])
        }
        topLPoints = new Float32Array(topLPoints)
        let topLineGeo = new LineGeometry()
        topLineGeo.setPositions(topLPoints)
        let topLine = new Line2(topLineGeo, topLineMat)
        topLine.name = drillDataList[0].realGroup
        pointsTopLineMeshList.push(topLine)

        // side
        for (let j = 0; j < drillDataList.length; j++) {
          sideLPoints = []
          sideLPoints.push(drillDataList[j].top[0])
          sideLPoints.push(drillDataList[j].top[2])
          sideLPoints.push(drillDataList[j].top[1])
          sideLPoints.push(drillDataList[j].bottom[0])
          sideLPoints.push(drillDataList[j].bottom[2])
          sideLPoints.push(drillDataList[j].bottom[1])
          sideLPoints = new Float32Array(sideLPoints)
          let sideLineGeo1 = new LineGeometry()
          sideLineGeo1.setPositions(sideLPoints)
          let sideLine1 = new Line2(sideLineGeo1, sideLineMat)
          sideLine1.name = drillDataList[j].realGroup
          pointsSideLineMeshList.push(sideLine1)
        }

        // 一排顶板、底板一圈儿的连线
        for (let k = 0; k < drillDataList.length; k++) {
          lineAllPoints.push(drillDataList[k].bottom[0])
          lineAllPoints.push(drillDataList[k].bottom[2])
          lineAllPoints.push(drillDataList[k].bottom[1])
        }
        for (let l = drillDataList.length - 1; l >= 0; l--) {
          lineAllPoints.push(drillDataList[l].top[0])
          lineAllPoints.push(drillDataList[l].top[2])
          lineAllPoints.push(drillDataList[l].top[1])
        }
        lineAllPoints.push(drillDataList[0].bottom[0])
        lineAllPoints.push(drillDataList[0].bottom[2])
        lineAllPoints.push(drillDataList[0].bottom[1])

        lineAllPoints = new Float32Array(lineAllPoints)
        let lineAllGeo = new LineGeometry()
        lineAllGeo.setPositions(lineAllPoints)
        let allPointsLine = new Line2(lineAllGeo, allPointsLineMat)
        allPointsLine.name = drillDataList[0].realGroup
        pointsLineMeshList.push(allPointsLine)

        /* sideLPoints = []
        sideLPoints.push(drillDataList[drillDataList.length - 1].top[0])
        sideLPoints.push(drillDataList[drillDataList.length - 1].top[2])
        sideLPoints.push(drillDataList[drillDataList.length - 1].top[1])
        sideLPoints.push(drillDataList[drillDataList.length - 1].bottom[0])
        sideLPoints.push(drillDataList[drillDataList.length - 1].bottom[2])
        sideLPoints.push(drillDataList[drillDataList.length - 1].bottom[1])
        sideLPoints = new Float32Array(sideLPoints)
        let sideLineGeo1 = new LineGeometry()
        sideLineGeo1.setPositions(sideLPoints)
        let sideLine1 = new Line2(sideLineGeo1, sideLineMat)
        sideLine1.name = drillDataList[0].realGroup
        pointsSideLineMeshList.push(sideLine1)

        sideLPoints = []
        sideLPoints.push(drillDataList[0].top[0])
        sideLPoints.push(drillDataList[0].top[2])
        sideLPoints.push(drillDataList[0].top[1])
        sideLPoints.push(drillDataList[0].bottom[0])
        sideLPoints.push(drillDataList[0].bottom[2])
        sideLPoints.push(drillDataList[0].bottom[1])
        sideLPoints = new Float32Array(sideLPoints)
        let sideLineGeo2 = new LineGeometry()
        sideLineGeo2.setPositions(sideLPoints)
        let sideLine2 = new Line2(sideLineGeo2, sideLineMat)
        sideLine2.name = drillDataList[0].realGroup
        pointsSideLineMeshList.push(sideLine2) */

        /*points.push(new THREE.Vector3(drillDataList[0].bottom[0], drillDataList[0].bottom[2], drillDataList[0].bottom[1])) */
      }

      for (let i = 0; i < pointsBottomLineMeshList.length; i++) {
        scene.add(pointsBottomLineMeshList[i])
      }

      for (let i = 0; i < pointsTopLineMeshList.length; i++) {
        scene.add(pointsTopLineMeshList[i])
      }

      for (let i = 0; i < pointsSideLineMeshList.length; i++) {
        scene.add(pointsSideLineMeshList[i])
      }

      for (let i = 0; i < pointsLineMeshList.length; i++) {
        scene.add(pointsLineMeshList[i])
      }

      /* let indexDelaunay1 = Delaunator.from(
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
      coalBottomMesh = new THREE.Mesh(
        coalBottomGeom, // re-use the existing geometry
        coalBottomMeshMat
      )
      scene.add(coalBottomMesh)

      coalTopGeom.setIndex(meshIndex1) // add three.js index to the existing geometry
      coalTopGeom.computeVertexNormals()
      coalTopMesh = new THREE.Mesh(
        coalTopGeom, // re-use the existing geometry
        coalTopMeshMat
      )
      scene.add(coalTopMesh) */

      /* ------------------------- */
      let botts = {} // 底板点位{lineNo0:[point0,point1,point2],lineNo1:[point0,point1,point2]}
      let xyPoints = [] // [[x,y],[x,y],[x,y],[x,y],[x,y]]
      let topXYPoints = []
      let edges = []  // 边界
      for (let i = 0; i < this.bottomPointList.length; i++) {
        xyPoints.push([this.bottomPointList[i][0], this.bottomPointList[i][1]])
        topXYPoints.push([this.topPointList[i][0], this.topPointList[i][1]])
        let g = this.bottomPointList[i][3]  // lineNo
        if (!botts[g]) {
          botts[g] = []
          botts[g].push(this.bottomPointList[i])
        } else {
          botts[g].push(this.bottomPointList[i])
        }
      }
      console.log('botts:', botts)
      let gs = Object.keys(botts)
      let sgs = gs.sort((a, b) => {
        return (a - b)
      })
      console.log('sgs:', sgs)
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
          // 取边界时隔一排取一个，避免出现锯齿
          if (i % 2 === 0) {
            underLine.push(line[0])
          }
          if (i % 2 === 1) {
            if (line.length - 1 > 0) {
              upLine.push(line[line.length - 1])
            }
          }
          //if (line.length > 3) {
          // 一组钻孔上点位太少的，忽略之
          /* underLine.push(line[0])
          if (line.length - 1 > 0) {
            upLine.push(line[line.length - 1])
          } */
          //}
        }
      }
      // 最下边一行和最后一行要反转一下
      underLine = underLine.reverse()
      endLine = endLine.reverse()
      //console.log('startLine:', startLine)
      //console.log('upLine:', upLine)
      //console.log('endLine:', endLine)
      //console.log('underLine:', underLine)
      let finalBorderList = [...startLine, ...upLine, ...endLine, ...underLine]
      for (let i = 0; i < finalBorderList.length; i++) {
        if (i < finalBorderList.length - 1) {
          edges.push([Number(finalBorderList[i][4].replace('实钻-', '')), Number(finalBorderList[i + 1][4].replace('实钻-', ''))])
        } else {
          edges.push([Number(finalBorderList[i][4].replace('实钻-', '')), Number(finalBorderList[0][4].replace('实钻-', ''))])
        }
      }
      //console.log('finalBorderList:', finalBorderList)
      //console.log('xyPoints:', xyPoints)
      //console.log('edges:', edges)

      // 顶底面构建
      // 方法1
      let cdtTri = cdt2d(xyPoints, edges, { exterior: false })
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

      // 顶底面构建
      // 方法2
      /* let allIndex = []
      for (let i = 0; i < this.groupNoList.length - 1; i++) {
        let line1DrillDataList = []
        let line2DrillDataList = []
        /!* let tlXyPoints = [] *!/
        let tempEdges = []
        for (let j = 0; j < this.originDrillDataList.length; j++) {
          if (this.originDrillDataList[j].realGroup === this.groupNoList[i]) {
            line1DrillDataList.push(this.originDrillDataList[j])
          }
        }
        for (let j = 0; j < this.originDrillDataList.length; j++) {
          if (this.originDrillDataList[j].realGroup === this.groupNoList[i + 1]) {
            line2DrillDataList.push(this.originDrillDataList[j])
          }
        }

        line2DrillDataList = line2DrillDataList.reverse()
        let twoLineData = [...line1DrillDataList, ...line2DrillDataList]
        for (let i = 0; i < twoLineData.length; i++) {
          if (i < twoLineData.length - 1) {
            tempEdges.push([Number(twoLineData[i].name.replace('实钻-', '')), Number(twoLineData[i + 1].name.replace('实钻-', ''))])
          } else {
            tempEdges.push([Number(twoLineData[i].name.replace('实钻-', '')), Number(twoLineData[0].name.replace('实钻-', ''))])
          }
        }
        //cleanPSLG(xyPoints, tempEdges)
        // 按照底面计算
        let cdtTri = cdt2d(xyPoints, tempEdges, { interior: true, exterior: false, delaunay: true, infinity: false })
        // 如果算不出来按照顶面计算
        if (cdtTri.length <= 0) {
          cdtTri = cdt2d(topXYPoints, tempEdges, { interior: true, exterior: false, delaunay: true, infinity: false })
        }
        // 如果还算不出来，手动布线
        if (cdtTri.length <= 0) {
          //1. 从起始点开始遍历

        }
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
      } */

      // 构建边界面
      for (let i = 0; i < finalBorderList.length; i++) {
        if (i === 0) {
          allIndex.push(Number(finalBorderList[0][4].replace('实钻-', '')))
          allIndex.push(Number(finalBorderList[0][4].replace('实钻-', '')) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[1][4].replace('实钻-', '')))

          allIndex.push(Number(finalBorderList[0][4].replace('实钻-', '')))
          allIndex.push(Number(finalBorderList[finalBorderList.length - 1][4].replace('实钻-', '')) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[0][4].replace('实钻-', '')) + this.bottomPointList.length)
        } else if (i === finalBorderList.length - 1) {
          allIndex.push(Number(finalBorderList[i][4].replace('实钻-', '')))
          allIndex.push(Number(finalBorderList[i][4].replace('实钻-', '')) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[0][4].replace('实钻-', '')))

          allIndex.push(Number(finalBorderList[i][4].replace('实钻-', '')))
          allIndex.push(Number(finalBorderList[i - 1][4].replace('实钻-', '')) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[i][4].replace('实钻-', '')) + this.bottomPointList.length)
        } else {
          allIndex.push(Number(finalBorderList[i][4].replace('实钻-', '')))
          allIndex.push(Number(finalBorderList[i][4].replace('实钻-', '')) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[i + 1][4].replace('实钻-', '')))

          allIndex.push(Number(finalBorderList[i][4].replace('实钻-', '')))
          allIndex.push(Number(finalBorderList[i - 1][4].replace('实钻-', '')) + this.bottomPointList.length)
          allIndex.push(Number(finalBorderList[i][4].replace('实钻-', '')) + this.bottomPointList.length)
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
      /* loader.load('https://hhhz-1251951224.cos.ap-nanjing.myqcloud.com/geometry1.json', function(geo) {

            coalCubeObj = new THREE.Mesh(
                geo,
                coalCubeMat
            )
            coalCubeObj.rotation.x = Math.PI * 0.5
            coalCubeObj.rotation.z = Math.PI
          },
          // onProgress callback
          function(xhr) {
            console.log((xhr.loaded / xhr.total * 100) + '% loaded')
            if ((xhr.loaded / xhr.total * 100) >= 100) {

              setTimeout(function() {
                for (let i = 0; i < coalCubeObj.geometry.attributes.position.array.length; i++) {
                  if (i === 0 || i % 3 === 0) {
                    coalCubeObj.geometry.attributes.position.array[i] = coalCubeObj.geometry.attributes.position.array[i] * -1
                  }
                }
                scene.add(coalCubeObj)
              }, 1000)
            }
          },
          // onError callback

          function(err) {
            console.log('An error happened')
          }
      ) */
      scene.add(coalCubeObj)

      /* let meshIndex1 = [] // delaunay index => three.js index
      for (let i = 0; i < indexDelaunay1.triangles.length; i++) {
        meshIndex1.push(indexDelaunay1.triangles[i])
      }

      let meshIndex2 = [] // delaunay index => three.js index
      for (let i = 0; i < indexDelaunay2.triangles.length; i++) {
        meshIndex2.push(indexDelaunay2.triangles[i])
      } */

      /* let meshIndex1 = []
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
      scene.add(coalBottomMesh) */

      /* coalBottomWireMesh = new THREE.Mesh(
        coalBottomGeom,
        coalBottomWireMeshMat
      )
      scene.add(coalBottomWireMesh) */

      /* coalTopGeom.setIndex(meshIndex1)
      coalTopGeom.computeVertexNormals()
      coalTopMesh = new THREE.Mesh(
          coalTopGeom,
          coalTopMeshMat
      )
      scene.add(coalTopMesh) */

      // 创建抽采范围
      for (let i = 0; i < this.points3DBottom.length; i++) {
        // 添加原始钻孔抽采范围, 并计算
        //this.addDrillRangeMeshToCalc(this.points3DBottom[i], this.points3DTop[i], this.radius, originDrillRangeMat, initRoot, this.originDrillGroupMeshList, '实钻-' + i)
        if (this.topPointList[i][3] % 2 === 0) {
          this.addDrillRangeMeshToScene(this.points3DBottom[i], this.points3DTop[i], this.radius, originDrillRangeMatS, false, this.originDrillRangeToSceneList, '实钻-' + i)
        } else {
          this.addDrillRangeMeshToScene(this.points3DBottom[i], this.points3DTop[i], this.radius, originDrillRangeMatS, false, this.originDrillRangeToSceneList, '实钻-' + i)
        }
      }
      //this.createFault()
      /* coalTopWireMesh = new THREE.Mesh(
        coalTopGeom,
        coalTopWireMeshMat
      )
      scene.add(coalTopWireMesh) */

      // 煤层实体
      /* coalGeometry = new ConvexGeometry(pointsAll)
      coalCubeObj = new Mesh(coalGeometry, coalCubeMat)
      scene.add(coalCubeObj) */

      // 全选钻孔
      /* let allDrillSelect = []
      for (let i = 0; i < this.bottomPointList.length; i++) {
        allDrillSelect.push([this.bottomPointList[i][5], this.bottomPointList[i][4]])
      }
      this.getOptions(allDrillSelect) */
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
        this.workPointsMeshList.push(wkMesh)
        scene.add(wkMesh)
      }
    }
    ,
    /**
     * 创建巷道
     */
    createRoad() {
      let rw = this.roadWay
      if (rw !== null) {
        const roadShape = new THREE.Shape()
            .moveTo(-(rw.wide / 2) - 0.2, -rw.height - 0.5)
            .lineTo(-(rw.wide / 2) - 0.2, 0)
            .absarc(0, 0, (rw.wide / 2) + 0.2, Math.PI, 0, true)
            .lineTo((rw.wide / 2) + 0.2, -rw.height - 0.5).lineTo(-(rw.wide / 2) - 0.2, -rw.height - 0.5)
        const roadShapeHole = new THREE.Shape()
            .moveTo(-(rw.wide / 2), -rw.height)
            .lineTo(-(rw.wide / 2), 0)
            .absarc(0, 0, (rw.wide / 2), Math.PI, 0, true)
            .lineTo((rw.wide / 2), -rw.height).lineTo(-(rw.wide / 2), -rw.height)
        /* const roadShape = new THREE.Shape()
            .moveTo(-2, -3)
            .lineTo(-2, 0)
            .absarc(0, 0, 2, Math.PI, 0, true)
            .lineTo(2, -3).lineTo(-2, -3)

        const roadShapeHole = new THREE.Shape()
            .moveTo(-1.8, -2.5)
            .lineTo(-1.8, 0)
            .absarc(0, 0, 1.8, Math.PI, 0, true)
            .lineTo(1.8, -2.5).lineTo(-1.8, -2.5) */

        roadShape.holes.push(roadShapeHole)
        let startWP = new THREE.Vector3(this.workPoints[0]['x'], this.workPoints[0]['z'], this.workPoints[0]['y'])
        let endWP = new THREE.Vector3(this.workPoints[this.workPoints.length - 1]['x'], this.workPoints[this.workPoints.length - 1]['z'], this.workPoints[this.workPoints.length - 1]['y'])
        this.roadLen = this.calDistance(startWP.x, startWP.y, startWP.z, endWP.x, endWP.y, endWP.z)
        const extrudeSettings = {
          depth: this.roadLen,
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

        scene.add(roadMesh)
      }
    }
    ,
    /**
     * 创建GUI面板
     */
    createGUI() {
      // gui
      if (!gui) {
        gui = new GUI({ width: 250, autoPlace: false })
        /* tGUIContainer.append(gui.domElement) */
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

        gui.add(params, 'drillSelectMode').name('钻孔选择模式')

        /* window.addEventListener('resize', function() {

          camera.aspect = window.innerWidth / window.innerHeight
          camera.updateProjectionMatrix()

          renderer.setSize(window.innerWidth, window.innerHeight)

        }, false) */

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
      }
    }
    ,
    /**
     * 创建断层
     */
    createFault() {
      let l = [
        new THREE.Vector3(8.21, 19.33 + 0.2, 33),
        new THREE.Vector3(12.78, 14.19 + 0.2, 33),
        new THREE.Vector3(19.9, 13.42 + 0.2, 33),
        new THREE.Vector3(25.65, 11.96 + 0.2, 33),
        new THREE.Vector3(31.93, 12.26 + 0.2, 33),
        new THREE.Vector3(38.99, 12.67 + 0.2, 33),
        new THREE.Vector3(46.57, 11.61 + 0.2, 33),
        new THREE.Vector3(49.08, 9.54 + 0.2, 33),
        new THREE.Vector3(56.3, 8.92 + 0.2, 33),
        new THREE.Vector3(58.56, 7.19 + 0.2, 33),
        new THREE.Vector3(56.56, 22.85 + 0.2, 36),
        new THREE.Vector3(48.03, 22.4 + 0.2, 36),
        new THREE.Vector3(40.54, 20.66 + 0.2, 36),
        new THREE.Vector3(36.3, 20.12 + 0.2, 36),
        new THREE.Vector3(27.77, 18.73 + 0.2, 36),
        new THREE.Vector3(22.64, 19.68 + 0.2, 36),
        new THREE.Vector3(20.15, 20.15 + 0.2, 36),
        new THREE.Vector3(16.39, 19.53 + 0.2, 36),
        new THREE.Vector3(11.64, 20.99 + 0.2, 36),
        new THREE.Vector3(6.58, 21.52 + 0.2, 36)]
      let ll = []
      for (let i = 0; i < l.length; i++) {
        ll.push([l[i].x, l[i].y])
      }
      let cdtTri = cdt2d(ll, [[0, 1], [1, 2], [2, 3], [3, 4], [4, 5], [5, 6], [6, 7], [7, 8], [8, 9], [9, 10], [10, 11], [11, 12], [12, 13], [13, 14], [14, 15], [15, 16], [16, 17], [17, 18], [18, 19], [19, 0]], { exterior: false })
      //let llllLine = new THREE.Line(new THREE.BufferGeometry().setFromPoints(l), new THREE.LineBasicMaterial({ color: 0xffff00 }))
      //scene.add(llllLine)
      let faultIndex = []
      for (let i = 0; i < cdtTri.length; i++) {
        faultIndex.push(cdtTri[i][0])
        faultIndex.push(cdtTri[i][1])
        faultIndex.push(cdtTri[i][2])
      }
      let faultGeometry = new THREE.BufferGeometry().setFromPoints(l)
      faultGeometry.setIndex(faultIndex)
      faultGeometry.computeVertexNormals()
      console.log('faultGeometry:', faultGeometry)
      let faultObj = new THREE.Mesh(
          faultGeometry,
          new THREE.MeshBasicMaterial({ color: 0x990000, side: THREE.DoubleSide, opacity: 0.7, transparent: true })
      )
      outline2Pass.selectedObjects = [faultObj]
      scene.add(faultObj)
      /* let cdtTri = cdt2d(xyPoints, edges, { exterior: false })
      //console.log('cdt2d:', cdtTri)

      let allIndex = []
      // 底面
      for (let i = 0; i < cdtTri.length; i++) {
        allIndex.push(cdtTri[i][0])
        allIndex.push(cdtTri[i][1])
        allIndex.push(cdtTri[i][2])
      } */

    },
    /**
     * 添加原始钻孔实体
     */
    createOriginDrill() {
      //console.log('-------------------------底板点位-------------------------------')
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
        let ep2 = new THREE.Vector3(this.finalPointList[i][0], this.finalPointList[i][2], this.finalPointList[i][1])
        // 单双排使用不同材质
        if (group % 2 === 0) {
          this.addDrillMesh(sp, ep0, 0.1, originDrillMatD, this.originDrillMeshList, this.bottomPointList[i][4])
          this.addDrillMesh(ep1, ep2, 0.1, originDrillMatD, this.originDrillBeyondCoalMeshList, this.finalPointList[i][4])
        } else {
          this.addDrillMesh(sp, ep0, 0.1, originDrillMatS, this.originDrillMeshList, this.bottomPointList[i][4])
          this.addDrillMesh(ep1, ep2, 0.1, originDrillMatS, this.originDrillBeyondCoalMeshList, this.finalPointList[i][4])
        }
        // 煤孔段
        this.addDrillMesh(ep0, ep1, 0.1, drillInCoalMat, this.originDrillInCoalMeshList, this.bottomPointList[i][4])
        // 底板
        //console.log(ep0.x, ep0.y, ep0.z)
        // 顶板
        //console.log(ep1.x, ep1.y, ep1.z)
      }
      //console.log('--------------------------------------------------------')
    }
    ,
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
    }
    ,
    /**
     * 外部发光
     * @param selectedObjects
     */
    /* initFaultObj() {
      // 创建一个EffectComposer（效果组合器）对象，然后在该对象上添加后期处理通道。
      composer2 = new EffectComposer(renderer)
      composer2.readBuffer.texture.encoding = THREE.sRGBEncoding
      composer2.writeBuffer.texture.encoding = THREE.sRGBEncoding
      // 新建一个场景通道  为了覆盖到原理来的场景上
      renderPass2 = new RenderPass(scene, camera)
      composer2.addPass(renderPass)

      const gammaCorrectionShader = new ShaderPass(GammaCorrectionShader)
      composer2.addPass(gammaCorrectionShader)

      // 物体边缘发光通道
      outline2Pass = new OutlinePass(new THREE.Vector2(outputContainer.clientWidth, outputContainer.clientHeight), scene, camera)
      outline2Pass.edgeStrength = 10 // 边框的亮度
      outline2Pass.edgeGlow = 1// 光晕[0,1]
      outline2Pass.usePatternTexture = false // 是否使用父级的材质
      outline2Pass.edgeThickness = 4 // 边框宽度
      outline2Pass.pulsePeriod = 2 // 呼吸闪烁的速度
      outline2Pass.visibleEdgeColor.set(parseInt('0x00ffaa')) // 呼吸显示的颜色
      outline2Pass.hiddenEdgeColor.set(parseInt('0x00ffaa')) // 呼吸显示的颜色
      outline2Pass.renderToScreen = true
      composer2.addPass(outline2Pass)
    }, */
    /**
     * 外部发光
     * @param selectedObjects
     */
    initOutlineObj() {
      // 创建一个EffectComposer（效果组合器）对象，然后在该对象上添加后期处理通道。
      composer = new EffectComposer(renderer)
      composer.readBuffer.texture.encoding = THREE.sRGBEncoding
      composer.writeBuffer.texture.encoding = THREE.sRGBEncoding
      // 新建一个场景通道  为了覆盖到原理来的场景上
      renderPass = new RenderPass(scene, camera)
      composer.addPass(renderPass)

      const gammaCorrectionShader = new ShaderPass(GammaCorrectionShader)
      composer.addPass(gammaCorrectionShader)

      // 物体边缘发光通道
      outlinePass = new OutlinePass(new THREE.Vector2(outputContainer.clientWidth, outputContainer.clientHeight), scene, camera)
      outlinePass.edgeStrength = 10 // 边框的亮度
      outlinePass.edgeGlow = 1// 光晕[0,1]
      outlinePass.usePatternTexture = false // 是否使用父级的材质
      outlinePass.edgeThickness = 4 // 边框宽度
      outlinePass.pulsePeriod = 2 // 呼吸闪烁的速度
      outlinePass.visibleEdgeColor.set(parseInt('0xc85862')) // 呼吸显示的颜色
      outlinePass.hiddenEdgeColor.set(parseInt('0xffffff')) // 呼吸显示的颜色
      outlinePass.renderToScreen = true
      composer.addPass(outlinePass)

      // 物体边缘发光通道
      outline2Pass = new OutlinePass(new THREE.Vector2(outputContainer.clientWidth, outputContainer.clientHeight), scene, camera)
      outline2Pass.edgeStrength = 10 // 边框的亮度
      outline2Pass.edgeGlow = 0.5// 光晕[0,1]
      outline2Pass.usePatternTexture = false // 是否使用父级的材质
      outline2Pass.edgeThickness = 5 // 边框宽度
      outline2Pass.pulsePeriod = 2 // 呼吸闪烁的速度
      outline2Pass.visibleEdgeColor.set(parseInt('0x990000')) // 呼吸显示的颜色
      outline2Pass.hiddenEdgeColor.set(parseInt('0x990000')) // 呼吸显示的颜色
      outline2Pass.renderToScreen = true
      composer.addPass(outline2Pass)
    }
    ,

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
      brushMat.transparent = true
      brushMat.opacity = 0.3
      brushMat.depthWrite = true
      brushMat.color.set(0xffc400).convertSRGBToLinear()

      // 原始钻孔材质
      originDrillMat = brushMat.clone()
      originDrillMat.transparent = true
      originDrillMat.opacity = 0.5
      originDrillMat.color.set(0xffc400).convertSRGBToLinear()

      // 原始钻孔材质
      originDrillMatS = brushMat.clone()
      originDrillMatS.depthWrite = true
      originDrillMatS.transparent = false
      //originDrillMatS.color.set(0xffc4ff).convertSRGBToLinear()
      originDrillMatS.color.set(0x00f0f0).convertSRGBToLinear()
      originDrillMatD = brushMat.clone()
      originDrillMatD.depthWrite = true
      originDrillMatD.transparent = false
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
      originDrillRangeMat.depthWrite = false
      originDrillRangeMat.color.set(0xffc400).convertSRGBToLinear()

      // 单双排钻孔抽采范围材质
      originDrillRangeMatS = brushMat.clone()
      originDrillRangeMatS.transparent = true
      originDrillRangeMatS.opacity = params.oriDRMOpacity
      //originDrillRangeMatS.color.set(0xffc4ff).convertSRGBToLinear()
      originDrillRangeMatS.color.set(0x00f0f0).convertSRGBToLinear()

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
      addDrillRangeMat.opacity = params.addDRMOpacity
      addDrillRangeMat.color.set(0x00f0f0).convertSRGBToLinear()

      // 巷道材质
      roadMat = new THREE.MeshPhongMaterial()
      roadMat.side = THREE.DoubleSide
      roadMat.transparent = true
      roadMat.opacity = 0.95
      roadMat.color.set(0xb7af7e).convertSRGBToLinear()

      // 结果材质
      resultGridMat = brushMat.clone()
      resultGridMat.transparent = true
      resultGridMat.opacity = 0
      resultGridMat.depthWrite = true
      resultGridMat.polygonOffsetFactor = -14
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
      coalTopMeshMat = new THREE.MeshBasicMaterial()
      //coalTopMeshMat.color.set(0x99cc00).convertSRGBToLinear()
      coalTopMeshMat.color.set(0x99cc00).convertSRGBToLinear()
      //coalTopMeshMat.wireframe = true
      //coalTopMeshMat.side = THREE.DoubleSide
      //coalTopMeshMat.depthWrite = true
      //coalTopMeshMat.transparent = false
      //coalTopMeshMat.opacity = 1

      coalBottomMeshMat = new THREE.MeshBasicMaterial()
      //coalBottomMeshMat.color.set(0x99ccff).convertSRGBToLinear()
      coalBottomMeshMat.color.set(0xff0000).convertSRGBToLinear()
      //coalBottomMeshMat.wireframe = true
      //coalBottomMeshMat.side = THREE.DoubleSide
      //coalBottomMeshMat.depthWrite = true
      //coalBottomMeshMat.transparent = false
      //coalBottomMeshMat.opacity = 1

      coalTopWireMeshMat = new THREE.MeshPhongMaterial({ color: 0xff0000 })
      coalTopWireMeshMat.wireframe = true
      coalTopWireMeshMat.side = THREE.DoubleSide
      coalTopWireMeshMat.transparent = false
      coalTopWireMeshMat.opacity = 1
      coalTopWireMeshMat.color.set(0xff0000).convertSRGBToLinear()

      coalBottomWireMeshMat = new THREE.MeshPhongMaterial()
      coalBottomWireMeshMat.wireframe = true
      coalBottomWireMeshMat.side = THREE.DoubleSide
      coalBottomWireMeshMat.transparent = false
      coalBottomWireMeshMat.opacity = 1
      coalBottomWireMeshMat.color.set(0x00ff00).convertSRGBToLinear()

      // 原始空白带材质
      originEmptyBandMat = new THREE.MeshLambertMaterial({ color: 0xffffff })
      originEmptyBandMat.transparent = false
      originEmptyBandMat.opacity = 1
      originEmptyBandMat.depthWrite = true
      originEmptyBandMat.polygonOffset = true
      originEmptyBandMat.polygonOffsetFactor = -16
      originEmptyBandMat.polygonOffsetUnits = 1

      // 煤层块
      coalCubeMat = new THREE.MeshLambertMaterial()
      //coalCubeMat = new THREE.MeshBasicMaterial()
      coalCubeMat.wireframe = false
      coalCubeMat.side = THREE.DoubleSide
      coalCubeMat.transparent = true
      coalCubeMat.opacity = 0.7
      //coalCubeMat.alphaTest = 0.5
      coalCubeMat.depthWrite = true
      coalCubeMat.color.set(0x7D7C82).convertSRGBToLinear()
      //coalCubeMat.color.set(0xffffff).convertSRGBToLinear()

      let resolution = new THREE.Vector2(outputContainer.clientWidth, outputContainer.clientHeight)
      //bottomLineMat = new LineMaterial({ color: 0xff0000, linewidth: 10 ,resolution})
      bottomLineMat = new LineMaterial({ color: 0xff0000, linewidth: 0.001, alphaToCoverage: false })
      //bottomLineMat.resolution.set(outputContainer.clientWidth, outputContainer.clientHeight)
      //topLineMat = new LineMaterial({ color: 0x99cc00, linewidth: 10 ,resolution})
      topLineMat = new LineMaterial({ color: 0x99cc00, linewidth: 0.001, alphaToCoverage: false })
      //topLineMat.resolution.set(outputContainer.clientWidth, outputContainer.clientHeight)
      //sideLineMat = new LineMaterial({ color: 0xffffff, linewidth: 10 ,resolution})
      sideLineMat = new LineMaterial({ color: 0xffffff, linewidth: 0.001, alphaToCoverage: false })
      allPointsLineMat = new LineMaterial({ color: 0xaa00aa, linewidth: 0.01, alphaToCoverage: false })
      //sideLineMat.resolution.set(outputContainer.clientWidth, outputContainer.clientHeight)
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

.el-form-item__label {
  padding: 0 5px 0 0;
}

.drill-desc {
  position: absolute;
  top: 180px;
  left: 20px;
//width: calc(100% - 300px); width: 350px; height: 300px;
}

.el-color-picker__trigger .el-color-picker__icon {
  display: none;
}

.el-button--checkempty {
  background-color: #E6A23C;
  color: #FFFFFF;
  float: right;
  width: 160px;
  border: solid 3px #000;
  outline: solid 3px #E6A23C;
}

.el-button--checkempty:hover {
  background: #ffc833;
  border: solid 3px #000;
  outline: solid 3px #ffc833;
  color: #FFFFFF;
}

.el-button--checkempty:focus {
  background: #ffc833;
  border: solid 3px #000;
  outline: solid 3px #ffc833;
  color: #FFFFFF;
}

.el-notification {
  left: 300px !important;
}

.el-popover__reference:focus:hover {
}

.el-popover {
  background-color: #ffffff;
  border: 0;
}

.el-form-item--mini.el-form-item {
  margin-bottom: 5px;
  margin-top: 5px;
}
</style>
