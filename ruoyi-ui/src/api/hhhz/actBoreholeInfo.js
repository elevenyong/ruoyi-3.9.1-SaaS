import request from "@/utils/request";

// 查询实钻基础信息t_actual_borehole列表
export function listActualBorehole(data) {
  return request({
    url: "/tBkActBoreholeInfo/list",
    method: "post",
    data: data,
  });
}
