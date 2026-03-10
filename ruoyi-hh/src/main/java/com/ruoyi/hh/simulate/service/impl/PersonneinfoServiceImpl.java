package com.ruoyi.hh.simulate.service.impl;

import java.util.List;

import com.ruoyi.hh.demo.domain.Personneinfo;
import com.ruoyi.hh.simulate.mapper.PersonneinfoMapper;
import com.ruoyi.hh.simulate.service.IPersonneinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 人员信息Service业务层处理
 * 
 * @author eleven
 * @date 2023-02-11
 */
@Service
public class PersonneinfoServiceImpl implements IPersonneinfoService
{
    @Resource
    private PersonneinfoMapper personneinfoMapper;

    /**
     * 查询人员信息
     * 
     * @param id 人员信息主键
     * @return 人员信息
     */
    @Override
    public Personneinfo selectPersonneinfoById(Integer id)
    {
        return personneinfoMapper.selectPersonneinfoById(id);
    }

    /**
     * 查询人员信息列表
     * 
     * @param personneinfo 人员信息
     * @return 人员信息
     */
    @Override
    public List<Personneinfo> selectPersonneinfoList(Personneinfo personneinfo)
    {
        return personneinfoMapper.selectPersonneinfoList(personneinfo);
    }

    /**
     * 新增人员信息
     * 
     * @param personneinfo 人员信息
     * @return 结果
     */
    @Override
    public int insertPersonneinfo(Personneinfo personneinfo)
    {
        return personneinfoMapper.insertPersonneinfo(personneinfo);
    }

    /**
     * 修改人员信息
     * 
     * @param personneinfo 人员信息
     * @return 结果
     */
    @Override
    public int updatePersonneinfo(Personneinfo personneinfo)
    {
        return personneinfoMapper.updatePersonneinfo(personneinfo);
    }

    /**
     * 批量删除人员信息
     * 
     * @param ids 需要删除的人员信息主键
     * @return 结果
     */
    @Override
    public int deletePersonneinfoByIds(Integer[] ids)
    {
        return personneinfoMapper.deletePersonneinfoByIds(ids);
    }

    /**
     * 删除人员信息信息
     * 
     * @param id 人员信息主键
     * @return 结果
     */
    @Override
    public int deletePersonneinfoById(Integer id)
    {
        return personneinfoMapper.deletePersonneinfoById(id);
    }
}
