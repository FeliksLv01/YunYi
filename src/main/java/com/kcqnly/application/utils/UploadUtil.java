package com.kcqnly.application.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.kcqnly.application.common.Result;
import com.kcqnly.application.model.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadUtil {
    /**
     * 文件上传
     *
     * @param url           服务器地址
     * @param path          路径
     * @param scene         场景
     * @param multipartFile 文件
     * @param showUrl       回显url
     * @return AjaxResult
     */
    public static Result upload(String tempPath, String url, String path, String scene, MultipartFile multipartFile, String showUrl) {
        Result result = null;
        File parentFile = new File(tempPath);
        if (parentFile.exists()) {
            if (!parentFile.isDirectory()) {
                return Result.error("临时目录不是一个文件夹");
            }
        } else {
            parentFile.mkdir();
        }
        File file = UploadUtil.getFile(multipartFile, tempPath);
        Map<String, Object> map = new HashMap<>(16);
        map.put("output", "json");
        map.put("path", path);
        map.put("scene", scene);
        map.put("file", file);
        try {
            String post = HttpUtil.post(url, map);
            UploadResult goFastDfsResult = JSONUtil.toBean(post, UploadResult.class);
            //替换url
            goFastDfsResult.setUrl(showUrl + goFastDfsResult.getPath());
            result = new Result(Result.AJAX_SUCCESS, goFastDfsResult);
        } catch (Exception e) {
            result = Result.error("上传出错");
        } finally {
            file.delete();
        }
        return result;
    }


    /**
     * multipartFile转file
     *
     * @param multipartFile
     * @return File
     */
    private static File getFile(MultipartFile multipartFile, String tempPath) {
        String fileName = multipartFile.getOriginalFilename();
        String filePath = tempPath;
        File file = new File(filePath + fileName);
        try {
            multipartFile.transferTo(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
