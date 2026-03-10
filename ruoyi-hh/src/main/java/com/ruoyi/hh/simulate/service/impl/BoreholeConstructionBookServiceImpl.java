package com.ruoyi.hh.simulate.service.impl;

import com.ruoyi.hh.simulate.domain.BoreholeConstructionBook;
import com.ruoyi.hh.simulate.mapper.BoreholeConstructionBookMapper;
import com.ruoyi.hh.simulate.service.BoreholeConstructionBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BoreholeConstructionBookServiceImpl implements BoreholeConstructionBookService {

    @Resource
    private BoreholeConstructionBookMapper boreholeConstructionBookMapper;

    @Override
    public List<BoreholeConstructionBook> selectBoreholeConstructionBook(Integer roadwayId,
                                                                         List<Integer> purposeboreholeid,
                                                                         List<String> groupNo,
                                                                         List<Integer> boreholeNo,
                                                                         Date startdate,
                                                                         Date enddate) {
        return this.boreholeConstructionBookMapper.selectBoreholeConstructionBook(roadwayId, purposeboreholeid, groupNo, boreholeNo, startdate, enddate);
    }

}
