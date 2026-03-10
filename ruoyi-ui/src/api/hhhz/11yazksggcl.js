import request from "@/utils/request";

// 查询实钻基础信息t_actual_borehole列表
export function listActual_borehole(query) {
  return request({
    url: "/tActualBorehole/getBoreholeInfo",
    method: "post",
    params: query,
  });
}
