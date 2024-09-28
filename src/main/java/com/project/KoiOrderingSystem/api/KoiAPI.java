package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.entity.KoiFish;
import com.project.KoiOrderingSystem.model.KoiRequest;
import com.project.KoiOrderingSystem.service.KoiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/koi")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class KoiAPI {

    @Autowired
    KoiService koiService;

    @PostMapping
    public ResponseEntity createKoi(@Valid @RequestBody KoiRequest koiRequest) {
        KoiFish newKoi = koiService.createKoi(koiRequest);
        return ResponseEntity.ok(newKoi);
    }

    @GetMapping
    public ResponseEntity getAllKoi() {
        List<KoiFish> koiFishList = koiService.getAllKoi();
        return ResponseEntity.ok(koiFishList);
    }

    @PutMapping("{koiId}")
    public ResponseEntity updateKoi(@PathVariable long koiId, @Valid @RequestBody KoiRequest koiRequest) {
        KoiFish newKoi = koiService.updateKoi(koiId, koiRequest);
        return ResponseEntity.ok(newKoi);
    }

    @DeleteMapping("{koiId}")
    public ResponseEntity deleteKoi(@PathVariable long koiId) {
        KoiFish oldKoi = koiService.deleteKoi(koiId);
        return ResponseEntity.ok(oldKoi);
    }
}
