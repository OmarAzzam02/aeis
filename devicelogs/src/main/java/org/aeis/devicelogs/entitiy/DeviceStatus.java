package org.aeis.devicelogs.entitiy;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device_status")
public class DeviceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_status_seq")
    @SequenceGenerator(name = "device_status_seq", sequenceName = "device_status_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "recording")
    private int isRecording;

    @Column(name = "status_time")
    private Date timeStamp;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;


}
