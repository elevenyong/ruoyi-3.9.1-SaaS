package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysOperLimitMapper;
import com.ruoyi.system.domain.SysOperLimit;
import com.ruoyi.system.service.ISysOperLimitService;

import javax.annotation.Resource;

/**
 * 接口次数限制Service业务层处理
 *
 * @author hh
 * @date 2023-03-01
 */
@Service
public class SysOperLimitServiceImpl implements ISysOperLimitService
{
    @Resource
    private SysOperLimitMapper sysOperLimitMapper;

    /**
     * 查询接口次数限制
     *
     * @param userid 接口次数限制主键
     * @return 接口次数限制
     */
    @Override
    public SysOperLimit selectSysOperLimitByUserid(Long userid)
    {
        return sysOperLimitMapper.selectSysOperLimitByUserid(userid);
    }

    /**
     * 查询接口次数限制列表
     *
     * @param sysOperLimit 接口次数限制
     * @return 接口次数限制
     */
    @Override
    public List<SysOperLimit> selectSysOperLimitList(SysOperLimit sysOperLimit)
    {
        return sysOperLimitMapper.selectSysOperLimitList(sysOperLimit);
    }

    /**
     * 新增接口次数限制
     *
     * @param sysOperLimit 接口次数限制
     * @return 结果
     */
    @Override
    public int insertSysOperLimit(SysOperLimit sysOperLimit)
    {
        return sysOperLimitMapper.insertSysOperLimit(sysOperLimit);
    }

    /**
     * 修改接口次数限制
     *
     * @param sysOperLimit 接口次数限制
     * @return 结果
     */
    @Override
    public int updateSysOperLimit(SysOperLimit sysOperLimit)
    {
        return sysOperLimitMapper.updateSysOperLimit(sysOperLimit);
    }

    /**
     * 批量删除接口次数限制
     *
     * @param userids 需要删除的接口次数限制主键
     * @return 结果
     */
    @Override
    public int deleteSysOperLimitByUserids(Long[] userids)
    {
        return sysOperLimitMapper.deleteSysOperLimitByUserids(userids);
    }

    /**
     * 删除接口次数限制信息
     *
     * @param userid 接口次数限制主键
     * @return 结果
     */
    @Override
    public int deleteSysOperLimitByUserid(Long userid)
    {
        return sysOperLimitMapper.deleteSysOperLimitByUserid(userid);
    }
}
