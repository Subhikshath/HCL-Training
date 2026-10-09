package CanteenBooking.com.canteen.controller;

import CanteenBooking.com.canteen.dto.OperatingDayRequest;
import CanteenBooking.com.canteen.dto.OperatingDayResponse;
import CanteenBooking.com.canteen.service.CanteenOperatingDayService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/canteens/{canteenId}/operating-days")
public class CanteenOperatingDayController {

    private final CanteenOperatingDayService operatingDayService;

    public CanteenOperatingDayController(
            CanteenOperatingDayService operatingDayService
    ) {
        this.operatingDayService = operatingDayService;
    }

    /*
     * STAFF can update today's canteen status
     * only if the canteen is assigned to that staff.
     *
     * ADMIN can update any canteen.
     */
    @PutMapping("/today")
    @PreAuthorize(
            "@staffAuthorizationService.canManageCanteen(authentication, #canteenId)"
    )
    public ResponseEntity<OperatingDayResponse> updateTodayStatus(
            @PathVariable Long canteenId,
            @Valid @RequestBody OperatingDayRequest request
    ) {

        OperatingDayResponse response =
                operatingDayService.updateTodayStatus(
                        canteenId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    /*
     * STUDENT can view today's canteen status.
     *
     * ADMIN and STAFF are also allowed to view it.
     */
    @GetMapping("/today")
    @PreAuthorize("hasAnyRole('STUDENT', 'STAFF', 'ADMIN')")
    public ResponseEntity<OperatingDayResponse> getTodayStatus(
            @PathVariable Long canteenId
    ) {

        return ResponseEntity.ok(
                operatingDayService.getTodayStatus(canteenId)
        );
    }
}