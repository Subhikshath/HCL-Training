package CanteenBooking.com.canteen.repository;


import CanteenBooking.com.canteen.entity.Canteen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CanteenRepository extends JpaRepository<Canteen, Long> {

    Optional<Canteen> findByName(String name);

    boolean existsByName(String name);
}