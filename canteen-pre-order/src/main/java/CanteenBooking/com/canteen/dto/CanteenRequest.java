package CanteenBooking.com.canteen.dto;

import CanteenBooking.com.canteen.entity.ContractStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CanteenRequest {

    @NotBlank(message = "Canteen name is required")
    @Size(max = 100, message = "Canteen name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    @NotBlank(message = "Staff name is required")
    private String staffName;

    @NotBlank(message = "Staff phone is required")
    private String staffPhone;

    @NotBlank(message = "Staff email is required")
    @Email(message = "Invalid staff email")
    private String staffEmail;

    @NotNull(message = "Contract start date is required")
    private LocalDate contractStartDate;

    private ContractStatus contractStatus;

    private LocalDate contractEndDate;

    private String contractWithdrawalReason;

    public CanteenRequest() {
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