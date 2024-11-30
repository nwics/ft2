package com.example.ft2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ft2.model.MCustomer;

public interface MCustomerRepository extends JpaRepository<MCustomer,Long>{
    List<MCustomer> findByIsDelete(Boolean isDelete);
	Page<MCustomer> findByIsDelete(Pageable page, Boolean isDelete);
    MCustomer findByBiodataId(Long biodataId);
    @Query(value="select mb.fullname as name ,extract (year from now()) - extract (year from dob) as usia, mc.gender,mbg.code,mc.rhesus_type,mc.\"height\",mc.weight,mcr.\"name\" as relasi from m_customer_member mcm \r\n"
			+ "left join m_customer mc on mc.id = mcm.customer_id \r\n"
			+ "left join m_biodata mb on mb.id = mc.biodata_id \r\n"
			+ "left join m_customer_relation mcr on mcr.id = mcm.customer_relation_id \r\n"
			+ "left join m_blood_group mbg on mbg.id = mc.blood_group_id\r\n"
			+ "group by mb.fullname, mc.dob, mc.gender , mbg.code , mc.rhesus_type , mc.\"height\",mc.weight,mcr.\"name\"\r\n", nativeQuery=true)
	Page<Object> sortByName(Pageable pageable);

    @Query(value = "select max(id) from m_customer mc", nativeQuery=true)
	Long getMaxCustomerId();
    @Query(value="select mb.fullname,extract (year from now()) - extract (year from dob) as \"usia\",mc.gender,mbg.code,mc.rhesus_type,mc.\"height\",mc.weight,mcr.\"name\" from m_customer_member mcm \r\n"
			+ "left join m_customer mc on mc.id = mcm.customer_id \r\n"
			+ "left join m_biodata mb on mb.id = mc.biodata_id \r\n"
			+ "left join m_customer_relation mcr on mcr.id = mcm.customer_relation_id \r\n"
			+ "left join m_blood_group mbg on mbg.id = mc.blood_group_id;", nativeQuery=true)
	List<Object> findAllPasien();
	
	@Query(value="select * from m_customer mc where is_delete = false", nativeQuery=true)
	Page<MCustomer> getMCustomerByPageable(Pageable pageable);
	
}
