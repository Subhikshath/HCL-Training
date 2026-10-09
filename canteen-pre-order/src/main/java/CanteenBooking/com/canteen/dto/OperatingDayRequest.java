package CanteenBooking.com.canteen.dto;

import CanteenBooking.com.session.entity.OperatingMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OperatingDayRequest {

    @NotNull
    private Boolean open;

    private OperatingMode operatingMode;

    @Size(max = 255)
    private String note;

    public OperatingDayRequest() {
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
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