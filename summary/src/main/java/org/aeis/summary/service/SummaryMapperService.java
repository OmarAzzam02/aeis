package org.aeis.summary.service;


import org.aeis.summary.dto.GeneratedSummaryDTO;
import org.aeis.summary.dto.SummaryDTO;
import org.aeis.summary.entity.Summary;
import org.springframework.stereotype.Service;

@Service
public class SummaryMapperService {

    public Summary mapToEntity(SummaryDTO summaryDTO) {
        return Summary.builder()
                .title(summaryDTO.getTitle())
                .courseId(summaryDTO.getCourseId())
                .content(summaryDTO.getContent())
                .build();
    }


    public SummaryDTO mapToDTO(Summary summary) {
        return SummaryDTO.builder()
                .title(summary.getTitle())
                .courseId(summary.getCourseId())
                .content(summary.getContent())
                .build();
    }

    public GeneratedSummaryDTO mapToGeneratedDTO(Summary summary) {
        return GeneratedSummaryDTO.builder()
                .id(summary.getId())
                .title(summary.getTitle())
                .courseId(summary.getCourseId())
                .content(summary.getContent())
                .build();
    }


}
