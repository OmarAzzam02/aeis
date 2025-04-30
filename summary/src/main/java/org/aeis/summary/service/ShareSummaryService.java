package org.aeis.summary.service;


import org.aeis.summary.dao.SummaryDAO;
import org.aeis.summary.dao.SummaryDAOImpl;
import org.aeis.summary.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShareSummaryService {



    @Autowired
    private SummaryDAO summaryDAO;



    public void shareSummary(Long id){
    Optional<Summary> summary =  summaryDAO.findById(id);
        if (summary.isPresent()) {
            summaryDAO.updateSharedStatusById(id);
        } else {
            throw new RuntimeException("Summary not found with id: " + id);
        }
    }


}
