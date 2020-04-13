package com.kcqnly.application.service;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kcqnly.application.common.*;
import com.kcqnly.application.common.FileResult;
import com.kcqnly.application.model.FileDetails;
import com.kcqnly.application.utils.FileUtil;
import com.kcqnly.application.utils.TimeUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Perfree
 * @Date 10:59 2019/5/11
 * 文件service
 */
@Service
public class FileService {

    private final String STATUS = "status";

    private final String STATUS_OK = "ok";
    /**
     * 获取一级目录
     *
     * @param peersGroupName
     * @param serverAddress
     * @return List<FileResult>
     */
    public List<FileResult> getParentFile(String peersGroupName, String serverAddress) {
        return FileUtil.getDirOrFileList(peersGroupName,serverAddress,null);
    }

    /**
     * 获取指定目录
     *
     * @param showUrl
     * @param serverAddress
     * @param dir
     * @return List<FileResult>
     */
    public List<FileResult> getDirFile(String showUrl, String serverAddress, String dir) {
        return FileUtil.getDirOrFileList(showUrl,serverAddress,dir);
    }

    /**
     * 删除文件
     * @param peersUrl 服务地址
     * @param md5 md5
     * @return boolean
     */
    public boolean deleteFile(String peersUrl, String md5) {
        HashMap<String, Object> param = new HashMap<>(10);
        param.put("md5",md5);
        JSONObject parseObj = JSONUtil.parseObj(HttpUtil.post(peersUrl + GoFastDfsApi.DELETE, param));
        if(parseObj.getStr(STATUS).equals(STATUS_OK)) {
            return true;
        }
        return false;
    }

    /**
     * 获取文件信息
     * @param peersUrl 服务地址
     * @param md5 md5
     * @return AjaxResult
     **/
    public Result details(String peersUrl, String md5) {
        HashMap<String, Object> param = new HashMap<>(10);
        param.put("md5",md5);
        JSONObject parseObj = JSONUtil.parseObj(HttpUtil.post(peersUrl + GoFastDfsApi.GET_FILE_INFO, param));
        if(parseObj.getStr(STATUS).equals(STATUS_OK)) {
            FileDetails fileDetails = JSONUtil.toBean(parseObj.getStr("data"), FileDetails.class);
            fileDetails.setSize(FileUtil.getLength(fileDetails.getSize()));
            fileDetails.setTimeStamp(TimeUtil.getFormatDate(new Date(Long.valueOf(fileDetails.getTimeStamp())* 1000)));
            return new Result(Result.AJAX_SUCCESS,"获取成功",fileDetails);
        }
        return new Result(Result.AJAX_ERROR,"获取文件信息失败");
    }
}
