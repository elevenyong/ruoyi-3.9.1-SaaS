package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TWorkingFaceOfUncoverCoal;
import com.ruoyi.hh.physical.mapper.TRoadwayMapper;
import com.ruoyi.hh.physical.mapper.TWorkingFaceOfUncoverCoalMapper;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfUncoverCoalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 揭煤工作面Service业务层处理
 *
 * @author hhhz
 * @date 2023-05-04
 */
@Service
public class TWorkingFaceOfUncoverCoalServiceImpl implements ITWorkingFaceOfUncoverCoalService {
    @Resource
    private TWorkingFaceOfUncoverCoalMapper tWorkingFaceOfUncoverCoalMapper;

    @Resource
    private TRoadwayMapper roadwayMapper;

    /**
     * 查询揭煤工作面
     *
     * @param id 揭煤工作面主键
     * @return 揭煤工作面
     */
    @Override
    public TWorkingFaceOfUncoverCoal selectTWorkingFaceOfUncoverCoalById(Integer id) {
        TWorkingFaceOfUncoverCoal face = tWorkingFaceOfUncoverCoalMapper.selectTWorkingFaceOfUncoverCoalById(id);
        if (face != null) {
            face.setAirIntakeRoadwayData(roadwayMapper.selectTRoadwayById(Integer.parseInt(face.getAirIntakeRoadway())));
            face.setAirReturnRoadwayData(roadwayMapper.selectTRoadwayById(Integer.parseInt(face.getAirReturnRoadway())));
            face.setCutEyeRoadwayData(roadwayMapper.selectTRoadwayById(Integer.parseInt(face.getCutEyeRoadway())));
            face.setCutEyeRoadwayData(roadwayMapper.selectTRoadwayById(Integer.parseInt(face.getCutEyeRoadway())));
            String bottoms = face.getBottomExtractionRoadway();
            String s = bottoms.replaceAll("\\[", "").replaceAll("]", "");
            String[] ids = s.split(",");
            face.setDcAirIntakeRoadwayData(roadwayMapper.selectTRoadwayById(Integer.parseInt(ids[0])));
            face.setDcAirReturnRoadwayData(roadwayMapper.selectTRoadwayById(Integer.parseInt(ids[1])));
            face.setDcCutEyeRoadwayData(roadwayMapper.selectTRoadwayById(Integer.parseInt(ids[2])));
        }
        return face;
    }

    /**
     * 查询揭煤工作面列表
     *
     * @param tWorkingFaceOfUncoverCoal 揭煤工作面
     * @return 揭煤工作面
     */
    @Override
    public List<TWorkingFaceOfUncoverCoal> selectTWorkingFaceOfUncoverCoalList(TWorkingFaceOfUncoverCoal tWorkingFaceOfUncoverCoal) {
        return tWorkingFaceOfUncoverCoalMapper.selectTWorkingFaceOfUncoverCoalList(tWorkingFaceOfUncoverCoal);
    }

    /**
     * 新增揭煤工作面
     *
     * @param tWorkingFaceOfUncoverCoal 揭煤工作面
     * @return 结果
     */
    @Override
    public int insertTWorkingFaceOfUncoverCoal(TWorkingFaceOfUncoverCoal tWorkingFaceOfUncoverCoal) {
        return tWorkingFaceOfUncoverCoalMapper.insertTWorkingFaceOfUncoverCoal(tWorkingFaceOfUncoverCoal);
    }

    /**
     * 修改揭煤工作面
     *
     * @param tWorkingFaceOfUncoverCoal 揭煤工作面
     * @return 结果
     */
    @Override
    public int updateTWorkingFaceOfUncoverCoal(TWorkingFaceOfUncoverCoal tWorkingFaceOfUncoverCoal) {
        return tWorkingFaceOfUncoverCoalMapper.updateTWorkingFaceOfUncoverCoal(tWorkingFaceOfUncoverCoal);
    }

    /**
     * 批量删除揭煤工作面
     *
     * @param ids 需要删除的揭煤工作面主键
     * @return 结果
     */
    @Override
    public int deleteTWorkingFaceOfUncoverCoalByIds(Integer[] ids) {
        return tWorkingFaceOfUncoverCoalMapper.deleteTWorkingFaceOfUncoverCoalByIds(ids);
    }

    /**
     * 删除揭煤工作面信息
     *
     * @param id 揭煤工作面主键
     * @return 结果
     */
    @Override
    public int deleteTWorkingFaceOfUncoverCoalById(Integer id) {
        return tWorkingFaceOfUncoverCoalMapper.deleteTWorkingFaceOfUncoverCoalById(id);
    }
}
