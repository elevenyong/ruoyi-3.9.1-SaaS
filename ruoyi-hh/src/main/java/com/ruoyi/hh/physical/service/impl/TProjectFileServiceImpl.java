package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TProjectFile;
import com.ruoyi.hh.physical.mapper.TProjectFileMapper;
import com.ruoyi.hh.physical.service.ITProjectFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TProjectFileServiceImpl implements ITProjectFileService {
    @Resource
    private TProjectFileMapper tProjectFileMapper;

    /**
     * 查询项目文件
     *
     * @param id 项目文件主键
     * @return 项目文件
     */
    @Override
    public TProjectFile selectTProjectFileById(Integer id) {
        return tProjectFileMapper.selectTProjectFileById(id);
    }

    /**
     * 查询项目文件列表
     *
     * @param tProjectFile 项目文件
     * @return 项目文件
     */
    @Override
    public List<TProjectFile> selectTProjectFileList(TProjectFile tProjectFile) {
        return tProjectFileMapper.selectTProjectFileList(tProjectFile);
    }

    /**
     * 新增项目文件
     *
     * @param tProjectFile 项目文件
     * @return 结果
     */
    @Override
    public int insertTProjectFile(TProjectFile tProjectFile) {
        return tProjectFileMapper.insertTProjectFile(tProjectFile);
    }

    /**
     * 修改项目文件
     *
     * @param tProjectFile 项目文件
     * @return 结果
     */
    @Override
    public int updateTProjectFile(TProjectFile tProjectFile) {
        return tProjectFileMapper.updateTProjectFile(tProjectFile);
    }

    /**
     * 批量删除项目文件
     *
     * @param ids 需要删除的项目文件主键
     * @return 结果
     */
    @Override
    public int deleteTProjectFileByIds(Integer[] ids) {
        return tProjectFileMapper.deleteTProjectFileByIds(ids);
    }

    /**
     * 删除项目文件信息
     *
     * @param id 项目文件主键
     * @return 结果
     */
    @Override
    public int deleteTProjectFileById(Integer id) {
        return tProjectFileMapper.deleteTProjectFileById(id);
    }


}
