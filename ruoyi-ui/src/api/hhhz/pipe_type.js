import request from '@/utils/request'

// 查询管道类型列表
export function listPipeType(data) {
  return request({
    url: '/tDrainagePipeType/list',
    method: 'post',
    data: data
  })
}

// 查询管道类型详细
export function getPipeType(id) {
  return request({
    url: '/tDrainagePipeType/' + id,
    method: 'get'
  })
}

// 新增管道类型
export function addPipeType(data) {
  return request({
    url: '/tDrainagePipeType/insert',
    method: 'post',
    data: data
  })
}

// 修改管道类型
export function updatePipeType(data) {
  return request({
    url: '/tDrainagePipeType/updateTDrainagePipeType',
    method: 'put',
    data: data
  })
}

// 删除管道类型
export function delPipeType(param) {
  return request({
    url: '/tDrainagePipeType/deleteTDrainagePipeTypeById',
    method: 'delete',
    params: param
  })
}
