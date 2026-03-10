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
      <el-form-item label="测点名称" prop="zkbh">
        <!-- @keyup.enter.native="handleQuery" -->
        <el-select
          v-model="selectCdIds"
          multiple
          collapse-tags
          placeholder="请选择测点名称"
        >
          <el-option
            v-for="item in cdOptions"
            :key="item.id"
            :label="item.measuringPointName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="测量时间" prop="measureTime">
        <el-date-picker clearable v-model="queryParams.measureTime" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择测量时间">
        </el-date-picker>
      </el-form-item> -->
      <!-- <el-form-item label="光瓦测量值" prop="concentration">
        <el-input
          v-model="queryParams.concentration"
          placeholder="请输入光瓦测量值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="传感器浓度" prop="concentrationS">
        <el-input
          v-model="queryParams.concentrationS"
          placeholder="请输入传感器浓度"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="校正后显示值" prop="concentrationRevise">
        <el-input
          v-model="queryParams.concentrationRevise"
          placeholder="请输入校正后显示值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负压" prop="vacuum">
        <el-input
          v-model="queryParams.vacuum"
          placeholder="请输入负压"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="传感器负压" prop="vacuumS">
        <el-input
          v-model="queryParams.vacuumS"
          placeholder="请输入传感器负压"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负压校正后显示" prop="vacuumRevise">
        <el-input
          v-model="queryParams.vacuumRevise"
          placeholder="请输入负压校正后显示"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="混合流量" prop="mixedflow">
        <el-input
          v-model="queryParams.mixedflow"
          placeholder="请输入混合流量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="传感器混合流量" prop="mixedflows">
        <el-input
          v-model="queryParams.mixedflows"
          placeholder="请输入传感器混合流量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="混合流量校正后显示" prop="mixedflowrevise">
        <el-input
          v-model="queryParams.mixedflowrevise"
          placeholder="请输入混合流量校正后显示"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="纯流量" prop="pureFlow">
        <el-input
          v-model="queryParams.pureFlow"
          placeholder="请输入纯流量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="传感器纯流量" prop="pureFlowS">
        <el-input
          v-model="queryParams.pureFlowS"
          placeholder="请输入传感器纯流量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="纯流量校正后显示" prop="pureFlowRevise">
        <el-input
          v-model="queryParams.pureFlowRevise"
          placeholder="请输入纯流量校正后显示"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="温度" prop="temperature">
        <el-input
          v-model="queryParams.temperature"
          placeholder="请输入温度"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="传感器温度" prop="temperatureS">
        <el-input
          v-model="queryParams.temperatureS"
          placeholder="请输入传感器温度"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="温度校正后显示" prop="temperatureRevise">
        <el-input
          v-model="queryParams.temperatureRevise"
          placeholder="请输入温度校正后显示"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一氧化碳便携仪" prop="coportable">
        <el-input
          v-model="queryParams.coportable"
          placeholder="请输入一氧化碳便携仪"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="传感器一氧化碳" prop="cos">
        <el-input
          v-model="queryParams.cos"
          placeholder="请输入传感器一氧化碳"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一氧化碳校正后显示" prop="corevise">
        <el-input
          v-model="queryParams.corevise"
          placeholder="请输入一氧化碳校正后显示"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="本地气压" prop="barometerValue">
        <el-input
          v-model="queryParams.barometerValue"
          placeholder="请输入本地气压"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="传感器气压值" prop="pressures">
        <el-input
          v-model="queryParams.pressures"
          placeholder="请输入传感器气压值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="校正后气压值" prop="pressureRevise">
        <el-input
          v-model="queryParams.pressureRevise"
          placeholder="请输入校正后气压值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实测人" prop="actualMeasurementPersion">
        <el-input
          v-model="queryParams.actualMeasurementPersion"
          placeholder="请输入实测人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="调校人" prop="timingPersion">
        <el-input
          v-model="queryParams.timingPersion"
          placeholder="请输入调校人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增</el-button
        >
        <el-button icon="el-icon-upload" size="mini" @click="handleImpCdrgdb"
          >导入</el-button
        >
      </el-form-item>
    </el-form>

    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['hhhz:tMeasurePointContrast:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['hhhz:tMeasurePointContrast:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['hhhz:tMeasurePointContrast:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['hhhz:tMeasurePointContrast:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row> -->

    <el-table
      v-loading="loading"
      :data="tMeasurePointContrastList"
      @selection-change="handleSelectionChange"
    >
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column label="${comment}" align="center" prop="id" /> -->
      <el-table-column label="测点" align="center" prop="measurePointLocal" />
      <!-- <el-table-column label="序号" align="center" prop="measurepointid" /> -->
      <el-table-column
        label="测量时间"
        align="center"
        prop="measureTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.measureTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="浓度(%)" align="center">
        <el-table-column
          label="光瓦测量值"
          align="center"
          prop="concentration"
        />
        <el-table-column
          label="传感器显示值"
          align="center"
          prop="concentrationS"
        />
        <el-table-column
          label="校正后显示值"
          align="center"
          prop="concentrationRevise"
        />
      </el-table-column>
      <el-table-column label="负压(KPa)" align="center">
        <el-table-column label="负压表实测值" align="center" prop="vacuum" />
        <el-table-column label="传感器显示值" align="center" prop="vacuumS" />
        <el-table-column
          label="校正后显示值"
          align="center"
          prop="vacuumRevise"
        />
      </el-table-column>
      <el-table-column label="纯流量(m³/min)" align="center">
        <el-table-column label="孔板测量值" align="center" prop="pureFlow" />
        <el-table-column label="传感器显示值" align="center" prop="pureFlowS" />
        <el-table-column
          label="校正后显示值"
          align="center"
          prop="pureFlowRevise"
        />
      </el-table-column>
      <el-table-column label="温度(°C)" align="center">
        <el-table-column
          label="温度计测量值"
          align="center"
          prop="temperature"
        />
        <el-table-column
          label="传感器显示值"
          align="center"
          prop="temperatureS"
        />
        <el-table-column
          label="校正后显示值"
          align="center"
          prop="temperatureRevise"
        />
      </el-table-column>
      <el-table-column label="一氧化碳(ppm)" align="center">
        <el-table-column
          label="一氧化碳便携仪"
          align="center"
          prop="coportable"
        />
        <el-table-column label="传感器显示值" align="center" prop="cos" />
        <el-table-column label="校正后显示值" align="center" prop="corevise" />
      </el-table-column>
      <el-table-column label="本底气压(KPa)" align="center">
        <el-table-column label="气压表" align="center" prop="barometerValue" />
        <el-table-column label="传感器气压值" align="center" prop="pressureS" />
        <el-table-column
          label="校正后气压值"
          align="center"
          prop="pressureRevise"
        />
      </el-table-column>

      <!-- <el-table-column label="混合流量" align="center" prop="mixedflow" />
      <el-table-column label="传感器混合流量" align="center" prop="mixedflows" />
      <el-table-column label="混合流量校正后显示" align="center" prop="mixedflowrevise" />
      <el-table-column label="纯流量" align="center" prop="pureFlow" />
      <el-table-column label="传感器纯流量" align="center" prop="pureFlowS" />
      <el-table-column label="纯流量校正后显示" align="center" prop="pureFlowRevise" />
      <el-table-column label="温度" align="center" prop="temperature" />
      <el-table-column label="传感器温度" align="center" prop="temperatureS" />
      <el-table-column label="温度校正后显示" align="center" prop="temperatureRevise" />
      <el-table-column label="一氧化碳便携仪" align="center" prop="coportable" />
      <el-table-column label="传感器一氧化碳" align="center" prop="cos" />
      <el-table-column label="一氧化碳校正后显示" align="center" prop="corevise" />
      <el-table-column label="本地气压" align="center" prop="barometerValue" />
      <el-table-column label="传感器气压值" align="center" prop="pressures" />
      <el-table-column label="校正后气压值" align="center" prop="pressureRevise" /> -->
      <el-table-column
        label="实测人"
        align="center"
        prop="actualMeasurementPersion"
      />
      <el-table-column label="调校人" align="center" prop="timingPersion" />
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
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
      <!-- <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['hhhz:tMeasurePointContrast:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['hhhz:tMeasurePointContrast:remove']">删除</el-button>
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

    <!-- 添加或修改tMeasurePointContrast对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="140px"
        inline
      >
        <el-form-item label="测点名称" prop="zkbh">
          <!-- @keyup.enter.native="handleQuery" -->
          <el-select v-model="form.measurepointid" placeholder="请选择测点名称">
            <el-option
              v-for="item in cdOptions"
              :key="item.id"
              :label="item.measuringPointName"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="测点ID" prop="measurepointid">
          <el-input v-model="form.measurepointid" placeholder="请输入测点ID" />
        </el-form-item> -->
        <el-form-item label="测量时间" prop="measureTime">
          <el-date-picker
            clearable
            v-model="form.measureTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择测量时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="光瓦测量值" prop="concentration">
          <el-input
            v-model="form.concentration"
            placeholder="请输入光瓦测量值"
          />
        </el-form-item>
        <el-form-item label="传感器浓度" prop="concentrationS">
          <el-input
            v-model="form.concentrationS"
            placeholder="请输入传感器浓度"
          />
        </el-form-item>
        <el-form-item label="校正后显示值" prop="concentrationRevise">
          <el-input
            v-model="form.concentrationRevise"
            placeholder="请输入校正后显示值"
          />
        </el-form-item>
        <el-form-item label="负压" prop="vacuum">
          <el-input v-model="form.vacuum" placeholder="请输入负压" />
        </el-form-item>
        <el-form-item label="传感器负压" prop="vacuumS">
          <el-input v-model="form.vacuumS" placeholder="请输入传感器负压" />
        </el-form-item>
        <el-form-item label="负压校正后显示" prop="vacuumRevise">
          <el-input
            v-model="form.vacuumRevise"
            placeholder="请输入负压校正后显示"
          />
        </el-form-item>
        <el-form-item label="混合流量" prop="mixedflow">
          <el-input v-model="form.mixedflow" placeholder="请输入混合流量" />
        </el-form-item>
        <el-form-item label="传感器混合流量" prop="mixedflows">
          <el-input
            v-model="form.mixedflows"
            placeholder="请输入传感器混合流量"
          />
        </el-form-item>
        <el-form-item label="混合流量校正后显示" prop="mixedflowrevise">
          <el-input
            v-model="form.mixedflowrevise"
            placeholder="请输入混合流量校正后显示"
          />
        </el-form-item>
        <el-form-item label="纯流量" prop="pureFlow">
          <el-input v-model="form.pureFlow" placeholder="请输入纯流量" />
        </el-form-item>
        <el-form-item label="传感器纯流量" prop="pureFlowS">
          <el-input v-model="form.pureFlowS" placeholder="请输入传感器纯流量" />
        </el-form-item>
        <el-form-item label="纯流量校正后显示" prop="pureFlowRevise">
          <el-input
            v-model="form.pureFlowRevise"
            placeholder="请输入纯流量校正后显示"
          />
        </el-form-item>
        <el-form-item label="温度" prop="temperature">
          <el-input v-model="form.temperature" placeholder="请输入温度" />
        </el-form-item>
        <el-form-item label="传感器温度" prop="temperatureS">
          <el-input
            v-model="form.temperatureS"
            placeholder="请输入传感器温度"
          />
        </el-form-item>
        <el-form-item label="温度校正后显示" prop="temperatureRevise">
          <el-input
            v-model="form.temperatureRevise"
            placeholder="请输入温度校正后显示"
          />
        </el-form-item>
        <el-form-item label="一氧化碳便携仪" prop="coportable">
          <el-input
            v-model="form.coportable"
            placeholder="请输入一氧化碳便携仪"
          />
        </el-form-item>
        <el-form-item label="传感器一氧化碳" prop="cos">
          <el-input v-model="form.cos" placeholder="请输入传感器一氧化碳" />
        </el-form-item>
        <el-form-item label="一氧化碳校正后显示" prop="corevise">
          <el-input
            v-model="form.corevise"
            placeholder="请输入一氧化碳校正后显示"
          />
        </el-form-item>
        <el-form-item label="本地气压" prop="barometerValue">
          <el-input
            v-model="form.barometerValue"
            placeholder="请输入本地气压"
          />
        </el-form-item>
        <el-form-item label="传感器气压值" prop="pressures">
          <el-input v-model="form.pressures" placeholder="请输入传感器气压值" />
        </el-form-item>
        <el-form-item label="校正后气压值" prop="pressureRevise">
          <el-input
            v-model="form.pressureRevise"
            placeholder="请输入校正后气压值"
          />
        </el-form-item>
        <el-form-item label="实测人" prop="actualMeasurementPersion">
          <el-input
            v-model="form.actualMeasurementPersion"
            placeholder="请输入实测人"
          />
        </el-form-item>
        <el-form-item label="调校人" prop="timingPersion">
          <el-input v-model="form.timingPersion" placeholder="请输入调校人" />
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

    <!-- 上传对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <!-- <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" />
            是否更新已经存在的用户数据
          </div> -->
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
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
import {
  listTMeasurePointContrast,
  getTMeasurePointContrast,
  delTMeasurePointContrast,
  addTMeasurePointContrast,
  updateTMeasurePointContrast,
} from "@/api//hhhz/tMeasurePointContrast";
import { gCedianL } from "@/api/hhhz/cedian";
import { getToken } from "@/utils/auth";
import { parseTime } from '../../../utils/ruoyi'

export default {
  name: "TMeasurePointContrast",
  data() {
    return {
      cdOptions: [],
      selectCdIds: [],
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
      // tMeasurePointContrast表格数据
      tMeasurePointContrastList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      upload: {
        // 是否显示弹出层
        open: false,
        // 弹出层标题
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: {
          Authorization: "Bearer " + getToken(),
        },
        // 上传的地址
        url: null,
      },
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
  async created() {
    await this.getCeDianList();
    this.getList();
  },
  methods: {
    parseTime,
    // 获取测点信息
    async getCeDianList() {
      await gCedianL({}).then((response) => {
        console.log("gCeDianList", response);
        this.cdOptions = response.rows;
      });
    },
    /** 查询tMeasurePointContrast列表 */
    getList() {
      this.loading = true;
      if (this.selectCdIds && this.selectCdIds.length >= 1) {
        this.queryParams.measurePointIds = JSON.stringify(this.selectCdIds)
          .replace("[", "")
          .replace("]", "");
      }
      console.log("queryParams", this.queryParams);
      listTMeasurePointContrast(this.queryParams).then((response) => {
        this.tMeasurePointContrastList = response.rows;
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
        measurepointid: null,
        measureTime: null,
        concentration: null,
        concentrationS: null,
        concentrationRevise: null,
        vacuum: null,
        vacuumS: null,
        vacuumRevise: null,
        mixedflow: null,
        mixedflows: null,
        mixedflowrevise: null,
        pureFlow: null,
        pureFlowS: null,
        pureFlowRevise: null,
        temperature: null,
        temperatureS: null,
        temperatureRevise: null,
        coportable: null,
        cos: null,
        corevise: null,
        barometerValue: null,
        pressures: null,
        pressureRevise: null,
        actualMeasurementPersion: null,
        timingPersion: null,
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
      this.title = "添加测点人工对比数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      //this.reset();
      //const id = row.id || this.ids;
      //getTMeasurePointContrast(id).then((response) => {
      this.form = row;
      this.open = true;
      this.title = "修改测点人工对比数据";
      //});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTMeasurePointContrast(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTMeasurePointContrast(this.form).then((response) => {
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
        .confirm('是否确认删除编号为"' + ids + '"的数据项？')
        .then(function () {
          return delTMeasurePointContrast(ids);
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
        "hhhz/tMeasurePointContrast/export",
        {
          ...this.queryParams,
        },
        `tMeasurePointContrast_${new Date().getTime()}.xlsx`
      );
    },
    /** 导入周期计量excel数据 */
    handleImpCdrgdb() {
      this.upload.type = "cdrgdb";
      this.upload.open = true;
      this.upload.title = "导入测点人工对比数据";
      this.upload.url =
        process.env.VUE_APP_BASE_API + "/tDrainageParameter/importData";
    },
    /** 下载模板操作 */
    importTemplate() {
      if (this.upload.type === "cdrgdb") {
        console.log("下载测点人工对比数据模板...");
        this.download(
          "/tDrainageParameter/exportTemplate",
          {},
          `测点人工对比_template_${new Date().getTime()}.xlsx`
        );
      } else {
        console.log("type", this.upload.type);
      }
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "导入结果",
        { dangerouslyUseHTMLString: true }
      );
      this.refreshRoad();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
  },
};
</script>
