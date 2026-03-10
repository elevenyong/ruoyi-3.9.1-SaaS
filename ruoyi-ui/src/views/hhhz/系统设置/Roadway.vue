<script>
import { uRoad, gRoad, aRoad, dRoad } from '@/api/hhhz/hangdao'
import * as XLSX from 'xlsx'

export default {
  props: ['currentRoad', 'roadEditable'],
  data() {
    return {
      loading: false,
      // 是否可编辑
      editable: false,
      // 巷道形状
      shapeTpyes: [
        {
          faultShapId: 1,
          shape: '矩形'
        },
        {
          faultShapId: 2,
          shape: '梯形'
        },
        {
          faultShapId: 3,
          shape: '拱形'
        }
      ],
      inOrOuts: [
        { id: 0, inout: '内错' },
        { id: 1, inout: '外错' }
      ],
      nearFloors: [
        { id: 0, nearFloor: '沿底掘进' },
        { id: 1, nearFloor: '沿顶掘进' }
      ],
      navIsActual: [
        { id: 0, label: '未掘（设计点）' },
        { id: 1, label: '已掘' }
      ],
      showUOANavPoint: false,
      showUOANavPointTitle: '',
      navPointEdit: false,
      showUOANavPointForm: {
        navigationPointName: null,
        isActualPoint: 1,
        roadwayId: null,
        x: null,
        y: null,
        z: null,
        id: null,
        serialNumber: null
      }
    }
  },
  mounted() {
    let _this = this
    console.log('mounted')
    if (this.currentRoad.id != null) {
      _this.resortAndCalcDis()
    } else {
      this.editable = true
    }
  },
  beforeUpdate() {
    let _this = this
    console.log('beforeUpdate')
    if (this.currentRoad.id != null) {
      _this.resortAndCalcDis()
    } else {
      this.editable = true
    }
  },
  methods: {
    delRoad() {
      let _this = this
      dRoad({ id: _this.currentRoad.id }).then(res => {
        _this.$modal.msgSuccess('巷道删除成功')
        _this.$emit('delRoadMsg', true)
      })
    },
    // 更新或新增巷道
    UOARoad() {
      if (this.currentRoad.tnavigationPointList && this.currentRoad.tnavigationPointList.length >= 2) {
        this.currentRoad.azimuthAngle = Number(this.currentRoad.azimuthAngle)
        this.currentRoad.height = Number(this.currentRoad.height)
        this.currentRoad.wide = Number(this.currentRoad.wide)
        if (this.currentRoad.id != null) {
          // 更新巷道
          uRoad(this.currentRoad).then((response) => {
            if (response.code == 200) {
              this.$modal.msgSuccess(response.msg)
            }
            this.refreshRoad()
          })
        } else {
          // 新增巷道
          aRoad(this.currentRoad).then((response) => {
            console.log(response)
            this.currentRoad = response.data
            this.refreshRoad()
          })
        }
      } else {
        this.$modal.msgError('导线点数量小于两个')
      }
    },
    // 刷新数据
    async refreshRoad() {
      let _this = this
      _this.loading = true
      _this.editable = false
      await gRoad({ id: this.currentRoad.id }).then((res) => {
        _this.currentRoad = res.rows[0]
        _this.loading = false
        _this.resortAndCalcDis()
        this.$modal.msgSuccess('刷新成功')
      })
    },
    exportTemplate() {
      this.downloadTemp('exportTemplate/导线点_模板.xlsx', `导线点_模板.xlsx`)
    },
    showUOANavPointDialog(navPoint) {
      let _this = this
      _this.showUOANavPoint = true
      if (navPoint != null) {
        _this.navPointEdit = true
        _this.showUOANavPointTitle = '编辑导线点'
        _this.showUOANavPointForm = navPoint
      } else {
        _this.navPointEdit = false
        _this.showUOANavPointTitle = '新增导线点'
        _this.showUOANavPointForm = {
          navigationPointName: null,
          isActualPoint: 1,
          roadwayId: null,
          x: null,
          y: null,
          z: null,
          id: null,
          serialNumber: null
        }
      }
    },
    delNavPoint(index) {
      let _this = this
      _this.currentRoad.tnavigationPointList.splice(index, 1)
      _this.resortAndCalcDis()
    },
    resortAndCalcDis() {
      let _this = this
      // 重排序
      let i = 1
      let totalDis = 0
      if (_this.currentRoad.tnavigationPointList.length > 1) {
        for (let j = 0; j < _this.currentRoad.tnavigationPointList.length; j++) {
          let point = _this.currentRoad.tnavigationPointList[j]
          if (j === 0) {
            point.distanceToBefore = 0
            point.distanceToStart = 0
            totalDis += point.distanceToBefore
          } else {
            let lpoint = _this.currentRoad.tnavigationPointList[j - 1]
            let dis = Math.sqrt((point.x - lpoint.x) * (point.x - lpoint.x) + (point.y - lpoint.y) * (point.y - lpoint.y))
            point.distanceToBefore = Number(dis.toFixed(2))
            totalDis += point.distanceToBefore
            point.distanceToStart = Number(totalDis.toFixed(2))
          }
          point.serialNumber = i
          i++
        }
      } else {
        _this.currentRoad.tnavigationPointList[0].serialNumber = 1
      }
    },
    /* 保存数据到列表 */
    saveNavPointToList() {
      let _this = this
      if (!_this.navPointEdit) {
        // 新增导线点
        let newPoint = {
          navigationPointName: null,
          isActualPoint: 1,
          roadwayId: null,
          x: null,
          y: null,
          z: null,
          id: null,
          serialNumber: null
        }
        newPoint.navigationPointName = _this.showUOANavPointForm.navigationPointName
        newPoint.isActualPoint = _this.showUOANavPointForm.isActualPoint
        newPoint.roadwayId = _this.currentRoad.id
        newPoint.x = Number(_this.showUOANavPointForm.x)
        newPoint.y = Number(_this.showUOANavPointForm.y)
        newPoint.z = Number(_this.showUOANavPointForm.z)
        _this.currentRoad.tnavigationPointList.push(newPoint)
      }
      _this.resortAndCalcDis()
      // 重置form
      _this.showUOANavPointForm = {
        navigationPointName: null,
        isActualPoint: 1,
        roadwayId: null,
        x: null,
        y: null,
        z: null,
        id: null,
        serialNumber: null
      }
      _this.showUOANavPoint = false
    },
    getMyExcel(file, fileList) {
      let _this = this
      // 用FileReader来完成数据流的转换
      let reader = new FileReader()
      reader.onload = function(e) {
        let data = e.target.result
        //console.log(XLSX)
        //通过XLSX来解析出workbook（工作簿）也就导入的excel文件
        let wb = XLSX.read(data, { type: 'binary' })
        const sheetsName = wb.SheetNames[0]
        const jsonList = XLSX.utils.sheet_to_json(wb.Sheets[sheetsName])
        console.log('jsonList', jsonList)
        _this.currentRoad.tnavigationPointList = []
        jsonList.forEach((item) => {
          _this.currentRoad.tnavigationPointList.push({
            navigationPointName: item['导线点名称'],
            x: Number(Number(item['X']).toFixed(4)),
            y: Number(Number(item['Y']).toFixed(4)),
            z: Number(Number(item['Z']).toFixed(4)),
            isActualPoint: 1
          })
        })
        _this.resortAndCalcDis()
      }
      reader.readAsBinaryString(file.raw)
    },
    handleEditRoad() {
      let _this = this
      _this.editable = true
    }
  }
}
</script>

<template>
  <div>
    <el-row v-loading="loading">
      <el-col>
        <el-form
          :inline="true"
          :model="currentRoad"
          size="small"
        >
          <el-form-item label="巷道名称：">
            <el-input
              v-model="currentRoad.roadway"
              :disabled="!editable"
              style="width: 180px"
            />
          </el-form-item>
          <el-form-item label="断面形状：">
            <el-select
              v-model="currentRoad.faultShapId"
              :disabled="!editable"
              clearable
              style="width: 120px"
            >
              <el-option
                v-for="dict in shapeTpyes"
                :key="dict.faultShapId"
                :label="dict.shape"
                :value="dict.faultShapId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="宽：">
            <el-input
              v-model="currentRoad.wide"
              :disabled="!editable"
              style="width: 120px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
          <el-form-item label="墙高：">
            <el-input
              v-model="currentRoad.height"
              :disabled="!editable"
              style="width: 120px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
          <el-form-item label="方位角：">
            <el-input
              v-model="currentRoad.azimuthAngle"
              :disabled="!editable"
              clearable
              style="width: 120px"
            >
              <template slot="append">°</template>
            </el-input>
          </el-form-item>
          <el-form-item label="煤巷沿顶/底掘进："
                        v-if="currentRoad&&(currentRoad.type===1||currentRoad.type===2||currentRoad.type===3||currentRoad.type===9)">
            <el-select
              v-model="currentRoad.nearFloor"
              :disabled="!editable"
              clearable
              style="width: 120px"
            >
              <el-option
                v-for="dict in nearFloors"
                :key="dict.id"
                :label="dict.nearFloor"
                :value="dict.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="内外错:"
                        v-if="currentRoad&&(currentRoad.type===4||currentRoad.type===5||currentRoad.type===6)">
            <el-select
              v-model="currentRoad.inOrOut"
              :disabled="!editable"
              placeholder="请选择"
              style="width: 120px"
            >
              <el-option
                v-for="dict in inOrOuts"
                :key="dict.id"
                :label="dict.inout"
                :value="dict.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="两巷水平投影间距:" label-width="140px"
                        v-if="currentRoad&&(currentRoad.type===4||currentRoad.type===5||currentRoad.type===6)">
            <el-input
              v-model="currentRoad.inoutDistance"
              :disabled="!editable"
              placeholder="请输入"
              @blur="currentRoad.inoutDistance!='' ? Number(currentRoad.inoutDistance):0"
              style="width: 120px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
          <el-form-item label="煤层底板到底抽巷间距:" label-width="160px"
                        v-if="currentRoad&&(currentRoad.type===4||currentRoad.type===5||currentRoad.type===6)">
            <el-input
              v-model="currentRoad.distanceCoalRoadway"
              :disabled="!editable"
              placeholder="请输入"
              @blur="currentRoad.distanceCoalRoadway!='' ? Number(currentRoad.distanceCoalRoadway):0"
              style="width: 120px"
            >
              <template slot="append">m</template>
            </el-input>
          </el-form-item>
          <!--          <el-form-item label="埋深:">
                      <el-input
                        v-model="currentRoad.elevation"
                        :disabled="!editable"
                        placeholder="请输入"
                        @blur="currentRoad.elevation!='' ? Number(currentRoad.elevation):0"
                        style="width: 120px"
                      >
                        <template slot="append">m</template>
                      </el-input>
                    </el-form-item>-->
          <el-form-item>
            <el-button
              v-if="!editable"
              icon="el-icon-edit"
              size="mini"
              type="primary"
              plain
              @click="handleEditRoad"
            >修改
            </el-button>
            <el-button
              style="margin-left: 20px"
              v-if="editable"
              icon="el-icon-check"
              size="mini"
              type="primary"
              plain
              @click="UOARoad"
            >保存
            </el-button>
            <el-popconfirm
              title="确定删除此巷道吗？"
              @confirm="delRoad"
            >
              <el-button
                style="margin-left: 20px"
                slot="reference"
                v-if="currentRoad.id != null"
                icon="el-icon-delete"
                size="mini"
                plain
                type="danger"
              >删除
              </el-button>
            </el-popconfirm>
            <el-button
              style="margin-left: 20px"
              v-if="currentRoad.id != null"
              icon="el-icon-refresh"
              size="mini"
              plain
              type="default"
              @click="refreshRoad"
            >刷新
            </el-button>
          </el-form-item>
        </el-form>

        <el-card style="margin-top: 20px">
          <div slot="header" class="clearfix">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5" style="float: left">
                <span>巷道导线点</span>
              </el-col>

              <div style="float: right">
                <el-col :span="1.5">
                <span>已创建导线点{{
                    currentRoad.tnavigationPointList
                      ? currentRoad.tnavigationPointList.length
                      : 0
                  }}个</span>
                </el-col>
                <el-col :span="1.5">

                  <el-button
                    :disabled="!editable"
                    v-if="currentRoad != null"
                    size="mini"
                    style="margin: 0 5px"
                    plain
                    type="primary"
                    icon="el-icon-plus"
                    @click="showUOANavPointDialog(null)"
                  >增加
                  </el-button>
                </el-col>
                <el-col :span="1.5">
                  <el-upload
                    ref="upload"
                    action="xlsx.html"
                    :show-file-list="false"
                    :auto-upload="false"
                    accept=".xlsx,.xls"
                    :disabled="!editable"
                    :on-change="getMyExcel"
                  >
                    <el-button
                      :disabled="!editable"
                      size="mini"
                      style="margin: 0 5px"
                      plain
                      icon="el-icon-upload2"
                      type="primary"
                    >导入
                    </el-button>
                  </el-upload>
                </el-col>
                <el-col :span="1.5">
                  <el-button type="info" plain icon="el-icon-download" size="mini" @click="exportTemplate">导出模板
                  </el-button>
                </el-col>
              </div>
            </el-row>
          </div>
          <div>
            <el-table
              key="dchdxd"
              :data="currentRoad.tnavigationPointList"
              height="300"
            >
              <el-table-column
                type="index"
                width="50"
                label="序号"
              />
              <!--              <el-table-column align="center" label="序号" prop="id"/>-->
              <el-table-column
                :show-overflow-tooltip="true"
                align="center"
                label="名称"
                prop="navigationPointName"
              />
              <el-table-column
                :show-overflow-tooltip="true"
                align="center"
                label="X坐标"
                prop="x"
              />
              <el-table-column
                :show-overflow-tooltip="true"
                align="center"
                label="Y坐标"
                prop="y"
              />
              <el-table-column
                :show-overflow-tooltip="true"
                align="center"
                label="Z坐标"
                prop="z"
              />
              <el-table-column
                :show-overflow-tooltip="true"
                align="center"
                label="是否已掘"
                prop="isActualPoint"
              >
                <template slot-scope="scope">
                  <span v-if="scope.row.isActualPoint===0" style="color: #ea8111">
                  未掘(规划点)
                  </span>
                  <span v-if="scope.row.isActualPoint===1" style="color: #1e84e9">
                   已掘
                  </span>
                </template>
              </el-table-column>
              <el-table-column
                :show-overflow-tooltip="true"
                align="center"
                label="距上一点(平距:m)"
                prop="distanceToBefore"
              />
              <el-table-column
                :show-overflow-tooltip="true"
                align="center"
                label="距起点(平距:m)"
                prop="distanceToStart"
              />
              <el-table-column align="center" label="操作" width="120">
                <template slot-scope="scope">
                  <el-button
                    :disabled="!editable"
                    icon="el-icon-edit"
                    size="mini"
                    type="text"
                    @click="showUOANavPointDialog(scope.row)"
                  >修改
                  </el-button>
                  <el-button
                    :disabled="!editable"
                    icon="el-icon-delete"
                    size="mini"
                    type="text"
                    style="color: red"
                    @click="delNavPoint(scope.$index)"
                  >删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog :title="showUOANavPointTitle" :visible.sync="showUOANavPoint" width="300px" append-to-body>
      <el-form ref="showUOANavPointForm" :model="showUOANavPointForm" label-width="100px" :inline="true">
        <el-form-item label="导线点名称" prop="x">
          <el-input v-model="showUOANavPointForm.navigationPointName" placeholder="" style="width: 140px"/>
        </el-form-item>
        <el-form-item label="x" prop="x">
          <el-input v-model="showUOANavPointForm.x" placeholder="" style="width: 140px"/>
        </el-form-item>
        <el-form-item label="y" prop="y">
          <el-input v-model="showUOANavPointForm.y" placeholder="" style="width: 140px"/>
        </el-form-item>
        <el-form-item label="z" prop="z">
          <el-input v-model="showUOANavPointForm.z" placeholder="" style="width: 140px"/>
        </el-form-item>
        <el-form-item label="是否已掘" prop="z">
          <el-select
            v-model="showUOANavPointForm.isActualPoint"
            placeholder="请选择"
            style="width: 140px"
          >
            <el-option
              v-for="dict in navIsActual"
              :key="dict.id"
              :label="dict.label"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
        <br>
        <el-button type="primary" size="mini" @click="saveNavPointToList" style="float: right">确定</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>
