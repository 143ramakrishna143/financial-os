package com.financialos.service;

import com.financialos.model.Stock;
import com.financialos.repository.StockRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Long id) {
        return stockRepository.findById(id);
    }

    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    public Double getTotalStockInvested() {
        return stockRepository.findAll().stream()
                .mapToDouble(Stock::getTotalInvested)
                .sum();
    }

    public Double getTotalStockCurrentValue() {
        return stockRepository.findAll().stream()
                .mapToDouble(Stock::getCurrentValue)
                .sum();
    }

    public Double getTotalStockProfit() {
        return getTotalStockCurrentValue() - getTotalStockInvested();
    }
}

