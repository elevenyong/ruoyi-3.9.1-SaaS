// 获取钻孔序号列表以及钻孔ID
import request from '@/utils/request'

// 获取煤层信息
export function gCoalInfo(data) {
  return request({
    url: '/tCoalinfo/list',
    method: 'post',
    data: data
  })
}

// 添加煤层基础信息
export function aCoalInfo(data) {
  return request({
    url: '/tCoalinfo/insertList',
    method: 'post',
    data: data
  })
}

// 更新煤层基础信息
export function uCoalInfo(data) {
  return request({
    url: '/tCoalinfo/updateTCoalinfo',
    method: 'put',
    data: data
  })
}

// 删除煤层
export function dCoalInfo(ids) {
  return request({
    url: '/tCoalinfo/deleteTCoalinfoByIds',
    method: 'delete',
    params: ids
  })
}

////////////POI////////////////
// 获取煤层信息
export function gCoalPoi(data) {
  return request({
    url: '/pointinfo/list',
    method: 'post',
    data: data
  })
}

// 添加煤层基础信息
export function aCoalPoi(data) {
  return request({
    url: '/pointinfo/insert',
    method: 'post',
    data: data
  })
}

// 更新煤层基础信息
export function uCoalPoi(data) {
  return request({
    url: '/pointinfo/updateCoalPointinfo',
    method: 'put',
    data: data
  })
}

// 删除煤层
export function dCoalPoi(ids) {
  return request({
    url: '/pointinfo/del',
    method: 'delete',
    params: ids
  })
}

/////////////////// 煤层坐标点类型 ////////////////////
// 查询煤层坐标点类型列表
export function lCoalPoiType(query) {
  return request({
    url: '/pointtype/list',
    method: 'get',
    params: query
  })
}

// 查询煤层坐标点类型详细
export function gCoalPoiType(id) {
  return request({
    url: '/pointtype/' + id,
    method: 'get'
  })
}

// 新增煤层坐标点类型
export function aCoalPoiType(data) {
  return request({
    url: '/pointtype',
    method: 'post',
    data: data
  })
}

// 修改煤层坐标点类型
export function uCoalPoiType(data) {
  return request({
    url: '/pointtype',
    method: 'put',
    data: data
  })
}

// 删除煤层坐标点类型
export function dCoalPoiType(id) {
  return request({
    url: '/pointtype/' + id,
    method: 'delete'
  })
}

