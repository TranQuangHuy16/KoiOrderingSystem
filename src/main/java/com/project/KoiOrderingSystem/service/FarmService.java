package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.Farm;
import com.project.KoiOrderingSystem.entity.KoiFish;
import com.project.KoiOrderingSystem.exception.EntityNotFoundException;
import com.project.KoiOrderingSystem.model.FarmRequest;
import com.project.KoiOrderingSystem.model.FarmResponse;
import com.project.KoiOrderingSystem.model.KoiFishResponse;
import com.project.KoiOrderingSystem.repository.FarmRepository;
import com.project.KoiOrderingSystem.repository.KoiRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FarmService {

    @Autowired
    FarmRepository farmRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    KoiService koiService;

    @Autowired
    KoiRepository koiRepository;

    public Farm createFarm(FarmRequest farmRequest) {
        Farm farm = modelMapper.map(farmRequest, Farm.class);

        Farm newFarm = farmRepository.save(farm);
        return farm;
    }

    public List<FarmResponse> getAllFarm() {
        List<FarmResponse> farmResponses = new ArrayList<>();

        for(Farm farm : farmRepository.findAll()) {
            FarmResponse farmResponse = modelMapper.map(farm, FarmResponse.class);
            List<KoiFishResponse> koiFishResponseList = new ArrayList<>();

            for(KoiFish koiFish : farm.getKoiFish()) {
                koiFishResponseList.add(modelMapper.map(koiFish, KoiFishResponse.class));
            }

            farmResponse.setKoiFishResponseList(koiFishResponseList);
            farmResponses.add(farmResponse);
        }

        return farmResponses;
    }

    public Farm updateFarm(long id, FarmRequest farmRequest) {
        Farm oldFarm = getFarmById(id);

        Farm farm = modelMapper.map(farmRequest, Farm.class);

        oldFarm.setFarmName(farm.getFarmName());
        oldFarm.setLocation(farm.getLocation());
        oldFarm.setDescription(farm.getDescription());
        oldFarm.setPhone(farm.getPhone());
        oldFarm.setEmail(farm.getEmail());
        oldFarm.setImage(farm.getImage());

        return farmRepository.save(oldFarm);
    }

    public Farm deleteFarm(long id) {
        Farm oldFarm = getFarmById(id);

        List<KoiFish> koiFishes = koiService.getAllKoi();
        for (KoiFish koiFish : koiFishes) {
            if (koiFish.getFarm().getId() == id) {
                koiFish.setDeleted(true);
                koiRepository.save(koiFish);
            }
        }

        oldFarm.setDeleted(true);
        return farmRepository.save(oldFarm);
    }

    public Farm getFarmById(long id) {
        Farm farm = farmRepository.findFarmById(id);
        if (farm == null) {
            throw new EntityNotFoundException("Farm not found");
        }
        return farm;
    }
}
