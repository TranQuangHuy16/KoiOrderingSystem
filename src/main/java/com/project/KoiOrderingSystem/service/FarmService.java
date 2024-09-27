package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.Farm;
import com.project.KoiOrderingSystem.exception.EntityNotFoundException;
import com.project.KoiOrderingSystem.model.FarmRequest;
import com.project.KoiOrderingSystem.repository.FarmRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmService {

    @Autowired
    FarmRepository farmRepository;

    @Autowired
    ModelMapper modelMapper;

    public Farm createFarm(FarmRequest farmRequest) {
        Farm farm = modelMapper.map(farmRequest, Farm.class);

        Farm newFarm = farmRepository.save(farm);
        return farm;
    }

    public List<Farm> getAllFarm() {
        List<Farm> farms = farmRepository.findFarmsByIsDeletedFalse();
        return farms;
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
