package za.co.carhire.domain.reservation;

/*
Location.java
Location POJO class
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

        public Builder setLocationID(int locationID) {
            this.locationID = locationID;
            return this;
        }
        public Builder setLocationName(String locationName) {
            this.locationName = locationName;
            return this;
        }
        public Builder setStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }
        public Builder setCity(String city) {
            this.city = city;
            return this;
        }
        public Builder setProvinceOrState(String provinceOrState) {
            this.provinceOrState = provinceOrState;
            return this;
        }
        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }
        public Builder setPostalCode(Short postalCode) {
            this.postalCode = postalCode;
            return this;
        }
        public Builder setPickUpLocations(List<Booking> pickUpLocations) {
            this.pickUpLocations = pickUpLocations;
            return this;
        }
        public Builder setDropOffLocations(List<Booking> dropOffLocations) {
            this.dropOffLocations = dropOffLocations;
            return this;
        }

        public Builder copy(Location location){
            this.locationID = location.locationID;
            this.locationName = location.locationName;
            this.streetName = location.streetName;
            this.city = location.city;
            this.provinceOrState = location.provinceOrState;
            this.country = location.country;
            this.postalCode = location.postalCode;
            this.pickUpLocations = location.pickUpLocations;
            this.dropOffLocations = location.dropOffLocations;
            return this;
        }

        public Location build(){
            return new Location(this);
        }

    }

}
