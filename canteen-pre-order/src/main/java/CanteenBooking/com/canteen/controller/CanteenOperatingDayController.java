package CanteenBooking.com.canteen.controller;

import CanteenBooking.com.canteen.dto.OperatingDayRequest;
import CanteenBooking.com.canteen.dto.OperatingDayResponse;
import CanteenBooking.com.canteen.service.CanteenOperatingDayService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
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
     * Staff updates today's status.
     */
    @PutMapping("/today")
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
     * Student views today's status.
     */
    @GetMapping("/today")
    public ResponseEntity<OperatingDayResponse> getTodayStatus(
            @PathVariable Long canteenId
    ) {

        return ResponseEntity.ok(
                operatingDayService.getTodayStatus(canteenId)
        );
    }
}