import request from '@/utils/request'

// 查询租户列表
export function listTenant(query) {
  return request({
    url: '/system/tenant/list',
    method: 'get',
    params: query
  })
}

// 查询租户详情（按tenantCode）
export function getTenantByCode(tenantCode) {
  return request({
    url: '/system/tenant/' + tenantCode,
    method: 'get'
  })
}

// 查询租户详情（按tenantId）
export function getTenant(tenantId) {
  return request({
    url: '/system/tenant/id/' + tenantId,
    method: 'get'
  })
}

// 新增租户
export function addTenant(data) {
  return request({
    url: '/system/tenant',
    method: 'post',
    data: data
  })
}

// 修改租户
export function updateTenant(data) {
  return request({
    url: '/system/tenant',
    method: 'put',
    data: data
  })
}

// 删除租户
export function delTenant(tenantIds) {
  return request({
    url: '/system/tenant/' + tenantIds,
    method: 'delete'
  })
}

// 导出租户
export function exportTenant(query) {
  return request({
    url: '/system/tenant/export',
    method: 'get',
    params: query
  })
}

// 修改租户状态
export function changeTenantStatus(tenantId, status) {
  const data = {
    tenantId,
    status
  }
  return request({
    url: '/system/tenant/changeStatus',
    method: 'put',
    data: data
  })
}
