import request from "@/utils/request";

// 新增导线点
export function aNavPoint(data) {
  return request({
    url: "/tNavigationPoint/insert",
    method: "post",
    data: data,
  });
}

// 删除导线点
export function dNavPoint(data) {
  return request({
    url: "/tNavigationPoint/deleteTNavigationPointById",
    method: "delete",
    params: data,
  });
}

// 更新导线点
export function uNavPoint(data) {
  return request({
    url: "/tNavigationPoint/updateTNavigationPoint",
    method: "put",
    data: data,
  });
}
