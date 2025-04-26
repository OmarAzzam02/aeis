package org.aeis.reader.service.handler;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class UrlServiceLocator {

    @Getter
    @Value("${user.management.login.url}")
    private String loginServiceUrl;

    @Value("${user.management.info.url}")
    private String UserInfoServiceUrl;

    @Value("${user.twoauth.factor.url}")
    private String generateOtpServiceUrl;

    @Value("${user.twoauth.verify.url}")
    private String verifyOtpServiceUrl;

    @Value("${user.management.validate.token.url}")
    private String validateTokenServiceUrl;

    @Value("${recording.service.start.url}")
    private String RECORDING_STARTER_URL;
    @Value("${recording.service.stop.url}")
    private String RECORDING_STOPPER_URL;
}
