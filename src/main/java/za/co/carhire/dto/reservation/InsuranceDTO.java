package za.co.carhire.dto.reservation;

import java.util.Date;

/**
 * InsuranceDTO
 * Data Transfer Object for Insurance entity
 * Used for API requests/responses
 * Author: System Generated
 * Date: 2025-10-07
 */
public class InsuranceDTO {
    private Integer insuranceID;
    private Date insuranceStartDate;
    private Double insuranceCost;
    private String insuranceProvider;
    private String status;
    private Long policyNumber;
    private String mechanic;
    private Integer carID;  // Reference to the Car (if needed)

    public InsuranceDTO() {
    }

    public InsuranceDTO(Integer insuranceID, Date insuranceStartDate, Double insuranceCost,
                        String insuranceProvider, String status, Long policyNumber,
                        String mechanic, Integer carID) {
        this.insuranceID = insuranceID;
        this.insuranceStartDate = insuranceStartDate;
        this.insuranceCost = insuranceCost;
        this.insuranceProvider = insuranceProvider;
        this.status = status;
        this.policyNumber = policyNumber;
        this.mechanic = mechanic;
        this.carID = carID;
    }

    // Getters and Setters
    public Integer getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(Integer insuranceID) {
        this.insuranceID = insuranceID;
    }

    public Date getInsuranceStartDate() {
        return insuranceStartDate;
    }

    public void setInsuranceStartDate(Date insuranceStartDate) {
        this.insuranceStartDate = insuranceStartDate;
    }

    public Double getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(Double insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(Long policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public Integer getCarID() {
        return carID;
    }

    public void setCarID(Integer carID) {
        this.carID = carID;
    }

    @Override
    public String toString() {
        return "InsuranceDTO{" +
                "insuranceID=" + insuranceID +
                ", insuranceStartDate=" + insuranceStartDate +
                ", insuranceCost=" + insuranceCost +
                ", insuranceProvider='" + insuranceProvider + '\'' +
                ", status='" + status + '\'' +
                ", policyNumber=" + policyNumber +
                ", mechanic='" + mechanic + '\'' +
                ", carID=" + carID +
                '}';
    }
}
