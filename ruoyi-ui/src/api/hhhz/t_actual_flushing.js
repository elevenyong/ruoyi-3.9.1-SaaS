import request from "@/utils/request";

// 查询tActualFlushing列表
export function listTActualFlushing(query) {
  return request({
    url: "/tActualFlushing/getFlushingStatistics",
    method: "post",
    params: query,
  });
}

// 水力冲孔台账
export function flushingStandingBook(query) {
  return request({
    url: "/tActualFlushing/getFlushingStandingBook",
    method: "post",
    params: query,
  });
}

// 瓦斯抽采钻孔施工台账
export function getTGasDrillingStandingBookList(query) {
  return request({
    url: "/tGasDrillingStandingBook/getTGasDrillingStandingBookList",
    method: "post",
    params: query,
  });
}

// 查询tActualFlushing详细
export function getTActualFlushing(id) {
  return request({
    url: "/tActualFlushing/" + id,
    method: "get",
  });
}

// 新增tActualFlushing
export function addTActualFlushing(data) {
  return request({
    url: "/tActualFlushing",
    method: "post",
    data: data,
  });
}

// 修改tActualFlushing
export function updateTActualFlushing(data) {
  return request({
    url: "/tActualFlushing",
    method: "put",
    data: data,
  });
}

// 删除tActualFlushing
export function delTActualFlushing(id) {
  return request({
    url: "/tActualFlushing/" + id,
    method: "delete",
  });
}
