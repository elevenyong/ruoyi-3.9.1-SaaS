package com.ruoyi.hh.business.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.hh.business.domain.TDesSchemeInfo;
import com.ruoyi.hh.business.mapper.TDesSchemeInfoMapper;
import com.ruoyi.hh.business.service.ITDesSchemeInfoService;
import com.ruoyi.hh.hhhz.domain.param.DesignParameters;
import com.ruoyi.hh.hhhz.mapper.TDesignParameterMapper;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import com.ruoyi.hh.physical.service.ITDesignedDirectionalBoreholeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 设计方案信息Service业务层处理
 *
 * @author eleven
 * @date 2023-06-30
 */
@Service
public class TDesSchemeInfoServiceImpl implements ITDesSchemeInfoService {
    @Resource
    private TDesSchemeInfoMapper tDesSchemeInfoMapper;
    @Resource
    private TDesignParameterMapper tDesignParameterMapper;
    @Resource
    private ITDesignedBoreholeService iTDesignedBoreholeService;
    @Resource
    private ITDesignedDirectionalBoreholeService iTDesDirectionBoreholeService;

    /**
     * 查询设计方案信息
     *
     * @param id 设计方案信息主键
     * @return 设计方案信息
     */
    @Override
    public TDesSchemeInfo selectTDesSchemeInfoById(Integer id) {
        List<DesignParameters> designParameters = getByDesSchemeId(id);
        TDesSchemeInfo tsi = tDesSchemeInfoMapper.selectTDesSchemeInfoById(id);
        tsi.setDesignParameters(designParameters);
        return tsi;
    }

    /**
     * 查询设计方案信息列表
     *
     * @param tDesSchemeInfo 设计方案信息
     * @return 设计方案信息
     */
    @Override
    public List<TDesSchemeInfo> selectTDesSchemeInfoList(TDesSchemeInfo tDesSchemeInfo) {
        List<TDesSchemeInfo> list = tDesSchemeInfoMapper.selectTDesSchemeInfoList(tDesSchemeInfo);
        for (TDesSchemeInfo tds : list) {
            List<DesignParameters> designParameters = getByDesSchemeId(tds.getId());
            tds.setDesignParameters(designParameters);
        }
        return list;
    }

    /**
     * 新增设计方案信息
     *
     * @param tDesSchemeInfo 设计方案信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTDesSchemeInfo(TDesSchemeInfo tDesSchemeInfo) {
        tDesSchemeInfo.setCreateTime(DateUtils.getNowDate());
        Integer result = tDesSchemeInfoMapper.insertTDesSchemeInfo(tDesSchemeInfo);
        Integer id = tDesSchemeInfo.getId();
        List<DesignParameters> list = tDesSchemeInfo.getDesignParameters();
        if (list != null) {
            for (DesignParameters dp : list) {
                DesignParameters d = this.tDesignParameterMapper.selectDesignParametersById(dp.getId());
                this.tDesignParameterMapper.updateDesignParameters(appendSchemeId(d, id));
            }
        }
        return result;
    }

    /**
     * 修改设计方案信息
     *
     * @param tDesSchemeInfo 设计方案信息
     * @return 结果
     */
    @Override
    public int updateTDesSchemeInfo(TDesSchemeInfo tDesSchemeInfo) {
        return tDesSchemeInfoMapper.updateTDesSchemeInfo(tDesSchemeInfo);
    }

    /**
     * 批量删除设计方案信息
     *
     * @param ids 需要删除的设计方案信息主键
     * @return 结果
     */
    @Override
    public int deleteTDesSchemeInfoByIds(Integer[] ids) {
        return tDesSchemeInfoMapper.deleteTDesSchemeInfoByIds(ids);
    }

    /**
     * 删除设计方案信息信息
     *
     * @param id 设计方案信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTDesSchemeInfoById(Integer id) {
        List<DesignParameters> designParameters = getByDesSchemeId(id);
        if (designParameters != null) {
            for (DesignParameters dp : designParameters) {
                this.tDesignParameterMapper.updateDesignParameters(subtractionSchemeId(dp, id));
            }
        }
        return tDesSchemeInfoMapper.deleteTDesSchemeInfoById(id);
    }

    /**
     * 根据方案ID查询设计参数id列表
     *
     * @param id 方案ID
     * @return
     */
    @Override
    public List<DesignParameters> getByDesSchemeId(Integer id) {
        //遍历所有的设计参数，找到设计参数中的SchemeId，看是否含有该方案的ID，有的话，保存并返回
        List<DesignParameters> list = this.tDesignParameterMapper.selectTDesignParametersList(new DesignParameters());
        list.removeIf(dp -> !isIn(dp.getSchemeId().split(","), id.toString()));
        return list;
    }

    /**
     * 判断数组中是否包含某元素
     *
     * @param strings 数组
     * @param str     某元素
     * @return
     */
    public boolean isIn(String[] strings, String str) {
        for (String s : strings) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 确定方案
     *
     * @param id 方案ID
     */
    @Override
    public void masureDesScheme(Integer id) {
        // 全部sign初始化为0
        TDesSchemeInfo tDesSchemeInfo = this.tDesSchemeInfoMapper.selectTDesSchemeInfoById(id);
        List<TDesSchemeInfo> tDesSchemeInfos = this.tDesSchemeInfoMapper.getByWorkfaceId(tDesSchemeInfo.getWorkfaceId());
        if (tDesSchemeInfos != null && tDesSchemeInfos.size() > 0) {
            for (TDesSchemeInfo t : tDesSchemeInfos) {
                t.setSign(0);
                List<DesignParameters> designParameters = getByDesSchemeId(t.getId());
                List<Integer> list = new ArrayList<>();
                if (designParameters != null) {
                    for (DesignParameters dp : designParameters) {
                        list.add(dp.getId());
                    }
                    iTDesignedBoreholeService.makeSureDesProgramme(0, list);
                    iTDesDirectionBoreholeService.makeSureDesProgramme(0, list);
                }
                this.tDesSchemeInfoMapper.updateTDesSchemeInfo(t);
            }
        }
        // 将选中方案的钻孔sign设为1
        tDesSchemeInfo.setSign(1);
        List<DesignParameters> designParameters = getByDesSchemeId(id);
        List<Integer> list = new ArrayList<>();
        if (designParameters != null) {
            for (DesignParameters dp : designParameters) {
                list.add(dp.getId());
            }
            iTDesignedBoreholeService.makeSureDesProgramme(1, list);
            iTDesDirectionBoreholeService.makeSureDesProgramme(1, list);
        }
        this.tDesSchemeInfoMapper.updateTDesSchemeInfo(tDesSchemeInfo);
    }

    /**
     * 设计参数追加方案ID
     *
     * @param designParameters
     * @param schemeId
     * @return
     */
    @Override
    public DesignParameters appendSchemeId(DesignParameters designParameters, Integer schemeId) {
        if (designParameters.getSchemeId() != null && !designParameters.getSchemeId().isEmpty()) {
            String[] split = designParameters.getSchemeId().split(",");
            StringBuilder schemeIdStr = new StringBuilder();
            for (String s : split) {
                if (s != null && !s.isEmpty()) {
                    schemeIdStr.append(s).append(",");
                }
            }
            schemeIdStr.append(schemeId);
            designParameters.setSchemeId(schemeIdStr.toString());
        } else {
            designParameters.setSchemeId(schemeId.toString());
        }
        return designParameters;
    }

    /**
     * 移除对应的设计方案ID
     *
     * @param designParameters
     * @param schemeId
     * @return
     */
    public DesignParameters subtractionSchemeId(DesignParameters designParameters, Integer schemeId) {
        String schemeIds = designParameters.getSchemeId();
        String[] schemeIdArr = schemeIds.split(",");
        ArrayList<String> newSchemeIdArr = new ArrayList<>();
        for (String s : schemeIdArr) {
            if (s == null || s.isEmpty() || s.equals(schemeId + "")) {
                continue;
            }
            newSchemeIdArr.add(s);
        }
        designParameters.setSchemeId(StringUtils.join(newSchemeIdArr, ","));
        return designParameters;
    }

    /**
     * 检查当前是否存在已选中的方案
     *
     * @return -1,不存在；n,选中方案的ID
     */
    @Override
    public Integer checkExistChooseScheme(Integer workFaceId) {
        List<TDesSchemeInfo> byWorkFaceId = tDesSchemeInfoMapper.getByWorkfaceId(workFaceId);
        for (TDesSchemeInfo tDesSchemeInfo : byWorkFaceId) {
            if (tDesSchemeInfo.getSign().equals(1)) {
                return tDesSchemeInfo.getId();
            }
        }
        return -1;
    }
}
