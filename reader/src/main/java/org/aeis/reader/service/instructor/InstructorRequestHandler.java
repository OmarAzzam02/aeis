package org.aeis.reader.service.instructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class InstructorRequestHandler {



    @Autowired
    private LectureRecordingService lectureRecordingService;

    @Autowired
    private ShareGeneratedContent shareGeneratedContent;

    public ResponseEntity<?> startRecording(String hallName, MultipartFile pdfFile, String authorization) {
         return lectureRecordingService.startRecording(hallName, pdfFile, authorization);
    }

    public ResponseEntity<?> stopRecording(String hallName, String authorization) {
        return lectureRecordingService.stopRecording(hallName, authorization);
    }

    public void shareSummary(Long id) {
        shareGeneratedContent.shareSummary(id);
    }

    public void shareVideo(Long id) {
        shareGeneratedContent.shareVideo(id);
    }

}
