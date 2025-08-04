package za.co.carhire.domain.reservation;

/*
Location.java
Location POJO class
Lisakhanya Zumana - 230864821
Date: 10 May 2025
 */

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "location")
public class Location implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int locationID;

  @Column(name = "location_name")
  private String locationName;

  @Column(name = "street_name")
  private String streetName;

  @Column(name = "city_or_town")
  private String cityOrTown;

  @Column(name = "province_or_state")
  private String provinceOrState;

  @Column(name = "country")
  private String country;

  @Column(name = "postal_code")
  private Short postalCode;

  @OneToMany(mappedBy = "pickupLocation")
  private List<Booking> pickUpLocations;

  @OneToMany(mappedBy = "dropOffLocation")
  private List<Booking> dropOffLocations;

  public Location() {

  }

  private Location(Builder builder) {
    this.locationID = builder.locationID;
    this.locationName = builder.locationName;
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

  public void setLocationID(int locationID) {
    this.locationID = locationID;
  }

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
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

  public Short getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(Short postalCode) {
    this.postalCode = postalCode;
  }

  public List<Booking> getPickUpLocations() {
    return pickUpLocations;
  }

  public void setPickUpLocations(List<Booking> pickUpLocations) {
    this.pickUpLocations = pickUpLocations;
  }

  public List<Booking> getDropOffLocations() {
    return dropOffLocations;
  }

  public void setDropOffLocations(List<Booking> dropOffLocations) {
    this.dropOffLocations = dropOffLocations;
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
    private String streetName;
    private String cityOrTown;
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

    public Builder copy(Location location) {
      this.locationID = location.locationID;
      this.locationName = location.locationName;
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
