import request from '@/utils/request'

// 查询采煤工作面列表
export function listCoalPointInfo(query) {
  return request({
    url: '/pointinfo/list',
    method: 'get',
    params: query
  })
}

// 查询采煤工作面详细
export function getCoalPointInfoById(id) {
  return request({
    url: '/pointinfo/' + id,
    method: 'get'
  })
}

// 新增采煤工作面
export function addCoalPointInfo(data) {
  return request({
    url: '/pointinfo',
    method: 'post',
    data: data
  })
}

// 修改采煤工作面
export function updateCoalPointInfo(data) {
  return request({
    url: '/pointinfo',
    method: 'put',
    data: data
  })
}

// 修改采煤工作面
export function insertCoalPointInfoList(data) {
  return request({
    url: '/pointinfo/insertList',
    method: 'post',
    data: data
  })
}

// 删除采煤工作面
export function delCoalPointInfo(id) {
  return request({
    url: '/pointinfo/' + id,
    method: 'delete'
  })
}
