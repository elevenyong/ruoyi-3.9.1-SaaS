import request from "@/utils/request";

// 查询tDrainageParameter列表
export function listTDrainageParameter(query) {
  return request({
    url: "/tDrainageParameter/getTDrainageParameter",
    method: "post",
    params: query,
  });
}

// 查询tDrainageParameter详细
export function getTDrainageParameter(id) {
  return request({
    url: "/tDrainageParameter/" + id,
    method: "get",
  });
}

// 新增tDrainageParameter
export function addTDrainageParameter(data) {
  return request({
    url: "/tDrainageParameter/insert",
    method: "post",
    data: data,
  });
}

// 修改tDrainageParameter，/tNavigationPoint/updateTNavigationPoint
export function updateTDrainageParameter(data) {
  return request({
    url: "/tDrainageParameter/updateDrainageParameter",
    method: "put",
    data: data,
  });
}

// 删除tDrainageParameter，/tNavigationPoint/deleteTNavigationPointById
export function delTDrainageParameter(data) {
  return request({
    url: "/tDrainageParameter/deletetDrainageParameterById",
    method: "delete",
    params: data,
  });
}
