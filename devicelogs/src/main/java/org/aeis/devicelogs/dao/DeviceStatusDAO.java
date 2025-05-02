package org.aeis.devicelogs.dao;


import org.aeis.devicelogs.entitiy.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceStatusDAO  extends JpaRepository<DeviceStatus, Long> {


}
