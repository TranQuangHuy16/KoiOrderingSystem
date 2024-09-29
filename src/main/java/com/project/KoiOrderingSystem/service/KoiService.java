package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.Farm;
import com.project.KoiOrderingSystem.entity.KoiFish;
import com.project.KoiOrderingSystem.exception.EntityNotFoundException;
import com.project.KoiOrderingSystem.model.KoiRequest;
import com.project.KoiOrderingSystem.repository.KoiRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KoiService {

    @Autowired
    KoiRepository koiRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FarmService farmService;

    public KoiFish createKoi(KoiRequest koiRequest) {

//        if (isFarmExist(koiRequest.getFarmId().getId())) {
//            KoiFish koi = modelMapper.map(koiRequest, KoiFish.class);
//            KoiFish newKoi = koiRepository.save(koi);
//            return newKoi;
//        } else {
//            throw new EntityNotFoundException("Farm is not exist");
//        }
//        koiRequest.setFarmId(farmService.getFarmById(koiRequest.getFarmId().getId()));
//        KoiFish koi = modelMapper.map(koiRequest, KoiFish.class);
////        koi.setFarm(farmService.getFarmById(koiRequest.getFarmId().getId()));
//        return koiRepository.save(koi);

        KoiFish newKoi = new KoiFish();

        newKoi.setKoiName(koiRequest.getKoiName());
        newKoi.setFarm(farmService.getFarmById(koiRequest.getFarmId()));
        newKoi.setType(koiRequest.getType());
        newKoi.setPrice(koiRequest.getPrice());
        newKoi.setDescription(koiRequest.getDescription());
        newKoi.setImage(koiRequest.getImage());

        return koiRepository.save(newKoi);

    }

    public List<KoiFish> getAllKoi() {
        List<KoiFish> koiFishList = koiRepository.findKoiFishesByIsDeletedFalse();
        return koiFishList;
    }


    public KoiFish updateKoi(long id, KoiRequest koiRequest) {
        KoiFish oldKoi = koiRepository.findKoiFishById(id);

        oldKoi.setKoiName(koiRequest.getKoiName());
        oldKoi.setFarm(farmService.getFarmById(koiRequest.getFarmId()));
        oldKoi.setType(koiRequest.getType());
        oldKoi.setPrice(koiRequest.getPrice());
        oldKoi.setDescription(koiRequest.getDescription());
        oldKoi.setImage(koiRequest.getImage());

        return koiRepository.save(oldKoi);
    }

    public KoiFish deleteKoi(long id) {
        KoiFish oldKoi = getKoi(id);

        oldKoi.setDeleted(true);
        return oldKoi;
    }

    public KoiFish getKoi(long id) {
        KoiFish koiFish = koiRepository.findKoiFishById(id);
        if (koiFish == null) {
            throw new EntityNotFoundException("Koi fish not found");
        }
        return koiFish;
    }

    public boolean isFarmExist(long id) {
        Farm farm = farmService.getFarmById(id);
        if (farm == null) {
            return false;
        }
        return true;
    }
}
