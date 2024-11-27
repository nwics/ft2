package com.example.ft2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ft2.model.BloodGroup;
import com.example.ft2.repository.BloodGroupRepository;

public class BloodGroupServiceImpl implements BloodGroupService{
     @Autowired
    private BloodGroupRepository bloodTypeRepository;

    @Override
    public List<BloodGroup> getAllBloodGroups() {
        return bloodTypeRepository.order();
    }

    @Override
    public Optional<BloodGroup> getBloodGroupById(Long id) {
        return bloodTypeRepository.findById(id);
    }

    @Override
    public BloodGroup addBloodGroup(BloodGroup bloodGroup) {
        bloodGroup.setCreatedBy(1L);
        bloodGroup.setCreatedOn(new Date());
        return bloodTypeRepository.save(bloodGroup);
    }

    @Override
    public BloodGroup updateBloodGroup(BloodGroup bloodGroup) {
        Optional<BloodGroup> existingBloodGroup = bloodTypeRepository.findById(bloodGroup.getId());

        if (existingBloodGroup.isPresent()) {
            BloodGroup existing = existingBloodGroup.get();
            bloodGroup.setCreatedBy(existing.getCreatedBy());
            bloodGroup.setCreatedOn(existing.getCreatedOn());
            bloodGroup.setModifiedBy(2L);
            bloodGroup.setModifiedOn(new Date());
            return bloodTypeRepository.save(bloodGroup);
        } else {
            throw new RuntimeException("Golongan darah tidak ditemukan");
        }
    }

    @Override
    public BloodGroup deleteBloodGroup(Long id) {
        Optional<BloodGroup> bloodGroup = bloodTypeRepository.findById(id);

        if (bloodGroup.isPresent()) {
            BloodGroup bg = bloodGroup.get();
            bg.setIsDelete(true);
            bg.setDeletedBy(1L);
            bg.setDeletedOn(new Date());
            return bloodTypeRepository.save(bg);
        } else {
            throw new RuntimeException("Golongan darah tidak ditemukan");
        }
    }

    @Override
    public List<BloodGroup> searchBloodGroups(String search) {
        return bloodTypeRepository.searching(search);
    }

}
