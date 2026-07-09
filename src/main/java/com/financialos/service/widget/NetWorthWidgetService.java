package com.financialos.service.widget;

import com.financialos.dto.widget.NetWorthWidgetDTO;
import com.financialos.service.FinanceFacade;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class NetWorthWidgetService {

    private final FinanceFacade financeFacade;

    public NetWorthWidgetService(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    public NetWorthWidgetDTO get() {
        NetWorthWidgetDTO dto = new NetWorthWidgetDTO();
        Map<String, Object> net = financeFacade.getNetWorth();
        // net map contains BigDecimal values under keys: totalBalance, mutualFundValue, stockValue, assets, netWorth
        Object tb = net.get("totalBalance");
        Object mf = net.get("mutualFundValue");
        Object sv = net.get("stockValue");
        Object assets = net.get("assets");
        Object nw = net.get("netWorth");
        if (tb instanceof BigDecimal) dto.setTotalBalance((BigDecimal) tb);
        if (mf instanceof BigDecimal) dto.setMutualFundValue((BigDecimal) mf);
        if (sv instanceof BigDecimal) dto.setStockValue((BigDecimal) sv);
        if (assets instanceof BigDecimal) dto.setAssets((BigDecimal) assets);
        if (nw instanceof BigDecimal) dto.setNetWorth((BigDecimal) nw);
        return dto;
    }
}

