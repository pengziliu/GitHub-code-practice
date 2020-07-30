package com.lzn.mybatisplus.codegenerator.utils.export;


import com.lzn.mybatisplus.codegenerator.utils.DateUtils;
import com.lzn.mybatisplus.codegenerator.utils.redis.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


public abstract class AbstractExportService<T> implements ExportService<T>,Serializable {

    public static final Logger logger = LoggerFactory.getLogger(AbstractExportService.class);

    private static final long serialVersionUID = -4462235660291304815L;
    /**
     * 每次查询数据的大小
     */
    protected final static int PER_SIZE = 10000;

    /**
     * 导出的表格列定义
     */
    protected Map<String,Integer> colMap;

    protected String sequenceId;

    protected static final String EXPORT_TOTAL_COUNT_CACHE_PREFIX = "EXPORT_TOTAL_COUNT_CACHE_PREFIX:";
    protected static final String EXPORT_FINISH_COUNT_CACHE_PREFIX = "EXPORT_FINISH_COUNT_CACHE_PREFIX:";

    /**
     * 将导出的进度放在 redis 中管理, 对所有请求共享
     */
    @Resource
    protected IRedisService redisService;

    protected boolean flagFreight = false;

    /**
     * 初始化下载
     */
    public abstract void initWriteFile(OutputStream outputStream);

    /**
     * 下载完成
     */
    public abstract void downloadFinish(OutputStream outputStream);

    public abstract void writeLine(String[] line);

    /**
     * 设置导出表格的表头
     */
    public String[] getTableHead(){
        return null;
    }

    @Override
    public void download(OutputStream outputStream) {
        initWriteFile(outputStream);
        // 先写 顶部的综合描述信息
        List<String[]> topInfo = topInfo();
        if(topInfo != null){
            for (String[] topLine : topInfo) {
                writeLine(topLine);
            }
        }
        try {
            // 然后再写表格的表头
            String[] tableHead = getTableHead();
            if (tableHead == null) {
                tableHead = initTableHead();
            }
            if (tableHead != null) {
                writeLine(tableHead);
            }
            // 分页查询数据 并写进CSV文件中
            int total = queryTotal();
            redisService.incrBy(EXPORT_TOTAL_COUNT_CACHE_PREFIX.concat(sequenceId),total);
            for (int count = 0; count < total; count += PER_SIZE) {
                List<T> result = queryList(count, PER_SIZE);
                for (T t : result) {
                    String[] line;
                    if (t instanceof ExportRowCreator) {
                        ExportRowCreator rowCreator = (ExportRowCreator) t;
                        line = rowCreator.createRow();
                    } else {
                        line = new String[colMap.size()];
                        Class<?> clazz = t.getClass();
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
                            if (colMap.containsKey(field.getName())) {
                                int colNum = colMap.get(field.getName());
                                String value = getFieldValue(field, t);
                                line[colNum] = value;
                                //                    }
                            }
                        }
                        Method[] methods = clazz.getDeclaredMethods();
                        for (Method method : methods) {
                            if (colMap.containsKey(method.getName())) {
                                int colNum = colMap.get(method.getName());
                                String value = getMethodValue(method, t);
                                line[colNum] = value;
                            }
                        }
                    }
                    writeLine(line);
                    redisService.incr(EXPORT_FINISH_COUNT_CACHE_PREFIX.concat(sequenceId));
                }
            }
        } catch (Exception e) {
            logger.error("导出创建文件时出错了",e);
        }
        downloadFinish(outputStream);
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
        }

    }


    @Override
    public List<String[]> topInfo() {
        return null;
    }

    /**
     * 获取属性的值
     */
    public String getFieldValue(Field field,T t){
        try {
            // 直接修改访问权限 获取属性值
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            Object value = field.get(t);
            if(value == null){
                return "";
            }
            /*
             // 通过getter方法获取属性值
             PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();//获得get方法
                if(getMethod == null){
                    LogUtil.error(clazz.getName() + "  " + field.getName() + " is private or protected,but no getter method");
                    return;
                }
                value = getMethod.invoke(t);//执行get方法返回一个Object
             */
            String fieldType = field.getGenericType().toString();
            return getValue(value, fieldType);
        } catch (Exception e){
            logger.error("获取导出对象的属性时 出错了",e);
            return "";
        }
    }

    /**
     * 获取方法的值
     */
    public String getMethodValue(Method method,T t){
        try {
            Object value = null;
            try {
                value = method.invoke(t);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("获取导出对象的方法值时 出错了",e);
                return "";
            }
            if(value == null){
                return "";
            }
            String resultType = method.getReturnType().toString();
            return getValue(value, resultType);
        }catch (Exception e){
            logger.error("获取导出对象的方法值时 出错了",e);
            return "";
        }
    }

    private String getValue(Object value, String valueType) {
        if (valueType.equals("class java.lang.String")){
            return (String) value;
        }else if (valueType.equals("class java.util.Date")){
            return DateUtils.formatDate((Date) value);
        }else if (valueType.equals("class java.math.BigDecimal")){
            return value.toString();
        }else if(valueType.equals("class java.lang.Byte")){
            return String.valueOf(value);
        }else if(valueType.equals("class java.lang.Short")){
            return String.valueOf(value);
        }else if(valueType.equals("class java.lang.Integer")){
            return String.valueOf(value);
        }else if(valueType.equals("class java.lang.Long")){
            return String.valueOf(value);
        }else if(valueType.equals("class java.lang.Float")){
            return String.valueOf(value);
        }else if(valueType.equals("class java.lang.Double")){
            return String.valueOf(value);
        }else if(valueType.equals("class java.lang.Boolean")){
            return String.valueOf(value);
        }else if(valueType.equals("class java.lang.Character")){
            return String.valueOf(value);
        }else if(valueType.equals("byte")){
            return String.valueOf(value);
        }else if(valueType.equals("short")){
            return String.valueOf(value);
        } else if(valueType.equals("int")){
            return String.valueOf(value);
        }else if(valueType.equals("long")){
            return String.valueOf(value);
        }else if(valueType.equals("float")){
            return String.valueOf(value);
        } else if(valueType.equals("double")){
            return String.valueOf(value);
        }else if(valueType.equals("boolean")){
            return String.valueOf(value);
        }else if(valueType.equals("char")){
            return String.valueOf(value);
        }else{
            logger.error("暂不支持的数据类型 ===> {} ",valueType);
            return value.toString();
        }
    }


    /**
     * 初始化表格的表头数据
     */
    public String[] initTableHead(){
        List<ColDescribe> colList = new LinkedList<>();
        Class<T> clazz = getClazz();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ColDescribe col = initColDescribe(field,field.getName());
            if(col != null){
                colList.add(col);
            }
        }
        // 遍历类中所有含有 ExportExcelField这个注解的方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods){
            ColDescribe col = initColDescribe(method,method.getName());
            if(col != null){
                colList.add(col);
            }
        }
        Collections.sort(colList, new Comparator<ColDescribe>() {
            @Override
            public int compare(ColDescribe o1, ColDescribe o2) {
                return o1.order - o2.order;
            }
        });
        String[] tableHead = new String[colList.size()];
        colMap = new HashMap<>();
        for (int i=0;i< colList.size();i++){
            ColDescribe colDescribe = colList.get(i);
            colMap.put(colDescribe.accessName,i);
            tableHead[i] = colDescribe.exportName;
        }
        return tableHead;
    }

    /**
     * 初始化列头的信息
     */
    private ColDescribe initColDescribe(AccessibleObject accessibleObject, String accessName) {
        if (!accessibleObject.isAnnotationPresent(ExportField.class)) {
            // 没有继承这个注解的列 则不考虑
            return null;
        }
        // 如果继承了 ExportExcelField注解  则按照这个注解处理
        ExportField annotation = accessibleObject.getAnnotation(ExportField.class);
        if (annotation != null && annotation.ignore()) {
            return null;
        }
        String name = accessName;
        if(annotation != null && annotation.name() != null){
            name = annotation.name();
        }
        int order = 0;
        if(annotation != null) {
            order = annotation.order();
        }
        ColDescribe col = new ColDescribe(accessName, name, order);
        return col;
    }

    /**
     * 导出的列定义
     */
    class ColDescribe {
        ColDescribe(String accessName, String exportName, int order) {
            this.accessName = accessName;
            this.exportName = exportName;
            this.order = order;
        }

        /**
         * 属性或者方法名
         */
        public String accessName;
        /**
         * 导出的表头名
         */
        public String exportName;
        /**
         * 排序
         */
        public int order;
    }

    /**
     * 设置当前导出的请求批次号
     * @param sequenceId
     */
    @Override
    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }
}
