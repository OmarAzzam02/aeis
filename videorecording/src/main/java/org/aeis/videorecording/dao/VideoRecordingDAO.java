package org.aeis.videorecording.dao;


import org.aeis.videorecording.entity.VideoRecording;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRecordingDAO extends JpaRepository<VideoRecording, Long> {


}
