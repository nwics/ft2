package com.example.ft2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ft2.model.MLocation;
import com.example.ft2.repository.MLocationRepository;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/dokter/")
public class ApiCariDokterController {
 
    @Autowired MLocationRepository lokasi;


    @GetMapping("lokasi")
    public ResponseEntity<?> getAllLocation(){
        try{
            List<MLocation> listLokasi = this.lokasi.findAll();
            return new ResponseEntity<>(listLokasi, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("lokasi/{id}")
    public ResponseEntity<?> getSpesialisById(@PathVariable(required = false)Long id){
        try{
            Optional<MLocation> lokasi = this.lokasi.findById(id);
            if(lokasi.isPresent()) {
                ResponseEntity rest = new ResponseEntity<>(lokasi, HttpStatus.OK);
                return rest;
            }
            else {
                return ResponseEntity.notFound().build();
            }
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    
}
