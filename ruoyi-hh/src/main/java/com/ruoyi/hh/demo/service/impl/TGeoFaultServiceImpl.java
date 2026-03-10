package com.ruoyi.hh.demo.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.TGeoFault;
import com.ruoyi.hh.demo.mapper.TGeoFaultMapper;
import com.ruoyi.hh.demo.service.ITGeoFaultService;
import com.ruoyi.hh.physical.domain.TCoalPoint;
import com.ruoyi.hh.physical.domain.TRockinfo;
import com.ruoyi.hh.physical.mapper.TCoalPointMapper;
import com.ruoyi.hh.physical.service.ITRockinfoService;
import org.apache.arrow.flatbuf.Int;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 断层Service业务层处理
 *
 * @author hhhz
 * @date 2022-10-28
 */
@Service
public class TGeoFaultServiceImpl implements ITGeoFaultService {
    @Resource
    private TGeoFaultMapper geoFaultMapper;
    @Resource
    private TCoalPointMapper coalPointMapper;

    @Resource
    private ITRockinfoService rockInfoService;

    /**
     * 查询断层
     *
     * @param id 断层主键
     * @return 断层
     */
    @Override
    public TGeoFault selectTGeoFaultById(Integer id) {
        TGeoFault tGeoFault = geoFaultMapper.selectTGeoFaultById(id);
        if (tGeoFault != null) {
            tGeoFault.setGeoFaultPoints(coalPointMapper.getPointsByFaultId(tGeoFault.getId()));
        }
        return tGeoFault;
    }

    /**
     * 查询断层列表
     *
     * @param TGeoFault 断层
     * @return 断层
     */
    @Override
    public List<TGeoFault> selectTGeoFaultList(TGeoFault TGeoFault) {
        List<TGeoFault> TGeoFaults = geoFaultMapper.selectTGeoFaultList(TGeoFault);
        if (!TGeoFaults.isEmpty()) {
            for (TGeoFault geoFault : TGeoFaults) {
                geoFault.setGeoFaultPoints(coalPointMapper.getPointsByFaultId(geoFault.getId()));
            }
        }
        return TGeoFaults;
    }

    /**
     * 新增断层
     *
     * @param geoFault 断层
     * @return 结果
     */
    @Override
    public AjaxResult insertTGeoFault(TGeoFault geoFault) {
        int i = geoFaultMapper.insertTGeoFault(geoFault);
        if (i > 0) {
            Integer faultId = geoFault.getId();
            List<TCoalPoint> geoFaultPoints = geoFault.getGeoFaultPoints();
            int i2 = 0;
            if (geoFaultPoints != null && !geoFaultPoints.isEmpty()) {
                for (TCoalPoint faultPoint : geoFaultPoints) {
                    faultPoint.setWorkfaceId(geoFault.getWorkfaceId());
                    faultPoint.setCoalinfoId(null);
                    faultPoint.setGeoFaultId(faultId);
                    faultPoint.setDataType(2);
                    i2 += coalPointMapper.insertTCoalPoint(faultPoint);
                }
            }
            return AjaxResult.success("断层创建成功");
        } else {
            return AjaxResult.error("断层创建失败");
        }

    }

    /**
     * 修改断层
     *
     * @param tGeoFault 断层
     * @return 结果
     */
    @Override
    public AjaxResult updateTGeoFault(TGeoFault tGeoFault) {
        int i = geoFaultMapper.updateTGeoFault(tGeoFault);
        if (i > 0) {
            int i1 = coalPointMapper.deleteTCoalPointByFaultId(tGeoFault.getId());
            List<TCoalPoint> geoFaultPoints = tGeoFault.getGeoFaultPoints();
            if (geoFaultPoints != null && !geoFaultPoints.isEmpty()) {
                for (TCoalPoint geoFaultPoint : geoFaultPoints) {
                    geoFaultPoint.setId(null);
                    // 绑定断层点的煤层ID以及工作面ID
                    geoFaultPoint.setWorkfaceId(tGeoFault.getWorkfaceId());

                    // 不再绑定地层ID
                    /*TRockinfo queryRockInfo = new TRockinfo();
                    queryRockInfo.setWorkfaceId(tGeoFault.getWorkfaceId());
                    queryRockInfo.setSerialNumber(0);
                    List<TRockinfo> rockInfos = rockInfoService.selectTRockinfoList(queryRockInfo);
                    if (rockInfos != null && !rockInfos.isEmpty()) {
                        geoFaultPoint.setCoalinfoId(rockInfos.get(0).getId());
                    }*/
                    geoFaultPoint.setGeoFaultId(tGeoFault.getId());
                    geoFaultPoint.setDataType(2);
                    int i2 = coalPointMapper.insertTCoalPoint(geoFaultPoint);
                }
            }
            return AjaxResult.success("更新断层成功");
        }
        return AjaxResult.error("更新失败，请稍后重试");
    }

    /**
     * 批量删除断层
     *
     * @param ids 需要删除的断层主键
     * @return 结果
     */
    @Override
    public int deleteTGeoFaultByIds(Integer[] ids) {
        int i = 0;
        for (Integer id : ids) {
            coalPointMapper.deleteTCoalPointByFaultId(id);
            i += geoFaultMapper.deleteTGeoFaultById(id);
        }
        return i;
    }

    /**
     * 删除断层信息
     *
     * @param id 断层主键
     * @return 结果
     */
    @Override
    public int deleteTGeoFaultById(Integer id) {
        return geoFaultMapper.deleteTGeoFaultById(id);
    }
}
