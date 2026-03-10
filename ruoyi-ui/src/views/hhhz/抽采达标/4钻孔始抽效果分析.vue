<template>
  <div class="app-container">
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
      </el-form-item>
      <el-form-item label="钻孔序号" prop="zkbh">
        <el-select
          v-model="queryZuankongxuhao"
          multiple
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
      <el-form-item label="钻孔用途" prop="zkyt">
        <el-select v-model="queryZuankongyongtu" placeholder="请选择钻孔用途">
          <el-option
            v-for="item in zkytOptions"
            :key="item.id"
            :label="item.purposeBorehole"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
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
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <!-- <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                    v-hasPermi="['system:actual_borehole:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                    v-hasPermi="['system:actual_borehole:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                    @click="handleDelete" v-hasPermi="['system:actual_borehole:remove']">删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    v-hasPermi="['system:actual_borehole:export']">导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row> -->
    <!-- <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="date" label="日期" width="150">
            </el-table-column>
            <el-table-column label="配送信息">
                <el-table-column prop="name" label="姓名" width="120">
                </el-table-column>
                <el-table-column label="地址">
                    <el-table-column prop="province" label="省份" width="120">
                    </el-table-column>
                    <el-table-column prop="city" label="市区" width="120">
                    </el-table-column>
                    <el-table-column prop="address" label="地址" width="300">
                    </el-table-column>
                    <el-table-column prop="zip" label="邮编" width="120">
                    </el-table-column>
                </el-table-column>
            </el-table-column>
        </el-table> -->
    <el-row>
      <el-col :span="8" class="card-box">
        <el-row>
          <el-card>
            <!-- <div slot="header"><span>最新抽采浓度钻孔数量占比</span></div> -->
            <div class="el-table el-table--enable-row-hover el-table--medium">
              <div ref="zkndstats" style="height: 420px" />
            </div>
          </el-card>
        </el-row>
        <el-row>
          <el-card>
            <!-- <div slot="header"><span>最新抽采浓度钻孔数量占比</span></div> -->
            <div class="el-table el-table--enable-row-hover el-table--medium">
              <div ref="zkslstats" style="height: 420px" />
            </div>
          </el-card>
        </el-row>
      </el-col>
      <el-col :span="16" class="card-box">
        <el-table
          v-loading="loading"
          :data="actual_boreholeList"
          @selection-change="handleSelectionChange"
        >
          <!-- <el-table-column type="selection" width="55" align="center" /> -->
          <el-table-column label="序号" align="center" prop="id" />
          <!-- <el-table-column label="孔号" align="center" prop="holeId" /> -->
          <el-table-column
            label="钻孔瓦斯浓度"
            align="center"
            prop="gasConcentration"
          />
          <el-table-column
            label="钻孔压力"
            align="center"
            prop="negativePressure"
          />
          <el-table-column label="测量人" align="center" prop="measurer" />
          <el-table-column label="测量日期" align="center" prop="measureTime" />
          <el-table-column
            label="钻孔用途"
            align="center"
            prop="purposeBorehole"
          />
          <el-table-column
            label="实际夹角"
            align="center"
            prop="azimuthAngle"
          />
          <el-table-column
            label="实际倾角"
            align="center"
            prop="inclinationAngle"
          />
          <el-table-column label="实际方位角" align="center" prop="angle" />
          <el-table-column
            label="实际孔深"
            align="center"
            prop="boreholeLength"
          />
          <el-table-column label="见煤情况" align="center" prop="seeRockCoalCase" />
          <el-table-column label="见岩情况" align="center" prop="seerockcase" />
          <el-table-column label="导线点" align="center" prop="" />
          <el-table-column
            label="距导线点距离"
            align="center"
            prop="distanceToNavigationPoint"
          />
          <el-table-column
            label="封孔方式"
            align="center"
            prop="sealingMaterial"
          />
          <el-table-column
            label="冲孔煤量"
            align="center"
            prop="actualCoalAmountFlushed"
          />
          <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                                v-hasPermi="['system:actual_borehole:edit']">修改</el-button>
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                                v-hasPermi="['system:actual_borehole:remove']">删除</el-button>
                        </template>
                    </el-table-column> -->
        </el-table>
        <!-- <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.index"
          :limit.sync="queryParams.size"
          @pagination="getList"
        /> -->
      </el-col>
    </el-row>
    <view class="contain">
      <view class="left-charts">
        <view class="top-chart"></view>
        <view class="bottom-chart"></view>
      </view>
      <view class="right-table">
        <el-table
          v-loading="loading"
          :data="actual_boreholeList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="编号" align="center" prop="id" />
          <el-table-column label="孔号" align="center" prop="dymc" />
          <el-table-column label="方位角" align="center">
            <el-table-column label="设计方位角/°" align="center" prop="mj" />
            <el-table-column label="实际方位角/°" align="center" prop="mchd" />
          </el-table-column>
          <el-table-column label="倾角" align="center">
            <el-table-column label="设计倾角/°" align="center" prop="rz" />
            <el-table-column label="实际倾角/°" align="center" prop="wshl" />
          </el-table-column>
          <el-table-column label="见煤" align="center">
            <el-table-column label="设计见煤/m" align="center" prop="wsccl" />
            <el-table-column label="实际见煤/m" align="center" prop="zkzc" />
          </el-table-column>
          <el-table-column label="止煤" align="center">
            <el-table-column label="设计止煤/m" align="center" prop="ljccl" />
            <el-table-column label="实际止煤/m" align="center" prop="cywshl" />
          </el-table-column>
          <el-table-column label="方位角分析/°" align="center" prop="cywszl" />
          <el-table-column label="倾角分析/°" align="center" prop="cywszl" />
          <el-table-column label="绝对分析" align="center">
            <el-table-column
              label="见煤绝对分析/m"
              align="center"
              prop="cywszl"
            />
            <el-table-column
              label="止煤绝对分析/m"
              align="center"
              prop="cywszl"
            />
          </el-table-column>
          <el-table-column label="相对分析" align="center">
            <el-table-column
              label="见煤相对分析/%"
              align="center"
              prop="cywszl"
            />
            <el-table-column
              label="止煤相对分析/%"
              align="center"
              prop="cywszl"
            />
          </el-table-column>
          <el-table-column label="煤段护孔率/%" align="center" prop="cywszl" />
          <el-table-column label="冲孔比例/%" align="center" prop="cywszl" />
          <el-table-column label="囊袋压力/MPa" align="center" prop="cywszl" />
          <el-table-column
            label="中间段压力/MPa"
            align="center"
            prop="cywszl"
          />
          <el-table-column label="打钻动力/MPa" align="center" prop="cywszl" />
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:actual_borehole:edit']"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:actual_borehole:remove']"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </view>
    </view>
    <!-- 添加或修改实钻基础信息t_actual_borehole对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="组号" prop="groupNo">
          <el-input v-model="form.groupNo" placeholder="请输入组号" />
        </el-form-item>
        <el-form-item label="孔号" prop="boreholeNo">
          <el-input v-model="form.boreholeNo" placeholder="请输入孔号" />
        </el-form-item>
        <el-form-item label="所属巷道ID" prop="roadwayId">
          <el-input v-model="form.roadwayId" placeholder="请输入所属巷道ID" />
        </el-form-item>
        <el-form-item label="所属区段ID" prop="roadwayZoneId">
          <el-input
            v-model="form.roadwayZoneId"
            placeholder="请输入所属区段ID"
          />
        </el-form-item>
        <el-form-item label="钻场ID" prop="boreholesiteid">
          <el-input v-model="form.boreholesiteid" placeholder="请输入钻场ID" />
        </el-form-item>
        <el-form-item label="钻孔时间" prop="drillingDate">
          <el-date-picker
            clearable
            v-model="form.drillingDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择钻孔时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="钻孔类型ID" prop="categoryCode">
          <el-input
            v-model="form.categoryCode"
            placeholder="请输入钻孔类型ID"
          />
        </el-form-item>
        <el-form-item label="钻孔用途ID" prop="purposeBoreholeId">
          <el-input
            v-model="form.purposeBoreholeId"
            placeholder="请输入钻孔用途ID"
          />
        </el-form-item>
        <el-form-item label="钻机信息ID" prop="drillingid">
          <el-input v-model="form.drillingid" placeholder="请输入钻机信息ID" />
        </el-form-item>
        <el-form-item label="关联测点ID" prop="measurepointid">
          <el-input
            v-model="form.measurepointid"
            placeholder="请输入关联测点ID"
          />
        </el-form-item>
        <el-form-item label="孔径" prop="borediameter">
          <el-input v-model="form.borediameter" placeholder="请输入孔径" />
        </el-form-item>
        <el-form-item label="实际夹角" prop="azimuthAngle">
          <el-input v-model="form.azimuthAngle" placeholder="请输入实际夹角" />
        </el-form-item>
        <el-form-item label="实际倾角" prop="inclinationAngle">
          <el-input
            v-model="form.inclinationAngle"
            placeholder="请输入实际倾角"
          />
        </el-form-item>
        <el-form-item label="实际见岩深度" prop="depth1stRockSeamStart">
          <el-input
            v-model="form.depth1stRockSeamStart"
            placeholder="请输入实际见岩深度"
          />
        </el-form-item>
        <el-form-item label="实际见煤深度" prop="depth1stCoalSeamStart">
          <el-input
            v-model="form.depth1stCoalSeamStart"
            placeholder="请输入实际见煤深度"
          />
        </el-form-item>
        <el-form-item label="实际第二段见岩孔深" prop="depth2stRockSeamStart">
          <el-input
            v-model="form.depth2stRockSeamStart"
            placeholder="请输入实际第二段见岩孔深"
          />
        </el-form-item>
        <el-form-item label="实际第二段见煤孔深" prop="depth2stCoalSeamStart">
          <el-input
            v-model="form.depth2stCoalSeamStart"
            placeholder="请输入实际第二段见煤孔深"
          />
        </el-form-item>
        <el-form-item label="实际第三段见岩孔深" prop="depth3stRockSeamStart">
          <el-input
            v-model="form.depth3stRockSeamStart"
            placeholder="请输入实际第三段见岩孔深"
          />
        </el-form-item>
        <el-form-item label="实际第三段见煤孔深" prop="depth3stcoalseamstart">
          <el-input
            v-model="form.depth3stcoalseamstart"
            placeholder="请输入实际第三段见煤孔深"
          />
        </el-form-item>
        <el-form-item label="实际孔深" prop="boreholeLength">
          <el-input
            v-model="form.boreholeLength"
            placeholder="请输入实际孔深"
          />
        </el-form-item>
        <el-form-item label="导线点ID" prop="navigationPointId">
          <el-input
            v-model="form.navigationPointId"
            placeholder="请输入导线点ID"
          />
        </el-form-item>
        <el-form-item label="距导线点距离" prop="distanceToNavigationPoint">
          <el-input
            v-model="form.distanceToNavigationPoint"
            placeholder="请输入距导线点距离"
          />
        </el-form-item>
        <el-form-item label="抽采半径" prop="extractionRadius">
          <el-input
            v-model="form.extractionRadius"
            placeholder="请输入抽采半径"
          />
        </el-form-item>
        <el-form-item label="距巷道中线距离" prop="distanceToCentrallineTunnel">
          <el-input
            v-model="form.distanceToCentrallineTunnel"
            placeholder="请输入距巷道中线距离"
          />
        </el-form-item>
        <el-form-item label="距拱基线高度" prop="holeAltitude">
          <el-input
            v-model="form.holeAltitude"
            placeholder="请输入距拱基线高度"
          />
        </el-form-item>
        <el-form-item label="邻近孔号(组号+孔号列表)" prop="adjacentBoreholeNo">
          <el-input
            v-model="form.adjacentBoreholeNo"
            placeholder="请输入邻近孔号(组号+孔号列表)"
          />
        </el-form-item>
        <el-form-item label="与邻近孔距离" prop="distanceAdjacentBorehole">
          <el-input
            v-model="form.distanceAdjacentBorehole"
            placeholder="请输入与邻近孔距离"
          />
        </el-form-item>
        <el-form-item label="验收人员" prop="personChecking">
          <el-input
            v-model="form.personChecking"
            placeholder="请输入验收人员"
          />
        </el-form-item>
        <el-form-item label="验收日期" prop="checkingDate">
          <el-date-picker
            clearable
            v-model="form.checkingDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择验收日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="钻孔标识" prop="sign">
          <el-input v-model="form.sign" placeholder="请输入钻孔标识" />
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
<style>
.el-row {
  margin-bottom: 15px;
}
</style>
<script>
import {
  gZuankongyongtuList,
  gGroupIdList,
  gRoadWayList,
  gHoleNoList,
} from "@/api/hhhz/zuankong";
import { listActual } from "@/api/hhhz/4zkscxgfx";
import echarts from "echarts";
require("echarts/theme/shine");

export default {
  name: "Actual_borehole",
  data() {
    return {
      gasContentCount: [0, 0, 0, 0],
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
      // 遮罩层
      loading: false,
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
      // 实钻基础信息t_actual_borehole表格数据
      actual_boreholeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        low: 0,
        height: 100,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getRoadWayList();
    this.getZuankongyongtuList();
    this.getList();
  },
  mounted() {
    //this.initCharts();
  },
  methods: {
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
        await gHoleNoList({
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
      delete delete this.queryParams.roadwayId;
    },
    // 初始化钻孔组号
    initZkzh() {
      this.initZkxh();
      this.zkzhOptions = [];
      this.queryZuankongzuhao = [];
    },
    // 初始化钻孔序号
    initZkxh() {
      this.zkxhOptions = [];
      this.queryZuankongxuhao = [];
    },
    // 初始化所有
    initAll() {
      this.initZkdd();
      this.queryZuankongyongtu = [];
      this.queryCheckingdate = [];
    },
    initCharts() {
      this.zkndstats = echarts.init(this.$refs.zkndstats, "shine");
      this.zkndstats.setOption({
        title: {
          text: "最新抽采浓度钻孔数量占比",
          subtext: "饼状图",
          left: "left",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "horizontal",
          left: "center",
          top: "90%",
        },
        series: [
          {
            name: "瓦斯浓度：数量",
            type: "pie",
            radius: "50%",
            data: [
              {
                name: "50%<A",
                value: this.gasContentCount[3],
              },
              {
                name: "30%<A≤50%",
                value: this.gasContentCount[2],
              },
              {
                name: "10%<A≤30%",
                value: this.gasContentCount[1],
              },
              {
                name: "A≤10%",
                value: this.gasContentCount[0],
              },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      });

      this.zkslstats = echarts.init(this.$refs.zkslstats);
      this.zkslstats.setOption({
        title: {
          text: "最新抽采浓度钻孔数量",
          subtext: "折线图",
          left: "left",
        },
        xAxis: {
          type: "category",
          data: ["A<=10%", "10%<A<=30%", "30%<A<=50%", "50%<A"],
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: this.gasContentCount,
            type: "line",
          },
        ],
        label: {
          show: true,
        },
      });
    },
    /** 查询实钻基础信息t_actual_borehole列表 */
    getList() {
      this.loading = true;
      // 钻孔组号
      console.log("组号:", this.queryZuankongzuhao);
      if (this.queryZuankongzuhao) {
        this.queryParams.groupNo = this.queryZuankongzuhao;
      } else {
        delete this.queryParams.groupNo;
      }
      // 钻孔序号
      console.log("序号:", this.queryZuankongxuhao);
      if (this.queryZuankongxuhao && this.queryZuankongxuhao.length >= 1) {
        this.queryParams.boreholeNo = JSON.stringify(this.queryZuankongxuhao)
          .replace("[", "")
          .replace("]", "");
      } else {
        delete this.queryParams.boreholeNo;
      }
      // 钻孔用途
      console.log("钻孔用途:", this.queryZuankongyongtu);
      if (this.queryZuankongyongtu && this.queryZuankongyongtu.length >= 1) {
        this.queryParams.purposeBoreholeId = JSON.stringify(
          this.queryZuankongyongtu
        )
          .replace("[", "")
          .replace("]", "");
      } else {
        delete this.queryParams.purposeBoreholeId;
      }
      // 时间
      console.log("时间", this.queryCheckingdate);
      if (this.queryCheckingdate && this.queryCheckingdate.length >= 1) {
        this.queryParams.startdate = this.queryCheckingdate[0]
          .toLocaleString()
          .replaceAll("/", "-");
      } else {
        delete this.queryParams.startdate;
      }
      console.log("queryParams", this.queryParams);
      listActual(this.queryParams).then((response) => {
        let resp = response.rows;
        this.actual_list = [];
        let ysList = [];
        resp.forEach((e) => {
          e.holeId = e.roadway + "-" + e.groupNo + "-" + e.boreholeNo;
          if (e.gasConcentration <= 10) {
            this.gasContentCount[0] += 1;
          } else if (10 < e.gasConcentration && e.gasConcentration <= 30) {
            this.gasContentCount[1] += 1;
          } else if (30 < e.gasConcentration && e.gasConcentration <= 50) {
            this.gasContentCount[2] += 1;
          } else if (50 < e.gasConcentration) {
            this.gasContentCount[3] += 1;
          }
          ysList.push(e);
        });
        this.actual_boreholeList = ysList;
        this.total = response.total;
        this.loading = false;
        this.initCharts();
      });
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
        groupNo: null,
        boreholeNo: null,
        roadwayId: null,
        roadwayZoneId: null,
        boreholesiteid: null,
        drillingDate: null,
        categoryCode: null,
        purposeBoreholeId: null,
        drillingid: null,
        measurepointid: null,
        borediameter: null,
        azimuthAngle: null,
        inclinationAngle: null,
        depth1stRockSeamStart: null,
        depth1stCoalSeamStart: null,
        depth2stRockSeamStart: null,
        depth2stCoalSeamStart: null,
        depth3stRockSeamStart: null,
        depth3stcoalseamstart: null,
        boreholeLength: null,
        navigationPointId: null,
        distanceToNavigationPoint: null,
        extractionRadius: null,
        distanceToCentrallineTunnel: null,
        holeAltitude: null,
        adjacentBoreholeNo: null,
        distanceAdjacentBorehole: null,
        personChecking: null,
        checkingDate: null,
        sign: null,
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
      /* this.queryParams.pageNum = 1; */
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        low: 0,
        height: 100,
      };
      this.initAll();
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
      this.title = "添加实钻基础信息t_actual_borehole";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getActual_borehole(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改实钻基础信息t_actual_borehole";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateActual_borehole(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addActual_borehole(this.form).then((response) => {
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
        .confirm(
          '是否确认删除实钻基础信息t_actual_borehole编号为"' +
            ids +
            '"的数据项？'
        )
        .then(function () {
          return delActual_borehole(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      let _this = this;
      this.download(
        "tActualBorehole/export",
        _this.actual_boreholeList,
        `actual_borehole_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
