import request from "@/utils/request";

// 新增巷道区段
export function aRoadZone(data) {
  return request({
    url: "/tRoadwayZone/insert",
    method: "post",
    data: data,
  });
}

// 删除巷道区段
export function dRoadZone(data) {
  return request({
    url: "/tRoadwayZone/deleteTRoadwayZoneById",
    method: "delete",
    params: data,
  });
}

// 更新巷道区段
export function uRoadZone(data) {
  return request({
    url: "/tRoadwayZone/updateTRoadwayZone",
    method: "put",
    data: data,
  });
}