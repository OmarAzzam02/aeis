package org.aeis.summary.service;


import org.aeis.summary.cache.SummariesCache;
import org.aeis.summary.dto.SummaryDTO;
import org.aeis.summary.dto.SummaryRequestDTO;
import org.aeis.summary.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HandleSummaryServices {

    @Autowired
    private SaveSummaryService saveSummaryService;

    @Autowired
    private ShareSummaryService shareSummaryService;

    @Autowired
    private RetrieveSharedSummaryService retrieveSharedSummaryService;

    @Autowired
    private SummaryMapperService mapperService;

    @Autowired
    private SummariesCache summariesCache;

    @Autowired
    private RedirectToReaderService redirectService;

    public void saveSummary(SummaryDTO summaryDTO) {
        Summary summaryToSave = mapperService.mapToEntity(summaryDTO);
      Summary saved =   saveSummaryService.saveSummary(summaryToSave);
        summariesCache.addSummary(saved);
        redirectService.redirectToReader();
    }

    public void shareSummary(Long id) {
         shareSummaryService.shareSummary(id);
    }


    public List<SummaryDTO> retrieveSummaries(SummaryRequestDTO summaryRequestDTO) {
        List<Long> ids = summaryRequestDTO.getCourseIds();
        List<Summary> summary = retrieveSharedSummaryService.retrieveSharedSummaries(ids);
        List<SummaryDTO> dto  = mapListToDTO(summary);
        return dto;
    }


    private List<SummaryDTO> mapListToDTO(List<Summary> summary) {
        return summary.stream()
                .map(mapperService::mapToDTO)
                .collect(Collectors.toList());
    }



}
