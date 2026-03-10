import request from "@/utils/request";

// 查询实钻基础信息t_actual_borehole列表
export function listActual(query) {
  return request({
    url: "/tGasExtractionAnalyze/getGasAnalyzeBegin",
    method: "post",
    params: query,
  });
}
