package za.co.carhire.domain.reservation;
/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 11/05/2025
 * */
import jakarta.persistence.*;
import za.co.carhire.domain.vehicle.Car;

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
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost")// e.g., Oil change, Tire rotation
    private double cost;
    @Column(name = "mechanic_name", nullable = false)
    private String mechanicName;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car; // Association with Car entity

    public Maintenance() {
    }

    public Maintenance(Builder builder) {
        this.maintenanceID = builder.maintenanceID;
        this.maintenanceDate = builder.maintenanceDate;
        this.description = builder.description;
        this.cost = builder.cost;
        this.mechanicName = builder.mechanicName;
        this.car = builder.car;
    }


    public int getMaintenanceID() {
        return maintenanceID;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceID=" + maintenanceID +
                ", maintenanceDate=" + maintenanceDate +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", mechanicName='" + mechanicName + '\'' +
                ", car=" + car +
                '}';
    }

    public static class Builder {
        private int maintenanceID;
        private Date maintenanceDate;
        private String description;
        private double cost;
        private String mechanicName;
        private Car car;

        public Builder setMaintenanceID(int maintenanceID) {
            this.maintenanceID = maintenanceID;
            return this;
        }

        public Builder setMaintenanceDate(Date maintenanceDate) {
            this.maintenanceDate = maintenanceDate;
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

        public Builder setMechanicName(String mechanicName) {
            this.mechanicName = mechanicName;
            return this;
        }

        public Builder setCar(Car car) {
            this.car = car;
            return this;
        }

        public Builder copy(Maintenance maintenance) {
            this.maintenanceID = maintenance.maintenanceID;
            this.maintenanceDate = maintenance.maintenanceDate;
            this.description = maintenance.description;
            this.cost = maintenance.cost;
            this.mechanicName = maintenance.mechanicName;
            this.car = maintenance.car;
            return this;
        }

        public Maintenance build() {
            return new Maintenance(this);
        }
    }
}
