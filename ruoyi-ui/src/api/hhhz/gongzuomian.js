import request from '@/utils/request'

// 获取工作面信息
export function gWorkFace(data) {
  return request({
    url: '/tWorkingFace/list',
    method: 'post',
    data: data
  })
}

// 批量添加工作面信息
export function aLWorkFace(data) {
  return request({
    url: '/tWorkingFace/insert',
    method: 'post',
    data: data
  })
}

// 更新工作面信息
export function uWorkFace(data) {
  return request({
    url: '/tWorkingFace/updateTWorkingFace',
    method: 'put',
    data: data
  })
}

// 删除工作面信息
export function dWorkFace(data) {
  return request({
    url: '/tWorkingFace/deleteTWorkingFaceByIds',
    method: 'delete',
    params: data
  })
}
