package org.aeis.videorecording.service;


import org.aeis.videorecording.cache.VideoRecordingCache;
import org.aeis.videorecording.dao.VideoRecordingDAO;
import org.aeis.videorecording.dto.VideoDTO;
import org.aeis.videorecording.dto.VideoRecordingRequest;
import org.aeis.videorecording.entity.VideoRecording;
import org.aeis.videorecording.mapper.VideoRecordingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HandleVideoRecordingService {


    @Autowired
    private VideoRecordingDAO videoRecordingDAO;

    @Autowired
    private VideoRecordingCache videoRecordingCache;

    @Autowired
    private VideoRecordingMapper videoRecordingMapper;

    @Autowired
    private RedirectGeneratedVideo redirectGeneratedVideo;



    public void saveVideoRecording(VideoDTO videoDTO){
        VideoRecording videoRecording = videoRecordingMapper.mapToEntity(videoDTO);
        VideoRecording savedVideoRecording = videoRecordingDAO.save(videoRecording);
        videoRecordingCache.addVideoRecording(savedVideoRecording);
        redirectGeneratedVideo.redirectToReader();
    }


    public void shareVideo(Long videoId) {
          videoRecordingDAO.updateSharedStatusById(videoId);

        Optional<VideoRecording> summary =  videoRecordingDAO.findById(videoId);
        if (summary.isPresent()) {
            videoRecordingDAO.updateSharedStatusById(videoId);
        } else {
            throw new RuntimeException("Summary not found with id: " + videoId  );
        }
    }



   public ResponseEntity<List<VideoDTO>> getAllSharedVideos(VideoRecordingRequest videoRequest) {
        List<VideoRecording> videoRecordings = videoRecordingDAO.findByCourseIdInAndIsShared(videoRequest.getCourseIds(),1);
        List<VideoDTO> videoDTOs = mapToDTos(videoRecordings);
        return ResponseEntity.ok(videoDTOs);
   }

    private List<VideoDTO> mapToDTos(List<VideoRecording> videoRecordings) {
        return videoRecordings.stream()
                .map(videoRecordingMapper::mapToDTO)
                .toList();
    }



}
