package com.financialos.dashboard.service;

import com.financialos.dto.DashboardOverviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * High-level dashboard service that coordinates dashboard composition.
 *
 * Responsibilities:
 * - Delegate widget execution to DashboardEngine
 * - Aggregate responses into DashboardOverviewDTO
 * - Provide simple public API for controllers
 *
 * Design: Thin wrapper over DashboardEngine
 *
 * This service keeps the public API stable while allowing DashboardEngine
 * to handle internal orchestration complexity and future optimizations
 * (caching, parallelization, metrics, etc.).
 *
 * Architecture:
 * DashboardControllerV2
 *     ↓ (calls)
 * DashboardService
 *     ↓ (delegates to)
 * DashboardEngine
 *     ↓ (executes)
 * Widgets
 */
@Service
public class DashboardService {
    private final DashboardEngine dashboardEngine;

    public DashboardService(DashboardEngine dashboardEngine) {
        this.dashboardEngine = dashboardEngine;
    }

    /**
     * Builds a complete dashboard overview by executing all registered widgets.
     *
     * Delegates to DashboardEngine for actual orchestration.
     *
     * @return DashboardOverviewDTO containing all widget responses
     */
    public DashboardOverviewDTO getDashboardOverview() {
        return dashboardEngine.buildDashboardOverview();
    }

    /**
     * Returns the count of registered widgets.
     *
     * @return number of registered widgets
     */
    public int getWidgetCount() {
        return dashboardEngine.getWidgetCount();
    }

    /**
     * Returns the execution order of widgets.
     *
     * @return list of widget IDs in execution order
     */
    public List<String> getWidgetOrder() {
        return dashboardEngine.getWidgetOrder();
    }
}
