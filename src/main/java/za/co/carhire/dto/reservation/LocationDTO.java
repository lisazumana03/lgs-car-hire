package za.co.carhire.dto.reservation;

import java.io.Serializable;

public class LocationDTO implements Serializable {

    private int locationID;
    private String locationName;
    private int streetNumber;
    private String streetName;
    private String cityOrTown;
    private String provinceOrState;
    private String postalCode;
    private String country;

    public LocationDTO() {
    }

    public static class Builder {
        private int locationID;
        private String locationName;
        private int streetNumber;
        private String streetName;
        private String cityOrTown;
        private String provinceOrState;
        private String postalCode;
        private String country;

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

        public Builder setCityOrTown(String cityOrTown) {
            this.cityOrTown = cityOrTown;
            return this;
        }

        public Builder setProvinceOrState(String provinceOrState) {
            this.provinceOrState = provinceOrState;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public LocationDTO build() {
            LocationDTO dto = new LocationDTO();
            dto.locationID = this.locationID;
            dto.locationName = this.locationName;
            dto.streetNumber = this.streetNumber;
            dto.streetName = this.streetName;
            dto.cityOrTown = this.cityOrTown;
            dto.provinceOrState = this.provinceOrState;
            dto.postalCode = this.postalCode;
            dto.country = this.country;
            return dto;
        }
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityOrTown() {
        return cityOrTown;
    }

    public void setCityOrTown(String cityOrTown) {
        this.cityOrTown = cityOrTown;
    }

    public String getProvinceOrState() {
        return provinceOrState;
    }

    public void setProvinceOrState(String provinceOrState) {
        this.provinceOrState = provinceOrState;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
                "locationID=" + locationID +
                ", locationName='" + locationName + '\'' +
                ", streetNumber=" + streetNumber +
                ", streetName='" + streetName + '\'' +
                ", cityOrTown='" + cityOrTown + '\'' +
                ", provinceOrState='" + provinceOrState + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
