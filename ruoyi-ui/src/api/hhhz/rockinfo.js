import request from '@/utils/request'

// 查询岩层信息列表
export function listRockinfo(query) {
  return request({
    url: '/rockinfo/list',
    method: 'get',
    params: query
  })
}

// 查询岩层信息详细
export function getRockinfo(id) {
  return request({
    url: '/rockinfo/' + id,
    method: 'get'
  })
}

// 新增岩层信息
export function addRockinfo(data) {
  return request({
    url: '/rockinfo',
    method: 'post',
    data: data
  })
}

// 修改岩层信息
export function updateRockinfo(data) {
  return request({
    url: '/rockinfo',
    method: 'put',
    data: data
  })
}

// 删除岩层信息
export function delRockinfo(id) {
  return request({
    url: '/rockinfo/' + id,
    method: 'delete'
  })
}
