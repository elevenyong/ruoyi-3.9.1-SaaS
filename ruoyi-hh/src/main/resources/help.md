**抽采动态相关：**
线程：PerceptionDataThread，读取指定位置的数据，根据煤矿感知接入规范解析出：
	CDSS（测点实时）、CDDY（测点基础信息）、TJSJ（测点统计）、YCBJ（异常报警）、FZDY（分站基础信息）、FZSS（分站实时）等信息。其中
	测点实时数据通过websocket发送出去，并保存到本地数据库中；测点基础信息保存到本地数据库中，并在接到下一包数据时进行对比，若有不同，做
	更新操作；异常报警信息保存到本地数据库。分站基础信息参考测点基础信息。分站实时信息参考测点实时信息。
数据库：测点基础信息表（t_station_info）中在原有列中添加新列roadway_id,drainage_unit_id,pipe_type_id,serial,piping,farthest_end等字段，
	其中roadway_id为所属巷道的ID，drainage_unit_id为所属抽采单元的ID，pipe_type_id为所属抽采管路类型ID，serial为同一巷道，同一抽采管路中该
	测点位置在整个串联结构中的位置（1、2、3、4），数值越靠前，位置越靠前；piping为是否为抽采单元中的总测点。farthest_end为是否为同一巷道，同一
	抽采管路中最后一个测点位置。
	1、抽采数据中间表（t_drainagedata_record）：线程ScriptRunnerExecSql中设置定时器，每天的凌晨固定时间点从抽采历史数据中读取头一天的数据，找到
	最后一包数据，并保存到t_drainagedata_record中。
	2、抽采数据累计表（t_drainagedata_sum）：表中的列gas_scalar_month为上截止到个月的月累计纯流量，gas_scalar为当月的月累计纯流量。在1中的线程
	数据写入中间表后，判断当天是否为当月的第一天：若为第一天，则将头一天的月累抽采纯量累加到gas_scalar_month中，并将月累计纯量赋值给gas_scalar；
	若不为当月的第一天，则只将月累计纯量赋值给gas_scalar；并更新数据日期。
	3、测点的累计抽采量：系统安装前的累计抽采纯量（t_drainagedata_before）+抽采数据累计表（t_drainagedata_sum）中月累计抽采纯量gas_scalar_month
	+抽采历史数据（t_bk_sensor_data）中的月累计纯量即为累计抽采纯量。瓦斯抽放量可直接从抽采历史数据中读出。
	4、抽采单元的累计抽采数据：同一巷道中，同一类型抽采管路中，根据serial序号，S1,S2,S3,S4。对应抽采单元D1,D2,D3,D4，D1抽采量：S1-S2，D2抽采量：S2-S3,
	D3抽采量：S3-S4,D4抽采单元farthest_end为真，则D4抽采单元的抽采量为S4。
注：有可能存在问题：
	1、系统正常运行中，线程1未完成将历抽采数据存入中间表，也为累计数据到抽采累计表中。则可通过接口/tBKSensorData/handMovementInsertData?startDate=2023-06-25&endDate=2023-07-02
	将时间段内的数据保存到中间表，并将抽采累计量写入表中。

**地应力（时间：2024年12月4日）**
新建数据库:地应力实时数据：t_stressmeter_data，地应力测点基本信心数据库：t_stressmeter_info;
根据感知数据接入规范得到：测点编码应为多个数据组成：如：41040300468802MN0023011A08，其包含了煤矿编码（12位），系统编码（2位），传感器数值类型编码（2位），传感器类型编号（4位）及测点原系统内部编码
（原系统提供，不能超过8位）；在读取测点基本信息时，通过测点编码+传感器类型+数据类型（如“CDDY”、“ZKYL”）来组成键放入内存中，来对比新数据是否一致。
t_sensor_type需要有内容。
新建TBZKSS和TBZKYL地应力相关类；新建类CrustalStressDataThread，读取并解析地应力数据线程；

	
	
	
	
