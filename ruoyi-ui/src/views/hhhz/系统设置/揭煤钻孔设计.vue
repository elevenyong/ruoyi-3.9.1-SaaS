<template>
  <div class="app-container">
    <el-row>
      <el-col :span="6" class="card-box">
        <el-card style="height: 85vh">
          <div slot="header" class="clearfix">
            <span>钻孔设计参数</span>
          </div>
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="188px">
            <el-form-item label="煤层厚度" prop="d">
              <el-input
                v-model="queryParams.d"
                placeholder="煤层厚度"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="石门高度" prop="H">
              <el-input
                v-model="queryParams.H"
                placeholder="石门高度"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="揭煤工作面与煤层距离" prop="N">
              <el-input
                v-model="queryParams.N"
                placeholder="揭煤工作面与煤层距离"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="石门宽度" prop="W">
              <el-input
                v-model="queryParams.W"
                placeholder="石门宽度"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="石门上/下底宽(梯形)" prop="Wp">
              <el-input
                v-model="queryParams.Wp"
                placeholder="石门上/下底宽(梯形)"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="石门帮高(拱形)" prop="Hp">
              <el-input
                v-model="queryParams.Hp"
                placeholder="石门帮高(拱形)"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="煤层倾角" prop="beta">
              <el-input
                v-model="queryParams.beta"
                placeholder="煤层倾角"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="石门与煤层走向夹角" prop="gama">
              <el-input
                v-model="queryParams.gama"
                placeholder="石门与煤层走向夹角"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="抽采半径" prop="R">
              <el-input
                v-model="queryParams.R"
                placeholder="抽采半径"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="上控制范围" prop="h1">
              <el-input
                v-model="queryParams.h1"
                placeholder="上控制范围"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="下控制范围" prop="h2">
              <el-input
                v-model="queryParams.h2"
                placeholder="下控制范围"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="左控制范围" prop="w1">
              <el-input
                v-model="queryParams.w1"
                placeholder="左控制范围"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="右控制范围" prop="w2">
              <el-input
                v-model="queryParams.w2"
                placeholder="右控制范围"
              >
                <template slot="append">m</template>
              </el-input>
            </el-form-item>
            <el-form-item label="巷头揭煤|辅助巷揭煤" prop="type">
              <el-select v-model="queryParams.type" placeholder="请选择">
                <el-option
                  v-for="item in types"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
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
            <el-table-column label="组号" align="center" prop="groupId"/>
            <el-table-column label="孔号" align="center" prop="drillId"/>
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
            <el-table-column label="见煤长度(m)" align="center" prop="distanceSeeCoal">
              <template slot-scope="scope">
                {{ Number(scope.row.distanceSeeCoal).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="穿煤长度(m)" align="center" prop="distanceThroughCoal">
              <template slot-scope="scope">
                {{ Number(scope.row.distanceThroughCoal).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="过煤长度(m)" align="center" prop="distanceAfterCoal">
              <template slot-scope="scope">
                {{ Number(scope.row.distanceAfterCoal).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="钻孔总长度(m)" align="center" prop="distanceTotal">
              <template slot-scope="scope">
                {{ Number(scope.row.distanceTotal).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="开孔高度(m)" align="center" prop="openingHeight">
              <template slot-scope="scope">
                {{ Number(scope.row.openingHeight).toFixed(2) }}
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { drillingUncovering } from '@/api/hhhz/drillingdes'
import ExportJsonExcel from 'js-export-excel'

export default {
  name: 'Info',
  data() {
    return {
      // 布孔方式
      types: [
        { value: 0, label: '巷头揭煤' },
        { value: 1, label: '辅助巷揭煤' }
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
        'd': 3, // 煤厚
        'H': 3, // 石门高度
        'N': 7, // 揭煤工作面与煤层距离
        'W': 4, // 石门宽度
        'Wp': 1,  // 石门上/下底宽(梯形)
        'Hp': 0.9, // 石门帮高(拱形)
        'beta': 30, // 煤层倾角
        'gama': 10, // 石门与煤层走向夹角
        'R': 5,  // 抽采半径
        'h1': 6, // 上控制范围
        'h2': 6, // 下控制范围
        'w1': 6, // 左控制范围
        'w2': 6, // 右控制范围
        'type': 0  // 0=巷头揭煤||1=辅助巷揭煤
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
      drillingUncovering(this.queryParams).then(response => {
        this.infoList = response.data.drillingData
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
        'd': 3, // 煤厚
        'H': 3, // 石门高度
        'N': 7, // 揭煤工作面与煤层距离
        'W': 4, // 石门宽度
        'Wp': 1,  // 石门上/下底宽(梯形)
        'Hp': 0.9, // 石门帮高(拱形)
        'beta': 30, // 煤层倾角
        'gama': 10, // 石门与煤层走向夹角
        'R': 5,  // 抽采半径
        'h1': 6, // 上控制范围
        'h2': 6, // 下控制范围
        'w1': 6, // 左控制范围
        'w2': 6, // 右控制范围
        'type': 0  // 0=巷头揭煤||1=辅助巷揭煤
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
          '组号': item.groupId,
          '孔号': item.drillId,
          '方位角(°)': item.azimuth.toFixed(1),
          '倾角(°)': item.tiltAngle.toFixed(1),
          '见煤长度(m)': item.distanceSeeCoal.toFixed(2),
          '穿煤长度(m)': item.distanceThroughCoal.toFixed(2),
          '过煤长度(m)': item.distanceAfterCoal.toFixed(2),
          '钻孔总长度(m)': item.distanceTotal.toFixed(2),
          '开孔高度(m)': item.openingHeight.toFixed(2)
        })
      })
      this.exportToExcel(tableExcel)
      console.log(tableExcel)
    },
    exportToExcel(tableExcel) {
      let option = {}
      option.fileName = '揭煤钻孔设计_施工参数表'
      option.datas = [
        {
          sheetData: tableExcel,
          sheetName: '施工参数表',
          sheetFilter: [
            '组号',
            '孔号',
            '方位角(°)',
            '倾角(°)',
            '见煤长度(m)',
            '穿煤长度(m)',
            '过煤长度(m)',
            '钻孔总长度(m)',
            '开孔高度(m)'
          ],
          sheetHeader: [
            '组号',
            '孔号',
            '方位角(°)',
            '倾角(°)',
            '见煤长度(m)',
            '穿煤长度(m)',
            '过煤长度(m)',
            '钻孔总长度(m)',
            '开孔高度(m)'
          ]
        }
      ]
      var toExcel = new ExportJsonExcel(option)
      toExcel.saveExcel()
    }
  }
}
</script>
