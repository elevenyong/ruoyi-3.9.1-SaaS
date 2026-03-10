// Dify API服务
import request from '@/utils/request';

/**
 * 会话管理API
 */
export const conversationApi = {
  // 获取会话列表
  getConversations(params = {}) {
    return request.get('/ai/conversations', {
      params: {
        limit: 20,
        sort_by: 'created_at',
        ...params,
      },
    });
  },

  // 删除会话
  deleteConversation(conversationId, user) {
    return request.delete(`/ai/conversations/${conversationId}`, {
      data: {
        user,
      },
    });
  },

  // 会话重命名
  renameConversation(conversationId, name, user) {
    return request.post(`/ai/conversations/${conversationId}/name`, {
      name,
      user,
    });
  },

  // 自动生成会话标题
  autoGenerateTitle(conversationId, user) {
    return request.post(`/ai/conversations/${conversationId}/name`, {
      auto_generate: true,
      user,
    });
  },
};

/**
 * 消息管理API
 */
export const messageApi = {
  // 获取会话历史消息
  getMessages(params = {}) {
    return request.get('/ai/messages', {
      params: {
        limit: 20,
        ...params,
      },
    });
  },

  // 发送消息
  sendMessage(data) {
    return request.post('/ai/chat-messages', {
      response_mode: 'streaming',
      inputs: {},
      auto_generate_name: true,
      ...data,
    });
  },

  // 停止响应
  stopMessage(taskId, user) {
    return request.post(`/ai/chat-messages/${taskId}/stop`, {
      user,
    });
  },

  // 消息反馈
  feedbackMessage(messageId, rating, content = '', user) {
    return request.post(`/ai/messages/${messageId}/feedbacks`, {
      rating, // 'like' | 'dislike' | null
      content,
      user,
    });
  },

  // 获取建议问题
  getSuggestedQuestions(messageId, user) {
    return request.get(`/ai/messages/${messageId}/suggested`, {
      params: {
        user,
      },
    });
  },
};

/**
 * 应用信息API
 */
export const appApi = {
  // 获取应用基本信息
  getAppInfo() {
    return request.get('/ai/info');
  },

  // 获取应用参数
  getAppParameters() {
    return request.get('/ai/parameters');
  },

  // 获取应用Meta信息
  getAppMeta() {
    return request.get('/ai/meta');
  },
};

/**
 * 文件上传API
 */
export const fileApi = {
  // 上传文件
  uploadFile(file, user) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('user', user);

    return request.post('/ai/files/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },
};

// 默认导出所有API
export default {
  conversation: conversationApi,
  message: messageApi,
  app: appApi,
  file: fileApi,
};
