package com.ruoyi.hh.business.mapper;

import com.ruoyi.hh.business.domain.TGasDrillingStandingBook;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TGasDrillingStandingBookMapper {
    public List<TGasDrillingStandingBook> getTGasDrillingStandingBookList(@Param("roadwayId") Integer roadwayId,
                                                                          @Param("groupNo") List<String> groupNo,
                                                                          @Param("purposeboreholeId") List<Integer> purposeboreholeId,
                                                                          @Param("startdate") Date startdate,
                                                                          @Param("enddate") Date enddate
                                                                          );
}
