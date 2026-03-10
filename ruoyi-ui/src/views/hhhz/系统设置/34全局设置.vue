<template>
  <div class="app-container">
    <el-row>
      <h4>全局设置</h4>
      <el-form
        :model="queryParams"
        ref="queryForm"
        size="small"
        :inline="true"
        v-show="showSearch"
        label-width="88px"
      >
        <el-form-item label="系统名称" prop="zkdd">
          <el-input v-model="sysName"></el-input>
        </el-form-item>
        <el-form-item label="系统Logo" prop="zkbh">
          <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="矿井名称" prop="zkbh">
          <el-input v-model="mineName"></el-input>
        </el-form-item>
      </el-form>
    </el-row>
    <el-divider></el-divider>
    <!--  班次、钻孔质量分析、突出临界值管理  -->
    <el-row>
      <el-col :span="7">
        <el-row>
          <h4 style="display: inline">班次设置
            <div style="display: inline">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-plus"
                @click="bcTableData.push({})"
              >新增班次
              </el-button>
            </div>
          </h4>
          <template>
            <el-table
              :data="bcTableData"
              style="width: 100%">
              <!--              <el-table-column
                              prop="id"
                              label="序号"
                              width="50" align="center">
                            </el-table-column>-->
              <el-table-column
                prop="shiftInfo"
                label="班次名称" align="center">
                <template slot-scope="scope">
                  <el-input
                    v-model="scope.row.shiftInfo"
                    placeholder=""
                    :disabled="scope.row.disabled"
                  />
                </template>
              </el-table-column>
              <el-table-column
                prop="startTime"
                label="开始时间" align="center">
                <template slot-scope="scope">
                  <el-date-picker
                    time-arrow-control
                    type="datetime"
                    v-model="scope.row.startTime"
                    style="width: 100%; font-size: 10px"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    :disabled="scope.row.disabled"
                  >
                  </el-date-picker>
                </template>
              </el-table-column>
              <el-table-column
                prop="endTime"
                label="结束时间" align="center">
                <template slot-scope="scope">
                  <el-date-picker
                    time-arrow-control
                    type="datetime"
                    v-model="scope.row.endtime"
                    style="width: 100%; font-size: 10px"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    :disabled="scope.row.disabled"
                  >
                  </el-date-picker>
                </template>
              </el-table-column>
              <el-table-column
                label="操作"
                align="center"
                class-name="small-padding fixed-width"
              >
                <template slot-scope="scope">
                  <el-button
                    v-if=" scope.row.disabled"
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="() => {scope.row.disabled = false;}"
                  >修改
                  </el-button>
                  <el-button
                    v-if=" !scope.row.disabled"
                    size="mini"
                    type="text"
                    icon="el-icon-check"
                    @click="update(scope.row)"
                  >保存
                  </el-button>
                  <el-button
                    v-if=" scope.row.id"
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="update(scope.row)"
                  >删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-row>
      </el-col>
      <el-col :span="7" :offset="1">
        <el-row>
          <h4 style="display: inline">钻孔质量分析管理
            <div style="display: inline">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-plus"
              >
              </el-button>
            </div>
          </h4>
          <template>
            <el-table
              :data="zkzlTableData"
              style="width: 100%">
              <el-table-column
                prop="id"
                label="序号">
              </el-table-column>
              <el-table-column
                prop="zbName"
                label="指标">
              </el-table-column>
              <el-table-column
                prop="dw"
                label="单位">
              </el-table-column>
              <el-table-column
                prop="num"
                label="临界值">
                <template slot-scope="scope">
                  <el-input
                    v-model="scope.row.num"
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
                    v-if=" scope.row.disabled"
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="() => {scope.row.disabled = false;}"
                  >修改
                  </el-button>
                  <el-button
                    v-if=" !scope.row.disabled"
                    size="mini"
                    type="text"
                    icon="el-icon-check"
                    @click="update(scope.row)"
                  >保存
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-row>
      </el-col>

      <el-col :span="7" :offset="1">
        <el-row>
          <h4 style="display: inline">突出临界值管理
            <div style="display: inline">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-plus"
              >
              </el-button>
            </div>
          </h4>
          <template>
            <el-table
              :data="tcljzTableData"
              style="width: 100%">
              <el-table-column
                prop="id"
                label="序号">
              </el-table-column>
              <el-table-column
                prop="zbName"
                label="指标">
              </el-table-column>
              <el-table-column
                prop="dw"
                label="单位">
              </el-table-column>
              <el-table-column
                prop="num"
                label="临界值">
                <template slot-scope="scope">
                  <el-input
                    v-model="scope.row.num"
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
                    v-if=" scope.row.disabled"
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="() => {scope.row.disabled = false;}"
                  >修改
                  </el-button>
                  <el-button
                    v-if=" !scope.row.disabled"
                    size="mini"
                    type="text"
                    icon="el-icon-check"
                    @click="update(scope.row)"
                  >保存
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-row>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="7">
        <el-row>
          <h4 style="display: inline">异常告警阈值
            <div style="display: inline">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-plus"
              >
              </el-button>
            </div>
          </h4>
          <template>
            <el-table
              :data="warningAlertsThreshold"
              style="width: 100%">
              <el-table-column
                prop="id"
                label="序号">
              </el-table-column>
              <el-table-column
                prop="zbName"
                label="指标">
              </el-table-column>
              <el-table-column
                prop="dw"
                label="单位">
              </el-table-column>
              <el-table-column
                prop="num"
                label="临界值">
                <template slot-scope="scope">
                  <el-input
                    v-model="scope.row.num"
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
                    v-if=" scope.row.disabled"
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="() => {scope.row.disabled = false;}"
                  >修改
                  </el-button>
                  <el-button
                    v-if=" !scope.row.disabled"
                    size="mini"
                    type="text"
                    icon="el-icon-check"
                    @click="update(scope.row)"
                  >保存
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-row>
      </el-col>
    </el-row>

    <!-- 添加或修改tGasContentPressure对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="取样点" prop="samplingpot">
          <el-input v-model="form.samplingpot" placeholder="请输入取样点"/>
        </el-form-item>
        <el-form-item label="孔号ID" prop="boreholeId">
          <el-input v-model="form.boreholeId" placeholder="请输入孔号ID"/>
        </el-form-item>
        <el-form-item label="取样深度" prop="samplingDepth">
          <el-input v-model="form.samplingDepth" placeholder="请输入取样深度"/>
        </el-form-item>
        <el-form-item label="瓦斯含量">
          <editor v-model="form.gasContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="瓦斯压力" prop="gasPressure">
          <el-input v-model="form.gasPressure" placeholder="请输入瓦斯压力"/>
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
.time-select {
  width: 200px;
  margin: 0;
}

.bukong {
  margin-left: 5px;
  color: royalblue;
}

.spli {
  text-align: center;
}

.cans {
  height: 60vh;
}

.can {
  width: 300px;
  height: 300px;
}

.my_el-form-item {
  width: 200px;
}
</style>
<script>
import { gGongzuobanci } from '@/api/hhhz/sys'
import { gGroupIdList, gHoleNoList, gRoadWayList, gZuankongyongtuList } from '@/api/hhhz/zuankong'
import { gZuankongyuanshiyanshoudan } from '@/api/hhhz/13zkysysd'
import { listConfig, getConfig, delConfig, addConfig, updateConfig, refreshCache } from '@/api/system/config'

export default {
  name: 'TGasContentPressure',
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
      // 工作班次
      gzbcOptions: [],
      // 钻孔地点
      zkddOptions: [],
      // 钻孔用途
      zkytOptions: [],
      // 钻孔组号
      zkzhOptions: [],
      // 钻孔编号
      zkxhOptions: [],
      ycxx: '',
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
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //sysName: '数字孪生智能抽采管理系统',
      sysName:'矿井瓦斯多元信息数据中心',
      mineName: 'XX矿',
      imageUrl: '',
      // 班次
      bcTableData: [{
        'searchValue': null,
        'createBy': '',
        'createTime': null,
        'updateBy': '',
        'updateTime': null,
        'remark': null,
        'params': {},
        'id': 1,
        'shiftInfo': '早班',
        'disabled': true
      },
        {
          'searchValue': null,
          'createBy': '',
          'createTime': null,
          'updateBy': '',
          'updateTime': null,
          'remark': null,
          'params': {},
          'id': 2,
          'shiftInfo': '中班',
          'disabled': true
        },
        {
          'searchValue': null,
          'createBy': '',
          'createTime': null,
          'updateBy': '',
          'updateTime': null,
          'remark': null,
          'params': {},
          'id': 3,
          'shiftInfo': '晚班',
          'disabled': true
        }],
      // 钻孔质量分析管理
      zkzlTableData: [{
        'id': 1,
        'zbName': '倾角偏差',
        'dw': '°',
        'num': 3,
        'disabled': true
      }, {
        'id': 2,
        'zbName': '方位角偏差',
        'dw': '°',
        'num': 1,
        'disabled': true
      }, {
        'id': 3,
        'zbName': '见（止）煤孔深分析',
        'dw': 'm',
        'num': 5,
        'disabled': true
      }, {
        'id': 4,
        'zbName': '见（止）煤孔深相对分析',
        'dw': '%',
        'num': 33.3,
        'disabled': true
      }, {
        'id': 5,
        'zbName': '注浆压力异常',
        'dw': 'MPa',
        'num': 0.8,
        'disabled': true
      }, {
        'id': 6,
        'zbName': '钻孔煤段护孔率',
        'dw': '%',
        'num': 90,
        'disabled': true
      }, {
        'id': 6,
        'zbName': '煤厚异常分析',
        'dw': '%',
        'num': 90,
        'disabled': true
      }],
      // 突出临界值
      tcljzTableData: [
        {
          'id': 1,
          'zbName': '瓦斯含量',
          'dw': 'm³/t',
          'num': 6,
          'disabled': true
        }, {
          'id': 2,
          'zbName': '瓦斯压力',
          'dw': 'MPa',
          'num': 0.6,
          'disabled': true
        }
      ],
      warningAlertsThreshold: []
    }
  },
  created() {
    this.getRoadWayList()
    this.getZuankongyongtuList()
    this.getGongzuobanciList()
    this.getConfigs()

    //this.getList();
  },
  mounted() {
    this.initCanvas1()
    this.initCanvas2()
  },
  methods: {
    async getConfigs() {
      await listConfig().then((response) => {
        console.log('getConfigs', response)
        let alarmConfig = response.rows
        for (let i = 0; i < alarmConfig.length; i++) {
          if (alarmConfig[i].configKey === 'sys.alarm.coalThick') {
            this.warningAlertsThreshold.push({
              'id': alarmConfig[i].configId,
              'zbName': '煤层厚度',
              'dw': 'm',
              'num': alarmConfig[i].configValue,
              'disabled': true
            })
          }
          if (alarmConfig[i].configKey === 'sys.alarm.coalThickChange') {
            this.warningAlertsThreshold.push({
              'id': alarmConfig[i].configId,
              'zbName': '煤层厚度变化率',
              'dw': 'm',
              'num': alarmConfig[i].configValue,
              'disabled': true
            })
          }
          if (alarmConfig[i].configKey === 'sys.alarm.coalAngleChange') {
            this.warningAlertsThreshold.push({
              'id': alarmConfig[i].configId,
              'zbName': '煤层倾角变化率',
              'dw': 'm',
              'num': alarmConfig[i].configValue,
              'disabled': true
            })
          }
        }
      })
    },
    // 获取工作班次
    async getGongzuobanciList() {
      await gGongzuobanci().then((response) => {
        console.log('gGongzuobanci', response)
        this.gzbcOptions = response.rows
      })
    },
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
    initCanvas1() {
      let c1 = this.$refs.can1.getContext('2d')
      //console.log(c1);
      c1.clearRect(0, 0, 600, 600)
      c1.setLineDash([])
      c1.beginPath()
      c1.moveTo(20, 20)
      c1.lineTo(300, 20)
      c1.stroke()

      c1.beginPath()
      c1.moveTo(20, 60)
      c1.setLineDash([4, 4])
      c1.lineTo(300, 60)
      c1.stroke()

      c1.beginPath()
      c1.setLineDash([])
      c1.moveTo(20, 100)
      c1.lineTo(300, 100)
      c1.stroke()

      c1.beginPath()
      c1.arc(120, 60, 5, 0, Math.PI * 2, true)
      c1.fillStyle = 'white'
      c1.fill()

      c1.beginPath()
      c1.arc(120, 60, 5, 0, Math.PI * 2, true)
      c1.stroke()

      c1.beginPath()
      c1.arc(260, 20, 5, 0, Math.PI * 2, true)
      c1.fillStyle = 'white'
      c1.fill()

      c1.beginPath()
      c1.arc(260, 20, 5, 0, Math.PI * 2, true)
      c1.stroke()

      c1.beginPath()
      c1.moveTo(120, 55)
      c1.lineTo(120, 30)
      c1.stroke()

      c1.beginPath()
      c1.moveTo(260, 25)
      c1.lineTo(260, 50)
      c1.stroke()

      c1.beginPath()
      c1.moveTo(120, 40)
      c1.lineTo(130, 35)
      c1.lineTo(130, 45)
      c1.fillStyle = 'black'
      c1.fill()

      c1.beginPath()
      c1.moveTo(260, 40)
      c1.lineTo(250, 35)
      c1.lineTo(250, 45)
      c1.fillStyle = 'black'
      c1.fill()

      c1.beginPath()
      c1.moveTo(130, 40)
      c1.lineTo(150, 40)
      c1.stroke()

      c1.beginPath()
      c1.moveTo(230, 40)
      c1.lineTo(250, 40)
      c1.stroke()

      c1.fillText('切眼方向(导向点内)', 210, 15)
    },
    initCanvas2() {
      let c2 = this.$refs.can2.getContext('2d')
      c2.setLineDash([])
      c2.beginPath()
      c2.moveTo(50, 200)
      c2.lineTo(50, 100)
      c2.stroke()

      c2.beginPath()
      c2.moveTo(150, 200)
      c2.lineTo(150, 100)
      c2.stroke()

      c2.beginPath()
      c2.arc(100, 100, 50, 0, Math.PI, true)
      c2.stroke()

      c2.beginPath()
      c2.arc(50, 100, 5, 0, Math.PI * 2, true)
      c2.fillStyle = 'white'
      c2.fill()

      c2.beginPath()
      c2.arc(50, 100, 5, 0, Math.PI * 2, true)
      c2.stroke()

      c2.beginPath()
      c2.moveTo(55, 100)
      c2.lineTo(75, 100)
      c2.stroke()

      /* c2.beginPath();
            c2.moveTo(260, 25);
            c2.lineTo(260, 50);
            c2.stroke(); */

      c2.beginPath()
      c2.moveTo(65, 100)
      c2.lineTo(60, 105)
      c2.lineTo(70, 105)
      c2.fillStyle = 'black'
      c2.fill()

      c2.beginPath()
      c2.moveTo(65, 200)
      c2.lineTo(60, 195)
      c2.lineTo(70, 195)
      c2.fillStyle = 'black'
      c2.fill()

      c2.beginPath()
      c2.moveTo(65, 195)
      c2.lineTo(65, 175)
      c2.stroke()

      c2.beginPath()
      c2.moveTo(65, 105)
      c2.lineTo(65, 125)
      c2.stroke()
    },
    /** 查询tGasContentPressure列表 */
    /** 查询tDrainageParameter列表 */
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
      gZuankongyuanshiyanshoudan(this.queryParams).then((response) => {
        let resp = response.rows
        this.actual_list = []
        let ysList = []
        resp.forEach((e) => {
          e.holeId = e.roadway + '-' + e.groupNo + '-' + e.boreholeNo
          e.downtubetime = [e.downTubeTimeStart, e.downTubeTimeEnd]
          ysList.push(e)
        })
        this.actual_list = ysList
        this.total = response.total

        if (this.actual_list.length >= 1) {
          this.form = this.actual_list[0]
        }
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
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
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加tGasContentPressure'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTGasContentPressure(id).then((response) => {
        this.form = response.data
        this.open = true
        this.title = '修改tGasContentPressure'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTGasContentPressure(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addTGasContentPressure(this.form).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除tGasContentPressure编号为"' + ids + '"的数据项？')
        .then(function() {
          return delTGasContentPressure(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        '/tGasContentPressure/export',
        {
          ...this.queryParams
        },
        `tGasContentPressure_${new Date().getTime()}.xlsx`
      )
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    }
  }
}
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 35px;
  height: 35px;
  line-height: 35px;
  text-align: center;
}

.avatar {
  width: 35px;
  height: 35px;
  display: block;
}
</style>
