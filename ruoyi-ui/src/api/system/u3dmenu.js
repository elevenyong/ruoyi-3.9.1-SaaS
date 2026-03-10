import request from "@/utils/request";

// 查询菜单列表
export function listMenu(query) {
  return request({
    url: "/system/u3dmenu/list",
    method: "get",
    params: query,
  });
}

// 查询菜单详细
export function getMenu(menuId) {
  return request({
    url: "/system/u3dmenu/" + menuId,
    method: "get",
  });
}

// 查询菜单下拉树结构
export function treeselect() {
  return request({
    url: "/system/u3dmenu/treeselect",
    method: "get",
  });
}

// 根据角色ID查询菜单下拉树结构
export function roleMenuTreeselect(roleId) {
  return request({
    url: "/system/u3dmenu/roleMenuTreeselect/" + roleId,
    method: "get",
  });
}

// 新增菜单
export function addMenu(data) {
  return request({
    url: "/system/u3dmenu",
    method: "post",
    data: data,
  });
}

// 修改菜单
export function updateMenu(data) {
  return request({
    url: "/system/u3dmenu",
    method: "put",
    data: data,
  });
}

// 删除菜单
export function delMenu(menuId) {
  return request({
    url: "/system/u3dmenu/" + menuId,
    method: "delete",
  });
}



// 查询菜单指向列表
export function listU3dredirect(query) {
    return request({
      url: '/system/u3dredirect/list',
      method: 'get',
      params: query
    })
  }
  
  // 查询菜单指向详细
  export function getU3dredirect(id) {
    return request({
      url: '/system/u3dredirect/' + id,
      method: 'get'
    })
  }
  
  // 新增菜单指向
  export function addU3dredirect(data) {
    return request({
      url: '/system/u3dredirect',
      method: 'post',
      data: data
    })
  }
  
  // 修改菜单指向
  export function updateU3dredirect(data) {
    return request({
      url: '/system/u3dredirect',
      method: 'put',
      data: data
    })
  }
  
  // 删除菜单指向
  export function delU3dredirect(id) {
    return request({
      url: '/system/u3dredirect/' + id,
      method: 'delete'
    })
  }