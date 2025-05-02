package org.aeis.devicelogs.dao;

import org.aeis.devicelogs.entitiy.Device;
import org.aeis.devicelogs.entitiy.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeviceDAO extends JpaRepository<Device, Long> {

//    List<Device> findAllByStatus(String status);
//    List<Device> findAllById(Long id);

    @Query("SELECT DISTINCT d FROM Device d LEFT JOIN FETCH d.statuses")
    List<Device> findAllWithStatuses();

}
