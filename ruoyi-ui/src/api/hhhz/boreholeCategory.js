import request from "@/utils/request";

export function listBoreholeCategory(data){
  return request({
    url: "/tBoreholeCategory/list",
    method: "post",
    data: data,
  });
}
