package CanteenBooking.com.canteen.repository;

import CanteenBooking.com.canteen.entity.CanteenOperatingDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CanteenOperatingDayRepository
        extends JpaRepository<CanteenOperatingDay, Long> {

    Optional<CanteenOperatingDay> findByCanteenIdAndOperatingDate(
            Long canteenId,
            LocalDate operatingDate
    );

    List<CanteenOperatingDay> findByOperatingDate(
            LocalDate operatingDate
    );
}