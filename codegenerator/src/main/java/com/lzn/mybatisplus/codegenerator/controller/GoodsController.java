
package com.lzn.mybatisplus.codegenerator.controller;

import com.lzn.mybatisplus.codegenerator.entity.Goods;
import com.lzn.mybatisplus.codegenerator.service.GoodsService;
import com.lzn.mybatisplus.codegenerator.export.GoodsVO;
import com.lzn.mybatisplus.codegenerator.export.GoodsExportService;
import com.lzn.mybatisplus.codegenerator.utils.entity.*;
import com.lzn.mybatisplus.codegenerator.utils.export.*;
import org.apache.commons.beanutils.ConvertUtils;
import com.lzn.mybatisplus.codegenerator.utils.ParameterUtil;
import com.lzn.mybatisplus.codegenerator.utils.entity.GridDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ${tablecomment}  前端控制器
 * </p>
 *
 * @author liuzhinan
 * @since 2020-07-22
 */
@Controller
@RequestMapping(value="/admin/goods")
public class GoodsController{
    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Resource
    private GoodsService goodsService;



    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model){
        return "/goods/list";
    }

    @RequestMapping(value = "searchList", method = RequestMethod.POST)
    @ResponseBody
    @ExportMethod(serviceClass = GoodsExportService.class, memo = "明细导出")
    public String searchList(ServletRequest request,@ModelAttribute("page")  OmuiPage page){
        try {
            Map<String,Object> searchParam =	 ParameterUtil.getParametersStartingWith(request, "filter_");
            GridDataModel<GoodsVO> gd =goodsService.findByPage(searchParam, page);
            return JsonMapper.nonDefaultMapper().toJson(gd);
        } catch (Exception e) {
            logger.error("查询出错了",e);
            return JsonMapper.nonDefaultMapper().toJson(new Resp("false", e.getMessage()));
        }
    }


}
