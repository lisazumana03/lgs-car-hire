package za.co.carhire.domain.reservation;

/*
Lisakhanya Zumana - 230864821
Date: 10 May 2025
 */

import java.util.Date;
import java.util.List;

public class Location {
    private int locationID;
    private String locationName;
    private String streetName;
    private String city;
    private String provinceOrState;
    private String country;
    private Short postalCode;
    private List<Booking> pickUpLocations;
    private List<Booking> dropOffLocations;

//    public Location(){
//
//    }

    private Location(Builder builder){
        this.locationID = builder.locationID;
        this.locationName = builder.locationName;
        this.streetName = builder.streetName;
        this.city = builder.city;
        this.provinceOrState = builder.provinceOrState;
        this.country = builder.country;
        this.postalCode = builder.postalCode;
        this.pickUpLocations = builder.pickUpLocations;
        this.dropOffLocations = builder.dropOffLocations;
    }

    public int getLocationID() {
        return locationID;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getCity() {
        return city;
    }

    public String getProvinceOrState() {
        return provinceOrState;
    }

    public String getCountry() {
        return country;
    }

    public Short getPostalCode() {
        return postalCode;
    }

    public List<Booking> getPickUpLocations() {
        return pickUpLocations;
    }

    public List<Booking> getDropOffLocations() {
        return dropOffLocations;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationID=" + locationID +
                ", locationName='" + locationName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", provinceOrState='" + provinceOrState + '\'' +
                ", country='" + country + '\'' +
                ", postalCode=" + postalCode +
                ", pickUpLocations=" + pickUpLocations +
                ", dropOffLocations=" + dropOffLocations +
                '}';
    }

    public static class Builder {
        private int locationID;
        private String locationName;
        private String streetName;
        private String city;
        private String provinceOrState;
        private String country;
        private Short postalCode;
        private List<Booking> pickUpLocations;
        private List<Booking> dropOffLocations;
    }

}
