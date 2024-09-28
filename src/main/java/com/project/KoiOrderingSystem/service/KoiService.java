package com.project.KoiOrderingSystem.service;

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

    public KoiFish createKoi(KoiRequest koiRequest) {
        KoiFish koi = modelMapper.map(koiRequest, KoiFish.class);

        KoiFish newKoi = koiRepository.save(koi);
        return newKoi;
    }

    public List<KoiFish> getAllKoi() {
        List<KoiFish> koiFishList = koiRepository.findKoiFishesByIsDeletedFalse();
        return koiFishList;
    }



    public KoiFish updateKoi(long id, KoiRequest koiRequest) {
        KoiFish oldKoi = koiRepository.findKoiFishById(id);

        KoiFish koi = modelMapper.map(koiRequest, KoiFish.class);

        oldKoi.setKoiName(koi.getKoiName());
        oldKoi.setFarm(koi.getFarm());
        oldKoi.setType(koi.getType());
        oldKoi.setPrice(koi.getPrice());
        oldKoi.setDescription(koi.getDescription());
        oldKoi.setImage(koi.getImage());

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
}
