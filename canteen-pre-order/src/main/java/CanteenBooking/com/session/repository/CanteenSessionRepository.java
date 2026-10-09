package CanteenBooking.com.session.repository;


import CanteenBooking.com.session.entity.CanteenSession;
import CanteenBooking.com.session.entity.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CanteenSessionRepository
        extends JpaRepository<CanteenSession, Long> {

    List<CanteenSession> findByCanteenIdAndSessionDate(
            Long canteenId,
            LocalDate sessionDate
    );

    List<CanteenSession> findByCanteenIdAndSessionDateAndActiveTrue(
            Long canteenId,
            LocalDate sessionDate
    );

    Optional<CanteenSession> findByCanteenIdAndSessionDateAndSessionType(
            Long canteenId,
            LocalDate sessionDate,
            SessionType sessionType
    );

    boolean existsByCanteenIdAndSessionDateAndSessionType(
            Long canteenId,
            LocalDate sessionDate,
            SessionType sessionType
    );
}