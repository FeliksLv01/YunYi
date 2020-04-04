package com.kcqnly.application.model;

import lombok.Data;

import java.util.List;


@Data
public class FileDetails {
    private String path;
    private String size;
    private String name;
    private String md5;
    private String scene;
    private String timeStamp;
    private List<String> peers;
}
