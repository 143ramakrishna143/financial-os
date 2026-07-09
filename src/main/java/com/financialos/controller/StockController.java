package com.financialos.controller;

import com.financialos.model.Stock;
import com.financialos.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Optional<Stock> stock = stockService.getStockById(id);
        return stock.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock created = stockService.saveStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        Optional<Stock> existing = stockService.getStockById(id);
        if (existing.isPresent()) {
            stock.setId(id);
            Stock updated = stockService.saveStock(stock);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<StockSummary> getStockSummary() {
        StockSummary summary = new StockSummary(
                stockService.getTotalStockInvested(),
                stockService.getTotalStockCurrentValue(),
                stockService.getTotalStockProfit()
        );
        return ResponseEntity.ok(summary);
    }

    public static class StockSummary {
        public Double totalInvested;
        public Double totalCurrentValue;
        public Double totalProfit;

        public StockSummary(Double totalInvested, Double totalCurrentValue, Double totalProfit) {
            this.totalInvested = totalInvested;
            this.totalCurrentValue = totalCurrentValue;
            this.totalProfit = totalProfit;
        }
    }
}

