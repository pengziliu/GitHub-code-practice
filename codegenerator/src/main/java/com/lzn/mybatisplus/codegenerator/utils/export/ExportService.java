package com.lzn.mybatisplus.codegenerator.utils.export;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 表格文件导出方法
 */
public interface ExportService<T> extends Serializable{


    /**
     * 下载的一些准备方法 可以在这个方法里面初始化一些查询参数等
     */
    void prepareExport(Map<String, Object> parameterMap);

    /**
     * 获取导出的进度
     * @return
     */
    //int getProcess();

    /**
     * 获取记录总数的方法
     */
    int queryTotal();

    /**
     * 获取记录List的方法
     */
    List<T> queryList(int start, int size);

    /**
     * 这个方法用来设置文件顶部的描述
     */
    List<String[]> topInfo();

    /**
     * 生成表格数据数据完成后开始下载
     */
    void download(OutputStream outputStream);

    /**
     * 下载的文件名
     * @return
     */
    String getFileName();

    /**
     * 获取泛型的class对象
     */
    Class<T> getClazz();

    /**
     * 设置单次导出的批次号
     * @param sequenceId
     */
    void setSequenceId(String sequenceId);
}
