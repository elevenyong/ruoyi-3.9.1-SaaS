<template>
  <div>
    <el-x-typewriter
      :content="basicContent"
      :typing="true"
      ref="basicDemo"
    />

    <div class="demo-controls">
      <el-button-group>
        <el-button
          size="small"
          type="primary"
          @click="startBasic"
        >
          预览
        </el-button>
        <el-button
          size="small"
          @click="resetBasic"
        >
          重置
        </el-button>
      </el-button-group>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      basicContent: '这是一个基础打字效果演示，展示Typewriter组件的基本功能。',
    };
  },
  methods: {
    startBasic() {
      this.$refs.basicDemo.restart();
    },
    resetBasic() {
      this.$refs.basicDemo.restart();
    },
  },
};
</script>

<style>
.demo-controls {
  margin-top: 15px;
}
</style>
