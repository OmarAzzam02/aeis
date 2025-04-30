package org.aeis.reader.cache.video;


import org.aeis.reader.dto.video.GeneratedVideoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeneratedVideoCache {


    @Autowired
    private List<GeneratedVideoDTO> videoRecordings;


    public void addVideoRecording(GeneratedVideoDTO videoRecording) {
        videoRecordings.add(videoRecording);
    }

    public List<GeneratedVideoDTO> getAllVideoRecordings() {
        return videoRecordings;
    }

    public GeneratedVideoDTO getLastVideoRecording() {
        if (videoRecordings.isEmpty()) {
            return null;
        }
        return videoRecordings.get(videoRecordings.size() - 1);
    }


}
