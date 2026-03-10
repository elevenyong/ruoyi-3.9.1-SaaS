<!-- 掘进工作面 -->
<!-- <transition name="el-fade-in"> -->
<div v-if="showjjgzm">
<el-row :gutter="20">
  <!--巷道工作面列表-->
  <el-col :span="4" :xs="24">
    <el-card style="height: 80vh; margin: 10px 5px">
      <div slot="header" class="clearfix">
        <span>掘进工作面列表</span>
        <div style="float: right">
          <el-button size="mini" type="primary">新增工作面</el-button>
        </div>
      </div>
      <div class="head-container">
        <li><a href="#">3: 2616下巷</a></li>
        <li><a href="#">11: 2715上巷</a></li>
      </div>
    </el-card>
  </el-col>
  <!--数据信息-->
  <el-col :span="20" :xs="24">
    <h4>基本信息</h4>
    <el-divider/>
    <el-form
      ref="queryForm"
      :inline="true"
      :model="queryParams"
      label-width="98px"
      size="small"
    >
      <el-form-item label="工作面名称：" prop="userName">
        <el-input
          v-model="queryParams.userName"
          clearable
          placeholder="请输入"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="断面形状：" prop="userName">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="宽：" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          clearable
          placeholder="请输入"
          style="width: 150px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <el-form-item label="高：" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          clearable
          placeholder="请输入"
          style="width: 150px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <el-form-item label="关联底抽巷：" prop="userName">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          icon="el-icon-download"
          size="mini"
          type="primary"
          @click="handleQuery"
        >保存
        </el-button>
        <el-button
          icon="el-icon-delete"
          size="mini"
          type="error"
          @click="resetQuery"
        >删除
        </el-button>
      </el-form-item>
    </el-form>

    <el-card>
      <div slot="header" class="clearfix">
        <span>巷道区段创建</span>
        <div style="float: right; padding: 3px 0">
          <span>已创建巷道0节</span>
          <el-button size="mini" style="margin: 0 5px" type="primary"
          >增加
          </el-button>
          <el-button size="mini" style="margin: 0 5px" type="primary"
          >导入
          </el-button>
        </div>
      </div>
      <div>
        <el-table
          v-loading="loading"
          :data="currentRoad.troadwayZoneList"
          @selection-change="handleSelectionChange"
        >
          <!-- <el-table-column type="selection" width="50" align="center" /> -->
          <el-table-column align="center" label="序号" prop="id"/>
          <el-table-column
            :show-overflow-tooltip="true"
            align="center"
            label="名称"
            prop="zoneName"
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
            align="center"
            label="方位角(°)"
            prop="azimuthAngle"
            width="120"
          />
          <el-table-column
            align="center"
            label="倾角(°)"
            prop="inclinationAngle"
            width="120"
          />
          <!--  <el-table-column
            label="操作"
            align="center"
            width="120"
          >
            <template slot-scope="scope" v-if="scope.row.userId !== 1">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
                >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
                >删除
              </el-button>
            </template>
          </el-table-column> -->
        </el-table>
      </div>
    </el-card>

    <el-card style="margin-top: 20px">
      <div slot="header" class="clearfix">
        <span>导线点创建</span>
        <div style="float: right; padding: 3px 0">
          <span>已创建导线点0个</span>
          <el-button size="mini" style="margin: 0 5px" type="primary"
          >增加
          </el-button>
          <el-button size="mini" style="margin: 0 5px" type="primary"
          >导入
          </el-button>
        </div>
      </div>
      <div>
        <el-table
          v-loading="loading"
          :data="userList"
          @selection-change="handleSelectionChange"
        >
          <!-- <el-table-column type="selection" width="50" align="center" /> -->
          <el-table-column
            key="userId"
            align="center"
            label="序号"
            prop="userId"
          />
          <el-table-column
            key="userName"
            :show-overflow-tooltip="true"
            align="center"
            label="名称"
            prop="userName"
          />
          <el-table-column
            key="nickName"
            :show-overflow-tooltip="true"
            align="center"
            label="X坐标"
            prop="nickName"
          />
          <el-table-column
            key="deptName"
            :show-overflow-tooltip="true"
            align="center"
            label="Y坐标"
            prop="dept.deptName"
          />
          <el-table-column
            key="postName"
            :show-overflow-tooltip="true"
            align="center"
            label="Z坐标"
            prop="roles[0].roleName"
          />
          <el-table-column
            key="phonenumber"
            align="center"
            label="操作"
            prop="phonenumber"
            width="120"
          >
            <template v-if="scope.row.userId !== 1" slot-scope="scope">
              <el-button
                v-hasPermi="['system:user:edit']"
                icon="el-icon-edit"
                size="mini"
                type="text"
                @click="handleUpdate(scope.row)"
              >修改
              </el-button>
              <el-button
                v-hasPermi="['system:user:remove']"
                icon="el-icon-delete"
                size="mini"
                type="text"
                @click="handleDelete(scope.row)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </el-col>
</el-row>
</div>
<!-- </transition> -->
<!-- 采煤工作面 -->
<div v-if="showcmgzm">
<el-row :gutter="20">
  <!--巷道工作面列表-->
  <el-col :span="4" :xs="24">
    <el-card style="height: 80vh; margin: 10px 5px">
      <div slot="header" class="clearfix">
        <span>采煤工作面列表</span>
        <div style="float: right">
          <el-button size="mini" type="primary">新增工作面</el-button>
        </div>
      </div>
      <div class="head-container">
        <li><a href="#">2: 2616工作面</a></li>
        <li><a href="#">23: 2611采面</a></li>
      </div>
    </el-card>
  </el-col>
  <!--数据信息-->
  <el-col :span="20" :xs="24">
    <h4>基本信息</h4>
    <el-divider/>
    <el-form
      ref="queryForm"
      :inline="true"
      :model="queryParams"
      label-width="138px"
      size="small"
    >
      <el-form-item label="采煤工作面名称：" prop="userName">
        <el-input
          v-model="queryParams.userName"
          clearable
          placeholder="请输入"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="倾向长度：" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          clearable
          placeholder="请输入"
          style="width: 150px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <el-form-item label="走向长度：" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          clearable
          placeholder="请输入"
          style="width: 150px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <el-form-item label="高度：" prop="userName">
        <el-input
          v-model="queryParams.phonenumber"
          clearable
          placeholder="请输入"
          style="width: 150px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <!-- <el-form-item>
                      <el-button type="primary" icon="el-icon-download" size="mini" @click="handleQuery">保存
                      </el-button>
                      <el-button type="error" icon="el-icon-delete" size="mini" @click="resetQuery">删除
                      </el-button>
                  </el-form-item> -->
    </el-form>

    <h4>支架信息</h4>
    <el-divider/>
    <el-form
      ref="queryForm"
      :inline="true"
      :model="queryParams"
      label-width="138px"
      size="small"
    >
      <el-form-item label="工作面支架数量：" prop="userName">
        <el-input
          v-model="queryParams.userName"
          clearable
          placeholder="请输入"
          style="width: 200px"
        >
          <template slot="append">个</template>
        </el-input>
      </el-form-item>
      <el-form-item label="支架宽度：" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          clearable
          placeholder="请输入"
          style="width: 150px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <el-form-item label="首架位置：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="首架距外帮距离：" prop="userName">
        <el-input
          v-model="queryParams.phonenumber"
          clearable
          placeholder="请输入"
          style="width: 150px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <!-- <el-form-item>
                      <el-button type="primary" icon="el-icon-download" size="mini" @click="handleQuery">保存
                      </el-button>
                      <el-button type="error" icon="el-icon-delete" size="mini" @click="resetQuery">删除
                      </el-button>
                  </el-form-item> -->
    </el-form>

    <h4>巷道关联创建</h4>
    <el-divider/>
    <el-form
      ref="queryForm"
      :model="queryParams"
      label-width="118px"
      size="small"
    >
      <el-form-item label="关联底抽巷：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联进风巷：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联回风巷：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联切眼巷：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联采区大巷：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item>
                      <el-button type="primary" icon="el-icon-download" size="mini" @click="handleQuery">保存
                      </el-button>
                      <el-button type="error" icon="el-icon-delete" size="mini" @click="resetQuery">删除
                      </el-button>
                  </el-form-item> -->
    </el-form>

    <div>
      <el-button
        icon="el-icon-download"
        size="mini"
        type="primary"
        @click="handleQuery"
      >保存
      </el-button>
      <el-button
        icon="el-icon-delete"
        size="mini"
        type="error"
        @click="resetQuery"
      >删除
      </el-button>
    </div>
  </el-col>
</el-row>
</div>

<!-- 揭煤工作面 -->
<div v-if="showjmgzm">
<el-row :gutter="20">
  <!--工作面列表-->
  <el-col :span="4" :xs="24">
    <el-card style="height: 80vh; margin: 10px 5px">
      <div slot="header" class="clearfix">
        <span>揭煤工作面列表</span>
        <div style="float: right">
          <el-button size="mini" type="primary">新增工作面</el-button>
        </div>
      </div>
      <div class="head-container">
        <li><a href="#">2: 2616工作面</a></li>
        <li><a href="#">23: 2611采面</a></li>
      </div>
    </el-card>
  </el-col>
  <!--数据信息-->
  <el-col :span="20" :xs="24">
    <h4>基本信息</h4>
    <el-divider/>
    <el-form
      ref="queryForm"
      :inline="true"
      :model="queryParams"
      label-width="138px"
      size="small"
    >
      <el-form-item label="采煤工作面名称：" prop="userName">
        <el-input
          v-model="queryParams.userName"
          clearable
          placeholder="请输入"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="倾向长度：" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          clearable
          placeholder="请输入"
          style="width: 150px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <el-form-item label="走向长度：" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          clearable
          placeholder="请输入"
          style="width: 150px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <el-form-item label="高度：" prop="userName">
        <el-input
          v-model="queryParams.phonenumber"
          clearable
          placeholder="请输入"
          style="width: 150px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <!-- <el-form-item>
                      <el-button type="primary" icon="el-icon-download" size="mini" @click="handleQuery">保存
                      </el-button>
                      <el-button type="error" icon="el-icon-delete" size="mini" @click="resetQuery">删除
                      </el-button>
                  </el-form-item> -->
    </el-form>

    <h4>巷道关联/创建</h4>
    <el-divider/>
    <el-form
      ref="queryForm"
      :model="queryParams"
      label-width="118px"
      size="small"
    >
      <el-form-item label="关联底抽巷：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联进风巷：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联回风巷：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联切眼巷：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联采区大巷：" prop="phonenumber">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item>
                      <el-button type="primary" icon="el-icon-download" size="mini" @click="handleQuery">保存
                      </el-button>
                      <el-button type="error" icon="el-icon-delete" size="mini" @click="resetQuery">删除
                      </el-button>
                  </el-form-item> -->
    </el-form>

    <div>
      <el-button
        icon="el-icon-download"
        size="mini"
        type="primary"
        @click="handleQuery"
      >保存
      </el-button>
      <el-button
        icon="el-icon-delete"
        size="mini"
        type="error"
        @click="resetQuery"
      >删除
      </el-button>
    </div>
  </el-col>
</el-row>
</div>

<!-- 采区大巷 -->
<div v-if="showcqdh">
<el-row :gutter="20">
  <!--巷道工作面列表-->
  <el-col :span="4" :xs="24">
    <el-card style="height: 80vh; margin: 10px 5px">
      <div slot="header" class="clearfix">
        <span>采区大巷列表</span>
        <div style="float: right">
          <el-button size="mini" type="primary" @click="showAddCqdh"
          >新增采区大巷
          </el-button
          >
        </div>
      </div>
      <div class="head-container">
        <li v-for="item in roadList" :key="item.id">
          <a href="#" @click="setCurrentRoad(item)">
            ID:{{ item.id }}--{{ item.roadway }}</a
          >
        </li>
      </div>
    </el-card>
  </el-col>
  <!--数据信息-->
  <el-col :span="20" :xs="24">
    <h4>基本信息</h4>
    <el-divider/>
    <h4>基本信息</h4>
    <el-divider/>
    <el-form
      :inline="true"
      :model="currentRoad"
      label-width="98px"
      size="small"
    >
      <el-form-item label="底抽巷名称：">
        <el-input
          v-model="currentRoad.roadway"
          :disabled="!editable"
          clearable
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
          clearable
          style="width: 140px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <el-form-item label="高：">
        <el-input
          v-model="currentRoad.height"
          :disabled="!editable"
          clearable
          style="width: 140px"
        >
          <template slot="append">m</template>
        </el-input>
      </el-form-item>
      <el-form-item label="方位角：">
        <el-input
          v-model="currentRoad.azimuthAngle"
          :disabled="!editable"
          clearable
          style="width: 140px"
        >
          <template slot="append">°</template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          v-if="!editable"
          icon="el-icon-edit"
          size="mini"
          type="primary"
          @click="
                  () => {
                    editable = true;
                  }
                "
        >修改
        </el-button>
        <el-button
          v-if="editable"
          icon="el-icon-check"
          size="mini"
          type="primary"
          @click="updateRoad"
        >保存
        </el-button>
        <el-button
          v-if="currentRoad != null"
          icon="el-icon-delete"
          size="mini"
          type="error"
          @click="delRoad"
        >删除
        </el-button>
        <el-button
          v-if="currentRoad != null"
          icon="el-icon-refresh"
          size="mini"
          type="default"
          @click="refreshRoad"
        >刷新
        </el-button>
      </el-form-item>
    </el-form>

    <el-card>
      <div slot="header" class="clearfix">
        <span>巷道区段创建</span>
        <div style="float: right; padding: 3px 0">
                <span
                >已创建巷道{{
                    currentRoad.troadwayZoneList
                      ? currentRoad.troadwayZoneList.length
                      : 0
                  }}节</span
                >
          <el-button
            v-if="currentRoad != null"
            size="mini"
            style="margin: 0 5px"
            type="primary"
            @click="showAddHdqd"
          >增加
          </el-button>
          <el-button
            v-if="currentRoad != null"
            size="mini"
            style="margin: 0 5px"
            type="primary"
            @click="handleImpHdqd"
          >导入
          </el-button>
          <el-button
            v-if="currentRoad != null"
            size="mini"
            style="margin: 0 5px"
            type="primary"
            @click="previewHdqd"
          >预览
          </el-button>
        </div>
      </div>
      <div>
        <el-table
          key="cqdhhdqd"
          v-loading="loading"
          :data="currentRoad.troadwayZoneList"
          height="350"
        >
          <!-- <el-table-column type="selection" width="50" align="center" /> -->
          <el-table-column align="center" label="序号" prop="id"/>
          <el-table-column
            :show-overflow-tooltip="true"
            align="center"
            label="名称"
            prop="zoneName"
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
            align="center"
            label="方位角(°)"
            prop="azimuthAngle"
            width="120"
          />
          <el-table-column
            align="center"
            label="倾角(°)"
            prop="inclinationAngle"
            width="120"
          />
          <el-table-column align="center" label="操作" width="120">
            <template slot-scope="scope">
              <el-button
                icon="el-icon-edit"
                size="mini"
                type="text"
                @click="
                        () => {
                          addhdqdshow = true;
                          hdqdData = scope.row;
                        }
                      "
              >修改
              </el-button>
              <el-button
                icon="el-icon-delete"
                size="mini"
                type="text"
                @click="delRoadZone(scope.row)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </el-col>
</el-row>
</div>
