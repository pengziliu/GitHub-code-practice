package com.lzn.mybatisplus.codegenerator.dao;

import com.lzn.mybatisplus.codegenerator.entity.Goods;
import com.lzn.mybatisplus.codegenerator.export.GoodsVO;
import java.util.List;
import java.util.Map;

public interface GoodsMybatisDao  {
    /**
     *  根据主键删除数据库的记录, test_goods
     */
    int deleteByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, test_goods
     */
    int insert(Goods record);

    /**
     *  根据指定主键获取一条数据库记录, test_goods
     */
    Goods selectByPrimaryKey(Long id);

    /**
     *  根据主键来更新符合条件的数据库记录, test_goods
     */
    int updateByPrimaryKey(Goods record);

    /**
     *  根据条件分页查询
     * */
    List<GoodsVO> listForPage(Map<String,Object> searchMap);

    /**
     *  根据条件分页查询(计数)
     * */
    long countForPage(Map<String,Object> searchMap);
    
}
