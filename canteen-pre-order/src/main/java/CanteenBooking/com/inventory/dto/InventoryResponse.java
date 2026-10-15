
package CanteenBooking.com.inventory.dto;

import java.time.LocalDateTime;

public class InventoryResponse {

    private Long inventoryId;
    private Long menuItemId;
    private String itemName;
    private Long canteenId;
    private int availableQuantity;
    private String note;
    private LocalDateTime updatedAt;

    public InventoryResponse(
            Long inventoryId,
            Long menuItemId,
            String itemName,
            Long canteenId,
            int availableQuantity,
            String note,
            LocalDateTime updatedAt) {

        this.inventoryId = inventoryId;
        this.menuItemId = menuItemId;
        this.itemName = itemName;
        this.canteenId = canteenId;
        this.availableQuantity = availableQuantity;
        this.note = note;
        this.updatedAt = updatedAt;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Long getCanteenId() {
        return canteenId;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
