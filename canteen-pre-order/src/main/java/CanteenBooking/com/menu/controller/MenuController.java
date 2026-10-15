
package CanteenBooking.com.menu.controller;

import CanteenBooking.com.menu.dto.DailyMenuRequest;
import CanteenBooking.com.menu.dto.DailyMenuResponse;
import CanteenBooking.com.menu.service.MenuService;
import CanteenBooking.com.security.StaffAuthorizationService;
import CanteenBooking.com.session.entity.SessionType;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canteens/{canteenId}/menus")
public class MenuController {

    private final MenuService menuService;
    private final StaffAuthorizationService staffAuthorizationService;

    public MenuController(
            MenuService menuService,
            StaffAuthorizationService staffAuthorizationService) {

        this.menuService = menuService;
        this.staffAuthorizationService = staffAuthorizationService;
    }

    // Create today's menu (STAFF or ADMIN)
    @PostMapping("/today")
    public DailyMenuResponse createTodayMenu(
            @PathVariable Long canteenId,
            @Valid @RequestBody DailyMenuRequest request,
            Authentication authentication) {

        boolean allowed = staffAuthorizationService
                .canManageCanteen(authentication, canteenId);

        if (!allowed) {
            throw new AccessDeniedException(
                    "You are not authorized to manage this canteen"
            );
        }

        return menuService.createTodayMenu(canteenId, request);
    }

    // Get all today's menus or filter by session type
    @GetMapping("/today")
    public List<DailyMenuResponse> getTodayMenus(
            @PathVariable Long canteenId,
            @RequestParam(required = false) SessionType sessionType) {

        if (sessionType != null) {
            return menuService.getTodayMenusBySession(
                    canteenId, sessionType);
        }

        return menuService.getTodayMenus(canteenId);
    }
}
