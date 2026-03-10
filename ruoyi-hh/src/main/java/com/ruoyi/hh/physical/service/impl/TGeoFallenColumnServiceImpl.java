package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TCoalPoint;
import com.ruoyi.hh.physical.domain.TGeoFallenColumn;
import com.ruoyi.hh.physical.mapper.TCoalPointMapper;
import com.ruoyi.hh.physical.mapper.TGeoFallenColumnMapper;
import com.ruoyi.hh.physical.service.ITGeoFallenColumnService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 陷落柱Service业务层处理
 *
 * @author hhhz
 * @date 2022-10-28
 */
@Service
public class TGeoFallenColumnServiceImpl implements ITGeoFallenColumnService {
    @Resource
    private TGeoFallenColumnMapper tGeoFallenColumnMapper;
    @Resource
    private TCoalPointMapper tCoalPointMapper;

    /**
     * 查询陷落柱
     *
     * @param id 陷落柱主键
     * @return 陷落柱
     */
    @Override
    public TGeoFallenColumn selectTGeoFallenColumnById(Integer id) {
        TGeoFallenColumn tGeoFallenColumn = tGeoFallenColumnMapper.selectTGeoFallenColumnById(id);
        if (tGeoFallenColumn != null) {
            tGeoFallenColumn.setGeoFallenColumnPoints(tCoalPointMapper.getPointsByFallenColumnId(tGeoFallenColumn.getId()));
        }
        return tGeoFallenColumn;
    }

    /**
     * 查询陷落柱列表
     *
     * @param tGeoFallenColumn 陷落柱
     * @return 陷落柱
     */
    @Override
    public List<TGeoFallenColumn> selectTGeoFallenColumnList(TGeoFallenColumn tGeoFallenColumn) {
        List<TGeoFallenColumn> tGeoFallenColumns = tGeoFallenColumnMapper.selectTGeoFallenColumnList(tGeoFallenColumn);
        if (!tGeoFallenColumns.isEmpty()) {
            for (TGeoFallenColumn GeoFallenColumn : tGeoFallenColumns) {
                GeoFallenColumn.setGeoFallenColumnPoints(tCoalPointMapper.getPointsByFallenColumnId(GeoFallenColumn.getId()));
            }
        }
        return tGeoFallenColumns;
    }

    /**
     * 新增陷落柱
     *
     * @param tGeoFallenColumn 陷落柱
     * @return 结果
     */
    @Override
    public int insertTGeoFallenColumn(TGeoFallenColumn tGeoFallenColumn) {
        int i = tGeoFallenColumnMapper.insertTGeoFallenColumn(tGeoFallenColumn);
        if (i > 0) {
            List<TCoalPoint> geoFallenColumnPoints = tGeoFallenColumn.getGeoFallenColumnPoints();
            for (TCoalPoint geoFallenColumnPoint : geoFallenColumnPoints) {
                geoFallenColumnPoint.setDataType(3);
                geoFallenColumnPoint.setIsuse(1);
                geoFallenColumnPoint.setWorkfaceId(tGeoFallenColumn.getWorkfaceId());
                geoFallenColumnPoint.setGeoFallenId(tGeoFallenColumn.getId());
                tCoalPointMapper.insertTCoalPoint(geoFallenColumnPoint);
            }
        }
        return i;
    }

    /**
     * 修改陷落柱
     *
     * @param tGeoFallenColumn 陷落柱
     * @return 结果
     */
    @Override
    public int updateTGeoFallenColumn(TGeoFallenColumn tGeoFallenColumn) {
        int i = tGeoFallenColumnMapper.updateTGeoFallenColumn(tGeoFallenColumn);
        List<TCoalPoint> fallenColumnPoints = tGeoFallenColumn.getGeoFallenColumnPoints();
        List<TCoalPoint> pointsByFallenColumnId = tCoalPointMapper.getPointsByFallenColumnId(tGeoFallenColumn.getId());
        for (TCoalPoint tCoalPoint : pointsByFallenColumnId) {
            tCoalPointMapper.deleteTCoalPointById(tCoalPoint.getId());
        }
        for (TCoalPoint fallenColumnPoint : fallenColumnPoints) {
            fallenColumnPoint.setId(null);
            fallenColumnPoint.setDataType(3);
            fallenColumnPoint.setIsuse(1);
            fallenColumnPoint.setWorkfaceId(tGeoFallenColumn.getWorkfaceId());
            fallenColumnPoint.setGeoFallenId(tGeoFallenColumn.getId());
            tCoalPointMapper.insertTCoalPoint(fallenColumnPoint);
        }
        return i;
    }

    /**
     * 批量删除陷落柱
     *
     * @param ids 需要删除的陷落柱主键
     * @return 结果
     */
    @Override
    public int deleteTGeoFallenColumnByIds(Integer[] ids) {
        int delCount = 0;
        for (Integer id : ids) {
            TGeoFallenColumn fallenColumn = tGeoFallenColumnMapper.selectTGeoFallenColumnById(id);
            if (fallenColumn != null) {
                int i = 0, i1 = 0;
                i = tCoalPointMapper.deleteTCoalPointByFallenColumnId(id);
                if (i > 0) {
                    i1 = tGeoFallenColumnMapper.deleteTGeoFallenColumnById(id);
                }
                if (i > 0 && i1 > 0) {
                    System.out.println(fallenColumn.getName() + "陷落柱，删除成功");
                    delCount++;
                }
            }
        }
        return delCount;
    }

    /**
     * 删除陷落柱信息
     *
     * @param id 陷落柱主键
     * @return 结果
     */
    @Override
    public int deleteTGeoFallenColumnById(Integer id) {
        TGeoFallenColumn fallenColumn = tGeoFallenColumnMapper.selectTGeoFallenColumnById(id);
        int i = 0, i1 = 0;
        if (fallenColumn != null) {
            i = tCoalPointMapper.deleteTCoalPointByFallenColumnId(id);
            if (i > 0) {
                i1 = tGeoFallenColumnMapper.deleteTGeoFallenColumnById(id);
            }
            if (i > 0 && i1 > 0) {
                System.out.println(fallenColumn.getName() + "陷落柱，删除成功");
            }
        }
        return i;
    }
}
