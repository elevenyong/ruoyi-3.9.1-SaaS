import request from "@/utils/request";

export function listAttributeType(data){
  return request({
    url: "/tAttributeType/list",
    method: "post",
    data: data,
  });
}
