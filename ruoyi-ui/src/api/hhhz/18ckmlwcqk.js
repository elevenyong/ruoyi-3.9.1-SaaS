import request from "@/utils/request";

// 查询实钻基础信息t_actual_borehole列表
export function gChongkongmeiliang(query) {
  return request({
    url: "/tActualFlushing/getFlushingStatistics",
    method: "post",
    params: query,
  });
}
