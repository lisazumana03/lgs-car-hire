package za.co.carhire.domain.reservation;
/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 11/05/2025
 * Updated: 16/10/2025 - Fixed relationship and added proper maintenance fields
 * */
import jakarta.persistence.*;
import za.co.carhire.domain.vehicle.Car;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "Maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maintenanceID;

    @Column(name = "maintenance_date", nullable = false)
    private Date maintenanceDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    private MaintenanceType serviceType;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "mileage_at_service", nullable = false)
    private int mileageAtService;

    @Column(name = "next_service_date")
    private Date nextServiceDate;

    @Column(name = "next_service_mileage")
    private Integer nextServiceMileage;

    @Column(name = "mechanic_name", nullable = false)
    private String mechanicName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MaintenanceStatus status;

    @Column(name = "notes", length = 1000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnoreProperties({"reviews", "maintenanceRecords", "imageData"})
    private Car car; // Association with Car entity - One car can have many maintenance records

    public Maintenance() {
    }

    public Maintenance(Builder builder) {
        this.maintenanceID = builder.maintenanceID;
        this.maintenanceDate = builder.maintenanceDate;
        this.serviceType = builder.serviceType;
        this.description = builder.description;
        this.cost = builder.cost;
        this.mileageAtService = builder.mileageAtService;
        this.nextServiceDate = builder.nextServiceDate;
        this.nextServiceMileage = builder.nextServiceMileage;
        this.mechanicName = builder.mechanicName;
        this.status = builder.status;
        this.notes = builder.notes;
        this.car = builder.car;
    }

    public int getMaintenanceID() {
        return maintenanceID;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public MaintenanceType getServiceType() {
        return serviceType;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public int getMileageAtService() {
        return mileageAtService;
    }

    public Date getNextServiceDate() {
        return nextServiceDate;
    }

    public Integer getNextServiceMileage() {
        return nextServiceMileage;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public void setServiceType(MaintenanceType serviceType) {
        this.serviceType = serviceType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setMileageAtService(int mileageAtService) {
        this.mileageAtService = mileageAtService;
    }

    public void setNextServiceDate(Date nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }

    public void setNextServiceMileage(Integer nextServiceMileage) {
        this.nextServiceMileage = nextServiceMileage;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public void setStatus(MaintenanceStatus status) {
        this.status = status;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceID=" + maintenanceID +
                ", maintenanceDate=" + maintenanceDate +
                ", serviceType=" + serviceType +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", mileageAtService=" + mileageAtService +
                ", nextServiceDate=" + nextServiceDate +
                ", nextServiceMileage=" + nextServiceMileage +
                ", mechanicName='" + mechanicName + '\'' +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                ", car=" + (car != null ? car.getCarID() : "null") +
                '}';
    }

    public static class Builder {
        private int maintenanceID;
        private Date maintenanceDate;
        private MaintenanceType serviceType;
        private String description;
        private double cost;
        private int mileageAtService;
        private Date nextServiceDate;
        private Integer nextServiceMileage;
        private String mechanicName;
        private MaintenanceStatus status;
        private String notes;
        private Car car;

        public Builder setMaintenanceID(int maintenanceID) {
            this.maintenanceID = maintenanceID;
            return this;
        }

        public Builder setMaintenanceDate(Date maintenanceDate) {
            this.maintenanceDate = maintenanceDate;
            return this;
        }

        public Builder setServiceType(MaintenanceType serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCost(double cost) {
            this.cost = cost;
            return this;
        }

        public Builder setMileageAtService(int mileageAtService) {
            this.mileageAtService = mileageAtService;
            return this;
        }

        public Builder setNextServiceDate(Date nextServiceDate) {
            this.nextServiceDate = nextServiceDate;
            return this;
        }

        public Builder setNextServiceMileage(Integer nextServiceMileage) {
            this.nextServiceMileage = nextServiceMileage;
            return this;
        }

        public Builder setMechanicName(String mechanicName) {
            this.mechanicName = mechanicName;
            return this;
        }

        public Builder setStatus(MaintenanceStatus status) {
            this.status = status;
            return this;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder setCar(Car car) {
            this.car = car;
            return this;
        }

        public Builder copy(Maintenance maintenance) {
            this.maintenanceID = maintenance.maintenanceID;
            this.maintenanceDate = maintenance.maintenanceDate;
            this.serviceType = maintenance.serviceType;
            this.description = maintenance.description;
            this.cost = maintenance.cost;
            this.mileageAtService = maintenance.mileageAtService;
            this.nextServiceDate = maintenance.nextServiceDate;
            this.nextServiceMileage = maintenance.nextServiceMileage;
            this.mechanicName = maintenance.mechanicName;
            this.status = maintenance.status;
            this.notes = maintenance.notes;
            this.car = maintenance.car;
            return this;
        }

        public Maintenance build() {
            return new Maintenance(this);
        }
    }
}
