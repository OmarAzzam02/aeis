package org.aeis.devicelogs.dto;

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
