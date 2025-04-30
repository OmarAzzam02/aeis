package org.aeis.reader.cache.summary;


import org.aeis.reader.dto.summary.GeneratedSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GeneratedSummaryCache {

    @Autowired
    private List<GeneratedSummaryDTO>  generatedSummaryCache;


    public void addGeneratedSummary(GeneratedSummaryDTO generatedSummaryDTO) {
        generatedSummaryCache.add(generatedSummaryDTO);
    }

    public GeneratedSummaryDTO getLastGeneratedSummary() {
        if (generatedSummaryCache.isEmpty()) {
            return null;
        }
        return generatedSummaryCache.get(generatedSummaryCache.size() - 1);
    }


    public void clearCache() {
        generatedSummaryCache.clear();
    }





}
