package za.co.carhire.domain.reservation;

/*
Sibulele Gift Nohamba
220374686
Date: 10/05/2025
Updated: 2025-10-16 - Fixed date types, removed mechanic field, added proper relationships
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import za.co.carhire.domain.vehicle.Car;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "insurance")
public class Insurance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int insuranceID;

    @Column(name = "insurance_start_date")
    private LocalDateTime insuranceStartDate;

    @Column(name = "insurance_end_date")
    private LocalDateTime insuranceEndDate;

    @Column(name = "insurance_cost")
    private double insuranceCost;

    @Column(name = "insurance_provider")
    private String insuranceProvider;

    @Column(name = "coverage_type")
    @Enumerated(EnumType.STRING)
    private CoverageType coverageType;

    @Column(name = "deductible")
    private double deductible;

    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "insurance")
    @JsonIgnore
    private List<Booking> bookings;

    // Default constructor
    public Insurance() {

    }

    // Constructor via Builder
    private Insurance(Builder builder) {
        this.insuranceID = builder.insuranceID;
        this.insuranceStartDate = builder.insuranceStartDate;
        this.insuranceEndDate = builder.insuranceEndDate;
        this.insuranceCost = builder.insuranceCost;
        this.insuranceProvider = builder.insuranceProvider;
        this.coverageType = builder.coverageType;
        this.deductible = builder.deductible;
        this.policyNumber = builder.policyNumber;
        this.isActive = builder.isActive;
        this.bookings = builder.bookings;
    }

    public int getInsuranceID() {
        return insuranceID;
    }

    public LocalDateTime getInsuranceStartDate() {
        return insuranceStartDate;
    }

    public LocalDateTime getInsuranceEndDate() {
        return insuranceEndDate;
    }

    public double getInsuranceCost() {
        return insuranceCost;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public CoverageType getCoverageType() {
        return coverageType;
    }

    public double getDeductible() {
        return deductible;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "insuranceID=" + insuranceID +
                ", insuranceStartDate=" + insuranceStartDate +
                ", insuranceEndDate=" + insuranceEndDate +
                ", insuranceCost=" + insuranceCost +
                ", insuranceProvider='" + insuranceProvider + '\'' +
                ", coverageType=" + coverageType +
                ", deductible=" + deductible +
                ", policyNumber='" + policyNumber + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    public static class Builder {
        private int insuranceID;
        private LocalDateTime insuranceStartDate;
        private LocalDateTime insuranceEndDate;
        private double insuranceCost;
        private String insuranceProvider;
        private CoverageType coverageType;
        private double deductible;
        private String policyNumber;
        private boolean isActive = true;
        private List<Booking> bookings;

        public Builder setInsuranceID(int insuranceID) {
            this.insuranceID = insuranceID;
            return this;
        }

        public Builder setInsuranceStartDate(LocalDateTime insuranceStartDate) {
            this.insuranceStartDate = insuranceStartDate;
            return this;
        }

        public Builder setInsuranceEndDate(LocalDateTime insuranceEndDate) {
            this.insuranceEndDate = insuranceEndDate;
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

        public Builder setCoverageType(CoverageType coverageType) {
            this.coverageType = coverageType;
            return this;
        }

        public Builder setDeductible(double deductible) {
            this.deductible = deductible;
            return this;
        }

        public Builder setPolicyNumber(String policyNumber) {
            this.policyNumber = policyNumber;
            return this;
        }

        public Builder setActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder setBookings(List<Booking> bookings) {
            this.bookings = bookings;
            return this;
        }

        public Builder copy(Insurance insurance) {
            this.insuranceID = insurance.insuranceID;
            this.insuranceStartDate = insurance.insuranceStartDate;
            this.insuranceEndDate = insurance.insuranceEndDate;
            this.insuranceCost = insurance.insuranceCost;
            this.insuranceProvider = insurance.insuranceProvider;
            this.coverageType = insurance.coverageType;
            this.deductible = insurance.deductible;
            this.policyNumber = insurance.policyNumber;
            this.isActive = insurance.isActive;
            this.bookings = insurance.bookings;
            return this;
        }

        public Insurance build() {
            return new Insurance(this);
        }
    }
}
