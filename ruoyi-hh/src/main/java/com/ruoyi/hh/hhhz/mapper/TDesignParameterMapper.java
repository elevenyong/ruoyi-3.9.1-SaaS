package com.ruoyi.hh.hhhz.mapper;

import com.ruoyi.hh.hhhz.domain.param.DesignParameters;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设计参数Mapper接口
 *
 * @author hhhz
 * @date 2023-04-24
 */
public interface TDesignParameterMapper {

    /**
     * 查询设计参数
     *
     * @param id 设计参数主键
     * @return 设计参数记录
     */
    public DesignParameters selectDesignParametersById(Integer id);

    /**
     * 查询设计参数记录列表
     *
     * @param designParameters 设计参数记录
     * @return 设计参数集合
     */
    public List<DesignParameters> selectTDesignParametersList(DesignParameters designParameters);

    /**
     * 新增设计参数
     *
     * @param designParameters 钻孔设计参数记录
     * @return 结果
     */
    public int insertDesignParameters(DesignParameters designParameters);

    /**
     * 修改设计参数
     *
     * @param designParameters 设计参数
     * @return 结果
     */
    public int updateDesignParameters(DesignParameters designParameters);

    /**
     * 删除设计参数记录
     *
     * @param id 设计参数主键
     * @return 结果
     */
    public int deleteDesignParametersById(Integer id);

    /**
     * 批量删除设计参数记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDesignParametersByIds(Integer[] ids);

    /**
     * 获取最新一条数据
     * @return
     */
    public DesignParameters getLastOne();

    /**
     * 更改参数的标识
     * @param ids 列表
     */
    public void updateSign(@Param("sign") Integer sign, @Param("list") List<Integer> ids);



}
