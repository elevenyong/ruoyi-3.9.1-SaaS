<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
            label-width="68px">
            <el-form-item label="版本号" prop="versionCode">
                <el-input v-model="queryParams.versionCode" placeholder="请输入版本号" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="版本名称" prop="versionName">
                <el-input v-model="queryParams.versionName" placeholder="请输入版本名称" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <!-- <el-form-item label="资源地址" prop="resUrl">
        <el-input
          v-model="queryParams.resUrl"
          placeholder="请输入资源地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否强制升级" prop="forceUpdate">
        <el-input
          v-model="queryParams.forceUpdate"
          placeholder="请输入是否强制升级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本内容描述" prop="versionDes">
        <el-input
          v-model="queryParams.versionDes"
          placeholder="请输入版本内容描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                    v-hasPermi="['system:version:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                    v-hasPermi="['system:version:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                    @click="handleDelete" v-hasPermi="['system:version:remove']">删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    v-hasPermi="['system:version:export']">导出</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-check" size="mini" :disabled="single"
                    @click="selectCurrentVersion" v-hasPermi="['system:version:export']">选定为当前版本</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="versionList" @selection-change="handleSelectionChange"
            :row-class-name="currentRow">
            <el-table-column type="selection" width="55" align="center" :selectable="checkSelectable" />
            <el-table-column label="id" align="center" prop="id" />
            <el-table-column label="强制升级" align="center" prop="forceUpdateGame" />
            <el-table-column label="软件版本" align="center" prop="internalGameVersion" />
            <el-table-column label="资源版本" align="center" prop="internalResourceVersion" />
            <el-table-column label="长度" align="center" prop="versionListLength" />
            <el-table-column label="ListHashcode" align="center" prop="versionListHashCode" />
            <el-table-column label="ZipLength" align="center" prop="versionListZipLength" />
            <el-table-column label="ZipHashcode" align="center" prop="versionListZipHashCode" />
            <el-table-column label="版本名称" align="center" prop="versionName" />
            <el-table-column label="资源地址" align="center" prop="updatePrefixUri" />
            <el-table-column label="最新版本" align="center" prop="latestGameVersion" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-upload ref="file" :file-list="filefileList" multiple :action="fileTargetUrl"
                        :before-upload="fileBeforeUpload" :on-success="getList">
                        <el-button size="small" type="primary" icon="el-icon-upload"
                            @click="handleResUpdate(scope.row)">上传资源包</el-button>
                    </el-upload>
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                        v-hasPermi="['system:version:edit']">修改</el-button>
                    <el-button v-if="scope.row.currentVersion !== 1" size="mini" type="text" icon="el-icon-delete"
                        @click="handleDelete(scope.row)" v-hasPermi="['system:version:remove']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize" @pagination="getList" />

        <!-- 添加或修改version对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
            <el-form ref="form" :model="formData" :rules="rules" label-width="180px">
                <el-form-item label="快捷输入" prop="quickInput">
                    <el-input v-model="quickInput" placeholder="code" clearable type="textarea" @change="quick"
                        :style="{ width: '100%' }">
                    </el-input>
                </el-form-item>
                <el-form-item label="强制升级" prop="forceUpdateGame">
                    <!-- <el-input v-model="formData.ForceUpdateGame" placeholder="请输入强制升级" /> -->
                    <el-switch v-model="formData.ForceUpdateGame"></el-switch>
                </el-form-item>
                <el-form-item label="软件版本" prop="internalGameVersion">
                    <el-input v-model="formData.InternalGameVersion" placeholder="请输入软件版本" />
                </el-form-item>
                <el-form-item label="资源版本" prop="internalResourceVersion">
                    <el-input v-model="formData.InternalResourceVersion" placeholder="请输入资源版本" />
                </el-form-item>
                <el-form-item label="长度" prop="versionListLength">
                    <el-input v-model="formData.VersionListLength" placeholder="请输入长度" />
                </el-form-item>
                <el-form-item label="versionListHashCode" prop="versionListHashCode">
                    <el-input v-model="formData.VersionListHashCode" placeholder="请输入hashcode" />
                </el-form-item>
                <el-form-item label="VersionListZipLength" prop="versionListZipLength">
                    <el-input v-model="formData.VersionListZipLength" placeholder="请输入" />
                </el-form-item>
                <el-form-item label="versionListZipHashCode" prop="versionListZipHashCode">
                    <el-input v-model="formData.VersionListZipHashCode" placeholder="请输入hashcode" />
                </el-form-item>
                <el-form-item label="资源地址" prop="updatePrefixUri">
                    <el-input v-model="formData.updatePrefixUri" type="textarea" placeholder="" />
                </el-form-item>
                <el-form-item label="最新版本" prop="latestGameVersion">
                    <el-input v-model="formData.latestGameVersion" placeholder="" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { listVersion, getVersion, delVersion, addVersion, updateVersion, putCurrentVersion } from "@/api/hhhz/version";

export default {
    name: "Version",
    data() {
        return {
            quickInput: '',
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
            // version表格数据
            versionList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                versionCode: null,
                versionName: null,
                resUrl: null,
                forceUpdate: null,
                versionDes: null
            },
            formData: {
                versionCode: '',
                versionName: undefined,
                versionDes: undefined,
                forceUpdate: false,
                file: null,
            },
            rules: {
                /* versionCode: [{
                    required: true,
                    message: '请输入版本号：xx.xx.xx',
                    trigger: 'blur'
                }, {
                    pattern: /^([0-9]\d|[0-9])(.([0-9]\d|\d)){2}$/,
                    message: '版本号格式错误，格式：xx.xx.xx',
                    trigger: 'blur'
                }],
                versionName: [{
                    required: true,
                    message: '请输入版本名',
                    trigger: 'blur'
                }], */
            },
            fileAction: 'http://192.168.0.117:8989/U3DRes/upload',
            fileTargetUrl: 'http://192.168.0.117:8989/U3DRes/upload',
            filefileList: [],
        };
    },
    created() {
        this.getList();
    },
    methods: {
        currentRow({ row, rowIndex }) {
            console.log(row)
            if (row.currentVersion === 1) {
                return 'success-row';
            }
            return '';
        },
        /** 被选为当前版本后不能被删除和选中 */
        checkSelectable(row) {
            return row.currentVersion !== 1
        },
        quick() {
            console.log(this.quickInput);
            let aa = JSON.parse(this.quickInput);
            if (aa !== null && aa !== undefined) {
                this.formData = aa
                console.log(this.formData)
            }
        },
        /** 查询version列表 */
        getList() {
            this.loading = true;
            listVersion(this.queryParams).then(response => {
                this.versionList = response.rows;
                this.total = response.total;
                this.loading = false;
            });
        },
        // 取消按钮
        cancel() {
            this.open = false;
            this.reset();
        },
        // 表单重置
        reset() {
            this.quickInput = "";
            this.formData = {
                ForceUpdateGame: null,
                LatestGameVersion: null,
                InternalGameVersion: null,
                InternalResourceVersion: null,
                UpdatePrefixUri: null,
                VersionListLength: null,
                VersionListHashCode: null,
                VersionListZipLength: null,
                VersionListZipHashCode: null,
                VersionName: null
            };
            this.resetForm("form");
        },
        handleResUpdate(row) {
            console.log("row", row);
            if (row.updatePrefixUri !== null && row.updatePrefixUri !== "") {
                let arr = row.updatePrefixUri.split("/");
                console.log(arr)
                let uuid = arr[arr.length - 2];
                this.fileTargetUrl = this.fileAction + "/" + row.id + "-" + uuid;
            } else {
                this.fileTargetUrl = this.fileAction + "/" + row.id + "-" + this.uuid2(16, 16);
            }
            console.log(this.fileTargetUrl)
        },
        fileBeforeUpload(file) {
            let isRightSize = file.size / 1024 / 1024 < 20
            if (!isRightSize) {
                this.$message.error('文件大小超过 20MB')
            }
            return isRightSize
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getList();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.resetForm("queryForm");
            this.handleQuery();
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            this.ids = selection.map(item => item.id)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /**选定当前版本 */
        selectCurrentVersion(row) {
            let _this = this;
            const vid = row.id || this.ids[0];
            putCurrentVersion(vid).then(response => {
                this.$modal.msgSuccess("修改成功");
                _this.getList()
            })
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = "添加version";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            console.log(row, row.id)
            const versionId = row.id || this.ids
            getVersion(versionId).then(response => {
                this.formData.ForceUpdateGame = response.data.forceUpdateGame === 1 ? true : false;
                this.formData.LatestGameVersion = response.data.latestGameVersion;
                this.formData.InternalGameVersion = response.data.internalGameVersion;
                this.formData.InternalResourceVersion = response.data.internalResourceVersion;
                this.formData.UpdatePrefixUri = response.data.updatePrefixUri;
                this.formData.VersionListLength = response.data.versionListLength;
                this.formData.VersionListHashCode = response.data.versionListHashCode;
                this.formData.VersionListZipLength = response.data.versionListZipLength;
                this.formData.VersionListZipHashCode = response.data.versionListZipHashCode;
                this.formData.VersionName = response.data.versionName;
                this.formData.id = response.data.id;
                //this.formData = response.data;
                this.open = true;
                this.title = "修改version";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.formData.forceUpdate) {
                        this.formData.forceUpdate = 1
                    } else {
                        this.formData.forceUpdate = 0
                    }
                    let paramData = {};
                    /* "ForceUpdateGame": true,
                    "LatestGameVersion": null,
                    "InternalGameVersion": 4,
                    "InternalResourceVersion": 4,
                    "UpdatePrefixUri": null,
                    "VersionListLength": 2137,
                    "VersionListHashCode": 1133367708,
                    "VersionListZipLength": 777,
                    "VersionListZipHashCode": 595157446,
                    "VersionName": "Screens" */
                    paramData.forceUpdateGame = this.formData.ForceUpdateGame ? 1 : 0;
                    paramData.latestGameVersion = this.formData.LatestGameVersion;
                    paramData.internalGameVersion = this.formData.InternalGameVersion;
                    paramData.internalResourceVersion = this.formData.InternalResourceVersion;
                    paramData.updatePrefixUri = this.formData.UpdatePrefixUri;
                    paramData.versionListLength = this.formData.VersionListLength;
                    paramData.versionListHashCode = this.formData.VersionListHashCode;
                    paramData.versionListZipLength = this.formData.VersionListZipLength;
                    paramData.versionListZipHashCode = this.formData.VersionListZipHashCode;
                    paramData.versionName = this.formData.VersionName;
                    if (this.formData.id != null) {
                        paramData.id = this.formData.id;
                        updateVersion(paramData).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addVersion(paramData).then(response => {
                            paramData.updatePrefixUri = "";
                            this.$modal.msgSuccess("新增成功");
                            this.open = false;
                            this.getList();
                        });
                    }
                }
            });
        },
        /** 删除按钮操作 */
        handleDelete(row) {
            const versionIds = row.id || this.ids;
            this.$modal.confirm('是否确认删除version编号为"' + versionIds + '"的数据项？').then(function () {
                return delVersion(versionIds);
            }).then(() => {
                this.getList();
                this.$modal.msgSuccess("删除成功");
            }).catch(() => { });
        },
        /** 导出按钮操作 */
        handleExport() {
            this.download('system/version/export', {
                ...this.queryParams
            }, `version_${new Date().getTime()}.xlsx`)
        },
        uuid2(len, radix) {
            var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
            var uuid = [],
                i;
            radix = radix || chars.length;

            if (len) {
                // Compact form
                for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
            } else {
                // rfc4122, version 4 form
                var r;

                // rfc4122 requires these characters
                uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
                uuid[14] = '4';

                // Fill in random data.  At i==19 set the high bits of clock sequence as
                // per rfc4122, sec. 4.1.5
                for (i = 0; i < 36; i++) {
                    if (!uuid[i]) {
                        r = 0 | Math.random() * 16;
                        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                    }
                }
            }

            return uuid.join('');
        }
    }
};
</script>
<style>
.el-table .success-row {
    background: #e8f4ff;
    color: #74bcff;
}
</style>