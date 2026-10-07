package CanteenBooking.com.canteen.entity;

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

    @Column(length = 255)
    private String note;

    public CanteenOperatingDay() {
    }

    public CanteenOperatingDay(
            Canteen canteen,
            LocalDate operatingDate,
            boolean open,
            String note
    ) {
        this.canteen = canteen;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}