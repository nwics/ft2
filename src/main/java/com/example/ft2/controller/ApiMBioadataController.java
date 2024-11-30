package com.example.ft2.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ft2.model.MBiodata;
import com.example.ft2.model.MCustomer;
import com.example.ft2.repository.MBiodataRepository;
import com.example.ft2.repository.MCustomerRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/biodata/")
public class ApiMBioadataController {

    @Autowired MBiodataRepository mBiodataRepository;

    @Autowired MCustomerRepository mCustomerRepository;

	@GetMapping("show")
	public ResponseEntity<List<MBiodata>> getAllBiodata() {
		try {
			List<MBiodata> listBiodata = this.mBiodataRepository.findByIsDelete();
			return new ResponseEntity<>(listBiodata, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

    @PutMapping("img/{id}")
	public ResponseEntity<Object> changeProfileImage(@RequestParam("img") MultipartFile file,
			@PathVariable("id") Long id) {
		try {
			System.out.println(file.getSize());
			System.out.println(file.getContentType().substring(0,5));
			if(file.getContentType().substring(0,5).equals("image")) {
				// Punya Handinata
				 String folderPath = "D:\\Handinata\\Mini Project\\miniproject335bfe\\src\\main\\resources\\static\\uploads\\" + file.getOriginalFilename();
				// Punya Fadli
				//String folderPath = "/home/pr001/Data/XsisAcademy/miniproject335bfe/src/main/resources/static/uploads/" + file.getOriginalFilename();
				
				File path = new File(folderPath);
				path.createNewFile();
				FileOutputStream output = new FileOutputStream(path);
				// System.out.println(file.getSize());

				output.write(file.getBytes());
				output.close();

				// 1048576

				MBiodata image = this.mBiodataRepository.findById(id).get();
				image.setModifiedBy(id);
				image.setModifiedOn(new Date());
				image.setImagePath("/uploads/" + file.getOriginalFilename());
				this.mBiodataRepository.save(image);
				return new ResponseEntity<Object>("Save Successful", HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("Error", HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>("Save Failed", HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("{id}")
	public ResponseEntity<Object> getBiodataById(@PathVariable("id") Long id) {
		try {
			MBiodata data = this.mBiodataRepository.findById(id).get();
			return new ResponseEntity<Object>(data, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("edit/{id}")
	public ResponseEntity<Object> editBiodata(@RequestBody MBiodata mbiodata, @PathVariable("id") Long id){
		//WARNING!!! REQUESTBODY MBIODATA INI BERISI CREATED ON, NAMUN BUKAN BERUPA TANGGAL KAPAN MBIODATA DIBUAT,
		//NAMUN TANGGAL LAHIR DARI USER
		MBiodata bio = this.mBiodataRepository.findById(mbiodata.getId()).orElse(null);
		if(bio!=null) {
			bio.setFullname(mbiodata.getFullname());
			bio.setMobilePhone(mbiodata.getMobilePhone());
			bio.setModifiedBy(id);
			bio.setModifiedOn(new Date());
			MBiodata bios = this.mBiodataRepository.save(bio);
			if(bios.equals(bio)) {
				MCustomer mc = this.mCustomerRepository.findByBiodataId(bio.getId());
				mc.setDob(mbiodata.getCreatedOn());
				mc.setModifiedBy(id);
				mc.setModifiedOn(new Date());
				MCustomer mcs = this.mCustomerRepository.save(mc);
				if(mcs.equals(mc)) {
					return new ResponseEntity<>("Modify success", HttpStatus.OK);
				}else {
					return new ResponseEntity<>("Modify failed", HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<>("Modify failed", HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<>("Modify failed", HttpStatus.OK);
		}
	}
}
