import request from "@/utils/request";

// 查询实钻基础信息t_actual_borehole列表
export function listDirActualBorehole(data) {
  return request({
    url: "/tActDirectionalBorehole/list",
    method: "post",
    data: data,
  });
}
