<template>
  <div class="app-container">
    <!-- 底抽巷 -->
    <!-- <transition name="el-fade-in"> -->
    <div v-if="showdch">
      <el-row :gutter="24">
        <!--巷道工作面列表-->
        <el-col :span="4" :xs="24">
          <el-card style="height: 80vh; margin: 10px 5px;overflow-y: auto">
            <!-- <div slot="header" class="clearfix">
              <span>底抽巷列表</span>
              <div style="float: right">
                <el-button type="primary" size="mini" @click="showAddDch"
                  >新增底抽巷
                </el-button>
              </div>
            </div>
            <div class="head-container">
              <li v-for="item in roadList" :key="item.id">
                <a href="#" @click="setCurrentRoad(item)">
                  ID:{{ item.id }}--{{ item.roadway }}</a
                >
              </li>
            </div> -->

            <div slot="header" class="clearfix">
              <span>工作面列表</span>
              <div style="float: right">
                <el-button size="mini" type="primary" @click="showAddDch">
                  新增评价单元
                </el-button>
              </div>
            </div>
            <div class="head-container">
              <!--              :default-active="currentRoad.type+'-'+currentRoad.id"
                            @close="handleClose"
                            @open="handleOpen"
                            collapse-transition
                              :default-openeds="[currentRoad.type.toString()]"-->
              <el-menu
                class="el-menu-vertical-demo"
              >

                <el-submenu v-for="menItem in roadTypes" :index="menItem.id">
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
            </div>
          </el-card>
        </el-col>
        <el-col :span="20" :xs="24">
          <el-row>
            <el-col :span="24">
              <el-row>
                <h4>详细信息</h4>
                <el-form
                  :model="form"
                  ref="queryForm"
                  size="small"
                  :inline="true"
                  v-show="true"
                  label-width="138px"
                >
                  <el-col :span="8">
                    <el-form-item label="单元名称" prop="zkdd">
                      <el-input
                        v-model="form.borediameter"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">mm</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="走向长度" prop="zkdd">
                      <el-input
                        v-model="form.angle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="宽度" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标1X" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">m</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标1Y" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标1Z" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标2X" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标2Y" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标2Z" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标3X" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标3Y" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标3Z" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标4X" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标4Y" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4">
                    <el-form-item label="坐标4z" prop="zkdd">
                      <el-input
                        v-model="form.azimuthAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="煤厚" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="容重" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="原始瓦斯含量" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="总瓦斯储量" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <!--                  -->
                  <el-col :span="6">
                    <el-form-item label="达标抽采量" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="初始抽采量" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="计量因子" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="煤炭储量" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <!--                  -->
                  <el-col :span="6">
                    <el-form-item label="达标残余瓦斯量" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="残余瓦斯含量" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="日计划抽采量" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="原始瓦斯压力" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <!--                  -->
                  <el-col :span="6">
                    <el-form-item label="达标可解吸瓦斯量" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="备注" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="抽采半径" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="预计达标时间" prop="zkdd">
                      <el-input
                        v-model="form.inclinationAngle"
                        placeholder="请输入"
                        class="my_el-form-item"
                      >
                        <template slot="append">°</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <!--                  -->
                  <!--                  <el-col :span="6">
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
                                    </el-col>-->
                </el-form>
              </el-row>
              <el-divider></el-divider>
              <el-row>
                <h4>关联测点信息</h4>
                <el-form
                  :model="queryParams"
                  ref="queryForm"
                  size="small"
                  :inline="true"
                  v-show="true"
                  label-width="138px"
                >
                  <el-col :span="7">
                    <el-form-item label="所在巷道" prop="zkdd">
                      <el-select value="">
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="7">
                    <el-form-item label="测点类型" prop="zkdd">
                      <el-select value="">
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="7">
                    <el-form-item label="抽采测点" prop="zkdd">
                      <el-select value="">
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="3">
                    <el-button>添加</el-button>
                  </el-col>
                  <el-table
                    :data="glcdTableData"
                    style="width: 100%">
                    <el-table-column
                      prop="id"
                      label="索引">
                    </el-table-column>
                    <el-table-column
                      prop="zbName"
                      label="抽采编号">
                    </el-table-column>
                    <el-table-column
                      prop="dw"
                      label="名称">
                    </el-table-column>
                    <el-table-column
                      prop="num"
                      label="位置">
                    </el-table-column>
                    <el-table-column
                      prop="num"
                      label="测点类型">
                    </el-table-column>
                    <el-table-column
                      prop="num"
                      label="所在巷道">
                    </el-table-column>
                    <el-table-column
                      label="操作"
                      align="center"
                      class-name="small-padding fixed-width"
                    >
                      <template slot-scope="scope">
                        <el-button
                          v-if=" scope.row.disabled"
                          size="mini"
                          type="text"
                          icon="el-icon-edit"
                          @click="() => {scope.row.disabled = false;}"
                        >修改
                        </el-button>
                        <el-button
                          v-if=" !scope.row.disabled"
                          size="mini"
                          type="text"
                          icon="el-icon-check"
                          @click="update(scope.row)"
                        >保存
                        </el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                  <el-upload
                    class="upload-demo"
                    action="https://jsonplaceholder.typicode.com/posts/"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :before-remove="beforeRemove"
                    multiple
                    :limit="3"
                    :on-exceed="handleExceed"
                    :file-list="fileList">
                    <el-button size="small" type="primary">点击上传</el-button>
                    <!--                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
                  </el-upload>
                </el-form>
              </el-row>
            </el-col>
          </el-row>
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
  </div>
</template>

<script>
import { aRoad, aRoadType, dRoad, dRoadType, gRoad, gRoadType, uRoad, uRoadType } from '@/api/hhhz/hangdao'
import { aRoadZone, dRoadZone, uRoadZone } from '@/api/hhhz/hangdaoquduan'
import { aNavPoint, dNavPoint, uNavPoint } from '@/api/hhhz/daoxiandian'
import { getToken } from '@/utils/auth'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'User',
  dicts: ['sys_normal_disable', 'sys_user_sex', 'sys_user_expire'],
  components: { Treeselect },
  data() {
    return {
      // 选中孔序号
      queryZuankongxuhao: [],
      // 选中组号
      queryZuankongzuhao: [],
      // 选中钻孔用途
      queryZuankongyongtu: [],
      // 选中时间
      queryCheckingdate: [],
      // 钻孔地点
      zkddOptions: [],
      // 钻孔用途
      zkytOptions: [],
      // 钻孔组号
      zkzhOptions: [],
      // 钻孔编号
      zkxhOptions: [],
      gzbcOptions: [],
      form: {},
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
      glcdTableData: [],
      fileList: []

    }
  },
  watch: {},
  async created() {
    await this.getRoadType()
    await this.getRoad({})
    if (this.dchRoadList.length >= 1) {
      this.currentRoad = this.dchRoadList[0]
    }
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    getZuankongkonghaoList() {

    },
    getGroupIdList() {

    },
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
