package com.example.ft2.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ft2.model.MDoctor;
import com.example.ft2.repository.MDoctorRepository;

@RestController
// @CrossOrigin("*")
@RequestMapping("/doctor/")
public class ApiMDoctorController {
    // @AutowiredMDoctorRepository mDoctorRepository;
    @Autowired MDoctorRepository mDoctorRepository;

    // cek data
    @GetMapping("search")
    public ResponseEntity<?> searchDoctors(@RequestParam(required = false, defaultValue = "") String name,
    @RequestParam(required = false) Long namaSpesialis,
    @RequestParam(required = false) String namaTindakan,
    @RequestParam(required = false) Long lokasiID,
    @RequestParam(defaultValue = "10") Long limit,
    @RequestParam(defaultValue = "0") Long offset){
        List<?> results = this.mDoctorRepository.findBySearchWithLokasi(name, namaSpesialis, namaTindakan, lokasiID, limit, offset);
        // return ResponseEntity.ok(results);
    List<Object> modifiedResults = new ArrayList<>(results);
    // Object temporaryObject = Arrays.asList(
    //     99, // ID sementara
    //     "Dr. Temporary", // Nama dokter
    //     "General Medicine", // Spesialisasi
    //     "Health Checkup", // Tindakan
    //     "Temporary Clinic", // Nama fasilitas medis
    //     "Jakarta", // Lokasi
    //     0, // Parent ID
    //     0, // Pengalaman (contoh: tahun)
    //     "Temporary Facility", // Kategori fasilitas
    //     "/images/doctors/temporary.jpg", // Path gambar
    //     "Friday", // Jadwal
    //     11, // Lokasi ID
    //     5, // ID Spesialisasi
    //     7  // ID Tindakan
    // );
    Object temporaryObject = Arrays.asList(
        Arrays.asList(4, "Dr. Alex Johnson", "Pediatrics", "Heart Checkup", "RS Heart Center", "Jakarta", 1, 5, "Hospital", "/images/doctors/alex_johnson.jpg", "Monday", 4, 1, 1),
        Arrays.asList(5, "Dr. Lisa Brown", "Pediatrics", "Vaccination", "Klinik Anak Sehat", "Surabaya", 2, 3, "Clinic", "/images/doctors/lisa_brown.jpg", "Tuesday", 6, 3, 3),
        Arrays.asList(6, "Dr. Mark Spencer", "Orthopedics", "Bone Surgery", "RS Ortopedi", "Bandung", 3, 7, "Hospital", "/images/doctors/mark_spencer.jpg", "Wednesday", 6, 3, 3),
        Arrays.asList(7, "Dr. Emily White", "Dermatology", "Skin Treatment", "Klinik Kulit Cantik", "Yogyakarta", 4, 4, "Clinic", "/images/doctors/emily_white.jpg", "Thursday", 8, 4, 4),
        Arrays.asList(8, "Dr. Daniel Green", "Neurology", "Brain Examination", "RS Otak Cerdas", "Medan", 5, 6, "Hospital", "/images/doctors/daniel_green.jpg", "Friday", 4, 1, 1),
        Arrays.asList(9, "Dr. Sophia Turner", "Pediatrics", "Eye Checkup", "Klinik Mata Sehat", "Semarang", 6, 2, "Clinic", "/images/doctors/sophia_turner.jpg", "Saturday", 4, 1, 2),
        Arrays.asList(10, "Dr. Michael Evans", "Pediatrics", "Lung Treatment", "RS Paru Sehat", "Malang", 7, 8, "Hospital", "/images/doctors/michael_evans.jpg", "Sunday", 4, 2, 3),
        Arrays.asList(11, "Dr. Olivia Scott", "Cardiology", "Diabetes Treatment", "Klinik Hormonal", "Palembang", 8, 3, "Clinic", "/images/doctors/olivia_scott.jpg", "Monday", 2, 2, 2),
        Arrays.asList(12, "Dr. William Brown", "Orthopedics", "Stomach Examination", "RS Pencernaan", "Bogor", 9, 4, "Hospital", "/images/doctors/william_brown.jpg", "Tuesday", 6, 2, 3)
    );
    modifiedResults.add(temporaryObject);
    return ResponseEntity.ok(modifiedResults);
    

    // results.add(new{
    //     4,"Dr. Michael Brown","Neurology","Brain Surgery","RS Cipto Mangunkusumo","Jakarta",3,0,"Hospital","/images/doctors/michael_brown.jpg","Thursday",5,2,2 
    // });
    }
    
    @GetMapping("all")
    public ResponseEntity<List<MDoctor>> getAllDoctors(){
        List<MDoctor> doctors = mDoctorRepository.findByIsDelete();
        return ResponseEntity.ok(doctors);
    }
	// Response-----------------------------------------------------------------------------------------------------------
	private Map<String, Object> response(String status, String message, Object data, Object schedule) {

		List<String> keyData = new ArrayList<>();
		keyData.add("priceStartFrom");
		keyData.add("priceUntilFrom");
		keyData.add("name");
		keyData.add("fullAddress");

		List<Object> doctorDetailResponse = new ArrayList<>();
		List<Object> doctorDetail = (List<Object>) data;
		for (int i = 0; i < doctorDetail.size(); i++) {
			Map<String, Object> doctorDetailMap = new HashMap<>();
			Object[] doctorDetailObj = (Object[]) doctorDetail.get(i);
			for (int j = 0; j < doctorDetailObj.length; j++) {
				doctorDetailMap.put(keyData.get(j), doctorDetailObj[j]);
			}
			doctorDetailResponse.add(doctorDetailMap);
		}

		List<String> keySchedule = new ArrayList<>();
		keySchedule.add("day");
		keySchedule.add("timeScheduleStart");
		keySchedule.add("timeScheduleEnd");

		List<Object> scheduleResponse = new ArrayList<>();
		List<Object> scheduleList = (List<Object>) schedule;
		for (int i = 0; i < scheduleList.size(); i++) {
			Map<String, Object> scheduleMap = new HashMap<>();
			Object[] scheduleObj = (Object[]) scheduleList.get(i);
			for (int j = 0; j < scheduleObj.length; j++) {
				scheduleMap.put(keySchedule.get(j), scheduleObj[j]);
			}
			scheduleResponse.add(scheduleMap);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("status", status);
		response.put("message", message);
		response.put("data", doctorDetailResponse);
		response.put("schedule", scheduleResponse);

		return response;
	}

	// Doctor
	// detail-----------------------------------------------------------------------------------------------------------
	@GetMapping("detail/{id}")
	public ResponseEntity<Map<String, Object>> getDetailDoctorById(@PathVariable("id") Long id) {
		try {
			List<Object> doctorDetail = this.mDoctorRepository.findDoctorDetail(id);
			List<Object> schedule = this.mDoctorRepository.listSchedule(id);
			if (doctorDetail.isEmpty()) {
				return new ResponseEntity<>(response("success", "No Data", doctorDetail, schedule), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(response("success", "Success Fetch Data", doctorDetail, schedule),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>(), new ArrayList<>()),
					HttpStatus.OK);
		}
	}
	// -----------------------------------------------------------------------------------------------------------

	@GetMapping("show")
	public ResponseEntity<List<MDoctor>> findAllDoctor() {
		try {
			List<MDoctor> listDoctor = this.mDoctorRepository.findByIsDelete();
			return new ResponseEntity<>(listDoctor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("{id}")
	public ResponseEntity<Object> getDoctorById(@PathVariable("id") Long id) {
		try {
			MDoctor doctor = this.mDoctorRepository.findByDoctorId(id);
			return new ResponseEntity<Object>(doctor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

    @GetMapping("pagingsearch")
	public ResponseEntity<Map<String, Object>> getAllDoctorBySearchWithLocation(
			@RequestParam(required = false) String namaDokter, @RequestParam(required = true) Long idSpesialis,
			@RequestParam(required = false) String namaTindakan, @RequestParam(required = false) Long lokasiId,
			@RequestParam(defaultValue = "4") Long perPage, @RequestParam(defaultValue = "0") Long offset,
			@RequestParam(defaultValue = "0") Long page) {
		try {
			offset = perPage * page;
			List<Object> allData = this.mDoctorRepository.showBySearchWithLokasiAll(namaDokter, idSpesialis,
					namaTindakan, lokasiId);
			List<Object> pages = this.mDoctorRepository.findBySearchWithLokasi(namaDokter, idSpesialis, namaTindakan,
					lokasiId, perPage, offset);

			List<Map<String, Object>> dokterResponse = new ArrayList<>();

			for (Object dokterObj : pages) {
				Map<String, Object> dokterMap = new HashMap<>();
				Object[] dokterArray = (Object[]) dokterObj;

				dokterMap.put("dokterId", dokterArray[0]);
				dokterMap.put("fullName", dokterArray[1]);
				dokterMap.put("namaSpesialis", dokterArray[2]);
				dokterMap.put("namaTindakan", dokterArray[3]);
				dokterMap.put("namaMedicalFacility", dokterArray[4]);
				dokterMap.put("namaKecamatan", dokterArray[5]);
				dokterMap.put("parentId", dokterArray[6]);
				dokterMap.put("pengalaman", dokterArray[7]);
				dokterMap.put("jenisRumahSakit", dokterArray[8]);
				dokterMap.put("fotoUrl", dokterArray[9]);
				dokterMap.put("hariBuka", dokterArray[10]);
				dokterMap.put("lokasiId", dokterArray[11]);

				dokterResponse.add(dokterMap);
			}

			int total = (int) Math.ceil((float)allData.size() / (float)perPage);
			if (total == 0) {
				total = 1;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", dokterResponse);
			response.put("total", allData.size());
			response.put("totalPages", total);
			response.put("page", page);
			response.put("perPage", perPage);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			Map<String, Object> err = new HashMap<>();
			err.put("error", e.getMessage());
			return new ResponseEntity<>(err,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
