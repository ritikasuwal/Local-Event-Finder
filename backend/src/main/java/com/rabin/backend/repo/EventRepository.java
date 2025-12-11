package com.rabin.backend.repo;

import com.rabin.backend.enums.EventStatus;
import com.rabin.backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("select e from Event e where e.eventStatus = :status")
    List<Event> findByEventStatus(EventStatus status);

    default List<Event> findActiveEvents(){
        return findByEventStatus(EventStatus.ACTIVE);
    }
}
