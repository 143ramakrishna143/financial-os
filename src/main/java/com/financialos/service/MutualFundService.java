package com.financialos.service;

import com.financialos.model.MutualFund;
import com.financialos.repository.MutualFundRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MutualFundService {

    private final MutualFundRepository mutualFundRepository;

    public MutualFundService(MutualFundRepository mutualFundRepository) {
        this.mutualFundRepository = mutualFundRepository;
    }

    public List<MutualFund> getAllMutualFunds() {
        return mutualFundRepository.findAll();
    }

    public Optional<MutualFund> getMutualFundById(Long id) {
        return mutualFundRepository.findById(id);
    }

    public MutualFund saveMutualFund(MutualFund mutualFund) {
        return mutualFundRepository.save(mutualFund);
    }

    public void deleteMutualFund(Long id) {
        mutualFundRepository.deleteById(id);
    }

    public Double getTotalMutualFundInvested() {
        return mutualFundRepository.findAll().stream()
                .mapToDouble(MutualFund::getAmountInvested)
                .sum();
    }

    public Double getTotalMutualFundCurrentValue() {
        return mutualFundRepository.findAll().stream()
                .mapToDouble(MutualFund::getCurrentValue)
                .sum();
    }

    public Double getTotalMutualFundProfit() {
        return getTotalMutualFundCurrentValue() - getTotalMutualFundInvested();
    }
}

