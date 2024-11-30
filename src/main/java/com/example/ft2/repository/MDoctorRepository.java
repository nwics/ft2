package com.example.ft2.repository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

import com.example.ft2.model.MDoctor;

public interface MDoctorRepository extends JpaRepository<MDoctor,Long>{

    @Query(value = "select * from m_doctor where is_delete = false", nativeQuery = true)
	List<MDoctor> findByIsDelete();
	
	Page<MDoctor> findByIsDelete(Pageable page, Boolean isDelete);
	
	@Query(value = "select * from m_doctor where biodata_id = ?1 and is_delete = false", nativeQuery = true)
	MDoctor findByDoctorId(Long id);

	@Query(value = "SELECT tdotp.price_start_from, tdotp.price_until_from, mmf.name, mmf.full_address\n" + //
			"FROM t_doctor_office_treatment_price tdotp \n" + //
			"JOIN t_doctor_office_treatment tdot \n" + //
			"ON tdotp.doctor_office_treatment_id = tdot.id\n" + //
			"JOIN t_doctor_office tdo\n" + //
			"ON tdot.doctor_office_id = tdo.id\n" + //
			"JOIN m_medical_facility mmf\n" + //
			"ON tdo.medical_facility_id = mmf.id\n" + //
			"WHERE tdo.doctor_id = ?1", nativeQuery = true)
    List<Object> findDoctorDetail(Long doctorId);

	@Query(value = "SELECT mmfs.day,mmfs.time_schedule_start, mmfs.time_schedule_end \n" + //
			"FROM t_doctor_office_schedule tdos\n" + //
			"JOIN m_medical_facility_schedule mmfs\n" + //
			"ON tdos.medical_facility_schedule_id = mmfs.id\n" + //
			"JOIN m_medical_facility mmf\n" + //
			"ON mmfs.medical_facility_id = mmf.id\n" + //
			"WHERE tdos.doctor_id = ?1",nativeQuery = true)
	List<Object> listSchedule(Long doctorId);
	
	@Query(value = "select * from m_doctor md\r\n"
			+ "join m_biodata mb on mb.id = md.biodata_id \r\n"
			+ "where mb.fullname ilike concat('%',?1,'%') and md.is_delete = false and mb.is_delete = false", nativeQuery = true)
	Page<MDoctor> findByNamePageable(String name,Pageable page);
	@Query(value = "select distinct on (md.id) md.id,mb.fullname,ms.\"name\",tdt.\"name\",mmf.\"name\", ml.\"name\", ml.parent_id,\r\n"
    + "sum(case \r\n"
    + "	when tdo.end_date is not null then (extract (year from tdo.end_date)) - extract (year from tdo.start_date)\r\n"
    + "	else (extract (year from now()) - extract (year from tdo.start_date))\r\n"
    + "end) as pengalaman, mmfc.\"name\", mb.image_path, mmfs.day,ml.id, ms.id, tdt.id\r\n"
    + "from m_doctor md \r\n"
    + "join m_biodata mb on mb.id = md.biodata_id \r\n"
    + "join t_current_doctor_specialization tcds on tcds.doctor_id = md.id \r\n"
    + "join m_specialization ms on ms.id = tcds.specialization_id\r\n"
    + "join t_doctor_treatment tdt on tdt.doctor_id  = md.id\r\n"
    + "join t_doctor_office tdo on md.id = tdo.doctor_id\r\n"
    + "join m_medical_facility mmf on mmf.id = tdo.medical_facility_id \r\n"
    + "left join m_medical_facility_category mmfc on mmf.medical_facility_category_id = mmfc.id \r\n"
    + "left join m_medical_facility_schedule mmfs on mmfs.medical_facility_id = mmf.id\r\n"
    + "join m_location ml on ml.id = mmf.location_id \r\n"
    + "where mb.fullname ilike concat('%',:name,'%') and (:namaSpesialis is null or ms.id = :namaSpesialis) and\r\n"
    + "(case \r\n"
    + "	when :namaTindakan is null then tdt.\"name\" ilike concat('%','','%')\r\n"
    + "	when :namaTindakan = '' then tdt.\"name\" ilike concat('%','','%')\r\n"
    + "	else tdt.\"name\" = :namaTindakan\r\n"
    + "end) and (:lokasiID is null or ml.parent_id = :lokasiID) and md.is_delete = false and tdt.is_delete = false and ms.is_delete = false and ml.is_delete = false and "
    + "mmf.is_delete = false and mmfc.is_delete = false and mb.is_delete = false and tdo.is_delete = false and mmfs.is_delete = false \r\n"
    + "group by md.id, md.biodata_id ,mb.fullname ,tcds.id,tcds.specialization_id ,ms.id ,ms.\"name\",tdt.id,tdt.\"name\",\r\n"
    + "tdo.id ,tdo.medical_facility_id,mmf.id , mmf.\"name\" ,mmf.location_id, ml.id , ml.\"name\", ml.parent_id, mmfc.\"name\", mb.image_path,\r\n"
    + "mmfs.day,ml.id order by md.id asc limit :limit  offset :offset", nativeQuery = true)
List<Object> findBySearchWithLokasi(String name,Long namaSpesialis,String namaTindakan,Long lokasiID, Long limit, Long offset);

@Query(value = "select distinct on (md.id) md.id,mb.fullname,ms.\"name\",tdt.\"name\",mmf.\"name\", ml.\"name\", ml.parent_id,\r\n"
    + "sum(case \r\n"
    + "	when tdo.end_date is not null then (extract (year from tdo.end_date)) - extract (year from tdo.start_date)\r\n"
    + "	else (extract (year from now()) - extract (year from tdo.start_date))\r\n"
    + "end) as pengalaman, mmfc.\"name\", mb.image_path, mmfs.day,ml.id, ms.id, tdt.id\r\n"
    + "from m_doctor md \r\n"
    + "join m_biodata mb on mb.id = md.biodata_id \r\n"
    + "join t_current_doctor_specialization tcds on tcds.doctor_id = md.id \r\n"
    + "join m_specialization ms on ms.id = tcds.specialization_id\r\n"
    + "join t_doctor_treatment tdt on tdt.doctor_id  = md.id\r\n"
    + "join t_doctor_office tdo on md.id = tdo.doctor_id\r\n"
    + "join m_medical_facility mmf on mmf.id = tdo.medical_facility_id \r\n"
    + "left join m_medical_facility_category mmfc on mmf.medical_facility_category_id = mmfc.id \r\n"
    + "left join m_medical_facility_schedule mmfs on mmfs.medical_facility_id = mmf.id\r\n"
    + "join m_location ml on ml.id = mmf.location_id \r\n"
    + "where mb.fullname ilike concat('%',:name,'%') and (:namaSpesialis is null or ms.id = :namaSpesialis) and\r\n"
    + "(case \r\n"
    + "	when :namaTindakan is null then tdt.\"name\" ilike concat('%','','%')\r\n"
    + "	when :namaTindakan = '' then tdt.\"name\" ilike concat('%','','%')\r\n"
    + "	else tdt.\"name\" = :namaTindakan\r\n"
    + "end) and (:lokasiID is null or ml.parent_id = :lokasiID) and md.is_delete = false and tdt.is_delete = false and ms.is_delete = false and ml.is_delete = false and "
    + "mmf.is_delete = false and mmfc.is_delete = false and mb.is_delete = false and tdo.is_delete = false and mmfs.is_delete = false \r\n"
    + "group by md.id, md.biodata_id ,mb.fullname ,tcds.id,tcds.specialization_id ,ms.id ,ms.\"name\",tdt.id,tdt.\"name\",\r\n"
    + "tdo.id ,tdo.medical_facility_id,mmf.id , mmf.\"name\" ,mmf.location_id, ml.id , ml.\"name\", ml.parent_id, mmfc.\"name\", mb.image_path,\r\n"
    + "mmfs.day,ml.id order by md.id asc ", nativeQuery = true)
List<Object> showBySearchWithLokasiAll(String name,Long namaSpesialis,String namaTindakan,Long lokasiID);
}
