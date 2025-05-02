package org.aeis.devicelogs.entitiy;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device")
@NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d")
public class Device {


    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "hall")
    private String hall;


    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DeviceStatus> statuses;

}



