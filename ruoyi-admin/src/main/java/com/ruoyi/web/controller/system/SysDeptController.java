package com.ruoyi.web.controller.system;

import java.util.Iterator;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.service.ISysRoleService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDeptService;

import javax.annotation.Resource;

/**
 * 部门信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController
{
    @Autowired
    private ISysDeptService deptService;

    @Resource
    private ISysRoleService roleService;

    /**
     * 获取部门列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDept dept)
    {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return success(depts);
    }

    /**
     * 查询部门列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list/exclude/{deptId}")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        Iterator<SysDept> it = depts.iterator();
        while (it.hasNext()) {
            SysDept d = (SysDept) it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
                it.remove();
            }
        }
        return AjaxResult.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId)
    {
        deptService.checkDeptDataScope(deptId);
        return success(deptService.selectDeptById(deptId));
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect() {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 加载对应角色部门列表树
     */
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public AjaxResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        ajax.put("depts", deptService.buildDeptTreeSelect(depts));
        return ajax;
    }

    /**
     * 新增部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(getUsername());
        // 新增部门
        int i = deptService.insertDept(dept);

        if (i > 0) {
            // insertDept 已开启主键回填，直接使用 dept.getDeptId()
            Long deptId = dept.getDeptId();
            if (deptId == null) {
                return AjaxResult.error("部门新增成功，但未获取到部门ID，请检查主键回填配置");
            }
            SysRole sysRole = new SysRole();
            sysRole.setRoleId(deptId);
            sysRole.setRoleKey(String.valueOf(deptId));
            sysRole.setStatus("0");
            sysRole.setMenuCheckStrictly(true);
            sysRole.setDeptCheckStrictly(true);
            sysRole.setDataScope("4");
            sysRole.setRoleSort(0);
            sysRole.setFlag(true);
            sysRole.setMenuIds(dept.getMenuIds());
            sysRole.setRoleName(dept.getDeptName() + "-" + deptId);
            sysRole.setCreateBy(getUsername());

            int i1 = roleService.insertRole(sysRole);
            if (i1 <= 0) {
                deptService.deleteDeptById(deptId);
            }
            return toAjax(i1);
        }
        return toAjax(i);
    }

    /**
     * 修改部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDept dept) {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(deptId)) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return AjaxResult.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(getUsername());
        // 先更新部门
        int i = deptService.updateDept(dept);

        if (i > 0) {
            // 再同步“部门对应角色”；历史数据可能不存在 role_id = dept_id 的记录
            SysRole sysRole = roleService.selectRoleById(deptId);
            int i1;

            if (sysRole == null) {
                sysRole = new SysRole();
                sysRole.setRoleId(deptId);
                sysRole.setRoleKey(String.valueOf(deptId));
                sysRole.setStatus("0");
                sysRole.setMenuCheckStrictly(true);
                sysRole.setDeptCheckStrictly(true);
                sysRole.setDataScope("4");
                sysRole.setRoleSort(0);
                sysRole.setFlag(true);
                sysRole.setMenuIds(dept.getMenuIds());
                sysRole.setRoleName(dept.getDeptName() + "-" + deptId);
                sysRole.setCreateBy(getUsername());
                i1 = roleService.insertRole(sysRole);
            } else {
                sysRole.setStatus("0");
                sysRole.setMenuCheckStrictly(true);
                sysRole.setDeptCheckStrictly(true);
                sysRole.setDataScope("4");
                sysRole.setRoleSort(0);
                sysRole.setFlag(true);
                sysRole.setMenuIds(dept.getMenuIds());
                sysRole.setRoleName(dept.getDeptName() + "-" + deptId);
                sysRole.setUpdateBy(getUsername());
                i1 = roleService.updateRole(sysRole);
            }
            if (i1 <= 0) {
                return AjaxResult.error("权限修改失败，请稍后重试");
            }
            return toAjax(i1);
        }
        return toAjax(i);

        //return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId) {

        /*if (deptService.hasChildByDeptId(deptId)) {
            return AjaxResult.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistPost(deptId)) {
            return AjaxResult.error("部门存在下级岗位,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return AjaxResult.error("部门存在用户,不允许删除");
        }

        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));*/
        if (deptService.hasChildByDeptId(deptId)) {
            return AjaxResult.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistPost(deptId)) {
            return AjaxResult.error("部门存在下级岗位,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return AjaxResult.error("部门存在用户,不允许删除");
        }

        deptService.checkDeptDataScope(deptId);

        int i = deptService.deleteDeptById(deptId);

        if (i > 0) {
            // 历史数据可能不存在对应角色，避免因为删角色返回 0 导致部门删除看起来失败
            SysRole sysRole = roleService.selectRoleById(deptId);
            if (sysRole != null) {
                roleService.deleteRoleById(deptId);
            }
            return toAjax(i);
        } else {
            return toAjax(i);
        }

        //return toAjax(deptService.deleteDeptById(deptId));
    }
}
