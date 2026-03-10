package com.ruoyi.hh.simulate.service;

import com.ruoyi.hh.simulate.domain.BoreholeConstructionBook;

import java.util.Date;
import java.util.List;

public interface BoreholeConstructionBookService {
    public List<BoreholeConstructionBook> selectBoreholeConstructionBook(Integer roadwayId,
                                                                         List<Integer> purposeboreholeid,
                                                                         List<String> groupNo,
                                                                         List<Integer> boreholeNo,
                                                                         Date startdate,
                                                                         Date enddate);

}
