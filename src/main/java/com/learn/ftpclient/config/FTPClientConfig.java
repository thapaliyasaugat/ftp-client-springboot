package com.learn.ftpclient.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;

/**
 * Created by Saugat Thapaliya on 5/12/2024
 **/
@Slf4j
@Configuration
//@Scope("prototype")
//@RequestScope
public class FTPClientConfig extends AbstractFTPClientSetting {

    FTPClientConfig(){
        log.info("FTPClientConfig initialized");
    }

    private static final String FTP_CONN = "ftpConfig";

    @Bean(name = FTP_CONN)
    @RequestScope
    public FTPClient defaultFTPClient(FTPSetting ftpSetting) throws IOException {
        log.info("FTP Client Config : {}", ftpSetting);
        return getFTPClient(ftpSetting);
    }
}
