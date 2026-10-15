
package CanteenBooking.com.inventory.service;

import CanteenBooking.com.inventory.dto.InventoryResponse;
import CanteenBooking.com.inventory.dto.InventoryUpdateRequest;
import CanteenBooking.com.inventory.entity.Inventory;
import CanteenBooking.com.inventory.repository.InventoryRepository;
import CanteenBooking.com.menu.entity.DailyMenu;
import CanteenBooking.com.menu.entity.MenuItem;
import CanteenBooking.com.menu.repository.MenuItemRepository;
import CanteenBooking.com.security.StaffAuthorizationService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final MenuItemRepository menuItemRepository;
    private final StaffAuthorizationService staffAuthorizationService;

    public InventoryService(
            InventoryRepository inventoryRepository,
            MenuItemRepository menuItemRepository,
            StaffAuthorizationService staffAuthorizationService) {
        this.inventoryRepository = inventoryRepository;
        this.menuItemRepository = menuItemRepository;
        this.staffAuthorizationService = staffAuthorizationService;
    }

    @Transactional
    public InventoryResponse updateQuantity(
            Long canteenId,
            Long menuItemId,
            InventoryUpdateRequest request,
            org.springframework.security.core.Authentication authentication) {

        if (request.getQuantity() == null || request.getQuantity() < 0) {
            throw new IllegalArgumentException(
                    "Quantity cannot be null or negative");
        }

        if (!staffAuthorizationService.canManageCanteen(
                authentication, canteenId)) {
            throw new AccessDeniedException(
                    "You are not authorized to manage this canteen");
        }

        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Menu item not found"));

        DailyMenu menu = item.getDailyMenu();

        if (!menu.getCanteen().getId().equals(canteenId)) {
            throw new AccessDeniedException(
                    "This menu item does not belong to this canteen");
        }

        if (!menu.getMenuDate().equals(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Inventory can only be updated for today's menu");
        }

        if (!menu.isActive()) {
            throw new IllegalArgumentException(
                    "Cannot update stock for an inactive menu");
        }

        Inventory inventory = inventoryRepository
                .findByMenuItemId(menuItemId)
                .orElseGet(() -> {
                    Inventory newInventory = new Inventory();
                    newInventory.setMenuItem(item);
                    return newInventory;
                });

        inventory.setAvailableQuantity(request.getQuantity());
        inventory.setNote(request.getNote());

        Inventory saved = inventoryRepository.save(inventory);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> getCanteenInventory(Long canteenId) {

        return inventoryRepository
                .findByMenuItemDailyMenuCanteenId(canteenId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private InventoryResponse toResponse(Inventory inventory) {

        MenuItem item = inventory.getMenuItem();

        return new InventoryResponse(
                inventory.getId(),
                item.getId(),
                item.getName(),
                item.getDailyMenu().getCanteen().getId(),
                inventory.getAvailableQuantity(),
                inventory.getNote(),
                inventory.getUpdatedAt()
        );
    }
}
