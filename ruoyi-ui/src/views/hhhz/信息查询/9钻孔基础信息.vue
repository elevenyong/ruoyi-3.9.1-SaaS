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
        >搜索
        </el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
        >重置
        </el-button
        >
        <el-button icon="el-icon-download" size="mini" @click="handleExport"
        >导出
        </el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="actual_boreholeList">
      <el-table-column label="巷道名称" align="center" prop="roadway"/>
      <el-table-column label="钻孔位置" align="center" prop="drillingLocation"/>
      <el-table-column label="钻孔编号" align="center" prop="boreholeCode"/>
      <el-table-column label="设计夹角" align="center" prop="desAngle"/>
      <el-table-column label="实际夹角" align="center" prop="actAngle"/>
      <el-table-column label="设计倾角" align="center" prop="desInclinationAngle"/>
      <el-table-column label="实际倾角" align="center" prop="actInclinationAngle"/>
      <el-table-column label="设计孔深" align="center" prop="desBoreholeLength"/>
      <el-table-column label="实际孔深" align="center" prop="actBoreholeLength"/>
      <el-table-column label="见岩见煤情况" align="center" prop="seeRockCoalCase"/>
      <el-table-column label="封孔方式" align="center" prop="sealingMaterial"/>
      <el-table-column label="冲孔长" align="center" prop="lengthFlushing"/>
      <el-table-column label="冲煤量" align="center" prop="actualCoalAmountFlushed"/>

      <!--<el-table-column label="巷道名称" align="center" prop="roadway"/>
      <el-table-column label="组号" align="center" prop="groupNo"/>
      <el-table-column label="序号" align="center" prop="boreholeNo"/>
      <el-table-column
        label="设计夹角(°)"
        align="center"
        prop="desAzimuthAngle"
      />
      <el-table-column
        label="实际夹角(°)"
        align="center"
        prop="actazimuthangle"
      />
      <el-table-column
        label="设计倾角(°)"
        align="center"
        prop="desInclinationAngle"
      />
      <el-table-column
        label="实际倾角(°)"
        align="center"
        prop="actInclinationAngle"
      />
      <el-table-column
        label="设计见煤孔深(m)"
        align="center"
        prop="desCoalDepth"
      />
      <el-table-column
        label="实际见煤孔深(m)"
        align="center"
        prop="actCoalDepth"
      />
      <el-table-column
        label="设计孔深(m)"
        align="center"
        prop="desBoreholeLength"
      />
      <el-table-column
        label="实际孔深(m)"
        align="center"
        prop="actBoreholeLength"
      />
      <el-table-column
        label="见岩孔深(m)"
        align="center"
        prop="rockseamlength"
      />
      <el-table-column
        label="导线点"
        align="center"
        prop="navigationPointName"
      />
      <el-table-column
        label="距导线点距离(m)"
        align="center"
        prop="distanceToNavigationPoint"
      />
      <el-table-column label="封孔方式" align="center" prop="sealingMaterial"/>
      <el-table-column label="冲孔长(m)" align="center" prop="lengthFlushing"/>
      <el-table-column
        label="冲孔煤量(t)"
        align="center"
        prop="actualCoalAmountFlushed"
      />
      <el-table-column
        label="冲孔煤量完成比例%"
        align="center"
        prop="complentionratioflushedcoal"
      />
      <el-table-column label="查看" align="center" prop="" />
             <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                        v-hasPermi="['system:actual_borehole:edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                        v-hasPermi="['system:actual_borehole:remove']">删除</el-button>
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
/* gZuankongkonghaoList, */
import { gGroupIdList, gHoleNoList, gRoadWayList, gZuankongyongtuList } from '@/api/hhhz/zuankong'
import { listActual_borehole } from '@/api/hhhz/9zkjcxx'

export default {
  name: 'Zuankongjichuxinxi9',
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
      // 实钻基础信息t_actual_borehole表格数据
      actual_boreholeList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupNo: []
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getRoadWayList()
    this.getZuankongyongtuList()
    this.getList()
  },
  methods: {
    // 获取钻孔地点
    async getRoadWayList() {
      this.loading = true
      await gRoadWayList({}).then((response) => {
        console.log('gRoadWayList', response)
        this.zkddOptions = response.rows
      })
    },
    // 获取钻孔用途
    async getZuankongyongtuList() {
      await gZuankongyongtuList({}).then((response) => {
        console.log('gZuankongyongtuList', response)
        this.zkytOptions = response.rows
      })
    },
    // 获取钻孔组号
    async getGroupIdList(rwId) {
      // 清空
      this.initZkzh()
      await gGroupIdList({ roadwayId: rwId }).then((response) => {
        console.log('gGroupIdList', response)
        this.zkzhOptions = response.data
      })
    },
    // 获取钻孔孔号
    async getZuankongkonghaoList(zkzh) {
      console.log('groupNo', zkzh)
      this.initZkxh()
      if (zkzh) {
        await gHoleNoList({
          roadwayId: this.queryParams.roadwayId,
          groupNo: zkzh
        }).then((response) => {
          console.log('gHoleNoList', response)
          this.zkxhOptions = response
        })
      } else {
        this.initZkxh()
      }
    },
    // 初始化操作会级联清空下层
    // 初始化钻孔地点
    initZkdd() {
      this.initZkzh()
      delete delete this.queryParams.roadwayId
    },
    // 初始化钻孔组号
    initZkzh() {
      this.initZkxh()
      this.zkzhOptions = []
      this.queryZuankongzuhao = []
    },
    // 初始化钻孔序号
    initZkxh() {
      this.zkxhOptions = []
      this.queryZuankongxuhao = []
    },
    // 初始化所有
    initAll() {
      this.initZkdd()
      this.queryZuankongyongtu = []
      this.queryCheckingdate = []
    },
    /** 查询实钻基础信息t_actual_borehole列表 */
    getList() {
      this.loading = true
      // 钻孔组号
      console.log('组号:', this.queryZuankongzuhao)
      if (this.queryZuankongzuhao) {
        this.queryParams.groupNo = this.queryZuankongzuhao
      } else {
        delete this.queryParams.groupNo
      }
      // 钻孔序号
      console.log('序号:', this.queryZuankongxuhao)
      if (this.queryZuankongxuhao && this.queryZuankongxuhao.length >= 1) {
        this.queryParams.boreholeNo = JSON.stringify(this.queryZuankongxuhao)
          .replace('[', '')
          .replace(']', '')
      } else {
        delete this.queryParams.boreholeNo
      }
      // 钻孔用途
      console.log('钻孔用途:', this.queryZuankongyongtu)
      if (this.queryZuankongyongtu && this.queryZuankongyongtu.length >= 1) {
        this.queryParams.purposeBoreholeId = JSON.stringify(
          this.queryZuankongyongtu
        )
          .replace('[', '')
          .replace(']', '')
      } else {
        delete this.queryParams.purposeBoreholeId
      }
      // 时间
      console.log('时间', this.queryCheckingdate)
      if (this.queryCheckingdate && this.queryCheckingdate.length >= 1) {
        this.queryParams.startdate = this.queryCheckingdate[0]
          .toLocaleString()
          .replaceAll('/', '-')
      } else {
        delete this.queryParams.startdate
      }
      console.log('queryParams', this.queryParams)
      listActual_borehole(this.queryParams).then((response) => {
        this.actual_boreholeList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        groupNo: []
      }
      this.initAll()
      this.handleQuery()
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('tActualBorehole/export', {}, `钻孔基础信息_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
