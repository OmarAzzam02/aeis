package org.aeis.reader.dto.device;

import lombok.*;

import java.util.Date;



@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusInfo {
    private String status;
    private int isRecording;
    private Date timeStamp;
}
    