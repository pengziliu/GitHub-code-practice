package com.lzp.fastdfs.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class FdfsConfig {

    @Value("${fdfs.resHost}")
    private String resHost;

    @Value("${fdfs.storagePort}")
    private String storagePort;
}
