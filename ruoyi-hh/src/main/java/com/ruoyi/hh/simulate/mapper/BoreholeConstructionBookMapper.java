package com.ruoyi.hh.simulate.mapper;

import com.ruoyi.hh.simulate.domain.BoreholeConstructionBook;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BoreholeConstructionBookMapper {

    public List<BoreholeConstructionBook> selectBoreholeConstructionBook(@Param("roadwayId")Integer roadwayId,
                                                                         @Param("purposeboreholeId")List<Integer> purposeboreholeId,
                                                                         @Param("groupNo")List<String> groupNo,
                                                                         @Param("boreholeNo")List<Integer> boreholeNo,
                                                                         @Param("startDate") Date startDate,
                                                                         @Param("endDate")Date endDate
                                                                         );
}
