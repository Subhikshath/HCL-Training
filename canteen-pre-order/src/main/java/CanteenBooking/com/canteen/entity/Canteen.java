package CanteenBooking.com.canteen.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "canteens")
public class Canteen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length = 200)
    private String location;

    // Primary canteen staff/contact details
    @Column(name = "staff_name", nullable = false)
    private String staffName;

    @Column(name = "staff_phone", nullable = false, length = 15)
    private String staffPhone;

    @Column(name = "staff_email", nullable = false, unique = true)
    private String staffEmail;

    // Contract details
    @Column(name = "contract_start_date", nullable = false)
    private LocalDate contractStartDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_status", nullable = false)
    private ContractStatus contractStatus = ContractStatus.ACTIVE;

    @Column(name = "contract_end_date")
    private LocalDate contractEndDate;

    @Column(name = "contract_withdrawal_reason", length = 500)
    private String contractWithdrawalReason;

    public Canteen() {
    }

    public Canteen(
            String name,
            String description,
            String location,
            String staffName,
            String staffPhone,
            String staffEmail,
            LocalDate contractStartDate,
            ContractStatus contractStatus,
            LocalDate contractEndDate,
            String contractWithdrawalReason
    ) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.staffName = staffName;
        this.staffPhone = staffPhone;
        this.staffEmail = staffEmail;
        this.contractStartDate = contractStartDate;
        this.contractStatus = contractStatus;
        this.contractEndDate = contractEndDate;
        this.contractWithdrawalReason = contractWithdrawalReason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getContractWithdrawalReason() {
        return contractWithdrawalReason;
    }

    public void setContractWithdrawalReason(String contractWithdrawalReason) {
        this.contractWithdrawalReason = contractWithdrawalReason;
    }
}