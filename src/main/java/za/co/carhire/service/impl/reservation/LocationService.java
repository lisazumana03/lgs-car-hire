//package za.co.carhire.service.impl.reservation;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import za.co.carhire.domain.reservation.Location;
//import za.co.carhire.repository.reservation.ILocationRepository;
//import za.co.carhire.service.reservation.ILocationService;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///*
//Lisakhanya Zumana (230864821)
//Date: 24/05/2025
// */
//
//@Service
//public class LocationService implements ILocationService {
//
//    @Autowired
//    private ILocationRepository locationRepository;
//
//    @Override
//    public Set<Location> getLocations() {
//        return locationRepository.findAll().stream().collect(Collectors.toSet());
//    }
//
//    @Override
//    public Location create(Location location) {
//        return locationRepository.save(location);
//    }
//
//
//    @Override
//    public Location read(int locationID) {
//        return locationRepository.findById(locationID).orElse(null);
//    }
//
//    @Override
//    public Location update(Location location) {
//        if(this.locationRepository.existsById(location.getLocationID()))
//            return this.locationRepository.save(location);
//        return null;
//    }
//
//    @Override
//    public void delete(int locationID) {
//        locationRepository.deleteById(locationID);
//    }
//}
