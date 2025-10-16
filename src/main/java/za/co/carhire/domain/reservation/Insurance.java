package za.co.carhire.domain.reservation;

/*
Sibulele Gift Nohamba
220374686
Date: 10/05/2025
 */

import jakarta.persistence.*;
import za.co.carhire.domain.vehicle.Car;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Insurance")
public class Insurance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int insuranceID;

    @Column(name = "insurance_start_date")
    private Date insuranceStartDate;

    @Column(name = "insurance_cost")
    private double insuranceCost;

    @Column(name = "insurance_provider")
    private String insuranceProvider;

    @Column(name = "status")
    private String status;
    // e.g., Active, Expired
    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "mechanic")
    private String mechanic;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car; // Association with Car entity

    // Default constructor
    public Insurance() {

    }

    // Constructor via Builder
    private Insurance(Builder builder) {
        this.insuranceID = builder.insuranceID;
        this.insuranceStartDate = builder.insuranceStartDate;
        this.insuranceCost = builder.insuranceCost;
        this.insuranceProvider = builder.insuranceProvider;
        this.status = builder.status;
        this.policyNumber = builder.policyNumber;
        this.mechanic = builder.mechanic;
        this.car = builder.car;
    }

    public int getInsuranceID() {
        return insuranceID;
    }

    public double getInsuranceCost() {
        return insuranceCost;
    }

    public Date getInsuranceStartDate() {
        return insuranceStartDate;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public String getStatus() {
        return status;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getMechanic() {
        return mechanic;
    }

    public Car getCar() {
        return car;
    }

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
                ", car=" + car +
                '}';
    }

    public static class Builder {
        private int insuranceID;

        private Date insuranceStartDate;

        private double insuranceCost;

        private String insuranceProvider;

        private String status;
        // e.g., Active, Expired
        private String policyNumber;

        private String mechanic;

        private Car car; // Association with Car entity

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

        public Builder setPolicyNumber(String policyNumber) {
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
            this.insuranceID = insurance.insuranceID;
            this.insuranceStartDate = insurance.insuranceStartDate;
            this.insuranceCost = insurance.insuranceCost;
            this.insuranceProvider = insurance.insuranceProvider;
            this.status = insurance.status;
            this.policyNumber = insurance.policyNumber;
            this.mechanic = insurance.mechanic;
            this.car = insurance.car;
            return this;
        }

        public Insurance build() {
            return new Insurance(this);
        }
    }
}
