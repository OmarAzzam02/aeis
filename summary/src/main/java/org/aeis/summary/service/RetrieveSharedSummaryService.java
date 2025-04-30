package org.aeis.summary.service;


import lombok.extern.log4j.Log4j2;
import org.aeis.summary.dao.SummaryDAO;
import org.aeis.summary.dao.SummaryDAOImpl;
import org.aeis.summary.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RetrieveSharedSummaryService {


    @Autowired
    private SummaryDAO summaryDAO;

    public List<Summary> retrieveSharedSummaries(List<Long> ids) {
        log.info("Retrieving the summary from the database");

        return summaryDAO.findByCourseIdInAndIsShared(ids, 1);
    }




}
