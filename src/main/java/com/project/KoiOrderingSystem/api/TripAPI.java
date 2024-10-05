package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.entity.Trip;
import com.project.KoiOrderingSystem.model.TripRequest;
import com.project.KoiOrderingSystem.service.TripService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class TripAPI {

    @Autowired
    TripService tripService;

    @PostMapping
    public ResponseEntity createTrip(@Valid @RequestBody TripRequest tripRequest) {
        Trip newtrip = tripService.createTrip(tripRequest);
        return ResponseEntity.ok(newtrip);
    }

    @GetMapping
    public ResponseEntity getAllTrip() {
        List<Trip> tripList = tripService.getAllTrip();
        return ResponseEntity.ok(tripList);
    }

    @GetMapping("{tripId}")
    public ResponseEntity getTripById(@PathVariable long tripId) {
        Trip trip = tripService.getTripById(tripId);
        return ResponseEntity.ok(trip);
    }

    @PutMapping("{tripId}")
    public ResponseEntity updateTrip(@Valid @RequestBody TripRequest tripRequest,@PathVariable long tripId) {
        Trip updatedTrip = tripService.updateTrip(tripRequest, tripId);
        return ResponseEntity.ok(updatedTrip);
    }

    @DeleteMapping("{tripId}")
    public ResponseEntity deleteTrip(@PathVariable long tripId) {
        tripService.deleteTrip(tripId);
        return ResponseEntity.ok("Deleted trip");
    }

}
