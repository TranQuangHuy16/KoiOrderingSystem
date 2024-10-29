package com.project.KoiOrderingSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.KoiOrderingSystem.entity.KoiFish;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class FarmResponse {

    long id;

    String farmName;

    String location;

    String description;

    String phone;

    String email;

    String image;

    List<KoiFishResponse> koiFishResponseList;
}
