package com.lzn.shardingsphere.config;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ShardingAlgorithmYear implements PreciseShardingAlgorithm,RangeShardingAlgorithm {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
        String tableNode=null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar=Calendar.getInstance();
            String dateStr=preciseShardingValue.getValue().toString();
            Date date=format.parse(dateStr);
            calendar.setTime(date);
            String year=calendar.get(Calendar.YEAR)+"";
            for(Object obj:collection){
                String oneNode=obj+"";
                if(oneNode.endsWith(year)){
                    tableNode=oneNode;
                    break;
                }
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return tableNode;
    }
    @Override
    public Collection<String> doSharding(Collection collection, RangeShardingValue rangeShardingValue) {
        Collection<String> collect = new ArrayList<String>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar=Calendar.getInstance();
            String dateUpperStr=rangeShardingValue.getValueRange().upperEndpoint().toString();
            String dateLowerStr=rangeShardingValue.getValueRange().lowerEndpoint().toString();
            Date dateUpper=format.parse(dateUpperStr);
            Date dateLower=format.parse(dateLowerStr);
            calendar.setTime(dateUpper);
            int yearUpper=calendar.get(Calendar.YEAR);
            calendar.setTime(dateLower);
            int yearLower=calendar.get(Calendar.YEAR);
            for(Object obj:collection){
                String tableNoe=obj+"";
                for(int i=yearLower;i<=yearUpper;i++){
                    if(tableNoe.endsWith(i+"")){
                        collect.add(tableNoe);
                        break;
                    }
                }
            }
        } catch (ParseException e)  {
            logger.error(e.getMessage(),e);
        }
        return collect;
    }
}
