package org.aeis.summary.dao;


import lombok.extern.log4j.Log4j2;
import org.aeis.summary.cache.SummariesCache;
import org.aeis.summary.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class SummaryDAOImpl  {

    @Autowired
    private SummariesCache summariesCache;

}
