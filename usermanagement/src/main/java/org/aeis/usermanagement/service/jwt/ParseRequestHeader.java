package org.aeis.usermanagement.service.jwt;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class ParseRequestHeader {

    public String parseHeader(HttpServletRequest request){
        return request.getHeader("Authorization").substring(7);
    }


}
