package org.aeis.videorecording.cache;


import org.aeis.videorecording.entity.VideoRecording;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoRecordingCache {


    @Autowired
    private List<VideoRecording> videoRecordings;


     public void addVideoRecording(VideoRecording videoRecording) {
        videoRecordings.add(videoRecording);
    }

    public List<VideoRecording> getVideoRecordings() {
        return videoRecordings;
    }

    public void clearCache() {
        videoRecordings.clear();
    }


    public VideoRecording getLastAddedSummary() {
        if (videoRecordings.isEmpty()) {
            return null;
        }
        return videoRecordings.get(videoRecordings.size() - 1);
    }
}
