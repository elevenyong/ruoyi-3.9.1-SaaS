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

    <el-row :gutter="10" class="myrow">
      <el-col :span="8">
        <el-card class="mycard">
          <el-table :data="actual_list" class="mytable">
            <el-table-column label="孔号" align="center" prop="holeId" />
            <el-table-column
              label="验收日期"
              align="center"
              prop="checkingDate"
            />
            <el-table-column
              label="操作"
              align="center"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-tickets"
                  @click="createYSD(scope.row)"
                >
                  查看验收单</el-button
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
        </el-card>
      </el-col>
      <el-col :span="14" class="mycard">
        <div class="pdf-view">
          <div id="pdf-content"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<style >
/* .el-col {
    height: 80vh;
} */
.myrow {
  height: 80vh;
  width: 100%;
}

.mycard {
  height: 100%;
}

.mytable {
  height: 80vh;
  overflow-y: scroll;
}

.pdf-view {
  width: 100%;
  height: 80vh;
}

#pdf-content {
  height: 80vh;
  width: 100%;
}
</style>
<script>
import {
  gZuankongyongtuList,
  gGroupIdList,
  gRoadWayList,
  gHoleNoList,
} from "@/api/hhhz/zuankong";
import { gZuankongyuanshiyanshoudan } from "@/api/hhhz/13zkysysd";
import { jsPDF } from "jspdf";
import { autoTable } from "jspdf-autotable";
import PDFObject from "pdfobject";
import "@/utils/simhei-normal";

export default {
  name: "Actual_borehole",
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
      actual_list: [],
      // 实钻基础信息t_actual_borehole表格数据
      actual_boreholeList: [],
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
  mounted() {
    PDFObject.embed(this.createPDFEmpty(), "#pdf-content");
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
    createYSD(data) {
      PDFObject.embed(this.createPDFWithValue(data), "#pdf-content");
    },
    createPDFWithValue(data) {
      console.log("PDFData", data);
      this.doc = new jsPDF("landscape");
      let fonts = this.doc.getFontList();
      this.doc.setFont("simhei", "normal");
      this.doc.setProperties({
        title: "瓦斯抽采穿层钻孔原始验收单",
        subject: "",
        author: "hhhz",
        keywords: "瓦斯",
        creator: "hhhz",
      });
      let body = [];
      let row = [
        {
          colSpan: 9,
          content: "瓦斯抽采穿层钻孔原始验收单",
          styles: { valign: "middle", halign: "center", fontSize: 20 },
        },
      ];
      let row0 = [];
      row0[0] = {
        colSpan: 2,
        content: "验收日期",
        styles: { valign: "middle", halign: "center" },
      };
      row0[1] = {
        colSpan: 2,
        //content: "2022年08月01日 早班",
        content:
          data.checkingDate != null
            ? data.checkingDate.substring(0, 4) +
              "年" +
              data.checkingDate.substring(5, 7) +
              "月" +
              data.checkingDate.substring(8, 10) +
              "日" +
              " 早班"
            : "2099年12月31日 早班",
        styles: { valign: "middle", halign: "left" },
      };
      row0[2] = {
        colSpan: 1,
        content: "验收地点",
        styles: { valign: "middle", halign: "center" },
      };
      row0[3] = {
        colSpan: 2,
        //content: "2200下巷底板抽放巷",
        content: data.roadway,
        styles: { valign: "middle", halign: "left" },
      };
      row0[4] = {
        colSpan: 2,
        content: "钻孔位置示意图",
        styles: { valign: "middle", halign: "center" },
      };
      let row1 = [];
      row1[0] = {
        colSpan: 1,
        rowSpan: 9,
        content: "验收情况",
        styles: { cellWidth: 10, valign: "middle", halign: "center" },
      };
      row1[1] = {
        colSpan: 1,
        content: "钻场编号",
        styles: { cellWidth: 25, valign: "middle", halign: "center" },
      };
      row1[2] = {
        colSpan: 1,
        content: "孔号",
        styles: { cellWidth: 30, valign: "middle", halign: "center" },
      };
      row1[3] = {
        colSpan: 1,
        content: "孔径(mm)",
        styles: { cellWidth: 30, valign: "middle", halign: "center" },
      };
      row1[4] = {
        colSpan: 1,
        content: "孔深(m)",
        styles: { cellWidth: 20, valign: "middle", halign: "center" },
      };
      row1[5] = {
        colSpan: 1,
        content: "钻孔夹角(°)",
        styles: { cellWidth: 25, valign: "middle", halign: "center" },
      };
      row1[6] = {
        colSpan: 1,
        content: "钻孔倾角(°)",
        styles: { cellWidth: 25, valign: "middle", halign: "center" },
      };
      row1[7] = {
        rowSpan: 10,
        colSpan: 2,
        content: "",
        styles: { cellWidth: 25, valign: "middle", halign: "center" },
      };
      let row2 = [];
      row2[0] = {
        colSpan: 1,
        //content: "3#",
        content: data.groupNo,
        styles: { valign: "middle", halign: "center" },
      };
      row2[1] = {
        colSpan: 1,
        //content: "123-10",
        content: data.holeId,
        styles: { valign: "middle", halign: "center" },
      };
      row2[2] = {
        colSpan: 1,
        //content: "94",
        content: data.borediameter,
        styles: { valign: "middle", halign: "center" },
      };
      row2[3] = {
        colSpan: 1,
        //content: "27.8",
        content: data.boreholeLength.toFixed(2),
        styles: { valign: "middle", halign: "center" },
      };
      row2[4] = {
        colSpan: 1,
        //content: "-90",
        content: data.azimuthAngle,
        styles: { valign: "middle", halign: "center" },
      };
      row2[5] = {
        colSpan: 1,
        //content: "50",
        content: data.inclinationAngle,
        styles: { valign: "middle", halign: "center" },
      };
      let row3 = [
        {
          colSpan: 1,
          content: "距离导线点距离(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "距巷道(硐室)中线距离(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "距拱基线上(下)高度(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "临近孔号",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "与临近孔距离(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "拔钻后孔口瓦斯浓度(%)",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row4 = [
        {
          colSpan: 1,
          //content: "2D3点内3.6",
          content:
            (data.navigationPointName != null
              ? data.navigationPointName
              : "导线点") +
            "-" +
            data.distanceToNavigationPoint,
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          //content: "2.5",
          content: data.distanceToCentrallineTunnel.toFixed(2),
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          //content: "1.7",
          content: data.heightArchBaseline.toFixed(2),
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          //content: "123-9",
          content: data.adjacentBoreholeNo.split(",")[0],
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          //content: "1.2",
          content:
            data.distanceAdjacentBorehole != null
              ? data.distanceAdjacentBorehole.toFixed(2)
              : 0,
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          //content: "0",
          content: data.gasConcentration,
          styles: { valign: "middle", halign: "center" },
        } /* , {
                    colSpan: 1,
                    content: "3.4",
                    styles: { valign: 'middle', halign: 'center' },
                }, {
                    colSpan: 1,
                    content: "3.6",
                    styles: { valign: 'middle', halign: 'center' },
                } */,
      ];
      let row5 = [
        {
          colSpan: 1,
          content: "拔钻后孔口CO(ppm)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 2,
          content: "钻孔见煤(岩)情况",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "打钻过程中异常现象标注",
          styles: { valign: "middle", halign: "center" },
        } /* , {
                    colSpan: 3,
                    content: "",
                    styles: { valign: 'middle', halign: 'center' },
                } */,
      ];
      let row6 = [
        {
          colSpan: 1,
          //content: "0",
          content:
            data.pulledcoconcentration != null ? data.pulledcoconcentration : 0,
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 2,
          //content: "22.7-26.3煤",
          content:
            (data.depth1stCoalSeamStart != null &&
            data.depth1stCoalSeamEnd != null
              ? data.depth1stCoalSeamStart.toFixed(2) +
                "-" +
                data.depth1stCoalSeamEnd.toFixed(2) +
                "煤;"
              : "") +
            (data.depth2stCoalSeamStart != null &&
            data.depth2stCoalSeamEnd != null
              ? data.depth2stCoalSeamStart.toFixed(2) +
                "-" +
                data.depth2stCoalSeamEnd.toFixed(2) +
                "煤;"
              : ""),
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "打钻过程中无顶钻塌孔等异常现象",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row7 = [
        {
          colSpan: 1,
          rowSpan: 5,
          content: "设计对比",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 2,
          content: "钻孔类别",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "设计孔径(mm)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "设计见煤孔深(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "设计夹角(°)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "设计倾角(°)",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row8 = [
        {
          colSpan: 2,
          //content: "穿层钻孔",
          content:
            data.boreholeCategory !== null ? data.boreholeCategory : "穿层钻孔",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: data.desborediameter,
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: data.desCoalSeamstart.toFixed(2),
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: data.desAzimuthAngle,
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: data.desInclinationAngle,
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 2,
          rowSpan: 4,
          content: "备注: \n  拔钻时间：15:13-18:27\n  下管时间：18:45-18:52",
          styles: { valign: "top", halign: "left" },
        },
      ];
      let row9 = [
        {
          colSpan: 6,
          content: "验收结果(是否合格,有效进尺m)",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row10 = [
        {
          colSpan: 6,
          content: "合格,有效进尺：" + data.validFootage.toFixed(2),
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row11 = [
        {
          colSpan: 4,
          content: "施工人员(签名)：",
          styles: { valign: "middle", halign: "left" },
        },
        {
          colSpan: 4,
          content: "验收人员(签名)：",
          styles: { valign: "middle", halign: "left" },
        },
      ];
      let row22 = [];
      let row44 = [];
      let row66 = [
        {
          colSpan: 1,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 2,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row88 = [
        {
          colSpan: 2,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      body.push(row);
      body.push(row0);
      body.push(row1);
      body.push(row2);
      body.push(row22);
      body.push(row3);
      body.push(row4);
      body.push(row44);
      body.push(row5);
      body.push(row6);
      body.push(row66);
      body.push(row7);
      body.push(row8);
      body.push(row88);
      body.push(row9);
      body.push(row10);
      body.push(row11);

      this.doc.autoTable({
        startY: 20,
        body: body,
        theme: "grid",
        styles: {
          font: "simhei",
          halign: "center",
          lineColor: [0, 0, 0],
          lineWidth: 0.2,
        },
      });
      this.doc.setLineWidth(0.2);
      this.doc.setDrawColor(0, 0, 0);
      this.doc.setFontSize(8);
      this.doc.line(200, 60, 260, 60);
      this.doc.text(200, 59, "切眼方向");
      this.doc.line(200, 80, 260, 80);
      this.doc.text(200, 79, "中心线");
      this.doc.circle(225, 70, 1);
      //this.doc.text(225, 68, "123-10");
      this.doc.text(225, 68, data.holeId);
      this.doc.circle(255, 70, 1);
      //this.doc.text(255, 68, "2D3点内");
      this.doc.text(
        255,
        68,
        data.navigationPointName != null ? data.navigationPointName : "导向点"
      );
      this.doc.line(225, 80, 225, 71);
      this.doc.text(226, 76, "1.7m");
      //this.doc.text(226, 76, data.distanceToCentrallineTunnel);
      this.doc.line(200, 100, 260, 100);
      this.doc.line(225, 100, 225, 105);
      this.doc.line(255, 100, 255, 105);
      this.doc.line(225, 103, 255, 103);
      //this.doc.text(240, 106, "3.6m");
      this.doc.text(240, 106, data.distanceToNavigationPoint + "m");
      console.log("return");
      return this.doc.output("datauristring");
    },
    createPDFEmpty() {
      this.doc = new jsPDF("landscape");
      let fonts = this.doc.getFontList();
      this.doc.setFont("simhei", "normal");
      console.log("输出空PDF表单");
      this.doc.setProperties({
        title: "请从列表中选择钻孔进行查看",
        subject: "",
        author: "hhhz",
        keywords: "瓦斯",
        creator: "hhhz",
      });
      let body = [];
      let row = [
        {
          colSpan: 9,
          content: "瓦斯抽采穿层钻孔原始验收单",
          styles: { valign: "middle", halign: "center", fontSize: 20 },
        },
      ];
      let row0 = [];
      row0[0] = {
        colSpan: 2,
        content: "验收日期",
        styles: { valign: "middle", halign: "center" },
      };
      row0[1] = {
        colSpan: 2,
        //content: "2022年08月01日 早班",
        content: "2099年12月31日 早班",
        styles: { valign: "middle", halign: "left" },
      };
      row0[2] = {
        colSpan: 1,
        content: "验收地点",
        styles: { valign: "middle", halign: "center" },
      };
      row0[3] = {
        colSpan: 2,
        //content: "2200下巷底板抽放巷",
        content: "",
        styles: { valign: "middle", halign: "left" },
      };
      row0[4] = {
        colSpan: 2,
        content: "钻孔位置示意图",
        styles: { valign: "middle", halign: "center" },
      };
      let row1 = [];
      row1[0] = {
        colSpan: 1,
        rowSpan: 9,
        content: "验收情况",
        styles: { cellWidth: 10, valign: "middle", halign: "center" },
      };
      row1[1] = {
        colSpan: 1,
        content: "钻场编号",
        styles: { cellWidth: 25, valign: "middle", halign: "center" },
      };
      row1[2] = {
        colSpan: 1,
        content: "孔号",
        styles: { cellWidth: 30, valign: "middle", halign: "center" },
      };
      row1[3] = {
        colSpan: 1,
        content: "孔径(mm)",
        styles: { cellWidth: 30, valign: "middle", halign: "center" },
      };
      row1[4] = {
        colSpan: 1,
        content: "孔深(m)",
        styles: { cellWidth: 20, valign: "middle", halign: "center" },
      };
      row1[5] = {
        colSpan: 1,
        content: "钻孔夹角(°)",
        styles: { cellWidth: 25, valign: "middle", halign: "center" },
      };
      row1[6] = {
        colSpan: 1,
        content: "钻孔倾角(°)",
        styles: { cellWidth: 25, valign: "middle", halign: "center" },
      };
      row1[7] = {
        rowSpan: 10,
        colSpan: 2,
        content: "",
        styles: { cellWidth: 25, valign: "middle", halign: "center" },
      };
      let row2 = [];
      row2[0] = {
        colSpan: 1,
        //content: "3#",
        content: "",
        styles: { valign: "middle", halign: "center" },
      };
      row2[1] = {
        colSpan: 1,
        //content: "123-10",
        content: "",
        styles: { valign: "middle", halign: "center" },
      };
      row2[2] = {
        colSpan: 1,
        //content: "94",
        content: "",
        styles: { valign: "middle", halign: "center" },
      };
      row2[3] = {
        colSpan: 1,
        //content: "27.8",
        content: "",
        styles: { valign: "middle", halign: "center" },
      };
      row2[4] = {
        colSpan: 1,
        //content: "-90",
        content: "",
        styles: { valign: "middle", halign: "center" },
      };
      row2[5] = {
        colSpan: 1,
        //content: "50",
        content: "",
        styles: { valign: "middle", halign: "center" },
      };
      let row3 = [
        {
          colSpan: 1,
          content: "距离导线点距离(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "距巷道(硐室)中线距离(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "距拱基线上(下)高度(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "临近孔号",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "与临近孔距离(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "拔钻后孔口瓦斯浓度(%)",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row4 = [
        {
          colSpan: 1,
          //content: "2D3点内3.6",
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          //content: "2.5",
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          //content: "1.7",
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          //content: "123-9",
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          //content: "1.2",
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          //content: "0",
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row5 = [
        {
          colSpan: 1,
          content: "拔钻后孔口CO(ppm)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 2,
          content: "钻孔见煤(岩)情况",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "打钻过程中异常现象标注",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row6 = [
        {
          colSpan: 1,
          //content: "0",
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 2,
          //content: "22.7-26.3煤",
          content: "0-0煤",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "打钻过程中无顶钻塌孔等异常现象",
          styles: { valign: "middle", halign: "center" },
        } /* , {
                    colSpan: 3,
                    content: "0",
                    styles: { valign: 'middle', halign: 'center' },
                } */,
      ];
      let row7 = [
        {
          colSpan: 1,
          rowSpan: 5,
          content: "设计对比",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 2,
          content: "钻孔类别",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "设计孔径(mm)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "设计见煤孔深(m)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "设计夹角(°)",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "设计倾角(°)",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row8 = [
        {
          colSpan: 2,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 1,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 2,
          rowSpan: 4,
          content: "备注:",
          styles: { valign: "top", halign: "left" },
        },
      ];
      let row9 = [
        {
          colSpan: 6,
          content: "验收结果(是否合格,有效进尺m)",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row10 = [
        {
          colSpan: 6,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row11 = [
        {
          colSpan: 4,
          content: "施工人员(签名)：",
          styles: { valign: "middle", halign: "left" },
        },
        {
          colSpan: 4,
          content: "验收人员(签名)：",
          styles: { valign: "middle", halign: "left" },
        },
      ];
      let row22 = [];
      let row44 = [];
      let row66 = [
        {
          colSpan: 1,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 2,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
        {
          colSpan: 3,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      let row88 = [
        {
          colSpan: 2,
          content: "",
          styles: { valign: "middle", halign: "center" },
        },
      ];
      body.push(row);
      body.push(row0);
      body.push(row1);
      body.push(row2);
      body.push(row22);
      body.push(row3);
      body.push(row4);
      body.push(row44);
      body.push(row5);
      body.push(row6);
      body.push(row66);
      body.push(row7);
      body.push(row8);
      body.push(row88);
      body.push(row9);
      body.push(row10);
      body.push(row11);

      this.doc.autoTable({
        startY: 20,
        body: body,
        theme: "grid",
        styles: {
          font: "simhei",
          halign: "center",
          lineColor: [0, 0, 0],
          lineWidth: 0.2,
        },
      });
      this.doc.setLineWidth(0.2);
      this.doc.setDrawColor(0, 0, 0);
      this.doc.setFontSize(8);
      this.doc.line(200, 60, 260, 60);
      this.doc.text(200, 59, "切眼方向");
      this.doc.line(200, 80, 260, 80);
      this.doc.text(200, 79, "中心线");
      this.doc.circle(225, 70, 1);
      //this.doc.text(225, 68, "123-10");
      this.doc.text(225, 68, "孔号");
      this.doc.circle(255, 70, 1);
      //this.doc.text(255, 68, "2D3点内");
      this.doc.text(255, 68, "导线点");
      this.doc.line(225, 80, 225, 71);
      //this.doc.text(226, 76, "1.7m");
      this.doc.text(226, 76, "0m");
      this.doc.line(200, 100, 260, 100);
      this.doc.line(225, 100, 225, 105);
      this.doc.line(255, 100, 255, 105);
      this.doc.line(225, 103, 255, 103);
      //this.doc.text(240, 106, "3.6m");
      this.doc.text(240, 106, "0m");
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
      gZuankongyuanshiyanshoudan(this.queryParams).then((response) => {
        let resp = response.rows;
        this.actual_list = [];
        let ysList = [];
        resp.forEach((e) => {
          e.holeId = e.roadway + "-" + e.groupNo + "-" + e.boreholeNo;
          ysList.push(e);
        });
        this.actual_list = ysList;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询实钻基础信息t_actual_borehole列表 */
    /* getList() {
      this.loading = true;
      listActual_borehole(this.queryParams).then((response) => {
        this.actual_boreholeList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    }, */
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
        heightArchBaseline: null,
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
