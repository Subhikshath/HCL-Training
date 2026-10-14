
package CanteenBooking.com.menu.dto;

import java.time.LocalDate;
import java.util.List;

public class DailyMenuResponse {

    private Long id;
    private Long canteenId;
    private LocalDate menuDate;
    private Long sessionId;
    private boolean active;
    private List<MenuItemResponse> items;

    public DailyMenuResponse(
            Long id,
            Long canteenId,
            LocalDate menuDate,
            Long sessionId,
            boolean active,
            List<MenuItemResponse> items) {
        this.id = id;
        this.canteenId = canteenId;
        this.menuDate = menuDate;
        this.sessionId = sessionId;
        this.active = active;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Long getCanteenId() {
        return canteenId;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public boolean isActive() {
        return active;
    }

    public List<MenuItemResponse> getItems() {
        return items;
    }
}
