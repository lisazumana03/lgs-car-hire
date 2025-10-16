package za.co.carhire.mapper.reservation;

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.dto.reservation.LocationDTO;

public class LocationMapper {

    /**
     * Convert Location entity to LocationDTO
     *
     * @param location the entity to convert
     * @return the DTO representation
     */
    public static LocationDTO toDTO(Location location) {
        if (location == null) {
            return null;
        }

        return new LocationDTO.Builder()
                .setLocationID(location.getLocationID())
                .setLocationName(location.getLocationName())
                .setStreetNumber(location.getStreetNumber())
                .setStreetName(location.getStreetName())
                .setCityOrTown(location.getCityOrTown())
                .setProvinceOrState(location.getProvinceOrState())
                .setPostalCode(location.getPostalCode())
                .setCountry(location.getCountry())
                .build();
    }

    /**
     * Convert LocationDTO to Location entity
     *
     * @param dto the DTO to convert
     * @return the entity representation
     */
    public static Location toEntity(LocationDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Location.Builder()
                .setLocationID(dto.getLocationID())
                .setLocationName(dto.getLocationName())
                .setStreetNumber(dto.getStreetNumber())
                .setStreetName(dto.getStreetName())
                .setCity(dto.getCityOrTown())
                .setProvinceOrState(dto.getProvinceOrState())
                .setPostalCode(dto.getPostalCode())
                .setCountry(dto.getCountry())
                .build();
    }

    /**
     * Update existing Location entity from LocationDTO
     *
     * @param existingLocation the existing entity to update
     * @param dto              the DTO with new values
     * @return the updated entity
     */
    public static Location updateEntityFromDTO(Location existingLocation, LocationDTO dto) {
        if (existingLocation == null || dto == null) {
            return existingLocation;
        }

        // Note: Location entity doesn't have setters, so we need to create a new one
        // using the Builder pattern with updated values
        return new Location.Builder()
                .setLocationID(existingLocation.getLocationID())
                .setLocationName(dto.getLocationName() != null ? dto.getLocationName() : existingLocation.getLocationName())
                .setStreetNumber(dto.getStreetNumber() != 0 ? dto.getStreetNumber() : existingLocation.getStreetNumber())
                .setStreetName(dto.getStreetName() != null ? dto.getStreetName() : existingLocation.getStreetName())
                .setCity(dto.getCityOrTown() != null ? dto.getCityOrTown() : existingLocation.getCityOrTown())
                .setProvinceOrState(dto.getProvinceOrState() != null ? dto.getProvinceOrState() : existingLocation.getProvinceOrState())
                .setPostalCode(dto.getPostalCode() != null ? dto.getPostalCode() : existingLocation.getPostalCode())
                .setCountry(dto.getCountry() != null ? dto.getCountry() : existingLocation.getCountry())
                .build();
    }
}
