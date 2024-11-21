package com.example.ft2.model;

import java.sql.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="m_admin")
public class MAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "biodata_id")
    public Long biodataId;

    @Column(name = "code")
    public String code;

    @Column(name = "created_by", nullable = false)
    public Long createdBy;

    @Column(name = "create_on", nullable = false)
    public Date createOn;

    @Column(name = "modified_by")
    public Long modifiedBy;

    @Column(name = "modified_on")
    public Date modifiedOn;

    @Column(name = "delete_by")
    public Long deleteBy;

    @Column(name = "delete_on")
    public Date deleteOn;

    @Column(name = "is_delete", nullable = false)
    public Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "biodata_id", insertable = false, updatable = false)
    public MBiodata mBiodata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBiodataId() {
        return biodataId;
    }

    public void setBiodataId(Long biodataId) {
        this.biodataId = biodataId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
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

    public Long getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(Long deleteBy) {
        this.deleteBy = deleteBy;
    }

    public Date getDeleteOn() {
        return deleteOn;
    }

    public void setDeleteOn(Date deleteOn) {
        this.deleteOn = deleteOn;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public MBiodata getmBiodata() {
        return mBiodata;
    }

    public void setmBiodata(MBiodata mBiodata) {
        this.mBiodata = mBiodata;
    }

    

}
