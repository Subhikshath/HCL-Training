
package CanteenBooking.com.inventory.repository;

import CanteenBooking.com.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByMenuItemId(Long menuItemId);

    List<Inventory> findByMenuItemDailyMenuCanteenId(Long canteenId);
}
