package com.ruoyi.hh.hhhz.service.impl;

import com.ruoyi.hh.hhhz.domain.Version;
import com.ruoyi.hh.hhhz.mapper.VersionMapper;
import com.ruoyi.hh.hhhz.service.IVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * versionService业务层处理
 *
 * @author hhhz
 * @date 2022-08-22
 */
@Service
public class VersionServiceImpl implements IVersionService {
    @Autowired
    private VersionMapper versionMapper;

    /**
     * 查询version
     *
     * @param id version主键
     * @return version
     */
    @Override
    public Version selectVersionById(Long id) {
        return versionMapper.selectVersionById(id);
    }

    /**
     * 查询version列表
     *
     * @param version version
     * @return version
     */
    @Override
    public List<Version> selectVersionList(Version version) {
        return versionMapper.selectVersionList(version);
    }

    /**
     * 新增version
     *
     * @param version version
     * @return 结果
     */
    @Override
    public int insertVersion(Version version) {
        return versionMapper.insertVersion(version);
    }

    /**
     * 修改version
     *
     * @param version version
     * @return 结果
     */
    @Override
    public int updateVersion(Version version) {
        return versionMapper.updateVersion(version);
    }

    /**
     * 批量删除version
     *
     * @param ids 需要删除的version主键
     * @return 结果
     */
    @Override
    public int deleteVersionByIds(Long[] ids) {
        return versionMapper.deleteVersionByIds(ids);
    }

    /**
     * 删除version信息
     *
     * @param id version主键
     * @return 结果
     */
    @Override
    public int deleteVersionById(Long id) {
        return versionMapper.deleteVersionById(id);
    }

    @Override
    public int putCurrentVersion(Long id) {
        versionMapper.resetCurrentVersion();
        return versionMapper.putCurrentVersion(id);
    }


}
