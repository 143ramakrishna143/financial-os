package com.financialos.dto.widget;

public class FinancialHealthWidgetDTO {
    private Integer score; // 0-100
    private Double emergencyFundPercent;
    private Double savingsRatePercent;
    private Double investmentRatioPercent;

    public FinancialHealthWidgetDTO() {}

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Double getEmergencyFundPercent() { return emergencyFundPercent; }
    public void setEmergencyFundPercent(Double emergencyFundPercent) { this.emergencyFundPercent = emergencyFundPercent; }

    public Double getSavingsRatePercent() { return savingsRatePercent; }
    public void setSavingsRatePercent(Double savingsRatePercent) { this.savingsRatePercent = savingsRatePercent; }

    public Double getInvestmentRatioPercent() { return investmentRatioPercent; }
    public void setInvestmentRatioPercent(Double investmentRatioPercent) { this.investmentRatioPercent = investmentRatioPercent; }
}

