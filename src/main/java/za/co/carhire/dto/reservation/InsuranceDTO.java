package za.co.carhire.dto.reservation;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object for Insurance entity
 * Author: Imtiyaaz Waggie 219374759
 *
 */
public class InsuranceDTO implements Serializable {

    private int insuranceID;
    private Date insuranceStartDate;
    private double insuranceCost;
    private String insuranceProvider;
    private String status;
    private long policyNumber;
    private String mechanic;

    // Related entity IDs
    private Integer carID;
    private String carBrandModel; // For display purposes

    public InsuranceDTO() {
    }

    public InsuranceDTO(int insuranceID, Date insuranceStartDate, double insuranceCost,
                        String insuranceProvider, String status, long policyNumber, String mechanic) {
        this.insuranceID = insuranceID;
        this.insuranceStartDate = insuranceStartDate;
        this.insuranceCost = insuranceCost;
        this.insuranceProvider = insuranceProvider;
        this.status = status;
        this.policyNumber = policyNumber;
        this.mechanic = mechanic;
    }

    public static class Builder {
        private int insuranceID;
        private Date insuranceStartDate;
        private double insuranceCost;
        private String insuranceProvider;
        private String status;
        private long policyNumber;
        private String mechanic;
        private Integer carID;
        private String carBrandModel;

        public Builder setInsuranceID(int insuranceID) {
            this.insuranceID = insuranceID;
            return this;
        }

        public Builder setInsuranceStartDate(Date insuranceStartDate) {
            this.insuranceStartDate = insuranceStartDate;
            return this;
        }

        public Builder setInsuranceCost(double insuranceCost) {
            this.insuranceCost = insuranceCost;
            return this;
        }

        public Builder setInsuranceProvider(String insuranceProvider) {
            this.insuranceProvider = insuranceProvider;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setPolicyNumber(long policyNumber) {
            this.policyNumber = policyNumber;
            return this;
        }

        public Builder setMechanic(String mechanic) {
            this.mechanic = mechanic;
            return this;
        }

        public Builder setCarID(Integer carID) {
            this.carID = carID;
            return this;
        }

        public Builder setCarBrandModel(String carBrandModel) {
            this.carBrandModel = carBrandModel;
            return this;
        }

        public InsuranceDTO build() {
            InsuranceDTO dto = new InsuranceDTO();
            dto.insuranceID = this.insuranceID;
            dto.insuranceStartDate = this.insuranceStartDate;
            dto.insuranceCost = this.insuranceCost;
            dto.insuranceProvider = this.insuranceProvider;
            dto.status = this.status;
            dto.policyNumber = this.policyNumber;
            dto.mechanic = this.mechanic;
            dto.carID = this.carID;
            dto.carBrandModel = this.carBrandModel;
            return dto;
        }
    }

    // Getters and Setters
    public int getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }

    public Date getInsuranceStartDate() {
        return insuranceStartDate;
    }

    public void setInsuranceStartDate(Date insuranceStartDate) {
        this.insuranceStartDate = insuranceStartDate;
    }

    public double getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(double insuranceCost) {
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

    public long getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(long policyNumber) {
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

    public String getCarBrandModel() {
        return carBrandModel;
    }

    public void setCarBrandModel(String carBrandModel) {
        this.carBrandModel = carBrandModel;
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
                ", carBrandModel='" + carBrandModel + '\'' +
                '}';
    }
}