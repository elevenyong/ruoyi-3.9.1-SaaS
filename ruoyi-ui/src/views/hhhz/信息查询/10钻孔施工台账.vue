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

    <el-table
      v-loading="loading"
      :data="actual_boreholeList"
      @selection-change="handleSelectionChange"
    >
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column label="${comment}" align="center" prop="id" />-->
      <el-table-column label="巷道名称" align="center" prop="roadway" />
      <el-table-column label="组号" align="center" prop="groupNo" />
      <el-table-column label="孔号" align="center" prop="boreholeNo" />
      <el-table-column label="拔钻日期" align="center" prop="pullDrillDate" />
      <el-table-column label="设计夹角" align="center" prop="desAngle" />
      <el-table-column label="实际夹角" align="center" prop="actAngle" />
      <el-table-column label="设计倾角" align="center" prop="desInclinationAngle" />
      <el-table-column label="实际倾角" align="center" prop="actInclinationAngle" />
      <el-table-column label="倾角误差" align="center" prop="errInclinationAngle" />
      <el-table-column label="设计见煤深度" align="center" prop="desCoalDepth" />
      <el-table-column label="实际见煤深度" align="center" prop="actCoalDepth" />
      <el-table-column label="设计孔深" align="center" prop="desBoreholeLength" />
      <el-table-column label="实际孔深" align="center" prop="actBoreholeLength" />
      <el-table-column label="见煤情况" align="center" prop="seeRockCoalCase" />
      <el-table-column label="临近孔号" align="center" prop="adjacentBoreholeNo" />
      <el-table-column label="临近孔距离" align="center" prop="distanceAdjacentBorehole" />
      <el-table-column label="距基线高度" align="center" prop="holeAltitude" />
      <el-table-column label="导线点" align="center" prop="navigationPointName" />
      <el-table-column label="距导线点距离" align="center" prop="distanceToNavigationPoint" />
      <el-table-column label="距巷道中线距离" align="center" prop="distanceToCentrallineTunnel" />
      <el-table-column label="施工人员" align="center" prop="personConstruction" />
      <el-table-column label="验收人员" align="center" prop="personChecking" />
      <el-table-column label="封孔方式" align="center" prop="sealingMaterial" />
      <el-table-column label="冲孔长度" align="center" prop="lengthFlushing" />
      <el-table-column label="冲孔煤量" align="center" prop="actualCoalAmountFlushed" />

<!--      <el-table-column label="序号" align="center" prop="boreholeNo" />
      <el-table-column label="钻孔地点" align="center" prop="roadway" />
      <el-table-column label="组号" align="center" prop="groupNo" />
      <el-table-column label="孔号" align="center" prop="boreholeNo" />
      <el-table-column label="拔钻日期" align="center" prop="pullDrillDate">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.drillingDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="班次" align="center" prop="shiftInfo" />
      <el-table-column label="夹角(°)" align="center" prop="inclinationAngle">
        <el-table-column label="设计" align="center" prop="desAzimuthAngle" />
        <el-table-column label="实际" align="center" prop="actAzimuthangle" />
      </el-table-column>
      <el-table-column
        label="倾角(°)"
        align="center"
        prop="depth1stRockSeamStart"
      >
        <el-table-column
          label="设计"
          align="center"
          prop="desInclinationAngle"
        />
        <el-table-column
          label="实际"
          align="center"
          prop="actInclinationAngle"
        />
        <el-table-column
          label="误差"
          align="center"
          prop="inclinationangleErr"
        />
      </el-table-column>
      <el-table-column label="孔径(mm)" align="center" prop="borediameter" />
      <el-table-column
        label="孔深(m)"
        align="center"
        prop="depth2stRockSeamStart"
      >
        <el-table-column
          label="设计"
          align="center"
          prop="desBoreholeLength"
        ></el-table-column>
        <el-table-column
          label="实际"
          align="center"
          prop="actBoreholeLength"
        ></el-table-column>
      </el-table-column>
      <el-table-column
        label="见煤深度(m)"
        align="center"
        prop="depth3stcoalseamstart"
      >
        <el-table-column
          label="设计"
          align="center"
          prop="desCoalDepth"
        ></el-table-column>
        <el-table-column
          label="实际"
          align="center"
          prop="actCoalDepth"
        ></el-table-column>
        <el-table-column
          label="误差"
          align="center"
          prop="seecoalerr"
        ></el-table-column>
        <el-table-column
          label="误差比例"
          align="center"
          prop="seecoalerrratio"
        ></el-table-column>
      </el-table-column>
      <el-table-column label="止煤深度(m)" align="center">
        <el-table-column
          label="设计"
          align="center"
          prop="desdepthstopcoalseamstart"
        ></el-table-column>
        <el-table-column
          label="实际"
          align="center"
          prop="actdepthstopcoalseamstart"
        ></el-table-column>
        <el-table-column
          label="误差"
          align="center"
          prop="stopcoalerr"
        ></el-table-column>
        <el-table-column
          label="误差比例"
          align="center"
          prop="stopcoalerrratio"
        ></el-table-column>
      </el-table-column>
      <el-table-column label="钻孔定位" align="center" prop="navigationPointId">
        <el-table-column
          label="导向点"
          align="center"
          prop="navigationPointName"
        ></el-table-column>
        <el-table-column
          label="与导向点距离(m)"
          align="center"
          prop="distanceToNavigationPoint"
        ></el-table-column>
      </el-table-column>
      <el-table-column
        label="是否安装吹水装置"
        align="center"
        prop="distanceToNavigationPoint"
      />
      <el-table-column label="钻孔用途" align="center" prop="purposeBorehole" />
      <el-table-column label="封孔方式" align="center" prop="sealingMaterial" />
      <el-table-column
        label="拔钻浓度(%)"
        align="center"
        prop="gasConcentration"
      />
      <el-table-column
        label="施工负责人"
        align="center"
        prop="drillingchargehand"
      />
      <el-table-column label="瓦斯检察员" align="center" prop="personChecking" />
      <el-table-column label="拔钻时间" align="center" prop="pullDrillDate">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pullDrillDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="封孔信息" align="center" prop="checkingDate">
        <el-table-column
          label="下管责任人"
          align="center"
          prop="checkerpipeinsertion"
        />
        <el-table-column
          label="下管长度(m)"
          align="center"
          prop="lengthpipeInsertion"
        />
        <el-table-column
          label="下管日期"
          align="center"
          prop="datepipeinsertion"
        >
          <template slot-scope="scope">
            <span>{{
              parseTime(scope.row.datepipeinsertion, "{y}-{m}-{d}")
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="注浆责任人"
          align="center"
          prop="sgchargehand"
        />
        <el-table-column
          label="注浆量(L)"
          align="center"
          prop="actualAmountBagGrouting"
        />
        <el-table-column
          label="注浆异常情况"
          align="center"
          prop="exceptionlabel"
        />
      </el-table-column>
      <el-table-column label="冲孔信息" align="center" prop="sign">
        <el-table-column label="冲孔日期" align="center" prop="dateflushing">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.dateflushing, "{y}-{m}-{d}") }}</span>
          </template></el-table-column
        >
        <el-table-column
          label="冲孔段(m)"
          align="center"
          prop="punchingparagraph"
        />
        <el-table-column
          label="冲孔长度(m)"
          align="center"
          prop="punchinglength"
        />
        <el-table-column label="冲孔时间" align="center" prop="dateflushing"
          ><template slot-scope="scope">
            <span>{{
              parseTime(scope.row.dateflushing, "{hh}:{mm}:{ss}")
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="冲孔压力(MPa)"
          align="center"
          prop="waterPressureDuringFlushing"
        />
        <el-table-column
          label="冲孔责任人"
          align="center"
          prop="afchargehand"
        />
        <el-table-column
          label="冲孔煤量(m³)"
          align="center"
          prop="actualCoalAmountFlushed"
        />
        <el-table-column label="验煤时间" align="center" prop="receptiontime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.drillingDate, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
      </el-table-column>-->
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
      </el-table-column> -->
    </el-table>

    <!-- <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize" @pagination="getList" /> -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

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

<script>
import {
  gZuankongyongtuList,
  gGroupIdList,
  gRoadWayList,
  gHoleNoList,
} from "@/api/hhhz/zuankong";
import { listActual } from "@/api/hhhz/10zksgtz";
import { parseTime } from '../../../utils/ruoyi'

export default {
  name: "Zuankongshigongtaizhang10",
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
        groupNo: [],
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
    parseTime,
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
          ysList.push(e);
        });
        this.actual_boreholeList = ysList;
        this.total = response.total;
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
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
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
        "BoreholeConstructionBook/export",
        {},
        `钻孔施工台账_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
