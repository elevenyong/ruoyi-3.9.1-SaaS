<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--巷道工作面列表-->
      <el-col :span="4" :xs="24">
        <el-card style="height: 80vh; margin: 10px 5px;overflow-y: auto">
          <!-- <div slot="header" class="clearfix">
            <span>底抽巷列表</span>
            <div style="float: right">
              <el-button type="primary" size="mini" @click="showAddDch"
                >新增底抽巷
              </el-button>
            </div>
          </div>
          <div class="head-container">
            <li v-for="item in roadList" :key="item.id">
              <a href="#" @click="setCurrentRoad(item)">
                ID:{{ item.id }}--{{ item.roadway }}</a
              >
            </li>
          </div> -->

          <div slot="header" class="clearfix">
            <span>巷道列表</span>

          </div>
          <div class="head-container">
            <el-menu
              class="el-menu-vertical-demo"
              :unique-opened="true"
            >
              <el-submenu v-for="(menItem,index) in roadTypes" :key="index" :index="index+''">
                <template slot="title">
                  <i class="el-icon-location"></i>
                  <span>{{ menItem.roadwayType }}</span>
                </template>
                <el-menu-item
                  v-for="(item,index) in roadList"
                  :key="index"
                  :index="index+''"
                  @click="()=>{
                    queryParams = {
                      pageNum: 1,
                      pageSize: 10,
                      };
                    roadwayId=item.id;
                    dxdList = item.tnavigationPointList;
                    listDesignedBorehole()
                  }"
                  v-if="item.type === menItem.id"
                >
                  {{ item.roadway }}
                </el-menu-item>
              </el-submenu>
            </el-menu>
          </div>
        </el-card>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>钻孔设计信息创建</span>
            <div style="float: right; padding: 3px 0">
              <!--              <el-button style="margin:0 5px" type="primary" size="mini" @click="handleAddDesHole">新增</el-button>-->
              <el-button style="margin:0 5px" type="primary" size="mini" @click="handleImpDesHole">导入</el-button>
            </div>
          </div>
          <div>
            <el-table v-loading="false" :data="boreholesList">
              <!-- <el-table-column type="selection" width="50" align="center" /> -->
              <!--              <el-table-column label="序号" align="center" key="id" prop="id"
                                            />-->
              <el-table-column label="施工巷道" align="center" key="roadway" prop="roadway"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="组号" align="center" key="groupNo" prop="groupNo"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="序号" align="center" key="boreholeNo" prop="boreholeNo"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="钻孔用途" align="center" key="purposeBorehole" prop="purposeBorehole"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="钻孔类别" align="center" key="boreholeCategory" prop="boreholeCategory"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="孔径(mm)" align="center" key="aperture" prop="aperture"
                               :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  {{ Number(scope.row.aperture).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="夹角(°)" align="center" key="angle" prop="angle"
                               width="120">
                <template slot-scope="scope">
                  {{ Number(scope.row.angle).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="方位角(°)" align="center" key="azimuthAngle" prop="azimuthAngle"
                               width="120">
                <template slot-scope="scope">
                  {{ Number(scope.row.azimuthAngle).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="倾角(°)" align="center" key="inclinationAngle" prop="inclinationAngle"
                               width="120">
                <template slot-scope="scope">
                  {{ Number(scope.row.inclinationAngle).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="见煤孔深(m)" align="center" key="depth1stCoalSeamStart"
                               prop="depth1stCoalSeamStart"
                               :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  {{ Number(scope.row.depth1stCoalSeamStart).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="见岩孔深(m)" align="center" key="depth1stRockSeamStart"
                               prop="depth1stRockSeamStart"
                               :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  {{ Number(scope.row.depth1stRockSeamStart).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="设计孔深(m)" align="center" key="boreholeLength" prop="boreholeLength"
                               :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  {{ Number(scope.row.boreholeLength).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="第一段煤孔深(m)" align="center" key="depth1stCoalSeamEnd"
                               prop="depth1stCoalSeamEnd" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  {{ Number(scope.row.depth1stCoalSeamEnd).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="第一段岩孔深(m)" align="center" key="depth1stRockSeamEnd"
                               prop="depth1stRockSeamEnd" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  {{ Number(scope.row.depth1stRockSeamEnd).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="第二段煤孔深(m)" align="center" key="depth2stCoalSeamEnd"
                               prop="depth2stCoalSeamEnd" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  {{ Number(scope.row.depth2stCoalSeamEnd).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="第二段岩孔深(m)" align="center" key="depth2stRockSeamEnd"
                               prop="depth2stRockSeamEnd" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  {{ Number(scope.row.depth2stRockSeamEnd).toFixed(2) }}
                </template>
              </el-table-column>
              <!--    <el-table-column label="操作" align="center" key="phonenumber" prop="phonenumber"
                                   width="120">
                    <template slot-scope="scope" v-if="scope.row.userId !== 1">
                      <el-button size="mini" type="text" icon="el-icon-edit"
                                 @click="handleUpdate(scope.row)" v-hasPermi="['system:user:edit']">修改
                      </el-button>
                      <el-button size="mini" type="text" icon="el-icon-delete"
                                 @click="handleDelete(scope.row)" v-hasPermi="['system:user:remove']">删除
                      </el-button>
                    </template>
                  </el-table-column>-->
            </el-table>

            <pagination
              v-show="total > 0"
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="listDesignedBorehole"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog :title="AOEDesHoleTitle" :visible.sync="showAOEDesHole" width="400px" append-to-body>
      <el-form ref="desHoleForm" :model="desHoleForm" :rules="rules" label-width="80px">
        <el-form-item label="组号" prop="groupNo">
          <el-input v-model="desHoleForm.groupNo" placeholder="请输入组号"/>
        </el-form-item>
        <el-form-item label="孔号" prop="boreholeNo">
          <el-input v-model="desHoleForm.boreholeNo" placeholder="请输入孔号"/>
        </el-form-item>
        <el-form-item label="导线点" prop="navigationPointId">
          <el-select
            v-model="desHoleForm.navigationPointId"
            placeholder="请选择"
            class="my_el-form-item"
          >
            <el-option
              v-for="(item,index) in dxdList"
              :key="index"
              :label="item.navigationPointName"
              :value="item.id"
            >
            </el-option>
          </el-select>
          <!--          <el-input v-model="desHoleForm.navigationPointId" placeholder=""/>-->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                 :action="upload.url" :disabled="upload.isUploading"
                 :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate">下载模板
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
import { gRoad, gRoadType } from '@/api/hhhz/hangdao'
import { getToken } from '@/utils/auth'
import { aDesignedBorehole, lDesignedBorehole, uDesignedBorehole } from '@/api/hhhz/designed_borehole'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'User',
  dicts: ['sys_normal_disable', 'sys_user_sex', 'sys_user_expire'],
  components: { Treeselect },
  data() {
    return {
      AOEDesHoleTitle: '',
      showAOEDesHole: false,
      // 导线点数据
      dxdList: [],
      desHoleForm: {},
      showAddDesHole: false,
      boreholesList: [],
      roadwayId: null,
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 上传弹窗数据绑定
      upload: {
        // 是否显示弹出层
        open: false,
        // 弹出层标题
        title: '',
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: {
          Authorization: 'Bearer ' + getToken()
        },
        // 上传的地址
        url: null
      },
      rules: {},
      // 巷道区段数据
      hdqdData: {},
      // 导线点数据
      dxdData: {},
      // 是否可编辑
      editable: false,
      // 当前表单数据
      currentRoad: {},
      // 所有巷道
      roadList: [],
      // 底抽巷列表
      dchRoadList: [],
      // 采区大巷列表
      cqdhRoadList: [],
      // 新增底抽巷数据
      roadData: {
        type: 2,
        roadwayType: '底抽巷'
      },
      aoeRoadWayTitle: '新增巷道',
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
      // 巷道类型
      roadTypes: [],
      hdqdpreviewshow: false,
      dxdpreviewshow: false,
      // 显示新增底抽巷弹窗
      addroadshow: false,
      // 显示新增巷道区段弹窗
      addhdqdshow: false,
      // 显示新增导线点弹窗
      adddxdshow: false,
      // 管理巷道类型
      manHdlxshow: false,
      // 修改巷道类型
      updateHdlxshow: false,
      updateHdlxData: {},

      showdch: true,
      showjjgzm: false,
      showcmgzm: false,
      showjmgzm: false,
      showcqdh: false,
      banEditExpire: true,
      loading: false
    }
  },
  watch: {},
  async created() {
    await this.getRoadType()
    await this.getRoad({})
  },
  methods: {
    // 获取巷道信息
    async getRoad(data) {
      this.cqdhRoadList = []
      this.dchRoadList = []
      await gRoad(data).then((response) => {
        console.log('gRoad', response)
        this.roadList = response.rows
        this.roadList.forEach((item) => {
          if (item.type === 1) {
            this.cqdhRoadList.push(item)
          } else if (item.type === 2) {
            this.dchRoadList.push(item)
          }
        })
        this.editable = false
      })
    },
    // 获取巷道类型
    async getRoadType() {
      await gRoadType({}).then((response) => {
        console.log('gRoadType', response)
        this.roadTypes = response.rows
      })
    },
    // 获取设计钻孔数据
    async listDesignedBorehole() {

      await lDesignedBorehole({ 'roadwayId': this.roadwayId }, this.queryParams).then((response) => {
        if (response.code === 200) {
          this.boreholesList = response.rows
          this.total = response.total
        }
      })
    },

    cancel() {
      this.desHoleForm = {}
      this.showAOEDesHole = false
    },

    /**
     * 新增钻孔
     */
    handleAddDesHole() {
      this.desHoleForm = {}
      this.showAOEDesHole = true
      this.AOEDesHoleTitle = '新增设计钻孔信息'
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs['desHoleForm'].validate((valid) => {
        if (valid) {
          if (this.desHoleForm.id != null) {
            uDesignedBorehole(this.desHoleForm).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.showAOEDesHole = false
              this.listDesignedBorehole()
            })
          } else {
            this.desHoleForm.roadwayId = this.roadwayId
            aDesignedBorehole([this.desHoleForm]).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.showAOEDesHole = false
              this.listDesignedBorehole()
            })
          }
        }
      })
    },

    /**
     * 导入钻孔
     */
    handleImpDesHole() {
      this.upload.type = '设计钻孔'
      this.upload.open = true
      this.upload.title = '导入设计钻孔数据'
      this.upload.url =
        process.env.VUE_APP_BASE_API + '/tDesignedBorehole/importData'
    },

    /** 下载模板操作 */
    importTemplate() {
      if (this.upload.type === 'dxd') {
        console.log('下载导线点数据模板...')
        this.download(
          'tNavigationPoint/exportTemplate',
          {},
          `导线点数据_template_${new Date().getTime()}.xlsx`
        )
      } else if (this.upload.type === 'hdqd') {
        console.log('下载巷道区段数据模板...')
        this.download(
          'tRoadwayZone/exportTemplate',
          {},
          `巷道区段数据_template_${new Date().getTime()}.xlsx`
        )
      } else {
        console.log('this.upload.type==hdqd', this.upload.type === 'hdqd')
      }
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert(
        '<div style=\'overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;\'>' +
        response.msg +
        '</div>',
        '导入结果',
        { dangerouslyUseHTMLString: true }
      )
      this.listDesignedBorehole()
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
    }
  }

}
</script>
