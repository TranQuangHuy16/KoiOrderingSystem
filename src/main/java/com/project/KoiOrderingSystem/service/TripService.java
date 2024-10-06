package com.project.KoiOrderingSystem.service;


import com.project.KoiOrderingSystem.entity.Farm;
import com.project.KoiOrderingSystem.entity.Trip;
import com.project.KoiOrderingSystem.exception.EntityNotFoundException;
import com.project.KoiOrderingSystem.model.TripRequest;
import com.project.KoiOrderingSystem.repository.FarmRepository;
import com.project.KoiOrderingSystem.repository.TripRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TripService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TripRespository tripRespository;
    @Autowired
    FarmRepository farmRepository;

    public Trip createTrip(TripRequest tripRequest) {
        Trip newtrip = new Trip();
        newtrip.setStartDate(tripRequest.getStartDate());
        newtrip.setEndDate(tripRequest.getEndDate());
        newtrip.setStartLocation(tripRequest.getStartLocation());
        newtrip.setEndLocation(tripRequest.getEndLocation());

        Set<Farm> farmList = new HashSet<>();
        for (Long farmIds : tripRequest.getFarmIds()) {
            Farm farm = farmRepository.findFarmById(farmIds);
            if(farm == null) {
                throw new EntityNotFoundException("Farm not found");
            }

            farmList.add(farm);

        }
        newtrip.setFarms(farmList);
        tripRespository.save(newtrip);
        return newtrip;
    }

    public List<Trip> getAllTrip() {
        List<Trip> tripList = tripRespository.findTripsByIsDeletedFalse();
        return tripList;
    }


    public Trip getTripById(long tripId) {
        Trip trip = tripRespository.findTripByIdAndIsDeletedFalse(tripId);
        if(trip == null) {
            throw new EntityNotFoundException("Trip not found");
        }
        return trip;
    }

    public Trip updateTrip(TripRequest tripRequest, long tripId) {
        Trip trip = getTripById(tripId);
        trip.setStartDate(tripRequest.getStartDate());
        trip.setEndDate(tripRequest.getEndDate());
        trip.setStartLocation(tripRequest.getStartLocation());
        trip.setEndLocation(tripRequest.getEndLocation());

        Set<Farm> farmList = new HashSet<>();
        for (Long farmIds : tripRequest.getFarmIds()) {
            Farm farm = farmRepository.findFarmById(farmIds);
            if(farm == null) {
                throw new EntityNotFoundException("Farm not found");
            }

            farmList.add(farm);

        }
        trip.setFarms(farmList);

        return tripRespository.save(trip);
    }

    public void deleteTrip(long tripId) {
        Trip deletedTrip = getTripById(tripId);
        deletedTrip.setDeleted(true);
        tripRespository.save(deletedTrip);
    }
}
