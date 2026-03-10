import request from '@/utils/request'

// 查询抽采单元列表
export function listDrainageUnit(data) {
  return request({
    url: '/tDrainageUnit/list',
    method: 'post',
    data: data
  })
}

// 查询抽采单元详细
export function getDrainageUnit(id) {
  return request({
    url: '/tDrainageUnit/' + id,
    method: 'get'
  })
}

// 新增抽采单元
export function addDrainageUnit(data) {
  return request({
    url: '/tDrainageUnit',
    method: 'post',
    data: data
  })
}

// 修改抽采单元
export function updateDrainageUnit(data) {
  return request({
    url: '/tDrainageUnit',
    method: 'put',
    data: data
  })
}

// 删除抽采单元
export function delDrainageUnit(id) {
  return request({
    url: '/tDrainageUnit/' + id,
    method: 'delete'
  })
}
