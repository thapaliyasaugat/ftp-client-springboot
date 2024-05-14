package com.learn.ftpclient.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * Created by Saugat Thapaliya on 5/12/2024
 **/
@Getter
@Setter
@ToString(exclude = {"password"})
public abstract class AbstractFTPClientSetting {
    private String host = "localhost";
    private int port = 21;
    private String username = "user";
    private String password = "password";
    private int timeout = 30000;
    private String encoding = "UTF-8";
    private int mode = FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE;
    private int bufferSize = 1024;

    protected FTPClient getFTPClient(AbstractFTPClientSetting ftpSetting) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpSetting.getHost(), ftpSetting.getPort());
        ftpClient.login(ftpSetting.getUsername(), ftpSetting.getPassword());
        ftpClient.setDefaultTimeout(ftpSetting.getTimeout());
        ftpClient.setBufferSize(ftpSetting.getBufferSize());
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setControlEncoding(ftpSetting.getEncoding());
        return ftpClient;
    }
}
