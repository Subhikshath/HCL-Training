
package CanteenBooking.com.menu.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class DailyMenuRequest {

    // Null for FULL_DAY; required for SESSION_BASED.
    private Long sessionId;

    @NotEmpty(message = "At least one menu item is required")
    @Valid
    private List<MenuItemRequest> items = new ArrayList<>();

    public DailyMenuRequest() {
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public List<MenuItemRequest> getItems() {
        return items;
    }

    public void setItems(List<MenuItemRequest> items) {
        this.items = items;
    }
}
