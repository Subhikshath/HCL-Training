package CanteenBooking.com.canteen.entity;

import CanteenBooking.com.session.entity.OperatingMode;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "canteen_operating_days",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"canteen_id", "operating_date"}
                )
        }
)
public class CanteenOperatingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "canteen_id", nullable = false)
    private Canteen canteen;

    @Column(name = "operating_date", nullable = false)
    private LocalDate operatingDate;

    @Column(nullable = false)
    private boolean open;

    @Enumerated(EnumType.STRING)
    @Column(name = "operating_mode")
    private OperatingMode operatingMode;

    @Column(length = 255)
    private String note;

    public CanteenOperatingDay() {
    }

    public CanteenOperatingDay(
            Canteen canteen,
            LocalDate operatingDate,
            boolean open,
            OperatingMode operatingMode,
            String note
    ) {
        this.canteen = canteen;
        this.operatingDate = operatingDate;
        this.open = open;
        this.operatingMode = operatingMode;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public Canteen getCanteen() {
        return canteen;
    }

    public void setCanteen(Canteen canteen) {
        this.canteen = canteen;
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