
package CanteenBooking.com.menu.repository;

import CanteenBooking.com.menu.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository
        extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByDailyMenuId(Long dailyMenuId);

    List<MenuItem> findByDailyMenuIdAndAvailableTrue(Long dailyMenuId);
}
