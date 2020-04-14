package com.kcqnly.application.controller;

import cn.hutool.core.util.StrUtil;
import com.kcqnly.application.common.GoFastDfsApi;
import com.kcqnly.application.common.Result;
import com.kcqnly.application.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UploadController {
    @Value("${upload.temp.path}")
    private String tempPath;

    @Value("${fileServer.url}")
    private String serverUrl;

    @Value("fileServer.showUrl")
    private String showUrl;

    @PostMapping("/file/upload/")
    @PreAuthorize("hasAuthority('文件列表')")
    public Result moreFileUpload(@RequestBody MultipartFile file,  String path) {
        String scene = path;
        if (file.isEmpty()) {
            return Result.error("请选择文件");
        }
        if (path.contains("/")) {
            scene = path.substring(0, path.lastIndexOf("/"));
        }
        //path="img/壁纸" scene="img"
        return UploadUtil.upload(tempPath, serverUrl + GoFastDfsApi.UPLOAD, path, scene, file, showUrl);
    }
}
