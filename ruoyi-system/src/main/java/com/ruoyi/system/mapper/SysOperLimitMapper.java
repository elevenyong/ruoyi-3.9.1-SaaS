package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysOperLimit;

/**
 * 接口次数限制Mapper接口
 *
 * @author hh
 * @date 2023-03-01
 */
public interface SysOperLimitMapper
{
    /**
     * 查询接口次数限制
     *
     * @param userid 接口次数限制主键
     * @return 接口次数限制
     */
    public SysOperLimit selectSysOperLimitByUserid(Long userid);

    /**
     * 查询接口次数限制列表
     *
     * @param sysOperLimit 接口次数限制
     * @return 接口次数限制集合
     */
    public List<SysOperLimit> selectSysOperLimitList(SysOperLimit sysOperLimit);

    /**
     * 新增接口次数限制
     *
     * @param sysOperLimit 接口次数限制
     * @return 结果
     */
    public int insertSysOperLimit(SysOperLimit sysOperLimit);

    /**
     * 修改接口次数限制
     *
     * @param sysOperLimit 接口次数限制
     * @return 结果
     */
    public int updateSysOperLimit(SysOperLimit sysOperLimit);

    /**
     * 删除接口次数限制
     *
     * @param userid 接口次数限制主键
     * @return 结果
     */
    public int deleteSysOperLimitByUserid(Long userid);

    /**
     * 批量删除接口次数限制
     *
     * @param userids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysOperLimitByUserids(Long[] userids);
}
