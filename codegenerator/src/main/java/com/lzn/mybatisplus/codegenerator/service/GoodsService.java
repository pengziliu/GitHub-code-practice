package com.lzn.mybatisplus.codegenerator.service;



import org.springframework.stereotype.Service;
import com.lzn.mybatisplus.codegenerator.dao.GoodsMybatisDao;
import com.lzn.mybatisplus.codegenerator.utils.entity.GridDataModel;
import com.lzn.mybatisplus.codegenerator.utils.entity.OmuiPage;
import com.lzn.mybatisplus.codegenerator.export.GoodsVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuzhinan
 * @since 2020-07-22
 */
@Service
public class GoodsService {

    @Resource
    private GoodsMybatisDao goodsDao;

    /**
     * 分页查询
     * */
    public GridDataModel<GoodsVO>  findByPage(Map<String, Object> searchParams, OmuiPage page){
        GridDataModel<GoodsVO> gm = new GridDataModel<GoodsVO>();
        searchParams.put("start", page.getStart());
        searchParams.put("limit", page.getLimit());
        long count = goodsDao.countForPage(searchParams);
        List<GoodsVO> list = goodsDao.listForPage(searchParams);
        gm.setTotal(count);
        gm.setRows(list);
        return gm;
    }



}

