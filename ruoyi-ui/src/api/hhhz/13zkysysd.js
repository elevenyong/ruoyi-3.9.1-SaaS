import request from '@/utils/request'

// 查询实钻基础信息t_actual_borehole列表
export function gZuankongyuanshiyanshoudan(query) {
  return request({
    url: '/tActualBorehole/getAcceptanceCertificate',
    method: 'post',
    params: query
  })
}

// 更新
export function uZuankongyuanshiyanshoudan(body) {
  return request({
    url: '/tActualBorehole/updateTActualBorehole',
    method: 'put',
    data: body
  })
}

