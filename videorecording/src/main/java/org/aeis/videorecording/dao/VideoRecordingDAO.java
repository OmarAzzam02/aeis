package org.aeis.videorecording.dao;


import org.aeis.videorecording.entity.VideoRecording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface VideoRecordingDAO extends JpaRepository<VideoRecording, Long> {

    List<VideoRecording> findByCourseIdInAndIsShared(List<Long> courseId, int isShared);

    @Modifying
    @Transactional
    @Query("UPDATE VideoRecording v SET v.isShared = 1 WHERE v.id = :id")
     void updateSharedStatusById(Long id);




}
