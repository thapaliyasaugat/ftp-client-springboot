package com.learn.ftpclient.service;

import com.learn.ftpclient.resource.DownloadRequest;
import com.learn.ftpclient.resource.UploadResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Saugat Thapaliya on 5/12/2024
 **/
public interface FTPClientService {
    UploadResponse uploadFile(String remotePath, MultipartFile file) throws IOException;
    void downloadFile(DownloadRequest downloadRequest) throws IOException;
}
