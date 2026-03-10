<template>
  <div class="app-container">
    <el-row>
      <h4>基础信息</h4>
      <el-form
        :model="queryParams"
        ref="queryForm"
        size="small"
        :inline="true"
        v-show="showSearch"
        label-width="88px"
      >
        <el-form-item label="钻孔地点" prop="zkdd">
          <el-select
            v-model="queryParams.roadwayId"
            placeholder="请选择钻孔地点"
            @change="getGroupIdList"
          >
            <el-option
              v-for="item in zkddOptions"
              :key="item.id"
              :label="item.roadway"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="钻孔组号" prop="zkbh">
          <el-col :span="20">
            <el-select
              filterable
              allow-create
              v-model="queryZuankongzuhao"
              collapse-tags
              placeholder="请选择钻孔组号"
              @change="getZuankongkonghaoList"
            >
              <el-option
                v-for="item in zkzhOptions"
                :key="item"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            <el-button class="bukong" type="text" @click="handlePatchHole" :disabled="queryParams.roadwayId==null">
              补孔
            </el-button>
            <!--            <span>补孔</span>-->
          </el-col>
        </el-form-item>
        <el-form-item label="钻孔序号" prop="zkbh">
          <el-select
            v-model="queryZuankongxuhao"
            collapse-tags
            placeholder="请选择钻孔序号"
          >
            <el-option
              v-for="item in zkxhOptions"
              :key="item"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <!-- <el-form-item label="参数用途" prop="zkyt">
                    <el-select v-model="queryParams.zkyt" placeholder="请选择钻孔用途">
                        <el-option v-for="item in zkytOptions" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item> -->
        <el-form-item label="验收日期" prop="checkingDate">
          <el-date-picker
            v-model="queryCheckingdate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="班次" prop="zkyt">
          <el-select v-model="queryParams.shiftId" placeholder="请选择工作班次">
            <el-option
              v-for="item in gzbcOptions"
              :key="item.id"
              :label="item.shiftInfo"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
          >查询
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-download"
            size="mini"
            @click="submitUpdate"
          >保存
          </el-button>
          <!-- <el-button
            type="primary"
            icon="el-icon-upload2"
            size="mini"
            @click="handleQuery"
            >导入
          </el-button> -->
          <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button> -->
        </el-form-item>
      </el-form>
    </el-row>
    <el-divider></el-divider>
    <el-row>
      <el-col :span="16">
        <el-row>
          <h4>钻孔物理信息</h4>
          <el-form
            :model="form"
            ref="queryForm"
            size="small"
            :inline="true"
            v-show="showSearch"
            label-width="138px"
          >
            <el-col :span="6">
              <el-form-item label="实际孔径" prop="zkdd">
                <el-input
                  v-model="form.borediameter"
                  placeholder="请输入实际孔径"
                  class="my_el-form-item"
                >
                  <template slot="append">mm</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="实际夹角" prop="zkdd">
                <el-input
                  v-model="form.angle"
                  placeholder="请输入实际夹角"
                  class="my_el-form-item"
                >
                  <template slot="append">°</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="实际方位角" prop="zkdd">
                <el-input
                  v-model="form.azimuthAngle"
                  placeholder="请输入实际方位角"
                  class="my_el-form-item"
                >
                  <template slot="append">°</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="实际倾角" prop="zkdd">
                <el-input
                  v-model="form.inclinationAngle"
                  placeholder="请输入实际倾角"
                  class="my_el-form-item"
                >
                  <template slot="append">°</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="第一岩段" prop="zkdd">
                <div class="my_el-form-item">
                  <el-col :span="11">
                    <el-input
                      v-model="form.depth1stRockSeamStart"
                      placeholder=""
                    />
                  </el-col>
                  <el-col :span="2" class="spli"> -</el-col>
                  <el-col :span="11">
                    <el-input
                      v-model="form.depth1stRockSeamEnd"
                      placeholder=""
                    />
                  </el-col>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="第一煤段" prop="zkdd">
                <div class="my_el-form-item">
                  <el-col :span="11">
                    <el-input
                      v-model="form.depth1stCoalSeamStart"
                      placeholder=""
                    />
                  </el-col>
                  <el-col :span="2" class="spli"> -</el-col>
                  <el-col :span="11">
                    <el-input
                      v-model="form.depth1stCoalSeamEnd"
                      placeholder=""
                    />
                  </el-col>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="第二岩段" prop="zkdd">
                <div class="my_el-form-item">
                  <el-col :span="11">
                    <el-input
                      v-model="form.depth2stRockSeamStart"
                      placeholder=""
                    />
                  </el-col>
                  <el-col :span="2" class="spli"> -</el-col>
                  <el-col :span="11">
                    <el-input
                      v-model="form.depth2stRockSeamEnd"
                      placeholder=""
                    />
                  </el-col>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="第二煤段" prop="zkdd">
                <div class="my_el-form-item">
                  <el-col :span="11">
                    <el-input
                      v-model="form.depth2stCoalSeamStart"
                      placeholder=""
                    />
                  </el-col>
                  <el-col :span="2" class="spli"> -</el-col>
                  <el-col :span="11">
                    <el-input
                      v-model="form.depth2stCoalSeamEnd"
                      placeholder=""
                    />
                  </el-col>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="第三岩段" prop="zkdd">
                <div class="my_el-form-item">
                  <el-col :span="11">
                    <el-input
                      v-model="form.depth3stRockSeamStart"
                      placeholder=""
                    />
                  </el-col>
                  <el-col :span="2" class="spli"> -</el-col>
                  <el-col :span="11">
                    <el-input
                      v-model="form.depth3stRockSeamEnd"
                      placeholder=""
                    />
                  </el-col>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="抽采半径" prop="zkdd">
                <el-input
                  v-model="queryParams.zkdd"
                  placeholder="请输入抽采半径"
                  class="my_el-form-item"
                >
                  <template slot="append">m</template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-form>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <h4>钻孔位置信息</h4>
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
            v-show="showSearch"
            label-width="138px"
          >
            <el-col :span="6">
              <el-form-item label="导线点" prop="zkdd">
                <el-select
                  v-model="form.navigationPointId"
                  placeholder="请选择"
                  class="my_el-form-item"
                >
                  <el-option
                    v-for="item in dxdList"
                    :key="item.id"
                    :label="item.navigationPointName"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
                <!--                <el-input v-model="form.navigationPointName"/>-->
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="对导线点方向" prop="zkdd">
                {{ form.directionToNavigation != null ? form.directionToNavigation : '正方向' }}
                <!-- <el-select
                  v-model="queryParams.zkdd"
                  placeholder="请选择"
                  class="my_el-form-item"
                >
                  <el-option
                    v-for="item in zkddOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select> -->
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="距导线点距离" prop="zkdd">
                <el-input
                  v-model="form.distanceToNavigationPoint"
                  placeholder=""
                  class="my_el-form-item"
                >
                  <template slot="append">m</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="距中心线距离" prop="zkdd">
                <el-input
                  v-model="form.distanceToCentrallineTunnel"
                  placeholder=""
                  class="my_el-form-item"
                >
                  <template slot="append">m</template>
                </el-input>
              </el-form-item>
            </el-col>
            <!-- <el-col :span="6">
              <el-form-item label="距洪基线高度(上正下负)" prop="zkdd">
                <el-input
                  v-model="queryParams.zkdd"
                  placeholder=""
                  class="my_el-form-item"
                >
                  <template slot="append">m</template>
                </el-input>
              </el-form-item>
            </el-col> -->
            <el-col :span="6">
              <el-form-item label="临近孔号" prop="zkdd">
                <!-- <el-select
                  v-model="queryParams.zkdd"
                  placeholder="请选择"
                  class="my_el-form-item"
                >
                  <el-option
                    v-for="item in zkddOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select> -->
                <el-input v-model="form.adjacentBoreholeNo"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="与临近孔距离" prop="zkdd">
                <el-input
                  v-model="form.distanceAdjacentBorehole"
                  placeholder=""
                  class="my_el-form-item"
                >
                  <template slot="append">m</template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-form>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <h4>其他</h4>
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
            v-show="showSearch"
            label-width="138px"
          >
            <el-col :span="8">
              <el-form-item label="拔钻时间" prop="zkdd">
                <!-- <el-time-picker
                  is-range
                  arrow-control
                  v-model="value2"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                  placeholder="选择时间范围"
                  class="time-select"
                >
                </el-time-picker> -->
                <el-date-picker
                  v-model="form.pullDrillDate"
                  class="time-select"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="拔钻后孔口瓦斯浓度" prop="zkdd">
                <el-input
                  v-model="form.gasConcentration"
                  placeholder=""
                  class="my_el-form-item"
                >
                  <template slot="append">%</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="拔钻后孔口CO浓度" prop="zkdd">
                <el-input
                  v-model="form.pulledcoconcentration"
                  placeholder=""
                  class="my_el-form-item"
                >
                  <template slot="append">ppm</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="打钻过程中异常现象" prop="zkdd">
                <el-checkbox-group
                  v-model="exceptionList"
                  style="width: 300px; line-height: normal"
                >
                  <el-checkbox label="喷孔"></el-checkbox>
                  <el-checkbox label="顶钻"></el-checkbox>
                  <el-checkbox label="夹钻"></el-checkbox>
                  <!--                  <el-checkbox label="其他"></el-checkbox>-->
                </el-checkbox-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="封孔方式" prop="zkdd">
                <el-select
                  v-model="queryParams.zkdd"
                  placeholder="请选择"
                  class="my_el-form-item"
                >
                  <el-option
                    v-for="item in zkddOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="下管时间" prop="zkdd">
                <el-date-picker
                  is-range
                  arrow-control
                  type="datetimerange"
                  v-model="form.downtubetime"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                  placeholder="选择时间范围"
                  class="time-select"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="封孔实管长度" prop="zkdd">
                <el-input
                  v-model="form.solidPipelineLength"
                  placeholder=""
                  class="my_el-form-item"
                >
                  <template slot="append">m</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="封孔花管长度" prop="zkdd">
                <el-input
                  v-model="form.sievePipelineLength"
                  placeholder=""
                  class="my_el-form-item"
                >
                  <template slot="append">m</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="封孔规格" prop="zkdd">
                <el-select
                  v-model="queryParams.zkdd"
                  placeholder="请选择"
                  class="my_el-form-item"
                >
                  <el-option
                    v-for="item in zkddOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="施工人员" prop="zkdd">
                <el-input
                  v-model="form.personConstruction"
                  placeholder=""
                  class="my_el-form-item"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="验收人员" prop="zkdd">
                <el-input
                  v-model="form.personChecking"
                  placeholder=""
                  class="my_el-form-item"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="验收时间" prop="zkdd">
                <el-date-picker
                  v-model="form.checkingDate"
                  placeholder=""
                  type="datetime"
                  class="my_el-form-item"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="其他封孔信息" prop="zkdd">
                <el-input
                  v-model="queryParams.zkdd"
                  placeholder=""
                  class="my_el-form-item"
                />
              </el-form-item>
            </el-col>
          </el-form>
        </el-row>
      </el-col>
      <el-col :span="6" class="cans">
        <el-card header="钻孔位置示意图">
          <el-row>
            <div class="can">
              <span style="font-size: 12px">平面图</span>
              <canvas ref="can1" width="300px" height="300px"></canvas>
            </div>
          </el-row>
          <el-row>
            <div class="can">
              <span style="font-size: 12px">开孔布置图</span>
              <canvas ref="can2" width="300px" height="300px"></canvas>
            </div>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <!-- <el-divider></el-divider>
        <el-container>
            <el-header>钻孔位置信息</el-header>
            <el-main>
                <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                    label-width="98px">
                    <el-form-item label="导线点" prop="zkdd">
                        <el-select v-model="queryParams.zkdd" placeholder="请选择">
                            <el-option v-for="item in zkddOptions" :key="item.value" :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="对导线点方向" prop="zkdd">
                        <el-select v-model="queryParams.zkdd" placeholder="请选择">
                            <el-option v-for="item in zkddOptions" :key="item.value" :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="距导线点距离" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="距中心线距离" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="距洪基线高度(上正下负)" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="临近孔号" prop="zkdd">
                        <el-select v-model="queryParams.zkdd" placeholder="请选择">
                            <el-option v-for="item in zkddOptions" :key="item.value" :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="与临近孔距离" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>
        <el-divider></el-divider>
        <el-container>
            <el-header>其他</el-header>
            <el-main>
                <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                    label-width="148px">
                    <el-form-item label="拔钻时间" prop="zkdd">
                        <el-select v-model="queryParams.zkdd" placeholder="请选择">
                            <el-option v-for="item in zkddOptions" :key="item.value" :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="拔钻后孔口瓦斯浓度" prop="zkdd">
                        <el-select v-model="queryParams.zkdd" placeholder="请选择">
                            <el-option v-for="item in zkddOptions" :key="item.value" :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="拔钻后孔口CO浓度" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="打钻过程中异常现象" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="封孔方式" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="下管时间" prop="zkdd">
                        <el-select v-model="queryParams.zkdd" placeholder="请选择">
                            <el-option v-for="item in zkddOptions" :key="item.value" :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="封孔实管长度" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="封孔花管长度" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="封孔规格" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="施工人员" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="验收人员" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                    <el-form-item label="其他封孔信息" prop="zkdd">
                        <el-input v-model="queryParams.zkdd" placeholder="" />
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container> -->
    <!-- <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                    v-hasPermi="['/:tGasContentPressure:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                    v-hasPermi="['/:tGasContentPressure:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                    @click="handleDelete" v-hasPermi="['/:tGasContentPressure:remove']">删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    v-hasPermi="['/:tGasContentPressure:export']">导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row> -->

    <!-- 添加或修改tGasContentPressure对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="取样点" prop="samplingpot">
          <el-input v-model="form.samplingpot" placeholder="请输入取样点"/>
        </el-form-item>
        <el-form-item label="孔号ID" prop="boreholeId">
          <el-input v-model="form.boreholeId" placeholder="请输入孔号ID"/>
        </el-form-item>
        <el-form-item label="取样深度" prop="samplingDepth">
          <el-input v-model="form.samplingDepth" placeholder="请输入取样深度"/>
        </el-form-item>
        <el-form-item label="瓦斯含量">
          <editor v-model="form.gasContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="瓦斯压力" prop="gasPressure">
          <el-input v-model="form.gasPressure" placeholder="请输入瓦斯压力"/>
        </el-form-item>
        <el-form-item label="测量日期" prop="measureDate">
          <el-date-picker
            clearable
            v-model="form.measureDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择测量日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog title="补孔" :visible.sync="showPatchHole" width="400px" append-to-body>
      <el-form ref="patchHoleForm" :model="patchHoleForm" :rules="rules" label-width="80px">
        <el-form-item label="组号" prop="groupNo">
          <el-input v-model="patchHoleForm.groupNo" placeholder="请输入组号"/>
        </el-form-item>
        <el-form-item label="孔号" prop="boreholeNo">
          <el-input v-model="patchHoleForm.boreholeNo" placeholder="请输入孔号"/>
        </el-form-item>
        <el-form-item label="导线点" prop="navigationPointId">
          <el-select
            v-model="patchHoleForm.navigationPointId"
            placeholder="请选择"
            class="my_el-form-item"
          >
            <el-option
              v-for="item in dxdList"
              :key="item.id"
              :label="item.navigationPointName+'  ('+item.x+','+item.y+','+item.z+')'"
              :value="item.id"
            >
            </el-option>
          </el-select>
          <!--          <el-input v-model="desHoleForm.navigationPointId" placeholder=""/>-->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPatchHoleForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<style scoped>
.time-select {
  width: 200px;
  margin: 0;
}

.bukong {
  margin-left: 5px;
  color: royalblue;
}

.spli {
  text-align: center;
}

.cans {
  height: 60vh;
}

.can {
  width: 300px;
  height: 300px;
}

.my_el-form-item {
  width: 200px;
}
</style>
<script>
import { gGongzuobanci } from '@/api/hhhz/sys'
import { gGroupIdList, gHoleNoList, gRoadWayList, gZuankongyongtuList } from '@/api/hhhz/zuankong'
import { gZuankongyuanshiyanshoudan, uZuankongyuanshiyanshoudan } from '@/api/hhhz/13zkysysd'

import { gRoad } from '@/api/hhhz/hangdao'
import { aActualBorehole } from '@/api/hhhz/actual_borehole'

export default {
  name: 'TGasContentPressure',
  data() {
    return {
      showPatchHole: false,
      patchHoleForm: {},
      // 选中孔序号
      queryZuankongxuhao: [],
      // 选中组号
      queryZuankongzuhao: [],
      // 选中钻孔用途
      queryZuankongyongtu: [],
      // 选中时间
      queryCheckingdate: [],
      // 工作班次
      gzbcOptions: [],
      // 钻孔地点
      zkddOptions: [],
      // 钻孔用途
      zkytOptions: [],
      // 钻孔组号
      zkzhOptions: [],
      // 钻孔编号
      zkxhOptions: [],
      // 导线点数据
      dxdList: [],
      exceptionList: [],
      ycxx: '',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // tGasContentPressure表格数据
      tGasContentPressureList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getRoadWayList()
    this.getZuankongyongtuList()
    this.getGongzuobanciList()

    //this.getList();
  },
  mounted() {
    this.initCanvas1()
    this.initCanvas2()
  },
  methods: {
    handlePatchHole() {
      this.patchHoleForm = {}
      this.patchHoleForm.roadwayId = this.queryParams.roadwayId
      this.showPatchHole = true
    },

    submitPatchHoleForm() {
      this.$refs['patchHoleForm'].validate((valid) => {
        if (valid) {
          if (this.patchHoleForm.id != null) {
            /* uDesignedBorehole(this.desHoleForm).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.showPatchHole = false
              this.listDesignedBorehole()
            }) */
          } else {
            aActualBorehole([this.patchHoleForm]).then((response) => {
              this.$modal.msgSuccess('补孔成功')
              this.showPatchHole = false
              this.getRoadWayList()
            })
          }
        }
      })
    },

    // 获取工作班次
    async getGongzuobanciList() {
      await gGongzuobanci().then((response) => {
        console.log('gGongzuobanci', response)
        this.gzbcOptions = response.rows
      })
    },
    // 获取钻孔地点
    async getRoadWayList() {
      this.loading = true
      await gRoadWayList({}).then((response) => {
        console.log('gRoadWayList', response)
        this.zkddOptions = response.rows
      })
    },
    // 获取钻孔用途
    async getZuankongyongtuList() {
      await gZuankongyongtuList({}).then((response) => {
        console.log('gZuankongyongtuList', response)
        this.zkytOptions = response.rows
      })
    },
    // 获取钻孔组号
    async getGroupIdList(rwId) {
      // 清空
      this.initZkzh()
      await gGroupIdList({ roadwayId: rwId }).then((response) => {
        console.log('gGroupIdList', response)
        this.zkzhOptions = response.data
      })
      await gRoad({ id: rwId }).then((response) => {
        console.log('gWorkFace', response)
        if (response.total !== 1) {
          console.log('err', response)
        } else {
          console.log('导线点', response.rows[0])
          this.dxdList = response.rows[0].tnavigationPointList
        }
      })
    },
    // 获取钻孔孔号
    async getZuankongkonghaoList(zkzh) {
      console.log('groupNo', zkzh)
      this.initZkxh()
      if (zkzh) {
        await gHoleNoList({
          roadwayId: this.queryParams.roadwayId,
          groupNo: zkzh
        }).then((response) => {
          console.log('gHoleNoList', response)
          this.zkxhOptions = response
        })
      } else {
        this.initZkxh()
      }
    },
    // 初始化操作会级联清空下层
    // 初始化钻孔地点
    initZkdd() {
      this.initZkzh()
      delete delete this.queryParams.roadwayId
    },
    // 初始化钻孔组号
    initZkzh() {
      this.initZkxh()
      this.zkzhOptions = []
      this.queryZuankongzuhao = []
      this.dxdList = []
    },
    // 初始化钻孔序号
    initZkxh() {
      this.zkxhOptions = []
      this.queryZuankongxuhao = []
    },
    // 初始化所有
    initAll() {
      this.initZkdd()
      this.queryZuankongyongtu = []
      this.queryCheckingdate = []
    },
    initCanvas1() {
      let c1 = this.$refs.can1.getContext('2d')
      //console.log(c1);
      c1.clearRect(0, 0, 600, 600)
      c1.setLineDash([])
      c1.beginPath()
      c1.moveTo(20, 20)
      c1.lineTo(300, 20)
      c1.stroke()

      c1.beginPath()
      c1.moveTo(20, 60)
      c1.setLineDash([4, 4])
      c1.lineTo(300, 60)
      c1.stroke()

      c1.beginPath()
      c1.setLineDash([])
      c1.moveTo(20, 100)
      c1.lineTo(300, 100)
      c1.stroke()

      c1.beginPath()
      c1.arc(120, 60, 5, 0, Math.PI * 2, true)
      c1.fillStyle = 'white'
      c1.fill()

      c1.beginPath()
      c1.arc(120, 60, 5, 0, Math.PI * 2, true)
      c1.stroke()

      c1.beginPath()
      c1.arc(260, 20, 5, 0, Math.PI * 2, true)
      c1.fillStyle = 'white'
      c1.fill()

      c1.beginPath()
      c1.arc(260, 20, 5, 0, Math.PI * 2, true)
      c1.stroke()

      c1.beginPath()
      c1.moveTo(120, 55)
      c1.lineTo(120, 30)
      c1.stroke()

      c1.beginPath()
      c1.moveTo(260, 25)
      c1.lineTo(260, 50)
      c1.stroke()

      c1.beginPath()
      c1.moveTo(120, 40)
      c1.lineTo(130, 35)
      c1.lineTo(130, 45)
      c1.fillStyle = 'black'
      c1.fill()

      c1.beginPath()
      c1.moveTo(260, 40)
      c1.lineTo(250, 35)
      c1.lineTo(250, 45)
      c1.fillStyle = 'black'
      c1.fill()

      c1.beginPath()
      c1.moveTo(130, 40)
      c1.lineTo(150, 40)
      c1.stroke()

      c1.beginPath()
      c1.moveTo(230, 40)
      c1.lineTo(250, 40)
      c1.stroke()

      c1.fillText('切眼方向(导向点内)', 210, 15)
    },
    initCanvas2() {
      let c2 = this.$refs.can2.getContext('2d')
      c2.setLineDash([])
      c2.beginPath()
      c2.moveTo(50, 200)
      c2.lineTo(50, 100)
      c2.stroke()

      c2.beginPath()
      c2.moveTo(150, 200)
      c2.lineTo(150, 100)
      c2.stroke()

      c2.beginPath()
      c2.arc(100, 100, 50, 0, Math.PI, true)
      c2.stroke()

      c2.beginPath()
      c2.arc(50, 100, 5, 0, Math.PI * 2, true)
      c2.fillStyle = 'white'
      c2.fill()

      c2.beginPath()
      c2.arc(50, 100, 5, 0, Math.PI * 2, true)
      c2.stroke()

      c2.beginPath()
      c2.moveTo(55, 100)
      c2.lineTo(75, 100)
      c2.stroke()

      /* c2.beginPath();
            c2.moveTo(260, 25);
            c2.lineTo(260, 50);
            c2.stroke(); */

      c2.beginPath()
      c2.moveTo(65, 100)
      c2.lineTo(60, 105)
      c2.lineTo(70, 105)
      c2.fillStyle = 'black'
      c2.fill()

      c2.beginPath()
      c2.moveTo(65, 200)
      c2.lineTo(60, 195)
      c2.lineTo(70, 195)
      c2.fillStyle = 'black'
      c2.fill()

      c2.beginPath()
      c2.moveTo(65, 195)
      c2.lineTo(65, 175)
      c2.stroke()

      c2.beginPath()
      c2.moveTo(65, 105)
      c2.lineTo(65, 125)
      c2.stroke()
    },
    /** 查询tGasContentPressure列表 */
    /** 查询tDrainageParameter列表 */
    getList() {
      this.loading = true
      // 钻孔组号
      console.log('组号:', this.queryZuankongzuhao)
      if (this.queryZuankongzuhao) {
        this.queryParams.groupNo = this.queryZuankongzuhao
      } else {
        delete this.queryParams.groupNo
      }
      // 钻孔序号
      console.log('序号:', this.queryZuankongxuhao)
      if (this.queryZuankongxuhao) {
        this.queryParams.boreholeNo = this.queryZuankongxuhao
      } else {
        delete this.queryParams.boreholeNo
      }
      // 钻孔用途
      console.log('钻孔用途:', this.queryZuankongyongtu)
      if (this.queryZuankongyongtu && this.queryZuankongyongtu.length >= 1) {
        this.queryParams.purposeBoreholeId = JSON.stringify(
          this.queryZuankongyongtu
        )
          .replace('[', '')
          .replace(']', '')
      } else {
        delete this.queryParams.purposeBoreholeId
      }
      // 时间
      console.log('时间', this.queryCheckingdate)
      if (this.queryCheckingdate && this.queryCheckingdate.length >= 1) {
        this.queryParams.startdate = this.queryCheckingdate[0]
          .toLocaleString()
          .replaceAll('/', '-')
      } else {
        delete this.queryParams.startdate
      }
      console.log('queryParams', this.queryParams)
      gZuankongyuanshiyanshoudan(this.queryParams).then((response) => {
        let resp = response.rows
        this.actual_list = []
        let ysList = []
        resp.forEach((e) => {
          e.holeId = e.roadway + '-' + e.groupNo + '-' + e.boreholeNo
          e.downtubetime = [e.downTubeTimeStart, e.downTubeTimeEnd]

          e.checkingDate = e.checkingDate + '00:00:00'
          ysList.push(e)
        })
        this.actual_list = ysList
        this.total = response.total

        if (this.actual_list.length >= 1) {
          this.form = this.actual_list[0]
          this.exceptionList = []
          if (this.form.exceptionDuringDrilling != null) {
            this.exceptionList = this.form.exceptionDuringDrilling.split(',')
          }
          console.log('this.exceptionList', this.exceptionList)
          console.log('form', this.form)
        }
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.patchHoleForm = {}
      this.showPatchHole = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        samplingpot: null,
        boreholeId: null,
        samplingDepth: null,
        gasContent: null,
        gasPressure: null,
        measureDate: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加tGasContentPressure'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTGasContentPressure(id).then((response) => {
        this.form = response.data
        this.open = true
        this.title = '修改tGasContentPressure'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTGasContentPressure(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addTGasContentPressure(this.form).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    // 提交更新
    submitUpdate() {
      this.form.downTubeTimeStart = this.form.downtubetime[0]
      this.form.downTubeTimeEnd = this.form.downtubetime[1]
      this.form.exceptionDuringDrilling = JSON.stringify(
        this.exceptionList
      )
        .replaceAll('[', '')
        .replaceAll(']', '')
        .replaceAll('\\', '')
        .replaceAll('"', '')
      uZuankongyuanshiyanshoudan(this.form).then((response) => {
        this.$modal.msgSuccess('修改成功')
        this.open = false
        this.getList()
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除tGasContentPressure编号为"' + ids + '"的数据项？')
        .then(function() {
          return delTGasContentPressure(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        '/tGasContentPressure/export',
        {
          ...this.queryParams
        },
        `tGasContentPressure_${new Date().getTime()}.xlsx`
      )
    }
  }
}
</script>
