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

    @Value("${user.management.validate.token.url}")
    public String getValidateTokenServiceUrl() {
        return validateTokenServiceUrl;
    }

    public String getUserInfoServiceUrl() {
        return UserInfoServiceUrl;
    }

    public String getVerifyOtpServiceUrl() {
        return verifyOtpServiceUrl;
    }

    public String getUserInfoServiceUrl(long userId) {
        return UserInfoServiceUrl+userId;
    }

    public String getLoginServiceUrl() {
        return loginServiceUrl;
    }

    public String getGenerateOtpServiceUrl() {
        return generateOtpServiceUrl;
    }
}
