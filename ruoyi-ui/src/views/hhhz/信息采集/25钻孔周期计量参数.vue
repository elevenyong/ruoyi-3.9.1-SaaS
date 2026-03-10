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
      <!-- <el-form-item label="钻孔用途" prop="zkyt">
                <el-select v-model="queryParams.zkyt" placeholder="请选择钻孔用途">
                    <el-option v-for="item in zkytOptions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="钻场编号" prop="zcbh">
                <el-select v-model="queryParams.zcbh" placeholder="请选择钻场编号">
                    <el-option v-for="item in zcbhOptions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="钻孔编号" prop="zkbh">
                <el-select v-model="queryParams.zkbh" placeholder="请选择钻孔编号">
                    <el-option v-for="item in zkbhOptions" :key="item.value" :label="item.label" :value="item.value">
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
        <el-button icon="el-icon-plus" size="mini" @click="handleAdd"
          >新增</el-button
        >
        <el-button
          type="primary"
          icon="el-icon-upload"
          size="mini"
          @click="handleImpZqjl"
          >导入</el-button
        >
      </el-form-item>
      <el-form-item> </el-form-item>
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

    <el-table
      v-loading="loading"
      :data="tDrainageParameterList"
      @selection-change="handleSelectionChange"
    >
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
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column label="${comment}" align="center" prop="id" /> -->
<!--      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="钻孔编号" align="center" prop="boreholeId" />
      <el-table-column label="孔号" align="center" prop="holeId" />
      <el-table-column label="始抽日期" align="center" prop="beginningconDate">
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.beginningconDate, "{y}-{m}-{d}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="始抽浓度(%)"
        align="center"
        prop="gasConcentration"
      />
      <el-table-column
        label="单孔浓度(%)"
        align="center"
        prop="gasConcentration"
      />
      <el-table-column
        label="始抽负压(KPa)"
        align="center"
        prop="negativePressure"
      />
      <el-table-column
        label="单孔负压(KPa)"
        align="center"
        prop="negativePressure"
      />
      <el-table-column label="测量日期" align="center" prop="measureDate">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.measureDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="测量人" align="center" prop="measurer" />-->
      <el-table-column label="操作" align="center" width="120">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="
              () => {
                open = true;
                form = scope.row;
                queryParams.roadwayId = form.roadwayId;
                queryParams.groupNo = form.groupNo;
              }
            "
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除
          </el-button>
        </template>
      </el-table-column>
      <!--------------------------------------------->
      <!-- <el-table-column label="对应孔号ID" align="center" prop="boreholeId" />
        <el-table-column label="始抽日期" align="center" prop="datedrainagestart" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.datedrainagestart, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="始抽浓度" align="center" prop="concentrationdrainagestart" />
        <el-table-column label="单孔浓度" align="center" prop="concentrationbore" />
        <el-table-column label="单孔负压" align="center" prop="vacuumbore" />
        <el-table-column label="预计抽采完成所用的天数" align="center" prop="estimatedtimedrainagedone" />
        <el-table-column label="测量日期" align="center" prop="measureDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.measureDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="测量人" align="center" prop="measureperson" />
        <el-table-column label="制表人" align="center" prop="tabulator" />
        <el-table-column label="备注" align="center" prop="remark" /> -->
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                        v-hasPermi="['/:tDrainageParameter:edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                        v-hasPermi="['/:tDrainageParameter:remove']">删除</el-button>
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

    <!-- 添加或修改tDrainageParameter对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
          <el-select v-model="form.boreholeNo" placeholder="请选择钻孔序号">
            <el-option
              v-for="item in zkxhOptions"
              :key="item.boreholeId"
              :label="item.boreholeNo"
              :value="item.boreholeId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="始抽日期" prop="beginningconDate">
          <el-date-picker
            clearable
            v-model="form.beginningconDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择始抽日期"
          >
          </el-date-picker>
        </el-form-item> -->
        <el-form-item label="始抽数据">
          <el-switch v-model="form.beginningcon"></el-switch>
        </el-form-item>
        <!-- <el-form-item label="始抽浓度" prop="gasConcentration">
          <el-input
            v-model="form.gasConcentration"
            placeholder="请输入始抽浓度"
            @blur="
              form.gasConcentration = Number(
                form.gasConcentration
              )
            "
          />
        </el-form-item> -->
        <el-form-item label="单孔浓度" prop="gasConcentration">
          <el-input
            v-model="form.gasConcentration"
            placeholder="请输入单孔浓度"
            @blur="form.gasConcentration = Number(form.gasConcentration)"
          />
        </el-form-item>
        <el-form-item label="单孔负压" prop="negativePressure">
          <el-input
            v-model="form.negativePressure"
            placeholder="请输入单孔负压"
            @blur="form.negativePressure = Number(form.negativePressure)"
          />
        </el-form-item>
        <!-- <el-form-item
          label="预计抽采完成所用的天数"
          prop="estimatedtimedrainagedone"
        >
          <el-input
            v-model="form.estimatedtimedrainagedone"
            placeholder="请输入预计抽采完成所用的天数"
            @blur="
              form.estimatedtimedrainagedone = Number(
                form.estimatedtimedrainagedone
              )
            "
          />
        </el-form-item> -->
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
        <el-form-item label="测量人" prop="measurer">
          <el-input v-model="form.measurer" placeholder="请输入测量人" />
        </el-form-item>
        <el-form-item label="制表人" prop="tabulator">
          <el-input v-model="form.tabulator" placeholder="请输入制表人" />
        </el-form-item>
        <!-- <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item> -->
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
  gZuankongyongtuList,
  gGroupIdList,
  gRoadWayList,
  gHoleNoListWithId,
} from "@/api/hhhz/zuankong";
import {
  listTDrainageParameter,
  getTDrainageParameter,
  delTDrainageParameter,
  addTDrainageParameter,
  updateTDrainageParameter,
} from "@/api/hhhz/zhouqijiliang";
import { getToken } from "@/utils/auth";
export default {
  name: "TDrainageParameter",
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
  created() {
    this.getRoadWayList();
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
      this.queryCheckingdate = [];
    },
    /** 查询tDrainageParameter列表 */
    getList() {
      this.loading = true;
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
        let resp = response.rows;
        this.actual_list = [];
        let ysList = [];
        resp.forEach((e) => {
          e.holeId = e.roadway + "-" + e.groupNo + "-" + e.boreholeNo;
          ysList.push(e);
        });
        this.tDrainageParameterList = ysList;
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
      this.form = {};
      //this.resetForm("form");
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
      this.title = "添加钻孔周期计量参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTDrainageParameter(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改钻孔周期计量参数";
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
      delTDrainageParameter({ id: row.id }).then((response) => {
        console.log("delTDrainageParameter", response);
        if (response.code == 200) {
          this.$modal.msgSuccess("删除成功");
        }
        this.getList();
      });
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
    /** 导入周期计量excel数据 */
    handleImpZqjl() {
      this.upload.type = "zqjl";
      this.upload.open = true;
      this.upload.title = "导入周期计量参数数据";
      this.upload.url =
        process.env.VUE_APP_BASE_API + "/tDrainageParameter/importData";
    },
    /** 下载模板操作 */
    importTemplate() {
      if (this.upload.type === "zqjl") {
        console.log("下载周期计量参数数据模板...");
        this.download(
          "/tDrainageParameter/exportTemplate",
          {},
          `周期计量参数数据_template_${new Date().getTime()}.xlsx`
        );
      } else {
        console.log("this.upload.type==hdqd", this.upload.type === "hdqd");
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
