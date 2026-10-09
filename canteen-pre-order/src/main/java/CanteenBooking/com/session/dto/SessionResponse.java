package CanteenBooking.com.session.dto;

import CanteenBooking.com.session.entity.SessionType;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessionResponse {

    private Long id;
    private Long canteenId;
    private LocalDate sessionDate;
    private SessionType sessionType;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean active;

    public SessionResponse() {
    }

    public SessionResponse(
            Long id,
            Long canteenId,
            LocalDate sessionDate,
            SessionType sessionType,
            LocalTime startTime,
            LocalTime endTime,
            boolean active
    ) {
        this.id = id;
        this.canteenId = canteenId;
        this.sessionDate = sessionDate;
        this.sessionType = sessionType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Long getCanteenId() {
        return canteenId;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCanteenId(Long canteenId) {
        this.canteenId = canteenId;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}