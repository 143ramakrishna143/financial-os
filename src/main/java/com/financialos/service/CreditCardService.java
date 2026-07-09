package com.financialos.service;

import com.financialos.model.CreditCard;
import com.financialos.repository.CreditCardRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public Optional<CreditCard> getCreditCardById(Long id) {
        return creditCardRepository.findById(id);
    }

    public CreditCard saveCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    public void deleteCreditCard(Long id) {
        creditCardRepository.deleteById(id);
    }

    public List<CreditCard> getActiveCreditCards() {
        return creditCardRepository.findByStatus("Active");
    }

    public List<CreditCard> getCreditCardsByIssuer(String issuer) {
        return creditCardRepository.findByCardIssuer(issuer);
    }

    public Double getTotalCreditLimit() {
        return creditCardRepository.findAll().stream()
                .mapToDouble(CreditCard::getCreditLimit)
                .sum();
    }

    public Double getTotalCreditCardBalance() {
        return creditCardRepository.findAll().stream()
                .mapToDouble(CreditCard::getCurrentBalance)
                .sum();
    }

    public Double getTotalAvailableCredit() {
        return getTotalCreditLimit() - getTotalCreditCardBalance();
    }
}

