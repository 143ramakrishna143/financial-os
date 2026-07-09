package com.financialos.service;

import com.financialos.model.Insurance;
import com.financialos.repository.InsuranceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    public List<Insurance> getAllInsurances() {
        return insuranceRepository.findAll();
    }

    public Optional<Insurance> getInsuranceById(Long id) {
        return insuranceRepository.findById(id);
    }

    public Insurance saveInsurance(Insurance insurance) {
        return insuranceRepository.save(insurance);
    }

    public void deleteInsurance(Long id) {
        insuranceRepository.deleteById(id);
    }

    public List<Insurance> getInsuranceByType(String insuranceType) {
        return insuranceRepository.findByInsuranceType(insuranceType);
    }

    public List<Insurance> getActiveInsurances() {
        return insuranceRepository.findByStatus("Active");
    }

    public Double getTotalAnnualPremium() {
        return insuranceRepository.findAll().stream()
                .mapToDouble(Insurance::getPremiumAmount)
                .sum();
    }
}

