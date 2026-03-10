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
        <el-button icon="el-icon-download" size="mini" @click="handleExport"
        >导出
        </el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="tDrainageParameterList">
      <el-table-column label="巷道名称" align="center" prop="roadway" />
      <el-table-column label="钻孔位置" align="center" prop="drillingLocation" />
      <el-table-column label="组号" align="center" prop="groupNo" />
      <el-table-column label="孔号" align="center" prop="boreholeNo" />
      <el-table-column label="钻孔编号" align="center" prop="boreholeCode" />
      <el-table-column label="单孔浓度" align="center" prop="gasConcentration" />
      <el-table-column label="单孔负压" align="center" prop="negativePressure" />
      <el-table-column label="始抽浓度" align="center" prop="gasConcentration" />
      <el-table-column label="始抽负压" align="center" prop="negativePressure" />
      <el-table-column label="预计抽采完成所用的天数" align="center" prop="estimatedTimeDrainageDone" />
      <el-table-column label="测量日期" align="center" prop="measureDate" />
      <el-table-column label="是否为始抽值" align="center" prop="beginningcon" />
      <el-table-column label="始抽浓度测量日期" align="center" prop="beginningconDate" />
      <el-table-column label="测量人" align="center" prop="measurer" />
      <el-table-column label="制表人" align="center" prop="tabulator" />
      <el-table-column label="实际孔深" align="center" prop="boreholeLength" />

<!--      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="钻孔地点" align="center" prop="roadway" />
      <el-table-column label="组号" align="center" prop="groupNo" />
      <el-table-column label="孔号" align="center" prop="boreholeNo" />
      <el-table-column label="孔深(m)" align="center" prop="boreholeLength" />
      <el-table-column
        label="始抽时间"
        align="center"
        prop="datedrainagestart"
      />
      <el-table-column
        label="始抽浓度(%)"
        align="center"
        prop="concentrationdrainagestart"
      />
      <el-table-column
        label="单孔浓度(%)"
        align="center"
        prop="concentrationbore"
      />
      <el-table-column label="单孔负压(KPa)" align="center" prop="vacuumbore" />
      <el-table-column label="测量人" align="center" prop="tabulator" />
      <el-table-column label="观测日期" align="center" prop="measureDate" />
      <el-table-column label="制表人" align="center" prop="" />-->
      <!-- <el-table-column
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
            v-hasPermi="['/:tDrainageParameter:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['/:tDrainageParameter:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {
  gZuankongyongtuList,
  gGroupIdList,
  gRoadWayList,
  gHoleNoList,
} from "@/api/hhhz/zuankong";
import { listTDrainageParameter } from "@/api/hhhz/zhouqijiliang";
export default {
  name: "Zuankongzhouqijiliangcanshu12",
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
      // tDrainageParameter表格数据
      tDrainageParameterList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
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
          console.log("gZuankongkonghaoList", response);
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
    /** 查询tDrainageParameter列表 */
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
      listTDrainageParameter(this.queryParams).then((response) => {
        this.tDrainageParameterList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        groupNo: [],
      };
      this.initAll();
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      let _this = this;
      this.download(
        "tDrainageParameter/export",
        {},
        `周期计量参数_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
