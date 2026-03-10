package com.ruoyi.hh.physical.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.physical.domain.TWorkingFaceOfCoalMining;

/**
 * 采煤工作面Service接口
 *
 * @author hhhz
 * @date 2023-04-28
 */
public interface ITWorkingFaceOfCoalMiningService {
    List<TWorkingFaceOfCoalMining> getAllTWorkingFaceOfCoalMining();

    /**
     * 查询采煤工作面
     *
     * @param id 采煤工作面主键
     * @return 采煤工作面
     */
    public TWorkingFaceOfCoalMining selectTWorkingFaceOfCoalMiningById(Integer id);

    /**
     * 查询采煤工作面列表
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 采煤工作面集合
     */
    public List<TWorkingFaceOfCoalMining> selectTWorkingFaceOfCoalMiningList(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining);

    /**
     * 查询采煤工作面列表(ForAI,临时解决授权问题)
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 采煤工作面集合
     */
    public List<TWorkingFaceOfCoalMining> selectTWorkingFaceOfCoalMiningListForAI(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining);

    /**
     * 新增采煤工作面
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 结果
     */
    public TWorkingFaceOfCoalMining insertTWorkingFaceOfCoalMining(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining);

    /**
     * 修改采煤工作面
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 结果
     */
    public TWorkingFaceOfCoalMining updateTWorkingFaceOfCoalMining(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining);

    /**
     * 批量删除采煤工作面
     *
     * @param ids 需要删除的采煤工作面主键集合
     * @return 结果
     */
    public int deleteTWorkingFaceOfCoalMiningByIds(Integer[] ids);

    /**
     * 删除采煤工作面信息
     *
     * @param id 采煤工作面主键
     * @return 结果
     */
    public int deleteTWorkingFaceOfCoalMiningById(Integer id);

    List<Integer> getWorkFaceByRoadwayId(Integer roadwayId);

    HashMap<String, String> exportData(Integer id) throws JsonProcessingException;

    AjaxResult importData(String data) throws JsonProcessingException;

    HashMap<String, HashMap<String, HashMap<String, List<?>>>> getBoreholesDetail(Integer workfaceId);
    HashMap<String, HashMap<Integer, ?>> getBoreholesCountDetail(Integer workfaceId);

    HashMap<String, HashMap<String, HashMap<String, List<?>>>> getBoreholesDetail(ArrayList<Integer> roadways, ArrayList<Integer> types);

    //HashMap<String, HashMap<String, HashMap<String, List<?>>>> getBoreholes(ArrayList<Integer> roadways, ArrayList<Integer> types);
    HashMap<String, List<?>> getBoreholes(ArrayList<Integer> roadways, ArrayList<Integer> types);

}
