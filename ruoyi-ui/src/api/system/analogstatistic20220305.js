import request from '@/utils/request'

// 查询KJ370Monitor列表
export function listAnalogstatistic20220305(query) {
  return request({
    url: '/system/analogstatistic20220305/list',
    method: 'get',
    params: query,
    timeout: 20000
  })
}

// 查询KJ370Monitor详细
export function getAnalogstatistic20220305(pointid) {
  return request({
    url: '/system/analogstatistic20220305/' + pointid,
    method: 'get'
  })
}

// 新增KJ370Monitor
export function addAnalogstatistic20220305(data) {
  return request({
    url: '/system/analogstatistic20220305',
    method: 'post',
    data: data
  })
}

// 修改KJ370Monitor
export function updateAnalogstatistic20220305(data) {
  return request({
    url: '/system/analogstatistic20220305',
    method: 'put',
    data: data
  })
}

// 删除KJ370Monitor
export function delAnalogstatistic20220305(pointid) {
  return request({
    url: '/system/analogstatistic20220305/' + pointid,
    method: 'delete'
  })
}
