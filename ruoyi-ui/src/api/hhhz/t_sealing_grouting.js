import request from '@/utils/request'

// 查询tSealingGrouting列表
export function listTSealingGrouting(query) {
  return request({
    url: '/tSealingGrouting/getPageList',
    method: 'post',
    params: query
  })
}

// 查询tSealingGrouting详细
export function getTSealingGrouting(id) {
  return request({
    url: '/tSealingGrouting/' + id,
    method: 'get'
  })
}

// 新增tSealingGrouting
export function addTSealingGrouting(data) {
  return request({
    url: '/tSealingGrouting',
    method: 'post',
    data: data
  })
}

// 修改tSealingGrouting
export function updateTSealingGrouting(data) {
  return request({
    url: '/tSealingGrouting',
    method: 'put',
    data: data
  })
}

// 删除tSealingGrouting
export function delTSealingGrouting(id) {
  return request({
    url: '/tSealingGrouting/' + id,
    method: 'delete'
  })
}
