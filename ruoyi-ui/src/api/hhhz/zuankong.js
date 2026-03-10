import request from "@/utils/request";

// 获取巷道列表
export function gRoadWayList(query) {
  return request({
    url: "/tRoadway/getPageList",
    method: "post",
    params: query,
  });
}

// 获取组号列表
export function gGroupIdList(query) {
  return request({
    url: "/tActualBorehole/getGroupIdsByRoadwayId",
    method: "post",
    params: query,
  });
}

// 获取钻孔序号列表
export function gHoleNoList(query) {
  return request({
    url: "/tActualBorehole/getBoreholenoByRoadwayIdAndGroupId",
    method: "post",
    params: query,
  });
}

// 获取钻孔序号列表以及钻孔ID
export function gHoleNoListWithId(query) {
  return request({
    url: "/tActualBorehole/getIdAndBoreholeNo",
    method: "post",
    params: query,
  });
}

// 获取钻孔用途列表
export function gZuankongyongtuList(data) {
  return request({
    url: "/tPurposeBorehole/list",
    method: "post",
    data: data,
  });
}
