package za.co.carhire.domain.reservation;
/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 11/05/2025
 * */
import za.co.carhire.domain.vehicle.Car;

import java.util.Date;

public class Maintenance {
    
    // Primary attributes
    private int maintenanceID;
    private Date serviceDate;
    private String description;
    private double cost;
    private String status;
    private String mechanic;

    // Relationship with Car
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
    
    
    //Getters
    public int getMaintenanceId() {
        return maintenanceID;
    }
    

    public Date getServiceDate() {
        return serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public String getMechanic() {
        return mechanic;
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
    public Maintenance build() {
            return new Maintenance(this);
        }
    }
}
