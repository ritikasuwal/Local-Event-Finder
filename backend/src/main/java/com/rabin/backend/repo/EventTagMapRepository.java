package com.rabin.backend.repo;

import com.rabin.backend.model.EventTagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventTagMapRepository extends JpaRepository<EventTagMap,Long> {

    @Query("select etm.eventTag.id from EventTagMap etm where etm.event.id = :eventId")
    List<Long> findTagIdsByEventId(@Param("eventId") Long eventId);
}
