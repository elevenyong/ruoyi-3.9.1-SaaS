<template>
  <div class="app-container">
    <!-- 底抽巷 -->
    <!-- <transition name="el-fade-in"> -->
    <div>
      <el-row :gutter="20">
        <!--工作面列表-->
        <el-col :span="6" :xs="24">
          <el-card style="height: 80vh; margin: 10px 5px;overflow-y: auto">
            <div slot="header" class="clearfix">
              <span>工作面列表</span>
              <div style="float: right">
                <el-button size="mini" type="primary" @click="setCurrentWorkFace(1,{})"
                >新增采煤工作面
                </el-button>
                <el-button size="mini" type="primary" @click="setCurrentWorkFace(2,{})"
                >新增揭煤工作面
                </el-button>
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
                  <el-submenu index="2">
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
                  <el-submenu index="3">
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
        <!--掘进工作面数据信息-->
        <el-col v-if="showInfo===0" :span="18" :xs="24">
          <h4>基本信息</h4>
          <el-divider/>
          <el-form
              ref="queryForm"
              :inline="true"
              :model="queryParams"
              label-width="98px"
              size="small"
          >
            <el-form-item label="工作面名称：" prop="workfaceName">
              <el-input
                  v-model="currentWorkFace.workfaceName"
                  clearable
                  placeholder="请输入"
                  :disabled="!editable"
                  style="width: 150px"
              />
            </el-form-item>
            <el-form-item label="所属采区：">
              <el-select
                  v-model="currentWorkFace.miningAreaId"
                  :disabled="!editable"
                  placeholder="请选择"
                  style="width: 150px"
              >
                <el-option
                    v-for="area in areas"
                    :key="area.id"
                    :label="area.miningArea"
                    :value="area.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="走向长度：" prop="towardsLength">
              <el-input
                  v-model="currentWorkFace.towardsLength"
                  placeholder="请输入"
                  :disabled="!editable"
                  @blur="currentWorkFace.towardsLength = Number(currentWorkFace.towardsLength)"
                  style="width: 120px"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="切眼长度：" prop="cutLength">
              <el-input
                  v-model="currentWorkFace.cutLength"
                  placeholder="请输入"
                  :disabled="!editable"
                  @blur="currentWorkFace.cutLength = Number(currentWorkFace.cutLength)"
                  style="width: 120px"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="所属煤层：" prop="coalId">
              <el-select
                  v-model="currentWorkFace.coalId"
                  :disabled="!editable"
                  placeholder="请选择"
                  style="width: 150px"
              >
                <el-option
                    v-for="coal in coalInfo"
                    :key="coal.id"
                    :label="coal.coalSerial"
                    :value="coal.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button
                  v-if="currentWorkFace.id != null && editable === false"
                  icon="el-icon-edit"
                  size="mini"
                  type="primary"
                  @click="
                  () => {
                    editable = true;
                  }
                "
              >修改
              </el-button>
              <el-button
                  v-if="editable"
                  icon="el-icon-check"
                  size="mini"
                  type="primary"
                  @click="updateWorkFace"
              >保存
              </el-button>
              <el-button
                  v-if="currentWorkFace.id != null"
                  icon="el-icon-delete"
                  size="mini"
                  type="danger"
                  @click="delWorkFace"
              >删除
              </el-button>
            </el-form-item>
          </el-form>

          <el-card>
            <div slot="header" class="clearfix">
              <span>巷道区段创建</span>
              <div style="float: right; padding: 3px 0">
                <span>已创建巷道0节</span>
                <el-button size="mini" style="margin: 0 5px" type="primary"
                >增加
                </el-button>
                <el-button size="mini" style="margin: 0 5px" type="primary"
                >导入
                </el-button>
              </div>
            </div>
            <div>
              <el-table
                  v-loading="loading"
                  :data="currentRoad.troadwayZoneList"
                  @selection-change="handleSelectionChange"
              >
                <!-- <el-table-column type="selection" width="50" align="center" /> -->
                <el-table-column align="center" label="序号" prop="id"/>
                <el-table-column
                    :show-overflow-tooltip="true"
                    align="center"
                    label="名称"
                    prop="zoneName"
                />
                <el-table-column
                    :show-overflow-tooltip="true"
                    align="center"
                    label="X坐标"
                    prop="x"
                />
                <el-table-column
                    :show-overflow-tooltip="true"
                    align="center"
                    label="Y坐标"
                    prop="y"
                />
                <el-table-column
                    :show-overflow-tooltip="true"
                    align="center"
                    label="Z坐标"
                    prop="z"
                />
                <el-table-column
                    align="center"
                    label="方位角(°)"
                    prop="azimuthAngle"
                    width="120"
                />
                <el-table-column
                    align="center"
                    label="倾角(°)"
                    prop="inclinationAngle"
                    width="120"
                />
                <!--  <el-table-column
                  label="操作"
                  align="center"
                  width="120"
                >
                  <template slot-scope="scope" v-if="scope.row.userId !== 1">
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handleUpdate(scope.row)"
                      v-hasPermi="['system:user:edit']"
                      >修改
                    </el-button>
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-delete"
                      @click="handleDelete(scope.row)"
                      v-hasPermi="['system:user:remove']"
                      >删除
                    </el-button>
                  </template>
                </el-table-column> -->
              </el-table>
            </div>
          </el-card>

          <el-card style="margin-top: 20px">
            <div slot="header" class="clearfix">
              <span>导线点创建</span>
              <div style="float: right; padding: 3px 0">
                <span>已创建导线点0个</span>
                <el-button size="mini" style="margin: 0 5px" type="primary"
                >增加
                </el-button>
                <el-button size="mini" style="margin: 0 5px" type="primary"
                >导入
                </el-button>
              </div>
            </div>
            <div>
              <el-table
                  v-loading="loading"
                  :data="userList"
                  @selection-change="handleSelectionChange"
              >
                <!-- <el-table-column type="selection" width="50" align="center" /> -->
                <el-table-column
                    key="userId"
                    align="center"
                    label="序号"
                    prop="userId"
                />
                <el-table-column
                    key="userName"
                    :show-overflow-tooltip="true"
                    align="center"
                    label="名称"
                    prop="userName"
                />
                <el-table-column
                    key="nickName"
                    :show-overflow-tooltip="true"
                    align="center"
                    label="X坐标"
                    prop="nickName"
                />
                <el-table-column
                    key="deptName"
                    :show-overflow-tooltip="true"
                    align="center"
                    label="Y坐标"
                    prop="dept.deptName"
                />
                <el-table-column
                    key="postName"
                    :show-overflow-tooltip="true"
                    align="center"
                    label="Z坐标"
                    prop="roles[0].roleName"
                />
                <el-table-column
                    key="phonenumber"
                    align="center"
                    label="操作"
                    prop="phonenumber"
                    width="120"
                >
                  <template v-if="scope.row.userId !== 1" slot-scope="scope">
                    <el-button
                        v-hasPermi="['system:user:edit']"
                        icon="el-icon-edit"
                        size="mini"
                        type="text"
                        @click="handleUpdate(scope.row)"
                    >修改
                    </el-button>
                    <el-button
                        v-hasPermi="['system:user:remove']"
                        icon="el-icon-delete"
                        size="mini"
                        type="text"
                        @click="handleDelete(scope.row)"
                    >删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-card>
        </el-col>

        <!--采煤工作面数据信息-->
        <el-col v-if="showInfo===1" :span="18" :xs="24">
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
          </el-form>
          <el-divider/>
          <h4>巷道关联</h4>
          <el-form ref="coalMiningWFForm" :model="coalMiningWF" label-width="130px">
            <!--            <el-form-item label="关联底抽巷" prop="bottomExtractionRoadway">
                          &lt;!&ndash;          <el-input v-model="coalMiningWF.bottomExtractionRoadway" placeholder="请输入关联底抽巷"/>&ndash;&gt;
                          <el-select
                            v-model="coalMiningWF.bottomExtractionRoadway"
                            multiple
                            placeholder="请选择关联底抽巷"
                          >
                            <el-option
                              v-for="road in dchRoadList"
                              :key="road.id"
                              :label="road.roadway"
                              :value="road.id"
                            />
                          </el-select>
                        </el-form-item>-->
            <el-form-item label="关联进风巷" prop="airIntakeRoadway">
              <!--          <el-input v-model="coalMiningWF.airIntakeRoadway" placeholder="请输入关联进风巷"/>-->
              <el-select
                  v-model="coalMiningWF.airIntakeRoadway"
                  placeholder="请选择关联进风巷"
              >
                <el-option
                    v-for="road in jfhRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
              进风底抽巷
              <el-select
                  v-model="coalMiningWF.dcAirIn"
                  placeholder="请选择"
                  @change="changeForceUpdate"
              >
                <el-option
                    v-for="road in dchRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="关联回风巷" prop="airReturnRoadway">
              <!--          <el-input v-model="coalMiningWF.airReturnRoadway" placeholder="请输入关联回风巷"/>-->
              <el-select
                  v-model="coalMiningWF.airReturnRoadway"
                  placeholder="请选择关联回风巷"
              >
                <el-option
                    v-for="road in hfhRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
              回风底抽巷:
              <el-select
                  v-model="coalMiningWF.dcAirReturn"
                  placeholder="请选择"
                  @change="changeForceUpdate"
              >
                <el-option
                    v-for="road in dchRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="关联切眼巷" prop="cutEyeRoadway">
              <!--          <el-input v-model="coalMiningWF.cutEyeRoadway" placeholder="请输入关联切眼巷"/>-->
              <el-select
                  v-model="coalMiningWF.cutEyeRoadway"
                  placeholder="请选择关联切眼巷"
              >
                <el-option
                    v-for="road in qyhRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
              切眼底抽巷:
              <el-select
                  v-model="coalMiningWF.dcCutEye"
                  placeholder="请选择"
                  @change="changeForceUpdate"
              >
                <el-option
                    v-for="road in dchRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="关联采区大巷" prop="miningAreaRoadway">
              <!--          <el-input v-model="coalMiningWF.miningAreaRoadway" placeholder="请输入关联采区大巷"/>-->
              <el-select
                  v-model="coalMiningWF.miningAreaRoadway"
                  placeholder="请选择关联采区大巷"
              >
                <el-option
                    v-for="road in cqdhRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
              中间底抽巷:
              <el-select
                  v-model="coalMiningWF.dcMiddle"
                  placeholder="请选择"
                  @change="changeForceUpdate"
              >
                <el-option
                    v-for="road in dchMiddleRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <!--              v-if="currentWorkFace.id != null && editable === false"-->
              <!--              <el-button
                              icon="el-icon-edit"
                              size="mini"
                              type="primary"
                              @click="
                                () => {
                                  editable = true;
                                }
                              "
                            >修改
                            </el-button>-->
              <!--              v-if="editable"-->
              <el-button
                  icon="el-icon-check"
                  size="mini"
                  type="primary"
                  @click="updateCoalMiningWFForm"
              >保存
              </el-button>
              <!--              v-if="currentWorkFace.id != null"-->
              <el-button
                  v-if="coalMiningWF.id"
                  icon="el-icon-delete"
                  size="mini"
                  type="danger"
                  @click="delCoalMiningWorkFace"
              >删除
              </el-button>
            </el-form-item>
          </el-form>
        </el-col>

        <!--揭煤工作面数据信息-->
        <el-col v-if="showInfo===2" :span="18" :xs="24">
          <!--:rules="rules"-->
          <h4>基本信息</h4>
          <el-divider/>
          <el-form ref="uncoverCoalWFForm" :model="uncoverCoalWF">
            <el-form-item label="揭煤工作面名称" prop="workfaceName" label-width="130px">
              <el-input v-model="uncoverCoalWF.workfaceName" placeholder="请输入采煤工作面名称"/>
            </el-form-item>
            <el-form-item label="走向长度" prop="towardsLength" label-width="130px">
              <el-input v-model="uncoverCoalWF.towardsLength" placeholder="请输入走向长度">
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="切眼长度" prop="cutLength" label-width="130px">
              <el-input v-model="uncoverCoalWF.cutLength" placeholder="请输入切眼长度">
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="高度" prop="height" label-width="130px">
              <el-input v-model="uncoverCoalWF.height" placeholder="请输入高度">
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
          </el-form>
          <el-divider/>
          <h4>巷道关联</h4>
          <el-form ref="uncoverCoalWFForm" :model="uncoverCoalWF">
            <!--            <el-form-item label="关联底抽巷" prop="bottomExtractionRoadway">
                          &lt;!&ndash;          <el-input v-model="coalMiningWF.bottomExtractionRoadway" placeholder="请输入关联底抽巷"/>&ndash;&gt;
                          <el-select
                            v-model="coalMiningWF.bottomExtractionRoadway"
                            multiple
                            placeholder="请选择关联底抽巷"
                          >
                            <el-option
                              v-for="road in dchRoadList"
                              :key="road.id"
                              :label="road.roadway"
                              :value="road.id"
                            />
                          </el-select>
                        </el-form-item>-->
            <el-form-item label="关联进风巷" prop="airIntakeRoadway">
              <!--          <el-input v-model="coalMiningWF.airIntakeRoadway" placeholder="请输入关联进风巷"/>-->
              <el-select
                  v-model="uncoverCoalWF.airIntakeRoadway"
                  placeholder="请选择关联进风巷"
              >
                <el-option
                    v-for="road in jfhRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
              进风底抽巷:
              <el-select
                  v-model="uncoverCoalWF.dcAirIn"
                  placeholder="请选择"
                  @change="changeForceUpdate"
              >
                <el-option
                    v-for="road in dchRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="关联回风巷" prop="airReturnRoadway">
              <!--          <el-input v-model="coalMiningWF.airReturnRoadway" placeholder="请输入关联回风巷"/>-->
              <el-select
                  v-model="uncoverCoalWF.airReturnRoadway"
                  placeholder="请选择关联回风巷"
              >
                <el-option
                    v-for="road in hfhRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
              回风底抽巷:
              <el-select
                  v-model="uncoverCoalWF.dcAirReturn"
                  placeholder="请选择"
                  @change="changeForceUpdate"
              >
                <el-option
                    v-for="road in dchRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="关联切眼巷" prop="cutEyeRoadway">
              <!--          <el-input v-model="coalMiningWF.cutEyeRoadway" placeholder="请输入关联切眼巷"/>-->
              <el-select
                  v-model="uncoverCoalWF.cutEyeRoadway"
                  placeholder="请选择关联切眼巷"
              >
                <el-option
                    v-for="road in qyhRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
              切眼底抽巷:
              <el-select
                  v-model="uncoverCoalWF.dcCutEye"
                  placeholder="请选择"
                  @change="changeForceUpdate"
              >
                <el-option
                    v-for="road in dchRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="关联采区大巷" prop="miningAreaRoadway">
              <!--          <el-input v-model="coalMiningWF.miningAreaRoadway" placeholder="请输入关联采区大巷"/>-->
              <el-select
                  v-model="uncoverCoalWF.miningAreaRoadway"
                  placeholder="请选择关联采区大巷"
              >
                <el-option
                    v-for="road in cqdhRoadList"
                    :key="road.id"
                    :label="road.roadway"
                    :value="road.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <!--              v-if="currentWorkFace.id != null && editable === false"-->
              <!--              <el-button
                              icon="el-icon-edit"
                              size="mini"
                              type="primary"
                              @click="
                                () => {
                                  editable = true;
                                }
                              "
                            >修改
                            </el-button>-->
              <!--              v-if="editable"-->
              <el-button
                  icon="el-icon-check"
                  size="mini"
                  type="primary"
                  @click="updateUncoverCoalWFForm"
              >保存
              </el-button>
              <!--              v-if="currentWorkFace.id != null"-->
              <el-button
                  v-if="uncoverCoalWF.id"
                  icon="el-icon-delete"
                  size="mini"
                  type="danger"
                  @click="delUncoverCoalWorkFace"
              >删除
              </el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
    <!--  </transition> -->

    <!-- 上传对话框 -->
    <el-dialog
        :title="upload.title"
        :visible.sync="upload.open"
        append-to-body
        width="400px"
    >
      <el-upload
          ref="upload"
          :action="upload.url"
          :auto-upload="false"
          :disabled="upload.isUploading"
          :headers="upload.headers"
          :limit="1"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess"
          accept=".xlsx, .xls"
          drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div slot="tip" class="el-upload__tip text-center">
          <!-- <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" />
            是否更新已经存在的用户数据
          </div> -->
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link
              :underline="false"
              style="font-size: 12px; vertical-align: baseline"
              type="primary"
              @click="importTemplate"
          >下载模板
          </el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改菜单对话框 -->
    <el-dialog
        title="新增工作面"
        :visible.sync="addwfshow"
        append-to-body
        width="680px"
    >
      <el-form
          ref="queryForm"
          :inline="true"
          :model="addWFData"
          label-width="98px"
          size="small"
      >
        <el-form-item label="工作面名称：">
          <el-input v-model="addWFData.workfaceName" clearable placeholder="请输入"/>
        </el-form-item>
        <el-form-item label="所属采区：">
          <el-select
              v-model="addWFData.miningAreaId"
              placeholder="请选择"
          >
            <el-option
                v-for="area in areas"
                :key="area.id"
                :label="area.miningArea"
                :value="area.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="所属煤层：">
          <el-select
              v-model="addWFData.coalId"
              placeholder="请选择"
          >
            <el-option
                v-for="coal in coalInfo"
                :key="coal.id"
                :label="coal.coalSerial"
                :value="coal.id"
            />
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="断面形状：">
                  <el-select
                    v-model="roadData.faultShapId"
                    clearable
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="dict in shapeTpyes"
                      :key="dict.faultShapId"
                      :label="dict.shape"
                      :value="dict.faultShapId"
                    >
                      <svg-icon :icon-class="dict.shape"/>
                      <span style="float: right; font-size: 13px">{{ dict.shape }}</span>
                    </el-option>
                  </el-select>
                </el-form-item>-->
        <el-form-item label="走向长度：">
          <el-input
              v-model="addWFData.towardsLength"
              clearable
              placeholder="请输入"
              @blur="addWFData.towardsLength = Number(addWFData.towardsLength)"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="切眼长度：">
          <el-input
              v-model="addWFData.cutLength"
              clearable
              placeholder="请输入"
              @blur="addWFData.cutLength = Number(addWFData.cutLength)"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <!--        <el-form-item label="方位角：">
                  <el-input
                    v-model="roadData.azimuthAngle"
                    clearable
                    placeholder="请输入"
                    @blur="roadData.azimuthAngle = Number(roadData.azimuthAngle)"
                  >
                    <template slot="append">°</template>
                  </el-input>
                </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
            size="mini"
            type="error"
            @click="
            () => {
              addwfshow = false;
            }
          "
        >取消
        </el-button>
        <el-button size="mini" type="primary" @click="addWorkFace">保存</el-button>
      </div>
    </el-dialog>

    <!--  采煤工作面  -->
    <el-dialog title="新增采煤工作面" :visible.sync="addCoalMiningWFShow" width="500px" append-to-body>
      <!--:rules="rules"-->
      <el-form ref="coalMiningWFForm" :model="coalMiningWF" label-width="130px">
        <el-form-item label="采煤工作面名称" prop="workfaceName">
          <el-input v-model="coalMiningWF.workfaceName" placeholder="请输入采煤工作面名称"/>
        </el-form-item>
        <el-form-item label="走向长度" prop="towardsLength">
          <el-input v-model="coalMiningWF.towardsLength" placeholder="请输入走向长度">
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="切眼长度" prop="cutLength">
          <el-input v-model="coalMiningWF.cutLength" placeholder="请输入切眼长度">
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="高度" prop="height">
          <el-input v-model="coalMiningWF.height" placeholder="请输入高度">
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="工作面支架数量" prop="numberOfWorkfaceSupports">
          <el-input v-model="coalMiningWF.numberOfWorkfaceSupports" placeholder="请输入工作面支架数量">
            <template slot="append">个</template>
          </el-input>
        </el-form-item>
        <el-form-item label="单个支架宽度" prop="supportLength">
          <el-input v-model="coalMiningWF.supportLength" placeholder="请输入单个支架宽度">
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="首架位置" prop="firstSupportPosition">
          <!--          <el-input v-model="coalMiningWF.firstSupportPosition" placeholder="请输入首架位置"/>-->
          <el-select
              v-model="coalMiningWF.firstSupportPosition"
              placeholder="请选择首架位置"
          >
            <el-option
                v-for="type in selectRoadTypes"
                :key="type.id"
                :label="type.typeName"
                :value="type.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="首架距离外帮距离" prop="outDistanceOfFirstSupport">
          <el-input v-model="coalMiningWF.outDistanceOfFirstSupport" placeholder="请输入首架距离外帮距离">
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="关联底抽巷" prop="bottomExtractionRoadway">
          <!--          <el-input v-model="coalMiningWF.bottomExtractionRoadway" placeholder="请输入关联底抽巷"/>-->
          <el-select
              v-model="coalMiningWF.bottomExtractionRoadway"
              multiple
              placeholder="请选择关联底抽巷"
          >
            <el-option
                v-for="road in dchRoadList"
                :key="road.id"
                :label="road.roadway"
                :value="road.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="关联进风巷" prop="airIntakeRoadway">
          <!--          <el-input v-model="coalMiningWF.airIntakeRoadway" placeholder="请输入关联进风巷"/>-->
          <el-select
              v-model="coalMiningWF.airIntakeRoadway"
              placeholder="请选择关联进风巷"
          >
            <el-option
                v-for="road in jfhRoadList"
                :key="road.id"
                :label="road.roadway"
                :value="road.id"
            />
          </el-select>
          进风底抽巷:
          <el-select
              v-model="coalMiningWF.dcAirIn"
              placeholder="请选择"
          >
            <el-option
                v-for="road in dchRoadList"
                :key="road.id"
                :label="road.roadway"
                :value="road.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="关联回风巷" prop="airReturnRoadway">
          <!--          <el-input v-model="coalMiningWF.airReturnRoadway" placeholder="请输入关联回风巷"/>-->
          <el-select
              v-model="coalMiningWF.airReturnRoadway"
              placeholder="请选择关联回风巷"
          >
            <el-option
                v-for="road in hfhRoadList"
                :key="road.id"
                :label="road.roadway"
                :value="road.id"
            />
          </el-select>
          回风底抽巷:
          <el-select
              v-model="coalMiningWF.dcAirReturn"
              placeholder="请选择"
          >
            <el-option
                v-for="road in dchRoadList"
                :key="road.id"
                :label="road.roadway"
                :value="road.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="关联切眼巷" prop="cutEyeRoadway">
          <!--          <el-input v-model="coalMiningWF.cutEyeRoadway" placeholder="请输入关联切眼巷"/>-->
          <el-select
              v-model="coalMiningWF.cutEyeRoadway"
              placeholder="请选择关联切眼巷"
          >
            <el-option
                v-for="road in qyhRoadList"
                :key="road.id"
                :label="road.roadway"
                :value="road.id"
            />
          </el-select>
          切眼底抽巷:
          <el-select
              v-model="coalMiningWF.dcCutEye"
              placeholder="请选择"
          >
            <el-option
                v-for="road in dchRoadList"
                :key="road.id"
                :label="road.roadway"
                :value="road.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="关联采区大巷" prop="miningAreaRoadway">
          <!--          <el-input v-model="coalMiningWF.miningAreaRoadway" placeholder="请输入关联采区大巷"/>-->
          <el-select
              v-model="coalMiningWF.miningAreaRoadway"
              placeholder="请选择关联采区大巷"
          >
            <el-option
                v-for="road in cqdhRoadList"
                :key="road.id"
                :label="road.roadway"
                :value="road.id"
            />
          </el-select>
          中间底抽巷:
          <el-select
              v-model="coalMiningWF.dcMiddle"
              placeholder="请选择"
              @change="changeForceUpdate"
          >
            <el-option
                v-for="road in dchMiddleRoadList"
                :key="road.id"
                :label="road.roadway"
                :value="road.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCoalMiningWFForm">确 定</el-button>
        <el-button @click="coalMiningWFCancel">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 新增/修改巷道区段 -->
    <el-dialog
        :visible.sync="addhdqdshow"
        append-to-body
        title="巷道区段"
        width="680px"
    >
      <el-form
          ref="queryForm"
          :inline="true"
          :model="hdqdData"
          label-width="98px"
          size="small"
      >
        <el-form-item label="名称：">
          <el-input
              v-model="hdqdData.zoneName"
              clearable
              placeholder="请输入"
          />
        </el-form-item>
        <el-form-item label="方位角：">
          <el-input
              v-model="hdqdData.azimuthAngle"
              clearable
              placeholder="请输入"
              @blur="hdqdData.azimuthAngle = Number(hdqdData.azimuthAngle)"
          >
            <template slot="append">°</template>
          </el-input>
        </el-form-item>
        <el-form-item label="倾角：">
          <el-input
              v-model="hdqdData.inclinationAngle"
              clearable
              placeholder="请输入"
              @blur="
              hdqdData.inclinationAngle = Number(hdqdData.inclinationAngle)
            "
          >
            <template slot="append">°</template>
          </el-input>
        </el-form-item>
        <el-form-item label="x：">
          <el-input
              v-model="hdqdData.x"
              clearable
              placeholder="请输入"
              @blur="hdqdData.x = Number(hdqdData.x)"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="y：">
          <el-input
              v-model="hdqdData.y"
              clearable
              placeholder="请输入"
              @blur="hdqdData.y = Number(hdqdData.y)"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="z：">
          <el-input
              v-model="hdqdData.z"
              clearable
              placeholder="请输入"
              @blur="hdqdData.z = Number(hdqdData.z)"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="长度：">
          <el-input
              v-model="hdqdData.length"
              clearable
              placeholder="请输入"
              @blur="hdqdData.length = Number(hdqdData.length)"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
            size="mini"
            type="error"
            @click="
            () => {
              addhdqdshow = false;
            }
          "
        >取消
        </el-button>
        <el-button size="mini" type="primary" @click="addOrUpdateRoadZone"
        >保存
        </el-button>
      </div>
    </el-dialog>
    <!-- 新增/修改导线点 -->
    <el-dialog
        :visible.sync="adddxdshow"
        append-to-body
        title="导线点"
        width="680px"
    >
      <el-form
          ref="queryForm"
          :inline="true"
          :model="dxdData"
          label-width="98px"
          size="small"
      >
        <el-form-item label="名称：">
          <el-input
              v-model="dxdData.navigationPointName"
              clearable
              placeholder="请输入"
          />
        </el-form-item>
        <el-form-item label="x：">
          <el-input
              v-model="dxdData.x"
              clearable
              placeholder="请输入"
              @blur="dxdData.x = Number(dxdData.x)"
          />
        </el-form-item>
        <el-form-item label="y：">
          <el-input
              v-model="dxdData.y"
              clearable
              placeholder="请输入"
              @blur="dxdData.y = Number(dxdData.y)"
          />
        </el-form-item>
        <el-form-item label="z：">
          <el-input
              v-model="dxdData.z"
              clearable
              placeholder="请输入"
              @blur="dxdData.z = Number(dxdData.z)"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
            size="mini"
            type="error"
            @click="
            () => {
              adddxdshow = false;
            }
          "
        >取消
        </el-button>
        <el-button size="mini" type="primary" @click="addOrUpdateNavPoint"
        >保存
        </el-button>
      </div>
    </el-dialog>
    <!-- 预览导线点 -->
    <el-dialog :visible.sync="dxdpreviewshow" title="预览导线点" width="680px">
      <canvas ref="can1" height="600px" width="600px"></canvas>
    </el-dialog>
    <!-- 预览导线点 -->
    <el-dialog
        :visible.sync="hdqdpreviewshow"
        title="预览巷道区段"
        width="680px"
    >
      <canvas ref="can2" height="600px" width="600px"></canvas>
    </el-dialog>
  </div>
</template>

<script>
import {
  addCoalminingworkingface,
  delCoalminingworkingface,
  listCoalminingworkingface,
  updateCoalminingworkingface
} from '@/api/hhhz/coalMiningWorkingFace'
import { aRoad, dRoad, gRoad, gRoadType, uRoad } from '@/api/hhhz/hangdao'
import { aRoadZone, dRoadZone, uRoadZone } from '@/api/hhhz/hangdaoquduan'
import { aNavPoint, dNavPoint, uNavPoint } from '@/api/hhhz/daoxiandian'
import { aLWorkFace, dWorkFace, gWorkFace, uWorkFace } from '@/api/hhhz/gongzuomian'
import { listArea } from '@/api/hhhz/caiqu'
import { gCoalInfo } from '@/api/hhhz/meiceng'
import { getToken } from '@/utils/auth'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { addUncoverCoal, delUncoverCoal, listUncoverCoal, updateUncoverCoal } from '@/api/hhhz/uncoverCoalWorkingFace'

export default {
  name: 'User',
  dicts: ['sys_normal_disable', 'sys_user_sex', 'sys_user_expire'],
  components: { Treeselect },
  data() {
    return {
      showInfo: 0,
      userList: [],
      queryParams: {},
      // 上传弹窗数据绑定
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: '',
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: {
          Authorization: 'Bearer ' + getToken()
        },
        // 上传的地址
        url: null
      },
      // 巷道区段数据
      hdqdData: {},
      // 导线点数据
      dxdData: {},
      // 是否可编辑
      editable: false,
      // 当前表单数据
      currentRoad: {},
      // 所有巷道
      roadList: [],
      // 底抽巷列表
      dchRoadList: [],
      dchMiddleRoadList: [],
      // 采区大巷列表
      cqdhRoadList: [],
      // 进风巷
      jfhRoadList: [],
      // 回风巷
      hfhRoadList: [],
      // 切眼巷
      qyhRoadList: [],
      // 新增底抽巷数据
      roadData: {
        type: 2,
        roadwayType: '底抽巷'
      },
      // 巷道形状
      shapeTpyes: [
        {
          faultShapId: 1,
          shape: '矩形'
        },
        {
          faultShapId: 2,
          shape: '梯形'
        },
        {
          faultShapId: 3,
          shape: '拱形'
        }
      ],
      // 巷道类型
      roadTypes: [],
      selectRoadTypes: [
        {
          id: 17,
          typeName: '进风巷'
        }, {
          id: 18,
          typeName: '回风巷'
        }, {
          id: 19,
          typeName: '切眼巷'
        }
      ],
      /////////
      /////////
      /////////
      // 煤层
      coalInfo: [],
      // 采区
      areas: [],
      // 工作面
      workFaces: [],
      // 当前显示工作面，绑定当前表单显示数据
      currentWorkFace: {},
      // 新增工作面
      addwfshow: false,
      // 绑定新增工作面弹窗数据
      addWFData: {},
      /////////
      /////////
      /////////
      hdqdpreviewshow: false,
      dxdpreviewshow: false,
      // 显示新增底抽巷弹窗
      addroadshow: false,
      // 显示新增巷道区段弹窗
      addhdqdshow: false,
      // 显示新增导线点弹窗
      adddxdshow: false,
      showdch: true,
      showjjgzm: false,
      showcmgzm: false,
      showjmgzm: false,
      showcqdh: false,
      banEditExpire: true,
      loading: false,
      // 新增采煤工作面
      addCoalMiningWFShow: false,
      coalMiningWF: { dcAirIn: null, dcAirReturn: null, dcCutEye: null, dcMiddle: null },
      coalMiningWFList: [],
      coalMiningWFQueryParams: {
        workfaceName: null,
        towardsLength: null,
        cutLength: null,
        height: null,
        numberOfWorkfaceSupports: null,
        supportLength: null,
        firstSupportPosition: null,
        outDistanceOfFirstSupport: null,
        bottomExtractionRoadway: null,
        airIntakeRoadway: null,
        airReturnRoadway: null,
        cutEyeRoadway: null,
        miningAreaRoadway: null
      },
      // 新增揭煤工作面
      addUncoverMiningWFShow: false,
      dipDirectionMenu:[
        {
          id:0,
          dipDirection:"顺时针(面向切眼左高右低)"
        },
        {
          id:1,
          dipDirection:"逆时针(面向切眼左低右高)"
        }
      ],
      uncoverCoalWF: { dcAirIn: null, dcAirReturn: null, dcCutEye: null, dcMiddle: null },
      uncoverCoalWFList: [],
      uncoverCoalWFQueryParams: {
        workfaceName: null,
        towardsLength: null,
        cutLength: null,
        height: null,
        bottomExtractionRoadway: null,
        airIntakeRoadway: null,
        airReturnRoadway: null,
        cutEyeRoadway: null,
        miningAreaRoadway: null
      }
    }
  },
  watch: {},
  async created() {
    await this.getCoalMiningWFList()
    await this.getUncoverCoalWFList()
    await this.getRoadType()
    await this.getRoad({})
    await this.getWorkFace({})
    await this.getAreaList({})
    await this.getCoalInfoList()
    /* if (this.dchRoadList.length >= 1) {
      this.currentRoad = this.dchRoadList[0]
    } */
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath)
    },
    resetQuery() {
    },
    handleQuery() {
    },
    handleSelectionChange() {
    },
    previewHdqd() {
      this.hdqdpreviewshow = true
      this.$nextTick(() => {
        let c1 = this.$refs.can2.getContext('2d')
        //console.log(c1);
        c1.clearRect(0, 0, 600, 600)
        c1.setLineDash([])
        if (this.currentRoad.troadwayZoneList.length >= 2) {
          let mx = 0
          let my = 0
          let nex = 0
          let ney = 0
          this.currentRoad.troadwayZoneList.forEach((e) => {
            mx = e.x > mx ? e.x : mx
            my = e.y > my ? e.y : my
            nex = e.x < 0 && e.x < nex ? e.x : nex
            ney = e.y < 0 && e.y < ney ? e.y : ney
          })
          let divs = 1
          while ((mx - nex) / divs >= 600 || (my - ney) / divs >= 600) {
            divs += 1
          }
          console.log('mx:', mx)
          console.log('my:', my)
          console.log('nex:', nex)
          console.log('ney:', ney)
          console.log('divs:', divs)

          let cpoint = this.currentRoad.troadwayZoneList[0]
          this.currentRoad.troadwayZoneList.forEach((e) => {
            c1.beginPath()
            c1.moveTo((cpoint.x - nex) / divs, (cpoint.y - ney) / divs)
            c1.lineTo(
                (e.x + Math.abs(nex)) / divs,
                (e.y + Math.abs(ney)) / divs
            )
            c1.stroke()
            cpoint = e
          })

          /* this.currentRoad.tnavigationPointList.forEach((e) => {
            c1.beginPath();
            c1.arc(
              (e.x + Math.abs(nex)) / divs,
              (e.y + Math.abs(ney)) / divs,
              5,
              0,
              Math.PI * 2,
              true
            );
            c1.stroke();
          }); */
        }
      })
    },
    changeForceUpdate() {
      this.$forceUpdate()
    },
    getUncoverCoalWFList() {
      //this.loading = true;
      listUncoverCoal(this.uncoverCoalWFQueryParams).then(response => {
        this.uncoverCoalWFList = response.rows
        //this.total = response.total;
        //this.loading = false;
      })
    },
    getCoalMiningWFList() {
      //this.loading = true;
      listCoalminingworkingface(this.coalMiningWFQueryParams).then(response => {
        this.coalMiningWFList = response.rows
        //this.total = response.total;
        //this.loading = false;
      })
    },

    // 预览导线点
    previewDxd() {
      this.dxdpreviewshow = true
      this.$nextTick(() => {
        let c1 = this.$refs.can1.getContext('2d')
        //console.log(c1);
        c1.clearRect(0, 0, 600, 600)
        c1.setLineDash([])
        if (this.currentRoad.tnavigationPointList.length >= 1) {
          /* let cpoint = this.currentRoad.tnavigationPointList[0];
          this.currentRoad.tnavigationPointList.forEach((e) => {
            c1.beginPath();
            c1.moveTo(cpoint.x, cpoint.y);
            c1.lineTo(e.x, e.y);
            c1.stroke();
            cpoint = e;
          }); */
          let mx = 0
          let my = 0
          let nex = 0
          let ney = 0
          this.currentRoad.tnavigationPointList.forEach((e) => {
            mx = e.x > mx ? e.x : mx
            my = e.y > my ? e.y : my
            nex = e.x < 0 && e.x < nex ? e.x : nex
            ney = e.y < 0 && e.y < ney ? e.y : ney
          })
          let divs = 1
          while ((mx - nex) / divs >= 600 || (my - ney) / divs >= 600) {
            divs += 1
          }
          console.log('mx:', mx)
          console.log('my:', my)
          console.log('nex:', nex)
          console.log('ney:', ney)
          console.log('divs:', divs)
          this.currentRoad.tnavigationPointList.forEach((e) => {
            c1.beginPath()
            c1.arc(
                (e.x + Math.abs(nex)) / divs,
                (e.y + Math.abs(ney)) / divs,
                5,
                0,
                Math.PI * 2,
                true
            )
            c1.stroke()
          })
        }
      })
    },

    /** 提交采煤工作面 */
    submitCoalMiningWFForm() {
      this.$refs['coalMiningWFForm'].validate(valid => {
        if (valid) {
          this.coalMiningWF.bottomExtractionRoadway = []
          this.coalMiningWF.bottomExtractionRoadway.push(this.coalMiningWF.dcAirIn)
          this.coalMiningWF.bottomExtractionRoadway.push(this.coalMiningWF.dcAirReturn)
          this.coalMiningWF.bottomExtractionRoadway.push(this.coalMiningWF.dcCutEye)
          this.coalMiningWF.bottomExtractionRoadway.push(this.coalMiningWF.dcMiddle)
          console.log('dcAirIn', this.coalMiningWF.dcAirIn, this.coalMiningWF.dcAirReturn, this.coalMiningWF.dcCutEye)
          this.coalMiningWF.bottomExtractionRoadway = JSON.stringify(this.coalMiningWF.bottomExtractionRoadway)
          if (this.coalMiningWF.id != null) {
            updateCoalminingworkingface(this.coalMiningWF).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.showCoalMiningWF = false
              this.getCoalMiningWFList()
            })
          } else {
            addCoalminingworkingface(this.coalMiningWF).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.showCoalMiningWF = false
              this.getCoalMiningWFList()
            })
          }
        }
      })
    },

    /**
     * 删除采煤工作面
     */
    delCoalMiningWorkFace() {
      let _this = this
      this.$modal.confirm('是否确认删除采煤工作面: ' + this.coalMiningWF.workfaceName + ' 的数据？').then(function() {
        return delCoalminingworkingface(_this.coalMiningWF.id)
      }).then(() => {
        this.getCoalMiningWFList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },

    /**
     * 更新采煤工作面数据
     */
    updateCoalMiningWFForm() {
      this.coalMiningWF.bottomExtractionRoadway = []
      this.coalMiningWF.bottomExtractionRoadway.push(this.coalMiningWF.dcAirIn)
      this.coalMiningWF.bottomExtractionRoadway.push(this.coalMiningWF.dcAirReturn)
      this.coalMiningWF.bottomExtractionRoadway.push(this.coalMiningWF.dcCutEye)
      this.coalMiningWF.bottomExtractionRoadway.push(this.coalMiningWF.dcMiddle)
      console.log('dcAirIn', this.coalMiningWF.dcAirIn, this.coalMiningWF.dcAirReturn, this.coalMiningWF.dcCutEye)
      this.coalMiningWF.bottomExtractionRoadway = JSON.stringify(this.coalMiningWF.bottomExtractionRoadway)
      if (this.coalMiningWF.id != null) {
        updateCoalminingworkingface(this.coalMiningWF).then(response => {
          this.$modal.msgSuccess('修改成功')
          this.showCoalMiningWF = false
          this.getCoalMiningWFList()
        })
      } else {
        addCoalminingworkingface(this.coalMiningWF).then(response => {
          this.$modal.msgSuccess('新增成功')
          this.showCoalMiningWF = false
          this.getCoalMiningWFList()
        })
      }
    },
    /**
     * 提交揭煤工作面数据
     */
    updateUncoverCoalWFForm() {
      this.uncoverCoalWF.bottomExtractionRoadway = []
      this.uncoverCoalWF.bottomExtractionRoadway.push(this.uncoverCoalWF.dcAirIn)
      this.uncoverCoalWF.bottomExtractionRoadway.push(this.uncoverCoalWF.dcAirReturn)
      this.uncoverCoalWF.bottomExtractionRoadway.push(this.uncoverCoalWF.dcCutEye)
      this.uncoverCoalWF.bottomExtractionRoadway.push(this.uncoverCoalWF.dcMiddle)
      console.log('dcAirIn', this.uncoverCoalWF.dcAirIn, this.uncoverCoalWF.dcAirReturn, this.uncoverCoalWF.dcCutEye)
      this.uncoverCoalWF.bottomExtractionRoadway = JSON.stringify(this.uncoverCoalWF.bottomExtractionRoadway)
      if (this.uncoverCoalWF.id != null) {
        updateUncoverCoal(this.uncoverCoalWF).then(response => {
          this.$modal.msgSuccess('修改成功')
          this.showCoalMiningWF = false
          this.getUncoverCoalWFList()
        })
      } else {
        addUncoverCoal(this.uncoverCoalWF).then(response => {
          this.$modal.msgSuccess('新增成功')
          this.showCoalMiningWF = false
          this.getUncoverCoalWFList()
        })
      }
    },

    /**
     * 删除揭煤工作面
     */
    delUncoverCoalWorkFace() {
      let _this = this
      this.$modal.confirm('是否确认删除揭煤工作面: ' + this.uncoverCoalWF.workfaceName + ' 的数据？').then(function() {
        return delUncoverCoal(_this.uncoverCoalWF.id)
      }).then(() => {
        this.getUncoverCoalWFList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },

    coalMiningWFCancel() {
      this.addCoalMiningWFShow = false
      this.coalMiningWF = {
        id: null,
        workfaceName: null,
        towardsLength: null,
        cutLength: null,
        height: null,
        numberOfWorkfaceSupports: null,
        supportLength: null,
        firstSupportPosition: null,
        outDistanceOfFirstSupport: null,
        bottomExtractionRoadway: null,
        airIntakeRoadway: null,
        airReturnRoadway: null,
        cutEyeRoadway: null,
        miningAreaRoadway: null
      }
      //this.resetForm('form')
    },

    // 获取巷道信息
    async getRoad(data) {
      //this.cqdhRoadList = []
      //this.dchRoadList = []
      await gRoad(data).then((response) => {
        console.log('gRoad', response)
        this.roadList = response.rows
        this.roadList.forEach((item) => {
          if (item.type === 1) {
            // 采区大巷
            this.cqdhRoadList.push(item)
          } else if (item.type === 2) {
            // 底抽巷
            this.dchRoadList.push(item)
          } else if (item.type === 17) {
            // 进风巷
            this.jfhRoadList.push(item)
          } else if (item.type === 18) {
            // 回风巷
            this.hfhRoadList.push(item)
          } else if (item.type === 19) {
            // 切眼巷
            this.qyhRoadList.push(item)
          } else if (item.type === 20) {
            this.dchMiddleRoadList.push(item)
          }
        })
        this.editable = false
      })
    },
    // 切换采区大巷
    async showCqdh() {
      this.showdch = false
      this.showjjgzm = false
      this.showcmgzm = false
      this.showjmgzm = false
      this.showcqdh = true
      await this.getRoad({ type: 1 })
      if (this.roadList.length >= 1) {
        this.currentRoad = this.roadList[0]
      }
    },

    // 切换底抽巷
    async showDch() {
      this.showdch = true
      this.showjjgzm = false
      this.showcmgzm = false
      this.showjmgzm = false
      this.showcqdh = false
      await this.getRoad({ type: 2 })
      if (this.roadList.length >= 1) {
        this.currentRoad = this.roadList[0]
      }
    },

    showAddCqdh() {
      this.roadData.type = 1
      this.roadData.roadwayType = '采区大巷'
      this.addroadshow = true
    },
    showAddDch() {
      this.addroadshow = true
    },

    /* showAddDch() {
      this.roadData.type = 2
      this.roadData.roadwayType = '底抽巷'
      this.addroadshow = true
    }, */

    showAddHdqd() {
      this.addhdqdshow = true
      this.hdqdData = {}
      this.hdqdData.roadwayId = this.currentRoad.id
    },
    showAddDxd() {
      this.adddxdshow = true
      this.dxdData = {}
      this.dxdData.roadwayId = this.currentRoad.id
    },
    showAddWorkFace() {
      this.addwfshow = true
      this.addWFData = {}
    },
    showCoalMiningWF() {
      this.addCoalMiningWFShow = true
      this.coalMiningWF = {}
    },
    showUncoverMiningWF() {
      this.addUncoverMiningWFShow = true
      this.coalMiningWF = {}
    },
    // 删除区段
    delNavPoint(data) {
      console.log('dNavPoint', data)
      dNavPoint({ id: data.id }).then((response) => {
        console.log('dNavPoint', response)
        this.refreshRoad()
      })
    },
    // 新增区段
    addOrUpdateNavPoint() {
      if (this.dxdData.id != null) {
        uNavPoint(this.dxdData).then((response) => {
          console.log('uNavPoint', response)
          if (response.code == 200) {
            this.$modal.msgSuccess(response.msg)
            this.adddxdshow = false
            this.dxdData = {}
            this.refreshRoad()
          }
        })
      } else {
        aNavPoint(this.dxdData).then((response) => {
          console.log('aNavPoint', response)
          if (response.code == 200) {
            this.$modal.msgSuccess(response.msg)
            this.adddxdshow = false
            this.dxdData = {}
            this.refreshRoad()
          }
        })
      }
    },
    // 删除区段
    delRoadZone(data) {
      console.log('delZone', data)
      dRoadZone({ id: data.id }).then((response) => {
        console.log('dRoadZone', response)
        this.refreshRoad()
      })
    },
    // 新增区段
    addOrUpdateRoadZone() {
      if (this.hdqdData.id != null) {
        uRoadZone(this.hdqdData).then((response) => {
          console.log('uRoadZone', response)
          if (response.code == 200) {
            this.$modal.msgSuccess(response.msg)
            this.addhdqdshow = false
            this.hdqdData = {}
            this.refreshRoad()
          }
        })
      } else {
        aRoadZone(this.hdqdData).then((response) => {
          console.log('aRoadZone', response)
          if (response.code == 200) {
            this.$modal.msgSuccess(response.msg)
            this.addhdqdshow = false
            this.hdqdData = {}
            this.refreshRoad()
          }
        })
      }
    },
    // 删除巷道
    delRoad() {
      dRoad({ id: this.currentRoad.id }).then((response) => {
        console.log('dRoad', response)
        if (response.code == 200) {
          this.$modal.msgSuccess(response.msg)
          this.getRoad({ type: this.currentRoad.type })
          if (this.roadList.length >= 1) {
            this.currentRoad = this.roadList[0]
          }
        }
      })
    },
    // 更新巷道
    updateRoad() {
      uRoad(this.currentRoad).then((response) => {
        console.log('uRoad', uRoad)
        if (response.code == 200) {
          this.$modal.msgSuccess(response.msg)
        }
        this.refreshRoad()
      })
    },
    // 刷新数据
    async refreshRoad() {
      await this.getRoad({})
      this.roadList.forEach((element) => {
        if (element.id === this.currentRoad.id) {
          this.currentRoad = element
          this.editable = false
        }
      })
    },
    // 设置当前Road
    setCurrentRoad(road) {
      this.currentRoad = road
      this.editable = false
    },
    // 设置当前Road
    setCurrentWorkFace(index, wf) {
      this.showInfo = index
      if (index === 0) {
        // 掘进工作面
        this.currentWorkFace = wf
        this.editable = false
      } else if (index === 1) {
        // 采煤工作面
        this.coalMiningWF = wf
        this.coalMiningWF.airIntakeRoadway = Number(this.coalMiningWF.airIntakeRoadway)
        this.coalMiningWF.airReturnRoadway = Number(this.coalMiningWF.airReturnRoadway)
        this.coalMiningWF.cutEyeRoadway = Number(this.coalMiningWF.cutEyeRoadway)
        this.coalMiningWF.miningAreaRoadway = Number(this.coalMiningWF.miningAreaRoadway)
        if (this.coalMiningWF.bottomExtractionRoadway) {
          let ppStr = JSON.stringify(this.coalMiningWF.bottomExtractionRoadway).replaceAll('"', '')
          let pp = JSON.parse(ppStr)
          this.coalMiningWF.bottomExtractionRoadway = pp
          this.coalMiningWF.dcAirIn = pp[0]
          this.coalMiningWF.dcAirReturn = pp[1]
          this.coalMiningWF.dcCutEye = pp[2]
          this.coalMiningWF.dcMiddle = pp[3]
        }
      } else if (index === 2) {
        // 揭煤工作面
        this.uncoverCoalWF = wf
        this.uncoverCoalWF.airIntakeRoadway = Number(this.uncoverCoalWF.airIntakeRoadway)
        this.uncoverCoalWF.airReturnRoadway = Number(this.uncoverCoalWF.airReturnRoadway)
        this.uncoverCoalWF.cutEyeRoadway = Number(this.uncoverCoalWF.cutEyeRoadway)
        this.uncoverCoalWF.miningAreaRoadway = Number(this.uncoverCoalWF.miningAreaRoadway)
        if (this.coalMiningWF.bottomExtractionRoadway) {
          let ppStr = JSON.stringify(this.coalMiningWF.bottomExtractionRoadway).replaceAll('"', '')
          let pp = JSON.parse(ppStr)
          this.uncoverCoalWF.bottomExtractionRoadway = pp
          this.uncoverCoalWF.dcAirIn = pp[0]
          this.uncoverCoalWF.dcAirReturn = pp[1]
          this.uncoverCoalWF.dcCutEye = pp[2]
          this.uncoverCoalWF.dcMiddle = pp[3]
        }
      }
    },
    // 获取煤层
    async getCoalInfoList() {
      await gCoalInfo({}).then((response) => {
        this.coalInfo = response.rows
      })
    },
    // 获取采区
    async getAreaList() {
      await listArea({}).then((response) => {
        this.areas = response.rows
      })
    },
    // 获取工作面
    async getWorkFace(data) {
      this.workFaces = []
      await gWorkFace(data).then((response) => {
        console.log('gWorkFace', response)
        this.workFaces = response.rows
        /*  this.roadList.forEach((item) => {
           this.workFaces.push(item)
         }) */
        this.editable = false
      })
    },
    // 新增工作面
    addWorkFace() {
      aLWorkFace(this.addWFData).then((response) => {
        console.log('aWorkFace', response)
        if (response.code === 200) {
          this.$modal.msgSuccess(response.msg)
          this.addwfshow = false
        }
        this.getWorkFace({})
      })
    },
    // 更新工作面信息
    updateWorkFace() {
      uWorkFace(this.currentWorkFace).then((response) => {
        console.log('aWorkFace', response)
        if (response.code === 200) {
          this.$modal.msgSuccess(response.msg)
          this.addwfshow = false
        }
        this.getWorkFace({})
      })
    },
    // 删除工作面
    delWorkFace() {
      dWorkFace({ id: this.currentWorkFace.id }).then((response) => {
        console.log('aWorkFace', response)
        if (response.code === 200) {
          this.$modal.msgSuccess(response.msg)
          this.addwfshow = false
        }
        this.currentWorkFace = {}
        this.getWorkFace({})
      })
    },
    // 获取巷道类型
    async getRoadType() {
      await gRoadType({}).then((response) => {
        console.log('gRoadType', response)
        this.roadTypes = response.rows
      })
    },
    // 新增底抽巷信息
    addRoad() {
      aRoad(this.roadData).then((response) => {
        console.log('aRoad', response)
        if (response.code == 200) {
          this.$modal.msgSuccess(response.msg)
          this.addroadshow = false
          this.roadData = {
            type: this.roadData.type,
            roadwayType: this.roadData.roadwayType
          }
        }
        this.getRoad({ type: this.roadData.type })
      })
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download(
          'system/user/export',
          {
            ...this.queryParams
          },
          `user_${new Date().getTime()}.xlsx`
      )
    },
    /** 导入导线点 */
    handleImpDxd() {
      this.upload.type = 'dxd'
      this.upload.open = true
      this.upload.title = '导入导线点数据'
      this.upload.url =
          process.env.VUE_APP_BASE_API + '/tNavigationPoint/importData'
    },
    /** 导入巷道区段 */
    handleImpHdqd() {
      this.upload.type = 'hdqd'
      this.upload.open = true
      this.upload.title = '导入巷道区段数据'
      this.upload.url =
          process.env.VUE_APP_BASE_API + '/tRoadwayZone/importData'
    },
    /** 下载模板操作 */
    importTemplate() {
      if (this.upload.type === 'dxd') {
        console.log('下载导线点数据模板...')
        this.download(
            'tNavigationPoint/exportTemplate',
            {},
            `导线点数据_template_${new Date().getTime()}.xlsx`
        )
      } else if (this.upload.type === 'hdqd') {
        console.log('下载巷道区段数据模板...')
        this.download(
            'tRoadwayZone/exportTemplate',
            {},
            `巷道区段数据_template_${new Date().getTime()}.xlsx`
        )
      } else {
        console.log('this.upload.type==hdqd', this.upload.type === 'hdqd')
      }
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert(
          '<div style=\'overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;\'>' +
          response.msg +
          '</div>',
          '导入结果',
          { dangerouslyUseHTMLString: true }
      )
      this.refreshRoad()
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
    }
  }
}
</script>
<style>
.sidebar-wrapper .el-scrollbar__wrap {
  overflow-x: hidden;
}

.el-scrollbar__wrap {
  overflow-x: hidden;
}

.is-horizontal {
  display: none;
}
</style>
