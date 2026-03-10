package com.ruoyi.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.domain.entity.SysU3DMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * U3DTreeSelect树结构实体类
 *
 * @author ruoyi
 */
public class U3DTreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String btnName;
    private String btnIcon;
    private Integer menuWeight;
    private String[] redirect;

    /**
     * 子节点
     */
    //@JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<U3DTreeSelect> subMenuBtns;

    public U3DTreeSelect() {

    }

    /*public U3DTreeSelect(SysDept dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(U3DTreeSelect::new).collect(Collectors.toList());
    }

    public U3DTreeSelect(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(U3DTreeSelect::new).collect(Collectors.toList());
    }*/

    public U3DTreeSelect(SysU3DMenu menu) {
        this.id = menu.getId();
        this.btnName = menu.getBtnName();
        this.btnIcon = menu.getBtnIcon();
        this.menuWeight = menu.getMenuWeight();
        String rd = menu.getRedirect();
        if (rd != null) {
            this.redirect = rd.split(",");
        } else {
            this.redirect = new String[]{};
        }
        this.subMenuBtns = menu.getChildren().stream().map(U3DTreeSelect::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/*
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }*/

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getBtnIcon() {
        return btnIcon;
    }

    public void setBtnIcon(String btnIcon) {
        this.btnIcon = btnIcon;
    }

    public Integer getMenuWeight() {
        return menuWeight;
    }

    public void setMenuWeight(Integer menuWeight) {
        this.menuWeight = menuWeight;
    }

    public String[] getRedirect() {
        return redirect;
    }

    public void setRedirect(String[] redirect) {
        this.redirect = redirect;
    }

/*public List<U3DTreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<U3DTreeSelect> children) {
        this.children = children;
    }*/

    public List<U3DTreeSelect> getSubMenuBtns() {
        return subMenuBtns;
    }

    public void setSubMenuBtns(List<U3DTreeSelect> subMenuBtns) {
        this.subMenuBtns = subMenuBtns;
    }
}
