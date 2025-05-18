package za.co.carhire.domain;

/*
Sibulele Gift Nohamba
220374686
Date: 10/05/2025
 */

import za.co.carhire.domain.vehicle.Car;

import java.util.Date;

public class Insurance {
    // Primary attributes
    private int insuranceID;
    private Date insuranceStartDate;
    private double insuranceCost;
    private String insuranceProvider;
    private String status;
    private long policyNumber;
    private String mechanic;

    // Relationship with Car
    private Car car;

    // Default constructor
    public Insurance() {}

    // Private constructor for Builder pattern
    private Insurance(Builder builder) {
        this.insuranceID = builder.insuranceID;
        this.insuranceStartDate = builder.insuranceStartDate;
        this.insuranceCost = builder.insuranceCost;
        this.insuranceProvider = builder.insuranceProvider;
        this.policyNumber = builder.policyNumber;
        this.status = builder.status;
        this.mechanic = builder.mechanic;
        this.car = builder.car;
    }

    // Methods from diagram
    public int addInsurance(int insuranceID, Date serviceDate, String description,
                            double cost, String mechanic, String status) {
        // Implementation to add insurance
        return insuranceID;
    }

    public void updateInsurance(int insuranceID, String status) {
        // Implementation to update insurance status
        if (this.insuranceID == insuranceID) {
            this.status = status;
        }
    }

    // Getters
    public int getInsuranceID() { return insuranceID; }
    public Date getInsuranceStartDate() { return insuranceStartDate; }
    public double getInsuranceCost() { return insuranceCost; }
    public String getInsuranceProvider() { return insuranceProvider; }
    public String getStatus() { return status; }
    public long getPolicyNumber() { return policyNumber; }
    public String getMechanic() { return mechanic; }
    public Car getCar() { return car; }

    @Override
    public String toString() {
        return "Insurance{" +
                "insuranceID=" + insuranceID +
                ", insuranceStartDate=" + insuranceStartDate +
                ", insuranceCost=" + insuranceCost +
                ", insuranceProvider='" + insuranceProvider + '\'' +
                ", status='" + status + '\'' +
                ", policyNumber=" + policyNumber +
                ", mechanic='" + mechanic + '\'' +
                ", car=" + (car != null ? car.getCarID() : "null") +
                '}';
    }

    // Builder class
    public static class Builder {
        private int insuranceID;
        private Date insuranceStartDate;
        private double insuranceCost;
        private String insuranceProvider;
        private String status;
        private long policyNumber;
        private String mechanic;
        private Car car;

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

        public Builder setCar(Car car) {
            this.car = car;
            return this;
        }

        public Builder copy(Insurance insurance) {
            this.insuranceID = insurance.getInsuranceID();
            this.insuranceStartDate = insurance.getInsuranceStartDate();
            this.insuranceCost = insurance.getInsuranceCost();
            this.insuranceProvider = insurance.getInsuranceProvider();
            this.status = insurance.getStatus();
            this.policyNumber = insurance.getPolicyNumber();
            this.mechanic = insurance.getMechanic();
            this.car = insurance.getCar();
            return this;
        }

        public Insurance build() {
            return new Insurance(this);
        }
    }
}
