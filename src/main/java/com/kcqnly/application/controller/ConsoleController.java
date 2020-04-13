package com.kcqnly.application.controller;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kcqnly.application.common.GoFastDfsApi;
import com.kcqnly.application.common.Result;
import com.kcqnly.application.model.ConsoleParam;
import com.kcqnly.application.service.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ConsoleController {

    @Value("${fileServer.url}")
    private String serverUrl;
    @Autowired
    private ConsoleService consoleService;

    @GetMapping("/console/state")
    @PreAuthorize("hasAuthority('控制台')")
    public Result getState() {
        try {
            String string = HttpUtil.get(serverUrl + GoFastDfsApi.STATUS);
            JSONObject parseObj = JSONUtil.parseObj(string);
            if (parseObj.get("status").equals("ok")) {
                ConsoleParam consoleParam = consoleService.getfileState(parseObj.get("data"));
                return Result.ok("成功",consoleParam);
            } else {
                return Result.error("调取go-fastdfs接口失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("系统异常");
    }

    /**
     * 删除空目录
     * @return AjaxResult
     */
    @PostMapping("/console/remove_empty_dir")
    @PreAuthorize("hasAuthority('控制台')")
    public Result remove_empty_dir(){
        String result = HttpUtil.post(serverUrl+GoFastDfsApi.REMOVE_EMPTY_DIR, new HashMap<>());
        JSONObject parseObj = JSONUtil.parseObj(result);
        if(parseObj.get("status").equals("ok")) {
            return Result.ok("操作成功,正在后台操作,请勿重复使用此功能",null);
        }else{
            return Result.error("操作失败,请稍后再试");
        }
    }

}
