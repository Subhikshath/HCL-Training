package CanteenBooking.com.canteen.dto;

import CanteenBooking.com.canteen.entity.ContractStatus;

import java.time.LocalDate;

public class CanteenResponse {

    private Long id;
    private String name;
    private String description;
    private String location;

    private String staffName;
    private String staffPhone;
    private String staffEmail;

    private LocalDate contractStartDate;
    private ContractStatus contractStatus;
    private LocalDate contractEndDate;
    private String contractWithdrawalReason;

    public CanteenResponse() {
    }

    public CanteenResponse(
            Long id,
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
        this.id = id;
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