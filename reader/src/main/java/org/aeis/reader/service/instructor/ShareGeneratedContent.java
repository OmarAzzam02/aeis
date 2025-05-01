package org.aeis.reader.service.instructor;


import org.aeis.reader.service.handler.UrlServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShareGeneratedContent {


    @Autowired
    private UrlServiceLocator urlServiceLocator;


    @Autowired
    private RestTemplate restTemplate;



    public void shareSummary(Long id) {
        String url = urlServiceLocator.getSUMMARY_SHARE_URL() + "/" + id;
        restTemplate.put(url, null, Void.class);
    }


    public void shareVideo(Long id) {
        String url = urlServiceLocator.getRECORDING_SHARE_URL() + "/" + id;
        restTemplate.put(url, null, Void.class);
    }
}
