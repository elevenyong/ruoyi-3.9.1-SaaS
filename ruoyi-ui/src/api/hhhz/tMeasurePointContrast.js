import request from "@/utils/request";

// 查询tMeasurePointContrast列表
export function listTMeasurePointContrast(query) {
  return request({
    url: "/tMeasurePointContrast/getByMeasurePointId",
    method: "post",
    params: query,
  });
}

// 查询tMeasurePointContrast详细
export function getTMeasurePointContrast(id) {
  return request({
    url: "/tMeasurePointContrast/" + id,
    method: "get",
  });
}

// 新增tMeasurePointContrast
export function addTMeasurePointContrast(data) {
  return request({
    url: "/tMeasurePointContrast",
    method: "post",
    data: data,
  });
}

// 修改tMeasurePointContrast
export function updateTMeasurePointContrast(data) {
  return request({
    url: "/tMeasurePointContrast",
    method: "put",
    data: data,
  });
}

// 删除tMeasurePointContrast
export function delTMeasurePointContrast(id) {
  return request({
    url: "/tMeasurePointContrast/" + id,
    method: "delete",
  });
}
