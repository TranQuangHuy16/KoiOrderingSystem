package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Account;
import lombok.Data;

@Data
public class EmailDetail {
    Account receiver;
    String subject;
    String link;

}
