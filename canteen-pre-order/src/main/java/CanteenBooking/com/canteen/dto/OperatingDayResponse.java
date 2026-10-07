package CanteenBooking.com.canteen.dto;

import java.time.LocalDate;

public class OperatingDayResponse {

    private Long id;
    private Long canteenId;
    private LocalDate operatingDate;
    private boolean open;
    private String note;

    public OperatingDayResponse() {
    }

    public OperatingDayResponse(
            Long id,
            Long canteenId,
            LocalDate operatingDate,
            boolean open,
            String note
    ) {
        this.id = id;
        this.canteenId = canteenId;
        this.operatingDate = operatingDate;
        this.open = open;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}