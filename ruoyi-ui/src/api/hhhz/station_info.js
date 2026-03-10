import request from '@/utils/request'

// 查询测点列表
export function listStationInfo(data) {
  return request({
    url: '/tStationInfo/list',
    method: 'post',
    data: data
  })
}

// 查询测点详细
export function getStationInfo(id) {
  return request({
    url: '/tStationInfo/' + id,
    method: 'post'
  })
}

// 新增测点
export function addStationInfo(data) {
  return request({
    url: '/tStationInfo',
    method: 'post',
    data: data
  })
}

// 修改测点
export function updateStationInfo(param) {
  return request({
    url: '/tStationInfo/setStationInfo',
    method: 'post',
    params: param
  })
}

// 删除测点
export function delStationInfo(id) {
  return request({
    url: '/tStationInfo/' + id,
    method: 'delete'
  })
}
