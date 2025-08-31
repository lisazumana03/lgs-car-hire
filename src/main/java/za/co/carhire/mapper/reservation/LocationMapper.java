package za.co.carhire.mapper.reservation;

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.dto.reservation.LocationDTO;

/**
 * Fixed Location Mapper
 * Author: Imtiyaaz Waggie 219374759
 *
 */
public class LocationMapper {

    public static LocationDTO toDTO(Location location) {
        if (location == null) {
            return null;
        }

        LocationDTO.Builder builder = new LocationDTO.Builder()
                .setLocationID(location.getLocationID())
                .setLocationName(location.getLocationName())
                .setStreetName(location.getStreetName())
                .setCityOrTown(location.getCityOrTown())
                .setProvinceOrState(location.getProvinceOrState())
                .setCountry(location.getCountry())
                .setPostalCode(location.getPostalCode());

        if (location.getPickUpLocations() != null) {
            builder.setTotalPickups(location.getPickUpLocations().size());
        }

        if (location.getDropOffLocations() != null) {
            builder.setTotalDropOffs(location.getDropOffLocations().size());
        }

        return builder.build();
    }

    public static Location toEntity(LocationDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Location.Builder()
                .setLocationID(dto.getLocationID())
                .setLocationName(dto.getLocationName())
                .setStreetName(dto.getStreetName())
                .setCity(dto.getCityOrTown())
                .setProvinceOrState(dto.getProvinceOrState())
                .setCountry(dto.getCountry())
                .setPostalCode(dto.getPostalCode())
                .build();
    }

    public static Location updateEntityFromDTO(Location existingLocation, LocationDTO dto) {
        if (existingLocation == null || dto == null) {
            return existingLocation;
        }

        return new Location.Builder()
                .copy(existingLocation)
                .setLocationName(dto.getLocationName())
                .setStreetName(dto.getStreetName())
                .setCity(dto.getCityOrTown())
                .setProvinceOrState(dto.getProvinceOrState())
                .setCountry(dto.getCountry())
                .setPostalCode(dto.getPostalCode())
                .build();
    }
}