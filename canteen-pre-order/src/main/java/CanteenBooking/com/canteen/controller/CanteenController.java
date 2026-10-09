package CanteenBooking.com.canteen.controller;

import CanteenBooking.com.canteen.dto.CanteenRequest;
import CanteenBooking.com.canteen.dto.CanteenResponse;
import CanteenBooking.com.canteen.service.CanteenService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canteens")
public class CanteenController {

    private final CanteenService canteenService;

    public CanteenController(
            CanteenService canteenService
    ) {
        this.canteenService = canteenService;
    }


    /*
     * ADMIN creates a canteen.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CanteenResponse> createCanteen(
            @Valid @RequestBody CanteenRequest request
    ) {

        CanteenResponse response =
                canteenService.createCanteen(request);

        return ResponseEntity.ok(response);
    }


    /*
     * STUDENT, STAFF and ADMIN can view all canteens.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'STAFF', 'ADMIN')")
    public ResponseEntity<List<CanteenResponse>> getAllCanteens() {

        return ResponseEntity.ok(
                canteenService.getAllCanteens()
        );
    }


    /*
     * STUDENT, STAFF and ADMIN can view a canteen.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'STAFF', 'ADMIN')")
    public ResponseEntity<CanteenResponse> getCanteenById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                canteenService.getCanteenById(id)
        );
    }


    /*
     * ADMIN updates a canteen.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CanteenResponse> updateCanteen(
            @PathVariable Long id,
            @Valid @RequestBody CanteenRequest request
    ) {

        CanteenResponse response =
                canteenService.updateCanteen(id, request);

        return ResponseEntity.ok(response);
    }


    /*
     * ADMIN withdraws a canteen.
     *
     * DELETE does not physically delete the canteen.
     * It marks the contract as WITHDRAWN.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> withdrawCanteen(
            @PathVariable Long id,
            @RequestParam String reason
    ) {

        canteenService.withdrawCanteen(
                id,
                reason
        );

        return ResponseEntity.noContent().build();
    }
}