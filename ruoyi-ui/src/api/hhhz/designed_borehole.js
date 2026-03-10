import request from '@/utils/request'

/**
 * 设计钻孔信息
 */

/**
 * 查询设计钻孔
 * @param query
 * @returns {AxiosPromise}
 */
export function lDesignedBorehole(data, params) {
  return request({
    url: '/tDesignedBorehole/list',
    method: 'post',
    params: params,
    data: data
  })
}

/**
 * 新增设计钻孔
 * @param data
 * @returns {AxiosPromise}
 */
export function aDesignedBorehole(data) {
  return request({
    url: '/tDesignedBorehole/insertList',
    method: 'post',
    data: data
  })
}

/**
 * 更新设计钻孔
 * @param data
 * @returns {AxiosPromise}
 */
export function uDesignedBorehole(data) {
  return request({
    url: '/tGasContentPressure/updateTGasContentPressure',
    method: 'put',
    data: data
  })
}

/**
 * 删除设计钻孔
 * @param id
 * @returns {AxiosPromise}
 */
export function dDesignedBorehole(ids) {
  return request({
    url: '/tDesignedBorehole/deleteTRoadwayByIds',
    method: 'delete',
    params: ids
  })
}
