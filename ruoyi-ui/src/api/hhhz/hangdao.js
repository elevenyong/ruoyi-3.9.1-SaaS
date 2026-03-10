import request from '@/utils/request'

// 获取巷道类型
export function gRoadType(query) {
  return request({
    url: '/tRoadtype/list',
    method: 'post',
    data: query
  })
}

// 新增巷道类型信息
export function aRoadType(data) {
  return request({
    url: '/tRoadtype/insertList',
    method: 'post',
    data: data
  })
}

// 修改巷道类型信息
export function uRoadType(data) {
  return request({
    url: '/tRoadtype/updateTRoadtype',
    method: 'put',
    data: data
  })
}

// 删除巷道类型信息
export function dRoadType(id) {
  return request({
    url: '/tRoadtype/deleteTRoadtypeByIds',
    method: 'delete',
    params: id
  })
}

// 获取巷道列表
export function gRoad(data) {
  return request({
    url: '/tRoadway/list',
    method: 'post',
    data: data
  })
}

export function gRoadWayByWorkFace(id) {
  return request({
    url: '/tRoadway/byWorkingFace/'+id,
    method: 'get'
  })
}

// 插入巷道基础信息
export function aRoad(data) {
  return request({
    url: '/tRoadway/insert',
    method: 'post',
    data: data
  })
}

// 更新巷道信息
export function uRoad(data) {
  return request({
    url: '/tRoadway/updateTRoadway',
    method: 'put',
    data: data
  })
}

// 删除巷道信息
export function dRoad(data) {
  return request({
    url: '/tRoadway/deleteTRoadwayById',
    method: 'delete',
    params: data
  })
}
