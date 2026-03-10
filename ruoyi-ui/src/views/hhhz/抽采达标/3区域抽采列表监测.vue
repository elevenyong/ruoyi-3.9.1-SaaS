<template>
    <div class="app-container">
        <!-- <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
            label-width="68px">
            <el-form-item label="工作面" prop="gzmValue">
                <el-select v-model="queryParams.gzmValue" placeholder="请选择工作面">
                    <el-option v-for="item in gzmOptions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="评价单元" prop="pingjiadanyuan">
                <el-select v-model="queryParams.pingjiadanyuan" placeholder="请选择评价单元">
                    <el-option v-for="item in pjdyOptions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="查询方式" prop="queryWay">
                <el-select v-model="queryParams.queryWay" placeholder="请选择评价结果">
                    <el-option v-for="item in queryWayOptions" :key="item.value" :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="() => { console.log('导出') }">导出</el-button>
            </el-form-item>
        </el-form> -->

        <!-- <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                    v-hasPermi="['system:actual_borehole:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                    v-hasPermi="['system:actual_borehole:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                    @click="handleDelete" v-hasPermi="['system:actual_borehole:remove']">删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    v-hasPermi="['system:actual_borehole:export']">导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row> -->

        <el-table v-loading="loading" :data="actual_boreholeList" @selection-change="handleSelectionChange">
            <!-- <el-table-column type="selection" width="55" align="center" /> -->
            <el-table-column label="序号" align="center" prop="id" />
            <el-table-column label="单元名称" align="center" prop="dymc" />
            <el-table-column label="面积㎡" align="center" prop="mj" width="180" />
            <el-table-column label="煤层厚度m" align="center" prop="mchd" />
            <el-table-column label="容重t/m³" align="center" prop="rz" />
            <el-table-column label="瓦斯含量m³/t" align="center" prop="wshl" />
            <el-table-column label="瓦斯储藏量m³" align="center" prop="wsccl" />
            <el-table-column label="钻孔总长m" align="center" prop="zkzc" />
            <el-table-column label="预抽开始时间" align="center" prop="yckssj" />
            <el-table-column label="日累计抽采量m³" align="center" prop="rljccl" />
            <el-table-column label="月累计抽采量m³" align="center" prop="yljccl" />
            <el-table-column label="年累计抽采量m³" align="center" prop="nljccl" />
            <el-table-column label="总累计抽采量m³" align="center" prop="zljccl" />
            <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                        v-hasPermi="['system:actual_borehole:edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                        v-hasPermi="['system:actual_borehole:remove']">删除</el-button>
                </template>
            </el-table-column> -->
        </el-table>
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.index" :limit.sync="queryParams.size"
            @pagination="getList" />

    </div>
</template>

<script>
import { listActual_borehole, getActual_borehole, delActual_borehole, addActual_borehole, updateActual_borehole } from "@/api/hhhz/actual_borehole";

export default {
    name: "Actual_borehole",
    data() {
        return {
            /**工作面 */
            gzmOptions: [
                {
                    value: '1',
                    label: '2616下巷'
                }, {
                    value: '2',
                    label: '2617下巷'
                }
            ],
            gzmValue: "",
            pjdyOptions: [
                {
                    value: '1',
                    label: '2616下巷-1'
                }, {
                    value: '2',
                    label: '2617下巷-2'
                }
            ],
            queryWayOptions: [
                {
                    value: '1',
                    label: '当前评价结果'
                }
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
            /**
             * <el-table-column label="序号" align="center" prop="id" />
            <el-table-column label="单元名称" align="center" prop="dymc" />
            <el-table-column label="面积㎡" align="center" prop="mj" width="180" />
            <el-table-column label="煤层厚度m" align="center" prop="mchd" />
            <el-table-column label="容重t/m³" align="center" prop="rz" />
            <el-table-column label="瓦斯含量m³/t" align="center" prop="wshl" />
            <el-table-column label="瓦斯储藏量m³" align="center" prop="wsccl" />
            <el-table-column label="钻孔总长m" align="center" prop="zkzc" />
            <el-table-column label="预抽开始时间" align="center" prop="yckssj" />
            <el-table-column label="日累计抽采量m³" align="center" prop="rljccl" />
            <el-table-column label="月累计抽采量m³" align="center" prop="yljccl" />
            <el-table-column label="年累计抽采量m³" align="center" prop="nljccl" />
            <el-table-column label="总累计抽采量m³" align="center" prop="zljccl" />
             */
            // 实钻基础信息t_actual_borehole表格数据
            actual_boreholeList: [
                {
                    id: 1,
                    dymc: "2616下巷-4",
                    mj: 3059.00,
                    mchd: 2.5,
                    rz: 1.45,
                    wshl: 9.68,
                    wsccl: 9107340,
                    zkzc: 1558.0,
                    yckssj: "2022-07-30",
                    rljccl: "",
                    yljccl: "",
                    nljccl: "",
                    zljccl: 3270000.01,
                },
                {
                    id: 2,
                    dymc: "23煤柱上巷",
                    mj: 3023.00,
                    mchd: 2.5,
                    rz: 1.45,
                    wshl: 9.68,
                    wsccl: 6107340,
                    zkzc: 1558.0,
                    yckssj: "2022-05-15",
                    rljccl: "",
                    yljccl: "",
                    nljccl: "",
                    zljccl: 1310400.01,
                },
                {
                    id: 3,
                    dymc: "2616下巷",
                    mj: 8951.6,
                    mchd: 2.5,
                    rz: 1.45,
                    wshl: 9.68,
                    wsccl: 107340,
                    zkzc: 1558.0,
                    yckssj: "2021-07-30",
                    rljccl: "",
                    yljccl: "",
                    nljccl: "",
                    zljccl: 70021.01,
                }
            ],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                queryWay: "",
                pingjiadanyuan: "",
                gzmValue: "",
                index: 1,
                size: 10,
                //pageNum: 1,
                //pageSize: 10,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
            }
        };
    },
    created() {
        //this.getList();
    },
    methods: {
        /** 查询实钻基础信息t_actual_borehole列表 */
        getList() {
            this.loading = true;
            listActual_borehole(this.queryParams).then(response => {
                this.actual_boreholeList = response.data.records;
                this.total = response.data.total;
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
            this.form = {
                id: null,
                groupNo: null,
                boreholeNo: null,
                roadwayId: null,
                roadwayZoneId: null,
                boreholesiteid: null,
                drillingDate: null,
                categoryCode: null,
                purposeBoreholeId: null,
                drillingid: null,
                measurepointid: null,
                borediameter: null,
                azimuthAngle: null,
                inclinationAngle: null,
                depth1stRockSeamStart: null,
                depth1stCoalSeamStart: null,
                depth2stRockSeamStart: null,
                depth2stCoalSeamStart: null,
                depth3stRockSeamStart: null,
                depth3stcoalseamstart: null,
                boreholeLength: null,
                navigationPointId: null,
                distanceToNavigationPoint: null,
                extractionRadius: null,
                distanceToCentrallineTunnel: null,
                holeAltitude: null,
                adjacentBoreholeNo: null,
                distanceAdjacentBorehole: null,
                personChecking: null,
                checkingDate: null,
                sign: null,
                createBy: null,
                createTime: null,
                updateBy: null,
                updateTime: null,
                remark: null
            };
            this.resetForm("form");
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.index = 1;
            /* this.queryParams.pageNum = 1; */
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
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = "添加实钻基础信息t_actual_borehole";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids
            getActual_borehole(id).then(response => {
                this.form = response.data;
                this.open = true;
                this.title = "修改实钻基础信息t_actual_borehole";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.form.id != null) {
                        updateActual_borehole(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addActual_borehole(this.form).then(response => {
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
            const ids = row.id || this.ids;
            this.$modal.confirm('是否确认删除实钻基础信息t_actual_borehole编号为"' + ids + '"的数据项？').then(function () {
                return delActual_borehole(ids);
            }).then(() => {
                this.getList();
                this.$modal.msgSuccess("删除成功");
            }).catch(() => { });
        },
        /** 导出按钮操作 */
        handleExport() {
            let _this = this;
            this.download('tActualBorehole/export',
                _this.actual_boreholeList
                , `actual_borehole_${new Date().getTime()}.xlsx`)
        }
    }
};
</script>
