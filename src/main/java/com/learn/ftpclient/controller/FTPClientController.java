package com.learn.ftpclient.controller;

import com.learn.ftpclient.resource.DownloadRequest;
import com.learn.ftpclient.resource.UploadResponse;
import com.learn.ftpclient.service.FTPClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Saugat Thapaliya on 5/12/2024
 **/
@RestController
@RequestMapping("/ftp")
@RequiredArgsConstructor
public class FTPClientController {
    private final FTPClientService ftpClientService;

    @PostMapping("/upload")
    public UploadResponse upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "path", required = false) String path) throws IOException {
        return ftpClientService.uploadFile(path, file);
    }

    @GetMapping("/download")
    public void downloadFileFromFtp(@RequestBody DownloadRequest downloadRequest) throws IOException {
        ftpClientService.downloadFile(downloadRequest);
    }
}
