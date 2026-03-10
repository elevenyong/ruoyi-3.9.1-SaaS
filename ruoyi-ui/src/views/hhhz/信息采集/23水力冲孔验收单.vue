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
        label-width="68px"
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
          <el-select
            v-model="queryParams.groupNo"
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
        </el-form-item>
        <el-form-item label="钻孔序号" prop="zkbh">
          <el-select
            v-model="queryParams.boreholeNo"
            placeholder="请选择钻孔序号"
          >
            <el-option
              v-for="item in zkxhOptions"
              :key="item.boreholeNo"
              :label="item.boreholeNo"
              :value="item.boreholeNo"
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
            >搜索</el-button
          >
          <el-button
            v-if="flushingData != null && flushingData.disabled == true"
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            @click="
              () => {
                flushingData.disabled = false;
              }
            "
            >编辑</el-button
          >
          <el-button
            v-if="
              flushingData != null &&
              flushingData.disabled != null &&
              flushingData.disabled != true
            "
            icon="el-icon-check"
            type="success"
            size="mini"
            @click="save()"
            >保存</el-button
          >
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="add()"
            >新增</el-button
          >
        </el-form-item>

        <el-form-item label="已存在冲孔信息" prop="zkyt" label-width="150px">
          <el-select v-model="rqbc" placeholder="请选择" @change="selectFlData">
            <el-option
              v-for="item in boreholeList"
              :key="item.rqbc"
              :label="item.rqbc"
              :value="item.rqbc"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <!-- <el-form
        :model="queryParams"
        ref="queryForm"
        size="small"
        :inline="true"
        v-show="showSearch"
        label-width="88px"
      >
        <el-form-item label="钻孔地点" prop="zkdd">
          <el-select v-model="queryParams.zkdd" placeholder="请选择钻孔地点">
            <el-option
              v-for="item in zkddOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="钻场编号" prop="zcbh">
          <el-col :span="20">
            <el-select v-model="queryParams.zcbh" placeholder="请选择钻场编号">
              <el-option
                v-for="item in zcbhOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="钻孔编号" prop="zkbh">
          <el-select v-model="queryParams.zkbh" placeholder="请选择钻孔编号">
            <el-option
              v-for="item in zkbhOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="已存在冲孔信息" prop="zkyt" label-width="250px">
          <el-select v-model="queryParams.zkyt" placeholder="请选择钻孔用途">
            <el-option
              v-for="item in zkytOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form> -->
    </el-row>
    <el-divider></el-divider>
    <el-row>
      <el-col :span="8">
        <h4>施工信息</h4>
        <el-form
          :model="flushingData"
          ref="queryForm"
          size="small"
          v-show="showSearch"
          label-width="158px"
        >
          <el-form-item label="冲孔日期" prop="zkdd" style="width: 450px">
            <el-date-picker
              clearable
              v-model="flushingData.dateflushing"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择冲孔日期"
              style="width: 180px"
              :disabled="flushingData.disabled"
            >
            </el-date-picker>
            <el-select
              v-model="flushingData.shiftId"
              placeholder="班次"
              style="width: 80px; margin: 0 3px"
              :disabled="flushingData.disabled"
            >
              <el-option
                v-for="item in gzbcOptions"
                :key="item.id"
                :label="item.shiftInfo"
                :value="item.id"
              >
              </el-option>
            </el-select>
            <span>班</span>
          </el-form-item>
          <el-form-item label="冲孔时间" prop="zkdd" style="width: 350px">
            <el-date-picker
              v-model="flushingData.timeflushing"
              type="datetimerange"
              value-format="yyyy-MM-dd HH:mm:ss"
              is-range
              arrow-control
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围"
              :disabled="flushingData.disabled"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="累计冲孔时间" prop="zkdd">
            <el-input
              v-model="flushingData.totalTimeFlushing"
              placeholder="120"
              style="width: 200px"
              :disabled="flushingData.disabled"
            >
              <template slot="append">min</template>
            </el-input>
          </el-form-item>

          <el-form-item label="冲孔压力" prop="zkdd">
            <el-input
              v-model="flushingData.waterPressureDuringFlushing"
              placeholder="请输入冲孔压力"
              style="width: 200px"
              :disabled="flushingData.disabled"
            >
              <template slot="append">MPa</template>
            </el-input>
          </el-form-item>
          <el-form-item label="冲孔段" prop="zkdd" style="width: 550px">
            <el-col :span="8">
              <!-- flushingData.punchingParagraph1 -->
              <el-input
                v-model="flushingData.punchingParagraph1"
                placeholder=""
                :disabled="flushingData.disabled"
              >
                <template slot="append">m</template>
              </el-input>
            </el-col>
            <el-col :span="1" class="spli"> - </el-col>
            <el-col :span="8">
              <!-- flushingData.punchingParagraph2 -->
              <el-input
                v-model="flushingData.punchingParagraph2"
                placeholder=""
                :disabled="flushingData.disabled"
              >
                <template slot="append">m</template>
              </el-input>
            </el-col>
            <el-col :span="7">
              <span style="font-size: 10px; margin-left: 3px"
                >冲孔段长度:{{
                  flushingData.punchingParagraph1 != null
                    ? (
                        flushingData.punchingParagraph2 -
                        flushingData.punchingParagraph1
                      ).toFixed(2)
                    : 0
                }}</span
              >
            </el-col>
          </el-form-item>
          <el-form-item label="冲孔前孔口瓦斯浓度" prop="zkdd">
            <el-input
              v-model="flushingData.gasconcbeforeflushing"
              placeholder=""
              style="width: 200px"
              :disabled="flushingData.disabled"
            >
              <template slot="append">%</template>
            </el-input>
          </el-form-item>
          <el-form-item label="冲孔后孔口瓦斯浓度" prop="zkdd">
            <el-input
              v-model="flushingData.gasconcafterflushing"
              placeholder=""
              style="width: 200px"
              :disabled="flushingData.disabled"
            >
              <template slot="append">%</template>
            </el-input>
          </el-form-item>

          <el-form-item
            label="冲孔期间瓦斯变化情况"
            prop="zkdd"
            style="width: 450px"
          >
            <el-col :span="11">
              <el-input
                v-model="flushingData.gaschangeduring"
                placeholder=""
                :disabled="flushingData.disabled"
              >
                <template slot="append">%</template>
              </el-input>
            </el-col>
            <el-col :span="2" class="spli"> - </el-col>
            <el-col :span="11">
              <el-input
                v-model="flushingData.gaschangeduring"
                placeholder=""
                :disabled="flushingData.disabled"
              >
                <template slot="append">%</template>
              </el-input>
            </el-col>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="6">
        <div></div>
      </el-col>
      <el-col :span="8">
        <h4>验收信息</h4>
        <el-form
          :model="flushingData"
          ref="queryForm"
          size="small"
          v-show="showSearch"
          label-width="158px"
        >
          <!-- <el-col :span="8">
                        <el-form-item label="拔钻时间" prop="zkdd">
                            <el-time-picker is-range arrow-control v-model="value2" range-separator="至"
                                start-placeholder="开始时间" end-placeholder="结束时间" placeholder="选择时间范围">
                            </el-time-picker>
                        </el-form-item>
                    </el-col> -->
          <el-form-item label="冲出煤量体积" prop="zkdd">
            <el-input
              v-model="flushingData.actualCoalAmountFlushed"
              placeholder=""
              style="width: 150px"
              :disabled="flushingData.disabled"
            >
              <template slot="append">m³</template>
            </el-input>
          </el-form-item>
          <el-form-item label="煤体密度" prop="zkdd">
            <el-input
              v-model="flushingData.coaldensity"
              placeholder=""
              style="width: 150px"
              :disabled="flushingData.disabled"
            >
              <template slot="append">t/m³</template>
            </el-input>
          </el-form-item>
          <el-form-item label="冲出煤量" prop="zkdd">
            <span
              >{{
                flushingData.actualCoalAmountFlushed != null &&
                flushingData.coaldensity != null
                  ? (
                      flushingData.actualCoalAmountFlushed *
                      flushingData.coaldensity
                    ).toFixed(2)
                  : 0
              }}t</span
            >
          </el-form-item>
          <el-form-item label="施工人员" prop="zkdd">
            <el-input
              v-model="flushingData.personConstruction"
              placeholder=""
              style="width: 100px"
              :disabled="flushingData.disabled"
            />
          </el-form-item>
          <el-form-item label="验收人员" prop="zkdd">
            <el-input
              v-model="flushingData.personChecking"
              placeholder=""
              style="width: 100px"
              :disabled="flushingData.disabled"
            />
          </el-form-item>
          <el-form-item label="打钻过程中异常现象" prop="zkdd">
            <el-input
              v-model="flushingData.exceptionPhenomena"
              placeholder=""
              style="width: 100px"
              :disabled="flushingData.disabled"
            />
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-row>
      <h4>冲孔验收单图片上传</h4>
      <el-upload
        class="upload-demo"
        drag
        action="https://jsonplaceholder.typicode.com/posts/"
        multiple
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">
          只能上传jpg/png文件，且不超过500kb
        </div>
      </el-upload>
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
          <el-input v-model="form.samplingpot" placeholder="请输入取样点" />
        </el-form-item>
        <el-form-item label="孔号ID" prop="boreholeId">
          <el-input v-model="form.boreholeId" placeholder="请输入孔号ID" />
        </el-form-item>
        <el-form-item label="取样深度" prop="samplingDepth">
          <el-input v-model="form.samplingDepth" placeholder="请输入取样深度" />
        </el-form-item>
        <el-form-item label="瓦斯含量">
          <editor v-model="form.gasContent" :min-height="192" />
        </el-form-item>
        <el-form-item label="瓦斯压力" prop="gasPressure">
          <el-input v-model="form.gasPressure" placeholder="请输入瓦斯压力" />
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
  </div>
</template>
<style scoped>
.bukong {
  margin-left: 5px;
  color: royalblue;
}

.spli {
  text-align: center;
}

.el-form-item__content {
  width: 350px;
}
</style>
<script>
import {
  gZuankongyongtuList,
  gGroupIdList,
  gRoadWayList,
  gHoleNoListWithId,
} from "@/api/hhhz/zuankong";
import { gGongzuobanci } from "@/api/hhhz/sys";
import {
  gShuilichongkongYanshoudan,
  aShuilichongkongtaizhang,
  uShuilichongkongtaizhang,
} from "@/api/hhhz/19slcktz";
export default {
  name: "TGasContentPressure",
  data() {
    return {
      rqbc: null,
      edit: true,
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
      // 工作班次
      gzbcOptions: [],
      // 冲孔List
      boreholeList: [],
      ycxx: "",
      flushingData: [],
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
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {},
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  async created() {
    await this.getRoadWayList();
    await this.getZuankongyongtuList();
    await this.getGongzuobanciList();
    //this.getList();
  },
  mounted() {
    //this.initCanvas1();
    //this.initCanvas2();
  },
  methods: {
    selectFlData(data) {
      this.boreholeList.forEach((e) => {
        if (e.rqbc === data) {
          this.flushingData = e;
          return;
        }
      });
    },
    // 获取工作班次
    async getGongzuobanciList() {
      await gGongzuobanci().then((response) => {
        console.log("gGongzuobanci", response);
        this.gzbcOptions = response.rows;
      });
    },
    // 获取钻孔地点
    async getRoadWayList() {
      this.loading = true;
      await gRoadWayList({}).then((response) => {
        console.log("gRoadWayList", response);
        this.zkddOptions = response.rows;
      });
    },
    // 获取钻孔用途
    async getZuankongyongtuList() {
      await gZuankongyongtuList({}).then((response) => {
        console.log("gZuankongyongtuList", response);
        this.zkytOptions = response.rows;
      });
    },
    // 获取钻孔组号
    async getGroupIdList(rwId) {
      // 清空
      this.initZkzh();
      await gGroupIdList({ roadwayId: rwId }).then((response) => {
        console.log("gGroupIdList", response);
        this.zkzhOptions = response.data;
      });
    },
    // 获取钻孔孔号
    async getZuankongkonghaoList(zkzh) {
      console.log("groupNo", zkzh);
      this.initZkxh();
      if (zkzh) {
        await gHoleNoListWithId({
          roadwayId: this.queryParams.roadwayId,
          groupNo: zkzh,
        }).then((response) => {
          console.log("gHoleNoList", response);
          this.zkxhOptions = response;
        });
      } else {
        this.initZkxh();
      }
    },
    // 初始化操作会级联清空下层
    // 初始化钻孔地点
    initZkdd() {
      this.initZkzh();
      delete this.queryParams.roadwayId;
    },
    // 初始化钻孔组号
    initZkzh() {
      this.initZkxh();
      this.zkzhOptions = [];
    },
    // 初始化钻孔序号
    initZkxh() {
      delete this.queryParams.boreholeNo;
    },
    // 初始化所有
    initAll() {
      this.initZkdd();
      this.queryZuankongyongtu = [];
      this.queryCheckingdate = [];
    },
    /** 查询tGasContentPressure列表 */
    getList() {
      this.loading = true;
      gShuilichongkongYanshoudan(this.queryParams).then((response) => {
        console.log("gShuilichongkongYanshoudan", response);
        this.total = response.total;
        if (response.total >= 1) {
          let resp = response.rows;
          this.actual_list = [];
          let ysList = [];
          resp.forEach((e) => {
            e.rqbc = e.dateflushing + " " + e.shiftInfo;
            e.disabled = true;
            e.holeId = e.roadway + "-" + e.groupNo + "-" + e.boreholeNo;
            e.timeflushing = [e.timeBeginFlushing, e.timeEndFlushing];
            ysList.push(e);
          });
          this.boreholeList = ysList;
          this.rqbc = this.boreholeList[0].rqbc;
          this.selectFlData(this.boreholeList[0].rqbc);
          /* this.flushingData.e.timeflushing = [
            this.flushingData.timeBeginFlushing,
            this.flushingData.timeEndFlushing,
          ]; */
          this.edit = true;
          console.log(this.flushingData);
        } else {
          this.$modal.msgSuccess("没有查询到冲孔信息，请创建。");
          this.add();
        }
        this.loading = false;
      });
    },
    add() {
      this.edit = false;
      this.boreholeList = [];
      this.rqbc = null;
      this.flushingData = {
        remark: null,
        roadway: this.getRoadWayName(this.queryParams.roadwayId),
        boreholeId: this.getBoreholeIdByNo(this.queryParams.boreholeNo),
        borediameter: null,
        boreholeLength: null,
        azimuthAngle: null,
        inclinationAngle: null,
        shiftId: null,
        shiftInfo: null,
        dateflushing: null,
        timeBeginFlushing: null,
        timeEndFlushing: null,
        totalTimeFlushing: null,
        waterPressureDuringFlushing: null,
        gasconcbeforeflushing: null,
        gasconcafterflushing: null,
        punchingParagraph1: null,
        punchingParagraph2: null,
        punchingparagraph: null,
        length: null,
        seeRockCoalCase: null,
        seerockcase: null,
        actualCoalAmountFlushed: null,
        coaldensity: null,
        completionRatioFlushedCoal: null,
        personConstruction: null,
        chargehand: null,
        receptiontime: null,
        gaschangeduring: null,
        exceptionPhenomena: null,
        personChecking: null,
        otherPerson: null,
        disabled: false,
      };
    },
    save() {
      this.flushingData.timeBeginFlushing = this.flushingData.timeflushing[0];
      this.flushingData.timeEndFlushing = this.flushingData.timeflushing[1];
      //delete this.flushingData.timeflushing;
      if (this.edit) {
        uShuilichongkongtaizhang(this.flushingData).then((response) => {
          console.log(response);
          if (response.code == 200) {
            this.$modal.msgSuccess(response.msg);
            this.getList();
          }
        });
      } else {
        aShuilichongkongtaizhang(this.flushingData).then((response) => {
          console.log(response);
          if (response.code == 200) {
            this.$modal.msgSuccess(response.msg);
            this.getList();
          }
        });
      }
    },
    getRoadWayName(rwid) {
      let rwName = "";
      console.log("this.zkddOptions::::", this.zkddOptions);
      this.zkddOptions.forEach((e) => {
        if (e.id === rwid) {
          rwName = e.roadway;
          return;
        }
      });
      console.log("rwName", rwName);
      return rwName;
    },
    getBoreholeIdByNo(bhno) {
      let boreholeId = null;
      console.log("this.zkxhOptions::::", this.zkxhOptions);
      this.zkxhOptions.forEach((e) => {
        if (e.boreholeNo === bhno) {
          boreholeId = e.boreholeId;
          return;
        }
      });
      console.log("boreholeId", boreholeId);
      return boreholeId;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
        remark: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加tGasContentPressure";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTGasContentPressure(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改tGasContentPressure";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTGasContentPressure(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTGasContentPressure(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除tGasContentPressure编号为"' + ids + '"的数据项？')
        .then(function () {
          return delTGasContentPressure(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "/tGasContentPressure/export",
        {
          ...this.queryParams,
        },
        `tGasContentPressure_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
