package com.lzn.mybatisplus.codegenerator.utils.export;



import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


/**
 * 默认Csv文件导出方法
 */
public abstract class AbstractCSVExportService<T> extends AbstractExportService<T> {

    CSVWriter csvWriter;

    @Override
    public void initWriteFile(OutputStream outputStream) {
        OutputStreamWriter osWriter = null;
        try {
            osWriter = new OutputStreamWriter(outputStream,"GBK");
        } catch (UnsupportedEncodingException e) {
            osWriter = new OutputStreamWriter(outputStream);
        }
        csvWriter = new CSVWriter(osWriter);
    }

    @Override
    public void downloadFinish(OutputStream outputStream) {
        try {
            csvWriter.close();
        } catch (IOException e) {

        }
    }

    @Override
    public void writeLine(String[] line) {
        csvWriter.writeNext(line);
    }
}
