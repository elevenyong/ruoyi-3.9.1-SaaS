<template>
  <div class="app-container">
    <span>请选择工作面：</span>
    <el-select v-model="currentWorkFaceId" placeholder="请选择工作面" @change="setCurrentWorkFace">
      <el-option v-for="item in workingFaceList" :key="item.id" :label="item.workfaceName"
                 :value="item.id">
      </el-option>
    </el-select>
    <el-button style="margin:0 5px" type="primary" size="mini"
               @click="()=>{this.rockTypeShow = true;}">
      管理岩层类型
    </el-button>
    <el-row class="rockRow">
      <div class="rockDiv">
        <el-button type="text" style="color: white;margin: 0 auto;" @click="showAoeRock(0)">添加顶板岩层
        </el-button>
        <el-button type="text" style="float: right;margin-right: 20px"></el-button>
      </div>
      <div v-for="item in rockInfoList" :key="item.id" class="rockLine"
           :style="[{'background-color': item.rockTypeData.color},{'height':(item.thickness*30)+'px'}]">
                  <span style="color: white;margin: 0 auto;">{{ (item.rockDesc?item.rockDesc:item.rockTypeData.rockType)+"("+item.thickness+"m)"}}</span>
        <el-button type="text" style="color: white;float: right;margin-right: 10px"
                   @click="editRockInfo(item)">编辑
        </el-button>
        <el-button type="text" style="color: red;float: right;margin-right: 10px" v-if="item.serialNumber!==0"
                   @click="handleDeleteRockInfo(item)">删除
        </el-button>
      </div>
      <div
        style="display: flex;flex-direction: row;align-items: center;height:40px;width:50vw;background-color: gray;border: 1px solid gray;">
        <el-button type="text" style="color: white;margin: 0 auto;" @click="showAoeRock(1)">添加底板岩层
        </el-button>
        <el-button type="text" style="color: red;float: right;margin-right: 20px"></el-button>
      </div>
    </el-row>


    <!--  新增|编辑岩层信息  -->
    <el-dialog :title="rockInfoTitle" :visible.sync="aoeRockShow" width="20%">
      <el-form ref="aoeRockInfo" :model="aoeRockInfo" label-width="80px">
        <el-form-item label="岩层类型" prop="rockType">
          <el-select v-model="aoeRockInfo.rockType" placeholder="请选择岩层类型"
                     :disabled="rockInfoList.length === 0||aoeRockInfo.serialNumber===0">
            <el-option v-for="item in rockTypeList" :key="item.id" :label="item.rockType"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地层名称" prop="rockDesc">
          <el-input
            v-model="aoeRockInfo.rockDesc"
            placeholder="请输入地层名称"
          />
        </el-form-item>
        <el-form-item label="地层厚度" prop="thickness">
          <el-input
            v-model="aoeRockInfo.thickness"
            placeholder="请输入岩层厚度"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRockInfoForm">确 定</el-button>
        <el-button @click="()=>{this.aoeRockShow = false;}">取 消</el-button>
      </div>
    </el-dialog>

    <!--  显示岩层类型信息  -->
    <el-dialog :title="rockTypeTitle" :visible.sync="rockTypeShow" width="30%">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="()=>{this.aoeRockTypeShow = true;this.aoeRockTypeTitle = '新增岩层类型';}"
          >新增
          </el-button>
        </el-col>
      </el-row>
      <el-table v-loading="false" :data="rockTypeList">
        <el-table-column label="id" align="center" prop="id"/>
        <el-table-column label="岩层类型名称" align="center" prop="rockType"/>
        <el-table-column label="岩层描述" align="center" prop="rockDesc"/>
        <el-table-column label="颜色" align="center" prop="color">
          <template slot-scope="scope">
            <el-color-picker v-model="scope.row.color" disabled></el-color-picker>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.id!==1"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleEditRockType(scope.row)"
            >修改
            </el-button>
            <el-button
              v-if="scope.row.id!==1"
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDeleteRockType(scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="()=>{this.rockTypeShow = false;}">取 消</el-button>
      </div>
    </el-dialog>

    <!--  新增|编辑岩层类型信息  -->
    <el-dialog :title="aoeRockTypeTitle" :visible.sync="aoeRockTypeShow" width="25%">
      <el-form ref="coalTypeDialog" :model="aoeRockType" label-width="120px">
        <el-form-item label="岩层类型名称" prop="type">
          <el-input v-model="aoeRockType.rockType" placeholder="请输入岩层类型名称"/>
        </el-form-item>
        <el-form-item label="备注" prop="description">
          <el-input v-model="aoeRockType.rockDesc" type="textarea" placeholder="请输入岩层描述信息"/>
        </el-form-item>
        <el-form-item label="颜色" prop="description">
          <el-color-picker v-model="aoeRockType.color"></el-color-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCoalTypeForm">确 定</el-button>
        <el-button @click="()=>{this.aoeRockTypeShow = false;this.aoeRockType = {};}">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 用户导入对话框 -->
    <!--    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
          <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                     :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
                     :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip text-center" slot="tip">
              <div class="el-upload__tip" slot="tip">
                <el-checkbox v-model="upload.updateSupport"/>
                是否更新已经存在的用户数据
              </div>
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
        </el-dialog>-->
  </div>
</template>

<style scoped>
.tablediv {
  margin-top: 10px;
}

.el-row {
  margin-bottom: 20px;
  display: flex;
  flex-wrap: wrap
}

.rockLine {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 50vw;
  border: 1px solid gray;
}

.rockRow {
  display: flex;
  flex-direction: row;
  width: 60vw;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
}

.rockDiv {
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 40px;
  width: 50vw;
  background-color: gray;
  border: 1px solid gray;
}
</style>
<script>
import { addRocktype, delRocktype, listRocktype, updateRocktype } from '@/api/hhhz/rocktype'
import { addRockinfo, delRockinfo, listRockinfo, updateRockinfo } from '@/api/hhhz/rockinfo'
import { listCoalminingworkingface } from '@/api/hhhz/coalMiningWorkingFace'
import item from '@/layout/components/Sidebar/Item.vue'

export default {
  name: 'CoalInfo',
  computed: {
    item() {
      return item
    }
  },
  dicts: ['sys_normal_disable', 'sys_user_sex', 'sys_user_expire'],
  data() {
    return {
      showzbd: true,
      showzkxx: false,
      coalInfoTitle: '新增工作面岩层',
      coalInfoShow: false,
      aoeCoalInfo: {},
      rockInfoTitle: '岩层信息',
      RockShow: false,
      aoeRockInfo: {},
      rockTypeTitle: '岩层类型',
      rockTypeShow: false,
      aoeRockTypeTitle: '新增煤层点类型',
      aoeRockTypeShow: false,
      aoeRockType: {},
      queryParams: {},
      // 添加|修改岩层
      aoeRockShow: false,
      // 岩层类型
      rockTypeList: [],
      // 工作面列表
      workingFaceList: [],
      // 岩层信息列表
      rockInfoList: [],
      // 断层信息
      geoFaultList: [],
      currentCoal: {},
      currentWorkFaceId: 0
    }
  },
  watch: {},
  async created() {
    await this.getWorkingFaceList()
    await this.getRockTypeList()
    //await this.getGeoFaultList()
    this.$message.success('请选择工作面！')
  },
  methods: {
    editRockInfo(item) {
      this.aoeRockInfo = item
      this.aoeRockShow = true
    },
    showAoeRock(type) {
      if (this.currentWorkFaceId === 0) {
        this.$message.error('请先选择工作面')
        return
      }
      this.aoeRockInfo = {}
      this.aoeRockInfo.workfaceId = this.currentWorkFaceId
      if (this.rockInfoList.length === 0) {
        this.aoeRockInfo.serialNumber = 0
        this.aoeRockInfo.rockType = 1
        this.aoeRockShow = true
        return
      }
      if (type === 0) {
        // 添加顶板
        this.aoeRockShow = true
        let serNum = 0
        for (let i = 0; i < this.rockInfoList.length; i++) {
          if (serNum <= this.rockInfoList[i].serialNumber) {
            serNum = this.rockInfoList[i].serialNumber
          }
        }
        this.aoeRockInfo.serialNumber = serNum + 1
      } else if (type === 1) {
        // 添加底板
        this.aoeRockShow = true
        let serNum = 0
        for (let i = 0; i < this.rockInfoList.length; i++) {
          if (serNum >= this.rockInfoList[i].serialNumber) {
            serNum = this.rockInfoList[i].serialNumber
          }
        }
        this.aoeRockInfo.serialNumber = serNum - 1
      }
    },
    handleEditRockType(rockType) {
      this.aoeRockTypeShow = true
      this.aoeRockTypeTitle = '修改岩层类型'
      this.aoeRockType = rockType
    },
    // 获取岩层类型数据
    async getRockTypeList() {
      this.aoeRockType = {}
      await listRocktype({}).then((resp) => {
        if (resp.code === 200) {
          this.rockTypeList = resp.rows
        }
      })
    },
    // 获取工作面数据
    async getWorkingFaceList() {
      await listCoalminingworkingface({}).then((response) => {
        if (response.code === 200) {
          this.workingFaceList = response.rows
        }
      })
    },
    // 显示选择的工作面
    setCurrentWorkFace(id) {
      let _this = this
      this.getRockInfoList(id)
    },
    getRockInfoList(id) {
      let _this = this
      listRockinfo({ workfaceId: id }).then(response => {
        _this.rockInfoList = response.rows
      })
    },
    handleEditRock(row) {
      this.RockShow = true
      this.aoeRock = row
      this.RockTitle = '修改煤层点'
    },
    // 提交岩层信息
    submitRockInfoForm() {
      console.log(this.aoeCoalInfo)
      let _this = this
      this.aoeRockInfo.workfaceId = this.currentWorkFaceId
      if (this.aoeRockInfo.id != null) {
        updateRockinfo(this.aoeRockInfo).then(response => {
          this.$modal.msgSuccess('修改成功')
          this.aoeRockShow = false
          this.getRockInfoList(_this.currentWorkFaceId)
        })
      } else {
        addRockinfo(this.aoeRockInfo).then(response => {
          this.$modal.msgSuccess('新增成功')
          this.aoeRockShow = false
          this.getRockInfoList(_this.currentWorkFaceId)
        })
      }
    },
    // 提交岩层点类型数据
    submitCoalTypeForm() {
      console.log(this.aoeRockType)
      let _this = this
      if (this.aoeRockType.id != null) {
        updateRocktype(this.aoeRockType).then(response => {
          this.$modal.msgSuccess('修改成功')
          this.aoeRockTypeShow = false
          this.getRockTypeList()
          this.getRockInfoList(_this.currentWorkFaceId)
        })
      } else {
        addRocktype(this.aoeRockType).then(response => {
          this.$modal.msgSuccess('新增成功')
          this.aoeRockTypeShow = false
          this.getRockTypeList()
          this.getRockInfoList(_this.currentWorkFaceId)
        })
      }
    },

    handleDeleteRockInfo(row) {
      let _this = this
      this.$modal.confirm('是否确认删除选中的: ' + row.rockTypeData.rockType + ' 的数据？').then(function() {
        return delRockinfo(row.id)
      }).then(() => {
        this.getRockInfoList(_this.currentWorkFaceId)
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleDeleteRockType(row) {
      let _this = this
      this.$modal.confirm('是否确认删除选中的: ' + row.rockType + ' 的数据？').then(function() {
        return delRocktype(row.id)
      }).then(() => {
        this.getRockInfoList(_this.currentWorkFaceId)
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    }
  }

}
</script>
