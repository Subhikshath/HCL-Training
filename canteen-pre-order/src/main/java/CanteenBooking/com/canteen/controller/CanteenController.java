package CanteenBooking.com.canteen.controller;


import CanteenBooking.com.canteen.dto.CanteenRequest;
import CanteenBooking.com.canteen.dto.CanteenResponse;
import CanteenBooking.com.canteen.service.CanteenService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/canteens")
public class CanteenController {

    private final CanteenService canteenService;

    public CanteenController(CanteenService canteenService) {
        this.canteenService = canteenService;
    }

    @PostMapping
    public ResponseEntity<CanteenResponse> createCanteen(
            @Valid @RequestBody CanteenRequest request
    ) {

        CanteenResponse response =
                canteenService.createCanteen(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CanteenResponse>> getAllCanteens() {

        return ResponseEntity.ok(
                canteenService.getAllCanteens()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanteenResponse> getCanteenById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                canteenService.getCanteenById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CanteenResponse> updateCanteen(
            @PathVariable Long id,
            @Valid @RequestBody CanteenRequest request
    ) {

        return ResponseEntity.ok(
                canteenService.updateCanteen(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> withdrawCanteen(
            @PathVariable Long id,
            @RequestParam String reason
    ) {

        canteenService.withdrawCanteen(id, reason);

        return ResponseEntity.noContent().build();
    }
}