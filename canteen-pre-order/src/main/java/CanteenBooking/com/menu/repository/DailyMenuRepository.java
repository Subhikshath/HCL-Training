
package CanteenBooking.com.menu.repository;

import CanteenBooking.com.menu.entity.DailyMenu;
import CanteenBooking.com.session.entity.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyMenuRepository
        extends JpaRepository<DailyMenu, Long> {

    List<DailyMenu> findByCanteenIdAndMenuDateAndActiveTrue(
            Long canteenId, LocalDate menuDate);

    Optional<DailyMenu> findByCanteenIdAndMenuDateAndSessionId(
            Long canteenId, LocalDate menuDate, Long sessionId);

    boolean existsByCanteenIdAndMenuDateAndSessionId(
            Long canteenId, LocalDate menuDate, Long sessionId);

    List<DailyMenu> findByCanteenIdAndMenuDateAndSessionSessionTypeAndActiveTrue(
            Long canteenId,
            LocalDate menuDate,
            SessionType sessionType
    );

    Optional<DailyMenu> findByCanteenIdAndMenuDateAndSessionIsNull(
            Long canteenId, LocalDate menuDate);

    boolean existsByCanteenIdAndMenuDateAndSessionIsNull(
            Long canteenId, LocalDate menuDate);
}
