import request from '@/utils/request'

// 查询岩层类型列表
export function listRocktype(query) {
  return request({
    url: '/rocktype/list',
    method: 'get',
    params: query
  })
}

// 查询岩层类型详细
export function getRocktype(id) {
  return request({
    url: '/rocktype/' + id,
    method: 'get'
  })
}

// 新增岩层类型
export function addRocktype(data) {
  return request({
    url: '/rocktype',
    method: 'post',
    data: data
  })
}

// 修改岩层类型
export function updateRocktype(data) {
  return request({
    url: '/rocktype',
    method: 'put',
    data: data
  })
}

// 删除岩层类型
export function delRocktype(id) {
  return request({
    url: '/rocktype/' + id,
    method: 'delete'
  })
}
