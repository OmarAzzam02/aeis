package org.aeis.tts.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
public class AudioResponseDTO {

     @JsonProperty("frame_id")
     private Long frameId;
     @JsonProperty("description")
     private String description;
     @JsonProperty("audio_file")
     private MultipartFile audioFile;


}
