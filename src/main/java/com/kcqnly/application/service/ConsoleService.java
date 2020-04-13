package com.kcqnly.application.service;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kcqnly.application.model.ConsoleParam;
import com.kcqnly.application.utils.FileUtil;
import com.kcqnly.application.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class ConsoleService {
    @Autowired
    private ConsoleParam consoleParam;

    /**
     * 文件存储统计
     *
     * @param data
     * @return ConsoleParam
     */
    public ConsoleParam getfileState(Object data) {
        List<String> dayFileSizeList = new ArrayList<>();
        //声明30天文件数量数据容器
        List<String> dayFileCountList = new ArrayList<>();
        //声明30天内日期容器
        List<String> dayNumList = new ArrayList<>();
        JSONObject parseObj = JSONUtil.parseObj(data);
        JSONArray parseArray = JSONUtil.parseArray(parseObj.get("Fs.FileStats"));
        // 剩余空间
        consoleParam.setDiskFreeSize(FileUtil.getLength(JSONUtil.parseObj(parseObj.get("Sys.DiskInfo")).getStr("free")));
        // 总空间
        consoleParam.setDiskTotalSize(FileUtil.getLength(JSONUtil.parseObj(parseObj.get("Sys.DiskInfo")).getStr("total")));
        for (int i = 0; i < parseArray.size(); i++) {
            JSONObject fileStats = JSONUtil.parseObj(parseArray.getStr(i));
            if (fileStats.get("date").equals("all")) {
                //获取总文件大小,数量
                consoleParam.setTotalFileSize(FileUtil.getLength(fileStats.getStr("totalSize")));
                consoleParam.setTotalFileCount(fileStats.getStr("fileCount"));
            }
            else{
                long subDay = TimeUtil.daysBetween(TimeUtil.StrToDate(fileStats.getStr("date"), "yyyyMMdd"), new Date());
                if (subDay <= 30){
                    //获取30天内文件大小,数量
                    dayFileSizeList.add(FileUtil.getMBLength(fileStats.getStr("totalSize")));
                    dayFileCountList.add(fileStats.getStr("fileCount"));
                    dayNumList.add(fileStats.getStr("date").substring(6)+"日");
                }
            }
        }

        Properties props = System.getProperties();
        consoleParam.setOsName(props.getProperty("os.name"));
        consoleParam.setOsArch(props.getProperty("os.arch"));
        consoleParam.setDayNumList(dayNumList);
        consoleParam.setDayFileCountList(dayFileCountList);
        consoleParam.setDayFileSizeList(dayFileSizeList);
        return consoleParam;
    }
}
