package com.learn.ftpclient.service;

import com.learn.ftpclient.resource.DownloadRequest;
import com.learn.ftpclient.resource.UploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by Saugat Thapaliya on 5/12/2024
 **/
@Slf4j
@Service
public class FTPClientServiceImpl implements FTPClientService {

    private final String DEFAULT_FILE_PATH = "/home/files";

    private final FTPClient ftpClient;

    public FTPClientServiceImpl(@Qualifier("ftpConfig") FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    private static String getFileName(String filePath) {
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }

    @Override
    public UploadResponse uploadFile(String remotePath, MultipartFile file) throws IOException {
        try {
            String filePath = prepareRemotePath(remotePath) + file.getOriginalFilename();
            log.info("uploading file to remote path : {}", filePath);
            InputStream fileInputStream = file.getInputStream();
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            System.out.println(ftpClient.getStatus());
            System.out.println("file" + fileInputStream);
            boolean uploaded = ftpClient.storeFile(filePath, fileInputStream);
            if (!uploaded) {
                log.error("upload failed");
                throw new RuntimeException("upload failed");
            }
            return UploadResponse.builder()
                    .code(0)
                    .message("upload success")
                    .fileName(filePath)
                    .build();
        } catch (IOException e) {
            log.error("I/O error uploading file to remote ftp : {}", e.getMessage());
            throw new RuntimeException("Error Uploading File");
        } catch (Exception e) {
            log.error("Error uploading file to remote ftp : {}", e.getMessage());
            throw new RuntimeException("Error Uploading File");
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    @Override
    public void downloadFile(DownloadRequest downloadRequest) throws IOException {
        try {
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            //local path
            OutputStream outputStream = new FileOutputStream("E:\\" + getFileName(downloadRequest.getFileName()));
            boolean success = ftpClient.retrieveFile(downloadRequest.getFileName(), outputStream);
            outputStream.close();

            if (success) {
                log.info("File downloaded successfully.");
            } else {
                log.error("File download failed.");
            }
        } catch (IOException e) {
            log.error("I/O error downloading file to remote ftp : {}", e.getMessage());
            throw new RuntimeException("Error Downloading File from Ftp Server");
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                log.error("Error terminating connection : {}", ex.getMessage());
            }
        }
    }

    private String prepareRemotePath(String remotePath) {
        String randomText = UUID.randomUUID().toString().replace("-", "");
        if (remotePath == null || remotePath.isEmpty()) {
            return DEFAULT_FILE_PATH + "/" + randomText + "_";
        }
        return remotePath + "/" + randomText + "_";
    }
}
