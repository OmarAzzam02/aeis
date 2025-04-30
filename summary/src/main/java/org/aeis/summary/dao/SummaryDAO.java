package org.aeis.summary.dao;

import org.aeis.summary.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface SummaryDAO extends JpaRepository<Summary, Long> {

    List<Summary> findByCourseIdInAndIsShared(List<Long> courseIds, int isShared);

    Optional<Summary> findById(Long id);


    @Modifying
    @Transactional
    @Query("UPDATE Summary s SET s.isShared = 1 WHERE s.id = :id")
    void updateSharedStatusById(Long id);
}
