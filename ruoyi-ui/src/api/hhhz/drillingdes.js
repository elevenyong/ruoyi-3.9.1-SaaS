import request from '@/utils/request'

// 单组钻孔设计
export function drillingSingle(data) {
  return request({
    url: '/drilling/single',
    method: 'post',
    data: data
  })
}

// 条带钻孔设计
export function drillingBand(data) {
  return request({
    url: '/drilling/band',
    method: 'post',
    data: data
  })
}

// 条带钻孔设计
export function drillingReMiningArea(data) {
  return request({
    url: '/drilling/reMiningArea',
    method: 'post',
    data: data
  })
}

// 顺层钻孔设计
export function drillingBedding(data) {
  return request({
    url: '/drilling/bedding',
    method: 'post',
    data: data
  })
}

// 定向钻孔设计
export function drillingDirectional(data) {
  return request({
    url: '/drilling/directional',
    method: 'post',
    data: data
  })
}

// 揭煤钻孔设计
export function drillingUncovering(data) {
  return request({
    url: '/drilling/uncovering',
    method: 'post',
    data: data
  })
}

// 条带钻孔导出
export function exportDrillingBand(data) {
  return request({
    url: '/drilling/band/export',
    method: 'post',
    data: data
  })
}
