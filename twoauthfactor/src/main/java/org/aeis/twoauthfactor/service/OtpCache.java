package org.aeis.twoauthfactor.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OtpCache {

    @Autowired
    private HashMap<String, String> otpCache;



    public void addOtpToCache(String email, String otp) {
        otpCache.put(email, otp);
    }


    public void removeOtpFromCache(String email) {
        otpCache.remove(email);
    }

    public String getOtpFromCache(String email) {
        return otpCache.get(email);
    }



}
