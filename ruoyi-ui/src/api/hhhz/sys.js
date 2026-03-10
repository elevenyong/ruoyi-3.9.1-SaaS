import request from "@/utils/request";

// 查询实钻基础信息t_actual_borehole列表
export function gGongzuobanci() {
  return request({
    url: "/tShiftinfo/getPageList",
    method: "post"
  });
}
