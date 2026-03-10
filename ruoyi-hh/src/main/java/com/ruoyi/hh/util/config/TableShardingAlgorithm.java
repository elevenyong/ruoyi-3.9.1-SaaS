package com.ruoyi.hh.util.config;

import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Range;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.core.env.Environment;

//@Component
public class TableShardingAlgorithm
implements StandardShardingAlgorithm<Date>
{
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        String tbName = shardingValue.getLogicTableName();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String value = format.format(shardingValue.getValue());
            Date date = format.parse(value);

            String year = String.format("%tY", date);
            String mon = String.format("%tm", date);
            String dat = String.format("%td", date);
            tbName = new StringBuilder().append(tbName).append(year).append(mon).append(dat).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (String each : availableTargetNames) {
            if (each.equals(tbName)) {
                return each;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        List<String> list = new ArrayList<>();
        Range<Date> valueRange = shardingValue.getValueRange();

        Date lowerDate = valueRange.lowerEndpoint();
        String year = String.format("%tY", lowerDate);
        String mon = String.format("%tm", lowerDate);
        String dat = String.format("%td", lowerDate);
        String lowerTableName = year + mon + dat;
        Date upperDate = valueRange.upperEndpoint();
        String upperYear = String.format("%tY", upperDate);
        String upperMonth = String.format("%tm", upperDate);
        String upperDay = String.format("%td", upperDate);
        String upperTableName = upperYear + upperMonth + upperDay;
        List<String> list2=getAllTableNameBySchema();

        for (String tableName : availableTargetNames) {
            String indexStr = tableName.substring(tableName.length() - 8, tableName.length());
            int index = Integer.parseInt(indexStr);
            if(index >= Integer.parseInt(lowerTableName)&&index <= Integer.parseInt(upperTableName)){
              if(list2.contains((tableName))) {
                    list.add(tableName);
              }
            }
        }
        return list;
    }

    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return null;
    }

	@Override
	public Properties getProps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProps(Properties props) {
		// TODO Auto-generated method stub
	}

    public static List<String> getAllTableNameBySchema() {
        List<String> res = new ArrayList<>();
        // 获取数据中的表名，需要自己构建数据源 SHOW TABLES like 'hss_history%'
        // List<String> tableNames = bookMapper.showTables();
        Environment env = SpringUtil.getApplicationContext().getEnvironment();
        try (Connection connection = DriverManager.getConnection(env.getProperty("spring.datasource.druid.master.url"), env.getProperty("spring.datasource.druid.master.username"), env.getProperty("spring.datasource.druid.master.password"));
             Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery("SHOW TABLES like 't_bk_sensor_data%'")) {
                while (rs.next()) {
                    res.add(rs.getString(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
