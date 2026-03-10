<template>
  <div>
    <el-cascader
        v-model="select_options"
        ref="cascader"
        :options="options_cascader"
        :props="getProps"
        :filterable="filterable"
        @change="optionsChange"
        collapse-tags
        clearable></el-cascader>
  </div>
</template>
<script>
import cloneDeep from 'lodash/cloneDeep'

export default {
  name: 'myCascader',
  props: {
    options: { // 级联选择器选项
      type: Array,
      default: []
    },
    is_deep: { // 是否有children，即数据深度是否为多层
      type: Boolean,
      required: true
    },
    has_all_select: { // 是否为带有全部的选项
      type: Boolean,
      required: true
    },
    all_select_flag: { // '全部'选项的标识
      type: String | Number,
      default: ''
    },
    value: {  // 绑定的value值
      type: String,
      default: 'value'
    },
    label: { // 绑定了label值
      type: String,
      default: 'label'
    },
    defaultSelectAll: {// 默认全选
      type: Boolean,
      default: false
    },
    filterable: { // 是否可以模糊搜索（只能选中最后一层子节点）
      type: Boolean,
      default: false
    }
  },
  mounted() {
    this.options_cascader = cloneDeep(this.options) // 深拷贝传过来的数据源，引用了lodash包
    if (!this.is_deep && this.has_all_select && !this.all_select_flag) {
      console.warn('当前为单层数据且含有全部选项，请输入全部选项标识,默认为\'\'，如果全部类型的标识为\'\'请忽略')
    }
  },
  data() {
    return {
      options_cascader: [], // 所有选项
      select_options: [], // 已选择的节点
      is_select_all: false, // 是否为全选
      deep_option_data: [] // 缓存各个深度的value
    }
  },
  watch: {
    options: function(val, oldVal) {
      this.options_cascader = cloneDeep(val) // 深拷贝传过来的数据源，引用了lodash包
      if (!this.is_deep && this.has_all_select && !this.all_select_flag) {
        console.warn('当前为单层数据且含有全部选项，请输入全部选项标识,默认为\'\'，如果全部类型的标识为\'\'请忽略')
      }
      if (this.defaultSelectAll){
        this.is_select_all = true
        this.selectAll()
        this.$emit('getOptions', this.select_options)
      }
    }
  },
  computed: {
    // 获取配置选项
    getProps() {
      return {
        multiple: true,
        expandTrigger: 'hover',
        emitPath: this.is_deep,
        value: this.value,
        label: this.label
      }
    }
  },
  methods: {
    optionsChange() {
      // 判断已选中的节点中是否包含全部
      let has_all_option = false
      if (!this.is_deep) {
        has_all_option = this.select_options.includes(this.all_select_flag)
      } else {
        has_all_option = this.select_options.some(res => res.length === 1)
      }
      /**
       * 如果已选择节点中包含全部，且is_select_all为true时，代表移除了选项中除全部外的某个节点
       * 如果已选择节点中包含全部，且is_select_all为false时，代表选择了全部节点
       * 如果已选择节点中不包含全部，且is_select_all为true时，代表取消选择全部节点
       * */
      if (has_all_option && this.is_select_all) {
        this.$refs.cascader.$refs.panel.clearCheckedNodes()
        this.select_options.splice(this.searchSelectAllNodeIndex(), 1)
        this.is_select_all = false
      } else if (has_all_option && !this.is_select_all) {
        this.is_select_all = true
        this.selectAll()
      } else if (!has_all_option && this.is_select_all) {
        this.is_select_all = false
        this.$refs.cascader.$refs.panel.clearCheckedNodes()
        this.select_options = []
      }
      this.$emit('getOptions', this.select_options)
    },
    // 查找全部节点所在的索引
    searchSelectAllNodeIndex() {
      if (!this.is_deep) {
        return this.select_options.indexOf(this.all_select_flag)
      } else {
        let selectAllOptionIndex = -1
        this.select_options.forEach((res, index) => {
          if (res.length === 1) {
            selectAllOptionIndex = index
          }
        })
        return selectAllOptionIndex
      }
    },
    // 选择全部
    selectAll() {
      this.select_options = []
      if (!this.is_deep) {
        // 为单层数据，即没有children
        this.options_cascader.forEach(res => {
          this.select_options.push(res[this.value])
        })
      } else {
        // 多层数据, 递归
        this.getDeepOptions(this.options_cascader)
      }
    },
    // 递归获取深层数据
    getDeepOptions(value) {
      let arr
      value.forEach(res => {
        if (res.children) {
          // 如果不是最后一层，则缓存当前层次的value
          this.deep_option_data.push(res[this.value])
          this.getDeepOptions(res.children)
        } else {
          // 如果是最后一层，把最后一层的value放入级联选择器绑定的数组
          arr = cloneDeep(this.deep_option_data)
          arr.push(res[this.value])
          this.select_options.push(arr)
        }
      })
      // 每一层循环结束后，清空本层的父节点
      this.deep_option_data.pop()
    }
  }
}
</script>

<style scoped>

</style>
