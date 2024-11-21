package com.example.ft2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ft2.model.MUser;
import com.example.ft2.repository.MUserRepository;

@RestController
@RequestMapping("/api/auth/")
public class ApiMUserController {

    @Autowired MUserRepository mUserRepository;

    @PostMapping("/login")
    public ResponseEntity<?>login (@RequestBody MUser mUser){
        MUser mUserData = this.mUserRepository.findByUsername(mUser.username);

        if(mUserData != null && mUserData.password.equals(mUser.password)) {
            return new ResponseEntity<>("Login berhasil", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Data tidak terdaftar", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody MUser mUserNew){
        if(mUserRepository.findByUsername(mUserNew.username) != null) {
            return new ResponseEntity<>("username sudah terdaftar", HttpStatus.OK);
        }
        else {
            this.mUserRepository.save(mUserNew);
            return new ResponseEntity<>("Berhasil melakukan register", HttpStatus.OK);
        }
    }

}
