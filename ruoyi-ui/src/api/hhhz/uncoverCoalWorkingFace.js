import request from '@/utils/request'

// 查询揭煤工作面列表
export function listUncoverCoal(query) {
  return request({
    url: '/uncoverCoal/list',
    method: 'get',
    params: query
  })
}

// 查询揭煤工作面详细
export function getUncoverCoal(id) {
  return request({
    url: '/uncoverCoal/' + id,
    method: 'get'
  })
}

// 新增揭煤工作面
export function addUncoverCoal(data) {
  return request({
    url: '/uncoverCoal',
    method: 'post',
    data: data
  })
}

// 修改揭煤工作面
export function updateUncoverCoal(data) {
  return request({
    url: '/uncoverCoal',
    method: 'put',
    data: data
  })
}

// 删除揭煤工作面
export function delUncoverCoal(id) {
  return request({
    url: '/uncoverCoal/' + id,
    method: 'delete'
  })
}
