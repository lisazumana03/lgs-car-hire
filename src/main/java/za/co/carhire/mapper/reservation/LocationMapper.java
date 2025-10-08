package za.co.carhire.mapper.reservation;

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.dto.reservation.LocationDTO;

/**
 * Mapper class for converting between Location entity and LocationDTO
 * Author: Lisakhanya Zumana - 230864821
 * Date: 08/10/2025
 */
public class LocationMapper {

    /**
     * Convert Location entity to LocationDTO
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
                .setCountry(location.getCountry())
                .setPostalCode(location.getPostalCode())
                .build();
    }

    /**
     * Convert LocationDTO to Location entity
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
                .setCountry(dto.getCountry())
                .setPostalCode(dto.getPostalCode())
                .build();
    }

    /**
     * Update existing Location entity from LocationDTO
     * @param existingLocation the existing entity to update
     * @param dto the DTO with new values
     * @return the updated entity
     */
    public static Location updateEntityFromDTO(Location existingLocation, LocationDTO dto) {
        if (existingLocation == null || dto == null) {
            return existingLocation;
        }

        return new Location.Builder()
                .copy(existingLocation)
                .setLocationName(dto.getLocationName())
                .setStreetNumber(dto.getStreetNumber())
                .setStreetName(dto.getStreetName())
                .setCity(dto.getCityOrTown())
                .setProvinceOrState(dto.getProvinceOrState())
                .setCountry(dto.getCountry())
                .setPostalCode(dto.getPostalCode())
                .build();
    }
}
