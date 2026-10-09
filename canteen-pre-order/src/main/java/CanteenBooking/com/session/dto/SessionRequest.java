package CanteenBooking.com.session.dto;

import CanteenBooking.com.session.entity.SessionType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class SessionRequest {

    @NotNull
    private SessionType sessionType;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    public SessionRequest() {
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}