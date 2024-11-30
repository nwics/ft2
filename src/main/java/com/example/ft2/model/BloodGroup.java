package com.example.ft2.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

// import groovy.transform.Generated;
// import groovyjarjarantlr4.v4.parse.ANTLRParser.prequelConstruct_return;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// import java.lang.annotation.Inherited;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "blood_group")
public class BloodGroup {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @Column(name = "decription", nullable = true)
    private String description;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_on", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdOn;

    @Column(name = "modified_by", nullable = true)
    private Long modifiedBy;

    @Column(name = "modified_on", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifiedOn;

    @Column(name = "deleted_by", nullable = true)
    private Long deletedBy;

    @Column(name = "deleted_on", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deletedOn;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    
}
