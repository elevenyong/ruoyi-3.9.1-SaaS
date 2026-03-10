package com.ruoyi.hh.simulate.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.physical.domain.TDrillinginfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterCoalSep extends BaseEntity {
    private double belttotal;
    private List<TDrillinginfo> drillinginfos;
}
