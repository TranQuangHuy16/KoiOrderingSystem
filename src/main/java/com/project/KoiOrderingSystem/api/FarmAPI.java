package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.entity.Farm;
import com.project.KoiOrderingSystem.model.FarmRequest;
import com.project.KoiOrderingSystem.service.FarmService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farm")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class FarmAPI {

    @Autowired
    FarmService farmService;

    @PostMapping
    public ResponseEntity createFarm(@Valid @RequestBody FarmRequest farmRequest) {
        Farm farm = farmService.createFarm(farmRequest);
        return ResponseEntity.ok(farm);
    }

    @GetMapping
    public ResponseEntity getAllFarm() {
        List<Farm> farms = farmService.getAllFarm();
        return ResponseEntity.ok(farms);
    }

    @PutMapping("{farmId}")
    public ResponseEntity updateFarm(@PathVariable long farmId, @Valid @RequestBody FarmRequest farmRequest) {
        Farm farm = farmService.updateFarm(farmId, farmRequest);
        return ResponseEntity.ok(farm);
    }

    @DeleteMapping("{farmId}")
    public ResponseEntity deleteFarm(@PathVariable long farmId) {
        Farm farm = farmService.deleteFarm(farmId);
        return ResponseEntity.ok(farm);
    }
}
