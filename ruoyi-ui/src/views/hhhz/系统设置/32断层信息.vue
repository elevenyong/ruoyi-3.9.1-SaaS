<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="5">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="98px">
          <el-form-item label="选择工作面" prop="workfaceId">
            <el-select v-model="currentWorkfaceId" placeholder="请选择工作面" @change="setCurrentWorkface">
              <el-option v-for="item in workfaceList" :key="item.id" :label="item.workfaceName"
                         :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>-->
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="geofaultList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id"/>
      <el-table-column label="断层名称" align="center" prop="name"/>
      <el-table-column label="正/逆断层" align="center" prop="faultType" :formatter="formatFault"/>
      <el-table-column label="断层面点" align="center" prop="inclination">
        <template slot-scope="scope">
          <el-button type="text" @click="showFaultPoint(scope.row)">断层面点</el-button>
        </template>
      </el-table-column>
      <el-table-column label="倾角(°)" align="center" prop="inclination"/>
      <el-table-column label="倾向" align="center" prop="azimuth"/>
      <!--      <el-table-column label="x坐标" align="center" prop="centerX"/>
            <el-table-column label="y坐标" align="center" prop="centerY"/>
            <el-table-column label="z坐标" align="center" prop="centerZ"/>-->
      <el-table-column label="断层落差(m)" align="center" prop="faultHeight"/>
      <el-table-column label="断层影响范围(与断层落差的比值)" width="130" align="center" prop="influenceScope"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            style="color: red"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

<!--    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />-->

    <!-- 添加或修改断层对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="断层名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入断层名称"/>
        </el-form-item>
        <el-form-item label="正\逆断层" prop="faultType">
          <el-radio v-model="form.faultType" :label="0">正断层</el-radio>
          <el-radio v-model="form.faultType" :label="1">逆断层</el-radio>
        </el-form-item>
        <el-form-item label="方位角" prop="azimuth">
          <el-input v-model="form.azimuth" @blur="form.azimuth = Number(form.azimuth)" placeholder="请输入方位角">
            <template slot="append">°</template>
          </el-input>
        </el-form-item>
        <el-form-item label="倾角" prop="inclination">
          <el-input v-model="form.inclination" @blur="form.inclination = Number(form.inclination)" placeholder="请输入倾角">
            <template slot="append">°</template>
          </el-input>
        </el-form-item>
        <el-form-item label="断层高度" prop="faultHeight">
          <el-input v-model="form.faultHeight" @blur="form.faultHeight = Number(form.faultHeight)" placeholder="请输入断层高度">
            <template slot="append">m</template>
          </el-input>
        </el-form-item>
        <el-form-item label="影响范围" prop="faultHeight">
          <el-input v-model="form.influenceScope" @blur="form.influenceScope = Number(form.influenceScope)" placeholder="请输入断层影响范围">
            <template slot="append">倍</template>
          </el-input>
        </el-form-item>
        <el-form-item label="断层面点" prop="faultHeight">
          <el-row :gutter="10">
            <el-col :span="3.5">
              <el-button size="mini" plain type="primary" icon="el-icon-plus" @click="handleAddFaultPoint">新增断层点
              </el-button>
            </el-col>
            <el-col :span="3.5">
              <el-upload
                ref="upload"
                action="xlsx.html"
                :show-file-list="false"
                :auto-upload="false"
                accept=".xlsx,.xls"
                :on-change="getMyExcel"
              >
                <el-button size="mini" plain type="primary" icon="el-icon-upload2">导入数据</el-button>
              </el-upload>
            </el-col>
            <el-col :span="3.5">
              <el-button type="info" plain icon="el-icon-download" size="mini" @click="exportTemplate">导出模板
              </el-button>
            </el-col>
          </el-row>
          <el-table
            key="uuid"
            :data="faultPointsList"
            height="400"
          >
            <el-table-column label="序号" type="index"/>
            <el-table-column label="x" prop="floorCoordinatex" align="center"/>
            <el-table-column label="y" prop="floorCoordinatey" align="center"/>
            <el-table-column label="z" prop="floorCoordinatez" align="center"/>
            <el-table-column label="操作" prop="volume" width="155px" align="center">
              <template slot-scope="scope">
                <el-button size="mini" type="text" icon="el-icon-edit"
                           v-if="scope.row.id" @click="handleEditFaultPoints(scope.row)">
                  修改
                </el-button>
                <el-button size="mini" type="text" icon="el-icon-delete" style="color: red"
                           @click="handleDeleteFaultPoints(scope.$index)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="断层面点" :visible.sync="showFaultPointsDialog" width="900px" append-to-body>
      <el-table
        key="uuid"
        :data="faultPointsList"
      >
        <el-table-column label="序号" type="index"/>
        <el-table-column label="x" prop="floorCoordinatex" align="center"/>
        <el-table-column label="y" prop="floorCoordinatey" align="center"/>
        <el-table-column label="z" prop="floorCoordinatez" align="center"/>
        <!--        <el-table-column label="操作" prop="volume" width="155px" align="center">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit"
                               v-if="scope.row.id" @click="handleEditFaultPoints(scope.row)">
                      修改
                    </el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" style="color: red"
                               @click="handleDeleteFaultPoints(scope.$index)">删除
                    </el-button>
                  </template>
                </el-table-column>-->
      </el-table>
    </el-dialog>
    <!-- 添加或修改断层面点对话框 -->
    <el-dialog :title="pointTitle" :visible.sync="aoeFaultPointShow" width="500px" append-to-body>
      <el-form ref="form" :model="aoeFaultPoint" label-width="80px">
        <el-form-item label="X" prop="floorCoordinatex">
          <el-input v-model="aoeFaultPoint.floorCoordinatex"
                    @blur="aoeFaultPoint.floorCoordinatex = Number(aoeFaultPoint.floorCoordinatex)"
                    placeholder="请输入X"/>
        </el-form-item>
        <el-form-item label="Y" prop="floorCoordinatey">
          <el-input v-model="aoeFaultPoint.floorCoordinatey"
                    @blur="aoeFaultPoint.floorCoordinatey = Number(aoeFaultPoint.floorCoordinatey)"
                    placeholder="请输入Y"/>
        </el-form-item>
        <el-form-item label="Z" prop="floorCoordinatez">
          <el-input v-model="aoeFaultPoint.floorCoordinatez"
                    @blur="aoeFaultPoint.floorCoordinatez = Number(aoeFaultPoint.floorCoordinatez)"
                    placeholder="请输入Z"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFaultPointForm">确 定</el-button>
        <el-button @click="cancelFaultPointForm">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addGeofault, delGeofault, getGeofault, listGeofault, updateGeofault } from '@/api/hhhz/duanceng'
import { getCoalminingWorkingfaceById, listCoalminingworkingface } from '@/api/hhhz/coalMiningWorkingFace'
import { listCoalPointInfo } from '@/api/hhhz/coalpoint'
import { listRockinfo } from '@/api/hhhz/rockinfo'
import * as XLSX from 'xlsx'

export default {
  name: 'Geofault',
  data() {
    return {
      pointTitle: '',
      aoeFaultPointShow: false,
      aoeFaultPoint: {},
      showFaultPointsDialog: false,
      faultPointsList: [],
      currentWorkfaceId: null,
      currentCoalId: null,
      currentFault: {},
      workfaceList: [],
      rockInfoList: [],
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
      // 断层表格数据
      geofaultList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        azimuth: null,
        inclination: null,
        centerX: null,
        centerY: null,
        centerZ: null,
        faultHeight: null,
        faultType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  async created() {
    await this.getWorkfaceList()
    //this.getList()
    this.$message.success('请选择工作面！')
  },
  methods: {
    exportTemplate() {
      this.downloadTemp('exportTemplate/断层点_模板.xlsx', `断层点_模板.xlsx`)
    },
    handleDeleteFaultPoints(index) {
      let _this = this
      _this.faultPointsList.splice(index, 1)
    },
    handleAddFaultPoint() {
      this.aoeFaultPointShow = true
      this.aoeFaultPoint = {
        floorCoordinatex: null,
        floorCoordinatey: null,
        floorCoordinatez: null
      }
      this.pointTitle = '新增断层面点'
    },
    handleEditFaultPoints(row) {
      this.aoeFaultPointShow = true
      this.aoeFaultPoint = row
      this.pointTitle = '修改断层面点'
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
        _this.faultPointsList = []
        jsonList.forEach((item) => {
          console.log(item)
          _this.faultPointsList.push({
            workfaceId: _this.currentWorkfaceId,
            coalThick: 0.0,
            floorCoordinatex: Number(Number(item['X']).toFixed(4)),
            floorCoordinatey: Number(Number(item['Y']).toFixed(4)),
            floorCoordinatez: Number(Number(item['Z']).toFixed(4)),
            coalinfoId: _this.currentCoalId,
            dataType: 2,
            isuse: 0
          })
        })
      }
      reader.readAsBinaryString(file.raw)
    },
    showFaultPoint(fault) {
      this.currentFault = fault
      this.faultPointsList = fault.geoFaultPoints
      this.showFaultPointsDialog = true
    },
    async getWorkfaceList() {
      let _this = this
      await listCoalminingworkingface({}).then(res => {
        _this.workfaceList = res.rows
      })
    },
    getFaultList() {
      let _this = this
      listGeofault({ workfaceId: this.currentWorkfaceId }).then((res) => {
        _this.geofaultList = res.rows
      })
      this.loading = false
    },
    setCurrentWorkface() {
      let _this = this
      this.getFaultList()
      listRockinfo({ workfaceId: this.currentWorkfaceId }).then((res) => {
        (res.rows).forEach(item => {
          if (item.serialNumber === 0) {
            _this.currentCoalId = item.id
          }
        })
      })
    },
    formatFault(row) {
      return row.faultType === 0 ? '正断层' : row.faultType === 1 ? '逆断层' : ''
    },
    /** 查询断层列表 */
    getList() {
      this.loading = true
      listGeofault(this.queryParams).then(response => {
        this.geofaultList = response.rows
        this.total = response.total
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
        name: null,
        azimuth: null,
        inclination: null,
        centerX: null,
        centerY: null,
        centerZ: null,
        faultHeight: null,
        faultType: null,
        geoFaultPoints: null
      }
      this.faultPointsList = []
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加断层'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      let _this = this
      _this.reset()
      const id = row.id || this.ids
      getGeofault(id).then(response => {
        _this.form = response.data
        _this.faultPointsList = _this.form.geoFaultPoints
        _this.open = true
        _this.title = '修改断层'
      })
    },
    submitFaultPointForm() {
      if (this.aoeFaultPoint.id != null) {
        // 修改
        this.aoeFaultPoint = {}
      } else {
        // 新增
        this.aoeFaultPoint.dataType = 2
        this.aoeFaultPoint.geoFaultId = this.currentFault.id
        this.aoeFaultPoint.workfaceId = this.currentWorkfaceId
        this.aoeFaultPoint.coalinfoId = this.currentCoalId
        this.faultPointsList.push(this.aoeFaultPoint)
        this.aoeFaultPoint = {}
      }
      this.aoeFaultPointShow = false
    },
    cancelFaultPointForm() {
      this.aoeFaultPointShow = false
    },
    /** 提交按钮 */
    submitForm() {
      this.form.geoFaultPoints = this.faultPointsList
      this.form.workfaceId = this.currentWorkfaceId
      this.form.length = 0
      if (this.form.id != null) {
        updateGeofault(this.form).then(response => {
          this.$modal.msgSuccess('修改成功')
          this.open = false
          this.getFaultList()
        })
      } else {
        addGeofault(this.form).then(response => {
          this.$modal.msgSuccess('新增成功')
          this.open = false
          this.getFaultList()
        })
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除断层编号为"' + ids + '"的数据项？').then(function() {
        return delGeofault(ids)
      }).then(() => {
        this.getFaultList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/geofault/export', {
        ...this.queryParams
      }, `geofault_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
