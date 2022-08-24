package com.codegym.service.locationRegion;

import com.codegym.model.LocationRegion;
import com.codegym.repository.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LocationRegionServiceImpl implements LocationRegionService {
    @Autowired
   private LocationRegionRepository locationRegionRepository;

    @Override
    public Iterable findAll() {
        return null;
    }

    @Override
    public Optional<LocationRegion> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public LocationRegion save(LocationRegion locationRegion) {

        return locationRegionRepository.save(locationRegion);
    }

    @Override
    public void remove(Long id) {

    }
}
