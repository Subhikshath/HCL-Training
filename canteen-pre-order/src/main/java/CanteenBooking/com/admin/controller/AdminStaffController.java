package CanteenBooking.com.admin.controller;

import CanteenBooking.com.admin.dto.CreateStaffRequest;
import CanteenBooking.com.admin.service.AdminStaffService;
import CanteenBooking.com.user.entity.User;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/staff")
public class AdminStaffController {

    private final AdminStaffService adminStaffService;

    public AdminStaffController(AdminStaffService adminStaffService) {
        this.adminStaffService = adminStaffService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createStaff(
            @Valid @RequestBody CreateStaffRequest request
    ) {

        User staff = adminStaffService.createStaff(request);

        return ResponseEntity.ok(
                "Staff created successfully. Staff ID: "
                        + staff.getId()
        );
    }
}