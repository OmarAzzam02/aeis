package org.aeis.summary.cache;


import org.aeis.summary.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SummariesCache {


    @Autowired
     private List<Summary> summariesCache;



    public void addSummary( Summary summary) {
        summariesCache.add(summary);
    }

    public Summary getSummary(Long id) {
        return summariesCache.stream()
                .filter(summary -> summary.getId().equals(id))
                .findFirst()
                .orElse(null);
    }


    public Summary getLastAddedSummary() {
        if (summariesCache.isEmpty()) {
            return null;
        }
        return summariesCache.get(summariesCache.size() - 1);
    }





}
