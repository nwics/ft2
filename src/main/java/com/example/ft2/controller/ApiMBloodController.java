package com.example.ft2.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ft2.model.MBlood;
import com.example.ft2.repository.MBloodRepository;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;

// import com.miniproject335b.app.repository.MBloodGroupRepository;
@RestController
@RequestMapping("/api/")
public class ApiMBloodController {

    @Autowired MBloodRepository mBloodRepository;

    @GetMapping("blood")
    public ResponseEntity<?> getAllBloodGroup() {
    List<MBlood> listMBlood = this.mBloodRepository.findByIsDelete(false);
    try {
        if (listMBlood.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK); // Array kosong
        } else {
            return new ResponseEntity<>(listMBlood, HttpStatus.OK); // Data ditemukan
        }
    } catch (Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal server error");
        response.put("details", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @GetMapping("get/{id}")
    public ResponseEntity<?> getBloodById(@PathVariable("id")Long id){
        try {
            MBlood mBloodData = this.mBloodRepository.findById(id).get();
            return new ResponseEntity<>(mBloodData,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("blood/add")
    public ResponseEntity<?> saveBlood(@RequestBody MBlood mBlood) {
        Optional<MBlood> mBloodData = this.mBloodRepository.findByCode(mBlood.code);
        if(mBloodData.isPresent()) {
            return new ResponseEntity<>("Data sudah ada", HttpStatus.OK);
        }
        mBlood.createBy = (long)1;
        mBlood.createOn = new Date();
        mBlood.isDelete = false;
        mBlood.code = mBlood.code;

        MBlood mBloodDataList = this.mBloodRepository.save(mBlood);
        if(mBloodDataList.equals(mBlood)) {
            return new ResponseEntity<>("Data sukses disimpan", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Data gagal disimpan", HttpStatus.BAD_REQUEST);
        }
        
    }

    @PutMapping("blood/edit/{userId}")
    public ResponseEntity<?>ediBlood(@RequestBody MBlood mBlood, @PathVariable("userId")Long userId){
        try{
            Optional<MBlood> mBloodData = this.mBloodRepository.findByCode(mBlood.code);

            if(mBloodData.isPresent()) {
                // if(mBloodData.get().equals())
                return new ResponseEntity<>("data sudah ada", HttpStatus.OK);
            }
        }
        catch(Exception e){

        }
        Long bloodId = mBlood.id;
        Optional<MBlood> itemList = this.mBloodRepository.findById(bloodId);
        if(itemList.isPresent()) {
            mBlood.id = bloodId;
            mBlood.modifiedBy = userId;
            mBlood.modifiedOn = new Date();
            mBlood.createBy = itemList.get().createBy;
            mBlood.createOn = itemList.get().createOn;
            mBlood.isDelete = false;
            mBlood.code = mBlood.code;
            this.mBloodRepository.save(mBlood);
            return new ResponseEntity<>("Edit succses", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("edit gagal", HttpStatus.NO_CONTENT);
        }

    }

    @PutMapping("blood/delete/{userId}/{id}")
    public ResponseEntity<?> deleteBlood(@PathVariable("userId")Long userId, @PathVariable("id")Long id){
        Optional<MBlood> item = this.mBloodRepository.findById(id);

        if(item.isPresent()) {
            MBlood mBlood = item.get();
            mBlood.deleteBy = userId;
            mBlood.deleteOn = new Date();
            mBlood.isDelete = true;
            this.mBloodRepository.save(mBlood);
            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  
        }
    }



}
