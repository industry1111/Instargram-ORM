package com.travel.web_oasis.domain;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {

    String createdBy;

    LocalDateTime createdDate;
    String modifiedBy;

    LocalDateTime modifiedDate;
}
