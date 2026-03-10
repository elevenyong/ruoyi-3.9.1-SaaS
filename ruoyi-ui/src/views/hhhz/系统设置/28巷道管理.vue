<template>
  <div class="app-container">
    <div v-if="showdch">
      <el-row :gutter="20">
        <!--巷道工作面列表-->
        <el-col :span="5" :xs="24">
          <el-card style="height: 80vh; margin: 10px 5px;overflow-x: hidden;">
            <div slot="header" class="clearfix">
              <span>巷道列表</span>
              <div style="float: right">
                <el-button size="mini" type="primary" @click="showAddDch"
                >新增巷道
                </el-button>
              </div>
            </div>
            <div class="head-container">
              <el-scrollbar style="height: 70vh;overflow-x: hidden;">
                <el-menu :unique-opened="true">
                  <el-submenu v-for="menItem in roadTypes" :index="menItem.id.toString()"
                              :key="menItem.id.toString()">
                    <template slot="title">
                      <i class="el-icon-location"></i>
                      <span>{{ menItem.roadwayType }}</span>
                    </template>
                    <el-menu-item
                      v-for="item in roadList"
                      :key="item.id.toString()"
                      :index="item.type+'-'+item.id"
                      @click="setCurrentRoad(item)"
                      v-if="item.type === menItem.id"
                    >
                      {{ item.roadway }}
                    </el-menu-item>
                  </el-submenu>


                  <!--
                                  <el-submenu index="2">
                                    <template slot="title">
                                      <i class="el-icon-location"></i>
                                      <span>底抽巷</span>
                                    </template>
                                    <el-menu-item
                                      v-for="item in dchRoadList"
                                      :key="item.id.toString()"
                                      :index="item.type+'-'+item.id"
                                      @click="setCurrentRoad(item)"
                                    >
                                      {{ item.roadway }}
                                    </el-menu-item>
                                  </el-submenu>
                                  <el-submenu index="1">
                                    <template slot="title">
                                      <i class="el-icon-location"></i>
                                      <span>采区大巷</span>
                                    </template>
                                    <el-menu-item
                                      v-for="(item) in cqdhRoadList"
                                      :key="item.id.toString()"
                                      :index="item.type+'-'+item.id"
                                      @click="setCurrentRoad(item)"
                                    >
                                      {{ item.roadway }}
                                    </el-menu-item>
                                  </el-submenu>
                  -->

                  <!--                <el-submenu index="3">
                                    <template slot="title">
                                      <i class="el-icon-location"></i>
                                      <span>掘进工作面</span>
                                    </template>
                                    <el-menu-item
                                      v-for="item in roadList"
                                      :key="item.id.toString()"
                                      :index="item.type+'-'+item.id"
                                      @click="setCurrentRoad(item)"
                                    >
                                      {{ item.roadway }}
                                    </el-menu-item>
                                  </el-submenu>
                                  <el-submenu index="4">
                                    <template slot="title">
                                      <i class="el-icon-location"></i>
                                      <span>采煤工作面</span>
                                    </template>
                                    <el-menu-item
                                      v-for="item in roadList"
                                      :key="item.id.toString()"
                                      :index="item.type+'-'+item.id"
                                      @click="setCurrentRoad(item)"
                                    >
                                      {{ item.roadway }}
                                    </el-menu-item>
                                  </el-submenu>
                                  <el-submenu index="5">
                                    <template slot="title">
                                      <i class="el-icon-location"></i>
                                      <span>揭煤工作面</span>
                                    </template>
                                    <el-menu-item
                                      v-for="item in roadList"
                                      :key="item.id.toString()"
                                      :index="item.type+'-'+item.id"
                                      @click="setCurrentRoad(item)"
                                    >
                                      {{ item.roadway }}
                                    </el-menu-item>
                                  </el-submenu>-->
                </el-menu>
              </el-scrollbar>
            </div>
          </el-card>
        </el-col>
        <!--数据信息-->
        <el-col :span="19" :xs="24">
          <h4>基本信息</h4>
          <el-divider/>
          <el-form
            :inline="true"
            :model="currentRoad"
            label-width="98px"
            size="small"
          >
            <el-form-item label="巷道名称：">
              <el-input
                v-model="currentRoad.roadway"
                :disabled="!editable"
                style="width: 180px"
              />
            </el-form-item>
            <el-form-item label="断面形状：">
              <el-select
                v-model="currentRoad.faultShapId"
                :disabled="!editable"
                clearable
                style="width: 120px"
              >
                <el-option
                  v-for="dict in shapeTpyes"
                  :key="dict.faultShapId"
                  :label="dict.shape"
                  :value="dict.faultShapId"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="宽：">
              <el-input
                v-model="currentRoad.wide"
                :disabled="!editable"
                style="width: 120px"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="高：">
              <el-input
                v-model="currentRoad.height"
                :disabled="!editable"
                style="width: 120px"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="方位角：">
              <el-input
                v-model="currentRoad.azimuthAngle"
                :disabled="!editable"
                clearable
                style="width: 120px"
              >
                <template slot="append">°</template>
              </el-input>
            </el-form-item>
            <br>
            <el-form-item label="内外错:">
              <el-select
                v-model="currentRoad.inOrOut"
                :disabled="!editable"
                placeholder="请选择"
                style="width: 120px"
              >
                <el-option
                  v-for="dict in inOrOuts"
                  :key="dict.id"
                  :label="dict.inout"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="两巷水平投影间距:" label-width="140px">
              <el-input
                v-model="currentRoad.inoutDistance"
                :disabled="!editable"
                placeholder="请输入"
                @blur="currentRoad.inoutDistance!='' ? Number(currentRoad.inoutDistance):0"
                style="width: 120px"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="煤层底板到底抽巷间距:" label-width="160px">
              <el-input
                v-model="currentRoad.distanceCoalRoadway"
                :disabled="!editable"
                placeholder="请输入"
                @blur="currentRoad.distanceCoalRoadway!='' ? Number(currentRoad.distanceCoalRoadway):0"
                style="width: 120px"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="埋深:">
              <el-input
                v-model="currentRoad.elevation"
                :disabled="!editable"
                placeholder="请输入"
                @blur="currentRoad.elevation!='' ? Number(currentRoad.elevation):0"
                style="width: 120px"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                v-if="!editable"
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
                @click="updateRoad"
              >保存
              </el-button>
              <el-button
                v-if="currentRoad.id != null"
                icon="el-icon-delete"
                size="mini"
                type="danger"
                @click="delRoad"
              >删除
              </el-button>
              <el-button
                v-if="currentRoad.id != null"
                icon="el-icon-refresh"
                size="mini"
                type="default"
                @click="refreshRoad"
              >刷新
              </el-button>
            </el-form-item>
          </el-form>

          <el-card>
            <div slot="header" class="clearfix">
              <span>巷道区段创建</span>
              <div style="float: right; padding: 3px 0">
                <span
                >已创建巷道{{
                    currentRoad.troadwayZoneList
                      ? currentRoad.troadwayZoneList.length
                      : 0
                  }}节</span
                >
                <el-button
                  v-if="currentRoad != null"
                  size="mini"
                  style="margin: 0 5px"
                  type="primary"
                  @click="showAddHdqd"
                >增加
                </el-button>
                <el-button
                  v-if="currentRoad != null"
                  size="mini"
                  style="margin: 0 5px"
                  type="primary"
                  @click="handleImpHdqd"
                >导入
                </el-button>
                <el-button
                  v-if="currentRoad != null"
                  size="mini"
                  style="margin: 0 5px"
                  type="primary"
                  @click="previewHdqd"
                >预览
                </el-button>
              </div>
            </div>
            <div style="padding: 0 0 0 0">
              <el-table
                key="dchhdqd"
                v-loading="loading"
                :data="currentRoad.troadwayZoneList"
                height="300"
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
                <el-table-column
                  align="center"
                  label="长度(m)"
                  prop="length"
                  width="120"
                />
                <el-table-column align="center" label="操作" width="120">
                  <template slot-scope="scope">
                    <el-button
                      icon="el-icon-edit"
                      size="mini"
                      type="text"
                      @click="
                        () => {
                          addhdqdshow = true;
                          hdqdData = scope.row;
                        }
                      "
                    >修改
                    </el-button>
                    <el-button
                      icon="el-icon-delete"
                      size="mini"
                      type="text"
                      @click="delRoadZone(scope.row)"
                    >删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-card>

          <el-card style="margin-top: 20px">
            <div slot="header" class="clearfix">
              <span>导线点创建</span>
              <div style="float: right; padding: 3px 0">
                <span
                >已创建导线点{{
                    currentRoad.tnavigationPointList
                      ? currentRoad.tnavigationPointList.length
                      : 0
                  }}个</span
                >
                <el-button
                  v-if="currentRoad != null"
                  size="mini"
                  style="margin: 0 5px"
                  type="primary"
                  @click="showAddDxd"
                >增加
                </el-button>
                <el-button
                  v-if="currentRoad != null"
                  size="mini"
                  style="margin: 0 5px"
                  type="primary"
                  @click="handleImpDxd"
                >导入
                </el-button>
                <el-button
                  v-if="currentRoad != null"
                  size="mini"
                  style="margin: 0 5px"
                  type="primary"
                  @click="previewDxd"
                >预览
                </el-button>
              </div>
            </div>
            <div>
              <el-table
                key="dchdxd"
                v-loading="loading"
                :data="currentRoad.tnavigationPointList"
                height="300"
                @selection-change="handleSelectionChange"
              >
                <!-- <el-table-column type="selection" width="50" align="center" /> -->
                <el-table-column align="center" label="序号" prop="id"/>
                <el-table-column
                  :show-overflow-tooltip="true"
                  align="center"
                  label="名称"
                  prop="navigationPointName"
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
                <el-table-column align="center" label="操作" width="120">
                  <template slot-scope="scope">
                    <el-button
                      icon="el-icon-edit"
                      size="mini"
                      type="text"
                      @click="
                        () => {
                          adddxdshow = true;
                          dxdData = scope.row;
                        }
                      "
                    >修改
                    </el-button>
                    <el-button
                      icon="el-icon-delete"
                      size="mini"
                      type="text"
                      @click="delNavPoint(scope.row)"
                    >删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
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

    <!-- 管理巷道 -->
    <el-dialog
      :title="aoeRoadWayTitle"
      :visible.sync="addroadshow"
      append-to-body
      width="680px"
    >
      <el-form
        ref="aoeRoadWay"
        :inline="true"
        :model="roadData"
        label-width="98px"
        size="small"
      >
        <el-form-item label="巷道名称：">
          <el-input v-model="roadData.roadway" clearable placeholder="请输入"/>
        </el-form-item>
        <el-form-item label="巷道类别：">
          <el-select
            v-model="roadData.type"
            placeholder="请选择"
          >
            <el-option
              v-for="dict in roadTypes"
              :key="dict.id"
              :label="dict.roadwayType"
              :value="dict.id"
            />
          </el-select>
          <el-button size="mini" type="text" @click="showManHdlb">巷道类别管理</el-button>
        </el-form-item>
        <el-form-item label="断面形状：">
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
        </el-form-item>
        <el-form-item label="宽：">
          <el-input
            v-model="roadData.wide"
            placeholder="请输入"
            @blur="roadData.wide !='' ? Number(roadData.wide):0"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="高：">
          <el-input
            v-model="roadData.height"
            placeholder="请输入"
            @blur="roadData.height !='' ? Number(roadData.height):0"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="方位角：">
          <el-input
            v-model="roadData.azimuthAngle"
            placeholder="请输入"
            @blur="roadData.azimuthAngle!='' ? Number(roadData.azimuthAngle):0"
          >
            <template slot="append">°</template>
          </el-input>
        </el-form-item>
        <el-form-item label="内外错">
          <el-select
            v-model="roadData.inOrOut"
            placeholder="请选择"
          >
            <el-option
              v-for="dict in inOrOuts"
              :key="dict.id"
              :label="dict.inout"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="两巷水平投影间距">
          <el-input
            v-model="roadData.inoutDistance"
            placeholder="请输入"
            @blur="roadData.inoutDistance!='' ? Number(roadData.inoutDistance):0"
          >
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="煤层底板到底抽巷间距">
          <el-input
            v-model="roadData.distanceCoalRoadway"
            :disabled="!editable"
            placeholder="请输入"
            @blur="roadData.distanceCoalRoadway!='' ? Number(roadData.distanceCoalRoadway):0"
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
              addroadshow = false;
            }
          "
        >取消
        </el-button>
        <el-button size="mini" type="primary" @click="addRoad">保存</el-button>
      </div>
    </el-dialog>

    <!--    管理巷道类型-->
    <el-dialog
      :visible.sync="manHdlxshow"
      append-to-body
      title="管理巷道类型"
      width="680px"
    >
      <el-row :gutter="10" class="mb8">
        <el-form :model="updateHdlxData" :inline="true">
          <el-form-item label="新增巷道类型">
            <el-input v-model="updateHdlxData.roadwayType"></el-input>
          </el-form-item>
          <el-button type="primary" @click="addOrUpdateRoadType">新增</el-button>
          <!--          <right-toolbar showSearch.sync="false" @queryTable="getList"></right-toolbar>-->
        </el-form>
      </el-row>

      <el-table v-loading="loading" :data="roadTypes">
        <!--<el-table-column type="selection" width="55" align="center"/>-->
        <el-table-column label="ID" align="center" prop="id"/>
        <el-table-column label="巷道类型" align="center" prop="roadwayType"/>
        <el-table-column label="备注" align="center" prop="remark"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="()=>{updateHdlxData =scope.row;updateHdlxshow=true;}"
              v-hasPermi="['system:roadtype:edit']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="delRoadType(scope.row)"
              v-hasPermi="['system:roadtype:remove']"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <!--    新增修改巷道类型-->
    <el-dialog title="修改巷道类型" :visible.sync="updateHdlxshow" width="500px" append-to-body>
      <el-form ref="updateHdlxData" :model="updateHdlxData" label-width="80px">
        <el-form-item label="ID" prop="id">
          <el-input v-model="updateHdlxData.id" placeholder="" disabled/>
        </el-form-item>
        <el-form-item label="巷道类型" prop="roadwayType">
          <el-input v-model="updateHdlxData.roadwayType" type="text" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="updateHdlxData.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addOrUpdateRoadType">确 定</el-button>
        <el-button @click="()=>{updateHdlxData ={};updateHdlxshow=false;}">取 消</el-button>
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
            @blur="hdqdData.azimuthAngle !='' ? Number(hdqdData.azimuthAngle):0"
          >
            <template slot="append">°</template>
          </el-input>
        </el-form-item>
        <el-form-item label="倾角：">
          <el-input
            v-model="hdqdData.inclinationAngle"
            clearable
            placeholder="请输入"
            @blur="hdqdData.inclinationAngle !='' ? Number(hdqdData.inclinationAngle):0"
          >
            <template slot="append">°</template>
          </el-input>
        </el-form-item>
        <el-form-item label="x：">
          <el-input
            v-model="hdqdData.x"
            clearable
            placeholder="请输入"
            @blur="hdqdData.x !='' ? Number(hdqdData.x):0"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="y：">
          <el-input
            v-model="hdqdData.y"
            clearable
            placeholder="请输入"
            @blur="hdqdData.y !='' ? Number(hdqdData.y):0"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="z：">
          <el-input
            v-model="hdqdData.z"
            clearable
            placeholder="请输入"
            @blur="hdqdData.z !='' ? Number(hdqdData.z):0"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="长度：">
          <el-input
            v-model="hdqdData.length"
            clearable
            placeholder="请输入"
            @blur="hdqdData.length !='' ? Number(hdqdData.length):0"
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
import { aRoad, aRoadType, dRoad, dRoadType, gRoad, gRoadType, uRoad, uRoadType } from '@/api/hhhz/hangdao'
import { aRoadZone, dRoadZone, uRoadZone } from '@/api/hhhz/hangdaoquduan'
import { aNavPoint, dNavPoint, uNavPoint } from '@/api/hhhz/daoxiandian'
import { getToken } from '@/utils/auth'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { Notify } from 'quasar'

export default {
  name: 'User',
  dicts: ['sys_normal_disable', 'sys_user_sex', 'sys_user_expire'],
  components: { Treeselect },
  data() {
    return {
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
      // 采区大巷列表
      cqdhRoadList: [],
      // 新增底抽巷数据
      roadData: {
        type: 2,
        roadwayType: '底抽巷'
      },
      aoeRoadWayTitle: '新增巷道',
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
      hdqdpreviewshow: false,
      dxdpreviewshow: false,
      // 显示新增底抽巷弹窗
      addroadshow: false,
      // 显示新增巷道区段弹窗
      addhdqdshow: false,
      // 显示新增导线点弹窗
      adddxdshow: false,
      // 管理巷道类型
      manHdlxshow: false,
      // 修改巷道类型
      updateHdlxshow: false,
      updateHdlxData: {},

      showdch: true,
      showjjgzm: false,
      showcmgzm: false,
      showjmgzm: false,
      showcqdh: false,
      banEditExpire: true,
      loading: false,
      inOrOuts: [
        { id: 0, inout: '内错' },
        { id: 1, inout: '外错' }
      ]
    }
  },
  watch: {},
  async created() {
    this.loading = true
    await this.getRoadType()
    await this.getRoad({})
    if (this.dchRoadList.length >= 1) {
      this.currentRoad = this.dchRoadList[0]
    }
    this.loading = false
    this.$modal.msgSuccess('巷道数据加载成功！')
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
    // 预览巷道区段
    previewHdqd() {
      this.hdqdpreviewshow = true
      this.$nextTick(() => {
        let c1 = this.$refs.can2.getContext('2d')
        //console.log(c1);
        c1.clearRect(0, 0, 600, 600)
        c1.setLineDash([])
        if (this.currentRoad.troadwayZoneList.length >= 2) {
          ////x,y////
          /* let mx = 0
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
          }) */
          ////end////
          ////x,z////
          let mx = 0
          let mz = 0
          let nex = 0
          let nez = 0
          this.currentRoad.troadwayZoneList.forEach((e) => {
            mx = e.x > mx ? e.x : mx
            mz = e.z > mz ? e.z : mz
            nex = e.x < 0 && e.x < nex ? e.x : nex
            nez = e.z < 0 && e.z < nez ? e.z : nez
          })
          let divs = 1
          while ((mx - nex) / divs >= 600 || (mz - nez) / divs >= 600) {
            divs += 1
          }
          console.log('mx:', mx)
          console.log('mz:', mz)
          console.log('nex:', nex)
          console.log('nez:', nez)
          console.log('divs:', divs)

          let cpoint = this.currentRoad.troadwayZoneList[0]
          this.currentRoad.troadwayZoneList.forEach((e) => {
            c1.beginPath()
            c1.moveTo((cpoint.x - nex) / divs + 5, (cpoint.y - nez) / divs + 5)
            c1.lineTo(
              (e.x + Math.abs(nex)) / divs + 5,
              (e.z + Math.abs(nez)) / divs + 5
            )
            c1.stroke()
            cpoint = e
          })

          ////end////

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
          ///////x,y///////
          /* let mx = 0
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
          }) */
          ////////end//////
          ///////x,z///////
          let mx = 0
          let mz = 0
          let nex = 0
          let nez = 0
          this.currentRoad.tnavigationPointList.forEach((e) => {
            mx = e.x > mx ? e.x : mx
            mz = e.z > mz ? e.z : mz
            nex = e.x < 0 && e.x < nex ? e.x : nex
            nez = e.z < 0 && e.z < nez ? e.z : nez
          })
          let divs = 1
          while ((mx - nex) / divs >= 600 || (mz - nez) / divs >= 600) {
            divs += 1
          }
          console.log('mx:', mx)
          console.log('mz:', mz)
          console.log('nex:', nex)
          console.log('nez:', nez)
          console.log('divs:', divs)
          this.currentRoad.tnavigationPointList.forEach((e) => {
            c1.beginPath()
            c1.arc(
              (e.x + Math.abs(nex)) / divs + 10,
              (e.z + Math.abs(nez)) / divs + 10,
              5,
              0,
              Math.PI * 2,
              true
            )
            c1.stroke()
          })
          ////end////
        }
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

    // 新增巷道区段
    showAddHdqd() {
      this.addhdqdshow = true
      this.hdqdData = {}
      this.hdqdData.roadwayId = this.currentRoad.id
    },

    // 新增导线点
    showAddDxd() {
      this.adddxdshow = true
      this.dxdData = {}
      this.dxdData.roadwayId = this.currentRoad.id
    },

    // 显示巷道类别管理
    showManHdlb() {
      this.manHdlxshow = true
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
    // 获取巷道信息
    async getRoad(data) {
      this.cqdhRoadList = []
      this.dchRoadList = []
      await gRoad(data).then((response) => {
        console.log('gRoad', response)
        this.roadList = response.rows
        this.roadList.forEach((item) => {
          if (item.type === 1) {
            this.cqdhRoadList.push(item)
          } else if (item.type === 2) {
            this.dchRoadList.push(item)
          }
        })
        this.editable = false
      })
    },
    // 获取巷道类型
    async getRoadType() {
      await gRoadType({}).then((response) => {
        console.log('gRoadType', response)
        this.roadTypes = response.rows
      })
    },
    // 新增巷道类型
    async addOrUpdateRoadType() {
      if (this.updateHdlxData.id != null) {
        uRoadType(this.updateHdlxData).then((response) => {
          console.log('uRoadType', response)
          /* if (response.code === 200) {
            this.$modal.msgSuccess(response.msg)
            this.getRoadType();
            this.updateHdlxshow = false;
            this.updateHdlxData={};
          } */
          this.$modal.msgSuccess(response.msg)
          this.getRoadType()
          this.updateHdlxshow = false
          this.updateHdlxData = {}
        })
      } else {
        aRoadType([this.updateHdlxData]).then((response) => {
          console.log('aRoadType', response)
          /* if (response.code === 200) {
            this.$modal.msgSuccess(response.msg)
            this.getRoadType();
            this.updateHdlxData={};
          } */
          this.$modal.msgSuccess(response.msg)
          this.getRoadType()
          this.updateHdlxData = {}
        })
      }
    },
    // 删除巷道类型
    async delRoadType(row) {
      await dRoadType({ ids: row.id }).then((response) => {
        console.log('dRoadType', response)
        this.getRoadType()
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
