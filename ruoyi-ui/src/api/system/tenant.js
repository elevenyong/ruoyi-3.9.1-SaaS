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
export function getTenant(tenantCode) {
  return request({
    url: '/system/tenant/' + tenantId,
    method: 'get'
  })
}

// 创建租户（同步创建租户管理员账号）
export function addTenant(data) {
  return request({
    url: '/system/tenant',
    method: 'post',
    data: data
  })
}

// 更新租户（包含停用/启用：status）
export function updateTenant(data) {
  return request({
    url: '/system/tenant',
    method: 'put',
    data: data
  })
}

// 初始化（或重新初始化）租户基础数据
export function initTenantBase(tenantId, templateTenantId) {
    return request({
      url: '/system/tenant/' + tenantId + '/init-base',
      method: 'post',
      data: { templateTenantId }
    })
  }
  
// 重置租户管理员密码
export function resetTenantAdminPwd(tenantId, adminUserName, newPassword) {
  return request({
    url: '/system/tenant/' + tenantId + '/reset-admin-pwd',
    method: 'post',
    data: { adminUserName, newPassword }
  })
}

export function changeTenantStatus(tenantId, status) {
  return request({
    url: '/system/tenant/changeStatus',
    method: 'put',
    data: { tenantId, status }
  })
}