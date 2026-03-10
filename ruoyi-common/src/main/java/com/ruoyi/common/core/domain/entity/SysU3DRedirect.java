package com.ruoyi.common.core.domain.entity;

public class SysU3DRedirect {
    private Long id;
    private String redirect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    @Override
    public String toString() {
        return "SysU3DRedirect{" +
                "id=" + id +
                ", redirect='" + redirect + '\'' +
                '}';
    }
}
