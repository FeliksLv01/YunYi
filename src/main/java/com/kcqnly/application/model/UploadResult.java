package com.kcqnly.application.model;

import lombok.Data;

@Data
public class UploadResult {
    private String url;
    private String md5;
    private String path;
    private String domain;
    private String scene;
    private String scenes;
    private String retmsg;
    private int retcode;
    private String src;
}
