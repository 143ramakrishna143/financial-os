package com.financialos.repository;

import com.financialos.model.MutualFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MutualFundRepository extends JpaRepository<MutualFund, Long> {
    List<MutualFund> findByFundName(String fundName);
}

