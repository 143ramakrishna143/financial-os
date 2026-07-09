package com.financialos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tickerSymbol;

    private String companyName;

    private Integer quantity;

    private Double buyPrice;

    private Double currentPrice;

    private Double totalInvested;

    private LocalDate purchaseDate;

    private String notes;

    public Stock() {
    }

    public Stock(String tickerSymbol, String companyName, Integer quantity, 
                 Double buyPrice, Double currentPrice, LocalDate purchaseDate) {
        this.tickerSymbol = tickerSymbol;
        this.companyName = companyName;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;
        this.totalInvested = quantity * buyPrice;
        this.purchaseDate = purchaseDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getTotalInvested() {
        return totalInvested;
    }

    public void setTotalInvested(Double totalInvested) {
        this.totalInvested = totalInvested;
    }

    public Double getCurrentValue() {
        return (currentPrice != null && quantity != null) ? currentPrice * quantity : 0;
    }

    public Double getProfit() {
        return getCurrentValue() - totalInvested;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

