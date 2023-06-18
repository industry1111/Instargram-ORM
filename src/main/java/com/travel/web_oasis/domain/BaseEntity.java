package com.travel.web_oasis.domain;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import lombok.val;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@ToString
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
public class BaseEntity {

    @CreatedBy
    String createdBy;
    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedBy
    String modifiedBy;
    @LastModifiedDate
    LocalDateTime modifiedDate;
}
