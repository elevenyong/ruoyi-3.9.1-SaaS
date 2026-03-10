<template>
  <div class="app-container">
    <el-row>
      <el-col :span="6" class="card-box">
        <el-card style="height: 85vh">
          <div slot="header" class="clearfix">
            <span>钻孔设计参数</span>
          </div>
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="188px">
            <el-form-item label="煤层厚度" prop="hc">
              <el-input
                v-model="queryParams.hc"
                placeholder="煤层厚度"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="煤层倾角" prop="alf">
              <el-input
                v-model="queryParams.alf"
                placeholder="煤层倾角"
              >
                <template slot="append">°</template>
              </el-input>
            </el-form-item>
            <el-form-item label="煤层底板到底抽巷间距" prop="h2">
              <el-input
                v-model="queryParams.h2"
                placeholder="煤层底板到底抽巷间距"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="底抽巷宽" prop="wd">
              <el-input
                v-model="queryParams.wd"
                placeholder="底抽巷宽"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="底抽巷高" prop="hd">
              <el-input
                v-model="queryParams.hd"
                placeholder="底抽巷高"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="底抽巷方位角" prop="betd">
              <el-input
                v-model="queryParams.betd"
                placeholder="底抽巷方位角"
              >
                <template slot="append">°</template>
              </el-input>
            </el-form-item>
            <el-form-item label="抽采半径" prop="radius">
              <el-input
                v-model="queryParams.radius"
                placeholder="抽采半径"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="抽采范围小值" prop="lMin">
              <el-input
                v-model="queryParams.lMin"
                placeholder="抽采范围小值"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="抽采范围大值" prop="lMax">
              <el-input
                v-model="queryParams.lMax"
                placeholder="抽采范围大值"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="钻杆长度" prop="perLen">
              <el-input
                v-model="queryParams.perLen"
                placeholder="钻杆长度"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="钻孔起始直线段数量" prop="startDrillNum">
              <el-input
                v-model="queryParams.startDrillNum"
                placeholder="钻孔起始直线段数量"
              >
                <template slot="append">根</template>
              </el-input>
            </el-form-item>
            <el-form-item label="钻孔孔径" prop="rz">
              <el-input
                v-model="queryParams.rz"
                placeholder="钻孔孔径"
              >
                <template slot="append">mm</template>
              </el-input>
            </el-form-item>
            <el-form-item label="测量间距" prop="measureSpace">
              <el-input
                v-model="queryParams.measureSpace"
                placeholder="测量间距"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="最大角度变化量(一个测量间距里对应多少度的角度变化)" prop="maxDeltaAng">
              <el-input
                v-model="queryParams.maxDeltaAng"
                placeholder="最大角度变化量(一个测量间距里对应多少度的角度变化)"
              >
                <template slot="append">°</template>
              </el-input>
            </el-form-item>
            <el-form-item label="最大初始角" prop="maxStartAng">
              <el-input
                v-model="queryParams.maxStartAng"
                placeholder="最大初始角"
              >
                <template slot="append">°</template>
              </el-input>
            </el-form-item>
            <el-form-item style="margin: auto;display: block;text-align: center;">
              <el-button type="primary" size="mini" @click="handleQuery">生成</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

        </el-card>
      </el-col>
      <el-col :span="18" class="card-box">
        <el-card style="height: 85vh">
          <div slot="header" class="clearfix">
            <span>施工数据表</span>
            <el-button style="float: right; padding: 3px 0" @click="handleExport" type="text">导出数据</el-button>
          </div>
          <el-table v-loading="loading" :data="infoList" height="1000" @selection-change="handleSelectionChange">
            <el-table-column label="孔深" align="center" prop="deep"/>
            <el-table-column label="方位角(°)" align="center" prop="azimuth">
              <template slot-scope="scope">
                {{ Number(scope.row.azimuth).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="倾角(°)" align="center" prop="tiltAngle">
              <template slot-scope="scope">
                {{ Number(scope.row.tiltAngle).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="偏差(m)" align="center" prop="deviation">
              <template slot-scope="scope">
                {{ Number(scope.row.deviation).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="投影深度(m)" align="center" prop="projDepth">
              <template slot-scope="scope">
                {{ Number(scope.row.projDepth).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="左右偏差(m)" align="center" prop="leftRightDeviation">
              <template slot-scope="scope">
                {{ Number(scope.row.leftRightDeviation).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="上下偏差(m)" align="center" prop="topBottomDeviation">
              <template slot-scope="scope">
                {{ Number(scope.row.topBottomDeviation).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="东偏(m)" align="center" prop="east">
              <template slot-scope="scope">
                {{ Number(scope.row.east).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="北偏(m)" align="center" prop="north">
              <template slot-scope="scope">
                {{ Number(scope.row.north).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="高程(m)" align="center" prop="elevation">
              <template slot-scope="scope">
                {{ Number(scope.row.elevation).toFixed(2) }}
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { drillingDirectional } from '@/api/hhhz/drillingdes'
import ExportJsonExcel from 'js-export-excel'

export default {
  name: 'Info',
  data() {
    return {
      // 布孔方式
      bukongs: [
        { value: 3, label: '三花布孔' },
        { value: 4, label: '正方形布孔' }
      ],
      // 内外错
      neiwaicuos: [
        { value: true, label: '内错' },
        { value: false, label: '外错' }
      ],
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
      // 分站信息表格数据
      infoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        'hc': 4.3, // 煤层厚度
        'alf': 15, // 煤层倾角
        'h2': 12,  // 煤层底板到底抽巷间距
        'wd': 4.6, // 底抽巷宽
        'hd': 3,   // 底抽巷高（hd）
        'betd': 0, // 底抽巷方位角（βd）
        'radius': 5,  // 抽采半径(r)
        'lMin': 100,  // 抽采范围小值
        'lMax': 110,  // 抽采范围大值
        'perLen': 1,  // 钻杆长度
        'startDrillNum': 3,  // 钻孔起始直线段数量
        'rz': 60,  // 钻孔孔径
        'measureSpace': 3,  // 测量间距
        'maxDeltaAng': 1,   // 最大角度变化量(一个测量间距里对应多少度的角度变化)
        'maxStartAng': 15  // 最大初始角
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    //this.getList();
  },
  methods: {
    /** 查询分站信息列表 */
    getList() {
      this.loading = true
      drillingDirectional(this.queryParams).then(response => {
        this.infoList = response.data.directionalDrillingData
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
        'hc': 4.3, // 煤层厚度
        'alf': 15, // 煤层倾角
        'h2': 12,  // 煤层底板到底抽巷间距
        'wd': 4.6, // 底抽巷宽
        'hd': 3,   // 底抽巷高（hd）
        'betd': 0, // 底抽巷方位角（βd）
        'radius': 5,  // 抽采半径(r)
        'lMin': 100,  // 抽采范围小值
        'lMax': 110,  // 抽采范围大值
        'perLen': 1,  // 钻杆长度
        'startDrillNum': 3,  // 钻孔起始直线段数量
        'rz': 60,  // 钻孔孔径
        'measureSpace': 3,  // 测量间距
        'maxDeltaAng': 1,   // 最大角度变化量(一个测量间距里对应多少度的角度变化)
        'maxStartAng': 15  // 最大初始角
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    }
    ,
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
    }
    ,
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    }
    ,
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加分站信息'
    }
    ,
    /**
     * 条带钻孔设计
     */

    /** 修改按钮操作 */
    /* handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改分站信息'
      })
    }, */
    /** 提交按钮 */
    /* submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    }, */
    /** 删除按钮操作 */
    /* handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除分站信息编号为"' + ids + '"的数据项？').then(function() {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }, */
    /** 导出按钮操作 */
    handleExport() {
      this.download()
    },

    download() {
      let tableExcel = []
      this.infoList.map(item => {
        tableExcel.push({
          '孔深': item.deep.toFixed(2),
          '方位角(°)': item.azimuth.toFixed(2),
          '倾角(°)': item.tiltAngle.toFixed(2),
          '偏差(m)': item.deviation.toFixed(2),
          '投影深度(m)': item.projDepth.toFixed(2),
          '左右偏差(m)': item.leftRightDeviation.toFixed(2),
          '上下偏差(m)': item.topBottomDeviation.toFixed(2),
          '东偏(m)': item.east.toFixed(2),
          '北偏(m)': item.north.toFixed(2),
          '高程(m)': item.elevation.toFixed(2)
        })
      })
      this.exportToExcel(tableExcel)
      console.log(tableExcel)
    },
    exportToExcel(tableExcel) {
      let option = {}
      option.fileName = '定向钻孔设计_施工参数表'
      option.datas = [
        {
          sheetData: tableExcel,
          sheetName: '施工参数表',
          sheetFilter: [
            '孔深',
            '方位角(°)',
            '倾角(°)',
            '偏差(m)',
            '投影深度(m)',
            '左右偏差(m)',
            '上下偏差(m)',
            '东偏(m)',
            '北偏(m)',
            '高程(m)'
          ],
          sheetHeader: [
            '孔深',
            '方位角(°)',
            '倾角(°)',
            '偏差(m)',
            '投影深度(m)',
            '左右偏差(m)',
            '上下偏差(m)',
            '东偏(m)',
            '北偏(m)',
            '高程(m)'
          ]
        }
      ]
      var toExcel = new ExportJsonExcel(option)
      toExcel.saveExcel()
    }
  }
}
</script>
