package org.aeis.summary.service;


import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.aeis.summary.cache.SummariesCache;
import org.aeis.summary.dao.SummaryDAOImpl;
import org.aeis.summary.dto.GeneratedSummaryDTO;
import org.aeis.summary.dto.SummaryDTO;
import org.aeis.summary.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class RedirectToReaderService {


    @Autowired
    RestTemplate restTemplate;

    @Value("${reader.service.url}")
    private String  readerUrl;

    @Autowired
    private SummaryDAOImpl summaryDAO;

    @Autowired
    private SummariesCache cache;

    @Autowired
    private SummaryMapperService mapperService;

    @Async
     public void redirectToReader() {
        log.info("Redirecting to reader service");
        Summary summary = cache.getLastAddedSummary();
        if (summary == null) {
            log.error("No summary found in cache");
            return;
        }


        GeneratedSummaryDTO generatedSummaryDTO = mapperService.mapToGeneratedDTO(summary);
        restTemplate.postForEntity(readerUrl, generatedSummaryDTO, Void.class);
    }



}
