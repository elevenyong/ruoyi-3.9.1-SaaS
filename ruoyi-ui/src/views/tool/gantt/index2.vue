<template>
  <div>
    <gantt-elastic v-show="showGantt" :options="options" :tasks="tasks" @tasks-changed="tasksUpdates"
      @options-changed="optionsUpdate" @dynamic-style-changed="styleUpdate">
    </gantt-elastic>
    <el-dialog v-bind="$attrs" :visible.sync="showForm" v-on="$listeners" @open="onOpen" @close="onClose" title="编辑任务">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-row type="flex" justify="start" align="top" :gutter="15">
          <el-form-item label="项目名称" prop="label">
            <el-input v-model="formData.label" placeholder="请输入项目名称" :maxlength="11" show-word-limit clearable
              :style="{ width: '100%' }"></el-input>
          </el-form-item>
          <el-form-item label="项目状态" prop="nums">
            <el-input v-model="formData.nums" placeholder="请输入项目状态" clearable :style="{ width: '100%' }">
            </el-input>
          </el-form-item>
        </el-row>
        <el-form-item label="负责人" prop="user">
          <el-select v-model="formData.user" placeholder="请选择负责人" clearable :style="{ width: '100%' }">
            <el-option v-for="(item, index) in users" :key="index" :label="item.label" :value="item.value"
              :disabled="item.disabled"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期范围" prop="during">
          <el-date-picker type="daterange" v-model="formData.during" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
            :style="{ width: '100%' }" start-placeholder="开始日期" end-placeholder="结束日期" range-separator="至" clearable>
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>

    <!-- -->
  </div>
</template>

<style>
</style>
<script>
import GanttElastic from "gantt-elastic";
import dayjs from "dayjs";
// import GanttHeader from "gantt-elastic-header";

//tasks是数据 真实的项目需要接口赋值数据
let that
let options = {
  taskMapping: {
    progress: "percent"
  },
  maxRows: 10,
  maxHeight: 500,
  // title: {
  //   label: "Your project title as html (link or whatever...)",
  //   html: false
  // },
  row: {
    height: 12,//设置行高
  },
  times: {
    timeScale: 60 * 1000,
    timeZoom: 20,
  },

  chart: {
    grid: {
      horizontal: {
        gap: 6,//*
      }
    },
    text: {
      offset: 4, //*
      xPadding: 10, //*
      display: true //*
    },

    expander: {
      type: 'chart',
      display: true, //*
      displayIfTaskListHidden: true, //*
      offset: 4, //*
      size: 18
    }
  },

  taskList: {
    // expander: {
    //   straight: true,
    // },
    columns: [
      {
        id: 0,
        label: "编辑",
        value: "edit",
        width: 40,
        html: true,
        events: {
          click({ data, column }) {
            if (that.flags != '1') {
              that.edit(data)
            }
          }
        }
      },
      {
        id: 1,
        label: "ID",
        value: "id",
        width: 40
      },
      {
        id: 2,
        label: "项目名称",
        value: "label",
        width: 150,
        expander: true,
        html: true,
        events: {
          click({ data, column }) {
            if (that.flags != '1') {
              that.showDialog(data.label)
            }
          }
        }
      },
      {
        id: 3,
        label: "状态",
        value: "nums",
        width: 80,
        // expander: true,
        // html: true,

      },
      {
        id: 4,
        label: "负责人",
        value: "user",
        width: 80,
        html: true
      },
      {
        id: 5,
        label: "开始时间",
        value: task => dayjs(task.start).format("YYYY-MM-DD"),
        width: 80,
        events: {
          click({ data, column }) {
            if (that.flags != '1') {
              that.showDialog(data.start)
            }
          }
        }
      },
      {
        id: 6,
        label: "结束时间",
        value: task => dayjs(task.end).format("YYYY-MM-DD"),
        width: 80
      },


    ],

  },

  calendar: {
    workingDays: [1, 2, 3, 4, 5, 6, 7], //*
    gap: 0,
    strokeWidth: 5,
    hour: {
      display: false
    },
  },

  locale: {
    weekdays: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
    months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
  },

};
export default {
  inheritAttrs: false,
  //   name: "Gantt",
  components: {
    GanttElastic,
    // GanttHeader 
  },
  props: {
    gantData: {},
    flags: {
      type: String
    }
  },
  data() {
    return {
      showGantt: true,
      dialogVisible: false,
      orderModal: false,
      TaskData: [],
      sontaskData: '',
      tasks: [
        {
          id: 1,
          edit: "<i class=\"el-icon-edit\"></i>",
          label: "项目一",
          nums: '预计延迟3天',
          user: 'tim',
          start: this.getTody(new Date(), 10),
          end: this.getTody(new Date(), -10),
          //duration: 2 * 24 * 60 * 60 * 1000,
          percent: 85,
          type: "project"
        },
        {
          id: 2,
          edit: "<i class=\"el-icon-edit\"></i>",
          label: "项目1-1",
          nums: '正常进行',
          user: 'tim',
          parentId: 1,
          start: this.getTody(new Date(), 0),
          end: this.getTody(new Date(), -20),
          //duration: 1 * 24 * 60 * 60 * 1000,
          percent: 85,
          type: "project"
        },
        {
          id: 3,
          edit: "<i class=\"el-icon-edit\"></i>",
          label: "项目二",
          nums: '进行中',
          user: 'zipp',
          start: this.getTody(new Date(), 5),
          end: this.getTody(new Date(), -25),
          //duration: 4 * 24 * 60 * 60 * 1000,
          percent: 10,
          type: "project"
        }
      ],
      options,
      dynamicStyle: {},
      successStyle: {
        base: {
          fill: "#1EBC61",
          stroke: "#0EAC51"
        }
      },
      unfinishedStyle: {
        base: {
          fill: "#dfe6ec",
          stroke: "#dfe6ec"
        }
      },
      comName: 'newSelectProdct',
      titles: '新品选品',
      flag: true,

      //弹框
      showForm: false,
      formData: {
        id: 0,
        label: '',
        nums: "",
        user: "",
        during: null,
      },
      rules: {
        label: [{
          required: true,
          message: '请输入项目名称',
          trigger: 'blur'
        }],
        nums: [{
          required: true,
          message: '请输入项目状态',
          trigger: 'blur'
        }],
        user: [{
          required: true,
          message: '请选择负责人',
          trigger: 'change'
        }],
        during: [{
          required: true,
          message: '日期范围不能为空',
          trigger: 'change'
        }],
      },
      users: [{
        "label": "user1",
        "value": "user1"
      }, {
        "label": "user2",
        "value": "user2"
      }],
    };
  },
  created() {

  },
  mounted() {
    that = this;
    if (this.gantData) {

      //this.getTaskList(this.gantData.id)
    }
  },
  computed: {

  },
  watch: {
    gantData: function (news, old) {
      //this.getTaskList(news.id)
    }
  },
  methods: {
    onOpen() { },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$emit('update:visible', false)
      this.showForm = false;
    },
    handleConfirm(data) {
      console.log(data)
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        let tempTasks = [];
        console.log(this.tasks, this.tasks.length)
        for (let i = 0; i < this.tasks.length; i++) {
          tempTasks[i] = this.tasks[i];
          if (this.tasks[i].id == this.formData.id) {
            tempTasks[i].label = this.formData.label;
            tempTasks[i].nums = this.formData.nums;
            tempTasks[i].start = this.formData.during[0];
            tempTasks[i].end = this.formData.during[1];
            tempTasks[i].user = this.formData.user;
          }
          var diffTime = that.datedifference(tempTasks[i].end, tempTasks[i].start);
          tempTasks[i].duration = diffTime * 24 * 60 * 60 * 1000;
          console.log(tempTasks[i])
        }
        console.log(tempTasks)
        //this.tasks = tempTasks;
        that.tasksUpdates(tempTasks, 2)
        this.close();
      })
    },
    edit(data) {
      console.log(data);
      this.$emit('update:visible', true)
      this.showForm = true;
      this.formData.id = data.id;
      this.formData.label = data.label;
      this.formData.nums = data.nums;
      this.formData.user = data.user;
      this.formData.during = [data.start, data.end];
      /*        {
              label: '',
              nums: undefined,
              field102: undefined,
              during: null,
            }, */
    },
    handleClose() {
      this.dialogVisible = false;
    },
    // addTask() {
    //   this.tasks.push({
    //     id: this.lastId++,
    //     label:
    //       '<a href="https://images.pexels.com/photos/423364/pexels-photo-423364.jpeg?auto=compress&cs=tinysrgb&h=650&w=940" target="_blank" style="color:#0077c0;">Yeaahh! you have added a task bro!</a>',
    //     user:
    //       '<a href="https://images.pexels.com/photos/423364/pexels-photo-423364.jpeg?auto=compress&cs=tinysrgb&h=650&w=940" target="_blank" style="color:#0077c0;">Awesome!</a>',
    //     start: getDate(24 * 3),
    //     duration: 1 * 24 * 60 * 60 * 1000,
    //     percent: 50,
    //     type: "project"
    //   });
    // },

    getTody(n, ds = 0, ys = 0) {
      var now = new Date(n);
      var time = now - 24 * 60 * 60 * 1000 * ds; //获取前N天
      var d = new Date(time);
      var year = d.getFullYear() - ys;//获取前N年的时间
      var mon = d.getMonth() + 1;
      var day = d.getDate();
      var week = d.getDay();
      var hour = d.getHours();
      var secd = d.getMinutes()
      var week = d.getDay();
      var times = '';
      if (mon == 0) {
        times = 12 + "-" + (day < 10 ? ("0" + day) : day);
      } else if (mon < 10) {
        times = (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ("0" + day) : day);
      } else {
        times = mon + "-" + (day < 10 ? ("0" + day) : day);
      }
      var today = year + '-' + times;

      return today

    },
    //计算时间差
    datedifference(date1, date2) {    //sDate1和sDate2是2006-12-18格式  
      var dateSpan, tempDate, iDays;
      var sDate1 = this.getTody(date1)
      sDate1 = Date.parse(sDate1)
      var sDate2 = this.getTody(date2)
      sDate2 = Date.parse(sDate2)
      dateSpan = sDate2 - sDate1;
      dateSpan = Math.abs(dateSpan);
      iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
      return iDays
    },
    //获取数据-----------------------------------------------------
    getTaskList(id) {
      var that = this;

      var params = {}
      params['projectId'] = id;
      this.$ajax
        .post("/ipm/index.php?a=default.taskList", params)
        .then(function (res) {
          if (res.data.success) {
            // that.showGantt = true;
            that.TaskData.length = []
            var list = res.data.datas;
            var startTime = list[0].startTime
            var endTime = list[list.length - 1].endTime
            var diffTime = that.datedifference(endTime, startTime)
            var temp1 = {
              id: Number(id),
              label: that.gantData.name,
              nums: '',
              user: '无',
              start: startTime,
              end: endTime,
              duration: diffTime * 24 * 60 * 60 * 1000,
              percent: 85,
              type: "project"
            }
            that.TaskData.push(temp1)
            for (var i = 0; i < list.length; i++) {
              var temp = {
                id: Number(list[i].id) + 1,
                code: list[i].code,
                label: '<span style="color:#0077c0;">' + list[i].name + '</span>',
                text: list[i].name,
                nums: list[i].statusText,
                user: list[i].operatePerson,
                parentId: Number(list[i].projectId),   //parentId 要跟父级的id对应
                start: list[i].startTime,
                end: list[i].endTime,
                duration: that.datedifference(list[i].endTime, list[i].startTime) * 24 * 60 * 60 * 1000, //获取时间差 任务完成的时间段
                percent: 50,
                type: "milestone",
                // collapsed: true,
                style: {
                  base: {
                    fill: "#1EBC61",
                    stroke: "#0EAC51"
                  }
                }
              }
              that.TaskData.push(temp)
            }


            if (that.TaskData.length > 0) {
              that.showGantt = true;
              that.tasksUpdates(that.TaskData, 2)
              that.optionsUpdate(that.options, 2)

            }

          } else {
            that.showGantt = false;
            that.$message.warning(res.data.message);
          }
        });
    },
    //点击弹出-----------------------------------------------------
    showDialog(data) {
      console.log(data)
    },


    tasksUpdates(tasks, num) {
      if (num == 2) {
        // this.tasks = [];
        this.tasks = tasks;
        //GanttObject.fixScrollPos();
      } else {
        return
      }


    },
    optionsUpdate(options, num) {
      if (num == 2) {
        this.options = options;
      }

    },
    styleUpdate(style) {
      this.dynamicStyle = style;
    }
  }
};
</script>
<style  >
</style>
