package com.financialos.repository;

import com.financialos.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findByStatus(String status);
    List<CreditCard> findByCardIssuer(String cardIssuer);
}

