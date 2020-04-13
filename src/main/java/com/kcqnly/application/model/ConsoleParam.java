package com.kcqnly.application.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Component
public class ConsoleParam {

    @Value("${version}")
    private String version;

    @Value("${version.date}")
    private String versionDate;
    //剩余空间
    private String diskFreeSize;
    //总空间
    private String diskTotalSize;
    //文件总大小
    private String totalFileSize;
    //文件数量
    private String totalFileCount;

    private String osName;

    private String osArch;

    //声明30天文件大小数据容器
    private List<String> dayFileSizeList;
    //声明30天文件数量数据容器
    private List<String> dayFileCountList;
    //声明30天内日期容器
    private List<String> dayNumList;
}
