package com.example.ft2.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ft2.model.BloodGroup;
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

    //  @GetMapping("bloodtype")
    // public ResponseEntity<ApiResponse<List<BloodGroup>>> getBloodGroup() {
    //     try {
    //         List<BloodGroup> listBloodGroup = this.bloodTypeRepository.order();
    //         if (listBloodGroup.isEmpty()) {
    //             return ResponseUtil.generateErrorMessage("Golongan Darah Tidak Ditemukan", listBloodGroup,
    //                     HttpStatus.CONFLICT);
    //         }
    //         return ResponseUtil.generateSuccessMessage("Golongan Darah Berhasil Ditemukan", listBloodGroup,
    //                 HttpStatus.CREATED);

    //     } catch (Exception e) {
    //         return ResponseUtil.generateErrorMessage("Gagal Mendapatkan Data Golongan Darah", e.getMessage(),
    //                 HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }



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

    // @GetMapping("bloodtype/{id}")
    // public ResponseEntity<?> getBloodGroup(@PathVariable("id") Long id) {
    //     try {
    //         Optional<BloodGroup> bloodData = this.bloodTypeRepository.findById(id);
    //         if (bloodData.isPresent()) {
    //             return ResponseEntity.status(HttpStatus.OK).body(bloodData.get());
    //         } else {
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Golongan Darah Tidak Ditemukan");
    //         }
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    //     }
    // }
    // @GetMapping("bloodtype/{id}")
    // public ResponseEntity<ApiResponse<Optional<BloodGroup>>> getBloodGroup(@PathVariable("id") Long id) {
    //     try {
    //         Optional<BloodGroup> bloodData = this.bloodTypeRepository.findById(id);
    //         if (bloodData.isPresent()) {
    //             return ResponseUtil.generateSuccessMessage("Berhasil Mendapatkan Data Golongan Darah", bloodData);
    //         } else {
    //             return ResponseUtil.generateErrorMessage("Golongan Darah Tidak Ditemukan",
    //                     "Tidak ada Golongan Darah dengan ID = "
    //                             + id,
    //                     HttpStatus.NOT_FOUND);
    //         }
    //     } catch (Exception e) {
    //         return ResponseUtil.generateErrorMessage("Gagal Mendapatkan Data Golongan Darah", e.getMessage(),
    //                 HttpStatus.INTERNAL_SERVER_ERROR);

    //     }
    // }

    // public Boolean CheckBloodType(String type) {
    //     if (type.equalsIgnoreCase("A") || type.equalsIgnoreCase("A-") ||
    //             type.equalsIgnoreCase("B") || type.equalsIgnoreCase("B-") ||
    //             type.equalsIgnoreCase("AB") || type.equalsIgnoreCase("AB-") ||
    //             type.equalsIgnoreCase("O") || type.equalsIgnoreCase("O-")) {
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }

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

    // @PostMapping("bloodtype/add")
    // public ResponseEntity<ApiResponse<BloodGroup>> addBloodType(@RequestBody BloodGroup bloodType) {
    //     try {
    //         String kode = bloodType.getCode();
    //         Boolean kodeExist = this.bloodTypeRepository.existsByCode(bloodType.getCode());

    //         Map<String, String> errors = new HashMap<>();

    //         if (kode.isBlank()) {
    //             errors.put("blood_code", "Kode Kosong.");
    //         }

    //         if (CheckBloodType(kode) == false && !kode.isBlank()) {
    //             errors.put("blood_code", "Kode " + kode + " bukan kode Golongan Darah yang ada.");
    //         }

    //         if (kodeExist) {
    //             errors.put("blood_code", kode + " Sudah Ada.");
    //         }

    //         if (!errors.isEmpty()) {
    //             return ResponseUtil.generateErrorMessage("Validasi Error", errors, HttpStatus.CONFLICT);
    //         }

    //         bloodType.setCreatedBy(1L);
    //         bloodType.setCreatedOn(new Date());

    //         BloodGroup saveBloodType = this.bloodTypeRepository.save(bloodType);
    //         return ResponseUtil.generateSuccessMessage("Golongan Darah Berhasil Disimpan", saveBloodType,
    //                 HttpStatus.CREATED);
    //     } catch (Exception e) {
    //         return ResponseUtil.generateErrorMessage("Golongan Darah Gagal Disimpan", e.getMessage(),
    //                 HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

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

    // @PutMapping("bloodtype/update")
    // public ResponseEntity<ApiResponse<BloodGroup>> updateBloodType(
    //         @RequestBody BloodGroup bloodType) {
    //     try {
    //         Map<String, String> errors = new HashMap<>();
    //         String kode = bloodType.getCode();

    //         if (kode.isBlank()) {
    //             errors.put("blood_code", "Kode Kosong.");
    //             return ResponseUtil.generateErrorMessage("Validasi Error", errors, HttpStatus.CONFLICT);
    //         }

    //         if (!this.bloodTypeRepository.existsByCode(bloodType.getCode())) {
    //             return ResponseUtil.generateErrorMessage("Gagal Merubah Data Golongan Darah",
    //                     "Golongan Darah dengan kode : " + bloodType.getCode() + " Tidak Ditemukan",
    //                     HttpStatus.NOT_FOUND);
    //         }

    //         Boolean kodeExist = this.bloodTypeRepository.existsByCode(kode, bloodType.getId());

    //         Optional<BloodGroup> bloodTypeData = this.bloodTypeRepository.findById(bloodType.getId());


    //         if (CheckBloodType(kode) == false && !kode.isBlank()) {
    //             errors.put("blood_code", "Kode " + kode + " merupakan kode Golongan Darah yang tidak ada");
    //         }

    //         if (kodeExist) {
    //             errors.put("blood_code", kode + " sudah ada.");
    //         }

    //         if (!errors.isEmpty()) {
    //             return ResponseUtil.generateErrorMessage("Validasi Error", errors, HttpStatus.CONFLICT);
    //         }

    //         bloodType.setCreatedBy(bloodTypeData.get().getCreatedBy());
    //         bloodType.setCreatedOn(bloodTypeData.get().getCreatedOn());
    //         bloodType.setModifiedBy(2L);
    //         bloodType.setModifiedOn(new Date());
    //         BloodGroup updatedData = this.bloodTypeRepository.save(bloodType);
    //         return ResponseUtil.generateSuccessMessage("Golongan Darah Berhasil Diubah", updatedData,
    //                 HttpStatus.CREATED);
    //     } catch (Exception e) {
    //         return ResponseUtil.generateErrorMessage("Golongan Darah Gagal Diubah", e.getMessage(),
    //                 HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

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
    // @PutMapping("bloodtype/delete/{id}")
    // public ResponseEntity<ApiResponse<BloodGroup>> deleteBloodType(@PathVariable("id") Long id) {
    //     try {
            // Mencari data berdasarkan ID
    //         Optional<BloodGroup> bloodData = this.bloodTypeRepository.findById(id);
    
            // Jika data tidak ditemukan
    //         if (bloodData.isEmpty()) {
        //          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Golongan Darah Tidak Ditemukan");
    //             return ResponseUtil.generateErrorMessage(
    //                 "Gagal Menghapus Golongan Darah",
    //                 "Golongan Darah dengan ID: " + id + " Tidak Ditemukan",
    //                 HttpStatus.NOT_FOUND
    //             );
    //         }
    
            // Validasi jika data sudah dihapus
    //         BloodGroup bloodType = bloodData.get();
    //         if (Boolean.TRUE.equals(bloodType.getIsDelete())) {
    //              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Golongan Darah Sudah Dihapus");
    //             return ResponseUtil.generateErrorMessage(
    //                 "Gagal Menghapus Golongan Darah",
    //                 "Golongan Darah dengan ID: " + id + " Sudah Dihapus",
    //                 HttpStatus.BAD_REQUEST
    //             );
    //         }
    
            // Menandai data sebagai dihapus
    //         bloodType.setIsDelete(true);
    //         bloodType.setDeletedBy(1L); // Sesuaikan ID pengguna yang melakukan penghapusan
    //         bloodType.setDeletedOn(new Date());
    //         BloodGroup deletedBloodType = this.bloodTypeRepository.save(bloodType);
    
    //         // Mengembalikan respons sukses
    //         return ResponseUtil.generateSuccessMessage(
    //             "Golongan Darah Berhasil Dihapus",
    //             deletedBloodType
    //          return ResponseEntity.status(HttpStatus.OK).body(deletedBloodType);
    //         );
    //     } catch (Exception e) {
            // Menangani error tidak terduga
    //         return ResponseUtil.generateErrorMessage(
    //             "Gagal Menghapus Golongan Darah",
    //             e.getMessage(),
    //             HttpStatus.INTERNAL_SERVER_ERROR
    //          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    //         );
    //     }
    // }

    // @GetMapping("bloodtype/search")
    // public ResponseEntity<ApiResponse<Map<String, Object>>> searchEngine(@RequestParam String search) {
    //     try {
    //         Map<String, Object> stringMap = new HashMap<>();
    //         System.out.println("-----------------------");
    //         System.out.println(search);
    //         stringMap.put("pencarian", search);

    //         List<BloodGroup> hasilPencarian = this.bloodTypeRepository.searching(search);
    //         if (hasilPencarian.isEmpty() || search.contains("%")) {
    //             return ResponseUtil.generateErrorMessage("Golongan Darah Tidak Ditemukan", stringMap,
    //                     HttpStatus.NOT_FOUND);
    //              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Golongan Darah Tidak Ditemukan");
    //         }
    //         stringMap.put("hasil_pencarian", hasilPencarian);

    // return ResponseEntity.status(HttpStatus.OK).body(hasilPencarian);
    //     } catch (Exception e) {
    //          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    //     }
    // }

    // @Autowired
    // private BloodGroupService bloodGroupService;

    // @GetMapping("bloodtype")
    // public ResponseEntity<List<BloodGroup>> getBloodGroup() {
    //     List<BloodGroup> bloodGroups = bloodGroupService.getAllBloodGroups();
    //     return ResponseEntity.ok(bloodGroups);
    // }

    // @GetMapping("bloodtype/{id}")
    // public ResponseEntity<?> getBloodGroup(@PathVariable("id") Long id) {
    //     Optional<BloodGroup> bloodGroup = bloodGroupService.getBloodGroupById(id);
    //     return bloodGroup.map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.status(404).body("Golongan Darah Tidak Ditemukan"));
    // }

    // @PostMapping("bloodtype/add")
    // public ResponseEntity<?> addBloodType(@RequestBody BloodGroup bloodType) {
    //     BloodGroup savedBloodType = bloodGroupService.addBloodGroup(bloodType);
    //     return ResponseEntity.status(201).body(savedBloodType);
    // }

    // @PutMapping("bloodtype/update")
    // public ResponseEntity<?> updateBloodType(@RequestBody BloodGroup bloodType) {
    //     try {
    //         BloodGroup updatedBloodType = bloodGroupService.updateBloodGroup(bloodType);
    //         return ResponseEntity.ok(updatedBloodType);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.status(404).body(e.getMessage());
    //     }
    // }

    // @PutMapping("bloodtype/delete/{id}")
    // public ResponseEntity<?> deleteBloodType(@PathVariable("id") Long id) {
    //     try {
    //         BloodGroup deletedBloodType = bloodGroupService.deleteBloodGroup(id);
    //         return ResponseEntity.ok(deletedBloodType);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.status(404).body(e.getMessage());
    //     }
    // }

    // @GetMapping("bloodtype/search")
    // public ResponseEntity<?> searchEngine(@RequestParam String search) {
    //     List<BloodGroup> results = bloodGroupService.searchBloodGroups(search);
    //     if (results.isEmpty()) {
    //         return ResponseEntity.status(404).body("Golongan Darah Tidak Ditemukan");
    //     }
    //     return ResponseEntity.ok(results);
    // }


}
