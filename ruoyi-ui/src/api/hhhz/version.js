import request from "@/utils/request";

// 查询version列表
export function listVersion(query) {
  return request({
    url: "/version/list",
    method: "get",
    params: query,
  });
}

// 查询version详细
export function getVersion(versionId) {
  return request({
    url: "/version/" + versionId,
    method: "get",
  });
}

// 新增version
export function addVersion(data) {
  return request({
    url: "/version",
    method: "post",
    data: data,
  });
}

// 修改version
export function updateVersion(data) {
  return request({
    url: "/version",
    method: "put",
    data: data,
  });
}

// 删除version
export function delVersion(versionId) {
  return request({
    url: "/version/" + versionId,
    method: "delete",
  });
}

export function putCurrentVersion(versionId) {
  return request({
    url: "/version/current/" + versionId,
    method: "put",
  });
}
