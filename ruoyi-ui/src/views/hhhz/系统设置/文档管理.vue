<template>
  <div style="padding: 50px">
    <client-only>
      <el-x-bubble-list
        :list="typingMessages"
        :max-height="'80%'"
        :defaultIsMarkdown="true"
        :defaultTyping="{ interval: 30, step: 2 }"
        @complete="onTypingComplete"
      />

      <div style="margin-top: 15px;">
        <el-button
          type="primary"
          size="small"
          @click="addMessage"
        >
          添加消息
        </el-button>
        <el-button
          type="success"
          size="small"
          @click="addTypingMessage"
        >
          添加打字效果消息
        </el-button>
      </div>
      <el-x-sender
        style="position:absolute;bottom: 20px;width:80vw"
        ref="senderRef"
        v-model="senderValue"
        :submit-btn-disabled="submitBtnDisabled"
        :loading="senderLoading"
        clearable
        @submit="handleSubmit"
        @cancel="handleCancel"
      />
    </client-only>
  </div>
</template>

<script>
export default {
  data() {
    return {
      typingMessages: [
        {
          content: '这是一条普通消息',
          placement: 'start',
          avatar: 'https://game.gtimg.cn/images/lol/act/img/tft/equip/HandofJustice.png',
          avatarSize: 40,
        },
        {
          content: '这是我的回复',
          placement: 'end',
          avatar: 'https://game.gtimg.cn/images/lol/act/img/tft/equip/tft_set10_emblem_pbj.png',
          avatarSize: 40,
        },
        {
          content:
            '这是一条**带有打字效果**的消息，会逐字显示出来。\n\n当消息很长时，会自动滚动到底部。',
          placement: 'start',
          avatar: 'https://game.gtimg.cn/images/lol/act/img/tft/equip/HandofJustice.png',
          typing: true,
          avatarSize: 40,
        },
      ],
      messageCounter: 0,
    };
  },
  methods: {
    addMessage() {
      this.messageCounter++;
      this.typingMessages.push({
        content: `这是新添加的第${this.messageCounter}条普通消息`,
        placement: this.messageCounter % 2 === 0 ? 'end' : 'start',
        avatarSize: 40,
        avatar:
          this.messageCounter % 2 === 0
            ? 'https://game.gtimg.cn/images/lol/act/img/tft/equip/tft_set10_emblem_pbj.png'
            : 'https://game.gtimg.cn/images/lol/act/img/tft/equip/HandofJustice.png',
      });
    },
    addTypingMessage() {
      this.messageCounter++;
      this.typingMessages.push({
        content: `这是新添加的第${this.messageCounter}条**打字效果**消息，会自动滚动到底部。\n\n- 项目1\n- 项目2\n- 项目3`,
        placement: 'start',
        avatar: 'https://game.gtimg.cn/images/lol/act/img/tft/equip/HandofJustice.png',
        typing: true,
        avatarSize: 40,
      });
    },
    onTypingComplete(instance, index) {
      console.log(`索引 ${index} 的消息完成打字`);
    },
  },
};
</script>
