package com.ssanusi.javaoauth2.models;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.Temporal;

import static javax.persistence.TemporalType.TIMESTAMP;

@EntityListeners(AuditingEntityListener.class)
abstract class Auditable {

    @CreatedBy
    protected String createdBy;

    @CreatedDate
    @Temporal(TIMESTAMP)
    protected String createdDate;

    @LastModifiedBy
    protected String lastModifiedBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected String lastModifiedData;
}
