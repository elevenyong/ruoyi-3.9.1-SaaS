import request from '@/utils/request'

// 查询采煤工作面列表
export function listCoalminingworkingface(query) {
  return request({
    url: '/coalMiningWorkingFace/list',
    method: 'get',
    params: query
  })
}

// 查询采煤工作面详细
export function getCoalminingWorkingfaceById(id) {
  return request({
    url: '/coalMiningWorkingFace/' + id,
    method: 'get'
  })
}

// 新增采煤工作面
export function addCoalminingworkingface(data) {
  return request({
    url: '/coalMiningWorkingFace',
    method: 'post',
    data: data
  })
}

// 修改采煤工作面
export function updateCoalminingworkingface(data) {
  return request({
    url: '/coalMiningWorkingFace',
    method: 'put',
    data: data
  })
}

// 删除采煤工作面
export function delCoalminingworkingface(id) {
  return request({
    url: '/coalMiningWorkingFace/' + id,
    method: 'delete'
  })
}
