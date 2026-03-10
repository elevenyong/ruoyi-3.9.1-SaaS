import request from "@/utils/request";

// 查询实钻基础信息t_actual_borehole列表
export function listActual_borehole(query) {
  return request({
    url: "/tBkActBoreholeInfo/getBoreholeInfo",
    method: "post",
    params: query,
  });
}

// 查询实钻基础信息t_actual_borehole详细
export function getActual_borehole(id) {
  return request({
    url: "/system/actual_borehole/" + id,
    method: "get",
  });
}

// 新增实钻基础信息t_actual_borehole
export function addActual_borehole(data) {
  return request({
    url: "/system/actual_borehole",
    method: "post",
    data: data,
  });
}

// 修改实钻基础信息t_actual_borehole
export function updateActual_borehole(data) {
  return request({
    url: "/system/actual_borehole",
    method: "put",
    data: data,
  });
}

// 删除实钻基础信息t_actual_borehole
export function delActual_borehole(id) {
  return request({
    url: "/tActualBorehole/deleteTActualBoreholeByIds/?ids=" + id,
    method: "delete",
  });
}

// 获取测点列表
export function gCeDian(data) {
  return request({
    url: "/tMeasuringPoint/getPageList",
    method: "post",
    params: data,
  });
}
