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
      <el-form-item label="验收日期" prop="checkingDate" label-width="98px">
        <el-date-picker
          v-model="queryParams.date"
          type="date"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions"
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
        <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button> -->
      </el-form-item>
    </el-form>

    <div class="pdf-view">
      <div id="pdf-content"></div>
    </div>
  </div>
</template>

<script>
import { gCedianL, gWasiribaoL } from '@/api/hhhz/cedian'
import { jsPDF } from 'jspdf'
import { autoTable } from 'jspdf-autotable'
import PDFObject from 'pdfobject'
import '@/utils/simhei-normal'
import { ClientRequest } from 'http'
//const doc = new jsPDF()

export default {
  name: 'TDrainageParameter',
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        },
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date())
            }
          }
        ]
      },
      selectDate: '',
      selectCdIds: [],
      cdOptions: [],
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
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        /* index: 1,
                size: 10 */
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      doc: null
    }
  },
  created() {
    //this.getList();
    //this.getCeDianList();
  },
  mounted() {
    /* doc.text("Hello world!", 10, 10);
        doc.save("aa.pdf"); */
    this.getCeDianList()
    PDFObject.embed(
      this.createPDF(
        new Date().getFullYear() +
        '年' +
        (new Date().getMonth() + 1) +
        '月' +
        new Date().getDate() +
        '日',
        []
      ),
      '#pdf-content'
    )
  },
  methods: {
    async getCeDianList() {
      await gCedianL({}).then((response) => {
        console.log('gCeDianList', response)
        this.cdOptions = response.rows
      })
    },
    createPDF(date, tbody) {
      this.doc = new jsPDF('landscape')
      let fonts = this.doc.getFontList()
      //console.log("fonts,", fonts);
      //this.doc.addFont("simsun.ttf");
      this.doc.setFont('simhei', 'normal')
      this.doc.setProperties({
        title: '瓦斯抽采小时报表',
        subject: '',
        author: 'hhhz',
        keywords: '瓦斯',
        creator: 'hhhz'
      })
      this.doc.setFontSize(20)
      this.doc.text('瓦斯抽采小时报表', 150, 20, null, null, 'center')
      this.doc.setFontSize(10)
      //this.doc.text("记录时间：2022年4月20日0点——2022年4月20日24点", 150, 30, null, null, "center");
      this.doc.text(
        '记录时间：' + date + '0点——' + date + '24点',
        150,
        30,
        null,
        null,
        'center'
      )
      this.doc.text('制表：', 250, 30, null, null, 'right')
      /*             this.doc.text("test", 0, 10);
            this.doc.text("test", 295, 10);
 */
      let headers = this.createHeaders([
        '泵站',
        '抽采负压（kPa）',
        '抽采时间',
        '抽采混量（m³）',
        '平均浓度（%）',
        '抽采纯量（m³）'
      ])
      /* this.doc.table(20, 30, this.generateData(5), headers, {
                font: 'simhei', //字体，如果不配置，表格中的中文仍会乱码
                textColor: [0, 0, 0],
            }); */
      this.doc.autoTable({
        /* body: [
                        { h1: '26瓦斯抽放泵站', h2: '41.07', h3: '24:00', h4: '22941.96', h5: '3.08', h6: '705.94' },
                        { h1: '26瓦斯抽放泵站', h2: '41.07', h3: '24:00', h4: '22941.96', h5: '3.08', h6: '705.94' },
                        { h1: '26瓦斯抽放泵站', h2: '41.07', h3: '24:00', h4: '22941.96', h5: '3.08', h6: '705.94' },
                        { h1: '26瓦斯抽放泵站', h2: '41.07', h3: '24:00', h4: '22941.96', h5: '3.08', h6: '705.94' },

                    ], */
        body: tbody,
        columns: [
          { header: '泵站', dataKey: 'h1' },
          { header: '抽采负压（kPa）', dataKey: 'h2' },
          { header: '抽采时长', dataKey: 'h3' },
          { header: '抽采混量（m3）', dataKey: 'h4' },
          { header: '平均浓度（%）', dataKey: 'h5' },
          { header: '抽采纯量（m3）', dataKey: 'h6' }
        ],
        styles: {
          font: 'simhei',
          halign: 'center',
          lineColor: [0, 0, 0],
          lineWidth: 0.2
        },
        headStyles: {
          fillColor: [255, 255, 255],
          fontSize: 12,
          textColor: [0, 0, 0]
        },
        /* footStyles: {
                        fillColor: [241, 196, 15],
                        fontSize: 15,
                    }, */
        bodyStyles: {
          fillColor: [255, 255, 255],
          /* textColor: 240, */
          textColor: [0, 0, 0]
        },
        rowStyles: {
          0: {
            rowHeight: 'auto'
          },
          1: {
            rowHeight: 'auto'
          },
          2: {
            rowHeight: 'auto'
          },
          3: {
            rowHeight: 'auto'
          },
          4: {
            rowHeight: 'auto'
          },
          5: {
            rowHeight: 'auto'
          },
          6: {
            rowHeight: 'auto'
          },
          7: {
            rowHeight: 'auto'
          },
          8: {
            rowHeight: 'auto'
          },
          9: {
            rowHeight: 'auto'
          },
          10: {
            rowHeight: 'auto'
          },
          11: {
            rowHeight: 30
          },
          12: {
            rowHeight: 'auto'
          }
        },
        /* alternateRowStyles: {
                        fillColor: [74, 96, 117],
                    }, */
        startY: 35
        /* theme: 'grid' */
      })
      return this.doc.output('datauristring')
    },
    createHeaders(keys) {
      var result = []
      for (var i = 0; i < keys.length; i += 1) {
        result.push({
          id: keys[i],
          name: keys[i],
          prompt: keys[i],
          width: 40,
          align: 'center',
          padding: 0
        })
      }
      return result
    },
    generateData(amount) {
      var result = []
      var data = {
        泵站: '26瓦斯抽放泵站',
        '抽采负压（kPa）': '41.07',
        抽采时间: '24:00',
        '抽采混量（m³）': '22941.96',
        '平均浓度（%）': '3.08',
        '抽采纯量（m³）': '705.94'
      }
      for (var i = 0; i < amount; i += 1) {
        //data.id = (i + 1).toString();
        result.push(Object.assign({}, data))
      }
      return result
    },
    /** 查询tDrainageParameter列表 */
    getList() {
      this.loading = true
      gWasiribaoL(this.queryParams).then((response) => {
        console.log(response)
        let mList = response.data
        let tableBody = []
        /* [
                    { h1: '26瓦斯抽放泵站', h2: '41.07', h3: '24:00', h4: '22941.96', h5: '3.08', h6: '705.94' },
                    { h1: '26瓦斯抽放泵站', h2: '41.07', h3: '24:00', h4: '22941.96', h5: '3.08', h6: '705.94' },
                ] */
        let mNames = []
        if (mList.length >= 1) {
          mList.forEach((e) => {
            if (mNames.indexOf(e.measuringPointName) === -1) {
              mNames.push(e.measuringPointName)
            }
          })
          mNames.forEach((name) => {
            let body = {}
            body.h1 = name
            body.h3 = '24:00'
            mList.forEach((e) => {
              if (name === e.measuringPointName) {
                switch (e.kind) {
                  case '抽采负压传感器': {
                    body.h2 = e.avgvalue
                    break
                  }
                  case '混合流量计量器': {
                    body.h4 = e.sumvalue
                    break
                  }
                  case '甲烷浓度传感器': {
                    body.h5 = e.avgvalue
                    break
                  }
                }
              }
            })
            body.h6 = (body.h4 * body.h5 * 0.01).toFixed(2)
            tableBody.push(body)
          })
        }
        let ds = this.queryParams.date.split('-')
        PDFObject.embed(
          this.createPDF(ds[0] + '年' + ds[1] + '月' + ds[2] + '日', tableBody),
          '#pdf-content'
        )
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
        boreholeId: null,
        datedrainagestart: null,
        concentrationdrainagestart: null,
        concentrationbore: null,
        vacuumbore: null,
        estimatedtimedrainagedone: null,
        measureDate: null,
        measureperson: null,
        tabulator: null,
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
      console.log('selectCdIds', this.selectCdIds)
      if (this.selectCdIds && this.selectCdIds.length >= 1) {
        this.queryParams.measurePointId = JSON.stringify(this.selectCdIds)
          .replace('[', '')
          .replace(']', '')
      }
      console.log('selectDate', this.selectDate)
      /* if (this.selectDate) {
                this.queryParams.date = this.selectDate.toLocaleString().replaceAll("/", "-").subString(0, 10);
            } */
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
      this.title = '添加tDrainageParameter'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTDrainageParameter(id).then((response) => {
        this.form = response.data
        this.open = true
        this.title = '修改tDrainageParameter'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateTDrainageParameter(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addTDrainageParameter(this.form).then((response) => {
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
        .confirm('是否确认删除tDrainageParameter编号为"' + ids + '"的数据项？')
        .then(function() {
          return delTDrainageParameter(ids)
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
        '//tDrainageParameter/export',
        {
          ...this.queryParams
        },
        `tDrainageParameter_${new Date().getTime()}.xlsx`
      )
    }
  }
}
</script>
<style>
#pdf-content {
  width: 80vw;
  height: 80vh;
}
</style>
