package com.kcqnly.application.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kcqnly.application.common.Result;
import com.kcqnly.application.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

@RestController
public class FileController {


    @Autowired
    private FileService fileService;
    @Value("${fileServer.url}")
    private String url;
    @Value("${fileServer.showUrl}")
    private String showUrl;
    @Value("${fileServer.groupName}")
    private String groupName;

    /**
     * 获取一级目录
     *
     * @return AjaxResult
     */
    @GetMapping("/file/getParentFile")
    @PreAuthorize("hasAuthority('文件列表')")
    public Result getParentFile() {
        return Result.ok(fileService.getParentFile(groupName, url));
    }

    /**
     * 指定目录获取
     *
     * @param dir
     * @return AjaxResult
     */
    @GetMapping("/file/getDirFile")
    @PreAuthorize("hasAuthority('文件列表')")
    public Result getDirFile(@RequestParam("dir") String dir) {
        return Result.ok( fileService.getDirFile(showUrl, url, dir));
    }

    /**
     * 删除文件
     *
     * @param md5
     * @return AjaxResult
     */
    @DeleteMapping("/file/delete/{md5}")
    @PreAuthorize("hasAuthority('删除文件')")
    public Result deleteFile(@PathVariable String md5) {
        if (fileService.deleteFile(url, md5)) {
            return new Result(Result.AJAX_SUCCESS,"删除成功");
        }
        return new Result(Result.AJAX_ERROR, "删除失败");
    }

    /**
     * 文件信息
     *
     * @param
     * @return AjaxResult
     */
    @PostMapping("/file/details")
    @PreAuthorize("hasAuthority('文件列表')")
    public Result details( String md5) {
        return fileService.details(url, md5);
    }

    /**
     * 下载文件
     *
     * @param path
     * @param name
     * @param response
     */
    @PreAuthorize("hasAuthority('下载')")
    @GetMapping("/file/downloadFile")
    public void downloadFile(String path,String name, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment;filename=" + name);
        response.setContentType("application/octet-stream");
        BufferedInputStream in = null;
        try {
            URL url1 = new URL(url + "/" + path + "/" + name.replaceAll(" ","%20"));
            in = new BufferedInputStream(url1.openStream());
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
            // 将网络输入流转换为输出流
            int i;
            while ((i = in.read()) != -1) {
                response.getOutputStream().write(i);
            }
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
