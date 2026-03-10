import request from "@/utils/request";

export function gFile(data){
  return request({
    url: "/tProjectFile/list",
    method: "post",
    data: data,
  });
}

export function dFile(ids){
  return request({
    url: "/tProjectFile/"+ids,
    method: "delete",
  });
}
