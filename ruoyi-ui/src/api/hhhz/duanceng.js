import request from '@/utils/request'

// 查询断层列表
export function listGeofault(query) {
  return request({
    url: '/geofault/list',
    method: 'get',
    params: query
  })
}

// 查询断层详细
export function getGeofault(id) {
  return request({
    url: '/geofault/' + id,
    method: 'get'
  })
}

// 新增断层
export function addGeofault(data) {
  return request({
    url: '/geofault',
    method: 'post',
    data: data
  })
}

// 修改断层
export function updateGeofault(data) {
  return request({
    url: '/geofault',
    method: 'put',
    data: data
  })
}

// 删除断层
export function delGeofault(id) {
  return request({
    url: '/geofault/' + id,
    method: 'delete'
  })
}
