<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
            label-width="68px">
            <el-form-item label="工作面" prop="gzm">
                <!-- @keyup.enter.native="handleQuery" -->
                <el-select v-model="queryParams.gzm" placeholder="请选择工作面">
                    <el-option v-for="item in gzmOptions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="评价单元" prop="pjdy">
                <el-select v-model="queryParams.pjdy" placeholder="请选择评价单元">
                    <el-option v-for="item in pjdyOptions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="() => { console.log('导出') }">导出</el-button>
            </el-form-item>
        </el-form>

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
            <el-table-column label="评价单元" align="center" prop="pjdy" />
            <el-table-column label="长度" align="center" prop="len" />
            <el-table-column label="宽度" align="center" prop="wid" />
            <el-table-column label="预抽时间最长的钻孔" align="center" prop="max">
                <el-table-column label="孔号" align="center" prop="max.kh" />
                <el-table-column label="开始时间" align="center" prop="max.start" />
                <el-table-column label="结束时间" align="center" prop="max.end" />
            </el-table-column>
            <el-table-column label="预抽时间最短的钻孔" align="center" prop="min">
                <el-table-column label="孔号" align="center" prop="min.kh" />
                <el-table-column label="开始时间" align="center" prop="min.start" />
                <el-table-column label="结束时间" align="center" prop="min.end" />
            </el-table-column>
            <el-table-column label="预抽时间最长的钻孔抽采天数Tmax" align="center" prop="tmax" />
            <el-table-column label="预抽时间最长的钻孔抽采天数Tmin" align="center" prop="tmin" />
            <el-table-column label="计算预抽时间差异系数" align="center" prop="cyxs" />
            <el-table-column label="预抽时间差异系数标准ɳ0" align="center" prop="cyxsbz" />
            <!--  <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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

        <!-- 添加或修改实钻基础信息t_actual_borehole对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="组号" prop="groupNo">
                    <el-input v-model="form.groupNo" placeholder="请输入组号" />
                </el-form-item>
                <el-form-item label="孔号" prop="boreholeNo">
                    <el-input v-model="form.boreholeNo" placeholder="请输入孔号" />
                </el-form-item>
                <el-form-item label="所属巷道ID" prop="roadwayId">
                    <el-input v-model="form.roadwayId" placeholder="请输入所属巷道ID" />
                </el-form-item>
                <el-form-item label="所属区段ID" prop="roadwayZoneId">
                    <el-input v-model="form.roadwayZoneId" placeholder="请输入所属区段ID" />
                </el-form-item>
                <el-form-item label="钻场ID" prop="boreholesiteid">
                    <el-input v-model="form.boreholesiteid" placeholder="请输入钻场ID" />
                </el-form-item>
                <el-form-item label="钻孔时间" prop="drillingDate">
                    <el-date-picker clearable v-model="form.drillingDate" type="date" value-format="yyyy-MM-dd"
                        placeholder="请选择钻孔时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="钻孔类型ID" prop="categoryCode">
                    <el-input v-model="form.categoryCode" placeholder="请输入钻孔类型ID" />
                </el-form-item>
                <el-form-item label="钻孔用途ID" prop="purposeBoreholeId">
                    <el-input v-model="form.purposeBoreholeId" placeholder="请输入钻孔用途ID" />
                </el-form-item>
                <el-form-item label="钻机信息ID" prop="drillingid">
                    <el-input v-model="form.drillingid" placeholder="请输入钻机信息ID" />
                </el-form-item>
                <el-form-item label="关联测点ID" prop="measurepointid">
                    <el-input v-model="form.measurepointid" placeholder="请输入关联测点ID" />
                </el-form-item>
                <el-form-item label="孔径" prop="borediameter">
                    <el-input v-model="form.borediameter" placeholder="请输入孔径" />
                </el-form-item>
                <el-form-item label="实际夹角" prop="azimuthAngle">
                    <el-input v-model="form.azimuthAngle" placeholder="请输入实际夹角" />
                </el-form-item>
                <el-form-item label="实际倾角" prop="inclinationAngle">
                    <el-input v-model="form.inclinationAngle" placeholder="请输入实际倾角" />
                </el-form-item>
                <el-form-item label="实际见岩深度" prop="depth1stRockSeamStart">
                    <el-input v-model="form.depth1stRockSeamStart" placeholder="请输入实际见岩深度" />
                </el-form-item>
                <el-form-item label="实际见煤深度" prop="depth1stCoalSeamStart">
                    <el-input v-model="form.depth1stCoalSeamStart" placeholder="请输入实际见煤深度" />
                </el-form-item>
                <el-form-item label="实际第二段见岩孔深" prop="depth2stRockSeamStart">
                    <el-input v-model="form.depth2stRockSeamStart" placeholder="请输入实际第二段见岩孔深" />
                </el-form-item>
                <el-form-item label="实际第二段见煤孔深" prop="depth2stCoalSeamStart">
                    <el-input v-model="form.depth2stCoalSeamStart" placeholder="请输入实际第二段见煤孔深" />
                </el-form-item>
                <el-form-item label="实际第三段见岩孔深" prop="depth3stRockSeamStart">
                    <el-input v-model="form.depth3stRockSeamStart" placeholder="请输入实际第三段见岩孔深" />
                </el-form-item>
                <el-form-item label="实际第三段见煤孔深" prop="depth3stcoalseamstart">
                    <el-input v-model="form.depth3stcoalseamstart" placeholder="请输入实际第三段见煤孔深" />
                </el-form-item>
                <el-form-item label="实际孔深" prop="boreholeLength">
                    <el-input v-model="form.boreholeLength" placeholder="请输入实际孔深" />
                </el-form-item>
                <el-form-item label="导线点ID" prop="navigationPointId">
                    <el-input v-model="form.navigationPointId" placeholder="请输入导线点ID" />
                </el-form-item>
                <el-form-item label="距导线点距离" prop="distanceToNavigationPoint">
                    <el-input v-model="form.distanceToNavigationPoint" placeholder="请输入距导线点距离" />
                </el-form-item>
                <el-form-item label="抽采半径" prop="extractionRadius">
                    <el-input v-model="form.extractionRadius" placeholder="请输入抽采半径" />
                </el-form-item>
                <el-form-item label="距巷道中线距离" prop="distanceToCentrallineTunnel">
                    <el-input v-model="form.distanceToCentrallineTunnel" placeholder="请输入距巷道中线距离" />
                </el-form-item>
                <el-form-item label="距拱基线高度" prop="holeAltitude">
                    <el-input v-model="form.holeAltitude" placeholder="请输入距拱基线高度" />
                </el-form-item>
                <el-form-item label="邻近孔号(组号+孔号列表)" prop="adjacentBoreholeNo">
                    <el-input v-model="form.adjacentBoreholeNo" placeholder="请输入邻近孔号(组号+孔号列表)" />
                </el-form-item>
                <el-form-item label="与邻近孔距离" prop="distanceAdjacentBorehole">
                    <el-input v-model="form.distanceAdjacentBorehole" placeholder="请输入与邻近孔距离" />
                </el-form-item>
                <el-form-item label="验收人员" prop="personChecking">
                    <el-input v-model="form.personChecking" placeholder="请输入验收人员" />
                </el-form-item>
                <el-form-item label="验收日期" prop="checkingDate">
                    <el-date-picker clearable v-model="form.checkingDate" type="date" value-format="yyyy-MM-dd"
                        placeholder="请选择验收日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="钻孔标识" prop="sign">
                    <el-input v-model="form.sign" placeholder="请输入钻孔标识" />
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listActual_borehole, getActual_borehole, delActual_borehole, addActual_borehole, updateActual_borehole } from "@/api/hhhz/actual_borehole";

export default {
    name: "Actual_borehole",
    data() {
        return {
            gzmOptions: [
                {
                    value: '1',
                    label: '2616下巷'
                }, {
                    value: '2',
                    label: '2617下巷'
                }
            ],
            pjdyOptions: [
                {
                    value: '1',
                    label: '2616下巷-1'
                }, {
                    value: '2',
                    label: '2617下巷-2'
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
             <el-table-column label="序号" align="center" prop="id" />
            <el-table-column label="评价单元" align="center" prop="pjdy" />
            <el-table-column label="长度" align="center" prop="len" />
            <el-table-column label="宽度" align="center" prop="wid" />
            <el-table-column label="预抽时间最长的钻孔" align="center" prop="max">
                <el-table-column label="孔号" align="center" prop="max.kh" />
                <el-table-column label="开始时间" align="center" prop="max.start" />
                <el-table-column label="结束时间" align="center" prop="max.end" />
            </el-table-column>
            <el-table-column label="预抽时间最短的钻孔" align="center" prop="min">
                <el-table-column label="孔号" align="center" prop="min.kh" />
                <el-table-column label="开始时间" align="center" prop="min.start" />
                <el-table-column label="结束时间" align="center" prop="min.end" />
            </el-table-column>
            <el-table-column label="预抽时间最长的钻孔抽采天数Tmax" align="center" prop="tmax" />
            <el-table-column label="预抽时间最长的钻孔抽采天数Tmin" align="center" prop="tmin" />
            <el-table-column label="计算预抽时间差异系数" align="center" prop="cyxs" />
            <el-table-column label="预抽时间差异系数标准ɳ0" align="center" prop="cyxsbz" />
             */
            // 实钻基础信息t_actual_borehole表格数据
            actual_boreholeList: [{
                id: 1,
                pjdy: "2616下巷-4",
                len: 47.5,
                wid: 64.4,
                max: {
                    kh: "46-1",
                    start: "2022-10-17",
                    end: "2021-09-03"
                },
                min: {
                    kh: "46-5",
                    start: "2022-10-18",
                    end: "2021-09-03"
                },
                tmax: 7721,
                tmin: 7697,
                cyxs: "0.31%",
                cyxsbz: "<30%"
            }, {
                id: 2,
                pjdy: "2616下巷-5",
                len: 10.3,
                wid: 64.4,
                max: {
                    kh: "-",
                    start: "-",
                    end: "2021-07-03"
                },
                min: {
                    kh: "-",
                    start: "-",
                    end: "2021-07-03"
                },
                tmax: "-",
                tmin: "-",
                cyxs: "-",
                cyxsbz: "<30%"
            }, {
                id: 3,
                pjdy: "2616下巷-6",
                len: 47.5,
                wid: 64.4,
                max: {
                    kh: "46-1",
                    start: "2022-10-17",
                    end: "2021-09-03"
                },
                min: {
                    kh: "46-5",
                    start: "2022-10-18",
                    end: "2021-09-03"
                },
                tmax: 7721,
                tmin: 7697,
                cyxs: "0.31%",
                cyxsbz: "<30%"
            }
            ],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                zkdd: "",
                zkyt: "",
                zcbh: "",
                zkbh: "",
                ysrq: "",
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
