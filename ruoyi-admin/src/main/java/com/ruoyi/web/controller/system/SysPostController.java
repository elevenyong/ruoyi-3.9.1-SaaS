package com.ruoyi.web.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysRoleService;
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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;

/**
 * 岗位信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/post")
// 整体禁用岗位管理功能，防止历史角色菜单/缓存权限残留后仍可访问
@PreAuthorize("@ss.hasPermi('system:post:disable')")
public class SysPostController extends BaseController {
    @Resource
    private ISysPostService postService;
    @Resource
    private ISysDeptService deptService;

    @Resource
    private ISysRoleService roleService;


    /**
     * 获取岗位列表
     */
    @PreAuthorize("@ss.hasPermi('system:post:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPost post) {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:post:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPost post) {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:post:query')")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId) {
        return AjaxResult.success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:add')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPost post) {
        //岗位名称允许重复
        /*if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post)))
        {
            return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        else*/
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy(getUsername());

        // role操作
        int i = postService.insertPost(post);

        if (i > 0) {
            List<SysPost> sysPosts = postService.selectPostList(post);
            if (sysPosts.size() > 0) {
                SysPost sysPost = sysPosts.get(0);
                SysRole sysRole = new SysRole();
                sysRole.setRoleId(sysPost.getPostId());
                sysRole.setRoleKey(sysPost.getPostId() + "");
                sysRole.setStatus("0");
                sysRole.setMenuCheckStrictly(true);
                sysRole.setDeptCheckStrictly(true);
                sysRole.setDataScope("4");
                sysRole.setRoleSort(0);
                sysRole.setFlag(true);
                sysRole.setMenuIds(post.getMenuIds());
                sysRole.setRoleName(deptService.selectDeptById(post.getDeptId()).getDeptName() + "-" + post.getPostName() + "-" + sysPost.getPostId());
                int i1 = roleService.insertRole(sysRole);
                if (i1 <= 0) {
                    postService.deletePostById(sysPost.getPostId());
                }
                return toAjax(i1);
            }
        }

        return toAjax(i);
    }

    /**
     * 修改岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:edit')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPost post) {
        //岗位名称允许重复
        /*if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post)))
        {
            return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        else */

        ///20230110
        /*if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateBy(getUsername());
        return toAjax(postService.updatePost(post));*/
        ///20230110
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateBy(getUsername());
        int i = postService.updatePost(post);

        if (i > 0) {
            SysRole sysRole = roleService.selectRoleById(post.getPostId());
            sysRole.setStatus("0");
            sysRole.setMenuCheckStrictly(true);
            sysRole.setDeptCheckStrictly(true);
            sysRole.setDataScope("4");
            sysRole.setRoleSort(0);
            sysRole.setFlag(true);
            sysRole.setMenuIds(post.getMenuIds());
            sysRole.setRoleName(deptService.selectDeptById(post.getDeptId()).getDeptName() + "-" + post.getPostName() + "-" + post.getPostId());
            int i1 = roleService.updateRole(sysRole);
            if (i1 <= 0) {
                return AjaxResult.error("权限修改失败，请稍后重试");
            }
            return toAjax(i1);
        }
        return toAjax(i);
        //return toAjax(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds) {
        //return toAjax(postService.deletePostByIds(postIds));
        int i = postService.deletePostByIds(postIds);
        if (i > 0) {
            return toAjax(roleService.deleteRoleByIds(postIds));
        } else {
            return toAjax(i);
        }
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        List<SysPost> posts = postService.selectPostAll();
        return AjaxResult.success(posts);
    }
}