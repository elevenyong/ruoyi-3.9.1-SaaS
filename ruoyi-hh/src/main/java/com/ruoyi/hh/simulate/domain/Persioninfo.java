package com.ruoyi.hh.simulate.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persioninfo extends BaseEntity {
    private Integer id;
    private String personneltype;
    private Integer sum;
}
