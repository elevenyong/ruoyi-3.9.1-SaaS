package com.ruoyi.hh.business.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.business.domain.*;
import com.ruoyi.hh.business.mapper.TBkSensorDataMapper;
import com.ruoyi.hh.business.mapper.TDrainagedataBeforeMapper;
import com.ruoyi.hh.business.mapper.TDrainagedataRecordMapper;
import com.ruoyi.hh.business.mapper.TDrainagedataSumMapper;
import com.ruoyi.hh.business.service.ITBkSensorDataService;
import com.ruoyi.hh.physical.domain.TDrainageUnit;
import com.ruoyi.hh.physical.domain.TStationInfo;
import com.ruoyi.hh.physical.mapper.TDrainageUnitMapper;
import com.ruoyi.hh.physical.mapper.TRoadwayMapper;
import com.ruoyi.hh.physical.mapper.TStationInfoMapper;
import com.ruoyi.hh.physical.mapper.TWorkingFaceMapper;
import com.ruoyi.hh.util.service.ITDrainagePipeTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 八矿传感器数据Service业务层处理
 * 
 * @author eleven
 * @date 2023-06-02
 */
@Service
public class TBkSensorDataServiceImpl implements ITBkSensorDataService
{
    @Resource
    private TBkSensorDataMapper tBkSensorDataMapper;
    @Resource
    private TDrainageUnitMapper tDrainageUnitMapper;
    @Resource
    private TStationInfoMapper tStationInfoMapper;
    @Resource
    private TDrainagedataBeforeMapper tDrainagedataBeforeMapper;
    @Resource
    private TDrainagedataRecordMapper tDrainagedataRecordMapper;
    @Resource
    private TDrainagedataSumMapper tBkDrainagedataSumMapper;
    @Resource
    private ITDrainagePipeTypeService itDrainagePipeTypeService;
    @Resource
    private TRoadwayMapper tRoadwayMapper;
    @Resource
    private TWorkingFaceMapper tWorkingFaceMapper;

    /**
     * 查询八矿传感器数据
     * 
     * @param id 八矿传感器数据主键
     * @return 八矿传感器数据
     */
    @Override
    public TBkSensorData selectTBkSensorDataById(Integer id)
    {
        return tBkSensorDataMapper.selectTBkSensorDataById(id);
    }

    /**
     * 查询八矿传感器数据列表
     * 
     * @param tBkSensorData 八矿传感器数据
     * @return 八矿传感器数据
     */
    @Override
    public List<TBkSensorData> selectTBkSensorDataList(TBkSensorData tBkSensorData)
    {
        return tBkSensorDataMapper.selectTBkSensorDataList(tBkSensorData);
    }

    /**
     * 新增八矿传感器数据
     * 
     * @param tBkSensorData 八矿传感器数据
     * @return 结果
     */
    @Override
    public int insertTBkSensorData(TBkSensorData tBkSensorData)
    {
        return tBkSensorDataMapper.insertTBkSensorData(tBkSensorData);
    }

    /**
     * 修改八矿传感器数据
     * 
     * @param tBkSensorData 八矿传感器数据
     * @return 结果
     */
    @Override
    public int updateTBkSensorData(TBkSensorData tBkSensorData)
    {
        return tBkSensorDataMapper.updateTBkSensorData(tBkSensorData);
    }

    /**
     * 批量删除八矿传感器数据
     * 
     * @param ids 需要删除的八矿传感器数据主键
     * @return 结果
     */
    @Override
    public int deleteTBkSensorDataByIds(Integer[] ids)
    {
        return tBkSensorDataMapper.deleteTBkSensorDataByIds(ids);
    }

    /**
     * 删除八矿传感器数据信息
     * 
     * @param id 八矿传感器数据主键
     * @return 结果
     */
    @Override
    public int deleteTBkSensorDataById(Integer id)
    {
        return tBkSensorDataMapper.deleteTBkSensorDataById(id);
    }

//    /**
//     * 查询某一天某位置处的测点的最后一包数据
//     * @param time 某天
//     * @param measurePointLocal 某一测点位置
//     * @return 列表
//     */
//    public List<TBkSensorData> getLastDataSomeDay(String time,String measurePointLocal){
//        return this.tBkSensorDataMapper.getLastDataSomeDay(time, measurePointLocal);
//    }
//
//    /**
//     * 查询某一天某位置处的测点的第一包数据
//     * @param time 某天
//     * @param measurePointLocal 某一测点位置
//     * @return 列表
//     */
//    public List<TBkSensorData> getFirstDataSomeDay(String time,String measurePointLocal){
//        return this.tBkSensorDataMapper.getFirstDataSomeDay(time, measurePointLocal);
//    }
    public List<TBkSensorData> getFirstDataSomeDay_Time(Date startTime,Date endTime,String measurePointLocal){
        return this.tBkSensorDataMapper.getFirstDataSomeDay_Time(startTime, endTime,measurePointLocal);
    }
    @Override
    public List<TBkSensorData> getLastDataSomeDay_Time(Date startTime, Date endTime,String measurePointLocal) {
        return this.tBkSensorDataMapper.getLastDataSomeDay_Time(startTime, endTime,measurePointLocal);
    }

    public List<TBkSensorData> getDataSomeDay_All(Date startTime,Date endTime,String measurePointLocal){
        return this.tBkSensorDataMapper.getDataSomeDay_All(startTime,endTime,measurePointLocal);
    }
    public List<TBkSensorData> getDataByLocal_All(Date startTime,Date endTime,
                                                  List<String> measurePointLocal){
        return this.tBkSensorDataMapper.getDataByLocal_All(startTime, endTime, measurePointLocal);
    }

    /**
     * 根据测点类型和测点编码查询历史曲线
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param measurePointCode 测点编码
     * @param sensorTypeName 测点类型名称
     * @return 结果
     */
    @Override
    public List<TBkSensorData> getHistoricalCurve(Date startTime, Date endTime, String measurePointCode, String sensorTypeName) {
        return this.tBkSensorDataMapper.getHistoricalCurve(startTime, endTime, measurePointCode, sensorTypeName);
    }

    /**
     * 根据日期查询当天数据中的日累计统计测点位置
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<String> getDayLocal(Date startTime,Date endTime){
        return this.tBkSensorDataMapper.getDayLocal(startTime,endTime);
    }

    /**
     * 根据日期查询当天数据中的月累计统计测点位置
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<String> getMonthLocal(Date startTime,Date endTime){
        return this.tBkSensorDataMapper.getMonthLocal(startTime,endTime);
    }
    /**
     * 根据日期查询当天数据中的瓦斯抽放量测点位置
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<String> getDrainageLocal(Date startTime, Date endTime) {
        return this.tBkSensorDataMapper.getDrainageLocal(startTime, endTime);
    }

    /**
     * 从抽采中间表中计算抽采累计表
     * @param date 指定某一天，格式：YYYY-MM-dd
     */
    @Override
    public void fileStationDataSum(String date) throws ParseException {
        List<TDrainageUnit> drainageUnits=this.tDrainageUnitMapper.selectTDrainageUnitList(new TDrainageUnit());  //先获取到所有的抽采单元列表
        for(TDrainageUnit tdu:drainageUnits){
            String measurePointLocal=this.tStationInfoMapper.getMeasurePointLocalByDUid(tdu.getId()); // 根据抽采单元ID获取抽采测点安装位置
            String yestoday=DateUtils.getYesToday(date);
            TDrainagedataRecord drainageQuantity=this.tDrainagedataRecordMapper.getDayDrainageByLocalAndTime(measurePointLocal,yestoday ); //获取昨天的瓦斯抽放量数据记录（昨天的最后一条）
            TDrainagedataRecord  monthData=this.tDrainagedataRecordMapper.getMonthDataByLocalAndTime(measurePointLocal, yestoday); //获取昨天的瓦斯月累计纯量记录（昨天的最后一条）
            TDrainagedataSum bss=this.tBkDrainagedataSumMapper.getByDrainageUnitId(tdu.getId());  //通过抽采单元ID获取抽采数据累计值表
            if(bss!=null){//抽采累计表中有数据
                if(isMonthStart(date)){ //当天为当月的第一天，则将月累计抽采纯量累加到累计抽采纯量上
                    bss.setDrainageUnit(tdu.getUnitName());
                    bss.setDrainageUnitId(tdu.getId());
                    if(monthData!=null){
                        bss.setDataDate(monthData.getDataTime());
                        bss.setGasScalarMonth(monthData.getValue());
                        if(bss.getGasScalar()==0){ //累计抽采纯量为0（系统安装未跨月）
                            bss.setGasScalar(monthData.getValue());
                            double gasReservesRemnant=tdu.getGasTotalReserves()*10000.00f-monthData.getValue();
                            bss.setGasReservesRemnant((long)gasReservesRemnant);  //累计纯量为空，则只减去月累计纯量
                        }else{
                            if(!bss.getDataDate().equals(monthData.getDataTime())){
                                bss.setGasScalar(monthData.getValue()+bss.getGasScalar());
                                double gasReservesRemnant=tdu.getGasTotalReserves()*10000.00f-bss.getGasScalar()-monthData.getValue();
                                bss.setGasReservesRemnant((long)gasReservesRemnant);  //剩余量等于减去累计纯量，再减去月累计纯量
                            }
                        }
                    }
                    if(drainageQuantity!=null){
                        bss.setDrainageQuantity(drainageQuantity.getValue());
                    }
                }else{
                    //当天不是月初的第一天，则把获取到的月累计纯量赋值到月累计纯量，累计抽采纯量不变
                    bss.setDrainageUnit(tdu.getUnitName());
                    bss.setDrainageUnitId(tdu.getId());
                    if(monthData!=null){
                        bss.setGasScalarMonth(monthData.getValue());
                        bss.setDataDate(monthData.getDataTime());
                        double gasReservesRemnant=tdu.getGasTotalReserves()*10000.00f-bss.getGasScalar()-monthData.getValue();
                        bss.setGasReservesRemnant((long)gasReservesRemnant);
                    }
                    if(drainageQuantity!=null){
                        bss.setDrainageQuantity(drainageQuantity.getValue());
                    }
                }
                this.tBkDrainagedataSumMapper.updateTDrainagedataSum(bss);
            }else{//抽采累计表中没有数据，组装抽采数据，并新增到累计抽采表中
                TDrainagedataSum tBkDrainagedataSum=new TDrainagedataSum();
                tBkDrainagedataSum.setDrainageUnit(tdu.getUnitName());
                tBkDrainagedataSum.setDrainageUnitId(tdu.getId());
                if(monthData!=null){
                    tBkDrainagedataSum.setDataDate(monthData.getDataTime());
                    tBkDrainagedataSum.setGasScalarMonth(monthData.getValue());
                    double gasReservesRemnant=tdu.getGasTotalReserves()*10000L-monthData.getValue();
                    tBkDrainagedataSum.setGasReservesRemnant((long)gasReservesRemnant);
                }else{
                    tBkDrainagedataSum.setGasReservesRemnant(tdu.getGasTotalReserves()*10000L);
                }
                if(drainageQuantity!=null){
                    tBkDrainagedataSum.setDataDate(monthData.getDataTime());
                    tBkDrainagedataSum.setDrainageQuantity(drainageQuantity.getValue());
                }
                this.tBkDrainagedataSumMapper.insertTDrainagedataSum(tBkDrainagedataSum);
            }
        }
    }

    /**
     * 获取此刻所有测点的抽采数据信息
     * @return
     */

    @Override
    public List<MeasurePointData> getMeasurePointData() throws ParseException {
        //获取所有抽采单元信息
        List<TDrainageUnit> drainageUnits=this.tDrainageUnitMapper.selectTDrainageUnitList(new TDrainageUnit());  //先获取到所有的抽采单元列表
        List<MeasurePointData> list=new ArrayList<>();
        for(TDrainageUnit tDrainageUnit:drainageUnits){
            //在测点基本信息中通过抽采单元ID获取测点编码
            MeasurePointData mpd=new MeasurePointData();
            TStationInfo tStationInfo=this.tStationInfoMapper.getMainByDrainageUnitId(tDrainageUnit.getId());  //通过抽采ID获取测点编码
            if(tStationInfo!=null){
                Integer workfaceId =this.tRoadwayMapper.getWorkfaceByRoadwayId(tStationInfo.getRoadwayId());
                if(workfaceId!=null){
                    mpd.setWorkfaceId(workfaceId);
                    mpd.setWorkfaceName(this.tWorkingFaceMapper.selectTWorkingFaceById(workfaceId).getWorkfaceName());
                }
                TDrainagedataBefore tDrainagedataBefore=this.tDrainagedataBeforeMapper.getByMeasurePointCode(tStationInfo.getMeasurePointCode()); //通过测点编码获取系统安装前的累计纯量
                TBkSensorData tBkSensorData=this.tBkSensorDataMapper.getGasScalarByMeasurePointCode(DateUtils.strToDateDay(DateUtils.getDate()),new Date(),tStationInfo.getMeasurePointCode()); //通过测点编码获取实时的抽采量
                TBkSensorData drainageQuantity=this.tBkSensorDataMapper.getdrainageQuantityByMeasurePointCode(DateUtils.strToDateDay(DateUtils.getDate()),new Date(),tStationInfo.getMeasurePointCode()); //通过测点编码获取实时的抽采量
                TDrainagedataSum tBkDrainagedataSum=this.tBkDrainagedataSumMapper.getByDrainageUnitId(tDrainageUnit.getId());  //获取抽采累计量表中的数据
                mpd.setFarthestEnd(tStationInfo.getFarthestEnd());
                mpd.setRoadwayId(tStationInfo.getRoadwayId());
                mpd.setDrainagePipeTypeId(tStationInfo.getPipeTypeId());
                if(itDrainagePipeTypeService.selectTDrainagePipeTypeById(tStationInfo.getPipeTypeId())!=null){
                    mpd.setDrainagePipeType(itDrainagePipeTypeService.selectTDrainagePipeTypeById(tStationInfo.getPipeTypeId()).getPipeType());
                }
                mpd.setSerial(tStationInfo.getSerial());
                mpd.setMeasurePointLocal(tStationInfo.getMeasurePointLocal());
                mpd.setDataTime(new Date());
                if(tBkSensorData!=null){
                    mpd.setMeasurePointCode(tBkSensorData.getMeasurePointCode());
                }
                Optional<Float> op1=Optional.ofNullable(tDrainagedataBefore).map(TDrainagedataBefore::getAccumulateGasScalar);
                Optional<Float> op2=Optional.ofNullable(tBkSensorData).map(TBkSensorData::getMeasurePointValue);
                Optional<Float> op3=Optional.ofNullable(tBkDrainagedataSum).map(TDrainagedataSum::getGasScalar);
                mpd.setGasScalar(op1.orElse(0.0f)+(op2.orElse(0.0f))+op3.orElse(0.0f));
                if(drainageQuantity!=null){
                    mpd.setDrainageQuantity(drainageQuantity.getMeasurePointValue());
                    mpd.setMeasurePointCode(drainageQuantity.getMeasurePointCode());
                }else if(drainageQuantity==null&&tBkDrainagedataSum!=null){
                    mpd.setDrainageQuantity(tBkDrainagedataSum.getDrainageQuantity());
                }
            }
            TStationInfo si=this.tStationInfoMapper.getByDrainageUnitId(tDrainageUnit.getId()).get(0);
            if(mpd.getMeasurePointLocal()==null){
                mpd.setMeasurePointLocal(si.getMeasurePointLocal());
            }
            if(mpd.getDataTime()==null){
                mpd.setDataTime(new Date());
            }
            if(mpd.getGasScalar()==null){
                mpd.setGasScalar(0.0f);
            }
            if(mpd.getDrainageQuantity()==null){
                mpd.setDrainageQuantity(0.0f);
            }
            if(mpd.getDrainagePipeTypeId()==null){
                mpd.setDrainagePipeTypeId(si.getPipeTypeId());
                mpd.setDrainagePipeType(itDrainagePipeTypeService.selectTDrainagePipeTypeById(si.getPipeTypeId()).getPipeType());
            }
            if(mpd.getFarthestEnd()==null){
                mpd.setFarthestEnd(si.getFarthestEnd());
            }
            mpd.setGasContentRemnant(tDrainageUnit.getGasContentOriginal());
            mpd.setGasReservesRemnant(tDrainageUnit.getGasTotalReserves()*10000);
            mpd.setDrainageUnitId(tDrainageUnit.getId());
            mpd.setDrainageUnit(tDrainageUnit.getUnitName());
            mpd.setRoadwayId(tDrainageUnit.getRoadwayId());
            mpd.setWorkfaceId(tDrainageUnit.getWorkfaceId());
            mpd.setWorkfaceName(tDrainageUnit.getWorkfaceName());
            list.add(mpd);
        }
        //通过抽采单元信息获取对应的测点安装位置（measurepointlocal=#{measurepointlocal,and piping=1的}）
        //抽采单元的抽采纯量=系统安装前的抽采量+抽采累计表中的纯量+当前数据库中的抽采累计纯量
        return list;
    }

    /**
     * 判断时间是不是某个月的第一天
     * @param time 时间
     * @return 结果
     */
    public boolean isMonthStart(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将字符串转换为 LocalDate 对象
        LocalDate date = LocalDate.parse(time, formatter);
        return date.getDayOfMonth()==1;
    }
}
