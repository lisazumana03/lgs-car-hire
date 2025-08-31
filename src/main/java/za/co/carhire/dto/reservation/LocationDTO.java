package za.co.carhire.dto.reservation;

import java.io.Serializable;

/**
 *
 * Author: Imtiyaaz Waggie 219374759
 * Fixed LocationDTO
 *
 */
public class LocationDTO implements Serializable {

    private int locationID;
    private String locationName;
    private String streetName;
    private String cityOrTown;
    private String provinceOrState;
    private String country;
    private String postalCode;

    private Integer totalPickups;
    private Integer totalDropOffs;

    public LocationDTO() {
    }

    public LocationDTO(int locationID, String locationName, String streetName,
                       String cityOrTown, String provinceOrState,
                       String country, String postalCode) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.streetName = streetName;
        this.cityOrTown = cityOrTown;
        this.provinceOrState = provinceOrState;
        this.country = country;
        this.postalCode = postalCode;
    }

    public static class Builder {
        private int locationID;
        private String locationName;
        private String streetName;
        private String cityOrTown;
        private String provinceOrState;
        private String country;
        private String postalCode;
        private Integer totalPickups;
        private Integer totalDropOffs;

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

        public Builder setCityOrTown(String cityOrTown) {
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

        public Builder setTotalPickups(Integer totalPickups) {
            this.totalPickups = totalPickups;
            return this;
        }

        public Builder setTotalDropOffs(Integer totalDropOffs) {
            this.totalDropOffs = totalDropOffs;
            return this;
        }

        public LocationDTO build() {
            LocationDTO dto = new LocationDTO();
            dto.locationID = this.locationID;
            dto.locationName = this.locationName;
            dto.streetName = this.streetName;
            dto.cityOrTown = this.cityOrTown;
            dto.provinceOrState = this.provinceOrState;
            dto.country = this.country;
            dto.postalCode = this.postalCode;
            dto.totalPickups = this.totalPickups;
            dto.totalDropOffs = this.totalDropOffs;
            return dto;
        }
    }

    // Getters and Setters
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getTotalPickups() {
        return totalPickups;
    }

    public void setTotalPickups(Integer totalPickups) {
        this.totalPickups = totalPickups;
    }

    public Integer getTotalDropOffs() {
        return totalDropOffs;
    }

    public void setTotalDropOffs(Integer totalDropOffs) {
        this.totalDropOffs = totalDropOffs;
    }

    public String getFullAddress() {
        StringBuilder address = new StringBuilder();
        if (streetName != null) address.append(streetName).append(", ");
        if (cityOrTown != null) address.append(cityOrTown).append(", ");
        if (provinceOrState != null) address.append(provinceOrState).append(", ");
        if (country != null) address.append(country);
        if (postalCode != null) address.append(" ").append(postalCode);
        return address.toString();
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
                "locationID=" + locationID +
                ", locationName='" + locationName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", cityOrTown='" + cityOrTown + '\'' +
                ", provinceOrState='" + provinceOrState + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", totalPickups=" + totalPickups +
                ", totalDropOffs=" + totalDropOffs +
                '}';
    }
}