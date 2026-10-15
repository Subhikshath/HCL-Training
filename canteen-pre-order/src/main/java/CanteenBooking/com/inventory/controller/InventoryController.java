
package CanteenBooking.com.inventory.controller;

import CanteenBooking.com.inventory.dto.InventoryResponse;
import CanteenBooking.com.inventory.dto.InventoryUpdateRequest;
import CanteenBooking.com.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canteens/{canteenId}/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PutMapping("/menu-items/{menuItemId}")
    public InventoryResponse updateQuantity(
            @PathVariable Long canteenId,
            @PathVariable Long menuItemId,
            @Valid @RequestBody InventoryUpdateRequest request,
            Authentication authentication) {

        return inventoryService.updateQuantity(
                canteenId,
                menuItemId,
                request,
                authentication
        );
    }

    @GetMapping
    public List<InventoryResponse> getCanteenInventory(
            @PathVariable Long canteenId) {

        return inventoryService.getCanteenInventory(canteenId);
    }
}
