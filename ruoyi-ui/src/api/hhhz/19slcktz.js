import request from "@/utils/request";

// 查询水力冲孔台账
export function gShuilichongkongtaizhang(query) {
  return request({
    //url: "/tActualFlushing/getFlushingStandingBook",
    url: "/tBkActBoreholeInfo/getTBKFlushingStandingBook",
    method: "post",
    params: query,
  });
}

// 查询水力冲孔验收单
export function gShuilichongkongYanshoudan(query) {
  return request({
    url: "/tActualFlushing/getAcceptBills",
    method: "post",
    params: query,
  });
}

// 新增水力冲孔
export function aShuilichongkongtaizhang(query) {
  return request({
    url: "/tActualFlushing/insert",
    method: "post",
    data: query,
  });
}

// 修改水力冲孔
export function uShuilichongkongtaizhang(query) {
  return request({
    url: "/tActualFlushing/updateTActualFlushing",
    method: "put",
    data: query,
  });
}

