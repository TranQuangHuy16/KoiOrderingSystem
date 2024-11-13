package com.project.KoiOrderingSystem.service;


import com.project.KoiOrderingSystem.entity.Farm;
import com.project.KoiOrderingSystem.entity.Trip;
import com.project.KoiOrderingSystem.entity.TripDetail;
import com.project.KoiOrderingSystem.exception.EntityNotFoundException;
import com.project.KoiOrderingSystem.model.TripDetailRequest;
import com.project.KoiOrderingSystem.model.TripRequest;
import com.project.KoiOrderingSystem.repository.FarmRepository;
import com.project.KoiOrderingSystem.repository.TripDetailRespository;
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

    @Autowired
    TripDetailRespository tripDetailRespository;

    public Trip createTrip(TripRequest tripRequest) {
        Trip newtrip = new Trip();
        newtrip.setStartDate(tripRequest.getStartDate());
        newtrip.setEndDate(tripRequest.getEndDate());
        newtrip.setStartLocation(tripRequest.getStartLocation());
        newtrip.setEndLocation(tripRequest.getEndLocation());
        newtrip.setPrice(tripRequest.getPrice());
        Set<TripDetail> tripDetails = new HashSet<>();
        tripRespository.save(newtrip);

        for (TripDetailRequest tripDetail : tripRequest.getTripDetailRequests()) {
            Farm farm = farmRepository.findFarmByIdAndIsDeletedFalse(tripDetail.getFarmId());
            if(farm == null) {
                throw new EntityNotFoundException("Farm not found");
            }
            if (tripDetail.getTravelDate().isBefore(newtrip.getStartDate()) || tripDetail.getTravelDate().isAfter(newtrip.getEndDate())) {
                throw new EntityNotFoundException("Travel date must be between start date and end date");
            }
            TripDetail newTripDetail = new TripDetail();
            newTripDetail.setFarm(farm);
            newTripDetail.setTravelDate(tripDetail.getTravelDate());
            newTripDetail.setTrip(newtrip);
            tripDetailRespository.save(newTripDetail);
            tripDetails.add(newTripDetail);
        }
        newtrip.setTripDetails(tripDetails);
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
        trip.setPrice(tripRequest.getPrice());
        Set<TripDetail> tripDetails = new HashSet<>();
        tripDetailRespository.deleteTripDetailsByTripId(trip.getId());

        for (TripDetailRequest tripDetail : tripRequest.getTripDetailRequests()) {
            Farm farm = farmRepository.findFarmByIdAndIsDeletedFalse(tripDetail.getFarmId());
            if(farm == null) {
                throw new EntityNotFoundException("Farm not found");
            }
            TripDetail newTripDetail = new TripDetail();
            newTripDetail.setFarm(farm);
            newTripDetail.setTravelDate(tripDetail.getTravelDate());
            newTripDetail.setTrip(trip);
            tripDetailRespository.save(newTripDetail);
            tripDetails.add(newTripDetail);
        }
        trip.setTripDetails(tripDetails);
        return tripRespository.save(trip);
    }

    public void deleteTrip(long tripId) {
        Trip deletedTrip = getTripById(tripId);
        deletedTrip.setDeleted(true);
        tripRespository.save(deletedTrip);
    }
}
