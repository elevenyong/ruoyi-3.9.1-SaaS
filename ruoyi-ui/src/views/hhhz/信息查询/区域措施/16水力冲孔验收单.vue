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
            :key="item"
            :label="item"
            :value="item"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!--<el-form-item label="钻孔用途" prop="zkyt">
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
      </el-form-item> -->
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
                    v-hasPermi="['/:tDrainageParameter:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                    v-hasPermi="['/:tDrainageParameter:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                    @click="handleDelete" v-hasPermi="['/:tDrainageParameter:remove']">删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    v-hasPermi="['/:tDrainageParameter:export']">导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row> -->
    <div class="pdf-view">
      <div id="pdf-content"></div>
    </div>
  </div>
</template>

<script>
import { gShuilichongkongtaizhang } from "@/api/hhhz/19slcktz";
import {
  gZuankongyongtuList,
  gGroupIdList,
  gRoadWayList,
  gHoleNoList,
} from "@/api/hhhz/zuankong";
import { jsPDF } from "jspdf";
import { autoTable } from "jspdf-autotable";
import PDFObject from "pdfobject";
import "@/utils/simhei-normal";
//const doc = new jsPDF()

export default {
  name: "TDrainageParameter",
  data() {
    return {
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
      boreholeList: [],
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
      queryParams: {},
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      doc: null,
    };
  },
  created() {
    this.getRoadWayList();
    this.getZuankongyongtuList();
    //this.getList();
  },
  mounted() {
    PDFObject.embed(this.createPDF(), "#pdf-content");
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
    createPDF() {
      this.doc = new jsPDF("landscape");
      let fonts = this.doc.getFontList();
      this.doc.setFont("simhei", "normal");
      this.doc.setProperties({
        title: "瓦斯抽采钻孔水力冲孔验收单",
        subject: "",
        author: "hhhz",
        keywords: "瓦斯",
        creator: "hhhz",
      });
      let body = [];
      let row = [
        {
          colSpan: 9,
          content: "瓦斯抽采钻孔水力冲孔验收单",
          styles: { valign: "middle", halign: "center", fontSize: 20 },
        },
      ];
      let row0 = [];
      row0[0] = {
        colSpan: 1,
        content: "冲孔日期",
        styles: { valign: "middle", halign: "center" },
      };
      row0[1] = {
        colSpan: 3,
        content: "2022年8月1日 早班",
        styles: { valign: "middle", halign: "left" },
      };
      row0[2] = {
        colSpan: 2,
        content: "钻孔地点",
        styles: { valign: "middle", halign: "center" },
      };
      row0[3] = {
        colSpan: 3,
        content: "2200下巷底板抽放巷",
        styles: { valign: "middle", halign: "left" },
      };
      let row1 = [];
      row1[0] = {
        colSpan: 1,
        rowSpan: 8,
        content: "验收情况",
        styles: { valign: "middle", halign: "center" },
      };
      row1[1] = {
        colSpan: 1,
        content: "钻场编号",
        styles: { valign: "middle", halign: "center" },
      };
      row1[2] = {
        colSpan: 1,
        content: "孔号",
        styles: { valign: "middle", halign: "center" },
      };
      row1[3] = {
        colSpan: 1,
        content: "孔径(mm)",
        styles: { valign: "middle", halign: "center" },
      };
      row1[4] = {
        colSpan: 1,
        content: "孔深(m)",
        styles: { valign: "middle", halign: "center" },
      };
      row1[5] = {
        colSpan: 1,
        content: "钻孔夹角(°)",
        styles: { valign: "middle", halign: "center" },
      };
      row1[6] = {
        colSpan: 1,
        content: "钻孔倾角(°)",
        styles: { valign: "middle", halign: "center" },
      };
      row1[7] = {
        colSpan: 1,
        content: "冲孔开始时间",
        styles: { valign: "middle", halign: "center" },
      };
      row1[8] = {
        colSpan: 1,
        content: "冲孔结束时间",
        styles: { valign: "middle", halign: "center" },
      };

      let row2 = [];
      row2[0] = {
        colSpan: 1,
        content: "1",
        styles: { valign: "middle", halign: "center" },
      };
      row2[1] = {
        colSpan: 1,
        content: "67-1",
        styles: { valign: "middle", halign: "center" },
      };
      row2[2] = {
        colSpan: 1,
        content: "94",
        styles: { valign: "middle", halign: "center" },
      };
      row2[3] = {
        colSpan: 1,
        content: "54.5",
        styles: { valign: "middle", halign: "center" },
      };
      row2[4] = {
        colSpan: 1,
        content: "90",
        styles: { valign: "middle", halign: "center" },
      };
      row2[5] = {
        colSpan: 1,
        content: "28",
        styles: { valign: "middle", halign: "center" },
      };
      row2[6] = {
        colSpan: 1,
        content: "07:09",
        styles: { valign: "middle", halign: "center" },
      };
      row2[7] = {
        colSpan: 1,
        content: "10:37",
        styles: { valign: "middle", halign: "center" },
      };
      let row3 = [
        {
          colSpan: 1,
          content: "累计冲孔时间(min)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔压力(MPa)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔前孔口瓦斯(%)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔后孔口瓦斯(%)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "见煤见岩情况",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔段",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔段长度(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲出煤量(m3)",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row4 = [
        {
          colSpan: 1,
          content: "120",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "15",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "0.01",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "0.03",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "暂无",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "42.4m-45.8m",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "3.4",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "3.6",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row5 = [
        {
          colSpan: 1,
          content: "冲出煤量(t)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "冲孔期间瓦斯变化情况（以传感器数据为准）",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "验收时间",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row6 = [
        {
          colSpan: 1,
          content: "3.6",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "0%~0%",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔过程中现场异常标注：",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "0",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row7 = [
        {
          colSpan: 1,
          content: "施工人员：",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "张三",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "验收人员：",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "李四",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "跟班队干：",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "王五",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row8 = [
        {
          colSpan: 1,
          content: "备注：",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 7,
          content: "冲出煤量(t)=冲出煤量(3.6)m3*(1)t/m3=(3.6)t",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      body.push(row);
      body.push(row0);
      body.push(row1);
      body.push(row2);
      body.push(row3);
      body.push(row4);
      body.push(row5);
      body.push(row6);
      body.push(row7);
      body.push(row8);

      this.doc.autoTable({
        startY: 60,
        body: body,
        theme: "grid",
        styles: {
          font: "simhei",
          halign: "center",
          lineColor: [0, 0, 0],
          lineWidth: 0.2,
        },
      });
      return this.doc.output("datauristring");
    },

    createPDFWithValue(data) {
      console.log("PDF", data);
      this.doc = new jsPDF("landscape");
      let fonts = this.doc.getFontList();
      this.doc.setFont("simhei", "normal");
      this.doc.setProperties({
        title: "瓦斯抽采钻孔水力冲孔验收单",
        subject: "",
        author: "hhhz",
        keywords: "瓦斯",
        creator: "hhhz",
      });
      let body = [];
      let row = [
        {
          colSpan: 9,
          content: "瓦斯抽采钻孔水力冲孔验收单",
          styles: { valign: "middle", halign: "center", fontSize: 20 },
        },
      ];
      let row0 = [];
      row0[0] = {
        colSpan: 1,
        content: "冲孔日期",
        styles: { valign: "middle", halign: "center" },
      };
      row0[1] = {
        colSpan: 3,
        content:
          data.timeBeginFlushing != null
            ? data.timeBeginFlushing.substring(0, 4) +
              "年" +
              data.timeBeginFlushing.substring(5, 7) +
              "月" +
              data.timeBeginFlushing.substring(8, 10) +
              "日" +
              " 早班"
            : "2099年12月31日 早班",
        styles: { valign: "middle", halign: "left" },
      };
      row0[2] = {
        colSpan: 2,
        content: "钻孔地点",
        styles: { valign: "middle", halign: "center" },
      };
      row0[3] = {
        colSpan: 3,
        content: data.roadway,
        styles: { valign: "middle", halign: "left" },
      };
      let row1 = [];
      row1[0] = {
        colSpan: 1,
        rowSpan: 8,
        content: "验收情况",
        styles: { valign: "middle", halign: "center" },
      };
      row1[1] = {
        colSpan: 1,
        content: "组号",
        styles: { valign: "middle", halign: "center" },
      };
      row1[2] = {
        colSpan: 1,
        content: "孔号",
        styles: { cellWidth: 35, valign: "middle", halign: "center" },
      };
      row1[3] = {
        colSpan: 1,
        content: "孔径(mm)",
        styles: { valign: "middle", halign: "center" },
      };
      row1[4] = {
        colSpan: 1,
        content: "孔深(m)",
        styles: { valign: "middle", halign: "center" },
      };
      row1[5] = {
        colSpan: 1,
        content: "钻孔夹角(°)",
        styles: { valign: "middle", halign: "center" },
      };
      row1[6] = {
        colSpan: 1,
        content: "钻孔倾角(°)",
        styles: { cellWidth: 30, valign: "middle", halign: "center" },
      };
      row1[7] = {
        colSpan: 1,
        content: "冲孔开始时间",
        styles: { valign: "middle", halign: "center" },
      };
      row1[8] = {
        colSpan: 1,
        content: "冲孔结束时间",
        styles: { valign: "middle", halign: "center" },
      };

      let row2 = [];
      row2[0] = {
        colSpan: 1,
        content: data.groupNo,
        styles: { valign: "middle", halign: "center" },
      };
      row2[1] = {
        colSpan: 1,
        content: data.holeId,
        styles: { valign: "middle", halign: "center" },
      };
      row2[2] = {
        colSpan: 1,
        content: "94",
        styles: { valign: "middle", halign: "center" },
      };
      row2[3] = {
        colSpan: 1,
        content: data.boreholeLength.toFixed(2),
        styles: { valign: "middle", halign: "center" },
      };
      row2[4] = {
        colSpan: 1,
        content: data.inclinationAngle,
        styles: { valign: "middle", halign: "center" },
      };
      row2[5] = {
        colSpan: 1,
        content: data.inclinationAngle,
        styles: { valign: "middle", halign: "center" },
      };
      row2[6] = {
        colSpan: 1,
        content:
          data.timeBeginFlushing != null
            ? (data.timeBeginFlushing + "").substring(10)
            : "",
        styles: { valign: "middle", halign: "center" },
      };
      row2[7] = {
        colSpan: 1,
        content:
          data.timeEndFlushing != null
            ? (data.timeEndFlushing + "").substring(10)
            : "",
        styles: { valign: "middle", halign: "center" },
      };
      let row3 = [
        {
          colSpan: 1,
          content: "累计冲孔时间(min)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔压力(MPa)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔前孔口瓦斯(%)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔后孔口瓦斯(%)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "见煤见岩情况",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔段",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔段长度(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲出煤量(m3)",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row4 = [
        {
          colSpan: 1,
          content: "120",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "15",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "0.01",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "0.03",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "暂无",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content:
            data.punchingParagraph1 + "m-" + data.punchingParagraph2 + "m",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "3.4",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "3.6",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row5 = [
        {
          colSpan: 1,
          content: "冲出煤量(t)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "冲孔期间瓦斯变化情况（以传感器数据为准）",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "验收时间",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row6 = [
        {
          colSpan: 1,
          content: data.actualCoalAmountFlushed,
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "0%~0%",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "冲孔过程中现场异常标注：",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "0",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row7 = [
        {
          colSpan: 1,
          content: "施工人员：",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: data.personConstruction,
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "验收人员：",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "李四",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "跟班队干：",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "王五",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row8 = [
        {
          colSpan: 1,
          content: "备注：",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 7,
          content: "冲出煤量(t)=冲出煤量(3.6)m3*(1)t/m3=(3.6)t",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      body.push(row);
      body.push(row0);
      body.push(row1);
      body.push(row2);
      body.push(row3);
      body.push(row4);
      body.push(row5);
      body.push(row6);
      body.push(row7);
      body.push(row8);

      this.doc.autoTable({
        startY: 60,
        body: body,
        theme: "grid",
        styles: {
          font: "simhei",
          halign: "center",
          lineColor: [0, 0, 0],
          lineWidth: 0.2,
        },
      });
      return this.doc.output("datauristring");
    },
    createHeaders(keys) {
      var result = [];
      for (var i = 0; i < keys.length; i += 1) {
        result.push({
          id: keys[i],
          name: keys[i],
          prompt: keys[i],
          width: 40,
          align: "center",
          padding: 0,
        });
      }
      return result;
    },
    generateData(amount) {
      var result = [];
      var data = {
        泵站: "26瓦斯抽放泵站",
        "抽采负压（kPa）": "41.07",
        抽采时间: "24:00",
        "抽采混量（m³）": "22941.96",
        "平均浓度（%）": "3.08",
        "抽采纯量（m³）": "705.94",
      };
      for (var i = 0; i < amount; i += 1) {
        //data.id = (i + 1).toString();
        result.push(Object.assign({}, data));
      }
      return result;
    },
    /** 查询tDrainageParameter列表 */
    getList() {
      this.loading = true;
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
      gShuilichongkongtaizhang(this.queryParams).then((response) => {
        console.log(response);
        this.total = response.total;
        if (response.total >= 1) {
          let resp = response.rows;
          this.actual_list = [];
          let ysList = [];
          resp.forEach((e) => {
            e.holeId = e.roadway + "-" + e.groupNo + "-" + e.boreholeNo;
            ysList.push(e);
          });
          this.boreholeList = ysList;
          PDFObject.embed(
            this.createPDFWithValue(this.boreholeList[0]),
            "#pdf-content"
          );
        }
        this.loading = false;
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
        boreholeId: null,
        datedrainagestart: null,
        concentrationdrainagestart: null,
        concentrationbore: null,
        vacuumbore: null,
        estimatedtimedrainagedone: null,
        measureDate: null,
        measureperson: null,
        tabulator: null,
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
      this.title = "添加tDrainageParameter";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTDrainageParameter(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改tDrainageParameter";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTDrainageParameter(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTDrainageParameter(this.form).then((response) => {
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
        .confirm('是否确认删除tDrainageParameter编号为"' + ids + '"的数据项？')
        .then(function () {
          return delTDrainageParameter(ids);
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
        "//tDrainageParameter/export",
        {
          ...this.queryParams,
        },
        `tDrainageParameter_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
<style>
#pdf-content {
  width: 80vw;
  height: 80vh;
}
</style>
