package org.aeis.summary.controller;


import org.aeis.summary.dto.SummaryDTO;
import org.aeis.summary.dto.SummaryRequestDTO;
import org.aeis.summary.service.HandleSummaryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SummaryController {

    @Autowired
    private HandleSummaryServices handleSummaryServices;

    @PostMapping("/save-summary")
    public ResponseEntity<?> saveSummary(@RequestBody SummaryDTO summaryDTO) {
        handleSummaryServices.saveSummary(summaryDTO);
        return ResponseEntity.ok("Summary saved successfully");
    }

    @PutMapping("/share-summary/{id}")
    public ResponseEntity<?> shareSummary(@PathVariable Long id){
        handleSummaryServices.shareSummary(id);
        return ResponseEntity.ok("Summary shared successfully");
    }


    @PostMapping("/retrieve-summaries")
    public ResponseEntity<List<SummaryDTO>> retrieveSummaries(@RequestBody SummaryRequestDTO summaryRequestDTO) {
        return ResponseEntity.ok(handleSummaryServices.retrieveSummaries(summaryRequestDTO));
    }




}
