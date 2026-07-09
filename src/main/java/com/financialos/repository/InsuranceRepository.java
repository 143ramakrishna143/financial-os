package com.financialos.repository;

import com.financialos.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    List<Insurance> findByInsuranceType(String insuranceType);
    List<Insurance> findByStatus(String status);
}

