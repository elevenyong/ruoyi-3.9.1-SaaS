import request from "@/utils/request";

// 查询封孔注浆记录列表
export function gFengkongzhujiang(query) {
  return request({
    url: "/tSealingGrouting/getSealingGroutingRecord",
    method: "post",
    params: query,
  });
}

// 新增封孔注浆记录
export function aFengkongzhujiang(body) {
  return request({
    url: "/tSealingGrouting/insert",
    method: "post",
    data: body,
  });
}

// 修改封孔注浆记录
export function uFengkongzhujiang(body) {
  return request({
    url: "/tSealingGrouting/updateTSealingGrouting",
    method: "put",
    data: body,
  });
}
