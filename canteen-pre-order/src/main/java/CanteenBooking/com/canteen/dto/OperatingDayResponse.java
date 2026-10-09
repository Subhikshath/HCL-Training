package CanteenBooking.com.canteen.dto;

import CanteenBooking.com.session.entity.OperatingMode;

import java.time.LocalDate;

public class OperatingDayResponse {

    private Long id;
    private Long canteenId;
    private LocalDate operatingDate;
    private boolean open;
    private OperatingMode operatingMode;
    private String note;

    public OperatingDayResponse() {
    }

    public OperatingDayResponse(
            Long id,
            Long canteenId,
            LocalDate operatingDate,
            boolean open,
            OperatingMode operatingMode,
            String note
    ) {
        this.id = id;
        this.canteenId = canteenId;
        this.operatingDate = operatingDate;
        this.open = open;
        this.operatingMode = operatingMode;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(Long canteenId) {
        this.canteenId = canteenId;
    }

    public LocalDate getOperatingDate() {
        return operatingDate;
    }

    public void setOperatingDate(LocalDate operatingDate) {
        this.operatingDate = operatingDate;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public OperatingMode getOperatingMode() {
        return operatingMode;
    }

    public void setOperatingMode(OperatingMode operatingMode) {
        this.operatingMode = operatingMode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}