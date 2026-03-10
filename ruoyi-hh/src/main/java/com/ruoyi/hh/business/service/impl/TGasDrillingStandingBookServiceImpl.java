package com.ruoyi.hh.business.service.impl;

import com.ruoyi.hh.business.domain.TGasDrillingStandingBook;
import com.ruoyi.hh.business.mapper.TGasDrillingStandingBookMapper;
import com.ruoyi.hh.business.service.ITGasDrillingStandingBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TGasDrillingStandingBookServiceImpl implements ITGasDrillingStandingBookService {

    @Resource
    private TGasDrillingStandingBookMapper tGasDrillingStandingBookMapper;
    @Override
    public List<TGasDrillingStandingBook> getTGasDrillingStandingBookList(Integer roadwayId,
                                                                          List<String> groupNo,
                                                                          List<Integer> purposeboreholeId,
                                                                          Date startdate,
                                                                          Date enddate) {
        return this.tGasDrillingStandingBookMapper.getTGasDrillingStandingBookList(roadwayId, groupNo, purposeboreholeId, startdate, enddate);
    }
}
