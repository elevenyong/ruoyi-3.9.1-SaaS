package com.ruoyi.hh.demo.domain;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskProgressData {
	
	private int taskId;
	private String taskName;
	private Date startDate;
	private Date planEndDate;
	private String remarks;
	private double total;
	private int keepDays;
	private List<SumByQuarterEnt> sumByQuarterEnts;

}
