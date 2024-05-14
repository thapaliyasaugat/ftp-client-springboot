package com.learn.ftpclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Saugat Thapaliya on 5/12/2024
 **/
@Component
@ConfigurationProperties("ftp.connection")
public class FTPSetting extends AbstractFTPClientSetting{

}
