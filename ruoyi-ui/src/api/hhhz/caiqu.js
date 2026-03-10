import request from '@/utils/request'

// 查询采区信息列表
export function listArea(query) {
  return request({
    url: '/area/list',
    method: 'get',
    params: query
  })
}

// 查询采区信息详细
export function getArea(id) {
  return request({
    url: '/area/' + id,
    method: 'get'
  })
}

// 新增采区信息
export function addArea(data) {
  return request({
    url: '/area',
    method: 'post',
    data: data
  })
}

// 修改采区信息
export function updateArea(data) {
  return request({
    url: '/area',
    method: 'put',
    data: data
  })
}

// 删除采区信息
export function delArea(ids) {
  return request({
    url: '/area/remove',
    method: 'delete',
    params: ids
  })
}
