package com.learn.ftpclient.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Saugat Thapaliya on 5/14/2024
 **/
@Getter
@Setter
@Builder
public class UploadResponse {
    private int code;
    private String message;
    private String fileName;
}
