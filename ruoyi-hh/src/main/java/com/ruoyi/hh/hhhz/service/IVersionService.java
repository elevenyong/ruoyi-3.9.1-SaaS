package com.ruoyi.hh.hhhz.service;


import com.ruoyi.hh.hhhz.domain.Version;

import java.util.List;

/**
 * versionService接口
 *
 * @author hhhz
 * @date 2022-08-22
 */
public interface IVersionService {
    /**
     * 查询version
     *
     * @param id version主键
     * @return version
     */
    public Version selectVersionById(Long id);

    /**
     * 查询version列表
     *
     * @param version version
     * @return version集合
     */
    public List<Version> selectVersionList(Version version);

    /**
     * 新增version
     *
     * @param version version
     * @return 结果
     */
    public int insertVersion(Version version);

    /**
     * 修改version
     *
     * @param version version
     * @return 结果
     */
    public int updateVersion(Version version);

    /**
     * 批量删除version
     *
     * @param ids 需要删除的version主键集合
     * @return 结果
     */
    public int deleteVersionByIds(Long[] ids);

    /**
     * 删除version信息
     *
     * @param id version主键
     * @return 结果
     */
    public int deleteVersionById(Long id);

    /**
     * 选择当前版本
     *
     * @param id id
     * @return
     */
    public int putCurrentVersion(Long id);
}
