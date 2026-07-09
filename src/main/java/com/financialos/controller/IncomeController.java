package com.financialos.controller;

import com.financialos.model.Income;
import com.financialos.repository.IncomeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    private final IncomeRepository incomeRepository;

    public IncomeController(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @GetMapping
    public List<Income> getAll() {
        return incomeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Income getOne(@PathVariable Long id) {
        return incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found: " + id));
    }

    @PostMapping
    public Income create(@RequestBody Income income) {
        return incomeRepository.save(income);
    }

    @PutMapping("/{id}")
    public Income update(@PathVariable Long id, @RequestBody Income updated) {
        Income existing = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found: " + id));
        existing.setSource(updated.getSource());
        existing.setAmount(updated.getAmount());
        existing.setDate(updated.getDate());
        existing.setNotes(updated.getNotes());
        return incomeRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        incomeRepository.deleteById(id);
    }
}
