package za.co.carhire.domain.reservation;

/***
 * Lisakhanya Zumana (230864821)
 * Date: 05/06/2025
 * Location POJO class
 */

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import za.co.carhire.util.LocationDeserializer;

@Entity
@Table(name = "location")
@JsonDeserialize(using = LocationDeserializer.class)
public class Location implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationID;
    private String locationName;
    private int streetNumber;
    private String streetName;
    private String cityOrTown;
    private String provinceOrState;
    private String country;
    private String postalCode;

    @OneToMany(mappedBy = "pickupLocation")
    @JsonIgnore
    private List<Booking> pickUpLocations;
    @OneToMany(mappedBy = "dropOffLocation")
    @JsonIgnore
    private List<Booking> dropOffLocations;

    public Location() {

    }

    private Location(Builder builder) {
        this.locationID = builder.locationID;
        this.locationName = builder.locationName;
        this.streetNumber = builder.streetNumber;
        this.streetName = builder.streetName;
        this.cityOrTown = builder.cityOrTown;
        this.provinceOrState = builder.provinceOrState;
        this.country = builder.country;
        this.postalCode = builder.postalCode;
        this.pickUpLocations = builder.pickUpLocations;
        this.dropOffLocations = builder.dropOffLocations;
    }

    public int getLocationID() {
        return locationID;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getCityOrTown() {
        return cityOrTown;
    }

    public String getProvinceOrState() {
        return provinceOrState;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
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
                ", cityOrTown='" + cityOrTown + '\'' +
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
        private int streetNumber;
        private String streetName;
        private String cityOrTown;
        private String provinceOrState;
        private String country;
        private String postalCode;
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

        public Builder setStreetNumber(int streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public Builder setStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder setCity(String cityOrTown) {
            this.cityOrTown = cityOrTown;
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

        public Builder setPostalCode(String postalCode) {
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

        public Builder copy(Location location) {
            this.locationID = location.locationID;
            this.locationName = location.locationName;
            this.streetNumber = location.streetNumber;
            this.streetName = location.streetName;
            this.cityOrTown = location.cityOrTown;
            this.provinceOrState = location.provinceOrState;
            this.country = location.country;
            this.postalCode = location.postalCode;
            this.pickUpLocations = location.pickUpLocations;
            this.dropOffLocations = location.dropOffLocations;
            return this;
        }

        public Location build() {
            return new Location(this);
        }

    }

}
