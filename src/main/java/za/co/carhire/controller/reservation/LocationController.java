package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.factory.reservation.LocationFactory;
import za.co.carhire.service.impl.reservation.LocationService;

/*
Lisakhanya Zumana (230864821)
Date: 25/05/2025
 */

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "http://localhost:3046/create-location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/create")
    public Location create(@RequestBody Location location){
        Location location01 = LocationFactory.createLocation(location.getLocationID(), location.getLocationName(),
                location.getStreetName(), location.getCityOrTown(), location.getProvinceOrState(),
                location.getCountry(), location.getPostalCode(),
                location.getPickUpLocations(), location.getDropOffLocations());
        return locationService.create(location01);
    }

    @GetMapping("/read{id}")
    public Location read(@PathVariable int id){
        return locationService.read(id);
    }

    @PostMapping("/update")
    public Location update(@RequestBody Location location){
        return locationService.update(location);
    }

    @GetMapping("/delete{id}")
    public void delete(@PathVariable int id){
        locationService.delete(id);
    }
}
