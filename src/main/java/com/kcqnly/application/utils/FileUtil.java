package com.kcqnly.application.utils;

import com.kcqnly.application.common.FileResult;

import java.text.DecimalFormat;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kcqnly.application.common.GoFastDfsApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * 文件大小转换工具类
 * @author Kcqnly
 *
 */
public class FileUtil {

    /**
     * 根据文件大小转换为B、KB、MB、GB单位字符串显示
     * @param strFileSize 文件的大小（long型）
     * @return 返回 转换后带有单位的字符串
     */
    public static String getLength(String strFileSize){
        long fileSize = Long.parseLong(strFileSize);
        if(fileSize < 1024){
            strFileSize = fileSize+"B";
            return strFileSize;
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        if (fileSize < 1024*1024){
            strFileSize = df.format(((double)fileSize)/1024)+"KB";
        }else if(fileSize < 1024*1024*1024){
            strFileSize = df.format(((double)fileSize)/(1024*1024))+"MB";
        }else{
            strFileSize = df.format(((double)fileSize)/(1024*1024*1024))+"GB";
        }
        return strFileSize;
    }

    /**
     * @param strFileSize 文件的大小（long型）
     * @return 返回 转换后不带单位的字符串
     */
    public static String getMBLength(String strFileSize){
        long fileSize=Long.parseLong(strFileSize);
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(((double)fileSize)/(1024*1024));
    }

    /**
     * 获取文件列表
     * @param serverAddress 服务地址
     * @param dir 要获取的目录(根目录为null)
     * @return List<FileResult>
     */
    public static List<FileResult> getDirOrFileList(String showUrl,String serverAddress,String dir){
        HashMap<String, Object> param = new HashMap<>(10);
        if(StrUtil.isNotBlank(dir)){
            param.put("dir",dir);
        }
        String result = HttpUtil.post(serverAddress + GoFastDfsApi.LIST_DIR, param);
        JSONObject parseObj = JSONUtil.parseObj(result);
        List<FileResult> files = new ArrayList<>();
        if(parseObj.getStr("message").equals("") && StrUtil.isNotBlank(parseObj.getStr("data"))) {
            JSONArray parseArray = JSONUtil.parseArray(parseObj.getStr("data"));
            for (int i = 0;i < parseArray.size();i++) {
                FileResult fileResult = new FileResult();
                JSONObject file = JSONUtil.parseObj(parseArray.getStr(i));
                if(file.getStr("name").equals("_tmp")){
                    continue;
                }
                fileResult.setMd5(file.getStr("md5"));
                fileResult.setPath(file.getStr("path"));
                fileResult.setName(file.getStr("name"));
                fileResult.setIs_dir(file.getBool("is_dir"));
                if(file.getBool("is_dir")){
                    fileResult.setSize("-");
                    fileResult.setType("directory");
                }else{
                    String[] str = fileResult.getName().split("\\.");
                    fileResult.setType(str[str.length-1]);
                    fileResult.setSize(getLength(file.getStr("size")));
                }
                fileResult.setMTime(TimeUtil.timeStamp2Date(file.getStr("mtime"),null));
                files.add(fileResult);
            }
        }
        return files;
    }

}
