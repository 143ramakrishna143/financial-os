package com.financialos.controller.v2;

import com.financialos.dashboard.service.DashboardService;
import com.financialos.dto.DashboardOverviewDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API endpoint for dashboard overview.
 *
 * This controller is responsible for:
 * - Receiving HTTP requests for dashboard data
 * - Delegating to DashboardService for orchestration
 * - Returning aggregated responses to clients
 *
 * The controller MUST ONLY call DashboardService.
 * All widget orchestration and execution is handled by DashboardService.
 */
@RestController
@RequestMapping("/api/v2/dashboard")
public class DashboardControllerV2 {

    private final DashboardService dashboardService;

    public DashboardControllerV2(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Get complete dashboard overview.
     *
     * Executes all registered dashboard widgets and returns aggregated responses
     * in a unified DashboardOverviewDTO format.
     *
     * @return ResponseEntity containing DashboardOverviewDTO with all widget responses
     */
    @GetMapping
    public ResponseEntity<DashboardOverviewDTO> getOverview() {
        DashboardOverviewDTO overview = dashboardService.getDashboardOverview();
        return ResponseEntity.ok(overview);
    }
}

