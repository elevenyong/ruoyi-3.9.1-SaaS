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
        <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        > -->
      </el-form-item>
    </el-form>

    <el-table
      v-loading="loading"
      key="fkzjjl"
      :data="tSealingGroutingList"
      @selection-change="handleSelectionChange"
    >
      <!--<el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="boreholeId">
      </el-table-column> -->
      <el-table-column label="钻孔地点" align="center" prop="roadway">
        <!-- <template slot-scope="scope">
          <span style="margin-left: 10px">2616下巷底板抽放巷</span>
        </template> -->
      </el-table-column>
      <el-table-column label="组号" align="center" prop="groupNo">
        <!-- <template slot-scope="scope">
          <span style="margin-left: 10px">2616下巷底板抽放巷</span>
        </template> -->
      </el-table-column>
      <el-table-column label="序号" align="center" prop="boreholeNo">
        <!-- <template slot-scope="scope">
          <span style="margin-left: 10px">67-1</span>
        </template> -->
      </el-table-column>
      <el-table-column label="注浆日期" align="center" width="150px">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.groutingDate"
            type="date"
            style="width: 100%; font-size: 10px"
            :disabled="scope.row.disabled"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </template>
      </el-table-column>
      <!-- <el-table-column label="下管长度(m)" align="center" prop="boreholeId" />
            <el-table-column label="合成树胶(袋)" align="center" prop="boreholeId" /> -->
      <el-table-column label="开始时间" align="center" width="200px">
        <template slot-scope="scope">
          <el-date-picker
            time-arrow-control
            type="datetime"
            v-model="scope.row.starttime"
            style="width: 100%; font-size: 10px"
            :disabled="scope.row.disabled"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" width="200px">
        <template slot-scope="scope">
          <el-date-picker
            time-arrow-control
            type="datetime"
            v-model="scope.row.endtime"
            style="width: 100%; font-size: 10px"
            :disabled="scope.row.disabled"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column label="设计囊袋注浆量(L)" align="center" width="100px">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.desamountbaggrouting"
            placeholder=""
            :disabled="scope.row.disabled"
          />
        </template>
      </el-table-column>
      <el-table-column label="实际囊袋注浆量(L)" align="center" width="100px">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.actualAmountBagGrouting"
            placeholder=""
            :disabled="scope.row.disabled"
          />
        </template>
      </el-table-column>
      <el-table-column label="囊袋注浆压力(MPa)" align="center" width="100px">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.actualBagPressure"
            placeholder=""
            :disabled="scope.row.disabled"
          />
        </template>
      </el-table-column>
      <el-table-column label="设计中间段注浆量(L)" align="center" width="100px">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.designedamountgapgrouting"
            placeholder=""
            :disabled="scope.row.disabled"
          />
        </template>
      </el-table-column>
      <el-table-column label="实际中间段注浆量(L)" align="center" width="100px">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.actualAmountGapGrouting"
            placeholder=""
            :disabled="scope.row.disabled"
          />
        </template>
      </el-table-column>
      <el-table-column label="中间段注浆压力(MPa)" align="center" width="100px">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.interludeGroutingPressure"
            placeholder=""
            :disabled="scope.row.disabled"
          />
        </template>
      </el-table-column>
      <el-table-column label="注浆人员" align="center">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.groutingPerson"
            placeholder=""
            :disabled="scope.row.disabled"
          />
        </template>
      </el-table-column>
      <el-table-column label="始抽瓦斯浓度(%)" align="center">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.startdrainagegasconcentration"
            placeholder=""
            :disabled="scope.row.disabled"
          />
        </template>
      </el-table-column>
      <el-table-column label="始抽浓度测量日期" align="center" width="150px">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.startdrainagedate"
            type="date"
            style="width: 100%; font-size: 10px"
            :disabled="scope.row.disabled"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column label="始抽浓度测量人员" align="center">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.startdrainageperson"
            placeholder=""
            :disabled="scope.row.disabled"
          />
        </template>
      </el-table-column>
      <el-table-column label="异常现象标注" align="center">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.exceptionlabel"
            placeholder=""
            :disabled="scope.row.disabled"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.add"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="add(scope.row)"
            >新增</el-button
          >
          <el-button
            v-if="!scope.row.add && scope.row.disabled"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="
              () => {
                scope.row.disabled = false;
              }
            "
            >修改</el-button
          >
          <el-button
            v-if="!scope.row.add && !scope.row.disabled"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="update(scope.row)"
            >保存</el-button
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

    <!-- 添加或修改tSealingGrouting对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="孔号对应ID" prop="boreholeId">
          <el-input v-model="form.boreholeId" placeholder="请输入孔号对应ID" />
        </el-form-item>
        <el-form-item label="班次ID" prop="shiftId">
          <el-input v-model="form.shiftId" placeholder="请输入班次ID" />
        </el-form-item>
        <el-form-item label="注浆日期" prop="groutingDate">
          <el-date-picker
            clearable
            v-model="form.groutingDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择注浆日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="下管长度" prop="lengthpipeinsertion">
          <el-input
            v-model="form.lengthpipeinsertion"
            placeholder="请输入下管长度"
          />
        </el-form-item>
        <el-form-item label="封孔方式ID" prop="holesealtypeid">
          <el-input
            v-model="form.holesealtypeid"
            placeholder="请输入封孔方式ID"
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="starttime">
          <el-date-picker
            clearable
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endtime">
          <el-date-picker
            clearable
            v-model="form.endtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="需要囊袋注浆量" prop="needamountbaggrouting">
          <el-input
            v-model="form.needamountbaggrouting"
            placeholder="请输入需要囊袋注浆量"
          />
        </el-form-item>
        <el-form-item label="实际囊袋注浆量" prop="actualAmountBagGrouting">
          <el-input
            v-model="form.actualAmountBagGrouting"
            placeholder="请输入实际囊袋注浆量"
          />
        </el-form-item>
        <el-form-item label="囊袋注浆压力" prop="actualBagPressure">
          <el-input
            v-model="form.actualBagPressure"
            placeholder="请输入囊袋注浆压力"
          />
        </el-form-item>
        <el-form-item label="所需中间段注浆量" prop="designedamountgapgrouting">
          <el-input
            v-model="form.designedamountgapgrouting"
            placeholder="请输入所需中间段注浆量"
          />
        </el-form-item>
        <el-form-item label="实际中间段注浆量" prop="actualAmountGapGrouting">
          <el-input
            v-model="form.actualAmountGapGrouting"
            placeholder="请输入实际中间段注浆量"
          />
        </el-form-item>
        <el-form-item label="中间段注浆压力" prop="interludeGroutingPressure">
          <el-input
            v-model="form.interludeGroutingPressure"
            placeholder="请输入中间段注浆压力"
          />
        </el-form-item>
        <el-form-item label="注浆人员" prop="groutingPerson">
          <el-input
            v-model="form.groutingPerson"
            placeholder="请输入注浆人员"
          />
        </el-form-item>
        <el-form-item label="始抽瓦斯浓度" prop="startdrainagegasconcentration">
          <el-input
            v-model="form.startdrainagegasconcentration"
            placeholder="请输入始抽瓦斯浓度"
          />
        </el-form-item>
        <el-form-item label="始抽浓度测量日期" prop="startdrainagedate">
          <el-date-picker
            clearable
            v-model="form.startdrainagedate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择始抽浓度测量日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="始抽浓度测量人员" prop="startdrainageperson">
          <el-input
            v-model="form.startdrainageperson"
            placeholder="请输入始抽浓度测量人员"
          />
        </el-form-item>
        <el-form-item label="异常现象标注" prop="exceptionlabel">
          <el-input
            v-model="form.exceptionlabel"
            placeholder="请输入异常现象标注"
          />
        </el-form-item>
        <el-form-item label="记录人" prop="recorder">
          <el-input v-model="form.recorder" placeholder="请输入记录人" />
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
  gHoleNoListWithId,
} from "@/api/hhhz/zuankong";
import {
  gFengkongzhujiang,
  aFengkongzhujiang,
  uFengkongzhujiang,
} from "@/api/hhhz/17fkzjjl";

export default {
  name: "TSealingGrouting",
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
      // tSealingGrouting表格数据
      tSealingGroutingList: [
        {
          zkdd: "2616下巷底板抽放巷",
        },
      ],
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
  async created() {
    await this.getRoadWayList();
    await this.getZuankongyongtuList();
    await this.getList();
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
      this.queryZuankongyongtu = [];
      this.queryCheckingdate = [];
    },
    /** 查询tSealingGrouting列表 */
    getList() {
      this.loading = true;
      gFengkongzhujiang(this.queryParams).then((response) => {
        let resp = response.rows;
        if (this.queryParams.boreholeNo != null && resp.length == 0) {
          let addData = {
            actualAmountBagGrouting: null,
            actualAmountGapGrouting: null,
            actualBagPressure: null,
            boreholeId: this.getBoreholeIdByNo(this.queryParams.boreholeNo),
            boreholeNo: this.queryParams.boreholeNo,
            chargehand: null,
            checkerpipeinsertion: null,
            createBy: null,
            createTime: null,
            datepipeinsertion: null,
            desamountbaggrouting: null,
            designedamountgapgrouting: null,
            endtime: null,
            exceptionlabel: null,
            groupNo: this.queryParams.groupNo,
            groutingDate: null,
            groutingPerson: null,
            holesealtypeid: null,
            id: null,
            interludeGroutingPressure: null,
            lengthpipeinsertion: null,
            params: null,
            recorder: null,
            remark: null,
            roadway: this.getRoadWayName(this.queryParams.roadwayId),
            searchValue: null,
            shiftId: null,
            startdrainagedate: null,
            startdrainagegasconcentration: null,
            startdrainageperson: null,
            starttime: null,
            tubeLength: null,
            updateBy: null,
            updateTime: null,
            add: true,
          };
          let ysList = [];
          ysList.push(addData);
          this.tSealingGroutingList = ysList;
          this.total = response.total;
          this.loading = false;
          return;
        }
        this.actual_list = [];
        let ysList = [];
        resp.forEach((e) => {
          e.disabled = true;
          e.holeId = e.roadway + "-" + e.groupNo + "-" + e.boreholeNo;
          ysList.push(e);
        });
        this.tSealingGroutingList = ysList;
        this.total = response.total;
        this.loading = false;
      });
    },
    add(rowData) {
      console.log("rowData", rowData);
      aFengkongzhujiang(rowData).then((response) => {
        console.log("add", response);
        if (response.code == 200) {
          this.$modal.msgSuccess(response.msg);
          this.getList();
        }
      });
    },
    update(rowData) {
      uFengkongzhujiang(rowData).then((response) => {
        console.log("update", response);
        if (response.code == 200) {
          this.$modal.msgSuccess(response.msg);
          this.getList();
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
    // 表单重置
    reset() {
      this.form = {
        id: null,
        boreholeId: null,
        shiftId: null,
        groutingDate: null,
        lengthpipeinsertion: null,
        holesealtypeid: null,
        starttime: null,
        endtime: null,
        needamountbaggrouting: null,
        actualAmountBagGrouting: null,
        actualBagPressure: null,
        designedamountgapgrouting: null,
        actualAmountGapGrouting: null,
        interludeGroutingPressure: null,
        groutingPerson: null,
        startdrainagegasconcentration: null,
        startdrainagedate: null,
        startdrainageperson: null,
        exceptionlabel: null,
        recorder: null,
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
      this.title = "添加tSealingGrouting";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTSealingGrouting(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改tSealingGrouting";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTSealingGrouting(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTSealingGrouting(this.form).then((response) => {
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
        .confirm('是否确认删除tSealingGrouting编号为"' + ids + '"的数据项？')
        .then(function () {
          return delTSealingGrouting(ids);
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
        "/tSealingGrouting/export",
        {
          ...this.queryParams,
        },
        `tSealingGrouting_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
