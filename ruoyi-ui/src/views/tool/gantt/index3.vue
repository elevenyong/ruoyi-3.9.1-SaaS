<template>
  <div class="q-pa-sm">
    <!-- <q-page class="q-pa-sm"> -->
    <gantt-elastic :options="options" :tasks="tasks" @tasks-changed="tasksUpdate" @options-changed="optionsUpdate"
      @dynamic-style-changed="styleUpdate">
      <!-- <gantt-header slot="header"></gantt-header> -->
    </gantt-elastic>
    <!-- <div class="q-mt-md" /> -->
    <el-button @click="addTask">添加任务</el-button>
    <!-- </q-page> -->
    <el-dialog v-bind="$attrs" :visible.sync="showForm" v-on="$listeners" @open="onOpen" @close="onClose" title="编辑任务">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-row type="flex" justify="start" align="top" :gutter="15">
          <el-form-item label="项目名称" prop="label">
            <el-input v-model="formData.label" placeholder="请输入项目名称" :maxlength="50" show-word-limit clearable
              :style="{ width: '100%' }"></el-input>
          </el-form-item>
          <!-- <el-form-item label="项目状态" prop="user">
            <el-input v-model="formData.user" placeholder="请输入项目状态" clearable :style="{ width: '100%' }">
            </el-input>
          </el-form-item> -->
        </el-row>
        <el-form-item label="负责人" prop="user">
          <el-select v-model="formData.user" placeholder="请选择负责人" clearable :style="{ width: '100%' }">
            <el-option v-for="(item, index) in users" :key="index" :label="item.label" :value="item.value"
              :disabled="item.disabled"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期范围" prop="startAndEnd">
          <!-- format="yyyy-MM-dd" value-format="yyyy-MM-dd" -->
          <el-date-picker type="datetimerange" v-model="formData.startAndEnd" format="yyyy-MM-dd HH:mm"
            value-format="yyyy-MM-dd HH:mm" :style="{ width: '100%' }" start-placeholder="开始日期" end-placeholder="结束日期"
            range-separator="至" @change="dateChanged" clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="持续时间(天)" prop="duration">
          <el-input v-model="formData.duration / (3600 * 24 * 1000)" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
</style>

<script>
import GanttElastic from "gantt-elastic";
import GanttHeader from "gantt-elastic-header";
import dayjs from "dayjs";

// just helper to get current dates
function getDate(hours) {
  const currentDate = new Date();
  const currentYear = currentDate.getFullYear();
  const currentMonth = currentDate.getMonth();
  const currentDay = currentDate.getDate();
  const timeStamp = new Date(
    currentYear,
    currentMonth,
    currentDay,
    0,
    0,
    0
  ).getTime();
  return new Date(timeStamp + hours * 60 * 60 * 1000).getTime();
}
let tasks = [
  {
    id: 1,
    edit: "<i class=\"el-icon-edit\"></i>",
    label: "Make some noise",
    user:
      '<a href="https://www.google.com/search?q=John+Doe" target="_blank" style="color:#0077c0;">John Doe</a>',
    start: getDate(-24 * 5),
    duration: 15 * 24 * 60 * 60 * 1000,
    percent: 85,
    type: "project"
    //collapsed: true,
  },
  {
    id: 2,
    edit: "<i class=\"el-icon-edit\"></i>",
    label: "With great power comes great responsibility",
    user:
      '<a href="https://www.google.com/search?q=Peter+Parker" target="_blank" style="color:#0077c0;">Peter Parker</a>',
    parentId: 1,
    start: getDate(-24 * 4),
    duration: 4 * 24 * 60 * 60 * 1000,
    percent: 50,
    type: "milestone",
    collapsed: true,
    style: {
      base: {
        fill: "#1EBC61",
        stroke: "#0EAC51"
      }
    }
  },
  {
    id: 3,
    edit: "<i class=\"el-icon-edit\"></i>",
    label: "Courage is being scared to death, but saddling up anyway.",
    user:
      '<a href="https://www.google.com/search?q=John+Wayne" target="_blank" style="color:#0077c0;">John Wayne</a>',
    parentId: 2,
    start: getDate(-24 * 3),
    duration: 2 * 24 * 60 * 60 * 1000,
    percent: 100,
    type: "task"
  },
  {
    id: 4,
    edit: "<i class=\"el-icon-edit\"></i>",
    label: "Put that toy AWAY!",
    user:
      '<a href="https://www.google.com/search?q=Clark+Kent" target="_blank" style="color:#0077c0;">Clark Kent</a>',
    start: getDate(-24 * 2),
    duration: 2 * 24 * 60 * 60 * 1000,
    percent: 50,
    type: "task",
    dependentOn: [3]
  },
  {
    id: 5,
    edit: "<i class=\"el-icon-edit\"></i>",
    label:
      "One billion, gajillion, fafillion... shabadylu...mil...shabady......uh, Yen.",
    user:
      '<a href="https://www.google.com/search?q=Austin+Powers" target="_blank" style="color:#0077c0;">Austin Powers</a>',
    parentId: 4,
    start: getDate(0),
    duration: 2 * 24 * 60 * 60 * 1000,
    percent: 10,
    type: "milestone",
    style: {
      base: {
        fill: "#0287D0",
        stroke: "#0077C0"
      }
    }
  },
  {
    id: 6,
    edit: "<i class=\"el-icon-edit\"></i>",
    label: "Butch Mario and the Luigi Kid",
    user:
      '<a href="https://www.google.com/search?q=Mario+Bros" target="_blank" style="color:#0077c0;">Mario Bros</a>',
    parentId: 5,
    start: getDate(24),
    duration: 1 * 24 * 60 * 60 * 1000,
    percent: 50,
    type: "task",
    collapsed: true,
    style: {
      base: {
        fill: "#8E44AD",
        stroke: "#7E349D"
      }
    }
  }
];
let that
let options = {
  taskMapping: {
    progress: "percent"
  },
  maxRows: 100,
  maxHeight: 500,
  /* title: {
    label: "Your project title as html (link or whatever...)",
    html: false
  }, */
  row: {
    height: 24
  },

  /* calendar: {
    hour: {
      display: true
    }
  }, */
  chart: {
    progress: {
      bar: false
    },
    expander: {
      display: true
    }
  },
  taskList: {
    expander: {
      straight: false
    },
    columns: [
      {
        id: 0,
        label: "编辑",
        value: "edit",
        width: 40,
        html: true,
        events: {
          click({ data, column }) {
            that.edit(data)
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
        label: "任务名称",
        value: "label",
        width: 200,
        expander: true,
        html: true,
        events: {
          click({ data, column }) {
            alert("description clicked!\n" + data.label);
          }
        }
      },
      {
        id: 3,
        label: "分配给",
        value: "user",
        width: 130,
        html: true
      },
      {
        id: 4,
        label: "开始时间",
        value: task => dayjs(task.start).format("YYYY-MM-DD"),
        width: 78
      },
      {
        id: 5,
        label: "结束时间",
        value: task => dayjs(task.start + task.duration).format("YYYY-MM-DD"),
        width: 78
      },
      {
        id: 6,
        label: "持续时间(天)",
        //value: task => dayjs(task.start).format("YYYY-MM-DD"),
        value: task => (task.duration) / (1000 * 3600 * 24),
        width: 90,
        style: {
          "task-list-header-label": {
            "text-align": "center",
            width: "100%"
          },
          "task-list-item-value-container": {
            "text-align": "center",
            width: "100%"
          }
        }
      },
      {
        id: 7,
        label: "类型",
        value: "type",
        width: 68
      },
      {
        id: 8,
        label: "进度%",
        value: "progress",
        width: 60,
        style: {
          "task-list-header-label": {
            "text-align": "center",
            width: "100%"
          },
          "task-list-item-value-container": {
            "text-align": "center",
            width: "100%"
          }
        }
      }
    ]
  },
  /* locale: {
    name: "en",
    Now: "Now",
    "X-Scale": "Zoom-X",
    "Y-Scale": "Zoom-Y",
    "Task list width": "Task list",
    "Before/After": "Expand",
    "Display task list": "Task list"
  } */
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
  name: "Gantt",
  components: {
    GanttElastic,
    //GanttHeader
  },
  data() {
    return {
      tasks,
      options,
      dynamicStyle: {},
      lastId: 16,

      //弹框
      showForm: false,
      formData: {
        id: 0,
        label: '',
        user: "",
        duration: 0,
        start: new Date().getTime(),
        startAndEnd: []
      },
      rules: {
        label: [{
          required: true,
          message: '请输入项目名称',
          trigger: 'blur'
        }],
        user: [{
          required: true,
          message: '请选择负责人',
          trigger: 'change'
        }]
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
  mounted() {
    that = this;
    if (this.gantData) {

      //this.getTaskList(this.gantData.id)
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
    /**
     * 修改
     * @param {object} data 
     */
    edit(data) {
      console.log(data);
      this.$emit('update:visible', true)
      this.showForm = true;
      this.formData.id = data.id;
      this.formData.label = data.label;
      this.formData.user = data.user;
      //new Date(new Date(data.start).getFullYear,new Date(data.start).getMonth,new Date(data.start).getDay,new Date(data.start).getHours,new Date(data.start).getMinutes)
      /* this.formData.startAndEnd = [new Date(new Date(data.start).getFullYear, new Date(data.start).getMonth, new Date(data.start).getDay, new Date(data.start).getHours, new Date(data.start).getMinutes),
      new Date(new Date(data.start + data.duration).getFullYear, new Date(data.start + data.duration).getMonth, new Date(data.start + data.duration).getDay, new Date(data.start + data.duration).getHours, new Date(data.start + data.duration).getMinutes)]; */
      this.formData.startAndEnd = [new Date(data.start), new Date(data.start + data.duration)];
      this.formData.duration = data.duration;
    },
    /**
     * 日期选择框时间改变后，修改duration
     */
    dateChanged() {
      this.formData.duration = new Date(this.formData.startAndEnd[1]).getTime() - new Date(this.formData.startAndEnd[0]).getTime();
      console.log("duration:" + this.formData.duration);
    },
    /**
     * 提交修改
     * @param {*} data 
     */
    handleConfirm(data) {
      console.log(data)
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        let tempTasks = [];
        console.log(this.tasks, this.tasks.length)
        let addTTask = {};
        let tempTTask = {};
        let delObjID = 0;
        console.log(this.tasks.length)
        for (let i = 0; i < this.tasks.length; i++) {
          tempTasks[i] = this.tasks[i];
          if (this.tasks[i].id == this.formData.id) {
            /* this.tasks[i].label = this.formData.label;
            this.tasks[i].start = new Date(this.formData.startAndEnd[0]).getTime();
            this.tasks[i].user = this.formData.user;
            this.tasks[i].duration = this.formData.duration;
            this.tasks[i].startTime = this.tasks[i].start; */
            /* tempTTask = JSON.parse(JSON.stringify(this.tasks[i]));
            tempTTask.label = this.formData.label;
            tempTTask.user = this.formData.user;
            tempTTask.start = new Date(this.formData.startAndEnd[0]).getTime();
            tempTTask.duration = this.formData.duration;
            tempTTask.percent = 50;
            tempTTask.type = "project";
            tempTTask.edit = "<i class=\"el-icon-edit\"></i>";
            console.log("tasks[i]:", this.tasks[i])
            console.log("tempTTask:", tempTTask) */
            addTTask = {
              parentId:this.tasks[i].parentId,
              id: this.formData.id,
              label: this.formData.label,
              user: this.formData.user,
              start: new Date(this.formData.startAndEnd[0]).getTime(),
              duration: this.formData.duration,
              percent: 50,
              type: "project",
              edit: "<i class=\"el-icon-edit\"></i>"
            }
            console.log("tasks[i]:", this.tasks[i])
            console.log("addTTask:", addTTask)
          }
        }

        this.tasks.splice(this.formData.id-1, 1, addTTask);

        this.close();
      })
    },
    addTask() {
      this.tasks.push({
        id: this.tasks.length++,
        label:
          '<a href="https://images.pexels.com/photos/423364/pexels-photo-423364.jpeg?auto=compress&cs=tinysrgb&h=650&w=940" target="_blank" style="color:#0077c0;">Yeaahh! you have added a task bro!</a>',
        user:
          '<a href="https://images.pexels.com/photos/423364/pexels-photo-423364.jpeg?auto=compress&cs=tinysrgb&h=650&w=940" target="_blank" style="color:#0077c0;">Awesome!</a>',
        start: getDate(24 * 3),
        duration: 20 * 24 * 60 * 60 * 1000,
        percent: 50,
        type: "project",
        edit: "<i class=\"el-icon-edit\"></i>"
      });
    },
    tasksUpdate(tasks) {
      this.tasks = tasks;
    },
    optionsUpdate(options) {
      this.options = options;
    },
    styleUpdate(style) {
      this.dynamicStyle = style;
    }
  }
};
</script>