package CanteenBooking.com.session.controller;

import CanteenBooking.com.session.dto.SessionRequest;
import CanteenBooking.com.session.dto.SessionResponse;
import CanteenBooking.com.session.service.CanteenSessionService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canteens/{canteenId}/sessions")
public class CanteenSessionController {

    private final CanteenSessionService sessionService;

    public CanteenSessionController(
            CanteenSessionService sessionService
    ) {
        this.sessionService = sessionService;
    }

    /*
     * ADMIN can create a session for any canteen.
     *
     * STAFF can create a session only
     * for their assigned canteen.
     */
    @PostMapping("/today")
    @PreAuthorize(
            "@staffAuthorizationService.canManageCanteen(authentication, #canteenId)"
    )
    public ResponseEntity<SessionResponse> createTodaySession(
            @PathVariable Long canteenId,
            @Valid @RequestBody SessionRequest request
    ) {

        return ResponseEntity.ok(
                sessionService.createTodaySession(
                        canteenId,
                        request
                )
        );
    }

    /*
     * STUDENT, STAFF and ADMIN can view today's sessions.
     */
    @GetMapping("/today")
    @PreAuthorize(
            "hasAnyRole('STUDENT', 'STAFF', 'ADMIN')"
    )
    public ResponseEntity<List<SessionResponse>> getTodaySessions(
            @PathVariable Long canteenId
    ) {

        return ResponseEntity.ok(
                sessionService.getTodaySessions(canteenId)
        );
    }

    /*
     * ADMIN can update any canteen session.
     *
     * STAFF can update only their assigned canteen session.
     */
    @PutMapping("/today/{sessionId}")
    @PreAuthorize(
            "@staffAuthorizationService.canManageCanteen(authentication, #canteenId)"
    )
    public ResponseEntity<SessionResponse> updateTodaySession(
            @PathVariable Long canteenId,
            @PathVariable Long sessionId,
            @Valid @RequestBody SessionRequest request
    ) {

        return ResponseEntity.ok(
                sessionService.updateTodaySession(
                        canteenId,
                        sessionId,
                        request
                )
        );
    }

    /*
     * ADMIN can deactivate any canteen session.
     *
     * STAFF can deactivate only their assigned canteen session.
     */
    @DeleteMapping("/today/{sessionId}")
    @PreAuthorize(
            "@staffAuthorizationService.canManageCanteen(authentication, #canteenId)"
    )
    public ResponseEntity<Void> deactivateTodaySession(
            @PathVariable Long canteenId,
            @PathVariable Long sessionId
    ) {

        sessionService.deactivateTodaySession(
                canteenId,
                sessionId
        );

        return ResponseEntity.noContent().build();
    }
}