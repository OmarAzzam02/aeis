package org.aeis.summary.dao;

import org.aeis.summary.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SummaryDAO extends JpaRepository<Summary, Long> {
    // Define any custom query methods here if needed
}
