import request from "@/utils/request";

// 查询tGasContentPressure列表
export function listTGasContentPressure(query) {
  return request({
    url: "/tGasContentPressure/getData",
    method: "post",
    params: query,
  });
}

// 查询tGasContentPressure详细
export function getTGasContentPressure(id) {
  return request({
    url: "/tGasContentPressure/" + id,
    method: "get",
  });
}

// 新增tGasContentPressure
export function addTGasContentPressure(data) {
  return request({
    url: "/tGasContentPressure/insert",
    method: "post",
    data: data,
  });
}

// 修改tGasContentPressure
export function updateTGasContentPressure(data) {
  return request({
    url: "/tGasContentPressure/updateTGasContentPressure",
    method: "put",
    data: data,
  });
}

// 删除tGasContentPressure
export function delTGasContentPressure(id) {
  return request({
    url: "/tGasContentPressure/" + id,
    method: "delete",
  });
}
