<script>
import { getToken } from '@/utils/auth'
import { createMxCad } from 'mxcad'
import { dFile, gFile } from '@/api/hhhz/file'
import { listCoalminingworkingface } from '@/api/hhhz/coalMiningWorkingFace'
import VueOfficeDocx from '@vue-office/docx'
import '@vue-office/docx/lib/index.css'
import VueOfficeExcel from '@vue-office/excel'
import '@vue-office/excel/lib/index.css'
import VueOfficePdf from '@vue-office/pdf'
import VueOfficePptx from '@vue-office/pptx'
import { downloadDoc } from '@/utils/request'

export default {
  components: {
    VueOfficeDocx, VueOfficeExcel, VueOfficePdf, VueOfficePptx
  },

  data() {
    return {
      docx: {
        path: null,
        title: null,
        showDialog: false
      },
      excel: {
        path: null,
        title: null,
        showDialog: false
      },
      vpdf: {
        path: null,
        title: null,
        showDialog: false
      },
      pptx: {
        path: null,
        title: null,
        showDialog: false
      },
      cad: {
        path: null,
        title: null,
        showDialog: false
      },
      options: {
        xls: false,       //预览xlsx文件设为false；预览xls文件设为true
        minColLength: 0,  // excel最少渲染多少列，如果想实现xlsx文件内容有几列，就渲染几列，可以将此值设置为0.
        minRowLength: 0,  // excel最少渲染多少行，如果想实现根据xlsx实际函数渲染，可以将此值设置为0.
        widthOffset: 10,  //如果渲染出来的结果感觉单元格宽度不够，可以在默认渲染的列表宽度上再加 Npx宽
        heightOffset: 10, //在默认渲染的列表高度上再加 Npx高
        beforeTransformData: (workbookData) => {
          return workbookData
        }, //底层通过exceljs获取excel文件内容，通过该钩子函数，可以对获取的excel文件内容进行修改，比如某个单元格的数据显示不正确，可以在此自行修改每个单元格的value值。
        transformData: (workbookData) => {
          return workbookData
        } //将获取到的excel数据进行处理之后且渲染到页面之前，可通过transformData对即将渲染的数据及样式进行修改，此时每个单元格的text值就是即将渲染到页面上的内容
      },
      src: '',
      docFileList: [],
      loading: true,
      fileList: [],
      workfaceList: [],
      currentWorkfaceId: null,
      queryParams: {},
      iframeSrc: process.env.VUE_APP_MXCAD_URL || '/mxcad/index.html',
      uploadCADUrl: process.env.VUE_APP_BASE_API + '/fileupload/cad/',
      uploadDOCUrl: process.env.VUE_APP_BASE_API + '/fileupload/doc/',
      uploadHeaders: { Authorization: 'Bearer ' + getToken() }
    }
  },
  created() {
    this.getWorkfaceList()
  },
  mounted() {
  },
  methods: {
    dialogInit() {
      let _this = this
      _this.docx = {
        path: '',
        title: '',
        showDialog: false
      }
      _this.excel = {
        path: '',
        title: '',
        showDialog: false
      }
      _this.vpdf = {
        path: '',
        title: '',
        showDialog: false
      }
      _this.pptx = {
        path: '',
        title: '',
        showDialog: false
      }
      _this.cad = {
        path: '',
        title: '',
        showDialog: false
      }
    },
    beforeUpload(file) {
      let reader = new FileReader()
      reader.readAsArrayBuffer(file)
      reader.onload = (loadEvent) => {
        let arrayBuffer = loadEvent.target.result
        this.src = arrayBuffer
      }
      return false
    },
    async getWorkfaceList() {
      let _this = this
      await listCoalminingworkingface({}).then(res => {
        _this.workfaceList = res.rows
      })
    },
    async setCurrentWorkface(id) {
      let _this = this
      _this.currentWorkfaceId = id
      _this.loading = true
      _this.fileList = []
      await gFile({ workfaceId: _this.currentWorkfaceId }).then(res => {
        _this.fileList = res.rows
        _this.loading = false
      })
    },
    downloadDoc(row) {
      downloadDoc(
        process.env.VUE_APP_BASE_API + row.filePath,
        row.fileName
      )
    },
    /** 删除按钮操作 */
    deleteDoc(row) {
      let _this = this
      //const ids = row.id || this.ids
      const ids = (row.id ? row.id : this.ids.join(','))
      dFile(ids).then(() => {
        _this.refreshList()
        _this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    previewDoc(row) {
      let _this = this
      _this.dialogInit()
      console.log(row)
      if (row.fileType === 'dwg') {
        console.log('dwg')
        _this.cad.showDialog = true
        _this.cad.title = row.fileName
        setTimeout(function() {
          _this.cad.path = process.env.VUE_APP_BASE_API + row.filePath + '.mxweb'
          /* createMxCad({
            canvas: '#myCanvas',
            locateFile: function(fileName) {
              return './' + fileName
            },
            fileUrl: _this.cad.path,
            fontspath: '/static/mxcad/dist/fonts'
          }).then((mxcad) => {
            console.log(mxcad)
          }) */
        }, 200)
      } else if (row.fileType.search('.wordprocessingml.document') !== -1) {
        console.log('.document')
        setTimeout(function() {
          _this.docx.showDialog = true
          _this.docx.title = row.fileName
          _this.docx.path = process.env.VUE_APP_BASE_API + row.filePath
        }, 200)
      } else if (row.fileType.search('application/pdf') !== -1) {
        console.log('application/pdf')
        setTimeout(function() {
          _this.vpdf.showDialog = true
          _this.vpdf.title = row.fileName
          _this.vpdf.path = process.env.VUE_APP_BASE_API + row.filePath
        }, 200)
      } else if (row.fileType.search('.spreadsheetml.sheet') !== -1) {
        console.log('.sheet')
        setTimeout(function() {
          _this.excel.showDialog = true
          _this.excel.title = row.fileName
          _this.excel.path = process.env.VUE_APP_BASE_API + row.filePath
        }, 200)
      } else if (row.fileType.search('.presentationml.presentation') !== -1) {
        console.log('.presentation')
        setTimeout(function() {
          _this.pptx.showDialog = true
          _this.pptx.title = row.fileName
          _this.pptx.path = process.env.VUE_APP_BASE_API + row.filePath
        }, 200)
      }
    },
    async refreshList(res) {
      let _this = this
      console.log(res)
      await gFile({ workfaceId: _this.currentWorkfaceId }).then(res => {
        _this.fileList = res.rows
      })
    },
    renderedHandler() {
      console.log('渲染成功')
    },
    errorHandler(err) {
      console.log('error', err)
    },
    uploadSuccess() {
      this.$message.success('文件上传成功！')
      this.$refs.upload1.clearFiles()
      this.$refs.upload2.clearFiles()
      this.refreshList()
    }
  }
}
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="98px">
      <el-form-item label="选择工作面" prop="workfaceId" label-width="98px">
        <el-select v-model="currentWorkfaceId" placeholder="请选择工作面" @change="setCurrentWorkface">
          <el-option v-for="item in workfaceList" :key="item.id" :label="item.workfaceName"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="workfaceId" label-width="98px">
        <div>
          <el-upload
            multiple
            ref="upload1"
            :limit="10"
            :headers="uploadHeaders"
            :action="uploadCADUrl + currentWorkfaceId"
            :auto-upload="true"
            accept=".dwg"
            :show-file-list="false"
            :on-success="uploadSuccess"
          >
            <el-button size="mini" plain type="primary" icon="el-icon-upload2">导入图纸</el-button>
          </el-upload>
        </div>
      </el-form-item>
      <el-form-item label="" prop="workfaceId" label-width="98px">
        <div>
          <el-upload
            multiple
            ref="upload2"
            :limit="10"
            :headers="uploadHeaders"
            :action="uploadDOCUrl + currentWorkfaceId"
            :auto-upload="true"
            accept=".doc,.docx,.ppt,.pptx,.pdf,.xls,.xlsx"
            :show-file-list="false"
            :on-success="uploadSuccess"
          >
            <el-button size="mini" plain type="primary" icon="el-icon-upload2">导入文档</el-button>
          </el-upload>
        </div>
      </el-form-item>
    </el-form>
    <el-dialog width="90%" :title="docx.title" :visible.sync="docx.showDialog" append-to-body>
      <vue-office-docx
        :src="docx.path"
        style="height: 80vh;"
        @rendered="renderedHandler"
        @error="errorHandler"
      />
    </el-dialog>
    <el-dialog width="90%" :title="excel.title" :visible.sync="excel.showDialog" append-to-body>
      <vue-office-excel
        :src="excel.path"
        :options="options"
        style="height: 80vh;"
        @rendered="renderedHandler"
        @error="errorHandler"
      />
    </el-dialog>
    <el-dialog width="90%" :title="vpdf.title" :visible.sync="vpdf.showDialog" append-to-body>
      <vue-office-pdf
        :src="vpdf.path"
        style="height: 80vh;"
        @rendered="renderedHandler"
        @error="errorHandler"
      />
    </el-dialog>
    <el-dialog width="90%" :title="pptx.title" :visible.sync="pptx.showDialog" append-to-body>
      <vue-office-pptx
        :src="pptx.path"
        style="height: 80vh;"
        @rendered="renderedHandler"
        @error="errorHandler"
      />
    </el-dialog>
    <el-dialog width="90%" :title="cad.title" :visible.sync="cad.showDialog" append-to-body>
      <div style="height: 80vh; overflow: hidden;">
        <canvas id="myCanvas"></canvas>
      </div>
    </el-dialog>
    <el-table
      v-loading="loading"
      :data="fileList"
    >
      <el-table-column label="文件ID" align="center" prop="id"/>
      <el-table-column label="文件名称" align="center" prop="fileName"/>
      <!--      <el-table-column label="对应工作面" align="center" prop="workfaceId"/>-->
      <!--      <el-table-column label="文件位置" align="center" prop="filePath"/>-->
      <el-table-column label="文件大小" align="center" prop="fileSize">
        <template slot-scope="scope">
          {{
            scope.row.fileSize > (1024 * 1024) ?
              Number((scope.row.fileSize / (1024 * 1024)).toFixed(2)) + ' MB' :
              Number((scope.row.fileSize / (1024)).toFixed(2)) + ' KB'
          }}
        </template>
      </el-table-column>
      <el-table-column label="文件类型" align="center" prop="fileType">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.fileType.search('.wordprocessingml.document') !== -1 ?'primary' :
              scope.row.fileType.search('dwg') !== -1?'info':
              scope.row.fileType.search('.spreadsheetml.sheet') !== -1?'warning':
              'danger'"
            disable-transitions>{{ scope.row.fileType.search('.wordprocessingml.document') !== -1 ?'docx' :
            scope.row.fileType.search('dwg') !== -1?'dwg':
              scope.row.fileType.search('.spreadsheetml.sheet') !== -1?'xlsx':
                'pptx' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="上传时间" align="center" prop="createTime"/>
      <!--      <el-table-column label="md5" align="center" prop="md5"/>
            <el-table-column label="备注" align="center" prop="remark"/>-->
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="previewDoc(scope.row)"
            style="color: #00E9D0"
          >查看
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="downloadDoc(scope.row)"
            style="color: #0BA1F8"
          >下载
          </el-button>
          <el-popconfirm
            title="确定删除此文件吗？"
            @confirm="deleteDoc(scope.row)"
          >
            <el-button
              slot="reference"
              size="mini"
              type="text"
              icon="el-icon-delete"
              style="color: #e12d30;margin-left: 10px"
            >删除
            </el-button>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>

</style>
