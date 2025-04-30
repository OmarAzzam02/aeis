package org.aeis.summary.service;


import lombok.extern.log4j.Log4j2;
import org.aeis.summary.dao.SummaryDAO;
import org.aeis.summary.dao.SummaryDAOImpl;
import org.aeis.summary.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class SaveSummaryService {

    @Autowired
    private SummaryDAO summaryDAO;


    public Summary saveSummary(Summary summary){
        log.info("Saving the summary in the db ");
       Summary saved =  summaryDAO.save(summary);
       return saved;
    }







}

