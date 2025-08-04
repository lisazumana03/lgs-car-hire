package za.co.carhire.domain.reservation;
/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 11/05/2025
 * */
import za.co.carhire.domain.vehicle.Car;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "maintenance")
public class Maintenance implements Serializable {

    // Primary attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int maintenanceID;
    
    @Column(name = "service_date")
    private Date serviceDate;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "cost")
    private double cost;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "mechanic")
    private String mechanic;

    // Relationship with Car
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    // Default constructor
    public Maintenance() {}

     private Maintenance(Builder builder){
        this.maintenanceID = builder.maintenanceID;
        this.serviceDate = builder.serviceDate;
        this.description = builder.description;
        this.cost = builder.cost;
        this.status = builder.status;
        this.mechanic = builder.mechanic;
        this.car = builder.car;
    }

     // Methods from diagram
    public int scheduleMaintenance(int maintenanceID, Date serviceDate, String description,
                            double cost, String mechanic) {
        // Implementation to schedule maintenance
        return maintenanceID;
    }

    public void updateMaintenanceStatus(int maintenanceID, String status) {
        // Implementation to update maintenance status
        if (this.maintenanceID == maintenanceID) {
            this.status = status;
        }
    }

    //Getters and Setters
    public int getMaintenanceId() {
        return maintenanceID;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceID = maintenanceId;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "MaintenanceID=" + maintenanceID +
                ", Service date=" + serviceDate +
                ", Description=" + description +
                ", Cost=" + cost  +
                ", Status=" + status +
                ", Mechanic=" + mechanic +
                ", Car=" + (car != null ? car.getCarID() : "null") +
                '}';
    }

    // Builder class
    public static class Builder {
    private int maintenanceID;
    private Date serviceDate;
    private String description;
    private double cost;
    private String status;
    private String mechanic;
    private Car car;

    public Builder setMaintenanceID(int maintenanceID) {
        this.maintenanceID = maintenanceID;
        return this;
    }

    public Builder setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
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

    public Builder setStatus(String status) {
        this.status = status;
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

    public Builder copy(Maintenance maintenance) {
        this.maintenanceID = maintenance.getMaintenanceId();
        this.serviceDate = maintenance.getServiceDate();
        this.description = maintenance.getDescription();
        this.cost = maintenance.getCost();
        this.status = maintenance.getStatus();
        this.mechanic = maintenance.getMechanic();
        this.car = maintenance.getCar();
        return this;
    }

    public Maintenance build() {
        return new Maintenance(this);
    }
    }
}
