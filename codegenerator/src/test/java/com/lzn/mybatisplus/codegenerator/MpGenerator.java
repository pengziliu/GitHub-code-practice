package com.lzn.mybatisplus.codegenerator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 辅助生产后台开发相关代码  开发时只在自己本地代码修改，不要提交
 * 生成jpaddao service controller entity java代码 和前端 flt文件。
 * 目前已实现list场景
 * input待实现
 */
public class MpGenerator {

    //注意：开发时只在自己本地代码修改，不要提交、不要提交 不要提交
    //第一步修改 javaSrcDir 修改成自己项目存放java源代码的根路径
    static String javaSrcDir = "D:/Git_space/lunzijihua/codegenerator/src/main/java";
    static String resourceDir = "D:/Git_space/lunzijihua/codegenerator/src/main/resources";
    //第二步修改 pageRootDir 修改成你要开发的模块的名称 存放ftl文件的文件夹的根路径
    static String pageRootDir ="D:/Git_space/lunzijihua/codegenerator/src/main/resources/templates/pages/";


    //第三步修改 packageName 修改成你要开发的模块的名称 包名 要小写 生产的entity service dao action文件夹和java代码会在下面
    static String packageName = "goods";//模块文件夹包名称
    //第四步修改 pageDirName 修改成你要开发的模块的名称 存放ftl文件的文件夹 要小写
    static String pageDirName = "goods";//模块页面文件夹名
    //第五步骤 表的前缀 填写了 生成文件时会去除掉
    static String tablePrefix="test_";
    //第六步 数据库里面对应的表的全名
    static String tableName="test_goods";

    /**
     * <p>
     * 代码自动生成
     * </p>
     */
    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(javaSrcDir);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        // .setKotlin(true) 是否生成 kotlin 代码
        gc.setAuthor("liuzhinan");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMybatisDao");
        // gc.setXmlName("%sDao");
        gc.setServiceName("%sService");
//         gc.setServiceImplName("%sService");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);


        dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("test");
        dsc.setPassword("123456");
        dsc.setUrl("jdbc:mysql://116.62.130.226:3306/myProject?useSSL=false");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[] { tablePrefix });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] { tableName }); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        strategy.setSuperEntityClass("com.lzn.mybatisplus.codegenerator.entity.IdEntity");
        // 自定义实体，公共字段
        //  strategy.setSuperEntityColumns(new String[] { "id", "create_date","modify_date" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.lzn.mybatisplus.codegenerator");
        pc.setModuleName(null);
        pc.setMapper("dao");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                map.put("pageDirName",pageDirName);
                map.put("packageName",packageName);
                this.setMap(map);
            }
        };

        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();

//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        //生成导出视图对象
        focList.add(new FileOutConfig("/templates/vm/vo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return javaSrcDir+"/com/lzn/mybatisplus/codegenerator/export/"+tableInfo.getEntityName()+"VO.java";
            }
        });
        //生成excel导出的服务类,
        focList.add(new FileOutConfig("/templates/vm/exportservice.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return javaSrcDir+"/com/lzn/mybatisplus/codegenerator/export/"+tableInfo.getEntityName()+"ExportService.java";
            }
        });
        //生成mybatisDao文件到指定目录
        focList.add(new FileOutConfig("/templates/vm/mybatisdao.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return  javaSrcDir+"/com/lzn/mybatisplus/codegenerator/dao/"+tableInfo.getEntityName()+"MybatisDao.java";
            }
        });

        //生成mapper文件到指定目录
        focList.add(new FileOutConfig("/templates/vm/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return resourceDir+"/mybatis/"+tableInfo.getEntityName()+"Mapper.xml";
            }
        });

        // 自定义 xxList.ftl 生成
        focList.add(new FileOutConfig("/templates/vm/list.ftl.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return pageRootDir+pageDirName+"/list.ftl";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setEntity("/templates/vm/entity.java.vm");
        tc.setService("/templates/vm/service.java.vm");
        tc.setServiceImpl(null);//设成null才会不生产
        tc.setController("/templates/vm/controller.java.vm");
        tc.setMapper(null);
        tc.setXml(null);
        mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}
