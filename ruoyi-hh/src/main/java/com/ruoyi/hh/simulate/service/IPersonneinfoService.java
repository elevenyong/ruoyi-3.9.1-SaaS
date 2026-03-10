package com.ruoyi.hh.simulate.service;

import com.ruoyi.hh.demo.domain.Personneinfo;

import java.util.List;

/**
 * 人员信息Service接口
 * 
 * @author eleven
 * @date 2023-02-11
 */
public interface IPersonneinfoService 
{
    /**
     * 查询人员信息
     * 
     * @param id 人员信息主键
     * @return 人员信息
     */
    public Personneinfo selectPersonneinfoById(Integer id);

    /**
     * 查询人员信息列表
     * 
     * @param personneinfo 人员信息
     * @return 人员信息集合
     */
    public List<Personneinfo> selectPersonneinfoList(Personneinfo personneinfo);

    /**
     * 新增人员信息
     * 
     * @param personneinfo 人员信息
     * @return 结果
     */
    public int insertPersonneinfo(Personneinfo personneinfo);

    /**
     * 修改人员信息
     * 
     * @param personneinfo 人员信息
     * @return 结果
     */
    public int updatePersonneinfo(Personneinfo personneinfo);

    /**
     * 批量删除人员信息
     * 
     * @param ids 需要删除的人员信息主键集合
     * @return 结果
     */
    public int deletePersonneinfoByIds(Integer[] ids);

    /**
     * 删除人员信息信息
     * 
     * @param id 人员信息主键
     * @return 结果
     */
    public int deletePersonneinfoById(Integer id);
}
