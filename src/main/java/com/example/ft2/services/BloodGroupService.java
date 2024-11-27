package com.example.ft2.services;

import java.util.List;
import java.util.Optional;

import com.example.ft2.model.BloodGroup;

public interface BloodGroupService {
    List<BloodGroup> getAllBloodGroups();

    Optional<BloodGroup> getBloodGroupById(Long id);

    BloodGroup addBloodGroup(BloodGroup bloodGroup);

    BloodGroup updateBloodGroup(BloodGroup bloodGroup);

    BloodGroup deleteBloodGroup(Long id);

    List<BloodGroup> searchBloodGroups(String search);
}
