package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TProjectFile;
import org.apache.arrow.flatbuf.Int;

import java.util.List;

public interface ITProjectFileService {
    /**
     * 查询项目文件
     *
     * @param id 项目文件主键
     * @return 项目文件
     */
    public TProjectFile selectTProjectFileById(Integer id);

    /**
     * 查询项目文件列表
     *
     * @param tProjectFile 项目文件
     * @return 项目文件集合
     */
    public List<TProjectFile> selectTProjectFileList(TProjectFile tProjectFile);

    /**
     * 新增项目文件
     *
     * @param tProjectFile 项目文件
     * @return 结果
     */
    public int insertTProjectFile(TProjectFile tProjectFile);

    /**
     * 修改项目文件
     *
     * @param tProjectFile 项目文件
     * @return 结果
     */
    public int updateTProjectFile(TProjectFile tProjectFile);

    /**
     * 批量删除项目文件
     *
     * @param ids 需要删除的项目文件主键集合
     * @return 结果
     */
    public int deleteTProjectFileByIds(Integer[] ids);

    /**
     * 删除项目文件信息
     *
     * @param id 项目文件主键
     * @return 结果
     */
    public int deleteTProjectFileById(Integer id);

}
