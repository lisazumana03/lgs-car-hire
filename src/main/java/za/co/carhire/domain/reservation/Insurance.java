package za.co.carhire.domain.reservation;

/*
Sibulele Gift Nohamba
220374686
Date: 10/05/2025

Imtiyaaz Waggie 219374759
- Added setter methods and copy builder functionality
 */

import jakarta.persistence.*;
import za.co.carhire.domain.vehicle.Car;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "insurance")
public class Insurance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "insurance_id")
    private int insuranceID;

    @Column(name = "insurance_start_date")
    @Temporal(TemporalType.DATE)
    private Date insuranceStartDate;

    @Column(name = "insurance_cost")
    private double insuranceCost;

    @Column(name = "insurance_provider")
    private String insuranceProvider;

    @Column(name = "status")
    private String status;

    @Column(name = "policy_number")
    private long policyNumber;

    @Column(name = "mechanic")
    private String mechanic;

    @OneToOne(mappedBy = "insurance", fetch = FetchType.LAZY)
    private Car car;

    public Insurance() {}

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

    public int addInsurance(int insuranceID, Date serviceDate, String description,
                            double cost, String mechanic, String status) {
        this.insuranceID = insuranceID;
        this.insuranceStartDate = serviceDate;
        this.insuranceCost = cost;
        this.mechanic = mechanic;
        this.status = status;
        return insuranceID;
    }

    public void updateInsurance(int insuranceID, String status) {
        if (this.insuranceID == insuranceID) {
            this.status = status;
        }
    }

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(this.status);
    }

    public boolean isCancelled() {
        return "CANCELLED".equalsIgnoreCase(this.status);
    }

    public boolean isExpired() {
        return "EXPIRED".equalsIgnoreCase(this.status);
    }

    public int getInsuranceID() {
        return insuranceID;
    }

    public Date getInsuranceStartDate() {
        return insuranceStartDate;
    }

    public double getInsuranceCost() {
        return insuranceCost;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public String getStatus() {
        return status;
    }

    public long getPolicyNumber() {
        return policyNumber;
    }

    public String getMechanic() {
        return mechanic;
    }

    public Car getCar() {
        return car;
    }

    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }

    public void setInsuranceStartDate(Date insuranceStartDate) {
        this.insuranceStartDate = insuranceStartDate;
    }

    public void setInsuranceCost(double insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPolicyNumber(long policyNumber) {
        this.policyNumber = policyNumber;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public void setCar(Car car) {
        this.car = car;
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
                ", car=" + (car != null ? car.getCarID() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Insurance insurance = (Insurance) o;
        return insuranceID == insurance.insuranceID;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(insuranceID);
    }

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
            if (insurance == null) {
                throw new IllegalArgumentException("Insurance to copy cannot be null");
            }
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