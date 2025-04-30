package org.aeis.videorecording.mapper;


import org.aeis.videorecording.dto.GeneratedVideoRecording;
import org.aeis.videorecording.dto.VideoDTO;
import org.aeis.videorecording.entity.VideoRecording;
import org.springframework.stereotype.Component;

@Component
public class VideoRecordingMapper {



   public VideoRecording mapToEntity(VideoDTO VideoDTO) {
        return VideoRecording.builder()
                .title(VideoDTO.getTitle())
                .courseId(VideoDTO.getCourseId())
                .content(VideoDTO.getContent())
                .build();
    }


    public VideoDTO mapToDTO(VideoRecording videoRecording) {
        return VideoDTO.builder()
                .title(videoRecording.getTitle())
                .courseId(videoRecording.getCourseId())
                .content(videoRecording.getContent())
                .build();
    }


    public GeneratedVideoRecording mapToGeneratedDTO(VideoRecording videoRecording) {
        return GeneratedVideoRecording.builder()
                .id(videoRecording.getId())
                .title(videoRecording.getTitle())
                .courseId(videoRecording.getCourseId())
                .content(videoRecording.getContent())
                .build();
    }




}
