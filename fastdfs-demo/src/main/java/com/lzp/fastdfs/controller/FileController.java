package com.lzp.fastdfs.controller;

import com.lzp.fastdfs.config.FdfsConfig;
import com.lzp.fastdfs.utils.CommonFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private CommonFileUtil fileUtil;

    @Autowired
    private FdfsConfig fdfsConfig;

    @PostMapping("/upload")
    public String uoloadFileToFast(@RequestParam("file") MultipartFile file) throws IOException {

        String path = fileUtil.uploadFile(file);
        String url = fdfsConfig.getResHost()+path;
        log.info("图片生成url为：{}",url);

        return "<a href="+url+">图片上传成功，点击访问</a>";
    }


}
